package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.DBSource;
import com.folders.rentaly.persistence.dao.UserDAO;

public class UserDAOJDBC implements UserDAO{

	private DBSource dbsource;
	
	public UserDAOJDBC(DBSource dbsource) {
		super();
		this.dbsource = dbsource;
	}

	@Override
	public List<User> findAll() {
		Connection con;
		List<User> users = new LinkedList<User>();
		try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.user";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			User user;
			while(rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				users.add(user);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
}
