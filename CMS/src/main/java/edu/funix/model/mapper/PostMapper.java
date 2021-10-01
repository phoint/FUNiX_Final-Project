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
			post.setContent(rs.getNString("Content"));
			post.setExcerpt(rs.getNString("Excerpt"));
			post.setPostUrl(rs.getString("PostURL"));
			post.setCreatedDate(rs.getDate("CreateDate"));
			post.setPublishDate(rs.getDate("PublishDate"));
			post.setModifiedDate(rs.getDate("ModifyDate"));
			post.setPostStatus(rs.getInt("PostStatus"));
			post.setIsVisible(rs.getInt("IsVisible"));
			post.setCreatedBy(rs.getLong("Author"));
			return post;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
