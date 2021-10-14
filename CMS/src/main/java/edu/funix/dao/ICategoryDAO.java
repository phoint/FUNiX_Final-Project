package edu.funix.dao;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.CategoryModel;

public interface ICategoryDAO extends GenericDAO<CategoryModel> {
	
	List<CategoryModel> findAll() throws SQLException, Exception;

	CategoryModel findCategoryById(long id) throws SQLException, Exception;

	void save(CategoryModel category) throws SQLException, Exception;

	List<CategoryModel> findAllByPostId(long postID) throws SQLException, Exception;

	void edit(CategoryModel category) throws SQLException, Exception;

	void delete(long id) throws SQLException, Exception;

	List<CategoryModel> findAll(long offset, long limit) throws SQLException, Exception;

	Long getTotalItems() throws SQLException, Exception;
}
