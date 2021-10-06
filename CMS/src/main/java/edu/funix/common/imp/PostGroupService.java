package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.IPostGroupService;
import edu.funix.dao.ICategoryDAO;
import edu.funix.dao.IPostGroupedDAO;
import edu.funix.dao.imp.CategoryDAO;
import edu.funix.dao.imp.PostGroupedDAO;
import edu.funix.model.CategoryModel;

public class PostGroupService implements IPostGroupService {
	private IPostGroupedDAO postGroupDAO;
	private ICategoryDAO categoryDao;

	public PostGroupService() {
		postGroupDAO = new PostGroupedDAO();
		categoryDao = new CategoryDAO();
	}

	@Override
	public List<CategoryModel> findCategoryInUse(long postID) throws SQLException, Exception {
		List<CategoryModel> categories = categoryDao.findAll();
		for (CategoryModel category : categories) {
			if (postGroupDAO.search(category.getId(), postID)) {
				category.setUsed(true);
			}
		}
		return categories;
	}

	@Override
	public void updateCategory(long postId, String[] catIds) throws SQLException, Exception {
		postGroupDAO.delete(postId);
		if (catIds != null) {
			for (int i = 0; i < catIds.length; i++) {
				postGroupDAO.save(Long.parseLong(catIds[i]), postId);
			}
		}
	}
}
