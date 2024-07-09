①②③④⑤⑥⑦

# 一、`Junit`

## 1.`Junit`单元测试

之前用于测试程序的方法，需要创建`Test`类，调用`main`方法，并且与业务逻辑冗余，如果测试逻辑不同，还需要写多个测试类。

例如测试一个`Calculator`类里的方法

```java
public class Calculator {
    public int add(int a,int b){
        return a+b;
    }
    public int sub(int a,int b){
        return a-b;
    }
}
```

测试方法

```java
public class Test {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.add(2, 3));
        System.out.println(calculator.sub(5, 1));
    }
}
```

为了解决上述传统测试带来的问题，我们引入`Junit`单元测试的方法。

我们一般将业务与测试分为不同的包，并按照命名原则进行命名。

编写`CalculatorTest`类

```java
public class CalculatorTest {
    //测试add()
    @Test//需要添加@Test注解
    public void testAdd(){
        System.out.println("测试add()");
        Calculator cal = new Calculator();
        int ret=cal.add(3,4);
        Assert.assertEquals(7,ret);//测试业务逻辑
    }

    //测试sub()
    @Test
    public void testSub(){
        System.out.println("测试sub()");
        Calculator cal = new Calculator();
        int ret=cal.sub(7,3);
        Assert.assertEquals(4,ret);//测试业务逻辑
    }
}
```

注意：==测试通过只是未检测到程序异常，但并不代表业务逻辑正确==，还需要使用`Assert`类的`assertEquals()`方法进行预测判断。

在上面测试中发现，我们测试`sub()`、`add()`方法，都需要创建一个`Calculator`对象。在`Junit`中，还可以通过`@Before`、`@After`注解来在测试前加入申请资源的代码，或在测试后加入释放资源的代码。

```java
public class CalculatorTest {
    Calculator cal=null;
    @Before
    public void init(){
        System.out.println("测试开始，申请测试资源");
        cal = new Calculator();
    }
    @After
    public void close(){
        System.out.println("测试结束，释放测试资源");
    }
    
    //测试add()
    @Test
    public void testAdd(){
        System.out.println("测试add()");
        int ret=cal.add(3,4);
        Assert.assertEquals(7,ret);//测试业务逻辑
    }

    //测试sub()
    @Test
    public void testSub(){
        System.out.println("测试sub()");
        int ret=cal.sub(7,3);
        Assert.assertEquals(4,ret);//测试业务逻辑
    }
}
```

## 2、注解

### （1）引入

> ==框架=注解+反射+设计模式==

在 Java 中，==注解（Annotations）是一种用于提供元数据的特殊形式的语法==。它们可以被用于类、方法、变量、参数和包声明上，提供了一种形式化的方法来描述这些元素的行为和特性。注解不直接影响程序的操作，但可以被用于工具、库和框架中，以便在运行时或编译时进行特殊处理。

注解可以用于为代码提供元数据。这些元数据可以被编译器用来检测错误或抑制警告，也可以在运行时被用来处理注解过的元素。例如，在 Java EE 或 Spring 框架中，注解被广泛用于定义组件、依赖注入和事务管理。

### （2）常用注解

#### ① `Junit`注解

- `@Test`：定义测试方法
- `@Before`：设置测试环境
- `@After`：设置测试环境

#### ② javadoc`注解

Javadoc 注释用于为 Java 类、接口、方法、字段和构造函数生成 API 文档。这些注释写在代码元素的声明之前，用 `/** ... */` 包围。

- **`@param`**：描述方法或构造函数的参数。
- **`@return`**：描述方法的返回值。
- **`@throws` 或 `@exception`**：描述方法可能抛出的异常。
- **`@see`**：提供参考链接到其他文档。
- **`@since`**：指示成员自某个版本开始存在。
- **`@deprecated`**：标记某个元素不再推荐使用，并通常会提供替代方案。
- **`@author`**：标明代码的作者。
- **`@version`**：指示类或接口的版本。

Javadoc 工具可用于从这些注释中生成 HTML 格式的 API 文档。这个工具扫描源代码文件中的 Javadoc 注释，并生成对应的 API 文档页面。

#### ③ 常用内置注解

- **`@Override`**：表明一个方法声明打算重写超类中的方法。
- **`@Deprecated`**：==标记已经不再推荐使用的方法或类==，这通常是因为它们已经被更好的选项所取代。
- **`@SuppressWarnings`**：指示编译器忽略特定的警告。

在现代Java开发中，注解广泛用于替代传统的配置文件。例如：在早期版本的 Spring 框架中，配置通常是通过 XML 文件进行的。随着 Spring 2.5 的发布，注解开始被用来替代或补充 XML 配置。

### （3）自定义注解

Java 允许您定义自己的注解。==自定义注解可以通过使用 `@interface` 关键字来创建==。您可以定义注解的可用位置（如方法、字段或类）和策略（如保留在源代码中、编译时或运行时）。

```java
public @interface MyAnnotation {
    //配置参数
    String author() default "lxy";//设置默认值
    String []description();
    int value();
}
```

```java
@MyAnnotation(//传递配置参数
    description = {"des1","des2"},
    value = 5
)
public class Test {
}
```

注解内部可以不声明配置参数，这样的注解起标记作用。

### （4）元注解

元注解是指那些应用于其他==注解上的注解==，它们用于定义一个注解的行为。

下面是JDK5常用的注解

- `@Target`：指明注解可以应用的Java元素（如类、方法、字段等）。`@Target(ElementType.METHOD)` 意味着注解只能应用于方法上。
- `@Retention`：指定注解保留策略——即注解在什么级别可用，是源代码（`SOURCE`）、类文件（`CLASS`）还是运行时（`RUNTIME`）。`@Retention(RetentionPolicy.RUNTIME)` 意味着注解在运行时可用，因此可以通过反射获取。
- `@Documented`：指示将注解包含在Javadoc中。如果一个注解定义了`@Documented`，则所有使用该注解的元素的文档都将包含这个注解的说明。
- `@Inherited`：指示子类可以继承父类中的注解。如果一个类级别的注解被标记为`@Inherited`，则这个注解会被继承到子类中。

# 二、枚举类

Java中的枚举类（`enum`）是一种特殊的类，用于定义一个变量组，代表一组固定的常量。使用枚举类时，Java 保证实例的唯一性，即在==内存中只有一个枚举类型的单一实例==。枚举类比较适合用于那些代表一组固定常量的场合，如星期、月份、季节等。

## 1.基本枚举类的使用

基础的枚举类使用非常直接，只需要使用 `enum` 关键字声明类名，然后在花括号内列出所有枚举常量即可。例如，定义一个表示星期的枚举类：

```java
public enum Day {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY
}
```

## 2.枚举类的特性

- **自动继承**：Java中的所有枚举都自动继承自`java.lang.Enum`类，因此不能再继承其他类。
- **只能定义私有构造器**：这确保了枚举常量的唯一性。
- **实现接口**：虽然枚举不能继承其他类，但可以实现一个或多个接口。
- **添加方法和字段**：枚举不仅限于定义常量，它们可以有自己的方法和字段。
- **覆写方法**：枚举可以覆写其父类`Enum`的方法，如`toString()`。

## 3.带属性和方法的枚举类

枚举类也可以包含字段和方法，这使得它们功能更加强大。例如，可以为`Day`枚举添加一个方法来检查是否为工作日：

```java
public enum Day {
    SUNDAY(false),
    MONDAY(true),
    TUESDAY(true),
    WEDNESDAY(true),
    THURSDAY(true),
    FRIDAY(true),
    SATURDAY(false);

    private final boolean isWorkday;

    Day(boolean isWorkday) {
        this.isWorkday = isWorkday;
    }

    public boolean isWorkday() {
        return this.isWorkday;
    }
}
```

在这个例子中，每个枚举常量都有一个布尔值属性`isWorkday`，表示该天是否为工作日。`isWorkday()`方法可以用来查询每个枚举常量是否代表一个工作日。

## 4.枚举类的高级特性

- **使用`values()`方法**：自动为枚举类型提供的方法，返回一个包含所有枚举值的数组。
- **使用`valueOf()`方法**：转换字符串为枚举实例。
- **使用`ordinal()`方法**：获取枚举常量的序数。

枚举类型是Java语言的一个强大特性，它提供了一种安全的方式来定义一组固定的值。通过为枚举添加属性和方法，可以使其功能更加丰富和强大。

枚举类的简单使用

```java
public enum Season {
    //枚举，对象或常量，放在最开始的位置
    //public static final Season SPRING = new Season("春天",1);
    SPRING("春天",1),//此处对应的SPRING本质上是一个Season对象
    SUMMER("夏天",2),
    AUTOMN("秋天",3),
    WINTER("冬天",4);
    private final String seasonName;
    private final int seasonNum;

    private Season(String seasonName, int seasonNum) {
        this.seasonName=seasonName;
        this.seasonNum=seasonNum;
    }

    @Override
    public String toString() {
        return "Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonNum=" + seasonNum +
                '}';
    }
}

//下面对应的Season02对象没有属性，因此也不需要构造器、重写方法等操作
public enum Season02 {
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER;
}

class Test{
    public static void main(String[] args) {
        System.out.println(Season.WINTER);
    }
}
```

枚举类继承的一些常用方法

```java
class Test{
    public static void main(String[] args) {
        //返回枚举对象数组
        Season[] values = Season.values();
        for(Season e:values){
            System.out.println(e);
        }
        //返回枚举对象
        Season spring = Season.valueOf("SPRING");
        System.out.println(spring);
    }
}
```

枚举对象实现多态

```java
public enum Season implements Show,Description{
    //枚举，对象或常量，放在最开始的位置
    SPRING("春天",1){//匿名对象，实现接口方法重写
        @Override
        public void descreiption() {
            System.out.println("春暖花开");
        }
    },
    SUMMER("夏天",2) {
        @Override
        public void descreiption() {
            System.out.println("烈日炎炎");
        }
    },
    AUTOMN("秋天",3) {
        @Override
        public void descreiption() {
            System.out.println("秋风落叶");
        }
    },
    WINTER("冬天",4) {
        @Override
        public void descreiption() {
            System.out.println("冰天雪地");
        }
    };
    private final String seasonName;
    private final int seasonNum;

    private Season(String seasonName, int seasonNum) {
        this.seasonName=seasonName;
        this.seasonNum=seasonNum;
    }

    @Override
    public String toString() {
        return "Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonNum=" + seasonNum +
                '}';
    }
	//调用不同的this实现接口方法重写
    @Override
    public void show() {
        System.out.println(this);
    }
}
class Test01{
    public static void main(String[] args) {
        Season[] values = Season.values();
        for(Season e:values){
            e.show();
            e.descreiption();
        }
    }
}
```

在`switch`语句中的使用

```java
public enum Gender {
    男,
    女;
}
```

```java
public class Test {
    public static void main(String[] args) {
        Gender sex=Gender.男;
        switch (sex){
            case 男:
                System.out.println("左边");
                break;
            case 女:
                System.out.println("右边");
                break;
        }
    }
}
```

# 三、反射

## 1.概念

Java 反射是一个功能强大的机制，==允许程序在运行时检查或修改类的行为==。反射API主要位于`java.lang.reflect`包中，提供了一种方法来==动态地加载类、访问类的成员、修改属性==等，即使在编译时不知道具体的类和方法。

例如，通过反射，你可以在不知道类名的情况下，实例化一个对象，调用其方法，或者改变它的属性。这是一种强大的特性，但也可能导致代码难以理解和维护，并且可能降低性能。

## 2.`Class`类

在程序执行的时候，将.class文件读入到JVM中，每当JVM加载一个类时，就会为该类创建一个`Class`对象。

这个对象包含了类的大量信息，包括：

- 类的名称
- 包信息
- 构造函数
- 方法
- 成员变量
- 注解
- 实现的接口
- 继承的父类
- 其他元数据

用户可以通过class类控制JVM对.class文件进行解释。

### （1）获取方式

可以通过多种方式获取对类的`Class`对象的引用，最常用的有：

- 使用`.class`语法，例如`String.class`。
- 调用对象的`.getClass()`方法，例如`"hello".getClass()`。
- 使用`Class.forName()`方法动态加载类，并获取类的`Class`对象，例如`Class.forName("java.lang.String")`。
- 类加载器

```java
public class Person  {
    public String name;
    private int  age;
    public void sleep(){
        System.out.println("Person sleep");
    }
    private  Person(String name,int age){
        this.name = name;
        this.age=age;
    }
    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

```java
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        //通过对象方法getClass（）获取
        Class c1=new Person().getClass();
        //通过类的静态属性class获取
        Class c2=Person.class;
        //通过Class类的方法forName获取，推荐方法
        Class c3 = Class.forName("com.lxy.refection.Person");
        //通过类加载器获取
        ClassLoader classLoader = Test.class.getClassLoader();
        Class c4 = classLoader.loadClass("com.lxy.refection.Person");

    }
}
```

### （2）具有`class`属性的种类

- 类
- 接口
- 注解
- 数组
- 基本数据类型
- void

```java
public class Test02 {
    public static void main(String[] args) {
        System.out.println(Person.class);
        System.out.println(Serializable.class);
        System.out.println(Override.class);
        System.out.println(int[].class);
        System.out.println(int.class);
        System.out.println(void.class);
    }
}
```

### （3）常用方法

一旦获得了`Class`对象，你就可以使用反射API来检查类的结构，创建类的对象，调用方法，获取和设置属性等，无需在编译时知道类的内部结构。反射机制主要用于：

- IDE开发工具：如在类浏览器中显示类的成员。
- 调试和测试工具：允许这些工具在运行时检查对象和类的状态。
- 动态代理。
- 对象序列化。
- 注解处理。

#### ① 获取构造器，并创建对象

```java
public class Test03 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class c=Class.forName("com.lxy.refection.Person");
        //获得运行时public修饰、无参的构造器
        Constructor con = c.getConstructor();
        Object obj = con.newInstance();
        System.out.println(obj);
        //获得运行时public修饰，指定参数的构造器
        Constructor con1 = c.getConstructor(String.class);
        System.out.println(con1.newInstance("zhangsan"));
        //获得运行时全部指定参数的构造器
        Constructor con2 = c.getDeclaredConstructor(String.class, int.class);
        con2.setAccessible(true);//设置私有构造函数可访问
        System.out.println(con2.newInstance("lisi",18));
    }
}
```

#### ② 获取属性，并赋值

```java
public class Test04 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class c=Class.forName("com.lxy.refection.Person");
        //获取运行时public修饰的属性
        Field[] fields1 = c.getFields();
        for(Field e:fields1){
            System.out.println(e);
        }
        //获取运行时全部的属性
        Field[] fields2 = c.getDeclaredFields();
        for(Field e:fields2){
            System.out.println(e);
        }
        
        //获取运行时public修饰指定的属性
        Field field1 = c.getDeclaredField("age");
        System.out.println(field1);
        //获取属性的名字
        System.out.println(field1.getName());
        //获取属性的修饰符
        System.out.println(field1.getModifiers());//返回一个整数，表示字段的Java语言修饰符。这些修饰符被编码为可以通过 Modifier 类中的方法解码的 int 值。
        System.out.println(Modifier.toString(field1.getModifiers()));
        System.out.println(field1.getType());

        //获取运行时(包括private修饰)指定的属性
        Field field2 = c.getField("name");
        System.out.println(field2);
        //赋值,需要先有一个对象，并且只能赋予非private修饰的属性
        Object obj = c.newInstance();
        field2.set(obj,"lihua");
        System.out.println(obj);
    }
}
```

#### ③ 获取方法，并调用

```java
public class Test05 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class c=Class.forName("com.lxy.refection.Person");
        //获得运行时所有public修饰的方法
        Method[] methods1 = c.getMethods();
        for(Method e:methods1){
            System.out.println(e);
        }
        //获得运行时所有（包括private修饰）的方法
        Method[] methods2 = c.getDeclaredMethods();
        for(Method e:methods2){
            System.out.println(e);
        }
        //获得运行时public修饰的指定方法
        Method method1 = c.getMethod("walk");
        //获得运行时所有（包括private修饰）的指定方法
        Method method2 = c.getDeclaredMethod("sleep");
        //获得方法的修饰符
        System.out.println(Modifier.toString(method1.getModifiers()));
        //获得方法的参数列表
        Parameter[] parameters = method1.getParameters();
        for(Parameter e:parameters){
            System.out.println(e);
        }
        //获得方法的名字
        System.out.println(method1.getName());
        //获得方法的返回值
        System.out.println(method1.getReturnType());
        //获得方法的注解
        Annotation[] annotations = method1.getAnnotations();
        for(Annotation e:annotations){
            System.out.println(e);
        }
        //获得方法的异常
        Class[] exceptionTypes = method1.getExceptionTypes();
        for(Class e:exceptionTypes){
            System.out.println(e);
        }
        //调用方法,需要先创建对象
        Object obj=c.newInstance();
        method1.invoke(obj);
    }
}
```

#### ④ 获取包、接口、注解

```java
public class Test06 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class c=Class.forName("com.lxy.refection.Person");
        //获取运行时类的接口
        Class[] interfaces = c.getInterfaces();
        for(Class e:interfaces){
            System.out.println(e);
        }
        //获取运行时父类的字节码信息
        Class superclass = c.getSuperclass();
        //获取运行时包的路径
        System.out.println(c.getPackage());
        //获取运行时类的注解
        Annotation[] annotations = c.getAnnotations();
        for(Annotation e:annotations){
            System.out.println(e);
        }
    }
}
```





















