package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.CategoryModel;

public class CategoryMapper implements RowMapper<CategoryModel> {

	@Override
	public CategoryModel mapRow(ResultSet rs) {
		try {
			CategoryModel model = new CategoryModel();
			model.setId(rs.getLong("catID"));
			model.setTitle(rs.getString("catName"));
			model.setUrl(rs.getString("URL"));
			model.setCreatedDate(rs.getTimestamp("createDate"));
			return model;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
