/*
 * @(#) AbstractDAO.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.dao.imp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.funix.context.DBConnection;
import edu.funix.context.SQLServerConnection;
import edu.funix.dao.GenericDAO;
import edu.funix.model.mapper.RowMapper;

/**
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 *
 * @param <T> A generic class containing a generic model
 */
public class AbstractDAO<T> implements GenericDAO<T> {
    DBConnection dbObject;

    /** Create a connection to SQL Server */
    public AbstractDAO() {
	dbObject = new SQLServerConnection();
    }

    /**
     * Sends a query to database for selecting any data matching conditions
     * 
     * @param <T>        A generic class containing a generic model
     * @param sql        A String containing a SQL Server query
     * @param rowMapper  An instance containing a mapper with generic class
     * @param parameters None or many instances containing the conditions
     * @return A list representing model's attribute
     * @throws An Exception happen when execute the query
     */
    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) throws SQLException, Exception {
	List<T> items = new ArrayList<>();
	try (Connection conn = dbObject.getConnection()) {
	    try (PreparedStatement stm = conn.prepareStatement(sql)) {
		conn.setAutoCommit(false);
		setParameter(stm, parameters);
		try (ResultSet result = stm.executeQuery()) {
		    while (result.next()) {
			items.add(rowMapper.mapRow(result));
		    }
		}
		conn.commit();
	    } catch (SQLException e) {
		conn.rollback();
		throw new SQLException(e);
	    } finally {
		conn.setAutoCommit(true);
	    }
	}
	return items;
    }

    /**
     * Call a stored procedure in database for getting data
     * 
     * @param <T>        A generic class containing a generic model
     * @param sql        A String containing a query for callable statement
     * @param rowMapper  An instance containing a mapper with generic class
     * @param parameters None or many instances containing the conditions
     * @return A list representing model's attribute
     */
    @Override
    public <T> List<T> call(String sql, RowMapper<T> rowMapper, Object... parameters) throws SQLException, Exception {
	List<T> items = new ArrayList<>();
	try (Connection conn = dbObject.getConnection()) {
	    try (CallableStatement stm = conn.prepareCall(sql)) {
		conn.setAutoCommit(false);
		setParameter(stm, parameters);
		try (ResultSet result = stm.executeQuery()) {
		    while (result.next()) {
			items.add(rowMapper.mapRow(result));
		    }
		}
		conn.commit();
	    } catch (SQLException e) {
		conn.rollback();
		throw new SQLException(e);
	    } finally {
		conn.setAutoCommit(true);
	    }
	}
	return items;
    }

    /**
     * Sets the parameters for preparing sql string
     * 
     * @param stm        A statement containing the sql string
     * @param parameters None or many instance containing the conditions
     */
    private void setParameter(PreparedStatement stm, Object... parameters) throws SQLException {
	for (int i = 0; i < parameters.length; i++) {
	    Object parameter = parameters[i];
	    int index = i + 1;
	    if (parameter instanceof Long) {
		stm.setLong(index, (Long) parameter);
	    } else if (parameter instanceof String) {
		stm.setString(index, (String) parameter);
	    } else if (parameter instanceof Boolean) {
		stm.setBoolean(index, (Boolean) parameter);
	    } else if (parameter instanceof Timestamp) {
		stm.setTimestamp(index, (Timestamp) parameter);
	    } else if (parameter instanceof Integer) {
		stm.setInt(index, (Integer) parameter);
	    } else if (parameter instanceof Date) {
		stm.setDate(index, (Date) parameter);
	    } else if (parameter == null) {
		stm.setNull(index, 0);
	    } else if (parameter instanceof Object[]) {
		stm.setObject(index, parameter, 4);
	    }
	}
    }

    /**
     * Sends a query to database for updating/ deleting any data matching conditions
     * 
     * @param sql        A String containing a SQL Server query
     * @param parameters None or many instances containing the conditions
     * @throws An Exception happen when execute the query
     */
    @Override
    public void update(String sql, Object... parameters) throws SQLException, Exception {
	try (Connection conn = dbObject.getConnection()) {
	    try (PreparedStatement updateStm = conn.prepareStatement(sql)) {
		conn.setAutoCommit(false);
		setParameter(updateStm, parameters);
		updateStm.executeUpdate();
		conn.commit();
	    } catch (Exception ex) {
		conn.rollback();
		throw new SQLException(ex);
	    } finally {
		conn.setAutoCommit(true);
	    }
	}
    }

    /**
     * Sends a query to database for inserting new data
     * 
     * @param sql        A String containing a SQL Server query
     * @param parameters None or many instances containing the conditions
     * @return A Long representing the new id of data inserted
     * @throws An Exception happen when execute the query
     */
    @Override
    public Long insert(String sql, Object... parameters) throws SQLException, Exception {
	Long id = null;
	try (Connection conn = dbObject.getConnection()) {
	    try (PreparedStatement updateStm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		conn.setAutoCommit(false);
		setParameter(updateStm, parameters);
		updateStm.executeUpdate();
		try (ResultSet rs = updateStm.getGeneratedKeys()) {
		    while (rs.next()) {
			id = rs.getLong(1);
		    }
		}
		conn.commit();
	    } catch (SQLException e) {
		conn.rollback();
		throw new SQLException(e);
	    } finally {
		conn.setAutoCommit(true);
	    }
	}
	return id;
    }

    /**
     * Sends a query to database for counting the number of data rows matching
     * conditions
     * 
     * @param sql        A String containing a SQL Server query
     * @param parameters None or many instances containing the conditions
     * @return A Long representing the number of data rows
     * @throws An Exception happen when execute the query
     */
    @Override
    public Long count(String sql, Object... parameters) throws SQLException, Exception {
	Long totalItems = null;
	try (Connection conn = dbObject.getConnection()) {
	    try (PreparedStatement stm = conn.prepareStatement(sql)) {
		conn.setAutoCommit(false);
		setParameter(stm, parameters);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
		    totalItems = rs.getLong(1);
		}
		conn.commit();
		return totalItems;
	    } catch (Exception ex) {
		conn.rollback();
		ex.printStackTrace();
	    } finally {
		conn.setAutoCommit(true);
	    }
	}
	return totalItems;
    }

    @Override
    public boolean checkUpdate(String sql, Object... parameters) throws SQLException, Exception {
	int result;
	try (Connection conn = dbObject.getConnection()) {
	    try (CallableStatement stm = conn.prepareCall(sql)) {
		conn.setAutoCommit(false);
		setParameter(stm, parameters);
		result = stm.executeUpdate();
		conn.commit();
	    } catch (SQLException e) {
		conn.rollback();
		throw new SQLException(e);
	    } finally {
		conn.setAutoCommit(true);
	    }
	}
	return result == 1 ? true : false;
    }

}
