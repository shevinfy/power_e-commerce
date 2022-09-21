package com.example.poweradmin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis相关配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.example.poweradmin.dao,com.example.poweradmin.mapper"})
public class MyBatisConfig {
}
