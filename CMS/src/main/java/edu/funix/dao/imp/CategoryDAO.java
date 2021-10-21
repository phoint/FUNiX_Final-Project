package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.ICategoryDAO;
import edu.funix.model.CategoryModel;
import edu.funix.model.PageModel;
import edu.funix.model.mapper.CategoryMapper;
import edu.funix.model.mapper.PostGroupedMapper;
import edu.funix.model.mapper.PostMapper;

public class CategoryDAO extends AbstractDAO<CategoryModel> implements ICategoryDAO {

    @Override
    public List<CategoryModel> findAll() throws SQLException, Exception {
	String sql = "SELECT * FROM tblCATEGORY";
	return query(sql, new CategoryMapper());
    }

    @Override
    public List<CategoryModel> findAll(PageModel page) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("SELECT * FROM ");
	sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY CatName ASC) AS RowNumber FROM tblCATEGORY) AS Pagable ");
	if (page.getTotalPage() == 1) {
	    return query(sql.toString(), new CategoryMapper());
	} else {
	    sql.append("WHERE RowNumber BETWEEN ? AND ?");
	    return query(sql.toString(), new CategoryMapper(), page.getOffset() + 1, page.getLimit());
	}
    }

    @Override
    public Long getTotalItems() throws SQLException, Exception {
	String sql = "SELECT count(*) FROM tblCATEGORY";
	return count(sql);
    }

    @Override
    public CategoryModel findCategoryById(long id) throws SQLException, Exception {
	String sql = "SELECT * FROM tblCATEGORY WHERE CatID = ?";
	List<CategoryModel> categories = query(sql, new CategoryMapper(), id);
	return categories.isEmpty() ? null : categories.get(0);
    }

    @Override
    public void save(CategoryModel category) throws SQLException, Exception {
	String sql = "INSERT INTO tblCATEGORY(CatName, CatURL, CatDesc) " + "VALUES (?,?,?)";
	update(sql, category.getName(), category.getUrl(), category.getDesc());
    }

    @Override
    public List<CategoryModel> findAllByPostId(long postID) throws SQLException, Exception {
	String sql = "SELECT * FROM tblCATEGORY LEFT JOIN tblPOSTGROUP ON tblCATEGORY.CatID = tblPOSTGROUP.CatID "
		+ "WHERE tblPOSTGROUP.PostID = ?";
	return query(sql, new CategoryMapper(), postID);
    }

    @Override
    public void edit(CategoryModel category) throws SQLException, Exception {
	String sql = "UPDATE tblCATEGORY SET CatName = ?, CatURL = ?, CatDesc = ? WHERE CatID = ?";
	update(sql, category.getName(), category.getUrl(), category.getDesc(), category.getId());
    }

    @Override
    public void delete(long id) throws SQLException, Exception {
	String sql = "DELETE FROM tblCATEGORY WHERE CatID = ?";
	query(sql, new PostGroupedMapper(), id);
    }

}
