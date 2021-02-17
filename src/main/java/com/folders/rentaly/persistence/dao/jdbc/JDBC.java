package com.folders.rentaly.persistence.dao.jdbc;

import com.folders.rentaly.persistence.DBSource;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class JDBC {
    @Autowired
    protected DBSource dbSource;
}
