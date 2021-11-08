package edu.funix.common;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.AccountModel;
import edu.funix.model.CommentModel;

public interface ICommentService {
    List<CommentModel> findAll() throws SQLException, Exception;

    List<CommentModel> findAllInPost(long id) throws SQLException, Exception;

    List<CommentModel> findAllParent(long id) throws SQLException, Exception;

    List<CommentModel> findAllReply(long id) throws SQLException, Exception;
    
    <T> void save(CommentModel comment, AccountModel<T> account) throws SQLException, Exception;
    
    void delete(String[] ids) throws NumberFormatException, SQLException, Exception;
    
    void confirm(String[] ids, boolean term) throws SQLException, Exception;
}
