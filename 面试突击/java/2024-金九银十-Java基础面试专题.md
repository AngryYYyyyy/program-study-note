# Java基础专题

> 讲师：波哥

Java基础这块涉及到的技术模块

- 面向对象
- 异常
- 集合
- 线程
- IO
- 网络通信
- 反射
- 泛型
- 注解
- 设计模式
- JDK的新特性

# 一、谈谈JDK、JRE、和JVM的区别

Java 虚拟机（Java Virtual Machine, JVM）是运行 Java 字节码的虚拟机。JVM 有针对不同系统的特定实现（Windows，Linux，macOS），目的是使用相同的字节码，它们都会给出相同的结果。字节码和不同系统的 JVM 实现是 Java 语言“一次编译，随处可以运行”的关键所在。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/cc9eb67e45a44a4bbe2a32b92df9a53b.png)

JDK和JRE

JDK（Java Development Kit），它是功能齐全的 Java SDK，是提供给开发者使用，能够创建和编译 Java 程序的开发套件。它包含了 JRE，同时还包含了编译 java 源码的编译器 javac 以及一些其他工具比如 javadoc（文档注释工具）、jdb（调试器）、jconsole（基于 JMX 的可视化监控⼯具）、javap（反编译工具）等等。

JRE（Java Runtime Environment） 是 Java 运行时环境。它是运行已编译 Java 程序所需的所有内容的集合，主要包括 Java 虚拟机（JVM）、Java 基础类库（Class Library）。

也就是说，JRE 是 Java 运行时环境，仅包含 Java 应用程序的运行时环境和必要的类库。而 JDK 则包含了 JRE，同时还包括了 javac、javadoc、jdb、jconsole、javap 等工具，可以用于 Java 应用程序的开发和调试。如果需要进行 Java 编程工作，比如编写和编译 Java 程序、使用 Java API 文档等，就需要安装 JDK。而对于某些需要使用 Java 特性的应用程序，如 JSP 转换为 Java Servlet、使用反射等，也需要 JDK 来编译和运行 Java 代码。因此，即使不打算进行 Java 应用程序的开发工作，也有可能需要安装 JDK。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/bd499c7952ef430db9301de4e5317102.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/88a19d4b61184dd8ba72be42270d5d5e.png)

需要注意的是JDK9后就不需要区分JDK和JRE的关系了。取而代之的是模块系统。

https://cloud.fynote.com/share/d/JkGGpfsjA

# 二、==和equals的区别

== 对于基本类型和引用类型的作用效果是不同的：

* 对于基本数据类型来说，== 比较的是值。
* 对于引用数据类型来说，== 比较的是对象的内存地址。

> 因为 Java 只有值传递，所以，对于 == 来说，不管是比较基本数据类型，还是引用数据类型的变量，其本质比较的都是值，只是引用类型变量存的值是对象的地址。

equals() 不能用于判断基本数据类型的变量，只能用来判断两个对象是否相等。equals()方法存在于Object类中，而Object类是所有类的直接或间接父类，因此所有的类都有equals()方法。

字符串的判断 == 需要使用 equals方法来判断 字符串的值是否相等

Object 类 equals() 方法:

```java
public boolean equals(Object obj) {
        return (this == obj);
    }
```

equals() 方法存在两种使用情况：

- 类没有重写 equals()方法：通过equals()比较该类的两个对象时，等价于通过“==”比较这两个对象，使用的默认是 Object类equals()方法。
- 类重写了 equals()方法：一般我们都重写 equals()方法来比较两个对象中的属性是否相等；若它们的属性相等，则返回 true(即，认为这两个对象相等)。

比如：

```java
String a = new String("ab"); // a 为一个引用
String b = new String("ab"); // b为另一个引用,对象的内容一样
String aa = "ab"; // 放在常量池中
String bb = "ab"; // 从常量池中查找
System.out.println(aa == bb);// true
System.out.println(a == b);// false
System.out.println(a.equals(b));// true
System.out.println(42 == 42.0);// true
```

String 中的 equals 方法是被重写过的，因为 Object 的 equals 方法是比较的对象的内存地址，而 String 的 equals 方法比较的是对象的值。

当创建 String 类型的对象时，虚拟机会在常量池中查找有没有已经存在的值和要创建的值相同的对象，如果有就把它赋给当前引用。如果没有就在常量池中重新创建一个 String 对象。

String类equals()方法：

```java
public boolean equals(Object anObject) {
    if (this == anObject) {
        return true;
    }
    if (anObject instanceof String) {
        String anotherString = (String)anObject;
        int n = value.length;
        if (n == anotherString.value.length) {
            char v1[] = value;
            char v2[] = anotherString.value;
            int i = 0;
            while (n-- != 0) {
                if (v1[i] != v2[i])
                    return false;
                i++;
            }
            return true;
        }
    }
    return false;
}


```

# 三、hashCode的介绍

hashCode() 的作用是获取哈希码（int 整数），也称为散列码。这个哈希码的作用是确定该对象在哈希表中的索引位置。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/4aff2fbfe22a42b48d5c9fa4bcca8d96.png)

hashCode() 方法hashCode() 定义在 JDK 的 Object 类中，这就意味着 Java 中的任何类都包含有
hashCode() 函数。另外需要注意的是：Object 的 hashCode() 方法是本地方法，也就是用 C 语言或 C++ 实现的。

散列表存储的是键值对(key-value)，它的特点是：**能根据“键”快速的检索出对应的“值”。这其中就利用到了散列码！（可以快速找到所需要的对象）**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/df67fd8abbaf4c4b8486700e4908f5cf.png)

冲突（碰撞)︰在散列表中插入一个数据元素时，需要根据关键字的值确定其存储地址，若该地址已经存储了其他元素，则称这种情况为“冲突(碰撞)”

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/666f54cf352c4bb1bd666172c6525e47.png)

其实， hashCode() 和 equals()都是用于比较两个对象是否 `相等`。

为什么JDK有了equals还要提供hashCode方法呢？

这个的主要原因还是hashCode的判断效率要比equals中更高。比如HashSet中的输入插入。

那为什么不只提供hashCode方法呢？

这是因为两个对象的hashCode 值相等并不代表两个对象就相等。

那为什么两个对象有相同的 hashCode 值，它们也不一定是相等的？

因为 hashCode() 所使用的哈希算法也许刚好会让多个对象传回相同的哈希值。越糟糕的哈希算法越容易碰撞，但这也与数据值域分布的特性有关（所谓哈希碰撞也就是指的是不同的对象得到相同的 hashCode )。

这块的内容总结下来就是：

* 如果两个对象的hashCode 值相等，那这两个对象不一定相等（哈希碰撞）。
* 如果两个对象的hashCode 值相等并且equals()方法也返回 true，我们才认为这两个对象相等。
* 如果两个对象的hashCode 值不相等，我们就可以直接认为这两个对象不相等。

扩展的问题：为什么重写 equals() 时必须重写 hashCode() 方法？

因为两个相等的对象的 hashCode 值必须是相等。也就是说如果 equals 方法判断两个对象是相等的，那这两个对象的 hashCode 值也要相等。

如果重写 equals() 时没有重写 hashCode() 方法的话就可能会导致 equals 方法判断是相等的两个对象，hashCode 值却不相等。

# 四、谈谈你对反射的理解

Java反射（Reflection）是[Java语言](https://so.csdn.net/so/search?q=Java%E8%AF%AD%E8%A8%80&spm=1001.2101.3001.7020)的一个特性，它允许程序在运行时对自身进行检查，并且能够操作类、接口、字段和方法等。反射提供了强大的功能，但也带来了一定的技术难点。

基本原理：

- 类的加载：Java反射始于类的加载。当使用Class.forName()或其他类加载器加载类时，JVM会读取类的字节码文件（.class文件），并将其转化为Class对象，这个对象包含了类的元数据信息，如类名、包名、父类、实现的接口、字段、方法等。
- 获取类的信息：通过Class对象，我们可以获取类的各种信息。例如，使用getMethods()方法获取类的所有公共方法，使用getDeclaredFields()方法获取类的所有字段（包括私有字段）。
- 动态调用：反射不仅允许我们获取类的信息，还允许我们动态地创建对象、调用方法、修改字段值等。通过newInstance()方法可以创建类的实例，通过getMethod()获取方法并使用invoke()方法调用它。

技术难点：

- 性能：反射操作相比直接操作代码更慢，因为反射涉及到动态解析和类型检查等。
- 安全性：反射允许访问类的私有成员，这可能导致安全漏洞。如果反射被恶意代码使用，可能会破坏系统的安全性。
- 复杂性：反射操作相对复杂，需要深入理解Java的类型系统和类加载机制。

安全措施：

- 访问控制：Java提供了访问控制修饰符（如private、protected和public）来控制对类的成员的访问。虽然反射可以突破这些限制，但我们应该避免在不需要时这样做。在设计API时，应该只暴露必要的公共接口，并隐藏敏感的内部实现。
- 代码签名和验证：JVM在加载类时会对类的字节码进行验证，以确保其符合Java语言的规范。此外，还可以使用代码签名来验证类的来源是否可信。这可以防止恶意代码被加载到JVM中。
- 最小权限原则：在编写使用反射的代码时，应遵循最小权限原则。即只请求执行所需任务所需的最小权限。例如，如果只需要读取某个字段的值，就不要请求修改该字段的权限。
- 安全管理器：Java提供了一个安全管理器（SecurityManager）类，它允许应用程序定义自己的安全策略。通过实现自定义的安全管理器，可以限制反射的使用，例如禁止加载来自不受信任的源的类。
- 代码审计和测试：对使用反射的代码进行严格的审计和测试是确保安全性的重要步骤。这包括检查是否有不安全的反射调用、验证输入数据的有效性以及确保代码符合安全最佳实践等。

# 五、Java集合的fail-fast机制

是java集合的一种错误检测机制，当多个线程对集合进行结构上的改变的操作 时，有可能会产生 fail-fast 机制。

例如：假设存在两个线程（线程1、线程2），线程1通过Iterator在遍历集合A中 的元素，在某个时候线程2修改了集合A的结构（是结构上面的修改，而不是简 单的修改集合元素的内容），那么这个时候程序就会抛出ConcurrentModificationException 异常，从而产生fail-fast机制。

原因：迭代器在遍历时直接访问集合中的内容，并且在遍历过程中使用一个 modCount 变量。集合在被遍历期间如果内容发生变化，就会改变modCount 的值。每当迭代器使用hashNext()/next()遍历下一个元素之前，都会检测 modCount变量是否为expectedmodCount值，是的话就返回遍历；否则抛出 异常，终止遍历。

解决办法：

- 在遍历过程中，所有涉及到改变modCount值得地方全部加上 synchronized。
- 使用CopyOnWriteArrayList来替换ArrayList

# 六、如果你有个集合不想被修改怎么办？

可以使用 Collections. unmodifiableCollection(Collection c)
方法来创建一个只读集合，这样改变集合的任何操作都会抛出 Java. lang. UnsupportedOperationException
异常。 示例代码如下：

```java
 List<String> list = new ArrayList<>(); 
 list. add("x"); 
 Collection<String> clist = Collections. unmodifiableCollection(list); 
 clist. add("y"); // 运行时此行报错 
 System. out. println(list. size()); 

```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/873b15dcfae8415996339fd8c5a34906.png)

通过查看相关的源码可以看到。如果想要修改相关的信息就会抛出对应的异常信息。

# 七、谈谈ArrayList的优缺点

ArrayList的优点如下：

- ArrayList 底层以数组实现，是一种随机访问模式。
- ArrayList 实现了 RandomAccess 接口，因此查找的时候非常快。
- ArrayList 在顺序添加一个元素的时候非常方便。

ArrayList 的缺点如下：

- 删除元素的时候，需要做一次元素复制操作。如果要复制的元素很多，那么就会比较耗费性能。
- 插入元素的时候，也需要做一次元素复制操作，缺点同上。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/523d2dbe904f45de9630109105aee066.png)

上面提到的随机访问。也就是 `RandomAccess`有什么作用呢？

RandomAccess接口是一个标记接口，用以标记实现的List集合具备快速随机访问的能力。

所有的List实现都支持随机访问的，只是基于基本结构的不同，实现的速度不同罢了，这里的快速随机访问，那么就不是所有List集合都支持了。

* ArrayList基于数组实现，天然带下标，可以实现常量级的随机访问，复杂度为O(1)
* LinkedList基于链表实现，随机访问需要依靠遍历实现，复杂度为O(n)

当一个List拥有快速访问功能时，其遍历方法采用for循环最快速。而没有快速访问功能的List，遍历的时候采用Iterator迭代器最快速。

当我们不明确获取到的是Arraylist，还是LinkedList的时候，我们可以通过RandomAccess来判断其是否支持快速随机访问，若支持则采用for循环遍历，否则采用迭代器遍历，如下方式：

```java
public class RandomAccessTest {
    private List<String> list = null;
    public RandomAccessTest(List<String> list){
        this.list = list;
    }
    public void loop(){
        if(list instanceof RandomAccess) {
            // for循环
            System.out.println("采用for循环遍历");
            for (int i = 0;i< list.size();i++) {
                System.out.println(list.get(i));
            }
        } else {
            // 迭代器
            System.out.println("采用迭代器遍历");
            Iterator it = list.iterator();
            while(it.hasNext()){
                System.out.println(it.next());
            }
        }
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1110");
        List<String> list1 = new LinkedList<>();
        list1.add("aaa");
        list1.add("bbb");
        list1.add("ccc");
        new RandomAccessTest(list).loop();
        new RandomAccessTest(list1).loop();
    }
}
```

# 八、异常处理影响性能吗

&emsp;&emsp;异常处理的性能成本非常高，每个 Java 程序员在开发时都应牢记这句话。创建一个异常非常慢，抛出一个异常又会消耗1~5ms，当一个异常在应用的多个层级之间传递时，会拖累整个应用的性能。
&emsp;&emsp;仅在异常情况下使用异常；在可恢复的异常情况下使用异常；尽管使用异常有利于 Java 开发，但是在应用中最好不要捕获太多的调用栈，因为在很多情况下都不需要打印调用栈就知道哪里出错了。因此，异常消息应该提供恰到好处的信息。

# 九、介绍下try-with-resource语法

&emsp;&emsp;try-with-resources 是 JDK 7 中一个新的异常处理机制，它能够很容易地关闭在 try-catch 语句块中使用的资源。所谓的资源（resource）是指在程序完成后，必须关闭的对象。try-with-resources 语句确保了每个资源在语句结束时关闭。所有实现了 java.lang.AutoCloseable 接口（其中，它包括实现了java.io.Closeable 的所有对象），可以使用作为资源。

**关闭单个资源**：

```java
public class Demo03 {
    public static void main(String[] args) {
        try(Resource res = new Resource()) {
            res.doSome();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Resource implements AutoCloseable {
    void doSome() {
        System.out.println("do something");
    }
    @Override
    public void close() throws Exception {
        System.out.println("resource is closed");
    }
}

```

查看编译后的代码

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1675489386042/f39af94001064f6bb857f1c5fca6c32e.png)

**关闭多个资源**

```java
public class Demo04 {
    public static void main(String[] args) {
        try(ResourceSome some = new ResourceSome();
            ResourceOther other = new ResourceOther()) {
            some.doSome();
            other.doOther();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

class ResourceSome implements AutoCloseable {
    void doSome() {
        System.out.println("do something");
    }
    @Override
    public void close() throws Exception {
        System.out.println("some resource is closed");
    }
}

class ResourceOther implements AutoCloseable {
    void doOther() {
        System.out.println("do other things");
    }
    @Override
    public void close() throws Exception {
        System.out.println("other resource is closed");
    }
}
```

编译后的代码

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1675489386042/2d3e6eb530034c90a25452afdbc3c094.png)

处理规则

1. 凡是实现了AutoCloseable接口的类，在try()里声明该类实例的时候，在try结束后，close方法都会被调用
2. try结束后自动调用的close方法，这个动作会早于finally里调用的方法。
3. 不管是否出现异常（int i=1/0会抛出异常），try()里的实例都会被调用close方法
4. 越晚声明的对象，会越早被close掉。

**JDK9中的改进**

&emsp;&emsp;在 JDK 9 已得到改进。如果你已经有一个资源是 final 或等效于 final 变量,您可以在 try-with-resources 语句中直接使用该变量，而无需在 try-with-resources 语句中声明一个新变量。

```java
// A final resource
final Resource resource1 = new Resource("resource1");
// An effectively final resource
Resource resource2 = new Resource("resource2");
try (resource1;
     resource2) {
    // 直接使用 resource1 and resource 2.
}
```

# 十、HashMap面试汇总

HashMap针对JDK的版本：

- jdk7：数组+链表
- jdk8：数组+链表+红黑树


数据结构+算法=程序

基础的数据结构的内容：数组 链表 二叉树 AVL 2-3-4树 红黑树

集合的基础：List Set  Map

## 1. jdk8为什么引入了红黑树

因为链表长度增加后检索的效率急剧降低，复杂度是 O(n) [https://blog.csdn.net/heihei2017/article/details/102775283]

## 2.解决hash冲突。为什么不直接用红黑树?

因为红黑树需要进行左旋，右旋，变色这些操作来保持平衡，而单链表不需要。当元素小于 8 个的时候，此时做查询操作，链表结构已经能保证查询性能。当元素大于 8 个的时候， 红黑树搜索时间复杂度是O(logn)，而链表是 O(n)，此时需要红黑树来加快查询速度，但是新增节点的效率变慢了。

因此，如果一开始就用红黑树结构，元素太少，新增效率又比较慢，无疑这是浪费性能的。

## 3.**为什么链表改为红黑树的阈值是 8？**

首先和hashcode碰撞次数的泊松分布有关，主要是为了寻找一种时间和空间的平衡。在负载因子0.75（HashMap默认）的情况下，单个hash槽内元素个数为8的概率小于百万分之一，将7作为一个分水岭，等于7时不做转换，大于等于8才转红黑树，小于等于6才转链表。链表中元素个数为8时的概率已经非常小，再多的就更少了，所以原作者在选择链表元素个数时选择了8，是根据概率统计而选择的。

## 4. 默认加载因子为什么是0.75

这个是从时间和空间的角度综合得出的。

- 如果是1.0 当数组的值全部填充了才会发生扩容，此时Hash冲突是避免不了的。链表的操作或者红黑树的操作会牺牲时间来保证空间的利用率
- 如果是0.5 当数组中一半的数据利用了之后就会开始扩容。这时填充的数据少。hash冲突也会减少，底层的链表和红黑树的高度也会降低。查询效率增加。但是这时还有太多的空间没有利用。空间资源浪费了。
- 所以0.75是综合考虑得出的

## 5.为什么要右移16位？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1708924487010/773ca8e62d6d41b68ab5620446fe057b.png)

其实是为了减少碰撞，进一步降低hash冲突的几率。int类型的数值是4个字节的，右移16位异或可以同时保留高16位于低16位的特征

当数组的长度很短时，只有低位数的hashcode值能参与运算。而让高16位参与运算可以更好的均匀散列，减少碰撞，进一步降低hash冲突的几率。并且使得高16位和低16位的信息都被保留了。

在HashMap的put方法里面，是通过Key的hash值与数组的长度取模计算得到数组的位置。

而在绝大部分的情况下，n的值一般都会小于2^16次方，也就是65536。

所以也就意味着i的值 ， 始终是使用hash值的低16位与(n-1)进行取模运算，这个是由与运算符&的特性决定的。

这样就会造成key的散列度不高，导致大量的key集中存储在固定的几个数组位置，很显然会影响到数据查找性能。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/e414bf10fdd7409894211fd3f813c338.png)

## 6.为什么Hash值要与length-1相与

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1723528724056/a8e4c9b9dc244b278c1ea5848f6d3697.png)

把 hash 值对数组长度取模运算，模运算的消耗很大，没有位运算快。
当 length 总是 2 的n次方时，h& (length-1) 运算等价于对length取模，也就是 h%length，但是 & 比 % 具有更高的效率。

## 7.介绍下put方法的流程

- 首先根据 key 的值计算 hash 值，找到该元素在数组中存储的下标；
- 如果数组是空的，则调用 resize 进行初始化；
- 如果没有哈希冲突直接放在对应的数组下标里；
- 如果冲突了，且 key 已经存在，就覆盖掉 value；
- 如果冲突后，发现该节点是红黑树，就将这个节点挂在树上；
- 如果冲突后是链表，判断该链表是否大于 8 ，如果大于 8 并且数组容量小于 64，就进行扩容；如果链表节点大于 8 并且数组的容量大于 64，则将这个结构转换为红黑树；否则，链表插入键值对，若 key 存在，就覆盖掉 value。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1708924487010/698cb1a0cb694799ac76e69c1bc1552c.png)
