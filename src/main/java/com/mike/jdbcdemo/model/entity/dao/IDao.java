package com.mike.jdbcdemo.model.entity.dao;

import com.mike.jdbcdemo.model.entity.User;

public interface IDao<T> {

    public T findByKey(Integer userKey);

    public T findById(String Id);
}
