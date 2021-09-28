package edu.funix.dao.imp;

import java.util.List;

import edu.funix.dao.IPostDAO;
import edu.funix.model.PostModel;
import edu.funix.model.mapper.PostMapper;

public class PostDAO extends AbstractDAO<PostModel> implements IPostDAO {

	@Override
	public List<PostModel> findAll() {
		String sql = "SELECT * FROM tblPOST";
		return query(sql, new PostMapper());
	}

	@Override
	public Long save(PostModel postModel) {
		String sql = "INSERT INTO tblPOST(PostTitle, PostURL, Content) VALUES (?,?,?)";
		return insert(sql, postModel.getTitle(), postModel.getPostUrl(), postModel.getContent());
	}

	@Override
	public void edit(PostModel postModel) {
	// TODO Auto-generated method stub
	}

	@Override
	public Long getTotalItems() {
		String sql = "SELECT count(*) FROM tblPOST";
		return count(sql);
	}
	
	
}
