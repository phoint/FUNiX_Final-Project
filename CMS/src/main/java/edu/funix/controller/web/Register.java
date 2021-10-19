package edu.funix.controller.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
import edu.funix.controller.manage.NewUser;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
	userService = new UserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String action = request.getParameter("action");
	UserModel newUser = new UserModel();
	if (action != null && action.equals("register")) {
	    try {
		BeanUtils.populate(newUser, request.getParameterMap());
	    } catch (IllegalAccessException | InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    String password = PasswordUtils.generate(10);
	    newUser.setPassword(password);
	    try {
		userService.save(newUser);
		Mailer.getTemplate(password);
		Mailer.send(newUser.getEmail(), "Hello New User", Mailer.getTemplate(password));
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    PageInfo.Login(request, response, PageType.LOGIN);
	    return;
	}
	PageInfo.Login(request, response, PageType.REGISTER);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
    }

}
