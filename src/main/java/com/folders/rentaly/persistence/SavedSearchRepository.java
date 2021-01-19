package com.folders.rentaly.persistence;

import com.folders.rentaly.model.SavedSearch;

import org.springframework.data.repository.CrudRepository;

public interface SavedSearchRepository extends CrudRepository<SavedSearch, Integer> {
    
}
