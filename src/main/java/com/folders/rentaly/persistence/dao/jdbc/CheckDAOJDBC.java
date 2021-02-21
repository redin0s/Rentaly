package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.folders.rentaly.persistence.dao.CheckDAO;
import com.folders.rentaly.persistence.dao.RentDAO;
import com.folders.rentaly.model.Check;
import com.folders.rentaly.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("checkDAO")
public class CheckDAOJDBC extends JDBC implements CheckDAO {

	public CheckDAOJDBC() {
		super("rentaly.check");
	}

	@Autowired
	private RentDAO rentDAO;

	private Check createSafeCheck(ResultSet rs) throws SQLException {
		Check check = new Check();
		check.setId(rs.getInt("id"));
		check.setCost(rs.getFloat("cost"));
		check.setCheck_type(rs.getString("check_type"));
		check.setExpire(rs.getObject("expire", LocalDate.class));
		check.setPaid(rs.getBoolean("paid"));
		check.setRent(rentDAO.get(rs.getInt("rent_id")).get());
		return check;
	}

	@Override
	public List<Check> findByRent_Realty_OwnerAndPaid(User owner, Boolean isPaid) {
		List<Check> ls = new ArrayList<Check>();
		String query = "SELECT * FROM \"check\" JOIN rent ON \"check\".rent_id = rent.id JOIN realty ON rent.realty_id = realty.id WHERE realty.owner_id = ? AND \"check\".paid = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, owner.getId());
            st.setBoolean(2, isPaid);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Check ch = createSafeCheck(rs);
				ls.add(ch);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
		return ls;
	}

	@Override
	public List<Check> findByRent_HolderAndPaid(User holder, Boolean isPaid) {
		List<Check> ls = new ArrayList<Check>();
		String query = "SELECT * FROM \"check\" JOIN rent ON \"check\".rent_id = rent.id  JOIN realty ON rent.realty_id = realty.id WHERE rent.holder_id = ? AND \"check\".paid = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, holder.getId());
            st.setBoolean(2, isPaid);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Check ch = createSafeCheck(rs);
				ls.add(ch);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}

		return ls;
	}

	@Override
	public void save(Check t) {
		String query = "INSERT INTO \"check\" (check_type, cost, expire, rent_id, paid, id) VALUES(?, ?, ?, ?, ?, ?) " +
		"ON CONFLICT (id) DO UPDATE SET check_type=EXCLUDED.check_type, cost=EXCLUDED.cost, expire=EXCLUDED.expire, rent_id=EXCLUDED.rent_id, paid=EXCLUDED.paid";

		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			if (t.getId() == null) 
				t.setId(getNextId());
			
			st.setInt(6, t.getId());
			st.setString(1, t.getCheck_type());
			st.setFloat(2, t.getCost());
			st.setObject(3, t.getExpire());
			st.setInt(4, t.getRent().getId());
			st.setBoolean(5, t.getPaid());
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Check t) {
        String query = "DELETE FROM \"check\" WHERE id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
            log.info(e.getMessage());
            log.trace(e.getStackTrace().toString());
		}
	}

	@Override
	public Optional<Check> get(Integer id) {
		Optional<Check> check = Optional.empty();
		String query = "SELECT * FROM \"check\" WHERE id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				check = Optional.of(createSafeCheck(rs));
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
		return check;
	}

	@Override
	public List<Check> findByOwnerAndExpireGreaterThanEqual(User owner, LocalDate date) {
		List<Check> ls = new ArrayList<Check>();
        String query = "SELECT * FROM \"check\" JOIN rent ON \"check\".rent_id = rent.id JOIN realty ON rent.realty_id = realty.id WHERE realty.owner_id=? AND expire >= ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, owner.getId());
			st.setObject(2, date);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Check ch = createSafeCheck(rs);
				ls.add(ch);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
		return ls;
	}

	@Override
	public List<Check> findByHolderAndExpireGreaterThanEqual(User holder, LocalDate date) {
		List<Check> ls = new ArrayList<Check>();
        String query = "SELECT COUNT(*) FROM \"check\" JOIN rent ON \"check\".rent_id = rent.id WHERE rent.holder_id=? and expire >= ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, holder.getId());
			st.setObject(2, date);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Check ch = createSafeCheck(rs);
				ls.add(ch);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
		return ls;
	}

	@Override
	public Integer countByOwnerAndExpireGreaterThanEqual(User owner, LocalDate date) {
		Integer count = 0;
        String query = "SELECT COUNT(*) FROM \"check\" JOIN rent ON \"check\".rent_id = rent.id JOIN realty ON rent.realty_id = realty.id WHERE realty.owner_id=? AND expire >= ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, owner.getId());
			st.setObject(2, date);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}

		return count;
	}

	@Override
	public Integer countByHolderAndExpireGreaterThanEqual(User holder, LocalDate date) {
		Integer count = 0;
        String query = "SELECT COUNT(*) FROM \"check\" JOIN rent ON \"check\".rent_id = rent.id WHERE rent.holder_id=? and expire >= ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, holder.getId());
			st.setObject(2, date);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}

		return count;
	}
}
