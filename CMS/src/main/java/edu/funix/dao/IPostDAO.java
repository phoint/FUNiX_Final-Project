package edu.funix.dao;

import java.util.List;

import edu.funix.model.PostModel;

public interface IPostDAO extends GenericDAO<PostModel> {
	List<PostModel> findAll();
	Long save(PostModel postModel);
	void edit(PostModel postModel);
	Long getTotalItems();
}
