package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.PostModel;

public class PostMapper implements RowMapper<PostModel> {

	@Override
	public PostModel mapRow(ResultSet rs) {
		try {
			PostModel post = new PostModel();
			post.setId(rs.getInt("PostID"));
			post.setTitle(rs.getString("PostTitle"));
			post.setPublishDate(rs.getDate("PublishDate"));
			return post;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
