package com.bloombloom.jdbc.connect;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectJDBC {
	static Connection con = null;

	static public Connection requestConnection() throws ClassNotFoundException, SQLException, NamingException {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource dataSource = (DataSource) envContext.lookup("jdbc/bank");
		con = dataSource.getConnection();
		return con;
	}

}
