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
import com.folders.rentaly.model.Check;
import com.folders.rentaly.model.User;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("checkDAO")
public class CheckDAOJDBC extends JDBC implements CheckDAO {

    private Check createSafeCheck(ResultSet rs) throws SQLException {
        Check check = new Check();
        check.setId(rs.getInt("id"));
        check.setCost(rs.getFloat("cost"));
        // check.setType(rs.getInt("type"));
        check.setExpire(rs.getObject("expire", LocalDate.class));
        return check;
    }

    @Override
    public List<Check> findByRent_Realty_Owner(User owner) {
        List<Check> ls = new ArrayList<Check>();
        try {
            Connection con = dbSource.getConnection();
            String query = "SELECT * FROM prova.check c, prova.rent rt, prova.realty ry, prova.user u WHERE c.rent_id = rt.id AND rt.realty_id = ry.id AND ry.owner_id = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, owner.getId());
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
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Check t) {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<Check> get(Integer id) {
        Optional<Check> check = Optional.empty();
        try {
            Connection con = dbSource.getConnection();
            String query = "SELECT * FROM prova.check c WHERE c.check_id = ?";
            PreparedStatement st = con.prepareStatement(query);
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
    public Integer countByOwnerAndExpireGreaterThanEqual(User owner, LocalDate date) {
        Integer count = 0;
        try {
            Connection con = dbSource.getConnection();
            String query = "SELECT COUNT(*) FROM prova.check JOIN prova.rent ON check.rent_id = rent.id  JOIN prova.realty ON rent.realty_id = realty.id WHERE owner_id=? AND expire >= ?";
            PreparedStatement st = con.prepareStatement(query);
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
        try {
            Connection con = dbSource.getConnection();
            String query = "SELECT COUNT(*) FROM prova.check JOIN prova.rent ON check.rent_id = rent.id WHERE holder_id=? and expire >= ?";
            PreparedStatement st = con.prepareStatement(query);
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
