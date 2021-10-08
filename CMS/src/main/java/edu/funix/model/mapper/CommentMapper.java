package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.CommentModel;

public class CommentMapper implements RowMapper<CommentModel> {

	@Override
	public CommentModel mapRow(ResultSet rs) {
		try {
			CommentModel comment = new CommentModel();
			comment.setId(rs.getLong("ComID"));
			comment.setComContent(rs.getString("ComContent"));
			comment.setCreatedDate(rs.getDate("CreateDate"));
			comment.setCreatedBy(rs.getLong("SubmitBy"));
			comment.setSubmitTo(rs.getLong("SubmitTo"));
			comment.setReplyTo(rs.getLong("ReplyTo"));
			comment.setConfirm(rs.getInt("Confirm") == 1 ? true : false);
			return comment;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
