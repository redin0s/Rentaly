package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.model.Insertion;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("realtyDAO")
public class RealtyDAOJDBC extends JDBC implements RealtyDAO {

    private Realty createSafeRealty(ResultSet rs) throws SQLException {
        Realty realty = new Realty();
        realty.setId(rs.getInt("id"));
        realty.setType(rs.getString("type"));
        realty.setSquare_meters(rs.getInt("square_meters"));
        realty.setMax_holders(rs.getInt("max_holders"));
        realty.setLongitude(rs.getDouble("longitude"));
        realty.setLatitude(rs.getDouble("latitude"));
        realty.setCity(rs.getString("city"));
        realty.setAddress(rs.getString("address"));
        realty.setDraft(rs.getBoolean("is_draft"));
        realty.setCurrent_holders(rs.getInt("current_holders"));
        realty.setInsertion(createInsertion(rs));
        return realty;
    }

    private Insertion createInsertion(ResultSet rs) throws SQLException {
        Insertion insertion = new Insertion();
        insertion.setId(rs.getInt("insertion_id"));
        if (insertion.getId() != null) {
            insertion.setCost(rs.getInt("cost"));
            insertion.setDescription(rs.getString("description"));
            insertion.setPublish_date(rs.getObject("publish_date", LocalDate.class));
            insertion.setIs_visible(rs.getBoolean("is_visible"));
        }
        return insertion;
    }

    @Override
    public List<Realty> findByOwner(User owner) {
        List<Realty> ls = new ArrayList<Realty>();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.realty LEFT JOIN prova.insertion ON realty.insertion_id = insertion.id WHERE owner_id = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            log.info(st.toString());
            ResultSet rs = st.executeQuery();
			while (rs.next()) {
                Realty realty = createSafeRealty(rs);
                realty.setOwner(owner);
                ls.add(realty);
            }
		} catch (SQLException e) {
            log.info(e.getMessage());
            log.trace(e.getStackTrace().toString());
        }
            
        return ls;
    }

    @Override
    public Optional<Realty> findByIdAndOwner(Integer id, User owner) {
        Optional<Realty> opt = get(id);
        if (!(opt.isPresent() && opt.get().getOwner().equals(owner))) 
            opt = Optional.empty();
        return opt;
    }

    @Override
    public List<Realty> findByOwnerAndInsertionNull(User owner) {
        List<Realty> ls = new ArrayList<Realty>();
        try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.realty WHERE owner_id = ? AND insertion IS NULL";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Realty realty = createSafeRealty(rs);
				realty.setOwner(owner);
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
        List<Realty> ls = new ArrayList<Realty>();
        try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.realty WHERE owner_id = ? AND insertion_id IS NOT NULL JOIN prova.insertion ON realty.insertion_id=insertion.id";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
            ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Realty realty = createSafeRealty(rs);
				realty.setOwner(owner);
				ls.add(realty);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
        return ls;
    }

	public List<Realty> findByOwnerAndDraft(User owner, Boolean draft) {
		List<Realty> ls = new ArrayList<Realty>();
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT * FROM prova.realty LEFT JOIN prova.insertion ON realty.insertion_id = insertion.id WHERE owner_id = ? AND is_draft = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
			st.setBoolean(2, draft);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Realty realty = createSafeRealty(rs);
				realty.setOwner(owner);
				ls.add(realty);
			}
		} catch (SQLException e) {
			log.info(e.getMessage());
			log.trace(e.getStackTrace().toString());
		}
		return ls;
	}

    @Override
    public List<Realty> findClosestToPoint(Float lat, Float lon) {
        List<Realty> ls = new ArrayList<Realty>();
        try {
			Connection con = dbSource.getConnection();
            String query =  "SELECT * FROM (" 
                           +"SELECT *, "
                           +"(6371 * acos(cos(radians(?)) * "
                           +"cos(radians(latitude)) * cos(radians(longitude) -"
                           +"radians(?)) + sin(radians(?)) *"
                           +"sin(radians(latitude)))) "
                           +"AS distance FROM prova.realty WHERE insertion_id IS NOT NULL) AS t "
                           +"INNER JOIN prova.insertion ON t.insertion_id=insertion.id WHERE t.distance < 20 ORDER BY t.distance "
                           +"LIMIT 20 OFFSET 0";
            PreparedStatement st = con.prepareStatement(query);
            st.setFloat(1, lat);
            st.setFloat(2, lon);
            st.setFloat(3, lat);
            ResultSet rs = st.executeQuery();
            if(rs.next() == false) {
                log.info("search gave no result");
            } else {
                do {
                    Realty realty = createSafeRealty(rs);
                    ls.add(realty);
                    log.info(realty.toString());
                } while (rs.next());
            }
		} catch (SQLException e) {
			log.info(e.getMessage());
            log.trace(e.getStackTrace().toString());
		}
        return ls;
    }

    @Override
    public Optional<Realty> get(Integer id) {
        Optional<Realty> opt = Optional.empty();
        try {
            Connection con = dbSource.getConnection();
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
    public void save(Realty t) {
        try {
			Connection con = dbSource.getConnection();
			PreparedStatement st;
			String query;
			if (t.getId() != null) {
				query = "UPDATE prova.realty SET type=?, square_meters=?, max_holders=?, " + 
						"owner_id=?, latitude=?, longitude=?, insertion_id=?, city=?, address=?, " +
                        "is_draft=?, current_holders=? WHERE id = ?";
				st = con.prepareStatement(query);
				st.setInt(12, t.getId());
			} else {
				query = "INSERT INTO prova.realty VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				st = con.prepareStatement(query);
			}
			st.setString(1, t.getType());
			st.setInt(2, t.getSquare_meters());
			st.setInt(3, t.getMax_holders());
			st.setInt(4, t.getOwner().getId());
			st.setDouble(5, t.getLatitude());
			st.setDouble(6, t.getLongitude());
			st.setInt(7, t.getInsertion().getId());
			st.setString(8, t.getCity());
			st.setString(9, t.getAddress());
			st.setBoolean(10, t.getDraft());
			st.setInt(11, t.getCurrent_holders());
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    @Override
    public void delete(Realty t) {
        try {
			Connection con = dbSource.getConnection();
			String query = "DELETE FROM prova.realty WHERE id = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    @Override
    public Integer countByOwnerAndDraft(User owner, Boolean draft) {
        Integer count = 0;
		try {
			Connection con = dbSource.getConnection();
			String query = "SELECT COUNT(*) FROM prova.realty WHERE owner_id = ? AND is_draft = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, owner.getId());
			st.setBoolean(2, draft);
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
