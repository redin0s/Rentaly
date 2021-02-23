package com.folders.rentaly.persistence.dao;

import java.util.List;

import com.folders.rentaly.model.SavedSearch;
import com.folders.rentaly.model.User;

public interface SavedSearchDAO extends DAO<SavedSearch> {
	List<SavedSearch> findSavedSearchByUser(User t);
}
