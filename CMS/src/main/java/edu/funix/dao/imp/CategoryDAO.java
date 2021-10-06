package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.ICategoryDAO;
import edu.funix.model.CategoryModel;
import edu.funix.model.mapper.CategoryMapper;

public class CategoryDAO extends AbstractDAO<CategoryModel> implements ICategoryDAO {

	@Override
	public List<CategoryModel> findAll() throws SQLException, Exception {
		String sql = "SELECT * FROM tblCATEGORY";
		return query(sql, new CategoryMapper());
	}
	
	@Override
	public List<CategoryModel> findAll(long offset, long limit) throws SQLException, Exception {
		StringBuilder sql = new StringBuilder("SELECT TOP(?) * FROM ");
		sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY CatName ASC) AS RowNumber ");
		sql.append("FROM tblCATEGORY) AS Pagable ");
		sql.append("WHERE RowNumber > ?");
		return query(sql.toString(), new CategoryMapper(), limit, offset);
	}
	
	@Override
	public Long getTotalItems() throws SQLException, Exception {
		String sql = "SELECT count(*) FROM tblCATEGORY";
		return count(sql);
	}

	@Override
	public CategoryModel findCategoryById(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM tblCATEGORY WHERE CatID = ?";
		return query(sql, new CategoryMapper(), id).get(0);
	}

	@Override
	public void save(CategoryModel category) throws SQLException, Exception {
		String sql = "INSERT INTO tblCATEGORY(CatName, CatURL, CatDesc) " + "VALUES (?,?,?)";
		update(sql, category.getName(), category.getUrl(), category.getDesc());

	}

	@Override
	public List<CategoryModel> findAllByPostId(long postID) throws SQLException, Exception {
		String sql = "SELECT * FROM tblCATEGORY " + "LEFT JOIN tblPOSTGROUP " + "ON tblCATEGORY.CatID = tblPOSTGROUP.CatID "
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
		update(sql, id);
	}

}
