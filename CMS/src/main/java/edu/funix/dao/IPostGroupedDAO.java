package edu.funix.dao;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.PostGroupedModel;

public interface IPostGroupedDAO extends GenericDAO<PostGroupedModel> {
	List<PostGroupedModel> findAll() throws SQLException, Exception;
	List<PostGroupedModel> findAllPostByCatId(long catId) throws SQLException, Exception;
	List<PostGroupedModel> findAllCatByPostId(long postId) throws SQLException, Exception;
	void save(long CatId, long PostId) throws SQLException, Exception;
	void delete(long CatId, long PostId) throws SQLException, Exception;

}
