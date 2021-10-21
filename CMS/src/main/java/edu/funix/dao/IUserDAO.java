/*
 * @(#) IUserDAO.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.dao;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.PageModel;
import edu.funix.model.UserModel;

/**
 * The UserDAO interface provide CRUD methods to interact with tblUSER in
 * database
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public interface IUserDAO extends GenericDAO<UserModel> {

    /**
     * Gets all users exist in database
     * 
     * @return A List representing all users
     * @throws SQLException
     * @throws Exception
     */
    List<UserModel> findAll() throws SQLException, Exception;

    /**
     * Gets user by an existed id
     * 
     * @param id A Long containing user's id
     * @return A UserModel representing the user
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    UserModel findUserById(long id) throws SQLException, Exception;

    /**
     * Gets user by an existed email
     * 
     * @param email A String containing user's email
     * @return A UserModel representing the user
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    UserModel findByEmail(String email) throws SQLException, Exception;

    /**
     * Save a new user account with basic information
     * 
     * @param user A UserModel containing user's information
     * @return A Long representing the new user's id
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    Long save(UserModel user) throws SQLException, Exception;

    /**
     * Update a existed user in database with new information
     * 
     * @param user A UserModel containing user's information
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    void edit(UserModel user) throws SQLException, Exception;

    /**
     * Delete a user has id matching
     * 
     * @param id A long containing user's id
     */
    void delete(long id);

    // TODO: Separate soft delete with hard delete user's account
    void permanentDelete(long id) throws SQLException, Exception;

    /**
     * Gets the total of users in database
     * 
     * @return A Long representing the total of users
     * @throws SQLException
     * @throws Exception
     */
    Long getTotalItems() throws SQLException, Exception;

    /**
     * Checks existed user's account for logging in system
     * 
     * @param username A String containing the account's username
     * @param password A String containing the account's password
     * @return A UserModel representing the user's attribute
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    UserModel checkLogin(String username, String password) throws SQLException, Exception;

    /**
     * Gets all the users will be displayed on one specific page. The PageModel's
     * attribute containing the offset and the total of rows will be gotten from
     * database.
     * 
     * @param page A PageModel containing information for pagination
     * @return A List representing the users on specific page
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     * @see PageModel
     */
    List<UserModel> findAll(PageModel page) throws SQLException, Exception;

    /**
     * Updates new password for user has id matching
     * 
     * @param userId      A Long containing user's id
     * @param newPassword A String containing new password
     * @throws SQLException
     * @throws Exception
     */
    void changePassword(Long userId, String newPassword) throws SQLException, Exception;
}
