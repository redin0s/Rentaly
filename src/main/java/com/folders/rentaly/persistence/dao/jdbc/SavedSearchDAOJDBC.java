package com.folders.rentaly.persistence.dao.jdbc;

import java.util.Optional;

import com.folders.rentaly.persistence.dao.SavedSearchDAO;
import com.folders.rentaly.model.SavedSearch;

import org.springframework.stereotype.Component;

@Component("savedSearchDAO")
public class SavedSearchDAOJDBC extends JDBC implements SavedSearchDAO {

    @Override
    public Optional<SavedSearch> get(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(SavedSearch t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(SavedSearch t) {
        // TODO Auto-generated method stub

    }
    
}
