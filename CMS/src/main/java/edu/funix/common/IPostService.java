/*
 * @(#) IPostService.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.common;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.PageModel;
import edu.funix.model.PostModel;

public interface IPostService {

    /**
     * Finds all post items
     * 
     * @return A list representing all post model
     */
    List<PostModel> findAll() throws SQLException, Exception;

    /**
     * Finds all post items on one page with pagination info
     * 
     * @param page A PageModel containing pagination info
     * @return A List representing all post model on one page
     */
    List<PostModel> findAll(PageModel page) throws SQLException, Exception;

    /**
     * Gets a list of post instances grouped by a category
     * 
     * @param CatID A Long containing category's id
     * @param page  A PageModel containing pagination info
     * @return A list representing the post instances in category
     */
    List<PostModel> categoryGroup(Long CatID, PageModel page) throws SQLException, Exception;

    /**
     * Finds all post items have title matching search key display on one page
     * 
     * @param page      A PageModel containing pagination info
     * @param searchKey A String containing title searching
     * @return A List representing all post model matching search key display on one
     *         page
     */
    List<PostModel> search(PageModel page, String searchKey) throws SQLException, Exception;

    /**
     * Finds a post with id matching
     * 
     * @param postId A Long containing post's id
     * @return A PostModel representing the post found
     */
    PostModel findPostById(long postId) throws SQLException, Exception;

    /**
     * Saves a new post to database
     * 
     * @param postModel A PostModel containing the new post
     * @return A Long representing the post's id has saved
     */
    Long save(PostModel postModel) throws SQLException, Exception;

    /**
     * Update a post's data
     * 
     * @param postModel A PostModel containing the post's information edited
     */
    void edit(PostModel postModel) throws SQLException, Exception;

    /**
     * Gets total post items
     * 
     * @return A Long representing the total post items
     */
    Long getTotalItems() throws SQLException, Exception;

    /**
     * Gets total post items matching search key
     * 
     * @param searchKey A String containing the title searching
     * @return A Long representing the total post items matching search key
     */
    Long getTotalItems(String searchKey) throws SQLException, Exception;

    /**
     * Deletes a post has the id matching
     * 
     * @param id A long containing the post's id
     */
    void delete(long id) throws SQLException, Exception;

    /**
     * Delete all post items
     */
    void deleteAll() throws SQLException, Exception;
}
