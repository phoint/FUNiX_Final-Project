package edu.funix.common;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.CategoryModel;
import edu.funix.model.PageModel;

public interface ICategoryService {
    List<CategoryModel> findAll() throws SQLException, Exception;

    CategoryModel findCategoryById(int id) throws SQLException, Exception;

    void save(CategoryModel category) throws SQLException, Exception;

    void edit(CategoryModel category) throws SQLException, Exception;

    void delete(long id) throws SQLException, Exception;

    List<CategoryModel> findAll(PageModel page) throws SQLException, Exception;

    Long getTotalItems() throws SQLException, Exception;

    void delete(String[] ids) throws NumberFormatException, SQLException, Exception;
}
