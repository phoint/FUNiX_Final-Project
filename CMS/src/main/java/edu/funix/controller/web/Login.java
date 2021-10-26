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

import edu.funix.Utils.Mailer;
import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.PasswordUtils;
import edu.funix.Utils.SessionUtil;
import edu.funix.common.IUserService;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
	userService = new UserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String action = request.getParameter("action");
	if (action != null && action.equals("logout")) {
	    SessionUtil.invalidate(request);
	    response.sendRedirect(request.getContextPath());
	    return;
	}
	if (action != null && action.equals("resetPwd")) {
	    PageInfo.login(request, response, PageType.FORGOT_PASSWORD);
	    return;
	}
	PageInfo.login(request, response, PageType.LOGIN);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String action = request.getParameter("action");
	String from = request.getParameter("from");
	String post = request.getParameter("p");
	String message = null;
	String error = null;
	UserModel loginUser = new UserModel();
	UserModel validUser = null;
	if (action != null && action.equals("dologin")) {
	    try {
		BeanUtils.populate(loginUser, request.getParameterMap());
		validUser = userService.LoginAttempt(loginUser);
	    } catch (IllegalAccessException | InvocationTargetException e) {
		error = e.getMessage();
		e.printStackTrace();
	    } catch (SQLException e) {
		error = e.getMessage();
		e.printStackTrace();
	    } catch (Exception e) {
		error = e.getMessage();
		e.printStackTrace();
	    }
	    if (validUser != null) {
		SessionUtil.add(request, "loginUser", validUser);
		if (from == null || from.trim().equals("")) {
		    if (validUser.isRole()) {
			response.sendRedirect(request.getContextPath() + "/admin/posts");
		    } else {
			response.sendRedirect(request.getContextPath());
		    }
		} else {
		    response.sendRedirect(request.getContextPath() + from);
		}
	    } else {
		request.setAttribute("error", error);
		PageInfo.login(request, response, PageType.LOGIN);
	    }
	} else if (action != null && action.equals("resetPwd")) {
	    String email = request.getParameter("userMail");
	    UserModel user = null;
	    try {
		user = userService.findBy(email);
	    } catch (Exception e) {
		error = e.getMessage();
		e.printStackTrace();
	    }
	    if (user != null) {
		user.setPassword(PasswordUtils.generate(10));
		try {
		    userService.edit(user);
		    Mailer.getTemplate(user.getPassword());
		    Mailer.send(user.getEmail(), "Reset Password", Mailer.getTemplate(user.getPassword()));
		    message = "New password send";
		} catch (Exception e) {
		    error = e.getMessage();
		    e.printStackTrace();
		}
	    } else {
		error = "Email is not exist!";
	    }
	    request.setAttribute("message", message);
	    request.setAttribute("error", error);
	    PageInfo.login(request, response, PageType.LOGIN);
	}

    }

}
