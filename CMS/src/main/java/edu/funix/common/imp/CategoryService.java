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
import edu.funix.model.PostGroupedModel;
import edu.funix.model.PostModel;

public class CategoryService implements ICategoryService {
    ICategoryDAO categoryDAO;
    IPostGroupedDAO postGroupDAO;
    IPageableService paging;

    public CategoryService() {
	categoryDAO = new CategoryDAO();
	postGroupDAO = new PostGroupedDAO();
	paging =new PageableService();
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
    public CategoryModel findCategoryById(int id) throws SQLException, Exception {
	return categoryDAO.findCategoryById(id);
    }

    @Override
    public void save(CategoryModel category) throws SQLException, Exception {
	categoryDAO.save(category);
    }

    @Override
    public void edit(CategoryModel category) throws SQLException, Exception {
	categoryDAO.edit(category);
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
