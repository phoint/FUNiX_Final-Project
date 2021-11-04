package edu.funix.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.funix.model.AbstractModel;
import edu.funix.model.AccountModel;
import edu.funix.model.UserModel;

public class SessionUtil {
	public static void add(HttpServletRequest request, String name, Object value) {
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}

	public static Object get(HttpServletRequest request, String name) {
		HttpSession session = request.getSession();
		return session.getAttribute(name);
	}

	public static void invalidate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	public static boolean isLogin(HttpServletRequest request) {
		return get(request, "loginUser") != null;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getLoginedUsername(HttpServletRequest request) {
		return get(request, "loginUser") == null ? null : ((AccountModel) get(request, "loginUser")).getUsername();
	}

}
