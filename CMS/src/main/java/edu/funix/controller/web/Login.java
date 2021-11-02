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
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.Utils.Mailer;
import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.PasswordUtils;
import edu.funix.Utils.SessionUtil;
import edu.funix.Utils.SlackApiUtil;
import edu.funix.common.IUserService;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Login.class);
    private IUserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
	userService = new UserService();
    }

    /**
     * Redirects or forwards to specific page base on corresponding parameter like
     * resetPwd or logout. If the request url does not have parameter, the login
     * page will be displayed.
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	String action = request.getParameter("action");
	if (action != null && action.equals("logout")) {
	    logger.debug("User logout, invalidate session");
	    SessionUtil.invalidate(request);
	    logger.debug("{}", SessionUtil.getLoginedUsername(request));
	    response.sendRedirect(request.getContextPath());
	    return;
	}
	if (action != null && action.equals("resetPwd")) {
	    logger.debug("Forward to change password page");
	    PageInfo.login(request, response, PageType.FORGOT_PASSWORD);
	    return;
	}
	logger.debug("Forward to change password page");
	PageInfo.login(request, response, PageType.LOGIN);
    }

    /**
     * Check the user's login and block the login attempt for 15 minutes if user
     * fail 3 times. If the request has the resetPwd parameter, this method will
     * send a random new password to user by email.
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	String action = request.getParameter("action");
	String from = request.getParameter("from");
	String message = null;
	String error = null;
	UserModel loginUser = new UserModel();
	UserModel validUser = null;
	/* Check the user's login attempt */
	if (action != null && action.equals("dologin")) {
	    logger.debug("Try login");
	    try {
		logger.debug("Mapping user's attribute ");
		BeanUtils.populate(loginUser, request.getParameterMap());
		logger.debug("{}", loginUser);
		logger.debug("Check login user");
		validUser = userService.LoginAttempt(loginUser);
		logger.debug("{}", validUser);
	    } catch (IllegalAccessException | InvocationTargetException e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    } catch (SQLException e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    } catch (Exception e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	    if (validUser != null) {
		SessionUtil.add(request, "loginUser", validUser);
		if (from == null || from.trim().equals("")) {
		    logger.info("Login from direct page");
		    if (validUser.isRole()) {
			logger.info("User's role: " + validUser.isRole());
			response.sendRedirect(request.getContextPath() + "/admin/posts");
		    } else {
			logger.info("User's role: " + validUser.isRole());
			response.sendRedirect(request.getContextPath());
		    }
		} else {
		    logger.info("Navigate from " + from);
		    response.sendRedirect(from);
		}
	    } else {
		logger.error("Login failed! iInvalid username or password");
		request.setAttribute("error", error);
		request.setAttribute("from", from);
		PageInfo.login(request, response, PageType.LOGIN);
	    }
	/* Generate a random password and send to user by mail */
	} else if (action != null && action.equals("resetPwd")) {
	    String email = request.getParameter("userMail");
	    logger.debug("Try reset password with email: " + email);
	    UserModel user = null;
	    try {
		/* check user's email if it exists */
		user = userService.findBy(email);
		logger.debug("{}", user);
	    } catch (Exception e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	    if (user != null) {
		/* Generate new random password and send email */
		logger.debug("Generate new random password");
		user.setPassword(PasswordUtils.generate(10));
		try {
		    logger.debug("Update user's infomation with new password");
		    userService.edit(user);
		    logger.debug("{}", user);
		    Mailer.getTemplate(user.getPassword());
		    Mailer.send(user.getEmail(), "Reset Password", Mailer.getTemplate(user.getPassword()));
		    logger.debug("Password was send to user by mail");
		    message = "New password send";
		} catch (Exception e) {
		    error = e.getMessage();
		    logger.error(e.getMessage(), e);
		    SlackApiUtil.pushLog(request, e.getMessage());
		}
	    } else {
		error = "Email is not exist!";
		logger.debug("User's mail is not exist");
	    }
	    request.setAttribute("message", message);
	    request.setAttribute("error", error);
	    request.setAttribute("from", from);
	    ;
	    logger.info("Forward to login page");
	    PageInfo.login(request, response, PageType.LOGIN);
	}
    }
}
