package edu.funix.controller.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.SessionUtil;
import edu.funix.common.IUserService;
import edu.funix.common.imp.UserService;
import edu.funix.domain.ChangePasswordForm;

/**
 * Servlet implementation class PasswordChange
 */
@WebServlet("/account/password-change")
public class PasswordChange extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordChange() {
	userService = new UserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	PageInfo.account(request, response, PageType.PASSWORD_CHANGE);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	ChangePasswordForm newPwd = new ChangePasswordForm();
	String error = null;
	String message = null;
	try {
	    BeanUtils.populate(newPwd, request.getParameterMap());
	} catch (IllegalAccessException | InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	newPwd.setUsername(SessionUtil.getLoginedUsername(request));
	try {
	    userService.changePassword(newPwd);
	    SessionUtil.invalidate(request);
	    message = "Password changed!";
	} catch (Exception e) {
	    error = e.getMessage();
	}
	request.setAttribute("error", error);
	request.setAttribute("message", message);
	PageInfo.account(request, response, PageType.PASSWORD_CHANGE);
	
    }

}
