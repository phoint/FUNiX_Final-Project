/*
 * @(#) PostMapper.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.PostModel;

/**
 * Representing a map which reflecting all the data from result set to post
 * instance
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public class PostMapper implements RowMapper<PostModel> {
    /* Implement a method setting value for post's attributes */

    /**
     * Maps all data got from postDAO and sets to post's attributes
     * 
     * @param rs A result set instance containing all data have gotten from DAO
     * @return An instance representing a post's information
     */
    @Override
    public PostModel mapRow(ResultSet rs) {
	try {
	    PostModel post = new PostModel();
	    post.setId(rs.getLong("PostID"));
	    post.setTitle(rs.getString("PostTitle"));
	    post.setContent(rs.getNString("Content"));
	    post.setExcerpt(rs.getNString("Excerpt"));
	    post.setPostUrl(rs.getString("PostURL"));
	    post.setCreatedDate(rs.getDate("CreateDate"));
	    post.setPublishDate(rs.getDate("PublishDate"));
	    post.setModifiedDate(rs.getDate("ModifyDate"));
	    post.setPostStatus(rs.getInt("PostStatus"));
	    post.setIsVisible(rs.getInt("IsVisible"));
	    post.setCreatedBy(rs.getLong("Author"));
	    post.setFeature(rs.getString("Feature"));
	    return post;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
    }

}
