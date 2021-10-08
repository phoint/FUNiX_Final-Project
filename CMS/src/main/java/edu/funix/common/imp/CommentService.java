package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.ICommentService;
import edu.funix.dao.ICommentDAO;
import edu.funix.dao.imp.CommentDAO;
import edu.funix.model.CommentModel;

public class CommentService implements ICommentService {
	private ICommentDAO commentDAO;
	
	

	public CommentService() {
		commentDAO = new CommentDAO();
	}

	@Override
	public List<CommentModel> findAll() throws SQLException, Exception {
		return commentDAO.findAll();
	}
	
	@Override
	public List<CommentModel> findAllInPost(long id) throws SQLException, Exception {
		List<CommentModel> comments = commentDAO.findAllParent(id);
		for (CommentModel comment : comments) {
			comment.setReplies(commentDAO.findAllReply(comment.getId()));
		}
		return comments.isEmpty() ? null : comments;
	}


}
