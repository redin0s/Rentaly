package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.model.User;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("userDAO")
public class UserDAOJDBC extends JDBC implements UserDAO {

	public UserDAOJDBC() {
		super("rentaly.user");
	}

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
		String query = "SELECT * FROM rentaly.user WHERE email = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {		
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
		String query = "INSERT INTO rentaly.user (email,password,active,id) VALUES (?,?,?,?)" +
		               "ON CONFLICT (id) DO UPDATE SET email=EXCLUDED.email, password=EXCLUDED.password, active=EXCLUDED.active";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {		
			if (user.getId() == null) {
				user.setActive(false);
				user.setId(getNextId());
			}
			st.setString(1, user.getEmail());
			st.setString(2, user.getPassword());
			st.setBoolean(3, user.getActive());
			st.setInt(4, user.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in insert/update user", e);
		}
	}

	@Override
	public Optional<User> get(Integer id) {
		Optional<User> useropt = Optional.empty();
		String query = "SELECT * FROM rentaly.user WHERE id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {		
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				useropt = Optional.of(createUser(rs));
			}
		} catch (Exception e) {
			log.error("error in find user by id", e);
		}

		return useropt;
	}

	@Override
	public void delete(User t) {
		String query = "DELETE FROM rentaly.user WHERE id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {		
			st.setInt(1, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in delete user", e);
		}
	}
}
