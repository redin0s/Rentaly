package com.folders.rentaly.persistence.dao.jdbc;

import java.util.Optional;

import com.folders.rentaly.persistence.dao.InsertionDAO;

import com.folders.rentaly.model.Insertion;

import org.springframework.stereotype.Component;

@Component("insertionDAO")
public class InsertionDAOJDBC extends JDBC implements InsertionDAO {

    @Override
    public Optional<Insertion> get(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Insertion t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Insertion t) {
        // TODO Auto-generated method stub

    }
    
}
