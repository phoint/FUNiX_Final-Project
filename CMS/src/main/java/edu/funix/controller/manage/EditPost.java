/*
 * @(#) EditPost.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.manage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.common.IPostGroupService;
import edu.funix.common.IPostService;
import edu.funix.common.imp.PostGroupService;
import edu.funix.common.imp.PostService;
import edu.funix.model.PostModel;
import edu.funix.utils.PageInfo;
import edu.funix.utils.PageType;
import edu.funix.utils.SlackApiUtil;

/**
 * Servlet implementation class EditPost
 */
@MultipartConfig
@WebServlet("/admin/edit-post")
public class EditPost extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EditPost.class);
    private IPostService postService;
    private IPostGroupService postGroupService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPost() {
	postService = new PostService();
	postGroupService = new PostGroupService();
    }

    /**
     * Receive the request with post's id parameter for showing informations to edit
     * form
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	/* Gets the post's id */
	String id = request.getParameter("id");
	PostModel post = null;

	try {
	    logger.debug("Get the post from id: " + id);
	    /* Gets the post's attributes */
	    if (id != null) {
		post = postService.findPostById(Long.parseLong(id));
		logger.debug("{}", post);
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getStackTrace().toString());
	}

	/* Sets and forward post's attribute to edit page */
	request.setAttribute("p", post);
	logger.info("Forward to edit post page");
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_POST);
    }

    /**
     * Receive the request with post's attribute editted for updating database. This
     * process give back message/error when success or error updating.
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	PostModel post = new PostModel();

	/* message and error showing the result of process */
	String message = null;
	String error = null;

	/* Gets the categories's changing */
	String[] catIds = request.getParameterValues("categories-edited");

	/* Mapping parameter's value to post's model */
	try {
	    BeanUtils.populate(post, request.getParameterMap());
	    logger.debug("{}", post);
	} catch (IllegalAccessException | InvocationTargetException e1) {
	    logger.error(e1.getMessage(), e1);
	    SlackApiUtil.pushLog(request, e1.toString());
	}

	try {
	    logger.debug("Update a post");
	    Long id = post.getId();

	    /* Updates the post's value in database */
	    postService.edit(post);

	    /* Updates the post's categories in database */
	    postGroupService.updateCategory(id, catIds);
	    /* Gets the post's attribute updated */
	    post = postService.findPostById(id);
	    logger.debug("{}", post);
	    message = "Success";
	} catch (SQLException sqlEx) {
	    error = "Update failed";
	    logger.error(sqlEx.getMessage(), sqlEx);
	    SlackApiUtil.pushLog(request, sqlEx.getMessage());
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}

	/* Sets and forward post's attribute with process's message to edit page */
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	request.setAttribute("p", post);
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_POST);
	logger.info("Forward to edit page " + request.getHeader("referer"));
    }
}
