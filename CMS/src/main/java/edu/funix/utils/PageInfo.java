package edu.funix.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageInfo {
    public static Map<PageType, PageInfo> pageRoute = new HashMap<PageType, PageInfo>();

    static {
	pageRoute.put(PageType.OPTION, new PageInfo("Option", "option.jsp", null));
	pageRoute.put(PageType.ACCESS_DENIED, new PageInfo("Access Denied", "accessDenied.jsp", null));
	pageRoute.put(PageType.LOGIN_ALREADY, new PageInfo("Login Already", "loginAlready.jsp", null));
	pageRoute.put(PageType.USER_MANAGEMENT_PAGE, new PageInfo("User Management", "users.jsp", null));
	pageRoute.put(PageType.NEW_USER, new PageInfo("New User", "newuser.jsp", null));
	pageRoute.put(PageType.EDIT_USER, new PageInfo("Edit User", "edituser.jsp", null));
	pageRoute.put(PageType.SUBCRIBER_MANAGEMENT_PAGE,
		new PageInfo("Subcribers Management", "subcribers.jsp", null));
	pageRoute.put(PageType.CATEGORY_MANAGEMENT_PAGE, new PageInfo("Categories Management", "category.jsp", null));
	pageRoute.put(PageType.EDIT_CATEGORY, new PageInfo("Edit Category", "edit-category.jsp", null));
	pageRoute.put(PageType.POST_MANAGEMENT_PAGE, new PageInfo("Post Management", "posts.jsp", null));
	pageRoute.put(PageType.NEW_POST, new PageInfo("New Post", "newPost.jsp", null));
	pageRoute.put(PageType.EDIT_POST, new PageInfo("Edit Post", "editPost.jsp", null));
	pageRoute.put(PageType.COMMENT_MANAGEMENT_PAGE, new PageInfo("Comments", "comment.jsp", null));
	pageRoute.put(PageType.LOGIN, new PageInfo("Login", "login.jsp", null));
	pageRoute.put(PageType.REGISTER, new PageInfo("Register", "register.jsp", null));
	pageRoute.put(PageType.PASSWORD_CHANGE, new PageInfo("Password Change", "changePwd.jsp", null));
	pageRoute.put(PageType.FORGOT_PASSWORD, new PageInfo("Reset password", "forgotPassword.jsp", null));
	pageRoute.put(PageType.HOMEPAGE, new PageInfo("Home", "home.jsp", null));
	pageRoute.put(PageType.POST_DETAIL, new PageInfo("Post", "post.jsp", null));
    }

    public static void PrepareAndForward(HttpServletRequest request, HttpServletResponse response, PageType pageType)
	    throws ServletException, IOException {
	PageInfo page = pageRoute.get(pageType);
	request.setAttribute("pageInfo", page);
	request.getRequestDispatcher("layout.jsp").forward(request, response);
    }

    public static void WebPrepareAndForward(HttpServletRequest request, HttpServletResponse response, PageType pageType)
	    throws ServletException, IOException {
	PageInfo page = pageRoute.get(pageType);
	request.setAttribute("pageInfo", page);
	request.getRequestDispatcher("web/layout.jsp").forward(request, response);
    }

    public static void WebPrepareAndForward(HttpServletRequest request, HttpServletResponse response, PageType pageType,
	    String title) throws ServletException, IOException {
	PageInfo page = pageRoute.get(pageType);
	page.setTitle(title);
	request.setAttribute("pageInfo", page);
	request.getRequestDispatcher("web/layout.jsp").forward(request, response);
    }

    public static void account(HttpServletRequest request, HttpServletResponse response, PageType pageType)
	    throws ServletException, IOException {
	PageInfo page = pageRoute.get(pageType);
	request.setAttribute("pageInfo", page);
	request.getRequestDispatcher("entry.jsp").forward(request, response);
    }

    public static void login(HttpServletRequest request, HttpServletResponse response, PageType pageType)
	    throws ServletException, IOException {
	PageInfo page = pageRoute.get(pageType);
	request.setAttribute("pageInfo", page);
	request.getRequestDispatcher("web/entry.jsp").forward(request, response);
    }

    public static void adminLogin(HttpServletRequest request, HttpServletResponse response, PageType pageType)
	    throws ServletException, IOException {
	PageInfo page = pageRoute.get(pageType);
	request.setAttribute("pageInfo", page);
	request.getRequestDispatcher("admin/entry.jsp").forward(request, response);
    }

    private String title;
    private String contentUrl;
    private String scriptUrl;

    public PageInfo(String title, String contentUrl, String scriptUrl) {
	super();
	this.title = title;
	this.contentUrl = contentUrl;
	this.scriptUrl = scriptUrl;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getContentUrl() {
	return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
	this.contentUrl = contentUrl;
    }

    public String getScriptUrl() {
	return scriptUrl;
    }

    public void setScriptUrl(String scriptUrl) {
	this.scriptUrl = scriptUrl;
    }

}
