# 2024年金三银四面试突击班MYSQL专题---索引

面试表达技巧：

1、回答任何问题的时候不要着急给出答案，先把面试官的问题听清楚

2、听到问题无论是自己会的问题还是不会的问题，先在脑海里想一两秒中，把要说的话做一个梳理

3、回答任何问题的时候要有条理，如果不知道怎么回答就按照总分或者总分总的方式来说

4、面试中遇到自己不会的问题不要慌，知道多少说多少，实在搞不定的大方承认自己没有接触过，不要顾左右而言他

----

### 1、谈一下你对于mysql索引的理解？（为什么mysql要选择B+树来存储索引）

​		mysql的索引选择B+树作为数据结构来进行存储，使用B+树的本质原因在于可以减少IO次数，提高查询的效率，简单点来说就是可以保证在树的高度不变的情况下可以存储更多的数据：

​		1、在MYSQL的数据库中，表的真实数据和索引数据都是存储在磁盘中，我们在进行数据读写的时候必然涉及到IO的问题，IO本质上来说是硬件方面的问题，但是我们在做索引设计的时候肯定要尽可能的考虑如何提高IO的效率，一般来说，提高IO效率主要有两个维度的考虑，减少IO次数和减少IO量，所以要遵循这两个原则

​		2、我们在进行数据存储的时候量是没办法预估的，当表的数据量非常大的时候，我们是没有办法一次性将所有的数据都读取到内存中，因此这个时候就要采用分治的思想，将数据进行分块读取，一旦分块读取的话，我们就要考虑设计合理的块大小

​		3、数据在磁盘存储的时候有时间局部性和空间局部性的特性，内存跟磁盘在进行数据交互的时候也不是需要啥就读取啥，而是会把相关的数据全部都加载到内存中，在进行加载的时候有一个最基本的逻辑单位称之为页，页的大小一般是4KB或者8KB，跟操作系统相关，我们在数据读取的时候一般会选择页的整数倍读取，比如innodb存储引擎每次读取16KB的大小。这个特性刚刚好跟我们上述说的分块读取的设计观念吻合起来，因此块的大小会选择页的整数倍，在MYSQL中一般都是16KB的大小，当然也可以通过参数来进行调整，比如innodb中的innodb_page_size这个参数，当然一般情况下我们不会调整这个参数的大小

​		4、当块的大小确定了之后我们就要考虑数据格式了，我们在使用索引的时候基本是根据某一个或者多个索引列的值来进行整行数据或者部分字段的读取，比如select * from table where id = 10这个语句就是根据id的值去检索整行记录，因此整体的数据格式可以设计为K-V格式的数据，K值就是索引列的值，V值的设计就需要进一步思考了。

​		5、正常情况下，当需要从磁盘中读取某一行记录的时候，需要知道一些信息才能够定位到数据，比如：文件名称，偏移量，数据长度，当知道这些信息的时候就可以定位到任何一行记录，所以我们可以将V的值设计为刚刚的几个字段，但是要考虑一件事，如果将刚刚的那些信息作为索引信息的话，那么在进行数据读取的时候，首先要打开一个文件，读取到刚刚的那几个字段信息，然后再根据那些信息找到对应的数据文件读取具体的行数据，如果打开一次文件就是一次IO的话，至少需要2次IO操作才可以读取，这个跟我们上面所说的减少IO次数有点相违背，所以最好的方式是在V中直接将行记录进行存储，那么在读取数据的时候就可以直接根据K值读取到行记录，只不过此时需要将数据跟索引绑定存储，在MYSQL中，innodb存储引擎就是这样存储的，数据文件和索引文件全部位于后缀名为ibd的文件中

​		6、当数据格式确定了之后我们就需要思考使用什么数据结构存储了。支持K-V格式的数据结构有很多，比如哈希表，二叉树，BST，AVL，红黑树，但是MYSQL最终选择了B+树，下面我们要对比下各个数据结构之间的区别：

​		（1）使用哈希表可以进行数据存储，但是哈希表本质是无序散列表，因此在进行范围查询的时候就必须要挨个进行数据的对比，此时的效率是比较低的，此外，哈希表会存在哈希碰撞或者哈希冲突的问题，需要设计性能优良的哈希算法，因此哈希表并不适用，但是在MYSQL中，MEMORY存储引擎支持哈希索引，innodb存储引擎支持自适应哈希，

​		（2）二叉树、BST、AVL、红黑树这几种树也可以支持K-V格式的数据存储，但是它们有一个共同的特点就是至多只有两个分支，那么在进行数据存储的时候，一个三层的树至多可以存储7个数据结果值，这个数据太少了，如果想要存储更多的数据，只能把树的高度变高，而树的高度变高之后又会导致IO的次数变多，影响查询效率，那么我们就要思考如何在保证树的高度不变的情况下存储更多的数据，上述的这些树存储数据少的原因在于分支至多只有两个，那么我们就要思考改变分支的结构了，因此有了B-树。

​		（3）使用B-树之后，存储如下图所示：在每一个数据块中包含三种类型的数据，分别是key值，行记录和指针，当需要进行数据读取的时候只要一层一层向下检索即可：

![1710734772703](1710734772703.png)

​		在上图中，如果要读取28这条记录的话，那么只需要读取磁盘块1,3,8就可以把数据取出来，如果一个磁盘块大小是16KB的话，那么读取48KB的数据就可以获取到要查询的记录，此时我们就要思考，这样3层的B-树存满的情况下可以存储多少条记录了，假设一条记录是1KB的大小，那么第一层至多存储15条记录，第二层至多存储16（第二层的子节点个数）*15（每个节点可以存储的行记录树）=240条记录，第三层至多存储16\*16\*15=3840条记录，那么三层的树存满的情况下最多存储15+240+3840=4095条记录，此时存储的数据量依然不是很大，如果想要存储更多的数据的话就只能将树变高，变成4层或者5层，那么此时又会增加IO的次数，会影响查询效率，那么此时就要思考为何只能存储这么少的数据，经过分析之后发现，data占用了大量的空间，因此考虑使用B+树进行数据的存储

​		（4）使用B+树之后，存储如下图所示：将所有的data全部放到了叶子节点中，非叶子节点中只存储key值和指针的值，在进行检索的时候可以从根节点向下检索，也可以在叶子节点中从前向后或者从后向前检索：

![1710735251445](1710735251445.png)

​		在上图中，所有的data都在叶子节点中，也就说第一层和第二层省掉了大量的data的存储空间，那么可以存储更多的数据，假设一个data还是1KB的大小，一个key加上一个指针的大小为10个字节，那么我们可以来计算下可以存储多少数据，第一层一个数据块，第二层16\*1024/10=1638个数据块，第三层1638*\*16\*1024\10=2683044个数据块，第三层的每个数据块可以存储16条记录，那么最后的总记录数为42928704条记录，可以发现跟B-树的存储不是一个量级，在相同树高的情况下，B+树可以存储更多的数据

​		因此MYSQL最终选择了B+树做为数据结构来存储，在刚刚上述的计算公式中，我们做了一个假设，key+指针一共占了10个字节，如果占用100个字节的话，那么整体的数据会缩小2个量级，因此在回答索引的树的高度的时候不要说3层或者4层，给一个标准说法：一般情况下，3-4层的B+树足以支撑千万级别的数据量存储。

### 2、索引有哪些分类？

​		索引的分类要按照不同的角度去进行分类：

​		1、从数据结构的角度可以分为B+树索引、哈希索引、FULLTEXT索引、R-Tree索引（用于对GIS数据创建SPATIAL索引）

​		2、从物理存储角度可以分为聚簇索引和非聚簇索引

​		3、从逻辑角度可以分为主键索引、普通索引、唯一索引、组合索引

### 3、聚簇索引与非聚簇索引

![1710737581418](1710737581418.png)

​		在MYSQL的innodb存储引擎中，数据在进行插入的时候必须要跟某一个索引列绑定在一起进行存储，如果有主键，那么选择主键，如果没有主键，那么选择唯一键，如果没有唯一键，那么系统会生成一个6字节的rowid进行存储，因此：

​		跟数据绑定存储的索引称之为聚簇索引

​		没有跟数据绑定存储的索引称之为非聚簇索引

​		一张表中只有一个聚簇索引，其他非聚簇索引的叶子节点中存储的值为聚簇索引的列值

### 4、回表、索引覆盖、最左匹配原则、索引下推

#### 		（1）回表

​		回表表示使用非聚簇索引时，数据库引擎会先根据普通索引找到匹配的行，然后根据叶子节点中存储的聚簇索引的值去聚簇索引的索引树中查找整行记录的过程。例如：

​		有一张表有如下字段：id，name，age，gender，address，其中id是主键，name是普通索引

​		那么要进行如下SQL语句的查询：

​		select * from table where name = 'zhangsan';

​		上述SQL语句的查找过程是：先根据name的值去name的索引树上进行检索，找到匹配的记录之后取出id的值，然后再根据id的值去id的B+树上检索整行的记录，在这个过程中，查找了两棵树，多进行了棵树的IO，因此效率比较低，在生产环境中应该尽量避免回表

#### 		（2）索引覆盖

​		索引覆盖是指一个索引包含了查询所需要的所有数据，从而在查询中无需回表从原表中获取数据

​		假设有一张表，表中有以下字段：id，name，age，gender，address，其中id是主键，name是普通索引

​		那么要进行如下SQL语句的查询：

​		select id,name from table where name = 'zhangsan';

​		查找过程如下：在name的索引树上包含了要查询的所有字段，所以直接通过name字段去name的B+树上检索对应的记录即可，不需要找到id之后再去id的B+树上检索数据

​		索引覆盖可以提高查询的性能，所以在生产环境做SQL优化的时候，可以考虑索引覆盖

#### 		（3）最左匹配原则

最左匹配原则主要适用于组合索引，指的是多个列值进行匹配的时候要严格遵循从左到右的顺序，否则会导致索引失效

```sql
假设有一张表，表中有以下字段：id，name，age，gender，address
id是主键，(name,age)是组合索引

1、Select * from table where name = 'zhangsan' and age = 10;
2、Select * from table where name = 'zhangsan';
3、Select * from table where age = 10;
4、Select * from table where age = 10 and name = 'zhangsan';

上述的四条语句中，1,2,4都可以用到组合索引，3用不到，但是很多同学会有疑问，为什么第四条会用到，明明不符合最左匹配原则的顺序，这里需要注意，如果把第四条SQL语句的条件换一下顺序，会影响最终的查询结果吗？答案是不会的，所以mysql中的优化器会进行优化，调整条件的顺序
```

#### 		（4）索引下推

​		ICP是针对mysql使用索引从表中检索行的情况进行优化，如果没有ICP，那么存储引擎会根据索引来定位到记录，然后将结果返回给mysql的server，然后在server上对where条件进行筛选。在启用ICP之后，如果where条件的一部分可以通过使用索引中的列来求值，那么mysql会把这部分的where条件筛选下推到存储引擎中。

​		使用索引下推的时候会有以下的条件：

​		1、当需要访问完整的行记录时，ICP用于range、ref、eq_ref和ref_or_null访问方法

​		2、ICP可以用于innodb和myisam表，包括分区的innodb表和myisam表

​		3、对于innodb表，ICP仅用于二级索引。ICP的目标是减少整行读取的次数，从而减少IO操作

​		4、在虚拟列上创建的二级索引不支持ICP

​		5、引用子查询的条件不能下推

​		6、引用存储函数的条件不能下推

​		7、触发器条件不能下推

​		8、不能将条件下推到包含对系统变量引用的派生表中

​		假设有一张表，表中有以下字段：id，name，age，gender，address，其中id是主键，(name,age)是组合索引

​		select * from table where name = 'zhangsan' and age = 10;

​		没有索引下推：mysql执行这条SQL语句的时候，会首先根据name的值去存储引擎中拉取数据，然后将数据返回到mysql server，然后在server层对age进行条件过滤，把符合条件的结果返回给客户端
​		有索引下推：mysql执行这条SQL语句的时候，会直接根据name和age的值去存储引擎中拉取数据，而无需在server层对数据进行条件过滤

​		所谓的下推指的是将条件的筛选从server层下推到存储引擎层

​		可以通过optizizer_switch中的index_condition_pushdown条件来是否开启，默认是开启的

```sql
SET optimizer_switch = 'index_condition_pushdown=off';
SET optimizer_switch = 'index_condition_pushdown=on';
```

### 5、如何设计性能优良的索引？

​		1、索引列占用的空间越小越好

​		2、选择索引列的时候尽量选择离散度高的列作为索引列，离散度的计算公式count(distinct(column_name)) / count(*)，这个值越大，那么越适合做索引

​		3、在where后的order by字段上添加索引，如果有where条件，是跟where条件一起创建，如果没有就只是order by

​		4、在join on的条件字段上添加索引

​		5、索引的个数不要过多，会增加索引的维护成本

​		6、频繁更新的字段，不要创建索引，会增加索引的维护成本

​		7、随机无序的值，不建议作为主键索引，如身份证号，UUID

​		8、索引列在设计的时候最好不为NULL

​		9、可以使用列前缀作为索引列

### 6、什么情况下会造成索引失效？

​		1、索引列上使用函数（replace\SUBSTR\CONCAT\sum count avg）、表达式

​		2、数据类型不匹配，当查询条件的数据类型和索引字段的类型不匹配

​		3、like 条件中前面带% 

​		4、在组合索引中，不满足最左匹配原则

​		5、使用is not null

​		6、mysql的优化器在进行分析的时候发现全表扫描比使用索引快的时候

​		7、使用or关键字会导致索引失效

### 7、主键为什么建议选择自增主键？

​		如果选择自增主键的话，每次新增数据时，都是以追加的形式进行存储，在本页索引写满之后，只需申请一个新页继续写入即可，不会产生页分裂问题

​		如果说采用业务字段作为主键的话，新增数据不一定是顺序的，需要挪动数据，页快满时还要去分裂页，保持索引的有序性，造成写数据成本较高

### 8、如何查看SQL语句是否使用索引

​		通过执行计划可以判断查询中是否用到了索引，以便进行SQL优化。

​		explain语句提供了mysql如何执行语句的信息，explain可以跟select、delete、insert、replace、update语句一起工作

| Column                                                       | JSON Name       | Meaning                                        |
| :----------------------------------------------------------- | :-------------- | :--------------------------------------------- |
| [`id`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_id) | `select_id`     | The `SELECT` identifier                        |
| [`select_type`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_select_type) | None            | The `SELECT` type                              |
| [`table`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_table) | `table_name`    | The table for the output row                   |
| [`partitions`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_partitions) | `partitions`    | The matching partitions                        |
| [`type`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_type) | `access_type`   | The join type                                  |
| [`possible_keys`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_possible_keys) | `possible_keys` | The possible indexes to choose                 |
| [`key`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_key) | `key`           | The index actually chosen                      |
| [`key_len`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_key_len) | `key_length`    | The length of the chosen key                   |
| [`ref`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_ref) | `ref`           | The columns compared to the index              |
| [`rows`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_rows) | `rows`          | Estimate of rows to be examined                |
| [`filtered`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_filtered) | `filtered`      | Percentage of rows filtered by table condition |
| [`Extra`](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html#explain_extra) | None            | Additional information                         |

#### id

select查询的序列号，包含一组数字，表示查询中执行select子句或者操作表的顺序

id号分为三种情况：

​		1、如果id相同，那么执行顺序从上到下

```sql
explain select * from emp e join dept d on e.deptno = d.deptno join salgrade sg on e.sal between sg.losal and sg.hisal;
```

​		2、如果id不同，如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行

```sql
explain select * from emp where ename not in (select ename from emp where ename like '%S%') ;
```

​		3、id相同和不同的，同时存在：相同的可以认为是一组，从上往下顺序执行，在所有组中，id值越大，优先级越高，越先执行

```sql
explain Select dept.*,person_num,avg_sal from dept,(select count(*) person_num,avg(sal) avg_sal,deptno from emp group by deptno) t where dept.deptno = t.deptno ;
```

#### **select_type**

主要用来分辨查询的类型，是普通查询还是联合查询还是子查询

| `select_type` Value                                          | JSON Name                    | Meaning                                                      |
| :----------------------------------------------------------- | :--------------------------- | :----------------------------------------------------------- |
| `SIMPLE`                                                     | None                         | Simple [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) (not using [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) or subqueries) |
| `PRIMARY`                                                    | None                         | Outermost [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) |
| [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) | None                         | Second or later [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) statement in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) |
| `DEPENDENT UNION`                                            | `dependent` (`true`)         | Second or later [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) statement in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html), dependent on outer query |
| `UNION RESULT`                                               | `union_result`               | Result of a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html). |
| [`SUBQUERY`](https://dev.mysql.com/doc/refman/8.0/en/optimizer-hints.html#optimizer-hints-subquery) | None                         | First [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) in subquery |
| `DEPENDENT SUBQUERY`                                         | `dependent` (`true`)         | First [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) in subquery, dependent on outer query |
| `DERIVED`                                                    | None                         | Derived table                                                |
| `DEPENDENT DERIVED`                                          | `dependent` (`true`)         | Derived table dependent on another table                     |
| `MATERIALIZED`                                               | `materialized_from_subquery` | Materialized subquery                                        |
| `UNCACHEABLE SUBQUERY`                                       | `cacheable` (`false`)        | A subquery for which the result cannot be cached and must be re-evaluated for each row of the outer query |
| `UNCACHEABLE UNION`                                          | `cacheable` (`false`)        | The second or later select in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) that belongs to an uncacheable subquery (see `UNCACHEABLE SUBQUERY`) |

```sql
--simple:简单的查询，不包含子查询和union
explain select * from emp;

--primary:查询中最外层的查询，如果查询中有子查询，则最外层的查询被标记为primary
explain select * from emp where ename not in (select ename from emp where ename like '%S%') ;

--union:若第二个select出现在union之后，则被标记为union
explain select * from emp where deptno = 10 union select * from emp where sal >2000;

--dependent union:跟union类似，此处的depentent表示union或union all联合而成的结果会受外部表影响
explain select * from emp e where e.empno  in ( select empno from emp where deptno = 10 union select empno from emp where sal >2000)

--union result:表示一个union的结果集作为一个单独的表返回，这通常发生在union操作之后，并且可能跟其他表进行join操作
explain select * from emp where deptno = 10 union select * from emp where sal >2000;

--subquery:在查询中作为另一个查询的子查询的查询，例如，在 `SELECT ... WHERE column IN (SELECT ...)` 结构中的子查询。
explain select * from emp where sal > (select avg(sal) from emp) ;

--dependent subquery:与subquery类似，但是这个查询依赖于外部查询的某些部分。
explain select e.empno,e.ename,e.sal from emp e where e.sal < (select e2.sal from emp e2 where e2.empno = e.mgr)

--DERIVED: 出现在from子句中的子查询，MySQL会为这个子查询生成一个临时表。这个值表示该查询是为派生表生成的。
explain select t.job from (select min(sal) min_sal,job from emp group by job) t where t.min_sal > 2500 ;
--dependent derived：与derived类似，但是这个查询依赖于外部查询的某些部分：未找到案例

--materialized：表示该子查询的结果被物化（即存储在临时表中），以供稍后的join使用，这种类型的子查询在执行时比常规子查询要慢，
EXPLAIN 
select * from emp where deptno in (select deptno from (select min(sal) min_sal,deptno from emp group by deptno) a where min_sal < '2000') ;

--UNCACHEABLE SUBQUERY：一个子查询的结果不能被缓存，因此每次都会重新计算：未找到案例
--uncacheable union:一个union的结果不能被缓存，因此每次都会重新计算：未找到案例
```

#### table

对应行正在访问哪一个表，表名或者别名，可能是临时表或者union合并结果集
		1、如果是具体的表名，则表明从实际的物理表中获取数据，当然也可以是表的别名

​		2、表名是derivedN的形式，表示使用了id为N的查询产生的衍生表

​		3、当有union result的时候，表名是union n1,n2等的形式，n1,n2表示参与union的id

#### **type**

type显示的是访问类型，访问类型表示我是以何种方式去访问我们的数据，最容易想的是全表扫描，直接暴力的遍历一张表去寻找需要的数据，效率非常低下，访问的类型有很多，效率从最好到最坏依次是：

system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL 

一般情况下，得保证查询至少达到range级别，最好能达到ref

```sql
--all:全表扫描，一般情况下出现这样的sql语句而且数据量比较大的话那么就需要进行优化。
explain select * from emp;

--index：全索引扫描这个比all的效率要好，主要有两种情况，一种是当前的查询时覆盖索引，即我们需要的数据在索引中就可以索取，或者是使用了索引进行排序，这样就避免数据的重排序
explain  select empno from emp;

--range：表示利用索引查询的时候限制了范围，在指定范围内进行查询，这样避免了index的全索引扫描，适用的操作符： =, <>, >, >=, <, <=, IS NULL, BETWEEN, LIKE, or IN() 
explain select * from emp where empno between 7000 and 7500;

--index_subquery：跟unique_subquery类型，使用的是辅助索引
SET optimizer_switch='materialization=off';
EXPLAIN select * from emp where ename not in (select dname from dept where dname like '%SALES' );
SET optimizer_switch='materialization=on';


--unique_subquery:子查询的结果由聚簇索引或者唯一索引覆盖
--dept表的deptno字段有主键
SET optimizer_switch='materialization=off';
EXPLAIN select * from emp where deptno not in (select deptno from dept where deptno >20 );
SET optimizer_switch='materialization=on';
 
--index_merge：索引合并，在where条件中使用不同的索引字段
--ename，deptno都创建索引
explain select * from emp where ename='SMITH' or deptno = 10;

--ref_or_null：跟ref类似，在ref的查询基础上，加一个null值的条件查询
explain select * from emp  where ename = 'SMITH' or ename is null;

--ref：使用了非聚集索引进行数据的查找
alter table emp add index idx_name(ename);
explain select * from emp  where ename = 'SMITH';

--eq_ref ：使用唯一性索引进行数据查找
explain select * from emp e,emp e2 where e.empno = e2.empno;

--const：这个表至多有一个匹配行，
explain select * from emp where empno = 7369;
 
--system：表只有一行记录（等于系统表），这是const类型的特例，平时不会出现
```

####  **possible_keys** 

​        显示可能应用在这张表中的索引，一个或多个，查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询实际使用

```sql
explain select * from emp where ename = 'SIMTH' and deptno = 10;
```

#### **key**

​		实际使用的索引，如果为null，则没有使用索引，查询中若使用了覆盖索引，则该索引和查询的select字段重叠。

```sql
explain select * from emp where ename = 'SIMTH' and deptno = 10;
```

#### **key_len**

表示索引中使用的字节数，可以通过key_len计算查询中使用的索引长度，在不损失精度的情况下长度越短越好。

```sql
explain select * from emp where ename = 'SIMTH' and deptno = 10;
```

#### **ref**

显示了那些列或常量被用于查找索引列，这对于非唯一索引查找有效

```sql
explain select * from emp,dept where emp.deptno = dept.deptno and emp.deptno = 10;
```

#### **rows**

根据表的统计信息及索引使用情况，大致估算出找出所需记录需要读取的行数，此参数很重要，直接反应的sql找了多少数据，在完成目的的情况下越少越好

```sql
explain select * from emp;
```

####  filtered

表示返回行的预估百分比，它显示了哪些行被过滤掉了，最大的值为100，这意味这没有对行进行筛选，从100开始递减的值表示过滤量在增加，rows表示预估的行数，rows*filtered表示与下表连接的行数

#### **extra**

提供查询的额外信息

```sql
--using filesort:说明mysql无法利用索引进行排序，只能利用排序算法进行排序，会消耗额外的位置
explain select * from emp order by sal;

--using temporary:建立临时表来保存中间结果，查询完成之后把临时表删除
explain select ename,count(*) from emp where deptno = 10 group by ename;

--using index:这个表示当前的查询时覆盖索引的，直接从索引中读取数据，而不用访问数据表。如果同时出现using where 表名索引被用来执行索引键值的查找，如果没有，表面索引被用来读取数据，而不是真的查找
explain select deptno,count(*) from emp group by deptno limit 10;

--using where:通常是进行全表或者全索引扫描后再用where子句完成结果过滤，需要添加索引
explain select * from emp where job='SMITH';

--using join buffer:使用连接缓存
explain select * from t3 join t2 on t3.c1 = t2.c1;

--impossible where：where语句的结果总是false
explain select * from emp where 1=0
```

### 