/*
 * @(#) IPostDAO.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.dao;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.PageModel;
import edu.funix.model.PostModel;

/**
 * The IPostDAO interface provides methods to interact with tblPOST in database
 * and/or getting data back.
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public interface IPostDAO extends GenericDAO<PostModel> {

    /**
     * Gets a list of post instances matching all data rows in table
     * 
     * @return A list representing the post instances
     */
    List<PostModel> findAll() throws SQLException, Exception;

    /**
     * Gets a list of post instances matching a specified range of data in table
     * 
     * @param page An instance of PageModel containing the paging's attribute
     * @return A list representing the post instances
     */
    List<PostModel> findAll(PageModel page) throws SQLException, Exception;

    /**
     * Gets a list of post instances has title matching search key
     * 
     * @param page      An instance of PageModel containing the paging's attribute
     * @param searchKey A String containing the title's keyword for searching
     * @return A list representing the post instances
     */
    List<PostModel> search(PageModel page, String searchKey) throws SQLException, Exception;

    /**
     * Gets a post instance had id matching the id in row table
     * 
     * @param postId A long containing the number of post's id
     * @return An instance representing the post
     */
    PostModel findPostById(long postId) throws SQLException, Exception;

    /**
     * Insert a new row to post table
     * 
     * @param postModel An instance containing the post's attribute
     * @return A Long containing the id number of new data inserted
     */
    Long save(PostModel postModel) throws SQLException, Exception;

    /**
     * Update an existing row from post table
     * 
     * @param postModel An instance containing the post's attribute edited
     */
    void edit(PostModel postModel) throws SQLException, Exception;

    /**
     * Gets the total of post in post table
     * 
     * @return A Long representing the number of post
     */
    Long getTotalItems() throws SQLException, Exception;

    /**
     * Gets the total of post in post table have title matching search key
     * 
     * @param searchKey A String containing keyword searching
     * @return A Long representing the total of post meet the conditions
     */
    Long getTotalItems(String searchKey) throws SQLException, Exception;

    /**
     * Delete an existing row from post table has an id matched
     * 
     * @param id A long containing the post's id
     */
    void delete(long id) throws SQLException, Exception;

    /**
     * Delete an existing row from post table has an id matched
     * 
     * @param ids A String array containing the post's ids
     */
    void delete(Object[] ids) throws SQLException, Exception;

    /**
     * Delete all items from post table
     */
    void deleteAll() throws SQLException, Exception;

}
