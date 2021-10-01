package edu.funix.model;

public class CategoryModel extends AbstractModel<CategoryModel> {
	private String name;
	private String url;
	private String desc;
	private long totalPost;

	public long getTotalPost() {
		return totalPost;
	}

	public void setTotalPost(long totalPost) {
		this.totalPost = totalPost;
	}

	public CategoryModel() {
		super();
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
