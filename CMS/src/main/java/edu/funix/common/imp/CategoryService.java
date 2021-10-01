package edu.funix.common.imp;

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
	public List<CategoryModel> findAll() {
		return categoryDAO.findAll();
	}

	@Override
	public CategoryModel findCategoryById(int id) {
		return categoryDAO.findCategoryById(id);
	}

	@Override
	public void save(CategoryModel category) {
		categoryDAO.save(category);
	}

	@Override
	public void edit(CategoryModel category) {
		categoryDAO.edit(category);
	}

	@Override
	public void delete(long id) {
		categoryDAO.delete(id);
	}

}
