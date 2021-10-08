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
import edu.funix.common.IPostGroupService;
import edu.funix.common.IPostService;
import edu.funix.common.imp.PostGroupService;
import edu.funix.common.imp.PostService;
import edu.funix.model.PostModel;

/**
 * Servlet implementation class Edit
 */
@WebServlet("/admin/edit-post")
public class EditPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IPostService postService;
	private IPostGroupService postGroupService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditPost() {
		postService = new PostService();
		postGroupService = new PostGroupService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		PostModel post = null;
		try {
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
		request.setAttribute("p", post);
		PageInfo.PrepareAndForward(request, response, PageType.EDIT_POST);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		try {
			PostModel post = new PostModel();
			BeanUtils.populate(post, request.getParameterMap());
			String[] catIds = request.getParameterValues("categories-edited");
			//TODO: Problem with Parameter mapping and could not set value for parameter in sql query
			postService.edit(post);
			postGroupService.updateCategory(post.getId(), catIds);
			post = postService.findPostById(post.getId());
			request.setAttribute("p", post);
			request.setAttribute("message", "Success");
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
		PageInfo.PrepareAndForward(request, response, PageType.EDIT_POST);
	}
}
