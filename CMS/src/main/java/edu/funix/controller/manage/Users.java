package edu.funix.controller.manage;

import java.io.IOException;
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
import edu.funix.model.PageModel;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class Users
 */
@WebServlet("/admin/users")
public class Users extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Users() {
	userService = new UserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String id = request.getParameter("id");
	String action = request.getParameter("action");
	String searchKey = request.getParameter("searchKey");
	UserModel users = new UserModel();
	PageModel page = new PageModel();
	String message = null;
	String error = null;

	/*
	 * Sets the search key to null if return empty string. This process help the
	 * service calling the right DAO's method
	 */
	if (searchKey != null && searchKey.trim().equals("")) {
	    searchKey = null;
	}
	try {
	    /* Check parameter for deleting feature */
	    if (id != null && action.equals("delete")) {
		userService.permanentDelete(Long.parseLong(id));
		message = "Delete success";
	    }
	    BeanUtils.populate(page, request.getParameterMap());
	    /* Finds the users base on search term */
	    if (searchKey != null) {
		users.setListResult(userService.search(page, searchKey));
	    } else {
		users.setListResult(userService.findAll(page));
	    }
	} catch (Exception e) {
	    error = e.getMessage();
	    e.printStackTrace();
	}
	request.setAttribute("page", page);
	request.setAttribute("users", users);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	PageInfo.PrepareAndForward(request, response, PageType.USER_MANAGEMENT_PAGE);
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
