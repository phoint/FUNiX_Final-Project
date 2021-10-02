package edu.funix.controller.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.funix.common.IPostService;
import edu.funix.common.PageInfo;
import edu.funix.common.PageType;
import edu.funix.common.imp.PostService;
import edu.funix.model.PostModel;

/**
 * Servlet implementation class PostManagement
 */
@WebServlet("/admin-posts")
public class Posts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IPostService postService;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Posts() {
		postService = new PostService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String action = request.getParameter("action");
		PostModel model = new PostModel();
		try {
			if (id != null && action.equals("delete")) {
				postService.delete(Long.parseLong(id));
			}
			model.setListResult(postService.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		model.setCurrentPage(request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage")));
//		model.setPage((int) Math.ceil((double) postService.getTotalItems() / model.getMaxItem()));
		request.setAttribute("posts", model);
		PageInfo.PrepareAndForward(request, response, PageType.POST_MANAGEMENT_PAGE);
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
