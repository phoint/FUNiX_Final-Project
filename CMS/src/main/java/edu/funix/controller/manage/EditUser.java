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
 * Servlet implementation class EditUser
 */
@WebServlet("/EditUser")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		UserModel user = new UserModel();
		String message = null;
		try {
			BeanUtils.populate(user, request.getParameterMap());
			userService.edit(user);
			request.setAttribute("user", userService.findUserById(user.getId()));
			message = "Success";
		} catch (IllegalAccessException | InvocationTargetException e) {
			message = "Fail";
			e.printStackTrace();
		} catch (SQLException e) {
			message = "Fail";
			e.printStackTrace();
		} catch (Exception e) {
			message = "Fail";
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		PageInfo.PrepareAndForward(request, response, PageType.EDIT_USER);
	}

}
