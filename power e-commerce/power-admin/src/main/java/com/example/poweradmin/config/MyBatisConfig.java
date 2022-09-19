package com.example.poweradmin.config;

import org.springframework.context.annotation.Configuration;

/**
 * Mybatis相关配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan()
public class MyBatisConfig {
}
