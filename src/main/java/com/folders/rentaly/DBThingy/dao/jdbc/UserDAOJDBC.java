package com.folders.rentaly.DBThingy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.folders.rentaly.DBThingy.DBSource;
import com.folders.rentaly.DBThingy.dao.UserDAO;
import com.folders.rentaly.model.User;

public class UserDAOJDBC implements UserDAO{

	private DBSource dbsource;
	
	public UserDAOJDBC(DBSource dbsource) {
		super();
		this.dbsource = dbsource;
	}

	@Override
	public Optional<User> findUser(String email) {
		Connection con;
		Optional<User> useropt = Optional.empty();
		try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.user WHERE email = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				useropt = Optional.of(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return useropt;
	}

	@Override
	public boolean registerUser(String email, String password) {
		Connection con = null;
		try {
			con = dbsource.getConnection();
			String query = "INSERT INTO prova.user VALUES (?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, email);
			st.setString(2, password);
			st.executeUpdate();
			con.commit();
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
