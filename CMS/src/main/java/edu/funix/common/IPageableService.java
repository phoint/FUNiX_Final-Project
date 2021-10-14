package edu.funix.common;

import edu.funix.model.PageModel;

public interface IPageableService {
    PageModel pageRequest(PageModel page, Long totalItems);
}
