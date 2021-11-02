/*
 * @(#) Home.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.SessionUtil;
import edu.funix.Utils.SlackApiUtil;
import edu.funix.common.ICategoryService;
import edu.funix.common.ICommentService;
import edu.funix.common.IPostService;
import edu.funix.common.imp.CategoryService;
import edu.funix.common.imp.CommentService;
import edu.funix.common.imp.PostService;
import edu.funix.model.CommentModel;
import edu.funix.model.PageModel;
import edu.funix.model.PostModel;
import edu.funix.model.UserModel;

/**
 * Servlet implementation class Home
 */
@WebServlet("/index.html")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Home.class);
    IPostService postService;
    ICommentService commentService;
    ICategoryService categoryService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
	postService = new PostService();
	commentService = new CommentService();
	categoryService = new CategoryService();
    }

    /**
     * Display posts base on filter specified by client or/and pagination. Display
     * post detail specify by corresponding parameter.
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	PostModel post = new PostModel();
	PageModel page = new PageModel();
	String postId = request.getParameter("p");
	String catId = request.getParameter("category");
	try {
	    /* Display post detail by id */
	    if (postId != null) {
		logger.debug("Get post by id: " + postId);
		post = postService.findPostById(Long.parseLong(postId));
		logger.debug("{}", post);
		request.setAttribute("post", post);
		logger.info("Forward post model to post detail page");
		PageInfo.WebPrepareAndForward(request, response, PageType.POST_DETAIL, post.getTitle());
		return;
	    }
	    logger.debug("Mapping pagination's attributes to page model");
	    BeanUtils.populate(page, request.getParameterMap());
	    logger.debug("{}", page);
	    logger.debug("Get all categories existed");
	    post.setCategories(categoryService.findAll());
	    logger.debug("{}", post.getCategories());
	    /* Display the posts filtered by a category */
	    if (catId != null && !catId.trim().equals("")) {
		logger.debug("Get the posts has category id " + catId + " on page " + page.getCurrentPage());
		post.setListResult(postService.categoryGroup(Long.parseLong(catId), page));
		logger.debug("{}", post.getListResult());
	    } else {
		logger.debug("Get the posts on page " + page.getCurrentPage());
		post.setListResult(postService.findAll(page));
		logger.debug("{}", post);
	    }
	} catch (IllegalAccessException | InvocationTargetException e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	} catch (SQLException e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("page", page);
	request.setAttribute("posts", post);
	logger.info("Forward to homepage");
	PageInfo.WebPrepareAndForward(request, response, PageType.HOMEPAGE);
    }

    /**
     * Submit a comment to a post. User need to login before submit.
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http post method, referer is: " + request.getHeader("referer"));
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	CommentModel comment = new CommentModel();
	PostModel post = new PostModel();
	String message = null;
	String error = null;

	String action = request.getParameter("action");
	if (action != null && action.equals("comment")) {
	    logger.debug("Try to submit a comment to a post");
	    try {
		logger.debug("Mapping comment's attributes to comment model");
		BeanUtils.populate(comment, request.getParameterMap());
		logger.debug("{}", comment);
	    } catch (IllegalAccessException | InvocationTargetException e) {
		error = e.getMessage();
		logger.error(e.getMessage(), e);
		SlackApiUtil.pushLog(request, e.getMessage());
	    }
	    /* Save the comment */
	    if (SessionUtil.isLogin(request)) {
		logger.debug("Get author for comment");
		UserModel loginUser = (UserModel) SessionUtil.get(request, "loginUser");
		comment.setCreatedBy(loginUser.getId());
		logger.debug("{}", comment);
		try {
		    logger.debug("Save the comment");
		    commentService.save(comment);
		    message = "The comment is submited";
		    logger.debug("Success");
		} catch (Exception e) {
		    error = "Something wrong, try later!";
		    logger.error(e.getMessage(), e);
		    SlackApiUtil.pushLog(request, e.getMessage());
		}
		/* Ask for login before submit */
	    } else {
		logger.debug("Not a login user. Please login before comment.");
		SessionUtil.add(request, "comContent", comment.getComContent());
		logger.debug("The previous path: " + request.getServletPath() + request.getQueryString());
		response.sendRedirect(request.getContextPath()
			+ "/login?error=You need to login before leave a comment&from=" + request.getHeader("referer"));
		return;
	    }
	}

	logger.info("Turn back to post detail [id = " + comment.getSubmitTo() + "]");
	response.sendRedirect(request.getContextPath() + "?p=" + comment.getSubmitTo());
    }
}
