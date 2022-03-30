package com.mike.jdbcdemo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class jdbcTemplateController {
    @Autowired
    @Qualifier("b2cJdbcTemplate")
    private NamedParameterJdbcTemplate b2cJdbcTemplate ;

    @Autowired
    @Qualifier("b2eJdbcTemplate")
    private NamedParameterJdbcTemplate b2eJdbcTemplate ;
}
