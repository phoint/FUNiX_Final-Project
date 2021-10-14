package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.IPostGroupedDAO;
import edu.funix.model.PostGroupedModel;
import edu.funix.model.mapper.PostGroupedMapper;

public class PostGroupedDAO extends AbstractDAO<PostGroupedModel> implements IPostGroupedDAO {

    @Override
    public List<PostGroupedModel> findAll() throws SQLException, Exception {
	String sql = "SELECT * FROM tblPOSTGROUP";
	return query(sql, new PostGroupedMapper());
    }

    @Override
    public boolean search(long catId, long postId) throws SQLException, Exception {
	String sql = "SELECT * FROM tblPOSTGROUP WHERE CatID = ? AND PostID = ?";
	return query(sql, new PostGroupedMapper(), catId, postId).isEmpty() ? false : true;
    }

    @Override
    public List<PostGroupedModel> findAllPostByCatId(long catId) throws SQLException, Exception {
	String sql = "SELECT * FROM tblPOSTGROUP WHERE CatID = ?";
	return query(sql, new PostGroupedMapper(), catId);
    }

    @Override
    public List<PostGroupedModel> findAllCatByPostId(long postId) throws SQLException, Exception {
	String sql = "SELECT * FROM tblPOSTGROUP WHERE PostID = ?";
	return query(sql, new PostGroupedMapper(), postId);
    }

    @Override
    public void save(long CatId, long PostId) throws SQLException, Exception {
	String sql = "INSERT INTO tblPOSTGROUP(CatID, PostID) VALUES (?,?)";
	update(sql, CatId, PostId);
    }

    @Override
    public void delete(long postId) throws SQLException, Exception {
	String sql = "DELETE FROM tblPOSTGROUP WHERE PostID = ?";
	update(sql, postId);
    }

    @Override
    public void delete(long CatId, long PostId) throws SQLException, Exception {
	String sql = "DELETE FROM tblPOSTGROUP WHERE CatID = ? AND PostID = ?";
	update(sql, CatId, PostId);

    }

    @Override
    public void deleteAll() throws SQLException, Exception {
	String sql = "DELETE FROM tblPOSTGROUP";
	update(sql);
    }

    @Override
    public Long totalPostByCategory(long CatId) throws SQLException, Exception {
	String sql = "SELECT count(*) FROM tblPOSTGROUP GROUP BY CatID Having CatID = ?";
	return count(sql, CatId);
    }

    @Override
    public void delete(Object[] PostId) throws SQLException, Exception {
	String sql = "DELETE FROM tblPOSTGROUP WHERE PostID IN (?)";
	update(sql, PostId);
    }

}
