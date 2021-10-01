package edu.funix.common.imp;

import java.util.List;

import edu.funix.common.IPostService;
import edu.funix.dao.ICategoryDAO;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.IUserDAO;
import edu.funix.dao.imp.CategoryDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.PostModel;

public class PostService implements IPostService{
	
	private IPostDAO postDAO;
	private ICategoryDAO catDAO;
	private IUserDAO userDAO;
	
	public PostService() {
		postDAO = new PostDAO();
		catDAO = new CategoryDAO();
		userDAO = new UserDAO();
	}

	@Override
	public List<PostModel> findAll() {
		List<PostModel> postList = postDAO.findAll();
		for (PostModel post : postList) {
			post.setCategories(catDAO.findAllByPostId(post.getId()));
			post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
		}
		return postList;
	}

	@Override
	public PostModel findPostById(long postId) {
		PostModel post = postDAO.findPostById(postId);
		post.setCategories(catDAO.findAllByPostId(post.getId()));
		post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
		return post;
	}

	@Override
	public Long save(PostModel postModel) {
		return postDAO.save(postModel);
	}

	@Override
	public Long getTotalItems() {
		// TODO Auto-generated method stub
		return postDAO.getTotalItems();
	}

	@Override
	public void edit(PostModel postModel) {
		postDAO.edit(postModel);
		
	}
	
	
	
}
