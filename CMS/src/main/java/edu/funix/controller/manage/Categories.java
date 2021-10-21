package edu.funix.controller.manage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.Utils.PageInfo;
import edu.funix.Utils.PageType;
import edu.funix.common.ICategoryService;
import edu.funix.common.imp.CategoryService;
import edu.funix.model.CategoryModel;
import edu.funix.model.PageModel;

/**
 * Servlet implementation class PostManagement
 */
@WebServlet("/admin/categories")
public class Categories extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ICategoryService categoryService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Categories() {
	categoryService = new CategoryService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	/* All categories' id selected for action */
	String[] id = request.getParameterValues("id");
	String action = request.getParameter("action");
	/* Search term for querying categories */
	String searchKey = request.getParameter("searchKey");
	CategoryModel categories = new CategoryModel();
	PageModel page = new PageModel();
	/* Message and error return on process */
	String message = null;
	String error = null;
	
	/* Delete a category has matching id */
	try {
	    if (id != null && action.equals("delete")) {
		categoryService.delete(id);
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	/* Mapping parameter to PageModel */
	try {
	    BeanUtils.populate(page, request.getParameterMap());
	} catch (IllegalAccessException | InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	if (searchKey != null && searchKey.trim().equals("")) {
	    searchKey = null;
	}
	/* Display a category list base on search term and pagination */
	try {
	    if (searchKey != null) {
		categories.setListResult(categoryService.search(page, searchKey));
	    } else {		
		categories.setListResult(categoryService.findAll(page));
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	request.setAttribute("error", error);
	request.setAttribute("message", message);
	request.setAttribute("searchKey", searchKey);
	request.setAttribute("page", page);
	request.setAttribute("categories", categories);
	PageInfo.PrepareAndForward(request, response, PageType.CATEGORY_MANAGEMENT_PAGE);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	request.setCharacterEncoding("UTF-8");
	CategoryModel category = new CategoryModel();
	String action = request.getParameter("action");
	String[] ids = request.getParameterValues("id");
	PageModel page = new PageModel();

	try {
	    if (ids != null && action.equals("delete")) {
		categoryService.delete(ids);
	    } else if (ids == null) {
		BeanUtils.populate(category, request.getParameterMap());
		if (category.getDesc() != null && category.getDesc().equals("")) {
		    category.setDesc(null);
		}
		categoryService.save(category);
	    }
	    category.setListResult(categoryService.findAll(page));
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
	request.setAttribute("message", "Success");
	request.setAttribute("categories", category);
	request.setAttribute("page", page);
	PageInfo.PrepareAndForward(request, response, PageType.CATEGORY_MANAGEMENT_PAGE);
    }
}
