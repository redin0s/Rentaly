package com.folders.rentaly.persistence;

import java.io.PrintWriter;
import java.util.Properties;

import javax.sql.DataSource;

import com.folders.rentaly.persistence.dao.jdbc.UserDAOJDBC;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBManager {
	private static DBManager dbmanager = null;
	private static DataSource dbsource = null;

	public DBManager() {
		try {
			//TODO cambiare sta cosa con file di config non dentro il codice

			Properties props = new Properties();
			props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
			props.setProperty("dataSource.user", "postgres");
			props.setProperty("dataSource.password", "segretodistato");
			props.setProperty("dataSource.databaseName", "rentaly");
			props.setProperty("dataSource.portNumber", "5432");
			props.put("dataSource.logWriter", new PrintWriter(System.out));

			HikariConfig config = new HikariConfig(props);

			dbsource = new HikariDataSource(config);
		} catch (Exception e) {
			System.err.println("Could not connect to DB " + e.getMessage());
			e.printStackTrace();
		}
	}

	public DataSource getSource() {
		return dbsource;
	}

	public static DBManager getInstance() {
		if (dbmanager == null) {
			dbmanager = new DBManager();
		}
		return dbmanager;
	}

	private UserDAOJDBC userDAO = null;

	public UserDAOJDBC getUserDAOJDBC() {
		if (userDAO == null)
			userDAO = new UserDAOJDBC(dbsource);

		return userDAO;
	}
}
