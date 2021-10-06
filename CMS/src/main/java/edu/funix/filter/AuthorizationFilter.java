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

import edu.funix.Utils.SessionUtil;
import edu.funix.model.UserModel;

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
		if (url.startsWith("/CMS/admin")) {
			UserModel userModel = (UserModel) SessionUtil.get(request, "loginUser");
			if (userModel != null) {
				if (userModel.isRole()) {
					chain.doFilter(request, response);
				} else if (!userModel.isRole()) {
					// TODO alerting access permission page for admin
					response.sendRedirect(request.getContextPath() + "/Login");
				}
			} else {
				// TODO alerting access permission page for login user
				response.sendRedirect(request.getContextPath() + "/Login");
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
