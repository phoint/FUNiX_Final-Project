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

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.SessionUtil;
import edu.funix.common.IUserService;
import edu.funix.common.imp.UserService;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageInfo.Login(request, response, PageType.LOGIN);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		UserModel loginUser = new UserModel();
		UserModel validUser = null;
			if (action != null && action.equals("dologin")) {
				try {
				BeanUtils.populate(loginUser, request.getParameterMap());
				validUser = userService.checkLogin(loginUser.getUsername(), loginUser.getPassword());
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (validUser.getLoginMessage() == null) {
					SessionUtil.add(request, "loginUser", validUser);
					if (validUser.isRole()) {
						response.sendRedirect(request.getContextPath() + "/admin/posts");
					} else {
						//TODO Page for login has role user
						System.out.println("Home Page");
					}
				} else {
					request.setAttribute("loginUser", validUser);
					PageInfo.Login(request, response, PageType.LOGIN);
				}
			}

	}

}
