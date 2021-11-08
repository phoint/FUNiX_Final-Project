package edu.funix.controller.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;

/**
 * Servlet implementation class AccessDenied
 */
@WebServlet("/access-denied")
public class AccessDenied extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccessDenied() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String action = request.getParameter("login-already");
	if (action != null && action.equals("true")) {	    
	    PageInfo.WebPrepareAndForward(request, response, PageType.LOGIN_ALREADY);
	} else {	    
	    PageInfo.WebPrepareAndForward(request, response, PageType.ACCESS_DENIED);
	}
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
