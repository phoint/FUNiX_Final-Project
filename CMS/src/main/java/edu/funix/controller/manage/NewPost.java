/*
 * @(#) NewPost.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.manage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.common.ICategoryService;
import edu.funix.common.IPostGroupService;
import edu.funix.common.IPostService;
import edu.funix.common.imp.CategoryService;
import edu.funix.common.imp.PostGroupService;
import edu.funix.common.imp.PostService;
import edu.funix.model.PostModel;
import edu.funix.utils.PageInfo;
import edu.funix.utils.PageType;
import edu.funix.utils.SessionUtil;
import edu.funix.utils.SlackApiUtil;

/**
 * Servlet implementation class NewPost
 */
@MultipartConfig
@WebServlet("/admin/new-post")
public class NewPost extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NewPost.class);
    private IPostService postService;
    private ICategoryService categoryService;
    private IPostGroupService postGroupService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewPost() {
	postService = new PostService();
	categoryService = new CategoryService();
	postGroupService = new PostGroupService();
    }

    /**
     * Set the default informations for displaying new post page
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	PostModel post = new PostModel();

	/* Sets the blank category to new post page */
	try {
	    logger.debug("Get all category for displaying in new post's view");
	    post.setCategories(categoryService.findAll());
	} catch (SQLException e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}

	/* Sets publish date to current date for default */
	logger.debug("Default published date for new post");
	post.setPublishDate(new Date(Calendar.getInstance().getTimeInMillis()));
	logger.debug("{}", post.getPublishDate());
	/* Sets and forwards default post's attribute to new post page */
	request.setAttribute("p", post);
	logger.info("Forward default information to new post page");
	PageInfo.PrepareAndForward(request, response, PageType.NEW_POST);
    }

    /**
     * Save new post
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	response.setContentType("text/html;charset=UTF-8");
	request.setCharacterEncoding("UTF-8");

	PostModel post = new PostModel();

	/* message and error showing the result of process */
	String message = null;
	String error = null;

	/* Mapping parameter's value to post's model */
	try {
	    logger.debug("Mapping new post's attributes to post model");
	    BeanUtils.populate(post, request.getParameterMap());
	    logger.debug("{}", post);
	} catch (IllegalAccessException | InvocationTargetException e1) {
	    logger.error(e1.getMessage(), e1);
	    SlackApiUtil.pushLog(request, e1.getMessage());
	}

	try {
	    /* Sets the blank category to new post page in case process fail */
	    logger.debug("Get all category for displaying in new post's view, in case insert fail");
	    post.setCategories(categoryService.findAll());

	    /* Inserts new post's row in database's post table */
	    logger.debug("Save new post");
	    Long id = postService.save(post);

	    /*
	     * Check the add new process. If success, updates the post's category and gets
	     * back
	     */
	    if (id != null) {
		logger.debug("Save success with id = " + id);
		logger.debug("Update categories is checked");
		String[] catIds = request.getParameterValues("new-categories");
		postGroupService.updateCategory(id, catIds);
		post = postService.findPostById(id);
		logger.debug("{}", post.getCategories());
		message = "New post is saved";
	    }
	} catch (SQLException e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}

	/*
	 * Sets and forward post's attribute with process's message depend on process's
	 * result
	 */
	request.setAttribute("p", post);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	if (message == null && error != null) {
	    logger.info("Stay in page if failed");
	    PageInfo.PrepareAndForward(request, response, PageType.NEW_POST);
	} else {
	    SessionUtil.add(request, "addNewSuccess", message);
	    logger.info("Redirect to post management page if success");
	    response.sendRedirect(request.getContextPath() + "/admin/posts");
	}
    }
}
