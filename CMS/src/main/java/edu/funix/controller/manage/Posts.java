package edu.funix.controller.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.common.ICategoryService;
import edu.funix.common.IPostService;
import edu.funix.common.imp.CategoryService;
import edu.funix.common.imp.PostService;
import edu.funix.model.PageModel;
import edu.funix.model.PostModel;

/**
 * Servlet implementation class PostManagement
 */
@WebServlet("/admin/posts")
public class Posts extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	/* Gets all id selected from view's form check box */
	String[] id = request.getParameterValues("id");
	String action = request.getParameter("action");
	String category = request.getParameter("category");
	/* Gets the title search key from view's form input */
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
	    searchKey = null;
	}
	try {
	    /* Check parameter for deleting feature */
	    if (id != null && action.equals("delete")) {
		for (int i = 0; i < id.length; i++) {
		    postService.delete(Long.parseLong(id[i]));
		}
		message = "delete success!";
	    }
	    /* Check parameter for deleting all items */
	    if (action != null && action.equals("deleteAll")) {
		postService.deleteAll();
		message = "All items deleted!";
	    }
	    BeanUtils.populate(page, request.getParameterMap());
	    post.setCategories(categoryService.findAll());

	    /* check the category' id for displaying posts in group */
	    if (category != null && !category.trim().equals("")) {
		post.setListResult(postService.categoryGroup(Long.parseLong(category), page));
	    } else if (searchKey != null) {
		/* Calls the service depend on search term */
		post.setListResult(postService.search(page, searchKey));
	    } else {
		post.setListResult(postService.findAll(page));
	    }
	} catch (Exception e) {
	    /* something go wrong */
	    error = "something wrong, try later!";
	    e.printStackTrace();
	}

	/* Forward to Post view */
	request.setAttribute("message", message);
	request.setAttribute("error", error);
	request.setAttribute("category", category);
	request.setAttribute("searchKey", searchKey);
	request.setAttribute("posts", post);
	request.setAttribute("page", page);
	PageInfo.PrepareAndForward(request, response, PageType.POST_MANAGEMENT_PAGE);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	/* Do everything the same doGet method */
	doGet(request, response);
    }

}
