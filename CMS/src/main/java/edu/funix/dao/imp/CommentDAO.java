package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.ICommentDAO;
import edu.funix.model.CommentModel;
import edu.funix.model.mapper.CommentMapper;

public class CommentDAO extends AbstractDAO<CommentModel> implements ICommentDAO {

	@Override
	public List<CommentModel> findAll() throws SQLException, Exception {
		String sql = "SELECT * FROM tblCOMMENT";
		return query(sql, new CommentMapper());
	}

	@Override
	public List<CommentModel> findAllParent(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM tblCOMMENT WHERE ReplyTo IS NULL AND SubmitTo = ?";
		return query(sql, new CommentMapper(), id);
	}

	@Override
	public List<CommentModel> findAllReply(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM tblCOMMENT WHERE ReplyTo = ?";
		return query(sql, new CommentMapper(), id);
	}

	@Override
	public void confirm(long id, boolean term) throws SQLException, Exception {
		String sql = "UPDATE tblCOMMENT SET Confirm = ? WHERE ComID = ?";
		update(sql, term ? 1 : 0, id);
	}

	@Override
	public void delete(long id) throws SQLException, Exception {
		String sql = "DELETE FROM tblCOMMENT WHERE ComID = ?";
		update(sql, id);
	}

	@Override
	public void save(CommentModel comment) throws SQLException, Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tblCOMMENT (ComContent, SubmitBy, SubmitTo, ReplyTo, Confirm) ");
		sql.append("VALUES (?,?,?,?,?)");
		update(sql.toString(), comment.getComContent(), comment.getCreatedBy(), comment.getSubmitTo(),
					comment.getReplyTo(), comment.getConfirm() ? 1: 0);
	}
	
	

}
