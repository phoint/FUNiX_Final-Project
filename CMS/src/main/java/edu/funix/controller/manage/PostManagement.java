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
@WebServlet("/PostManagement")
public class PostManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IPostService postService;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostManagement() {
		postService = new PostService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PostModel model = new PostModel();
		model.setListResult(postService.findAll());
		model.setCurrentPage(request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage")));
		model.setPage((int) Math.ceil((double) postService.getTotalItems() / model.getMaxItem()));
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
