**前言：**在快速演变的现代软件开发领域，数据持久化技术不仅是基础，更是决定应用性能与可靠性的关键。尽管传统的JDBC提供了强大的功能，其复杂的对象构建和操作流程却常常使开发者感到头疼。为了突破这一束缚，ORM（对象关系映射）框架悄然崛起，以其简洁高效的模式，重新定义了数据处理方式。本文将深入探讨ORM的概念，并以Java领域广泛应用的MyBatis框架为例，揭示其在实际开发中的独特价值和强大功能。

本篇文章不仅详细介绍了MyBatis在实践中的常用方法，而且深入剖析了其核心配置，主要针对其基础概念及应用进行阐述。在未来的系列文章中，我们将继续探索MyBatis的源码，以期更全面深入地理解其背后的原理和技术细节。

# 一、什么是ORM

ORM（Object-Relational Mapping，对象关系映射）是一种在关系型数据库和编程语言的对象之间进行自动化持久化数据的技术。通过ORM框架，开发者可以使用自己熟悉的编程语言中的对象来操作数据，而无需直接编写复杂的SQL查询语句。这不仅简化了数据操作，还提升了开发效率。

## 1.常见的ORM框架

- **Hibernate**：Java领域最知名的ORM框架之一，功能强大，支持多种复杂映射关系。
- **Entity Framework**：Microsoft推出的ORM框架，适用于.NET平台。
- **Django ORM**：Python语言中的知名ORM框架，内置于Django框架中。

## 2.MyBatis：一种半自动化的ORM框架

MyBatis 是一个流行的Java持久层框架，它提供了半自动化的ORM功能。与全自动化的ORM框架（如Hibernate）不同，MyBatis允许开发者更细粒度地控制SQL语句，并通过XML或注解来配置和映射原生信息，从而实现Java对象与数据库表之间的映射。

MyBatis特别适合以下场景：

- **需要高度定制化SQL的项目**：如报表系统、复杂查询和数据统计分析等。
- **对性能要求高的项目**：MyBatis通过手写SQL提供了更高的性能优化空间。
- **已有大量原生SQL的项目**：MyBatis可以无缝集成已有的SQL语句，降低迁移成本。

ORM框架通过简化数据操作，提高了开发效率和代码可维护性。在Java领域，MyBatis以其灵活性和高性能得到了广泛应用。无论是新项目还是现有项目，选择合适的ORM框架都能为开发工作带来显著的提升。

# 二、MyBatis编程示例

> 在这个 MyBatis 编程示例中，我们将通过 Maven 来管理依赖，并一步步展示如何使用 MyBatis 框架来进行数据库操作。

## 1.导入相关依赖

首先，我们需要在 Maven 的 `pom.xml` 文件中添加必要的依赖，包括 MyBatis 的核心库、MySQL 的 JDBC 驱动，以及 Lombok 库，后者用于简化实体类的编写。

```xml
<!--mybatis 核心jar包-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.16</version>
</dependency>
<!--mysql驱动-->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.32</version>
</dependency>
```

## 2.相关配置

接着，我们需要在 `mybatis-config.xml` 配置文件中指定数据库连接的各项参数，并引入数据库连接属性文件 `db.properties`。此外，我们还定义了实体类的包路径以便 MyBatis 自动扫描，并配置了数据源和事务管理器。

```properties
jdbc_driver=com.mysql.cj.jdbc.Driver
jdbc_url=jdbc:mysql://127.0.0.1:3306/mybatisdb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
jdbc_username=root
jdbc_password=root
```



```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--导入外部配置属性-->
    <properties resource="db.properties"></properties>
    <!--包扫描实体类-->
    <typeAliases>
        <package name="com.lxy.entity"/>
    </typeAliases>
    <!--配置环境信息-->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc_driver}"/>
                <property name="url" value="${jdbc_url}"/>
                <property name="username" value="${jdbc_username}"/>
                <property name="password" value="${jdbc_password}"/>
            </dataSource>
        </environment>
    </environments>
    <!--加载mapper映射文件-->
    <mappers>
        <mapper resource="com/lxy/mapper/DeptMapper.xml"></mapper>
        <mapper resource="com/lxy/mapper/EmpMapper.xml"></mapper>
    </mappers>
</configuration>
```

## 3.创建表&实体类

在数据库方面，我们通过 SQL 脚本创建了 `dept` 和 `emp` 表，并插入了初始数据。相应地，我们也定义了两个 Java 实体类 `Dept` 和 `Emp`，使用 Lombok 注解简化了属性的 getter 和 setter 方法。

```sql
CREATE TABLE `dept`(
    `deptno` INT(2) NOT NULL, 
    `dname` VARCHAR(14),
    `loc` VARCHAR(13),
    CONSTRAINT pk_dept PRIMARY KEY(deptno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO dept VALUES (10,'ACCOUNTING','NEW YORK'); 
INSERT INTO dept VALUES (20,'RESEARCH','DALLAS'); 
INSERT INTO dept VALUES (30,'SALES','CHICAGO');  
INSERT INTO dept VALUES (40,'OPERATIONS','BOSTON');


CREATE TABLE `emp` (
    `empno` int(4) NOT NULL PRIMARY KEY,
    `ename` VARCHAR(10),  
    `job` VARCHAR(9),  
    `mgr` int(4),  
    `hiredate` DATE,  
    `sal` float(7,2),  
    `comm` float(7,2),  
    `deptno` int(2),
    CONSTRAINT fk_deptno FOREIGN KEY(deptno) REFERENCES dept(deptno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO EMP VALUES (7369,'SMITH','CLERK',7902,'1980-12-17',800,NULL,20); 
INSERT INTO EMP VALUES (7499,'ALLEN','SALESMAN',7698,'1981-02-20',1600,300,30);
INSERT INTO EMP VALUES (7521,'WARD','SALESMAN',7698,'1981-02-22',1250,500,30); 
INSERT INTO EMP VALUES (7566,'JONES','MANAGER',7839,'1981-04-02',2975,NULL,20); 
INSERT INTO EMP VALUES (7654,'MARTIN','SALESMAN',7698,'1981-09-28',1250,1400,30); 
INSERT INTO EMP VALUES (7698,'BLAKE','MANAGER',7839,'1981-05-01',2850,NULL,30); 
INSERT INTO EMP VALUES (7782,'CLARK','MANAGER',7839,'1981-06-09',2450,NULL,10); 
INSERT INTO EMP VALUES (7788,'SCOTT','ANALYST',7566,'1987-07-13',3000,NULL,20); 
INSERT INTO EMP VALUES (7839,'KING','PRESIDENT',NULL,'1981-11-07',5000,NULL,10); 
INSERT INTO EMP VALUES (7844,'TURNER','SALESMAN',7698,'1981-09-08',1500,0,30); 
INSERT INTO EMP VALUES (7876,'ADAMS','CLERK',7788,'1987-07-13',1100,NULL,20); 
INSERT INTO EMP VALUES (7900,'JAMES','CLERK',7698,'1981-12-03',950,NULL,30); 
INSERT INTO EMP VALUES (7902,'FORD','ANALYST',7566,'1981-12-03',3000,NULL,20); 
INSERT INTO EMP VALUES (7934,'MILLER','CLERK',7782,'1982-01-23',1300,NULL,10);

CREATE TABLE `salgrade` (  
    `grade` int, 
    `losal` int,  
    `hisal` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 

INSERT INTO SALGRADE VALUES (1,700,1200); 
INSERT INTO SALGRADE VALUES (2,1201,1400); 
INSERT INTO SALGRADE VALUES (3,1401,2000); 
INSERT INTO SALGRADE VALUES (4,2001,3000); 
INSERT INTO SALGRADE VALUES (5,3001,9999);
```



```java
@Data
public class Dept implements Serializable {

    private Integer deptno;

    private String dname;

    private String loc;

    private static final long serialVersionUID = 1L;
}
```

```java
@Data
public class Emp implements Serializable {

    private Integer empno;

    private String ename;

    private String job;

    private Integer mgr;

    private Date hiredate;

    private Double sal;

    private Double comm;

    private Integer deptno;

    private static final long serialVersionUID = 1L;
}
```

## 4.XML映射器

在 XML 映射器文件中，我们定义了如何将 SQL 查询的结果映射到 Java 实体类 `Emp`。我们定义了一个基础的结果映射和一个查询所有员工的 SQL 语句。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lxy.mapper.EmpMapper">

    <resultMap id="BaseResultMap" type="com.lxy.entity.Emp">
            <id property="empno" column="empno" jdbcType="INTEGER"/>
            <result property="ename" column="ename" jdbcType="VARCHAR"/>
            <result property="job" column="job" jdbcType="VARCHAR"/>
            <result property="mgr" column="mgr" jdbcType="INTEGER"/>
            <result property="hiredate" column="hiredate" jdbcType="DATE"/>
            <result property="sal" column="sal" jdbcType="FLOAT"/>
            <result property="comm" column="comm" jdbcType="FLOAT"/>
            <result property="deptno" column="deptno" jdbcType="INTEGER"/>
    </resultMap>

    <select id="listEmp" resultType="emp" >
        select * from emp 
    </select>
</mapper>
```

## 5.获取sql会话

在 Java 代码中，我们首先解析配置文件来构建一个 `SqlSessionFactory`，然后从这个工厂获取一个 `SqlSession`。

```java
/*解析mybatis-config.xml配置文件*/
InputStream resource = Resources.getResourceAsStream("mybatis-config.xml");
/*构建SqlSession工厂*/
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resource);
/*构建sql会话*/
SqlSession session = factory.openSession();
```

## 6.执行sql

通过这个会话，我们可以执行映射器中定义的 SQL 查询，并将查询结果打印出来。

```java
/*执行sql*/
List<Emp> listEmp = session.selectList("com.lxy.mapper.EmpMapper.listEmp");
listEmp.forEach(System.out::println);
```

## 7.关闭sql会话

最后，不要忘记在结束程序前关闭 SQL 会话，以释放数据库连接和其他资源。

```java
/*关闭sql会话*/
session.close();
```

# 三、全局配置文件详解

## 1.文件结构

MyBatis 的全局配置文件（mybatis-config.xml）包含了影响 MyBatis 行为的设置和属性。下面是一个典型的配置文件结构：

configuration

- properties
- settings
- typeAliases
- typeHandlers
- objectFactory
- plugins
- environments
  - environment
    - transactionManager
    - dataSource
- databaseIdProvider
- mappers

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--这里可以指定一个属性文件，通过这个文件可以配置数据库连接的各种属性-->
    <properties resource="db.properties"/>
    <settings>
        <!-- 配置详细设置 -->
        <!--用来配置 MyBatis 的运行时行为，比如懒加载、缓存等-->
    </settings>
    <typeAliases>
        <!-- 类型别名定义 -->
        <!--为 Java 类型设置一个短的名字，它只和 XML 配置有关，简化了 XML 配置文件-->
    </typeAliases>
    <typeHandlers>
        <!-- 类型处理器定义 -->
        <!-- 指定不同类型之间的转换规则，例如 JDBC 类型和 Java 类型之间的转换-->
    </typeHandlers>
    <objectFactory>
        <!-- 对象工厂设置 -->
        <!--通过实现 `ObjectFactory` 接口，你可以自定义对象创建的方式，通常用于依赖注入等高级功能-->
    </objectFactory>
    <plugins>
        <!-- 插件配置 -->
        <!--MyBatis 允许你插入自定义插件，可以在 SQL 执行过程中拦截调用-->
    </plugins>
    <environments>
        <!-- 环境配置 -->
        <!--配置数据库的环境，可以配置多个环境，每个环境包括数据源和事务管理器-->
        <environment id="development">
            <!--用于配置事务管理类型-->
            <transactionManager type="JDBC"/>
            <!--用于配置数据库连接-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc_driver}"/>
                <property name="url" value="${jdbc_url}"/>
                <property name="username" value="${jdbc_username}"/>
                <property name="password" value="${jdbc_password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!-- 映射文件配置 -->
        <!--指定映射器文件的位置，映射器文件定义了 SQL 代码和 Java 类型之间的映射关系-->
    </mappers>
</configuration>
```

## 2.标签详解

### （1）configuration

这是配置文件的根节点，所有的配置项都包含在这里。

### （2）properties 

这部分用于定义外部配置文件的路径，或直接在此配置一些属性。可以在配置文件的其他部分通过 `${propertyName}` 的方式引用这里定义的属性值。

```xml
<!--定义外部配置文件路径，根路径为class path-->
<properties resource="database.properties">
    <!--直接配置一些属性 -->
    <property name="username" value="root"/>
    <property name="password" value="secret"/>
</properties>
```

### （3）settings

影响 MyBatis 全局行为的细粒度设置，例如缓存、懒加载等。

| 设置参数                         | 描述                                                         | 有效值                                           | 默认值                                                |
| -------------------------------- | :----------------------------------------------------------- | :----------------------------------------------- | :---------------------------------------------------- |
| cacheEnabled                     | 全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存。   | true                                             | false                                                 |
| lazyLoadingEnabled               | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态。 | true                                             | false                                                 |
| aggressiveLazyLoading            | 当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载（参考lazyLoadTriggerMethods). | true                                             | false                                                 |
| multipleResultSetsEnabled        | 是否允许单一语句返回多结果集（需要兼容驱动）。               | true                                             | false                                                 |
| useColumnLabel                   | 使用列标签代替列名。不同的驱动在这方面会有不同的表现， 具体可参考相关驱动文档或通过测试这两种不同的模式来观察所用驱动的结果。 | true                                             | false                                                 |
| useGeneratedKeys                 | 允许 JDBC 支持自动生成主键，需要驱动兼容。 如果设置为 true 则这个设置强制使用自动生成主键，尽管一些驱动不能兼容但仍可正常工作（比如 Derby）。 | true                                             | false                                                 |
| autoMappingBehavior              | 指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示取消自动映射；PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。 FULL 会自动映射任意复杂的结果集（无论是否嵌套）。 | NONE, PARTIAL, FULL                              | PARTIAL                                               |
| autoMappingUnknownColumnBehavior | 指定发现自动映射目标未知列（或者未知属性类型）的行为。 NONE: 不做任何反应WARNING: 输出提醒日志('org.apache.ibatis.session.AutoMappingUnknownColumnBehavior' 的日志等级必须设置为 WARN) FAILING: 映射失败 (抛出 SqlSessionException) | NONE, WARNING, FAILING                           | NONE                                                  |
| defaultExecutorType              | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。 | SIMPLE REUSE BATCH                               | SIMPLE                                                |
| defaultStatementTimeout          | 设置超时时间，它决定驱动等待数据库响应的秒数。               | 任意正整数                                       | Not Set (null)                                        |
| defaultFetchSize                 | 为驱动的结果集获取数量（fetchSize）设置一个提示值。此参数只可以在查询设置中被覆盖。 | 任意正整数                                       | Not Set (null)                                        |
| safeRowBoundsEnabled             | 允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为false。 | true                                             | false                                                 |
| safeResultHandlerEnabled         | 允许在嵌套语句中使用分页（ResultHandler）。如果允许使用则设置为false。 | true                                             | false                                                 |
| mapUnderscoreToCamelCase         | 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。 | true\| false                                     | False                                                 |
| localCacheScope                  | MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。 默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。 | SESSION\| STATEMENT                              | SESSION                                               |
| jdbcTypeForNull                  | 当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。 某些驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 | JdbcType 常量. 大多都为: NULL, VARCHAR and OTHER | OTHER                                                 |
| lazyLoadTriggerMethods           | 指定哪个对象的方法触发一次延迟加载。                         | 用逗号分隔的方法列表。                           | equals,clone,hashCode,toString                        |
| defaultScriptingLanguage         | 指定动态 SQL 生成的默认语言。                                | 一个类型别名或完全限定类名。                     | org.apache.ibatis.scripting.xmltags.XMLLanguageDriver |
| defaultEnumTypeHandler           | 指定 Enum 使用的默认 TypeHandler 。 (从3.4.5开始) 	一个类型别名或完全限定类名。 | org.apache.ibatis.type.EnumTypeHandler           |                                                       |
| callSettersOnNulls               | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这对于有 Map.keySet() 依赖或 null 值初始化的时候是有用的。注意基本类型（int、boolean等）是不能设置成 null 的。 | true\| false                                     | false                                                 |
| returnInstanceForEmptyRow        | 当返回行的所有列都是空时，MyBatis默认返回null。 当开启这个设置时，MyBatis会返回一个空实例。 请注意，它也适用于嵌套的结果集 (i.e. collectioin and association)。（从3.4.2开始） | true\| false                                     | false                                                 |
| logPrefix                        | 指定 MyBatis 增加到日志名称的前缀。                          | 任何字符串                                       | Not set                                               |
| logImpl                          | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J                                            | LOG4J\| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING\     |
| proxyFactory                     | 指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。    | CGLIB\                                           | JAVASSIST                                             |
| vfsImpl                          | 指定VFS的实现                                                | 自定义VFS的实现的类全限定名，以逗号分隔。        | Not set                                               |
| useActualParamName               | 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的工程必须采用Java 8编译，并且加上-parameters选项。（从3.4.1开始） | true\| false                                     | true                                                  |
| configurationFactory             | 指定一个提供Configuration实例的类。 这个被返回的Configuration实例用来加载被反序列化对象的懒加载属性值。 这个类必须包含一个签名方法static Configuration getConfiguration(). (从 3.2.3 版本开始) | 类型别名或者全类名.                              | Not set                                               |

###  （4）typeAliases

为 Java 类型设置一个短的名字，可以在 XML 映射文件中使用。并且预先定义好了很多类型别名，具体在`org.apache.ibatis.type.TypeAliasRegistry`类中，这也是可以使用基本类型别名的原因。

有两种主要方式来设置别名：

1. **为单个类指定别名**：

   ```xml
   <typeAliases>
       <typeAlias alias="emp" type="com.lxy.entity.Emp" />
   </typeAliases>
   ```

2. **通过包扫描自动设置别名**：
   别名默认为类名的首字母小写。例如，对于类 `com.lxy.entity.Emp`，默认别名将是 `emp`。

   ```xml
   <typeAliases>
       <package name="com.lxy.entity"/>
   </typeAliases>
   ```

配置好别名后，在映射文件中，可以直接使用这些别名来引用对应的类。这主要体现在两个属性上：

- **`resultType`**：指定 SQL 查询返回的结果集应该被映射成的 Java 类型。
- **`parameterType`**：指定传入 SQL 语句的参数类型。

###  （5）typeHandlers

指定不同类型之间的转换规则，例如 Java 类型与数据库类型的转换,通过实现`TypeHandler`或继承`BaseTypeHandler`，实现自定义类型处理器。

```xml
<typeHandlers>
    <typeHandler handler="com.example.MyTypeHandler"/>
</typeHandlers>
```

### （6）objectFactory

通过实现 `ObjectFactory` 或继承`DefaultObjectFactory`，可以自定义对象创建的过程。

```xml
<objectFactory type="com.example.MyObjectFactory"/>
```

###  （7）plugins

允许你插入自定义行为到 MyBatis 操作数据库的过程中，例如：拦截器。

```xml
<plugins>
    <plugin interceptor="com.example.MyPlugin">
        <property name="someProperty" value="100"/>
    </plugin>
</plugins>
```

### （8）environments

配置不同环境下的数据库连接，可以定义多个环境。

- **JDBC 事务管理器**：直接使用 JDBC 的提交和回滚功能，适用于简单的应用程序。
- **MANAGED 事务管理器**：容器负责管理事务整个生命周期，通常用在 JEE 应用服务器中。

```xml
<environments default="development">
    <environment id="development">
        <!--事务管理器-->
        <transactionManager type="JDBC"/>
        <!--数据源-->
        <dataSource type="POOLED">
            <property name="driver" value="com.mysql.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://localhost/mybatis"/>
            <property name="username" value="${username}"/>
            <property name="password" value="${password}"/>
        </dataSource>
    </environment>
</environments>
```

事务的管理通常在服务层进行，确保一系列操作要么全部成功，要么全部失败。在使用 Spring 框架集成 MyBatis 时，可以利用 Spring 的声明式事务管理来简化事务控制。

### （9）mappers

指定 SQL 映射文件的位置，这些文件包含 SQL 语句和映射定义。

使用 `resource` 属性是最常见的加载方式。这种方式通过类路径（Classpath）来加载映射文件。这要求映射文件在项目的类路径中，通常放在 `src/main/resources` 目录下。

```xml
<mappers>
    <mapper resource="mapper/DeptMapper.xml"></mapper>
    <mapper resource="mapper/EmpMapper.xml"></mapper>
</mappers>
```

在代理模式下，通过指定映射器接口的完全限定类名来加载映射文件。MyBatis 会查找与映射器接口同名的 XML 文件（只是扩展名不同）。这种方式适合使用映射器接口（Mapper Interface）和 XML 文件结合的方式。

```xml
<mappers>
    <mapper class="com.lxy.mapper.EmpMapper"/>
    <mapper class="com.lxy.mapper.DeptMapper"/>
</mappers>
```

URL通过指定映射文件的 URL 来加载。这种方式适用于映射文件位于网络或特定位置的情况，不常用于普通项目开发。

```xml
<mappers>
    <mapper url="file:D:\program-study-note\Java\framework\MyBatis\mybatis-study-all\src\main\resources\mapper\DeptMapper.xml"/>
</mappers>
```

Package这种方式可以扫描指定包下的所有映射器接口，并为它们加载同名的 XML 文件。这样，就不需要逐个列出每个映射器，可以大大简化配置文件。

```xml
<mappers>
    <package name="com.lxy.mapper"/>
</mappers>
```

但是使用`package`时，要注意映射文件应与接口处于同一目录。

通常，Mapper 接口位于 `src/main/java` 下的某个包中（例如 `com.lxy.mapper`），而 XML 映射文件则放在 `src/main/resources` 下，但应保持与 Java 包结构相同的路径。这样做的主要原因是 Maven 或 Gradle 构建工具在编译和打包时，通常不会将 `src/main/java` 下的非 `.java` 文件包含在内。

那么对应的 XML 文件应该放在 `src/main/resources/com/lxy/mapper` 目录下。这样，无论是接口还是 XML 文件，它们的包结构都是 `com.lxy.mapper`。

# 四、XML映射器文件

MyBatis 的 XML 映射器文件是框架中非常核心的组成部分，它定义了如何将 SQL 语句映射到 Java 方法。通过 XML 映射器文件，你可以定义 SQL 语句、参数传递方式、结果集处理方式等。这些文件通常以 `.xml` 结尾，并与接口（Mapper 接口）相关联。下面我们将详细解释 XML 映射器的各个组件和其功能。

## 1.文件结构

映射文件结构如下：

`mapper`

- `cache` – 该命名空间的缓存配置。
- `cache-ref` – 引用其它命名空间的缓存配置。
- `resultMap` – 描述如何从数据库结果集中加载对象，是最复杂也是最强大的元素。
- `parameterMap` – 老式风格的参数映射。此元素已被废弃，并可能在将来被移除！请使用行内参数映射。文档中不会介绍此元素。
- `insert` – 映射插入语句。
- `update` – 映射更新语句。
- `delete` – 映射删除语句。
- `select` – 映射查询语句。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--指定与映射器文件关联的 Java Mapper 接口的全限定名，这是连接接口方法和 SQL 语句的桥梁-->
<mapper namespace="com.example.mapper.UserMapper">
    
	<!--为当前命名空间配置缓存，提高查询效率，开启二级缓存-->
    <cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
    
    <!--引用另一个命名空间的缓存配置，实现缓存共享-->
    <cache-ref namespace="com.example.otherMapper"/>
    

    <!-- 结果映射 -->
    <!--描述如何从数据库结果集映射到 Java 对象，支持复杂的数据结构-->
    <resultMap id="userResultMap" type="User">
        <id column="id" property="id" jdbcType="INTEGER"/>
        <result column="name" property="name" jdbcType="VARCHAR"/>
        <!-- 更多字段映射 -->
    </resultMap>

    <!-- SQL 语句 -->
    <!--定义 SQL 操作，`id` 属性为接口中方法的名称。-->
    
    <!--查询操作-->
    <select id="selectUserById" parameterType="int" resultMap="userResultMap">
        SELECT * FROM users WHERE id = #{id}
    </select>

    <!-- 更新操作 -->
    <update id="updateUser">
        UPDATE users SET name = #{name} WHERE id = #{id}
    </update>

    <!-- 插入操作 -->
    <!-- 映射插入操作，可返回自动生成的键值 -->
    <insert id="insertUser",useGeneratedKeys="true" keyProperty="id">
        INSERT INTO users (name) VALUES (#{name})
    </insert>

    <!-- 删除操作 -->
    <delete id="deleteUser">
        DELETE FROM users WHERE id = #{id}
    </delete>
</mapper>
```

## 2.`select`属性

| 属性            | 描述                                                         |
| :-------------- | :----------------------------------------------------------- |
| `id`            | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| `parameterType` | 将会传入这条语句的参数的类全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过类型处理器（TypeHandler）推断出具体传入语句的参数，默认值为未设置（unset）。 |
| `parameterMap`  | 用于引用外部 parameterMap 的属性，目前已被废弃。请使用行内参数映射和 parameterType 属性。 |
| `resultType`    | 期望从这条语句中返回结果的类全限定名或别名。 注意，如果返回的是集合，那应该设置为集合包含的类型，而不是集合本身的类型。 resultType 和 resultMap 之间只能同时使用一个。 |
| `resultMap`     | 对外部 resultMap 的命名引用。结果映射是 MyBatis 最强大的特性，如果你对其理解透彻，许多复杂的映射问题都能迎刃而解。 resultType 和 resultMap 之间只能同时使用一个。 |
| `flushCache`    | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：false。 |
| `useCache`      | 将其设置为 true 后，将会导致本条语句的结果被二级缓存缓存起来，默认值：对 select 元素为 true。 |
| `timeout`       | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖数据库驱动）。 |
| `fetchSize`     | 这是一个给驱动的建议值，尝试让驱动程序每次批量返回的结果行数等于这个设置值。 默认值为未设置（unset）（依赖驱动）。 |
| `statementType` | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| `resultSetType` | FORWARD_ONLY，SCROLL_SENSITIVE, SCROLL_INSENSITIVE 或 DEFAULT（等价于 unset） 中的一个，默认值为 unset （依赖数据库驱动）。 |
| `databaseId`    | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |
| `resultOrdered` | 这个设置仅针对嵌套结果 select 语句：如果为 true，将会假设包含了嵌套结果集或是分组，当返回一个主结果行时，就不会产生对前面结果集的引用。 这就使得在获取嵌套结果集的时候不至于内存不够用。默认值：`false`。 |
| `resultSets`    | 这个设置仅适用于多结果集的情况。它将列出语句执行后返回的结果集并赋予每个结果集一个名称，多个名称之间以逗号分隔。 |

## 3.参数传递

### （1）`parameterType`

#### 单个基本类型或其包装类

当 SQL 语句需要一个简单的参数，如基本数据类型或其包装类（例如 `int`, `Integer`, `String` 等），可以直接指定这些类型。MyBatis 已经为这些常见的基本类型提供了内置的别名，使得配置更加简洁。

```xml
<select id="selectUserById" parameterType="int" resultType="com.example.User">
  SELECT * FROM users WHERE id = #{id}
</select>
```

在这个例子中，`parameterType="int"` 指明了传入参数是一个整型（`int`），在 SQL 语句中通过 `#{id}` 占位符来引用。

#### 复杂对象类型

如果参数是一个更复杂的 Java 对象，例如一个自定义的 Java Bean，MyBatis 能够根据对象的属性名自动匹配属性值到 SQL 语句中的对应占位符。如果你已经通过 `typeAliases` 为这个 Java 类型定义了一个别名，或者 MyBatis 已经扫描了包含这个类的包，你可以直接使用该别名作为 `parameterType`。

```xml
<!-- 使用别名 "user" -->
<insert id="insertUser" parameterType="user">
  INSERT INTO users (name, email) VALUES (#{name}, #{email})
</insert>
```

在这个例子中，假设 `User` 是一个包含 `name` 和 `email` 属性的 Java 类，MyBatis 将自动绑定这些属性到 SQL 语句中的 `#{name}` 和 `#{email}`。

如果该Java对象内部嵌套对象时，你可以在 SQL 映射文件中通过属性路径来引用这些属性。这样做可以直接在 SQL 语句中使用这些属性。

```xml
<select id="findUserByDetails" parameterType="user" resultType="com.example.User">
  SELECT * FROM users WHERE name = #{name} AND address.city = #{address.city}
</select>
```

在这个例子中，假设 `User` 类有一个 `name` 属性和一个 `address` 对象，而 `address` 对象又有一个 `city` 属性。你可以直接在 SQL 语句中使用 `#{address.city}` 来引用这个属性。

#### 多参数传递

在不想创建一个专门的类来包含所有参数的情况下，可以使用 `Map` 来传递多个参数。在 XML 配置中，`parameterType="map"` 允许你在 SQL 语句中通过 Map 的键来引用值。

```xml
<select id="findUserByNameAndEmail" parameterType="map" resultType="com.example.User">
  SELECT * FROM users WHERE name = #{name} AND email = #{email}
</select>
```

在这个例子中，你可以创建一个 Map 对象，并放入 `name` 和 `email` 键值对，MyBatis 将从 Map 中取出对应的值并替换 SQL 语句中的占位符。

### （2）`resultType`

#### 单个基本类型或其包装类

当你期望从数据库查询返回一个单一值时，如执行计数或求和操作，可以直接将 `resultType` 设置为该值的数据类型。这样，MyBatis 将自动处理结果，并返回一个单一的基本类型或其包装类的实例。

```xml
<select id="countUsers" resultType="int">
  SELECT COUNT(*) FROM users
</select>
```

这个查询将返回用户总数，结果类型为 `int`，适用于任何需要返回一个单一数字结果的场景。

#### 复杂对象类型

当查询结果需要映射到一个复杂的 Java 对象时，可以指定一个自定义的 Java 类型作为 `resultType`。MyBatis 将自动按照列名和 Java 类的属性名的匹配来进行结果集到对象的映射。

```xml
<select id="selectUserById" resultType="com.example.User">
  SELECT * FROM users WHERE id = #{id}
</select>
```

在这个例子中，假设 `User` 类包含与数据库表 `users` 列相对应的属性，MyBatis 将数据库查询的结果直接映射到 `User` 类的实例中。

#### 返回集合类型

如果查询预计返回多条记录，可以设置 `resultType` 为集合中元素的类型。在 MyBatis 中，这通常意味着返回的是一个 `List` 类型的集合，集合中的每个元素是 `resultType` 指定的类型。

```xml
<select id="selectAllUsers" resultType="com.example.User">
  SELECT * FROM users
</select>
```

这个查询将返回一个包含所有用户的列表，每个列表项都是一个 `User` 对象。在应用中处理时，这将自动被 MyBatis 解析为 `List<com.example.User>`。

### （3）`resultMap`

在 MyBatis 中，`resultMap` 是一种高级的结果映射机制，用于处理那些不能通过简单的 `resultType` 来映射的复杂情形。使用 `resultMap` 可以更精细地控制数据库结果集的映射过程，特别是在处理复杂的数据结构、关联、集合、继承等场景时。这提供了比自动映射更高的灵活性和控制力。

`resultMap` 允许你详细地指定如何从 SQL 查询的结果集映射数据到 Java 对象的属性。它包括字段的映射、数据类型转换、复杂关联和集合的处理等。

#### `resultMap`常见属性

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| `property`    | 需要映射到 JavaBean 的属性名称。此属性在 Java 类中对应的字段名。 |
| `javaType`    | property的 Java 类型，可以是一个完整的类名或一个类型别名。如果匹配的是一个 JavaBean，那 MyBatis 通常能够自动检测其类型。 |
| `column`      | 数据表的列名或者列的别名，指定从数据库表中哪一列映射到指定的属性上。 |
| `jdbcType`    | 列在数据库中的 JDBC 类型。这个属性主要在插入、更新或删除操作中针对允许空值的列有用，以指定 JDBC 应如何处理 SQL NULL 值。 |
| `typeHandler` | 类型处理器，用于覆写 MyBatis 的默认行为，实现 `javaType` 和 `jdbcType` 之间的转换。通常可以省略，MyBatis 会自动探测使用的类型处理器。 |
| `fetchType`   | 指定加载类型，主要用于关联对象的加载策略，如 `lazy`（懒加载）或 `eager`（立即加载）。 |
| `select`      | 用于 `association`（一对一）和 `collection`（一对多）的属性，指定用于加载这些属性的另一个 SQL 映射语句的完全限定名。 |
| `ofType`      | 用于 `collection` 元素，指明集合中元素的类型，这对于集合中泛型的识别尤其重要。 |

#### `resultMap`常见子元素

| 子元素            | 描述                                                         |
| ----------------- | ------------------------------------------------------------ |
| `<constructor>`   | 用于在结果映射中创建对象时指定构造函数的参数，允许映射复杂类型的对象时指定哪些列映射到构造函数的哪些参数。 |
| `<id>`            | 映射表中的主键列到 Java 对象的一个属性，通常用来标识结果对象的唯一性，也用于优化查询性能。 |
| `<result>`        | 映射表中的非主键列到 Java 对象的属性。这是最常用的元素，用于基本的列到属性的映射。 |
| `<association>`   | 用于映射复杂的对象关系，如一对一关系。它自身可以包含 `<id>`, `<result>`, `<association>`, `<collection>` 等子元素。 |
| `<collection>`    | 用于映射一对多的关系，例如一个用户有多个订单。此元素可以包含 `<id>`, `<result>`, `<association>`, `<collection>` 等子元素，用于定义集合中每个元素的映射。 |
| `<discriminator>` | 基于数据库列的值决定使用哪个 `resultMap`。此元素内部包含多个 `<case>` 元素，用于根据列的值选择正确的映射策略。 |
| `<case>`          | 用于在 `<discriminator>` 元素内部，根据列的特定值指定应使用的 `resultMap`。 |

示例：

```xml
<resultMap id="userResultMap" type="com.example.User">
    <id column="user_id" property="id" jdbcType="INTEGER"/>
    <result column="user_name" property="name" jdbcType="VARCHAR"/>
    <result column="email" property="email" jdbcType="VARCHAR"/>
    <!-- 更复杂的映射可以在这里配置 -->
</resultMap>

<select id="selectUserById" resultMap="userResultMap">
    SELECT user_id, user_name, email FROM users WHERE user_id = #{id}
</select>
```

在这个例子中，`resultMap` 定义了如何将查询结果中的 `user_id`、`user_name` 和 `email` 列映射到 `User` 类的 `id`、`name` 和 `email` 属性。

# 五、API详解

## 1.SqlSessionFactoryBuilder

这个类用于构建 `SqlSessionFactory`，通常从 XML 配置文件或预配置的 `Configuration` 实例来构建。

**主要方法：**

- **build(InputStream inputStream)**：从 XML 配置文件中读取配置信息并构建 `SqlSessionFactory`。
- **build(Configuration config)**：直接使用预先配置好的 `Configuration` 对象来构建 `SqlSessionFactory`。

## 2.SqlSessionFactory

`SqlSessionFactory` 是 MyBatis 的一个核心接口，它是创建 `SqlSession` 的工厂。在 MyBatis 的配置完成后，通常通过 `SqlSessionFactoryBuilder` 来构建 `SqlSessionFactory`，它读取 MyBatis 配置文件（如 mybatis-config.xml）或直接通过 Java 代码配置。

**主要方法：**

- **openSession()**：这是最常用的方法之一，用于获取一个新的 `SqlSession`。你可以选择是否传入参数来控制事务的自动提交行为。
- **openSession(boolean autoCommit)**：获取 `SqlSession` 时，可以指定该会话是否自动提交事务。
- **openSession(Connection connection)**：允许你传入一个 JDBC `Connection` 对象来创建会话。
- **openSession(TransactionIsolationLevel level)**：可以指定事务的隔离级别。
- **openSession(ExecutorType execType)**：允许你指定执行器的类型，例如批处理。

## 3.SqlSession

`SqlSession` 提供了在数据库执行 SQL 命令所需的所有方法。你可以通过 `SqlSession` 实例来直接执行已映射的 SQL 语句。

**主要方法：**

- **selectOne(String statement, Object parameter)**：用于执行返回单条记录的查询语句。
- **selectList(String statement, Object parameter)**：执行返回多条记录的查询语句，结果是列表形式。
- **insert(String statement, Object parameter)**：执行插入操作。
- **update(String statement, Object parameter)**：执行更新操作。
- **delete(String statement, Object parameter)**：执行删除操作。
- **commit()**：提交当前会话的所有更改。
- **rollback()**：撤销当前会话中的所有更改。
- **close()**：关闭会话，释放资源。
- **getMapper(Class<T> type)**：获取映射器（Mapper）接口的实例，该接口的实现由 MyBatis 自动生成。

以下是如何使用 `SqlSessionFactory` 和 `SqlSession` 的简单示例：

```java
// 构建 SqlSessionFactory
InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

// 打开 SqlSession
try (SqlSession session = sqlSessionFactory.openSession()) {
    // 获取 Mapper
    UserMapper mapper = session.getMapper(UserMapper.class);
    // 执行查询
    User user = mapper.findUserById(1);
    System.out.println(user.getName());
    // 提交事务
    session.commit();
} catch (Exception e) {
    e.printStackTrace();
}
```

在这个示例中，我们通过配置文件构建了 `SqlSessionFactory`，然后从这个工厂获取了一个 `SqlSession`。通过这个会话，我们获取了 `UserMapper` 接口的实例，并执行了一个查询操作。

# 六、代理模式

## 1. 传统模式的弊端

传统的数据库操作模式通常涉及直接在代码中编写 SQL 语句和手动管理数据库连接与资源。这种方式有以下几个弊端：

1. **代码耦合度高** ：SQL 语句和业务逻辑代码紧密耦合，修改 SQL 语句可能需要重新编译整个应用，不利于维护和扩展。
   
2. **重复代码多** ：每个数据库操作都需要重复的进行连接管理、资源清理等步骤，增加了代码的冗余。
   
3. **事务管理复杂**  ：在复杂的业务场景中，手动管理事务使得代码更加复杂和容易出错。
   
4. **难以适应变化**  ：随着业务的发展，数据库操作的复杂性可能增加，传统模式下修改和扩展都相对困难。

## 2. 代理流程



![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407191457481.png)

## 3.查询所有员工示例

![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407191503958.png)

首先，定义一个 `EmpMapper` 接口，其中包含一个方法 `listEmp()`，用于查询数据库中所有的员工记录。

```java
public interface EmpMapper {
    List<Emp> listEmp();
}
```

接下来，创建一个映射文件，这里称为 `EmpMapper.xml`。在该文件中，定义一个 `<select>` 查询标签，其 `id` 与接口中的方法名 `listEmp` 相对应，并指定返回结果的类型为 `emp`。

```sql
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lxy.mapper.EmpMapper">

    <select id="listEmp" resultType="emp" >
        select * from emp
    </select>
</mapper>
```

最后，在应用程序中，通过 MyBatis 的 `SqlSession` 获取 `EmpMapper` 接口的代理对象，并调用 `listEmp()` 方法来执行 SQL 查询。查询结果返回一个 `List<Emp>` 对象，其中包含从数据库表 `emp` 中检索到的所有行。通过使用 Java 8 的流操作，可以遍历并打印出每个 `Emp` 对象的详细信息。

```java
/*获取代理对象*/
EmpMapper mapper = session.getMapper(EmpMapper.class);
/*调用接口方法*/
List<Emp> listEmp = mapper.listEmp();
listEmp.forEach(System.out::println);
```

# 七、高级特性

## 1.动态SQL

动态SQL是指那些根据应用程序的需要在运行时构建和执行的SQL语句。

MyBatis 提供了一系列强大的动态 SQL 标签，它们可以在 XML 映射文件中使用，以构建灵活且动态的 SQL 语句。下面是一些常用的 MyBatis 动态 SQL 标签及其介绍：

### （1） `<if>`

`if` 元素用于在 SQL 语句中加入条件判断。根据条件是否满足，可以决定是否包含某部分 SQL 代码。这在需要根据不同的输入构建查询条件时特别有用。

**示例**：在查询用户信息时，可以根据 `username` 是否提供来动态构建 WHERE 子句：

```xml
<select id="findUserByUsername" resultType="User">
    SELECT * FROM users
    <where>
        <if test="username != null and username != ''">
            username = #{username}
        </if>
    </where>
</select>
```

### （2） `<choose>`, `<when>`, `<otherwise>`

`choose` 元素类似于 Java 中的 `switch` 语句，它允许从多个选项中选择一个来执行。结合 `when` 和 `otherwise` 子元素，可以根据条件选择性地构建 SQL 语句的一部分。

**示例**：在查询订单时，可以根据不同的状态代码来动态调整查询条件：

```xml
<select id="findOrder" resultType="Order">
    SELECT * FROM orders
    <where>
        <choose>
            <when test="state == 'NEW'">
                state = 'NEW'
            </when>
            <when test="state == 'PENDING'">
                state = 'PENDING'
            </when>
            <otherwise>
                state = 'CLOSED'
            </otherwise>
        </choose>
    </where>
</select>
```

### （3） `<foreach>`

`foreach` 元素用于遍历集合，并对每个元素执行 SQL 语句片段，常用于实现 IN 查询、批量插入和更新操作。

- **示例**：使用 IN 语句查询多个用户 ID：

  ```xml
  <select id="findUsersByIds" resultType="User">
      SELECT * FROM users
      WHERE id IN
      <foreach item="id" collection="list" open="(" separator="," close=")">
          #{id}
      </foreach>
  </select>
  ```

### （4）`<where>`

智能地插入 `WHERE` 关键字，并且如果其内部条件为真，则自动处理第一个条件前的 `AND` 或 `OR` 关键字。

- **示例**：

  ```xml
  <where>
    <if test="condition1">
      column1 = #{value1}
    </if>
    <if test="condition2">
      AND column2 = #{value2}
    </if>
  </where>
  ```

### （5） `<set>`

`set` 元素通常用于构建动态的 `UPDATE` 语句，它可以根据条件包括或排除特定的列更新。使用 `<set>` 的好处是，它自动处理列的前导逗号，使得构造 SQL 更加简洁。

**示例**：动态更新用户信息，仅更新非空字段：

```xml
<update id="updateUser" parameterType="User">
    UPDATE users
    <set>
        <if test="username != null">
            username = #{username},
        </if>
        <if test="email != null">
            email = #{email},
        </if>
        <if test="age != null">
            age = #{age}
        </if>
    </set>
    WHERE id = #{id}
</update>
```

### （6） `<trim>`

`trim` 元素用于动态添加或删除 SQL 语句的前缀、后缀或同时处理前后缀。这非常有用，尤其是在处理动态 `INSERT` 或 `UPDATE` 语句时，用于添加或移除逗号。

**示例**：动态构建 UPDATE 语句，根据提供的参数决定更新哪些字段：

```xml
<update id="updateUser" parameterType="User">
    UPDATE users
    <set>
        <trim suffixOverrides=",">
            <if test="username != null">username = #{username},</if>
            <if test="email != null">email = #{email},</if>
        </trim>
    </set>
    WHERE id = #{id}
</update>
```

### （7）`<bind>`

`bind` 元素允许你在 XML 映射文件中定义变量，并将其用于 SQL 查询中。这对于复杂的 SQL 表达式或重复使用的值非常有用。

**示例：**使用变量进行模糊查询：

```xml
<select id="findUsersByName" resultType="User">
    <bind name="pattern" value="'%' + name + '%'"/>
    SELECT * FROM users
    WHERE username LIKE #{pattern}
</select>
```

### （8）`<sql>`

`sql` 元素用于定义可重用的 SQL 代码片段。它可以被其他 SQL 映射语句引用，有助于避免重复的 SQL 代码片段，使得维护更加方便。

**示例**：定义一个通用的列选择 SQL 片段：

```xml
<sql id="userColumns">
    id, username, email, age
</sql>

<select id="selectUsers" resultType="User">
    SELECT <include refid="userColumns"/>
    FROM users
</select>

```

## 2.关联（级联）查询

### （1）一对一

一对一关系通常表示一个实体对象与另一个实体对象有唯一关联。

例如，假设每个员工 (`emp`) 有一个与之直接关联的部门信息 (`dept`)，即在原来的Emp类中内嵌一个Dept类。

这里的一对一关系是指每个 `Emp` 对象包含一个 `Dept` 对象。

**示例**：查询员工及其对应的部门信息。

```xml
<select id="selectAllEmployeesWithDept" resultMap="employeeWithDeptResultMap">
    SELECT e.*, d.deptno as d_deptno, d.dname, d.loc
    FROM emp e
    LEFT JOIN dept d ON e.deptno = d.deptno
</select>

<resultMap id="employeeWithDeptResultMap" type="Emp">
    <id column="empno" property="empno"/>
    <result column="ename" property="ename"/>
    <result column="job" property="job"/>
    <result column="mgr" property="mgr"/>
    <result column="hiredate" property="hiredate"/>
    <result column="sal" property="sal"/>
    <result column="comm" property="comm"/>
    <result column="deptno" property="deptno"/>
    <association property="dept" javaType="Dept">
        <id column="deptno" property="deptno"/>
        <result column="dname" property="dname"/>
        <result column="loc" property="loc"/>
    </association>
</resultMap>
```

### （2）一对多

一对多关系通常表示一个实体（如部门）与多个其他实体（如员工）之间的关系。

一个 `Dept` 包含多个 `Emp`，需要进行一对多的映射。

**示例**：查询部门及其下所有员工的信息。

```xml
<select id="selectAllDeptsWithEmployees" resultMap="deptWithEmployeesResultMap">
    SELECT d.*, e.empno, e.ename, e.job, e.mgr, e.hiredate, e.sal, e.comm
    FROM dept d
    LEFT JOIN emp e ON d.deptno = e.deptno
</select>

<resultMap id="deptWithEmployeesResultMap" type="Dept">
    <id column="deptno" property="deptno"/>
    <result column="dname" property="dname"/>
    <result column="loc" property="loc"/>
    <collection property="employees" ofType="Emp">
        <id column="empno" property="empno"/>
        <result column="ename" property="ename"/>
        <result column="job" property="job"/>
        <result column="mgr" property="mgr"/>
        <result column="hiredate" property="hiredate"/>
        <result column="sal" property="sal"/>
        <result column="comm" property="comm"/>
    </collection>
</resultMap>
```

### （3）多对多关系

在数据库模型中，多对多关系是两种实体之间的复杂关系，其中一个实体的记录可以与另一个实体的多个记录相关联，反之亦然。例如，学生和课程之间的关系：一个学生可以注册多门课程，而一门课程也可以被多名学生注册。在 MyBatis 中实现多对多关系通常涉及到使用一个联结表（或中间表）来存储两个实体之间的关系。

数据库表结构

- **Students**
  - student_id (PK)
  - name
- **Courses**
  - course_id (PK)
  - title
- **Student_Course**
  - student_id (FK)
  - course_id (FK)

## 3.子查询

在某些情况下，尤其是在关联的员工数据量非常大或者查询条件非常复杂时，使用子查询来处理每个部门的员工信息可能更有效。子查询允许在需要时才加载员工数据，这可以是通过延迟加载（lazy loading）实现的。

**示例**：根据部门号查询部门及其下所有员工的信息。

```xml
<mapper namespace="com.lxy.mapper.DeptMapper">
    <resultMap id="deptWithEmployeesResultMap" type="Dept">
        <id column="deptno" property="deptno"/>
        <result column="dname" property="dname"/>
        <result column="loc" property="loc"/>
        <!--select 调用另一个sql-->
         <!--column 给另一个sql传递的参数-->
        <!--fetchType 设置加载模式，eager积极加载-->
        <collection property="employees" 
                    ofType="Emp" 
                    javaType="list"
                    select="com.lxy.mapper.EmpMapper.findEmpsByDeptno"
                    column="deptno"                 
                    fetchType="lazy"> 
        </collection>
    </resultMap>

    <select id="selectDeptsWithEmployees" resultMap="deptWithEmployeesResultMap">
        SELECT deptno, dname, loc
        FROM dept WHERE deptno=#{deptno}
    </select>
</mapper>
```

注意：在 `<association>` 或 `<collection>` 的子查询中`column` 属性通常用于传递主查询中某个列的值到子查询中，作为子查询的参数。

## 4.关联查询数据加载模式

### （1）立即加载（Eager Loading）

意味着在执行主查询时，与之相关的所有数据都会被立即从数据库中加载。这是通过在单个查询中使用 JOIN 操作或通过同时发出多个查询来实现的。

当用户请求查看一个商品详情时，商品的基本信息、评价和推荐商品应该立即显示。因此，可以使用立即加载来一次性获取所有相关数据，确保用户体验流畅。

### （2）延迟加载（Lazy Loading）

指只在真正需要数据时才从数据库中加载它们。这通常是通过访问对象的属性触发的，如第一次访问关联的集合或实体字段时。

当访问部门信息时，可以立即加载员工的基本信息，如名字和职位，但更详细的信息，如联系方式和履历，应使用延迟加载，直到用户请求查看。

在 MyBatis 配置文件中，可以全局地或者局部地设置加载策略。

**全局配置**:

```xml
<settings>
    <!--控制是否启用延迟加载（Lazy Loading）-->
    <setting name="lazyLoadingEnabled" value="true"/>
    <!--控制当某一个属性被加载时，是否也同时加载其他所有延迟加载的属性-->
    <setting name="aggressiveLazyLoading" value="false"/> 
</settings>
```

**局部配置**:

```xml
<association property="department" column="dept_id" 
             select="selectDepartmentById" fetchType="lazy"/>
```

### （3）N+1问题

**N+1问题** 是在ORM（对象关系映射）和数据库交互中常见的一个性能问题。它发生在你查询主实体时，并且每个主实体的关联实体也需要进行单独的查询时。具体来说，这意味着首先执行一个查询来获取所有主实体（"1"个查询），然后对每个主实体（假设有"N"个），执行另外的查询来获取其关联的实体。这样，总共就会执行N+1次数据库查询。

例如，如果你有一个部门表（Dept）和员工表（Emp），每个部门有多名员工。当你想要获取所有部门及其所有员工的信息时：
1. 你首先查询部门表以获取所有部门。
2. 然后对于每个部门，你又执行一个查询来获取属于该部门的所有员工。

如果有10个部门，这就会导致1次查询部门的操作加上10次查询员工的操作，共计11次查询，这就是N+1问题。

**立即加载**（Eager Loading）是避免N+1问题的一种策略。在立即加载中，主实体及其关联的实体都在单次操作（通常是一次查询）中被加载。这通常通过使用数据库的JOIN操作来实现，将所有必要的数据在数据库层面一起加载，而不是在应用层面分开查询。

## 5.注解开发

MyBatis 在提供强大的 XML 映射文件的同时，也支持通过注解的方式来简化 SQL 语句的配置和使用。这种方式在简单到中等复杂度的项目中非常方便，特别是在涉及到少量 SQL 操作的时候。使用注解可以减少 XML 配置文件的数量，使项目结构更简洁。

以下是每个注解的详细解释和使用场景：

- **`@Select`**：这个注解用来定义一个 SELECT 查询语句。你可以直接在注解中写入 SQL 语句，并通过方法返回查询结果。例如：

  ```java
  @Select("SELECT * FROM users WHERE id = #{id}")
  User getUserById(int id);
  ```

- **`@Insert`**：用于定义 INSERT 插入语句。你可以通过此注解将一个对象插入到数据库中。例如：

  ```java
  @Insert("INSERT INTO users(name, email) VALUES(#{name}, #{email})")
  void insertUser(User user);
  ```

- **`@Update`**：此注解用于定义 UPDATE 更新语句。可以用来更新数据库中的现有记录。例如：

  ```java
  @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
  void updateUser(User user);
  ```

- **`@Delete`**：用于定义 DELETE 删除语句，用来删除数据库中的记录。例如：

  ```java
  @Delete("DELETE FROM users WHERE id = #{id}")
  void deleteUser(int id);
  ```

- **`@Results`** 和 **`@Result`**：这两个注解通常一起使用，用于映射数据库的查询结果到 Java 对象。这是替代 XML 配置的 `resultMap` 的注解方式。例如：

  ```java
  @Results({
      @Result(property = "id", column = "user_id"),
      @Result(property = "name", column = "user_name")
  })
  @Select("SELECT user_id, user_name FROM users")
  List<User> getUsers();
  ```

- **`@Param`**：此注解用于给 SQL 语句中的参数命名，增加代码的可读性。在使用动态 SQL 时尤其有用。例如：

  ```java
  @Select("SELECT * FROM users WHERE name = #{name}")
  User findUserByName(@Param("name") String name);
  ```

- **`@Mapper`**：这个注解的主要作用是标记一个接口，让 MyBatis 知道这个接口是用于数据库操作的 Mapper 接口。通过标记这个注解，MyBatis 在背后会自动为这个接口生成代理对象，这个代理对象能够执行实际的 SQL 语句。

  在 Spring 框架中，结合 `@MapperScan` 注解可以自动扫描包含 `@Mapper` 的接口，从而自动注册为 Spring 管理的 Bean，这使得在 Spring 应用中集成 MyBatis 变得更加无缝和简洁，而不需要在配置中进行mapper扫描。
  
  只需简单地在接口上添加这个注解即可：
  
  ```java
  @Mapper
  public interface UserMapper {
      // 方法定义
  }
  ```

## 6.缓存机制

MyBatis 提供了两级缓存机制：一级缓存和二级缓存，用来减少数据库访问次数，提高应用性能。



![image-20240618110152586](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407191706405.png)

### （1）一级缓存（本地缓存）

一级缓存是 MyBatis 默认的缓存，它仅仅对一个 SqlSession 内的查询操作有效。当在同一个 SqlSession 中执行多次相同的 SQL 查询时，第一次查询会从数据库中获取数据并将查询结果缓存起来，随后的相同查询就可以直接从缓存中获取结果，避免了数据库访问的开销。

一级缓存的作用域仅限于同一个 SqlSession。如果开启新的 SqlSession 或者调用 SqlSession 的 `clearCache()` 方法，之前的缓存会被清空。

一级缓存虽然可以减少数据库的查询次数，但它只在 SqlSession 的生命周期内有效，因此它在处理跨 Session 的数据一致性时有一定的局限性。

### （2）二级缓存（全局缓存）

二级缓存是 MyBatis 提供的一个可选功能，它的作用范围不限于一个 SqlSession，可以跨 SqlSession 和 Mapper 实例共享数据。二级缓存需要显示开启和配置。

二级缓存可以通过在 mybatis-config.xml 中配置，或者在 Mapper 映射文件中配置。开启后，可以使用注解或 XML 配置来控制缓存策略。

```xml
<!-- mybatis-config.xml-->
<configuration>
    <settings>
        <setting name="cacheEnabled" value="true"/>
    </settings>
</configuration>
```

```xml
<!--Mapper XML-->
<mapper namespace="com.example.mapper.UserMapper">
    <cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
    <select id="selectUser" resultType="com.example.User">
        SELECT * FROM users
    </select>
</mapper>
```

- **`Cache` ：**在 Mapper XML 文件中配置 `<cache>` 标签会开启该 namespace 的二级缓存。一旦配置，所有的 select 查询都将被缓存，而 insert、update、delete 操作会清除这些缓存，以保证数据的一致性。
  - **type**: 缓存的具体实现类型，默认是 `PERPETUAL`。

  - **eviction**: 缓存的回收策略，如 `LRU`（Least Recently Used 最近最少使用）、`FIFO`（First In First Out 先进先出）等。

  - **flushInterval**: 缓存多长时间清空一次，单位是毫秒。

  - **size**: 引用数目，用于设定缓存项的数量，防止内存溢出。

  - **readOnly**: 是否只读，`true` 表示创建缓存对象的副本，`false` 表示直接返回引用。

  - **blocking**: 阻塞，`true` 表示缓存会锁定，直到返回缓存数据为止。
- **`useCache` ：**是否对特定的 `<select>` 查询启用二级缓存，这对于需要频繁更新的数据是非常有用的，因为可以设置为 `false` 来保证数据的实时性。
- **`flushCache` ：**控制执行特定 SQL 操作后是否清空相关缓存，以维护数据一致性。这两个属性的合理配置可以在提高查询效率和保证数据更新一致性之间找到一个平衡点，极大地提升了应用的性能和数据管理的灵活性。

二级缓存的数据是跨 SqlSession 的，同一个 Mapper 对应的命名空间内的缓存可以被不同的 SqlSession 访问和更新。同时，两个不同的 Mapper 操作相同的数据库表，它们也不会共享缓存数据

使用二级缓存时，需要确保数据模型满足序列化的要求，因为缓存的数据在多个会话间共享，在二级缓存数据时可能会将对象序列化存储到文件系统、数据库或其他缓存介质中。

### （3）执行顺序

在 MyBatis 中，如果配置了二级缓存，那么查询顺序应该是：

1. **二级缓存**：首先，MyBatis 会检查是否启用了二级缓存。

2. **一级缓存**：如果二级缓存没有命中，那么 MyBatis 会检查一级缓存。如果缓存命中，会将缓存同步到二级缓存中。

3. **数据库查询**：如果两级缓存都未命中，MyBatis 最后将查询数据库，查询结果不仅会放入一级缓存，还会根据配置条件更新到二级缓存中。

这种查询顺序优化了数据获取的性能，减少了对数据库的直接访问，特别是在多用户环境和读多写少的应用场景中，二级缓存的作用更加显著。同时，也需要注意缓存数据的一致性问题，确保在数据被更新时相应的缓存能够被清除或更新，避免脏读。

### （4）第三方缓存

在现代的高并发系统架构中，使用分布式缓存是提高性能和系统扩展性的关键技术之一。分布式缓存能够通过集中管理缓存数据，解决在集群部署环境中因数据存储分散带来的数据一致性问题和管理复杂性。

Ehcache 是一个纯Java的分布式缓存框架，广泛用于提高大型、复杂的分布式Web应用的性能。它支持内存和磁盘存储，能有效管理大量数据。Ehcache 可以单独使用或者与其他缓存框架组合使用，以增强其功能和效率。Ehcache 特别适合用于需要快速访问数据的场景，例如页面缓存、数据查询缓存等。

MyBatis集成Ehcache

1. **Ehcache依赖**：

```xml
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>版本号</version>
</dependency>
```

2. **配置Ehcache**：

创建一个`ehcache.xml`配置文件，在其中定义缓存的名称、大小、过期时间等。

```xml
<ehcache>
    <cache name="com.example.mapper.UserMapper"
           maxEntriesLocalHeap="1000"
           eternal="false"
           timeToIdleSeconds="300"
           timeToLiveSeconds="600">
    </cache>
</ehcache>
```

3. **在Mapper XML文件中指定使用Ehcache**：

修改Mapper XML配置文件或使用注解，以指明使用Ehcache作为缓存实现。

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```

### （5）缓存问题

缓存数据可能会因为多个会话中的不同数据修改操作而变得不一致。在使用缓存时，应该考虑数据一致性的策略，例如定期刷新缓存或在更新数据时清空缓存。

缓存可以显著提高查询性能，但如果不当使用，也可能因为频繁的缓存读写和维护成本而降低系统性能。需要根据实际业务需求合理配置缓存策略。

MyBatis 的缓存机制提供了灵活的配置选项，可以根据应用的具体需求来优化数据库访问性能和数据管理效率。

## 7.逆向工程

逆向工程（Reverse Engineering）在软件开发中指的是从现有的软件系统、数据库或应用程序中提取知识或设计信息，并根据这些信息创建出新的设计或代码。

在数据库和ORM（对象关系映射）框架中，逆向工程常用于从数据库表结构自动生成实体类和映射文件，这样可以大大节省开发时间，提高效率，同时减少因手动编码而导致的错误。

这里推荐使用IDEA MyBatis-X插件。

### （1）MyBatis Generator

MyBatis Generator (MBG) 是一个用于自动生成 MyBatis 的 SQL 映射文件和持久化类的代码生成器。它可以大幅度减少手动编写代码的工作量，特别是在处理大量表或复杂关系时。

**导入依赖**：在项目中添加 MyBatis Generator 的依赖。

```xml
<!-- 代码生成工具jar -->
<dependency>
  <groupId>org.mybatis.generator</groupId>
  <artifactId>mybatis-generator-core</artifactId>
  <version>1.3.2</version>
</dependency>
```

**配置 generatorConfig.xml**：定义如何生成代码，包括数据库连接信息、生成文件的目标包和路径等。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
   <context id="testTables" targetRuntime="MyBatis3">
      <commentGenerator>
         <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
         <property name="suppressAllComments" value="true" />
      </commentGenerator>
      <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
      <!-- <jdbcConnection driverClass="com.mysql.jdbc.Driver"
         connectionURL="jdbc:mysql://localhost:3306/mybatis" userId="root"
         password="123">
      </jdbcConnection> -->
       <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
         connectionURL="jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai&amp;allowPublicKeyRetrieval=true"
         userId="root"
         password="root">
      </jdbcConnection> 
      <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和 
         NUMERIC 类型解析为java.math.BigDecimal -->
      <javaTypeResolver>
         <property name="forceBigDecimals" value="false" />
      </javaTypeResolver>
      <!-- targetProject:生成PO类的位置 -->
      <javaModelGenerator targetPackage="com.msb.pojo"
         targetProject=".\src">
         <!-- enableSubPackages:是否让schema作为包的后缀 -->
         <property name="enableSubPackages" value="false" />
         <!-- 从数据库返回的值被清理前后的空格 -->
         <property name="trimStrings" value="true" />
      </javaModelGenerator>
        <!-- targetProject:mapper映射文件生成的位置 -->
      <sqlMapGenerator targetPackage="com.msb.mapper"
         targetProject=".\src">
         <!-- enableSubPackages:是否让schema作为包的后缀 -->
         <property name="enableSubPackages" value="false" />
      </sqlMapGenerator>
      <!-- targetPackage：mapper接口生成的位置 -->
      <javaClientGenerator type="XMLMAPPER"
         targetPackage="com.msb.mapper"
         targetProject=".\src">
         <!-- enableSubPackages:是否让schema作为包的后缀 -->
         <property name="enableSubPackages" value="false" />
      </javaClientGenerator>
      <!-- 指定数据库表 -->
      
      <table tableName="dept" domainObjectName="Dept"
       enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false"    
               enableSelectByExample="false" selectByExampleQueryId="false" >
               <columnOverride column="id" javaType="Integer" />
         </table>   
   </context>
</generatorConfiguration>
```

**运行逆向工程**：执行主类，自动读取配置文件，生成代码。

```java
public class GeneratorSqlmap {
    public void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("D:\\代码仓库\\SSM\\mybaits_all\\mybatis_test_reserve\\target\\classes\\generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
    }
    public static void main(String[] args) throws Exception {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### （2）IDEA MyBatis-X插件

MyBatis-X 是一个为 IntelliJ IDEA 开发的插件，专门用于提升使用 MyBatis 框架的开发效率。

**功能和优势**

- **直观的界面**：提供可视化操作界面，使得操作更加直观和便捷。
- **快速导航**：支持从 Mapper 接口跳转到对应的 XML 映射文件，反之亦然，提高代码的可导航性。
- **代码生成**：支持快速生成 SQL 映射文件和 Mapper 接口，减少手动编写的需要。
- **代码辅助**：提供代码补全、语法高亮等功能，帮助开发者编写更准确、高效的代码。

**使用方法**

**安装插件**：在 IDEA 中通过插件市场搜索并安装 MyBatis-X。

![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407191740990.png)

**IDEA连接数据库**：根据项目需求配置数据库连接。

![image-20240719174248640](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407191743341.png)

**generator**：根据项目需求生成相关实体类、Mapper XML、Mapper 接口、等。

![image-20240719174508231](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407191745072.png)

![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407191746996.png)

## 8.日志管理

日志管理在任何框架中都是一个重要的组成部分，它能帮助开发者监控应用的运行状态、调试代码、以及诊断问题。MyBatis 提供了灵活的日志配置，支持多种日志框架，使得开发者可以根据需要选择合适的日志解决方案。

MyBatis 可以集成多种日志框架，如 SLF4J、Log4j2、JDK logging 等。这提供了极大的灵活性，允许开发者根据项目的需要选择最适合的日志框架。

在 MyBatis 中配置日志框架通常很简单。你可以在 MyBatis 的配置文件 `mybatis-config.xml` 中指定使用的日志实现：

```xml
<configuration>
    <settings>
        <!-- 配置 MyBatis 使用的日志框架 -->
        <setting name="logImpl" value="LOG4J2"/>
    </settings>
</configuration>
```

在这个例子中，`logImpl` 的值可以是以下任一：

- `STDOUT_LOGGING`: 使用标准输出（控制台）日志。
- `LOG4J2`: 使用 Log4j2 日志框架。
- `SLF4J`: 使用 SLF4J 日志框架。SLF4J是日志门面，MyBatis 不支持logback，但是可以通过SLF4J进行支持，也是最为推荐的使用。
- `COMMONS_LOGGING`: 使用 Apache Commons Logging 日志框架。































