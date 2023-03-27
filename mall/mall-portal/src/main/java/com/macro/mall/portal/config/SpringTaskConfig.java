package com.macro.mall.portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务相关配置
 *
 * @configuration注释 标识一个类为配置类，取代bean.xml去配置文件去注册bean对象
 * @EnableScheduling注释 开启springTask定时任务能力
 *
 * SpringTask框架整合第一步，然后再去写执行定时任务类的组件即可
 */
@Configuration
@EnableScheduling
public class SpringTaskConfig {
}
