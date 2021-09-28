package edu.funix.context;

import java.sql.Connection;

public interface DBConnection {
	Connection getConnection() throws Exception;
}
