/*
 * @(#) PostDAO.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.IPostDAO;
import edu.funix.model.PageModel;
import edu.funix.model.PostModel;
import edu.funix.model.mapper.PostMapper;

/**
 * The PostDAO class provides methods to interact with tblPOST in database
 * and/or getting data back.
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public class PostDAO extends AbstractDAO<PostModel> implements IPostDAO {

    /**
     * Gets a list of post instances matching all data rows in table
     * 
     * @return A list representing the post instances
     */
    @Override
    public List<PostModel> findAll() throws SQLException, Exception {
	String sql = "SELECT * FROM tblPOST";
	return query(sql, new PostMapper());
    }

    /**
     * Gets a list of post instances matching a specified range of data in table
     * 
     * @param page An instance of PageModel containing the paging's attribute
     * @return A list representing the post instances
     * 
     * @see PageModel
     */
    @Override
    public List<PostModel> findAll(PageModel page) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("SELECT * FROM ");
	sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY CreateDate DESC) AS RowNumber FROM tblPOST) AS Pagable ");
	if (page.getTotalPage() == 1) {
	    return query(sql.toString(), new PostMapper());
	} else {
	    sql.append("WHERE RowNumber BETWEEN ? AND ?");
	    return query(sql.toString(), new PostMapper(), page.getOffset() + 1, page.getLimit());
	}
    }
    
    /**
     * Gets a list of post instances grouped by a category
     * 
     * @param CatID A Long containing category's id
     * @param page  An instance of PageModel containing the paging's attribute
     * @return A list representing the post instances in category
     */
    @Override
    public List<PostModel> categoryGroup(Long CatID, PageModel page) throws SQLException, Exception {
	String sql = "{CALL CategoryGroup(?,?,?)}";
	return call(sql, new PostMapper(), CatID, page.getLimit(), page.getOffset());
    }
    
    /**
     * Gets a list of post instances has title matching search key
     * 
     * @param page      An instance of PageModel containing the paging's attribute
     * @param searchKey A String containing the title's keyword for searching
     * @return A list representing the post instances
     */
    @Override
    public List<PostModel> search(PageModel page, String searchKey) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("SELECT * FROM ");
	String key = "%" + searchKey + "%";
	sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY CreateDate DESC) AS RowNumber "
		+ "FROM tblPOST WHERE PostTitle LIKE ?) AS Pagable ");
	if (page.getTotalPage() == 1) {
	    return query(sql.toString(), new PostMapper(), key);
	} else {
	    sql.append("WHERE RowNumber BETWEEN ? AND ?");
	    return query(sql.toString(), new PostMapper(), key, page.getOffset() + 1, page.getLimit());
	}
    }

    /**
     * Gets a post instance had id matching the id in row table
     * 
     * @param postId A long containing the number of post's id
     * @return An instance representing the post
     */
    @Override
    public PostModel findPostById(long postId) throws SQLException, Exception {
	String sql = "SELECT * FROM tblPOST WHERE PostID = ?";
	return query(sql, new PostMapper(), postId).get(0);
    }

    /**
     * Insert a new row to post table
     * 
     * @param postModel An instance containing the post's attribute
     * @return A Long containing the id number of new data inserted
     */
    @Override
    public Long save(PostModel post) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("INSERT INTO tblPOST ");
	sql.append("(PostTitle, Excerpt, Content, PostURL, PublishDate, ");
	sql.append("PostStatus, IsVisible, Author, Feature) ");
	sql.append("VALUES (?,?,?,?,?,?,?,?,?)");
	return insert(sql.toString(), post.getTitle(), post.getExcerpt(), post.getContent(), post.getPostUrl(),
		post.getPublishDate(), post.getPostStatus(), post.getIsVisible(), post.getCreatedBy(),
		post.getFeature());
    }

    /**
     * Update an existing row from post table
     * 
     * @param postModel An instance containing the post's attribute edited
     */
    @Override
    public void edit(PostModel post) throws SQLException, Exception {
	String sql = "UPDATE tblPOST SET PostTitle = ?, Excerpt = ?, "
		+ "Content = ?, PostURL = ?, PublishDate = ?, ModifyDate = ?, "
		+ "PostStatus = ?, IsVisible = ?, Feature = ? WHERE PostID = ?";
	update(sql, post.getTitle(), post.getExcerpt(), post.getContent(), post.getPostUrl(), post.getPublishDate(),
		post.getModifiedDate(), post.getPostStatus(), post.getIsVisible(), post.getFeature(), post.getId());
    }

    /**
     * Gets the total of post in post table
     * 
     * @return A Long representing the number of post
     */
    @Override
    public Long getTotalItems() throws SQLException, Exception {
	String sql = "SELECT count(*) FROM tblPOST";
	return count(sql);
    }

    /**
     * Gets the total of post in post table have title matching search key
     * 
     * @param postTitle A String containing keyword searching
     * @return A Long representing the total of post meet the conditions
     */
    @Override
    public Long getTotalItems(String postTitle) throws SQLException, Exception {
	String sql = "SELECT count(*) FROM tblPOST WHERE PostTitle LIKE ?";
	String key = "%" + postTitle + "%";
	return count(sql, key);
    }
    
    @Override
    public Long getTotalItems(long userID) throws SQLException, Exception {
        String sql = "SELECT count(*) FROM tblPOST WHERE Author = ?";
        return count(sql, userID);
    }

    /**
     * Delete an existing row from post table has an id matched
     * 
     * @param id A long containing the post's id
     */
    @Override
    public void delete(long id) throws SQLException, Exception {
	String sql = "DELETE FROM tblPOST WHERE PostID = ?";
	update(sql, id);
    }

    /**
     * Delete an existing row from post table has an id matched
     * 
     * @param id A String array containing the post's ids
     */
    @Override
    public void delete(Object[] ids) throws SQLException, Exception {
	String sql = "DELETE FROM tblPOST WHERE PostID IN (?)";
	update(sql, ids);
    }

    /**
     * Delete all items from post table
     */
    @Override
    public void deleteAll() throws SQLException, Exception {
	String sql = "DELETE FROM tblPOST";
	update(sql);
    }

}
