package edu.funix.controller.web;

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
import edu.funix.common.IPostService;
import edu.funix.common.imp.PostService;
import edu.funix.model.PageModel;
import edu.funix.model.PostModel;

/**
 * Servlet implementation class Home
 */
@WebServlet("/index.html")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
  IPostService postService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
    	postService = new PostService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PostModel post = new PostModel();
		PageModel page = new PageModel();
		String postId = request.getParameter("p");
		try {
			if (postId != null) {
				post = postService.findPostById(Long.parseLong(postId));
				request.setAttribute("post", post);
				PageInfo.WebPrepareAndForward(request, response, PageType.POST_DETAIL);
				return;
			}
			BeanUtils.populate(page, request.getParameterMap());
			post.setListResult(postService.findAll(page));
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
		request.setAttribute("page", page);
		request.setAttribute("posts", post);
		PageInfo.WebPrepareAndForward(request, response, PageType.HOMEPAGE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
