package com.folders.rentaly.persistence;

import com.folders.rentaly.persistence.dao.jdbc.UserDAOJDBC;

public class DBManager {
	private static DBManager dbmanager = null;
	private static DBSource dbsource = null;

	public DBManager() {
		try {
			//TODO cambiare sta cosa con file di config non dentro il codice
			dbsource = new DBSource("jdbc:postgresql://localhost:5432/rentaly", "postgres", "segretodistato");
		} catch (Exception e) {
			System.err.println("Could not connect to DB" + e.getMessage());
		}
	}

	public DBSource getSource() {
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
