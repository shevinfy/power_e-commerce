# power e-commerce 后端开发文档

## 参考文档：

[(20条消息) Github开源项目详解--Mall（一）_吃个小菜的博客-CSDN博客_mall项目](https://blog.csdn.net/qq_45714272/article/details/125565042)

[mall架构及功能概览 | mall学习教程 (macrozheng.com)](https://www.macrozheng.com/mall/foreword/mall_foreword_01.html#mall项目简介)

## 后台架构：

```
mall
├── mall-common – 工具类及通用代码
├── mall-mbg – MyBatisGenerator生成的数据库操作代码
├── mall-security – SpringSecurity封装公用模块
├── mall-admin – 后台商城管理系统接口
├── mall-search – 基于Elasticsearch的商品搜索系统
├── mall-portal – 前台商城系统接口
└── mall-demo – 框架搭建时的测试代码
```

#### 总结：

springboot项目，一般大项目都会分成一个父项目，下面多个子项目，子项目是后台web，admin，前台接口portal，或者mgb代码生成之类的。==目的：模块之间隔离，修改某一个模块的代码，其他模块不会受到影响。==

每一个子项目都有一个自己的pom.xml文件，项目依赖文件。

父项目的pom.xml文件与子项目pom.xml文件的关系：==（是继承关系！）==

父项目写好全部的依赖，子项目就可以用这些依赖。在这里父项目pom.xml中dependencies和dependencyManager很重要。如果是==dependencies中的依赖子项目会**自动继承**==，但是dependencyManager标签中申明的依赖，子项目想要继承就要在自己的pom.xml中写**groupId和artifactId**去声明。

#### 我学习的文档：

[SpringBoot 多模块项目 - SpringBoot教程 - 基础教程在线 (nhooo.com)](https://www.nhooo.com/springboot/springboot-multi-module-project.html)

[Maven 多模块父子工程的实现(含Spring Boot示例)_java_脚本之家 (jb51.net)](https://www.jb51.net/article/209590.htm)

[(21条消息) Maven学习笔记-父pom和子pom_一片蓝蓝的云的博客-CSDN博客_父pom](https://blog.csdn.net/mumuwang1234/article/details/108679923)





## 项目搭建

### 一、使用IDEA初始化一个SpringBoot项目

![img](https://www.macrozheng.com/assets/arch_screen_01.ab5f2485.png)

### 二、添加项目依赖pom.xml文件

> 在pom.xml中添加相关依赖。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!--本项目的信息-->
    <groupId>com.example.power</groupId>
    <artifactId>power_e-commerce</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>

    <!--为了子项目可以使用父项目的依赖-->
    <modules>
        <module>power-admin</module>
        <module>power-common</module>
        <module>power-demo</module>
        <module>power-mbg</module>
        <module>power-portal</module>
        <module>power-search</module>
        <module>power-security</module>
    </modules>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <!--properties快速控制版本信息-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <skipTests>true</skipTests>
        <docker.host>http://39.108.74.53:2375</docker.host>
        <docker.maven.plugin.version>0.40.0</docker.maven.plugin.version>
        <pagehelper-starter.version>1.4.2</pagehelper-starter.version>
        <pagehelper.version>5.3.0</pagehelper.version>
        <druid.version>1.2.9</druid.version>
        <hutool.version>5.8.0</hutool.version>
        <springfox-swagger.version>3.0.0</springfox-swagger.version>
        <swagger-models.version>1.6.0</swagger-models.version>
        <swagger-annotations.version>1.6.0</swagger-annotations.version>
        <mybatis-generator.version>1.4.1</mybatis-generator.version>
        <mybatis.version>3.5.9</mybatis.version>
        <mysql-connector.version>8.0.29</mysql-connector.version>
        <spring-data-commons.version>2.7.0</spring-data-commons.version>
        <jjwt.version>0.9.1</jjwt.version>
        <aliyun-oss.version>2.5.0</aliyun-oss.version>
        <logstash-logback.version>7.2</logstash-logback.version>
        <minio.version>8.4.1</minio.version>
        <!--子模块的版本信息-->
        <power-common.version>0.0.1-SNAPSHOT</power-common.version>
        <power-mbg.version>0.0.1-SNAPSHOT</power-mbg.version>
        <power-security.version>0.0.1-SNAPSHOT</power-security.version>
    </properties>

    <!--项目依赖-->
    <dependencies>
        <!--springboot的通用依赖模块 starter (web,actuator,aop,test) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

        <!--MyBatis分页插件-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.4.2</version>
        </dependency>

        <!--集成druid连接池 druid是阿里公司的，java中的数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.9</version>
        </dependency>

        <!--MyBatis 生成器-->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.4.1</version>
        </dependency>

        <!--mysql数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.15</version>
        </dependency>

        <!--lombok依赖-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!--hutool中有很多java的类-->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.8.0</version>
        </dependency>

    </dependencies>

    <dependencyManagement>
        <dependencies>
            <!--power通用模块-->
            <!--common是power中工具类模块-->
            <dependency>
                <groupId>com.example.power</groupId>
                <artifactId>power-common</artifactId>
                <version>${power-common.version}</version>
            </dependency>
            <!--mgb是power中的MBG生成模块-->
            <dependency>
                <groupId>com.example.power</groupId>
                <artifactId>power-mbg</artifactId>
                <version>${power-mbg.version}</version>
            </dependency>
            <!--power项目中的安全模块-->
            <dependency>
                <groupId>com.example.power</groupId>
                <artifactId>power-security</artifactId>
                <version>${power-security.version}</version>
            </dependency>
            <!--MyBatis分页插件starter-->
            <dependency>
                <groupId>com.github.pagehelper</groupId>
                <artifactId>pagehelper-spring-boot-starter</artifactId>
                <version>${pagehelper-starter.version}</version>
            </dependency>
            <!--MyBatis分页插件-->
            <dependency>
                <groupId>com.github.pagehelper</groupId>
                <artifactId>pagehelper</artifactId>
                <version>${pagehelper.version}</version>
            </dependency>
            <!--集成druid连接池-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!--Hutool Java工具包-->
            <dependency>
                <groupId>cn.hutool</groupId>
                <artifactId>hutool-all</artifactId>
                <version>${hutool.version}</version>
            </dependency>
            <!--Swagger-UI API文档生产工具-->
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-boot-starter</artifactId>
                <version>${spring-swagger.version}</version>
            </dependency>
            <!--解决Swagger访问主页时的NumberFormatException问题-->
            <dependency>
                <groupId>io.swagger</groupId>
                <artifactId>swagger-models</artifactId>
                <version>${swagger-models.version}</version>
            </dependency>
            <dependency>
                <groupId>io.swagger</groupId>
                <artifactId>swagger-annotations</artifactId>
                <version>${swagger-annotations.version}</version>
            </dependency>
            <!--MyBatis 生成器-->
            <dependency>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-core</artifactId>
                <version>${mybatis-generator.version}</version>
            </dependency>
            <!--MyBatis-->
            <dependency>
                <groupId>org.mybatis</groupId>
                <artifactId>mybatis</artifactId>
                <version>${mybatis.version}</version>
            </dependency>
            <!--Mysql数据库驱动包-->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql-connector.version}</version>
            </dependency>
            <!--SpringData工具包-->
            <dependency>
                <groupId>org.springframework.data</groupId>
                <artifactId>spring-data-commons</artifactId>
                <version>${spring-data-commons.version}</version>
            </dependency>
            <!--JWT（Json Web Token）登录支持-->
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt</artifactId>
                <version>${jjwt.version}</version>
            </dependency>
            <!--阿里云OSS  对象存储服务-->
            <dependency>
                <groupId>com.aliyun.oss</groupId>
                <artifactId>aliyun-sdk-oss</artifactId>
                <version>${aliyun-oss.version}</version>
            </dependency>
            <!--集成logstash-->
            <dependency>
                <groupId>net.logstash.logback</groupId>
                <artifactId>logstash-logback-encoder</artifactId>
                <version>7.2</version>
            </dependency>
            <!--MinIO JAVA SDK-->
            <dependency>
                <groupId>io.minio</groupId>
                <artifactId>minio</artifactId>
                <version>${minio.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>

            </plugins>
        </pluginManagement>
    </build>
</project>

```

#### groupId与artifactId：

groupId：是项目组织**唯一的标识符**，实际对应java包的结构，==是main目录里java的目录结构==
artifactId：就是项目唯一的标识符，实际项目的名称

groupId一般分为多个段，这里只说两段，第一段为域，第二段为公司名称。域又分为org、com、cn等等许多，其中org为非盈利阻止，com为商业阻止。比如apache公司的tomcat项目，groupId为org.apache，它的域为org，公司名称为apache，artifactId为tomcat

#### OSS：

OSS是==阿里云对象存储服务==（Object Storage Service）的一个简称，它是阿里云提供的海量、安全、低成本、高可靠的==云存储服务==。
即开即用、无限大空间的存储集群。相较传统建服务器存储而言，OSS在可靠性、安全性、成本和数据处理能力方面都有着突出的优势。使用OSS，您可以通过网络随时存储和调用包括文本、图片和视频等在内的各种非结构化数据文件。
OSS将数据文件以对象/文件（Object）的形式上传到存储空间（Bucket）中。OSS提供的是一个==Key-Value键值对==形式的对象存储服务。==用户可以根据Object的名称（Key）唯一地址获取该Object的内容。==

#### logstash：

logstash就是一根具备实时==数据传输能力的管道==，负责将数据信息从管道的输入端传输到管道的输出端；与此同时这根管道还可以让你根据自己的需求在中间加上滤网，Logstash提供里很多功能强大的滤网以满足你的各种应用场景。

logstash常用于日志系统中做日志采集设备，最常用于ELK中作为日志收集器使用。

#### MinIo java SDK：

MinIO 是一款基于Go语言发开的高性能、分布式的对象存储系统。客户端支持Java,Net,Python,Javacript, Golang语言。

MinIO 是一款高性能、**分布式的对象存储系统**. 它是一款软件产品, 可以100%的运行在标准硬件。

### 三、修改power-admin的SpringBoot配置文件

yml文件分为application.yml，application-dev.yml，application-prod.yml

#### 1、application.yml是总的

1. spring（spring-application）应用程序名称（spring-profiles）环境选择（spring-servlet）文件上传（spring-mvc）修改springmvc的默认配置pathmatcher去兼容swaggger2
2. server 后端服务器端口
3. mybatis Scanner扫描器，扫描 .xml文件
4. jwt 存储
5. redis
6. secure 安全
7. oss 对象云存储服务



> 在application.yml中==添加数据源配置==和==MyBatis的mapper.xml的路径配置==。

```yaml
spring:
  application:
    name: power-admin  #应用程序名称
  profiles:
    active: dev #默认为开发环境
  servlet:
    multipart:
      enabled: true #开启文件上传
      max-file-size: 10MB #限制文件上传大小为10M

  #springboot升级到2.6之后，需要设置spring.mvc.pathmatch.matching-strategy来兼容Swagger2
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher


server:
  port: 8081  # 服务器配置

# 配置Scanner扫描器
mybatis:
  mapper-locations: # 配置多个扫描路径
    - classpath:dao/*.xml
    - classpath*:com/**/mapper/*/xml
    
    
jwt:
  tokenHeader: Authorization #JWT存储的请求头
  secret: mall-admin-secret #JWT加解密使用的密钥
  expiration: 604800 #JWT的超期限时间(60*60*24*7)
  tokenHead: 'Bearer '  #JWT负载中拿到开头

redis:
  database: mall
  key:
    admin: 'ums:admin'
    resourceList: 'ums:resourceList'
  expire:
    common: 86400 # 24小时

secure:
  ignored:
    urls: #安全路径白名单
      - /swagger-ui/
      - /swagger-resources/**
      - /**/v2/api-docs
      - /**/*.html
      - /**/*.js
      - /**/*.css
      - /**/*.png
      - /**/*.map
      - /favicon.ico
      - /actuator/**
      - /druid/**
      - /admin/login
      - /admin/register
      - /admin/info
      - /admin/logout
      - /minio/upload

aliyun:
  oss:
    endpoint: oss-cn-shenzhen.aliyuncs.com # oss对外服务的访问域名
    accessKeyId: test # 访问身份验证中用到用户标识
    accessKeySecret: test # 用户用于加密签名字符串和oss用来验证签名字符串的密钥
    bucketName: macro-oss # oss的存储空间
    policy:
      expire: 300 # 签名有效期(S)
    maxSize: 10 # 上传文件大小(M)
    callback: http://39.98.190.128:8080/aliyun/oss/callback # 文件上传成功后的回调地址
    dir:
      prefix: mall/images/ # 上传文件夹路径前缀

```

##### 1.1、spring.mvc.pathmatch.matching-strategy:

由于**Spring Boot 2.6.x** 请求路径与 Spring MVC 处理映射匹配的默认策略从`AntPathMatcher`更改为`PathPatternParser`。所以需要设置`spring.mvc.pathmatch.matching-strategy为ant-path-matcher`来改变它。

##### 1.2、mybatis的springboot使用：

pom

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
</dependency>
```

如果使用了pagehelper的starter，则可以只导入如下的Maven库即可（因为整合PageHelper就是相当于整合MyBatis）

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
</dependency>
```

在配置类中定义接口扫描范围

```java
@Configuration
@EnableTransactionManagement
@MapperScan({"com.test.mapper","com.test.portal.dao"})
public class MyBatisConfig {
}
```

@Configuration用于指定该类为配置入口类
可以在MappderScan中定义多个接口包名
@EnableTransactionManagement是用于开启事务的

在yml中配置Scanner扫描器

```yml
mybatis:
  mapper-locations:
    - classpath:dao/*.xml
    - classpath*:com/**/mapper/*.xml
```

可以配置多个扫描路径

##### 1.3、 redis

Redis 是目前业界使用最广泛的内存数据存储。Redis 是目前业界使用最广泛的内存数据存储。

#### 2、application-dev.yml 开发环境

1. spring（spring-database）（spring-database-druid java连接数据库（连接池，监控））数据库 ==（spring-redis）==redis 配置redis服务地址，连接服务
2. minio 配置minio服务地址，连接服务
3. loggin 配置项目不同日志记录等级
4. logstash 配置日志存储路劲，在线记录日志

```yml
spring:
  datasource: #配置jdbc数据源
    url: jdbc:mysql://localhost:3306/power?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asiz/Shanghai&useSSL=false
    username: root
    userword: xwwfy
    druid:  # 可以是c3p0，dbcp或者druid、HikariCP等任意数据源
      initial-size: 5 #连接池初始化大小
      min-idle: 10 #最小空闲连接数
      max-active: 20 #最大连接数
      web-stat-filter: # 这个过滤器的作用就是统计web应用请求中所有的数据库信息
        exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*ico,/druid/*" #不统计这些请求数据
      stat-view-servlet: #访问监控网页的登录用户名和密码
        login-username: druid
        login-password: druid

    redis:
      host: localhost
      database: 0 # Redis数据库索引（默认为0）
      port: 6379 # Redis服务器连接端口
      password: # Redis服务器连接密码（默认为空）

minio:
  endpoint: http://localhost:9000 #MinIO服务所在地址
  bucketName: mall #存储桶名称
  accessKey: minioadmin #访问的key
  secretKey: minioadmin #访问的秘钥

# 修改日志级别
logging:
  level:
    # 设置日志的默认级别为 info
    root: info
    # 设置com.example.poweradmin包下的日志级别是debug
    com.example.poweradmin: debug
  # 指定日志文件输出
  file:
    name: F:\java_project\mall\shevinfy_power_e-commerce\power e-commerce\document\log\poweradmin_log.txt

logstash:
  host: localhost
  enableInnerLog: false

```

##### 2.1、druid配置

**配置Druid数据源（连接池）：** 如同以前 c3p0、dbcp 数据源可以设置数据源连接初始化大小、最大连接数、等待时间、最小连接数 等一样，Druid 数据源同理可以进行设置；

**配置 Druid web 监控 filter（WebStatFilter）：** **这个过滤器的作用就是统计 web 应用请求中所有的数据库信息**，比如 发出的 sql 语句，sql 执行的时间、请求次数、请求的 url 地址、以及seesion 监控、数据库表的访问次数 等等。

**配置 Druid 后台管理 Servlet（StatViewServlet）：** Druid 数据源具有监控的功能，并提供了一个 web 界面方便用户查看，类似安装 路由器 时，人家也提供了一个默认的 web 页面；需要设置 Druid 的后台管理页面的属性，比如 **登录账号、密码**等；

##### 2.2、elasticsearch：

Elasticsearch 是一个基于Lucene的分布式、高扩展、高实时的搜索与数据分析引擎，常被用作**全文检索、结构化搜索、分析**以及这三个功能的组合。它可以被下面这样准确的形容：

- 一个分布式的实时文档存储，**每个字段 可以被索引与搜索**
- 一个分布式**实时分析搜索引擎**
- 能胜任上百个服务节点的扩展，并支持 PB 级别的结构化或者非结构化数据

##### 2.3.配置jdbc数据源

可以是c3p0、dbcp或者druid、HikariCP等任意数据源

```yml
spring:
  datasource:
    url: jdbc:mysql://192.168.9.51:3306/testdb
    username: root
    password: 123456
    druid:
      initial-size: 5 #连接池初始化大小
      min-idle: 10 #最小空闲连接数
      max-active: 20 #最大连接数
```

#### 3、applicattion-prov.yml 生产环境

```yml
spring:
  datasource:
    url: jdbc:mysql://db:3306/power?useUnicode=ture&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: xwwfy

    druid:
      inttial-size: 5 #连接池初始化大小
      min-idle: 10 #最小空闲连接数
      max-active: 20 #最大连接数
      web-stat-filter:
        exclusions: "*.jpg,*.png,*.gif,*.css,*.js,*.ico,/druid/*" #不统计这些请求数据
      stat-view-servlet: #访问监控网页的登录用户名和密码
        login-username: druid
        login-password: druid

  redis:
    host: redis # Redis服务器地址
    database: 0 # Redis数据库索引（默认为0）
    port: 6379
    password:
    timeout:

minio:
  endpoint: http://192.168.3.101:9090 #MinIO服务所在地址
  bucketName: mall #存储桶名称
  accessKey: minioadmin #访问的key
  secretKey: minioadmin #访问的秘钥

logging:
  file:
    path: /var/logs
  level:
    root: info
    com.macro.mall: info

logstash:
  host: logstash
```

与dev基本一样，有一些细节配置改一下就好了，比如：==接口都要改成上线服务器的地址。文件位置也是服务器的位置。==

### 项目结构说明

先写poweradmin

![img](images/1.png)

# ============== power项目的架构： ======

# power e-commerce整合SpringBoot+MyBatis+swagger+redis+SpringSecurity+JWT+SpringTask+Elasticsearch+MongoDB+RabbitMQ+OSS搭建基本骨架

[macrozheng](https://www.macrozheng.com/)2019年5月6日Mall学习教程架构篇SpringBootMyBatis

------

整合SpringBoot+MyBatis搭建基本骨架，实现基本的CRUD操作及通过PageHelper实现分页查询。

## 项目使用框架介绍

### SpringBoot

> SpringBoot可以让你快速构建基于Spring的Web应用程序，内置多种==Web容器(如Tomcat)==，通过启动入口程序的main函数即可运行。

### PagerHelper

> MyBatis分页插件，简单的几行代码就能==实现分页==，在与SpringBoot整合时，只要==整合了PagerHelper就自动整合了MyBatis。==



```java
PageHelper.startPage(pageNum, pageSize);
//之后进行查询操作将自动进行分页
List<PmsBrand> brandList = brandMapper.selectByExample(new PmsBrandExample());
//通过构造PageInfo对象获取分页信息，如当前页码，总页数，总条数
PageInfo<PmsBrand> pageInfo = new PageInfo<PmsBrand>(list);
```

### Druid

> alibaba开源的数据库连接池，号称Java语言中最好的数据库连接池。

### Mybatis generator

> MyBatis的代码生成器，可以根据数据库生成model、mapper.xml、mapper接口和Example，通常情况下的==单表查询==不用再手写mapper。

## power-mbg（MyBatis搭建基本骨架）

### 参考资料：

[MyBatis：Mybatis逆向工程问题记录 - 怒吼的萝卜 - 博客园 (cnblogs.com)](https://www.cnblogs.com/nhdlb/p/10904567.html)

generetorConfig配置文件详细介绍：[(21条消息) generatorConfiguration配置详解_无形风的博客-CSDN博客_generatorconfig配置](https://blog.csdn.net/xukaiqiang123/article/details/125993327)

==写每一个子模块的时候都要先写pom.xml依赖配置文件，导入对应的依赖==

### pom.xml（power-admin）：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example.power</groupId>
    <artifactId>power-admin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>power-admin</name>
    <description>power-admin project for power_e-commerce</description>

    <parent>
        <groupId>com.example.power</groupId>
        <artifactId>power_e-commerce</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <!--依赖管理-->
    <dependencies>
        <dependency>
            <groupId>com.example.power</groupId>
            <artifactId>power-mbg</artifactId>
        </dependency>
        <dependency>
            <groupId>com.example.power</groupId>
            <artifactId>power-security</artifactId>
        </dependency>
        <!--minio存储服务，用来存储对象可以整合OSS-->
        <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
        </dependency>
        <!--oss对象存储服务-->
        <dependency>
            <groupId>com.aliyun.oss</groupId>
            <artifactId>aliyun-sdk-oss</artifactId>
        </dependency>
        <dependency>
            <groupId>io.swagger.core.v3</groupId>
            <artifactId>swagger-annotations</artifactId>
            <version>2.2.0</version>
        </dependency>

    </dependencies>

</project>

```

#### pom.xml（power-mbg）文件总结：

我们是mybatis的自动生成代码子模块，

- 需要mybatis的依赖，

- 连接数据库，需要mysql-connection-java依赖，

- 数据库缓存druid依赖。

- 分页依赖，

- 通用类依赖。

- 和每一个子模块都要引入父项目的pom.xml文件

- ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <parent>
          <groupId>com.example.power</groupId>
          <artifactId>power_e-commerce</artifactId>
          <version>0.0.1-SNAPSHOT</version>
      </parent>
  
      <groupId>com.example.power</groupId>
      <artifactId>power-mbg</artifactId>
      <version>0.0.1-SNAPSHOT</version>
  
      <name>power-mbg</name>
      <description>power-mbg project for power_e-commerce</description>
  
      <dependencies>
          <!--通用类依赖导入-->
          <dependency>
              <groupId>com.example.power</groupId>
              <artifactId>power-common</artifactId>
          </dependency>
          <!--分页依赖-->
          <dependency>
              <groupId>com.github.pagehelper</groupId>
              <artifactId>pagehelper-spring-boot-starter</artifactId>
          </dependency>
          <!--java与数据库连接池druid-->
          <dependency>
              <groupId>com.alibaba</groupId>
              <artifactId>druid-spring-boot-starter</artifactId>
          </dependency>
          <!--mybatis代码生成器mybatis generator-->
          <dependency>
              <groupId>org.mybatis.generator</groupId>
              <artifactId>mybatis-generator-core</artifactId>
          </dependency>
          <!--java连接mysql-->
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
          </dependency>
          <dependency>
              <groupId>io.swagger</groupId>
              <artifactId>swagger-annotations</artifactId>
              <version>1.6.2</version>
          </dependency>
      </dependencies>
  
  </project>
  
  ```

  

### Mybatis generator 配置文件

### 1.generatorConfig.xml配置文件

> 配置数据库连接，Mybatis generator生成model、mapper接口及mapper.xml的路径。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--绑定generator.properties配置-->
    <properties resource="generator.properties"/>
    <context id="MySqlContext" targetRuntime="MyBatis3" defaultModelType="flat">
        <!--property（所有物）添加分隔符，设置编码-->
            <!--begin和end默认双引号mysql要用单引号-->
            <property name="beginningDelimiter" value="`"/>
            <property name="endingDelimiter" value="`"/>
            <!--特殊指定编码-->
            <property name="javaFileEncoding" value="UTF-8"/>
        <!--plugin（插件）修改或者扩展MBG生成的代码-->
            <!--为模型生成序列化方法-->
            <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>
            <!--为生成的Java模型创建一个toString方法-->
            <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>
            <!--生成mapper.xml时覆盖原文件-->
            <plugin type="org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin" />
        <!--commentGenerator（评论生成器）-->
            <commentGenerator type="com.example.power.CommentGenerator">
                <!--是否去除自动生成的注释 true 是  false 否-->
                <property name="suppressAllComments" value="true"/>
                <!--是否去除自动生成注释时间 true 是 false 否-->
                <property name="suppressDate" value="true"/>
                <property name="addRemarkComments" value="true"/>
            </commentGenerator>
        <!--jdbc-->
            <jdbcConnection driverClass="${jdbc.driverClass}"
                            connectionURL="${jdbc.connectionURL}"
                            userId="${jdbc.userId}"
                            password="${jdbc.password}">
                    <!--解决mysql驱动升级到8.0后不生成指定数据库代码的问题-->
                    <property name="nullCatalogMeansCurrent" value="true"/>
            </jdbcConnection>
        <!--控制生成的实体类-->
            <javaModelGenerator targetPackage="com.example.powermbg.model" targetProject="power-mbg\src\main\java"/>
        <!--我们的targetRunTime目标是MyBatis3  如果指定了sqlMapGenerator  MBG就会生成xml的SQL映射文件和实体类-->
            <sqlMapGenerator targetPackage="com.example.powermbg.mapper" targetProject="power-mbg\src\main\resources"/>
        <!--如果不配置该元素，就不会生成Mapper接口-->
            <javaClientGenerator type="XMLMAPPER" targetPackage="com.example.powermbg.mapper"
                                 targetProject="power-mbg\src\main\java"/>
        <!--配置通过内省的表，只有配置的才会生成实体类和其他文件-->
            <table tableName="%">       <!--生成全部表tableName设为%-->
                <generatedKey column="id" sqlStatement="Mysql" identity="true" />
            </table>
    </context>
</generatorConfiguration>

```



#### properties元素：

这个元素用来指定外部的属性元素，不是必须的元素。通过resource或者url来指定属性文件的位置

#### 子元素：

这些子元素（==有严格的配置顺序==）包括：

```xml
<property> (0个或多个)
<plugin> (0个或多个)
<commentGenerator> (0个或1个)
<jdbcConnection> (1个)
<javaTypeResolver> (0个或1个)
<javaModelGenerator> (1个)
<sqlMapGenerator> (0个或1个)
<javaClientGenerator> (0个或1个)
<table> (1个或多个)
```

**反单引号(`)\**就是\**分隔符**，**分隔符**可以用于**表名**或者**列名**。

##### property标签支持的属性：

```
autoDelimitKeywords
beginningDelimiter
endingDelimiter
javaFileEncoding
javaFormatter
xmlFormatter

autoDelimitKeyword：当表名或者字段名为SQL关键字的时候，可以设置该属性为true，MBG会自动给表名或字段名添加分隔符。

beginningDelimiter和endingDelimiter属性：
由于beginningDelimiter和endingDelimiter的默认值为双引号(")，在Mysql中不能这么写，所以还要将这两个默认值改为反单引号(`)，配置如下：

属性javaFileEncoding设置要使用的Java文件的编码，默认使用当前平台的编码，只有当生产的编码需要特殊指定时才需要使用，一般用不到。

最后两个javaFormatter和xmlFormatter属性可能会很有用，如果你想使用模板来定制生成的java文件和xml文件的样式，你可以通过指定这两个属性的值来实现。
```

#####  plugin 元素：

元素用来**定义一个插件**。插件用于**扩展或修改**通过MyBatis Generator (MBG)==代码生成器生成的代码==。

##### commentGenerator元素：

该元素最多可以配置1个。

这个元素非常有用，相信很多人都有过这样的需求，就是希望MBG生成的代码中可以包含**注释信息**，具体就是生成表或字段的备注信息。

默认的实现类中提供了两个可选属性，需要通过属性进行配置。

```
suppressAllComments:**阻止**生成注释，默认为false
suppressDate:**阻止**生成的注释包含时间戳，默认为false
```

#####  jdbcConnection 元素：

用于指定数据库连接信息，该元素必选，并且只能有一个。

配置该元素只需要注意如果JDBC驱动不在**classpath**下，就需要通过元素引入jar包，这里**推荐**将jar包放到**classpath**下。

该元素有两个必选属性:

```
driverClass:访问数据库的JDBC驱动程序的完全限定类名
connectionURL:访问数据库的JDBC连接URL
12
```

该元素还有两个可选属性:

```
userId:访问数据库的用户ID
password:访问数据库的密码
```

##### javaModelGenerator 元素：

该元素必须配置一个，并且最多一个。

该元素用来控制生成的实体类，根据中配置的defaultModelType，一个表可能会对应生成多个不同的实体类。一个表对应多个类实际上并不方便，所以前面也推荐使用flat，这种情况下一个表对应一个实体类。

该元素只有两个属性，都是必选的。

```
targetPackage:生成实体类存放的包名，一般就是放在该包下。实际还会受到其他配置的影响(<table>中会提到)。
targetProject:指定目标项目路径，可以是绝对路径或相对路径（如 targetProject="src/main/java"）
```

#####  sqlMapGenerator元素

该元素可选，最多配置一个。但是有如下两种必选的特殊情况：

```xml
如果targetRuntime目标是**iBATIS2**，该元素必须配置一个。
如果targetRuntime目标是**MyBatis3**，只有当<javaClientGenerator>需要XML时，该元素必须配置一个。 如果没有配置<javaClientGenerator>，则使用以下的规则：
    如果指定了一个<sqlMapGenerator>，那么MBG将只生成XML的SQL映射文件和实体类。
    如果没有指定<sqlMapGenerator>，那么MBG将只生成实体类。
1234
```

该元素只有两个属性（和前面提过的的属性含义一样），都是必选的。

```
targetPackage:生成实体类存放的包名，一般就是放在该包下。实际还会受到其他配置的影响(<table>中会提到)。
targetProject:指定目标项目路径，可以是绝对路径或相对路径（如 targetProject="src/main/resource
```

#####  javaClientGenerator 元素：

该元素可选，最多配置一个。

如果不配置该元素，就不会生成Mapper接口。

##### table 元素：

该元素至少要配置一个，可以配置多个。

该元素用来配置要通过内省的表。只有配置的才会生成实体类和其他文件。

该元素有一个必选属性：

```
tableName：指定要生成的表名，可以使用SQL通配符匹配多个表。
1
```

例如要生成全部的表，可以按如下配置：

```java
<table tableName="%" />
```

### 2.generator.properties

```properties
jdbc.driverClass=com.mysql.cj.jdbc.Driver
jdbc.connectionURL=jdbc:mysql://localhost:3306/power?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
jdbc.userId=root
jdbc.password=xwwfy
```



### 3.Generator类（可运行类）

运行Generator的main函数生成代码

```java
package com.example.powermbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static void main(String[] args) throws Exception{
        // MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<String>();
        // 当生成代码重复时，覆盖原代码
        boolean overwrite = true;
        // 读取我们的MBG配置文件
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        // 创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,callback,warnings);
        // 执行生成代码
        myBatisGenerator.generate(null);
        // 输出警告信息
        for (String warning:warnings
             ) {
            System.out.println(warning);
        }

    }
}
```

### 4.CommentGenerator.java(mybatis代码自动生成 自定义注解生成)

```java
package com.example.powermbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 *  自定义注释生成器
 *  继承默认DefaultCommentGenerator类重写方法自定义
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;
    private static final String EXAMPLE_SUFFIX="Example";
    private static final String MAPPER_SUFFIX="Mapper";
    private static final String API_MODEL_PROPERTY_CLASS_NAME="io.swagger.annotations.ApiModelProperty";

    /**
     * 设置用户胚珠参数
     * @param properties
     */
    @Override
    public void addConfigurationProperties(Properties properties){
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 给字段添加注释
     * @param field 这个field要选择org.mybatis.generator.api.dom.java.Field这个包。
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        // 根据参数和备注信息判断信息判断是否添加swagger注释信息
        if(addRemarkComments&&StringUtility.stringHasValue(remarks)){
            //数据库中特殊字符需要转义
            if(remarks.contains("\"")){
                remarks = remarks.replace("\"","'");
            }
            // 给model的字段添加swagger注解
            field.addJavaDocLine("@ApiModelProperty(value = \""+remarks+"\")");
        }

    }


    /**
     *  给Model的字段添加注释
     */
    private void addFieldJavaDoc(Field field, String remarks) {
        // 文档注释开始
        field.addJavaDocLine("/**");
        //获取数据库字段的备注信息
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for (String remarkLine:remarkLines
        ) {
            field.addJavaDocLine("*"+remarkLine);
        }
        addJavadocTag(field,false);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        // 只在model中添加swagger注解类的导入
        if(!compilationUnit.getType().getFullyQualifiedName().contains(MAPPER_SUFFIX) && !compilationUnit.getType().getFullyQualifiedName().contains(MAPPER_SUFFIX)) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_CLASS_NAME));
        }
    }
}

```

- addFieldComment：用备注和参数判断去自动生成**字段/属性**的swagger注解。
- addFieldJavaDoc：自动生成**model**上的注释。通过addJavadocLine去写注释。
- addJavaFileComment：这个是用来说明自动生成swagger注解是在哪个地方。我写的是只在model中添加swagger注释

### power-mbg子模块总结：

power-mbg子模块就只有4个文件要自己编写，然后创建3个文件目录去存放自动生成代码。

#### 4个文件：

- generatorConfig.xml：这个文件是写mybatis-mbg自动生成代码的配置。
  - 可以用插件写生成的代码要加toString方法或者序列化。
  - 可以用commentGeneretor去写注释
  - jdbcConnection去连接java与mysql
  - javaModelGenerator去标志生成的实体类Model放在哪个文件。本项目放在model文件夹。
  - sqlMapGenerator：标志生成的xml文件烦恼在哪个文件，本项目放在resource的文件夹的mapper下面。
  - javaClientGenerator是存放生成的接口，本项目放在java文件夹下的mapper下面。
  - table是指定数据库中哪个表。%代表全部
- generator.properties：jdbc的配置信息放在这里比如，java-mysql驱动，连接配置信息（ip：端口，地区时间，连接是否为true这些）
- Generator.java：自动代码生成的启动类。用来输出我们警告信息，指定是否覆盖原代码，读取MBG配置文件，执行生成代码。
- CommentGenerator.java：写自动生成代码时自定义注释和注解。addFieldComment给字段添加注释，addFieldJavaDoc给model字段添加注释，addJavaFileComment写只在model上添加注释

#### 3个文件目录：

java下的mapper：存放mapper接口文件

java下的model：存放实体类

resource下的mapper：存放xml映射的sql文件。



==写好mybatis-mbg子模块的以后，我们需要在业务上写service，controller层和MyBatisConfig配置类去动态生成mapper接口的路径==

## ==power-admin：==

### 文件信息：

```
java com.example.power
	bo		业务对象
	config 	配置
	controller  控制层（响应用户请求）
	dao		Mapper层（抽象类：xxxMapper.java文件，交体实现在xxxMapper.xml）
	dto		数据传输对象
	service	接口（接口实现类）
		impl	实现层
	validator	状态约束校验器
	PowerAdminApplication
resource
	dao
	META-INF
	application.yml
	application-dev.yml
	application-prod.yml
```

#### dto（`Data Transfer Object`）：

**数据传输对象**，这个概念来源于J2EE的设计模式，原来的目的是为了EJB的分布式应用提供粗粒度的数据实体，以减少分布式调用的次数，从而提高分布式调用的性能和降低网络负载，但在这里，更符合泛指用于[展示层](https://www.zhihu.com/search?q=展示层&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"article"%2C"sourceId"%3A"502192814"})与服务层之间的数据传输对象。

#### BO（`Business Object`）：

**业务对象**，把业务逻辑封装为一个对象，这个对象可以包括一个或多个其它的对象。

#### Controller（控制层）：

**后台总控制器，接收所有前台传过来的请求**，再把它分配到它需要调的service里，相当于前后台的入口（**Controller可以有多个，比如可以有10个不频繁访问的服务，可以用一个Controller来控制，另外两个访问频繁的用另一个Controller控制，分开均衡一点）**

#### Services（业务层）【应用层】：

==所有的逻辑在Services里，逻辑，算法，都是由Services构成的==（可以理解成Services里面写核心算法，比如if,else，通过JAVA来实现逻辑的变化，数据的变化）**一个按钮对应一个service，**所有请求先到controller里面，然后再去找它要哪个服务，再创建一个对应的实例。（简单的项目也可以不用控制器，直接前台调services,但一般都会有一个控制器来管理一下服务的请求）

#### Dao（持久层）：

接口（应用层和数据层的接口），==只定义对象，不定义具体的内容==，之所以有Dao，是为了让**Services可以直接用里面定义好的东西**，没有Dao的话是调不到的，相当于是在Services里调的是一个接口，Spring运行的时候Spring和Mybatis实时在里面生成方法，实际生成的方法在jar包里面。每添加一个方法，一个SQL，要在Dao里添加一个接口的定义。通过Dao找Mapper文件下的同名的SQL语句，一个Dao对应一个Mapper

#### Mapper【数据层】：

**写在xml文件里，里面写对应的SQL语句**，实现在数据库中怎么查找。所有的xml都是配置文件，给程序去读取的

#### Model：

保存着与数据库表所对应的结构体，作用是方便用表的结构体，增加Mapper的易用性，把所有SQL字段变成变量放在里面，根据表结构自动生成

#### 参考资料：

[SpringBoot框架中各层（DTO、DAO、Service、Controller）理解 - Chen洋 - 博客园 (cnblogs.com)](https://www.cnblogs.com/cy0628/p/15162816.html)

 [(21条消息) vo、dto、bo、do、po的概念理解以及与controller、service、dao层的对应关系_Albertliuc的博客-CSDN博客_controller dto vo](https://blog.csdn.net/m0_70651612/article/details/125833766)

[(21条消息) Java中Validator的使用_Ark方舟的博客-CSDN博客_java validator](https://blog.csdn.net/weixin_40516924/article/details/121696707)

[(21条消息) Java Web中的mapper，service，controller，model究竟分别是什么作用？_栗少的博客-CSDN博客_java中controller的作用](https://blog.csdn.net/weixin_38556197/article/details/115026671)



==写每一个子模块的时候都要先写pom.xml依赖配置文件，导入对应的依赖==

### pom.xml（power-admin）：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example.power</groupId>
    <artifactId>power-admin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>power-admin</name>
    <description>power-admin project for power_e-commerce</description>

    <parent>
        <groupId>com.example.power</groupId>
        <artifactId>power_e-commerce</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <!--依赖管理-->
    <dependencies>
        <dependency>
            <groupId>com.example.power</groupId>
            <artifactId>power-mbg</artifactId>
        </dependency>
        <dependency>
            <groupId>com.example.power</groupId>
            <artifactId>power-security</artifactId>
        </dependency>
        <!--minio存储服务，用来存储对象可以整合OSS-->
        <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
        </dependency>
        <!--oss对象存储服务-->
        <dependency>
            <groupId>com.aliyun.oss</groupId>
            <artifactId>aliyun-sdk-oss</artifactId>
        </dependency>
        <dependency>
            <groupId>io.swagger.core.v3</groupId>
            <artifactId>swagger-annotations</artifactId>
            <version>2.2.0</version>
        </dependency>

    </dependencies>

</project>

```

依赖

#### minio：

==分布式==文件存储系统minio

##### 1.minio应用场景：

互联网海量==非结构化数据==的存储需求

- 电商网站：海量商品图片。
- 视频网站：海量视频文件
- 网盘：海量文件
- 社交网站：海量图片

##### 2.minio简介：

[Minio](https://so.csdn.net/so/search?q=Minio&spm=1001.2101.3001.7020) 是一个基于 Apache License v2.0 开源协议的**对象存储服务**。它兼容**亚马逊 S3 云存储服务接口**，非常适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份数据和容器/虚拟机镜像等，而一个对象文件可以是任意大小，从几 kb 到最大 5T 不等。

Minio 是一个非常轻量的服务,可以很简单的和其他应用的结合，类似 NodeJS, Redis 或者 MySQL。==Minio 不仅提供了服务器、Web 访问、客户端，还提供了 Docker 安装，各种语言的 SDK、实例、实战秘籍等等，支持**分布式部**署==。

##### 3.Minio 的特点：

- 1.高性能：Minio 支持一个对象文件可以是任意大小，从几 kb 到最大 5T 不等。
- 2.可扩展:不同 Minio 集群可以组成联邦，并形成一个全局的命名空间，并跨越多个数据中心
- 3.云原生:容器化、基于 K8S 的编排、多租户支持。
- 4.Amazon S3 兼容。
- 5.存储多种后端。

- 6.SDK 支持:有类似 Java、Python 或 Go 等语言的 sdk 支持。
- 7.Lambda 计算：Minio 服务器通过其兼容 AWS SNS / SQS 的事件通知服务触发 Lambda 功能。

- 8.有图形化操作界面
- 9.功能简单

- 10.支持纠删码:Minio 使用纠删码、Checksum 来防止硬件错误和静默数据污染。
- 11.读写程序优异
- 12.低冗余，磁盘损坏高容忍

#####4. MinIO的基础概念：

术语	含义
Object	存储到MinIO的基本对象。如文件，字节流等
==Bucket==	存储Object的逻辑空间，**每个Bucket之间的数据时相互隔离的**。对于用户而言，相当于存放文件的顶层文件夹。
Drive	存储Object的磁盘。在MinIO启动时，以参数的方式传入。
==Set==	
一组Drive的集合。根据集群规模自动划分Set，每个Set中的Drive分布在不同位置。

-  一个对象存储在一个Set上。

-  一个集群划分成多个Set。

- 一个Set包含的Drive数量是固定的，默认由系统根据集群规模自动计算出。

- 一个Set中的Drive尽可能分布在不同的节点上。

EC	
**纠删码（Erasure Code）**，保证高可靠。

- n 份原始数据，m份编码数据。

- 任意小于等于m份的数据丢失，以通过剩下的数据还原出来。

##### 本地存储集成minio实现oss对象存储：

[(22条消息) 本地存储集成minio实现oss对象存储_菜鸟歪歪歪的博客-CSDN博客_minio oss](https://blog.csdn.net/csdnwangshaopeng/article/details/125622450)

[(22条消息) oss数据库---MINIO简要使用说明_Lagom365的博客-CSDN博客_minio oss](https://blog.csdn.net/weixin_42126753/article/details/125279972?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~Rate-5-125279972-blog-125622450.pc_relevant_multi_platform_featuressortv2dupreplace&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~Rate-5-125279972-blog-125622450.pc_relevant_multi_platform_featuressortv2dupreplace&utm_relevant_index=8)



##### 参考文章：

[(22条消息) Minio是什么_地推的博客-CSDN博客_minio](https://blog.csdn.net/douyinbuwen/article/details/124511371)

https://blog.csdn.net/douyinbuwen/article/details/124511371

https://blog.csdn.net/liyazhen2011/article/details/123268823

[(22条消息) 本地存储集成minio实现oss对象存储_菜鸟歪歪歪的博客-CSDN博客_minio oss](https://blog.csdn.net/csdnwangshaopeng/article/details/125622450)

[(22条消息) oss数据库---MINIO简要使用说明_Lagom365的博客-CSDN博客_minio oss](https://blog.csdn.net/weixin_42126753/article/details/125279972?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~Rate-5-125279972-blog-125622450.pc_relevant_multi_platform_featuressortv2dupreplace&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~Rate-5-125279972-blog-125622450.pc_relevant_multi_platform_featuressortv2dupreplace&utm_relevant_index=8)



#### OSS（阿里云对象存储服务）：

##### OSS工作原理

数据以对象（Object）的形式存储在OSS的存储空间（Bucket ）中。如果要使用OSS存储数据，您需要先创建Bucket，并指定Bucket的地域、访问权限、存储类型等属性。创建Bucket后，您可以将数据以Object的形式上传到Bucket，并指定Object的文件名（Key）作为其唯一标识。

OSS以HTTP RESTful API的形式对外提供服务，访问不同地域需要不同的访问域名（Endpoint）。当您请求访问OSS时，OSS通过使用访问密钥（AccessKey ID和AccessKey Secret）对称加密的方法来验证某个请求的发送者身份。

Object操作在OSS上具有原子性和强一致性。

- 存储空间

  存储空间是用户用于存储对象（Object）的容器，所有的对象都必须隶属于某个存储空间。存储空间具有各种配置属性，包括地域、访问权限、存储类型等。用户可以根据实际需求，创建不同类型的存储空间来存储不同的数据。

- 对象

  对象是OSS存储数据的基本单元，也被称为OSS的文件。和传统的文件系统不同，对象没有文件目录层级结构的关系。对象由元信息（Object Meta），用户数据（Data）和文件名（Key）组成，并且由存储空间内部唯一的Key来标识。对象元信息是一组键值对，表示了对象的一些属性，比如最后修改时间、大小等信息，同时用户也可以在元信息中存储一些自定义的信息。

- 对象名称

  在各语言SDK中，ObjectKey、Key以及ObjectName是同一概念，均表示对Object执行相关操作时需要填写的Object名称。例如向某一存储空间上传Object时，ObjectKey表示上传的Object所在存储空间的完整名称，即包含文件后缀在内的完整路径，如填写为abc/efg/123.jpg。

- 地域

  Region表示OSS的数据中心所在物理位置。用户可以根据费用、请求来源等选择合适的地域创建Bucket。一般来说，距离用户更近的Region访问速度更快。更多信息，请参见[访问域名和数据中心](https://help.aliyun.com/document_detail/31837.htm#concept-zt4-cvy-5db)。

- 访问域名

  Endpoint表示OSS对外服务的访问域名。OSS以HTTP RESTful API的形式对外提供服务，当访问不同的Region的时候，需要不同的域名。通过内网和外网访问同一个Region所需要的Endpoint也是不同的。例如杭州Region的外网Endpoint是oss-cn-hangzhou.aliyuncs.com，内网Endpoint是oss-cn-hangzhou-internal.aliyuncs.com。更多信息，请参见[访问域名和数据中心](https://help.aliyun.com/document_detail/31837.htm#concept-zt4-cvy-5db)。

- 访问密钥

  AccessKey简称AK，指的是访问身份验证中用到的AccessKey ID和AccessKey Secret。OSS通过使用AccessKey ID和AccessKey Secret对称加密的方法来验证某个请求的发送者身份。AccessKey ID用于标识用户；AccessKey Secret是用户用于加密签名字符串和OSS用来验证签名字符串的密钥，必须保密。

- 强一致性

  Object操作在OSS上具有原子性，操作要么成功要么失败，不会存在有中间状态的Object。OSS保证用户一旦上传完成之后读到的Object是完整的，OSS不会返回给用户一个部分上传成功的Object。

  Object操作在OSS同样具有强一致性，用户一旦收到了一个上传（PUT）成功的响应，该上传的Object就已经立即可读，且数据已经冗余写入到多个设备中。不存在上传的中间状态，即不会出现read-after-write却无法读取到数据的情况。删除操作也类似，即用户成功删除指定的Object后，该Object立即变为不存在。

关于OSS基本概念的完整介绍，请参见[基本概念](https://help.aliyun.com/document_detail/31827.htm#concept-izx-fmt-tdb)。

##### OSS重要特性

- 版本控制

  版本控制是针对存储空间（Bucket）级别的数据保护功能。开启版本控制后，针对数据的覆盖和删除操作将会以历史版本的形式保存下来。您在错误覆盖或者删除文件（Object）后，能够将Bucket中存储的Object恢复至任意时刻的历史版本。有关版本控制的更多信息，请参见[版本控制介绍](https://help.aliyun.com/document_detail/109695.htm#concept-jdg-4rx-bgb)。

- Bucket Policy

  Bucket拥有者可通过Bucket Policy授权不同用户以何种权限访问指定的OSS资源。例如您需要进行跨账号或对匿名用户授权访问或管理整个Bucket或Bucket内的部分资源，或者需要对同账号下的不同RAM用户授予访问或管理Bucket资源的不同权限，例如只读、读写或完全控制的权限等。有关配置Bucket Policy的操作步骤，请参见[通过Bucket Policy授权用户访问指定资源](https://help.aliyun.com/document_detail/85111.htm#concept-ahc-tx4-j2b)。

- 跨区域复制

  跨区域复制（Cross-Region Replication）是跨不同OSS数据中心（地域）的Bucket自动、异步（近实时）复制Object，它会将Object的创建、更新和删除等操作从源存储空间复制到不同区域的目标存储空间。跨区域复制功能满足Bucket跨区域容灾或用户数据复制的需求。有关跨区域复制的更多信息，请参见[跨区域复制](https://help.aliyun.com/document_detail/31864.htm#concept-zjp-31z-5db)。

- 数据加密

  服务器端加密：上传文件时，OSS对收到的文件进行加密，再将得到的加密文件持久化保存；下载文件时，OSS自动将加密文件解密后返回给用户，并在返回的HTTP请求Header中，声明该文件进行了服务器端加密。有关服务器端加密的更多信息，请参见[服务器端加密](https://help.aliyun.com/document_detail/31871.htm#concept-lqm-fkd-5db)。

  客户端加密：将文件上传到OSS之前在本地进行加密。有关客户端加密的更多信息，请参见[客户端加密](https://help.aliyun.com/document_detail/73332.htm#concept-2323737)。

#### fabric8：

我们调用k8s api接口的中间商。

##### Fabric8简介

**fabric8**是一个开源**集成开发平台**，为基于[Kubernetes](http://kubernetes.io/)和[Jenkins](https://jenkins.io/)的[微服务](https://so.csdn.net/so/search?q=微服务&spm=1001.2101.3001.7020)提供[持续发布](http://fabric8.io/guide/cdelivery.html)。

使用fabric可以很方便的通过[Continuous Delivery pipelines](http://fabric8.io/guide/cdelivery.html)创建、编译、部署和测试微服务，然后通过Continuous Improvement和[ChatOps](http://fabric8.io/guide/chat.html)运行和管理他们。

[Fabric8微服务平台](http://fabric8.io/guide/fabric8DevOps.html)提供：

- [Developer Console](http://fabric8.io/guide/console.html)，是一个[富web应用](http://www.infoq.com/cn/news/2014/11/seven-principles-rich-web-app)，提供一个单页面来创建、编辑、编译、部署和测试微服务。

- [Continuous Integration and Continous Delivery](http://fabric8.io/guide/cdelivery.html)，使用 [Jenkins](https://jenkins.io/) with a [Jenkins Workflow Library](http://fabric8.io/guide/jenkinsWorkflowLibrary.html)更快和更可靠的交付软件。

- [Management](http://fabric8.io/guide/management.html)，集中式管理[Logging](http://fabric8.io/guide/logging.html)、[Metrics](http://fabric8.io/guide/metrics.html), [ChatOps](http://fabric8.io/guide/chat.html)、[Chaos Monkey](http://fabric8.io/guide/chaosMonkey.html)，使用[Hawtio](http://hawt.io/)和[Jolokia](http://jolokia.org/)管理Java Containers。

- [Integration](http://fabric8.io/guide/ipaas.html) *Integration Platform As A Service* with [deep visualisation](http://fabric8.io/guide/console.html) of your [Apache Camel](http://camel.apache.org/) integration services, an [API Registry](http://fabric8.io/guide/apiRegistry.html) to view of all your RESTful and SOAP APIs and [Fabric8 MQ](http://fabric8.io/guide/fabric8MQ.html) provides *Messaging As A Service* based on [Apache ActiveMQ](http://activemq.apache.org/)。

- Java Tools

   帮助Java应用使用

  Kubernetes

  :

  - [Maven Plugin](http://fabric8.io/guide/mavenPlugin.html) for working with [Kubernetes](http://kubernetes.io/) ，这真是极好的
  - [Integration and System Testing](http://fabric8.io/guide/testing.html) of [Kubernetes](http://kubernetes.io/) resources easily inside [JUnit](http://junit.org/) with [Arquillian](http://arquillian.org/)
  - [Java Libraries](http://fabric8.io/guide/javaLibraries.html) and support for [CDI](http://fabric8.io/guide/cdi.html) extensions for working with [Kubernetes](http://kubernetes.io/).

##### Fabric8微服务平台

Fabric8提供了一个完全集成的开源微服务平台，可在任何的[Kubernetes](http://kubernetes.io/)和[OpenShift](http://www.openshift.org/)环境中开箱即用。

整个平台是基于微服务而且是模块化的，你可以按照微服务的方式来使用它。

微服务平台提供的服务有：

- 开发者控制台，这是一个富Web应用程序，它提供了一个单一的页面来创建、编辑、编译、部署和测试微服务。
- 持续集成和持续交付，帮助团队以更快更可靠的方式交付软件，可以使用以下开源软件：
  - [Jenkins](https://jenkins.io/)：CI／CD pipeline
  - [Nexus](http://www.sonatype.org/nexus/)： 组件库
  - [Gogs](http://gogs.io/)：git代码库
  - [SonarQube](http://www.sonarqube.org/)：代码质量维护平台
  - [Jenkins Workflow Library](http://fabric8.io/guide/jenkinsWorkflowLibrary.html)：在不同的项目中复用[Jenkins Workflow scripts](https://github.com/fabric8io/jenkins-workflow-library)
  - [Fabric8.yml](http://fabric8.io/guide/fabric8YmlFile.html)：为每个项目、存储库、聊天室、工作流脚本和问题跟踪器提供一个配置文件
- [ChatOps](http://fabric8.io/guide/chat.html)：通过使用[hubot](https://hubot.github.com/)来开发和管理，能够让你的团队拥抱DevOps，通过聊天和系统通知的方式来[approval of release promotion](https://github.com/fabric8io/fabric8-jenkins-workflow-steps#hubotapprove)
- [Chaos Monkey](http://fabric8.io/guide/chaosMonkey.html)：通过干掉[pods](http://fabric8.io/guide/pods.html)来测试系统健壮性和可靠性
- 管理
  - [日志](http://fabric8.io/guide/logging.html) 统一集群日志和可视化查看状态
  - [metris](http://fabric8.io/guide/metrics.html) 可查看历史metrics和可视化

##### 参考文章：

[(22条消息) Springboot使用fabric8调用Kubernetes Api(token方式)_Zhangmaoyang的博客-CSDN博客](https://blog.csdn.net/Zhangmaoyang/article/details/123003202)

[fabric8是神门 - 搜索 (bing.com)](https://cn.bing.com/search?q=fabric8是神门&qs=n&form=QBRE&sp=-1&pq=fabric8是shen'men&sc=0-16&sk=&cvid=597628E2D2F14E3F94004FF8CDBB8C2F&ghsh=0&ghacc=0&ghpl=)

官方文档：[Introduction | Fabric8 Documentation](http://fabric8.io/guide/)



配置子pom.xm文件时，父pom.xml与子pom.xml问题报Non-resolvable：

在子pom.xml上的父标签parent加上：

```xml
<relativePath>../parent-pom/parent-pom-service/pom.xml</relativePath>
```

[(22条消息) [Maven\] 打包报错Non-resolvable parent POM for_对码当歌，人生几何！的博客-CSDN博客](https://blog.csdn.net/u012433915/article/details/115367189)

### 添加MyBatis的Java配置

> 用于配置==需要动态生成的mapper接口的路径==

```java
package com.example.poweradmin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis相关配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.example.power.dao,com.example.power.mapper"})
public class MyBatisConfig {
}
```

#### @Configuration注释：

 **声明一个类为配置类**，用于取代bean.xml配置文件注册bean对象。

@[Configuration](https://so.csdn.net/so/search?q=Configuration&spm=1001.2101.3001.7020)是Spring的注解

#### @EnableTransactionManagement：

开启事务支持注释，是springboot的事务管理注释。

1. 在配置类上加上`@EnableTransactionManagement`开启事务支持
2. 在service实现类中加上`@Transactional`，如果该类中某个业务方法在执行时报错会进行`回滚`

#### @MapperScan

1、@Mapper注解：
作用：在**接口类上添加了@Mapper**，在编译之后会生成相应的接口实现类
添加位置：接口类上面

如果想要每个接口都要变成实现类，那么**需要在每个接口类上加上@Mapper注解，比较麻烦，解决这个问题用@MapperScan**

2、@MapperScan
作用：指定要变成实现类的接口所在的包，然后**包下面的所有接口在编译之后都会生成相应的实现类**
添加位置：是在Springboot启动类上面添加或者mybatis自动代码生成配置类

假如：添加@MapperScan(“com.winter.dao”)注解以后，com.winter.dao包下面的接口类，在编译之后都会生成相应的实现类

3、使用@MapperScan注解多个包
（实际用的时候根据自己的包路径进行修改）

### 实现Controller中的接口

> 实现PmsBrand表中的添加、修改、删除及分页查询接口。

```java
package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.service.PmsBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 品牌管理Controller
 * Created by macro on 2019/4/19.
 */
@Controller
@RequestMapping("/brand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService demoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(demoService.listAllBrand());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = demoService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("createBrand success:{}", pmsBrand);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto, BindingResult result) {
        CommonResult commonResult;
        int count = demoService.updateBrand(id, pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.debug("updateBrand success:{}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("updateBrand failed:{}", pmsBrandDto);
        }
        return commonResult;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        int count = demoService.deleteBrand(id);
        if (count == 1) {
            LOGGER.debug("deleteBrand success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<PmsBrand> brandList = demoService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(demoService.getBrand(id));
    }
}
```

#### @AutoWired注释

## 联系

1. @Autowired和@Resource注解都是作为==bean对象注入的时候使用==的
2. 两者都可以声明在字段和setter方法上

注意：如果声明在字段上，那么就不需要再写setter方法。但是本质上，该对象还是作为set方法的实参，通过执行set方法注入，只是省略了setter方法罢了

## 区别

1. @Autowired注解是Spring提供的，而@Resource注解是J2EE本身提供的
2. @Autowird注解默认通过byType方式注入，而@Resource注解默认通过byName方式注入
3. @Autowired注解注入的对象需要在IOC容器中存在，否则需要加上属性required=false，表示忽略当前要注入的bean，如果有直接注入，没有跳过，不会报错

[(101条消息) @Autowired和@Resource注解的区别和联系（十分详细，不看后悔）_莫小兮丶的博客-CSDN博客_@resource](https://blog.csdn.net/qq_45590494/article/details/114444371)

[(101条消息) Spring中的byName与byType_坦GA的博客-CSDN博客_spring byname](https://blog.csdn.net/tanga842428/article/details/54694484)

### [#](https://www.macrozheng.com/mall/architect/mall_arch_01.html#添加service接口)添加Service接口

```java
package com.macro.mall.service;

import com.macro.mall.dto.PmsBrandParam;
import com.macro.mall.model.PmsBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品品牌管理Service
 * Created by macro on 2018/4/26.
 */
public interface PmsBrandService {
    /**
     * 获取所有品牌
     */
    List<PmsBrand> listAllBrand();

    /**
     * 创建品牌
     */
    int createBrand(PmsBrandParam pmsBrandParam);

    /**
     * 修改品牌
     */
    @Transactional
    int updateBrand(Long id, PmsBrandParam pmsBrandParam);

    /**
     * 删除品牌
     */
    int deleteBrand(Long id);

    /**
     * 批量删除品牌
     */
    int deleteBrand(List<Long> ids);

    /**
     * 分页查询品牌
     */
    List<PmsBrand> listBrand(String keyword, int pageNum, int pageSize);

    /**
     * 获取品牌详情
     */
    PmsBrand getBrand(Long id);

    /**
     * 修改显示状态
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 修改厂家制造商状态
     */
    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}

```

### 实现Service接口

```java
package com.macro.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.PmsBrandParam;
import com.macro.mall.mapper.PmsBrandMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsBrandExample;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductExample;
import com.macro.mall.service.PmsBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品品牌管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
    @Autowired(required = false)
    private PmsBrandMapper brandMapper;
    @Autowired(required = false)
    private PmsProductMapper productMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StrUtil.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        return brandMapper.insertSelective(pmsBrand);
    }

    @Override
    public int updateBrand(Long id, PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);
        pmsBrand.setId(id);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StrUtil.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        //更新品牌时要更新商品中的品牌名称
        PmsProduct product = new PmsProduct();
        product.setBrandName(pmsBrand.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExampleSelective(product,example);
        return brandMapper.updateByPrimaryKeySelective(pmsBrand);
    }

    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBrand(List<Long> ids) {
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return brandMapper.deleteByExample(pmsBrandExample);
    }

    @Override
    public List<PmsBrand> listBrand(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria = pmsBrandExample.createCriteria();
        if (!StrUtil.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return brandMapper.selectByExample(pmsBrandExample);
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setShowStatus(showStatus);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(pmsBrand, pmsBrandExample);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setFactoryStatus(factoryStatus);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(pmsBrand, pmsBrandExample);
    }
}

```



### 开发接口的套路：

Controller控制层写方法映射，前端可以访问的映射。用ioc技术调用service层的类。注释用Autowired自动注入bean

Service服务层写方法。Controller可以调用service写的方法。增删查改，更新，条件查询

ServiceImpl实现服务层。具体的业务实现就在这面写，比如更新时（1.更新品牌时要更新商品中的品牌名称 2.如果创建时首字母为空，取名称的第一个为首字母）这种细节操作。或者业务流程！

实体类（domin，model）（有些项目实体类要自己写在demin包（包名字都可以），有些项目实体类用mybatis Generator技术生成在别的模块model包（包名字都可以）下）

mapper，dao是这个包存在与数据库交互的方法，与resource报下的mapper，dao包（这里是sql语句，后缀.xml）相结合就可以与数据库交互。

1. 我们需要操作的是复杂的sql语句，如（多表查询，单表条件查询）这样的。

2. 我们不需要操作的有两种可能

   1. mapper接口去实现BaseMapper这个接口，可以不用写基本的增删查改（这个就要自己去写domin实体类了在domin实体类上加上这些注释@Data
      @Builder
      @AllArgsConstructor
      @NoArgsConstructor
      @TableName("app")
   2. 用mybatis Generator这个技术去生成单表的增删查改mapper model 与resource下的mapper.xml文件

   

建表写sql 定义实体类 dao与mapper service controller maven工程分析

启动类PowerAdminApplication.java

```java
package com.example.power;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PowerAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerAdminApplication.class, args);
    }

}


注解:@SpringBootApplication（@SpringBootApplication = (默认属性)@Configuration + @EnableAutoConfiguration + @ComponentScan）
```

# power整合Swagger-UI实现在线API文档

## 项目使用框架介绍

### Swagger-UI

> Swagger-UI是HTML, Javascript, CSS的一个集合，可以动态地根据注解生成在线API文档。

#### 常用注解

- @Api：用于修饰Controller类，生成Controller相关文档信息
- @ApiOperation：用于修饰Controller类中的方法，生成接口方法相关文档信息
- @ApiParam：用于修饰接口中的参数，生成接口参数相关文档信息
- @ApiModelProperty：用于修饰实体类的属性，当实体类是请求参数或返回结果时，直接生成相关文档信息

## 整合Swagger-UI

### 添加项目依赖

> 在pom.xml中新增Swagger-UI相关依赖



```xml
<!--Swagger-UI API文档生产工具-->
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>2.7.0</version>
</dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
  <version>2.7.0</version>
</dependency>
```

### 添加Swagger-UI的配置

> 添加Swagger-UI的Java配置文件

注意：Swagger对生成API文档的范围有三种不同的选择

- 生成指定包下面的类的API文档
- 生成有指定注解的类的API文档
- 生成有指定注解的方法的API文档



```java
package com.macro.mall.tiny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.macro.mall.tiny.controller"))
                //为有@Api注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有@ApiOperation注解的方法生成API文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerUI演示")
                .description("mall-tiny")
                .contact("macro")
                .version("1.0")
                .build();
    }
}
```

### 给PmsBrandController添加Swagger注解

> 给原有的品牌管理Controller添加上Swagger注解



```java
package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 品牌管理Controller
 * Created by macro on 2019/4/19.
 */
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@Controller
@RequestMapping("/brand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService brandService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    @ApiOperation("获取所有品牌列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(brandService.listAllBrand());
    }

    @ApiOperation("添加品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = brandService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("createBrand success:{}", pmsBrand);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    @ApiOperation("更新指定id品牌信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto, BindingResult result) {
        CommonResult commonResult;
        int count = brandService.updateBrand(id, pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.debug("updateBrand success:{}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("updateBrand failed:{}", pmsBrandDto);
        }
        return commonResult;
    }

    @ApiOperation("删除指定id的品牌")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        int count = brandService.deleteBrand(id);
        if (count == 1) {
            LOGGER.debug("deleteBrand success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }

    @ApiOperation("分页查询品牌列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1")
                                                        @ApiParam("页码") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3")
                                                        @ApiParam("每页数量") Integer pageSize) {
        List<PmsBrand> brandList = brandService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @ApiOperation("获取指定id的品牌详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(brandService.getBrand(id));
    }
}
```

### 修改MyBatis Generator注释的生成规则

> CommentGenerator为MyBatis Generator的自定义注释生成器，修改addFieldComment方法使其生成Swagger的@ApiModelProperty注解来取代原来的方法注释，添加addJavaFileComment方法，使其能在import中导入@ApiModelProperty，否则需要手动导入该类，在需要生成大量实体类时，是一件非常麻烦的事。



```java
package com.macro.mall.tiny.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * 自定义注释生成器
 * Created by macro on 2018/4/26.
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;
    private static final String EXAMPLE_SUFFIX="Example";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME="io.swagger.annotations.ApiModelProperty";

    /**
     * 设置用户配置的参数
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 给字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加备注信息
        if(addRemarkComments&&StringUtility.stringHasValue(remarks)){
//            addFieldJavaDoc(field, remarks);
            //数据库中特殊字符需要转义
            if(remarks.contains("\"")){
                remarks = remarks.replace("\"","'");
            }
            //给model的字段添加swagger注解
            field.addJavaDocLine("@ApiModelProperty(value = \""+remarks+"\")");
        }
    }

    /**
     * 给model的字段添加注释
     */
    private void addFieldJavaDoc(Field field, String remarks) {
        //文档注释开始
        field.addJavaDocLine("/**");
        //获取数据库字段的备注信息
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for(String remarkLine:remarkLines){
            field.addJavaDocLine(" * "+remarkLine);
        }
        addJavadocTag(field, false);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        //只在model中添加swagger注解类的导入
        if(!compilationUnit.isJavaInterface()&&!compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)){
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }
    }
}
```

### 运行代码生成器重新生成mbg包中的代码

> 运行com.macro.mall.tiny.mbg.Generator的main方法，重新生成mbg中的代码，可以看到PmsBrand类中已经自动根据数据库注释添加了@ApiModelProperty注解

![img](https://www.macrozheng.com/assets/arch_screen_03.d7630b01.png)

### 运行项目，查看结果

#### 访问Swagger-UI接口文档地址

接口地址：http://localhost:8080/swagger-ui.html

![img](https://www.macrozheng.com/assets/arch_screen_04.4ae6d6d5.png)

#### 对请求参数已经添加说明

![img](https://www.macrozheng.com/assets/arch_screen_05.998e8ad6.png)

#### 对返回结果已经添加说明

![img](https://www.macrozheng.com/assets/arch_screen_06.dc73ab5c.png)

### 直接在在线文档上面进行接口测试

![img](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA+MAAAA+CAYAAABAzMq8AAAPuklEQVR4nO3df0hdZ57H8c8MMxMwhd2kqVabtGnYrJlpxBrdrYt/TBIwMWmKHQIhM4WpFlyEhM4fRc1MWYIMbYwSSocUwgqNG0i3BMpEJrZWITGwMpbGccV0p5Il61Sj1Zg6O5PIdveP2ec595x7z7m/vNeYo6bvFxS9554fzznmj37O9/nxrb8YAgAAAAAAofn2cjcAAAAAAIBvGsI4AAAAAAAhI4wDAAAAABAywjgAAAAAACEjjAMAAAAAEDLCOAAAAAAAISOMAwAAAAAQMsI4AAAAAAAhI4wDAAAAABCy7yTbePmz6bDbAQAAAABA6HY/k7cs100axq0bE3/UO12/19zd/w2zPUtm3SPf05Hnv68Df79puZsCAAAAAFiBRif/vGzXTtlNfTUHccu23d4DAAAAAAArTcowvpqDuOdhuAcAAAAAwMOHCdwAAAAAAAgZYRwAAAAAgJARxgEAAAAACBlhHAAAAACAkBHGF2mwba02rm/V4HI3JJXJC6pbv1Z1Hyx2zfhpXaw191h7QbcX2HPFPwsAAAAAq9qd3l51jCRuHzl3Ken21SDlOuNIdPuDn6ikTjp9/T2t/tXLP9Gv1u9W688va6LhOWdL5P461dhzT6+WLfK011q1cU/z/Z0DAAAAWCozQzp2VmpoKtGjzoZZfXxySKqt1N7c2G421NUNJzm+uFADP92a5gI31PHajEpPVago8Lv/+1Gd0Ro1NwWv6bFB8/mpzQtcJ1v2Pgc0tf+Aaop8m0f6VT6Um+JaXlvT8D8Pc65jXxaqpXg87hmnvqeRc72arPI9B9ueD3PU5Ts2bRvt3/PkLfX5tw1f8rV5ndoDz3/lIoxnbFr9lzolE15fLNDqrwJfu6pWVev0S5Egbivpv3BeNNxz7k/K04tnze/ZnresURM9MoG8VRVfNap0aVsNAAAALChZsO577VZww8lLOu797gTMA2o3xw2WuOHVDYRd+WMqNwE1KHWwluZU99qluH0PaCDpvhGPVm5WvblGx8jWYHCOioRk1RxI8X2S8Dszrit6Qg2B/U1A/3BezbVpQn/uE8Fg7Oc8k9SHei8Ajs94n0ejz25nVblaKjeoqGqDzp/sV4EXmIsq1PVlr9p6Z53vY+E8sY2ff9ivj2sr1HKqJNYk/99slSGMZ2ryqrpMFm888txyt2RJDF5plqrPqqLA3TA5po/0rOoL0h6WmbIfqlFva3xSKl2K8wEAAABZKDLBesD7kGFlPBUnKHf7q932+FEV5MaFfhPAd1Y9oW2pKrPRaq9SV587LsVtTxf6U3OCeffXzu/eS4h6G+TzTECf+Vp9/hcRS15Jds/nf+5OBd39OrdELTXmswnfDRqKtlPmL1be7Z1jTs877bbnKtSked7aYbfP68rwDel3o77Ar2BlfMGeDCvHAwvjbW8c0uH82Of//LcL2vVu5Pd/bPyR/mnbd317/5/6359Q3uGn9TdJzvWnz4f1TGv826hwDZ6v1UcmvL6Z0PXa7e7tfTT7DJ09pMe849rWqvrEcXX2mK/2mABs/tl3uhVjr1u4Z1/7TbUfzHM/2THbW3RU5nxHxlTiHBuRrAt45DreJ3ON65tT38zkBZ05Yc8Ta2ci3/V99+OMRd9unoWvzfVJj+/UFyaMizAOAACA0N1QxzmpxoYyG/6a/N9t0F4TcJ1wfFIJQfSMPxAX2xS8Tvm5vuTnVJxzTJA0Qd0J/XHd1LtnNWl2L4oL0He+nNfO/HWyQTNt9TnKfWng2KqaU9kFTK8S7VzbhPPfmJ8j3be0zYTylugNR9oeMHPLDcIpFC/0ZiDYM8B7GbCzyrdLUYXbhkoNVMY2O+18vDKuyn1Dg6aJNlpu21+p0qF+qSn2soXKeECh3j9drIr//i9teuXT6Na2N36k9/f8Wod73A1T5vvXPw0e2uN+fqVK4zv+R7882qd/XvoGLsIn6j9hg+cP48Jrs6rX23B9TxP2oxtUS9qeio7Dju73jgm1Zr/o8dda9Qu9pYmv3ot+3rhni371VFzQ7jTn+8Fls98956MTuvf8RE9ef8/tTu6G5s5YyPdeENjAvC/J3dz+7UXz3XHVZzum2x0PbgP4hPvSwHmhcGKB4wAAAIBQmfBaYquv69Ty+KjKO/yhOxaeB5rs+OOhwFjn+hp/N3W7ZYMK8odiAXt6Xn35uWpJcd3S4lHVBSrPHlvl3eCcr6Yp8chE7kuDaJvjuqnb9nXMRc/dXJXkFH4mxNaZrDawUGjNspt6X3esot13ziYfX2W8+xG12BciXmU82uYsKv4zd/V5bo7JOfOaMh+LSqTywDAAxY0Z9/0NV7glD+Ntb7hBPC5oN7z+66W+VGhuf/B2ZHz1P+TFfWO2XfeNiy44pPaeMRNY39bFl7yw7O73RlwVuqxR7f4wXPayTlc36+iVT0wY9wd5E7J9wb70pbPad6JWXb+d1os2EF/7FxPEbZXbPz77Ob3ac1ytvmp6zCf6V1uN//nPshzPbUL/O5Gu7W8ejD2Hxw6+pdOXOp02AAAAACtGXo7UYYKi7bbcdFfHTIDr83/vD3S9m6JV5MTKuPnf/Hzp/PCs9pp9RobmVF9SkXC5yd5eDdqqrr+LfBL+LuSZiFS44zY6oXbeGYvuBFpnUjMTcovTnMi0eSAv8eVD0Nb0LwqKKhLCvL8C73Xhd9iXFnok8fhT/op/sgnj4rqcl0RefjSYcw9Gz5GmjavIEofxv1NZvu1y/unCu64awYnbgp7VpvhtBZu1T81xXbST7Bfg7+r+B902YToa3Ks368nA+Z9y3uh5s/c7Y79NYK+Ir3I77UgifuI2/3nMPaYM6O6Y+cTeAXna9APZXuk+ps3VUtcfpqWy+BcYAAAAwIPkTiKmJ9R1qlDbTt7VHdtV3Zv0ywmus9qVojKbWBmXHi02YfPsuO5UzmlweJ1Kfxrbf+RcJEzulAmlRfETmPl4FefKYNfs2HnSdbeeC+47NOeE4Gj7zf01VM2qbyq2j79iHbmvyH4t+819vdafOE48UGmP8F4EJN5TunHm/m7qc74J3JLtG+l+X+N+St5NfavzAuBO75j7Oc0z1uqpilsPeAI3t8t6TuSTf9y48p/W+LtPux/+pPdf6XbGXaw4XuU5y4nbRhYIosHx4raL+U2ddsZoZ+ajm2OyQdgRH9hTilW3K3wvB5yu7/9hx4YvfI9FT2USru1M7Dclcz918o+DBwAAAB402737gPYqEu7O2PHG5r9ocJ2eN6FUuuJWujOSu0m7NKRrvfM6U5zrVr69pcMKVT88o1LnXLNK3gX7hjpO3g2e01sWLNM2RM1q0oTubSXpj0s2ZtxhK8s1Jnifu+FMJqfcnFgN0Tf5mT2mLXo2/z0Fx5lHQ7/7ssF59gmT5i215N3cA/e5CjzgMD6qw0ftm5BIKA9EsmRjxlcgb9bxH2c5vjptaHWWEetMmLBtfLGN7BzTF+ZH6snYvOu6M8LHTdxW2nBPnSaQJ451T7TQS4aIyDj2rgMEcQAAACwPZ6Zzk0O6qsbUFg3ekaW9dtVu1pRT6d6QJCz6K6+2sjsWCX61G3Ts5LzaoxOpRUK/E04Dx3+t48nGjJuw+oK/fUNzCYH6TPxs6mnGb3/+pQn+RbHjJ6cy7/oe625+I/NjknAmpbOh311n3HKe+1Sk3TLh+JhKUrxwSFXhzmT8d4pnLLcHwCqxxGH8U12belr7ny2UepZ39vMl4c46ntg12/PvCct3ZTQ5mrOMmAnFgcBuArUtlFdn18Qnt1SnaUdwAjdnRvgUbSvdZf4p77mqQRPGk3ZVd7vHt/or8o7I5HZBkXspOkIQBwAAQPhsIDyfX64BEwJj3ZvtF6O6ssOEw1yzfYdvbWv5likbHoqsCz7tLUVmw7etBt8y4XiN6pyKcrqZzTOojM8M6by5Vp/ZXlMUO1dmXaw3qGzHGh3vHtWIXbfbaXx/pO3pxownYydHMz9eWHDH5JwXANEVtOb1GxOQzzjh2j8je6y7fOL9Bbu7J++mngyV8aQars5o/+FifdaoZV+O7H55wbozZXW3U0dfv6AKb+mva61O1/N97W+lnxzNWYfbBNvoZG22kuxbHi0Ljx38mRrrduvo9lZt8mZTdyvvQd6M8C9nOXGb5zn9uL1areZadVtiFe/BtsW1GwAAAHhQ7Drj3mzn0cBox0R/mKOupkj4frSyRLtODqjj8UhALIqfeG3a/emOpa53lwSzob38nNIE8oUq47P6+KwJqlWFav7dqMpNSHeqyFmw487bpy7Fxmabc7dXzatuoTHj8ZxJ1nJiw4WHR6NjvK3YOO/4ezIh2ukuv0a7quzztH18c/RCU0V0/LcV6eqerDJuzzfgPNO9Gd91/PFUxhP19OmZnki39PF3fa9m5mf0y3d9+wXGjMeNJ18RvInb0s06boK6XQN8/drolmDX81S82c53a6NbVW7suanTynzMeOBcX12W1u9W9Xp39nS71vn1s9L22thu3sRtCTPCZ+6xg+9pSHas+xZtrPPafU+d8q9xDgAAACw3fxfodaq31dkpO6u6P0DbbublZj8TrocKk4drG06H18RmLVcktLe7gbxdo25FutAX5NNXxkfODejKDnc8d+VWldnZ1b1QHbdEl8Mdxx2/znjCywMp8DnVmPFoDwBXfU1FrBt8NmPG7Xrr+ZvV4szmnvjoUpvT1Ey6CeAy8XBUxr/1FyN+4+XPpvXyqavL0Z4lN3DqwOIOdNfUtmHz1SzHi6887lrkspO0HUre5d65X/nWKr8fdnb4t31roQMAAADA4sW6sLtLo9VW+pZ1u+UuW5fF+uWu0ck/a/czyzO89gFP4LZaxWYdz3bithUpuhZ5iiBuOUuhXUwYe76469kq/LPqJIgDAAAAWAK2a36N85vt0eBbG86/bN0qQxhPyi7NdU8vLnczlkpZoya+aky/T8Ehvdl+USXb1+qL++kN4OtRcP8VdgAAAAB4OBHGEWXHhE8cvM+TZBL8AQAAAOAb7tvL3QAAAAAAAL5pCOMAAAAAAIQsZRhf98j3wmzHA/Ew3AMAAAAA4OGTMowfef77qzrM2rbbewAAAAAAYKVJuc44AAAAAAAPs7/6rlT6tytsnfHtm/46zHYAAAAAABCq6+N/XLZrM4EbAAAAAAAhI4wDAAAAABAywjgAAAAAACEjjAMAAAAAEDLCOAAAAAAAISOMAwAAAAAQMsI4AAAAAAAhI4wDAAAAABAywjgAAAAAACEjjAMAAAAAEDLCOAAAAAAAISOMAwAAAAAQMsI4AAAAAAAhI4wDAAAAABAywjgAAAAAACEjjAMAAAAAEDLCOAAAAAAAISOMAwAAAAAQMsI4AAAAAAAhI4wDAAAAABAywjgAAAAAACH7fyzoNBDxZQfIAAAAAElFTkSuQmCC)

![img](https://www.macrozheng.com/assets/arch_screen_08.ec7a037e.png)

# power整合Redis实现缓存功能

本文主要讲解mall整合Redis的过程，以短信验证码的存储验证为例。

## Redis的安装和启动

> Redis是用C语言开发的一个高性能键值对数据库，可用于数据缓存，主要用于处理大量数据的高访问负载。

- 下载Redis,下载地址：https://github.com/MicrosoftArchive/redis/releases

![img](https://www.macrozheng.com/assets/arch_screen_09.4c12a355.png)

- 下载完后解压到指定目录

![img](https://www.macrozheng.com/assets/arch_screen_10.c78d0cd5.png)

- 在当前地址栏输入cmd后，执行redis的启动命令：redis-server.exe redis.windows.conf

![img](https://www.macrozheng.com/assets/arch_screen_11.b5362a4a.png)

## 整合Redis

### 添加项目依赖

> 在pom.xml中新增Redis相关依赖



```xml
<!--redis依赖配置-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_03.html#修改springboot配置文件)修改SpringBoot配置文件

> 在application.yml中添加Redis的配置及Redis中自定义key的配置。

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_03.html#在spring节点下添加redis的配置)在spring节点下添加Redis的配置



```yaml
  redis:
    host: localhost # Redis服务器地址
    database: 0 # Redis数据库索引（默认为0）
    port: 6379 # Redis服务器连接端口
    password: # Redis服务器连接密码（默认为空）
    jedis:
      pool:
        max-active: 8 # 连接池最大连接数（使用负值表示没有限制）
        max-wait: -1ms # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-idle: 8 # 连接池中的最大空闲连接
        min-idle: 0 # 连接池中的最小空闲连接
    timeout: 3000ms # 连接超时时间（毫秒）
```

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_03.html#在根节点下添加redis自定义key的配置)在根节点下添加Redis自定义key的配置



```yaml
# 自定义redis key
redis:
  key:
    prefix:
      authCode: "portal:authCode:"
    expire:
      authCode: 120 # 验证码超期时间
```



### [#](https://www.macrozheng.com/mall/architect/mall_arch_03.html#添加redisservice接口用于定义一些常用redis操作)添加RedisService接口用于定义一些常用Redis操作



```java
package com.macro.mall.tiny.service;

/**
 * redis操作Service,
 * 对象和数组都以json形式进行存储
 * Created by macro on 2018/8/7.
 */
public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_03.html#注入stringredistemplate-实现redisservice接口)注入StringRedisTemplate，实现RedisService接口



```java
package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 * Created by macro on 2018/8/7.
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key,delta);
    }
}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_03.html#添加umsmembercontroller)添加UmsMemberController

> 添加根据电话号码获取验证码的接口和校验验证码的接口



```java
package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 会员登录注册管理Controller
 * Created by macro on 2018/8/3.
 */
@Controller
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
@RequestMapping("/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@RequestParam String telephone,
                                 @RequestParam String authCode) {
        return memberService.verifyAuthCode(telephone,authCode);
    }
}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_03.html#添加umsmemberservice接口)添加UmsMemberService接口



```java
package com.macro.mall.tiny.service;

import com.macro.mall.tiny.common.api.CommonResult;

/**
 * 会员管理Service
 * Created by macro on 2018/8/3.
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    CommonResult verifyAuthCode(String telephone, String authCode);

}
```

### 添加UmsMemberService接口的实现类UmsMemberServiceImpl

> 生成验证码时，将自定义的Redis键值加上手机号生成一个Redis的key,以验证码为value存入到Redis中，并设置过期时间为自己配置的时间（这里为120s）。校验验证码时根据手机号码来获取Redis里面存储的验证码，并与传入的验证码进行比对。



```java
package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.service.RedisService;
import com.macro.mall.tiny.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * 会员管理Service实现类
 * Created by macro on 2018/8/3.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(sb.toString(), "获取验证码成功");
    }


    //对输入的验证码进行校验
    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode) {
        if (StringUtils.isEmpty(authCode)) {
            return CommonResult.failed("请输入验证码");
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        boolean result = authCode.equals(realAuthCode);
        if (result) {
            return CommonResult.success(null, "验证码校验成功");
        } else {
            return CommonResult.failed("验证码不正确");
        }
    }

}
```

### 运行项目

> 访问Swagger的API文档地址http://localhost:8080/swagger-ui.html ,对接口进行测试。

![img](https://www.macrozheng.com/assets/arch_screen_12.fcdeb6ab.png)

# power整合SpringSecurity和JWT实现认证和授权

讲解power通过整合SpringSecurity和JWT实现**后台用户的登录和授权**功能，同时改造Swagger-UI的配置使其可以**自动记住登录令牌进行发送**。

## 项目使用框架介绍

### SpringSecurity

> SpringSecurity是一个强大的可高度定制的认证和授权框架，对于Spring应用来说它是一套Web安全标准。SpringSecurity注重于为Java应用提供认证和授权功能，像所有的Spring项目一样，它对自定义需求具有强大的扩展性。

### JWT

> JWT是JSON WEB TOKEN的缩写，它是基于 RFC 7519 标准定义的一种可以安全传输的的JSON对象，由于使用了数字签名，所以是可信任和安全的。

#### JWT的组成

- JWT token的格式：header.payload.signature
- header中用于存放签名的生成算法

```json
{"alg": "HS512"}
```

- payload中用于存放用户名、token的生成时间和过期时间



```json
{"sub":"admin","created":1489079981393,"exp":1489684781}
```

- signature为以header和payload生成的签名，一旦header和payload被篡改，验证将失败



```java
//secret为加密算法的密钥
String signature = HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
```

#### JWT实例

这是一个JWT的字符串

```text
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE1NTY3NzkxMjUzMDksImV4cCI6MTU1NzM4MzkyNX0.d-iki0193X0bBOETf2UN3r3PotNIEAV7mzIxxeI5IxFyzzkOZxS0PGfF_SK6wxCv2K8S0cZjMkv6b5bCqc0VBw
```

可以在该网站上获得解析结果：https://jwt.io/ ![img](https://www.macrozheng.com/assets/arch_screen_13.fc3ff0ff.png)

#### JWT实现认证和授权的原理

- 用户调用登录接口，登录成功后获取到JWT的token；
- 之后用户每次调用接口都在http的header中添加一个叫Authorization的头，值为JWT的token；
- 后台程序通过对Authorization头中信息的解码及数字签名校验来获取其中的用户信息，从而实现认证和授权。

### Hutool

> Hutool是一个丰富的Java开源工具包,它帮助我们简化每一行代码，减少每一个方法，mall项目采用了此工具包。

## 项目使用表说明

- `ums_admin`：后台用户表
- `ums_role`：后台用户角色表
- `ums_permission`：后台用户权限表
- `ums_admin_role_relation`：后台用户和角色关系表，用户与角色是多对多关系
- `ums_role_permission_relation`：后台用户角色和权限关系表，角色与权限是多对多关系
- `ums_admin_permission_relation`：后台用户和权限关系表(除角色中定义的权限以外的加减权限)，加权限是指用户比角色多出的权限，减权限是指用户比角色少的权限

## 整合SpringSecurity及JWT

### 在pom.xml中添加项目依赖



```xml
<!--SpringSecurity依赖配置-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<!--Hutool Java工具包-->
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>4.5.7</version>
</dependency>
<!--JWT(Json Web Token)登录支持-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.0</version>
</dependency>
```

### 添加JWT token的工具类

> 用于生成和解析JWT token的工具类

相关方法说明：

- generateToken(UserDetails userDetails) :用于根据登录用户信息生成token
- getUserNameFromToken(String token)：从token中获取登录用户的信息
- validateToken(String token, UserDetails userDetails)：判断token是否还有效



```java
package com.macro.mall.tiny.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * Created by macro on 2018/4/26.
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}",token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username =  claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
```

### 添加SpringSecurity的配置类



```java
package com.macro.mall.tiny.config;

import com.macro.mall.tiny.component.JwtAuthenticationTokenFilter;
import com.macro.mall.tiny.component.RestAuthenticationEntryPoint;
import com.macro.mall.tiny.component.RestfulAccessDeniedHandler;
import com.macro.mall.tiny.dto.AdminUserDetails;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


/**
 * SpringSecurity的配置
 * Created by macro on 2018/4/26.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
                .disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                )
                .permitAll()
                .antMatchers("/admin/login", "/admin/register")// 对登录注册要允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
//                .antMatchers("/**")//测试时全部运行访问
//                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            UmsAdmin admin = adminService.getAdminByUsername(username);
            if (admin != null) {
                List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
                return new AdminUserDetails(admin,permissionList);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
```

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_04.html#相关依赖及方法说明)相关依赖及方法说明

- configure(HttpSecurity httpSecurity)：用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；
- configure(AuthenticationManagerBuilder auth)：用于配置UserDetailsService及PasswordEncoder；
- RestfulAccessDeniedHandler：当用户没有访问权限时的处理器，用于返回JSON格式的处理结果；
- RestAuthenticationEntryPoint：当未登录或token失效时，返回JSON格式的结果；
- UserDetailsService:SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现；
- UserDetails：SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现；
- PasswordEncoder：SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncoder；
- JwtAuthenticationTokenFilter：在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。

### 添加RestfulAccessDeniedHandler



```java
package com.macro.mall.tiny.component;

import cn.hutool.json.JSONUtil;
import com.macro.mall.tiny.common.api.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当访问接口没有权限时，自定义的返回结果
 * Created by macro on 2018/4/26.
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonResult.forbidden(e.getMessage())));
        response.getWriter().flush();
    }
}
```

### 添加RestAuthenticationEntryPoint



```java
package com.macro.mall.tiny.component;

import cn.hutool.json.JSONUtil;
import com.macro.mall.tiny.common.api.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果
 * Created by macro on 2018/5/14.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonResult.unauthorized(authException.getMessage())));
        response.getWriter().flush();
    }
}
```

### 添加AdminUserDetails



```java
package com.macro.mall.tiny.dto;

import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 * Created by macro on 2018/4/26.
 */
public class AdminUserDetails implements UserDetails {
    private UmsAdmin umsAdmin;
    private List<UmsPermission> permissionList;
    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissionList.stream()
                .filter(permission -> permission.getValue()!=null)
                .map(permission ->new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
```

### 添加JwtAuthenticationTokenFilter

> 在用户名和密码校验前添加的过滤器，如果请求中有jwt的token且有效，会取出token中的用户名，然后调用SpringSecurity的API进行登录操作。



```java
package com.macro.mall.tiny.component;

import com.macro.mall.tiny.common.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 * Created by macro on 2018/4/26.
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            LOGGER.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
```

## controller和service层的代码实现及登录授权流程演示。

## 登录注册功能实现

### 添加UmsAdminController类

> 实现了后台用户登录、注册及获取权限的接口



```java
package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.UmsAdminLoginParam;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户管理
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    private UmsAdminService adminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取用户所有权限（包括+-权限）")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId) {
        List<UmsPermission> permissionList = adminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }
}
```

### 添加UmsAdminService接口



```java
package com.macro.mall.tiny.service;

import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后台管理员Service
 * Created by macro on 2018/4/26.
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Long adminId);
}
```



### 添加UmsAdminServiceImpl类



```java
package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.common.utils.JwtTokenUtil;
import com.macro.mall.tiny.dao.UmsAdminRoleRelationDao;
import com.macro.mall.tiny.dto.UmsAdminLoginParam;
import com.macro.mall.tiny.mbg.mapper.UmsAdminMapper;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsAdminExample;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * UmsAdminService实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }


    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationDao.getPermissionList(adminId);
    }
}
```

### 修改Swagger的配置

> 通过**修改配置实现调用接口自带Authorization头，这样就可以访问需要登录的接口**了。



```java
package com.macro.mall.tiny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2API文档的配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.macro.mall.tiny.controller"))
                .paths(PathSelectors.any())
                .build()
                //添加登录认证
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerUI演示")
                .description("mall-tiny")
                .contact("macro")
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        //设置请求头信息
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/brand/.*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }
}
```

## 认证与授权流程演示

### 运行项目，访问API

Swagger api地址：http://localhost:8080/swagger-ui.html

![img](https://www.macrozheng.com/assets/arch_screen_14.d0e56003.png)

### 未登录前访问接口

![img](https://www.macrozheng.com/assets/arch_screen_15.df1c06fc.png)

![img](https://www.macrozheng.com/assets/arch_screen_16.5a767892.png)

### 登录后访问接口

- 进行登录操作：登录帐号test 123456

![img](https://www.macrozheng.com/assets/arch_screen_17.31a5112f.png)

![img](https://www.macrozheng.com/assets/arch_screen_18.fde07ce7.png)

- 点击Authorize按钮，在弹框中输入登录接口中获取到的token信息

![img](https://www.macrozheng.com/assets/arch_screen_19.510cc7c4.png)

![img](https://www.macrozheng.com/assets/arch_screen_20.6d041b76.png)

- 登录后访问获取权限列表接口，发现已经可以正常访问

![img](https://www.macrozheng.com/assets/arch_screen_15.df1c06fc.png)

![img](https://www.macrozheng.com/assets/arch_screen_21.42d126b5.png)

### 访问需要权限的接口

> 由于test帐号并没有设置任何权限，所以他无法访问具有pms:brand:read权限的获取品牌列表接口。

![img](https://www.macrozheng.com/assets/arch_screen_22.b275addc.png)

![img](https://www.macrozheng.com/assets/arch_screen_23.4203dba6.png)

### 改用其他有权限的帐号登录

> 改用admin 123456登录后访问，点击Authorize按钮打开弹框,点击logout登出后再重新输入新token。

![img](https://www.macrozheng.com/assets/arch_screen_22.b275addc.png)

![img](https://www.macrozheng.com/assets/arch_screen_24.be4a59e3.png)

# mall整合SpringTask实现定时任务

power整合SpringTask的过程，以批量修改超时订单为例。

## 项目使用框架介绍

### SpringTask

> SpringTask是Spring自主研发的轻量级定时任务工具，相比于Quartz更加简单方便，且不需要引入其他依赖即可使用。

### Cron表达式

> Cron表达式是一个字符串，包括6~7个时间元素，在SpringTask中可以用于==**指定任务的执行时间。**==

#### Cron的语法格式

Seconds Minutes Hours DayofMonth Month DayofWeek

#### Cron格式中每个时间元素的说明

| 时间元素   | 可出现的字符  | 有效数值范围 |
| ---------- | ------------- | ------------ |
| Seconds    | , - * /       | 0-59         |
| Minutes    | , - * /       | 0-59         |
| Hours      | , - * /       | 0-23         |
| DayofMonth | , - * / ? L W | 0-31         |
| Month      | , - * /       | 1-12         |
| DayofWeek  | , - * / ? L # | 1-7或SUN-SAT |

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_06.html#cron格式中特殊字符说明)Cron格式中特殊字符说明

| 字符 | 作用                                      | 举例                                                         |
| ---- | ----------------------------------------- | ------------------------------------------------------------ |
| ,    | 列出枚举值                                | 在Minutes域使用5,10，表示在5分和10分各触发一次               |
| -    | 表示触发范围                              | 在Minutes域使用5-10，表示从5分到10分钟每分钟触发一次         |
| *    | 匹配任意值                                | 在Minutes域使用*, 表示每分钟都会触发一次                     |
| /    | 起始时间开始触发，每隔固定时间触发一次    | 在Minutes域使用5/10,表示5分时触发一次，每10分钟再触发一次    |
| ?    | 在DayofMonth和DayofWeek中，用于匹配任意值 | 在DayofMonth域使用?,表示每天都触发一次                       |
| #    | 在DayofMonth中，确定第几个星期几          | 1#3表示第三个星期日                                          |
| L    | 表示最后                                  | 在DayofWeek中使用5L,表示在最后一个星期四触发                 |
| W    | 表示有效工作日(周一到周五)                | 在DayofMonth使用5W，如果5日是星期六，则将在最近的工作日4日触发一次 |

## 业务场景说明

- 用户对某商品进行下单操作；
- 系统需要根据用户购买的商品信息生成订单并锁定商品的库存；
- 系统设置了60分钟用户不付款就会取消订单；
- 开启一个定时任务，每隔10分钟检查下，如果有超时还未付款的订单，就取消订单并取消锁定的商品库存。

## 整合SpringTask

> 由于SpringTask已经存在于Spring框架中，所以无需添加依赖。

### 添加SpringTask的配置

> 只需要在配置类中添加一个@EnableScheduling注解即可开启SpringTask的定时任务能力。



```java
package com.macro.mall.tiny.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务配置
 * Created by macro on 2019/4/8.
 */
@Configuration
@EnableScheduling
public class SpringTaskConfig {
}
```



### 添加OrderTimeOutCancelTask来执行定时任务



```java
package com.macro.mall.tiny.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by macro on 2018/8/24.
 * 订单超时取消并解锁库存的定时器
 */
@Component
public class OrderTimeOutCancelTask {
    private Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每10分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutOrder() {
        // TODO: 2019/5/3 此处应调用取消订单的方法，具体查看mall项目源码
        LOGGER.info("取消订单，并根据sku编号释放锁定库存");
    }
}
```



# power整合Elasticsearch实现商品搜索

## 项目使用框架介绍

### Elasticsearch

> Elasticsearch 是一个分布式、可扩展、实时的搜索与数据分析引擎。 它能从项目一开始就赋予你的==数据以搜索、分析和探索的能力==，可用于==实现**全文搜索**和**实时数据统计**==。

#### Elasticsearch的安装和使用

1. 下载Elasticsearch6.2.2的zip包，并解压到指定目录，下载地址：[https://www.elastic.co/cn/downloads/past-releases/elasticsearch-6-2-2open in new window](https://www.elastic.co/cn/downloads/past-releases/elasticsearch-6-2-2)

![img](https://www.macrozheng.com/assets/arch_screen_25.48daf958.png)

1. 安装中文分词插件，在elasticsearch-6.2.2\bin目录下执行以下命令：elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.2.2/elasticsearch-analysis-ik-6.2.2.zip

![img](https://www.macrozheng.com/assets/arch_screen_26.3dcb5bb5.png)

1. 运行bin目录下的elasticsearch.bat启动Elasticsearch

![img](https://www.macrozheng.com/assets/arch_screen_27.ba3cb8e0.png)

1. 下载Kibana,作为访问Elasticsearch的客户端，请下载6.2.2版本的zip包，并解压到指定目录，下载地址：[https://artifacts.elastic.co/downloads/kibana/kibana-6.2.2-windows-x86_64.zipopen in new window](https://artifacts.elastic.co/downloads/kibana/kibana-6.2.2-windows-x86_64.zip)

![img](https://www.macrozheng.com/assets/arch_screen_28.021b8895.png)

1. 运行bin目录下的kibana.bat，启动Kibana的用户界面

![img](https://www.macrozheng.com/assets/arch_screen_29.0d7fe0a1.png)

1. 访问[http://localhost:5601open in new window](http://localhost:5601/) 即可打开Kibana的用户界面

![img](https://www.macrozheng.com/assets/arch_screen_30.362cad7b.png)

### Spring Data Elasticsearch

> Spring Data Elasticsearch是Spring提供的一种以Spring Data风格来操作数据存储的方式，它可以避免编写大量的样板代码。

#### 常用注解

##### @Document

```java
//标示映射到Elasticsearch文档上的领域对象
public @interface Document {
  //索引库名次，mysql中数据库的概念
	String indexName();
  //文档类型，mysql中表的概念
	String type() default "";
  //默认分片数
	short shards() default 5;
  //默认副本数量
	short replicas() default 1;

}
```

#### @Id

```java
//表示是文档的id，文档可以认为是mysql中表行的概念
public @interface Id {
}
```

#### @Field

```java
public @interface Field {
  //文档中字段的类型
	FieldType type() default FieldType.Auto;
  //是否建立倒排索引
	boolean index() default true;
  //是否进行存储
	boolean store() default false;
  //分词器名次
	String analyzer() default "";
}
```



```java
//为文档自动指定元数据类型
public enum FieldType {
	Text,//会进行分词并建了索引的字符类型
	Integer,
	Long,
	Date,
	Float,
	Double,
	Boolean,
	Object,
	Auto,//自动判断字段类型
	Nested,//嵌套对象类型
	Ip,
	Attachment,
	Keyword//不会进行分词建立索引的类型
}
```

#### Sping Data方式的数据操作

##### 继承ElasticsearchRepository接口可以获得常用的数据操作方法

![img](https://www.macrozheng.com/assets/arch_screen_31.17948c33.png)

##### 可以使用衍生查询

> 在接口中直接指定查询方法名称便可查询，无需进行实现，如商品表中有商品名称、标题和关键字，直接定义以下查询，就可以对这三个字段进行全文搜索。



```java
    /**
     * 搜索查询
     *
     * @param name              商品名称
     * @param subTitle          商品标题
     * @param keywords          商品关键字
     * @param page              分页信息
     * @return
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);
```

> 在idea中直接会提示对应字段

![img](https://www.macrozheng.com/assets/arch_screen_32.d55bbbce.png)

##### 使用@Query注解可以用Elasticsearch的DSL语句进行查询

```java
@Query("{"bool" : {"must" : {"field" : {"name" : "?0"}}}}")
Page<EsProduct> findByName(String name,Pageable pageable);
```

## 项目使用表说明

- `pms_product`：商品信息表
- `pms_product_attribute`：商品属性参数表
- `pms_product_attribute_value`：存储产品参数值的表

## 整合Elasticsearch实现商品搜索

### 在pom.xml中添加相关依赖



```xml
<!--Elasticsearch相关依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch<artifactId>
</dependency>
```

### 修改SpringBoot配置文件

> 修改application.yml文件，在spring节点下添加Elasticsearch相关配置。



```yaml
data:
  elasticsearch:
    repositories:
      enabled: true
    cluster-nodes: 127.0.0.1:9300 # es的连接地址及端口号
    cluster-name: elasticsearch # es集群的名称
```

### 添加商品文档对象EsProduct

> 不需要中文分词的字段设置成@Field(type = FieldType.Keyword)类型，需要中文分词的设置成@Field(analyzer = "ik_max_word",type = FieldType.Text)类型。



```java
package com.macro.mall.tiny.nosql.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 搜索中的商品信息
 * Created by macro on 2018/6/19.
 */
@Document(indexName = "pms", type = "product",shards = 1,replicas = 0)
public class EsProduct implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String productSn;
    private Long brandId;
    @Field(type = FieldType.Keyword)
    private String brandName;
    private Long productCategoryId;
    @Field(type = FieldType.Keyword)
    private String productCategoryName;
    private String pic;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String name;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String subTitle;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String keywords;
    private BigDecimal price;
    private Integer sale;
    private Integer newStatus;
    private Integer recommandStatus;
    private Integer stock;
    private Integer promotionType;
    private Integer sort;
    @Field(type =FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;

    //省略了所有getter和setter方法

```

### 添加EsProductRepository接口用于操作Elasticsearch

> 继承ElasticsearchRepository接口，这样就拥有了一些基本的Elasticsearch数据操作方法，同时定义了一个衍生查询方法。



```java
package com.macro.mall.tiny.nosql.elasticsearch.repository;

import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 商品ES操作类
 * Created by macro on 2018/6/19.
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {
    /**
     * 搜索查询
     *
     * @param name              商品名称
     * @param subTitle          商品标题
     * @param keywords          商品关键字
     * @param page              分页信息
     * @return
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);

}
```

### 添加EsProductService接口

```java
package com.macro.mall.tiny.service;

import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 商品搜索管理Service
 * Created by macro on 2018/6/19.
 */
public interface EsProductService {
    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll();

    /**
     * 根据id删除商品
     */
    void delete(Long id);

    /**
     * 根据id创建商品
     */
    EsProduct create(Long id);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称或者副标题
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);

}
```



### 添加EsProductService接口的实现类EsProductServiceImpl



```java
package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.dao.EsProductDao;
import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import com.macro.mall.tiny.nosql.elasticsearch.repository.EsProductRepository;
import com.macro.mall.tiny.service.EsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 商品搜索管理Service实现类
 * Created by macro on 2018/6/19.
 */
@Service
public class EsProductServiceImpl implements EsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);
    @Autowired
    private EsProductDao productDao;
    @Autowired
    private EsProductRepository productRepository;

    @Override
    public int importAll() {
        List<EsProduct> esProductList = productDao.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = productRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> esProductList = productDao.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            result = productRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsProduct> esProductList = new ArrayList<>();
            for (Long id : ids) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            productRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }

}
```

### 添加EsProductController定义接口



```java
package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import com.macro.mall.tiny.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 搜索商品管理Controller
 * Created by macro on 2018/6/19.
 */
@Controller
@Api(tags = "EsProductController", description = "搜索商品管理")
@RequestMapping("/esProduct")
public class EsProductController {
    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value = "导入所有数据库中商品到ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> importAllList() {
        int count = esProductService.importAll();
        return CommonResult.success(count);
    }

    @ApiOperation(value = "根据id删除商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable Long id) {
        esProductService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id批量删除商品")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> delete(@RequestParam("ids") List<Long> ids) {
        esProductService.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<EsProduct> create(@PathVariable Long id) {
        EsProduct esProduct = esProductService.create(id);
        if (esProduct != null) {
            return CommonResult.success(esProduct);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

}
```



## 进行接口测试

### 将数据库中数据导入到Elasticsearch

![img](https://www.macrozheng.com/assets/arch_screen_33.8dde5d01.png)![img](https://www.macrozheng.com/assets/arch_screen_34.3ca48f68.png)

### 进行商品搜索

![img](https://www.macrozheng.com/assets/arch_screen_35.8ebe7677.png)![img](https://www.macrozheng.com/assets/arch_screen_36.a7628da1.png)

# power整合MongoDB实现文档操作

> power整合Mongodb的过程，以实现**商品浏览记录**在Mongodb中的添加、删除、查询为例。

## 项目使用框架介绍

### Mongodb

> Mongodb是为快速开发互联网Web应用而构建的数据库系统，其数据模型和持久化策略就是为了构建高读/写吞吐量和高自动灾备伸缩性的系统。

#### Mongodb的安装和使用

1. 下载Mongodb安装包，下载地址：[https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-3.2.21-signed.msiopen in new window](https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-3.2.21-signed.msi)
2. 选择安装路径进行安装

![img](https://www.macrozheng.com/assets/arch_screen_37.91e60eec.png)

![img](https://www.macrozheng.com/assets/arch_screen_38.38adc5fb.png)

1. 在安装路径下创建data\db和data\log两个文件夹

![img](https://www.macrozheng.com/assets/arch_screen_39.953a7588.png)

1. 在安装路径下创建mongod.cfg配置文件

```text
systemLog:
    destination: file
    path: D:\developer\env\MongoDB\data\log\mongod.log
storage:
    dbPath: D:\developer\env\MongoDB\data\db
```

1. 安装为服务（运行命令需要用管理员权限）



```text
D:\developer\env\MongoDB\bin\mongod.exe --config "D:\developer\env\MongoDB\mongod.cfg" --install
```

![img](https://www.macrozheng.com/assets/arch_screen_40.676ced03.png)

1. 服务相关命令

```text
启动服务：net start MongoDB
关闭服务：net stop MongoDB
移除服务：D:\developer\env\MongoDB\bin\mongod.exe --remove
```

1. 下载客户端程序：[https://download.robomongo.org/1.2.1/windows/robo3t-1.2.1-windows-x86_64-3e50a65.zipopen in new window](https://download.robomongo.org/1.2.1/windows/robo3t-1.2.1-windows-x86_64-3e50a65.zip)
2. 解压到指定目录，打开robo3t.exe并连接到localhost:27017

![img](https://www.macrozheng.com/assets/arch_screen_41.ec3d5223.png)

### Spring Data Mongodb

> 和Spring Data Elasticsearch类似，Spring Data Mongodb是Spring提供的一种以Spring Data风格来操作数据存储的方式，它可以避免编写大量的样板代码。

#### 常用注解

- @Document:标示映射到Mongodb文档上的领域对象
- @Id:标示某个域为ID域
- @Indexed:标示某个字段为Mongodb的索引字段

#### Sping Data方式的数据操作

##### 继承MongoRepository接口可以获得常用的数据操作方法

![img](https://www.macrozheng.com/assets/arch_screen_42.0a75a3c4.png)

##### 可以使用衍生查询

> 在接口中直接指定查询方法名称便可查询，无需进行实现，以下为根据会员id按时间倒序获取浏览记录的例子。



```java
/**
 * 会员商品浏览历史Repository
 * Created by macro on 2018/8/3.
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory,String> {
    /**
     * 根据会员id按时间倒序获取浏览记录
     * @param memberId 会员id
     */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
```

> 在idea中直接会提示对应字段

![img](https://www.macrozheng.com/assets/arch_screen_43.07ef59a9.png)

##### 使用@Query注解可以用Mongodb的JSON查询语句进行查询

```java
@Query("{ 'memberId' : ?0 }")
List<MemberReadHistory> findByMemberId(Long memberId);
```

## 整合Mongodb实现文档操作

### 在pom.xml中添加相关依赖



```xml
<!---mongodb相关依赖-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

### 修改SpringBoot配置文件

> 修改application.yml文件，在spring:data节点下添加Mongodb相关配置。



```yaml
mongodb:
  host: localhost # mongodb的连接地址
  port: 27017 # mongodb的连接端口号
  database: mall-port # mongodb的连接的数据库
```

### 添加会员浏览记录文档对象MemberReadHistory

> 文档对象的ID域添加@Id注解，需要检索的字段添加@Indexed注解。



```java
package com.macro.mall.tiny.nosql.mongodb.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 用户商品浏览历史记录
 * Created by macro on 2018/8/3.
 */
@Document
public class MemberReadHistory {
    @Id
    private String id;
    @Indexed
    private Long memberId;
    private String memberNickname;
    private String memberIcon;
    @Indexed
    private Long productId;
    private String productName;
    private String productPic;
    private String productSubTitle;
    private String productPrice;
    private Date createTime;

    //省略了所有getter和setter方法

}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_08.html#添加memberreadhistoryrepository接口用于操作mongodb)添加MemberReadHistoryRepository接口用于操作Mongodb

> 继承MongoRepository接口，这样就拥有了一些基本的Mongodb数据操作方法，同时定义了一个衍生查询方法。



```java
package com.macro.mall.tiny.nosql.mongodb.repository;


import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 会员商品浏览历史Repository
 * Created by macro on 2018/8/3.
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory,String> {
    /**
     * 根据会员id按时间倒序获取浏览记录
     * @param memberId 会员id
     */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_08.html#添加memberreadhistoryservice接口)添加MemberReadHistoryService接口



```java
package com.macro.mall.tiny.service;


import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

/**
 * 会员浏览记录管理Service
 * Created by macro on 2018/8/3.
 */
public interface MemberReadHistoryService {
    /**
     * 生成浏览记录
     */
    int create(MemberReadHistory memberReadHistory);

    /**
     * 批量删除浏览记录
     */
    int delete(List<String> ids);

    /**
     * 获取用户浏览历史记录
     */
    List<MemberReadHistory> list(Long memberId);
}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_08.html#添加memberreadhistoryservice接口实现类memberreadhistoryserviceimpl)添加MemberReadHistoryService接口实现类MemberReadHistoryServiceImpl



```java
package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;
import com.macro.mall.tiny.nosql.mongodb.repository.MemberReadHistoryRepository;
import com.macro.mall.tiny.service.MemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会员浏览记录管理Service实现类
 * Created by macro on 2018/8/3.
 */
@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {
    @Autowired
    private MemberReadHistoryRepository memberReadHistoryRepository;
    @Override
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        memberReadHistoryRepository.save(memberReadHistory);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> deleteList = new ArrayList<>();
        for(String id:ids){
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            deleteList.add(memberReadHistory);
        }
        memberReadHistoryRepository.deleteAll(deleteList);
        return ids.size();
    }

    @Override
    public List<MemberReadHistory> list(Long memberId) {
        return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(memberId);
    }
}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_08.html#添加memberreadhistorycontroller定义接口)添加MemberReadHistoryController定义接口



```java
package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;
import com.macro.mall.tiny.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员商品浏览记录管理Controller
 * Created by macro on 2018/8/3.
 */
@Controller
@Api(tags = "MemberReadHistoryController", description = "会员商品浏览记录管理")
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController {
    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation("创建浏览记录")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除浏览记录")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<String> ids) {
        int count = memberReadHistoryService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("展示浏览记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<MemberReadHistory>> list(Long memberId) {
        List<MemberReadHistory> memberReadHistoryList = memberReadHistoryService.list(memberId);
        return CommonResult.success(memberReadHistoryList);
    }
}
```

## [#](https://www.macrozheng.com/mall/architect/mall_arch_08.html#进行接口测试)进行接口测试

### [#](https://www.macrozheng.com/mall/architect/mall_arch_08.html#添加商品浏览记录到mongodb)添加商品浏览记录到Mongodb

![img](https://www.macrozheng.com/assets/arch_screen_44.22000e26.png)![img](https://www.macrozheng.com/assets/arch_screen_45.6f2feba2.png)

### [#](https://www.macrozheng.com/mall/architect/mall_arch_08.html#查询mongodb中的商品浏览记录)查询Mongodb中的商品浏览记录

![img](https://www.macrozheng.com/assets/arch_screen_46.1d0fe235.png)![img](https://www.macrozheng.com/assets/arch_screen_47.56016fd3.png)

# power整合RabbitMQ实现延迟消息

power整合RabbitMQ实现延迟消息的过程，以发送延迟消息取消超时订单为例。

## 项目使用框架介绍

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#rabbitmq)RabbitMQ

> RabbitMQ是一个被广泛使用的开源消息队列。它是轻量级且易于部署的，它能支持多种消息协议。RabbitMQ可以部署在分布式和联合配置中，以满足高规模、高可用性的需求。

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#rabbitmq的安装和使用)RabbitMQ的安装和使用

1. 安装Erlang，下载地址：[http://erlang.org/download/otp_win64_21.3.exeopen in new window](http://erlang.org/download/otp_win64_21.3.exe)

![img](https://www.macrozheng.com/assets/arch_screen_53.e6e83c02.png)

1. 安装RabbitMQ，下载地址：[https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.14/rabbitmq-server-3.7.14.exeopen in new window](https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.14/rabbitmq-server-3.7.14.exe)

![img](https://www.macrozheng.com/assets/arch_screen_54.a1fa2b98.png)

1. 安装完成后，进入RabbitMQ安装目录下的sbin目录

![img](https://www.macrozheng.com/assets/arch_screen_55.cff0e57a.png)

1. 在地址栏输入cmd并回车启动命令行，然后输入以下命令启动管理功能：



```text
rabbitmq-plugins enable rabbitmq_management
```

1

![img](https://www.macrozheng.com/assets/arch_screen_56.ebd1846b.png)

1. 访问地址查看是否安装成功：[http://localhost:15672/open in new window](http://localhost:15672/)

![img](https://www.macrozheng.com/assets/arch_screen_57.f0fc20b9.png)

1. 输入账号密码并登录：guest guest
2. 创建帐号并设置其角色为管理员：mall mall

![img](https://www.macrozheng.com/assets/arch_screen_58.2d67e0fe.png)

1. 创建一个新的虚拟host为：/mall

![img](https://www.macrozheng.com/assets/arch_screen_59.6d1d4f9f.png)

1. 点击mall用户进入用户配置页面

![img](https://www.macrozheng.com/assets/arch_screen_60.6ad3fe16.png)

1. 给mall用户配置该虚拟host的权限

![img](https://www.macrozheng.com/assets/arch_screen_61.f2470471.png)

1. 至此，RabbitMQ的安装和配置完成。

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#rabbitmq的消息模型)RabbitMQ的消息模型

![img](https://www.macrozheng.com/assets/arch_screen_52.b0519322.png)

| 标志 | 中文名     | 英文名   | 描述                                             |
| ---- | ---------- | -------- | ------------------------------------------------ |
| P    | 生产者     | Producer | 消息的发送者，可以将消息发送到交换机             |
| C    | 消费者     | Consumer | 消息的接收者，从队列中获取消息进行消费           |
| X    | 交换机     | Exchange | 接收生产者发送的消息，并根据路由键发送给指定队列 |
| Q    | 队列       | Queue    | 存储从交换机发来的消息                           |
| type | 交换机类型 | type     | direct表示直接根据路由键（orange/black）发送消息 |

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#lombok)Lombok

> Lombok为Java语言添加了非常有趣的附加功能，你可以不用再为实体类手写getter,setter等方法，通过一个注解即可拥有。

注意：需要安装idea的Lombok插件，并在项目中的pom文件中添加依赖。

![img](https://www.macrozheng.com/assets/arch_screen_48.79b8b569.png)

## [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#业务场景说明)业务场景说明

> 用于解决用户下单以后，订单超时如何取消订单的问题。

- 用户进行下单操作（会有锁定商品库存、使用优惠券、积分一系列的操作）；
- 生成订单，获取订单的id；
- 获取到设置的订单超时时间（假设设置的为60分钟不支付取消订单）；
- 按订单超时时间发送一个延迟消息给RabbitMQ，让它在订单超时后触发取消订单的操作；
- 如果用户没有支付，进行取消订单操作（释放锁定商品库存、返还优惠券、返回积分一系列操作）。

## [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#整合rabbitmq实现延迟消息)整合RabbitMQ实现延迟消息

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#在pom-xml中添加相关依赖)在pom.xml中添加相关依赖



```xml
<!--消息队列相关依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<!--lombok依赖-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

1
2
3
4
5
6
7
8
9
10
11

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#修改springboot配置文件)修改SpringBoot配置文件

> 修改application.yml文件，在spring节点下添加Mongodb相关配置。



```yaml
  rabbitmq:
    host: localhost # rabbitmq的连接地址
    port: 5672 # rabbitmq的连接端口号
    virtual-host: /mall # rabbitmq的虚拟host
    username: mall # rabbitmq的用户名
    password: mall # rabbitmq的密码
    publisher-confirms: true #如果对异步消息需要回调必须设置为true
```

1
2
3
4
5
6
7

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#添加消息队列的枚举配置类queueenum)添加消息队列的枚举配置类QueueEnum

> 用于延迟消息队列及处理取消订单消息队列的常量定义，包括交换机名称、队列名称、路由键名称。



```java
package com.macro.mall.tiny.dto;

import lombok.Getter;

/**
 * 消息队列枚举配置
 * Created by macro on 2018/9/14.
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#添加rabbitmq的配置)添加RabbitMQ的配置

> 用于配置交换机、队列及队列与交换机的绑定关系。



```java
package com.macro.mall.tiny.config;

import com.macro.mall.tiny.dto.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置
 * Created by macro on 2018/9/14.
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 订单消息实际消费队列所绑定的交换机
     */
    @Bean
    DirectExchange orderDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单延迟队列队列所绑定的交换机
     */
    @Bean
    DirectExchange orderTtlDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单实际消费队列
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
    }

    /**
     * 订单延迟队列（死信队列）
     */
    @Bean
    public Queue orderTtlQueue() {
        return QueueBuilder
                .durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())//到期后转发的路由键
                .build();
    }

    /**
     * 将订单队列绑定到交换机
     */
    @Bean
    Binding orderBinding(DirectExchange orderDirect,Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }

    /**
     * 将订单延迟队列绑定到交换机
     */
    @Bean
    Binding orderTtlBinding(DirectExchange orderTtlDirect,Queue orderTtlQueue){
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
    }

}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#在rabbitmq管理页面可以看到以下交换机和队列)在RabbitMQ管理页面可以看到以下交换机和队列

![img](https://www.macrozheng.com/assets/arch_screen_62.91cd7f34.png)![img](https://www.macrozheng.com/assets/arch_screen_63.56651c53.png)

![img](https://www.macrozheng.com/assets/arch_screen_64.53bc9573.png)![img](https://www.macrozheng.com/assets/arch_screen_65.f8f39713.png)

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#交换机及队列说明)交换机及队列说明

- mall.order.direct（取消订单消息队列所绑定的交换机）:绑定的队列为mall.order.cancel，一旦有消息以mall.order.cancel为路由键发过来，会发送到此队列。
- mall.order.direct.ttl（订单延迟消息队列所绑定的交换机）:绑定的队列为mall.order.cancel.ttl，一旦有消息以mall.order.cancel.ttl为路由键发送过来，会转发到此队列，并在此队列保存一定时间，等到超时后会自动将消息发送到mall.order.cancel（取消订单消息消费队列）。

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#添加延迟消息的发送者cancelordersender)添加延迟消息的发送者CancelOrderSender

> 用于向订单延迟消息队列（mall.order.cancel.ttl）里发送消息。



```java
package com.macro.mall.tiny.component;

import com.macro.mall.tiny.dto.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的发出者
 * Created by macro on 2018/9/14.
 */
@Component
public class CancelOrderSender {
    private static Logger LOGGER =LoggerFactory.getLogger(CancelOrderSender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId,final long delayTimes){
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        LOGGER.info("send delay message orderId:{}",orderId);
    }
}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#添加取消订单消息的接收者cancelorderreceiver)添加取消订单消息的接收者CancelOrderReceiver

> 用于从取消订单的消息队列（mall.order.cancel）里接收消息。



```java
package com.macro.mall.tiny.component;

import com.macro.mall.tiny.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的处理者
 * Created by macro on 2018/9/14.
 */
@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {
    private static Logger LOGGER =LoggerFactory.getLogger(CancelOrderReceiver.class);
    @Autowired
    private OmsPortalOrderService portalOrderService;
    @RabbitHandler
    public void handle(Long orderId){
        LOGGER.info("receive delay message orderId:{}",orderId);
        portalOrderService.cancelOrder(orderId);
    }
}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#添加omsportalorderservice接口)添加OmsPortalOrderService接口



```java
package com.macro.mall.tiny.service;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * 前台订单管理Service
 * Created by macro on 2018/8/30.
 */
public interface OmsPortalOrderService {

    /**
     * 根据提交信息生成订单
     */
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);
}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#添加omsportalorderservice的实现类omsportalorderserviceimpl)添加OmsPortalOrderService的实现类OmsPortalOrderServiceImpl



```java
package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.component.CancelOrderSender;
import com.macro.mall.tiny.dto.OrderParam;
import com.macro.mall.tiny.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 前台订单管理Service
 * Created by macro on 2018/8/30.
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private static Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);
    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //todo 执行一系类下单操作，具体参考mall项目
        LOGGER.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null, "下单成功");
    }

    @Override
    public void cancelOrder(Long orderId) {
        //todo 执行一系类取消订单操作，具体参考mall项目
        LOGGER.info("process cancelOrder orderId:{}",orderId);
    }

    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间，假设为60分钟
        long delayTimes = 30 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }

}
```

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#添加omsportalordercontroller定义接口)添加OmsPortalOrderController定义接口



```java
package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.dto.OrderParam;
import com.macro.mall.tiny.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单管理Controller
 * Created by macro on 2018/8/30.
 */
@Controller
@Api(tags = "OmsPortalOrderController", description = "订单管理")
@RequestMapping("/order")
public class OmsPortalOrderController {
    @Autowired
    private OmsPortalOrderService portalOrderService;

    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object generateOrder(@RequestBody OrderParam orderParam) {
        return portalOrderService.generateOrder(orderParam);
    }
}
```

## [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#进行接口测试)进行接口测试

### [#](https://www.macrozheng.com/mall/architect/mall_arch_09.html#调用下单接口)调用下单接口

注意：已经将延迟消息时间设置为30秒

![img](https://www.macrozheng.com/assets/arch_screen_49.75e568d7.png)

![img](https://www.macrozheng.com/assets/arch_screen_50.4d122dd6.png)

![img](https://www.macrozheng.com/assets/arch_screen_51.08c7216b.png)

# power整合OSS实现文件上传

power整合OSS实现文件上传的过程，采用的是服务端签名后前端直传的方式。

## MinIO

mall项目能同时支持OSS和MinIO两种对象存储，如果想使用MinIO实现文件存储，可以参考如下文章。

- [MinIO使用教程](https://www.macrozheng.com/project/minio_console_start.html)
- [前后端分离项目使用MinIO实现文件存储](https://www.macrozheng.com/mall/technology/minio_use.html)

## [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#oss)OSS

> 阿里云对象存储服务（Object Storage Service，简称 OSS），是阿里云提供的海量、安全、低成本、高可靠的云存储服务。OSS可用于图片、音视频、日志等海量文件的存储。各种终端设备、Web网站程序、移动应用可以直接向OSS写入或读取数据。

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#oss中的相关概念)OSS中的相关概念

- Endpoint：访问域名，通过该域名可以访问OSS服务的API，进行文件上传、下载等操作。
- Bucket：存储空间，是存储对象的容器，所有存储对象都必须隶属于某个存储空间。
- Object：对象，对象是 OSS 存储数据的基本单元，也被称为 OSS 的文件。
- AccessKey：访问密钥，指的是访问身份验证中用到的 AccessKeyId 和 AccessKeySecret。

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#oss的相关设置)OSS的相关设置

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#开通oss服务)开通OSS服务

- 登录阿里云官网；
- 将鼠标移至产品标签页，单击对象存储 OSS，打开OSS 产品详情页面；
- 在OSS产品详情页，单击立即开通。

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#创建存储空间)创建存储空间

- 点击网页右上角控制台按钮进入控制台

![img](https://www.macrozheng.com/assets/arch_screen_77.d6080613.png)

- 选择我的云产品中的对象存储OSS

![img](https://www.macrozheng.com/assets/arch_screen_78.4945e92c.png)

- 点击左侧存储空间的加号新建存储空间

![img](https://www.macrozheng.com/assets/arch_screen_79.cb239737.png)

- 新建存储空间并设置读写权限为公共读

![img](https://www.macrozheng.com/assets/arch_screen_80.9dde6423.png)

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#跨域资源共享-cors-的设置)跨域资源共享（CORS）的设置

> 由于浏览器处于安全考虑，不允许跨域资源访问，所以我们要设置OSS的跨域资源共享。

- 选择一个存储空间，打开其基础设置

![img](https://www.macrozheng.com/assets/arch_screen_81.f9b45016.png)

- 点击跨越设置的设置按钮

![img](https://www.macrozheng.com/assets/arch_screen_82.925a8c9e.png)

- 点击创建规则

![img](https://www.macrozheng.com/assets/arch_screen_83.1ec0b32b.png)

- 进行跨域规则设置

![img](https://www.macrozheng.com/assets/arch_screen_84.5cfeaa04.png)

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#服务端签名后前端直传的相关说明)服务端签名后前端直传的相关说明

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#流程示例图)流程示例图

![img](https://www.macrozheng.com/assets/arch_screen_85.4e9853ba.png)

#### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#流程介绍)流程介绍

1. Web前端请求应用服务器，获取上传所需参数（如OSS的accessKeyId、policy、callback等参数）
2. 应用服务器返回相关参数
3. Web前端直接向OSS服务发起上传文件请求
4. 等上传完成后OSS服务会回调应用服务器的回调接口
5. 应用服务器返回响应给OSS服务
6. OSS服务将应用服务器回调接口的内容返回给Web前端

## [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#整合oss实现文件上传)整合OSS实现文件上传

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#在pom-xml中添加相关依赖)在pom.xml中添加相关依赖



```xml
<!-- OSS SDK 相关依赖 -->
<dependency>
    <groupId>com.aliyun.oss</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>2.5.0</version>
</dependency>
```

1
2
3
4
5
6

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#修改springboot配置文件)修改SpringBoot配置文件

> 修改application.yml文件，添加OSS相关配置。

注意：endpoint、accessKeyId、accessKeySecret、bucketName、callback、prefix都要改为你自己帐号OSS相关的，callback需要是公网可以访问的地址。



```yaml
# OSS相关配置信息
aliyun:
  oss:
    endpoint: oss-cn-shenzhen.aliyuncs.com # oss对外服务的访问域名
    accessKeyId: test # 访问身份验证中用到用户标识
    accessKeySecret: test # 用户用于加密签名字符串和oss用来验证签名字符串的密钥
    bucketName: macro-oss # oss的存储空间
    policy:
      expire: 300 # 签名有效期(S)
    maxSize: 10 # 上传文件大小(M)
    callback: http://localhost:8080/aliyun/oss/callback # 文件上传成功后的回调地址
    dir:
      prefix: mall/images/ # 上传文件夹路径前缀
```

1
2
3
4
5
6
7
8
9
10
11
12
13

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#添加oss的相关java配置)添加OSS的相关Java配置

> 用于配置OSS的连接客户端OSSClient。



```java
package com.macro.mall.tiny.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by macro on 2018/5/17.
 */
@Configuration
public class OssConfig {
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.accessKeyId}")
    private String ALIYUN_OSS_ACCESSKEYID;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ALIYUN_OSS_ACCESSKEYSECRET;
    @Bean
    public OSSClient ossClient(){
        return new OSSClient(ALIYUN_OSS_ENDPOINT,ALIYUN_OSS_ACCESSKEYID,ALIYUN_OSS_ACCESSKEYSECRET);
    }
}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#添加oss上传策略封装对象osspolicyresult)添加OSS上传策略封装对象OssPolicyResult

> 前端直接上传文件时所需参数，从后端返回过来。



```java
package com.macro.mall.tiny.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 获取OSS上传文件授权返回结果
 * Created by macro on 2018/5/17.
 */
public class OssPolicyResult {
    @ApiModelProperty("访问身份验证中用到用户标识")
    private String accessKeyId;
    @ApiModelProperty("用户表单上传的策略,经过base64编码过的字符串")
    private String policy;
    @ApiModelProperty("对policy签名后的字符串")
    private String signature;
    @ApiModelProperty("上传文件夹路径前缀")
    private String dir;
    @ApiModelProperty("oss对外服务的访问域名")
    private String host;
    @ApiModelProperty("上传成功后的回调设置")
    private String callback;

    //省略了所有getter,setter方法
}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#添加oss上传成功后的回调参数对象osscallbackparam)添加OSS上传成功后的回调参数对象OssCallbackParam

> 当OSS上传成功后，会根据该配置参数来回调对应接口。



```java
package com.macro.mall.tiny.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * oss上传成功后的回调参数
 * Created by macro on 2018/5/17.
 */
public class OssCallbackParam {
    @ApiModelProperty("请求的回调地址")
    private String callbackUrl;
    @ApiModelProperty("回调是传入request中的参数")
    private String callbackBody;
    @ApiModelProperty("回调时传入参数的格式，比如表单提交形式")
    private String callbackBodyType;

    //省略了所有getter,setter方法
}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#oss上传成功后的回调结果对象osscallbackresult)OSS上传成功后的回调结果对象OssCallbackResult

> 回调接口中返回的数据对象，封装了上传文件的信息。



```java
package com.macro.mall.tiny.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * oss上传文件的回调结果
 * Created by macro on 2018/5/17.
 */
public class OssCallbackResult {
    @ApiModelProperty("文件名称")
    private String filename;
    @ApiModelProperty("文件大小")
    private String size;
    @ApiModelProperty("文件的mimeType")
    private String mimeType;
    @ApiModelProperty("图片文件的宽")
    private String width;
    @ApiModelProperty("图片文件的高")
    private String height;

    //省略了所有getter,setter方法
}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#添加oss业务接口ossservice)添加OSS业务接口OssService



```java
package com.macro.mall.tiny.service;

import com.macro.mall.tiny.dto.OssCallbackResult;
import com.macro.mall.tiny.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * oss上传管理Service
 * Created by macro on 2018/5/17.
 */
public interface OssService {
    /**
     * oss上传策略生成
     */
    OssPolicyResult policy();

    /**
     * oss上传成功回调
     */
    OssCallbackResult callback(HttpServletRequest request);
}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#添加oss业务接口ossservice的实现类ossserviceimpl)添加OSS业务接口OssService的实现类OssServiceImpl



```java
package com.macro.mall.tiny.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.macro.mall.tiny.dto.OssCallbackParam;
import com.macro.mall.tiny.dto.OssCallbackResult;
import com.macro.mall.tiny.dto.OssPolicyResult;
import com.macro.mall.tiny.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * oss上传管理Service实现类
 * Created by macro on 2018/5/17.
 */
@Service
public class OssServiceImpl implements OssService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);
	@Value("${aliyun.oss.policy.expire}")
	private int ALIYUN_OSS_EXPIRE;
	@Value("${aliyun.oss.maxSize}")
	private int ALIYUN_OSS_MAX_SIZE;
	@Value("${aliyun.oss.callback}")
	private String ALIYUN_OSS_CALLBACK;
	@Value("${aliyun.oss.bucketName}")
	private String ALIYUN_OSS_BUCKET_NAME;
	@Value("${aliyun.oss.endpoint}")
	private String ALIYUN_OSS_ENDPOINT;
	@Value("${aliyun.oss.dir.prefix}")
	private String ALIYUN_OSS_DIR_PREFIX;

	@Autowired
	private OSSClient ossClient;

	/**
	 * 签名生成
	 */
	@Override
	public OssPolicyResult policy() {
		OssPolicyResult result = new OssPolicyResult();
		// 存储目录
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dir = ALIYUN_OSS_DIR_PREFIX+sdf.format(new Date());
		// 签名有效期
		long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
		Date expiration = new Date(expireEndTime);
		// 文件大小
		long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
		// 回调
		OssCallbackParam callback = new OssCallbackParam();
		callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
		callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
		callback.setCallbackBodyType("application/x-www-form-urlencoded");
		// 提交节点
		String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
		try {
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
			String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String policy = BinaryUtil.toBase64String(binaryData);
			String signature = ossClient.calculatePostSignature(postPolicy);
			String callbackData = BinaryUtil.toBase64String(JSONUtil.parse(callback).toString().getBytes("utf-8"));
			// 返回结果
			result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
			result.setPolicy(policy);
			result.setSignature(signature);
			result.setDir(dir);
			result.setCallback(callbackData);
			result.setHost(action);
		} catch (Exception e) {
			LOGGER.error("签名生成失败", e);
		}
		return result;
	}

	@Override
	public OssCallbackResult callback(HttpServletRequest request) {
		OssCallbackResult result= new OssCallbackResult();
		String filename = request.getParameter("filename");
		filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);
		result.setFilename(filename);
		result.setSize(request.getParameter("size"));
		result.setMimeType(request.getParameter("mimeType"));
		result.setWidth(request.getParameter("width"));
		result.setHeight(request.getParameter("height"));
		return result;
	}

}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
81
82
83
84
85
86
87
88
89
90
91
92
93
94
95
96
97
98
99
100
101
102
103

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#添加osscontroller定义接口)添加OssController定义接口



```java
package com.macro.mall.tiny.controller;


import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.OssCallbackResult;
import com.macro.mall.tiny.dto.OssPolicyResult;
import com.macro.mall.tiny.service.impl.OssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Oss相关操作接口
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssServiceImpl ossService;

    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return CommonResult.success(ossCallbackResult);
    }

}
```

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46

## [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#进行接口测试)进行接口测试

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#测试获取上传策略的接口)测试获取上传策略的接口

![img](https://www.macrozheng.com/assets/arch_screen_66.55d29bc0.png)

![img](https://www.macrozheng.com/assets/arch_screen_67.305916c4.png)

![img](https://www.macrozheng.com/assets/arch_screen_68.08b7da45.png)

### [#](https://www.macrozheng.com/mall/architect/mall_arch_10.html#启动mall-admin-web前端项目来测试上传接口)启动mall-admin-web前端项目来测试上传接口

- 如何启动前端项目，具体参考该项目的readme文档：[https://github.com/macrozheng/mall-admin-webopen in new window](https://github.com/macrozheng/mall-admin-web)
- 点击添加商品品牌的上传按钮进行测试

![img](https://www.macrozheng.com/assets/arch_screen_69.66c9fbb7.png)

- 会调用两次请求，第一次访问本地接口获取上传的策略

![img](https://www.macrozheng.com/assets/arch_screen_70.fd449e18.png)

![img](https://www.macrozheng.com/assets/arch_screen_71.9f899f70.png)

- 第二次调用oss服务 的接口进行文件上传

![img](https://www.macrozheng.com/assets/arch_screen_72.607500f5.png)

![img](https://www.macrozheng.com/assets/arch_screen_73.14afac34.png)

- 可以看到上面接口调用并没有传入回调参数callback,所以接口返回了204 no content,这次我们传入回调参数callback试试，可以发现oss服务回调了我们自己定义的回调接口，并返回了相应结果。

![img](https://www.macrozheng.com/assets/arch_screen_74.3439e3af.png)

![img](https://www.macrozheng.com/assets/arch_screen_75.43becf75.png)

![img](https://www.macrozheng.com/assets/arch_screen_76.5b138bc5.png)

# ======================power项目业务============================

# 数据库表结构概览

后台系统主要包括商品管理、订单管理、营销管理（运营管理+促销管理）、内容管理、用户管理等模块，本文主要对这些模块的数据库表结构及功能做大概的介绍。