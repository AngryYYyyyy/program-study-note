# 一、简介

Spring Boot是Spring框架的一个扩展，它简化了基于Spring的应用开发过程。通过提供一套默认配置（"约定优于配置"），Spring Boot使得开发者可以快速启动和开发新的Spring应用程序。Spring Boot的主要目标是==减少项目搭建的复杂度==，快速创建独立的、生产级别的基于Spring框架的应用。

### 主要特点

1. **简化配置**：Spring Boot提供了大量的==自动配置==，尽量减少了配置文件的使用。
2. **独立运行**：Spring Boot应用可以打包成一个独立的Jar文件，其中包含了Web服务器（如Tomcat），可以直接运行，无需外部Web服务器。
3. **内嵌服务器**：支持==内嵌的Tomcat==、Jetty或Undertow服务器，简化了Web应用的部署。
4. **微服务支持**：与Spring Cloud集成，方便构建和部署微服务架构的应用。
5. **监控**：提供了一系列管理和监控应用的工具，如Spring Boot Actuator。
6. **扩展性**：提供了==大量的Starters==，使得添加新功能（如数据库、消息服务等）变得非常简单。

# 二、项目搭建

## 1.创建项目

### （1）手动创建

在`pom.xml`中添加Spring Boot的父项目依赖和起步依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <!--继承父项目方式-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.5</version>
    </parent>
    <groupId>com.msb</groupId>
    <artifactId>springboot01</artifactId>
    <version>1.0-SNAPSHOT</version>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.4.5</version>
        </dependency>
    </dependencies>
</project>
```

创建启动类

在Spring Boot应用程序中，==启动类是应用启动的入口==。这个类通过调用`SpringApplication.run()`方法来启动Spring应用的上下文。Spring Boot的启动类通常通过添加`@SpringBootApplication`注解来标记，这个注解是一个方便的注解，它包含了`@Configuration`、`@EnableAutoConfiguration`和`@ComponentScan`三个注解，这些注解共同启用了Spring Boot的自动配置和组件扫描功能。

```java
@SpringBootApplication // 标记为Spring Boot应用的启动入口
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 启动Spring应用
    }
}
```

**关键元素解释**

- **@SpringBootApplication**：标记主类为Spring Boot应用的启动类。该注解结合了以下三个注解的功能：
  - **@Configuration**：将该类标记为配置类，允许在其内部声明一些额外的@Bean注解，用于实例化Bean。
  - **@EnableAutoConfiguration**：==启用Spring Boot的自动配置机制==，根据项目中添加的jar依赖自动配置应用。
  - **@ComponentScan**：==启用组件扫描==，默认扫描与启动类同包以及子包下的所有组件（@Component、@Service、@Repository、@Controller等）。

- **SpringApplication.run()**：静态方法，==负责启动Spring应用的上下文（ApplicationContext）==。这个方法读取注解配置、启动Spring上下文，并执行Spring容器的初始化过程。它接受两个参数，第一个是启动类的类对象`Application.class`，第二个是main方法的参数`args`。

这种方式使得开发Spring应用变得非常简单，不需要传统的XML配置文件，也不需要配置复杂的Web应用服务器，就可以快速启动和开发基于Spring框架的应用程序。

### （2）IntelliJ IDEA

创建项目

![image-20240319094227985](.\assets\image-20240319094227985.png)

设置WEB-INF

![image-20240319094331797](.\assets\image-20240319094331797.png)

### （3）项目结构

![image-20240319092107809](.\assets\image-20240319092107809.png)

## 2.项目配置

SpringBoot默认读取项目下名字为application开头的  yml yaml properties配置文件

### （1）properties配置

```properties
server.port=8080
spring.datasource.driver-class-name=
spring.datasource.url=
spring.datasource.name=
spring.datasource.password=
```

注意：这里的每一个. 都代表一个层级，转换成yml之后,使用缩进代表层级关系

### （2）yml配置

**基本格式要求**

- 大小写敏感
- 使用缩进代表层级关系
- 注意空格

```yml
server:
  port: 8080
spring:
  datasource:
    driver-class-name:
    url:
    name:
    password: 
```

### （3）优先级

a当前项目根目录下的一个/config子目录中(最高)
config/application.properties
config/application.yml

b当前项目根目录中(其次)
application.properties
application.yml

c项目的resources即classpath根路径下的/config目录中(一般)
resources/config/application.properties
resources/config/application.yml

d项目的resources即classpath根路径中(最后)
resources/application.properties
resources/application.yml

## 3.启动原理

**SpringApplication.run()的工作流程**

1. **创建`SpringApplication`对象**：基于传入的启动类（如`Application.class`），Spring Boot创建一个`SpringApplication`对象。

2. **推断应用的类型**：根据类路径上的内容（如是否存在Spring MVC或Spring WebFlux相关的类），推断应用是一个普通的Spring应用、一个Servlet Web应用还是一个反应式Web应用，并相应地设置应用上下文的类型。

3. **==加载配置==**：读取`application.properties`或`application.yml`文件中的配置、环境变量、命令行参数等，加载应用配置。

4. **==初始化上下文==**：根据推断出的应用类型，创建并初始化`ApplicationContext`。这个过程包括注册配置类、初始化beans、处理自动配置等。

5. **启动Spring容器**：完成所有配置和初始化后，启动Spring容器。对于Web应用，这还包括启动内嵌的Servlet容器（如Tomcat）。

6. **运行应用**：应用上下文和Spring容器启动完成后，应用进入运行状态。对于Web应用，这时Web服务器开始监听HTTP请求。

总的来说，`SpringApplication.run()`不仅仅是读取应用配置，它是整个Spring Boot应用启动流程的核心，负责启动、配置和管理Spring应用的生命周期。

# 三、SpringBoot整合

## 1.MyBatis

> ==简化：依赖、配置、在启动类上添加mapper扫描==

### （1）依赖

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.3</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.21</version>
</dependency>
```

### （2）配置

```yml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: root
mybatis:
  mapper-locations: classpath:mybatis/*.xml
  type-aliases-package: com.lxy.pojo
```

### （3）业务

示例：返回全部user信息

#### 1）UserMapper接口和UserMapper.xml

```java
public interface UserMapper {
    List<User> selectAll();
}
```

```java
@MapperScan("com.lxy.mapper")
@SpringBootApplication
public class SpringbootTest03Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootTest03Application.class, args);
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lxy.mapper.UserMapper">
    <select id="selectAll" resultType="user" >
        select * from user
    </select>
</mapper>
```

==注意==：

- UserMapper并没有添加@Mapper注解，是因为在启动类添加了mapper扫描
- Mapper能扫描到pojo，是因为在application.yml配置中mybatis添加了type-aliases-package
- 这里接口和xml文件可以不在同一路径下，并且命名可以不一致，通过命名空间匹配

#### 2）UserService接口和UserServiceImpl

```java
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }
}
```

#### 3）UserController

```java
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll(){
        return userService.findAll();
    }
}
```

## 2.logback

Logback是由Log4j创始人设计的另一个开源日志框架，作为SLF4J的一个自然扩展，它被认为是==Log4j的一个改进版==，提供了更快的日志记录性能和更多的灵活配置选项。Logback被分为三个模块：logback-core, logback-classic 和 logback-access。logback-classic 完全实现了SLF4J API，因此可以直接替换其他基于SLF4J的日志系统，如log4j或java.util.logging。logback-access则提供了与Servlet容器集成的能力，用于HTTP访问日志的管理。

### （1）依赖

在Spring Boot项目中我们不需要额外的添加Logback的依赖，因为在spring-boot-starter或者spring-boot-starter-web中已经包含了Logback的依赖。

### （2）配置

classpath下创建logback.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
 <configuration>
<!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径-->  
    <property name="LOG_HOME" value="${catalina.base}/logs/" />  
    <!-- 控制台输出 -->   
    <appender name="Stdout" class="ch.qos.logback.core.ConsoleAppender">
       <!-- 日志输出格式 -->  
        <layout class="ch.qos.logback.classic.PatternLayout">   
             <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符--> 
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n   
            </pattern>   
        </layout>   
    </appender>   
    <!-- 按照每天生成日志文件 -->   
    <appender name="RollingFile"  class="ch.qos.logback.core.rolling.RollingFileAppender">   
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${LOG_HOME}/server.%d{yyyy-MM-dd}.log</FileNamePattern>   
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>   
        <layout class="ch.qos.logback.classic.PatternLayout">  
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符--> 
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n   
            </pattern>   
       </layout> 
        <!--日志文件最大的大小-->
       <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
         <MaxFileSize>10MB</MaxFileSize>
       </triggeringPolicy>
    </appender>     
    <!-- 日志输出级别 -->
    <root level="info">   
        <appender-ref ref="Stdout" />   
        <appender-ref ref="RollingFile" />   
    </root>
    <logger name="com.lxy.mapper" level="DEBUG"></logger>
<!--日志异步到数据库 -->  
<!--<appender name="DB" class="ch.qos.logback.classic.db.DBAppender">
        日志异步到数据库 
        <connectionSource class="ch.qos.logback.core.db.DriverManagerConnectionSource">
           连接池 
           <dataSource class="com.mchange.v2.c3p0.ComboPooledDataSource">
              <driverClass>com.mysql.jdbc.Driver</driverClass>
              <url>jdbc:mysql://127.0.0.1:3306/databaseName</url>
              <user>root</user>
              <password>root</password>
            </dataSource>
        </connectionSource>
  </appender> -->
</configuration>
```

## 3.PageHelper

### （1）==实现原理==

1. **设置分页参数**：当调用`PageHelper.startPage(int pageNum, int pageSize)`方法时，PageHelper内部会将分页参数（页码和每页数量）保存到当前线程的`ThreadLocal`变量中。
2. **修改查询SQL**：PageHelper实现了MyBatis的`Interceptor`接口，通过拦截器机制，当执行数据库查询操作时，PageHelper会获取到当前线程的分页参数。根据获取到的分页参数，PageHelper动态地修改原始SQL语句，加入分页的SQL片段（如`LIMIT`和`OFFSET`子句），以实现数据库层面的分页功能。
3. **执行分页查询**：修改后的SQL语句被执行，数据库返回当前页的数据。
5. **清理分页参数**：查询完成后，通常在`finally`块中，PageHelper会清除`ThreadLocal`中存储的分页参数，避免对后续操作产生影响。

### （2）依赖

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.1</version>
</dependency>
```

注意：低版本的pagehelper可能存在循环依赖

### （3）配置

SpringBoot会自动配置MyBatis分页。

### （4）业务

#### 1）Emp

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private Date hiredate;
    private Double sal;
    private Double comm;
    private Integer deptno;
}
```

#### 2）EmpMapper接口和EmpMapper.xml

```java
public interface EmpMapper {
    public List<Emp> selectAll();
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lxy.mapper.EmpMapper">
    <select id="selectAll" resultType="emp" >
        select * from emp
    </select>
</mapper>
```

注意：检查方法名保持一致

#### 3）EmpService接口和EmpServiceImpl

```java
public interface EmpService {
    public List<Emp> findByPage(Integer pageNum, Integer pageSize);
}
```

```java
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Override
    public List<Emp> findByPage(Integer pageNum, Integer pageSize) {
        Page<Emp> page = PageHelper.startPage(pageNum, pageSize);
        List<Emp> list =empMapper.selectAll();
        // 方式1
        System.out.println("当前页:"+page.getPageNum());
        System.out.println("总页数"+page.getPages());
        System.out.println("页大小:"+page.getPageSize());
        System.out.println("总记录数:"+page.getTotal());
        System.out.println("当前页数据"+page.getResult());
        // 方式2
        PageInfo<Emp> pi =new PageInfo<>(list);
        System.out.println("当前页"+pi.getPageNum());
        System.out.println("总页数"+pi.getPages());
        System.out.println("页大小"+pi.getSize());
        System.out.println("总记录数"+pi.getTotal());
        System.out.println("当前页数据"+pi.getList());
        return list;
    }
}
```

注意：不要忘记@Service注解

#### 4）EmpController

```java
@Controller
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpService empService;
    @RequestMapping("/findByPage/{pageNum}/{pageSize}")
    @ResponseBody
    public List<Emp> findByPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize){
        return empService.findByPage(pageNum,pageSize);
    }
}
```

## 4.Druid

Alibaba Druid是一个高性能的数据库连接池，它提供了强大的监控和扩展功能，适用于大规模生产环境。Druid是在为了克服传统数据库连接池的一些限制和不足而设计的，特别是在高并发、高可用和高监控的场景下。Druid已经在多个大型互联网公司得到广泛应用，并被证明是非常可靠和高效的。

### （1）Druid的主要特性

1. **==强大的监控==**：Druid提供了详细的SQL监控，可以输出每条SQL的执行情况，包括执行时间、执行次数等。同时，它还支持数据库密码加密，确保安全性。

2. **高并发**：Druid内部使用了高效的锁机制，可以支持非常高的并发访问。

3. **扩展性**：Druid支持多种数据库，包括MySQL、Oracle、PostgreSQL等，几乎可以无缝替换其他数据库连接池。

4. **稳定性**：Druid通过内置的监控和自我诊断能力，可以有效地防止数据库连接泄露等问题，提高应用的稳定性。

5. **丰富的配置项**：Druid提供了丰富的配置项，允许开发者根据自己的需求灵活配置，如初始连接数、最大连接数、最小空闲连接数等。

### （2）依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.10</version>
</dependency>
```

### （3）配置

修改application.xml的spring配置

```yml
spring:
  datasource:
    # 使用阿里的Druid连接池
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 填写你数据库的url、登录名、密码和数据库名
    url: jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: root
    password: root
    druid:
      initial-size: 5
      min-idle: 5
      maxActive: 20
      # 配置获取连接等待超时的时间
      maxWait: 60000
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      timeBetweenEvictionRunsMillis: 60000
      # 配置一个连接在池中最小生存的时间，单位是毫秒
      minEvictableIdleTimeMillis: 300000
      validationQuery: SELECT 1
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      # 打开PSCache，并且指定每个连接上PSCache的大小
      poolPreparedStatements: true
      maxPoolPreparedStatementPerConnectionSize: 20
      # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
      filters: stat,wall,slf4j
      # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
      connectionProperties: druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
      # 配置DruidStatFilter
      web-stat-filter:
        enabled: true
        url-pattern: "/*"
        exclusions: "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"
      # 配置DruidStatViewServlet
      stat-view-servlet:
        url-pattern: "/druid/*"
        # IP白名单(没有配置或者为空，则允许所有访问)
        allow: 127.0.0.1,192.168.8.109
        # IP黑名单 (存在共同时，deny优先于allow)
        deny: 192.168.1.188
        #  禁用HTML页面上的“Reset All”功能
        reset-enable: false
        # 登录名
        login-username: admin
        # 登录密码
        login-password: 123456
```

注意：druid在datasource的下一级

### （4）测试

> [Druid Stat Index](http://localhost:8080/druid/index.html)

![image-20240319115616483](.\assets\image-20240319115616483.png)







## 5.JSP

### （1）依赖

```xml
<!--JSP依赖-->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
    <scope>provided</scope>
</dependency>
```

### （2）配置