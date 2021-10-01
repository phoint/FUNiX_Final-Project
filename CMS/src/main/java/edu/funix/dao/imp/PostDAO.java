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
	public PostModel findPostById(long postId) {
		String sql = "SELECT * FROM tblPOST WHERE PostID = ?";
		return query(sql, new PostMapper(), postId).get(0);
	}

	@Override
	public Long save(PostModel post) {
		String sql = "INSERT INTO tblPOST (PostTitle, Excerpt, Content, PostURL, "
		    + "PublishDate, PostStatus, IsVisible, Author) VALUES (?,?,?,?,?,?,?,?)";
		return insert(sql, post.getTitle(), post.getExcerpt(), post.getContent(), post.getPostUrl(), post.getPublishDate(),
		    post.getPostStatus(), post.getIsVisible(), post.getCreatedBy());
	}

	@Override
	public void edit(PostModel post) {
		String sql = "UPDATE tblPOST SET PostTitle = ?, Excerpt = ?, "
		    + "Content = ?, PostURL = ?, PublishDate = ?, ModifyDate = ?, "
		    + "PostStatus = ?, IsVisible = ? WHERE PostID = ?";
		update(sql, post.getTitle(), post.getExcerpt(), post.getContent(), post.getPostUrl(), post.getPublishDate(),
		    post.getModifiedDate(), post.getPostStatus(), post.getIsVisible(), post.getId());
	}

	@Override
	public Long getTotalItems() {
		String sql = "SELECT count(*) FROM tblPOST";
		return count(sql);
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM tblPOST WHERE PostID = ?";
		update(sql, id);
	}

}
