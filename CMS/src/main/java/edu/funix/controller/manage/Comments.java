/*
 * @(#) Comments.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.controller.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.SlackApiUtil;
import edu.funix.common.ICommentService;
import edu.funix.common.imp.CommentService;
import edu.funix.model.CommentModel;

/**
 * Servlet implementation class Comments
 */
@WebServlet("/admin/comments")
public class Comments extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Comments.class);
    private ICommentService commentService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
	commentService = new CommentService();
    }

    /**
     * Manage the comments with delete and confirm feature
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	logger.info("called http get method, referer is: " + request.getHeader("referer"));
	CommentModel comments = new CommentModel();
	String action = request.getParameter("action");
	String[] ids = request.getParameterValues("id");
	String confirmTerm = request.getParameter("confirm");
	String message = null;
	String error = null;
	try {
	    if (action != null && ids != null) {
		if (action.equals("delete")) {
		    logger.debug("delete comment(s) by ids = " + ids);
		    commentService.delete(ids);
		    message = "Delete success";
		}
		if (action.equals("confirm") && confirmTerm != null) {
		    logger.debug("approve comment(s) by ids = " + ids);
		    commentService.confirm(ids, Boolean.parseBoolean(confirmTerm));
		    message = "Comment is confirmed";
		}
	    }
	} catch (Exception e1) {
	    error = e1.getMessage();
	    logger.error(e1.getMessage(), e1);
	    SlackApiUtil.pushLog(request, e1.getMessage());
	}
	try {
	    logger.debug("Display comments on page ");
	    // TODO create a pagination feature here
	    comments.setListResult(commentService.findAll());
	    logger.debug("{}", comments.getListResult());
	} catch (Exception e) {
	    error = e.getMessage();
	    logger.error(e.getMessage(), e);
	    SlackApiUtil.pushLog(request, e.getMessage());
	}
	request.setAttribute("comments", comments);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	logger.info("Forward to comment management page");
	PageInfo.PrepareAndForward(request, response, PageType.COMMENT_MANAGEMENT_PAGE);
    }

    /**
     * The same with doGet
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	/* Do the same with doGet method */
	doGet(request, response);
    }

}
