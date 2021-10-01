package edu.funix.common;

import java.util.List;

import edu.funix.model.CategoryModel;

public interface ICategoryService {
	List<CategoryModel> findAll();
	CategoryModel findCategoryById(int id);
	void save(CategoryModel category);
	void edit(CategoryModel category);
	void delete(long id);
}
