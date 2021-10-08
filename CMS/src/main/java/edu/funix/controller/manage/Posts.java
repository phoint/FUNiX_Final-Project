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
import edu.funix.common.IPostService;
import edu.funix.common.imp.PageRequest;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] id = request.getParameterValues("id");
		String action = request.getParameter("action");
		PostModel model = new PostModel();
		PageModel page = new PageModel();
		try {
			if (id != null && action.equals("delete")) {
				for (int i = 0; i < id.length; i++) {
					postService.delete(Long.parseLong(id[i]));
				}
			}
			BeanUtils.populate(page, request.getParameterMap());
			model.setListResult(postService.pageRequest(page));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("posts", model);
		request.setAttribute("page", page);
		PageInfo.PrepareAndForward(request, response, PageType.POST_MANAGEMENT_PAGE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
