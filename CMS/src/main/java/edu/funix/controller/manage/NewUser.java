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

import edu.funix.common.IUserService;
import edu.funix.common.PageInfo;
import edu.funix.common.PageType;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageInfo.PrepareAndForward(request, response, PageType.NEW_USER);
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
			Long id = userService.save(user);
			message = "Success";
			request.setAttribute("users", userService.findAll());
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
		PageInfo.PrepareAndForward(request, response, PageType.USER_MANAGEMENT_PAGE);
	}

}
