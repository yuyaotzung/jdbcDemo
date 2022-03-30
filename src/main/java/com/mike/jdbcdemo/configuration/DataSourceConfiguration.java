package com.mike.jdbcdemo.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.b2c")
    public DataSource b2cDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public NamedParameterJdbcTemplate b2cJdbcTemplate(@Qualifier("b2cDataSource")DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.b2e")
    public DataSource b2eDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public NamedParameterJdbcTemplate b2eJdbcTemplate(@Qualifier("b2eDataSource")DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}