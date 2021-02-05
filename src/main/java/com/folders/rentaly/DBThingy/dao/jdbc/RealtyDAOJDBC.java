package com.folders.rentaly.DBThingy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.folders.rentaly.DBThingy.DBSource;
import com.folders.rentaly.DBThingy.dao.RealtyDAO;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealtyDAOJDBC implements RealtyDAO {

    private DBSource dbsource;
    
    private static final Logger log = LoggerFactory.getLogger(RealtyDAOJDBC.class);

	public RealtyDAOJDBC(DBSource dbsource) {
		super();
		this.dbsource = dbsource;
    }

    private Realty createSafeRealty(ResultSet rs) throws SQLException {
        Realty realty = new Realty();
        realty.setId(rs.getInt("id"));
        realty.set
        return realty;
    }

    @Override
    public List<Realty> findByOwner(User owner) {
        Connection con;
        List<Realty> ls = new ArrayList<Realty>();
		try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.realty WHERE owner_id = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            ResultSet rs = st.executeQuery();
			while (rs.next()) {
                Realty realty = createSafeRealty(rs);
                realty.setOwner(user);
                ls.add(realty);
            }
		} catch (SQLException e) {
            log.info(e.getMessage());
            log.trace(e.getStackTrace().toString());
        }
            
        return ls;
    }
    
    @Override
    public Optional<Realty> findById(Integer id) {
        Connection con;
        Optional<Realty> opt = Optional.empty();
        try {
            con = dbsource.getConnection();
            String query = "SELECT * FROM prova.realty WHERE id = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                opt = Optional.of(createSafeRealty(rs));
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.trace(e.getStackTrace().toString());
        }
        return opt;
    }

    @Override
    public Optional<Realty> findByIdAndOwner(Integer id, User owner) {
        Optional<Realty> opt = findById(id);
        if (!(opt.isPresent() && opt.get().getOwner().equals(owner))) 
            opt = Optional.empty();
        return opt;
    }

    @Override
    public List<Realty> findByOwnerAndInsertionNull(User owner) {
		Connection con;
        List<Realty> ls = new ArrayList<Realty>();
        try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.realty WHERE owner_id = ? AND insertion IS NULL";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Realty realty = createSafeRealty(rs);
				realty.setOwner(user);
				ls.add(realty);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
        return ls;
    }

    @Override
    public List<Realty> findByOwnerAndInsertionNotNull(User owner) {
		Connection con;
        List<Realty> ls = new ArrayList<Realty>();
        try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.realty WHERE owner_id = ? AND insertion IS NOT NULL";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Realty realty = createSafeRealty(rs);
				realty.setOwner(user);
				ls.add(realty);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
        return ls;
    }

    @Override
    public List<Realty> findByOwnerAndDraftFalse(User owner) {
		Connection con;
        List<Realty> ls = new ArrayList<Realty>();
        try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.realty WHERE owner_id = ? AND is_draft = 0";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Realty realty = createSafeRealty(rs);
				realty.setOwner(user);
				ls.add(realty);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
        return ls;
    }

    @Override
    public List<Realty> findByOwnerAndDraftTrue(User owner) {
		Connection con;
        List<Realty> ls = new ArrayList<Realty>();
        try {
			con = dbsource.getConnection();
			String query = "SELECT * FROM prova.realty WHERE owner_id = ? AND is_draft = 1";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Realty realty = createSafeRealty(rs);
				realty.setOwner(user);
				ls.add(realty);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
        return ls;
        return null;
    }
}
