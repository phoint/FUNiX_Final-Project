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
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.common.IMediaService;
import edu.funix.common.IPostGroupService;
import edu.funix.common.IPostService;
import edu.funix.common.imp.MediaService;
import edu.funix.common.imp.PostGroupService;
import edu.funix.common.imp.PostService;
import edu.funix.model.PostModel;

/**
 * Servlet implementation class Edit
 */
@MultipartConfig
@WebServlet("/admin/edit-post")
public class EditPost extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IPostService postService;
    private IPostGroupService postGroupService;
    private IMediaService mediaService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPost() {
	postService = new PostService();
	postGroupService = new PostGroupService();
	mediaService = new MediaService();
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

	/* Gets the post's id */
	String id = request.getParameter("id");
	PostModel post = null;

	try {
	    /* Gets the post's attributes */
	    if (id != null) {
		post = postService.findPostById(Long.parseLong(id));
	    }
	} catch (NumberFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	/* Sets and forward post's attribute to edit page */
	request.setAttribute("p", post);
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
	PostModel post = new PostModel();

	/* message and error showing the result of process */
	String message = null;
	String error = null;

	/* Gets the categories's changing */
	String[] catIds = request.getParameterValues("categories-edited");

	/* Mapping parameter's value to post's model */
	try {
	    BeanUtils.populate(post, request.getParameterMap());
	} catch (IllegalAccessException | InvocationTargetException e1) {
	    e1.printStackTrace();
	}

	try {
	    Long id = post.getId();

	    /* Updates the post's value in database */
	    postService.edit(post);

	    /* Updates the post's categories in database */
	    postGroupService.updateCategory(id, catIds);

	    /* Gets the post's attribute updated */
	    post = postService.findPostById(id);
	    message = "Success";
	} catch (SQLException sqlEx) {
	    error = sqlEx.getErrorCode() + sqlEx.getMessage() + sqlEx.getCause();
	    sqlEx.printStackTrace();
	} catch (Exception e) {
	    error = e.getMessage();
	    e.printStackTrace();
	}

	/* Sets and forward post's attribute with process's message to edit page */
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	request.setAttribute("p", post);
	PageInfo.PrepareAndForward(request, response, PageType.EDIT_POST);
    }
}
