package edu.funix.dao;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.CommentModel;

public interface ICommentDAO extends GenericDAO<CommentModel> {
    List<CommentModel> findAll() throws SQLException, Exception;

    List<CommentModel> findAllParent(long id) throws SQLException, Exception;

    List<CommentModel> findAllReply(long id) throws SQLException, Exception;

    void confirm(long id, boolean term) throws SQLException, Exception;

    void delete(long id) throws SQLException, Exception;

    void save(CommentModel comment) throws SQLException, Exception;

}
