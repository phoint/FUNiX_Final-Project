package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.PostGroupedModel;

public class PostGroupedMapper implements RowMapper<PostGroupedModel> {

	@Override
	public PostGroupedModel mapRow(ResultSet rs) {
		try {
			PostGroupedModel postgroups = new PostGroupedModel();
			postgroups.setCatID(rs.getLong("CatID"));
			postgroups.setPostID(rs.getLong("PostID"));
			return postgroups;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
