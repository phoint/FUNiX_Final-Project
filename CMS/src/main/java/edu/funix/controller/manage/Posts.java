/*
 * @(#) Posts.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.manage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.common.ICategoryService;
import edu.funix.common.IPostService;
import edu.funix.common.imp.CategoryService;
import edu.funix.common.imp.PostService;
import edu.funix.model.PageModel;
import edu.funix.model.PostModel;
import edu.funix.utils.PageInfo;
import edu.funix.utils.PageType;
import edu.funix.utils.SlackApiUtil;

/**
 * Servlet implementation class Posts
 */
@WebServlet("/admin/posts")
public class Posts extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Posts.class);
    private IPostService postService;
    private ICategoryService categoryService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Posts() {
	postService = new PostService();
	categoryService = new CategoryService();
    }

    /**
     * Display posts base on filter specified by client or/and pagination. Also,
     * this method take action on delete items when getting suitable parameter
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	/* Gets all id selected from view's form check box */
	String[] ids = request.getParameterValues("id");
	String action = request.getParameter("action");
	String category = request.getParameter("category");
	/* Gets the title search key from form input */
	String searchKey = request.getParameter("searchKey");
	PostModel post = new PostModel();
	PageModel page = new PageModel();
	/* message and error showing the result of process */
	String message = null;
	String error = null;

	/*
	 * Sets the search key to null if return empty string. This process help the
	 * service calling the right DAO's method
	 */
	if (searchKey != null && searchKey.trim().equals("")) {
	    logger.debug("convert empty searchKey to null - searchKey: " + searchKey);
	    searchKey = null;
	}
	try {
	    /* Check parameter for deleting feature */
	    if (ids != null && action.equals("delete")) {
		logger.debug("delete post(s): " + ids);
		postService.delete(ids);
		message = "delete success!";
	    }
	    /* Check parameter for deleting all items */
	    if (action != null && action.equals("deleteAll")) {
		logger.debug("delete all posts");
		postService.deleteAll();
		message = "All items deleted!";
	    }
	} catch (Exception e) {
	    /* something go wrong */
	    error = "Delete fail";
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	try {
	    logger.debug("Mapping pagination's attributes to page model");
	    BeanUtils.populate(page, request.getParameterMap());
	    logger.debug("{}", page);
	} catch (IllegalAccessException | InvocationTargetException e) {
	    error = "Something wrong, try later!";
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}

	try {
	    /* check the category' id for displaying posts in group */
	    if (category != null && !category.trim().equals("")) {
		logger.debug("Display posts by category id = " + category + " on page " + page.getCurrentPage());
		post.setListResult(postService.categoryGroup(Long.parseLong(category), page));
		/* Display posts base on search term and pagination */
	    } else if (searchKey != null) {
		logger.debug("Display posts by searchKey = " + searchKey + " on page " + page.getCurrentPage());
		post.setListResult(postService.search(page, searchKey));
		/* Display all post base on pagination */
	    } else {
		logger.debug("Display posts on page " + page.getCurrentPage());
		post.setListResult(postService.findAll(page));
	    }
	} catch (Exception e) {
	    error = "Something wrong, try later!";
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	try {
	    logger.debug("Set the categories for dropdown filter");
	    post.setCategories(categoryService.findAll());
	    logger.debug("{}", post.getCategories());
	} catch (Exception e) {
	    error = "Something wrong, try later!";
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	/* Forward to Post view */
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	request.setAttribute("category", category);
	request.setAttribute("searchKey", searchKey);
	request.setAttribute("posts", post);
	request.setAttribute("page", page);
	logger.info("Forward to post management page");
	PageInfo.PrepareAndForward(request, response, PageType.POST_MANAGEMENT_PAGE);
    }

    /**
     * Do everything the same doGet method
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	/* Do everything the same doGet method */
	doGet(request, response);
    }

}
