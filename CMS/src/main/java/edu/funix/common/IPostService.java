package edu.funix.common;

import java.util.List;

import edu.funix.model.PostModel;

public interface IPostService {
	List<PostModel> findAll();
	PostModel save();
	Long getTotalItems();
}
