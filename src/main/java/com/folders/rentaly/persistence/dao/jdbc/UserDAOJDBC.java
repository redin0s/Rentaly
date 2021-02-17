package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("userDAO")
public class UserDAOJDBC extends JDBC implements UserDAO {

    private static final Logger log = LoggerFactory.getLogger(RealtyDAOJDBC.class);

	private User createUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setActive(rs.getBoolean("active"));
		return user;
	}

	@Override
	public Optional<User> findUser(String email) {
		Optional<User> useropt = Optional.empty();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.user WHERE email = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				useropt = Optional.of(createUser(rs));
			}
		} catch (SQLException e) {
			log.error("error in find user by email", e);
		}

		return useropt;
	}

	@Override
	public void save(User user) {
		Connection con = null;
		PreparedStatement st = null;
		try {
			String query;
			con = dbSource.getConnection();
			if (user.getId() == null) {
				query = "INSERT INTO prova.user (email,password,active) VALUES (?,?,?)";
				st = con.prepareStatement(query);
				st.setBoolean(3, false);
			}
			else {
				query = "UPDATE prova.user SET email=?, password=?, active=?  WHERE id = ?";
				st = con.prepareStatement(query);
				st.setBoolean(3, user.getActive());
				st.setInt(4, user.getId());
			}
			st.setString(1, user.getEmail());
			st.setString(2, user.getPassword());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in insert/update user", e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			}
			catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
	}

	@Override
	public Optional<User> get(Integer id) {
		
		Optional<User> useropt = Optional.empty();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.user WHERE id = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				useropt = Optional.of(createUser(rs));
			}
		} catch (SQLException e) {
			log.error("error in find user by id", e);
		}

		return useropt;
	}

	@Override
	public void delete(User t) {
		try {
			Connection con = dbSource.getConnection();
			String query = "DELETE FROM prova.user WHERE id = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in delete user", e);
		}
	}
}
