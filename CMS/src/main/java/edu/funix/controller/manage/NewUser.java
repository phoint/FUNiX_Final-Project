/*
 * @(#) NewUser.java 1.0 2021/09/06
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

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.SlackApiUtil;
import edu.funix.common.IUserService;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/admin/new-user")
public class NewUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NewUser.class);
    private IUserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
	userService = new UserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	PageInfo.PrepareAndForward(request, response, PageType.NEW_USER);
    }

    /**
     * Create new account for user
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	UserModel user = new UserModel();
	String message = null;
	String error = null;
	try {
	    logger.debug("Mapping user's attribute to user model");
	    BeanUtils.populate(user, request.getParameterMap());
	    logger.debug("{}", user);
	    try {
		logger.debug("Insert new user");
		Long id = userService.save(user);
		logger.debug("User's id: " + id);
		message = "Success";
		request.setAttribute("users", userService.findAll());
	    } catch (Exception e) {
		if (e.getMessage().contains("UQ_tblUSER_UserMail")) {
		    error = "Email is existed!";
		} else if (e.getMessage().contains("UQ_tblUSER_Username")) {
		    error = "Username is existed!";
		} else {
		    error = e.getMessage();
		}
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
	if (error != null) {
	    logger.info("Stay in page if failed");
	    PageInfo.PrepareAndForward(request, response, PageType.NEW_USER);
	} else {
	    logger.info("Redirect to user management page if success");
	    response.sendRedirect(request.getContextPath() + "/admin/users");
	}
    }

}
