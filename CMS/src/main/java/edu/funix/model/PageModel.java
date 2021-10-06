package edu.funix.model;

public class PageModel {
	private Integer currentPage;
	private Integer maxItem;
	private Long totalPage;
	private Boolean sortTerm;
	private String sortBy;
	
	public PageModel() {
	}
	
	public Boolean isSortTerm() {
		return sortTerm;
	}
	public void setSortTerm(Boolean sortTerm) {
		this.sortTerm = sortTerm;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getMaxItem() {
		return maxItem;
	}
	public void setMaxItem(Integer maxItem) {
		this.maxItem = maxItem;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}
}
