package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.common.ICommentService;
import edu.funix.dao.IAccountDAO;
import edu.funix.dao.ICommentDAO;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.imp.CommentDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.dao.imp.SubcriberDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.AccountModel;
import edu.funix.model.CommentModel;
import edu.funix.model.SubcriberModel;
import edu.funix.model.UserModel;

public class CommentService implements ICommentService {
    private ICommentDAO commentDAO;
    private IAccountDAO<UserModel> userDAO;
    private IAccountDAO<SubcriberModel> subcriberDAO;
    private IPostDAO postDAO;

    public CommentService() {
	commentDAO = new CommentDAO();
	userDAO = new UserDAO();
	subcriberDAO = new SubcriberDAO();
	postDAO = new PostDAO();
    }

    @Override
    public List<CommentModel> findAll() throws SQLException, Exception {
	List<CommentModel> comments = commentDAO.findAll();
	for (CommentModel comment : comments) {
	    AccountModel<?> account = new AccountModel<>();
	    if (comment.getSeedBy() != null && comment.getSeedBy() != 0) {
		BeanUtils.copyProperties(account, userDAO.findBy(comment.getSeedBy()));
	    } else if (comment.getCreatedBy() != null && comment.getCreatedBy() != 0) {
		BeanUtils.copyProperties(account, subcriberDAO.findBy(comment.getCreatedBy()));
	    }
	    comment.setAuthor(account);
	    comment.setResponseIn(postDAO.findPostById(comment.getSubmitTo()));
	}
	return comments;
    }

    @Override
    public List<CommentModel> findAllParent(long postId) throws SQLException, Exception {
	List<CommentModel> comments = commentDAO.findAllParent(postId);
	for (CommentModel comment : comments) {
	    AccountModel<?> account = new AccountModel<>();
	    if (comment.getSeedBy() != null && comment.getSeedBy() != 0) {
		BeanUtils.copyProperties(account, userDAO.findBy(comment.getSeedBy()));
	    } else if (comment.getCreatedBy() != null && comment.getCreatedBy() != 0) {
		BeanUtils.copyProperties(account, subcriberDAO.findBy(comment.getCreatedBy()));
	    }
	    comment.setAuthor(account);
	}
	return comments;
    }

    @Override
    public List<CommentModel> findAllReply(long parentComId) throws SQLException, Exception {
	List<CommentModel> comments = commentDAO.findAllReply(parentComId);
	for (CommentModel comment : comments) {
	    AccountModel<?> account = new AccountModel<>();
	    if (comment.getSeedBy() != null && comment.getSeedBy() != 0) {
		BeanUtils.copyProperties(account, userDAO.findBy(comment.getSeedBy()));
	    } else if (comment.getCreatedBy() != null && comment.getCreatedBy() != 0) {
		BeanUtils.copyProperties(account, subcriberDAO.findBy(comment.getCreatedBy()));
	    }
	    comment.setAuthor(account);
	}
	return comments;
    }

    @Override
    public List<CommentModel> findAllInPost(long postId) throws SQLException, Exception {
	List<CommentModel> comments = findAllParent(postId);
	for (CommentModel comment : comments) {
	    comment.setReplies(findAllReply(comment.getId()));
	}
	return comments.isEmpty() ? null : comments;
    }

    @Override
    public <T> void save(CommentModel comment, AccountModel<T> account) throws SQLException, Exception {
	if (account instanceof SubcriberModel) {
	    comment.setCreatedBy(account.getId());
	} else {
	    comment.setSeedBy(account.getId());
	    comment.setConfirm(true);
	}
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
