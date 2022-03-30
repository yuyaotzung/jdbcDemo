package com.mike.jdbcdemo.controller;

import com.mike.jdbcdemo.model.entity.MenuDTO;
import com.mike.jdbcdemo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    // rename Asc
    @Autowired
    private MenuService menuService ;

    @GetMapping("/menu/{menuId}")
    public MenuDTO queryMenu(@PathVariable  String menuId){
        return  menuService.findById(menuId);
    }
}
