package edu.funix.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.funix.model.AccountModel;
import edu.funix.model.UserModel;
import edu.funix.utils.SessionUtil;

public class AuthorizationFilter implements Filter {

    private ServletContext context;

    @Override
    public void destroy() {
	// TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
	    throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) servletRequest;
	HttpServletResponse response = (HttpServletResponse) servletResponse;
	String url = request.getRequestURI();
	String queryString = request.getQueryString();
	if (url.startsWith("/CMS/admin/")) {
	    if (SessionUtil.isLogin(request)) {
		String userType = (String) SessionUtil.get(request, "userType");
		if (userType != null && userType.equals("User")) {
		    UserModel userModel = (UserModel) SessionUtil.get(request, "loginUser");
		    if (userModel != null) {
			if (userModel.isRole()) {
			    chain.doFilter(request, response);
			} else if (!userModel.isRole()) {
			    // TODO alerting access permission page for admin
			    response.sendRedirect(request.getContextPath() + "/access-denied");
			}
		    }
		} else {
		    // TODO alerting access permission page for login user
		    response.sendRedirect(request.getContextPath() + "/access-denied");
		}
	    } else {
		response.sendRedirect(request.getContextPath() + "/access-denied");
	    }

	} else if (url.startsWith("/CMS/account")) {
	    if (SessionUtil.isLogin(request)) {
		chain.doFilter(request, response);
	    } else {
		response.sendRedirect(request.getContextPath() + "/access-denied");
	    }
	} else if ((url.endsWith("/CMS/login") || url.endsWith("/CMS/admin-login")) && queryString == null) {
	    if (SessionUtil.isLogin(request)) {
		response.sendRedirect(request.getContextPath() + "/access-denied?login-already=true");
	    } else {		
		chain.doFilter(request, response);
	    }
	} else {
	    chain.doFilter(request, response);
	}
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
	this.context = filterConfig.getServletContext();
    }

}
