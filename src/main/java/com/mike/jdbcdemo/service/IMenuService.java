package com.mike.jdbcdemo.service;

import com.mike.jdbcdemo.model.entity.MenuDTO;

public interface IMenuService {
    public MenuDTO findById(String id);
}
