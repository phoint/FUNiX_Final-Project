package edu.funix.dao.imp;

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

public class AbstractDAO<T> implements GenericDAO<T> {
	DBConnection dbObject;
	
	public AbstractDAO() {
		dbObject = new SQLServerConnection();
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> items = new ArrayList<>();
		try (Connection conn = dbObject.getConnection();
			PreparedStatement stm = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);
			setParameter(stm,parameters);
			ResultSet result = stm.executeQuery();
			conn.commit();
			while (result.next()) {
				items.add(rowMapper.mapRow(result));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return items;
	}

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
			} else if (parameter instanceof Date ) {
				stm.setDate(index, (Date) parameter);
			} else if (parameter == null) {
				stm.setNString(index, null);
			}
		}
	}

	@Override
	public void update(String sql, Object... parameters) {
		try (Connection conn = dbObject.getConnection()) {
			try (PreparedStatement updateStm = conn.prepareStatement(sql)) {
				conn.setAutoCommit(false);
				setParameter(updateStm, parameters);
				updateStm.executeUpdate();
				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				ex.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Long insert(String sql, Object... parameters) {
		Long id = null;
		try (Connection conn = dbObject.getConnection()) {
			try (PreparedStatement updateStm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				conn.setAutoCommit(false);
				setParameter(updateStm, parameters);
				updateStm.executeUpdate();
				ResultSet rs = updateStm.getGeneratedKeys();
				while (rs.next()) {
					id = rs.getLong(1);
				}
				conn.commit();
				return id;
			} catch (Exception ex) {
				conn.rollback();
				ex.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long count(String sql, Object... parameters) {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
