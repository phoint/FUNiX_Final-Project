package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.IPaging;
import edu.funix.common.IPostGroupService;
import edu.funix.common.IPostService;
import edu.funix.dao.ICategoryDAO;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.IPostGroupedDAO;
import edu.funix.dao.IUserDAO;
import edu.funix.dao.imp.CategoryDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.dao.imp.PostGroupedDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.PostModel;

public class PostService implements IPostService {

	private IPostDAO postDAO;
	private ICategoryDAO catDAO;
	private IUserDAO userDAO;
	private IPostGroupedDAO postGroupDAO;
	private IPostGroupService postGroupService;

	public PostService() {
		postDAO = new PostDAO();
		catDAO = new CategoryDAO();
		userDAO = new UserDAO();
		postGroupDAO = new PostGroupedDAO();
		postGroupService = new PostGroupService();
	}

	@Override
	public List<PostModel> findAll() throws SQLException, Exception {
		List<PostModel> postList = postDAO.findAll();
		for (PostModel post : postList) {
			post.setCategories(postGroupService.findCategoryInUse(post.getId()));
			post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
		}
		return postList;
	}

	@Override
	public List<PostModel> findAll(long offset, long limit) throws SQLException, Exception {
		List<PostModel> postList = postDAO.findAll(offset, limit);
		for (PostModel post : postList) {
			post.setCategories(postGroupService.findCategoryInUse(post.getId()));
			post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
		}
		return postList;
	}

	@Override
	public PostModel findPostById(long postId) throws SQLException, Exception {
		PostModel post = postDAO.findPostById(postId);
		post.setCategories(postGroupService.findCategoryInUse(post.getId()));
		post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
		return post;
	}

	@Override
	public Long save(PostModel postModel) throws SQLException, Exception {
		return postDAO.save(postModel);
	}

	@Override
	public Long getTotalItems() throws SQLException, Exception {
		// TODO Auto-generated method stub
		return postDAO.getTotalItems();
	}

	@Override
	public void edit(PostModel postModel) throws SQLException, Exception {
		postDAO.edit(postModel);

	}

	@Override
	public void delete(long id) throws SQLException, Exception {
		postGroupDAO.delete(id);
		postDAO.delete(id);
	}

	@Override
	public List<PostModel> pageRequest(PageRequest page) throws SQLException, Exception {
		if (page.getPage() != null) {
			return postDAO.findAll(page.getOffset(), page.getLimit());
		}
		return postDAO.findAll();
	}

}
