package com.folders.rentaly.DBThingy;

import com.folders.rentaly.DBThingy.dao.UserDAO;
import com.folders.rentaly.DBThingy.dao.jdbc.UserDAOJDBC;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("dbManager")
public class DBManager {
	private DBSource dbsource;

	public DBManager(
	@Value("${datasource.url}") final String url,
	@Value("${datasource.username}") final String username,
	@Value("${datasource.password}") final String password) {
		try {
			dbsource = new DBSource(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private UserDAO userDAO = null;
	
	public UserDAO getUserDAOJDBC(){
		if (userDAO == null)
			userDAO = new UserDAOJDBC(dbsource);
		
		return userDAO;
	}

}
