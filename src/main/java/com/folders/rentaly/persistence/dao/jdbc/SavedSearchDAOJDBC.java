package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.folders.rentaly.persistence.dao.SavedSearchDAO;
import com.folders.rentaly.model.SavedSearch;
import com.folders.rentaly.model.User;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("savedSearchDAO")
public class SavedSearchDAOJDBC extends JDBC implements SavedSearchDAO {

	public SavedSearchDAOJDBC() {
		super("saved_search");
	}

	private SavedSearch createSavedSearch(ResultSet rs) throws SQLException {
		SavedSearch savedSearch = new SavedSearch();
		savedSearch.setId(rs.getInt("id"));
		savedSearch.setLatitude(rs.getDouble("latitude"));
		savedSearch.setLongitude(rs.getDouble("longitude"));
		savedSearch.setMax_distance(rs.getInt("max_distance"));
		savedSearch.setMin_price(rs.getFloat("min_price"));
		savedSearch.setMax_price(rs.getFloat("max_price"));
		savedSearch.setCity(rs.getString("city"));
		savedSearch.setType(rs.getString("type"));
		return savedSearch;
	}

	@Override
	public List<SavedSearch> findSavedSearchByUser(User t) {
		List<SavedSearch> ls = new ArrayList<SavedSearch>();
		String query = "SELECT * FROM saved_search WHERE user_id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, t.getId());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				SavedSearch savedsearchopt = createSavedSearch(rs);
                savedsearchopt.setUser(t);
				ls.add(savedsearchopt);
			}
		} catch (SQLException e) {
			log.error("error in find saved_search by email", e);
		}

		return ls;
	}

	@Override
	public Optional<SavedSearch> get(Integer id) {
        throw new UnsupportedOperationException();
	}

	@Override
    public void save(SavedSearch t) {
        String query = "INSERT INTO saved_search (max_distance,min_price,max_price,user_id,latitude,longitude,id,city,type) VALUES (?,?,?,?,?,?,?,?,?)" +
		               "ON CONFLICT (id) DO UPDATE SET max_distance=EXCLUDED.max_distance, min_price=EXCLUDED.min_price,"+
                       " max_price=EXCLUDED.max_price, user_id=EXCLUDED.user_id, latitude=EXCLUDED.latitude, longitude=EXCLUDED.longitude, city=EXCLUDED.city, type=EXCLUDED.type";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			if (t.getId() == null) 
				t.setId(getNextId());
			
			st.setInt(1, t.getMax_distance());
			st.setFloat(2, t.getMin_price());
			st.setFloat(3, t.getMax_price());
			st.setInt(4, t.getUser().getId());
            st.setDouble(5, t.getLatitude());
            st.setDouble(6, t.getLongitude());
			st.setInt(7, t.getId());
			st.setString(8, t.getCity());
			st.setString(9, t.getType());

			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in insert/update saved search", e);
		}
    }

	@Override
	public void delete(SavedSearch t) {
		String query = "DELETE FROM saved_search WHERE id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, t.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in delete savedsearch", e);
		}
	}

	@Override
	public void deleteByUser(Integer id, User user) {
		String query = "DELETE FROM saved_search WHERE id = ? AND user_id = ?";
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, id);
			st.setInt(2, user.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			log.error("error in delete savedsearch with id", e);
		}
	}
}
