package com.folders.rentaly.persistence.dao;

import java.util.Optional;

import com.folders.rentaly.model.User;

public interface UserDAO extends DAO<User> {
	public Optional<User> findUser(String email);
}