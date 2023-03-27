package com.macro.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis相关配置
 * @configuration注释 是spring中的一个注释 声明一个类为配置类，用于取代bean.xml配置文件注册bean对象。
 * @EnableTransactionManagement注释 是springboot中的注释 表示开始支持事务
 *      1.在spingboot的配置类加上@EnableTransactionManagement开启事务支持
 *      2.在service是实现类中加上@Transactional注释，表示这个实现类报错进行回滚
 * @MapprScan注释
 * @Mapper注释
 * 作用：在接口类上添加了@Mapper，在编译之后会生成相应的接口实现类
 * 添加位置：接口类上面
 * 如果每一个接口类都要变成实现类，那么需要在每个接口类上加上@Mapper，比较麻烦，解决这个问题用@MapperScan注释
 *
 * @MapperScan
 * 作用：指定要变成实现类的接口所在包，然后包下面的所有接口在编译之后都会生成相应的实现类
 * 添加位置：实在Springboot启动类上面添加合作mtbatis自动代码生成配置类
 *
 * @MapperScan({"com.example.power.dao,com.example.power.mapper"})
 * 在com.example.power.dao和com.example.power.mapper报下的接口类，都会变成实现类
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.macro.mall.mapper","com.macro.mall.dao"})
public class MyBatisConfig {
}
