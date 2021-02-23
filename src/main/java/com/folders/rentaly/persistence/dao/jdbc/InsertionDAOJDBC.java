package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.folders.rentaly.persistence.dao.InsertionDAO;

import com.folders.rentaly.model.Insertion;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("insertionDAO")
public class InsertionDAOJDBC extends JDBC implements InsertionDAO {

	public InsertionDAOJDBC() {
		super("insertion");
	}

	private Insertion createInsertion(ResultSet rs) throws SQLException {
		Insertion insertion = new Insertion();
		insertion.setId(rs.getInt("id"));
		insertion.setCost(rs.getInt("cost"));
		insertion.setDescription(rs.getString("description"));
		insertion.setIs_visible(rs.getBoolean("is_visible"));
		return insertion;
	}

	@Override
	public Optional<Insertion> get(Integer id) {
		Optional<Insertion> insertionopt = Optional.empty();
		String query = "SELECT * FROM insertion WHERE id = ?";
		try (
			Connection con = dbSource.getConnection();
			PreparedStatement st = con.prepareStatement(query);
		)
		{
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				insertionopt = Optional.of(createInsertion(rs));
			}
		} catch (SQLException e) {
			log.error("error in find insertion by id", e);
		}

		return insertionopt;
	}

	@Override
	public void save(Insertion t) {
		String query = "INSERT INTO insertion (description,cost,is_visible,id) VALUES (?,?,?,?) " +
		"ON CONFLICT (id) DO UPDATE SET description=EXCLUDED.description, cost=EXCLUDED.cost, is_visible=EXCLUDED.is_visible";

		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			if(t.getId() == null)
				t.setId(getNextId());
			st.setString(1, t.getDescription());
			st.setFloat(2, t.getCost());
			st.setBoolean(3, t.getIs_visible());
			st.setInt(4, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in insert/update user", e);
		} 
	}

	@Override
	public void delete(Insertion t) {
		String query = "DELETE FROM insertion WHERE id = ?";
		try (
			Connection con = dbSource.getConnection();
			PreparedStatement st = con.prepareStatement(query);
		)
		{
			st.setInt(1, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in delete insertion", e);
		}
	}

}
