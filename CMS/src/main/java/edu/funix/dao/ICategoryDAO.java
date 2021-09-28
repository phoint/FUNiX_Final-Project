package edu.funix.dao;

import java.util.List;

import edu.funix.model.CategoryModel;

public interface ICategoryDAO extends GenericDAO<CategoryModel> {
	List<CategoryModel> findAll();
}
