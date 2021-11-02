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

import edu.funix.Utils.Mailer;
import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.PasswordUtils;
import edu.funix.Utils.SlackApiUtil;
import edu.funix.common.IUserService;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Register.class);
    private IUserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
	userService = new UserService();
    }

    /**
     * Forward to the register page
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	logger.info("Forward to register page");
	PageInfo.login(request, response, PageType.REGISTER);
    }

    /**
     * Create a user's account with information from register form. The password
     * will be generated and send to user by mail. User log in with that password
     * and username created for first time. After logging in, the user could change
     * the password.
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	UserModel newUser = new UserModel();
	try {
	    logger.debug("Mapping user's attributes to user model");
	    BeanUtils.populate(newUser, request.getParameterMap());
	} catch (IllegalAccessException | InvocationTargetException e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	/* Auto generate a safe password has length = 10 */
	logger.debug("Generate random password");
	String password = PasswordUtils.generate(10);
	newUser.setPassword(password);
	try {
	    logger.debug("Insert new user");
	    userService.save(newUser);
	    logger.debug("{}", newUser);
	    /* Send the password to user by mail */
	    Mailer.send(newUser.getEmail(), "Hello New User", Mailer.getTemplate(password));
	    logger.debug("Send password to user by mail - Success");
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	logger.info("Forward to login page");
	PageInfo.login(request, response, PageType.LOGIN);
    }
}
