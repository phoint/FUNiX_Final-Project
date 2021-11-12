package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.ICategoryService;
import edu.funix.common.IPageableService;
import edu.funix.dao.ICategoryDAO;
import edu.funix.dao.IPostGroupedDAO;
import edu.funix.dao.imp.CategoryDAO;
import edu.funix.dao.imp.PostGroupedDAO;
import edu.funix.model.CategoryModel;
import edu.funix.model.PageModel;

public class CategoryService implements ICategoryService {
    ICategoryDAO categoryDAO;
    IPostGroupedDAO postGroupDAO;
    IPageableService paging;

    public CategoryService() {
	categoryDAO = new CategoryDAO();
	postGroupDAO = new PostGroupedDAO();
	paging = new PageableService();
    }

    @Override
    public List<CategoryModel> findAll() throws SQLException, Exception {
	List<CategoryModel> categories = categoryDAO.findAll();
	for (CategoryModel category : categories) {
	    category.setTotalPost(postGroupDAO.totalPostByCategory(category.getId()));
	}
	return categories;
    }

    @Override
    public List<CategoryModel> findAll(PageModel page) throws SQLException, Exception {
	page = paging.pageRequest(page, getTotalItems());
	List<CategoryModel> categories = categoryDAO.findAll(page);
	for (CategoryModel category : categories) {
	    category.setTotalPost(postGroupDAO.totalPostByCategory(category.getId()));
	}
	return categories;
    }

    @Override
    public List<CategoryModel> search(PageModel page, String searchKey) throws SQLException, Exception {
	page = paging.pageRequest(page, categoryDAO.getTotalItems(searchKey));
	List<CategoryModel> categories = categoryDAO.search(page, searchKey);
	for (CategoryModel category : categories) {
	    category.setTotalPost(postGroupDAO.totalPostByCategory(category.getId()));
	}
	return categories;
    }

    @Override
    public CategoryModel findCategoryById(long id) throws SQLException, Exception {
	CategoryModel category = categoryDAO.findCategoryById(id);
	if (category == null) {
	    throw new SQLException("The category is not found");
	}
	return category;
    }

    @Override
    public void save(CategoryModel category) throws SQLException, Exception {
	if (category.getName() == null) {
	    throw new SQLException("Can not leave the category's name blank");
	} else if (category.getUrl() == null) {
	    throw new SQLException("Can not leave the category's url blank");
	} else {
	    try {
		categoryDAO.save(category);
	    } catch (Exception e) {
		if (e.getMessage().contains("UQ__tblCATEG__9C61AB2658B4A229")) {
		    throw new Exception("The category's name is existed");
		} else if (e.getMessage().contains("UQ__tblCATEG__239D501FAF2356F7")) {
		    throw new Exception("The category's url is existed");
		} else {
		    throw new Exception(e);
		}
	    }
	}
    }

    @Override
    public void edit(CategoryModel category) throws SQLException, Exception {
	if (category.getName() == null) {
	    throw new SQLException("Can not leave the category's name blank");
	} else if (category.getUrl() == null) {
	    throw new SQLException("Can not leave the category's url blank");
	} else {
	    try {
		categoryDAO.edit(category);
	    } catch (Exception e) {
		if (e.getMessage().contains("UQ__tblCATEG__9C61AB2658B4A229")) {
		    throw new Exception("The category's name is existed");
		} else if (e.getMessage().contains("UQ__tblCATEG__239D501FAF2356F7")) {
		    throw new Exception("The category's url is existed");
		} else {
		    throw new Exception(e);
		}
	    }
	}
    }

    @Override
    public void delete(long id) throws SQLException, Exception {
	categoryDAO.delete(id);
    }

    @Override
    public void delete(String[] ids) throws NumberFormatException, SQLException, Exception {
	for (int i = 0; i < ids.length; i++) {
	    categoryDAO.delete(Long.parseLong(ids[i]));
	}
    }

    @Override
    public Long getTotalItems() throws SQLException, Exception {
	// TODO Auto-generated method stub
	return categoryDAO.getTotalItems();
    }
}
