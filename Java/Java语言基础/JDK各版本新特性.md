JAVA各版本新特性专题课程



## 课程介绍

> 主要目的:

旨在介绍各个版本的JDK的新特性,带学生了解,或者说是预习将来可能会再工作中使用到的JDK,主要侧重语法和API层面的讲解,以免在工作中遇见新的JDK时会陌生

> 授课特点

1 着重讲解语法层次和API层次的改变,这也是和程序员关联性最强的改变,因为直接影响着我们的变化

2 深入浅出,其他对编码影响不大的新特性介绍性讲解,或者提示要点

3 要求学生有JAVASE基础即可学习,部分涉及到JVM,GC等架构层次的知识只在这里做提示和介绍,因为仅凭JAVASE的知识学习起来还是相当困难的,在架构和JAVA进阶课程中会有详细的讲解,可以咨询马士兵教育相关课程深入学习

4 JAVA8已经使用多年,其中的Lambda和Stream等特性已经不能算是新特性,而是基本功,所以这里不做讲解,新特性从JAVA9开始讲解

> JDK版本变化特征

JAVA8 及之前,是版本都是特性驱动的版本更新,就是有重大的特性产生,然后进行更新

JAVA9开始,JDK开始以时间为驱动进行更新,以半年为周期,到时即更新,三年出一个长期支持版,其他都是短暂的版本

目前的长期支持版有 JAVA8  JAVA11 和即将出现的JAVA17,这些版本大家注意在将来的工作中使用的概率更高,也就是说我们将来研发,使用JAVA11 ,然后使用JAVA17 是必然的,只是一个时间的问题





![1630658353281](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630658353281.png)

新的长期支持版每三年发布一次,根据后续的发布计划,下一个长期支持的版本JAVA17将于2021年发布

## 环境准备

> 下载JDK,可以通过 https://www.injdk.cn/ 去下载各种不同版本的JDK, 因为JDK是向下兼容的,所以我们使用一个较新的JDK,就可以去测试和学习从9-最新所有版本的新特征了,我们这里以安装openjdk16为例,下载安装JDK16的压缩包

![1629961731077](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629961731077.png)

> 下载后可以解压到我们磁盘的任意位置,我这里的位置是 c:/program files/java

![1629961777964](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629961777964.png)

> 在使用idea创建项目时,可以选择该JDK

![1629961866979](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629961866979.png)

> 添加JDK

![1629961904463](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629961904463.png)

> 当然,也可以在项目创建完毕之后,更换JDK为16

# JAVA9

## 概述

 ​	经过4次推迟，历经曲折的Java9最终在2017年9月21日发布。因为里面加入的模块化系统，在最初设想的时候并没有想过那么复杂，花费的时间超出预估时间。距离java8大约三年时间。
  ​	Java 9提供了超过150项新功能特性，包括备受期待的模块化系统、可交互的REPL工具: jshell, JDK编译工具，语法层面的改变：Java公共API和私有代码，以及安全增强、扩展提升、性能管理改善等。可以说Java 9是一个庞大的系统工程，完全做了一个整体改变。
  ​	 但是这个巨大改变的功劳，都给了java11了，**目前oracle对8,11都长期支持，9,10不支持了**，只能从历史版本（http://jdk.java.net/）中下载，Java 11 将会获得 Oracle 提供的长期支持服务，直至2026年9月。
  ​	从Java9这个版本开始，Java 的计划发布周期是6个月，下一个Java的主版本将于2018年3月发布，命名为Java18.3(java10)， 紧接着再过六个月将发布Java18.9(java11).
  ​	这意味着Java的更新**从传统的以特性驱动的发布周期，转变为以时间驱动的(6个月为周期)发布模式**（更快的时间周期,oracle的理念就是小步快跑，快速迭代，像IBM（DB2数据库，保守型内部测试才投入市场）），并逐步的将Oracle JDK原商业特性进行开源。针对企业客户的需求，Oracle将以三年为周期发布长期支持版本(long term support)



> * 重大改变：
>   1 模块化系统
>   2 jShell命令
> * 语法层面改变：
>   1 接口私有方法
>   2 钻石操作符（泛型<>）使用升级
>   3 语法改进：try语句
> * API层面变化：
>   1 String存储结构变更 （从char类型数组变为byte类型数组）
>   2 集合特性：of()
>   3 增强的Stream API(不断迭代，要重视这个Stream)
>   4 全新的HTTP客户端API（不仅支持HTTP1.1而且还支持HTTP2：从5 以前的HttpUrlConnection-变为现在的HttpClient）
>   6 Deprecated的 API
> * 其它变化：
>   1 javadoc的HTML5支持
>   2 Js引擎升级：Nashorn（9对8升级了，但是11里又没有了）
>   3 java的动态编译器
>   4 多版本兼容jar包

 官方提供的新特性的列表

 https://docs.oracle.com/javase/9/whatsnew/toc.htm#JSNEW-GUID-C23AFD78-C777-460B-8ACE-58BE5EA681F6

 openJDK  可参考源码

 http://openjdk.java.net/projects/jdk9/

 在线 OracleJDK Documentation 在线文档

 https://docs.oracle.com/javase/9/



>  JDK和JRE的目录的改变



![1629877485595](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629877485595.png)

> bin 包含命令行开发和调试工具 如javac jar  javadoc 
>
> include 包含编译本地代码时使用的c/c++头部文件
>
> lib 包含JDK工具的几个jar和其他类型的文件,他有一个tools.jar文件,其中含javac编译器的java类
>
> jre/bin目录 包含基本指令,如java指令,在windows平台上,它包含系统的运行时动态链接
>
> jre/lib包含用户可编辑的配置文件,如properties和.policy文件,包含几个jar文件,rt.jar文件包含运行时的java类和资源文件



![1629877501349](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629877501349.png)

> bin 包含所有指令,在windows平台上,他继续包含系统的运行时动态链接
>
> conf目录 包含用户可编辑的配置文件,例如之前位于jre/lib目录中的.properties和policy
>
> includes 包含在以前编译本地代码时使用c/c++头文件,他只存在于JDK中
>
> jmods  包含JMOD格式的平台模块,创建自定义运行时映像需要他,它只存在于jdk中
>
> legal 法律声明
>
> lib 包含非windows平台上的动态链接本地库,其子目录和文件不应由开发人员直接编译或使用
>
> 
>
> 从9开始以后的JDK目录结构都是如此





## 一 语法层次的改变

### 1_钻石操作符号语法升级

>钻石操作符,就是我们泛型使用的符号<> 
>
>JAVA8 中,匿名内部类不能使用钻石操作符,如下代码在JAVA8 中是报错的,匿名内部类这里不支持泛型推断,重写的方法不明确泛型



![1629966490626](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629966490626.png)

> 这里匿名内部类中的<>号里必须要和前面的声明保持一致,不能空着不写,这样重写的方法就根据匿名内部类的泛型

![1629966550374](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629966550374.png)

> 但是这种写法在JAVA9 中就允许了

![1629966634077](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629966634077.png)

> 而且在JAVA9中,匿名内部类的语法不仅仅可以用于接口和抽象类,普通类也可以通过匿名内部类写法,在某个实例上完成对某个方法的重写

``` java

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Demo1 {
    public static void main(String[] args) {
        /*
        * 匿名内部类仅仅在接口和抽象类上使用,作为一种快速的实现方式
        * JAVA9中,普通类也可以借助这种语法形式实现对方法的快速临时的重写
        * */
        Person<String> person=new Person<>(){
            @Override
            public void eat(String s) {
                super.eat(s);
            }
        };
        person.eat("包子");

    }
}
class Person<T>{
    public void eat(T t){
        System.out.println("Person eat");
    }
}
```





### 2_try结构语法升级

> 普通的try catch finally语句  要释放的资源可以放到finally语句块中

``` java
        InputStreamReader reader =null;
        try{
            reader =new InputStreamReader(System.in);
            int read = reader.read();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            // 这里可以释放资源
            if(null != reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
```





> JAVA 8中已经对try语法进行了升级,可以将要释放的资源放到try后面的小括号中,这样就不用通过finally语句块释放资源了,**但是要求执行后必须关闭的资源一定要放在try子句中进行初始化,否则编译不通过.** 下面的案例中,reader必须放在try后面的小括号中进行初始化

``` java
        try( InputStreamReader reader=new InputStreamReader(System.in) ){
            int read = reader.read();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
```





> JAVA 9 资源的关闭操作,我们可以在try子句中使用已经初始化的资源但是此时的资源必须 是final修饰的,final可以省略不写

``` java

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Demo2 {
    public void testx(){
        // 传统的 try catch finally语句块
        InputStreamReader isr =null;
        try{
            isr =new InputStreamReader(new FileInputStream("d:/aaa.txt"));
            isr.read();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != isr){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // JAVA8 try语法升级
    public void testa(){
        // JAVA8 try catch finally语句块
        try( InputStreamReader isr =new InputStreamReader(new FileInputStream("d:/aaa.txt"));){
            isr.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // JAVA9 try语法升级
    public void testb() throws FileNotFoundException {
        // JAVA9 try catch finally语句块
        InputStreamReader isr =new InputStreamReader(new FileInputStream("d:/aaa.txt"));
        OutputStreamWriter isw =new OutputStreamWriter(new FileOutputStream("d:/aaa.txt"));
        try( isr; isw){
            isr.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

```





### 3_下划线命名标识符的使用限制

> 标识符命名组成：字母，数字，下划线，美元符
>
> JAVA8 中,可以使用一个 _ 作为标识符的命名

``` java
String  _  ="宇智波赵四儿";
```

> JAVA9 中,就不可以使用一个_ 作为标识符的命名了,不通过编译,但是标识符中仍然可以使用_,必须配合其他内容

``` java
String stu_name="旋涡刘能";
```

> 小细节,注意一下即可,一般也没人直接单独用一个_ 作为标识符的命名





## 二 API层次的改变



### 1_接口中的私有方法



> JAVA7 中,接口只能有抽象方法
>
> JAVA8 中,接口中static(静态不可重写)和default(可以重写)修饰的方法可以拥有方法体
>
> JAVA9 中,接口中可以使用private修饰方法,并拥有方法体,但是接口中的成员变量仍然不能用private修饰
>
> 感觉: 接口中的代码越来越靠近抽象类,但是仍然是支持多继承的

![1629968793799](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629968793799.png)

``` java
/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Demo4 {
    // 接口,是一种规范和要求
    // 实现多继承
}
// java7 接口中的方法必须都是抽象的
interface  Inter1{
    void methoda();
}
// java8接口可以定义static/default修饰的非抽象方法
interface  Inter2{
    void methoda();
    static void methodB(){

    }
    default void methodC(){

    }
}
// java9 允许定义私有的非抽象方法
interface  Inter3{
    void methoda();
    static void methodB(){

    }
    default void methodC(){
        methodD();
    }
    private void methodD(){

    }
}
```





### 2_String底层存储结构的变更

> JAVA8 中的String源码
>
> String类内部维护的是一个final修饰的私有char数组,说明String的底层是通过char数组存储字符串的

![1630032137816](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630032137816.png)

>JAVA9 中String的源码
>
>String类内部维护的是一个final修饰的私有byte数组,说明String的底层是通过byte数组存储字符串的



![1630032189562](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630032189562.png)



> 这么做的原因:大多数String对象只包含latin-1字符。 这样的字符只需要一个字节的存储空间，因此这样的String对象的内部字符数组中有一半的空间没有使用 , 我们建议将String类的内部表示形式从UTF-16字符数组更改为一个字节数组加上一个结束编码标志字段   





### 3_Stream新增4个API



>  JAVA9 中,Stream接口添加了4个新方法,takeWhile,dropWhile,ofNullable,还有一个iterate 方法的新重载方法,可以通过一个Predicate来指定什么时候结束迭代

``` java 
 /**
     * 测试Stream新增takeWhile方法
     * takeWhile  从流中的头开始取元素,直到不满足条件为止
     */
    public static void testTakeWhile(){
         List<Integer> list = Arrays.asList(1, 89, 63, 45, 72, 65, 41, 65, 82, 35, 95, 100);
        // 从头开始取所有奇数,直到遇见一个偶数为止
        list.stream().takeWhile(e-> e%2==1).forEach(System.out::println);

    }

    /**
     * 测试Stream新增dropWhile方法
     * dropWhile  从头开始删除满足条件的数据,直到遇见第一个不满足的位置,并保留剩余元素
     */
    public static void testDropWhile(){
        List<Integer> list = Arrays.asList(2, 86, 63, 45, 72, 65, 41, 65, 82, 35, 95, 100);
        // 删除流开头所有的偶数,直到遇见奇数为止
        list.stream().dropWhile(e-> e%2==0 ).forEach(System.out::println);

    }

    /**
     * 测试Stream新增ofNullable方法
     * ofNullable 允许创建Stream流时,只放入一个null
     */
    public static void testOfNullable(){
        // of方法获取流 ,允许元素中有多个null值
        Stream<Integer> stream1 = Stream.of(10, 20, 30, null);
        // 如果元素中只有一个null,是不允许的
        Stream<Integer> stream2 = Stream.of(null);
        // JAVA9中,如果元素为null,返回的是一个空Stream,如果不为null,返回一个只有一个元素的Stream
        Stream<Integer> stream3 = Stream.ofNullable(null);
    }

    /**
     * 测试Stream新增iterate方法
     * iterate指定种子数,指定条件和迭代方式来获取流
     */
    public static void testNewIterate(){
        //JAVA8通过 generate方法获取一个Stream
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
        //JAVA8 通过iterate获取一个Stream
        Stream.iterate(0,t-> t+2).limit(10).forEach(System.out::println);
        //JAVA9通过重载iterate获取Stream
        Stream.iterate(0,t -> t<10,t-> t+1).forEach(System.out::println);
    }
```



> 除了Stream本身的扩展,Optional和Stream之间的结合也得到了改进,现在可以通过Optional的新方法将一个Optional对象转换为一个Stream对象(可能是空的)

```  java
    /**
     * Optional类新增Stream方法,可以将一个Optional转换为Stream
     */
    public static void testOptionalStream(){
        List<Integer> list =new ArrayList<>();
        Collections.addAll(list,10,5,45,95,36,85,47);
        Optional<List<Integer>> optional=Optional.ofNullable(list);

        // 通过optional的Stream方法获取一个Stream
        Stream<List<Integer>> stream = optional.stream();
        // 以为内部的每个元素也是一个List,通过flatMap方法,将内部的List转换为Stream后再放入一个大Stream
        stream.flatMap(x->x.stream()).forEach(System.out::println);

    }
```



### 4_InputStream新增transferTo方法



> InputStream新增transferTo方法,可以用来将数据直接传输到OutpuStream,这是在处理原始数据时非常常见的一种方法

``` java
        InputStream inputStream =new FileInputStream("d:/aaa.txt");
        OutputStream outputStream=new FileOutputStream("d:/bbb.txt");
        try (inputStream;outputStream){
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
```



### 5_只读集合的创建



> JAVA8 要创建一个只读,不可改变的集合,必须构造和分配他,然后添加元素,然后再包装成一个不可修的集合

``` java
        List<String> list= new ArrayList<>();
        list.add("Tom");
        list.add("Jerry");
        list.add("Mark");
        list.add("Jhon");
        list = Collections.unmodifiableList(list);
        System.out.println(list);
```

> 放入数据后,然后要通过unmodifiableList才能让集合变为只读集合,不能表达为单个的表达式
>
> JAVA9 通过集合工厂方法,创建一个只读集合,只要通过新增的of方法即可完成创建

``` java
        List<String> list= List.of("TOM","Jerry","Mark","Ben");
        System.out.println(list);
```



![1630035230203](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630035230203.png)

> 上面是List接口的of方法, 同样的,Set接口和Map接口下也新增了of方法,也是返回一个只读集合

## 三 其他变化

### 1_JAVA9 模块化

 谈到Java9大家往往第一个想到的就是Jigsaw项目（后改名为Modularity）。众所周知，Java已经发展超过20年(95年最初发布)，Java和相关生态在不断丰富的同时也越来越暴露出一些问题:

 1Java运行环境的膨胀和臃肿。**每次JVM启动的时候，至少会有30~ 60MB的内存加载，主要原因是JVM需要加载rt.jar**,不管其中的类是否被classloader加载，第一步整个jar都会被JVM加载到内存当中去(而模块化可以根据模块的需要加载程序运行需要的class)

 2当代码库越来越大，创建复杂，盘根错节的“意大利面条式代码”的几率呈指数级的增长。不同版本的类库交叉依赖导致让人头疼的问题，这些都阻碍了Java 开发和运行效率的提升。

 3很难真正地对代码进行封装,而系统并没有对不同部分(也就是JAR文件)之间的依赖关系有个明确的概念。每一个公共类都可以被类路径之下任何其它的公共类所访问到，这样就会导致无意中使用了并不想被公开访问的API.



 本质上讲，模块化，就是在package外面包裹一层->>>说白了项目下有众多 模块 
  进行项目管理，管理各个模块，比如一个电商项目  下面有支付模块 购物模块，，，模块跟模块之间相互调用，这样代码就更安全，可以指定哪些暴露 哪些隐藏！



模块之间的可访问性是所使用的模块和使用模块之间的双向协议：模块明确地使其公共类型可供其他模块使用，而且使用这些公共类型的模块明确声明对第一个模块的依赖，模块中所有未导出的软件包都是模块的私有的，他们不能在模块之外使用.之前做不到，现在可以考虑这个事了



> 在jdk9中,项目模块之间的依赖

在JDK9 先准备一个项目,普通java项目即可,然后再项目下准备两个模块,名字为jdk9module1和jdk9module2,如下图

![1629959034495](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629959034495.png)

> jdk9module1中添加一些类

![1629959219122](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629959219122.png)

> 如何在jdk9module2中使用这个类? 或者说是使用模块1中的类,第一步,在两个模块的src下创建各自的module-info.java

![1629959400483](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629959400483.png)



> 创建完毕后,结构如下

![1629959441838](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629959441838.png)

> 在jdk9module1的module-info.java文件中,设置哪些包可以向外暴露

![1629959666167](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629959666167.png)

> 在jdk9module2的module-info.java中引入模块1

![1629959723837](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629959723837.png)

> 但是发现报错了,原因是,我们要把模块1添加为模块2的运行环境,具体操作如下
>
> project structure > modules > jdk9module2 >dependencies  >>+module lib > jdk9module1 > apply >>ok

![引入模块依赖](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/%E5%BC%95%E5%85%A5%E6%A8%A1%E5%9D%97%E4%BE%9D%E8%B5%96.gif)

> 这个是时候,我们在模块2中就可以使用模块1 中的类了

![使用模块一中的代码](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/%E4%BD%BF%E7%94%A8%E6%A8%A1%E5%9D%97%E4%B8%80%E4%B8%AD%E7%9A%84%E4%BB%A3%E7%A0%81.gif)



### 2_可交互的REPL工具:jshell

 像Python和Scala 之类的语言早就有交互式编程环境REPL (read -evaluate - print -loop)了，以交互式的方式对语句和表达式进行求值。开发者只需要输入一些代码，就可以在编译前获得对程序的反馈。而之前的Java 版本要想执行代码，必须创建文件、声明类、提供测试方法方可实现。

> 要想实现REPL，需要一个命令：JShell命令（linux中是shell命令，java中的shell命令）

![1629877864498](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629877864498.png)



> 将环境变量配置为java9，就可以在控制命令台使用jshell命令了：如果电脑上安装了其他版本的JDK,环境变量也是其他版本,大家可以在dos上通过cd 切换到指定版本的bin目录下,就可以使用该版本的jshell了

![1629962311415](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629962311415.png)



> 案例：简单输出语句：



![1629877895300](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629877895300.png)



> 案例：变量定义

![1629877926195](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629877926195.png)

> 案例：方法定义和调用：

![1629877952559](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629877952559.png)

![1629877968367](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629877968367.png)

> 导包：

![1629877991221](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629877991221.png)

> 其实jshell中有默认的导入一些包（除了java.lang之外，以下包也可以直接用），可以直接使用不用导包，查看有哪些：

![1629878023330](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629878023330.png)

> 常用命令：

![1629878047871](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629878047871.png)

![1629878065195](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629878065195.png)

> 可以将某些常用命令持久化在硬盘上：

![1629878182887](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629878182887.png)

![1629878211555](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629878211555.png)

> jshell不用编译时异常处理：

![1629878234271](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629878234271.png)

> 退出jshell

![1629878258920](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1629878258920.png)



# JAVA10

## 概述

> 2018年3月21日, Oracle官方宣布JAVA10正式发布
>
> JAVA9和java10 都不是 LTS (Long-Term-Support)版本.和过去的JAVA大版本升级不同,这两个只有半年左右的开发和维护时间. 而JAVA11 也是就是18.9,才是JAVA之后的第一个长期支持版本
>
> JAVA10 一共定义了109个新特性,其中包含JEP,对程序员来说,真正的新特性也就一个,还有一些新的API和JVM规范以及JAVA语言规范上的改动.
>
> JAVA10 的12个JEP (JDK Enhancement Proposal特性加强协议) ,可参阅官方文档 http://openjdk.java.net/projects/jdk/10/

![1630388526815](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630388526815.png)



> 286: 局部变量类型推断
>
> 296: JDK库合并
>
> 304: 统一的垃圾回收接口
>
> 307:为G1提供并行的Full GC
>
> 310:应用程序类数据共享
>
> 312: ThreadLocal握手交互
>
> 313: 移除JDK中附带的javah工具
>
> 314: 使用附加的Unicode语言标记拓展
>
> 316:能将对内存占用分配给用户指定的备用内存设备
>
> 317:使用基于JAVA的JIT编译器
>
> 319: 根证书
>
> 322:基于时间的发布版本
>
> 

## 一 语法层次的变化

### 1_局部变量的类型推断

> 局部变量的显示类型声明,常常认为是不必须的,给一个好听的名字经常可以很清楚的表达出下面应该怎样继续.减少了啰嗦和形式的代码,避免了信息的冗余,而且对齐了变量名,容易阅读

作为JAVA程序员,在声明一个变量时,我们一般都是书写两次变量类型,第一次用于声明变量类型,第二次用于构造器

``` java
ArrayList<String> list =new ArrayList<>();
```

变量的声明类型书写复杂且较长,尤其是加上泛型的使用

``` java
Iterator<Map.Entry<Integer,Student>> iterator=set.iterator();
```

我们也经常声明一种变量,它只被使用一次,并且是在下一行代码中

``` java
URL url=new URL("http://www.mashibing.com");
URLConnection connection= url.openConnection();
Reader reader =new BufferedReader(new InputStreamReader(connection.getInputStream));
```

> **总之: 变量标识符(变量名)就可以让我清楚的知道变量如何使用,没必要前面加上一大串的类型声明**

局部变量推断有点类似JavaScript中的弱变量类型的写法,通过后面的数据来推断前面的数据类型,数据类型的声明统一为var 

``` java
public void test1(){
    // 局部变量,根据所赋的值,推断变量的类型
    var num =10;
    var arrayList =new ArrayList<String>();
    // 遍历操作
    for(var i : arrayList){
        System.out.println(i);
        System.out.println(i.getClass());
    }
    // 普通的遍历操作
    for(int i =0;i<100;i++){
        System.out.println(i);
    }
    // 推断类型的遍历操作
    for(var i =0;i<100;i++){
        System.out.println(i);
    }
    
}
```

> 什么情况下不能使用类型推断

1 变量的声明不能使用类型推断,因为无法推断

``` java
// 根据右边的数据推断类型,不赋值压根没给推断的机会,这是错的
var name;
```

2 初始值为null

``` java
        // null值无法推断数据类型,这是不能使用类型推断
        var str =null;
```

3 lambda表达式

``` java
        // 这是OK的
        Supplier<Double> supplier=()-> Math.random();
        // 这是不OK的 lambda表达式需要显式数据类型,不然无法明确是哪个接口
        var supplier2=()->Math.random();
```

4 方法引用

``` java
        // 这是可以的进行方法引用的
        Consumer<String> consumer=System.out::println;
        // 这是不可以的,无法明确接口类型
        var consumer2=System.out::println;
```

5 为数组静态初始化

```java
        //动态初始化数据可以进行类型推断
        var arr =new String[10];
        //静态初始化数组不可以使用类型推断 
        var arr2 ={"asdf","qwer","zxcv","tyui","ghjk"};
```

6 类型推断仅仅是局部变量,成员变量不可以使用类型推断

``` java
public class Test1 {
    // 成员变量不能使用类型推断
    var name="小明";
    public void testMethod1(){
        // 局部变量可以使用类型推断
        var localname="小明";
    }
}
```

7 其他不可以推断的情况

```java
// 情况1 没有初始化的局部变量声明
var s; var x=null;
// 情况2 方法的返回值类型
public var test1()
// 情况3 方法的参数类型
public void test2(var a,var b)
// 情况4 构造器的参数类型
public Person(var name,var age)
// 情况5 属性
class Person{
    var name;
}
// 情况6 catch块
try{
}catch(var e){
}
```



> 以下两点需要注意
>
> 1 var不是一个关键字
>
> 我们无需担心变量名或者方法名会与var发生冲突,因为var实际上不是一个关键字,而是一个类型名,只有在编译器需要知道类型的地方法才会用到它.除此之外,他就是一个普通的合法标识符.也就是说,除了不能用它做类名,其他都可以.但是又有哪个傻瓜非要用var做类名呢?
>
> 2 这毕竟不是JavaScript
>
> var并不会改变java是一门静态语言的事实,编译器负责推断出类型,并把结果写入字节码,也就是说,数据类型还是在字节码中的,java还是属于强类型的编程语言,开发人员没有明确写出来而已.而JavaScript是弱类型解释型的脚本语言,和这里的类型推断是两回事.

## 二 API层次的变化

### 1_集合中新增copyOf创建只读集合

``` java
        // JAVA9中新增创建只读的方法
        var strings1 = List.of("MCA", "JAVA", "Golang");
        // JAVA10中新增的创建只读集合的方法
        var strings2 = List.copyOf(strings1);
        // 判断两个集合在内存上是否是同一个,结果为true
        System.out.println(strings1==strings2);

        // 创建一个普通集合
        var strings3=new ArrayList<String>();
        // 通过copyOf方法创建一个只读集合
        var strings4 = List.copyOf(strings3);
        // 判断两个集合在内存上是否是同一个,结果为false
        System.out.println(strings3==strings4);
```

>  结论:copyOf方法的作用通过一个集合返回的是一个只读集合,如果参数本来就是只读集合,那么返回的就是参数,如果参数不是只读集合,就再创造一个只读集合返回



# JAVA11

## 概述



> 2018年9月26日,Oracle官方发布JAVA11.这是JAVA大版本周期变化后的第一个长期支持版本,非常值得关注.最新发布的JAVA11将带来ZGC HttpClient等重要特性,一共17个需要我们关注的JEP,参考文档http://openjdk.java.net/projects/jdk/11/

![1630397289057](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630397289057.png)

> 181:基于嵌套的访问控制  
>
> 309:动态类文件常量  
>
> 315:改进Aarch64 Intrinsics  
>
> 318:Epsilon:一个无操作的垃圾收集器  
>
> 320:移除Java EE和CORBA模块  
>
> 321:HTTP客户端(标准)  
>
> 323:本地变量语法Lambda参数  
>
> 324:与Curve25519和Curve448的密钥一致  
>
> 327: Unicode 10  
>
> 328:飞行记录器  
>
> 329: ChaCha20和Poly1305密码算法  
>
> 330:启动单文件源代码程序  
>
> 331:低开销堆分析  
>
> 332: TLS (Transport Layer Security) 1.3  
>
> 333:ZGC:一个可伸缩的低延迟垃圾收集器  (实验)  
>
> 335:已弃用Nashorn JavaScript引擎  
>
> 336:已弃用Pack200工具和API  

## 一 语法层次的变化

### 1_局部变量类型推断升级

> 局部变量类型推断是java10开始新增的新特性,java11中对局部变量推断进行了升级,在var支持添加注解的语法格式,JAVA10中是无法实现的,在JAVA11中加入了这样的语

lambda表达式中,注解修饰变量的时候,变量的数据类型必须要写,不能省略,像下面这种写法就是错误的

``` java
Consumer<String> con =(@Deprecated  t) -> System.out.println(t.toLowerCase());
```

这个时候就必须要为小括号中的参数添加数据类型,应该这样写

``` java
Consumer<String> con =(@Deprecated String t) -> System.out.println(t.toLowerCase());
```

java11中,lambda表达式中的参数数据类型可以使用var,但是不能不写

``` java
Consumer<String> con =(@Deprecated var t) -> System.out.println(t.toLowerCase());
```

## 二 API层次的变化

### 1_新增的String处理方法

| 描述                 | 举例                                                         |
| -------------------- | ------------------------------------------------------------ |
| 判断字符串是否为空白 | "  ".isBlank(); // true                                      |
| 去除字符串首尾空白   | "  www.mashibin.com  ".strip(); // "www.mashibing.com"       |
| 去除字符串尾部空格   | "  www.mashibin.com  ".stripTrailin(); // " www.mashibing.com" |
| 去除字符串首部空格   | "  www.mashibin.com  ".stripLeading(); // "www.mashibing.com " |
| 复制字符串           | "mashibing".repeat(2);// "mashibingmashibing"                |
| 行数统计             | "A\nB\nC\nD".lines().count(); // 4                           |

``` java
        // 判断字符串是否为空白
        boolean b = " \t ".isBlank();// true
        // 去除字符串首尾空白
        String s1 = " \n \t www.mashibin.com\n \t  ".strip();// "www.mashibing.com"
        // 去除字符串尾部空格
        String s2 = "  \n \twww.mashibin.com\n \t  ".stripTrailing();// " \n \twww.mashibing.com"
        // 去除字符串首部空格
        String s3 = "  \n \twww.mashibin.com\n \t  ".stripLeading();// "www.mashibing.com\n \t "
        // 复制字符串
        String r = "mashibing".repeat(2);// "mashibingmashibing"
        // 行数统计
        long c = "A\nB\nC\nD".lines().count();// 4
```



### 2_Optional新增方法



> Optional也增加了几个非常好用的方法,现在可以很方便的讲一个Optional转换成一个Stream,或者当一个空Optional时,给它一个替代的. 我们发现从JDK8开始出现Stream以后,每个版本都有相关的更新.

| 新增方法                                                     | 描述                                                         | 新增版本 |
| ------------------------------------------------------------ | ------------------------------------------------------------ | -------- |
| boolean isEmpty()                                            | 判断value是否为空                                            | JDK11    |
| T orElseThrow()                                              | value非空,返回value,否则抛出NoSuchElementExpception          | JDK10    |
| ifPresentOrElse(Consumer<? super T> action,Runnable emptyAction) | value非空,执行参数1功能,如果value为空,执行参数2功能          | JDK9     |
| Optional<T> or(Supplier<? extends Optional<? extends T> supplier) | value非空,返回对应的Optional,value为空,返回形参封装的Optional | JDK9     |
| Stream<T> stream();                                          | value非空,返回一个仅包含此value的Steam,否则,返回一个空的Stream | JDK9     |

``` java
        Optional<String> optional =Optional.empty();
        //JDK8 判断value是否存在
        System.out.println(optional.isPresent());
        //JDK11 判断value是否为空
        System.out.println(optional.isEmpty());

        //JDK10 返回value,如果为null则直接抛出 NoSuchElementExpception
        Optional<String> optional2 = Optional.of("element1");
        String value = optional2.orElseThrow();
        System.out.println(value);

        //JDK9  value非空,执行参数1功能,如果value为空,执行参数2功能
        Optional<String> optional3 =Optional.empty();// Optional.of("element1");
        optional.ifPresentOrElse((v)-> System.out.println("value为"+v),()-> System.out.println("value为null"));

        // JDK9 value非空,返回对应的Optional,value为空,返回形参封装的Optional
        Optional<String> optional4 =Optional.empty();// Optional.of("element1");
        Optional<String> optional5 = optional4.or(() -> Optional.of("element2"));
        System.out.println(optional5);

        // JDK9 value非空,返回一个仅包含此value的Steam,否则,返回一个空的Stream
        Optional<String> optional6 =Optional.of("element3");//Optional.empty();
        Stream<String> stream = optional6.stream();
        stream.forEach(System.out::println);
```









### 3_HTTPClient

> HTTP,用于传输网页的协议,在1997年就被采用1.1的版本中,到2015年,HTTP2才成为标准. HTTP1.1和HTTP2的主要区别就是如何在客户端和服务器之间构建和传输数据, HTTP1.1依赖请求/响应周期. HTTP2允许服务器push数据:它可以发送比客户端请求更多的数据.这使得他可以优先处理并发送对于首先加载网页至关重要的数据.

![1630723140272](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630723140272.png)



> JAVA9开始引入一个处理HTTP请求的HTTPClient API,该API支持同步和异步,而在JAVA11中成为正式可用状态,可以在java.net包中找到这个API,它将替代仅适用于bolocking模式的HTTPUrlConnection(创建于Http1.0s时代,并使用了协议无关的方法),并提供对WebSocket和HTTP2的支持

``` java
//HttpClient 替换原有的HttpUrlConnection  同步方式
HttpClient client =HttpClient.newHttpClient();
HttpRequest request =HttpRequest.newBuilder(URI.create("http://127.0.0.1:8080/demo")).build();
HttpResponse.BodyHandler<String> respnoseBodyHandler= HttpResponse.BodyHandlers.ofString();
HttpResponse<String> response =client.send(request,respnoseBodyHandler);
String body = response.body();
System.out.println(body);
```



``` java
//HttpClient 替换原有的HttpUrlConnection  异步方式
HttpClient client =HttpClient.newHttpClient();
HttpRequest request =HttpRequest.newBuilder(URI.create("http://127.0.0.1:8080/demo")).build();
HttpResponse.BodyHandler<String> respnoseBodyHandler= HttpResponse.BodyHandlers.ofString();
CompletableFuture<HttpResponse<String>> sendAsync = client.sendAsync(request, respnoseBodyHandler);
sendAsync.thenApply(t-> t.body()).thenAccept(System.out::println);
```



## 三 其他变化

### 1_更简化的编译运行

> JAVA11 提供了更简化的编译运行程序

> 编译一个java源代码文件语法应该是

``` java 
javac Test1.java
```

> 解释执行一个java字节码的语法应该是

``` java 
java Test1
```

> 在我们目前的知识里面,运行一个java源代码必须经过两个不中,一个是编译,一个是解释执行,而在java11中,通过一个java命令就可以直接搞定了,语法是

``` java 
java Test1.java
```

> 需要注意的是:
>
> 1 源代码文件中如果有多个类,执行源文件中的第一个类中主方法,注意这里的第一个是代码顺序的第一个,和是否由public修饰无关
>
> 2不可以使用其他源文件中定中自定义的类,当前文件中自定义的类是可以使用的



定义一个源代码文件进行测试

``` java
public class HelloJAVA11{
    public static void main(String[] args){
        System.out.println("HelloJAVA11.main");
        // 实例化当前文件中的Person类
        Person p=new Person();
        // 实例化另一个文件中的Student类
        Student stu =new Student();
            
    }
    
}

class Person {
    private String pid;
    private String pname;
    
}
class Test2{
    public static void main(String[] args){
        System.out.println("Test2.main");
    }
}
```

``` java
public class Student{
    
}
```

如果当前文件中,没有使用其他文件中的类,可以直接运行成功

![1630725285981](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630725285981.png)

如果当前文件中使用类其他文件中的类,那么会出现异常

![1630725332399](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630725332399.png)















### 2_废弃 Nashorn

>  废弃Nashorn javaScript引擎,在后续的版本中准备移除,需要的可以考虑 GraalVM



### 3_ZGC 



GC 是java的主要优势之一(另一个是强大的JVM),永远都是java优化的一个核心点. 然而,当GC的STW(stop the world)太长,就会影响应用的响应时间. 消除或者减少GC的停顿时长,将会使JAVA对更广泛的引用场景成为一个更具有吸引力的平台. 此外,现代系统中可用内存不断正常,用户和程序员希望JVM能够以更高效的方式利用这些内存,并且无需长时间STW. ZGC  A Scalable Low-Latency Garbage Collector(Experimental).作为JDK11最瞩目的特征,但是后面带了Experimental,说明是实验版本,也就不建议在生产环境中使用.ZGC是一个并发,基于 region的压缩性垃圾收集器,只有root扫描阶段会STW,因此GC停顿时间不会随着堆的增长和存活对象的增长而变长.



>  优势

* 暂停时间不会超过10ms
* 既能处理几百兆的小堆,也能处理几个T的大堆(OMG)
* 和G1相比,应用吞吐能力不会下降超过15%
* 为未来的GC功能和利用colord指针以及Load Barriers优化奠定基础
* 初始只支持64位系统

> 设计目标

1 支持TB级内存容量,暂停时间低(<10ms),对整个程序吞吐量的影响小于15%

2 将来还可以扩展实现机制,用以支持很多让人兴奋的功能. 如多层堆或者压缩堆

PS: 多层堆即对象置于DRAM和冷对象置于NVMe闪存

### 4_其他了解

* unicode10
* Deprecate The Pack200 Tools and API 
* 新的Epsilon垃圾收集器
* 完全支持Linux容器,包括Docker
* 支持G1上的并行完全垃圾收集
* 最新的HTTPS安全协议TLS 1.3 
* JAVA Flight Recoder



# JAVA12

## 概述

2019年3月19日,java12正式发布了,总共有8个新的JEP(JDK Enhancement Proposals)

``` java
JDK 12 is the open-source reference implementation of version 12 of the Java SE12
Platform as specified by by JSR 386 in the Java Community Process.
JDK 12 reached General Availability on 19 March 2019. Production-ready binaries under
the GPL are available from Oracle; binaries from other vendors will follow shortly.
The features and schedule of this release were proposed and tracked via the JEP
Process, as amended by the JEP 2.0 proposal. The release was produced using the JDK
Release Process(JEP 3).
```

Features

``` java 
http://openjdk.java.net/projects/jdk/12/
```

![1630745315617](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630745315617.png)

``` java 
189:Shenandoah:A Low-Pause-Time Garbage Collector(Experimental)
低暂停时间的GC http://openjdk.java.net/jeps/189
230:Microbenchmark Suite
微基准测试套件 http://openjdk.java.net/jeps/230
325:Switch Expressions(Preview)
switch表达式 http://openjdk.java.net/jeps/325
334:JVM Constants API
JVM常量API http://openjdk.java.net/jeps/334
340:One AArch64 Port,Not Two
只保留一个AArch64实现 http://openjdk.java.net/jeps/340
341:Default CDS Archives
默认类数据共享归档文件 http://openjdk.java.net/jeps/341
344:Abortable Mixed Collections for G1
可中止的G1 Mixed GC http://openjdk.java.net/jeps/344
346:Promptly Return Unused Committed Memory from G1
G1及时返回未使用的已分配内存 http://openjdk.java.net/jeps/346
```







## 一 语法层次的变化

### 1_swich表达式(预览)



> 传统的switch声明语句(switch statement)在使用中有一些问题：

​	1匹配自上而下,若无break, 后面的case语句都会执行；
​	2不同的case语句定义的变量名不能重复；
​	3不能在一个case里写多个执行结果一致的条件；
​	4整个switch不能作为表达式返回值；
Java 12提供增强版的 switch 语句或称为 "switch 表达式"来写出更加简化的代码。

> 什么是预览?



Switch 表达式也是作为预览语言功能的第一个语言改动被引入新版 Java 中来的，这是一种引入新特性的测试版的方法。通过这种方式，能够根据用户反馈进行升级、更改.如果没有被很好的接纳，则可以完全删除该功能。预览功能的没有被包含在Java SE 规范中。也就时说: 这不是一个正式的语法,是暂时进行测试的一种语法



>  switch详细语法

扩展的 switch 语句，不仅可以作为语句（statement），还可以作为表达式（expression），并且两种写法都可以
使用传统的 switch 语法，或者使用简化的“case L ->”模式匹配语法作用于不同范围并控制执行流。这些更改将简化日
常编码工作，并为 switch 中的模式匹配（JEP 305）做好准备。

* 使用 Java 12 中 Switch 表达式的写法，省去了 break 语句，避免了因少写 break 而出错。

* 同时将多个 case 合并到一行，显得简洁、清晰也更加优雅的表达逻辑分支，其具体写法就是将之前的 case 语
  句表成了：case L ->，即如果条件匹配 case ，则执行标签右侧的代码 ，同时标签右侧的代码段只能是表达
  式、代码块或 throw 语句。

* 为了保持兼容性，case 条件语句中依然可以使用字符 : ，这时 fall-through 规则依然有效的，即不能省略原有
  的 break 语句，但是同一个 Switch 结构里不能混用 -> 和 : ，否则会有编译错误。并且简化后的 Switch 代码块
  中定义的局部变量，其作用域就限制在代码块中，而不是蔓延到整个 Switch 结构，也不用根据不同的判断条件
  来给变量赋值。



>  JAVA12之前,switch语句写法如下

``` java
/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class TestSwitch {
    public static void main(String[] args) {
        Month month=Month.APRIL;
        String season;
        switch (month){
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                season="winter";
                break;
            case MARCH:
            case APRIL:
            case MAY:
                season="spring";
                break;
            case JUNE:
            case JULY:
            case AUGUST:
                season="summer";
                break;
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
                season="autumn";
                break;
            default:
                throw new RuntimeException("NoSuchMonthException");
        }
        System.out.println(season);

    }
}

enum Month{
    JANUARY, FEBRUARY, MARCH,APRIL,MAY,JUNE,JULY,AUGUST,SEPTEMBER,OCTOBER,NOVEMBER, DECEMBER;
}

```

>  java12 之后,switch语句可以如下

``` java
/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class TestSwitch {
    public static void main(String[] args) {
        Month month=Month.APRIL;
        String season;
        switch (month){
            case DECEMBER,JANUARY,FEBRUARY ->season="winter";
            case MARCH,APRIL,MAY -> season="spring";
            case JUNE,JULY,AUGUST -> season="summer";
            case SEPTEMBER,OCTOBER,NOVEMBER -> season="autumn";
            default -> throw new RuntimeException("NoSuchMonth");
        }
        System.out.println(season);
    }
}

enum Month{
    JANUARY, FEBRUARY, MARCH,APRIL,MAY,JUNE,JULY,AUGUST,SEPTEMBER,OCTOBER,NOVEMBER, DECEMBER;
}
```

> 似乎可以看出,java开发者或将逐渐的从复杂繁琐的底层抽象代码的编写中解放出来,编写一些更高层次更优雅的代码. 减少出错,提高开发效率. 目前Switch表达式支持下面的数据类型, byte char short int Byte, Character,Short,Integer,enum,String,未来是否会支持 float double和long? 目前本版本未对支持的数据类型进行拓展



## 二API层次的变化



### 1_支持数字压缩格式化

NumberFormat 添加了对以紧凑形式格式化数字的支持。紧凑数字格式是指以简短或人类可读形式表示的数字。例
如，在en_US语言环境中，1000可以格式化为“1K”，1000000可以格式化为“1M”，具体取决于指定的样式
NumberFormat.Style。

``` java
var cnf = NumberFormat.getCompactNumberInstance(Locale.CHINA,
NumberFormat.Style.SHORT);
System.out.println(cnf.format(1_0000));
System.out.println(cnf.format(1_9200));
System.out.println(cnf.format(1_000_000));
System.out.println(cnf.format(1L << 30));
System.out.println(cnf.format(1L << 40));
System.out.println(cnf.format(1L << 50));
```


运行结果为

``` java
1万
2万
100万
11亿
1兆
1126兆
```



### 2_String新方法

> String#transform(Function)

它提供的函数作为输入提供给特定的String实例，并返回该函数返回的输出。

``` java
var result = "马士兵".transform(input -> input + "教育");
System.out.println(result); //马士兵教育
```

``` java
var result = "teacher"
.transform(input -> input + " mashibing")
.transform(String::toUpperCase)
System.out.println(result); //TEACHER MASHIBING
```

该方法源码

``` java
/**
     * This method allows the application of a function to {@code this}
     * string. The function should expect a single String argument
     * and produce an {@code R} result.
     * <p>
     * Any exception thrown by {@code f.apply()} will be propagated to the
     * caller.
     *
     * @param f    a function to apply
     *
     * @param <R>  the type of the result
     *
     * @return     the result of applying the function to this string
     *
     * @see java.util.function.Function
     *
     * @since 12
     */
    public <R> R transform(Function<? super String, ? extends R> f) {
        return f.apply(this);
    }
```



传入一个函数式接口 Function，接受一个值，返回一个值，连续调用transform方法,对字符串进行连续三次的改变

``` java
List<String> list1 = List.of("Java", " Golang", " MCA ");
List<String> list2 = new ArrayList<>();
list1.forEach(element -> list2.add(element.transform(String::strip)
.transform(String::toUpperCase)
.transform((e) -> "Hello," + e))
);
list2.forEach(System.out::println);
```

结果为:

``` java
Hello,JAVA
Hello,GOLANG
Hello,MCA
```

感觉和StreamAPI中的map功能和语法上有些类似,但是这里毕竟不是Stream,StreamAPI如果对元素进行改变使用map方法完成上述功能

``` java
List<String> list1 = List.of("Java ", " Golang", " MCA ");
Stream<String> stringStream = list1.stream().map(element ->
element.strip()).map(String::toUpperCase).map(element -> "yeah!," + element);
List<String> list2 = stringStream.collect(Collectors.toList());
list2.forEach(System.out::println);
```

结果为:

``` java
yeah!,JAVA
yeah!,GOLANG
yeah!,MCA
```

> String#indent

该方法允许我们调整String实例的缩进。

``` java
String result = "Java\nGolang\nMCA".indent(3);
System.out.println(result);
```

结果为:

``` java
   Java
   Golang
   MCA
```

换行符 \n 后向前缩进 n 个空格，为 0 或负数不缩进。

indent源码

``` java
public String indent(int n) {
        if (isEmpty()) {
            return "";
        }
        Stream<String> stream = lines();
        if (n > 0) {
            final String spaces = " ".repeat(n);
            stream = stream.map(s -> spaces + s);
        } else if (n == Integer.MIN_VALUE) {
            stream = stream.map(s -> s.stripLeading());
        } else if (n < 0) {
            stream = stream.map(s -> s.substring(Math.min(-n, s.indexOfNonWhitespace())));
        }
        return stream.collect(Collectors.joining("\n", "", "\n"));
    }
```







### 3_Files新增mismatch方法

> 对比两个文件的差异,返回从哪个字节开始出现了不一致

``` java
FileWriter fileWriter = new FileWriter("d:/a.txt");
fileWriter.write("a");
fileWriter.write("b");
fileWriter.write("c");
fileWriter.close();
FileWriter fileWriterB = new FileWriter("d:/b.txt");
fileWriterB.write("a");
fileWriterB.write("1");
fileWriterB.write("c");
fileWriterB.close();
System.out.println(Files.mismatch(Path.of("d:/a.txt"),Path.of("d:/b.txt")));
```







## 三 关于GC方面的新特性



### 1_Shenandoah GC：低停顿时间的GC（预览）



Shenandoah 垃圾回收器是 Red Hat 在 2014 年宣布进行的一项垃圾收集器研究项目 Pauseless GC 的实现，旨在针
对 JVM 上的内存收回实现低停顿的需求。该设计将与应用程序线程并发，通过交换 CPU 并发周期和空间以改善停顿
时间，使得垃圾回收器执行线程能够在 Java 线程运行时进行堆压缩，并且标记和整理能够同时进行，因此避免了在
大多数 JVM 垃圾收集器中所遇到的问题。
据 Red Hat 研发 Shenandoah 团队对外宣称，Shenandoah 垃圾回收器的暂停时间与堆大小无关，这意味着无论将
堆设置为 200 MB 还是 200 GB，都将拥有一致的系统暂停时间，不过实际使用性能将取决于实际工作堆的大小和工
作负载。与其他 Pauseless GC 类似，Shenandoah GC 主要目标是 99.9% 的暂停小于 10ms，暂停与堆大小无关等。这是一个实验性功能，不包含在默认（Oracle）的OpenJDK版本中。

> STW stop the world

​	Stop-the-World ，简称STW ，指的是GC 事件发生过程中，停止所有的应用程序线程的执行.

​	**垃圾回收器的任务是识别和回收垃圾对象进行内存清理。垃圾回收要求系统进入一个停顿的状态。**停顿的目的是终止所有应用程序的执行，这样才不会有新的垃圾产生，同时保证了系统状态在某一个瞬间的一致性，并且有益于垃圾回收器更好地标记垃圾对象。**停顿产生时整个应用程序会被暂停，没有任何响应，有点像卡死的感觉，这个停顿称为STW 。**
​	如果Stop-the- World 出现在新生代的Minor GC 中时， 由于新生代的内存空间通常都比较小，所以暂停的时间较短,在可接受的范围内，老年代的Full GC 中时，程序的工作线程被暂停的时间将会更久。内存空间越大，执行Full GC 的时间就会越久，工作线程被暂停的时间也就会更长。**到目前为止，哪怕是G1 也不能完全避免Stop-the-world 情况发生，只能说垃圾回收器越来越优秀，尽可能地缩短了暂停时间。**

> java垃圾收集器的分类  常见的Serial(串行) ParNEW(并行)  Parallel(吞吐优先并行) CMS(低延迟) G1(区域化分代),不同的垃圾收集器都有自己的特征,简单进行一个分类

>  按线程数分类

1 串行垃圾回收器

串行回收指的是在同一时间段内只允许一件事情发生，简单来说，当多个CPU 可用时，也只能有一个CPU
用于执行垃圾回收操作，井且在执行垃圾回收时，程序中的工作线程将会被暂停，当垃圾收集工作完成后
才会恢复之前被暂停的工作线程，这就是串行回收。

![1630743436323](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630743436323.png)

2 并行垃圾回收器

和串行回收相反，并行收集可以运用多个CPU 同时执行垃圾回收，因此提升了应用的吞吐量，不过并行回
收仍然与串行回收一样，采用独占式，使用了“ Stop-the-world ”机制和复制算法

![1630743474804](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630743474804.png)

>  按照工作模式分类



1 并发式垃圾收集器

并发式垃圾回收器与应用程序线程交替工作，以尽可能减少应用程序的停顿时间。

2 独占式垃圾收集器

独占式垃圾回收器（ Stop the world)一旦运行，就停止应用程序中的其他所有线程，直到垃圾回收过程完
全结束。

![1630743967253](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630743967253.png)



>  按照碎片处理方式

1 压缩式垃圾回收器

压缩式垃圾回收器会在回收完成后，对存活对象进行压缩整理，消除回收后的碎片。

2 非压缩式垃圾回收器

非压缩式的垃圾回收器不进行这步操作。

> 按照工作的内存区间

1 年轻代垃圾回收器

2 老年代垃圾回收器

> 如何判断垃圾回收器的性能

* 吞吐量：程序的运行时间（程序的运行时间＋内存回收的时间）。
* 垃圾收集开销：吞吐量的补数，垃圾收集器所占时间与总时间的比例。
* 暂停时间：执行垃圾收集时，程序的工作线程被暂停的时间,就是用户功能延迟的时间。
* 收集频率：相对于应用程序的执行，收集操作发生的频率。
* 堆空间： Java 堆区所占的内存大小。
* 快速： 一个对象从诞生到被回收所经历的时间。

垃圾收集器中吞吐量和低延迟这两个目标本身是相互矛盾的，因为如果选择以吞吐量优先(单位时间希望运行更多的应用程序)，那么必然需要降低内存回收的执行频率，但是这样会导致GC 需要更长的暂停时间来执行内存回收。相反的，如果选择以低延迟优先为原则，那么为了降低每次执行内存回收时的暂停时间，也只能频繁地执行内存回收，但这又引起了年轻代内存的缩减和导致程序吞吐量的下降



> Shenandoah工作原理

从原理的角度，我们可以参考该项目官方的示意图，其内存结构与 G1 非常相似，都是将内存划分为类似棋盘的
region。整体流程与 G1 也是比较相似的，最大的区别在于实现了并发的 疏散(Evacuation) 环节，引入的 Brooks
Forwarding Pointer 技术使得 GC 在移动对象时，对象引用仍然可以访问。

G1 内存设计

![1630986143084](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630986143084.png)

Shenandoah GC 工作周期如下所示：

![1630744256988](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630744256988.png)

``` java
1. Init Mark 启动并发标记阶段
2. 并发标记遍历堆阶段
3. 并发标记完成阶段
4. 并发整理回收无活动区域阶段
5. 并发 Evacuation 整理内存区域阶段
6. Init Update Refs 更新引用初始化 阶段
7. 并发更新引用阶段
8. Final Update Refs 完成引用更新阶段
9. 并发回收无引用区域阶段
```

了解 Shenandoah GC 的人比较少，提及比较多的是 Oracle 在 JDK11 中开源出来的 ZGC，或者商业版本的 Azul C4（Continuously Concurrent Compacting Collector）。也有人认为Shenandoah 其实际意义大于后两者,原因有一下几点：

```  java
1 使用 ZGC 的最低门槛是升级到 JDK11，版本的更新不是一件容易的事情,而且 ZGC 实际表现如何也是尚不清楚。
2 C4 成本较高,很多企业甚至斤斤计较几百元的软件成本。
3 Shenandoah GC 可是有稳定的 JDK8u 版本发布的。甚至已经有公司在 HBase 等高实时性产品中有较多的实践。
4 ZGC也是面向low-pause-time的垃圾收集器，不过ZGC是基于colored pointers来实现，而Shenandoah GC是
基于brooks pointers来实现。
```



不是唯有 GC 停顿可能导致常规应用程序响应时间比较长。具有较长的 GC 停顿时间会导致系统响应慢的问题，但响应时间慢并非一定是 GC 停顿时间长导致的，队列延迟、网络延迟、其他依赖服务延迟和操作提供调度程序抖动等都可能导致响应变慢。使用 Shenandoah 时需要全面了解系统运行情况，综合分析系统响应时间。下面是jbb15benchmark 中，Shenandoah GC 相对于其他主流 GC 的表现。

各种 GC 工作负载对比：

![1630744443082](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630744443082.png)

GC 暂停相比于 CMS 等选择有数量级程度的提高，对于 GC 暂停非常敏感的场景，价值还是很明显的，能够在 SLA
层面有显著提高。当然，这种对于低延迟的保证，也是以消耗 CPU 等计算资源为代价的，实际吞吐量表现也不是非
常明朗，需要看企业的实际场景需求，并不是一个一劳永逸的解决方案。



``` java
-XX:+AlwaysPreTouch：使用所有可用的内存分页，减少系统运行停顿，为避免运行时性能损失。
    
-Xmx == -Xmsv：设置初始堆大小与最大值一致，可以减轻伸缩堆大小带来的压力，与 AlwaysPreTouch 参数配
合使用，在启动时提交所有内存，避免在最终使用中出现系统停顿。
    
-XX:+ UseTransparentHugePages：能够大大提高大堆的性能，同时建议在 Linux 上使用时将
/sys/kernel/mm/transparent_hugepage/enabled 和
/sys/kernel/mm/transparent_hugepage/defragv 设置为：madvise，同时与 AlwaysPreTouch 一起使
用时，init 和 shutdownv 速度会更快，因为它将使用更大的页面进行预处理。
    
-XX:+UseNUMA：虽然 Shenandoah 尚未明确支持 NUMA（Non-Uniform Memory Access），但最好启用此功
能以在多插槽主机上启用 NUMA 交错。与 AlwaysPreTouch 相结合，它提供了比默认配置更好的性能。
    
-XX:+DisableExplicitGC：忽略代码中的 System.gc() 调用。当用户在代码中调用 System.gc() 时会强制
Shenandoah 执行 STW Full GC ，应禁用它以防止执行此操作，另外还可以使用 
    
-XX:+ExplicitGCInvokesConcurrent，在 调用 System.gc() 时执行 CMS GC 而不是 Full GC，建议在有
System.gc() 调用的情况下使用。
不过目前 Shenandoah 垃圾回收器还被标记为实验项目，如果要使用Shenandoah GC需要编译时--with-jvmfeatures
选项带有shenandoahgc，然后启动时使用参数
    
-XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
```



### 2_可中断的 G1 Mixed GC 

当 G1 垃圾回收器的回收超过暂停时间的目标，则能中止垃圾回收过程。

G1是一个垃圾收集器，设计用于具有大量内存的多处理器机器。由于它提高了性能效率，G1垃圾收集器最终将取代
CMS垃圾收集器。**该垃圾收集器设计的主要目标之一是满足用户设置的预期的 JVM 停顿时间。**

G1 采用一个高级分析引擎来选择在收集期间要处理的工作量，此选择过程的结果是一组称为 GC 回收集（collection
set( CSet )）的区域。一旦收集器确定了 GC 回收集 并且 GC 回收、整理工作已经开始，这个过程是without stopping的，即 G1 收集器必须完成收集集合的所有区域中的所有活动对象之后才能停止；但是如果收集器选择过大的 GC 回收集，此时的STW时间会过长超出目标pause time。

这种情况在mixed collections时候比较明显。这个特性启动了一个机制，当选择了一个比较大的collection set，Java
12 中将把 GC 回收集（混合收集集合）拆分为mandatory（必需或强制）及optional两部分( 当完成mandatory的部
分，如果还有剩余时间则会去处理optional部分)来将mixed collections从without stopping变为abortable，以更好满
足指定pause time的目标。

* 其中必需处理的部分包括 G1 垃圾收集器不能递增处理的 GC 回收集的部分（如：年轻代），同时也可以包含老
  年代以提高处理效率。

* 将 GC 回收集拆分为必需和可选部分时，垃圾收集过程优先处理必需部分。同时，需要为可选 GC 回收集部分维
  护一些其他数据，这会产生轻微的 CPU 开销，但小于 1 ％的变化，同时在 G1 回收器处理 GC 回收集期间，本
  机内存使用率也可能会增加，使用上述情况只适用于包含可选 GC 回收部分的 GC 混合回收集合。

* 在 G1 垃圾回收器完成收集需要必需回收的部分之后，如果还有时间的话，便开始收集可选的部分。但是粗粒度
  的处理，可选部分的处理粒度取决于剩余的时间，一次只能处理可选部分的一个子集区域。在完成可选收集部
  分的收集后，G1 垃圾回收器可以根据剩余时间决定是否停止收集。如果在处理完必需处理的部分后，剩余时间
  不足，总时间花销接近预期时间，G1 垃圾回收器也可以中止可选部分的回收以达到满足预期停顿时间的目标。

### 3_ 增强G1 

> 概述

上面介绍了 Java 12 中增强了 G1 垃圾收集器关于混合收集集合的处理策略，这节主要介绍在 Java 12 中同时也对 G1
垃圾回收器进行了改进，**使其能够在空闲时自动将 Java 堆内存返还给操作系统**，这也是 Java 12 中的另外一项重大
改进。

目前 Java 11 版本中包含的 G1 垃圾收集器暂时无法及时将已提交的 Java 堆内存返回给操作系统。为什么呢？ G1目
前只有在full GC或者concurrent cycle（并发处理周期）的时候才会归还内存，由于这两个场景都是G1极力避免的，
因此在大多数场景下可能不会及时归还committed Java heap memory给操作系统。除非有外部强制执行。

在使用云平台的容器环境中，这种不利之处特别明显。即使在虚拟机不活动，但如果仍然使用其分配的内存资源，哪
怕是其中的一小部分，G1 回收器也仍将保留所有已分配的 Java 堆内存。而这将导致用户需要始终为所有资源付费，
哪怕是实际并未用到，而云提供商也无法充分利用其硬件。如果在此期间虚拟机能够检测到 Java 堆内存的实际使用
情况，并在利用空闲时间自动将 Java 堆内存返还，则两者都将受益。

> 具体操作

为了尽可能的向操作系统返回空闲内存，**G1 垃圾收集器将在应用程序不活动期间定期生成或持续循环检查整体 Java堆使用情况，以便 G1 垃圾收集器能够更及时的将 Java 堆中不使用内存部分返还给操作系统。**对于长时间处于空闲状态的应用程序，此项改进将使 JVM 的内存利用率更加高效。而在用户控制下，可以可选地执行Full GC，以使返回的内存量最大化。

JDK12的这个特性新增了两个参数分别是G1 PeriodicGCInterval及G1 PeriodicGCSystemLoadThreshold，设置为0
的话，表示禁用。如果应用程序为非活动状态，在下面两种情况任何一个描述下，G1 回收器会触发定期垃圾收集：

* 自上次垃圾回收完成以来已超过 G1PeriodicGCInterval ( milliseconds )， 并且此时没有正在进行的垃圾回收
  任务。如果 G1PeriodicGCInterval 值为零表示禁用快速回收内存的定期垃圾收集。

* 应用所在主机系统上执行方法 getloadavg()，默认一分钟内系统返回的平均负载值低于
  G1PeriodicGCSystemLoadThreshold指定的阈值，则触发full GC或者concurrent GC( 如果开启
  G1PeriodicGCInvokesConcurrent )，GC之后Java heap size会被重写调整，然后多余的内存将会归还给操作
  系统。如果 G1PeriodicGCSystemLoadThreshold 值为零，则此条件不生效。

如果不满足上述条件中的任何一个，则取消当期的定期垃圾回收。等一个 G1PeriodicGCInterval 时间周期后，将重
新考虑是否执行定期垃圾回收。

G1 定期垃圾收集的类型根据 G1PeriodicGCInvokesConcurrent 参数的值确定：如果设置值了，G1 垃圾回收器将继
续上一个或者启动一个新并发周期；如果没有设置值，则 G1 回收器将执行一个Full GC。在每次一次 GC 回收末尾，
G1 回收器将调整当前的 Java 堆大小，此时便有可能会将未使用内存返还给操作系统。新的 Java 堆内存大小根据现
有配置确定，具体包括下列配置：- XX:MinHeapFreeRatio、-XX:MaxHeapFreeRatio、-Xms、-Xmx。

默认情况下，G1 回收器在定期垃圾回收期间新启动或继续上一轮并发周期，将最大限度地减少应用程序的中断。**如果定期垃圾收集严重影响程序执行，则需要考虑整个系统 CPU 负载，或让用户禁用定期垃圾收集。**





## 四 其他新特性简介



### 1_JVM常量API

Java 12 中引入 JVM 常量 API，用来更容易地对关键类文件 (key class-file) 和运行时构件（artefact）的名义描述
(nominal description) 进行建模，特别是对那些从常量池加载的常量，这是一项非常技术性的变化，能够以更简
单、标准的方式处理可加载常量。

具体来说就是java.base模块新增了java.lang.constant包。包中定义了一系列基于值的符号引用（JVMS 5.1）类型，它们能够描述每种可加载常量。

![1630987332315](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630987332315.png)



``` java
官方api链接地址：
http://cr.openjdk.java.net/~iris/se/12/latestSpec/api/java.base/java/lang/constant/package-summary.html

Java SE > Java SE Specifications > Java Virtual Machine Specification下的第5章：
Chapter 5. Loading, Linking, and Initializing
https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-5.html
```

引入了ConstantDesc接口( ClassDesc、MethodTypeDesc、MethodHandleDesc这几个接口直接继承了ConstantDesc
接口)以及Constable接口；ConstantDesc接口定义了resolveConstantDesc方法，Constable接口定义了
describeConstable方法；String、Integer、Long、Float、Double均实现了这两个接口，而EnumDesc实现了
ConstantDesc接口

![1630741892162](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1630741892162.png)

符号引用以纯 nominal 形式描述可加载常量，与类加载或可访问性上下文区分开。有些类可以作为自己的符号引用
（例如 String）。而对于可链接常量，另外定义了一系列符号引用类型，具体包括： ClassDesc (Class 的可加载常量
标称描述符) ，MethodTypeDesc(方法类型常量标称描述符) ，MethodHandleDesc (方法句柄常量标称描述符) 和
DynamicConstantDesc (动态常量标称描述符) ，它们包含描述这些常量的 nominal 信息。此 API 对于操作类和方法
的工具很有帮助。



ConstantDesc源码

``` java
/**A nominal descriptor for a loadable constant value, as defined in JVMS 4.4. Such a descriptor can be resolved via resolveConstantDesc(MethodHandles.Lookup) to yield the constant value itself.
Class names in a nominal descriptor, like class names in the constant pool of a classfile, must be interpreted with respect to a particular class loader, which is not part of the nominal descriptor.
Static constants that are expressible natively in the constant pool (String, Integer, Long, Float, and Double) implement ConstantDesc, and serve as nominal descriptors for themselves. Native linkable constants (Class, MethodType, and MethodHandle) have counterpart ConstantDesc types: ClassDesc, MethodTypeDesc, and MethodHandleDesc. Other constants are represented by subtypes of DynamicConstantDesc.
APIs that perform generation or parsing of bytecode are encouraged to use ConstantDesc to describe the operand of an ldc instruction (including dynamic constants), the static bootstrap arguments of dynamic constants and invokedynamic instructions, and other bytecodes or classfile structures that make use of the constant pool.
Constants describing various common constants (such as ClassDesc instances for platform types) can be found in ConstantDescs.
Implementations of ConstantDesc should be immutable and their behavior should not rely on object identity.
Non-platform classes should not implement ConstantDesc directly. Instead, they should extend DynamicConstantDesc (as Enum.EnumDesc and invoke.VarHandle.VarHandleDesc do.)
Nominal descriptors should be compared using the Object.equals(Object) method. There is no guarantee that any particular entity will always be represented by the same descriptor instance.
API Note:
In the future, if the Java language permits, ConstantDesc may become a sealed interface, which would prohibit subclassing except by explicitly permitted types. Clients can assume that the following set of subtypes is exhaustive: String, Integer, Long, Float, Double, ClassDesc, MethodTypeDesc, MethodHandleDesc, and DynamicConstantDesc; this list may be extended to reflect future changes to the constant pool format as defined in JVMS 4.4.
Since:
12
See Also:
Constable, ConstantDescs
*/
public interface ConstantDesc {
    /**
     * Resolves this descriptor reflectively, emulating the resolution behavior
     * of JVMS 5.4.3 and the access control behavior of JVMS 5.4.4.  The resolution
     * and access control context is provided by the {@link MethodHandles.Lookup}
     * parameter.  No caching of the resulting value is performed.
     *
     * @param lookup The {@link MethodHandles.Lookup} to provide name resolution
     *               and access control context
     * @return the resolved constant value
     * @throws ReflectiveOperationException if a class, method, or field
     * could not be reflectively resolved in the course of resolution
     * @throws LinkageError if a linkage error occurs
     *
     * @apiNote {@linkplain MethodTypeDesc} can represent method type descriptors
     * that are not representable by {@linkplain MethodType}, such as methods with
     * more than 255 parameter slots, so attempts to resolve these may result in errors.
     *
     * @jvms 5.4.3 Resolution
     * @jvms 5.4.4 Access Control
     */
    Object resolveConstantDesc(MethodHandles.Lookup lookup) throws ReflectiveOperationException;
}
```





Constable接口源码

``` java
/**
 * Represents a type which is <em>constable</em>.  A constable type is one whose
 * values are constants that can be represented in the constant pool of a Java
 * classfile as described in JVMS 4.4, and whose instances can describe themselves
 * nominally as a {@link ConstantDesc}.
 *
 * <p>Some constable types have a native representation in the constant pool:
 * {@link String}, {@link Integer}, {@link Long}, {@link Float},
 * {@link Double}, {@link Class}, {@link MethodType}, and {@link MethodHandle}.
 * The types {@link String}, {@link Integer}, {@link Long}, {@link Float},
 * and {@link Double} serve as their own nominal descriptors; {@link Class},
 * {@link MethodType}, and {@link MethodHandle} have corresponding nominal
 * descriptors {@link ClassDesc}, {@link MethodTypeDesc}, and {@link MethodHandleDesc}.
 *
 * <p>Other reference types can be constable if their instances can describe
 * themselves in nominal form as a {@link ConstantDesc}. Examples in the Java SE
 * Platform API are types that support Java language features such as {@link Enum},
 * and runtime support classes such as {@link VarHandle}.  These are typically
 * described with a {@link DynamicConstantDesc}, which describes dynamically
 * generated constants (JVMS 4.4.10).
 *
 * <p>The nominal form of an instance of a constable type is obtained via
 * {@link #describeConstable()}. A {@linkplain Constable} need
 * not be able to (or may choose not to) describe all its instances in the form of
 * a {@link ConstantDesc}; this method returns an {@link Optional} that can be
 * empty to indicate that a nominal descriptor could not be created for an instance.
 * (For example, {@link MethodHandle} will produce nominal descriptors for direct
 * method handles, but not necessarily those produced by method handle
 * combinators.)
 * @jvms 4.4 The Constant Pool
 * @jvms 4.4.10 The {@code CONSTANT_Dynamic_info} and {@code CONSTANT_InvokeDynamic_info} Structures
 *
 * @since 12
 */
public interface Constable {
    /**
     * Returns an {@link Optional} containing the nominal descriptor for this
     * instance, if one can be constructed, or an empty {@link Optional}
     * if one cannot be constructed.
     *
     * @return An {@link Optional} containing the resulting nominal descriptor,
     * or an empty {@link Optional} if one cannot be constructed.
     */
    Optional<? extends ConstantDesc> describeConstable();
}

```



String源码

``` java
/**
* Returns an {@link Optional} containing the nominal descriptor for this
* instance, which is the instance itself.
*
* @return an {@link Optional} describing the {@linkplain String} instance
* @since 12
*/
@Override
public Optional<String> describeConstable() {
return Optional.of(this);
}
/**
* Resolves this instance as a {@link ConstantDesc}, the result of which is
* the instance itself.
*
* @param lookup ignored
* @return the {@linkplain String} instance
* @since 12
*/
@Override
public String resolveConstantDesc(MethodHandles.Lookup lookup) {
return this;
}
```



测试代码

``` java
String name = "马士兵教育";
Optional<String> optional = name.describeConstable();
System.out.println(optional.get());
```

测试结果

``` java
马士兵教育
```





### 2_微基准测试套件

>  什么是JMH(java微基准测试)

JMH，即Java Microbenchmark Harness，是专门用于代码微基准测试的工具套件。何谓Micro Benchmark呢？简
单的来说就是基于方法层面的基准测试，精度可以达到微秒级。当你定位到热点方法，希望进一步优化方法性能的时
候，就可以使用JMH对优化的结果进行量化的分析。

> 应用场景

1 想准确的知道某个方法需要执行多长时间，以及执行时间和输入之间的相关性；
2 对比接口不同实现在给定条件下的吞吐量；
3 查看多少百分比的请求在多长时间内完成；

>  JMH使用

要使用JMH，首先需要准备好Maven环境,

如果要在现有Maven项目中使用JMH，只需要把生成出来的两个依赖以及shade插件拷贝到项目的pom中即可

``` xml
<dependency>
	<groupId>org.openjdk.jmh</groupId>
	<artifactId>jmh-core</artifactId>
	<version>0.7.1</version>
</dependency>
<dependency>
	<groupId>org.openjdk.jmh</groupId>
	<artifactId>jmh-generator-annprocess</artifactId>
	<version>0.7.1</version>
	<scope>provided</scope>
</dependency>

<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-shade-plugin</artifactId>
	<version>2.0</version>
	<executions>
		<execution>
			<phase>package</phase>
			<goals>
				<goal>shade</goal>
			</goals>
			<configuration>
				<finalName>microbenchmarks</finalName>
				<transformers>
   					 <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
						<mainClass>org.openjdk.jmh.Main</mainClass>
					</transformer>
				</transformers>
			</configuration>
		</execution>
	</executions>
</plugin>

```

> 新特性说明
>
> Java 12 中添加一套新的基本的微基准测试套件（microbenchmarks suite），此功能为JDK源代码添加了一套微基准测试（大约100个），**简化了现有微基准测试的运行和新基准测试的创建过程**.使开发人员可以轻松运行现有的微基准测试并创建新的基准测试，其目标在于提供一个稳定且优化过的基准。 它基于Java Microbenchmark
> Harness（JMH），可以轻松测试JDK性能，支持JMH更新。
>
> **微基准套件与 JDK 源代码位于同一个目录中，并且在构建后将生成单个 jar 文件**。但它是一个单独的项目，在支持构建期间不会执行，以方便开发人员和其他对构建微基准套件不感兴趣的人在构建时花费比较少的构建时间。
>
> 要构建微基准套件，用户需要运行命令：make build-microbenchmark， 类似的命令还有：make test TEST="micro:java.lang.invoke" 将使用默认设置运行java.lang.invoke 相关的微基准测试。



### 3_只保留一个AArch64实现

> 现状

当前 Java 11 及之前版本JDK中存在两个64位ARM端口。这些文件的主要来源位于src/hotspot/cpu/arm 和
open/src/hotspot/cpu/aarch64 目录中。尽管两个端口都产生了aarch64 实现，我们将前者（由Oracle贡献）称
为arm64 ，将后者称为aarch64 。

> 新特征

Java 12 中将删除由 Oracle 提供的 arm64端口相关的所有源码，即删除目录 open/src/hotspot/cpu/arm 中关于
64-bit 的这套实现，只保留其中有关 32-bit ARM端口的实现，余下目录的 open/src/hotspot/cpu/aarch64 代码
部分就成了 AArch64 的默认实现。

> 目的

这将使开发贡献者将他们的精力集中在单个 64 位 ARM 实现上，并消除维护两套实现所需的重复工作。



### 4_默认生成类的数据共享(CDS)归档文件

> 概述

我们知道在同一个物理机／虚拟机上启动多个JVM时，如果每个虚拟机都单独装载自己需要的所有类，启动成本和内
存占用是比较高的。所以Java团队引入了类数据共享机制 (Class Data Sharing ，简称 CDS) 的概念，通过把一些核心
类在每个JVM间共享，每个JVM只需要装载自己的应用类即可。好处是：启动时间减少了，另外核心类是共享的，所
以JVM的内存占用也减少了。

>  历史版本

1 JDK5引入了Class-Data Sharing可以用于多个JVM共享class，提升启动速度，最早只支持system classes及
serial GC。
2 JDK9对其进行扩展以支持application classes(自定义class)及其他GC算法。
3 java10的新特性JEP 310: Application Class-Data Sharing扩展了JDK5引入的Class-Data Sharing，支持application的Class-Data Sharing并开源出来(以前是commercial feature)
		CDS 只能作用于 BootClassLoader 加载的类，不能作用于 AppClassLoader 或者自定义的 ClassLoader加载的类。在 Java 10 中，则将 CDS 扩展为 AppCDS，顾名思义，AppCDS 不止能够作用于BootClassLoader了，AppClassLoader 和自定义的 ClassLoader 也都能够起作用，大大加大了 CDS 的适用范围。也就说开发自定义的类也可以装载给多个JVM共享了。
4 JDK11将-Xshare:off改为默认-Xshare:auto，以更加方便使用CDS特性

>  迭代效果

可以说，自 Java 8 以来，在基本 CDS 功能上进行了许多增强、改进，启用 CDS 后应用的启动时间和内存占用量显着
减少。使用 Java 11 早期版本在 64 位 Linux 平台上运行 HelloWorld 进行测试，测试结果显示启动时间缩短有 32％，同时在其他 64 位平台上，也有类似或更高的启动性能提升。

>  JAVA12中的新特性

JDK 12之前，想要利用CDS的用户，即使仅使用JDK中提供的默认类列表，也必须java -Xshare:dump 作为额外的步
骤来运行。

Java 12 针对 64 位平台下的 JDK 构建过程进行了增强改进，使其默认生成类数据共享（CDS）归档，以进一步达到
改进应用程序的启动时间的目的，同时也避免了需要手动运行：java -Xshare:dump 的需要，修改后的 JDK 将在
${JAVA_HOME}/lib/server 目录中生成一份名为classes.jsa的默认archive文件(大概有18M)方便大家使用。

当然如果需要，也可以添加其他 GC 参数，来调整堆大小等，以获得更优的内存分布情况，同时用户也可以像之前一
样创建自定义的 CDS 存档文件。





### 5_增加:支持UNICODE11

JDK 12版本包括对Unicode 11.0.0的支持。在发布支持Unicode 10.0.0的JDK 11之后，Unicode 11.0.0引
入了以下JDK 12中包含的新功能： 684 new characters,   11 new blocks.  7 new scripts.
其中：



684个新字符，包含以下重要内容：

* 66个表情符号字符（66 emoji characters）

* Copyleft符号（Copyleft symbol）

* 评级系统的半星（Half stars for rating systems）

* 额外的占星符号（Additional astrological symbols）

* 象棋中国象棋符号（Xiangqi Chinese chess symbols）

  

7个新脚本：

* Hanifi Rohingya
* Old Sogdian
* Sogdian
* Dogra
* Gunjala Gondi
* Makasar
* Medefaidrin

11个新块，包括上面列出的新脚本的7个块和以下现有脚本的4个块：

* 格鲁吉亚扩展（Georgian Extended）
* 玛雅数字（Mayan Numerals）
* 印度Siyaq数字（Indic Siyaq Numbers）
* 国际象棋符号（Chess Symbols）



### 6_其他新增

* Collectors新增teeing方法用于聚合两个downstream的结果
* CompletionStage新增exceptionallyAsync、exceptionallyComposeAsync方法，允许方法体在异步线程执
  行，同时新增了exceptionallyCompose方法支持在exceptionally的时候构建新的CompletionStage。
* ZGC: Concurrent Class Unloading
  * ZGC在JDK11的时候还不支持class unloading，JDK12对ZGC支持了Concurrent Class Unloading，默认是
    开启，使用-XX:-ClassUnloading可以禁用

* 新增-XX:+ExtensiveErrorReports
  * -XX:+ExtensiveErrorReports可以用于在jvm crash的时候收集更多的报告信息到hs_err.log文件中，
    product builds中默认是关闭的，要开启的话，需要自己添加-XX:+ExtensiveErrorReports参数

* 新增安全相关的改进
  * 支持java.security.manager系统属性，当设置为disallow的时候，则不使用SecurityManager以提升性
    能，如果此时调用System.setSecurityManager则会抛出UnsupportedOperationExceptionkeytool新增-
    groupname选项允许在生成key pair的时候指定一个named group新增PKCS12 KeyStore配置属性用于自
    定义PKCS12 keystores的生成Java Flight Recorder新增了security-related的event支持ChaCha20 and
    Poly1305 TLS Cipher Suites

### 7_ 移除项



移除com.sun.awt.SecurityWarnin；
移除FileInputStream、FileOutputStream、- Java.util.ZipFile/Inflator/Deflator的finalize方法；
移除GTE CyberTrust Global Root；
移除javac的-source, -target对6及1.6的支持，同时移除--release选项；

### 8_废弃项

废弃的API列表见deprecated-list
废弃-XX:+/-MonitorInUseLists选项
废弃Default Keytool的-keyalg值







# JAVA13

##  概述

2019年9月17日，国际知名的OpenJDK开源社区发布了Java编程语言环境的最新版本OpenJDK13。

``` java
Features：总共有5个新的JEP(JDK Enhancement Proposals):
http://openjdk.java.net/projects/jdk/13/
```

Features

``` java
350:Dynamic CDS Archives
动态CDS档案
351:ZGC: Uncommit Unused Memory
ZGC:取消使用未使用的内存
353:Reimplement the Legacy Socket API
重新实现旧版套接字API
354:Switch Expressions (Preview)
switch表达式（预览）
355:Text Blocks (Preview)
文本块
```



## 一 语法层面的变化



### 1_switch表达式(预览)

在JDK 12中引入了Switch表达式作为预览特性。JDK 13提出了第二个switch表达式预览。JEP 354修改了这个特性，
它引入了**yield语句，用于返回值**。这意味着，switch表达式(返回值)应该使用yield, switch语句(不返回值)应该使用
break。
在 JDK 12中有一个，但是要进行一个更改：要从 switch 表达式中生成一个值 break，要删除with value语句以支持a
yield 声明。目的是扩展，switch 以便它可以用作语句或表达式，因此两个表单既可以使用 case ... : 带有连贯符号的
传统标签，也可以使用新 case … -> 标签，而不需要通过，还有一个新的语句用于从 switch 表达式中产生值。这些
更改将简化编码并为模式匹配做好准备。

在以前，我们想要在switch中返回内容，还是比较麻烦的，一般语法如下：

``` java
String x = "3";
int i;
switch (x) {
	case "1":
		i=1;
		break;
	case "2":
		i=2;
		break;
	default:
		i = x.length();
		break;
}
System.out.println(i);
```

在JDK13中使用以下语法：

``` java
String x = "3";
int i = switch (x) {
	case "1" -> 1;
	case "2" -> 2;
	default -> {
		yield 3;
	}
};
System.out.println(i);
```

或者

``` java
String x = "3";
int i = switch (x) {
	case "1":
		yield 1;
	case "2":
		yield 2;
	default:
		yield 3;
};
System.out.println(i);
```

在这之后，switch中就多了一个关键字用于跳出switch块了，那就是yield，他用于返回一个值。和return的区别在
于：**return会直接跳出当前循环或者方法，而yield只会跳出当前switch块。**



### 2_文本块(预览)

> 概念

在JDK 12中引入了Raw String Literals特性，但在发布之前就放弃了。这个JEP与引入多行字符串文字（text block）
在意义上是类似的。

这条新特性跟 Kotlin 里的文本块是类似的。

> 问题

在Java中，通常需要使用String类型表达HTML，XML，SQL或JSON等格式的字符串，在进行字符串赋值时需要进行
转义和连接操作，然后才能编译该代码，这种表达方式难以阅读并且难以维护。**文本块就是指多行字符串，例如一段格式化后的xml、json等。而有了文本块以后，用户不需要转义，Java能自动搞定。因此，文本块将提高Java程序的可读性和可写性。**

> 目标

简化跨越多行的字符串，避免对换行等特殊字符进行转义，简化编写Java程序。
增强Java程序中字符串的可读性。

定义一段HTML代码

``` html
<html>
	<body>
		<a href="http://www.mashibing.com">马士兵教育</a>
	</body>
</html>
```

将这段代码放入java的String中,会出现如下效果

``` java
String words =
    "<html>\n" +
	"\t<body>\n" +
	"\t\t<a href=\"http://www.mashibing.com\">马士兵教育</a>\n" +
	"\t</body>\n" +
	"</html>";
```

自动将空格换行缩进和特殊符号进行了转义,但是在JDK13中可以使用这样的语法了:

``` java
String words = """
    <html>
        <body>
            <a href="http://www.mashibing.com">马士兵教育</a>
        </body>
    </html>""";
```

使用"""作为文本块的开始符和结束符，在其中就可以放置多行的字符串，不需要进行任何转义。看起来就十分清爽
了。
如常见的SQL语句：

``` sql
select empno,ename,sal,deptno
from emp
where deptno in (40,50,60)
order by deptno asc
```

原来的方式

``` java
String query = "select empno,ename,sal,deptno\n" +
"from emp\n" +
"where deptno in (40,50,60)\n" +
"order by deptno asc";
```

现在方式

``` java
String newQuery = """
select empno,ename,sal,deptno
from emp
where deptno in (40,50,60)
order by deptno asc
""";
```

> 语法细节1 基本使用

* 文本块是Java语言中的一种新文字。它可以用来表示任何字符串，并且提供更大的表现力和更少的复杂性。
* 文本块由零个或多个字符组成，由开始和结束分隔符括起来。
  * 开始分隔符是由三个双引号字符（"""），后面可以跟零个或多个空格，最终以行终止符结束。文本块内容
    以开始分隔符的行终止符后的第一个字符开始。
  * 结束分隔符也是由三个双引号字符（"""）表示，文本块内容以结束分隔符的第一个双引号之前的最后一个
    字符结束。
* 文本块中的内容可以直接使用"，"但不是必需的。
* 文本块中的内容可以直接包括行终止符。允许在文本块中使用 \n，但不是必需的。例如，文本块：

``` java
"""
line1
line2
line3
"""
```

相当于

```java
"line1\nline2\nline3\n"
```

或者手相当于一个字符串用+拼接

``` java
"line1\n" +
"line2\n" +
"line3\n"
```

文本块可以表示空字符串，但不建议这样做，因为它需要两行源代码：

``` java
String empty = """
""";
```

以下是错误格式的文本块

``` java
String a = """"""; // 开始分隔符后没有行终止符
String b = """ """; // 开始分隔符后没有行终止符
String c = """
			"; // 没有结束分隔符
String d = """
abc \ def
"""; // 含有未转义的反斜线（请参阅下面的转义处理）
```

在运行时，文本块将被实例化为String的实例，就像字符串一样。从文本块派生的String实例与从字符串派生的实例
是无法区分的。具有相同内容的两个文本块将引用相同的String实例，就像字符串一样。

> 语法细节2  **编译器在编译时,会删除多余的空格**

下面这段代码中，我们用.来表示我们代码中的的空格，而这些位置的空格就是多余的。

``` java
String html = """
..............<html>
.............. <body>
.............. <p>Hello, world</p>
.............. </body>
..............</html>
..............""";
```

多余的空格还会出现在每一行的结尾，特别是当你从其他地方复制过来时，更容易出现这种情况，比如下面的代码：

``` java
String html = """
..............<html>...
.............. <body>
.............. <p>Hello, world</p>....
.............. </body>.
..............</html>...
..............""";
```

PS每行文字后面的空格,编译器会自动帮助我们去掉,但是开头部分的空格和结束的"""; 前面的空格数有关, """;前面有几个空格,就会自动帮助我们去掉每一行前面的几个空格

这些多余的空格对于程序员来说是看不到的，但是他又是实际存在的，所以如果编译器不做处理，可能会导致程序员
看到的两个文本块内容是一样的，但是这两个文本块却因为存在这种多余的空格而导致差异，比如哈希值不相等。

> 语法细节3 转义字符

允许开发人员使用 \n，\f 和\r 来进行字符串的垂直格式化，使用 \b和 \t进行水平格式化。比如下面的代码是合法
的：

``` java
String html = """
		<html>\n
			<body>\n
				<p>Hello, world</p>\n
			</body>\n
		</html>\n
""";
```

请注意，在文本块内自由使用"是合法的。例如:

``` java
String story = """
"When I use a word," Humpty Dumpty said,
in rather a scornful tone, "it means just what I
choose it to mean - neither more nor less."
"The question is," said Alice, "whether you
can make words mean so many different things."
"The question is," said Humpty Dumpty,
"which is to be master - that's all."
""";
```

但是，三个"字符的序列需要进行转义至少一个"以避免模仿结束分隔符：

``` java
String code =
"""
String text = \"""
A text block inside a text block
\""";
""";
```

> 语法细节4 文本块连接

可以在任何可以使用字符串的地方使用文本块。例如，文本块和字符串可以相互连接：

``` java
String code = "public void print(Object o) {" +
	"""
		System.out.println(Objects.toString(o));
	}
	""";
```

但是，涉及文本块的连接可能变得相当笨重。以下面文本块为基础：

``` java
String code = """
	public void print(Object o) {
		System.out.println(Objects.toString(o));
	}
""";
```

假设我们想把上面的Object改为来自某一变量，我们可能会这么写：

``` java
String code = """
		public void print(""" + type + """
		o) {
			System.out.println(Objects.toString(o));
		}
		""";
```

可以发现这种写法可读性是非常差的，更简洁的替代方法是使用String :: replace或String :: format，比如：

``` java
String code = """
		public void print($type o) {
			System.out.println(Objects.toString(o));
		}
		""".replace("$type", type);
```

``` java
String code = String.format("""
		public void print(%s o) {
			System.out.println(Objects.toString(o));
		}
		""", type);
```

另一个方法是使用String :: formatted，这是一个新方法，比如：

``` java
String source = """
		public void print(%s object) {
			System.out.println(Objects.toString(object));
		}
		""".formatted(type);
```



## 二 API层次的变化

### 1_重新实现旧版套接字API

> 目前的问题

重新实现了古老的 Socket 接口。现在已有的 java.net.Socket 和 java.net.ServerSocket 以及它们的实现类，都可以
回溯到 JDK 1.0 时代了。

* 它们的实现是混合了 Java 和 C 的代码的，维护和调试都很痛苦。
* 实现类还使用了线程栈作为 I/O 的缓冲，导致在某些情况下还需要增加线程栈的大小。
* 支持异步关闭，此操作是通过使用一个本地的数据结构来实现的，这种方式这些年也带来了潜在的不稳定性和
  跨平台移植问题。该实现还存在几个并发问题，需要彻底解决。

在未来的网络世界，要快速响应，不能阻塞本地方法线程，当前的实现不适合使用了。

> 新的实现类

全新实现的 NioSocketImpl 来替换JDK1.0的PlainSocketImpl。此实现与NIO实现共享相同的内部基础结构,并且与现有的缓冲区高速缓存机制集成在一起,因此不需要使用线程堆栈.除此之外,他还有一些其他更改,例如使用java.lang.ref.Cleaner机制关闭套接字,实现在尚未关闭的套接字上进行了垃圾收集,以及在轮训时套接字出于非阻塞模式时处理超时操作等方法

* 它便于维护和调试，与 NewI/O (NIO) 使用相同的 JDK 内部结构，因此不需要使用系统本地代码。
* 它与现有的缓冲区缓存机制集成在一起，这样就不需要为 I/O 使用线程栈。
* 它使用 java.util.concurrent 锁，而不是 synchronized 同步方法，增强了并发能力。
* 新的实现是Java 13中的默认实现，但是旧的实现还没有删除，可以通过设置系统属性jdk.net.usePlainSocketImpl来切换到旧版本。

> 代码说明

运行一个实例化Socket和ServerSocket的类将显示这个调试输出。这是默认的(新的)。

``` java
/**
 * The abstract class {@code SocketImpl} is a common superclass
 * of all classes that actually implement sockets. It is used to
 * create both client and server sockets.
 *
 * @implNote Client and server sockets created with the {@code Socket} and
 * {@code SocketServer} public constructors create a system-default
 * {@code SocketImpl}. The JDK historically used a {@code SocketImpl}
 * implementation type named "PlainSocketImpl" that has since been replaced by a
 * newer implementation. The JDK continues to ship with the older implementation
 * to allow code to run that depends on unspecified behavior that differs between
 * the old and new implementations. The old implementation will be used if the
 * Java virtual machine is started with the system property {@systemProperty
 * jdk.net.usePlainSocketImpl} set to use the old implementation. It may also be
 * set in the JDK's network configuration file, located in {@code
 * ${java.home}/conf/net.properties}. The value of the property is the string
 * representation of a boolean. If set without a value then it defaults to {@code
 * true}, hence running with {@code -Djdk.net.usePlainSocketImpl} or {@code
 * -Djdk.net.usePlainSocketImpl=true} will configure the Java virtual machine
 * to use the old implementation. The property and old implementation will be
 * removed in a future version.
 *
 * @since   1.0
 */
public abstract class SocketImpl implements SocketOptions {
    private static final boolean USE_PLAINSOCKETIMPL = usePlainSocketImpl();

    private static boolean usePlainSocketImpl() {
        PrivilegedAction<String> pa = () -> NetProperties.get("jdk.net.usePlainSocketImpl");
        String s = AccessController.doPrivileged(pa);
        return (s != null) && !s.equalsIgnoreCase("false");
    }
```

SocketImpl的USE_PLAINSOCKETIMPL取决于usePlainSocketImpl方法，而它会从NetProperties读取
jdk.net.usePlainSocketImpl配置，如果不为null且不为false，则usePlainSocketImpl方法返回true；
createPlatformSocketImpl会根据USE_PLAINSOCKETIMPL来创建PlainSocketImpl或者NioSocketImpl。







## 三 其他变化

### 1_ZGC取消未使用的内存

> G1和Shenandoah

JVM的GC释放的内存会还给操作系统吗？
GC后的内存如何处置，其实是取决于不同的垃圾回收器。因为把内存还给OS，意味着要调整JVM的堆大小，这个过
程是比较耗费资源的。

* Java12的346: Promptly Return Unused Committed Memory from G1新增了两个参数分别是
  G1PeriodicGCInterval及G1PeriodicGCSystemLoadThreshold用于GC之后重新调整Java heap size，然后将多
  余的内存归还给操作系统

* Java12的189: Shenandoah: A Low-Pause-Time Garbage Collector (Experimental)拥有参数
  -XX:ShenandoahUncommitDelay=来指定ZPage的page cache的失效时间，然后归还内存

HotSpot的G1和Shenandoah这两个GC已经提供了这种能力，并且对某些用户来说，非常有用。因此，Java13则给
ZGC新增归还unused heap memory给操作系统的特性。

>ZGC的使用背景

在JDK 11中，Java引入了ZGC，这是一款可伸缩的低延迟垃圾收集器，但是当时只是实验性的。号称不管你开了多大
的堆内存，它都能保证在 10 毫秒内释放 JVM ，不让它停顿在那。但是，当时的设计是它不能把内存归还给操作系
统。对于比较关心内存占用的应用来说，肯定希望进程不要占用过多的内存空间了，所以这次增加了这个特性。

![1631003992014](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631003992014.png)

在Java 13中，JEP 351再次对ZGC做了增强，将没有使用的堆内存归还给操作系统。ZGC当前不能把内存归还给操作
系统，即使是那些很久都没有使用的内存，也只进不出。这种行为并不是对任何应用和环境都是友好的，尤其是那些
内存占用敏感的服务，例如：

1. 按需付费使用的容器环境；
2. 应用程序可能长时间闲置，并且和很多其他应用共享和竞争资源的环境；
3. 应用程序在执行期间有非常不同的堆空间需求，例如，可能在启动的时候所需的堆比稳定运行的时候需要更多
的堆内存。





> 使用细节

ZGC的堆由若干个Region组成，每个Region被称之为ZPage。每个Zpage与数量可变的已提交内存相关联。当ZGC压
缩堆的时候，ZPage就会释放，然后进入page cache，即ZPageCache。这些在page cache中的ZPage集合就表示没
有使用部分的堆，这部分内存应该被归还给操作系统。回收内存可以简单的通过从page cache中逐出若干个选好的
ZPage来实现，由于page cache是以LRU（Least recently used，最近最少使用）顺序保存ZPage的，并且按照尺寸
（小，中，大）进行隔离，因此逐出ZPage机制和回收内存相对简单了很多，主要挑战是设计关于何时从page
cache中逐出ZPage的策略。

一个简单的策略就是设定一个超时或者延迟值，表示ZPage被驱逐前，能在page cache中驻留多长时间。这个超时时
间会有一个合理的默认值，也可以通过JVM参数覆盖它。Shenandoah GC用了一个类型的策略，默认超时时间是5分
钟，可以通过参数-XX:ShenandoahUncommitDelay = milliseconds覆盖默认值。

像上面这样的策略可能会运作得相当好。但是，用户还可以设想更复杂的策略：不需要添加任何新的命令行选项。例
如，基于GC频率或某些其他数据找到合适超时值的启发式算法。JDK13将使用哪种具体策略目前尚未确定。可能最
初只提供一个简单的超时策略，使用-XX:ZUncommitDelay = seconds选项，以后的版本会添加更复杂、更智能的
策略（如果可以的话）。

uncommit能力默认是开启的，但是无论指定何种策略，ZGC都不能把堆内存降到低于Xms。这就意味着，如果Xmx
和Xms相等的话，这个能力就失效了。-XX:-ZUncommit这个参数也能让这个内存管理能力失效。



### 2__动态CDS档案（动态类数据共享归档）_

> 作用 

**在JAVA应用程序在程序执行结束时动态归档类. 归档的类将包括默认基层CDS归档中不存在的所有已加载应用程序类和类库**



CDS，是java 12的特性了，可以让不同 Java 进程之间共享一份类元数据，减少内存占用，它还能加快应用的启动速
度。**而JDK13的这个特性支持在Java application执行之后进行动态archive。**存档类将包括默认的基础层CDS存档
中不存在的所有已加载的应用程序和库类。也就是说，在Java 13中再使用AppCDS的时候，就不再需要这么复杂了。
该提案处于目标阶段，旨在提高AppCDS的可用性，并消除用户进行运行时创建每个应用程序的类列表的需要。

``` java
# JVM退出时动态创建共享归档文件：导出jsa
java -XX:ArchiveClassesAtExit=hello.jsa -cp hello.jar Hello
# 用动态创建的共享归档文件运行应用:使用jsa
java -XX:SharedArchiveFile=hello.jsa -cp hello.jar Hello
```

> 目的

JAVA13 这次对CDS增强的目的

* 改善APPCDS的可用性,减少用户每次都要创建一个类列表的需要
* 通过开启 -Xshare:dump 选项来开启静态归档,使用类列表仍然行得通,包含内置的类加载信息和用户定义的类加载信息

> 意义

在JDK13中做的增强,可以只开启命令行选项完成上述过程,在程序运行的时候,动态评估哪些类需要归档,同时支持内置的类加载器和用户定义的类加载器

在第一次程序执行完成后,会自动的将类进行归档,后续启动项目的时候也无需指定要使用哪些归档,整个过程看起来更加透明



### 3_增加废弃和移除

#### 增加项

* 添加FileSystems.newFileSystem(Path, Map<String, ?>) Method
* 新的java.nio.ByteBuffer Bulk get/put Methods Transfer Bytes Without Regard to Buffer Position
* 支持Unicode 12.1
* 添加-XX:SoftMaxHeapSize Flag，目前仅仅对ZGC起作用
* ZGC的最大heap大小增大到16TB

#### 移除项

* 移除awt.toolkit System Property
* 移除Runtime Trace Methods
* 移除-XX:+AggressiveOpts
* 移除Two Comodo Root CA Certificates、Two DocuSign Root CA Certificates
* 移除内部的com.sun.net.ssl包

#### 废弃项

* 废弃-Xverify:none及-noverify
* 废弃rmic Tool并准备移除
* 废弃javax.security.cert并准备移除



# JAVA14

## 概述

Oracle在2020年3月17日宣布JAVA14 全面上市,JAVA14通过每六个个月发布一次新功能,为企业和开发人员社区提供增强功能,继续了Oracle加快创新的承诺. 最新的JAVA开发工具包提供了新功能,其中包括两项备受期待的新预览功能,实例匹配的匹配模式(JEP 305) 和记录(JEP 359),以及文本块的第二个预览(JEP 368),此外,最新的JAVA版本增加了对switch表达式的语言支持,公开了,用于持续监控JDK Flight Recorder数据的新API,将低延迟的Z垃圾收集器的可用性扩招到了macOS和Windows,并在孵化器模块中添加了包装完备的java应用程序和新的外部内存访问API,以安全高效的访问JAVA对外部的内存

我们可以在openjdk灌完中观察到JDK14发布的详细官方计划和具体新特性详情,地址如下

https://openjdk.java.net/projects/jdk/14/

JAVA14 一共发行了16个JEP(JDK Enhancement Proposals,JDK 增强提案)

![1631073757234](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631073757234.png)

![1631073770033](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631073770033.png)

![1631074436015](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631074436015.png)

>  语言特性7项目  



* switch表达式(标准) 
* 友好的空指针异常
* 非易失性字节缓冲区
* record
* instanceof模式匹配
* 文本块改进 二次预览
* 外部存储API

> 垃圾回收修改

* G1的NUMA内存分配优化

>  新增工具

* JAVA打包工具 孵化
* JFR事件流

>  增加废弃和移除

* MacOS系统上的ZGC试验
* windows系统上的ZGC实验
* 弃用Parallel Scavenge 和Serial Old垃圾收集算法
* 弃用Solaris和SPCRC端口
* 移除CMS垃圾收集器
* 删除Pack200工具和API



## 一 语法层面的变化

### 1_instanceof的模式匹配(预览)

Pattern Matching for instanceof (Preview)

以往我们使用instanceof运算符都是先判断,然后在进行强转,例如我们查看String的equals方法源码

``` java
 public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        // 先进行类型的判断 
        if (anObject instanceof String) {
            // 然后进行强转
            String aString = (String)anObject;
            if (!COMPACT_STRINGS || this.coder == aString.coder) {
                return StringLatin1.equals(value, aString.value);
            }
        }
        return false;
    }
```

需要先判断类型,然后强转,还要声明一个本地变量,语法比较麻烦.比较理想的状态是,在执行类型检测的时候同时执行类型转换

JEP305 新增了使instanceof运算符具有匹配的能力. 模式匹配能够是程序的通用逻辑更加简洁,代码更加简单,同时在做类型判断和类型转换的时候也更加安全.详情如下

JAVA14 提供了新的解决方案: 新的instanceof模式匹配,新的模式匹配语法是: 在instanceof的类型之后添加了变量. 如果对obj的类型检查通过,obj会被转换成后面的变量表示的数据类型. 数据类型的声明仅仅书写一次即可

``` java 
		Object obj ="hello java";
        if(obj instanceof String str){
            System.out.println(str);
        }else{
            System.out.println("not a String");
        }
```

上述语法的判断逻辑时,如果obj是String类型,则会转换为后面的str,如果不是,则执行else,注意,此时的str仅仅是if语句块里的局部变量,在else语句块中不可用

```java
        Object obj ="hello java";
        // 这里做的是取反运算
        if(!(obj instanceof String str) ){
            System.out.println("not a String");
            //System.out.println(str);// 这里不能使用str
        }else{
            System.out.println(str);// 这里可以使用str
        }
```

但是如果if语句中使用了! 这种取反运算,那么逻辑上就是相反的,这个时候else才是相当于成功转换了,所以在else中可以使用str,if中不可以使用str

``` java
		Object obj =new Date();// "hello java";
        if(obj instanceof String str && str.length()>2){
            System.out.println(str);
        }else{
            System.out.println("not a String or length <=2");
        }
```

上述语句块中,如果if中的判断逻辑比较复杂,是可以在后续的其他条件中使用str变量进行判断的,但是注意这里的运算符是短路与运算,就是要保证后面在使用str时,已经完成了转换,如果使用短路或运算,无法保证str是可以成功转换的,是不允许的,如下面的代码,就是错的

``` java
		Object obj =new Date();// "hello java";
        if(obj instanceof String str || str.length()>2){
            System.out.println(str);
        }else{
            System.out.println("not a String or length <=2");
        }
```

总之: if语句块中的小括号内,要保证成功的进行了转换才可以在if语句库中使用转换的对象,否则不可以

通过这个模式匹配,我们可以简化在类中重写的equals方法

``` java
import java.util.Objects;
class Person{
    private String pname;
    private Integer page;
    public Person(String pname,Integer page){
        this.pname =pname;
        this.page=page;
    }
    @Override
    public boolean equals(Object obj){
        return obj instanceof Person p && Objects.equals(this.pname,p.pname)&& Objects.equels(this.page,p.page);
    }
}
```



### 2_switch表达式(标准)

java的Switch语句是一个一直在变化的语法,可能是因为之前的不够强大,在JAVA14中,我们依然可以看到对于switch的语法优化.

我们简单整理一下switch语句在各个版本中的特点

JAVA5 switch变量类型可以使用枚举了

JAVA7 switch变量类型中可以使用String

JAVA11 switch语句可以自动省略break导致的贯穿提示警告 case L ->

JAVA12 switch语句可以作为表达式,用变量接收结果,可以省略break

JAVA13 switch中可以使用yield关键字停止switch语句块

JAVA14 JEP361switch表达式(标准)是独立的,不依赖于JEP 325 和 JEP 354,也就是说这里开始,之前学习的switch语句的语法成为一个正式的标准.未来是否有更多的改进,我们可以拭目以待



JDK12对缺省break的贯穿弱点进行了改进,case: 改成 case  L -> ,这样即使不写也不会贯穿了,而且可以作为表达式返回结果

``` java
var grade ="a";
var res =switch(grade){
        case "a" -> "优秀";
        case "b" -> "良好";
    	case "c" -> "一般";
        case "d" -> "及格";
        default -> "no such grade";
}


```



JAVA12 开始也可以进行多值匹配的支持

``` java
var grade ="a";
var res =switch(grade){
        case "a","b" -> "优秀";
    	case "c" -> "一般";
        case "d" -> "及格";
        default -> "no such grade";
}
```

JAVA13开始可以使用 yield返回结果,这里的case后面仍然是: 

``` java
String x = "3";
int i = switch (x) {
	case "1":
		yield 1;
	case "2":{
        System.out.println("");
        yield 2;
    }
		
	default:
		yield 3;
};
System.out.println(i);
```



### 3_文本块改进(第二次预览)

Text Blocks(Second Preview)



> 问题

文本块是在JAVA13中开始了第一次的预览,目标是在字符串中可以更好的表达 HTML XML SQL或者JSON格式的字符串,减少各种的不相关一些空格换行符号,字符串转义和字符串加号的拼接,在JAVA14中,增加了两个escape sequence ,分别是 \ <line-terminator>(取消换行操作)  与\s escape sequence(增加空格),文本块进行了第二次预览,进一步调了JAVA程序书写大段字符串文本的可读性和方便性



> 目标

* 简化跨越多行的字符串,避免对换行等特殊字符进行转义,简化java程序
* 增强java程序中用字符串表示其他语言代码的可读性
* 解析新的转义序列

``` java
String textBlock= """
               <!DOCTYPE html>
               <html lang="en">
               <head>
                   <meta charset="UTF-8">
                   <title>Title</title>
               </head>\s\
               <body>
                              
               </body>\s
               </html>
               """;
        System.out.println(textBlock);
```









###  4_Records 记录类型(预览 JEP359)

通过Record增强java编程语言,Record提供了一种紧凑的语法来声明类,这些类是浅层不可变数据的透明持有者.

> 问题分析

我们经常听到这样的抱怨:"JAVA太冗长","JAVA规矩多". 最明显的就是最为简单数据载体的类,为了写一个数据类,开发人员必须编写许多低价值,重复,且容易出错的代码,构造函数,getter setter访问器,equals,hashcode,toString这些东西,尽管IDE可以提供一些插件和手段优化,但是仍然没有改变这些代码依然存在,需要操作的事实

传统的类如下

```

class Person{
    private Integer pid;
    private String pname;
    private Integer page;

    @Override
    public String toString() {
        return "Person{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", page=" + page +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(pid, person.pid) && Objects.equals(pname, person.pname) && Objects.equals(page, person.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, pname, page);
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Person() {
    }

    public Person(Integer pid, String pname, Integer page) {
        this.pid = pid;
        this.pname = pname;
        this.page = page;
    }
}

```

就算是使用IDE的快捷键,这些代码也是臃肿的

>  Records记录类型语法

Record是java的一种新的类型,同枚举一样,Record也是对类的一种限制,Record放弃了类通常享有的特性:将API和表示解耦,但是作为回报,record使数据类型变得非常简洁,一般可以帮助我们定义一些简单的用于传递数据的实体类

一个record具有名称和状态描述,状态描述声明了record的组成部分

```
record Person(String name ,int age){}
```

因为record在与以上是数据的简单透明持有者,所以record会自动获取很多的标准成员

- 状态声明中的每个成员,都是一个private final的字段,属性设置值则不可修改
- 状态声明中的每个组件的公共读取访问方法,该方法和组件具有相同的名字,get方法和属性名一致
- 一个公共的构造函数,其签名与状态声明相同,构造方法和签名合二为一
- equals和hashcode的实现
- toString的实现
- record提供的默认是一个全参的构造器

测试代码如下

```
package com.msb.test;

import java.util.Objects;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test2 {
    public static void main(String[] args) {
        Person p =new Person(1,"张三",10);
        Person p2 =new Person(1,"张三",10);
        System.out.println(p.pname());
        System.out.println(p);
        System.out.println(p.hashCode());
        System.out.println(p2.hashCode());
        System.out.println(p.equals(p2));
    }
}
record Person(Integer pid,String pname ,Integer page){};
```

测试结果如下

```
张三
Person[pid=1, pname=张三, page=10]
24022530
24022530
true

```

![1631091181735](file://E:/msbEdu/%E5%BD%95%E5%88%B6%E8%A7%86%E9%A2%91/%E7%B2%BE%E5%93%81%E5%B0%8F%E8%AF%BE/JDK%E6%96%B0%E7%89%B9%E5%BE%81/JDK%E5%90%84%E4%B8%AA%E7%89%88%E6%9C%AC%E7%89%B9%E5%BE%81/JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631091181735.png?lastModify=1631082891)





> Records的一些限制

records类是隐含的final类,并且不是抽象类,records不能拓展任何类,不能被继承,声明的任何其他字段都必须是静态的

,records的API仅仅能由其状态描述定义(通过属性定义)

> 在record中声明额外的变量类型

也可以显示声明从状态描述自动派生的任何成员,可以在没有正式参数列表的情况下声明构造函数,并且在正常的构造函数主体正常完成是调用隐式初始化,这样就可以在显示构造函数中仅执行其参数的验证逻辑,并且省略字段的初始化

测试代码如下

```
package com.msb.test;

import java.util.Objects;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test2 {
    public static void main(String[] args) {
        Person p =new Person(1,"张三",10);
        Person p2 =new Person(1,"张三",10);
        System.out.println(p.pname());
        System.out.println(p);
        System.out.println(p.hashCode());
        System.out.println(p2.hashCode());
        System.out.println(p.equals(p2));

    }
}
record Person(Integer pid,String pname ,Integer page){
    // 定义额外的变量必须是静态的,不能定义成员变量
    private static String name;
    public static void setName(String name){
        Person.name=name;
    }
    // 可以定义其他实例方法
    public void eat(){
        System.out.println("eat");
    }
    // 可以定义其他静态方法
    public static void methodA(){
        System.out.println("methoA");
    }
    // 这里是构造函数,默认就是全参的构造函数,和record声明的参数列表是一致的,
    // 这里可以使用全参构造函数中的所有参数
    // 在这里会默认执行参数给属性赋值操作,就是在这里默认会有this.pid=pid,this.pname=pname,this.page=page
    public Person{
        System.out.println(pid);
        System.out.println(pname);
        System.out.println(page);
    }
};
```

## 二 API层面的变化



## 三 关于GC

### 1_G1的NUMA内存分配优化

>  NUMA-Aware Memory Allocation for G1 

NUMA

NUMA就是非统一内存访问架构（英语：non-uniform memory access，简称NUMA），是一种为多处理器的电脑设计的内存架构，内存访问时间取决于内存相对于处理器的位置。在NUMA下，处理器访问它自己的本地内存的速度比非本地内存（内存位于另一个处理器，或者是处理器之间共享的内存）快一些。如下图所示，Node0中的CPU如果访问Node0中的内存，那就是访问本地内存，如果它访问了Node1中的内存，那就是远程访问，性能较差：

![1631094038083](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631094038083.png)



非统一内存访问架构的特点是：

被共享的内存物理上是分布式的，所有这些内存的集合就是全局地址空间。所以处理器访问这些内存的时间是不一样的，显然访问本地内存的速度要比访问全局共享内存或远程访问外地内存要快些。另外，NUMA中内存可能是分层的：本地内存，群内共享内存，全局共享内存。

> 目标

JEP345希望通过实现NUMA-aware的内存分配，改进G1在大型机上的性能。

现代的multi-socket服务器越来越多都有NUMA，意思是，内存到每个socket的距离是不相等的，内存到不同的socket之间的访问是有性能差异的，这个距离越长，延迟就会越大，性能就会越差！（https://openjdk.java.net/jeps/345）

G1的堆组织为固定大小区域的集合。一个区域通常是一组物理页面，尽管使用大页面（通过 -XX:+UseLargePages）时，多个区域可能组成一个物理页面。

如果指定了+XX:+UseNUMA选项，则在初始化JVM时，区域将平均分布在可用NUMA节点的总数上。

在开始时固定每个区域的NUMA节点有些不灵活，但是可以通过以下增强来缓解。为了为mutator线程分配新的对象，G1可能需要分配一个新的区域。它将通过从NUMA节点中优先选择一个与当前线程绑定的空闲区域来执行此操作，以便将对象保留在新生代的同一NUMA节点上。如果在为变量分配区域的过程中，同一NUMA节点上没有空闲区域，则G1将触发垃圾回收。要评估的另一种想法是，从距离最近的NUMA节点开始，按距离顺序在其他NUMA节点中搜索自由区域。

该特性不会尝试将对象保留在老年代的同一NUMA节点上。

JEP 345专门用于实现G1垃圾收集器的NUMA支持，仅用于内存管理（内存分配），并且仅在Linux下。对于NUMA体系结构的这种支持是否也适用于其他垃圾回收器或其他部分（例如任务队列窃取），尚不清楚。

### 2_弃用Serial+CMS,ParNew+Serial Old

由于维护和兼容性测试的成本,在JDK8时将Serial+CMS,ParNew+Serial Old这两个组合声明为废弃(JEP173),并在JDK9中完全取消了这些组合的支持(JEP214)

![1631588534143](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631588534143.png)

ParallelScavenge+SerialOld GC 的GC组合要被标记为Deprecate了

> 官方给出的理由

这个GC组合需要大量的代码维护工作,并且,这个GC组合很少被使用.因为它的使用场景应该是一个很大的Young区和一个很小的Old区,这样的话,Old区用SerialOld GC去收集停顿时间才可以勉强被接受

废弃了Parallelyoung generationGC 与SerialOldGC组合  (-XX:+UseParallelGC  与 -XX:-UseParallelOldGC 配合开启),现在使用-XX:+UseParallelGC -XX:-UseParallelOldGC或者使用  -XX:-UseParallelOldGC会出现如下警告

![1631589225439](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631589225439.png)





### 3_删除CMS

>  删除CMS垃圾回收器

自从G1出现后,CMS在JDK9中就被标记为Deprecate了

> CMS弊端

* 会产生内存碎片,导致并发清除后,用户线程可用空间不足(标记清除算法产生,需要整理算法解决)
* 既然强调了并发(Concurrent) CMS收集器对于CPU资源非常敏感,导致吞吐量降低
* CMS收集器无法处理浮动垃圾(用户线程和垃圾回收线程并发执行,回收时用户线程产生新的垃圾)

当CMS停止工作时,会把 Serial Old GC作为备选方案,而它是JVM中性能最差的垃圾收集方式,停顿几秒甚至十秒都有可能

移除了CMS垃圾收集器,如果继续在JDK14中使用-XX:+UseConcMarkSweepGC 不会报错,仅仅给出一个warning警告

``` java
warning: Ignoring option UseConcMarkSweepGC; support was removed in 14.0
```



> 其他垃圾收集器

G1回收器hotSpot已经默认使用有几年了,我们还看到两个新的GC JAVA11中的ZGC和openJDK12中的Shenandoah,后两者主要特点是:低停顿时间

Shenandoah非Oracle官方发布的,是OpenJDK于JAVA12发布的



| 收集器名称       | 运行时间 | 总停顿时间 | 最大停顿时间 | 平均停顿时间 |
| ---------------- | -------- | ---------- | ------------ | ------------ |
| Shenandoah       | 387.602s | 320ms      | 89.79ms      | 53.01ms      |
| G1               | 312.052s | 11.7s      | 1.24s        | 450.12ms     |
| CMS              | 286.264s | 12.78s     | 4.39s        | 852.26ms     |
| ParallelScavenge | 260.092s | 6.59s      | 3.04s        | 823.75ms     |



### 4_ZGC on macOS and Windows

JAVA14之前,ZGC仅仅支持Linux

基于一些开发部署和测试的需要,ZGC在JDK14中支持在macOS 和windows,因此许多桌面级应用可以从ZGC中受益,目前还是一个实验性版本,要想在macOS 和windows上使用

ZGC与Shenandoah目标非常相似,都是在尽量减少吞吐量的情况下,实现对任意堆大小(TB级)都可以把垃圾收集器停顿时间限制在10毫秒以内的低延迟时间

ZGC 收集器是一款基于Region内存布局的,暂时不设分代的,使用了读屏障,染色指针和内存多重映射等技术来实现并发的标记压缩算法,以低延迟为首要目标的一款垃圾收集器.现在想在macOS 和windows上使用ZGC, 方式如下

-XX:+UnlockExperimentalVMOptions -XX:+UseZGC



关于ZGC的一些测试数据

![1631591809475](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631591809475.png)





![1631592040352](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631592040352.png)



ZGC目前还处于一个实验状态,但是性能非常亮眼,未来将在服务端,大内存,低延迟应用上作为首选的垃圾收集器



## 三 其他变化

### 1_友好的空指针异常提示

NullpointerException是java开发中经常遇见的问题,在JDK14之前的版本中,空指针异常的提示信息就是简答的null,并不会告诉我们更加有用的信息,知识根据异常产生的日志来进行查找和处理,对于很长的引用来说,很难定位到具体是哪个对象为null.

> 演示适用情况

``` java
public class Test2 {
    public static void main(String[] args) {
        Person p =new Person();
        p.cat.eat();

    }
}

class Person{
    public Cat cat;
}
class Cat {
    public void eat(){

    }
}
```

上面的代码在调用eat方法时就会出现空指针异常

![1631086461797](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631086461797.png)

这种提示其实并不是很详细,我们可以在运行代码的时候,加上一段配置,用以展示比较友好的控制成提示信息

``` java
-XX:+ShowCodeDetailsInExceptionMessages
```

输出的信息如下所示

![1631087081162](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631087081162.png)

> 其他适用情况

但是对于更复杂的代码,不适用调用器就无法确定是哪个变量为空

``` java
a.b.c.d=100;
```

仅仅使用文件名和行数,并不能精确的提示到底是哪个变量为null

访问多维数组也会发生类似的情况

``` java
a[j][j][k]=99;
```

这里如果发生npe文件名和行号也是无法精确指出到底是哪一层的数组出现了空指针

再例如

``` java
a.i=b.j;
```

这里出现了npe 如果仅凭文件名和行号,无法确定到底是a的问题还是b的问题

NPE也可能在方法调用中传递

``` java
x().y().i=99;
```

这里如果出现了NPE,那么仅凭行号和文件名也是无法确定到底是x方法还是y方法的问题

接下来简单演示一个代码

``` java
/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test2 {
    public static void main(String[] args) {
       A a =new A();
	   B b=null;
	   a.i=b.j;

    }
}

class A{
	int i;
}
class B{
	int j;
}
```





如果没有友好提示

![1631087823799](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631087823799.png)

如果有友好提示

![1631087848805](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631087848805.png)

这时就可以发现,是j的问题,那么其实就是b对象的问题

### 2_JAVA打包工具  JEP343(孵化阶段)

该特征旨在创建一个用于打包独立java应用程序的工具.JAVA应用的打包和分发一直都是个老大的难题. 用户希望JAVA引用的安装和运行方式和其他应用有相似的体验. 比如,在windows上只需要双击文件就可以运行. JAVA平台本身没有提供实用的工具解决这个问题. 通常都依赖第三方的工具完成,这个JEP的目标就是创建一个简单的JAVa打包工具jpackage. 相对于第三方工具,jpackage只适用于比较简单的场景,不过对很多应用来说已经足够好了.

该jpackage工具将java的应用程序打包到特定的平台的程序包中,该程序包包含所必须的依赖. 该应用程序可以作为普通的jar文件或者模块的集合提供,受支持的特定平台的软件包格式为:

1 Linux deb或者 rpm

2 maxOS: pkg和dmg

3 windows L msi和exe

默认情况下,jpackage以最适合其运行系统的格式生成软件包

>  项目打包-非模块化项目

如果有一个包含jar文件的应用程序,所有的应用程序都位于一个名为lib 的目录总,并且lib/main.jar包含主类,可以通过如下命令打包

``` java
$ jpackage --name myapp -- input lib --main-jar main.jar
```

将以本地系统的默认格式打包应用程序,将生成的打包文件保留到当前目录中. 如果MANIFEST.MF文件中没有main.jar.没有Main-Class属性,则必须显式指定主类

``` java
$ jpackage --name myapp --input lib --main-jar main.jar \ --main-class myapp.Main
```

软件包的名称将为没有app ,尽管软件包文件本身的名称将更长,并以软件包类型皆为,该软件包将包括该应用程序的启动器,也称为myapp .要启动应用程序,启动程序将会从输入目录复制的每个jar文件放在jvm的类路径上

如果您希望默认格式以外的其他格式制作软件包,请使用 --type选项. 例如,要在macOS 上生成pkg文件而不是dmg文件

``` java
$ jpackage --name myapp --input lib --main-jar main.jar --type pkg
```

>项目打包-模块化项目

如果您有一个模块化应用程序,该程序有目录中的模块化jar文件或JMOD文件组成,并且模块中lib包含主类myAPP,则命令为

``` java
$ jpackage -name myapp --moudule-path lib -m myapp
```

如果myAPP模块未标识主类,则必须再次明确

``` java
$ jpackage -name myapp --moudule-path lib -m myapp/myapp.Main
```

### 3_JFR事件流



> 简介

Java Flight Recorder（JFR）是JVM的诊断和性能分析工具。

JAVA14之前只能做离线的分析,现在可以做实时的持续监视

它可以收集有关JVM以及在其上运行的Java应用程序的数据。JFR是集成到JVM中的，所以JFR对JVM的性能影响非常小，我们可以放心的使用它。

一般来说，在使用默认配置的时候，性能影响要小于1%。

JFR的历史很久远了。早在Oracle2008年收购BEA的时候就有了。JFR一般和JMC（Java Mission Control）协同工作。

JFR是一个基于事件的低开销的分析引擎，具有高性能的后端，可以以二进制格式编写事件，而JMC是一个GUI工具，用于检查JFR创建的数据文件。

这些工具最早是在BEA的JRockit JVM中出现的，最后被移植到了Oracle JDK。最开始JFR是商用版本，但是在JDK11的时候，JFR和JMC完全开源了，这意味着我们在非商用的情况下也可以使用了。

而在今天的JDK 14中，引入了一个新的JFR特性叫做JFR Event Streaming，我们将在本文中简要介绍。

先介绍一下JFR和JMC。

> JFR

上面我们简单的介绍了一下JFR。JFR是JVM的调优工具，通过不停的收集JVM和java应用程序中的各种事件，从而为后续的JMC分析提供数据。

Event是由三部分组成的：时间戳，事件名和数据。同时JFR也会处理三种类型的Event：持续一段时间的Event，立刻触发的Event和抽样的Event。

为了保证性能的最新影响，在使用JFR的时候，请选择你需要的事件类型。

JFR从JVM中搜集到Event之后，会将其写入一个小的thread-local缓存中，然后刷新到一个全局的内存缓存中，最后将缓存中的数据写到磁盘中去。

或者你可以配置JFR不写到磁盘中去，但是这样缓存中只会保存部分events的信息。这也是为什么会有JDK14 JEP 349的原因。

开启JFR有很多种方式，这里我们关注下面两种：

1. 添加命令行参数

```java
-XX:StartFlightRecording:<options>
```

启动命令行参数的格式如上所述。

JFR可以获取超过一百种不同类型的元数据。如果要我们一个个来指定这些元数据，将会是一个非常大的功能。所以JDK已经为我们提供了两个默认的profile：default.jfc and profile.jfc。

其中 default.jfc 是默认的记录等级，对JVM性能影响不大，适合普通的，大部分应用程序。而profile.jfc包含了更多的细节，对性能影响会更多一些。

如果你不想使用默认的两个jfc文件，也可以按照你自己的需要来创建。

下面看一个更加完整的命令行参数：

```java
-XX:StartFlightRecording:disk=true,filename=/tmp/customer.jfr,maxage=5h,settings=profile
```

上面的命令会创建一个最大age是5h的profile信息文件。

1. 使用jcmd

命令行添加参数还是太麻烦了，如果我们想动态添加JFR，则可以使用jcmd命令。

```java
jcmd <pid> JFR.start name=custProfile settings=default
jcmd <pid> JFR.dump filename=custProfile.jfr
jcmd <pid> JFR.stop
```

上面的命令在一个运行中的JVM中启动了JFR，并将统计结果dump到了文件中。

上面的custProfile.jfr是一个二进制文件，为了对其进行分析，我们需要和JFR配套的工具JMC。

> JMC

JDK Mission Control 是一个用于对 Java 应用程序进行管理、监视、概要分析和故障排除的工具套件。

在JDK14中，JMC是独立于JDK单独发行的。我们可以下载之后进行安装。

我们先启动一个程序，用于做JFR的测试。

```java
@Slf4j
public class ThreadTest {

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        Runnable runnable= ()->{
            while(true){
                log.info(Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(),e);
                }
            }
        };

        for(int i=0; i<10; i++){
            executorService.submit(runnable);
        }
    }
}
```

很简单的一个程序，启动了10个线程，我们启动这个程序。

然后再去看看JMC的界面：

JMC非常强大，也有很多功能，具体的细节大家可以自己运行去体会。



> JFR事件

JMC好用是好用，但是要一个一个的去监听JFR文件会很繁琐。接下来我们来介绍一下怎么采用写代码的方式来监听JFR事件。

如果我们想通过程序来获取“Class Loading Statistics"的信息，可以这样做。

上图的右侧是具体的信息，我们可以看到主要包含三个字段：开始时间，Loaded Class Count和 Unloaded Class Count。

我们的思路就是使用jdk.jfr.consumer.RecordingFile去读取生成的JFR文件，然后对文件中的数据进行解析。

相应代码如下：

```java
@Slf4j
public class JFREvent {

    private static Predicate<RecordedEvent> testMaker(String s) {
        return e -> e.getEventType().getName().startsWith(s);
    }

    private static final Map<Predicate<RecordedEvent>,
            Function<RecordedEvent, Map<String, String>>> mappers =
            Map.of(testMaker("jdk.ClassLoadingStatistics"),
                    ev -> Map.of("start", ""+ ev.getStartTime(),
                            "Loaded Class Count",""+ ev.getLong("loadedClassCount"),
                            "Unloaded Class Count", ""+ ev.getLong("unloadedClassCount")
                    ));

    @Test
    public void readJFRFile() throws IOException {
        RecordingFile recordingFile = new RecordingFile(Paths.get("/Users/flydean/flight_recording_1401comflydeaneventstreamThreadTest.jfr"));
        while (recordingFile.hasMoreEvents()) {
            var event = recordingFile.readEvent();
            if (event != null) {
                var details = convertEvent(event);
                if (details == null) {
                    // details为空
                } else {
                    // 打印目标
                    log.info("{}",details);
                }
            }
        }
    }

    public Map<String, String> convertEvent(final RecordedEvent e) {
        for (var ent : mappers.entrySet()) {
            if (ent.getKey().test(e)) {
                return ent.getValue().apply(e);
            }
        }
        return null;
    }
}
```

注意，在convertEvent方法中，我们将从文件中读取的Event转换成了map对象。

在构建map时，我们先判断Event的名字是不是我们所需要的jdk.ClassLoadingStatistics，然后将Event中其他的字段进行转换。最后输出。

运行结果：

```java
{start=2021-04-29T02:18:41.770618136Z, Loaded Class Count=2861, Unloaded Class Count=0}
...
```

可以看到输出结果和界面上面是一样的。

> JFR事件流

讲了这么多，终于到我们今天要讲的内容了：JFR事件流。

上面的JFR事件中，我们需要去读取JFR文件，进行分析。但是文件是死的，人是活的，每次分析都需要先生成JFR文件简直是太复杂了。是个程序员都不能容忍。

在JFR事件流中，我们可以监听Event的变化，从而在程序中进行相应的处理。这样不需要生成JFR文件也可以监听事件变化。

```java
    public static void main(String[] args) throws IOException, ParseException {
        //default or profile 两个默认的profiling configuration files
        Configuration config = Configuration.getConfiguration("default");
        try (var es = new RecordingStream(config)) {
            es.onEvent("jdk.GarbageCollection", System.out::println);
            es.onEvent("jdk.CPULoad", System.out::println);
            es.onEvent("jdk.JVMInformation", System.out::println);
            es.setMaxAge(Duration.ofSeconds(10));
            es.start();
        }
    }
```

看看上面的例子。我们通过Configuration.getConfiguration("default")获取到了默认的default配置。

然后通过构建了default的RecordingStream。通过onEvent方法，我们对相应的Event进行处理。



###  4_外部存储器API  (孵化阶段)

通过一个API,以允许java程序安全有效的访问JAVA堆之外的外部存储(堆以外的外部存储空间)

 目的:[JEP 370](https://openjdk.java.net/jeps/370)旨在实现一种提供“通用性”，“安全性”和“确定性”的“外部存储器API”JEP还指出，此外部内存API旨在替代当前使用的方法（ [java.nio.ByteBuffer](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/nio/ByteBuffer.html)和[sun.misc.Unsafe](https://hg.openjdk.java.net/jdk/jdk/file/tip/src/jdk.unsupported/share/classes/sun/misc/Unsafe.java) ）。

 

许多java的库都能访问外部存储,例如 ignite ,mapDB , memcached以及netty的ByteBuffer API ,这样可以:

* 避免垃圾回收相关成本和不可预测性
* 跨多个进程共享内存
* 通过将文件映射到内存中来序列化和反序列化内容

但是JAVAAPI本身没有提供一个令人满意的访问外部内存的解决方案

当java程序需要访问堆内存之外的外部存储是,通常有两种方式

* java.nio.ByteBuffer ,:ByteBuffer 允许使用allcateDirect() 方法在堆内存之外分配内存空间

* sum.misc.Unsafe : Unsafe 中的方法可以直接对内存地址进行操作

ByteBuffer有自己的限制. 首先是ByteBuffer的大小不能超过2G,其次是内存的释放依靠垃圾回收器,Unsafe的API在使用是不安全的,风险很高,可能会造成JVM崩溃.另外Unsafe本身是不被支持的API,并不推荐

 JEP 370的“描述”部分引入了安全高效的API来访问外部外部内存地址,目前该API还是属于孵化阶段,相关API在jdk.incubator.foreign模块的jdk.incubator.foreign包中, 三个API分别是： `MemorySegment` ， `MemoryAddress`和`MemoryLayout` 。 `MemorySegment`用于对具有给定空间和时间范围的连续内存区域进行建模。 可以将`MemoryAddress`视为段内的偏移量。 最后， `MemoryLayout`是内存段内容的程序化描述。



### 5_非易失性映射字节缓冲区

JAVA14增加了一种文件映射模式,用于访问非易失性内存,非易失性内存能够持久保持数据,因此可以利用该特性来改进性能

JEP352 可以使用FileChannelAPI创建引用非易失性内存,(non-volatile memory) 的MappedByteBuffer实例,该JEP建议升级MappedByteBuffer以支持对非易失性存储器的访问,唯一需要的API更改是FileChannel客户端,以请求映射位于NVM的支持的文件系统,而不是常规的文件存储系统上的文件,对MappedByteBuffer API最新的更改意味着他支持允许直接内存更新所需要的所有行为,并提供更高级别的JAVA客户端库所需要的持久性保证,以实现持久性的数据类型

> 目标

NVM为引用程序程序员提供了在程序运行过程中创建和更新程序转台的机会,而减少了输出到持久性介质或者从持久性介质输入是的成本. 对于事务程序特别重要,在事务程序中,需要定期保持不确定状态以启用崩溃恢复.

现有的C库(例如Intel的libpmen),为c程序员提供了对集成NVM的高效访问,它们还一次基础来支持对各种持久性数据类型的简单管理.当前,由于频繁需要进行系统调用或者JNI来调用原始操作,从而确保内存更改是持久的,因此即使禁用JAVA的基础类库也很昂贵.同样的问题限制了高级库的使用.并且,由于C中提供的持久数据类型分配在无法从JAVA直接访问的内存中这一事实而加剧了这一问题.

该特性试图通过允许映射到ByteBuffer的NVM的有效写回来解决第一个问题. 由于java可以直接访问ByteBuffer映射内存,因此可以通过实现与C语言中提供的客户端库等效的客户端库来解决第二个问题,以管理不同持久数据类型存储.

> 初步变更

该JEP使用了JAVASE API的两个增强功能

* 支持 Implementation-defined的映射模式
* MappedByteBuffer::force方法指定范围

> 特定于JDK的API更改

* 通过新模块中的公共API公开新的MApMode枚举值

一个公共扩展枚举ExtendedMapMode将添加到jdk.nio.mapmode程序包

``` java
package jdk.nio.mapmode;
public class ExtendedMapMode{
    private ExtendedMapMode(){
        
    }
    public static final MapMode READ_ONLY_SYNC=  ... ...
    
}
```

在调用FileChannel::map方法创建映射到NVM设备文件上的只读或者写MappedByteBuffer时,可以使用上述的枚举值,如果这些标志在不支持NVM设备文件平台上传递,程序会抛出UnsupportedOperationException异常,在受支持的平台上,及当目标FileChannel实例是通过NVM设备打开的派生文件是,才能传递这些参数,在任何情况下,都会抛出IOException;





# JAVA15

## 概述

2020年9月15日,java15正式发布,(风平浪静的一个版本)共有14个JEP,是时间驱动形式发布的第六个版本.相关文档: https://openjdk.java.net/projects/jdk/15/



![1631670467582](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631670467582.png)

![1631670520496](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631670520496.png)

一个孵化器阶段,三个预览,两个废弃和两个移除





JAVA15参与企业

![1631676405146](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631676405146.png)





> java各版本主要特征

`JDK5` :enum 泛型 自动装箱拆箱 可变参数 增强循环

JDK6 : 支持脚本语言 JDBC4.0API

JDK7 :支持trycatch-with-resources switch语句新增String支持 NIO2.0

`JDK8` : lambda StreamAPI 新的时间日期API 方法引用 构造器引用

JDK9 :模块化 jShell

JDK10:局部变量类型推断

`JDK11`:ZGC  Epsilon GC 

JDK12:switch表达式 ShenandoahGC 增强G1

JDK13:switch表达式引入 yield 文本块

JDK14:instanceof模式识别 Records 弃用ParallelScavenge+Serial GC组合 删除CMS



各个本版JEP数量

![1631675941015](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631675941015.png)

idea 下载地址

https://www.jetbrains.com/idea/download/#section=windows

## 一 语法层面的变化

### 1_密封类(预览)

>  JEP360:Sealed Classes(Preview)密封的类和接口预览

通过密封的类和接口来增强Java编程语言,这是新的预览特性,用于限制超类的使用密封的类和接口限制其他可继承或者实现他们的其他类或接口.

> 目标

允许类或接口的开发者来控制那些代码负责实现,提供了比限制使用超类的访问修饰符声明方式更多选择,并通过支持对模式的详尽分析而支持模式匹配的未来发展

在java中,类层次构造通过集成实现代码的重用,父类的方法可以被许多子类继承.但是,类层次接口的目的并不总是重用代码.有时是对域中存在的各种可能性进行建模,例如图形库支持函的形状类型.当以这种方式使用类层次结构是,我们可能需要限制子类集从而简化建模.

虽然我们可以通过final来限定子类继承,但是这是绝对杜绝类子类,而类的密封是允许子类,但是限定是那个或者哪些.

> 具体方式

引入 Seald class或interface,这些class或者interface只允许被指定的类或者interface进行扩展和实现

使用修饰符sealed,我们可以将一个类声明为密封类.密封类使用reserved关键字permits列出可以直接扩展他的类.子类可以是最终的,非密封或者密封的

> 示例代码

``` java

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class TestSealedClass {
}
/*sealed 对Person类进行密封
* permits 指明哪些类可以继承
* 子类必须是final修饰的或者也是密封的
* 如果子类不想被密封,可以使用non-sealed修饰
* */
sealed class Person permits Worker,Teacher,Cook,Boss,Employee,Student {}
final class Cook              extends Person{}
final class Boss              extends Person{}
final class Employee          extends Person{}
final class Teacher           extends Person{}
// 密封的子类允许继续有子类
sealed class Student          extends Person permits PrimaryStudent,GraduateStudent{}
final class PrimaryStudent    extends Student{}
final class GraduateStudent   extends Student{}
// 通过non-sealed取消子类密封
non-sealed class Worker       extends Person{}
class CarWorker               extends Worker{}
```



> 密封接口 指定实现类的接口

``` java
public class Test2 {
}

/*
* 只有接口可以继承接口
* 一个接口可以同时继承多个接口
* final不能修饰接口,密封接口在被继承时,子接口要么使用 sealed non sealed  修饰
* */
sealed interface  Myinter1 permits Myinter3{}
sealed interface  Myinter2 permits Myinter3 {}
sealed interface  Myinter3 extends Myinter1,Myinter2{}
non-sealed class MyImpl implements Myinter3{}



sealed interface I permits A,B,C  {}
final class A implements I{}
sealed class B implements I{}
non-sealed class C implements I{}

final class D extends B{}
    
    
    
```

密封接口不可以使用匿名内部类进行实现


> 密封列与接口和模式匹配问题

``` java
public class TestSealedClass {
    public static void main(String[] args) {
        test(new C());
    }
    public static void test(C c){
        if( c instanceof I){
            System.out.println( "it is an i");
        }else{
            System.out.println("it is not i");
        }
    }

}
interface I{

}
sealed class C implements I permits D,E{}
non-sealed class D extends C{}
final class E extends C {}
// 密封类仅仅是控制类的继承和实现关系,不会影响我们的模式匹配
```

> 密封接口和records

record是隐匿式的final,可以直接实现密封接口

``` java
package com.msb.test2;

public class TestRecords {
    public static void main(String[] args) {
        MyInter1 myInter1=new Person(10,"旋涡刘能");
     
    }
}

sealed interface  MyInter1{
    public void eat();
}

/*record 默认继承的 java.lang.Record
* record可以直接实现密封接口,不需要用sealed 修饰 non-sealed 修饰
* record本身是隐式的final修饰
* 
* */

record Person(Integer pid,String pname)  implements MyInter1 {
    @Override
    public void eat() {
        
    }
}
record Student(Integer pid,String pname) implements MyInter1{
    @Override
    public void eat() {
        
    }
}
record Cook(Integer pid,String pname) implements MyInter1{
    @Override
    public void eat() {
        
    }
}
record Worker(Integer pid,String pname) implements MyInter1{
    @Override
    public void eat() {
        
    }
}
```



### 2_隐藏类

> JEP371 :HiddenClass(隐藏类)

该提案通过启用标准API来定义无法发现且有有限生命周期的隐藏类,从而提高JVM上所有语言的效率. JDK内部和外部的框架将能够动态生成类,而这些类可以定义隐藏类.通常来说基于JVM的很多语言都有动态生成类的机制,这样可以提高语言的灵活性和效率.

* 隐藏类天生为框架设计的,在运行时生成内部的class   
* 隐藏类只能通过反射访问,不能直接被其他类的字节码访问
* 隐藏类可以独立于其他类加载,卸载,这样可以减少框架的内存占用

> 什么是Hidden Class

就是不能直接被其他class的二进制代码使用的class. 主要被一些框架用来生成运行时类,但是这些类不能被用来直接使用的,是通过反射来调用的

比如JDK8中引入的lambda表达式,编译时不会将lambda表达式转换为专门的类,而是在运行时将相应的字节码动态生成相应的类对象

另外使用动态代理也可以为某些类生成新的动态类

> 特征

我们希望这样的动态类有哪些特征呢?

* 不可发现性.因为我们是为某些静态的类动态生成的动态类,所以我们希望这个动态生成的类看作是静态类的一部分,所以我们不希望除了该静态类以外的其他机制发现
* 访问控制. 我们希望在访问控制静态类的同时,也能控制到动态生成的类
* 生命周期.动态生成类的声明周期一般都比较短. 我们不需要将其保存和静态类的生命周期一致

>API支持

因此,我们需要一些API来定义无法发现的且具有有限声明周期的隐藏类,这将有助于提高基于JVM的语言实现效率.比如

java.lang.reflect.Proxy 可以定义隐藏类作为实现代理接口的代理类

java.lang.invoke.StringConcatFactory可以生成隐藏类来保存常量连接方法

java.lang.invoke.LambdaMetaFactory可以生成隐藏的nestmate类,以容纳访问封闭变量的lambda主体

普通类是通过调用ClassLoader::defineClass创建的,而隐藏类是通过调用Lookup::defineHiddenClass创建的,这使JVM提供的字节派生一个隐藏类,链接该隐藏类,并返回提供对隐藏类的反射访问的查找对象,调用程序可以通过返回的查找对象来获取隐藏类的Class对象

### 3_instanceof模式匹配(预览)

>  JAVA 14中作为预览语言功能引入instanceof模式匹配,在JAVA15中出于第二次预览,而没有任何更改,回顾JAVA14即可





### 4_Records(预览)

> Records Class 第二次预览

JDK14中引入了Records, 只用一个Records可以很方便的创建一个常量类,就是一个数据的透明持有类,简化专门用于存储数据的类的创建语法 

> 当声明一个Record时,该类将自动获取的内容

* 获取成员变量的简单方法, 就是get方法,get方法将简化为成员变量同名方法
* 一个equals的实现
* 一个hashcode的实现
* 一个toString的重现
* 一个全参构造方法
* 对应声明的所有final修饰的成员变量







### 5_文本块(确定)

>  JAVA13开始引入文本块,JAVA14 进行二次预览,JAVA15中成为一个正式的标准,参照JAVA14中的文本块回顾即可







## 二 API层面的变化



## 三 关于虚拟机

### 1_ZGC功能(确定)

JEP377:ZGC :A Scalable Low_Latency Garbage Collector ZGC  功能成为正式标准

ZGC是JAVA11 引入的新的垃圾收集器,经历了多个阶段,自从终于成正式特性自2008年以来,ZGC已经增加了许多改进,并发类卸载,取消未使用的内存,对类数据实现共享的支持到NUMA感知,此外,最大的堆从4T增加到了16T,支持平台包括Linux,Windows和MacOS .ZGC 是一个重新设计的并发垃圾收集器,通过GC停顿时间来提高性能,但是这并不是替换默认的G1垃圾收集器,只不过之前需要-XX:+UnlockExperimentalVMOptions -XX:+UseZGC,现在只需要-XX:+UseZGC就可以,相信不久的将来它必然会成为默认的垃圾回收器

相关的参数有:

``` java
ZAllocationSpikeTolerance ZCollectionInterval ZFragmentationLimit ZMarkStackSpaceLimit ZProcative ZUncommit ZunCommitDelay ZGC-specific JFR events(ZAllocationStall ZPageAllocation ZPageCacheFlush ZRelocationSet ZRelocationSetGroup Zuncommit ) 也从experimental变为product
```



### 2_ShenandoahGC垃圾收集算法转正

> Shenandoah垃圾回收算法终于从实验特性转变为产品特性

这是一个JAVA12引入的回收算法,该算法通过正在运行的JAVA线程同时进行疏散工作来减少GC暂停时间.Shenandoah的暂停时间与堆大小无关,无论是200M还是200G ,都具有机会一致的暂停时间. 

Shenandoah 和ZGC 对比

* 相同: 性能几乎认为是相同的
* 不同: ZGC是OracleJDK的, 而Shenandoah只存在于OpenJDK中,因此使用时需要注意JDK版本

> 打开方式: 使用-XX:+UseShenandoahGC命令行参数打开

Shenandoah在JDK12作为experimental引入,在JDK15变为Production ,之前需要通过-XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC ,现在只需要-XX:+UseShenandoahGC







## 四 其他变化

### 1_edDSA签名算法

> Edwards-Curve Digital Singnature Algorithm 数字曲线签名算法

这是一个新功能,新加基于EdWardS-Curve 数字签名算法,与JDK中现有的签名方案相比,EdDSA具有更高的安全性和性能,因此备受关注.它已经在OpenSSL和BoringSSL等加密库中得到支持,在区块链领域用的比较多.

EdDSA是一种现代椭圆曲线方案,具有JDK中现有签名方案的优点,EdDSA将只在SunECMA提供中实现

### 2_禁用偏向锁定

> jep 374 Disable and Deprecate Biased Locking 禁用偏向锁定

在默认情况下禁用偏向锁定,并弃用所有的相关命令选项.目标是确定是否需要继续支持偏置锁定的高维护成本的遗留同步优化.HotSpot虚拟机使用该优化来减少非竞争锁的开销. 尽管某些JAVA应用程序在禁用偏向锁后可能会出现性能下降,但是偏向锁的性能提高通常不像以前那么明显

该特性默认禁用了 biased locking(-XX:+UseBisaedLocking),并且废弃了所有相关的命令行选型(BiasedLockingStartupDelay,BiasedLockingBulkRebiasThreshold,BiasedLockingBulkRevokeThreshold,BiasedLockingDecayTime,UseOptoBiasInlining,PrintBisasedLockingStatistics and PrintPreciseBiasedLockingStatistics)



### 3_重新实现SocketAPI 

> JEP373 Reimplement the legcy DatagramSocketAPI  重新实现DatagramSocketAPI

作为JEP353的后续,该方案重新实现了遗留的套接字API. java.net.datagram.Socket 和java.netMulticastSocket的当前实现可以追溯到JDK1.0,当时IPV6还在开发中. 因此,当前的套接字实现尝试调和IPV4和IPV6难以维护的方式.

> 具体情况

* 通过替换 java.net.datagram 的基础实现,重新实现旧版DatagramSocket API 
* 更改java.net.DatagramSocket和java.net.MulticastSocket 为更加简单,现代化的底层实现,提高了JDK的可维护性和稳定性

> 新的实现

1 易于维护和调试

2 Project Loom中正在探索虚拟线程协同



### 4_外部存储API

> JEP383 Foreign-Memory Access API (Second Incubator) 外部存储器访问API

目的是引入一个API.以允许java程序安全.有效地访问JAVA对之外的外部存储器.如本机,持久和托管堆.

有许多JAVA程序访是访问外部内存的,比如 Ignite和MapDB.`该API将有助于避免与垃圾收集相关的成本以及与跨进程共享内存以及通过将文件映射到内存来序列化和返序列化内存内容相关的不可预测性`. 该java API 目前没有为访问外部内存停工令人满意的解决方案.但是在新的提议中,API不应该破坏JVM的安全性

Foreign-Memory Access API在JDK14中作为 incubating API引入,在JDK15中出于 Second Incubator,提供了改进.



### 5_废弃和移除

> 废弃

* Deprecated RMI Activation For Removal

RMI Activation(延迟激活: 延迟激活对象,推迟到客户第一次使用之前)被标记为删除,在未来的版本中将会删除,自JAVA8依赖一直是可选的,而不是必选项目. RMI激活机制增加了持续的维护负担,RMI的其他部分暂时不会被弃用.

对于现在应用程序来说. 分布式系统大部都是基于Web的,web服务器已经解决了穿越防火墙,过滤请求,身份验证和安全性问题,并且也提供了很多延迟加载的技术.所以在现代引用程序中,RMIActivation已经很少用了,并且在各种开源代码库中,也基本上找不见了

在JDK8中, RMI Activation被置为可选,JDK15 中,废弃了

* Deprecated -XX:ForceMUMA Option ,废弃了ForceNUMA选项
* Disable Native SunEC Implementation by Default 默认禁用了Native SunEC Implementation

> 移除

* Obsolete -XX:UseAdaptiveGCBoundary,淘汰了 -XX:UseAdativeGCBoundary

* 移除Solaris和SPCRC端口

近年来,Solaris和SPARC都已被Linux操作系统和英特尔处理器取代.放弃对Solaris和SPARC 端口的支持,将使OpenJDK社区的贡献者们能够加速开发新功能,从而推动平台向前发展

* 移除 Nashorn JS 引擎

Nashorn 是JDK提出的脚本执行引擎,该功能时2014年3月发布的JDK8的新特性,在JDK11就已经把它标记为废弃了,JDK15完全移除了

在JDK中取以代之的是GraalVM . GraalVM 是一个运行时平台,他支持java和其他基于java字节码的语言,但也支持其他语言,如JAVAScript Ruby Python 或者 LLVM. 性能是Nashorn 的两倍以上

JDK15 移除了Nashorn JAVAScript Engine 以及jjs命令工具,具体就是jdk.scripting.nashorn及jdk.scripting.nashorn.shell这两个模块移除了

![1631797518607](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631797518607.png)

Graal VM在hotSpot VM基础上,增强而形成的跨语言全栈虚拟机,可以作为"任何语言"的运行平台使用. 





# JAVA16

## 概述

2021年3月16日正式发布,一共更新了17JEP

https://openjdk.java.net/projects/jdk/16/

![1631799306032](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631799306032.png)



![1631801073728](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631801073728.png)



## 一 语法层面

### 1_JEP 397：密封类（第二次预览）

> sealed class 第二次预览

通过密封的类和接口来增强Java编程语言,这是新的预览特性,用于限制超类的使用密封的类和接口限制其他可继承或者实现他们的其他类或接口.

> 目标

允许类或接口的开发者来控制那些代码负责实现,提供了比限制使用超类的访问修饰符声明方式更多选择,并通过支持对模式的详尽分析而支持模式匹配的未来发展

在java中,类层次构造通过集成实现代码的重用,父类的方法可以被许多子类继承.但是,类层次接口的目的并不总是重用代码.有时是对域中存在的各种可能性进行建模,例如图形库支持函的形状类型.当以这种方式使用类层次结构是,我们可能需要限制子类集从而简化建模.

虽然我们可以通过final来限定子类继承,但是这是绝对杜绝类子类,而类的密封是允许子类,但是限定是那个或者哪些.

### 2_JEP 394：instanceof 的模式匹配

> 概括

 增强Java编程语言与*模式匹配*的 `instanceof`运算符。 [模式匹配](https://cr.openjdk.java.net/~briangoetz/amber/pattern-match.html) 允许更简洁、更安全地表达程序中的常见逻辑，即从对象中有条件地提取组件。 

> 历史

模式匹配`instanceof`由[JEP 305](https://openjdk.java.net/jeps/305)提出 并在 [JDK 14 中](https://openjdk.java.net/projects/jdk/14)作为 [预览功能提供](https://openjdk.java.net/jeps/12)。它由[JEP 375](https://openjdk.java.net/jeps/375)重新提出， 并在 [JDK 15 中](https://openjdk.java.net/projects/jdk/15)进行第二轮预览。

该 JEP 建议在 JDK 16 中完成该功能，并进行以下改进：

- 取消模式变量是隐式 final 的限制，以减少局部变量和模式变量之间的不对称性。
- 将`instanceof`类型*S*的表达式与类型*T*的模式进行比较，使模式表达式成为编译时错误，其中*S*是*T*的子类型。（这个`instanceof`表达式总是成功，然后毫无意义。相反的情况，模式匹配总是失败，已经是一个编译时错误。）

可以根据进一步的反馈合并其他改进。

> 原因

几乎每个程序都包含某种逻辑，这些逻辑结合了测试表达式是否具有特定类型或结构，然后有条件地提取其状态的组件以进行进一步处理。例如，所有 Java 程序员都熟悉 `instanceof`-and-cast 习语：

```java
if (obj instanceof String) {
    String s = (String) obj;    // grr...
    ...
}
```

有三件事情会在这里：测试（是`obj`一`String`？），转换（铸造`obj`到`String`），和一个新的局部变量的声明（`s`），这样我们就可以使用字符串值。这种模式很简单，所有 Java 程序员都可以理解，但由于几个原因，它并不是最理想的。它很乏味；应该不需要同时进行类型测试和强制转换（`instanceof`测试后你还会做什么 ？）。这个样板——特别是该类型的三个出现`String`——混淆了后面更重要的逻辑。但最重要的是，重复提供了错误潜入程序中的机会。

我们相信 Java 是时候拥抱*模式匹配了*，而不是寻求临时解决方案。模式匹配允许简洁地表达对象的所需“形状”（*模式*），并允许各种语句和表达式根据其输入（*匹配*）测试该“形状” 。许多语言，从 Haskell 到 C#，都因为其简洁和安全而采用了模式匹配

这允许我们将上面繁琐的代码重构为以下内容：

```java
if (obj instanceof String s) {
    // Let pattern matching do the work!
    ...
}
```

### 3_JEP 395：记录

> 概述

 使用[记录](https://cr.openjdk.java.net/~briangoetz/amber/datum.html)增强 Java 编程语言，[记录](https://cr.openjdk.java.net/~briangoetz/amber/datum.html)是充当不可变数据的透明载体的类。记录可以被认为是*名义元组*。 

> 历史

记录由[JEP 359](https://openjdk.java.net/jeps/359)提出 并在[JDK 14 中](https://openjdk.java.net/projects/jdk/14)作为 [预览功能提供](https://openjdk.java.net/jeps/12)。

作为对反馈的回应，[JEP 384](https://openjdk.java.net/jeps/384)对该设计进行了改进， 并在[JDK 15 中](https://openjdk.java.net/projects/jdk/15)作为第二次预览功能交付 。第二次预览的改进如下：

- 在第一个预览版中，规范构造函数必须是`public`. 在第二个预览中，如果隐式声明了规范构造函数，则其访问修饰符与记录类相同；如果显式声明了规范构造函数，则其访问修饰符必须提供至少与记录类一样多的访问权限。
- `@Override`注释的含义被扩展为包括注释方法是记录组件的显式声明的访问器方法的情况。
- 为了强制使用紧凑构造函数，分配给构造函数主体中的任何实例字段会导致编译时错误。
- 引入了声明本地记录类、本地枚举类和本地接口的能力。

该 JEP 建议在 JDK 16 中完成该功能，并进行以下改进：

- 放宽长期存在的限制，即内部类不能声明显式或隐式静态成员。这将变得合法，特别是将允许内部类声明作为记录类的成员。

可以根据进一步的反馈合并其他改进。

> 目标

- 设计一个面向对象的构造来表达简单的值聚合。
- 帮助开发人员专注于建模不可变数据而不是可扩展行为。
- 自动实现数据驱动的方法，例如`equals`和访问器。
- 保留长期存在的 Java 原则，例如名义类型和迁移兼容性。

> 细节实现

人们普遍抱怨“Java 太冗长”或“仪式太多”。一些最严重的违规者是那些只不过是少数值的不可变 *数据载体*的类。正确编写这样的数据载体类涉及许多低价值、重复、容易出错的代码：构造函数、访问器`equals`、`hashCode`、`toString`、 等。 例如，携带 x 和 y 坐标的类不可避免地以这样的方式结束：

```
class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x() { return x; }
    int y() { return y; }

    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        Point other = (Point) o;
        return other.x == x && other.y == y;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        return String.format("Point[x=%d, y=%d]", x, y);
    }
}
```

开发人员有时试图通过省略诸如 的方法来偷工减料 `equals`，导致令人惊讶的行为或可调试性差，或者将替代但不完全合适的类压入服务中，因为它具有“正确的形状”并且他们还不想声明另一个班级。

集成开发环境帮助我们*写*的大部分代码的数据载体类，但没有做任何事情来帮助*读者*提炼出设计意图的“我是一个数据载体`x`和`y`”从几十个样板线。编写对少数值建模的 Java 代码应该更容易编写、阅读和验证是否正确。

虽然表面上将记录视为主要与样板减少有关，但我们选择了一个更具语义的目标：*将数据建模为数据*。（如果语义是正确的，样板将自行处理。）声明数据载体类应该简单而简洁*，默认情况下，* 这些类使它们的数据不可变，并提供生成和使用数据的方法的惯用实现。

*记录类*是 Java 语言中的一种新类。记录类有助于用比普通类更少的仪式对普通数据聚合进行建模。

记录类的声明主要由其*状态*的声明组成 ；然后记录类提交到与该状态匹配的 API。这意味着记录类放弃了类通常享有的自由——将类的 API 与其内部表示分离的能力——但作为回报，记录类声明变得更加简洁。

更准确地说，记录类声明由名称、可选类型参数、标题和正文组成。标题列出了记录类的*组件*，它们是构成其状态的变量。（此组件列表有时称为*状态描述*。）例如：

```
record Point(int x, int y) { }
```

因为记录类在语义上声称是其数据的透明载体，所以记录类会自动获取许多标准成员：

- 对于头部中的每个组件，两个成员：一个`public`与组件同名和返回类型的访问器方法，以及一个`private` `final`与组件类型相同的字段；
- 一个*规范构造函数，*其签名与标头相同，并将每个私有字段分配给`new` 实例化记录的表达式中的相应参数；
- `equals`以及`hashCode`确保两个记录值相同的方法，如果它们是相同的类型并且包含相同的组件值；和
- 一种`toString`返回所有记录组件的字符串表示形式及其名称的方法。

换句话说，记录类的头部描述了它的状态，即它的组件的类型和名称，而 API 是从该状态描述中机械地和完全地派生出来的。API 包括用于构建、成员访问、平等和显示的协议。（我们希望未来的版本支持解构模式以实现强大的模式匹配。）

### 3_JEP 390：基于值的类的警告

> 概括

 将原始包装类指定为*基于值的，*并弃用它们的构造函数以进行删除，提示新的弃用警告。提供有关对 Java 平台中任何基于值的类的实例进行同步的不当尝试的警告。 

> 原因

在[瓦尔哈拉项目](https://openjdk.java.net/projects/valhalla/)正在推行显著增强了Java编程模型的形式*原始类*。这些类声明它们的实例是无身份的并且能够进行内联或扁平化表示，其中实例可以在内存位置之间自由复制并仅使用实例字段的值进行编码。

原始类的设计和实现已经足够成熟，我们可以自信地预期在未来的版本中将 Java 平台的某些类迁移为原始类。

迁移的候选者 在 API 规范中被非正式地指定为 [*基于值的类*](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/doc-files/ValueBased.html)。从广义上讲，这意味着它们对标识对类的行为不重要的不可变对象进行编码，并且它们不提供实例创建机制，例如公共构造函数，保证每次调用具有唯一标识。

原始包装类（`java.lang.Integer`、`java.lang.Double`等）也旨在成为原始类。这些类满足大多数被指定为基于值的要求，但它们公开了不推荐使用的（自 Java 9 起）`public`构造函数。通过对定义进行一些调整，它们也可以被视为基于值的类。

基于值的类的客户端通常不受原始类迁移的影响，除非它们违反了使用这些类的建议。特别是，在发生迁移的未来 Java 版本上运行时：

1. 这些相等 (per `equals`) 的类的实例也可能被认为是相同的 (per `==`)，这可能会破坏依赖于`!=`正确行为的结果的程序。
2. 尝试使用`new Integer`,`new Double`等创建包装类实例，而不是隐式装箱或对`valueOf`工厂方法的调用，将产生 `LinkageError`。
3. 尝试在这些类的实例上进行同步将产生异常。

这些更改对某些人来说可能不方便，但解决方法很简单：如果您需要标识，请使用不同的类——通常是您自己定义的类，但`Object`也`AtomicReference`可能是合适的。迁移到原始类的好处——更好的性能、可靠的相等语义、统一原始类和类——将非常值得带来的不便。

(1) 已经因为避免承诺基于值的类的工厂方法中的唯一身份而受到劝阻。没有一种实用的方法可以自动检测忽略这些规范并依赖当前实现行为的程序，但我们预计这种情况很少见。

我们可以通过弃用包装类构造函数来阻止（2）移除，这将放大编译对这些构造函数的调用时发生的警告。现有 Java 项目的很大一部分（可能占其中的 1%-10%）调用包装类构造函数，但在许多情况下，它们仅打算在 Java 9 之前的版本上运行。许多流行的开源项目已经通过从源代码中删除包装构造函数调用来响应 Java 9 的弃用警告，鉴于“弃用以删除”警告的紧迫性，我们可以期待更多这样做。用于缓解此问题的其他功能在*依赖项*部分中进行了描述。

我们可以通过在编译时和运行时实施警告来阻止 (3)，以通知程序员他们的同步操作在未来版本中将不起作用。







## 二 API层面

### 1_JEP 338：Vector API（孵化器）

> 概述

 提供的初始迭代[培养箱模块](https://openjdk.java.net/jeps/11)， `jdk.incubator.vector`来表达向量计算在运行时可靠地编译到最佳矢量的硬件指令上支持的CPU架构，从而实现优异的性能等效标量计算。 

> 目标

- *清晰简洁的 API：* API 应能够清晰简洁地表达广泛的矢量计算，这些矢量计算由一系列矢量操作组成，这些矢量操作通常在循环内组成，可能还有控制流。应该可以表达对向量大小（或每个向量的车道数）通用的计算，从而使此类计算能够在支持不同向量大小的硬件之间移植（如下一个目标中详述）。
- *平台不可知：* API 应与体系结构无关，支持在支持向量硬件指令的多个 CPU 体系结构上的运行时实现。与平台优化和可移植性冲突的 Java API 中的常见情况一样，偏向于使 Vector API 具有可移植性，即使某些特定于平台的习语不能直接用可移植代码表达。x64 和 AArch64 性能的下一个目标代表了支持 Java 的所有平台上的适当性能目标。在[ARM可缩放矢量扩展](https://arxiv.org/pdf/1803.06185.pdf)（SVE）在这方面特别关注，以确保API能支持这种架构，即使是写没有已知的生产硬件实现的。

- *在 x64 和 AArch64 架构上可靠的运行时编译和性能：* Java 运行时，特别是 HotSpot C2 编译器，应在有能力的 x64 架构上将向量操作序列编译为相应的向量硬件指令序列，例如[Streaming SIMD](https://en.wikipedia.org/wiki/Streaming_SIMD_Extensions)支持的那些 [扩展](https://en.wikipedia.org/wiki/Streaming_SIMD_Extensions)(SSE) 和[高级矢量扩展](https://en.wikipedia.org/wiki/Advanced_Vector_Extensions) (AVX) 扩展，从而生成高效和高性能的代码。程序员应该相信他们表达的向量操作将可靠地映射到相关的硬件向量指令。这同样适用于编译为[Neon](https://en.wikipedia.org/wiki/ARM_architecture#Advanced_SIMD_(Neon))支持的向量硬件指令序列的有能力的 ARM AArch64 架构。

- *优雅降级：* 如果向量计算无法在运行时完全表示为硬件向量指令序列，要么是因为架构不支持某些所需指令，要么是因为不支持另一种 CPU 架构，那么 Vector API 实现应优雅降级并且仍然起作用。这可能包括如果矢量计算无法充分编译为矢量硬件指令，则向开发人员发出警告。在没有向量的平台上，优雅降级将产生与手动展开循环竞争的代码，其中展开因子是所选向量中的通道数。

> 动机

 Vector API 旨在通过提供一种在 Java 中编写复杂矢量算法的机制来解决这些问题，使用 HotSpot 中预先存在的矢量化支持，但使用用户模型使矢量化更加可预测和健壮。手工编码的向量循环可以表达`hashCode`自动向量化器可能永远不会优化的高性能算法（例如向量化或专门的数组比较）。这种显式矢量化 API 可能适用于许多领域，例如机器学习、线性代数、密码学、金融和 JDK 本身的用法。 

## 三 其他

### 1_JEP 347：启用 C++14 语言功能

> 概括

 允许在 JDK C++ 源代码中使用 C++14 语言特性，并给出关于哪些特性可以在 HotSpot 代码中使用的具体指导。 



> 目标

通过 JDK 15，JDK 中 C++ 代码使用的语言特性已经被限制在 C++98/03 语言标准。在 JDK 11 中，代码已更新以支持使用较新版本的 C++ 标准进行构建，尽管它还没有使用任何新功能。这包括能够使用支持 C++11/14 语言功能的各种编译器的最新版本进行构建。

此 JEP 的目的是正式允许 JDK 中的 C++ 源代码更改以利用 C++14 语言功能，并提供有关哪些功能可以在 HotSpot 代码中使用的具体指导。

> 描述

 要利用 C++14 语言功能，需要在构建时进行一些更改，具体取决于平台编译器。还需要指定各种平台编译器的最低可接受版本。应明确指定所需的语言标准；较新的编译器版本可能会默认使用较新的且可能不兼容的语言标准。 

- Windows：JDK 11 需要 Visual Studio 2017。（早期版本会生成配置时警告，可能会也可能不起作用。）对于 Visual Studio 2017，默认的 C++ 标准是 C++14。`/std:c++14` 应添加该选项。将完全放弃对旧版本的支持。
- Linux：将`-std=gnu++98`编译器选项替换为`-std=c++14`. gcc 的最低支持版本是 5.0。
- macOS：将`-std=gnu++98`编译器选项替换为`-std=c++14`. clang 的最低支持版本是 3.5。
- AIX/PowerPC：将`-std=gnu++98`编译器选项 替换为`-std=c++14`并要求使用 xlclang++ 作为编译器。xlclang++ 的最低支持版本是 16.1。



###  2_JEP 357：从 Mercurial 迁移到 Git 

> 概括

 将 OpenJDK 社区的源代码存储库从 Mercurial (hg) 迁移到 Git。 

> 目标

- 将所有单存储库 OpenJDK 项目从 Mercurial 迁移到 Git
- 保留所有版本控制历史，包括标签
- 根据 Git 最佳实践重新格式化提交消息
- 将[jcheck](https://openjdk.java.net/projects/code-tools/jcheck/)、 [webrev](https://openjdk.java.net/projects/code-tools/webrev/)和 [defpath](https://openjdk.java.net/projects/code-tools/defpath/)工具[移植](https://openjdk.java.net/projects/code-tools/defpath/)到 Git
- 创建一个工具来在 Mercurial 和 Git 哈希之间进行转换

> 动机

迁移到 Git 的三个主要原因：

1. 版本控制系统元数据的大小
2. 可用工具
3. 可用主机

转换后的存储库的初始原型显示版本控制元数据的大小显着减少。例如，存储库的`.git`目录对于`jdk/jdk`Git 大约为 300 MB，`.hg`对于 Mercurial，该目录大约为 1.2 GB，具体取决于所使用的 Mercurial 版本。元数据的减少保留了本地磁盘空间并减少了克隆时间，因为需要通过线路的位更少。Git 还具有 仅克隆部分历史记录的*浅层克隆*，从而为不需要整个历史记录的用户提供更少的元数据。

与 Mercurial 相比，与 Git 交互的工具还有很多：

- 所有文本编辑器都具有 Git 集成，无论是本机还是插件形式，包括[Emacs](https://www.gnu.org/software/emacs/) （[magit](https://magit.vc/)插件）、[Vim](https://www.vim.org/) （[fugitive.git](https://github.com/tpope/vim-fugitive)插件）、 [VS Code](https://code.visualstudio.com/)（内置）和 [Atom](https://atom.io/)（内置）。
- 几乎所有集成开发环境 (IDE) 还附带开箱即用的 Git 集成，包括 [IntelliJ](https://www.jetbrains.com/idea/)（内置）、 [Eclipse](https://eclipse.org/)（内置）、 [NetBeans](https://netbeans.org/)（内置）和 [Visual Studio](https://visualstudio.microsoft.com/)（内置）。
- 有多个桌面客户端可用于与本地 Git 存储库进行交互。

最后，有许多选项可用于托管 Git 存储库，无论是自托管还是作为服务托管。



###  3_JEP 376：ZGC：并发线程堆栈处理

> 概述

将 ZGC 线程堆栈处理从安全点移动到并发阶段。 

> 目标

- 从 ZGC 安全点中删除线程堆栈处理。
- 使堆栈处理变得懒惰、协作、并发和增量。
- 从 ZGC 安全点中删除所有其他每线程根处理。
- 提供一种机制，其他 HotSpot 子系统可以通过该机制延迟处理堆栈。

> 原因

ZGC 垃圾收集器 (GC) 旨在使 HotSpot 中的 GC 暂停和可扩展性问题成为过去。到目前为止，我们已经将所有随堆大小和元空间大小扩展的 GC 操作从安全点操作移到并发阶段。这些包括标记、重定位、引用处理、类卸载和大多数根处理。

仍然在 GC 安全点中完成的唯一活动是根处理的子集和有时间限制的标记终止操作。根包括 Java 线程堆栈和各种其他线程根。这些根是有问题的，因为它们会随着线程的数量而扩展。由于大型机器上有许多线程，根处理成为一个问题。

为了超越我们今天所拥有的，并满足在 GC 安全点内花费的时间不超过一毫秒的期望，即使在大型机器上，我们也必须将这种每线程处理，包括堆栈扫描，移出并发阶段。

在这项工作之后，在 ZGC 安全点操作中基本上不会做任何重要的事情。

作为该项目的一部分构建的基础设施最终可能会被其他项目使用，例如 Loom 和 JFR，以统一延迟堆栈处理。



### 4_JEP 380：Unix 域套接字通道

> 概述



 将 Unix 域 ( `AF_UNIX`) 套接字支持添加到包中的[套接字通道](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/nio/channels/SocketChannel.html)和[服务器套接字通道](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/nio/channels/ServerSocketChannel.html)API `java.nio.channels`。扩展[继承的通道机制](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/nio/channels/spi/SelectorProvider.html#inheritedChannel())以支持 Unix 域套接字通道和服务器套接字通道。 



> 目标

 Unix 域套接字用于同一主机上的进程间通信 (IPC)。它们在大多数方面类似于 TCP/IP 套接字，不同之处在于它们由文件系统路径名而不是 Internet 协议 (IP) 地址和端口号寻址。此 JEP 的目标是支持在主要 Unix 平台和 Windows 中通用的 Unix 域套接字的所有功能。Unix 域套接字通道在读/写行为、连接设置、服务器对传入连接的接受、与选择器中的其他非阻塞可选通道的多路复用以及相关套接字的支持方面的行为与现有的 TCP/IP 通道相同选项。 

> 原因

对于本地、进程间通信，Unix 域套接字比 TCP/IP 环回连接更安全、更高效。

- Unix 域套接字严格用于同一系统上的进程之间的通信。不打算接受远程连接的应用程序可以通过使用 Unix 域套接字来提高安全性。
- Unix 域套接字受到操作系统强制的、基于文件系统的访问控制的进一步保护。
- Unix 域套接字比 TCP/IP 环回连接具有更快的设置时间和更高的数据吞吐量。
- 对于需要在同一系统上的容器之间进行通信的容器环境，Unix 域套接字可能是比 TCP/IP 套接字更好的解决方案。这可以使用位于共享卷中的套接字来实现。

Unix 域套接字长期以来一直是大多数 Unix 平台的一个功能，现在 Windows 10 和 Windows Server 2019 都支持。

> 具体操作

为了支持 Unix 域套接字通道，我们将添加以下 API 元素：

- 一个新的套接字地址类，`java.net.UnixDomainSocketAddress`；
- 甲`UNIX`在现有的恒定值`java.net.StandardProtocolFamily`枚举;
- 新`open`的工厂方法`SocketChannel`，并`ServerSocketChannel`指定协议族
- 更新 `SocketChannel`和`ServerSocketChannel`规范以指定 Unix 域套接字的通道的行为方式。

### 5_JEP 386：Alpine Linux 端口

> 概括

 将 JDK 移植到 Alpine Linux，以及在 x64 和 AArch64 架构上使用 musl 作为其主要 C 库的其他 Linux 发行版， 



> 原因

[Musl](https://musl.libc.org/)是针对基于 Linux 的系统的 ISO C 和 POSIX 标准中描述的标准库功能的实现。包括[Alpine Linux](https://alpinelinux.org/)和 [OpenWrt](https://openwrt.org/)在内的几个 Linux 发行[版](https://alpinelinux.org/)都基于 musl，而其他一些发行版提供了可选的 musl 包（例如[Arch Linux](https://www.archlinux.org/)）。

Alpine Linux 发行版由于其较小的镜像大小，被广泛用于云部署、微服务和容器环境。例如，[用于 Alpine Linux 的](https://hub.docker.com/_/alpine)Docker[基础映像](https://hub.docker.com/_/alpine)小于 6 MB。使 Java 在此类设置中开箱即用将允许 Tomcat、Jetty、Spring 和其他流行框架在此类环境中本地工作。

通过使用`jlink` [(JEP 282)](https://openjdk.java.net/jeps/282)来减少 Java 运行时的大小，用户将能够创建一个更小的图像来运行特定的应用程序。应用程序所需的模块集可以通过`jdeps`命令确定。例如，如果目标应用程序仅依赖于`java.base`模块，则带有 Alpine Linux 的 Docker 映像和仅带有该模块的 Java 运行时和服务器 VM 大小为 38 MB。

同样的动机也适用于嵌入式部署，它们也有大小限制。

>  具体操作

该 JEP 旨在整合上游的[Portola 项目](https://openjdk.java.net/projects/portola/)。

此端口将不支持 HotSpot Serviceability Agent 的附加机制。

要在 Alpine Linux 上构建 JDK 的 musl 变体，需要以下软件包：

 alpine-sdk alsa-lib alsa-lib-dev autoconf bash cups-dev cups-libs fontconfig fontconfig-dev freetype freetype-dev grep libx11 libx11-dev libxext libxext-dev libxrandr libxrandr-dev libxrender libxrender-dev libxt libxt-dev libxtst -dev linux-headers zip

安装这些软件包后，JDK 构建过程将照常工作。

如果有需求，可以在后续增强中实现其他架构的 Musl 端口。



### 6_JEP 387：弹性元空间

> 概括

更及时地将未使用的 HotSpot 类元数据（即元*空间*）内存*返还*给操作系统，减少元空间占用空间，并简化元空间代码以降低维护成本。



> 原因

自从在[JEP 122 中出现](https://openjdk.java.net/jeps/122)以来，元空间就因高堆外内存使用而臭名昭著。大多数普通应用程序没有问题，但很容易以错误的方式刺激元空间分配器，从而导致过多的内存浪费。不幸的是，这些类型的病例情况并不少见。

元空间内存在每类加载器管理[领域](https://en.wikipedia.org/wiki/Region-based_memory_management)。一个 arena 包含一个或多个 *chunks*，它的加载器通过廉价的指针碰撞从中分配。元空间块是粗粒度的，以保持分配操作的效率。然而，这会导致使用许多小类加载器的应用程序遭受不合理的高元空间使用。

当类加载器被回收时，其元空间领域中的块被放置在空闲列表中以供以后重用。然而，这种重用可能不会在很长一段时间内发生，或者可能永远不会发生。因此，具有大量类加载和卸载活动的应用程序可能会在元空间空闲列表中累积大量未使用的空间。如果没有碎片化，该空间可以返回给操作系统以用于其他目的，但通常情况并非如此。

> 具体操作

我们建议用基于[好友的分配方案](https://en.wikipedia.org/wiki/Buddy_memory_allocation)替换现有的元空间内存分配器。这是一种古老且经过验证的算法，已成功用于例如 Linux 内核。这种方案将使以更小的块分配元空间内存变得可行，这将减少类加载器的开销。它还将减少碎片，这将使我们能够通过将未使用的元空间内存返回给操作系统来提高弹性。

我们还将根据需要将操作系统中的内存延迟提交到 arenas。这将减少从大型竞技场开始但不立即使用它们或可能永远不会使用它们的全部范围的加载器的占用空间，例如引导类加载器。

最后，为了充分利用伙伴分配提供的弹性，我们将元空间内存安排成大小均匀的*颗粒*，这些*颗粒*可以相互独立地提交和取消提交。这些颗粒的大小可以通过一个新的命令行选项来控制，它提供了一种控制虚拟内存碎片的简单方法。

可以在[此处](https://cr.openjdk.java.net/~stuefe/JEP-Improve-Metaspace-Allocator/review-guide/review-guide-1.0.html)找到详细描述新算法的文档。工作原型作为[JDK 沙箱存储库中的一个分支](https://hg.openjdk.java.net/jdk/sandbox/shortlog/38a706be96d4)存在。





###  7_JEP 388：Windows/AArch64 端口

> 概括

 将 JDK 移植到 Windows/AArch64 

> 动机

 随着新的消费级和服务器级 AArch64 (ARM64) 硬件的发布，由于最终用户的需求，Windows/AArch64 已成为一个重要的平台。 

> 具体操作

通过扩展之前为 Linux/AArch64 移植[(JEP 237](https://openjdk.java.net/jeps/237) )所做的工作，我们已将 JDK 移植到 Windows/AArch64 。此端口包括模板解释器、C1 和 C2 JIT 编译器以及垃圾收集器（串行、并行、G1、Z 和 Shenandoah）。它支持 Windows 10 和 Windows Server 2016 操作系统。

这个 JEP 的重点不是移植工作本身，它大部分是完整的，而是将移植集成到 JDK 主线存储库中。

目前，我们有十几个变更集。我们将对共享代码的更改保持在最低限度。我们的更改将 AArch64 内存模型的支持扩展到 Windows，解决了一些 MSVC 问题，将 LLP64 支持添加到 AArch64 端口，并在 Windows 上执行 CPU 功能检测。我们还修改了构建脚本以更好地支持交叉编译和 Windows 工具链。

新平台代码本身仅限于 15 (+4) 个文件和 1222 行 (+322)。

可[在此处](https://github.com/microsoft/openjdk-aarch64)获得抢先体验的二进制文件。





###  8_ JEP 389：外部链接器 API（孵化器）

> 概括

 介绍一个 API，它提供对本机代码的静态类型、纯 Java 访问。此 API 与外部内存 API ( [JEP 393](https://openjdk.java.net/jeps/393) ) 一起，将大大简化绑定到本机库的其他容易出错的过程。 

 为该 JEP 提供基础的 Foreign-Memory Access API 最初由[JEP 370](https://openjdk.java.net/jeps/370)提出，并于 2019 年底作为[孵化 API](https://openjdk.java.net/jeps/11)面向 Java 14，随后由面向 Java 的[JEP 383](https://openjdk.java.net/jeps/383)和[JEP 393](https://openjdk.java.net/jeps/393)更新分别为 15 和 16。外部内存访问 API 和外部链接器 API 共同构成了[巴拿马项目的](https://openjdk.java.net/projects/panama/)关键可交付成果。 

> 目标

- *易用性：*用卓越的纯 Java 开发模型替换 JNI。
- *C 支持：* 这项工作的初始范围旨在在 x64 和 AArch64 平台上提供与 C 库的高质量、完全优化的互操作性。
- *通用性：*外部链接器 API 和实现应该足够灵活，随着时间的推移，可以适应其他平台（例如，32 位 x86）和用非 C 语言编写的外部函数（例如 C++、Fortran）。
- *性能：*外部链接器 API 应提供与 JNI 相当或优于 JNI 的性能。



> 原因

从 Java 1.1 开始，Java 就支持通过[Java 本地接口 (JNI)](https://docs.oracle.com/en/java/javase/14/docs/specs/jni/index.html)调用本地方法，但这条路径一直是艰难而脆弱的。使用 JNI 包装本机函数需要开发多个工件：Java API、C 头文件和 C 实现。即使有工具帮助，Java 开发人员也必须跨多个工具链工作，以保持多个依赖于平台的工件同步。这对于稳定的 API 来说已经够难了，但是当试图跟踪正在进行的 API 时，每次 API 发展时更新所有这些工件是一个重大的维护负担。最后，JNI 主要是关于代码的，但代码总是交换数据，而 JNI 在访问本机数据方面提供的帮助很小。出于这个原因，开发人员经常求助于解决方法（例如直接缓冲区或`sun.misc.Unsafe`) 这使得应用程序代码更难维护，甚至更不安全。

多年来，出现了许多框架来填补 JNI 留下的空白，包括[JNA](https://github.com/java-native-access/jna)、[JNR](https://github.com/jnr/jnr-ffi)和[JavaCPP](https://github.com/bytedeco/javacpp)。JNA 和 JNR 从用户定义的接口声明动态生成包装器；JavaCPP 生成由 JNI 方法声明上的注释静态驱动的包装器。虽然这些框架通常比 JNI 体验有显着改进，但情况仍然不太理想，尤其是与提供一流的本地互操作的语言相比时。例如，Python 的[ctypes](https://docs.python.org/3/library/ctypes.html)包可以在没有任何胶水代码的情况下动态包装本机函数。其他语言，例如[Rust](https://rust-lang.github.io/rust-bindgen/)，提供了从 C/C++ 头文件机械地派生本机包装器的工具。

最终，Java 开发人员应该能够（大部分）*只使用*任何被认为对特定任务有用的本地库——我们已经看到现状如何阻碍实现这一目标。此 JEP 通过引入高效且受支持的 API — 外部链接器 API — 来纠正这种不平衡，该 API 提供外部函数支持，而无需任何干预 JNI 胶水代码。它通过将外部函数公开为可以在纯 Java 代码中声明和调用的方法句柄来实现这一点。这大大简化了编写、构建和分发依赖于外部库的 Java 库和应用程序的任务。此外，Foreign Linker API 与 Foreign-Memory Access API 一起，为第三方本机互操作框架（无论是现在还是未来）都可以可靠地构建提供了坚实而高效的基础。

### 9_JEP 392：打包工具

> 概括

 提供`jpackage`用于打包自包含 Java 应用程序的工具。 

> 历史

该`jpackage`工具是由[JEP 343](https://openjdk.java.net/jeps/343)在 JDK 14 中作为孵化工具引入的。它仍然是 JDK 15 中的一个孵化工具，以便有时间提供额外的反馈。现在可以将其从孵化提升为生产就绪功能。作为这种转换的结果，`jpackage`模块的名称将从 更改`jdk.incubator.jpackage`为`jdk.jpackage`。

与 JEP 343 相关的唯一实质性变化是我们用`--bind-services`更通用的`--jlink-options`选项替换了该选项，[如下所述](https://openjdk.java.net/jeps/392#Runtime-images)。

> 目标

创建一个基于遗留 JavaFX[`javapackager`](https://docs.oracle.com/javase/9/tools/javapackager.htm#JSWOR719)工具的打包工具：

- 支持原生打包格式，为最终用户提供自然的安装体验。这些格式包括`msi`与`exe`在Windows，`pkg`并`dmg`在MacOS，以及`deb`和`rpm`在Linux上。
- 允许在打包时指定启动时间参数。
- 可以直接从命令行调用，也可以通过`ToolProvider`API 以编程方式调用。

> 原因

许多 Java 应用程序需要以一流的方式安装在本机平台上，而不是简单地放置在类路径或模块路径上。应用程序开发人员提供一个简单的 JAR 文件是不够的；他们必须提供适合本机平台的可安装包。这允许以用户熟悉的方式分发、安装和卸载 Java 应用程序。例如，在 Windows 上，用户希望能够双击一个软件包来安装他们的软件，然后使用控制面板来删除软件；在 macOS 上，用户希望能够双击 DMG 文件并将他们的应用程序拖到应用程序文件夹中。

jpackage 工具还可以帮助填补过去技术留下的空白，例如从 Oracle 的 JDK 11 中删除的 Java Web Start，以及`pack200`在 JDK 14 ( [JEP 367](https://openjdk.java.net/jeps/367) ) 中删除的。开发人员可以`jlink`将 JDK 拆分为所需的最小模块集，然后使用打包工具生成可部署到目标机器的压缩、可安装映像。

以前，为了满足这些要求，`javapackager`Oracle 的 JDK 8 随附了一个名为的打包工具。但是，作为 JavaFX 删除的一部分，它已从 Oracle 的 JDK 11 中删除。

> 具体操作

该`jpackage`工具将 Java 应用程序打包到特定于平台的包中，其中包含所有必需的依赖项。应用程序可以作为普通 JAR 文件的集合或作为模块的集合提供。支持的特定于平台的包格式是：

- Linux：`deb`和`rpm`
- macOS：`pkg`和`dmg`
- 窗户：`msi`和`exe`

默认情况下，`jpackage`以最适合运行它的系统的格式生成包。

> 非模块化应用打包

假设您有一个由 JAR 文件组成的应用程序，所有这些文件都在一个名为 的目录中`lib`，并且`lib/main.jar`包含主类。然后命令

```
$ jpackage --name myapp --input lib --main-jar main.jar
```

将以本地系统的默认格式打包应用程序，将生成的包文件保留在当前目录中。如果`MANIFEST.MF`文件中`main.jar`没有`Main-Class`属性，则必须明确指定主类：

```
$ jpackage --name myapp --input lib --main-jar main.jar \
  --main-class myapp.Main
```

包的名称将是`myapp`，但包文件本身的名称会更长，并以包类型结尾（例如，`myapp.exe`）。该软件包将包含应用程序的启动器，也称为`myapp`. 为了启动应用程序，启动程序将从输入目录复制的每个 JAR 文件放置在 JVM 的类路径上。

如果您希望以默认格式以外的格式生成包，请使用该`--type`选项。例如，要在 macOS 上生成`pkg`文件而不是`dmg`文件：

```
$ jpackage --name myapp --input lib --main-jar main.jar --type pkg
```

> 模块化应用打包

如果您有一个模块化应用程序，由目录中的模块化 JAR 文件和/或 JMOD 文件组成，并且`lib`模块中的主类`myapp`，则命令

```
$ jpackage --name myapp --module-path lib -m myapp
```

会打包。如果`myapp`模块未标识其主类，那么您必须再次明确指定：

```
$ jpackage --name myapp --module-path lib -m myapp/myapp.Main
```

（创建模块化 JAR 或 JMOD 文件时，您可以使用和工具`--main-class`选项指定主类。）`jar``jmod`





### 10_JEP 393：外部内存访问 API（第三个孵化器）



> 概述

 引入 API 以允许 Java 程序安全有效地访问 Java 堆之外的外部内存。 

> 历史

Foreign-Memory Access API 最初由[JEP 370](https://openjdk.java.net/jeps/370)提出，并于 2019 年底作为[孵化 API](https://openjdk.java.net/jeps/11)面向 Java 14 ，后来由[JEP 383](https://openjdk.java.net/jeps/383)重新孵化，后者在 2020 年中期面向 Java 15。该 JEP 建议结合基于反馈，并在 Java 16 中重新孵化 API。此 API 更新中包含以下更改：

- `MemorySegment`和`MemoryAddress`接口之间的角色分离更清晰；
- 一个新的接口，`MemoryAccess`它提供了通用的静态内存访问器，以便`VarHandle`在简单的情况下最大限度地减少对API的需求；
- 支持*共享*段；和
- 使用`Cleaner`.

> 目标

- *通用性：*单个 API 应该能够对各种外部内存（例如，本机内存、持久内存、托管堆内存等）进行操作。
- *安全性：*无论操作何种内存，API 都不应该破坏 JVM 的安全性。
- *控制：*客户端应该可以选择如何释放内存段：显式（通过方法调用）或隐式（当该段不再使用时）。
- *可用性：*对于需要访问外部内存的程序，API 应该是传统 Java API（如[`sun.misc.Unsafe`](https://hg.openjdk.java.net/jdk/jdk/file/tip/src/jdk.unsupported/share/classes/sun/misc/Unsafe.java).



> 原因

许多 Java 程序访问外部内存，例如[Ignite](https://apacheignite.readme.io/v1.0/docs/off-heap-memory)、[mapDB](http://www.mapdb.org/)、[memcached](https://github.com/dustin/java-memcached-client)、[Lucene](https://lucene.apache.org/)和 Netty 的[ByteBuf](https://netty.io/wiki/using-as-a-generic-library.html) API。通过这样做，他们可以：

- 避免与垃圾收集相关的成本和不可预测性（尤其是在维护大型缓存时）；
- 跨多个进程共享内存；和
- 通过将文件映射到内存（例如，通过[`mmap`](https://en.wikipedia.org/wiki/Mmap)）来序列化和反序列化内存内容。

不幸的是，Java API 并没有为访问外部内存提供令人满意的解决方案：

- Java 1.4 中引入的[`ByteBuffer`API](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/nio/ByteBuffer.html)允许创建*直接*字节缓冲区，这些缓冲区在堆外分配，因此允许用户直接从 Java 操作堆外内存。但是，直接缓冲区是有限的。例如，无法创建大于 2 GB 的直接缓冲区，因为`ByteBuffer`API 使用`int`基于索引的方案。此外，使用直接缓冲区可能很麻烦，因为与它们相关联的内存的释放留给垃圾收集器；也就是说，只有在垃圾收集器认为直接缓冲区不可访问后，才能释放相关内存。多年来，为了克服这些和其他限制（例如[4496703](https://bugs.openjdk.java.net/browse/JDK-4496703)、[6558368](https://bugs.openjdk.java.net/browse/JDK-6558368)，[4837564](https://bugs.openjdk.java.net/browse/JDK-4837564)和[5029431](https://bugs.openjdk.java.net/browse/JDK-5029431)）。许多这些限制源于这样一个事实，即`ByteBuffer`API 不仅设计用于堆外内存访问，而且还用于生产者/消费者在字符集编码/解码和部分 I/O 操作等领域交换批量数据。
- 开发人员可以从 Java 代码访问外部内存的另一个常见途径是[`Unsafe`API](https://hg.openjdk.java.net/jdk/jdk/file/tip/src/jdk.unsupported/share/classes/sun/misc/Unsafe.java)。由于相对通用的寻址模型`Unsafe`，公开了许多适用于堆上和堆外访问的内存访问操作（例如，`Unsafe::getInt`和`putInt`）。使用`Unsafe`访问内存是非常有效的：所有的内存访问操作被定义为热点JVM内部函数，所以存储器存取操作是由热点JIT编译器优化常规。然而，`Unsafe`根据定义，API 是*不安全的*——它允许访问*任何*内存位置（例如，`Unsafe::getInt`获取`long`地址）。这意味着 Java 程序可以通过访问一些已经释放的内存位置来使 JVM 崩溃。最重要的是，`Unsafe`API 不是受支持的 Java API，一直[强烈不鼓励](https://web.archive.org/web/19980215011039/http://java.sun.com/products/jdk/faq/faq-sun-packages.html)使用它。
- 使用 JNI 访问外部内存是一种可能，但与此解决方案相关的固有成本使其在实践中很少适用。整个开发流程很复杂，因为 JNI 要求开发人员编写和维护 C 代码片段。JNI 本身也很慢，因为每次访问都需要从 Java 到本机的转换。

总之，在访问外部内存时，开发人员面临着两难选择：他们应该选择安全但有限（并且可能效率较低）的路径，例如`ByteBuffer`API，还是应该放弃安全保证并接受危险且不受支持的路径？`Unsafe`API？

此 JEP 为外部内存访问引入了安全、受支持且高效的 API。通过为访问外部内存的问题提供有针对性的解决方案，开发人员将摆脱现有 API 的限制和危险。他们还将享受更高的性能，因为新的 API 将在设计时考虑到 JIT 优化。

> 具体操作

Foreign-Memory Access API 作为一个名为的[孵化器模块](https://openjdk.java.net/jeps/11)提供`jdk.incubator.foreign`，位于同名的包中。它引入了三个主要抽象：`MemorySegment`，`MemoryAddress`和`MemoryLayout`：

- `MemorySegment`对具有给定空间和时间界限的连续内存区域进行建模，
- `MemoryAddress对一个地址建模，该地址可以驻留在堆上或堆外，并且
- `MemoryLayout`是对内存段内容的编程描述。

可以从各种来源创建内存段，例如本机内存缓冲区、内存映射文件、Java 数组和字节缓冲区（直接或基于堆）。例如，可以按如下方式创建本机内存段：

```
try (MemorySegment segment = MemorySegment.allocateNative(100)) {
   ...
}
```

这将创建一个与大小为 100 字节的本机内存缓冲区相关联的内存段。

内存段在*空间上是有界的*，这意味着它们有下限和上限。任何尝试使用该段访问这些边界之外的内存都将导致异常。

正如`try`上面使用-with-resource 构造所证明的那样，内存段也是有*时间限制的*，这意味着它们必须被创建、使用，然后在不再使用时关闭。关闭一个段会导致额外的副作用，例如与该段相关联的内存的释放。任何访问已关闭内存段的尝试都会导致异常。空间和时间边界共同保证了外部内存访问 API 的安全性，从而保证它的使用不会使 JVM 崩溃。

### 11_JEP 396：默认情况下强封装 JDK 内部

> 概述

 默认情况下，强封装 JDK 的所有内部元素，除了[关键的内部 API](https://openjdk.java.net/jeps/260#Description)，如`sun.misc.Unsafe`. 允许最终用户选择自 JDK 9 以来一直默认的宽松强封装。 

> 目标

- 继续提高 JDK 的安全性和可维护性，这是[Project Jigsaw](https://openjdk.java.net/projects/jigsaw)的主要目标之一。
- 鼓励开发人员从使用内部元素迁移到使用标准 API，以便他们和他们的用户可以轻松升级到未来的 Java 版本。

> 动机

多年来，各种库、框架、工具和应用程序的开发人员以损害安全性和可维护性的方式使用 JDK 的内部元素。特别是：

- 包的一些非`public`类、方法和字段`java.*`定义了特权操作，例如[在特定类加载器中定义新类的能力](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/ClassLoader.html#defineClass(java.lang.String,byte[],int,int))，而其他则传送敏感数据，例如[加密密钥](https://youtu.be/Vxfd3ehdAZc?t=1248)。尽管在`java.*`包中，但这些元素是 JDK 的内部元素。外部代码通过反射使用这些内部元素会使平台的安全性处于危险之中。
- 包的所有类、方法和字段`sun.*`都是 JDK 的内部 API。一些类，方法和属性`com.sun.*`， `jdk.*`以及`org.*`包也是内部API。这些 API 从来都不是标准的，从来没有得到支持，也[从来没有打算供外部使用](http://web.archive.org/web/19980215011039/http://java.sun.com/products/jdk/faq/faq-sun-packages.html)。外部代码使用这些内部元素是一种持续的维护负担。为了不破坏现有代码，保留这些 API 所花费的时间和精力可以更好地用于推动平台向前发展。

在 Java 9 中，我们通过利用模块来[限制对其内部元素的访问](https://openjdk.java.net/jeps/260)，提高了 JDK 的安全性和可维护性。模块提供*强封装*，这意味着

- 模块外的代码只能访问该 模块导出的包的`public`和`protected`元素，并且
- `protected` 此外，元素只能从定义它们的类的子类中访问。

强封装适用于编译时和运行时，包括编译代码尝试在运行时通过反射访问元素时。`public`导出包的非元素和未导出包的所有元素都被称为*强封装*。

在 JDK 9 及更高版本中，我们强烈封装了所有新的内部元素，从而限制了对它们的访问。然而，为了帮助迁移，我们故意选择不在运行时强封装 JDK 8 中存在的包的内容。因此类路径上的库和应用程序代码可以继续使用反射来访问非`public`元素的`java.*`包，以及所有元素`sun.*`和其他内部包，用于封装在JDK 8存在这种安排被称为[*放松强大的封装性*](https://openjdk.java.net/jeps/261#Relaxed-strong-encapsulation)。

我们于 2017 年 9 月发布了 JDK 9。 JDK 的大多数常用内部元素现在都有[标准替代品](https://wiki.openjdk.java.net/display/JDK8/Java+Dependency+Analysis+Tool#JavaDependencyAnalysisTool-ReplaceusesoftheJDK'sinternalAPIs)。开发人员必须在三年中，从标准的API，如JDK的内部元素迁移走 `java.lang.invoke.MethodHandles.Lookup::defineClass`，`java.util.Base64`和`java.lang.ref.Cleaner`。许多库、框架和工具维护者已经完成了迁移并发布了其组件的更新版本。我们现在准备迈出下一步，按照`sun.misc.Unsafe`Project Jigsaw 最初计划的那样，对 JDK 的所有内部元素进行强封装——除了关键的内部 API，例如。

> 具体操作

松弛强封装由启动器选项控制 `--illegal-access`。这个选项由[JEP 261](https://openjdk.java.net/jeps/261#Relaxed-strong-encapsulation)引入，被挑衅地命名以阻止它的使用。它目前的工作原理如下：

- `--illegal-access=permit`安排 JDK 8 中存在的每个包都对未命名模块中的代码[开放](https://docs.oracle.com/javase/specs/jls/se15/html/jls-7.html#jls-7.7.2)。因此，类路径上的代码可以继续使用反射来访问包的非公共元素`java.*`，以及`sun.*` JDK 8 中存在的包和其他内部包的所有元素。对任何此类元素的第一次反射访问操作会导致发出警告，但在那之后不会发出警告。

  自 JDK 9 以来，此模式一直是默认模式。

- `--illegal-access=warn``permit`除了针对每个非法反射访问操作发出警告消息之外，其他都相同。

- `--illegal-access=debug``warn`除了为每个非法反射访问操作发出警告消息和堆栈跟踪之外，其他都相同。

- `--illegal-access=deny`禁用所有非法访问操作，但由其他命令行选项启用的操作除外，*例如*， `--add-opens`。

作为对 JDK 的所有内部元素进行强封装的下一步，我们建议将`--illegal-access` 选项的默认模式从`permit`更改为`deny`。通过此更改，默认情况下将不再打开JDK 8 中存在且不包含[关键内部 API 的包](https://openjdk.java.net/jeps/260#Description)；[此处](https://cr.openjdk.java.net/~mr/jigsaw/jdk8-packages-denied-by-default)提供完整列表 。 **该`sun.misc`包仍将由`jdk.unsupported`模块导出，并且仍可通过反射访问。**

我们还将修改[Java 平台规范中](https://cr.openjdk.java.net/~iris/se/9/java-se-9-fr-spec-01/#Relaxing-strong-encapsulation)的相关文本，以禁止在任何 Java 平台实现中默认打开任何包，除非该包明确声明`open`在其包含模块的声明中。

的`permit`，`warn`以及`debug`该模式`--illegal-access`选项将继续工作。这些模式允许最终用户根据需要选择宽松的强封装。

我们预计未来的 JEP 会`--illegal-access`完全取消该选项。那时将无法通过单个命令行选项打开所有 JDK 8 包。仍然可以使用[`--add-opens`](https://openjdk.java.net/jeps/261#Breaking-encapsulation)命令行选项或 [`Add-Opens`](https://openjdk.java.net/jeps/261#Packaging:-Modular-JAR-files)JAR-file 属性来打开特定的包。

为了准备最终删除该`--illegal-access`选项，我们将弃用它作为本 JEP 的一部分进行删除。因此，为`java`启动器指定该选项将导致发出弃用警告。

# JAVA 17

 ## 概述



 JDK 16 刚发布半年（2021/03/16），JDK 17 又如期而至（2021/09/14），这个时间点特殊，蹭苹果发布会的热度？记得当年 [JDK 15](https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247507309&idx=1&sn=e78cfee56a2b5cd617c0370f64f4c83d&scene=21#wechat_redirect) 的发布也是同天

 **Oracle 宣布，从 JDK 17 开始，后面的 JDK 都全部免费提供！！！** 

![1631802196015](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631802196015.png)



 Java 17+ 可以免费使用了，包括商用，更详细的条款可以阅读： 

>  https://www.oracle.com/downloads/licenses/no-fee-license.html 



![1631802323345](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631802323345.png)

JDK 17 是自 2018 年 JDK 11 后的第二个长期支持版本，支持到 2029 年 9 月，支持时间长达 8 年，这下可以不用死守 JDK 8 了，JDK 17+ 也可以是一种新的选择了。下一个第三个长期支持版本是 JDK 21，时间为 2023 年 9 月，这次长期支持版本发布计划改了，不再是原来的 3 年一次，而是改成了 2 年一次！非长期支持版本还是半年发一次不变，下一个非长期支持版本计划在 2022/03 发布



![1631802422250](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631802422250.png)



OpenJDK文档

> https://openjdk.java.net/projects/jdk/17/

![1631802460848](JDK%E5%90%84%E7%89%88%E6%9C%AC%E6%96%B0%E7%89%B9%E6%80%A7.assets/1631802460848.png)

 JDK 17 这个版本提供了 14 个增强功能，另外在性能、稳定性和安全性上面也得到了大量的提升，以及还有一些孵化和预览特性，有了这些新变化，Java 会进一步提高开发人员的生产力。 



## 一 语法层面变化



### 1_JEP 409：密封类



> 概述

 密封类，这个特性在 [JDK 15](https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247507309&idx=1&sn=e78cfee56a2b5cd617c0370f64f4c83d&scene=21#wechat_redirect) 中首次成为预览特性，在 JDK 16 中进行二次预览，在 JDK 17 这个版本中终于正式转正了。 

> 历史

 密封类是由[JEP 360](https://openjdk.java.net/jeps/360)提出的，并在[JDK 15 中](https://openjdk.java.net/projects/jdk/15)作为 [预览功能提供](https://openjdk.java.net/jeps/12)。它们由[JEP 397](https://openjdk.java.net/jeps/397)再次提出并进行了改进，并作为预览功能在 [JDK 16](https://openjdk.java.net/projects/jdk/16)中提供。该 JEP 建议在 JDK 17 中完成密封类，与 JDK 16 没有任何变化。 

> 目标

- 允许类或接口的作者控制负责实现它的代码。
- 提供比访问修饰符更具声明性的方式来限制超类的使用。
- 通过为[模式](https://cr.openjdk.java.net/~briangoetz/amber/pattern-match.html)的详尽分析提供基础，支持[模式匹配](https://cr.openjdk.java.net/~briangoetz/amber/pattern-match.html)的未来方向。

> 原因

类和接口的继承层次结构的面向对象数据模型已被证明在对现代应用程序处理的现实世界数据进行建模方面非常有效。这种表现力是 Java 语言的一个重要方面。

然而，在某些情况下，可以有效地控制这种表现力。例如，Java 支持*枚举类*来模拟给定类只有固定数量实例的情况。在以下代码中，枚举类列出了一组固定的行星。它们是该类的唯一值，因此您可以彻底切换它们——无需编写`default`子句：

```
enum Planet { MERCURY, VENUS, EARTH }

Planet p = ...
switch (p) {
  case MERCURY: ...
  case VENUS: ...
  case EARTH: ...
}
```

使用枚举类值的模型固定集通常是有帮助的，但有时我们想一套固定的模型*种*价值。我们可以通过使用类层次结构来做到这一点，而不是作为代码继承和重用的机制，而是作为列出各种值的一种方式。以我们的行星示例为基础，我们可以对天文领域中的各种值进行建模，如下所示：

```
interface Celestial { ... }
final class Planet implements Celestial { ... }
final class Star   implements Celestial { ... }
final class Comet  implements Celestial { ... }
```

然而，这种层次结构并没有反映重要的领域知识，即我们的模型中只有三种天体。在这些情况下，限制子类或子接口的集合可以简化建模。

考虑另一个例子：在一个图形库中，一个类的作者`Shape` 可能希望只有特定的类可以扩展`Shape`，因为该库的大部分工作涉及以适当的方式处理每种形状。作者对处理 的已知子类的代码的清晰度感兴趣 `Shape`，而对编写代码来防御 的未知子类不感兴趣`Shape`。允许任意类扩展`Shape`，从而继承其代码以供重用，在这种情况下不是目标。不幸的是，Java 假定代码重用始终是一个目标：如果`Shape`可以扩展，那么它可以扩展任意数量的类。放宽这个假设是有帮助的，这样作者就可以声明一个类层次结构，该层次结构不能被任意类扩展。在这样一个封闭的类层次结构中，代码重用仍然是可能的，但不能超出。

Java 开发人员熟悉限制子类集的想法，因为它经常出现在 API 设计中。该语言在这方面提供了有限的工具：要么创建一个类`final`，使其具有零个子类，要么使该类或其构造函数成为包私有的，因此它只能在同一个包中具有子类。包私有超类的示例 [出现在 JDK 中](https://github.com/openjdk/jdk/tree/master/src/java.base/share/classes/java/lang)：

```
package java.lang;

abstract class AbstractStringBuilder { ... }
public final class StringBuffer  extends AbstractStringBuilder { ... }
public final class StringBuilder extends AbstractStringBuilder { ... }
```

当目标是代码重用时，包私有方法很有用，例如`AbstractStringBuilder`让`append`. 然而，当目标是对替代方案进行建模时，这种方法是无用的，因为用户代码无法访问关键抽象——超类——以 `switch`覆盖它。允许用户访问超类而不允许他们扩展它是无法指定的，除非诉诸涉及非`public`构造函数的脆弱技巧——这些技巧不适用于接口。在声明`Shape`及其子类的图形库中，如果只有一个包可以访问`Shape`.

总之，超类应该可以被广泛*访问* （因为它代表用户的重要抽象）但不能广泛 *扩展*（因为它的子类应该仅限于作者已知的那些）。这样一个超类的作者应该能够表达它是与一组给定的子类共同开发的，既为读者记录意图，又允许 Java 编译器强制执行。同时，超类不应过度约束其子类，例如，强迫它们成为`final`或阻止它们定义自己的状态。

### 2_JEP 406：switch模式匹配（预览）

> 概述

 使用`switch` 表达式和语句的模式匹配以及对模式语言的扩展来增强 Java 编程语言。扩展模式匹配以`switch`允许针对多个模式测试表达式，每个模式都有特定的操作，以便可以简洁安全地表达复杂的面向数据的查询。 

 instanceof 模式匹配是JAVA14 非常赞的一个新特性！ 这次在 JDK 17 中为 switch 语句支持模式匹配 

> 目标

- `switch`通过允许模式出现在`case`标签中来扩展表达式和语句的表现力和适用性。
- 允许在`switch`需要时放松对历史的零敌意。
- 引入两种新的模式：*保护模式*，允许使用任意布尔表达式细化模式匹配逻辑，以及带 *括号的模式*，以解决一些解析歧义。
- 确保所有现有的`switch`表达式和语句继续编译而不做任何更改并以相同的语义执行。
- 不要引入`switch`与传统`switch` 构造分离的具有模式匹配语义的新式表达式或语句。
- `switch`当 case 标签是模式与 case 标签是传统常量时，不要使表达式或语句的行为不同。

老式的写法

``` java
static String formatter(Object o) {
    String formatted = "unknown";
    if (o instanceof Integer i) {
        formatted = String.format("int %d", i);
    } else if (o instanceof Long l) {
        formatted = String.format("long %d", l);
    } else if (o instanceof Double d) {
        formatted = String.format("double %f", d);
    } else if (o instanceof String s) {
        formatted = String.format("String %s", s);
    }
    return formatted;
}
```

支持模式匹配的switch

``` java
static String formatterPatternSwitch(Object o) {
    return switch (o) {
        case Integer i -> String.format("int %d", i);
        case Long l    -> String.format("long %d", l);
        case Double d  -> String.format("double %f", d);
        case String s  -> String.format("String %s", s);
        default        -> o.toString();
    };
}
```

 直接在 switch 上支持 Object 类型，这就等于同时支持多种类型，使用模式匹配得到具体类型，大大简化了语法量，这个功能还是挺实用的， 目前看转正只是一个时间上的问题而已.

## 二 API层面变化

### 1_JEP 414：Vector API（第二个孵化器）

> 概括

 引入一个 API 来表达向量计算，这些计算在运行时可靠地编译为支持的 CPU 架构上的最佳向量指令，从而实现优于等效标量计算的性能。 



> 历史

Vector API 由[JEP 338](https://openjdk.java.net/jeps/338)提出并作为[孵化 API](https://openjdk.java.net/jeps/11)集成到 Java 16 中。我们在此建议结合改进以响应反馈以及性能改进和其他重要的实施改进。我们包括以下显着变化：

- 增强 API 以支持字符操作，例如 UTF-8 字符解码。具体来说，我们添加了在`short`向量和`char`数组之间复制字符的方法，以及用于与整数向量进行无符号比较的新向量比较运算符。
- 用于将`byte`向量与`boolean` 数组相互转换的 API 的增强功能。
- 使用英特尔的[短向量数学库 (SVML)](https://software.intel.com/content/www/us/en/develop/documentation/cpp-compiler-developer-guide-and-reference/top/compiler-reference/intrinsics/intrinsics-for-intel-advanced-vector-extensions-512-intel-avx-512-instructions/intrinsics-for-arithmetic-operations-1/intrinsics-for-short-vector-math-library-svml-operations.html)对 x64 上的超越和三角泳道运算的内在支持。
- Intel x64 和 ARM NEON 实现的一般性能增强。

> 目标

- *清晰简洁的 API* ——API 应该能够清晰简洁地表达广泛的向量计算，这些向量计算由循环内组成的向量操作序列组成，可能还有控制流。应该可以表达与向量大小或每个向量的车道数相关的通用计算，从而使此类计算能够跨支持不同向量大小的硬件进行移植。
- *平台不可知——API*应该是 CPU 架构不可知的，支持在支持向量指令的多种架构上实现。就像在 Java API 中一样，平台优化和可移植性发生冲突，那么偏向于使 API 可移植，即使这会导致某些特定于平台的习语无法在可移植代码中表达。
- *在 x64 和 AArch64 架构上可靠的运行时编译和性能*——在强大的 x64 架构上，Java 运行时，特别是 HotSpot C2 编译器，应该将向量操作编译为相应的高效和高性能向量指令，例如那些由[Streaming SIMD Extensions](https://en.wikipedia.org/wiki/Streaming_SIMD_Extensions) (SSE) 和[Advanced](https://en.wikipedia.org/wiki/Advanced_Vector_Extensions)支持的 [指令](https://en.wikipedia.org/wiki/Streaming_SIMD_Extensions)[矢量扩展](https://en.wikipedia.org/wiki/Advanced_Vector_Extensions) (AVX)。开发人员应该相信他们表达的向量操作将可靠地映射到相关的向量指令。在功能强大的 ARM AArch64 架构上，C2 将类似地将向量操作编译为[NEON](https://en.wikipedia.org/wiki/ARM_architecture#Advanced_SIMD_(Neon))支持的向量指令。

- *优雅降级*——有时向量计算无法在运行时完全表达为向量指令序列，这可能是因为架构不支持某些所需的指令。在这种情况下，Vector API 实现应该优雅地降级并仍然起作用。如果无法将向量计算有效地编译为向量指令，则这可能涉及发出警告。在没有向量的平台上，优雅降级将产生与手动展开循环竞争的代码，其中展开因子是所选向量中的通道数。

> 原因

向量计算由对向量的一系列操作组成。向量包含（通常）固定的标量值序列，其中标量值对应于硬件定义的向量通道的数量。应用于具有相同通道数的两个向量的二元运算，对于每个通道，将对来自每个向量的相应两个标量值应用等效的标量运算。这通常称为[单指令多数据](https://en.wikipedia.org/wiki/SIMD)(SIMD)。

向量运算表达了一定程度的并行性，可以在单个 CPU 周期内执行更多工作，从而显着提高性能。例如，给定两个向量，每个向量包含八个整数的序列（即八个通道），可以使用单个硬件指令将这两个向量相加。向量加法指令对十六个整数进行运算，执行八次整数加法，而通常对两个整数进行运算，执行一次整数加法所需的时间。

HotSpot 已经支持[自动向量化](https://cr.openjdk.java.net/~vlivanov/talks/2017_Vectorization_in_HotSpot_JVM.pdf)，它将标量操作转换为超字操作，然后映射到向量指令。可转换的标量操作集是有限的，并且在代码形状的变化方面也很脆弱。此外，可能仅使用可用向量指令的子集，从而限制了生成代码的性能。

今天，希望编写可靠地转换为超字操作的标量操作的开发人员需要了解 HotSpot 的自动矢量化算法及其局限性，以实现可靠和可持续的性能。在某些情况下，可能无法编写可转换的标量操作。例如，HotSpot 不会转换用于计算数组散列码的简单标量操作（因此是 [`Arrays::hashCode`](https://github.com/openjdk/jdk/blob/2a406f3ce5e200af9909ce051fdeed0cc059fea0/src/java.base/share/classes/java/util/Arrays.java#L4251)方法），也不能自动矢量化代码以按字典顺序比较两个数组（因此我们[添加了用于字典比较的内在函数](https://bugs.openjdk.java.net/browse/JDK-8033148)）。

Vector API 旨在通过提供一种在 Java 中编写复杂矢量算法的方法来改善这种情况，使用现有的 HotSpot 自动矢量化器，但使用用户模型使矢量化更加可预测和健壮。手工编码的向量循环可以表达高性能算法，例如向量化`hashCode`或专门的数组比较，自动向量化器可能永远不会优化这些算法。许多领域都可以从这个显式向量 API 中受益，包括机器学习、线性代数、密码学、金融和 JDK 本身的代码。

###  2_JEP 415：特定于上下文的反序列化过滤器

> 概括

 允许应用程序通过 JVM 范围的过滤器工厂配置特定于上下文和动态选择的反序列化过滤器，该工厂被调用以为每个单独的反序列化操作选择一个过滤器 

> 原因

反序列化不受信任的数据是一种固有的危险活动，因为传入数据流的内容决定了创建的对象、其字段的值以及它们之间的引用。在许多典型用途中，流中的字节是从未知、不受信任或未经身份验证的客户端接收的。通过仔细构建流，攻击者可以导致恶意执行任意类中的代码。如果对象构造具有改变状态或调用其他操作的副作用，那么这些操作可能会损害应用程序对象、库对象甚至 Java 运行时的完整性。禁用反序列化攻击的关键是防止任意类的实例被反序列化，从而防止直接或间接执行它们的方法。

我们在 Java 9 中引入了[反序列化过滤器 (JEP 290)](https://openjdk.java.net/jeps/290)，使应用程序和库代码能够在反序列化之前[验证传入的数据流](https://docs.oracle.com/en/java/javase/16/core/serialization-filtering1.html#GUID-55BABE96-3048-4A9F-A7E6-781790FF3480)。此类代码[`java.io.ObjectInputFilter`](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/io/ObjectInputFilter.html)在创建反序列化流（即 a [`java.io.ObjectInputStream`](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/io/ObjectInputStream.html)）时提供验证逻辑作为 a 。

依赖流的创建者来明确请求验证有几个限制。这种方法不能扩展，并且很难在代码发布后更新过滤器。它也不能对应用程序中第三方库执行的反序列化操作进行过滤。

为了解决这些限制，JEP 290 还引入了一个 JVM 范围的反序列化过滤器，可以通过 API、系统属性或安全属性进行设置。此过滤器是*静态的，*因为它在启动时只指定一次。使用静态 JVM 范围过滤器的经验表明，它也有局限性，尤其是在具有库层和多个执行上下文的复杂应用程序中。对每个使用 JVM 范围的过滤器 `ObjectInputStream`要求过滤器覆盖应用程序中的每个执行上下文，因此过滤器通常会变得过于包容或过于严格。

更好的方法是以不需要每个流创建者参与的方式配置每个流过滤器。

为了保护 JVM 免受反序列化漏洞的影响，应用程序开发人员需要清楚地描述每个组件或库可以序列化或反序列化的对象。对于每个上下文和用例，开发人员应该构建并应用适当的过滤器。例如，如果应用程序使用特定库来反序列化特定对象群组，则可以在调用库时应用相关类的过滤器。创建类的允许列表并拒绝其他所有内容，可以防止流中未知或意外的对象。封装或其他自然应用程序或库分区边界可用于缩小允许或绝对不允许的对象集。

应用程序的开发人员处于了解应用程序组件的结构和操作的最佳位置。此增强功能使应用程序开发人员能够构建过滤器并将其应用于每个反序列化操作。

> 具体操作

如上所述，JEP 290 引入了 per-stream 反序列化过滤器和静态 JVM-wide 过滤器。每当`ObjectInputStream`创建一个时，它的每个流过滤器都会被初始化为静态 JVM 范围的过滤器。如果需要，可以稍后将每个流过滤器更改为不同的过滤器。

这里我们介绍一个可配置的 JVM 范围的*过滤器工厂*。每当`ObjectInputStream`创建an 时 ，它的每个流过滤器都会初始化为通过调用静态 JVM 范围过滤器工厂返回的值。因此，这些过滤器是*动态的*和*特定*于*上下文的*，与单个静态 JVM 范围的反序列化过滤器不同。为了向后兼容，如果未设置过滤器工厂，则内置工厂将返回静态 JVM 范围的过滤器（如果已配置）。

过滤器工厂用于 Java 运行时中的每个反序列化操作，无论是在应用程序代码、库代码中，还是在 JDK 本身的代码中。该工厂特定于应用程序，应考虑应用程序中的每个反序列化执行上下文。过滤器工厂从`ObjectInputStream`构造函数调用，也从 `ObjectInputStream.setObjectInputFilter`. 参数是当前过滤器和新过滤器。从构造函数调用时，当前过滤器是`null`，新过滤器是静态 JVM 范围的过滤器。工厂确定并返回流的初始过滤器。工厂可以使用其他特定于上下文的控件创建复合过滤器，或者只返回静态 JVM 范围的过滤器。如果`ObjectInputStream.setObjectInputFilter`被调用，工厂被第二次调用，并使用第一次调用返回的过滤器和请求的新过滤器。工厂决定如何组合两个过滤器并返回过滤器，替换流上的过滤器。

对于简单的情况，过滤器工厂可以为整个应用程序返回一个固定的过滤器。例如，这里有一个过滤器，它允许示例类，允许`java.base`模块中的类，并拒绝所有其他类：

```
var filter = ObjectInputFilter.Config.createFilter("example.*;java.base/*;!*")
```

在具有多个执行上下文的应用程序中，过滤器工厂可以通过为每个上下文提供自定义过滤器来更好地保护各个上下文。构建流时，过滤器工厂可以根据当前线程本地状态、调用者的层次结构、库、模块和类加载器来识别执行上下文。此时，用于创建或选择过滤器的策略可以根据上下文选择特定过滤器或过滤器组合。

如果存在多个过滤器，则可以合并它们的结果。组合过滤器的一种有用方法是，如果任何过滤器拒绝反序列化，则拒绝反序列化，如果任何过滤器允许，则允许反序列化，否则保持未定状态。

> 命令行使用

属性`jdk.serialFilter`和`jdk.serialFilterFactory`可以
在命令行上设置过滤器和过滤器工厂。现有`jdk.serialFilter`属性设置基于模式的过滤器。

该`jdk.serialFilterFactory`属性是在第一次反序列化之前要设置的过滤器工厂的类名。该类必须是公共的，并且可由应用程序类加载器访问。

为了与 JEP 290 兼容，如果`jdk.serialFilterFactory`未设置属性，则过滤器工厂将设置为提供与早期版本兼容的内置函数。

> 应用程序接口

我们在`ObjectInputFilter.Config`类中定义了两个方法来设置和获取 JVM 范围的过滤器工厂。过滤器工厂是一个有两个参数的函数，一个当前过滤器和一个下一个过滤器，它返回一个过滤器。

```java
/**
 * Return the JVM-wide deserialization filter factory.
 *
 * @return the JVM-wide serialization filter factory; non-null
 */
public static BinaryOperator<ObjectInputFilter> getSerialFilterFactory();

/**
 * Set the JVM-wide deserialization filter factory.
 *
 * The filter factory is a function of two parameters, the current filter
 * and the next filter, that returns the filter to be used for the stream.
 *
 * @param filterFactory the serialization filter factory to set as the
 * JVM-wide filter factory; not null
 */
public static void setSerialFilterFactory(BinaryOperator<ObjectInputFilter> filterFactory);
```

> 示例

这个类展示了如何过滤到当前线程中发生的每个反序列化操作。它定义了一个线程局部变量来保存每个线程的过滤器，定义一个过滤器工厂来返回该过滤器，将工厂配置为 JVM 范围的过滤器工厂，并提供一个实用函数来`Runnable`在特定的 per 上下文中运行-线程过滤器。

```java
public class FilterInThread implements BinaryOperator<ObjectInputFilter> {

    // ThreadLocal to hold the serial filter to be applied
    private final ThreadLocal<ObjectInputFilter> filterThreadLocal = new ThreadLocal<>();

    // Construct a FilterInThread deserialization filter factory.
    public FilterInThread() {}

    /**
     * The filter factory, which is invoked every time a new ObjectInputStream
     * is created.  If a per-stream filter is already set then it returns a
     * filter that combines the results of invoking each filter.
     *
     * @param curr the current filter on the stream
     * @param next a per stream filter
     * @return the selected filter
     */
    public ObjectInputFilter apply(ObjectInputFilter curr, ObjectInputFilter next) {
        if (curr == null) {
            // Called from the OIS constructor or perhaps OIS.setObjectInputFilter with no current filter
            var filter = filterThreadLocal.get();
            if (filter != null) {
                // Prepend a filter to assert that all classes have been Allowed or Rejected
                filter = ObjectInputFilter.Config.rejectUndecidedClass(filter);
            }
            if (next != null) {
                // Prepend the next filter to the thread filter, if any
                // Initially this is the static JVM-wide filter passed from the OIS constructor
                // Append the filter to reject all UNDECIDED results
                filter = ObjectInputFilter.Config.merge(next, filter);
                filter = ObjectInputFilter.Config.rejectUndecidedClass(filter);
            }
            return filter;
        } else {
            // Called from OIS.setObjectInputFilter with a current filter and a stream-specific filter.
            // The curr filter already incorporates the thread filter and static JVM-wide filter
            // and rejection of undecided classes
            // If there is a stream-specific filter prepend it and a filter to recheck for undecided
            if (next != null) {
                next = ObjectInputFilter.Config.merge(next, curr);
                next = ObjectInputFilter.Config.rejectUndecidedClass(next);
                return next;
            }
            return curr;
        }
    }

    /**
     * Apply the filter and invoke the runnable.
     *
     * @param filter the serial filter to apply to every deserialization in the thread
     * @param runnable a Runnable to invoke
     */
    public void doWithSerialFilter(ObjectInputFilter filter, Runnable runnable) {
        var prevFilter = filterThreadLocal.get();
        try {
            filterThreadLocal.set(filter);
            runnable.run();
        } finally {
            filterThreadLocal.set(prevFilter);
        }
    }
}
```

如果已经设置了特定于流的过滤器， `ObjectInputStream::setObjectFilter`则过滤器工厂将该过滤器与下一个过滤器组合。如果任一过滤器拒绝一个类，则该类将被拒绝。如果任一过滤器允许该类，则该类被允许。否则，结果未定。

这是使用`FilterInThread`该类的一个简单示例：

```java
// Create a FilterInThread filter factory and set
    var filterInThread = new FilterInThread();
    ObjectInputFilter.Config.setSerialFilterFactory(filterInThread);

    // Create a filter to allow example.* classes and reject all others
    var filter = ObjectInputFilter.Config.createFilter("example.*;java.base/*;!*");
    filterInThread.doWithSerialFilter(filter, () -> {
          byte[] bytes = ...;
          var o = deserializeObject(bytes);
    });
```





## 三 其他变化

### 1_JEP 306：恢复始终严格的浮点语义

> 概括

 使浮点运算始终严格，而不是同时具有严格的浮点语义 ( `strictfp`) 和略有不同的默认浮点语义。这将恢复语言和 VM 的原始浮点语义，匹配 Java SE 1.2 中引入严格和默认浮点模式之前的语义。 

> 目标

- 简化数字敏感库的开发，包括`java.lang.Math`和`java.lang.StrictMath`.
- 在平台的一个棘手方面提供更多的规律性。

> 原因

1990 年代后期改变平台默认浮点语义的动力源于原始 Java 语言和 JVM 语义之间的不良交互以及流行的 x86 架构的 x87 浮点协处理器指令集的一些不幸特性. 在所有情况下匹配精确的浮点语义，包括非正规操作数和结果，需要大量额外指令的开销。在没有上溢或下溢的情况下匹配结果可以用更少的开销来实现，这大致是 Java SE 1.2 中引入的修改后的默认浮点语义所允许的。

但是，从 2001 年左右开始在奔腾 4 和更高版本的处理器中提供的 SSE2（流式 SIMD 扩展 2）扩展可以以直接的方式支持严格的 JVM 浮点运算，而不会产生过多的开销。

由于英特尔和 AMD 长期以来都支持 SSE2 和更高版本的扩展，这些扩展允许自然支持严格的浮点语义，因此不再存在具有不同于严格的默认浮点语义的技术动机。

> 具体细节描述

 此 JEP 将修改的接口包括浮点表达式覆盖范围内的 Java 语言规范（请参阅[JLS](https://docs.oracle.com/javase/specs/jls/se16/html/index.html)部分 4.2.3*浮点类型、格式和值*、5.1.13 *值集转换、15.4 *FP-strict Expressions*、对第 15 章后面其他部分的许多小更新）和 Java 虚拟机规范的类似部分（[JVMS](https://docs.oracle.com/javase/specs/jvms/se16/html/index.html) 2.3.2*浮点类型、值集和值*，第 2.8.2 节*浮点模式*，2.8.3*值集）转换*，以及对个别浮点指令的许多小更新）。值集和值集转换的概念将从 JLS 和 JVMS 中删除。JDK 中的实现更改将包括更新 HotSpot 虚拟机，使其永远不会在允许扩展指数值集的浮点模式下运行（这种模式必须存在于`strictfp`操作中）并更新`javac`以发出新的 lint 警告以防止不必要的使用的`strictfp`修饰符。 



### 2_JEP 356：增强型伪随机数生成器

> 概括

 为伪随机数生成器 (PRNG) 提供新的接口类型和实现，包括可跳转的 PRNG 和额外的一类可拆分 PRNG 算法 (LXM)。 

> 目标

- 使在应用程序中交替使用各种 PRNG 算法变得更容易。
- 通过提供 PRNG 对象流更好地支持基于流的编程。
- 消除现有 PRNG 类中的代码重复。
- 小心地保留 class 的现有行为`java.util.Random`。

> 原因

- 使用遗留的 PRNG 类`Random`、`ThreadLocalRandom`和`SplittableRandom`，很难用其他算法替换应用程序中的任何一个，尽管它们都支持几乎相同的方法集。例如，如果一个应用程序使用 class 的实例`Random`，它必然会声明 type 的变量`Random`，它不能保存 class 的实例`SplittableRandom`；更改要使用的应用程序 `SplittableRandom`需要更改用于保存 PRNG 对象的每个变量（包括方法参数）的类型。一个例外是它`ThreadLocalRandom`是 的子类`Random`，纯粹是为了允许 类型的变量`Random`保存 的实例 `ThreadLocalRandom`，但`ThreadLocalRandom`几乎覆盖了 的所有方法`Random`。接口可以轻松解决这个问题。
- 传统类`Random`，`ThreadLocalRandom`以及`SplittableRandom`所有支持等方法，`nextDouble()`和`nextBoolean()`以及流产生方法，如`ints()`和`longs()`，但他们拥有完全独立的，几乎复制和粘贴的方式工作。重构此代码将使其更易于维护，此外，文档将使第三方更容易创建新的 PRNG 类，这些类也支持相同的完整方法套件。
- 2016 年，测试揭示了 class 使用的算法中的两个新弱点`SplittableRandom`。一方面，相对较小的修订可以避免这些弱点。另一方面，还发现了一类新的可拆分 PRNG 算法 (LXM)，其速度几乎一样快，甚至更容易实现，并且似乎完全避免了容易出现的三类弱点 `SplittableRandom`。
- 能够从 PRNG 获取 PRNG 对象流使得使用流方法表达某些类型的代码变得更加容易。
- 文献中有许多 PRNG 算法不是可拆分的，而是可跳跃的（也许也是可跳跃的，即能够进行非常长的跳跃和普通跳跃），这一特性与拆分完全不同，但也有助于支持流PRNG 对象。过去，很难在 Java 中利用这一特性。可跳转 PRNG 算法的示例是 Xoshiro256** 和 Xoroshiro128+。
  - Xoshiro256** 和 Xoroshiro128+：[http](http://xoshiro.di.unimi.it/) ://xoshiro.di.unimi.it

> 具体描述

我们提供了一个新接口，`RandomGenerator`为所有现有的和新的 PRNG 提供统一的 API。`RandomGenerators`提供命名方法`ints`，`longs`，`doubles`，`nextBoolean`，`nextInt`，`nextLong`， `nextDouble`，和`nextFloat`，与他们所有的当前参数的变化。

我们提供了四个新的专门的 RandomGenerator 接口：

- `SplittableRandomGenerator`扩展`RandomGenerator`并提供
  名为`split`and 的方法`splits`。可拆分性允许用户从现有的 RandomGenerator 生成一个新的 RandomGenerator，这通常会产生统计上独立的结果。
- `JumpableRandomGenerator`扩展`RandomGenerator`并提供
  名为`jump`and 的方法`jumps`。可跳跃性允许用户跳过中等数量的平局。
- `LeapableRandomGenerator`扩展`RandomGenerator`并提供
  名为`leap`and 的方法`leaps`。Leapability 允许用户跳过大量的抽奖。
- `ArbitrarilyJumpableRandomGenerator`扩展`LeapableRandomGenerator`并且还提供了`jump`和 的其他变体`jumps`，允许指定任意跳跃距离。

我们提供了一个新类`RandomGeneratorFactory`，用于定位和构造`RandomGenerator`实现的实例。在 `RandomGeneratorFactory`使用`ServiceLoader.Provider`注册API `RandomGenerator`的实现。

我们重构了`Random`, `ThreadLocalRandom`,`SplittableRandom`以便共享它们的大部分实现代码，此外，还使这些代码也可以被其他算法重用。这个重构创建底层非公抽象类`AbstractRandomGenerator`，`AbstractSplittableRandomGenerator`， `AbstractJumpableRandomGenerator`，`AbstractLeapableRandomGenerator`，和 `AbstractArbitrarilyJumpableRandomGenerator`，为每个方法提供仅实现`nextInt()`，`nextLong()`和（如果相关的话）任一`split()`，或`jump()`，或`jump()`和 `leap()`，或`jump(distance)`。经过这次重构，`Random`, `ThreadLocalRandom`, 和`SplittableRandom`继承了 `RandomGenerator`接口。注意，因为`SecureRandom`是 的子类 `Random`，所有的实例`SecureRandom`也自动支持该 `RandomGenerator`接口，无需重新编码`SecureRandom` 类或其任何相关的实现引擎。

我们还添加了底层非公共类，这些类扩展`AbstractSplittableRandomGenerator` （并因此实现`SplittableRandomGenerator`和`RandomGenerator`）以支持 LXM 系列 PRNG 算法的六个特定成员：

- `L32X64MixRandom`
- `L32X64StarStarRandom`
- `L64X128MixRandom`
- `L64X128StarStarRandom`
- `L64X256MixRandom`
- `L64X1024MixRandom`
- `L128X128MixRandom`
- `L128X256MixRandom`
- `L128X1024MixRandom`

LXM 算法的中心 nextLong（或 nextInt）方法的结构遵循 Sebastiano Vigna 在 2017 年 12 月提出的建议，即使用一个 LCG 子生成器和一个基于异或的子生成器（而不是两个 LCG 子生成器）将提供更长的周期、优越的等分布、可扩展性和更好的质量。这里的每个具体实现都结合了目前最著名的基于异或的生成器之一（xoroshiro 或 xoshiro，由 Blackman 和 Vigna 在“Scrambled Linear Pseudorandom Number Generators”，ACM Trans. Math. Softw.，2021 中描述）与一个 LCG使用目前最著名的乘数之一（通过 Steele 和 Vigna 在 2019 年搜索更好的乘数找到），然后应用 Doug Lea 确定的混合函数。

我们还提供了这些广泛使用的 PRNG 算法的实现：

- `Xoshiro256PlusPlus`
- `Xoroshiro128PlusPlus`

上面提到的非公共抽象实现将来可能会作为随机数实现器 SPI 的一部分提供。

这套算法为 Java 程序员提供了空间、时间、质量和与其他语言兼容性之间的合理范围的权衡。

### 3_JEP 382：新的 macOS 渲染管线

> 概括

 使用 Apple Metal API 为 macOS 实现 Java 2D 内部渲染管道，作为现有管道的替代方案，现有管道使用已弃用的 Apple OpenGL API。 



> 目标

- 为使用 macOS Metal 框架的 Java 2D API 提供功能齐全的渲染管道。
- 如果 Apple 从未来版本的 macOS 中删除已弃用的 OpenGL API，请做好准备。
- 确保新管道到 Java 应用程序的透明度。
- 确保实现与现有 OpenGL 管道的功能奇偶校验。
- 在选定的实际应用程序和基准测试中提供与 OpenGL 管道一样好或更好的性能。
- 创建适合现有 Java 2D 管道模型的干净架构。
- 与 OpenGL 管道共存，直到它过时。

> 原因

两个主要因素促使在 macOS 上引入新的基于 Metal 的渲染管道：

- Apple于 2018 年 9 月[在 macOS 10.14 中弃用了 OpenGL 渲染库。 macOS 上的](https://developer.apple.com/documentation/macos_release_notes/macos_mojave_10_14_release_notes?language=objc#3035786)Java 2D 完全依赖 OpenGL 进行其内部渲染管道，因此需要新的管道实现。
- Apple 声称[Metal 框架](https://developer.apple.com/metal/)（它们替代 OpenGL）具有卓越的性能。对于 Java 2D API，通常是这种情况，但有一些例外。

> 具体描述

大多数图形 Java 应用程序是使用 Swing UI 工具包编写的，该工具包通过 Java 2D API 呈现。在内部，Java 2D 可以使用软件渲染和屏幕上的 blit，也可以使用特定于平台的 API，例如 Linux 上的 X11/Xrender、Windows 上的 Direct3D 或 macOS 上的 OpenGL。这些特定于平台的 API 通常提供比软件渲染更好的性能，并且通常会减轻 CPU 的负担。Metal 是用于此类渲染的新 macOS 平台 API，取代了已弃用的 OpenGL API。（该名称与 Swing “金属”外观和感觉无关；这只是巧合。）

我们创建了大量新的内部实现代码来使用 Metal 框架，就像我们已经为其他特定于平台的 API 所做的那样。虽然很容易适应现有框架，但新代码在使用图形硬件方面更加现代，使用着色器而不是固定功能管道。这些更改仅限于特定于 macOS 的代码，甚至只更新了 Metal 和 OpenGL 之间共享的最少量代码。我们没有引入任何新的 Java API，也没有改变任何现有的 API。

Metal 管道可以与 OpenGL 管道共存。当图形应用程序启动时，会选择其中一个。目前，OpenGL 仍然是默认设置。仅当在启动时指定或 OpenGL 初始化失败时才使用 Metal，就像在没有 OpenGL 支持的未来版本的 macOS 中一样。

在集成此 JEP 时，Apple 尚未删除 OpenGL。在此之前，应用程序可以通过`-Dsun.java2d.metal=true`在`java`命令行上指定来选择加入 Metal 。我们将在未来的版本中将 Metal 渲染管线设为默认。

在集成到 JDK 之前，我们在[Project Lanai 中](https://openjdk.java.net/projects/lanai/)对这个 JEP 进行了工作。

### 4_JEP 391：macOS/AArch64 端口

> 概括

 将 JDK 移植到 macOS/AArch64。 



> 原因

Apple 宣布了一项将[其 Macintosh 计算机系列从 x64 过渡到 AArch64](https://en.wikipedia.org/wiki/Mac_transition_to_Apple_Silicon)的长期计划。因此，我们希望看到对 JDK 的 macOS/AArch64 端口的广泛需求。

尽管可以通过 macOS 的内置[Rosetta 2](https://en.wikipedia.org/wiki/Rosetta_(software)#Rosetta_2) 转换器在基于 AArch64 的系统上运行 JDK 的 macOS/x64 版本，但该翻译几乎肯定会带来显着的性能损失。



> 具体描述

Linux 的 AArch64 端口（[JEP 237](https://openjdk.java.net/jeps/237)）已经存在，Windows 的 AArch64 端口（[JEP 388](https://openjdk.java.net/jeps/388)）的工作正在进行中。我们希望通过使用条件编译（在 JDK 的端口中很常见）来重用来自这些端口的现有 AArch64 代码，以适应低级约定的差异，例如应用程序二进制接口 (ABI) 和保留的处理器寄存器集。

macOS/AArch64 禁止内存段同时可执行和可写，这一策略称为[*write-xor-execute* (W^X)](https://en.wikipedia.org/wiki/W^X)。HotSpot VM 会定期创建和修改可执行代码，因此此 JEP 将在 HotSpot 中为 macOS/AArch64 实现 W^X 支持。

### 5_JEP 398：弃用 Applet API 以进行删除

> 概述

 弃用 Applet API 以进行删除。它基本上无关紧要，因为所有 Web 浏览器供应商都已取消对 Java 浏览器插件的支持或宣布了这样做的计划。  Java 9 中的[JEP 289](https://openjdk.java.net/jeps/289)先前已弃用 Applet API，但并未将其删除。 

> 具体内容

弃用或移除标准 Java API 的这些类和接口：

- `java.applet.Applet`
- `java.applet.AppletStub`
- `java.applet.AppletContext`
- `java.applet.AudioClip`
- `javax.swing.JApplet`
- `java.beans.AppletInitializer`

弃用（删除）引用上述类和接口的任何 API 元素，包括以下中的方法和字段：

- `java.beans.Beans`
- `javax.swing.RepaintManager`
- `javax.naming.Context`

### 6_JEP 403：强封装 JDK 内部

> 概述

 强烈封装 JDK 的所有内部元素，除了 [关键的内部 API](https://openjdk.java.net/jeps/260#Description)，如`sun.misc.Unsafe`. 不再可能通过单个命令行选项来放松内部元素的强封装，就像在 JDK 9 到 JDK 16 中那样。 

 这个 JEP 是[JEP 396](https://openjdk.java.net/jeps/396)的继承者，它将 JDK 从默认的*宽松强封装*转换为默认 *强封装*，同时允许用户根据需要返回到轻松的姿势。本 JEP 的目标、非目标、动机、风险和假设部分与 JEP 396 的部分基本相同，但为了读者的方便在此处复制。 

> 目标

- 继续提高 JDK 的安全性和可维护性，这是[Project Jigsaw](https://openjdk.java.net/projects/jigsaw)的主要目标之一。
- 鼓励开发人员从使用内部元素迁移到使用标准 API，以便他们和他们的用户可以轻松升级到未来的 Java 版本。

> 原因

多年来，各种库、框架、工具和应用程序的开发人员以损害安全性和可维护性的方式使用 JDK 的内部元素。特别是：

- 包的一些非`public`类、方法和字段`java.*`定义了特权操作，例如[在特定类加载器中定义新类的能力](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/ClassLoader.html#defineClass(java.lang.String,byte[],int,int))，而其他则传送敏感数据，例如[加密密钥](https://youtu.be/Vxfd3ehdAZc?t=1248)。尽管在`java.*`包中，但这些元素是 JDK 的内部元素。外部代码通过反射使用这些内部元素会使平台的安全性处于危险之中。
- 包的所有类、方法和字段`sun.*`都是 JDK 的内部 API。大多数类，方法和领域`com.sun.*`， `jdk.*`以及`org.*`包也是内部API。这些 API 从来都不是标准的，从来没有得到支持，也[从来没有打算供外部使用](http://web.archive.org/web/19980215011039/http://java.sun.com/products/jdk/faq/faq-sun-packages.html)。外部代码使用这些内部元素是一种持续的维护负担。为了不破坏现有代码，保留这些 API 所花费的时间和精力可以更好地用于推动平台向前发展。

在 Java 9 中，我们通过利用模块来[限制对其内部元素的访问](https://openjdk.java.net/jeps/260)，提高了 JDK 的安全性和可维护性。模块提供*强封装*，这意味着

- 模块外的代码只能访问该 模块导出的包的`public`和`protected`元素，并且
- `protected` 此外，元素只能从定义它们的类的子类中访问。

强封装适用于编译时和运行时，包括编译代码尝试在运行时通过反射访问元素时。`public`导出包的非元素和未导出包的所有元素都被称为*强封装*。

在 JDK 9 及更高版本中，我们强烈封装了所有新的内部元素，从而限制了对它们的访问。然而，为了帮助迁移，我们故意选择不在运行时强封装 JDK 8 中已经存在的内部元素。因此类路径上的库和应用程序代码可以继续使用反射来访问非`public`元素的`java.*`包，以及所有元素`sun.*`和其他内部包，用于在JDK 8存在这种安排被称为包[*宽松强大的封装性*](https://openjdk.java.net/jeps/261#Relaxed-strong-encapsulation)，并且在JDK 9的默认行为。

我们早在 2017 年 9 月就发布了 JDK 9。 JDK 的大多数常用内部元素现在都有[标准替代品](https://wiki.openjdk.java.net/display/JDK8/Java+Dependency+Analysis+Tool#JavaDependencyAnalysisTool-ReplaceusesoftheJDK'sinternalAPIs)。开发人员必须在三年中，从标准的API，如JDK的内部元素迁移走 `java.lang.invoke.MethodHandles.Lookup::defineClass`，`java.util.Base64`和`java.lang.ref.Cleaner`。许多库、框架和工具维护者已经完成了迁移并发布了其组件的更新版本。现在对宽松强封装的需求比 2017 年弱，而且逐年进一步减弱。

在 2021 年 3 月发布的 JDK 16 中，我们迈出了下一步，以强封装 JDK 的所有内部元素。 [JEP 396](https://openjdk.java.net/jeps/396)将强封装作为默认行为，但关键的内部 API（如`sun.misc.Unsafe`）仍然可用。在 JDK 16 中，最终用户仍然可以选择宽松的强封装，以便访问 JDK 8 中存在的内部元素。

我们现在准备通过移除选择宽松强封装的能力，在这个旅程中再迈出一步。这意味着 JDK 的所有内部元素都将被强封装，除了关键的内部 API，如`sun.misc.Unsafe`.

> 具体描述

松弛强封装由启动器选项控制 `--illegal-access`。这个选项由[JEP 261](https://openjdk.java.net/jeps/261#Relaxed-strong-encapsulation)引入，被挑衅地命名以阻止其使用。在 JDK 16 及更早版本中，它的工作方式如下：

- `--illegal-access=permit`安排 JDK 8 中存在的每个包都对未命名模块中的代码[开放](https://docs.oracle.com/javase/specs/jls/se15/html/jls-7.html#jls-7.7.2)。因此，类路径上的代码可以继续使用反射来访问包的非公共元素`java.*`，以及`sun.*` JDK 8 中存在的包和其他内部包的所有元素。对任何此类元素的第一次反射访问操作会导致发出警告，但在那之后不会发出警告。

  此模式是 JDK 9 到 JDK 15 的默认模式。

- `--illegal-access=warn``permit`除了针对每个非法反射访问操作发出警告消息之外，其他都相同。

- `--illegal-access=debug``warn`除了为每个非法反射访问操作发出警告消息和堆栈跟踪之外，其他都相同。

- `--illegal-access=deny`禁用所有非法访问操作，但由其他命令行选项启用的操作除外，*例如*， `--add-opens`。

  此模式是 JDK 16 中的默认模式。

作为强封装 JDK 的所有内部元素的下一步，我们建议使该`--illegal-access`选项过时。此选项的任何使用，无论是使用`permit`、`warn`、`debug`或`deny`，都不会产生任何影响，只会发出警告消息。我们希望`--illegal-access`在未来的版本中完全删除该选项。

通过此更改，最终用户将无法再使用该 `--illegal-access`选项来启用对 JDK 内部元素的访问。（影响到包的列表，请[点击这里](https://cr.openjdk.java.net/~mr/jigsaw/jdk8-packages-strongly-encapsulated)。） **的`sun.misc`和`sun.reflect`软件包将仍然由出口 `jdk.unsupported`模块，并且仍然是开放的，这样的代码可以通过反射访问他们的非公开内容。**不会以这种方式打开其他 JDK 包。

仍然可以使用[`--add-opens`](https://openjdk.java.net/jeps/261#Breaking-encapsulation) 命令行选项或[`Add-Opens`](https://openjdk.java.net/jeps/261#Packaging:-Modular-JAR-files)JAR 文件清单属性来打开特定的包。

### 7_JEP 407：删除 RMI 激活

> 概括

 删除远程方法调用 (RMI) 激活机制，同时保留 RMI 的其余部分。 



> 原因

RMI 激活机制已过时且已废弃。它已被Java SE 15 中的[JEP 385](https://openjdk.java.net/jeps/385)弃用。没有收到针对该弃用的评论。请参阅[JEP 385](https://openjdk.java.net/jeps/385)了解完整的背景、原理、风险和替代方案。

Java EE 平台包含一项称为[*JavaBeans Activation Framework*](https://github.com/javaee/activation) (JAF) 的技术。作为[Eclipse EE4J](https://projects.eclipse.org/projects/ee4j)计划的一部分，它后来更名为[*Jakarta Activation*](https://eclipse-ee4j.github.io/jaf/)。JavaBeans Activation 和 Jakarta Activation 技术与 RMI Activation 完全无关，它们不受从 Java SE 中删除 RMI Activation 的影响。

> 具体描述

- `java.rmi.activation`从 Java SE API 规范中删除包
- 更新*RMI 规范*以删除提及 RMI 激活
- 去掉实现RMI激活机制的JDK库代码
- 删除 RMI 激活机制的 JDK 回归测试
- 删除 JDK 的`rmid`激活守护进程及其文档

### 8_JEP 410：删除实验性 AOT 和 JIT 编译器



> 概括

 删除实验性的基于 Java 的提前 (AOT) 和即时 (JIT) 编译器。该编译器自推出以来几乎没有什么用处，维护它所需的工作量很大。保留实验性的 Java 级 JVM 编译器接口 (JVMCI)，以便开发人员可以继续使用外部构建的编译器版本进行 JIT 编译。 



> 原因

提前编译（该`jaotc`工具）已通过[JEP 295](https://openjdk.java.net/jeps/295)作为实验性功能合并到 JDK 9 中。该`jaotc`工具使用 Graal 编译器，它本身是用 Java 编写的，用于 AOT 编译。

Graal 编译器通过[JEP 317](https://openjdk.java.net/jeps/317)在 JDK 10 中作为实验性 JIT 编译器提供。

自从引入这些实验性功能以来，我们几乎没有看到它们的使用，并且维护和增强它们所需的工作量很大。这些特性[没有包含](https://bugs.openjdk.java.net/browse/JDK-8255616)在 Oracle 发布的 JDK 16 版本中，并且没有人抱怨。

> 具体操作

删除三个 JDK 模块：

- `jdk.aot`—`jaotc`工具
- `jdk.internal.vm.compiler` — Graal 编译器
- `jdk.internal.vm.compiler.management` — 格拉尔的 `MBean`

保留这两个 Graal 相关的源文件，以便 JVMCI 模块 ( `jdk.internal.vm.ci`, [JEP 243](https://openjdk.java.net/jeps/243) ) 继续构建：

- `src/jdk.internal.vm.compiler/share/classes/module-info.java`
- `src/jdk.internal.vm.compiler.management/share/classes/module-info.java`

删除与AOT编译相关的HotSpot代码：

- `src/hotspot/share/aot` — 转储和加载 AOT 代码
- 由保护的附加代码 `#if INCLUDE_AOT`

最后，删除测试以及与 Graal 和 AOT 编译相关的 makefile 中的代码。

### 9_JEP 411：弃用安全管理器以进行删除

> 概述

 弃用安全管理器以在未来版本中移除。安全管理器可追溯到 Java 1.0。多年来，它一直不是保护客户端 Java 代码的主要手段，也很少用于保护服务器端代码。为了推动 Java 向前发展，我们打算弃用安全管理器，以便与旧 Applet API ( [JEP 398](https://openjdk.java.net/jeps/398) )一起删除。 



> 目标

- 为开发人员在 Java 的未来版本中移除安全管理器做好准备。
- 警告用户他们的 Java 应用程序是否依赖于安全管理器。
- 评估是否需要新的 API 或机制来解决使用安全管理器的特定狭窄用例，例如阻塞`System::exit`。

> 原因

Java 平台强调安全性。数据的*完整性*受到 Java 语言和 VM 内置内存安全的保护：变量在使用前被初始化，数组边界被检查，内存释放是完全自动的。同时，数据的*机密*性受到 Java 类库对现代加密算法和协议（如 SHA-3、EdDSA 和 TLS 1.3）的可信实现的保护。安全是一门动态的科学，因此我们不断更新 Java 平台以解决新的漏洞并反映新的行业态势，例如通过弃用弱加密协议。

一个长期存在的安全元素是安全管理器，它可以追溯到 Java 1.0。在 Web 浏览器下载 Java 小程序的时代，安全管理器通过在*沙箱中*运行小程序来保护用户机器的完整性及其数据的机密性，从而拒绝访问文件系统或网络等资源。Java 类库的小尺寸`java.*`——Java 1.0 中只有八个包——使得代码变得可行，例如，`java.io`在执行任何操作之前咨询安全管理器。安全管理器在*不受信任的代码*（来自远程机器的小程序）和*受信任的代码*之间*划清*了界限（本地机器上的类）：它会批准所有涉及可信代码资源访问的操作，但拒绝不可信代码的资源访问。

随着对 Java 兴趣的增长，我们引入了*签名小程序*以允许安全管理器信任远程代码，从而允许小程序访问与通过`java`命令行运行的本地代码相同的资源。同时，Java 类库也在迅速扩展——Java 1.1 引入了 JavaBeans、JDBC、反射、RMI 和序列化——这意味着受信任的代码可以访问重要的新资源，如数据库连接、RMI 服务器和反射对象。允许所有受信任的代码访问所有资源是不可取的，因此在 Java 1.2 中，我们重新设计了安全管理器以专注于应用*最小权限原则：*默认情况下，所有代码都将被视为不受信任，受阻止访问资源的沙盒式控制的约束，并且用户将通过授予他们访问特定资源的特定权限来信任特定的代码库。理论上，类路径上的应用程序 JAR 在使用 JDK 的方式方面可能比来自 Internet 的小程序更受限制。限制权限被视为限制代码体中可能存在的任何漏洞影响的一种方式——实际上是一种纵深防御机制。

因此，安全经理希望防范两种威胁：*恶意意图*（尤其是远程代码）和*意外漏洞*（尤其是本地代码）。

由于 Java 平台不再支持小程序，远程代码的恶意威胁已经消退。Applet API[在 2017 年在 Java 9 中](https://openjdk.java.net/jeps/289)被[弃用](https://openjdk.java.net/jeps/289)，然后[在 2021 年在 Java 17 中](https://openjdk.java.net/jeps/398)被[弃用，](https://openjdk.java.net/jeps/289)并打算在未来的版本中将其删除。[2018 年](https://www.oracle.com/technetwork/java/javase/javaclientroadmapupdatev2020may-6548840.pdf#[{"num"%3A34%2C"gen"%3A0}%2C{"name"%3A"XYZ"}%2C93%2C665%2C0])，运行小程序的闭源浏览器插件与[闭源 Java Web Start 技术](https://www.oracle.com/technetwork/java/javase/javaclientroadmapupdatev2020may-6548840.pdf#[{"num"%3A34%2C"gen"%3A0}%2C{"name"%3A"XYZ"}%2C93%2C504%2C0])一起[从 Oracle 的 JDK 11 中删除](https://www.oracle.com/technetwork/java/javase/javaclientroadmapupdatev2020may-6548840.pdf#[{"num"%3A34%2C"gen"%3A0}%2C{"name"%3A"XYZ"}%2C93%2C665%2C0])。因此，安全管理器防范的许多风险不再重要。此外，安全管理器无法防范许多现在很重要的风险。安全经理无法解决[行业领导者在 2020 年确定](https://cwe.mitre.org/top25/archive/2020/2020_cwe_top25.html)的[25 个最危险问题中的](https://cwe.mitre.org/top25/archive/2020/2020_cwe_top25.html)19 个，因此诸如 XML 外部实体引用 (XXE) 注入和不正确的输入验证等问题需要 Java 类库中的直接对策。（例如，JAXP 可以[防止 XXE 攻击和 XML 实体扩展](https://docs.oracle.com/en/java/javase/16/security/java-api-xml-processing-jaxp-security-guide.html#GUID-D97A1F1D-8DDF-4D19-A7E5-99099F27346E)，而[序列化过滤](https://docs.oracle.com/en/java/javase/16/core/serialization-filtering1.html#GUID-3ECB288D-E5BD-4412-892F-E9BB11D4C98A)可以防止恶意数据在造成任何损害之前被反序列化。）安全管理器也[无法防止基于推测执行漏洞的恶意行为](https://mail.openjdk.java.net/pipermail/vuln-announce/2019-July/000002.html)。

不幸的是，安全管理器对恶意意图缺乏效力，因为安全管理器必须被编入 Java 类库的结构中。因此，这是一个持续的维护负担。必须评估所有新功能和 API，以确保它们在启用安全管理器时正确运行。基于最小特权原则的访问控制可以在Java 1.0中的类库已经可行，但快速增长`java.*`和`javax.*`包导致了整个 JDK 中的数十个权限和数百个权限检查。这是保持安全的一个重要表面区域，特别是因为权限可以以令人惊讶的方式进行交互。某些权限，例如，允许应用程序或库代码执行一系列安全操作，其整体效果足够不安全，如果直接授予则需要更强大的权限。

使用安全管理器几乎不可能解决本地代码中意外漏洞的威胁。许多声称安全管理器被广泛用于保护本地代码的说法经不起推敲。它在生产中的使用远比许多人想象的要少。它没有使用的原因有很多：

- **脆弱的权限模型**— 希望从安全管理器中受益的应用程序开发人员必须仔细授予应用程序执行所有操作所需的所有权限。没有办法获得部分安全性，其中只有少数资源受到访问控制。例如，假设开发人员担心非法访问数据，因此希望授予仅从特定目录读取文件的权限。授予文件读取权限是不够的，因为应用程序几乎肯定会使用 Java 类库中除了读取文件（例如，写入文件）之外的其他操作，而这些其他操作将被安全管理器拒绝，因为代码将没有适当的许可。只有仔细记录他们的代码如何与 Java 类库中的安全敏感操作交互的开发人员才能授予必要的权限。这不是常见的开发人员工作流程。（安全管理员不允许[否定权限](https://bugs.openjdk.java.net/browse/JDK-4943650)，可以表示“授予除读取文件以外的所有操作的权限”。）
- **困难的编程模型**——安全经理通过检查导致操作的所有运行代码的权限来批准安全敏感操作。这使得编写与安全管理器一起运行的库变得困难，因为库开发人员记录其库代码所需的权限是不够的。除了已授予该代码的任何权限之外，使用该库的应用程序开发人员还需要为其应用程序代码授予相同的权限。这违反了最小特权原则，因为应用程序代码可能不需要库的权限来进行自己的操作。库开发人员可以通过谨慎使用`java.security.AccessController`API 要求安全管理器只考虑库的权限，但是这个和其他[安全编码指南](https://www.oracle.com/java/technologies/javase/seccodeguide.html#9)的复杂性远远超出了大多数开发人员的兴趣。应用程序开发人员的阻力最小的路径通常是授予`AllPermission`任何相关的 JAR 文件，但这又与最小权限原则背道而驰。
- **性能不佳**——安全管理器的核心是一个复杂的访问控制算法，它通常会带来不可接受的性能损失。因此，默认情况下，对于在命令行上运行的 JVM，安全管理器始终处于禁用状态。这进一步降低了开发人员对投资使库和应用程序与安全管理器一起运行的兴趣。缺乏帮助推断和验证权限的工具是另一个障碍。

在引入安全管理器的四分之一个世纪以来，采用率一直很低。只有少数应用程序附带限制其自身操作的策略文件（例如，[ElasticSearch](https://github.com/elastic/elasticsearch/blob/master/server/src/main/resources/org/elasticsearch/bootstrap/security.policy)）。类似地，只有少数框架附带策略文件（例如[Tomcat](https://github.com/apache/tomcat/blob/master/conf/catalina.policy)），使用这些框架构建应用程序的开发人员仍然面临着确定他们自己的代码和他们使用的库所需的权限这一几乎无法克服的挑战。一些框架（例如[NetBeans](https://github.com/apache/netbeans/blob/master/platform/core.execution/src/org/netbeans/core/execution/SecMan.java)）避开策略文件而是实现自定义安全管理器以防止插件调用`System::exit` 或者深入了解代码的行为，例如它是否打开文件和网络连接——我们认为通过其他方式更好地服务的用例。

总之，使用安全管理器开发现代 Java 应用程序没有太大的兴趣。根据权限做出访问控制决策既笨拙又缓慢，并且在整个行业中失宠；例如，.NET[不再支持它](https://docs.microsoft.com/en-us/dotnet/framework/misc/code-access-security)。通过在 Java 平台的较低级别提供完整性可以更好地实现安全性——例如，通过加强模块边界 ( [JEP 403](https://openjdk.java.net/jeps/403) ) 以防止访问 JDK 实现细节，并[强化实现本身](https://bugs.openjdk.java.net/browse/JDK-8210522)— 并通过容器和管理程序等进程外机制将整个 Java 运行时与敏感资源隔离。为了推动 Java 平台向前发展，我们将弃用从 JDK 中删除的旧安全管理器技术。我们计划在多个版本中弃用和削弱安全管理器的功能，同时为诸如阻塞等任务`System::exit`和其他被认为足够重要以进行替换的用例创建替代 API 。

> 具体操作

在 Java 17 中，我们将：

- 弃用（删除）大多数与安全管理器相关的类和方法。
- 如果在命令行上启用了安全管理器，则在启动时发出警告消息。
- 如果 Java 应用程序或库动态安装安全管理器，则在运行时发出警告消息。

在 Java 18 中，除非最终用户明确选择允许，否则我们将阻止 Java 应用程序或库动态安装安全管理器。从历史上看，Java 应用程序或库总是被允许动态安装安全管理器，但从[Java 12 开始](https://www.oracle.com/java/technologies/javase/12-relnote-issues.html#JDK-8191053)，最终用户已经能够通过在命令行 ( )上设置系统属性`java.security.manager`来阻止它——这会导致抛出. [在Java中18起](https://bugs.openjdk.java.net/browse/JDK-8270380)，默认值将是，如果不通过其他方式设置。因此，调用的应用程序和库可能会由于意外的. 为了像以前一样工作，最终用户必须设置`disallow``java -Djava.security.manager=disallow ...``System::setSecurityManager``UnsupportedOperationException``java.security.manager``disallow``java -D...``System::setSecurityManager``UnsupportedOperationException``System::setSecurityManager``java.security.manager`到`allow`命令行 ( `java -Djava.security.manager=allow ...`)。

在 Java 18 及更高版本中，我们将降级其他安全管理器 API，以便它们保持原样，但功能有限或没有功能。例如，我们可以`AccessController::doPrivileged`简单地修改来运行给定的动作，或者修改`System::getSecurityManager`总是返回`null`。这将允许支持安全管理器并针对以前的 Java 版本编译的库继续工作而无需更改甚至重新编译。一旦这样做的兼容性风险下降到可接受的水平，我们希望删除这些 API。

在 Java 18 及更高版本中，我们可能会更改 Java SE API 定义，以便之前执行权限检查的操作不再执行它们，或者在启用安全管理器时执行更少的检查。因此，`@throws SecurityException`将出现在 API 规范中较少的方法中。

### 10_JEP 412：外部函数和内存 API（孵化器）

> 概括

 介绍一个 API，Java 程序可以通过该 API 与 Java 运行时之外的代码和数据进行互操作。通过有效调用外部函数（即 JVM 之外的代码），以及安全地访问外部内存（即不由 JVM 管理的内存），API 使 Java 程序能够调用本地库和处理本地数据，而没有JNI。 



> 历史

 本 JEP 中提出的 API 是两个孵化 API 的演变：外部内存访问 API 和外部链接器 API。Foreign-Memory Access API 最初由[JEP 370](https://openjdk.java.net/jeps/370)提出，并于 2019 年末针对 Java 14 作为[孵化 API](https://openjdk.java.net/jeps/11)；它由Java 15 中的[JEP 383](https://openjdk.java.net/jeps/383)和Java 16 中的[JEP 393](https://openjdk.java.net/jeps/393)重新孵化。外部链接器 API 最初由[JEP 389](https://openjdk.java.net/jeps/389)提出，并于 2020 年末针对 Java 16，也作为[孵化 API](https://openjdk.java.net/jeps/11)。 

> 目标

- *易用性*— 用高级的纯 Java 开发模型替换 Java 本机接口 ( [JNI](https://docs.oracle.com/en/java/javase/16/docs/specs/jni/index.html) )。
- *性能* — 提供与现有 API（例如 JNI 和`sun.misc.Unsafe`.
- *通用性*— 提供对不同类型的外部内存（例如，本机内存、持久内存和托管堆内存）进行操作的方法，并随着时间的推移适应其他平台（例如，32 位 x86）和用其他语言编写的外部函数比 C（例如，C++、Fortran）。
- *安全*——默认禁用不安全的操作，只有在应用程序开发人员或最终用户明确选择后才允许它们。

> 原因

 Java 平台一直为希望超越 JVM 并与其他平台交互的库和应用程序开发人员提供丰富的基础。Java API 以方便可靠的方式公开非 Java 资源，无论是访问远程数据 (JDBC)、调用 Web 服务（HTTP 客户端）、服务远程客户端（NIO 通道）还是与本地进程通信（Unix 域套接字） . 不幸的是，Java 开发人员在访问一种重要的非 Java 资源时仍然面临重大障碍：与 JVM 位于同一台机器上但在 Java 运行时之外的代码和数据。

**外部存储** 

存储在 Java 运行时之外的内存中的数据称为*堆外*数据。（*堆*是 Java 对象所在的地方——*堆上*数据——以及垃圾收集器工作的地方。）访问堆外数据对于[Tensorflow](https://github.com/tensorflow/tensorflow)、[Ignite](https://ignite.apache.org/)、[Lucene](https://lucene.apache.org/)和[Netty](https://netty.io/)等流行 Java 库的性能至关重要，主要是因为它让他们避免了与垃圾收集相关的成本和不可预测性。它还允许通过将文件映射到内存中来序列化和反序列化数据结构，例如[`mmap`](https://en.wikipedia.org/wiki/Mmap)。然而，Java 平台目前还没有为访问堆外数据提供令人满意的解决方案。

- 该[`ByteBuffer`API](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/nio/ByteBuffer.html)允许创建的*直接*被分配离堆字节缓冲区，但他们的最大大小为2 GB的，他们得不到及时释放。这些和其他限制源于这样一个事实，即`ByteBuffer`API 不仅设计用于堆外内存访问，而且还用于生产者/消费者在字符集编码/解码和部分 I/O 操作等领域交换批量数据。在这种情况下，多年来提交的许多堆外增强请求（例如[4496703](https://bugs.openjdk.java.net/browse/JDK-4496703)、[6558368](https://bugs.openjdk.java.net/browse/JDK-6558368)、[4837564](https://bugs.openjdk.java.net/browse/JDK-4837564)和[5029431](https://bugs.openjdk.java.net/browse/JDK-5029431)）一直无法满足。
- 该[`sun.misc.Unsafe`API](https://hg.openjdk.java.net/jdk/jdk/file/tip/src/jdk.unsupported/share/classes/sun/misc/Unsafe.java)对堆数据自曝存储器存取操作也为离堆数据的工作。使用`Unsafe`是高效的，因为它的内存访问操作被定义为 HotSpot JVM 内部函数并由 JIT 编译器优化。但是，使用`Unsafe`是危险的，因为它允许访问任何内存位置。这意味着 Java 程序可以通过访问一个已经释放的位置来使 JVM 崩溃；由于这个原因和其他原因，`Unsafe`一直[强烈反对使用](https://web.archive.org/web/19980215011039/http://java.sun.com/products/jdk/faq/faq-sun-packages.html)。
- 使用 JNI 调用本地库然后访问堆外数据是可能的，但性能开销很少使它适用：从 Java 到本地比访问内存慢几个数量级，因为 JNI 方法调用没有从许多常见的JIT 优化，例如内联。

综上所述，在访问堆外数据时，Java 开发人员面临一个两难选择：他们应该选择安全但效率低下的路径 ( `ByteBuffer`) 还是应该放弃安全以支持性能 ( `Unsafe`)？他们需要的是用于访问堆外数据（即外部内存）的受支持 API，从头开始设计以确保安全并考虑到 JIT 优化。

**外部函数**

JNI 从 Java 1.1 开始就支持调用本机代码（即外部函数），但是由于多种原因它不够用。

- JNI 涉及几个乏味的工件：Java API（`native`方法）、从 Java API 派生的 C 头文件，以及调用感兴趣的本机库的 C 实现。Java 开发人员必须跨多个工具链工作以保持依赖于平台的工件同步，这在本机库快速发展时尤其繁重。
- JNI 只能与用语言（通常是 C 和 C++）编写的库进行互操作，这些语言使用操作系统和 CPU 的调用约定，JVM 是为其构建的。一个`native`方法不能被用来调用写在使用不同的约定语言的功能。
- JNI 不协调 Java 类型系统与 C 类型系统。Java 中的聚合数据是用对象表示的，而 C 中的聚合数据是用结构体表示的，因此任何传递给`native`方法的Java 对象都必须由本机代码费力地解包。例如，考虑`Person`Java 中的记录类：将`Person`对象传递给`native`方法将需要本机代码使用 JNI 的 C API从对象中提取字段（例如，`firstName`和`lastName`）。因此，Java 开发人员有时会将他们的数据扁平化为单个对象（例如，字节数组或直接字节缓冲区），但更多时候，由于通过 JNI 传递 Java 对象很慢，他们使用`Unsafe`API 来分配堆外内存和将其地址作为`native`方法传递给方法`long` — 这使得 Java 代码非常不安全！

多年来，出现了许多框架来填补 JNI 留下的空白，包括[JNA](https://github.com/java-native-access/jna)、[JNR](https://github.com/jnr/jnr-ffi)和[JavaCPP](https://github.com/bytedeco/javacpp)。尽管这些框架通常比 JNI 有了显着的改进，但情况仍然不太理想，尤其是与提供一流的本地互操作性的语言相比时。例如，Python 的[ctypes](https://docs.python.org/3/library/ctypes.html)包可以在本地库中动态包装函数，无需任何胶水代码。其他语言，例如[Rust](https://rust-lang.github.io/rust-bindgen/)，提供了从 C/C++ 头文件机械地派生本机包装器的工具。

最终，Java 开发人员应该有一个受支持的 API，让他们可以直接使用任何被认为对特定任务有用的本机库，而不需要 JNI 的乏味粘连和笨拙。一个极好的构建*方法*是*handles*，它是在 Java 7 中引入的，用于支持 JVM 上的快速动态语言。通过方法句柄公开本机代码将从根本上简化编写、构建和分发依赖本机库的 Java 库的任务。此外，能够对外部函数（即本机代码）和外部内存（即堆外数据）进行建模的 API 将为第三方本机互操作框架提供坚实的基础。

> 描述

外部函数和内存 API (FFM API) 定义了类和接口，以便库和应用程序中的客户端代码可以

- 分配外部内存
  ( `MemorySegment`, `MemoryAddress`, 和`SegmentAllocator`),
- 操作和访问结构化的外部内存
  （`MemoryLayout`，`MemoryHandles`， 和`MemoryAccess`），
- 管理外部资源的生命周期 ( `ResourceScope`)，以及
- 调用外部函数（`SymbolLookup`和`CLinker`）。

FFM API 位于模块的`jdk.incubator.foreign`包中`jdk.incubator.foreign`。

> 例子

作为使用 FFM API 的简短示例，这里是 Java 代码，它获取 C 库函数的方法句柄`radixsort`，然后使用它对 Java 数组中的四个字符串进行排序（省略了一些细节）：

```
// 1. Find foreign function on the C library path
MethodHandle radixSort = CLinker.getInstance().downcallHandle(
                             CLinker.systemLookup().lookup("radixsort"), ...);
// 2. Allocate on-heap memory to store four strings
String[] javaStrings   = { "mouse", "cat", "dog", "car" };
// 3. Allocate off-heap memory to store four pointers
MemorySegment offHeap  = MemorySegment.allocateNative(
                             MemoryLayout.ofSequence(javaStrings.length,
                                                     CLinker.C_POINTER), ...);
// 4. Copy the strings from on-heap to off-heap
for (int i = 0; i < javaStrings.length; i++) {
    // Allocate a string off-heap, then store a pointer to it
    MemorySegment cString = CLinker.toCString(javaStrings[i], newImplicitScope());
    MemoryAccess.setAddressAtIndex(offHeap, i, cString.address());
}
// 5. Sort the off-heap data by calling the foreign function
radixSort.invoke(offHeap.address(), javaStrings.length, MemoryAddress.NULL, '\0');
// 6. Copy the (reordered) strings from off-heap to on-heap
for (int i = 0; i < javaStrings.length; i++) {
    MemoryAddress cStringPtr = MemoryAccess.getAddressAtIndex(offHeap, i);
    javaStrings[i] = CLinker.toJavaStringRestricted(cStringPtr);
}
assert Arrays.equals(javaStrings, new String[] {"car", "cat", "dog", "mouse"});  // true
```

这段代码比任何使用 JNI 的解决方案都清晰得多，因为隐藏在`native`方法调用后面的隐式转换和内存取消引用现在直接用 Java 表示。也可以使用现代 Java 习语；例如，流可以允许多个线程在堆上和堆外内存之间并行复制数据。