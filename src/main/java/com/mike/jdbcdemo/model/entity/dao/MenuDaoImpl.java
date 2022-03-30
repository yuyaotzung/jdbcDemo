package com.mike.jdbcdemo.model.entity.dao;

import com.mike.jdbcdemo.model.entity.MenuDTO;
import com.mike.jdbcdemo.model.entity.User;
import com.mike.jdbcdemo.model.entity.rowmapper.MenuRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuDaoImpl implements IDao<MenuDTO> {

    @Autowired
   // private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Qualifier("b2cJdbcTemplate")
    private NamedParameterJdbcTemplate b2cJdbcTemplate ;

    public MenuDTO findById(String menuId){
        String sql = "SELECT MENUID,ROOTMENUID,PARENTMENUID,MENUDESC,TASKID,ORDERNO FROM b2c.menu WHERE MENUID =:menuId";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("menuId",menuId);
        MenuRowMapper menuRowMapper = new MenuRowMapper();
        List<MenuDTO> menuDTOs = b2cJdbcTemplate.query(sql,paramMap,menuRowMapper);
        if(!menuDTOs.isEmpty()){
            return menuDTOs.get(0);
        }
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuId("");
        return menuDTO;
    }
    public MenuDTO findByKey(Integer key){
        return null;
    }
}
