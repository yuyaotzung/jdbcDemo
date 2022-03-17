package com.mike.jdbcdemo.dao;

import com.mike.jdbcdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Win7dao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public User findByKey(Integer userKey){
        StringBuilder sql = new StringBuilder("select id,name from ");
        Map<String,Integer> paramMap = new HashMap<String,Integer>();
        paramMap.put("userKey",userKey);
        User User = new User();
        User.setUserKey(1);
        User.setUserName("Mike");
        User.setCompanyId("88720124");
        //user User = namedParameterJdbcTemplate.query(sql,paramMap,);
        return User;
    }
}
