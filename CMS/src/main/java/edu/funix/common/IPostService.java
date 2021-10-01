package edu.funix.common;

import java.util.List;

import edu.funix.model.PostModel;

public interface IPostService {
	List<PostModel> findAll();
	PostModel findPostById(long postId);
	Long save(PostModel postModel);
	void edit(PostModel postModel);
	Long getTotalItems();
	void delete(long id);
}
