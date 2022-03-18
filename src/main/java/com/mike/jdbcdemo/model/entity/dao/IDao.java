package com.mike.jdbcdemo.model.entity.dao;

import com.mike.jdbcdemo.model.entity.User;

public interface IDao {

    public User findByKey(Integer userKey);
}
