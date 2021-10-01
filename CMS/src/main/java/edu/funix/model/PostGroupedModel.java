package edu.funix.model;

import java.util.ArrayList;
import java.util.List;

public class PostGroupedModel extends AbstractModel<PostGroupedModel> {
	private long postID;
	private long catID;
	private List<Long> categories;
	private List<Long> posts;

	public PostGroupedModel() {
		super();
		categories = new ArrayList<Long>();
		posts = new ArrayList<Long>();
	}

	public long getPostID() {
		return postID;
	}

	public void setPostID(long postID) {
		this.postID = postID;
	}

	public long getCatID() {
		return catID;
	}

	public void setCatID(long catID) {
		this.catID = catID;
	}

	public List<Long> getCategories() {
		return categories;
	}

	public void setCategories(List<Long> categories) {
		this.categories = categories;
	}

	public List<Long> getPosts() {
		return posts;
	}

	public void setPosts(List<Long> posts) {
		this.posts = posts;
	}
}
