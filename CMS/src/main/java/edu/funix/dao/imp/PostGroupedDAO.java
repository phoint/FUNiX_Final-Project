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
	public void delete(long CatId, long PostId) throws SQLException, Exception {
		String sql = "DELETE FROM tblPOSTGROUP WHERE CatID = ? AND PostID = ?";
		update(sql, CatId, PostId);

	}

}
