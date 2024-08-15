# 一、Lambda表达式

## 1、引入

创建一个线程，指定线程的执行任务

```java
public class Test01 {
    public static void main(String[] args) {
        new Thread() {//匿名内部类
            @Override
            public void run() {
                System.out.println("Thread is running");
            }
        }.start();
    }
}
```

这里我们省去了一个`Runnable`的实现类的定义，采用了匿名内部类的方法，重写了`run()`方法，可是我们发现对于`Runnable`接口的实现，只需要关注实现方法的方法体。

由于匿名内部类的冗余，语法新增了Lambda表达式，提供了一种简洁的方式来==实现接口的抽象方法==，而==无需创建接口的具体实现类==。

下面是使用Lambda表达式创建线程

```java
public class Test01 {
    public static void main(String[] args) {
        new Thread(()->{System.out.println("Thread is running");}).start();
    }
}
```

## 2、语法规则

```java
(参数列表)->{方法体;}
```

注意：

- 参数列表的类型可以省略，只有一个参数时，可以省略`()`。
- 方法体只有一行时，可以省略`return`、`{}`、`;`。

例如：

```java
public class Test01 {
    public static void main(String[] args) {
        new Thread(()->System.out.println("Thread is running")).start();//省略了{}、;
    }
}
```

有参数无返回值的Lambda表达式

当传递一个Lambda表达式给`test`方法时，Java虚拟机将这个Lambda表达式视为`Service`接口的一个实现。这是因为Lambda表达式符合`Service`接口`show`方法的签名和返回类型。

```java
public interface Service {
    public abstract void show(String str);
}
```

```java
public class Test02 {
    public static void main(String[] args) {
        test((str)->{System.out.println(str);});
        test(str->System.out.println(str));//省略了()、{}、;
    }
    static void test(Service str){
        str.show("hello");
    }
}
```

有参数有返回值的Lambda表达式

```java
public interface Service01 {
    int show(String str);
}
```

```java
public class Test03 {
    public static void main(String[] args) {
        test((String str)->{return str.length();});
        test(str->str.length());//省略了()、return、{}、;
    }
    static void test(Service01 str){
        System.out.println(str.show("hello"));
    }
}
```

## 3、@FunctionalInterface注解

> Lambda表达式只适用于只声明一个抽象方法的接口

`@FunctionalInterface` 是 Java 中的一个注解，用于标记一个接口为函数式接口。函数式接口是指仅包含一个抽象方法的接口，可以有多个默认方法或静态方法。它们通常用于 lambda 表达式和方法引用的目标类型。

```java
@FunctionalInterface
public interface Service01 {
    int show(String str);
    //void method();只能声明一个抽象方法
}
```

## 4、Lambda表达式的原理

```java
public class Test01 {
    public static void main(String[] args) {
        new Thread() {//匿名内部类
            @Override
            public void run() {
                System.out.println("Thread is running");
            }
        }.start();
    }
}
```

在运行`Test01`时，会产生两个字节码文件，`Test01.class`存储的是该类的字节码信息。那么`Test01$1.class`存储的是什么呢？

使用反编译查看。

```java
package com.lxy.test01;

import java.io.PrintStream;

final class Test01$1 extends Thread
{
  public void run()
  {
    System.out.println("Thread is running");
  }
}
```

通过观察可以发现，`Test01$1.class`存储了内部类`Test01$1`的字节码信息，这就是程序中所匿名的内部类。

```java
public class Test01 {
    public static void main(String[] args) {
        new Thread(()->System.out.println("Thread is running")).start();
    }
}
```

在命令台使用输入下面命令，将由 Lambda 表达式生成的代理类文件输出到指定的文件系统路径。以便反汇编观察 Lambda 表达式。

```shell
java -Djdk.internal.lambda.dumpProxyClasses=<输出路径> 要运行的包名.类名
```

```java
package com.lxy.test01;

import java.io.PrintStream;

final class Test01$1 extends Thread
{
  public void run()
  {
    Test01.lambda$main$0();
  }
}
```

发现重写`run()`方法内调用了静态的`lambda$main$0()`方法。

```java
private static void lambda$main$0();
        System.out.println("Thread is running");
    }
```

可以观察到，Lambda表达式实际上创建了一个内部类用来实现接口，但是与匿名内部类不同点在于Lambda表达式定义了一个静态的`lambda$main$0()`方法，在重写接口方法中调用了`lambda$main$0()`方法。

## 5、Lambda表达书与匿名内部类对比

所需类型不一样

- ==匿名内部类的类型可以是 类，抽象类，接口==
- ==Lambda表达式需要的类型必须是接口==

抽象方法的数量不一样

- 匿名内部类所需的接口中的抽象方法的数量是随意的
- ==Lambda表达式所需的接口中只能有一个抽象方法==

实现原理不一样

- 匿名内部类是在编译后形成一个class
- Lambda表达式是在程序运行的时候动态生成class

# 二、接口中新增方法

JDK8之前，接口的内容。

```java
interface 接口名{
    静态常量;
    抽象方法;
}
```

为了接口的拓展，JDK新增的接口方法。

```java
interface 接口名{
    静态常量;
    抽象方法;
    默认方法；
    静态方法；
}
```

## 1、默认方法

### （1）语法格式

```java
interface 接口名{
    修饰符 default 返回值 方法名(){
        方法体;
    }
}
```

### （2）使用

```java
public interface Service02 {
    void method01();
    public default  void method02(){
        System.out.println("method02");
    }
}
```

```java
public class Test04 {
    public static void main(String[] args) {
        test(new Service02() {
            @Override
            public void method01() {
                System.out.println("override method01");
            }
            public void method02(){//可以直接调用，如果重写，则要去掉default修饰符
                System.out.println("override method02");
            }
        });
    }
    public static void test(Service02 ser){
        ser.method01();
        ser.method02();
    }
}
```

## 2、静态方法

### （1）语法格式

```java
interface 接口名{
    修饰符 static 返回值 方法名(){
        方法体;
    }
}
```

### （2）使用

```java
public interface Service02 {
    void method01();
    public default  void method02(){
        System.out.println("method02");
    }
    public static   void method03(){//不能被重写
        System.out.println("method03");
    }
}
```

```java
public class Test04 {
    public static void main(String[] args) {
        test(new Service02() {
            @Override
            public void method01() {
                System.out.println("override method01");
            }
            public void method02(){
                System.out.println("override method02");
            }
        });
    }
    public static void test(Service02 ser){
        ser.method01();
        ser.method02();
        Service02.method03();//只能使用接口.的方法调用
    }
}
```

# 三、函数式接口

Lambda表达式的前提是需要有接口，而不关心接口名，抽象方法名。只关心抽象方法的参数列表和返回值类型。因此为了让我们使用Lambda表达式更加的方法，在JDK中提供了大量常用的函数式接口。

## 1、`Supplier`

### （1）接口形式

> 无参、有返回值

```java
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

### （2）使用

```java
public class Test01 {
    public static void main(String[] args) {
        test(()->{
            //实现0~9依次相加
            int sum=0;
            for (int i = 0; i < 10; i++) {
                sum+=i;
            }
            return sum;//返回
        });
    }
    public static void test(Supplier<Integer> sup){
        Integer sum = sup.get();
        System.out.println(sum);
    }
}
```

## 2、`Consumer`

### （1）接口形式

> 抽象方法：有参数，无返回值

```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
    
    default Consumer<T> andThen(Consumer<? super T> after) {//将两个 Consumer 操作组合成一个
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```

### （2）使用

```java
public class Test02 {
    public static void main(String[] args) {
        test((str)->{
            System.out.println(str);
        });
        test01((str)->{
                    System.out.println("1:"+str);
                },(str)->{
                    System.out.println("2:"+str);
                }
        );
    }
    public static void test(Consumer<String> con){
        con.accept("hello");
    }
    public static void test01(Consumer<String> con1,Consumer<String> con2){
        con1.andThen(con2).accept("hello");//输出：1:hello        2:hello
    }
}
```

## 3、`Function`

### （1）接口形式

> 有参数、有返回值

```java
@FunctionalInterface
public interface Function<T, R> {

    R apply(T t);
	//compose 方法允许您创建一个新的函数，该函数首先将输入应用于 before 函数，然后将结果应用于当前的 Function 实例。
    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }
	//andThen 方法使您能够创建一个新的函数，该函数首先应用当前的 Function 实例，然后将结果应用于 after 函数。
    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }
	////identity 方法提供了一个恒等函数，它返回其输入参数。
    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
```

### （2）使用

```java
public class Test03 {
    public static void main(String[] args) {
        test((str)->{
            return str.length();
        });
        test01((str)->{
            return str.length();
        },(num)->{
            return num*10;
        });
        System.out.println(Function.identity().apply("helloworld"));
    }
    public static void test(Function<String,Integer> fun){
        Integer len = fun.apply("hello");
        System.out.println(len);
    }
    public static void test01(Function<String,Integer> fun1,Function<Integer,Integer> fun2){
        System.out.println(fun1.andThen(fun2).apply("hello"));//输出：50----"hello"->5*10->50
        System.out.println(fun2.compose(fun1).apply("hello"));//输出：50----"hello"->5*10->50
    }
}
```

## 4、`Predicate`

### （1）接口形式

> 有参数，返回值是布尔类型

```java
@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);

    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }

    default Predicate<T> negate() {
        return (t) -> !test(t);
    }

    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }
    
    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }
}
```



### （2）使用

```java
public class Test04 {
    public static void main(String[] args) {
        test((str)->{
            return "hello".equals(str);
        });
        test01((num)->{
            return num>2;
        },(num)->{
            return num<5;
        });
        System.out.println(Predicate.isEqual("hello").test("hello"));//true
        System.out.println(Predicate.isEqual("hello").test("world"));//false
    }
    public static void test(Predicate<String> pre){
        System.out.println(pre.test("hello"));//true
        System.out.println(pre.test("helloworld"));//flase
    }
    public static void test01(Predicate<Integer> pre1,Predicate<Integer> pre2){
        System.out.println();
        System.out.println(pre1.and(pre2).test(3));//true
        System.out.println(pre1.and(pre2).test(3));//true
        System.out.println(pre1.and(pre2).test(2));//false
        System.out.println(pre1.or(pre2).test(3));//true
        System.out.println(pre1.or(pre2).test(1));//true
        System.out.println(pre1.test(3));//true
        System.out.println(pre1.negate().test(3));//false
        System.out.println(pre1.and(pre2).negate().test(3));//false
        System.out.println();
    }
}
```

# 四、方法引用

在 Java 中，方法引用是 Java 8 引入的一项功能，它提供了一种简洁的方式来引用已存在的方法。==方法引用可以被看作是一个 Lambda 表达式的简化形式，用于直接调用某个方法==。方法引用的主要用途是在函数式接口的实现中，提供一种更简洁的语法。

注意：

- 方法引用必须与函数式接口的参数、返回值保持一致

## 1、静态方法引用

```java
public class Test01 {
    public static void main(String[] args) {
        //Lambda表达式
        Supplier<Long> sup=()->System.currentTimeMillis();
        System.out.println(sup.get());
        //方法引用
        Supplier<Long> sup1=System::currentTimeMillis;
        System.out.println(sup.get());
    }
}
```

## 2、实例方法引用

### （1）对象的实例方法引用

```java
public class Test03 {
    public static void main(String[] args) {
        String str="hello";//需要先有一个对象实例
        //Lambda表达式
        Supplier<Integer> sup=()->str.length();
        System.out.println(sup.get());
        //引用方法
        Supplier<Integer> sup1= str::length;//这个实例对象充当了一个输入参数
        System.out.println(sup.get());
    }
}
```

### （2）类的实例方法引用

```java
public class Test02 {
    public static void main(String[] args) {
        //Lambda表达式
        Function<String,Integer> fun=(str)->str.length();
        System.out.println(fun.apply("hello"));
        //引用方法
        Function<String,Integer> fun1=String::length;
        System.out.println(fun1.apply("hello"));
    }
}
```

## 3、构造方法的引用

```java
public class Test04 {
    static class Person{
        private String name;

        public Person() {
        }

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
    public static void main(String[] args) {
        //Lambda表达式
        Function<String,Person> fun=(str)->new Person(str);
        System.out.println(fun.apply("lihua"));
        //引用方法
        Function<String,Person> fun1=Person::new;
        System.out.println(fun1.apply("lihua"));
        //Lambda表达式,创建数组
        Function<Integer,String[]> fun2=(num)->new String[num];
        String[] arr1 = fun2.apply(5);
        for(String e:arr1){
            System.out.println(e);
        }
        //引用方法
        Function<Integer,String[]> fun3=String[]::new;
        String[] arr2 = fun3.apply(5);
        for(String e:arr2){
            System.out.println(e);
        }
    }
}
```

# 五、`Stream` API

> `Stream` 本身不存储元素。它只是在源（如集合或数组）的基础上进行操作。

Java 中的 `Stream` 是 Java 8 引入的一种新的抽象概念，它是对集合对象进行各种非常便利的高级操作的一种方式。`Stream` API 是函数式编程在 Java 中的一个重要体现，它提供了一种高效且直观的处理大量数据的方法。

## 1、传统集合处理对比

下面是对`ArarryList<String>`的传统筛选处理

```java
public class Test01 {
    public static void main(String[] args) {
        List<String> list= Arrays.asList("lihua","zhangsan","wangwu","lili");
        //遍历打印
        for(String e:list){
            System.out.print(e+"\t");
        }
        System.out.println();
        //获取字符长度大于5的
        List<String> list1=new ArrayList<>();
        for(String e:list){
            if(e.length()>5){
                list1.add(e);
            }
        }
        //获取字符含z的
        List<String> list2=new ArrayList<>();
        for(String e:list){
            if(e.contains("z")){
                list2.add(e);
            }
        }
        //打印提取的list
        for(String e:list2){
            System.out.print(e+"\t");
        }
    }
}
```

使用`stream`API更加高效、简便。

```java
public class Test02 {
    public static void main(String[] args) {
        List<String> list= Arrays.asList("lihua","zhangsan","wangwu","lili");
        list.stream()
                .filter(str->str.length()>5)
                .filter(str->str.contains("z"))
                .forEach(System.out::println);
    }
}
```

## 2、获取方式

#### （1）`Collection`接口的`stream()`默认方法

```java
class test01{
    public static void main(String[] args) {
        List<String> list=  new ArrayList<>();
        Stream<String> stream = list.stream();
        Set<String> set=new HashSet<>();
        Stream<String> stream1 = set.stream();
    }
}
```

对于`Map`接口，我们可以分别对`key`、`value`分别获取流。

```java
class test02{
    public static void main(String[] args) {
        Map<String,Integer> map=new HashMap<>();
        Stream<String> stream = map.keySet().stream();
        Stream<Integer> stream1 = map.values().stream();
    }
}
```

==但是对于数组，数组对象无法添加默认的办法==，因此还需要`of()`静态方法。

#### （2）`Stream`类的`of()`静态方法

```java
class test03{
    public static void main(String[] args) {
        Integer[] arr={1,2,3,4,5,6};
        Stream<Integer> stream = Stream.of(arr);
        stream.forEach(System.out::println);
    }
}
```

注意：基本数据类型的数组是不行的

## 3、常用方法

### （1）终端操作

这些操作返回一个非 `Stream` 的结果，如集合、元素、数值等，或者不返回任何内容（例如 `forEach`）。终端操作会触发实际的数据处理。

#### 1）`forEach()`

对流中的每个元素执行一个操作。这是一个终端操作，常用于打印流中的数据或对其进行其他形式的处理。

```java
void forEach(Consumer<? super T> action);
```

```java
@Test
public void test01(){
    Stream.of("a","b","c").forEach(System.out::println);
}
```

#### 2）`count()`

返回流中的元素个数。这是一个终端操作。

```java
long count();
```

```java
@Test
public void test02(){
    long count = Stream.of("a", "b", "c").count();
    System.out.println(count);
}
```

#### 3）`match`

提供对流中元素的匹配操作，根据提供的谓词（Predicate）进行测试。

```java
boolean anyMatch(Predicate<? super T> predicate);//任意一个满足即可

boolean allMatch(Predicate<? super T> predicate);//全部满足

boolean noneMatch(Predicate<? super T> predicate);//全都不满足
```

```java
@Test
public void test09(){
    boolean b = Stream.of(2, 3, 4, 5).allMatch(num -> num > 1);
    boolean b1 = Stream.of(2, 3, 4, 5).noneMatch(num -> num > 6);
    boolean b2 = Stream.of(2, 3, 4, 5).anyMatch(num -> num > 3);
    System.out.println(b);
    System.out.println(b1);
    System.out.println(b2);
}
```

#### 4）`find`

返回流中的某个元素。

```java
Optional<T> findFirst();
Optional<T> findAny();//findAny() 在并行流中不保证返回第一个元素，而是返回任意一个元素。
```

```java
@Test
public void test10(){
    Optional<Integer> first = Stream.of(2, 3, 4, 5).findFirst();
    System.out.println(first.get());

    Optional<Integer> first2 = Stream.of(2, 3, 4, 5).findAny();
    System.out.println(first2.get());
}
```

#### 5）`max()  min()`

分别返回流中的最大值和最小值。

```java
Optional<T> min(Comparator<? super T> comparator);
Optional<T> max(Comparator<? super T> comparator);
```

```java
@Test
public void test11(){
    Optional<Integer> max = Stream.of(2, 3, 4, 5).max((o1,o2)->o1-o2);
    Optional<Integer> min = Stream.of(2, 3, 4, 5).min((o1,o2)->o1-o2);
    System.out.println("max:"+max.get()+"\tmin:"+min.get());
}
```

#### 6）`reduce()`

对流中的元素==进行重复的合并操作==，最终得到一个值。它是一个终端操作，通常用于数值计算，如求和、求乘积等。

```java
   /* 
     * @param identity   初始值，或称作“累积器的默认值”。在流为空时，它也是结果的默认值。
     * @param accumulator 一个用于将两个元素结合起来的函数，参数为同类型 T，返回值也是同类型 T。
     * @return 返回一个 T 类型的值，它是通过将流中的元素逐一应用 accumulator 函数，结合 identity 值，计算出来的结果。
     */
T reduce(T identity, BinaryOperator<T> accumulator);

Optional<T> reduce(BinaryOperator<T> accumulator);
 /**
     * @param identity    初始值，或称作“累积器的默认值”。在流为空时，它也是结果的默认值。
     * @param accumulator 一个用于将元素和累积值结合起来的函数，参数为 U（累积值的类型）和 T（流中元素的类型），返回值为 U。
     * @param combiner    一个用于在并行处理时合并部分结果的函数，参数和返回值都为 U。
     * @return 返回一个 U 类型的值，它是通过将流中的元素逐一应用 accumulator 函数，结合 identity 值，计算出来的结果。如果流是并行的，combiner 函数会被用来合并部分结果。
     */
<U> U reduce(U identity,
             BiFunction<U, ? super T, U> accumulator,
             BinaryOperator<U> combiner);
```

```java
    @Test
    public void test12(){
        Integer sum = Stream.of(1, 2, 3, 4, 5, 6).reduce(10, (o1, o2) -> {
            System.out.println(Thread.currentThread() + ":" + "\to1:" + o1 + "\to2:" + o2);
            return o1 + o2;
        });
        System.out.println(sum);

        Optional<Integer> sum01 = Stream.of(1, 2, 3, 4, 5, 6).reduce((o1, o2) -> {
            System.out.println(Thread.currentThread() + ":" + "\to1:" + o1 + "\to2:" + o2);
            return o1 + o2;
        });
        System.out.println(sum01.get());

        Integer sum02 = Stream.of(1, 2, 3, 4, 5, 6).parallel().reduce(0, (o1, o2) -> {
            System.out.println(Thread.currentThread() + ":" + "\to1:" + o1 + "\to2:" + o2);
            return o1 + o2;
        }, Integer::sum);
        System.out.println(sum02);
    }
}
```

在实际应用中，经常与`map()`方法搭配使用。

```java
    @Test
    public void test13(){
        //年龄总和
        Integer sum = Stream.of(
                        new Person("张三", 18)
                        , new Person("李四", 22)
                        , new Person("张三", 13)
                        , new Person("王五", 15)
                        , new Person("张三", 19)
                ).map(Person::getAge)
                .reduce(0,Integer::sum);
        System.out.println(sum);

        //年龄最大值
        Integer max = Stream.of(
                        new Person("张三", 18)
                        , new Person("李四", 22)
                        , new Person("张三", 13)
                        , new Person("王五", 15)
                        , new Person("张三", 19)
                ).map(Person::getAge)
                .reduce(0,Math::max);
        System.out.println(max);
        //字符a出现的次数
        Integer count = Stream.of("a", "b", "c", "d", "a", "c", "a")
                .map(ch -> ("a".equals(ch)) ? 1 : 0)
                .reduce(0,Integer::sum);
        System.out.println(count);
    }
}
```

### （2）中间操作

#### 1）`fitter(`)

对流中的元素进行过滤，只保留满足指定条件的元素。

```java
Stream<T> filter(Predicate<? super T> predicate);
```

```java
@Test
public void test03(){
    Stream.of(1,2,3,4,5,6,7).filter(num->num>3).forEach(System.out::println);
}
```

#### 2）`limit()`

限制流中的元素数量

```java
Stream<T> limit(long maxSize);
```

```java
@Test
public void test04(){
    Stream.of(1,2,3,4,5,6,7).limit(4).forEach(System.out::println);
}
```

#### 3）`skip()`

跳过流中的前 N 个元素。

```java
Stream<T> skip(long n);
```

```java
@Test
public void test05(){
    Stream.of(1,2,3,4,5,6,7).skip(3).forEach(System.out::println);
}
```

#### 4）`map()`

对流中的每个元素应用一个函数，并将结果映射为一个新的流。

```java
<R> Stream<R> map(Function<? super T, ? extends R> mapper);
```

```java
@Test
public void test06(){
    Stream.of(1,2,3,4,5,6,7).map(num->num.toString()).forEach(System.out::println);
    Stream.of("lihua","zhangsan","lili","wangwu").map(String::length).forEach(System.out::println);
}
```

#### 5）`sorted()`

对流中的元素进行排序。

```java
Stream<T> sorted(Comparator<? super T> comparator);
```

```java
@Test
public void test07(){
    Stream.of(4,7,3,1,5,2,9,8).sorted((o1, o2) ->o1-o2 ).forEach(System.out::println);
    Stream.of("4","7","3","1","5","2","9","8").map(Integer::parseInt).sorted((o1, o2) ->o1-o2 ).forEach(System.out::println);//先使用map将String转为Integer
}
```

#### 6）`distinct()`

返回一个去除重复元素后的流。

```java
Stream<T> distinct();
```

```java
@Test
public void test08(){
    Stream.of(1,1,1,2,2,3,4,5).distinct().forEach(System.out::println);
}
```

#### 7）`mapToInt()`

与 `map()` 类似，但是它生成一个特化的 `IntStream`。

当处理的是原始数据类型（如 `int`）时，使用特化的流（如 `IntStream`）比使用泛型流（如 `Stream<Integer>`）有更好的性能。这是因为特化流避免了装箱和拆箱操作，这些操作在泛型流中是必需的。

```java
IntStream mapToInt(ToIntFunction<? super T> mapper);
//ToIntFunction
@FunctionalInterface
public interface ToIntFunction<T> {
    int applyAsInt(T value);
}
```

```java
@Test
public void test14() {
    Integer arr[] = {1,2,3,5,6,8};
    Stream.of(arr)
            .mapToInt(Integer::intValue)
            .filter(num->num>3)
            .forEach(System.out::println);
}
```

#### 8）`concat()`

不是 `Stream` 实例的方法，而是 `Stream` 类的一个静态方法，用于连接两个流。

```java
public static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b) {
    Objects.requireNonNull(a);
    Objects.requireNonNull(b);

    @SuppressWarnings("unchecked")
    Spliterator<T> split = new Streams.ConcatSpliterator.OfRef<>(
            (Spliterator<T>) a.spliterator(), (Spliterator<T>) b.spliterator());
    Stream<T> stream = StreamSupport.stream(split, a.isParallel() || b.isParallel());
    return stream.onClose(Streams.composedClose(a, b));
}
```

```java
@Test
public void test15() {
    Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
    Stream<Integer> stream01 = Stream.of(6,7,8,9,10);
    Stream.concat(stream,stream01).forEach(System.out::println);
}
```

## 4、收集流

`collect()` 方法是一种非常灵活且强大的终端操作，用于将流转换成不同类型的结果，如集合、字符串或任何其他复合类型。`collect()` 方法通常与 `Collector` 接口的实现一起使用，用于指定如何收集流中的元素。

`Collector` 接口定义了收集操作（即累积元素、可选的转换和返回结果）的各个方面。Java 提供了 `Collectors` 工具类，它包含了许多常用的 `Collector` 实现，如转换到列表、集合、或者用分隔符连接字符串等

### （1）**转换为列表**：`Collectors.toList()`

- 将流中的元素收集到列表中。

```java
@Test
public void test01() {
    List<String> list = Stream.of("aa", "bb", "cc", "dd").collect(Collectors.toList());
    System.out.println(list);
}
```

### （2）**转换为集合**：`Collectors.toSet()`

- 将流中的元素收集到集合中，删除重复项。

```java
@Test
public void test02() {
    Set<String> set = Stream.of("aa", "bb", "cc", "dd","aa").collect(Collectors.toSet());
    System.out.println(set);
}
```

### （3）**转换为映射**：`Collectors.toMap()`

- 将流中的元素收集到映射中，需要提供键和值的映射函数。

```java
@Test
public void test03() {
    Map<String, Integer> map = Stream.of("aa", "bb", "cc", "dd").collect(Collectors.toMap(Function.identity(), String::length));
    System.out.println(map);
}
```

### （3）转化为数组：`toArray()`

- 对于普通的对象流，你可以使用 `toArray(T[]::new)` 来获得特定类型的数组。
- 不指定类型会返回`Object[]`数组

```java
@Test
public void test04() {
    Object[] array = Stream.of("aa", "bb", "cc", "dd").toArray();
    String[] array1 = Stream.of("aa", "bb", "cc", "dd").toArray(String[]::new);
}
```

### （4）**字符串连接**：`Collectors.joining()`

- 将流中的字符串元素连接成一个字符串。
- 参数指定连接格式

```java
@Test
public void test05() {
    String str = Stream.of("aa", "bb", "cc", "dd").collect(Collectors.joining());
    System.out.println(str);
    String str01 = Stream.of("aa", "bb", "cc", "dd").collect(Collectors.joining("-","@@@","$$$"));
    System.out.println(str01);//@@@aa-bb-cc-dd$$$
}
```



### （5）**分组**：`Collectors.groupingBy()`

- 根据某个属性或规则将流中的元素分组。
- 可以多级分组

```java
@Test
public void test06() {
    Map<String, List<Integer>> map = Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
            .collect(Collectors.groupingBy(num -> num > 4 ? "大于4" : "不大于4"));
    System.out.println(map);

    Map<String, List<Integer>> map1 = Stream.of(
                    new Person("张三", 18)
                    , new Person("李四", 22)
                    , new Person("张三", 13)
                    , new Person("王五", 15)
                    , new Person("张三", 19)
            ).map(Person::getAge)
            .collect(Collectors.groupingBy(num -> num >= 18 ? "成年" : "未成年"));
    System.out.println(map1);
    //多级分组，先按名字，再按年纪
    Map<String, Map<String, List<Person>>> map2 = Stream.of(
            new Person("张三", 18)
            , new Person("李四", 22)
            , new Person("张三", 13)
            , new Person("王五", 15)
            , new Person("张三", 19)
    ).collect(Collectors.groupingBy(Person::getName, Collectors.groupingBy(p -> p.getAge() >= 18 ? "成年" : "未成年")));
    System.out.println(map2);//{李四={成年=[Person{name='李四', age=22}]}, 
    //张三={
    	//未成年=[Person{name='张三', age=13}], 
    	//成年=[Person{name='张三', age=18}, Person{name='张三', age=19}]}, 王五={未成年=[Person{name='王五', age=15}]}}

}
```

### （6）**分区**：`Collectors.partitioningBy()`

- 根据布尔表达式将流中的元素分为两组（`true`、`false`）。

```java
@Test
public void test07() {
    Map<Boolean, List<Person>> map = Stream.of(
            new Person("张三", 18)
            , new Person("李四", 22)
            , new Person("张三", 13)
            , new Person("王五", 15)
            , new Person("张三", 19)
    ).collect(Collectors.partitioningBy(p -> p.getAge() >= 18));
    System.out.println(map);
}
//{
//	false=[Person{name='张三', age=13}, Person{name='王五', age=15}], 
//	true=[Person{name='张三', age=18}, Person{name='李四', age=22}, Person{name='张三', age=19}]
//}
```

### （7）**自定义收集器**：

- 可以通过实现 `Collector` 接口来创建自定义的收集器。

```java
@Test
public void test08() {
    ArrayList<Person> arrayList = Stream.of(
            new Person("张三", 18)
            , new Person("李四", 22)
            , new Person("张三", 13)
            , new Person("王五", 15)
            , new Person("张三", 19)
    ).collect(Collectors.toCollection(ArrayList::new));
    System.out.println(arrayList);

    HashSet<Person> hashSet = Stream.of(
            new Person("张三", 18)
            , new Person("李四", 22)
            , new Person("张三", 13)
            , new Person("王五", 15)
            , new Person("张三", 19)
    ).collect(Collectors.toCollection(HashSet::new));
    System.out.println(hashSet);
}
```

### （8）聚合计算

- 聚合计算是指对数据集进行操作以得到一个单一的汇总结果，如求和、求平均、求最大值或最小值等。

```java
@Test
public void test09() {
    //元素个数
    System.out.println("元素个数："+Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(Collectors.counting()));
    //最大值、最小值
    System.out.println("最大值："+Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(Collectors.maxBy(Math::max)));
    System.out.println("最大值："+Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(Collectors.minBy(Math::min)));
    //求和
    System.out.println("和："+Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(Collectors.summarizingInt(num -> num)));
    //输出： 和：IntSummaryStatistics{count=9, sum=45, min=1, average=5.000000, max=9}
    //求平均值
    System.out.println("平均值："+Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(Collectors.averagingInt(num -> num)));
}
```

## 5、并行流

Java 8 引入的并行流（Parallel Streams）是一种特殊的流，它能够在多核处理器上并行地处理数据。并行流利用了 Java 的 Fork/Join 框架来将任务分解成更小的任务，然后将这些小任务分配给不同的线程进行处理，最后将结果合并起来。这种方法可以显著提高处理大量数据时的性能，特别是在进行复杂或耗时的计算任务时。

### （1）获取并行流

#### 1）`Collection`接口的`parallelStream()`默认方法

```java
@Test
    public void test010() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer sum = list.parallelStream().reduce(0,(o1, o2) -> {
            System.out.println(Thread.currentThread() + ":" + "\to1:" + o1 + "\to2" + o2);
            return o1 + o2;
        },Integer::sum);
        System.out.println(sum);
    }
}
```

#### 2）`Stream`类的`parallel()`实现方法

```java
@Test
    public void test010() {
        Integer sum01 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).parallel().reduce(0, (o1, o2) -> {
            System.out.println(Thread.currentThread() + ":" + "\to1:" + o1 + "\to2" + o2);
            return o1 + o2;
        }, Integer::sum);
        System.out.println(sum01);
    }
}
```

### （2）与串行流对比

显著提高处理大量数据时的性能，实验如下

```java
public class StreamTest03 {
    private long times=500000000;
    private long start;
    @Before
    public void before(){
        start=System.currentTimeMillis();
    }
    @After
    public void after(){
        long end=System.currentTimeMillis();
        System.out.println(end-start);
    }
    @Test
    public void test01(){
        long sum=0;
        for (int i = 0; i <= times; i++) {
            sum+=i;
        }
        System.out.println(sum);
    }
    @Test
    public void test02(){
        long sum = LongStream.rangeClosed(0, times).sum();
        System.out.println(sum);

    }
    @Test
    public void test03(){
        long sum = LongStream.rangeClosed(0, times).parallel().sum();
        System.out.println(sum);
    }
}
```

```java
125000000250000000
251
125000000250000000
220
125000000250000000
31//并行流
```



### （3）线程安全

```java
@Test
public void test011(){
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
        list.add(i);
    }
    System.out.println(list.size());

    List<Integer> list1 = new ArrayList<>();
    // 使用并行流来向集合中添加数据
    list.parallelStream()
            .forEach(list1::add);
    System.out.println(list1.size());
}
```

程序会异常，或者集合大小不同，这是由于多线程抢占共同资源引发的线程安全问题。

解决方案：

- 同步锁
- 使用线程安全的容器或将容器转换为线程安全
-  `collect` 方法内部会进行适当的同步和并发控制

```java
@Test
public void test012() {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
        list.add(i);
    }
    System.out.println(list.size());

    List<Integer> list1 = new ArrayList<>();
    //同步锁
    Object obj = new Object();
    list.parallelStream()
            .forEach(i -> {
                        synchronized (obj) {
                            list1.add(i);
                        }
                    }
            );
    System.out.println(list1.size());


    //将线程安全容器转换为线程安全
    List<Integer> list2 = new ArrayList<>();
    List<Integer> synchronizededList2 = Collections.synchronizedList(list2);
    list.parallelStream()
            .forEach(synchronizededList2::add);
    System.out.println(synchronizededList2.size());
    //使用collect方法
    List<Integer> list3 = list.parallelStream()
            .collect(Collectors.toList());
    System.out.println(list3.size());
}
```



# 六、Optional类

在 Java 8 中引入的 `Optional` 类是一个容器类，用于表示一个值可能存在也可能不存在。它可以避免直接返回 `null` 值，从而减少 `NullPointerException` 的风险。`Optional` 类的使用提高了程序的可读性和健壮性，尤其是在处理可能为空的对象时。

## 1、**创建 Optional 对象**

- `Optional.empty()`：创建一个空的 `Optional` 实例。
- `Optional.of(value)`：创建一个包含非空值的 `Optional` 实例。如果 `value` 为 `null`，则会抛出 `NullPointerException`。
- `Optional.ofNullable(value)`：创建一个包含值的 `Optional` 实例，如果 `value` 为 `null`，则返回一个空的 `Optional` 实例。

```java
@Test
public void test01(){
    Optional<Object> op = Optional.empty();
    Optional<Integer> op1 = Optional.of(10);//不支持null
    Optional<List<String>> op2 = Optional.ofNullable(Arrays.asList("lili", "zhangsan", "wangwu"));
    Optional<Object> op3 = Optional.ofNullable(null);//支持null
    System.out.println(op);
    System.out.println(op1);
    System.out.println(op2);
    System.out.println(op3);//输出：Optional.empty
}
```

## 2、常用方法

### （1）**访问 Optional 对象的值**

- `isPresent()`：检查是否有值存在。
- `get()`：如果值存在，返回值；否则抛出 `NoSuchElementException`。
- `orElse(defaultValue)`：如果值存在，返回该值；否则返回默认值。
- `orElseGet(supplier)`：与 `orElse` 类似，但提供了一个供应商接口来动态生成默认值。
- `orElseThrow(exceptionSupplier)`：如果值存在，返回该值；否则抛出由提供的异常生成器生成的异常。

```java
@Test
public void test02(){
    Optional<Integer> op = Optional.of(10);
    if(op.isPresent()){
        System.out.println(op.get());
    }
    System.out.println(op.orElse(20));
    System.out.println(op.orElseGet(() -> 20));

    Optional<Integer> op1 = Optional.empty();
    try {
        if(op.isPresent()){//值不存在，抛出异常
            System.out.println(op1.get());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println(op1.orElse(20));
    System.out.println(op1.orElseGet(() -> 20));
}
```

### （2）**Optional 的消费**

- `ifPresent(consumer)`：如果值存在，执行提供的消费者操作。

```java
@Test
public void test03(){
    Optional<String> op = Optional.empty();
    op.ifPresent(System.out::println);

    Optional<String> op1 = Optional.ofNullable("hello");
    op1.ifPresent(System.out::println);
    op1.ifPresent(str-> System.out.println(str.length()));
}
```

# 七、注解

## 1、重复注解

在 Java 8 之前，同一个注解在同一个位置只能声明一次。Java 8 引入了重复注解的概念，允许在同一个位置多次使用相同的注解。

### （1）定义

- 创建一个注解，并使用 `@Repeatable` 注解标记它。`@Repeatable` 的值是一个容器注解，用于存放重复注解的实例。
- 定义一个容器注解，它应该包含一个返回重复注解数组的方法。

```java
@Repeatable(MyAnnotations.class)
@Retention(RetentionPolicy.RUNTIME)//运行时仍可以使用
public @interface MyAnnotation {
    String value();
}
//容器注解
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotations{
    MyAnnotation[] value();
}
```

### （2）使用

```java
@MyAnnotation("test01")
@MyAnnotation("test02")
@MyAnnotation("test03")
public class Test {
    @MyAnnotation("method01")
    @MyAnnotation("method02")
    @MyAnnotation("method03")
    public void method(){
    }
    //反射获取注解信息
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        MyAnnotation[] annotationsByType = Test.class.getAnnotationsByType(MyAnnotation.class);
        for(MyAnnotation e:annotationsByType){
            System.out.println(e.value());
        }
        MyAnnotation[] annotationsByType01 = Test.class.getMethod("method").getAnnotationsByType(MyAnnotation.class);
        for(MyAnnotation e:annotationsByType01){
            System.out.println(e.value());
        }
    }
}
```

## 2、类型注解

类型注解扩展了注解的应用范围，允许将注解不仅仅应用于声明，还可以应用于任何使用类型的地方，例如在类型转换、泛型和实现接口中。

JDK8为`@Target`元注解新增了两种类型： `TYPE_PARAMETER `， `TYPE_USE `。 

- `TYPE_PARAMETER `：表示该注解能写在类型参数的声明语句中。 类型参数声明如：` <T> `
- `TYPE_USE `：表示注解可以再任何用到类型的地方使用。

### （1）定义

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_PARAMETER , ElementType.TYPE_USE})//数组形式
public @interface TypeAnnotation {
    String value();
}
```

### （2）使用

```java
public class Test01 {
    private @TypeAnnotation("int") int num;
    public <@TypeAnnotation("T")T,@TypeAnnotation("K")K,@TypeAnnotation("V")V> void method(){

    }

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        TypeAnnotation typeAnnotation = Test01.class.getDeclaredField("num").getAnnotatedType().getAnnotation(TypeAnnotation.class);
        if(typeAnnotation!=null){
            System.out.println(typeAnnotation.value());
        }


        TypeVariable<Method>[] typeParameters = Test01.class.getMethod("method").getTypeParameters();
        for (TypeVariable<Method> typeParam : typeParameters) {
            TypeAnnotation annotation = typeParam.getAnnotation(TypeAnnotation.class);
            if (annotation != null) {
                System.out.println(annotation.value());
            }
        }

    }
}
```

# 八、时间api







