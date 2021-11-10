/*
 * @(#) Users.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.common.IAccountService;
import edu.funix.common.imp.UserService;
import edu.funix.model.PageModel;
import edu.funix.model.UserModel;
import edu.funix.utils.PageInfo;
import edu.funix.utils.PageType;
import edu.funix.utils.SlackApiUtil;

/**
 * Servlet implementation class Users
 */
@WebServlet("/admin/users")
public class Users extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Users.class);
    private IAccountService<UserModel> userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Users() {
	userService = new UserService();
    }

    /**
     * Display user base on filter specified by client or/and pagination. Also,
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
	UserModel users = new UserModel();
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
	    if (ids != null && action.equals("delete")) {
		logger.debug("delete user(s): " + ids);
		userService.delete(ids);
		message = "Delete success";
	    }
	    logger.debug("Mapping pagination's attributes to page model");
	    BeanUtils.populate(page, request.getParameterMap());
	    logger.debug("{}", page);
	    /* Finds the users base on search term */
	    if (searchKey != null) {
		logger.debug("Display posts by search key: " + searchKey + " on " + page.getCurrentPage());
		users.setListResult(userService.search(page, searchKey));
	    } else {
		logger.debug("Display posts on page " + page.getCurrentPage());
		users.setListResult(userService.findAll(page));
	    }
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("searchKey", searchKey);
	request.setAttribute("page", page);
	request.setAttribute("users", users);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	logger.info("Forward to user management page");
	PageInfo.PrepareAndForward(request, response, PageType.USER_MANAGEMENT_PAGE);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	doGet(request, response);
    }

}
