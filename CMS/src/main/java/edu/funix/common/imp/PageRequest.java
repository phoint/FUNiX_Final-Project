package edu.funix.common.imp;

import edu.funix.common.IPaging;

public class PageRequest implements IPaging {
	private Integer page;
	private Integer maxItem;

	public PageRequest(Integer page, Integer maxItem) {
		this.page = page;
		this.maxItem = maxItem;
	}

	@Override
	public Integer getPage() {
		return this.page;
	}

	@Override
	public Integer getOffset() {
		if (page != null) {
			return (this.page - 1) * this.maxItem;
		}
		return null;
	}

	@Override
	public Integer getLimit() {
		if (page != null) {
			return this.maxItem;
		}
		return null;
	}

}
