package com.folders.rentaly.persistence.dao;

import java.util.List;

import com.folders.rentaly.model.User;

public interface UserDAO {
	public List<User> findAll();
}