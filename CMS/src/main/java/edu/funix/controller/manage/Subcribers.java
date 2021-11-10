/*
 * @(#) Users.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.manage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.common.IAccountService;
import edu.funix.common.imp.SubcriberService;
import edu.funix.model.AccountModel;
import edu.funix.model.CategoryModel;
import edu.funix.model.PageModel;
import edu.funix.model.SubcriberModel;
import edu.funix.utils.Mailer;
import edu.funix.utils.PageInfo;
import edu.funix.utils.PageType;
import edu.funix.utils.PasswordUtils;
import edu.funix.utils.SlackApiUtil;

/**
 * Servlet implementation class Subcribers
 */
@WebServlet("/admin/subcribers")
public class Subcribers extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Subcribers.class);
    private IAccountService<SubcriberModel> subcriberService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Subcribers() {
	subcriberService = new SubcriberService();
    }

    /**
     * Display subcriber base on filter specified by client or/and pagination. Also,
     * this method take action on delete items when getting suitable parameter
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	String[] ids = request.getParameterValues("id");
	String action = request.getParameter("action");
	String searchKey = request.getParameter("searchKey");
	SubcriberModel subcriber = new SubcriberModel();
	PageModel page = new PageModel();
	String message = null;
	String error = null;

	/*
	 * Sets the search key to null if return empty string. This process help the
	 * service calling the right DAO's method
	 */
	if (searchKey != null && searchKey.trim().equals("")) {
	    logger.debug("convert empty searchKey to null - searchKey: " + searchKey);
	    searchKey = null;
	}
	try {
	    /* Check parameter for deleting feature */
	    try {
		if (ids != null && action.equals("delete")) {
		    logger.debug("delete user(s): " + ids);
		    subcriberService.delete(ids);
		    message = "Delete success";
		} else if (ids != null && action != null && action.equals("edit")) {
		    logger.debug("Find subcriber by id = " + ids[0]);
		    subcriber = subcriberService.findBy(Long.parseLong(ids[0]));
		}
	    } catch (Exception e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	    logger.debug("Mapping pagination's attributes to page model");
	    BeanUtils.populate(page, request.getParameterMap());
	    logger.debug("{}", page);
	    /* Finds the users base on search term */
	    try {
		if (searchKey != null) {
		    logger.debug("Display posts by search key: " + searchKey + " on " + page.getCurrentPage());
		    subcriber.setListResult(subcriberService.search(page, searchKey));
		} else {
		    logger.debug("Display posts on page " + page.getCurrentPage());
		    subcriber.setListResult(subcriberService.findAll(page));
		}
	    } catch (Exception e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("searchKey", searchKey);
	request.setAttribute("page", page);
	request.setAttribute("subcribers", subcriber);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	logger.info("Forward to user management page");
	PageInfo.PrepareAndForward(request, response, PageType.SUBCRIBER_MANAGEMENT_PAGE);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	response.setContentType("text/html;charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	String[] ids = request.getParameterValues("id");
	PageModel page = new PageModel();
	SubcriberModel subcriber = new SubcriberModel();
	/* Message and error return on process */
	String message = null;
	String error = null;
	try {
	    logger.debug("Mapping subcriber's attribute to subcriber model");
	    BeanUtils.populate(subcriber, request.getParameterMap());
	    logger.debug("{}", subcriber);
	    try {
		if (ids != null && action != null && action.equals("delete")) {
		    logger.debug("Delete subcriber(s) by id = " + ids);
		    subcriberService.delete(ids);
		    message = "Subcriber(s) are deleted";
		} else if (action != null && action.equals("edit")) {
		    logger.debug("Update subcriber's information: ");
		    logger.debug("{}", subcriber);
		    subcriberService.edit(subcriber);
		    message = "Subcriber is updated";
		} else if (action != null && action.equals("resetPwd")) {
		    logger.debug("Reset password and send to email");
		    subcriber.setPassword(PasswordUtils.generate(10));
		    logger.debug("Update user with new password: " + subcriber.getPassword());
		    subcriberService.edit(subcriber);
		    logger.debug("Send password by email: " + subcriber.getEmail());
		    Mailer.send(subcriber.getEmail(), "Reset Password", Mailer.getTemplate(subcriber.getPassword()));
		    message = "New password send";
		    subcriber = subcriberService.findBy(subcriber.getId());
		} else {
		    /* Auto generate a safe password has length = 10 */
		    logger.debug("Generate random password");
		    String password = PasswordUtils.generate(10);
		    subcriber.setPassword(password);
		    logger.debug("Insert new subcriber into database with password: " + subcriber.getPassword());
		    subcriberService.save(subcriber);
		    /* Send the password to user by mail */
		    Mailer.send(subcriber.getEmail(), "Hello New User", Mailer.getTemplate(password));
		    logger.debug("Send password to user by mail - Success");
		    subcriber = new SubcriberModel();
		    message = "New subcriber inserted";
		}
		logger.debug("Get new subcriber list");
		subcriber.setListResult(subcriberService.findAll(page));
		logger.debug("{}", subcriber.getListResult());
	    } catch (Exception e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	} catch (IllegalAccessException | InvocationTargetException e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	request.setAttribute("subcribers", subcriber);
	request.setAttribute("page", page);
	logger.info("Forward to subcriber management page");
	PageInfo.PrepareAndForward(request, response, PageType.SUBCRIBER_MANAGEMENT_PAGE);
    }
}
