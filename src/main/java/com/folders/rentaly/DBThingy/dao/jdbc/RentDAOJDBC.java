package com.folders.rentaly.DBThingy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.folders.rentaly.DBThingy.DBSource;
import com.folders.rentaly.DBThingy.dao.RentDAO;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RentDAOJDBC implements RentDAO {

    private DBSource dbsource;
    
    private static final Logger log = LoggerFactory.getLogger(RentDAOJDBC.class);

	public RentDAOJDBC(DBSource dbsource) {
		super();
		this.dbsource = dbsource;
    }
    
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
		Connection con;
        List<Rent> ls = new ArrayList<Rent>();
		try {
			con = dbsource.getConnection();
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
	public List<Rent> findByRealty_OwnerAndEndGreaterThanEqual(User owner, LocalDate end) {
		Connection con;
        List<Rent> ls = new ArrayList<>();
		try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.rent as rent WHERE (SELECT owner_id FROM prova.realty as realty WHERE realty.id = rent.id) = ? AND end >= ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            st.setObject(2, end);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
                Rent rent = createSafeRent(rs);
				rent.setRealty(RealtyDAOJDBC.findById(rs.getInt("realty_id")));
				rent.setHolder(UserDAOJDBC.findById(rs.getInt("holder_id")));

				ls.add(rent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public List<Rent> findByHolderAndEndGreaterThanEqual(User holder, LocalDate end) {
		Connection con;
		Optional<User> useropt = Optional.empty();
		try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.rent WHERE holder_id = ? AND end >= ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, holder.getId());
            st.setObject(2, end);
			ResultSet rs = st.executeQuery();

			List<Rent> ls = new ArrayList<>();
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

}