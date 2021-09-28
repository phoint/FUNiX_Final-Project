package edu.funix.common.imp;

import java.util.List;

import edu.funix.common.IPostService;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.model.PostModel;

public class PostService implements IPostService{
	
	private IPostDAO postDAO;
	
	public PostService() {
		postDAO = new PostDAO();
	}

	@Override
	public List<PostModel> findAll() {
		return postDAO.findAll();
	}

	@Override
	public PostModel save() {
		return null;
	}

	@Override
	public Long getTotalItems() {
		// TODO Auto-generated method stub
		return postDAO.getTotalItems();
	}
	
}
