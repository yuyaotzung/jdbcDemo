package com.mike.jdbcdemo.service;

import com.mike.jdbcdemo.model.entity.MenuDTO;
import com.mike.jdbcdemo.model.entity.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuService implements IMenuService{

    @Autowired
    private IDao<MenuDTO> menuDao ;

    public MenuDTO findById(String id){
        return  menuDao.findById(id);
    }
}
