package com.example.springboot;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MysqlDataSourceConfig {

//    @Bean
//    public DataSource dataSource() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC");
//        hikariConfig.setUsername("root");
//        hikariConfig.setPassword("admin");
//
//        hikariConfig.setConnectionTestQuery("SELECT 1");
//        hikariConfig.setPoolName("MySQL::springHikariCP");
//        hikariConfig.setConnectionTimeout(10000);
//        hikariConfig.setMaximumPoolSize(50);
//        hikariConfig.setMinimumIdle(10);
//
//        return new HikariDataSource(hikariConfig);
//    }

}
