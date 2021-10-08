package edu.funix.common;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.CategoryModel;

public interface IPostGroupService {
	List<CategoryModel> findCategoryInUse(long postID) throws SQLException, Exception;
	void updateCategory(long postId, String[] catIds) throws SQLException, Exception;
	Long totalPostByCategory(long CatId) throws SQLException, Exception;
}
