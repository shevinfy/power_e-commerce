package com.macro.mall.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Swagger自定义配置
 * @Data ： 注解的目标是 类上
 * 提供类的get、set、equals、hashCode、canEqual、toString方法
 * 如果方法值默认不写是系统默认值
 * @EqualsAndHashCode
 *
 * @Builder lombok的创建对象方法
 * 设计数据实体时，对外保持private setter，而对属性的赋值采用Builder的方式。
 */
@Data
@EqualsAndHashCode
@Builder
public class SwaggerProperties {
    /**
     * API文档生成基础路径
     */
    private String apiBasePackage;
    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档联系人姓名
     */
    private String contactName;
    /**
     * 文档联系人网址
     */
    private String contactUrl;
    /**
     * 文档联系人邮箱
     */
    private String contactEmail;
}
