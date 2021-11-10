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
import java.security.GeneralSecurityException;

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
import edu.funix.model.SubcriberModel;
import edu.funix.utils.GoogleUtil;
import edu.funix.utils.Mailer;
import edu.funix.utils.PageInfo;
import edu.funix.utils.PageType;
import edu.funix.utils.PasswordUtils;
import edu.funix.utils.SessionUtil;
import edu.funix.utils.SlackApiUtil;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Login.class);
    private IAccountService<SubcriberModel> subcriberService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
	subcriberService = new SubcriberService();
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
	logger.debug("Forward to login page");
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
	String credential = request.getParameter("credential");
	String message = null;
	String error = null;
	SubcriberModel loginUser = new SubcriberModel();
	SubcriberModel validUser = null;
	/* Check the user's login attempt */
	if (credential != null && !credential.isEmpty()) {
	    try {
		logger.info(credential);
		logger.debug("Mapping user's attribute from Google ID Token ");
		BeanUtils.copyProperties(loginUser, GoogleUtil.decode(credential));
		logger.debug("{}", GoogleUtil.decode(credential).toString());
		logger.debug("{}", loginUser);
		try {
		    validUser = subcriberService.socialLogin((SubcriberModel) loginUser);
		} catch (Exception e) {
		    error = e.getMessage();
		    logger.error(e.getMessage(), e);
		    SlackApiUtil.pushLog(request, e.getMessage());
		}
	    } catch (IllegalAccessException | InvocationTargetException | GeneralSecurityException | IOException e) {
		error = e.getMessage();
		logger.error(e.getCause().toString(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	} else if (action != null && action.equals("dologin")) {
	    logger.debug("Try login");
	    try {
		logger.debug("Mapping user's attribute ");
		BeanUtils.populate(loginUser, request.getParameterMap());
		logger.debug("{}", loginUser);
		try {
		    logger.debug("Check login user");
		    validUser = subcriberService.LoginAttempt((SubcriberModel) loginUser);
		    logger.debug("{}", validUser);
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
	    /* Generate a random password and send to user by mail */
	} else if (action != null && action.equals("resetPwd")) {
	    String email = request.getParameter("userMail");
	    logger.debug("Try reset password with email: " + email);
	    SubcriberModel subcriber = null;
	    try {
		/* check user's email if it exists */
		subcriber = subcriberService.findBy(email);
		logger.debug("{}", subcriber);
	    } catch (Exception e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	    if (subcriber != null) {
		/* Generate new random password and send email */
		logger.debug("Generate new random password");
		subcriber.setPassword(PasswordUtils.generate(10));
		try {
		    logger.debug("Update user's infomation with new password");
		    subcriberService.edit(subcriber);
		    logger.debug("{}", subcriber);
		    Mailer.send(subcriber.getEmail(), "Reset Password", Mailer.getTemplate(subcriber.getPassword()));
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
	    logger.info("Forward to login page");
	    PageInfo.login(request, response, PageType.LOGIN);
	}
	if (validUser != null) {
	    SessionUtil.add(request, "loginUser", validUser);
	    SessionUtil.add(request, "userType", "Subcriber");
	    if (from == null || from.trim().equals("")) {
		logger.info("Login from direct page");
		response.sendRedirect(request.getContextPath());
	    } else {
		logger.info("Navigate from " + from);
		response.sendRedirect(from);
	    }
	} else {
	    logger.error("Login failed! invalid username or password");
	    request.setAttribute("error", error);
	    request.setAttribute("from", from);
	    PageInfo.login(request, response, PageType.LOGIN);
	}
    }
}
