package edu.funix.dao;

import java.util.List;

import edu.funix.model.PostGroupedModel;

public interface IPostGroupedDAO extends GenericDAO<PostGroupedModel> {
	List<PostGroupedModel> findAll();
	List<PostGroupedModel> findAllPostByCatId(long catId);
	List<PostGroupedModel> findAllCatByPostId(long postId);
	void save(long CatId, long PostId);
	void delete(long CatId, long PostId);

}
