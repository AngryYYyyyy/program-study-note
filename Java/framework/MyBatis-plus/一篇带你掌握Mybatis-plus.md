**前言**：在深入学习MyBatis之后，我们不难发现许多数据库操作具有共同性。这种共性在学习JDBC时已经引入了抽象BaseDAO的概念。那么，对于Mapper和Service层，是否也可以通过抽象来简化和统一操作呢？MyBatis-Plus正是基于这样的思考而生的。它不仅优化了数据访问层的代码复用，还简化了许多常规操作。本文将主要介绍MyBatis-Plus的应用，而不深入其底层实现。这也符合作者关于工具和框架学习的建议：理解其应用边界，而非过分深究其内部原理。

另外，作者建议还是多从官网进行学习，本文主要是从官网并结合自己的经验，列举出常用的实践应用。

# 一、MyBatis-Plus的使用

MyBatis-Plus 是 MyBatis 的增强工具，它在不改变 MyBatis 原有操作的基础上增加了很多便捷的功能，极大地提高了开发效率。该框架提供了自动化的 CRUD 操作，动态 SQL 生成以及分页插件等。

> 官网链接：[MyBatis-Plus 🚀 为简化开发而生 (baomidou.com)](https://baomidou.com/)
>
> MyBatis 最佳搭档，只做增强不做改变，为简化开发、提高效率而生。

我们先通过简单案例，直观的看一下MyBatis-Plus究竟有哪些便捷性。

下面以SpringBoot项目为例，以Maven作为依赖管理。

## 数据库准备

首先，我们需要准备数据库表和一些初始数据。以下是创建用户表及插入一些示例数据的 SQL 脚本：

```sql
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);
```

```sql
DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');

```

![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407201946590.png)

## 导入依赖

接下来，我们需要在 Maven 的 `pom.xml` 文件中添加必要的依赖：

```xml
 <dependencies>
        <!-- MyBatis-Plus启动器，增强版MyBatis，提供更高效的操作和动态SQL能力 -->

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>3.5.7</version>
        </dependency>

        <!-- MySQL连接器，指定为运行时依赖，用于数据库连接 -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- 阿里巴巴的Druid数据库连接池，提供高性能的数据库连接管理和监控 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.14</version>
        </dependency>

        <!-- Lombok库，用于自动生成Java的getter、setter、构造函数等，标记为可选依赖 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Spring Boot的测试启动器，包括JUnit、Spring Test等测试工具 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

## 相关配置

为了确保应用可以正确连接数据库并利用 MyBatis-Plus 的功能，我们需要在 `application.yml` 中添加相应的配置：

```yml
spring:
  application:
    name: mybatis-plus-study-all
  datasource:
    # 数据库连接URL
    url: jdbc:mysql://127.0.0.1:3306/mybatisdb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    # 数据库用户名
    username: root
    # 数据库密码
    password: root
    # JDBC驱动类名
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis-plus:
  # Mapper XML文件路径
  mapper-locations: classpath:/mapper/**/*.xml
  # 实体类别名包路径
  type-aliases-package: com.lxy.mybatisplusstudyall.model.entity
  configuration:
    # MyBatis底层日志实现
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

```

## 编码

定义一个 `User` 类，它将映射到数据库中的 `user` 表：

```java
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

创建一个 `UserMapper` 接口，继承自 MyBatis-Plus 的 `BaseMapper`：

```java
/**
 * MyBatisPlus中的Mapper接口继承自BaseMapper
 */
public interface UserMapper extends BaseMapper<User> {
}
```

## 测试

编写一个简单的测试用例，验证我们可以通过 MyBatis-Plus 操作数据库：

```java
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    void listUser() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
}
```

![ ](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407202042559.png)

在使用MyBatis-Plus的过程中，你可能会问：传统的XML映射文件去哪了？这恰恰展示了MyBatis框架的一个重要优势。MyBatis-Plus通过封装了一些常用功能，例如继承自`BaseMapper`的Mapper接口，使得我们无需手动构建复杂的XML映射文件，就能高效地实现数据库操作。这种方式极大地提高了开发效率，降低了代码的复杂性。

# 二、配置

在接下来的部分，我们将详细介绍如何在Spring Boot项目中配置MyBatis-Plus，这些配置不仅包括从MyBatis继承的基本配置，还包括MyBatis-Plus自己的扩展配置。

在 Spring Boot 项目中，可以通过 `application.yml` 或 `application.properties` 文件来配置 MyBatis-Plus。

## 1.Base

### （1）configLocation

- **类型**：`String`
- **默认值**：`null`

指定 MyBatis 配置文件的位置。如果有单独的 MyBatis 配置文件，应将其路径配置到 `configLocation`。

**配置示例**：

```yml
mybatis-plus:
  config-location: classpath:/mybatis-config.xml
```

### （2）mapperLocations

- **类型**：`String[]`
- **默认值**：`["classpath*:/mapper/**/*.xml"]`

指定 MyBatis Mapper 对应的 XML 文件位置。如果在 Mapper 中有自定义方法，需要配置此项。

**配置示例**：

```yml
mybatis-plus:
  mapper-locations: classpath:/mapper/**.xml
```

### （3）typeAliasesPackage

- **类型**：`String`
- **默认值**：`null`

指定 MyBatis 别名包扫描路径，用于给包中的类注册别名。注册后，在 Mapper 对应的 XML 文件中可以直接使用类名，无需使用全限定类名。

**配置示例**：

```
mybatis-plus:

  type-aliases-package: com.your.domain
```

### （4）configuration

- **类型**：`Configuration`
- **默认值**：`null`

原生 MyBatis 所支持的配置。

### （5）globalConfig

- **类型**：`com.baomidou.mybatisplus.core.config.GlobalConfig`
- **默认值**：`GlobalConfig::new`

MyBatis-Plus 全局策略配置。

**配置示例**：

```yml
mybatis-plus:
  global-config:
    db-config:
      table-prefix: tbl_
      id-type: auto
```

## 2.Configuration

MyBatis-Plus 的 `Configuration` 配置继承自 MyBatis 原生支持的配置，这意味着您可以通过 MyBatis XML 配置文件（mybatis-cofig.xml）的形式进行配置，也可以通过 Spring Boot 配置文件进行设置。

### （1）mapUnderscoreToCamelCase

- **类型**：`boolean`
- **默认值**：`true`

开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN（下划线命名） 到经典 Java 属性名 aColumn（驼峰命名） 的类似映射。

**配置示例**：

```
mybatis-plus:

  configuration:

    map-underscore-to-camel-case: true
```

提示

在 MyBatis-Plus 中，此属性也将用于生成最终的 SQL 的 select body。如果您的数据库命名符合规则，无需使用 `@TableField` 注解指定数据库字段名。

### （2）aggressiveLazyLoading

- **类型**：`boolean`
- **默认值**：`true`

当设置为 true 时，懒加载的对象可能被任何懒属性全部加载，否则，每个属性都按需加载。需要和 `lazyLoadingEnabled` 一起使用。

**配置示例**：

```yml
mybatis-plus:

  configuration:

    aggressive-lazy-loading: false
```

### （3）autoMappingUnknownColumnBehavior

- **类型**：`AutoMappingUnknownColumnBehavior`
- **默认值**：`NONE`

MyBatis 自动映射时未知列或未知属性处理策略，通过该配置可指定 MyBatis 在自动映射过程中遇到未知列或者未知属性时如何处理。

- AutoMappingUnknownColumnBehavior.NONE：不做任何处理 (默认值)
- AutoMappingUnknownColumnBehavior.WARNING：以日志的形式打印相关警告信息
- AutoMappingUnknownColumnBehavior.FAILING：当作映射失败处理，并抛出异常和详细信息

**配置示例**：

```yml
mybatis-plus:

  configuration:

    auto-mapping-unknown-column-behavior: warning
```

### （4）localCacheScope

- **类型**：`String`
- **默认值**：`SESSION`

Mybatis 一级缓存，默认为 SESSION。

- SESSION：Session 级别缓存，同一个 Session 相同查询语句不会再次查询数据库
- STATEMENT：关闭一级缓存

**配置示例**：

```yml
mybatis-plus:

  configuration:

    local-cache-scope: statement
```

注意：在单服务架构中（仅有一个程序提供相同服务），开启一级缓存不会影响业务，只会提高性能。在微服务架构中需要关闭一级缓存，原因是：Service1 查询数据后，如果 Service2 修改了数据，Service1 再次查询时可能会得到过期数据。

### （5）cacheEnabled

- **类型**：`boolean`
- **默认值**：`true`

是否开启 MyBatis 二级缓存。

**配置示例**：

```yml
mybatis-plus:

  configuration:

    cache-enabled: false
```

## 3.GlobalConfig

### （1）dbConfig

- **类型**：`com.baomidou.mybatisplus.core.config.GlobalConfig$DbConfig`
- **默认值**：`null`

MyBatis-Plus 全局策略中的 DB 策略配置。

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      table-prefix: tbl_

      id-type: ASSIGN_ID
```

## 4.DbConfig

### （1）idType

- 类型：`com.baomidou.mybatisplus.annotation.IdType`
- 默认值：`ASSIGN_ID`

全局默认主键类型。

- `IdType.AUTO`：使用数据库自增 ID 作为主键。
- `IdType.NONE`：无特定生成策略，如果全局配置中有 IdType 相关的配置，则会跟随全局配置。
- `IdType.INPUT`：在插入数据前，由用户自行设置主键值。
- `IdType.ASSIGN_ID`：自动分配 `ID`，适用于 `Long`、`Integer`、`String` 类型的主键。默认使用雪花算法通过 `IdentifierGenerator` 的 `nextId` 实现。@since 3.3.0
- `IdType.ASSIGN_UUID`：自动分配 `UUID`，适用于 `String` 类型的主键。默认实现为 `IdentifierGenerator` 的 `nextUUID` 方法。@since 3.3.0

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      id-type: ASSIGN_ID
```



### （2）tableUnderline

- 类型：`boolean`
- 默认值：`true`

控制表名是否使用驼峰转下划线命名。

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      table-underline: false
```

### （3）capitalMode

- 类型：`boolean`
- 默认值：`false`

控制表名和字段名是否使用大写命名。

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      capital-mode: true
```

### （4）keyGenerator

- 类型：`com.baomidou.mybatisplus.core.incrementer.IKeyGenerator`
- 默认值：`null`

自定义表主键生成器。Starter 下支持`@Bean`注入。

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      key-generator: com.example.CustomKeyGenerator
@Bean

public IKeyGenerator keyGenerator() {

    return new CustomKeyGenerator();

}
```

### （5）logicDeleteField

- 类型：`String`
- 默认值：`null`

全局的 Entity 逻辑删除字段属性名，仅在逻辑删除功能打开时有效。

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      logic-delete-field: deleted
```

### （6）logicDeleteValue

- 类型：`String`
- 默认值：`1`

逻辑已删除值，仅在逻辑删除功能打开时有效。

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      logic-delete-value: true
```

### （7）logicNotDeleteValue

- 类型：`String`
- 默认值：`0`

逻辑未删除值，仅在逻辑删除功能打开时有效。

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      logic-not-delete-value: false
```

### （8）insertStrategy

- 类型：`com.baomidou.mybatisplus.annotation.FieldStrategy`
- 默认值：`NOT_NULL`

控制字段在 Insert 时的字段验证策略。

- FieldStrategy.DEFAULT：遵循全局配置的策略。如果全局配置未指定，默认行为是仅在字段值不为 NULL 时插入该字段。
- FieldStrategy.ALWAYS：总是插入该字段，无论字段值是否为 NULL。
- FieldStrategy.NOT_NULL：仅在字段值不为 NULL 时插入该字段。
- FieldStrategy.NOT_EMPTY：仅在字段值不为空（对于字符串类型）或不为 NULL（对于其他类型）时插入该字段。
- FieldStrategy.NEVER：从不插入该字段，即使字段值不为 NULL。
- FieldStrategy.IGNORED： 忽略判断，效果等同于”ALWAYS” @Deprecated

**配置示例**：

```yml
mybatis-plus:

  global-config:

    db-config:

      insert-strategy: NEVER
```

# 三、注解

## （1）@TableName

该注解用于指定实体类对应的数据库表名。当实体类名与数据库表名不一致，或者实体类名不是数据库表名的驼峰写法时，您需要使用这个注解来明确指定表名。

```java
@TableName("sys_user")

public class User {

    private Long id;

    private String name;

    private Integer age;

    private String email;

}
```

value：指定实体类对应的数据库表名。如果实体类名与表名不一致，使用这个属性来指定正确的表名。

resultMap：指定在 XML 中定义的 ResultMap 的 ID，用于将查询结果映射到特定类型的实体类对象。

excludeProperty：指定在映射时需要排除的属性名。这些属性将不会被包含在生成的 SQL 语句中。

## （2）@TableId

该注解用于标记实体类中的主键字段。如果你的主键字段名为 id，你可以省略这个注解。

```java
@TableName("sys_user")

public class User {

    @TableId

    private Long id;

    private String name;

    private Integer age;

    private String email;

}
```

value：指定数据库表的主键字段名。如果不设置，MyBatis-Plus 将使用实体类中的字段名作为数据库表的主键字段名。

type：指定主键的生成策略。

**IdType 枚举类型定义**

- `IdType.AUTO`：使用数据库自增 ID 作为主键。
- `IdType.NONE`：无特定生成策略，如果全局配置中有 IdType 相关的配置，则会跟随全局配置。
- `IdType.INPUT`：在插入数据前，由用户自行设置主键值。
- `IdType.ASSIGN_ID`：自动分配 `ID`，适用于 `Long`、`Integer`、`String` 类型的主键。默认使用雪花算法通过 `IdentifierGenerator` 的 `nextId` 实现。@since 3.3.0
- `IdType.ASSIGN_UUID`：自动分配 `UUID`，适用于 `String` 类型的主键。默认实现为 `IdentifierGenerator` 的 `nextUUID` 方法。@since 3.3.0

请注意，已弃用的ID类型（如 `ID_WORKER`, `UUID`, `ID_WORKER_STR`）应避免使用，并使用 `ASSIGN_ID` 或 `ASSIGN_UUID` 代替。这些新的策略提供了更好的灵活性和兼容性。

## （3）@TableField

该注解用于标记实体类中的非主键字段，它告诉 MyBatis-Plus 如何映射实体类字段到数据库表字段。如果你的实体类字段名遵循驼峰命名规则，并且与数据库表字段名一致，你可以省略这个注解。

```java
@TableName("sys_user")

public class User {

    @TableId

    private Long id;

    @TableField("nickname") // 映射到数据库字段 "nickname"

    private String name;

    private Integer age;

    private String email;

}
```

value：指定数据库中的字段名。如果你的实体类字段名与数据库字段名不同，使用这个属性来指定正确的数据库字段名。

exist：指示这个字段是否存在于数据库表中。如果设置为 false，MyBatis-Plus 在生成 SQL 时会忽略这个字段。

condition：在执行实体查询（EntityQuery）时，指定字段的条件表达式。这允许你自定义字段在 WHERE 子句中的比较方式。如果该项有值则按设置的值为准，无值则默认为全局的 `%s=#{%s}` 为准。

EntityQuery 是指在构建查询条件时，直接使用实体类的字段来设置查询条件，而不是手动编写 SQL 片段。

**示例说明**

假设我们有一个实体类 User，它有 id、name 和 age 三个字段。我们想要查询所有年龄大于 18 岁的用户，我们可以使用 QueryWrapper 来构建这个查询，直接传递 User 实体类实例：

```java
// 实体类定义
@TableName("sys_user")
public class User {
    @TableId
    private Long id;

    private String name;

    @TableField(condition = "%s > #{%s}") // 自定义 age 字段的条件表达式
    private Integer age;

    private String email;
}

// 使用 EntityQuery 构建查询
public List<User> findUserAgeOver18() {
    // 创建 User 实例，用于设置查询条件
    User queryEntity = new User();
    queryEntity.setAge(18); // 设置 age 字段的值

    // 创建 QueryWrapper 实例，并传递 User 实例
    QueryWrapper<User> queryWrapper = new QueryWrapper<>(queryEntity);

    // 执行查询
    List<User> userList = userMapper.selectList(queryWrapper);

    return userList;
}
```

在这个例子中，我们通过 `@TableField(condition = "%s > #{%s}")` 注解为 age 字段设置了自定义的条件表达式。当构建查询时，我们创建了一个 User 实例，并设置了 age 字段的值为 18。然后，我们使用这个实例来创建 QueryWrapper，MyBatis-Plus 会根据实体类上的注解自动生成相应的 SQL 查询条件。

执行 findUserAgeOver18 方法时，MyBatis-Plus 会生成类似以下的 SQL 语句：

```
SELECT * FROM sys_user WHERE age > 18;
```

通过这种方式，condition 属性允许我们自定义字段在查询时的行为，使得查询更加灵活和符合特定需求，同时避免了手动编写 SQL 片段的繁琐。

update：在执行更新操作时，指定字段在 SET 子句中的表达式。这个属性的优先级高于 el 属性，允许你自定义字段的更新逻辑。

**示例说明**

假设我们有一个实体类 User，其中包含一个 version 字段，我们希望在每次更新用户信息时，自动将 version 字段的值增加 1。我们可以使用 @TableField 注解的 update 属性来实现这个功能：

```java
@TableName("sys_user")
public class User {
    @TableId
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @TableField(update="%s+1") // 自定义更新时的表达式
    private Integer version;
}
```

在这个例子中，`@TableField(update="%s+1")` 注解告诉 MyBatis-Plus，在执行更新操作时，对于 version 字段，应该使用 `version = version + 1` 的表达式。这意味着，每次更新用户信息时，version 字段的值都会自动增加 1。

如果我们执行以下更新操作：

```j
User user = new User();
user.setId(1L);
user.setName("Updated Name");
user.setAge(30);
user.setEmail("updated@example.com");

userMapper.updateById(user);
```

MyBatis-Plus 会自动生成类似以下的 SQL 语句：

```sql
UPDATE sys_user

SET name = 'Updated Name', age = 30, email = 'updated@example.com', version = version + 1

WHERE id = 1;
```

通过这种方式，update 属性允许我们自定义字段在更新时的行为，使得更新操作更加灵活和符合特定需求，同时避免了手动编写 SQL 片段的繁琐。



fill：字段自动填充策略。该属性用于指定在执行数据库操作（如插入、更新）时，如何自动填充字段的值。通过使用 FieldFill 枚举，可以灵活地控制字段的填充行为。

**FieldFill枚举类型定义**

- `FieldFill.DEFAULT`：默认不进行填充，依赖于数据库的默认值或手动设置。
- `FieldFill.INSERT`：在插入操作时自动填充字段值。
- `FieldFill.UPDATE`：在更新操作时自动填充字段值。
- `FieldFill.INSERT_UPDATE`：在插入和更新操作时都会自动填充字段值。

**示例说明**

假设有一个 User 实体类，其中包含 createTime 和 updateTime 字段，我们希望在创建用户时自动填充创建时间，在更新用户信息时自动填充更新时间。

```java
@TableName("user")
public class User {
    // 其他字段...

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    // 构造函数、getter 和 setter...
}
```

在这个示例中，createTime 字段会在插入操作时自动填充当前时间，而 updateTime 字段会在更新操作时自动填充当前时间。这样，我们就不需要在每次数据库操作时手动设置这些时间字段的值了。

**请注意**，为了使自动填充功能正常工作，您需要在 MyBatis-Plus 的配置中设置相应的自动填充处理器，并且确保在实体类对应的 Mapper 接口中定义了相应的插入和更新方法。



select：指示在执行查询操作时，该字段是否应该包含在 SELECT 语句中。这个属性允许您控制查询结果中包含哪些字段，从而提供更细粒度的数据访问控制。

**详细说明**

- 当 select 属性设置为 true（默认值）时，该字段将包含在查询结果中。
- 当 select 属性设置为 false 时，即使该字段存在于数据库表中，它也不会包含在查询结果中。这在需要保护敏感数据或优化查询性能时非常有用。

**示例说明**

假设有一个 User 实体类，其中包含 password 字段，我们希望在查询用户信息时排除密码字段，以保护用户隐私。

```
import com.baomidou.mybatisplus.annotation.TableField;import com.baomidou.mybatisplus.annotation.TableName;
@TableName("user")public class User {    // 其他字段...
    @TableField(select = false)    private String password;
    // 构造函数、getter 和 setter...}
```

在这个示例中，当执行查询操作时，password 字段不会被包含在 SELECT 语句中，因此不会出现在查询结果中。这样，即使数据库中存储了密码信息，也不会在常规查询中泄露。

请注意，@TableField 注解的 select 属性仅影响 MyBatis-Plus 生成的查询语句，不会影响其他框架或手动编写的 SQL 语句。此外，如果使用了 select = false 的字段，那么在自定义查询或使用其他方式访问该字段时，需要特别注意数据的安全性和完整性。



jdbcType：JDBC类型，用于指定字段在数据库中的数据类型。这个属性允许您显式地设置字段的数据库类型，以确保与数据库的兼容性，特别是在处理特殊类型或自定义类型时。

**详细说明**

- 当 jdbcType 属性设置为 JdbcType.UNDEFINED（默认值）时，MyBatis-Plus 将根据字段的 Java 类型自动推断其 JDBC 类型。
- 当 jdbcType 属性设置为特定的 JdbcType 枚举值时，该字段将使用指定的 JDBC 类型进行数据库操作。这可以用于解决类型映射问题，或者在需要精确控制数据库类型时使用。

**请注意**，jdbcType 属性仅在特定情况下需要设置，例如当 Java 类型与数据库类型之间存在不明确的映射关系时。在大多数情况下，MyBatis-Plus 能够自动处理类型映射，因此不需要显式设置 jdbcType。此外，jdbcType 属性仅影响 MyBatis-Plus 生成的 SQL 语句，不会影响其他框架或手动编写的 SQL 语句。



numericScale：指定小数点后保留的位数，该属性仅在执行 update 操作时生效。它用于控制数值类型字段在更新时的小数精度。

**详细说明**

- 当 numericScale 属性设置为空字符串（默认值）时，字段的小数精度将遵循数据库的默认设置或字段定义时的设置。
- 当 numericScale 属性设置为特定的数值（如 “2”）时，该字段在执行 update 操作时将按照指定的小数位数进行处理。

在这个示例中，price 字段在执行 update 操作时将确保小数点后保留两位。这意味着在更新价格时，无论传入的价格值小数位数是多少，都会被格式化为两位小数。

**请注意**，为了使 numericScale 属性生效，您需要确保数据库支持指定的小数位数，并且在执行 update 操作时，传入的数值类型字段值会被正确处理。此外，numericScale 属性仅影响 MyBatis-Plus 生成的 SQL 语句，不会影响其他框架或手动编写的 SQL 语句。

## （4）@Version

该注解用于标记实体类中的字段作为乐观锁版本号字段。乐观锁是一种并发控制机制，它假设多个事务可以同时进行而不会互相干扰，只在提交事务时检查是否有冲突。通过在实体类中使用`@Version`注解，MyBatis-Plus 会在更新操作时自动检查版本号，确保在更新过程中数据没有被其他事务修改。

```java
@TableName("sys_user")

public class User {

    @TableId

    private Long id;

    @TableField("nickname") // 映射到数据库字段 "nickname"

    private String name;

    private Integer age;

    private String email;

    @Version // 标记为乐观锁版本号字段

    private Integer version;

}
```

在上面的示例中，version字段被标记为乐观锁版本号。当执行更新操作时，MyBatis-Plus 会检查该字段的值是否与数据库中的值一致。如果不一致，说明在读取数据后有其他事务修改了数据，此时会抛出乐观锁异常，提示开发者进行相应的处理。

使用@Version注解可以有效地防止并发更新时出现的数据不一致问题，提高系统的并发性能和数据完整性。开发者无需手动编写版本号检查的代码，MyBatis-Plus 会自动处理这一过程。

## （5）@EnumValue

该注解用于标记枚举类中的字段，指定在数据库中存储的枚举值。当实体类中的某个字段是枚举类型时，使用@EnumValue注解可以告诉MyBatis-Plus在数据库中存储枚举值的哪个属性。

```java
@TableName("sys_user")
public class User {
    @TableId
    private Long id;
    @TableField("nickname") // 映射到数据库字段 "nickname"
    private String name;
    private Integer age;
    private String email;
    private Gender gender; // 假设 Gender 是一个枚举类型
}

public enum Gender {
    MALE("M", "男"),
    FEMALE("F", "女");

    private String code;
    private String description;

    Gender(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @EnumValue // 指定存储到数据库的枚举值为 code
    public String getCode() {
        return code;
    }
}
```

在上面的示例中，Gender枚举类中的code字段被标记为@EnumValue，这意味着在数据库中存储User实体类的gender字段时，将存储Gender枚举的code值，而不是枚举常量本身。

使用@EnumValue注解可以灵活地控制枚举类型在数据库中的存储方式，使得数据库中的数据更加紧凑和易于处理。同时，它也简化了从数据库读取枚举值时的转换过程，因为MyBatis-Plus会自动根据@EnumValue注解的配置将数据库中的值转换为对应的枚举实例。

## （6）@TableLogic

该注解用于标记实体类中的字段作为逻辑删除字段。逻辑删除是一种数据管理策略，它不是真正地从数据库中删除记录，而是在记录中标记该记录为已删除状态。通过使用`@TableLogic`注解，MyBatis-Plus 可以在查询、更新和删除操作中自动处理逻辑删除字段的值。

```java
@TableName("sys_user")

public class User {

    @TableId

    private Long id;

    @TableField("nickname") // 映射到数据库字段 "nickname"

    private String name;

    private Integer age;

    private String email;

    @TableLogic(value = "0", delval = "1") // 逻辑删除字段

    private Integer deleted;

}
```

在上面的示例中，deleted字段被标记为逻辑删除字段。@TableLogic注解的value属性指定了逻辑未删除的值（在这个例子中是0），而delval属性指定了逻辑删除的值（在这个例子中是1）。

当执行查询操作时，MyBatis-Plus 会自动过滤掉标记为逻辑删除的记录，只返回未删除的记录。在执行更新操作时，如果更新操作会导致逻辑删除字段的值变为逻辑删除值，MyBatis-Plus 会自动将该记录标记为已删除。在执行删除操作时，MyBatis-Plus 会自动将逻辑删除字段的值更新为逻辑删除值，而不是物理删除记录。

使用@TableLogic注解可以实现数据的逻辑删除，有助于维护数据的完整性和可追溯性，同时避免了物理删除操作可能带来的数据丢失风险。开发者无需手动编写逻辑删除的代码，MyBatis-Plus 会自动处理这一过程。

## （7）@InterceptorIgnore

该注解用于指定在执行 MyBatis-Plus 的某个方法时，是否忽略特定的拦截器。MyBatis-Plus 提供了多个拦截器，用于实现如分页、逻辑删除、多租户等功能。通过使用@InterceptorIgnore注解，开发者可以控制哪些拦截器在执行特定方法时应该被忽略。

```java
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @InterceptorIgnore(tenantLine = "1") // 忽略多租户拦截器
    public List<User> selectUsers() {
        return userMapper.selectList(null);
    }
}
```

在上面的示例中，@InterceptorIgnore注解被应用于selectUsers方法，并指定了tenantLine属性为”1”，这意味着在执行该方法时，多租户拦截器将被忽略。

@InterceptorIgnore注解的value属性是一个字符串，可以包含多个以逗号分隔的拦截器名称。如果value的值为”1”、“yes”、“on”，则对应的拦截器将被忽略；如果value的值为”0”、“false”、“off”或为空，则拦截器将正常执行。

使用@InterceptorIgnore注解可以灵活地控制拦截器的行为，允许开发者在特定的业务场景下选择性地禁用某些拦截器，以满足特定的业务需求或性能优化。

## （8）@OrderBy

该注解用于指定实体类中的字段在执行查询操作时的默认排序方式。通过在实体类字段上使用@OrderBy注解，可以确保在执行查询时，如果没有显式指定排序条件，MyBatis-Plus 将按照注解中定义的排序规则返回结果。

```java
@TableName("sys_user")

public class User {

    @TableId

    private Long id;

    @TableField("nickname") // 映射到数据库字段 "nickname"

    private String name;

    @OrderBy(asc = false, sort = 10) // 指定默认排序为倒序，优先级为10

    private Integer age;

    private String email;

}
```

在上面的示例中，age字段被标记为@OrderBy，并设置了asc属性为false，表示默认排序为倒序（降序），sort属性设置为10，表示该排序规则的优先级。

@OrderBy注解的asc属性用于指定排序是否为升序，如果设置为true，则表示升序排序；如果设置为false，则表示降序排序。sort属性用于指定排序规则的优先级，数字越小，优先级越高，即越先被应用。

需要注意的是，@OrderBy注解的排序规则优先级低于在查询时通过Wrapper条件查询对象显式指定的排序条件。如果在Wrapper中指定了排序条件，那么@OrderBy注解中定义的默认排序将被覆盖。

使用@OrderBy注解可以简化代码，避免在每次查询时都显式指定排序条件，同时提供了一种默认的排序方式，有助于提高代码的可读性和维护性。

# 四、持久层接口

> 持久层接口与条件构造器均主要查看官网文档，都很常用，下面只列出重要的部分，自行取官网查看
>
> [持久层接口 | MyBatis-Plus (baomidou.com)](https://baomidou.com/guides/data-interface/)

# 五、条件构造器

![image-20240720224515895](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407202245495.png)

# 六、高级应用

## 1.数据安全保护

MyBatis-Plus 提供了数据安全保护功能，旨在防止因开发人员流动而导致的敏感信息泄露。从3.3.2版本开始，MyBatis-Plus 支持通过加密配置和数据安全措施来增强数据库的安全性。

### （1）配置安全

MyBatis-Plus 允许你使用加密后的字符串来配置数据库连接信息。在 YML 配置文件中，以 `mpw:` 开头的配置项将被视为加密内容。

```yml
spring:

  datasource:

    url: mpw:qRhvCwF4GOqjessEB3G+a5okP+uXXr96wcucn2Pev6Bf1oEMZ1gVpPPhdDmjQqoM

    password: mpw:Hzy5iliJbwDHhjLs1L0j6w==

    username: mpw:Xb+EgsyuYRXw7U7sBJjBpA==
```

使用 AES 算法生成随机密钥，并对敏感数据进行加密。

```java
// 生成16位随机AES密钥String randomKey = AES.generateRandomKey();
// 使用随机密钥加密数据String encryptedData = AES.encrypt(data, randomKey);
```

在启动应用程序时，通过命令行参数或环境变量传递密钥。

```java
// Jar 启动参数示例（在IDEA中设置Program arguments，或在服务器上设置为启动环境变量）

--mpw.key=d1104d7c3b616f0b
```

### （2）数据安全

MyBatis-Plus 提供了字段加密解密和字段脱敏功能，以保护存储在数据库中的敏感数据。

**字段加密解密**：对数据库中的特定字段进行加密存储，并在需要时解密使用。

- 注解 @FieldEncrypt

  ```java
  @FieldEncrypt
  private String email;
  ```

  |   属性    |   类型    | 必须指定 |      默认值      | 描述                 |
  | :-------: | :-------: | :------: | :--------------: | -------------------- |
  | password  |  String   |    否    |        ""        | 加密密码             |
  | algorithm | Algorithm |    否    | PBEWithMD5AndDES | PBE MD5 DES 混合算法 |
  | encryptor |   Class   |    否    |    IEncryptor    | 加密处理器           |

- 算法 Algorithm

  |            算法             |                        描述                         |
  | :-------------------------: | :-------------------------------------------------: |
  |           MD5_32            |                   32 位 md5 算法                    |
  |           MD5_16            |                   16 位 md5 算法                    |
  |           BASE64            |          64 个字符来表示任意二进制数据算法          |
  |             AES             |   AES 对称算法 【需要模糊查询的请务必使用该算法】   |
  |             RSA             |                   非对称加密算法                    |
  |             SM2             |          国密 SM2 非对称加密算法，基于 ECC          |
  |             SM3             |   国密 SM3 消息摘要算法，可以用 MD5 作为对比理解    |
  |             SM4             | 国密 SM4 对称加密算法，无线局域网标准的分组数据算法 |
  |      PBEWithMD5AndDES       |                      混合算法                       |
  |   PBEWithMD5AndTripleDES    |                      混合算法                       |
  | PBEWithHMACSHA512AndAES_256 |                      混合算法                       |
  |    PBEWithSHA1AndDESede     |                      混合算法                       |
  |    PBEWithSHA1AndRC2_40     |                      混合算法                       |

**字段脱敏**：对敏感字段进行脱敏处理，以隐藏或模糊敏感信息。

- 注解 @FieldSensitive：实现数据脱敏，内置 `手机号`、`邮箱`、`银行卡号` 等 9 种常用脱敏规则

  ```java
  @FieldSensitive("testStrategy")
  private String username;
  
  @Configuration
  public class SensitiveStrategyConfig {
  
      /**
       * 注入脱敏策略
       */
      @Bean
      public ISensitiveStrategy sensitiveStrategy() {
          // 自定义 testStrategy 类型脱敏处理
          return new SensitiveStrategy().addStrategy("testStrategy", t -> t + "***test***");
      }
  }
  
  // 跳过脱密处理，用于编辑场景
  RequestDataTransfer.skipSensitive();
  ```

### （3）SQL 注入安全保护

使用 `Wrappers.query()` 方法时，可以通过 `.checkSqlInjection()` 开启自动检查。

```java
Wrappers.query()

// 开启自动检查 SQL 注入

.checkSqlInjection().orderByDesc("任意前端传入字段")
```

通过上述措施，MyBatis-Plus 帮助你构建了一个更加安全的数据库环境，保护了敏感数据不被泄露。

## 2.数据审计

对比两对象属性差异，例如：银行流水对账。

```java
// 1，异步回调，注意 @EnableAsync 开启异步
applicationEventPublisher.publishEvent(new DataAuditEvent((t) -> {
    List<Change> changes = t.apply(newVersion, oldVersion);
    for (Change valueChange : changes) {
        ValueChange change = (ValueChange) valueChange;
        System.err.println(String.format("%s不匹配，期望值 %s 实际值 %s", change.getPropertyName(), change.getLeft(), change.getRight()));
    }
}));

// 2，手动调用对比
DataAuditor.compare(obj1, obj2);
```

## 3.分页

在 Spring Boot 项目中，你可以通过 Java 配置来添加分页插件：

```java
@Configuration
@MapperScan("scan.your.mapper.package")
public class MybatisPlusConfig {

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }
}
```

`PaginationInnerInterceptor` 提供了以下属性来定制分页行为：

|  属性名  |   类型   | 默认值 |           描述           |
| :------: | :------: | :----: | :----------------------: |
| overflow | boolean  | false  | 溢出总页数后是否进行处理 |
| maxLimit |   Long   |        |     单页分页条数限制     |
|  dbType  |  DbType  |        |        数据库类型        |
| dialect  | IDialect |        |        方言实现类        |

> 建议单一数据库类型的均设置 dbType

你可以通过以下方式在 Mapper 方法中使用分页：

```java
IPage<UserVo> selectPageVo(IPage<?> page, Integer state);

// 或者自定义分页类

MyPage selectPageVo(MyPage page);

// 或者返回 List

List<UserVo> selectPageVo(IPage<UserVo> page, Integer state);
```

对应的 XML 配置：

```java
<select id="selectPageVo" resultType="xxx.xxx.xxx.UserVo">

    SELECT id,name FROM user WHERE state=#{state}

</select>
```

> 如果返回类型是 IPage，则入参的 IPage 不能为 null。如果想临时不分页，可以在初始化 IPage 时 size 参数传入小于 0 的值。 如果返回类型是 List，则入参的 IPage 可以为 null，但需要手动设置入参的 IPage.setRecords(返回的 List)。 如果 XML 需要从 page 里取值，需要使用 `page.属性` 获取。

- 生成 countSql 时，如果 left join 的表不参与 where 条件，会将其优化掉。建议在任何带有 left join 的 SQL 中，都给表和字段加上别名。
- 在使用多个插件时，请将分页插件放到插件执行链的最后面，以避免 COUNT SQL 执行不准确的问题。

`Page` 类继承了 `IPage` 类，实现了简单分页模型。如果你需要实现自己的分页模型，可以继承 `Page` 类或实现 `IPage` 类。

|         属性名         |      类型       |  默认值   |                    描述                     |
| :--------------------: | :-------------: | :-------: | :-----------------------------------------: |
|        records         |     List<T>     | emptyList |                查询数据列表                 |
|         total          |      Long       |     0     |              查询列表总记录数               |
|          size          |      Long       |    10     |            每页显示条数，默认 10            |
|        current         |      Long       |     1     |                   当前页                    |
|         orders         | List<OrderItem> | emptyList |                排序字段信息                 |
|    optimizeCountSql    |     boolean     |   true    |             自动优化 COUNT SQL              |
| optimizeJoinOfCountSql |     boolean     |   true    | 自动优化 COUNT SQL 是否把 join 查询部分移除 |
|      searchCount       |     boolean     |   true    |             是否进行 count 查询             |
|        maxLimit        |      Long       |           |              单页分页条数限制               |
|        countId         |     String      |           |     XML 自定义 count 查询的 statementId     |

通过这些配置和使用方法，你可以轻松地在 MyBatis-Plus 中实现分页查询，提高应用的性能和用户体验。

