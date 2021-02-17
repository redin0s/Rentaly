package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.folders.rentaly.persistence.dao.RentDAO;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("rentDAO")
public class RentDAOJDBC extends JDBC implements RentDAO {

	private static final Logger log = LoggerFactory.getLogger(RentDAOJDBC.class);

	private User createHolder(ResultSet rs) throws SQLException {
		User holder = new User();
		holder.setId(rs.getInt("holder_id"));
		holder.setEmail(rs.getString("email"));
		holder.setPassword(rs.getString("password"));
		return holder;
	}

	private Rent createSafeRent(ResultSet rs) throws SQLException {
		Rent rent = new Rent();
		rent.setId(rs.getInt("id"));
		rent.setCost(rs.getInt("cost"));
		rent.setStart(rs.getObject("start_date", LocalDate.class));
		rent.setEnd(rs.getObject("end_date", LocalDate.class));
		return rent;
	}

	@Override
	public List<Rent> findByRealty(Realty realty) {
		List<Rent> ls = new ArrayList<Rent>();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.rent WHERE realty_id = ? JOIN prova.user ON holder_id = id";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, realty.getId());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Rent rent = createSafeRent(rs);
				rent.setRealty(realty);
				rent.setHolder(createHolder(rs));
				ls.add(rent);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}

		return ls;
	}

	@Override
	public List<Rent> findByRealty_Owner(User owner) {
		List<Rent> ls = new ArrayList<>();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.rent as rent WHERE (SELECT owner_id FROM prova.realty as realty WHERE realty.id = rent.id) = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rent rent = createSafeRent(rs);

				ls.add(rent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public List<Rent> findByRealty_OwnerAndEndGreaterThanEqual(User owner, LocalDate end) {
		List<Rent> ls = new ArrayList<>();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.rent as rent WHERE (SELECT owner_id FROM prova.realty as realty WHERE realty.id = rent.id) = ? AND end >= ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
			st.setObject(2, end);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rent rent = createSafeRent(rs);
				// rent.setRealty(RealtyDAOJDBC.findById(rs.getInt("realty_id")));
				// rent.setHolder(UserDAOJDBC.findById(rs.getInt("holder_id")));

				ls.add(rent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public List<Rent> findByHolder(User holder) {
		List<Rent> ls = new ArrayList<>();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.rent WHERE holder_id = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, holder.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rent rent = createSafeRent(rs);
				Realty r = new Realty();
				r.setId(rs.getInt("realty_id"));
				rent.setRealty(r);
				rent.setHolder(holder);

				ls.add(rent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public List<Rent> findByHolderAndEndGreaterThanEqual(User holder, LocalDate end) {
		List<Rent> ls = new ArrayList<>();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.rent WHERE holder_id = ? AND end >= ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, holder.getId());
			st.setObject(2, end);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rent rent = createSafeRent(rs);
				Realty r = new Realty();
				r.setId(rs.getInt("realty_id"));
				rent.setRealty(r);
				rent.setHolder(holder);

				ls.add(rent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public Optional<Rent> get(Integer id) {
		Optional<Rent> rent = Optional.empty();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.rent WHERE id ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				rent = Optional.of(createSafeRent(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rent;
	}

	@Override
	public void save(Rent t) {
		try {
			Connection con = dbSource.getConnection();
			PreparedStatement st;
			String query;
			if (t.getId() != null) {
				query = "UPDATE prova.rent SET cost=?, realty_id=?, start_date=?, end_date=?, holder_id=?, active=? WHERE id = ?";
				st = con.prepareStatement(query);
				st.setInt(7, t.getId());
			} else {
				query = "INSERT INTO prova.rent VALUES(?, ?, ?, ?, ?, ?)";
				st = con.prepareStatement(query);
			}
			st.setInt(1, t.getCost());
			st.setInt(2, t.getRealty().getId());
			st.setDate(3, Date.valueOf(t.getStart()));
			st.setDate(4, Date.valueOf(t.getEnd()));
			st.setInt(5, t.getHolder().getId());
			st.setBoolean(6, t.getActive());
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Rent t) {
		try {
			Connection con = dbSource.getConnection();
			String query = "DELETE FROM prova.rent WHERE id = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}