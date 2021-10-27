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

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.common.IUserService;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/admin/new-user")
public class NewUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
	PageInfo.PrepareAndForward(request, response, PageType.NEW_USER);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	UserModel user = new UserModel();
	String message = null;
	String error = null;
	try {
	    BeanUtils.populate(user, request.getParameterMap());
	    try {
		Long id = userService.save(user);
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
		e.printStackTrace();
	    }
	} catch (IllegalAccessException | InvocationTargetException e) {
	    error = e.getMessage();
	    e.printStackTrace();
	}

	request.setAttribute("message", message);
	request.setAttribute("error", error);
	if (error != null) {
	    PageInfo.PrepareAndForward(request, response, PageType.NEW_USER);
	} else {
	    response.sendRedirect(request.getContextPath() + "/admin/users");
	}
    }

}
