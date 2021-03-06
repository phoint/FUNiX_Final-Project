/*
 * @(#) PostService.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.common.imp;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import edu.funix.common.ICommentService;
import edu.funix.common.IPageableService;
import edu.funix.common.IPostGroupService;
import edu.funix.common.IPostService;
import edu.funix.dao.IAccountDAO;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.IPostGroupedDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.dao.imp.PostGroupedDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.PageModel;
import edu.funix.model.PostModel;
import edu.funix.model.UserModel;

public class PostService implements IPostService {

    private IPostDAO postDAO;
    private IAccountDAO<UserModel> userDAO;
    private IPostGroupedDAO postGroupDAO;
    private IPostGroupService postGroupService;
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
	    post.setAuthor(userDAO.findBy(post.getCreatedBy()));
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
	    post.setAuthor(userDAO.findBy(post.getCreatedBy()));
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
	    post.setAuthor(userDAO.findBy(post.getCreatedBy()));
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
	post.setAuthor(userDAO.findBy(post.getCreatedBy()));
	post.setComments(commentService.findAllInPost(post.getId()));
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
	Long id = null;
	if (postModel.getTitle() == null || postModel.getTitle().trim().equals("")) {
	    throw new SQLException("Can not leave the title blank");
	} else if (postModel.getPostUrl() == null || postModel.getPostUrl().trim().equals("")) {
	    throw new SQLException("Can not leave the url snippet blank");
	} else if (postModel.getPostStatus() == 1 && postModel.getPublishDate() == null) {
	    throw new SQLException("Please choose a day for publishing.");
	} else {
	    try {
		id = postDAO.save(postModel);
	    } catch (Exception e) {
		if (e.getMessage().contains("UQ__tblPOST__AF49EBA443428087")) {
		    throw new SQLException("The url is existed, please choose another");
		} else if (e.getMessage().contains("UQ__tblPOST__F9522A8EFDB1E844")) {
		    throw new SQLException("The title is existed, please choose another");
		} else {
		    throw new Exception(e);
		}
	    }
	}
	return id;
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
	if (postModel.getTitle() == null || postModel.getTitle().trim().equals("")) {
	    throw new SQLException("Can not leave the title blank");

	} else if (postModel.getPostUrl() == null || postModel.getPostUrl().trim().equals("")) {
	    throw new SQLException("Can not leave the url snippet blank");
	} else if (postModel.getPostStatus() == 1 && postModel.getPublishDate() == null) {
	    throw new SQLException("Please choose a day for publishing.");
	} else {
	    postModel.setModifiedDate(new Date(Calendar.getInstance().getTimeInMillis()));
	    try {
		postDAO.edit(postModel);
	    } catch (Exception e) {
		if (e.getMessage().contains("UQ__tblPOST__AF49EBA443428087")) {
		    throw new SQLException("The url is existed, please choose another");
		} else if (e.getMessage().contains("UQ__tblPOST__F9522A8EFDB1E844")) {
		    throw new SQLException("The title is existed, please choose another");
		} else {
		    throw new Exception(e);
		}
	    }
	}
    }

    /**
     * Deletes a post has the id matching
     * 
     * @param id A long containing the post's id
     */
    @Override
    public void delete(String[] ids) throws SQLException, Exception {
	for (String id : ids) {
	    postGroupDAO.delete(Long.parseLong(id));
	    postDAO.delete(Long.parseLong(id));
	}
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
	    post.setAuthor(userDAO.findBy(post.getCreatedBy()));
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
