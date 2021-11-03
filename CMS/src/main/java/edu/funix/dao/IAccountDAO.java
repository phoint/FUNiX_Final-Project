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
 * @param <T>
 */
public interface IAccountDAO<T> extends GenericDAO<T> {

    /**
     * Gets all users exist in database
     * 
     * @return A List representing all users
     * @throws SQLException
     * @throws Exception
     */
    List<T> findAll() throws SQLException, Exception;

    /**
     * Gets user by an existed id
     * 
     * @param id A Long containing user's id
     * @return A UserModel representing the user
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    T findBy(long id) throws SQLException, Exception;

    /**
     * Gets user by an existed email
     * 
     * @param email A String containing user's email
     * @return A UserModel representing the user
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    T findBy(String email) throws SQLException, Exception;

    /**
     * Save a new user account with basic information
     * 
     * @param user A UserModel containing user's information
     * @return A Long representing the new user's id
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    Long save(T user) throws SQLException, Exception;

    /**
     * Update a existed user in database with new information
     * 
     * @param user A UserModel containing user's information
     * @throws SQLException
     * @throws Exception
     * @see UserModel
     */
    void edit(T user) throws SQLException, Exception;

    /**
     * Update the user's status to inactive but do not delete from database
     * 
     * @param id A long containing user's id
     * @throws Exception
     * @throws SQLException
     */
    void delete(long id) throws SQLException, Exception;

    /**
     * Delete a user has id matching from database
     * 
     * @param id A long containing user's id
     * @throws SQLException
     * @throws Exception
     */
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
     * Gets total of users has username matching
     * 
     * @param username A String containing username
     * @return A Long representing the total items
     * @throws Exception
     * @throws SQLException
     */
    Long getTotalItems(String username) throws SQLException, Exception;

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
    T checkLogin(String username, String password) throws SQLException, Exception;

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
    List<T> findAll(PageModel page) throws SQLException, Exception;

    /**
     * Gets all user has username matching search key. The PageModel's attribute
     * determine the offset and limit the total items return to list.
     * 
     * @param page     A PageModel containing pagination information
     * @param username A String containing search key
     * @return A List representing the users matching search key on one page
     * @throws SQLException
     * @throws Exception
     */
    List<T> searchBy(PageModel page, String searchKey) throws SQLException, Exception;

    /**
     * Updates new password for user has id matching
     * 
     * @param userId      A Long containing user's id
     * @param newPassword A String containing new password
     * @throws SQLException
     * @throws Exception
     */
    void changePassword(Long userId, String newPassword) throws SQLException, Exception;

    /**
     * Save the total of failed login attempts for locking account for a time
     * 
     * @param failedAttempts An integer containing the number of failed attempts
     * @param username       A String containing the login username
     * @throws SQLException
     * @throws Exception
     */
    void updateFailedAttempts(int failedAttempts, String username) throws SQLException, Exception;

    /**
     * Update user's locked status when getting the limited number of attempts
     * 
     * @param user A UserModel containing user's attributes
     * @throws SQLException
     * @throws Exception
     */
    void updateLockUser(T user) throws SQLException, Exception;
}
