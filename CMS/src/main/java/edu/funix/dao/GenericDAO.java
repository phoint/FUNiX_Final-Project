package edu.funix.dao;

import java.util.List;

import edu.funix.model.mapper.RowMapper;

public interface GenericDAO<T> {
	<T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) throws Exception;
	void update(String sql, Object... parameters) throws Exception;
	Long insert(String sql, Object... parameters) throws Exception;
	Long count(String sql, Object... parameters) throws Exception;
}
