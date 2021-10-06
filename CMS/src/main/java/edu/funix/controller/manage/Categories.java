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
		CategoryModel categories = new CategoryModel();
		PageModel page = new PageModel();
		try {
			if (id != null && action.equals("delete")) {
				categoryService.delete(Long.parseLong(id));
			}
			BeanUtils.populate(page, request.getParameterMap());
			categories.setListResult(categoryService.pageRequest(page));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("page", page);
		request.setAttribute("categories", categories);
		PageInfo.PrepareAndForward(request, response, PageType.CATEGORY_MANAGEMENT_PAGE);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryModel category = new CategoryModel();
		PageModel page = new PageModel();
		
		try {
			BeanUtils.populate(category, request.getParameterMap());
			if (category.getDesc().equals("")) {
				category.setDesc(null);
			}
			categoryService.save(category);
			category.setListResult(categoryService.pageRequest(page));
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
