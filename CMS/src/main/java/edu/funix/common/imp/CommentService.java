package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.ICommentService;
import edu.funix.dao.ICommentDAO;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.IUserDAO;
import edu.funix.dao.imp.CommentDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.CommentModel;

public class CommentService implements ICommentService {
    private ICommentDAO commentDAO;
    private IUserDAO userDAO;
    private IPostDAO postDAO;

    public CommentService() {
	commentDAO = new CommentDAO();
	userDAO = new UserDAO();
	postDAO = new PostDAO();
    }

    @Override
    public List<CommentModel> findAll() throws SQLException, Exception {
	List<CommentModel> comments = commentDAO.findAll();
	for (CommentModel comment : comments) {
	    comment.setAuthor(userDAO.findBy(comment.getCreatedBy()));
	    comment.setResponseIn(postDAO.findPostById(comment.getSubmitTo()));
	}
	return comments;
    }

    @Override
    public List<CommentModel> findAllParent(long id) throws SQLException, Exception {
	List<CommentModel> comments = commentDAO.findAllParent(id);
	for (CommentModel comment : comments) {
	    comment.setAuthor(userDAO.findBy(comment.getCreatedBy()));
	}
	return comments;
    }

    @Override
    public List<CommentModel> findAllReply(long id) throws SQLException, Exception {
	List<CommentModel> comments = commentDAO.findAllReply(id);
	for (CommentModel comment : comments) {
	    comment.setAuthor(userDAO.findBy(comment.getCreatedBy()));
	}
	return comments;
    }

    @Override
    public List<CommentModel> findAllInPost(long id) throws SQLException, Exception {
	List<CommentModel> comments = findAllParent(id);
	for (CommentModel comment : comments) {
	    comment.setReplies(findAllReply(comment.getId()));
	}
	return comments.isEmpty() ? null : comments;
    }

    @Override
    public void save(CommentModel comment) throws SQLException, Exception {
	commentDAO.save(comment);
    }

    @Override
    public void delete(String[] ids) throws NumberFormatException, SQLException, Exception {
	for (String id : ids) {
	    commentDAO.delete(Long.parseLong(id));
	}

    }

    @Override
    public void confirm(String[] ids, boolean term) throws SQLException, Exception {
	for (String id : ids) {
	    commentDAO.confirm(Long.parseLong(id), term);
	}
    }

}
