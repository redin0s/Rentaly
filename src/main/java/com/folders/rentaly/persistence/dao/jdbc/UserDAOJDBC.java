package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.UserDAO;

public class UserDAOJDBC implements UserDAO{

	private DataSource dbsource;
	private int numOfUsers;
	
	public UserDAOJDBC(DataSource dbsource) {
		super();
		this.dbsource = dbsource;
		this.numOfUsers = getNOfUsers();
	}

	private int getNOfUsers() {
		try {
			Connection con = dbsource.getConnection();
			String query = "SELECT COUNT(*) as num FROM prova.user";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				return rs.getInt("num");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int getNumOfUsers() {
		return numOfUsers;
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
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				users.add(user);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public User findUser(String username) {
		Connection con;
		try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.user WHERE username = ?";
			PreparedStatement st = con.prepareStatement(query);
			//Statement st = con.createStatement();
			//ResultSet rs = st.executeQuery(query);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			User user;
			while(rs.next()) {
				user = new User();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean registerUser(String username, String password) {
		Connection con = null;
		try {
			con = dbsource.getConnection();
			String query = "INSERT INTO prova.user VALUES (?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			//Statement st = con.createStatement();
			//ResultSet rs = st.executeQuery(query);
			st.setInt(1, numOfUsers+1);
			st.setString(2, username);
			st.setString(3, password);
			st.executeUpdate();
			con.commit();
			numOfUsers++;
		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
		        try {
		          System.err.print("Transaction is being rolled back");
		          con.rollback();
		        } catch (SQLException excep) {
		        	System.err.print(excep);
		        }
		      }
		}
		return false;
	}	
	
}
