/*
 * @(#) PostService.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.ICommentService;
import edu.funix.common.IPageableService;
import edu.funix.common.IPostGroupService;
import edu.funix.common.IPostService;
import edu.funix.dao.IMediaDAO;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.IPostGroupedDAO;
import edu.funix.dao.IUserDAO;
import edu.funix.dao.imp.MediaDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.dao.imp.PostGroupedDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.PageModel;
import edu.funix.model.PostModel;

public class PostService implements IPostService {

    private IPostDAO postDAO;
    private IUserDAO userDAO;
    private IPostGroupedDAO postGroupDAO;
    private IPostGroupService postGroupService;
    private IMediaDAO mediaDAO;
    private ICommentService commentService;
    private IPageableService paging;

    /**
     * Creates an instances of Post Service
     */
    public PostService() {
	postDAO = new PostDAO();
	userDAO = new UserDAO();
	postGroupDAO = new PostGroupedDAO();
	postGroupService = new PostGroupService();
	mediaDAO = new MediaDAO();
	commentService = new CommentService();
	paging = new PageableService();
    }

    /**
     * Finds all post items
     * 
     * @return A list representing all post model
     */
    @Override
    public List<PostModel> findAll() throws SQLException, Exception {
	List<PostModel> postList = postDAO.findAll();
	for (PostModel post : postList) {
	    post.setCategories(postGroupService.findCategoryInUse(post.getId()));
	    post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
	    post.setImage(mediaDAO.findById(post.getFeature()));
	}
	return postList;
    }

    /**
     * Finds all post items on one page with pagination info
     * 
     * @param page A PageModel containing pagination info
     * @return A List representing all post model on one page
     */
    @Override
    public List<PostModel> findAll(PageModel page) throws SQLException, Exception {
	page = paging.pageRequest(page, getTotalItems());
	List<PostModel> postList = postDAO.findAll(page);
	for (PostModel post : postList) {
	    post.setCategories(postGroupService.findCategoryInUse(post.getId()));
	    post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
	    post.setImage(mediaDAO.findById(post.getFeature()));
	}
	return postList;
    }
    
    /**
     * Gets a list of post instances grouped by a category
     * 
     * @param CatID A Long containing category's id
     * @param page  A PageModel containing pagination info
     * @return A list representing the post instances in category
     * @throws Exception 
     * @throws SQLException 
     */
    @Override
    public List<PostModel> categoryGroup(Long CatID, PageModel page) throws SQLException, Exception {
	page = paging.pageRequest(page, postGroupService.totalPostByCategory(CatID));
	List<PostModel> postList = postDAO.categoryGroup(CatID, page);
	for (PostModel post : postList) {
	    post.setCategories(postGroupService.findCategoryInUse(post.getId()));
	    post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
	    post.setImage(mediaDAO.findById(post.getFeature()));
	}
	return postList;
    }

    /**
     * Finds a post with id matching
     * 
     * @param postId A Long containing post's id
     * @return A PostModel representing the post found
     */
    @Override
    public PostModel findPostById(long postId) throws SQLException, Exception {
	PostModel post = postDAO.findPostById(postId);
	post.setCategories(postGroupService.findCategoryInUse(post.getId()));
	post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
	post.setComments(commentService.findAllInPost(post.getId()));
	post.setImage(mediaDAO.findById(post.getFeature()));
	return post;
    }

    /**
     * Saves a new post to database
     * 
     * @param postModel A PostModel containing the new post
     * @return A Long representing the post's id has saved
     */
    @Override
    public Long save(PostModel postModel) throws SQLException, Exception {
	if (postModel.getImage() != null) {
	    postModel.getImage().setCreatedBy(postModel.getCreatedBy());
	    postModel.setFeature(mediaDAO.save(postModel.getImage()));
	}
	return postDAO.save(postModel);
    }

    /**
     * Gets total post items
     * 
     * @return A Long representing the total post items
     */
    @Override
    public Long getTotalItems() throws SQLException, Exception {
	return postDAO.getTotalItems();
    }

    /**
     * Gets total post items matching search key
     * 
     * @param searchKey A String containing the title searching
     * @return A Long representing the total post items matching search key
     */
    @Override
    public Long getTotalItems(String searchKey) throws SQLException, Exception {
	return postDAO.getTotalItems(searchKey);
    }

    /**
     * Update a post's data
     * 
     * @param postModel A PostModel containing the post's information edited
     */
    @Override
    public void edit(PostModel postModel) throws SQLException, Exception {
	if (postModel.getImage() != null) {
	    postModel.getImage().setCreatedBy(postModel.getCreatedBy());
	    postModel.setFeature(mediaDAO.save(postModel.getImage()));
	}
	postDAO.edit(postModel);

    }

    /**
     * Deletes a post has the id matching
     * 
     * @param id A long containing the post's id
     */
    @Override
    public void delete(long id) throws SQLException, Exception {
	postGroupDAO.delete(id);
	postDAO.delete(id);
    }

    /**
     * Finds all post items have title matching search key display on one page
     * 
     * @param page      A PageModel containing pagination info
     * @param searchKey A String containing title searching
     * @return A List representing all post model matching search key display on one
     *         page
     */
    @Override
    public List<PostModel> search(PageModel page, String searchKey) throws SQLException, Exception {
	page = paging.pageRequest(page, getTotalItems(searchKey));
	List<PostModel> postList = postDAO.search(page, searchKey);
	for (PostModel post : postList) {
	    post.setCategories(postGroupService.findCategoryInUse(post.getId()));
	    post.setAuthor(userDAO.findUserById(post.getCreatedBy()));
	    post.setImage(mediaDAO.findById(post.getFeature()));
	}
	return postList;
    }

    /**
     * Delete all post items
     */
    @Override
    public void deleteAll() throws SQLException, Exception {
	postGroupDAO.deleteAll();
	postDAO.deleteAll();
    }

}
