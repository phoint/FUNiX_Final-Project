package edu.funix.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class SQLServerConnection implements DBConnection {

    ResourceBundle dbBunble = ResourceBundle.getBundle("db");

    public Connection getConnection() throws Exception {
	String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName;
	if (instance == null || instance.trim().isEmpty()) {
	    url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName
		    + ";sendTimeAsDatetime = true";
	}
	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	return DriverManager.getConnection(url, userID, password);
    }

    private final String serverName = dbBunble.getString("serverName");
    private final String portNumber = dbBunble.getString("portNumber");
    private final String dbName = dbBunble.getString("dbName");
    private final String instance = dbBunble.getString("instance");
    private final String userID = dbBunble.getString("userID");
    private final String password = dbBunble.getString("password");
}
