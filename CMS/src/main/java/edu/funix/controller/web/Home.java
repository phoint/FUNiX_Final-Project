package edu.funix.controller.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.Utils.SessionUtil;
import edu.funix.common.ICommentService;
import edu.funix.common.IPostService;
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
    IPostService postService;
    ICommentService commentService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
	postService = new PostService();
	commentService = new CommentService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	PostModel post = new PostModel();
	PageModel page = new PageModel();
	String postId = request.getParameter("p");
	String CatId = request.getParameter("category");
	try {
	    if (postId != null) {
		post = postService.findPostById(Long.parseLong(postId));
		request.setAttribute("post", post);
		PageInfo.WebPrepareAndForward(request, response, PageType.POST_DETAIL);
		return;
	    }
	    BeanUtils.populate(page, request.getParameterMap());
	    if (CatId != null && !CatId.trim().equals("")) {
		post.setListResult(postService.categoryGroup(Long.parseLong(CatId), page));
	    } else {
		post.setListResult(postService.findAll(page));
	    }
	} catch (IllegalAccessException | InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	request.setAttribute("page", page);
	request.setAttribute("posts", post);
	PageInfo.WebPrepareAndForward(request, response, PageType.HOMEPAGE);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	CommentModel comment = new CommentModel();
	PostModel post = new PostModel();
	String message = null;
	String error = null;
	
	String action = request.getParameter("action");
	if (action != null && action.equals("comment")) {
	    try {
		BeanUtils.populate(comment, request.getParameterMap());
	    } catch (IllegalAccessException | InvocationTargetException e) {
		error = e.getMessage();
		e.printStackTrace();
	    }
	    if (SessionUtil.isLogin(request)) {
		UserModel loginUser = (UserModel) SessionUtil.get(request, "loginUser");
		comment.setCreatedBy(loginUser.getId());
		try {
		    commentService.save(comment);
		} catch (Exception e) {
		    error = "Something wrong, try later!";
		    e.printStackTrace();
		}
	    }
	}
	try {
	    post = postService.findPostById(comment.getSubmitTo());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	request.setAttribute("post", post);
	PageInfo.WebPrepareAndForward(request, response, PageType.POST_DETAIL);
    }

}
