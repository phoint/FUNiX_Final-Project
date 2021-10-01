package edu.funix.dao.imp;

import java.util.List;

import edu.funix.dao.ICategoryDAO;
import edu.funix.model.CategoryModel;
import edu.funix.model.mapper.CategoryMapper;

public class CategoryDAO extends AbstractDAO<CategoryModel> implements ICategoryDAO {

	@Override
	public List<CategoryModel> findAll() {
		String sql = "SELECT * FROM tblCATEGORY";
		return query(sql, new CategoryMapper());
	}

	@Override
	public CategoryModel findCategoryById(long id) {
		String sql = "SELECT * FROM tblCATEGORY WHERE CatID = ?";
		return query(sql, new CategoryMapper(), id).get(0);
	}

	@Override
	public void save(CategoryModel category) {
		String sql = "INSERT INTO tblCATEGORY(CatName, CatURL, CatDesc) " + "VALUES (?,?,?)";
		update(sql, category.getName(), category.getUrl(), category.getDesc());

	}

	@Override
	public List<CategoryModel> findAllByPostId(long postID) {
		String sql = "SELECT * FROM tblCATEGORY " + "LEFT JOIN tblPOSTGROUP " + "ON tblCATEGORY.CatID = tblPOSTGROUP.CatID "
		    + "WHERE tblPOSTGROUP.PostID = ?";
		return query(sql, new CategoryMapper(), postID);
	}

	@Override
	public void edit(CategoryModel category) {
		String sql = "UPDATE tblCATEGORY SET CatName = ?, CatURL = ?, CatDesc = ? WHERE CatID = ?";
		update(sql, category.getName(), category.getUrl(), category.getDesc(), category.getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM tblCATEGORY WHERE CatID = ?";
		update(sql, id);
	}

}
