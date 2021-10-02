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

import edu.funix.common.ICategoryService;
import edu.funix.common.PageInfo;
import edu.funix.common.PageType;
import edu.funix.common.imp.CategoryService;
import edu.funix.model.CategoryModel;
import edu.funix.model.PostModel;

/**
 * Servlet implementation class PostManagement
 */
@WebServlet("/admin-categories")
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String action = request.getParameter("action");
		try {
			if (id != null && action.equals("delete")) {
				categoryService.delete(Long.parseLong(id));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CategoryModel listCategory = new CategoryModel();
		try {
			listCategory.setListResult(categoryService.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		listCategory.setCurrentPage(
//		    request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage")));
//		model.setPage((int) Math.ceil((double) postService.getTotalItems() / model.getMaxItem()));
		request.setAttribute("categories", listCategory);
		PageInfo.PrepareAndForward(request, response, PageType.CATEGORY_MANAGEMENT_PAGE);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		try {
			CategoryModel category = new CategoryModel();
			BeanUtils.populate(category, request.getParameterMap());
			if (category.getDesc().equals("")) {
				category.setDesc(null);
			}
			categoryService.save(category);
			category.setListResult(categoryService.findAll());
			request.setAttribute("categories", category);
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
		PageInfo.PrepareAndForward(request, response, PageType.CATEGORY_MANAGEMENT_PAGE);
	}
}
