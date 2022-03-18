package com.mike.jdbcdemo.model.entity.rowmapper;

import com.mike.jdbcdemo.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
