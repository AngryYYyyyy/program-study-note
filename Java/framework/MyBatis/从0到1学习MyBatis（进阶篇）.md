前言：在探究现代应用开发中的数据持久层框架时，MyBatis 无疑占据了重要的位置。它通过桥接应用程序和数据库，使得数据操作变得灵活而直观。本文旨在深入剖析 MyBatis 的内部机制，提供给读者一个清晰的框架源码视角。

这篇文章适合对 Java 和 SQL 有一定基础，希望了解 ORM 框架内部工作原理的开发者。无论是软件开发新手还是有经验的工程师，只要您对底层实现细节感兴趣，都能在本文中获得有价值的信息。现在，如果您已具备相关的前置知识，可以直接跳至第三节，深入了解 MyBatis 的底层机制。

# 一、ORM框架

对象关系映射（ORM）框架的发展是现代软件开发中一个重要的进展，特别是在处理数据库和对象编程语言之间的不匹配问题（即所谓的"对象-关系不匹配"）时。从最初的简单库到今天的成熟框架，ORM技术已经在数据持久化和应用开发中扮演了核心角色。

## 1.ORM框架的发展

### （1）早期的ORM技术

ORM的概念在1990年代初开始获得关注，当时的主要问题是如何将在关系数据库中存储的数据转化为应用程序中使用的对象。最初的解决方案很大程度上依赖于手动编写代码来转换数据表中的行为对象，这一过程既繁琐又易于出错。

### （2）ORM框架的崛起

随着Java和.NET这样的平台的兴起，ORM框架开始逐渐流行。这些框架提供了自动化工具，帮助开发者减少手动处理数据库交互的工作量。例如：

- **Hibernate**：在Java社区，Hibernate成为最受欢迎的ORM框架之一。它提供了丰富的映射能力来处理复杂的关系数据，并支持延迟加载、缓存以及复杂查询功能，极大地简化了数据库操作。
- **Entity Framework**：作为.NET环境的一个回应，Entity Framework提供了一个广泛的模型来处理数据，并支持LINQ查询，使得.NET开发者能够以一种更自然的方式来处理数据库数据。
- **ActiveRecord**：在Ruby社区中，ActiveRecord作为Ruby on Rails框架的一部分，以其简单易用而著称，遵循“约定优于配置”的原则。

### （3）ORM框架的成熟与挑战

随着ORM技术的普及，开发者开始意识到它不仅仅是简化数据库编程的工具。高级功能如二级缓存、事务管理、多版本并发控制和自动迁移工具等，使得ORM框架成为企业应用中不可或缺的一部分。

然而，ORM框架也面临着一些批评和挑战，特别是在性能和复杂性管理方面：
- **性能问题**：ORM框架的抽象层可能隐藏了一些性能陷阱，如N+1查询问题、不恰当的数据抓取策略等。
- **复杂性和学习曲线**：虽然ORM框架简化了很多数据库操作，但它们自身的复杂性也要求开发者投入时间来学习如何正确使用。

## 2.核心技术

### （1）JDBC（Java Database Connectivity）

JDBC 是 Java 的一套核心API，用于连接数据库，执行SQL语句，处理结果集。这是一种比较基础的技术，直接与数据库交互，需要开发者手动处理SQL语句及结果集，适用于对性能要求极高或需要精细控制数据库操作的场景。

### （2）Spring Data JPA

Spring Data JPA 是基于JPA（Java Persistence API）规范的一部分，旨在简化持久层的实现。它提供了一套声明式的方式来进行数据访问，支持通过方法名称约定自动生成查询。Spring Data JPA 使得数据库交互更加简单，自动处理了很多常见的持久化操作，极大地减少了编码工作量。

### （3）Hibernate
Hibernate 是一个功能强大的ORM（对象关系映射）框架，它允许将Java对象映射到数据库表中，自动转换对象到SQL语句，从而抽象出直接的数据库操作。Hibernate 支持懒加载、缓存、事务管理等复杂的ORM功能，适合需要处理复杂关系和大规模数据的应用。

### （4）MyBatis
MyBatis 是一个半ORM框架，不同于Hibernate的全自动映射，MyBatis 提供更多的控制能力，允许开发者编写原生SQL，并将结果映射到Java对象上。这种方式在需要优化SQL或执行复杂查询的场景下非常有用，同时也保持了代码的简洁性和易于维护性。

# 二、MyBatis核心应用

## 1.实际案例

### （1）环境准备

#### ① 导入依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.16</version>
    </dependency>
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
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### ② 实体类

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

#### ③ 全局配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--导入外部配置属性-->
    <properties resource="db.properties"></properties>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <!--包扫描实体类-->
    <typeAliases>
        <package name="com.lxy.example.model.entity"/>
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
        <package name="com.lxy.example.mapper"/>
    </mappers>
</configuration>
```

#### ④ 映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lxy.example.mapper.EmpMapper">

    <resultMap id="BaseResultMap" type="emp">
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

#### ⑤ 数据库属性文件

```properties
jdbc_driver=com.mysql.cj.jdbc.Driver
jdbc_url=jdbc:mysql://127.0.0.1:3306/mybatisdb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
jdbc_username=root
jdbc_password=root
```

### （2）传统开发

```java
@Test
public void test() throws IOException {
    /*解析mybatis-config.xml配置文件*/
    InputStream resource = Resources.getResourceAsStream("mybatis-config.xml");
    /*构建SqlSession工厂*/
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resource);
    /*构建sql会话*/
    SqlSession session = factory.openSession();
    /*执行sql*/
    List<Emp> listEmp = session.selectList("com.lxy.mapper.EmpMapper.listEmp");
    listEmp.forEach(System.out::println);
    /*关闭sql会话*/
    session.close();
}
```

### （3）代理模式开发

#### ① 接口声明

```java
public interface EmpMapper {
    List<Emp> listEmp();
}
```

#### ② 测试

```java
@Test
public void test1() throws IOException {
    /*解析mybatis-config.xml配置文件*/
    InputStream resource = Resources.getResourceAsStream("mybatis-config.xml");
    /*构建SqlSession工厂*/
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resource);
    /*构建sql会话*/
    SqlSession session = factory.openSession();
    /*获取代理对象*/
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    /*调用接口方法*/
    List<Emp> listEmp = mapper.listEmp();
    listEmp.forEach(System.out::println);
    /*关闭sql会话*/
    session.close();
}
```

## 2.核心配置

### （1）DTD文件

DTD（Document Type Definition，文档类型定义）是一种定义 XML（Extensible Markup Language，可扩展标记语言）文档结构和规则的格式。它通过设定哪些元素是合法的、这些元素如何组织、以及它们可以包含哪些属性，来指导解析器理解和处理 XML 文档。这样，DTD 为 XML 文档构建了一个结构框架，确保了文档信息的一致性和正确的组织方式。

以 mybatis-config.xml 文件为例，其引入的 DTD 如下所示：

```xml
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
```

此段代码明确规定了 MyBatis 的全局配置文件中可以使用的每个元素和属性，及其相互之间的关系，从而保证配置文件的有效性和标准性。

### （2）全局配置文件

全局配置文件子元素结构如下：

- configuration（配置）
- properties（属性）
- settings（设置）
- typeAliases（类型别名）
- typeHandlers（类型处理器）
- objectFactory（对象工厂）
- plugins（插件）
- environments（环境配置）
  - environment（环境变量）
    - transactionManager（事务管理器）
    - dataSource（数据源）
- databaseIdProvider（数据库厂商标识）
- mappers（映射器）

#### ① configuration

这是配置文件的根节点，所有的配置项都包含在这里。

#### ② properties 

这部分用于定义外部配置文件的路径，或直接在此配置一些属性。可以在配置文件的其他部分通过 `${propertyName}` 的方式引用这里定义的属性值。

```xml
<properties resource="database.properties">
    <property name="username" value="root"/>
    <property name="password" value="secret"/>
</properties>
```

#### ③ settings

影响 MyBatis 全局行为的细粒度设置，例如缓存、懒加载等。

| 设置参数                         | 描述                                                         | 有效值                                           | 默认值                                                |
| -------------------------------- | :----------------------------------------------------------- | :----------------------------------------------- | :---------------------------------------------------- |
| cacheEnabled                     | 全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存。   | true                                             | false                                                 |
| lazyLoadingEnabled               | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态。 | true                                             | false                                                 |
| aggressiveLazyLoading            | 当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载. | true                                             | false                                                 |
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

####  ④ typeAliases

为 Java 类型设置一个短的名字，可以在 XML 映射文件中使用。并且预先定义好了很多类型别名，具体在`org.apache.ibatis.type.TypeAliasRegistry`类中，这也是可以使用基本类型别名的原因。

####  ⑤ typeHandlers

指定不同类型之间的转换规则，例如 Java 类型与数据库类型的转换,通过实现`TypeHandler`或继承`BaseTypeHandler`，实现自定义类型处理器。

```xml
<typeHandlers>
    <typeHandler handler="com.example.MyTypeHandler"/>
</typeHandlers>
```

#### ⑥ objectFactory

通过实现 `ObjectFactory` 或继承`DefaultObjectFactory`，可以自定义对象创建的过程。

```xml
<objectFactory type="com.example.MyObjectFactory"/>
```

####  ⑦ plugins

允许你插入自定义行为到 MyBatis 操作数据库的过程中，类似于拦截器。

```xml
<plugins>
    <plugin interceptor="com.example.MyPlugin">
        <property name="someProperty" value="100"/>
    </plugin>
</plugins>
```

#### ⑧ environments

配置不同环境下的数据库连接，可以定义多个环境。

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

#### ⑨ mappers

指定 SQL 映射文件的位置，这些文件包含 SQL 语句和映射定义。

### （3）SQL 映射文件

SQL 映射文件子元素结构如下：

- `cache` – 该命名空间的缓存配置。
- `cache-ref` – 引用其它命名空间的缓存配置。
- `resultMap` – 描述如何从数据库结果集中加载对象，是最复杂也是最强大的元素。
- `parameterMap` – 老式风格的参数映射。此元素已被废弃，并可能在将来被移除！请使用行内参数映射。文档中不会介绍此元素。
- `sql` – 可被其它语句引用的可重用语句块。
- `insert` – 映射插入语句。
- `update` – 映射更新语句。
- `delete` – 映射删除语句。
- `select` – 映射查询语句。

#### ① **`cache`**
为当前命名空间配置缓存，提高查询效率，开启二级缓存。

```xml
<cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
```

#### ② **`cache-ref`**
引用另一个命名空间的缓存配置，实现缓存共享。

```xml
<cache-ref namespace="com.example.otherMapper"/>
```

#### ③ **`resultMap`**
详细描述如何从数据库结果集映射到 Java 对象，支持复杂的数据结构。

```xml
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="username" />
  <association property="address" column="address_id" javaType="Address">
    <id property="id" column="address_id" />
    <result property="street" column="street" />
  </association>
</resultMap>
```

#### ④ **`sql`**
定义可重用的 SQL 代码片段，减少代码重复。

```xml
<sql id="userColumns">user_id, username, password, email</sql>
```

#### ⑤ **`insert`**
映射插入操作，可返回自动生成的键值。

```xml
<insert id="insertUser" useGeneratedKeys="true" keyProperty="id">
  INSERT INTO users (username, password, email) VALUES (#{username}, #{password}, #{email})
</insert>
```

#### ⑥ **`update`**
映射更新操作，支持动态 SQL。

```xml
<update id="updateUser">
  UPDATE users SET username = #{username}, password = #{password} WHERE id = #{id}
</update>
```

#### ⑦ **`delete`**
映射删除操作。

```xml
<delete id="deleteUser">
  DELETE FROM users WHERE id = #{id}
</delete>
```

#### ⑧ **`select`**
映射查询操作，可用于复杂的结果映射。

```xml
<select id="findUserById" resultMap="userResultMap">
  SELECT * FROM users WHERE id = #{id}
</select>
```

`select`属性如下：

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

## 3.高级应用

### （1）动态SQL

### （2）批量操作

### （3）关联查询

### （4）分页

### （5）MyBatis-Plus

# 三、MyBatis核心处理层

## 1.源码环境

将源码打包到本地仓库，并关联源码，这样就可以进行注释了。

![image-20240729204618450](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407292046287.png)

## 2.三层划分

![image-20240619132028519](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407311502956.png)

### （1）接口层

接口层是开发者与MyBatis交互最频繁的层面，主要涉及以下几个关键组件和功能：

- **`SqlSession`**：这是一个面向用户的接口，封装了对数据库操作的所有方法。它的实现类负责管理SQL命令的执行、事务的处理和对结果集的处理。
- **`Mapper`**：开发者定义的接口，通过XML或注解与SQL语句绑定。MyBatis使用动态代理技术来透明地将这些接口方法调用转换为数据库操作。

### （2）核心处理层

![image-20240619164151245](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407311508770.png)



![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407311504048.png)

核心处理层是MyBatis的执行引擎，负责处理所有SQL操作的细节。主要功能包括：

1. **配置解析**：将全文配置文件、Mapper配置文件进行解析，生成访问入口。
2. **参数映射**：将方法调用中的参数转换为JDBC可以接受的形式，包括基本类型、Map或POJO。
3. **SQL解析**：解析映射文件中定义的SQL语句，实现包括条件判断、循环等动态SQL功能。
4. **SQL执行**：使用JDBC API执行SQL语句，这包括创建`PreparedStatement`，设置参数，执行查询或更新。
5. **结果映射**：将JDBC返回的`ResultSet`转换为Java对象，可以是简单的JavaBean、集合或复杂的嵌套对象。

此外，插件（如分页插件、权限插件等）也在这一层实现，通常通过拦截器（Interceptor）方式集成，以增强或修改SQL执行流程。

### （3）基础支持层

基础支持层提供了底层的、通用的技术支持和服务，这些功能确保核心层可以高效、稳定地运行。包括：

- **数据源管理**：管理数据库连接，包括连接的配置、获取、释放等。
- **事务管理**：处理事务的开启、提交和回滚。
- **缓存管理**：提供一级缓存和二级缓存支持，减少数据库访问次数，提高性能。
- **日志管理**：集成日志框架，方便跟踪执行过程和调试。
- **XML解析**：解析映射文件，将XML配置转化为内部配置对象。
- **反射、IO和其他工具**：提供反射机制操作对象，文件IO处理等。

这些组件确保MyBatis能够作为一个健壮的框架运行，在各种应用环境下提供稳定的数据访问服务。

## 3.核心对象

### （1）简单描述

#### ① Configuration
- **功能**：MyBatis的配置中心，包含所有配置信息。
- **关键部件**：
  - **MapperRegistry**：维护Mapper接口与代理对象的关系。
  - **TypeAliasRegistry**：管理类名与别名的对应，简化XML配置。
  - **TypeHandlerRegistry**：管理Java类型与数据库类型间的映射和转换。

#### ② SqlSession
- **功能**：提供执行SQL命令、获取Mapper及管理事务的接口，是与数据库交互的直接接口。
- **实现类**：**DefaultSqlSession**，封装了数据库操作的所有方法。

#### ③ Executor
- **功能**：负责SQL生成和执行，以及事务管理。
- **实现类**：
  - **SimpleExecutor**：每次操作创建新Statement。
  - **ReuseExecutor**：重用Statements。
  - **BatchExecutor**：批量更新。
  - **BaseExecutor**：提供缓存管理和延迟加载。

#### ④ StatementHandler
- **功能**：封装JDBC Statement的使用，负责SQL执行过程。
- **类型**：**SimpleStatementHandler**、**PreparedStatementHandler**、**CallableStatementHandler**。

#### ⑤ ParameterHandler
- **功能**：将参数转换成JDBC格式。
- **默认实现**：**DefaultParameterHandler**，解析和设置SQL参数。

#### ⑥ ResultSetHandler
- **功能**：将ResultSet转换成Java对象。
- **默认实现**：**DefaultResultSetHandler**，处理结果集映射。

#### ⑦ MapperProxy
- **功能**：生成Mapper接口的代理对象，将方法调用转为SQL命令。
- **生成方式**：**MapperProxyFactory**。

#### ⑧ MappedStatement
- **功能**：维护SQL映射的详细信息，包括SQL语句和参数结果映射。
- **组件**：**SqlSource** 和 **BoundSql**，负责生成可执行的SQL。

#### ⑨ SqlSessionFactory
- **功能**：创建和管理`SqlSession`的工厂。
- **重要性**：作为创建`SqlSession`的入口，持久存在整个应用周期内，通常实现为单例模式。

## 4.获取 `SqlSessionFactory`

首先解析配置信息并将其封装在`Configuration`对象中。这个过程涉及到读取和解析全局配置文件（通常是`mybatis-config.xml`）以及相关的映射文件（通常是`*Mapper.xml`文件）。完成这些步骤后，使用这个`Configuration`对象创建一个`DefaultSqlSessionFactory`实例，该实例随后用于生成`SqlSession`，通过`SqlSession`可以执行实际的数据库操作。

### （1）`SqlSessionFactoryBuilder`
这个类用于构建`SqlSessionFactory`实例。它使用配置文件来构建`SqlSessionFactory`。

```java
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
```

`SqlSessionFactoryBuilder` 提供了多种`build`方法重载，以下是核心方法。

```java
public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
    try {
        // 创建 XMLConfigBuilder 对象，用于解析 mybatis-config.xml 配置文件。这个过程中会创建并初始化 Configuration 对象。
        XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
        // 解析配置文件并构建 Configuration 对象。此对象包含了 MyBatis 配置文件和映射文件中的所有配置信息。
        return build(parser.parse());
    } catch (Exception e) {
        throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
        ErrorContext.instance().reset();
        try {
            inputStream.close();
        } catch (IOException e) {
        }
    }
}
public SqlSessionFactory build(Configuration config) {
    // 使用解析得到的 Configuration 对象创建 DefaultSqlSessionFactory 实例。
    return new DefaultSqlSessionFactory(config);
}
```

### （2）`Configuration`
MyBatis的配置对象，包含了所有的设置信息，例如`MappedStatement`等。

```java
 protected final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("Mapped Statements collection")
      .conflictMessageProducer((savedValue, targetValue) ->
          ". please check " + savedValue.getResource() + " and " + targetValue.getResource());
```

### （3）`XMLConfigBuilder`
专门用于解析MyBatis的全局配置文件。它读取配置文件并构建`Configuration`对象。

```java
private XMLConfigBuilder(XPathParser parser, String environment, Properties props) {
    // 调用父类构造器，初始化一个新的Configuration对象。此步骤包括注册类型别名等预配置。
    super(new Configuration()); 
   
    ErrorContext.instance().resource("SQL Mapper Configuration");
    this.configuration.setVariables(props); 
    this.parsed = false; 
    this.environment = environment; 
    
    // 初始化XPath解析器，用于从XML文件中解析出配置信息。
    this.parser = parser; 
}
```

`XMLConfigBuilder`提供了`parse`方法

```java
public Configuration parse() {
    // 检查是否已解析过配置，确保每个实例只被解析一次。
    if (parsed) {
      throw new BuilderException("Each XMLConfigBuilder can only be used once.");
    }
    parsed = true;  
    // 开始解析configuration节点
    parseConfiguration(parser.evalNode("/configuration"));
    return configuration;  
}
/**
 * 解析全局配置文件，将配置信息加载到Configuration对象中。
 * @param root 配置文件的根节点
 */
private void parseConfiguration(XNode root) {
    try {
      // 解析<properties>标签，这是首先进行的，以确保所有属性都被加载。
      propertiesElement(root.evalNode("properties"));
      // 解析<settings>标签，这里定义了MyBatis的运行时行为。
      Properties settings = settingsAsProperties(root.evalNode("settings"));
      // 加载自定义的VFS实现。
      loadCustomVfs(settings);
      // 设置自定义日志实现。
      loadCustomLogImpl(settings);
      // 解析<typeAliases>标签，为Java类型设置别名。
      typeAliasesElement(root.evalNode("typeAliases"));
      // 解析<plugins>标签，注册拦截器插件。
      pluginElement(root.evalNode("plugins"));
      // 解析<objectFactory>标签，用于创建结果对象。
      objectFactoryElement(root.evalNode("objectFactory"));
      // 解析<objectWrapperFactory>标签，用于包装返回的对象。
      objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
      // 解析<reflectorFactory>标签，用于反射操作。
      reflectorFactoryElement(root.evalNode("reflectorFactory"));
      // 应用<settings>配置到Configuration对象。
      settingsElement(settings);
      // 解析<environments>标签，配置数据库环境。
      environmentsElement(root.evalNode("environments"));
      // 解析<databaseIdProvider>标签，用于多数据库厂商支持。
      databaseIdProviderElement(root.evalNode("databaseIdProvider"));
      // 解析<typeHandlers>标签，配置类型处理器。
      typeHandlerElement(root.evalNode("typeHandlers"));
      // 解析<mappers>标签，加载SQL映射文件。
      mapperElement(root.evalNode("mappers")); // 将映射文件中的SQL语句和映射配置加载到Configuration对象中。
    } catch (Exception e) {
      // 在解析配置时发生异常，抛出更明确的错误信息。
      throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
    }
}
```

在解析<mappers>标签时，使用`mapperElement（）`方法，又构建了`XMLMapperBuilder`，用于解析映射器XML文件（通常是`*Mapper.xml`），并将解析后的SQL语句和映射配置封装到`MappedStatement`，并添加到`Configuration`对象中。

### （4）时序图

![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407311034238.png)

## 5.获取`SqlSession`

`DefaultSqlSessionFactory`提供了`openSession`接口生成`SqlSession`，首先涉及到使用`Configuration`对象中的设置来配置`Executor`，根据配置信息，可以选择不同类型的`Executor`，以适应不同的性能需求和操作特点。配置好的`Executor`随后与`Configuration`及其他可能的设置一起被封装在`DefaultSqlSession`实例中返回。

### （1）`DefaultSqlSessionFactory`

依赖于`Configuration`对象来获取数据库环境设置、映射器和其他配置信息。利用这些信息，`DefaultSqlSessionFactory`生成配置好的`SqlSession`。

```java
@Override
public SqlSession openSession() {
  // 从默认数据源打开一个SqlSession，使用配置中定义的默认Executor类型。
  // 默认情况下事务自动提交被关闭。
  return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, false);
}

/**
 * 从数据源打开一个SqlSession。
 * @param execType 执行器的类型，指定如何创建Executor。
 * @param level 事务隔离级别，如果传入null，则使用数据源的默认隔离级别。
 * @param autoCommit 是否自动提交事务。如果为true，每个SQL语句后事务将自动提交。
 * @return 返回一个新的SqlSession实例。
 */
private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
    Transaction tx = null;
    try {
      // 获取配置中设置的环境，环境包括数据源和事务工厂。
      final Environment environment = configuration.getEnvironment();
      // 从环境中获取事务工厂。
      final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
      // 使用事务工厂，根据提供的数据源、隔离级别和自动提交设置，创建一个新的事务。
      tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
      // 创建一个Executor，它是基于提供的事务对象和指定的执行器类型。
      final Executor executor = configuration.newExecutor(tx, execType);
      // 返回一个新的DefaultSqlSession实例，它封装了配置、Executor和事务提交设置。
      return new DefaultSqlSession(configuration, executor, autoCommit);
    } catch (Exception e) {
      // 在打开会话失败的情况下，确保事务被正确关闭，防止资源泄漏。
      closeTransaction(tx);
      // 封装并抛出具体的异常信息，提供错误原因。
      throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
    } finally {
      // 重置错误上下文，清除之前的状态，保持错误上下文的准确性。
      ErrorContext.instance().reset();
    }
}

```

`Configuration`中的`newExecutor()`方法创建`Executor`，根据执行器类型创建相应的Executor实例，如果启用了二级缓存，对Executor进行装饰，应用所有插件对Executor的增强。

```java
/**
 * 创建一个新的Executor对象，这是执行数据库命令的核心。
 * @param transaction 当前事务上下文
 * @param executorType 指定的执行器类型，如未指定则使用默认类型
 * @return 返回配置后的Executor实例
 */
public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    // 如果没有指定执行器类型，则使用配置中的默认类型。如果默认类型也未设置，使用SIMPLE类型。
    executorType = executorType == null ? defaultExecutorType : executorType;
    executorType = executorType == null ? ExecutorType.SIMPLE : executorType;

    Executor executor;

    // 根据执行器类型创建相应的Executor实例。
    if (ExecutorType.BATCH == executorType) {
        // BATCH类型执行器，对SQL语句执行进行批处理，以减少网络往返。
        executor = new BatchExecutor(this, transaction);
    } else if (ExecutorType.REUSE == executorType) {
        // REUSE类型执行器，重用Statement对象，减少对象的创建和销毁。
        executor = new ReuseExecutor(this, transaction);
    } else {
        // SIMPLE类型执行器，默认类型，每次操作都创建新的Statement对象。
        executor = new SimpleExecutor(this, transaction);
    }

    // 如果启用了二级缓存，对Executor进行装饰，增加缓存层。
    if (cacheEnabled) {
        // 使用装饰器模式增加缓存功能，改善查询性能。
        executor = new CachingExecutor(executor);
    }

    // 应用所有插件对Executor的增强，这可能包括性能监控、日志等插件。
    executor = (Executor) interceptorChain.pluginAll(executor);

    // 返回最终配置好的Executor实例。
    return executor;
}
```

### （2）`Executor`

`Executor`是接口，核心的数据库操作执行者。它封装了JDBC或任何其他数据库交互方式，确保SQL命令正确执行，同时管理事务。

```java
public interface Executor {
  // 表示没有结果处理的常量。
  ResultHandler NO_RESULT_HANDLER = null;

  /**
   * 执行更新操作（如INSERT、UPDATE、DELETE）。
   * @param ms 映射声明，定义了SQL语句及其配置。
   * @param parameter 传递给SQL语句的参数对象。
   * @return 影响的行数。
   * @throws SQLException 执行更新时发生错误。
   */
  int update(MappedStatement ms, Object parameter) throws SQLException;

  /**
   * 执行查询操作，返回对象列表。
   * @param ms 映射声明，用于查询。
   * @param parameter 查询使用的参数对象。
   * @param rowBounds 用于限制查询范围的RowBounds对象。
   * @param resultHandler 结果处理器，用于处理查询结果。
   * @param cacheKey 缓存键，用于标识查询缓存。
   * @param boundSql 绑定的SQL语句。
   * @return 查询结果列表。
   * @throws SQLException 执行查询时发生错误。
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

  /**
   * 执行查询操作，返回对象列表。
   * @param ms 映射声明，用于查询。
   * @param parameter 查询使用的参数对象。
   * @param rowBounds 用于限制查询范围的RowBounds对象。
   * @param resultHandler 结果处理器，用于处理查询结果。
   * @return 查询结果列表。
   * @throws SQLException 执行查询时发生错误。
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

  /**
   * 执行查询操作，返回游标。
   * @param ms 映射声明，用于查询。
   * @param parameter 查询使用的参数对象。
   * @param rowBounds 用于限制查询范围的RowBounds对象。
   * @return 查询结果的游标。
   * @throws SQLException 执行查询时发生错误。
   */
  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

  /**
   * 执行批处理操作，通常用于批量更新。
   * @return 批处理结果的列表。
   * @throws SQLException 执行批处理时发生错误。
   */
  List<BatchResult> flushStatements() throws SQLException;

  /**
   * 提交事务，如果required为true则必须提交。
   * @param required 是否必须提交。
   * @throws SQLException 提交事务时发生错误。
   */
  void commit(boolean required) throws SQLException;

  /**
   * 回滚事务，如果required为true则必须回滚。
   * @param required 是否必须回滚。
   * @throws SQLException 回滚事务时发生错误。
   */
  void rollback(boolean required) throws SQLException;

  /**
   * 创建用于缓存的键。
   * @param ms 映射声明。
   * @param parameterObject 参数对象。
   * @param rowBounds RowBounds对象。
   * @param boundSql 绑定的SQL语句。
   * @return 缓存键对象。
   */
  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

  /**
   * 检查指定的映射声明和缓存键是否已被缓存。
   * @param ms 映射声明。
   * @param key 缓存键。
   * @return 如果已缓存则返回true。
   */
  boolean isCached(MappedStatement ms, CacheKey key);

  /**
   * 清除本地一级缓存。
   */
  void clearLocalCache();

  /**
   * 延迟加载一级缓存中的数据。
   * @param ms 映射声明。
   * @param resultObject 结果对象。
   * @param property 属性名。
   * @param key 缓存键。
   * @param targetType

 目标类型。
   */
  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

  /**
   * 获取事务对象。
   * @return 当前的事务对象。
   */
  Transaction getTransaction();

  /**
   * 关闭执行器，如果forceRollback为true，则强制回滚未提交的事务。
   * @param forceRollback 是否强制回滚。
   */
  void close(boolean forceRollback);

  /**
   * 检查执行器是否已关闭。
   * @return 如果已关闭返回true。
   */
  boolean isClosed();

  /**
   * 设置执行器的包装器。
   * @param executor 要设置的执行器。
   */
  void setExecutorWrapper(Executor executor);
}
```

`BaseExecutor`是`Executor`的实现类，以下是其继承的子类：

- **SimpleExecutor**：每次操作都创建一个新的数据库连接。
- **ReuseExecutor**：复用数据库连接。
- **BatchExecutor**：支持批量更新操作以优化性能。

### （3）时序图

![image-20240619141537624](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407311509979.png)

## 6.获取`Mapper`

### （1）`DefaultSqlSession`

`DefaultSqlSession`是`SqlSession`接口的一个实现，提供了执行SQL命令、获取Mapper、管理事务等功能。它是用户与数据库交互的主要接口。

在获取Mapper时，`DefaultSqlSession`使用配置中注册的`MapperRegistry`来查找和创建Mapper代理。

```java
//DefaultSqlSession
@Override
public <T> T getMapper(Class<T> type) {
    return configuration.getMapper(type, this);
}
```

```java
//Configuration
public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
  return mapperRegistry.getMapper(type, sqlSession);
}
```

`MapperRegistry`这个类负责维护所有Mapper接口与其对应的代理工厂的映射关系。每一个Mapper接口都有一个相应的`MapperProxyFactory`。

```java
//Mapper接口与MapperProxyFactory映射表
private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

@SuppressWarnings("unchecked")
/**
 * 从注册表中获取指定Mapper接口的代理实例。
 * @param type Mapper接口的Class类型。
 * @param sqlSession 当前的SqlSession，它是执行数据库操作的会话。
 * @return 返回指定类型的Mapper接口的代理实例。
 * @throws BindingException 如果没有找到对应的Mapper接口或创建实例时发生错误。
 */
public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    // 从已知的Mapper注册表中获取对应的MapperProxyFactory。
    final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
    if (mapperProxyFactory == null) {
        // 如果没有注册过这个Mapper接口，抛出绑定异常。
        throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
    }
    try {
        // 使用得到的MapperProxyFactory来创建一个新的代理实例。
        return mapperProxyFactory.newInstance(sqlSession);
    } catch (Exception e) {
        throw new BindingException("Error getting mapper instance. Cause: " + e, e);
    }
}

```

### （2）`MapperProxyFactory`

`MapperProxyFactory`是一个工厂类，负责创建一个`MapperProxy`代理对象，该对象实现了Mapper接口，并能将接口方法调用转化为数据库操作。

这个工厂类使用JDK动态代理机制，在运行时创建实现了Mapper接口的代理对象。每次通过`SqlSession.getMapper`获取Mapper时，实际上是获取由这个工厂创建的代理对象。

```java
public class MapperProxyFactory<T> {

  /**
   * 代理工厂的构造函数参数，指定了要创建代理的Mapper接口。
   */
  private final Class<T> mapperInterface;
  
  /**
   * 方法缓存，用于存储映射的方法和对应的调用处理器，优化反射操作的性能。
   */
  private final Map<Method, MapperMethodInvoker> methodCache = new ConcurrentHashMap<>();

  /**
   * 构造函数，初始化一个新的MapperProxyFactory实例。
   * @param mapperInterface 要代理的Mapper接口的Class对象。
   */
  public MapperProxyFactory(Class<T> mapperInterface) {
    this.mapperInterface = mapperInterface;
  }

  /**
   * 获取当前代理工厂关联的Mapper接口Class对象。
   * @return 代理的Mapper接口Class对象。
   */
  public Class<T> getMapperInterface() {
    return mapperInterface;
  }

  /**
   * 获取当前代理工厂的方法缓存。
   * @return 包含方法和对应Invoker的映射关系的缓存。
   */
  public Map<Method, MapperMethodInvoker> getMethodCache() {
    return methodCache;
  }

  @SuppressWarnings("unchecked")
  /**
   * 利用Java动态代理机制创建实现了mapperInterface接口的代理对象。
   * @param mapperProxy 实现InvocationHandler接口的代理处理器。
   * @return 代理对象，它实现了mapperInterface接口。
   */
  protected T newInstance(MapperProxy<T> mapperProxy) {
    // 创建代理对象，该代理对象实现了指定的mapperInterface接口。
    return (T) Proxy.newProxyInstance(
      mapperInterface.getClassLoader(), // 使用mapper接口的类加载器
      new Class[] { mapperInterface },   // 代理类需要实现的接口列表
      mapperProxy                        // 调用处理器，负责处理接口方法的调用
    );
  }

  /**
   * 创建一个新的mapper代理实例。
   * @param sqlSession 当前操作数据库的会话。
   * @return 实现了mapperInterface接口的代理对象。
   */
  public T newInstance(SqlSession sqlSession) {
    // 创建MapperProxy对象，它是实现了InvocationHandler接口的代理处理器。
    final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
    // 调用newInstance方法创建代理对象。
    return newInstance(mapperProxy);
  }

}

```

### （3）`MapperProxy`

`MapperProxy`是实现了`InvocationHandler`接口的代理类，用于拦截对Mapper接口方法的调用。

```java
private final Map<Method, MapperMethodInvoker> methodCache;

/**
 * 处理对Mapper接口方法的动态代理调用。
 * @param proxy 代理对象
 * @param method 被调用的方法
 * @param args 方法参数
 * @return 方法调用的结果
 * @throws Throwable 捕获并处理方法执行过程中抛出的所有异常
 */
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
        // 如果是Object类的基本方法，直接在当前实例上执行，不进行数据库操作
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            // 如果是Mapper接口方法，通过缓存的Invoker执行SQL操作
            return cachedInvoker(method).invoke(proxy, method, args, sqlSession);
        }
    } catch (Throwable t) {
        // 对异常进行解包，重新抛出更具体的异常信息
        throw ExceptionUtil.unwrapThrowable(t);
    }
}

/**
 * 根据方法获取或创建相应的MapperMethodInvoker，该对象负责具体的SQL操作。
 * @param method Mapper接口中的方法
 * @return MapperMethodInvoker实例，负责执行具体的方法
 * @throws Throwable 如果创建Invoker过程中出现错误
 */
private MapperMethodInvoker cachedInvoker(Method method) throws Throwable {
    try {
        // 使用methodCache缓存方法执行器，避免重复创建执行器，提高性能
        return methodCache.computeIfAbsent(method, m -> {
            // 检查方法是否是接口默认方法
            if (m.isDefault()) {
                try {
                    // 根据Java版本使用不同的方法处理机制
                    if (privateLookupInMethod == null) {
                        return new DefaultMethodInvoker(getMethodHandleJava8(method));
                    } else {
                        return new DefaultMethodInvoker(getMethodHandleJava9(method));
                    }
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                         | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 对于普通方法，创建一个新的MapperMethod实例，并封装在PlainMethodInvoker中
                return new PlainMethodInvoker(new MapperMethod(mapperInterface, method, sqlSession.getConfiguration()));
            }
        });
    } catch (RuntimeException re) {
        // 如果从methodCache获取Invoker时发生错误，获取并抛出真正的原因
        Throwable cause = re.getCause();
        throw cause == null ? re : cause;
    }
}
```

### （4）时序图

![image-20240619181230529](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407311509711.png)

## 7.`SQL`执行

### （1）`PlainMethodInvoker`

`PlainMethodInvoker`是一个专门用于执行数据库操作的类，其作用是直接执行由`MapperMethod`定义的SQL逻辑。这个类是`MapperMethodInvoker`接口的具体实现之一，用于处理那些不需要额外处理的直接数据库操作。

```java
/**
 * 实现 MapperMethodInvoker 接口的静态内部类，用于直接执行 SQL 语句。
 * 该类设计用于直接调用映射到 SQL 操作的方法，无需额外处理。
 */
private static class PlainMethodInvoker implements MapperMethodInvoker {
  // 用于执行 SQL 语句的 MapperMethod 实例。
  private final MapperMethod mapperMethod;

  /**
   * 构造函数，初始化 mapperMethod 字段。
   *
   * @param mapperMethod MapperMethod 对象，包含具体的数据库操作逻辑。
   */
  public PlainMethodInvoker(MapperMethod mapperMethod) {
    super();  // 调用父类构造函数
    this.mapperMethod = mapperMethod;
  }

  /**
   * 处理代理对象的方法调用，执行对应的 SQL 操作。
   * 
   * @param proxy 代理对象，虽然此处未直接使用，但符合代理模式的调用约定。
   * @param method 被调用的方法，描述了要执行的具体操作。
   * @param args 方法参数，传递给 SQL 语句的参数。
   * @param sqlSession 提供数据库会话的能力，用于执行 SQL。
   * @return 执行 SQL 后的结果对象。
   * @throws Throwable 如果 SQL 执行过程中出现错误，将异常抛出。
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable {
    // 调用 MapperMethod 的 execute 方法，执行 SQL 并返回结果。
    // 此方法是 SQL 执行的真正起点。
    return mapperMethod.execute(sqlSession, args);
  }
}
```

### （2）`MapperMethod`

```java
/**
 * 执行 SQL 操作的核心方法，根据 SQL 命令的类型，调用相应的 SqlSession 方法。
 * 
 * @param sqlSession 提供执行 SQL 命令的会话。
 * @param args 传递给 SQL 命令的参数。
 * @return 根据 SQL 操作和方法的返回类型处理后的结果。
 * @throws Throwable 如果在 SQL 操作过程中出现错误，抛出异常。
 */
public Object execute(SqlSession sqlSession, Object[] args) throws Throwable {
  Object result;
  switch (command.getType()) { // 根据 SQL 语句的类型决定操作
    case INSERT:
      // 将参数转换为 SQL 命令需要的形式
      Object param = method.convertArgsToSqlCommandParam(args);
      // 执行 insert 操作，并处理返回结果的数量
      result = rowCountResult(sqlSession.insert(command.getName(), param));
      break;
    case UPDATE:
      param = method.convertArgsToSqlCommandParam(args);
      result = rowCountResult(sqlSession.update(command.getName(), param));
      break;
    case DELETE:
      param = method.convertArgsToSqlCommandParam(args);
      result = rowCountResult(sqlSession.delete(command.getName(), param));
      break;
    case SELECT:
      if (method.returnsVoid() && method.hasResultHandler()) {
        // 如果方法返回值为 void 且有 ResultHandler 处理结果集
        executeWithResultHandler(sqlSession, args);
        result = null;
      } else if (method.returnsMany()) {
        // 如果返回值为集合类型
        result = executeForMany(sqlSession, args);
      } else if (method.returnsMap()) {
        // 如果返回值为 Map 类型
        result = executeForMap(sqlSession, args);
      } else if (method.returnsCursor()) {
        // 如果返回值为 Cursor 类型
        result = executeForCursor(sqlSession, args);
      } else {
        // 如果返回单个对象
        param = method.convertArgsToSqlCommandParam(args);
        result = sqlSession.selectOne(command.getName(), param);
        if (method.returnsOptional() && (result == null || !method.getReturnType().equals(result.getClass()))) {
          result = Optional.ofNullable(result);
        }
      }
      break;
    case FLUSH:
      // 执行刷新，通常用于批处理语句
      result = sqlSession.flushStatements();
      break;
    default:
      throw new BindingException("Unknown execution method for: " + command.getName());
  }
  // 处理返回值为基本类型且结果为 null 的情况
  if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
    throw new BindingException("Mapper method '" + command.getName()
        + "' attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
  }
  return result;
}
```

### （3）DefaultSqlSession

```java
/**
 * 查询数据库并返回结果列表。
 * 这个方法用于执行具体的 SQL 查询，返回一个泛型列表。
 * 
 * @param <E> 返回列表的元素类型
 * @param statement SQL语句的标识，用于在配置中查找对应的MappedStatement
 * @param parameter 传递给 SQL 语句的参数
 * @param rowBounds 用于支持分页查询的辅助对象
 * @return 查询结果的列表，类型为泛型E
 * @throws RuntimeException 如果查询过程中发生错误
 */
@Override
public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
  try {
    // 从配置中获取MappedStatement对象，此对象包含了SQL语句及其相关配置信息
    MappedStatement ms = configuration.getMappedStatement(statement);
    // 检查是否启用了缓存，如果启用了，查询操作会通过CachingExecutor来执行以利用缓存
    // wrapCollection方法对参数进行预处理，确保参数格式正确
    return executor.query(ms, wrapCollection(parameter), rowBounds, Executor.NO_RESULT_HANDLER);
  } catch (Exception e) {
    // 如果查询过程中发生异常，使用ExceptionFactory封装原始异常，并抛出新的异常
    throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
  } finally {
    // 无论查询成功还是失败，重置ErrorContext的状态以清除当前线程的错误信息
    ErrorContext.instance().reset();
  }
}
```

### （4）CachingExecutor

`CachingExecutor`是MyBatis中用于实现查询缓存的执行器。它封装了基础执行器（如`SimpleExecutor`、`ReuseExecutor`或`BatchExecutor`），并在此基础上添加了二级缓存的功能。

```java
@Override
/**
 * 执行数据库查询并使用二级缓存来提高查询效率。
 * 该方法首先检查是否存在有效的缓存，若存在并适用，则优先从缓存中获取数据。
 * 
 * @param <E> 泛型类型，表示查询结果集中的元素类型。
 * @param ms 表示映射的 SQL 语句的 MappedStatement 对象。
 * @param parameterObject SQL 语句中使用的参数对象。
 * @param rowBounds 用于分页查询的辅助对象。
 * @param resultHandler 结果处理器，用于处理 SQL 查询出的结果集。
 * @param key 用于缓存查找的缓存键。
 * @param boundSql 封装了 SQL 语句和参数的 BoundSql 对象。
 * @return 返回查询结果的列表，类型为泛型E。
 * @throws SQLException 如果查询过程中发生 SQL 异常，则抛出。
 */
public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql)
    throws SQLException {
  // 获取与当前 MappedStatement 关联的 Cache 对象
  Cache cache = ms.getCache();
  if (cache != null) {
    // 根据 MappedStatement 配置决定是否需要刷新缓存
    flushCacheIfRequired(ms);
    // 检查是否允许使用缓存且未使用结果处理器
    if (ms.isUseCache() && resultHandler == null) {
      // 确保没有使用存储过程的输出参数
      ensureNoOutParams(ms, boundSql);
      // 从二级缓存中尝试获取查询结果
      @SuppressWarnings("unchecked")
      List<E> list = (List<E>) tcm.getObject(cache, key);
      if (list == null) {
        // 如果缓存中没有数据，则通过委托执行器进行数据库查询
        list = delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
        // 将查询结果存入二级缓存
        tcm.putObject(cache, key, list);
      }
      return list;
    }
  }
  // 如果不使用缓存或缓存未命中，则通过委托执行器进行查询
  return delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
}
```

### （5）BaseExecutor

```java
@Override
/**
 * 执行数据库查询操作，此方法支持一级缓存，并处理递归查询。
 * @param <E> 泛型，表示查询结果集中的元素类型。
 * @param ms 映射的 SQL 语句的 MappedStatement 对象，包含 SQL 命令和配置信息。
 * @param parameter 用于 SQL 语句的参数。
 * @param rowBounds 用于分页的辅助对象。
 * @param resultHandler 结果处理器，用于自定义结果处理逻辑。
 * @param key 用于缓存查找的键。
 * @param boundSql 封装了 SQL 语句和参数的对象。
 * @return 返回查询结果的列表。
 * @throws SQLException 如果数据库操作产生异常。
 */
public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
  // 设置当前操作的上下文，用于错误追踪
  ErrorContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());
  // 检查执行器是否已经关闭
  if (closed) {
    throw new ExecutorException("Executor was closed.");
  }
  // 检查是否需要清空一级缓存
  if (queryStack == 0 && ms.isFlushCacheRequired()) {
    // 根据MappedStatement配置，如果需要，即使是查询操作也要清空一级缓存
    clearLocalCache();
  }
  List<E> list;
  try {
    // 防止递归查询时重复处理缓存
    queryStack++;
    // 查询一级缓存，如果不使用结果处理器，则尝试从缓存中获取结果
    list = resultHandler == null ? (List<E>) localCache.getObject(key) : null;
    if (list != null) {
      // 如果缓存中有数据，处理本地缓存中的输出参数
      handleLocallyCachedOutputParameters(ms, key, parameter, boundSql);
    } else {
      // 如果一级缓存中没有数据，从数据库查询数据
      list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
    }
  } finally {
    // 操作完成后，递归层数减少
    queryStack--;
  }
  if (queryStack == 0) {
    // 所有查询完成后，处理所有延迟加载的内容
    for (DeferredLoad deferredLoad : deferredLoads) {
      deferredLoad.load();
    }
    // 清除所有延迟加载项
    deferredLoads.clear();
    // 如果本地缓存作用域为 STATEMENT，则清空一级缓存
    if (configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
      clearLocalCache();
    }
  }
  return list;
}


/**
 * 从数据库中查询数据，此方法直接与数据库交互，同时处理一级缓存的逻辑。
 * @param <E> 泛型，表示查询结果集中的元素类型。
 * @param ms 映射的 SQL 语句的 MappedStatement 对象，包含 SQL 命令和配置信息。
 * @param parameter 用于 SQL 语句的参数。
 * @param rowBounds 用于分页的辅助对象。
 * @param resultHandler 结果处理器，用于自定义结果处理逻辑。
 * @param key 用于缓存查找的键。
 * @param boundSql 封装了 SQL 语句和参数的对象。
 * @return 返回查询结果的列表。
 * @throws SQLException 如果数据库操作产生异常。
 */
private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
    List<E> list;
    // 在一级缓存中放置一个占位符，防止在查询操作完成前相同的查询再次访问数据库
    localCache.putObject(key, EXECUTION_PLACEHOLDER);
    try {
      // 根据不同的执行器类型执行查询，SimpleExecutor 是默认的执行器类型
      list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
    } finally {
      // 查询完成后，移除占位符
      localCache.removeObject(key);
    }
    // 查询结果存入一级缓存
    localCache.putObject(key, list);
    // 如果是存储过程调用，还需要缓存输出参数
    if (ms.getStatementType() == StatementType.CALLABLE) {
      localOutputParameterCache.putObject(key, parameter);
    }
    return list;
}

```

### （6）SimpleExecutor

```java
@Override
/**
 * 执行数据库查询操作，此方法是数据库操作的核心，负责创建和执行 SQL 语句。
 * @param <E> 泛型，表示查询结果集中的元素类型。
 * @param ms 映射的 SQL 语句的 MappedStatement 对象，包含 SQL 命令和配置信息。
 * @param parameter 用于 SQL 语句的参数。
 * @param rowBounds 用于分页的辅助对象。
 * @param resultHandler 结果处理器，用于自定义结果处理逻辑。
 * @param boundSql 封装了 SQL 语句和参数的对象。
 * @return 返回查询结果的列表。
 * @throws SQLException 如果数据库操作产生异常。
 */
public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
    Statement stmt = null;
    try {
      // 获取配置信息
      Configuration configuration = ms.getConfiguration();
      // 根据配置创建 StatementHandler，通常是 PreparedStatementHandler
      // 此处理器同时负责参数处理（ParameterHandler）和结果集处理（ResultSetHandler）
      StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, resultHandler, boundSql);
      // 准备 Statement 对象，进行 SQL 语句的预编译，包括处理 SQL 占位符
      stmt = prepareStatement(handler, ms.getStatementLog());
      // 执行查询，返回结果
      return handler.query(stmt, resultHandler);
    } finally {
      // 不论查询成功与否，都确保 Statement 被关闭，释放资源
      closeStatement(stmt);
    }
}
```

### （7）PreparedStatementHandler

```java
@Override
/**
 * 执行数据库查询并处理结果集。
 * @param <E> 泛型，表示查询结果集中的元素类型。
 * @param statement 已经准备好的 SQL 语句对象，通常是 PreparedStatement 对象。
 * @param resultHandler 结果处理器，用于自定义处理 SQL 查询出的结果集。
 * @return 返回处理后的查询结果。
 * @throws SQLException 如果执行 SQL 或结果集处理过程中发生错误。
 */
public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
  // PreparedStatement 实际可能是 PreparedStatementLogger 的代理对象，用于添加日志功能
  PreparedStatement ps = (PreparedStatement) statement;
  // 执行 SQL 查询
  ps.execute(); // 如果是代理对象，则会触发代理逻辑，比如日志记录
  // 调用 ResultSetHandler 处理返回的结果集，转换成需要的格式
  return resultSetHandler.handleResultSets(ps);
}
```

# 四、MyBatis基础支持层

## 1.数据源管理

## 2.缓存管理

## 3.日志管理

## 4.反射工具管理

## 5.类型转换管理

## 6.插件管理

MyBatis 的插件机制是一个允许开发者在不修改 MyBatis 核心代码的情况下，扩展和增强框架功能的方式。这种机制主要通过一个拦截器（Interceptor）接口实现，允许你在 MyBatis 执行其核心逻辑（如执行 SQL、参数设置等）的关键点插入自定义行为。

### （1）插件的工作原理

MyBatis 定义了一个 `Interceptor` 接口，任何想要实现插件功能的类都必须实现这个接口。这个接口包含以下方法：
- `intercept(Invocation invocation)`: 这是主要的方法，所有的拦截逻辑都在这里实现。当拦截到某个事件时，MyBatis 会调用此方法。
- `plugin(Object target)`: 用于包装目标对象的方法，通常通过动态代理的方式来创建代理对象。
- `setProperties(Properties properties)`: 可以通过配置传递参数给插件。

在 MyBatis 的配置文件中（通常是 `mybatis-config.xml`），你需要指定你的插件，例如：
```xml
<plugins>
  <plugin interceptor="com.example.MyPlugin">
    <property name="someProperty" value="value"/>
  </plugin>
</plugins>
```

MyBatis 使用 JDK 的动态代理或 CGLIB 来为目标对象创建代理。插件可以在这些代理对象的方法被调用时，通过 `intercept` 方法加入自定义逻辑。

MyBatis 允许拦截的四种类型的方法：

-  Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
-  ParameterHandler (getParameterObject, setParameters)
-  ResultSetHandler (handleResultSets, handleOutputParameters)
-  StatementHandler (prepare, parameterize, batch, update, query)

下面是一个简单的插件示例，它在执行 SQL 前后输出日志：

```java
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

@Intercepts({
    @Signature(
        type= Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
    )
})
public class ExamplePlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("Before executing: " + invocation.getMethod());
        Object result = invocation.proceed();
        System.out.println("After executing: " + invocation.getMethod());
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 读取配置的属性
    }
}
```

这个插件通过指定拦截 `Executor` 的 `update` 方法，可以在任何 update 操作执行前后插入自定义的逻辑。通过类似的方式，可以创建更多功能强大、定制化的插件，以满足不同的业务需求。

### （2）核心源码

在 MyBatis 的 `Configuration` 类中，`newExecutor` 方法负责创建执行器（Executor），并通过插件机制对其进行增强。代码中使用 `interceptorChain.pluginAll(executor)` 表示将执行器通过拦截器链进行包装，实现插件的功能。

**InterceptorChain 类**

该类的主要职责是管理所有的拦截器（Interceptor）并通过它们对目标对象进行代理封装。

```java
public class InterceptorChain {

  // 存储所有拦截器的列表
  private final List<Interceptor> interceptors = new ArrayList<>();

  /**
   * 对所有添加的拦截器进行遍历，并逐一对目标对象应用插件逻辑。
   * 
   * @param target 需要被插件化处理的目标对象。
   * @return 经过所有拦截器插件处理后的目标对象。
   */
  public Object pluginAll(Object target) {
    for (Interceptor interceptor : interceptors) {
      target = interceptor.plugin(target);
    }
    return target;
  }

  /**
   * 添加一个拦截器到拦截器链中。
   * 
   * @param interceptor 要添加的拦截器。
   */
  public void addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
  }

  /**
   * 返回一个不可修改的拦截器列表。
   * 
   * @return 不可修改的拦截器列表。
   */
  public List<Interceptor> getInterceptors() {
    return Collections.unmodifiableList(interceptors);
  }
}

```

**Interceptor 接口**

拦截器接口定义了拦截逻辑的基本框架，每个拦截器都应实现此接口。

```java
public interface Interceptor {

  /**
   * 实现拦截逻辑的方法。
   * 
   * @param invocation 拦截调用的上下文信息。
   * @return 拦截后的返回结果。
   * @throws Throwable 可能抛出的异常。
   */
  Object intercept(Invocation invocation) throws Throwable;

  /**
   * 生成目标对象的代理，以启用拦截逻辑。
   * 
   * @param target 被代理的原始对象。
   * @return 代理后的对象。
   */
  default Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  /**
   * 根据配置初始化拦截器实例。
   * 
   * @param properties 配置属性。
   */
  default void setProperties(Properties properties) {
    // 通常用于从配置文件中读取参数，本例中未实现具体操作。
  }
}

```

**Plugin 类**

该类实现了 `InvocationHandler` 接口，并用于动态生成代理对象，从而在方法执行前后插入自定义的拦截逻辑。

```java
public class Plugin implements InvocationHandler {

  private final Object target; // 代理的目标对象
  private final Interceptor interceptor; // 与此插件关联的拦截器
  private final Map<Class<?>, Set<Method>> signatureMap; // 存储方法签名，这些方法是拦截器需要拦截的

  private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
    this.target = target;
    this.interceptor = interceptor;
    this.signatureMap = signatureMap;
  }

  /**
   * 创建目标对象的代理。
   * 
   * @param target 需要代理的目标对象。
   * @param interceptor 应用于代理对象的拦截器。
   * @return 代理后的对象，如果目标类型没有实现任何接口，则返回原始对象。
   */
  public static Object wrap(Object target, Interceptor interceptor) {
    Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
    Class<?> type = target.getClass();
    Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
    if (interfaces.length > 0) {
      return Proxy.newProxyInstance(
          type.getClassLoader(),
          interfaces,
          new Plugin(target, interceptor, signatureMap));
    }
    return target;
  }

  /**
   * 当代理对象的一个方法被调用时执行。
   * 
   * @param proxy 代理对象本身。
   * @param method 被调用的方法。
   * @param args 方法的参数。
   * @return 方法调用的结果。
   * @throws Throwable 如果拦截或原始方法调用抛出异常。
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      Set<Method> methods = signatureMap.get(method.getDeclaringClass());
      if (methods != null && methods.contains(method)) {
        return interceptor.intercept(new Invocation(target, method, args));
      }
      return method.invoke(target, args);
    } catch (Exception e) {
      throw ExceptionUtil.unwrapThrowable(e);
    }
  }

  /**
   * 从拦截器获取带有 @Intercepts 注解的方法签名信息。
   * 
   * @param interceptor 拦截器实例。
   * @return 方法签名的映射。
   */
  private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
    Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
    if (interceptsAnnotation == null) {
      throw new PluginException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
    }
    Signature[] sigs = interceptsAnnotation.value();
    Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
    for (Signature sig : sigs) {
      Set<Method> methods = signatureMap.computeIfAbsent(sig.type(), k -> new HashSet<>());
      try {
        Method method = sig.type().getMethod(sig.method(), sig.args());
        methods.add(method);
      } catch (NoSuchMethodException e) {
        throw new PluginException("Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
      }
    }
    return signatureMap;
  }

  /**
   * 获取一个类及其父类实现的所有接口，这些接口在 @Signature 注解中有声明。
   * 
   * @param type 目标对象的类型。
   * @param signatureMap 包含拦截信息的签名映射。
   * @return 一个包含所有相关接口的数组。
   */
  private static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
    Set<Class<?>> interfaces = new HashSet<>();
    while (type != null) {
      for (Class<?> c : type.getInterfaces()) {
        if (signatureMap.containsKey(c)) {
          interfaces.add(c);
        }
      }
      type = type.getSuperclass();
    }
    return interfaces.toArray(new Class<?>[interfaces.size()]);
  }
}
```

### （3）PageHelper























