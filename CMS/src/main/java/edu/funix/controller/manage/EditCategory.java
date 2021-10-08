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

/**
 * Servlet implementation class EditCategory
 */
@WebServlet("/admin/edit-category")
public class EditCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ICategoryService categoryService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCategory() {
        super();
        categoryService = new CategoryService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		CategoryModel category = null;
		try {
			if (id != null) {
				category = categoryService.findCategoryById(Integer.parseInt(id));
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
		request.setAttribute("category", category);
		PageInfo.PrepareAndForward(request, response, PageType.EDIT_CATEGORY);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryModel category = new CategoryModel();
	
		try {
			BeanUtils.populate(category, request.getParameterMap());
			if (category.getDesc().equals("")) {
				category.setDesc(null);
			}
			categoryService.edit(category);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("category", category);
		request.setAttribute("message", "Success");
		PageInfo.PrepareAndForward(request, response, PageType.EDIT_CATEGORY);
	}

}
