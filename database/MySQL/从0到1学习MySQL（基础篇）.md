前言：在这篇博客中，我们将深入了解数据库的基本概念，特别是关系型数据库管理系统（RDBMS）。我们将从数据库的定义开始，逐步探索其核心术语，并重点介绍MySQL数据库系统的功能和优势。这将帮助读者理解为什么数据库在现代数据管理中扮演着如此关键的角色。

另外，此篇博客主要是针对于MySQL的基础学习，后续会有进阶版。

# 一、什么是数据库

数据库是一个系统化的数据存储和管理的仓库，其中数据按照特定的数据结构进行组织。不仅如此，每个数据库都支持通过一个或多个API来创建、访问、管理、搜索以及复制存储的数据。

尽管数据可以存储在简单的文件中，文件系统在读写操作上通常速度较慢。因此，为了更高效地管理大规模数据，现代系统通常采用关系型数据库管理系统（RDBMS）。关系型数据库基于关系模型构建，它利用集合代数等数学概念高效地处理数据。

RDBMS的核心特性包括：
- 数据呈现为表格形式。
- 每一行代表一条记录。
- 每一列代表某一数据域的所有数据。
- 多行多列形成一张表格。
- 多张表格组成整个数据库。

## 混淆概念

![image-20240617131601170](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181719178.png)

- 数据（Data）：指客观事物的描述符号，这些符号包括但不限于数字，还包括字母、文字、图形、音频、视频等多种形式。
- 数据库（Database，DB）：按照一定格式存储的数据集合，支持多用户共享并与应用程序独立存在。
- 数据库管理系统（Database Management System，DBMS）：用于定义、管理、组织、存储和高效检索数据的软件。它也负责数据的安全性和完整性保护。
- 数据库应用程序（Database Application System，DBAS）：基于数据库管理系统开发，使用DBMS语法，面向最终用户的直接应用程序。
- 数据库管理员（Database Administrator，DBA）：负责数据库的日常运营和维护工作。
- 最终用户（User）：数据库应用程序的直接使用者，他们通过应用程序来操作数据，而不直接与数据库交互。
- 数据库系统（Database System，DBS）：包括数据库、数据库管理系统、数据库应用程序、数据库管理员和最终用户。

而我们即将深入学习的MySQL数据库严格意义上属于数据库管理系统，不过本文仍然按照广义上的数据库，并不深究其声明。

## 关系数据库与非关系数据库

关系型数据库（RDBMS）基于关系模型，以表格的形式存储数据。每个表格（也称为关系）由行（记录）和列（属性）组成，表格之间通过外键等方式建立联系。

**常见系统**：

- Oracle
- MySQL
- Microsoft SQL Server
- PostgreSQL

更适合需要严格数据完整性和复杂查询的场景，如金融服务、客户关系管理（CRM）系统。

非关系型数据库（NoSQL）不完全基于表格结构，可以存储非结构化和半结构化数据。它包括多种数据模型，如键值对、文档、列存储和图形数据库。

**常见系统**：

- MongoDB（文档存储）
- Redis（键值存储）
- Cassandra（列存储）
- Neo4j（图形数据库）

适合处理大规模的数据集、快速迭代的应用开发以及对灵活性和可扩展性要求高的场景，如大数据分析、实时应用程序、内容管理系统等。

## RDBMS 术语

在深入学习MySQL数据库之前，让我们首先了解一些关键的RDBMS术语：

- **数据库:** 数据库是一系列相关联表格的集合。
- **数据表:** 表是一种数据的二维矩阵，看似简单的电子表格。
- **列:** 列包含了具有相同数据类型的数据项，例如邮政编码。
- **行:** 行（也称为记录或元组）是一组相关的数据项，如用户订阅信息。
- **主键:** 唯一标识表中的记录，每个表中只能有一个主键。
- **外键:** 用于建立两个表之间的联系。
- **复合键:** 多个列组合成一个索引键，通常用于建立复合索引。
- **索引:** 索引是一种能够加快访问特定信息的数据库表结构，类似于书本的目录。

## MySQL数据库

MySQL是一个广泛使用的关系型数据库管理系统，由瑞典的MySQL AB公司开发，现归属于Oracle公司。它以其高效性、灵活性而著称，尤其适用于处理大规模数据。

- **开源性质:** MySQL遵循GPL协议，允许用户自由修改和使用源代码。
- **扩展性:** 支持处理包含数千万条记录的大型数据库。
- **兼容性:** 可以在多种操作系统上运行，并支持多种编程语言，如C、Java、Python等。
- **与PHP的兼容性:** 特别适合用于Web开发，与PHP有良好的集成。
- **可定制性:** 用户可以根据自身需求定制MySQL，以满足特定的业务需求。

通过以上介绍，希望您能更好地理解数据库和MySQL的重要性及其在现代技术环境中的应用。接下来的内容将详细介绍如何在实际项目中有效使用MySQL，以及如何利用其强大功能优化数据管理。

---

# 二、MySQL部署

MySQL是一种广泛使用的开源关系数据库管理系统。在本指南中，我们将介绍如何在Windows操作系统上下载、安装和配置MySQL，并展示一些基本的命令操作。

## 下载与安装

首先，访问MySQL的[官网](https://www.mysql.com/cn/)下载最新版本的MySQL。在学习阶段，建议使用Windows系统，以便快速上手。

下载完成后，按照安装向导的指示进行安装。安装过程中，可以选择默认设置，直到安装完成。

安装后，默认的安装目录如下：

- 软件文件夹：`C:\Program Files\MySQL\MySQL Server 8.0`
- 数据文件夹：`C:\ProgramData\MySQL\MySQL Server 8.0`

MySQL的配置文件`my.ini`位于数据文件夹中，路径为：`C:\ProgramData\MySQL\MySQL Server 8.0\my.ini`。以下是需要注意的配置信息：

```ini
port=3306  # 监听端口是3306
datadir=C:/ProgramData/MySQL/MySQL Server 8.0/Data  # 数据文件夹位置
default-storage-engine=INNODB  # 默认存储引擎
```

## 登录与访问

确保MySQL服务已经启动，可以通过任务管理器或服务管理工具查看MySQL服务的运行状态。



![MySQL服务](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181719030.png)



在客户端进行连接时，可以使用命令行工具`mysql.exe`进行连接。运行以下命令：

```bash
.\mysql.exe -hlocalhost -uroot -p
```

此命令需要在MySQL安装目录下的`bin`文件夹中运行。当然，你也可以将其配置到环境变量中，以便在任何路径下都可以直接运行该命令。

![MySQL连接](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181719666.png)



连接成功后，可以使用一些基本的MySQL命令：

```mysql
show databases;  # 显示所有数据库
use database_name;  # 切换到指定数据库
show tables;  # 显示当前数据库中的所有表
quit;  # 退出MySQL客户端
```

## Navicat 

> [Navicat | 产品](https://www.navicat.com.cn/products)

Navicat是一款强大的数据库管理和开发软件，支持多种数据库系统，包括MySQL、MariaDB、SQL Server、SQLite、Oracle和PostgreSQL等。它由香港的PremiumSoft CyberTech Ltd.开发，为数据库管理提供了一个直观、用户友好的图形界面。Navicat使得管理数据库、执行SQL脚本、同步数据库、生成报告等任务变得简单高效。

虽然Navicat提供了丰富的图形界面功能，但在学习阶段，我们建议主要使用Navicat作为显示工具，其操作还是使用传统的命令操作。这样可以帮助你更好地理解和掌握MySQL的基本命令和操作逻辑。

![image-20240718145140173](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181719449.png)

# 三、知识扫盲

## SQL语言

SQL（Structured Query Language）是一种专用于数据库管理系统的标准编程语言。它用于存储、操作和检索数据库中存储的数据。下面是一些SQL的基本组成部分：

1. **数据定义语言（DDL）**：包括创建、修改、删除数据库和数据库结构（表、索引等）的语句。常用的DDL语句有：
   - `CREATE`：用于创建新的数据库或数据库表
   - `ALTER`：用于修改现有的数据库结构
   - `DROP`：用于删除整个数据库或表
2. **数据操作语言（DML）**：用于数据的增加、删除、修改。常用的DML语句有：
   - `INSERT`：用于向表中添加新的数据行
   - `UPDATE`：用于修改表中的数据
   - `DELETE`：用于从表中删除数据
3. **数据查询语言（DQL）**：主要是`SELECT`语句，用于从一个或多个表中检索数据。
4. **数据控制语言（DCL）**：包括用于定义数据库访问权限和安全级别的命令。常用的DCL语句有：
   - `GRANT`：授权用户访问特定数据库对象的权限
   - `REVOKE`：撤销先前授予的权限
5. **事务控制语言（TCL）**：用于数据库的事务管理。
   - `COMMIT`：提交事务，使所有数据变更成为永久性的。
   - `ROLLBACK`：回滚事务到某个保存点。
   - `SAVEPOINT`：设置事务的保存点，用于回滚。

SQL的使用广泛，几乎所有的关系数据库系统如MySQL、PostgreSQL、Oracle、SQL Server等都支持SQL，使得它成为数据库管理的重要工具。

## 数据库表

表（Table）是关系型数据库中存储和组织数据的基本结构，非常适合以结构化方式表示信息。在关系型数据库中，每个表可以被看作是一个二维的数据格，具体内容包括行和列：

**记录（Record）**：也称为行（Row），在表中代表一个数据实体的集合。每行包含该实体的相关信息，例如一个员工的记录可能包含员工ID、姓名、职位和联系信息等。

在理想情况下，表中的每行都应有一定方式进行唯一标识，通常是通过一个主键（Primary Key）实现。主键是一个字段（或字段的组合），其值在表中唯一。

**字段（Field）**：也称为列（Column），它定义了存储在表中的某种类型的数据。每个字段都有特定的数据类型，如整数、字符串、日期等。

定义字段可以存储的数据类型，这有助于数据库管理系统（DBMS）更有效地存储、检索和处理数据。

## 字段数据类型

### 数值数据类型

- `INT`：存储标准整数。不同数据库系统可能会有不同的大小变体，如 `TINYINT`, `SMALLINT`, `MEDIUMINT`, `BIGINT` 等。
- `DECIMAL(M, N)`：存储固定精度和比例的数值。适用于需要精确数值表示的情况，如财务数据。`M` 是数字总位数，`N` 是小数点后的位数。

- `FLOAT`：存储单精度浮点数。
- `DOUBLE`：存储双精度浮点数。

### 字符串数据类型

- `CHAR(N)`：固定长度的字符串。存储时，字符串长度不足 `N` 时会用空格填充。
- `VARCHAR(N)`：可变长度的字符串。只使用实际需要的空间，并允许长度最多为 `N` 的字符串。

- `TEXT`：用于存储长文本。
- `BLOB`：二进制大对象，用于存储二进制数据，如图像、音频或其他多媒体数据。

### 日期和时间数据类型

- `DATE`：仅存储日期（年、月、日）。
- `TIME`：仅存储时间（小时、分钟、秒）。
- `DATETIME`：存储日期和时间。
- `TIMESTAMP`：存储日期和时间，通常用于记录数据变更的时间点。

- `YEAR`：用于存储年份。

### 布尔数据类型

- **BOOLEAN**：存储真（TRUE）或假（FALSE）值。在某些数据库系统中，它可能只是 `TINYINT` 类型的别名，其中 0 表示 FALSE，非 0 值表示 TRUE。

## 约束

在SQL中，约束用来限定数据库表中数据的规则，确保数据的准确性和可靠性。

### 非外键约束

| 约束条件                             | 约束描述                                                     |
| ------------------------------------ | ------------------------------------------------------------ |
| 主键约束（PRIMARY KEY）              | 确保每条记录在表中的唯一性。主键字段不能有重复值，且不能为NULL。 |
| 非空约束（NOT NULL）                 | 确保字段在插入记录时必须有值，不能为NULL。                   |
| 唯一约束（UNIQUE）                   | 确保字段中的所有值都是唯一的，可以有多个NULL值，但每个NULL都被视为唯一。 |
| 检查约束（CHECK）                    | 确保字段中的值符合指定的条件。                               |
| 默认值约束（DEFAULT）                | 如果在插入记录时未指定某字段的值，则自动使用预设的默认值。   |
| 字段值自动增加约束（AUTO_INCREMENT） | 适用于整数字段，每当添加新记录时，该字段的值会自动增长。与主键通常一起使用。 |

### 外键约束

外键约束（Foreign Key Constraint）是关系型数据库中的一个重要概念，它用于在两个表之间建立联系并维护数据的完整性。外键约束确保一个表中的数据值必须对应另一个表的主键值，这样可以防止记录之间的悬挂引用。

- **数据完整性**：确保引用完整性，即只能输入在关联表中已存在的值。
- **参照完整性**：确保一个表中的字段值（外键值）必须在另一个表的指定字段（通常是主键）中有对应的值。
- **级联更新和删除**：如果主键表中的数据被更新或删除，相关的外键数据可以被自动更新或删除，这通过级联规则来实现。

# 四、MySQL实战

我们可以创建一个简单的学生管理数据库，其中包括学生表（students）和班级表（classes）。下面是如何创建数据库以及设计这些表及其之间的关系：

## 创建数据库

创建数据库通常使用 `CREATE DATABASE` 语句。这条命令后面跟随你想创建的数据库名称。例如：

```sql
CREATE DATABASE SchoolDB;
```

这条命令会创建一个名为 `SchoolDB` 的新数据库。如果你需要设置特定的字符集或排序规则，可以添加额外的参数。

```sql
CREATE DATABASE SchoolDB 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

- **CHARACTER SET**：用于指定数据库的默认字符集。例如，`utf8mb4` 是一个支持全部Unicode字符的字符集。
- **COLLATE**：用于指定数据库的默认字符排序规则。这影响字符串的比较和排序。例如，`utf8mb4_unicode_ci` 是一个大小写不敏感的比较规则。

在MySQL中，如果数据库已经被创建，你不能直接修改 `CREATE DATABASE` 语句中的定义，但可以修改数据库的一些属性如字符集和排序规则，使用 `ALTER DATABASE` 语句：

```sql
sqlCopy codeALTER DATABASE SchoolDB
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

这个命令会将 `SchoolDB` 数据库的字符集更改为 `utf8mb4` 并设置排序规则为 `utf8mb4_unicode_ci`。

删除数据库通常使用 `DROP DATABASE` 语句。这条命令后面也是跟随数据库的名称。例如：

```sql
DROP DATABASE SchoolDB;
```

这条命令会删除 `SchoolDB` 数据库。使用这条命令时需要格外小心，因为一旦执行，所有在该数据库中的数据都将被永久删除，无法恢复。

## 创建表

#### 班级表（classes）

- **class_id**：班级的唯一标识符，作为主键，并设定自动递增。
- **class_name**：班级的名称。
- **teacher_name**：班级老师的名字。

```sql
CREATE TABLE `schooldb`.`classes` (
    class_id INT AUTO_INCREMENT,
    class_name VARCHAR(100) NOT NULL,
    teacher_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (class_id)
);
```

#### 学生表（students）

- **student_id**：学生的唯一标识符，作为主键。
- **student_name**：学生的姓名。
- **class_id**：学生所属的班级，这是一个外键，指向班级表的class_id。

```sql
CREATE TABLE `schooldb`.`students` (
    student_id INT AUTO_INCREMENT,
    student_name VARCHAR(100),
    class_id INT ,
    PRIMARY KEY (student_id) NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(class_id)
);
```

每个**班级**可以有多个**学生**，但每个学生只能属于一个班级。这是一个典型的“一对多”关系。

**class_id**在学生表中作为外键，确保所有的学生记录都关联到一个有效的班级。通过外键约束，我们可以确保不会在学生表中引用不存在的班级。

## DML

我们可以向刚刚创建的学生和班级表中添加一些示例数据。

### 插入班级数据

```sql
INSERT INTO classes (class_name, teacher_name) VALUES ('计算机科学', '张老师');
INSERT INTO classes (class_name, teacher_name) VALUES ('物理学', '李老师');
```

![image-20240718154306916](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181721032.png)

### 插入学生数据

在插入学生数据之前，我们需要知道每个班级的`class_id`，因为这将作为外键关联到学生表。

并且可以将多个INSERT语句整理为一条

```sql
INSERT INTO students (student_name, class_id) VALUES
('王小明', 1),
('蔡红',2),
('赵晓蓉', 2),
('李雷', 1);
```

![image-20240718154252557](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181721967.png)

注意由于存在外键的约束，插入学生数据时我们只能选择已存在的班级，同时，在我们删除班级时，也需要考虑班级内是否还存在学生。

### 修改学生数据

假设我们需要修改学生王小明的名字为王明，并且将他转移到物理学班：

```sql
UPDATE students 
SET student_name = '王明', class_id = 2 
WHERE student_name = '王小明';
```

![image-20240718155406295](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181722021.png)

### 删除学生数据

如果需要删除学生赵晓蓉的记录，我们可以使用以下SQL语句：

```sql
DELETE FROM students 
WHERE student_name = '赵晓蓉';
```

![image-20240718155427042](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181722655.png)

## DQL

### 查询学生数据

查询所有学生数据

```sql
SELECT *FROM students;
```

![image-20240718155612246](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181722241.png)

# 五、DQL详解

## 关键字

数据查询语言（DQL）主要基于`SELECT`语句来执行对数据库的查询。在使用`SELECT`语句时，经常会搭配一些关键字来过滤和排序结果，使查询更加强大和灵活。以下是一些常用的关键字及其使用方法：

#### （1）WHERE
- **用途**：用于指定查询条件，过滤返回的记录。
- **示例**：查询学生表中所有年龄大于18岁的学生。
  ```sql
  SELECT * FROM students WHERE age > 18;
  ```

#### （2）ORDER BY
- **用途**：用于对结果集进行排序。
- **示例**：按照年龄从小到大对学生进行排序。
  ```sql
  SELECT * FROM students ORDER BY age ASC;
  ```
- `ASC`表示升序，`DESC`表示降序。

#### （3）AND和OR
- **用途**：用于在WHERE子句中组合多个条件。
- **示例**：查询学生表中年龄大于18且性别为男的学生。
  ```sql
  SELECT * FROM students WHERE age > 18 AND gender = 'Male';
  ```
- **示例**：查询学生表中年龄小于18或者住在北京的学生。
  ```sql
  SELECT * FROM students WHERE age < 18 OR city = 'Beijing';
  ```

#### （4）GROUP BY
- **用途**：用于对相同数据进行分组，通常与聚合函数（如`COUNT`, `MAX`, `MIN`, `SUM`, `AVG`）一起使用。
- **示例**：按班级分组，计算每个班级的学生人数。
  ```sql
  SELECT class_id, COUNT(*) AS student_count FROM students GROUP BY class_id;
  ```

#### （5）HAVING
- **用途**：用于过滤分组后的结果，与WHERE类似，但是HAVING作用于聚合后的结果。
- **示例**：找出学生人数超过30人的班级。
  ```sql
  SELECT class_id, COUNT(*) AS student_count FROM students GROUP BY class_id HAVING student_count > 30;
  ```

是的，SQL 中除了前面提到的关键字，还有许多其他的关键字和功能可以用于更复杂的查询和数据处理。以下是一些额外的关键字和它们的应用：

#### （6）LIMIT
- **用途**：用来限制由`SELECT`语句返回的记录数，常用于分页处理。
- **示例**：查询前10个记录。
  
  ```sql
  SELECT * FROM students LIMIT 10;
  ```

#### （7）JOIN子句
- **用途**：用于结合两个或多个数据库表的记录。
- **种类**：
  - `INNER JOIN`：只返回两个表中匹配的记录。
  - `LEFT JOIN`（或`LEFT OUTER JOIN`）：返回左表的所有记录，即使右表中没有匹配。
  - `RIGHT JOIN`（或`RIGHT OUTER JOIN`）：返回右表的所有记录，即使左表中没有匹配。
  - `FULL JOIN`（或`FULL OUTER JOIN`）：返回两个表中所有匹配的记录，如果没有匹配，则相应的结果为NULL。
- **示例**：将学生表和班级表结合，找出所有学生及其所在班级的名称。
  ```sql
  SELECT students.student_name, classes.class_name
  FROM students
  INNER JOIN classes ON students.class_id = classes.class_id;
  ```

#### （8）DISTINCT
- **用途**：用于返回唯一不同的值。
- **示例**：查询所有不同的班级名称。
  ```sql
  SELECT DISTINCT class_name FROM classes;
  ```

#### （9）IN
- **用途**：用于指定某列的多个可能值。
- **示例**：查询在指定列表班级编号中的学生。
  ```sql
  SELECT * FROM students WHERE class_id IN (1, 2, 3);
  ```

#### （10）BETWEEN
- **用途**：用于在两个值之间选择范围，包括边界值。
- **示例**：查询年龄在18到22岁之间的学生。
  ```sql
  SELECT * FROM students WHERE age BETWEEN 18 AND 22;
  ```

#### （11）LIKE
- **用途**：用于在`WHERE`子句中搜索列中的指定模式。
- **示例**：查询名字以“王”开头的学生。
  ```sql
  SELECT * FROM students WHERE student_name LIKE '王%';
  ```

#### （12）CASE
- **用途**：在SQL查询中实现条件逻辑，类似于编程中的`if-else`语句。
- **示例**：根据学生的分数分配等级。
  ```sql
  SELECT student_name, score,
         CASE 
             WHEN score >= 90 THEN '优秀'
             WHEN score >= 80 THEN '良好'
             WHEN score >= 60 THEN '及格'
             ELSE '不及格'
         END AS grade
  FROM students;
  ```

#### （13）EXISTS
- **用途**：用来测试子查询是否返回任何行。
- **示例**：查询至少有一名学生的班级。
  ```sql
  SELECT * FROM classes
  WHERE EXISTS (SELECT 1 FROM students WHERE students.class_id = classes.class_id);
  ```

#### （14）UNION和UNION ALL
- **用途**：`UNION`用于合并两个或多个`SELECT`语句的结果集，并消除重复行；`UNION ALL`则合并结果集但不消除重复行。
- **示例**：将两个查询的结果合并显示。
  ```sql
  SELECT student_name FROM students WHERE class_id = 1
  UNION
  SELECT student_name FROM students WHERE class_id = 2;
  ```

#### （15）INDEXES
- **用途**：在表的列上创建索引，以加快搜索和排序的速度。
- **示例**：为学生姓名创建索引。
  ```sql
  CREATE INDEX idx_student_name ON students(student_name);
  ```

#### （16）ROLLUP和CUBE
- **用途**：用于执行多级或多维的聚合查询。
- **ROLLUP**：生成与指定列列表的层次结构相对应的汇总。
- **CUBE**：生成指定列的所有可能组合的汇总。
- **示例**（使用ROLLUP）：计算总分、每个班级的总分和全校的总分。
  ```sql
  SELECT class_id, SUM(score) FROM students GROUP BY class_id WITH ROLLUP;
  ```

## 聚合函数

SQL中的聚合函数用于在一组值上执行计算，通常与`GROUP BY`子句结合使用来对结果进行分组。以下是一些常见的聚合函数及其用途：

#### （1）COUNT
- **用途**：计算行数或非NULL值的数量。
- **示例**：计算学生总数。
  ```sql
  SELECT COUNT(*) FROM students;
  ```
- **示例**：计算特定班级的学生数。
  ```sql
  SELECT COUNT(student_id) FROM students WHERE class_id = 1;
  ```

#### （2）SUM
- **用途**：计算数值列的总和。
- **示例**：计算所有学生的总成绩。
  ```sql
  SELECT SUM(score) FROM students;
  ```

#### （3）AVG
- **用途**：计算数值列的平均值。
- **示例**：计算学生平均成绩。
  ```sql
  SELECT AVG(score) FROM students;
  ```

#### （4）MAX
- **用途**：找到数值列的最大值。
- **示例**：查找学生中的最高分。
  ```sql
  SELECT MAX(score) FROM students;
  ```

#### （5）MIN
- **用途**：找到数值列的最小值。
- **示例**：查找学生中的最低分。
  ```sql
  SELECT MIN(score) FROM students;
  ```

#### （6）GROUP_CONCAT
- **用途**：将多个列值连接成一个字符串。
- **示例**：列出每个班级的所有学生名字。
  ```sql
  SELECT class_id, GROUP_CONCAT(student_name ORDER BY student_name SEPARATOR ', ') AS students
  FROM students
  GROUP BY class_id;
  ```

## 多表查询

多表查询主要通过`JOIN`操作来实现，它允许你在一个查询中组合多个表的数据。根据连接的类型，`JOIN`可以分类为内连接（INNER JOIN）、外连接（LEFT JOIN, RIGHT JOIN, FULL OUTER JOIN）等。

现在表结构如下：注意这里取消了外键约束，李四并没有分配班级

![image-20240718161409554](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181722841.png)![image-20240718161615740](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181722020.png)

#### （1）INNER JOIN
- **用途**：只返回两个表中匹配的记录。
- **示例**：查询学生的姓名及其对应的班级名称。
  
  ```sql
  SELECT students.student_name, classes.class_name
  FROM students
  INNER JOIN classes ON students.class_id = classes.class_id;
  ```

![image-20240718161648795](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181720787.png)

#### （2）LEFT JOIN

- **用途**：返回左表的所有记录，即使右表中没有匹配。
- **示例**：查询所有学生及其可能的班级名称（包括未分班的学生）。
  
  ```sql
  SELECT students.student_name, classes.class_name
  FROM students
  LEFT JOIN classes ON students.class_id = classes.class_id;
  ```

![image-20240718161710513](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181720531.png)

#### （3）RIGHT JOIN

- **用途**：返回右表的所有记录，即使左表中没有匹配。
- **示例**：查询所有班级及其可能的学生姓名。
  ```sql
  SELECT students.student_name, classes.class_name
  FROM students
  RIGHT JOIN classes ON students.class_id = classes.class_id;
  ```

![image-20240718161755517](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407181720182.png)

## 子查询

子查询是嵌套在其他SQL查询中的查询，可以用在`SELECT`、`FROM`和`WHERE`子句中。

#### （1）在WHERE子句中的子查询
- **用途**：允许在主查询的条件中使用另一个SELECT语句的结果。
- **示例**：查询分数高于学生平均分的学生。
  ```sql
  SELECT *
  FROM students
  WHERE score > (SELECT AVG(score) FROM students);
  ```

#### （2）在FROM子句中的子查询
- **用途**：子查询作为一个临时表出现在FROM子句中。
- **示例**：查询每个班级的平均分，并找出超过这个平均分的学生。
  ```sql
  SELECT s.student_name, s.score
  FROM students s
  INNER JOIN (
      SELECT class_id, AVG(score) AS avg_score
      FROM students
      GROUP BY class_id
  ) AS class_avg ON s.class_id = class_avg.class_id
  WHERE s.score > class_avg.avg_score;
  ```

#### （3）在SELECT子句中的子查询
- **用途**：子查询用于计算作为一个字段返回的值。
- **示例**：查询每个学生及其班级的平均分。
  ```sql
  SELECT student_name, (
      SELECT AVG(score)
      FROM students
      WHERE class_id = s.class_id
  ) AS class_avg_score
  FROM students s;
  ```

多表查询和子查询是SQL中强大的工具，使你能够有效地从关系型数据库中提取和处理复杂的数据关系。

# 六、高级特性

## 事务

事务（Transaction）是数据库管理系统中执行操作的基本单位，它可以包含一个或多个命令。事务具备四大特性，通常以ACID为代表，这些特性确保了事务的可靠执行以及数据的一致性和完整性。

### ACID特性

**原子性（Atomicity）**：原子性确保事务中的操作要么全部完成，要么全部不执行。

**一致性（Consistency）**：一致性确保事务的执行将数据库从一个一致的状态转换到另一个一致的状态。

**隔离性（Isolation）**：隔离性确保并发事务的执行不会互相干扰，事务对数据的修改在提交前对其他事务是不可见的。

**持久性（Durability）**：持久性确保一旦事务被提交，其结果就是永久的，即使系统发生故障也不会丢失数据。

### 并发问题

#### （1）脏读（Dirty Reads）

脏读发生在一个事务读取了另一个事务尚未提交的数据。如果那个未提交的事务最终被回滚，则第一个事务读取的数据实际上是不存在的。

**例子**：

- 事务A修改一个记录的值但未提交。
- 事务B读取了事务A修改的值。
- 事务A回滚改动，导致事务B读取的数据变成了不存在的“脏”数据。

#### （2）不可重复读（Non-repeatable Reads）

不可重复读发生在一个事务中多次读取同一数据集合时，由于其他事务的更新操作，导致两次读取的数据不一致。

**例子**：

- 事务A读取一个记录。
- 事务B更新该记录并提交。
- 事务A再次读取同一记录，发现记录已经变化。

#### （3）幻读（Phantom Reads）

幻读发生在一个事务中多次读取同一数据集合时，由于其他事务的插入操作，每次返回的记录数都可能不同。

**例子**：

- 事务A读取一个记录。
- 事务B插入符合事务A查询条件的新记录并提交。
- 事务A再次读取同一记录，发现有新的记录出现。

### 事务隔离级别

| 隔离级别             | Dirty Reads | Non-repeatable Reads | Phantom Reads |
| -------------------- | :---------: | :------------------: | :-----------: |
| **READ UNCOMMITTED** |      √      |          √           |       √       |
| **READ COMMITTED**   |      ×      |          √           |       √       |
| **REPEATABLE READ**  |      ×      |          ×           |       √       |
| **SERIALIZABLE**     |      ×      |          ×           |       ×       |

每种隔离级别都是在性能和一致性之间做权衡。例如，较低的隔离级别（如读未提交）可能导致更多的并发问题，但可以获得更高的性能；而较高的隔离级别（如串行化）虽然可以避免绝大多数并发问题，但可能会降低并发性能。选择合适的隔离级别需要根据具体的应用场景和性能需求来决定。

全局设置：影响服务器上所有新创建的事务的隔离级别。

```sql
SET GLOBAL transaction_isolation = 'REPEATABLE-READ';
```

会话级别设置：只影响当前会话中的事务。

```sql
SET SESSION transaction_isolation = 'READ-COMMITTED';
```

你可以通过以下命令查询当前的全局和会话隔离级别：

```sql
SELECT @@GLOBAL.transaction_isolation, @@SESSION.transaction_isolation;
```

### 使用SQL进行事务控制

在SQL中，可以通过一系列命令来管理事务，从而确保数据操作的ACID属性。以下是一些基本的SQL命令用于控制事务：

1. **BEGIN TRANSACTION** 或 **START TRANSACTION**
   - 开始一个新的事务。
   - 示例：
     ```sql
     START TRANSACTION;
     ```

2. **COMMIT**
   - 提交当前事务，使所有数据变更永久生效。
   - 示例：
     ```sql
     COMMIT;
     ```

3. **ROLLBACK**
   - 回滚当前事务，撤销自事务开始以来的所有未提交的更改。
   - 示例：
     ```sql
     ROLLBACK;
     ```

4. **SAVEPOINT**
   - 设置事务中的一个保存点，可以回滚到这个点而不是整个事务的起始。
   - 示例：
     ```sql
     SAVEPOINT savepoint_name;
     ```

5. **ROLLBACK TO SAVEPOINT**
   - 回滚到指定的保存点。
   - 示例：
     ```sql
     ROLLBACK TO savepoint_name;
     ```

6. **RELEASE SAVEPOINT**
   - 删除一个保存点。
   - 示例：
     ```sql
     RELEASE SAVEPOINT savepoint_name;
     ```

### 事务的实际应用示例

假设你在银行数据库管理系统中处理账户转账，涉及两个账户，一个账户扣款（账户A），另一个账户（账户B）相应地增加同等金额。

```sql
START TRANSACTION;

-- 尝试从账户A扣款100
UPDATE accounts SET balance = balance - 100 WHERE account_id = 'A';

-- 尝试向账户B存入100
UPDATE accounts SET balance = balance + 100 WHERE account_id = 'B';

-- 检查是否有错误发生，如果没有，提交事务
COMMIT;

-- 如果有错误，则回滚所有更改
ROLLBACK;
```

在这个例子中，使用事务确保了转账操作的原子性和一致性。如果在扣款或者存款过程中发生任何错误（如余额不足），则整个事务将回滚，保持数据的一致性。

事务的正确使用是数据库管理的重要方面，特别是在处理重要数据时，如金融信息。确保你熟悉这些控制技术，能有效地应用它们来维护数据库的完整性和可靠性。

## 视图（Views）

视图是基于SQL查询的虚拟表。它们是可查询的对象，存在于数据库中，其内容由查询定义，但不存储实际的数据。视图可以包含一个或多个表中的数据，并可以像表一样使用。

- **简化复杂的SQL操作**：通过将复杂的查询封装在视图中，用户可以通过简单的查询视图来获取需要的数据。
- **增强数据安全性**：可以限制用户通过视图访问特定的数据，而不是直接访问底层的表。
- **逻辑数据独立性**：视图可以作为数据结构的抽象，隐藏底层数据的具体实现。

#### **创建视图的示例**

假设有一个只展示计算机科学班级学生的视图。

```sql
CREATE VIEW CompSciStudents AS
SELECT student_id, student_name, class_id
FROM students
WHERE class_id = 1  -- 假设1是计算机科学班的class_id
WITH CHECK OPTION;
```

如果没有 `WITH CHECK OPTION`，用户可能能通过这个视图插入一个不属于计算机科学班的学生，这违反了视图的目的。使用 `WITH CHECK OPTION` 可以防止这种情况发生。

#### **查询视图**

```sql
SELECT * FROM CompSciStudents;
```

如果尝试通过这个视图插入一个不属于计算机科学班的学生（假设`class_id`不是1），操作会失败：

```sql
INSERT INTO CompSciStudents (student_name, class_id) VALUES ('新学生', 2);
```

因为`class_id`为2不满足视图的定义条件（`class_id = 1`），所以这个插入操作将被拒绝，并且数据库将返回一个错误。

## 存储过程（Stored Procedures）

存储过程是一种预编译的SQL代码集，可以像调用函数一样在数据库中执行。存储过程通常用于执行重复的或复杂的数据库操作，它们在数据库服务器上执行，可以减少网络流量和提高性能。

- **性能优化**：由于存储过程是预编译的，其执行比相同操作的普通SQL查询快。
- **减少网络流量**：多条语句的操作可以封装在一个存储过程中，只需单一的调用即可执行，减少了客户端和数据库服务器之间的通信。
- **提高安全性**：可以通过权限控制限制对存储过程的访问，而不是直接操作表。

#### **创建存储过程的示例**

假设我们有一个存储过程，它接收一个班级ID，然后返回该班级的所有学生的信息：

```sql
CREATE PROCEDURE GetStudentsByClass(IN class_id INT)
BEGIN
    SELECT * FROM students WHERE class_id = class_id;
END;
```

在这个示例中，`class_id`是一个输入参数，用于从`students`表中检索特定班级的学生。

创建一个存储过程，它接收一个学生ID，并返回该学生的姓名：

```sql
CREATE PROCEDURE GetStudentName(IN student_id INT, OUT student_name VARCHAR(100))
BEGIN
    SELECT student_name INTO student_name FROM students WHERE student_id = student_id;
END;
```

在这个示例中，`student_name`是一个输出参数，用于返回查询结果。

存储过程还可以定义一个返回值，通常用于返回操作的状态或结果代码。

创建一个存储过程，它尝试更新学生信息，并返回操作成功与否的状态码：

```sql
CREATE PROCEDURE UpdateStudentName(IN student_id INT, IN new_name VARCHAR(100))
BEGIN
    UPDATE students SET student_name = new_name WHERE student_id = student_id;
    SELECT ROW_COUNT();  -- 返回影响的行数
END;
```

在这个示例中，存储过程通过`SELECT ROW_COUNT();`返回一个整数值，表示更新操作影响了多少行。

#### **调用存储过程**

调用带有参数的存储过程时，必须提供正确数量的参数。对于输出参数，还需要处理返回值：

```sql
-- 定义变量来接收输出参数的值
SET @studentName = '';

-- 调用存储过程
CALL GetStudentName(1, @studentName);

-- 获取输出参数的值
SELECT @studentName;
```

#### **删除存储过程**
首先需要删除旧的存储过程，然后重新创建新的：
```sql
DROP PROCEDURE IF EXISTS GetStudentInfo;
```

























