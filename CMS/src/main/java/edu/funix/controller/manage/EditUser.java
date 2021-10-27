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

import edu.funix.Utils.Mailer;
import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.PasswordUtils;
import edu.funix.common.IUserService;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class EditUser
 */
@WebServlet("/admin/edit-user")
public class EditUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;

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
	UserModel user = new UserModel();
	String id = request.getParameter("id");
	String message = null;
	try {
	    if (id != null) {
		user = userService.findUserById(Long.parseLong(id));
	    }
	} catch (NumberFormatException e) {
	    message = "Did not find any user";
	    e.printStackTrace();
	} catch (SQLException e) {
	    message = "Did not find any user";
	    e.printStackTrace();
	} catch (Exception e) {
	    message = "Did not find any user";
	    e.printStackTrace();
	}
	request.setAttribute("message", message);
	request.setAttribute("user", user);
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_USER);
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
	String action = request.getParameter("action");
	String message = null;
	String error = null;
	try {
	    BeanUtils.populate(user, request.getParameterMap());
	    if (action != null && action.equals("resetPwd")) {
		user.setPassword(PasswordUtils.generate(10));
		try {
		    userService.edit(user);
		    Mailer.getTemplate(user.getPassword());
		    Mailer.send(user.getEmail(), "Reset Password", Mailer.getTemplate(user.getPassword()));
		    message = "New password send";
		    user = userService.findUserById(user.getId());
		} catch (Exception e) {
		    error = e.getMessage();
		    e.printStackTrace();
		}
	    } else {
		try {
		    userService.edit(user);
		    user = userService.findUserById(user.getId());
		} catch (Exception e) {
		    error = e.getMessage();
		    e.printStackTrace();
		}
	    }
	} catch (IllegalAccessException | InvocationTargetException e) {
	    error = e.getMessage();
	    e.printStackTrace();
	}
	
	request.setAttribute("user", user);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_USER);
    }

}
