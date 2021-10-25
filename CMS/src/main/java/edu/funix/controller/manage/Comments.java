package edu.funix.controller.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.common.ICommentService;
import edu.funix.common.imp.CommentService;
import edu.funix.model.CommentModel;

/**
 * Servlet implementation class Comments
 */
@WebServlet("/admin/comments")
public class Comments extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ICommentService commentService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
	commentService = new CommentService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	CommentModel comments = new CommentModel();
	String action = request.getParameter("action");
	String[] ids = request.getParameterValues("id");
	String confirmTerm = request.getParameter("confirm");
	try {
	    if (action != null && ids != null) {
		if (action.equals("delete")) {
		    commentService.delete(ids);
		}
		if (action.equals("confirm") && confirmTerm != null) {
		    commentService.confirm(ids, Boolean.parseBoolean(confirmTerm));
		}
	    }
	} catch (Exception e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	try {
	    comments.setListResult(commentService.findAll());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	request.setAttribute("comments", comments);
	PageInfo.PrepareAndForward(request, response, PageType.COMMENT_MANAGEMENT_PAGE);
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
