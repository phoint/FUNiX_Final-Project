package edu.funix.model;

public class CategoryModel extends AbstractModel<CategoryModel> {
	private String title;
	private String url;

	public CategoryModel() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
