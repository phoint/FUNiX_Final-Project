package edu.funix.dao;

import java.util.List;

import edu.funix.model.CategoryModel;

public interface ICategoryDAO extends GenericDAO<CategoryModel> {
	List<CategoryModel> findAll();
	CategoryModel findCategoryById(long id);
	void save(CategoryModel category);
	List<CategoryModel> findAllByPostId(long postID);
	void edit(CategoryModel category);
	void delete(long id);
}
