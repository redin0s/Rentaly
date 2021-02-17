package com.folders.rentaly.persistence.dao;

import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(Integer id);
    void save(T t);
    void delete(T t);
}
