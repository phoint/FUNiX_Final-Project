/*
 * @(#) Register.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.web;

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
import edu.funix.Utils.SessionUtil;
import edu.funix.Utils.SlackApiUtil;
import edu.funix.common.IAccountService;
import edu.funix.common.imp.SubcriberService;
import edu.funix.common.imp.UserService;
import edu.funix.domain.ChangePasswordForm;
import edu.funix.model.AccountModel;
import edu.funix.model.SubcriberModel;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class PasswordChange
 */
@WebServlet("/account/password-change")
public class PasswordChange extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PasswordChange.class);
    private IAccountService<UserModel> userService;
    private IAccountService<SubcriberModel> subcriberService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordChange() {
	userService = new UserService();
	subcriberService = new SubcriberService();
    }

    /**
     * Forward to change password page
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	PageInfo.account(request, response, PageType.PASSWORD_CHANGE);
    }

    /**
     * This method is available for login users only. Firstly, the current password
     * will be checked. If the current password is matching, the new password will
     * be updated.
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	/* A specific model which store the change password form's value */
	ChangePasswordForm newPwd = new ChangePasswordForm();
	String error = null;
	String message = null;
	try {
	    logger.debug("Mapping password changing to form model");
	    BeanUtils.populate(newPwd, request.getParameterMap());
	    logger.debug("{}", newPwd);
	} catch (IllegalAccessException | InvocationTargetException e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	logger.debug("Get login username from session");
	newPwd.setUsername(SessionUtil.getLoginedUsername(request));
	logger.debug("{}", newPwd);
	try {
	    logger.debug("Change user password and logout current session");
	    String userType = (String) SessionUtil.get(request, "userType");
	    if (userType.equals("User")) {
		logger.debug("Account is an user");
		userService.changePassword(newPwd);		
	    } else {
		logger.debug("Account is a subcriber");
		subcriberService.changePassword(newPwd);
	    }
	    /* After changing successful, user will be log out */
	    SessionUtil.invalidate(request);
	    message = "Password changed!";
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("error", error);
	request.setAttribute("message", message);
	logger.info("Forward to password change page");
	PageInfo.account(request, response, PageType.PASSWORD_CHANGE);
    }

}
