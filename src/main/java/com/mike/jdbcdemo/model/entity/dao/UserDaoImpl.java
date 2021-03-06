package com.mike.jdbcdemo.model.entity.dao;

import com.mike.jdbcdemo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDaoImpl implements IDao<User> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public User findByKey(Integer userKey){
        StringBuilder sql = new StringBuilder("select id,name from ");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userKey",userKey);
        User User = new User();
        User.setUserKey(1);
        User.setUserName("Mike");
        User.setCompanyId("88720124");
        //user User = namedParameterJdbcTemplate.query(sql,paramMap,);
        return User;
    }
    public User findById(String Id){
        return null;
    }
}
