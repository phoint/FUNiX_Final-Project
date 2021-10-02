package edu.funix.common;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.PostModel;

public interface IPostService {
	List<PostModel> findAll() throws SQLException, Exception;
	PostModel findPostById(long postId) throws SQLException, Exception;
	Long save(PostModel postModel) throws SQLException, Exception;
	void edit(PostModel postModel) throws SQLException, Exception;
	Long getTotalItems() throws SQLException, Exception;
	void delete(long id) throws SQLException, Exception;
}
