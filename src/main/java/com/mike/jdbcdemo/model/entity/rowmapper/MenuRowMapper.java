package com.mike.jdbcdemo.model.entity.rowmapper;

import com.mike.jdbcdemo.model.entity.MenuDTO;
import com.mike.jdbcdemo.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuRowMapper implements RowMapper<MenuDTO> {
    @Override
    public MenuDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuId(resultSet.getString("MENUID"));
        menuDTO.setRootMenuId(resultSet.getString("ROOTMENUID"));
        menuDTO.setParentMenuId(resultSet.getString("PARENTMENUID"));
        menuDTO.setMenuDesc(resultSet.getString("MENUDESC"));
        menuDTO.setTaskId(resultSet.getString("TASKID"));
        menuDTO.setOrderNo(resultSet.getInt("ORDERNO"));
        return menuDTO;
    }
}
