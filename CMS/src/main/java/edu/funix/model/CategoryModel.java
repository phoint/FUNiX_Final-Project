package edu.funix.model;

public class CategoryModel extends AbstractModel<CategoryModel> {
    private String name;
    private String url;
    private String desc;
    private Long totalPost;
    private boolean isUsed;

    public CategoryModel() {
	super();
    }

    public boolean isUsed() {
	return isUsed;
    }

    public void setUsed(boolean isUsed) {
	this.isUsed = isUsed;
    }

    public Long getTotalPost() {
	return totalPost;
    }

    public void setTotalPost(long totalPost) {
	this.totalPost = totalPost;
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
