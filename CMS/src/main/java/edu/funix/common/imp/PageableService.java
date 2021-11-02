/**
 * 
 */
package edu.funix.common.imp;

import edu.funix.common.IPageableService;
import edu.funix.model.PageModel;

/**
 * @author Admin
 *
 */
public class PageableService implements IPageableService {

    @Override
    public PageModel pageRequest(PageModel page, Long totalItems) {
	if (page.getMaxItem() == null) {
	    page.setMaxItem(10);
	}
	if (totalItems != null) {
	    page.setTotalPage((long) Math.ceil((double) (totalItems - 1) / page.getMaxItem()));
	}
	if (page.getTotalPage() > 1 && page.getCurrentPage() == null) {
	    page.setCurrentPage(1);
	}
	if (page.getCurrentPage() != null) {
	    page.setOffset((long) (page.getCurrentPage() - 1) * page.getMaxItem());
	    page.setLimit((long) page.getCurrentPage() * page.getMaxItem());
	} else {
	    page.setOffset(0L);
	    page.setLimit((long)page.getMaxItem());
	}
	return page;
    }

}
