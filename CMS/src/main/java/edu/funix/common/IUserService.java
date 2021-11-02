/*
 * @(#) IUserService.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.common;

import java.sql.SQLException;
import java.util.List;

import edu.funix.domain.ChangePasswordForm;
import edu.funix.model.PageModel;
import edu.funix.model.UserModel;

/**
 * The User Service handle the business logic for user entity
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public interface IUserService {

    /**
     * Gets all users exist in database
     * 
     * @return A List representing all users
     * @throws SQLException
     * @throws Exception
     */
    List<UserModel> findAll() throws SQLException, Exception;

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
     * Finds all users have username matching search key display on one page
     * 
     * @param page      A PageModel containing pagination information
     * @param searchKey A String containing search key
     * @return A List representing all users matching search key on one page
     * @throws Exception
     * @throws SQLException
     */
    List<UserModel> search(PageModel page, String searchKey) throws SQLException, Exception;

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
    UserModel findBy(String email) throws SQLException, Exception;

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
     * Update the user's status to inactive but do not delete from database
     * 
     * @param id A long containing user's id
     * @throws Exception
     * @throws SQLException
     * @throws NumberFormatException
     */
    void delete(String[] ids) throws NumberFormatException, SQLException, Exception;

    /**
     * Delete a user has id matching
     * 
     * @param id A long containing user's id
     * @throws Exception
     * @throws SQLException
     * @throws NumberFormatException
     */
    void permanentDelete(String[] ids) throws SQLException, Exception;

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
     * Updates new password for user has id matching
     * 
     * @param userId      A Long containing user's id
     * @param newPassword A String containing new password
     * @throws SQLException
     * @throws Exception
     */
    void changePassword(ChangePasswordForm newPwd) throws SQLException, Exception;

    /**
     * Increase and update the number of login failed attempts
     * 
     * @param user A UserModel containing user's attributes
     * @throws SQLException
     * @throws Exception
     */
    void increaseFailedAttempts(UserModel user) throws SQLException, Exception;

    /**
     * Reset the number of login failed attempts after login success
     * 
     * @param username A String containing the login username
     * @throws SQLException
     * @throws Exception
     */
    void resetFailedAttempts(String username) throws SQLException, Exception;

    /**
     * Locks the user's login when getting the limited number of attempts
     * 
     * @param user A UserModel conataining user's attributes
     * @throws SQLException
     * @throws Exception
     */
    void lockUser(UserModel user) throws SQLException, Exception;

    /**
     * Unlock user's account after locking for a limited time
     * 
     * @param user A UserModel containing user's attributes
     * @return A boolean representing the account's locking status
     * @throws SQLException
     * @throws Exception
     */
    boolean unlockWhenTimeExpired(UserModel user) throws SQLException, Exception;

    UserModel LoginAttempt(UserModel user) throws SQLException, Exception;
}
