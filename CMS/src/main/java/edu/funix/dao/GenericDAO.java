/*
 * @(#) GenericDAO.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.dao;

import java.sql.SQLException;
import java.util.List;

import edu.funix.model.mapper.RowMapper;

/**
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 *
 * @param <T> A generic class containing a generic model
 */
public interface GenericDAO<T> {
    /**
     * Sends a query to database for selecting any data matching conditions
     * 
     * @param <T>        A generic class containing a generic model
     * @param sql        A String containing a SQL Server query
     * @param rowMapper  An instance containing a mapper with generic class
     * @param parameters None or many instances containing the conditions
     * @return A list representing model's attribute
     */
    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) throws Exception;

    /**
     * Sends a query to database for updating/ deleting any data matching conditions
     * 
     * @param sql        A String containing a SQL Server query
     * @param parameters None or many instances containing the conditions
     */
    void update(String sql, Object... parameters) throws Exception;

    /**
     * Sends a query to database for inserting new data
     * 
     * @param sql        A String containing a SQL Server query
     * @param parameters None or many instances containing the conditions
     * @return A Long representing the new id of data inserted
     */
    Long insert(String sql, Object... parameters) throws Exception;

    /**
     * Sends a query to database for counting the number of data rows matching
     * conditions
     * 
     * @param sql        A String containing a SQL Server query
     * @param parameters None or many instances containing the conditions
     * @return A Long representing the number of data rows
     */
    Long count(String sql, Object... parameters) throws Exception;

    /**
     * Call a stored procedure in database for getting data
     * 
     * @param <T>        A generic class containing a generic model
     * @param sql        A String containing a query for callable statement
     * @param rowMapper  An instance containing a mapper with generic class
     * @param parameters None or many instances containing the conditions
     * @return A list representing model's attribute
     */
    <T> List<T> call(String sql, RowMapper<T> rowMapper, Object... parameters) throws SQLException, Exception;
    
    boolean checkUpdate(String sql, Object... parameters) throws SQLException, Exception;
}
