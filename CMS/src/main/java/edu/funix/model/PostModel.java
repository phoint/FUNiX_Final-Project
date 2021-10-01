package edu.funix.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PostModel extends AbstractModel<PostModel> {
	private String title;
	private String excerpt;
	private String content;
	private String postUrl;
	private Date publishDate;
	private int postStatus;
	private boolean isVisible;
	private String feature;
	private AbstractModel<UserModel> author;
	private List<CategoryModel> categories;

	public PostModel() {
		super();
		author = new UserModel();
		categories = new ArrayList<CategoryModel>();
	}

	public AbstractModel<UserModel> getAuthor() {
		return author;
	}

	public void setAuthor(AbstractModel<UserModel> author) {
		this.author = author;
	}

	public List<CategoryModel> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryModel> categories) {
		this.categories = categories;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public int getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(int postStatus) {
		this.postStatus = postStatus;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

}
