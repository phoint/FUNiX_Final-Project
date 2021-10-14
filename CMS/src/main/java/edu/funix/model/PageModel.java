package edu.funix.model;

public class PageModel {
    private Integer currentPage;
    private Integer maxItem;
    private Long totalPage;
    private Long offset;
    private Long limit;
    private Boolean sortTerm;
    private String sortBy;

    public PageModel() {
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

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
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
}
