/*
 * @(#) EditUser.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.manage;

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
import edu.funix.Utils.SlackApiUtil;
import edu.funix.common.IAccountService;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class EditUser
 */
@WebServlet("/admin/edit-user")
public class EditUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EditUser.class);
    private IAccountService<UserModel> userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUser() {
	userService = new UserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	UserModel user = new UserModel();
	String id = request.getParameter("id");
	String message = null;
	String error = null;
	try {
	    if (id != null) {
		logger.debug("Find the user by id " + id);
		user = userService.findBy(Long.parseLong(id));
		logger.debug("{}", user);
	    }
	} catch (NumberFormatException e) {
	    error = "Did not find any user";
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	} catch (SQLException e) {
	    error = "Did not find any user";
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	} catch (Exception e) {
	    error = "Did not find any user";
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	request.setAttribute("user", user);
	logger.info("Forward to edit user");
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_USER);
    }

    /**
     * Update the edited information to database
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
	String action = request.getParameter("action");
	String message = null;
	String error = null;
	try {
	    logger.debug("Mapping user's attributes to user model");
	    BeanUtils.populate(user, request.getParameterMap());
	    logger.debug("{}",user);
	    if (action != null && action.equals("resetPwd")) {
		logger.debug("Reset password and send to email");
		user.setPassword(PasswordUtils.generate(10));
		try {
		    logger.debug("Update user with new password");
		    userService.edit(user);
		    logger.debug("Send password by email: " + user.getEmail());
		    Mailer.getTemplate(user.getPassword());
		    Mailer.send(user.getEmail(), "Reset Password", Mailer.getTemplate(user.getPassword()));
		    message = "New password send";
		    user = userService.findBy(user.getId());
		} catch (Exception e) {
		    error = e.getMessage();
		    logger.error(e.getMessage(), e);
		    SlackApiUtil.pushLog(request, e.getMessage());
		}
	    } else {
		try {
		    logger.debug("Update the user's attribute but not password");
		    userService.edit(user);
		    user = userService.findBy(user.getId());
		} catch (Exception e) {
		    error = e.getMessage();
		    logger.error(e.getMessage(), e);
		    SlackApiUtil.pushLog(request, e.getMessage());
		}
	    }
	} catch (IllegalAccessException | InvocationTargetException e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}

	request.setAttribute("user", user);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	logger.info("Stay in edit page and display message");
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_USER);
    }

}
