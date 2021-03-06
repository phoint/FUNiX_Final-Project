package edu.funix.dao;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.PostGroupedModel;

public interface IPostGroupedDAO extends GenericDAO<PostGroupedModel> {
    List<PostGroupedModel> findAll() throws SQLException, Exception;

    boolean search(long catId, long postId) throws SQLException, Exception;

    List<PostGroupedModel> findAllPostByCatId(long catId) throws SQLException, Exception;

    List<PostGroupedModel> findAllCatByPostId(long postId) throws SQLException, Exception;

    void save(long CatId, long PostId) throws SQLException, Exception;

    void delete(long PostId) throws SQLException, Exception;

    void delete(Object[] PostId) throws SQLException, Exception;

    void deleteCategory(long CatId) throws SQLException, Exception;

    void deleteAll() throws SQLException, Exception;

    Long totalPostByCategory(long CatId) throws SQLException, Exception;

}
