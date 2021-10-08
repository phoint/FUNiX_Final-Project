package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.ICategoryService;
import edu.funix.dao.ICategoryDAO;
import edu.funix.dao.IPostGroupedDAO;
import edu.funix.dao.imp.CategoryDAO;
import edu.funix.dao.imp.PostGroupedDAO;
import edu.funix.model.CategoryModel;
import edu.funix.model.PageModel;
import edu.funix.model.PostModel;

public class CategoryService implements ICategoryService {
	ICategoryDAO categoryDAO;
	IPostGroupedDAO postGroupDAO;

	public CategoryService() {
		categoryDAO = new CategoryDAO();
		postGroupDAO = new PostGroupedDAO();
	}

	@Override
	public List<CategoryModel> findAll() throws SQLException, Exception {
		List<CategoryModel> categories = categoryDAO.findAll();
		for (CategoryModel category : categories) {
			category.setTotalPost(postGroupDAO.totalPostByCategory(category.getId()));
		}
		return categories;
	}
	
	@Override
	public List<CategoryModel> findAll(long offset, long limit) throws SQLException, Exception {
		List<CategoryModel> categories = categoryDAO.findAll(offset, limit);
		for (CategoryModel category : categories) {
			category.setTotalPost(postGroupDAO.totalPostByCategory(category.getId()));
		}
		return categories;
	}

	@Override
	public CategoryModel findCategoryById(int id) throws SQLException, Exception {
		return categoryDAO.findCategoryById(id);
	}

	@Override
	public void save(CategoryModel category) throws SQLException, Exception {
		categoryDAO.save(category);
	}

	@Override
	public void edit(CategoryModel category) throws SQLException, Exception {
		categoryDAO.edit(category);
	}

	@Override
	public void delete(long id) throws SQLException, Exception {
		categoryDAO.delete(id);
	}
	
	@Override
	public void multiDelete(String[] ids) throws NumberFormatException, SQLException, Exception {
		for (int i = 0; i < ids.length; i++) {
			categoryDAO.delete(Long.parseLong(ids[i]));
		}
	}
	
	@Override
	public Long getTotalItems() throws SQLException, Exception {
		// TODO Auto-generated method stub
		return categoryDAO.getTotalItems();
	}

	@Override
	public List<CategoryModel> pageRequest(PageModel page) throws SQLException, Exception {
		if (page.getMaxItem() == null) {
			page.setMaxItem(10);
		}
		page.setTotalPage((long)Math.ceil((double)getTotalItems() / page.getMaxItem()));
		if (page.getTotalPage() > 1 && page.getCurrentPage() == null) {
			page.setCurrentPage(1);
		} 
		if (page.getCurrentPage() != null) {
			long offset = (page.getCurrentPage() - 1) * page.getMaxItem();
			long limit = page.getCurrentPage() * page.getMaxItem();
			return findAll(offset, limit);
		}
		return findAll();
	}
}
