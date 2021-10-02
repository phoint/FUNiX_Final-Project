package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.ICategoryService;
import edu.funix.dao.ICategoryDAO;
import edu.funix.dao.imp.CategoryDAO;
import edu.funix.model.CategoryModel;

public class CategoryService implements ICategoryService {
	ICategoryDAO categoryDAO;

	public CategoryService() {
		categoryDAO = new CategoryDAO();
	}

	@Override
	public List<CategoryModel> findAll() throws SQLException, Exception {
		return categoryDAO.findAll();
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

}
