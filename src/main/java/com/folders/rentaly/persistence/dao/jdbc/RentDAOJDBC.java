package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.persistence.dao.RentDAO;
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("rentDAO")
public class RentDAOJDBC extends JDBC implements RentDAO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RealtyDAO realtyDAO;

	public RentDAOJDBC() {
		super("rent");
	}

	private Rent createSafeRent(ResultSet rs) throws SQLException {
		Rent rent = new Rent();
		rent.setId(rs.getInt("id"));
		rent.setCost(rs.getInt("cost"));
		rent.setStart(rs.getObject("start_date", LocalDate.class));
		rent.setEnd(rs.getObject("end_date", LocalDate.class));
		rent.setActive(rs.getBoolean("active"));
		Integer id = rs.getInt("holder_id");
		if (!rs.wasNull()) {
			Optional<User> user = userDAO.get(id);
			if (user.isPresent()) {
				rent.setHolder(user.get());
			}
		}
		rent.setRealty(realtyDAO.get(rs.getInt("realty_id")).get());
		return rent;
	}

	@Override
	public List<Rent> findByRealty(Realty realty) {
		List<Rent> ls = new ArrayList<Rent>();
		String query = "SELECT * FROM rent JOIN user ON rent.holder_id = user.id WHERE rent.realty_id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, realty.getId());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Rent rent = createSafeRent(rs);
				rent.setRealty(realty);
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
		String query = "SELECT * FROM rent as rent WHERE (SELECT owner_id FROM realty as realty WHERE realty.id = rent.id) = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
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
		String query = "SELECT * FROM rent JOIN realty ON rent.realty_id = realty.id WHERE owner_id = ? AND end_date >= ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, owner.getId());
			st.setObject(2, end);
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
	public List<Rent> findByHolder(User holder) {
		List<Rent> ls = new ArrayList<>();
		String query = "SELECT * FROM rent WHERE holder_id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, holder.getId());
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
	public List<Rent> findByHolderAndEndGreaterThanEqual(User holder, LocalDate end) {
		List<Rent> ls = new ArrayList<>();
		String query = "SELECT * FROM rent WHERE holder_id = ? AND end_date >= ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, holder.getId());
			st.setObject(2, end);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rent rent = createSafeRent(rs);
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
		String query = "SELECT * FROM rent WHERE id=?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
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
		String query = "INSERT INTO rent (cost, realty_id, start_date, end_date, holder_id, active, id) VALUES(?, ?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO UPDATE SET "+
						"cost=EXCLUDED.cost, realty_id=EXCLUDED.realty_id, start_date=EXCLUDED.start_date, end_date=EXCLUDED.end_date, holder_id=EXCLUDED.holder_id, "+
						"active=EXCLUDED.active";	
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			if (t.getId() == null) {
				t.setId(getNextId());
				t.setActive(false);
			}
			if(t.getHolder() == null) {
				st.setNull(5, Types.INTEGER);
			}
			else {
				st.setInt(5, t.getHolder().getId());
			}
			st.setInt(1, t.getCost());
			st.setInt(2, t.getRealty().getId());
			st.setObject(3, t.getStart());
			st.setObject(4, t.getEnd());
			st.setBoolean(6, t.getActive());
			st.setInt(7, t.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Rent t) {
		String query = "DELETE FROM rent WHERE id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer countByRealty_OwnerAndEndGreaterThanEqual(User owner, LocalDate end) {
		Integer count = 0;
		String query = "SELECT COUNT(*) FROM rent JOIN realty ON rent.realty_id = realty.id WHERE owner_id = ? AND end_date >= ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, owner.getId());
			st.setObject(2, end);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}