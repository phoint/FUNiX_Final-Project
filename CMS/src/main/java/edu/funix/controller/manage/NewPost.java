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
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.common.ICategoryService;
import edu.funix.common.IMediaService;
import edu.funix.common.IPostGroupService;
import edu.funix.common.IPostService;
import edu.funix.common.imp.CategoryService;
import edu.funix.common.imp.MediaService;
import edu.funix.common.imp.PostGroupService;
import edu.funix.common.imp.PostService;
import edu.funix.model.PostModel;

/**
 * Servlet implementation class NewPost
 */
@MultipartConfig
@WebServlet("/admin/new-post")
public class NewPost extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IPostService postService;
    private ICategoryService categoryService;
    private IPostGroupService postGroupService;
    private IMediaService mediaService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewPost() {
	postService = new PostService();
	categoryService = new CategoryService();
	postGroupService = new PostGroupService();
	mediaService = new MediaService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	PostModel post = new PostModel();

	/* Sets the blank category to new post page */
	try {
	    post.setCategories(categoryService.findAll());
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	/* Sets publish date to current date for default */
	post.setPublishDate(new Date(Calendar.getInstance().getTimeInMillis()));

	/* Sets and forwards default post's attribute to new post page */
	request.setAttribute("p", post);
	PageInfo.PrepareAndForward(request, response, PageType.NEW_POST);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	request.setCharacterEncoding("UTF-8");

	PostModel post = new PostModel();

	/* message and error showing the result of process */
	String message = null;
	String error = null;

	/* Gets image's attribute upload */
	Part imageUpload = request.getPart("feature-image");

	/* Mapping parameter's value to post's model */
	try {
	    BeanUtils.populate(post, request.getParameterMap());
	} catch (IllegalAccessException | InvocationTargetException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	try {
	    /* Sets the blank category to new post page in case process fail */
	    post.setCategories(categoryService.findAll());

	    /* Checks image's attribute. If presented, sets the upload's info */
	    if (imageUpload != null) {
		post.setImage(mediaService.getUploadPath(request, imageUpload));
	    }

	    /* Inserts new post's row in database's post table */
	    Long id = postService.save(post);

	    /*
	     * Check the add new process. If success, updates the post's category and gets
	     * back
	     */
	    if (id != null) {
		String[] catIds = request.getParameterValues("new-categories");
		postGroupService.updateCategory(id, catIds);
		post = postService.findPostById(id);
		message = "Success";
	    }
	} catch (SQLException e) {
	    error = e.getMessage() + e.getErrorCode() + e.getSQLState();
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	/*
	 * Sets and forward post's attribute with process's message depend on process's
	 * result
	 */
	request.setAttribute("p", post);
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	if (message == null && error != null) {
	    PageInfo.PrepareAndForward(request, response, PageType.NEW_POST);
	} else {
	    response.sendRedirect(request.getContextPath() + "/admin/posts");
	}
    }
}
