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
     * Forward to the register page
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
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
	UserModel newUser = new UserModel();
	try {
	    BeanUtils.populate(newUser, request.getParameterMap());
	} catch (IllegalAccessException | InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	/* Auto generate a safe password has length = 10 */
	String password = PasswordUtils.generate(10);
	newUser.setPassword(password);
	try {
	    userService.save(newUser);
	    /* Send the password to user by mail */
	    Mailer.send(newUser.getEmail(), "Hello New User", Mailer.getTemplate(password));
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	PageInfo.login(request, response, PageType.LOGIN);
    }
}
