package com.folders.rentaly.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.folders.rentaly.persistence.DBSource;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class JDBC {
    @Autowired
    protected DBSource dbSource;

    protected String tablename;

    protected Integer getNextId() {
        String query = "SELECT nextval(pg_get_serial_sequence( ? , 'id'))";
        Integer id = null;
		try (Connection con = dbSource.getConnection(); PreparedStatement st = con.prepareStatement(query);) {
            st.setString(1, tablename);
            ResultSet rs = st.executeQuery(); 
            if (rs.next()) {
                id = rs.getInt(1);
            }
		} catch (SQLException e) {
            log.info(e.getMessage());
            log.trace(e.getStackTrace().toString());
		}
        return id;
    }

    public JDBC(String tablename) {
        this.tablename = tablename;
    }
}
