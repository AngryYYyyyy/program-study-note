# 一、简介

Spring 框架是一个开源的、轻量级的、综合性的框架，最初由 Rod Johnson 在 2003 年发布。它是为了解决企业级应用开发的复杂性而设计的，提供了一种简单的开发模式来构建所有类型的 Java 应用。Spring 的核心特性可以用于任何类型的部署平台，是当今最受欢迎的 Java EE（企业版）应用程序开发框架之一。

## 1.核心特点

- **轻量级**：Spring 是一个轻量级的框架，它通过==控制反转（IoC）==和==依赖注入（DI）==减少了对程序代码的侵入。
- **面向切面的编程（AOP）**：Spring ==支持面向切面的编程==，允许定义方法拦截器和切点来解耦代码中的交叉关注点。
- **事务管理**：Spring 提供了一致的事务管理接口，可以管理 Java 应用中的事务。
- **集成**：Spring 可以轻松集成其他 Java 框架，如 MyBatis、Quartz、JMS 等。

## 2.主要模块

![image-20240308204006868](.\assets\image-20240308204006868.png)

Spring 框架包含了多个模块，为开发者提供了构建企业级应用的一整套工具。主要模块包括：

- **核心容器**：包括 Spring Core、Beans、Context、Expression Language，提供了框架的基础部分。
- **数据访问/集成**：包括 JDBC、ORM、OXM、JMS 和事务模块，用于数据库操作和事务管理。
- **Web**：包括 Web、Web-MVC、Web-Socket、Web-Portlet 等模块，提供了创建 Web 应用的功能。
- **AOP 和设备支持**：为应用提供了面向切面的编程实现。
- **消息和测试**：用于提供消息传递功能和测试支持。

# 二、控制反转（IoC）

## 1.概念

控制反转（IoC，Inversion of Control）是一种设计原则，用于减少计算机代码之间的耦合度。在传统的程序设计中，代码之间的依赖关系通常由程序员在代码中直接设定。这种方式虽然直接，但会导致代码之间的耦合度增加，从而降低了代码的可维护性和可扩展性。==控制反转的核心思想是将对象的创建和依赖关系的管理从程序代码中抽离出来，交由外部容器或框架来处理。==

### （1）IoC的工作原理

==在控制反转模式下，对象的创建和它们之间的依赖关系不再由对象本身在内部控制，而是由外部容器在程序运行时进行动态注入。==这意味着，对象的依赖项（如服务、配置等）不是在对象内部创建或查找，而是在创建对象的时候由外部环境注入进来。

==依赖注入（Dependency Injection, DI）是实现IoC的一种方法，它允许对象通过构造器参数、工厂方法参数或对象的属性来接收依赖项。==这种方式下，对象不需要从容器中查找依赖或管理它们的生命周期，依赖关系完全由外部容器管理。

Spring框架是控制反转原则的一个典型应用，通过其IoC容器，Spring管理着应用中对象的创建、配置和管理过程。Spring容器通过读取配置文件或注解，自动地将依赖关系注入到组件中，从而实现了松耦合和高度灵活性。开发者只需要关注业务逻辑的实现，而对象的生命周期和依赖关系管理都交给了Spring容器来处理。

## 2.简单使用

### （1）pom.xml中导入依赖

```xml
<!--包含其他核心容器的依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.5</version>
</dependency>
```

### （2）配置applicationContext.xml文件

创建EmpDao接口

```java
public interface EmpDao {
    void addEmp();
}
```

创建EmpDaoImpl实现类

```java
public class EmpDaoImpl implements EmpDao {
    @Override
    public void addEmp() {
        System.out.println("addEmp");
    }
}
```

applicationContext.xml声明对象

```xml
<bean id="empDao" class="com.lxy.dao.EmpDaoImpl"></bean><!--后续会通过包扫描声明-->
```

### （3）测试

```java
@Test
public void test02(){
    //通过applicationContext.xml，初始化容器
    ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
    //通过键值对，获取bean对象，并确定类型
    EmpDao empDao = applicationContext.getBean("empDao", EmpDao.class);
    empDao.addEmp();
}
```

## 3.原理分析

### （1）使用 XML 解析技术读取配置文件

```xml
<beans>
    <bean id="empDao" class="com.msb.dao.impl.EmpDaoImpl"></bean>
</beans>
```

使用 Java 中的 XML 解析技术（如 DOM、SAX 或 JAXB）来解析这个文件。以下是使用 DOM 解析示例：

```java
Map<String, Object> beans = new HashMap<>();

DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
Document doc = builder.parse(new File("applicationContext.xml"));
NodeList beanList = doc.getElementsByTagName("bean");
for (int i = 0; i < beanList.getLength(); i++) {
    Node bean = beanList.item(i);
    NamedNodeMap attr = bean.getAttributes();
    String beanId = attr.getNamedItem("id").getNodeValue();
    String className = attr.getNamedItem("class").getNodeValue();
    beans.put(beanId, className);
}
```

### 2. 使用反射技术实例化对象并放入容器

使用反射技术实例化对象，并将对象放入一个容器中（如 Map 集合）。

```java
for (Map.Entry<String, String> entry : beans.entrySet()) {
    String beanId = entry.getKey();
    String className = entry.getValue();
    Class<?> clazz = Class.forName(className);
    Object obj = clazz.newInstance();
    beans.put(beanId, obj);
}
```

注意，上述代码片段假定 `beans` Map 的类型已经更改为 `Map<String, Object>`，以存储实例化的对象而不是类名。

### （3）使用工厂模式返回 Bean 对象

最后，实现一个简单的工厂方法 `getBean`，从容器中根据 Bean 的 ID 获取对象。

```java
public Object getBean(String name) {
    return beans.get(name);
}
```

# 三、依赖注入（DI）

## 1.概念

依赖注入（Dependency Injection, DI）是一种软件设计模式，用于实现控制反转（Inversion of Control, IoC），其核心目的是降低程序组件之间的耦合度。通过依赖注入，组件的依赖关系不是由组件内部自行创建或查找，而是由外部容器在创建组件时注入进去。这样做的好处是增加了代码的灵活性和可测试性，同时也提高了代码的可维护性。

依赖注入的基本原理是，一个对象不应负责创建或查找它所需的依赖对象（服务）。相反，这些==依赖应当由外部通过某种方式提供给对象==。这“某种方式”通常是通过构造器、设置器方法（setter）或者接口注入。

## 2.多种xml注入方法

### （1）设置器

```xml
<bean id="user" class="com.lxy.User">
    <property name="uesrId" value="1"></property>
    <property name="userName" value="zhangsan"></property>
    <property name="userPassword" value="123456"></property>
</bean>
```

### （2）构造器

```xml
<bean id="user" class="com.lxy.User">
    <constructor-arg name="uesrId" value="1"></constructor-arg>
    <constructor-arg name="userName" value="zhangsan"></constructor-arg>
    <constructor-arg name="userPassword" value="123456"></constructor-arg>
</bean>
<bean id="user01" class="com.lxy.User">
    <constructor-arg index="0" value="2"></constructor-arg>
    <constructor-arg index="1" value="lisi"></constructor-arg>
    <constructor-arg index="2"  value="654321"></constructor-arg>
</bean>
```

还可以通过p名称空间和c名称空间来简化设置器、构造器的依赖注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="user01" class="com.lxy.User" p:uesrId="1" p:userName="zhangsan" p:userPassword="123456"></bean>
    <bean id="user02" class="com.lxy.User" c:uesrId="2" c:userName="lisi" c:userPassword="654321"></bean>
</beans>
```

特殊符号<>&处理分别为`&lt;` `&gt;` `&amp;`

```xml
<bean id="user03" class="com.lxy.User" c:uesrId="3" c:userName="xiaoming" c:userPassword="&amp; &gt; &lt;"></bean>
```

### （3）注入引用

创建实体类Cat、Mouse

```java
public class Cat {
    private String catName;
    private Mouse mouse;
}
```

```java
public class Mouse {
    private String mouseName;
    private Date date;
}
```

引入外部bean

```xml
    <!--引用外部bean-->
    <bean id="date" class="java.util.Date"></bean>
    <bean id="mouse" class="com.lxy.Mouse">
        <property name="mouseName" value="jerry"></property>
        <property name="date" ref="date"></property>
    </bean>
    <bean id="cat" class="com.lxy.Cat">
        <property name="catName" value="tom"></property>
        <property name="mouse" ref="mouse"></property>
    </bean>
```

引用内部bean

```xml
<!--引用内部bean-->
    <bean id="mouse01" class="com.lxy.Mouse">
        <property name="mouseName" value="jerry"></property>
        <property name="date" >
            <bean class="java.util.Date"></bean>
        </property>
    </bean>
    <bean id="cat01" class="com.lxy.Cat">
        <property name="catName" value="tom"></property>
        <property name="mouse" >
            <bean class="com.lxy.Mouse">
                <property name="mouseName" value="jerry"></property>
                <property name="date">
                    <bean class="java.util.Date"></bean>
                </property>
            </bean>
        </property>
    </bean>
```



### （4）注入集合

创建实体类Book、Student

```java
public class Book {
    private String bname;
    private String author;
}
```

```java
public class Student {
    private String[] books;
    private Set<String> bookSet;
    private List<String> bookList;
    private Map<String,String> bookMap;
    private List<Book> bookList2;
}
```

数组

```xml
<property name="books">
    <array>
        <value>java</value>
        <value>c++</value>
        <value>linux</value>
    </array>
</property>
```

Set

```xml
<property name="bookSet">
    <set>
        <value>java</value>
        <value>c++</value>
        <value>linux</value>
    </set>
</property>
```

List

```xml
<property name="bookList">
    <list>
        <value>java</value>
        <value>c++</value>
        <value>linux</value>
    </list>
</property>
```

Map

```xml
<property name="bookMap">
    <map>
        <entry key="java" value="1"></entry>
        <entry key="c++" value="2"></entry>
        <entry key="linux" value="3"></entry>
    </map>
</property>
```

==使用util简化集合配置==

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util
       http://www.springframework.org/schema/util/spring-util.xsd
">
    <util:list id="outBookList">
        <bean class="com.lxy.Book" p:bname="java" p:author="zhangsan"></bean>
        <bean class="com.lxy.Book" p:bname="c++" p:author="lisi"></bean>
        <bean class="com.lxy.Book" p:bname="linux" p:author="wangwu"></bean>
    </util:list>
        <property name="bookList2" ref="outBookList"></property>
    </bean>
</beans>
```

# 四、Bean的管理

在Spring框架中，"Bean"是一个被实例化、组装，并通过Spring容器所管理的对象。Spring容器负责创建Bean、装配它们的依赖关系，以及管理它们的整个生命周期。Bean是Spring应用中最基本的组成部分，它们通常代表应用的业务逻辑对象、数据库连接、事务管理器等。

## 1.Bean的作用域

Spring支持多种Bean的作用域：
- **单例（Singleton）**：在Spring IoC容器中，每个Bean定义有且只有一个实例。
- **原型（Prototype）**：每次请求都会创建一个新的Bean实例。
- **请求（Request）**：每个HTTP请求都会创建一个新的Bean，仅在web应用中使用。
- **会话（Session）**：在一个HTTP Session中，每个Session都会创建一个新的Bean，仅在web应用中使用。
- **全局会话（Global Session）**：在一个全局HTTP Session中，每个全局Session都会创建一个新的Bean，仅在Portlet应用中使用。

## 2.Bean的生命周期
Bean的生命周期包括多个阶段，从创建到销毁。Spring容器管理着Bean的整个生命周期。主要步骤包括：
1. **实例化Bean**：根据Bean的定义创建Bean的实例。
2. **设置属性值**：Spring容器通过反射机制给Bean的属性赋值。
3. **调用Bean的初始化方法**：如果Bean实现了`InitializingBean`接口或通过配置指定了init-method，Spring容器将调用这些方法。
4. **Bean的使用**：此时，Bean已准备就绪，可以被应用程序使用。
5. **调用Bean的销毁方法**：如果Bean实现了`DisposableBean`接口或通过配置指定了destroy-method，Spring容器将在Bean不再需要时调用这些方法。

## 3.后置处理器

在Spring框架中，后置处理器（Bean Post Processor）是一种特殊的Bean，它允许对Spring容器中的Bean实例进行额外的处理，这些处理动作会在Bean的初始化方法执行之前和之后发生。

示例

```java
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        // 在初始化之前执行的逻辑
        System.out.println("Before Initialization : " + beanName);
        return bean; // 可以返回原始Bean，也可以返回包装后的Bean
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // 在初始化之后执行的逻辑
        System.out.println("After Initialization : " + beanName);
        return bean; // 可以返回原始Bean，也可以返回包装后的Bean
    }
}
```

## 3.自动装配

在Spring框架中，自动装配（autowiring）是一种依赖注入的方式，它允许Spring容器==自动检测和装配Bean之间的依赖关系==，从而减少了配置上的工作量。自动装配通过检查应用上下文中的Bean定义和要求的依赖关系，自动将合适的Bean注入到其他Bean中。

示例

```xml
<bean id="dept01" class="com.lxy.Dept" p:deptName="销售部"></bean>
<!--byName 根据目标id值和属性值注入,要保证当前对象的属性值和目标对象的id值一致
    byType 根据类型注入,要保证相同类型的目标对象在容器中只有一个实例-->
<bean id="emp" class="com.lxy.Emp" autowire="byType"></bean>
```

## 4.Bean的配置

### （1）XML配置
### （2）注解配置
在Spring框架中，`@Component`, `@Service`, `@Repository`, 和 `@Controller` 注解是用于实现==自动组件扫描并注册Bean到Spring容器中的标注方式==。这些注解让开发者能够明确类的角色和用途，同时促进了低耦合和高内聚的设计原则。尽管它们在技术上具有相似的自动装配行为，但是通过使用这些具体的注解，可以为不同的应用层提供更清晰的指示。

示例

```java
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void add() {
        System.out.println("add.....");
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.lxy" use-default-filters="true"></context:component-scan>
</beans>
```

### （3）==完全注解配置==

```java
@ComponentScan(basePackages = "com.lxy")//包扫描
@PropertySource("classpath:infomation.properties")//引用外部资源
public class SpringConfig {
}
```

## 5.引用外部属性资源

infomation.properties

```properties
username01=zhangsan
userid=1
userpasswords=123456
```

applicationContext.xml

```xml
<context:property-placeholder location="classpath:infomation.properties"></context:property-placeholder>
```

# 五、AOP

## 1.代理模式

代理模式是一种设计模式，它通过引入一个代理对象来控制对另一个对象的访问。代理可以在客户端和目标对象之间起到中介的作用，并可以==在不改变目标对象的前提下进行一系列操作==，如访问控制、远程访问、延迟初始化、监视或记录日志等。

### （1）JDK动态代理（Proxy代理）

JDK动态代理是==基于接口的代理==，每个代理实例都有一个关联的调用处理器（InvocationHandler），当通过代理对象调用方法时，调用会被转发到这个调用处理器。

JDK动态代理的要求是目标对象必须实现一个或多个接口。

**示例代码**：

```java
public interface MyInterface {
    void doSomething();
}

public class MyInterfaceImpl implements MyInterface {
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在调用具体方法前可以添加自定义操作
        System.out.println("Before method call");
        Object result = method.invoke(target, args);
        // 在调用具体方法后可以添加自定义操作
        System.out.println("After method call");
        return result;
    }
}

// 创建代理实例
MyInterface proxyInstance = (MyInterface) Proxy.newProxyInstance(
    MyInterfaceImpl.class.getClassLoader(),
    new Class[] { MyInterface.class },
    new MyInvocationHandler(new MyInterfaceImpl()));
proxyInstance.doSomething();
```

### （2）CGLIB代理

CGLIB（Code Generation Library）是一个强大的高性能代码生成库，用于在运行时扩展Java类和实现接口。CGLIB代理不需要目标对象实现接口，它是通过==继承目标类来实现代理==的，因此也被称为子类代理。

**示例代码**：

首先需要添加CGLIB到项目依赖中，然后可以这样使用：

```java
public class MyBean {
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

public class MyMethodInterceptor implements MethodInterceptor {
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // 在调用具体方法前可以添加自定义操作
        System.out.println("Before method call");
        Object result = proxy.invokeSuper(obj, args);
        // 在调用具体方法后可以添加自定义操作
        System.out.println("After method call");
        return result;
    }
}

// 创建代理实例
Enhancer enhancer = new Enhancer();
enhancer.setSuperclass(MyBean.class);
enhancer.setCallback(new MyMethodInterceptor());
MyBean proxyBean = (MyBean) enhancer.create();
proxyBean.doSomething();
```

## 2.AOP概念

==面向切面编程==（Aspect-Oriented Programming，AOP）是一种编程范式，==旨在将横切关注点（cross-cutting concerns）与业务主逻辑分离==，以提高代码的模块化程度。横切关注点是那些影响应用多个部分的问题，例如日志记录、事务管理、安全性等，这些通常与业务逻辑的核心功能交叉但又相对独立。

==Spring框架==通过Spring AOP和整合AspectJ提供了对面向切面编程的支持，==允许开发者以声明的方式处理这些横切关注点==，而不是通过硬编码。

### （1）AOP的术语

- **Aspect（切面）**：一个关注点的模块化，这个关注点可能会横切多个对象。
- **Join Point（连接点）**：程序执行过程中的某个特定点，比如方法调用或异常抛出的时刻。
- **Advice（通知）**：在切面的某个特定的连接点上执行的动作。有不同类型的通知：前置通知（Before）、后置通知（After）、返回通知（After-returning）、抛出异常通知（After-throwing）和环绕通知（Around）。
- **Pointcut（切入点）**：匹配连接点的表达式，表示==实际被增强的方法==。
- **Target Object（目标对象）**：被一个或多个切面所通知的对象。

### （2）通知

在Spring AOP（面向切面编程）中，"通知"（Advice）定义了在切面的某个连接点（Join Point）上要执行的动作。Spring AOP 支持五种类型的通知，这些通知允许在方法的调用前、调用后、完成后等不同时间点织入不同的行为。以下是这五种通知类型的概述：

1. **前置通知（Before advice）**：
   - 在目标方法被调用之前执行。
   - 不能阻止方法执行除非抛出异常。
   - 用途：检查输入参数、安全认证、日志记录等。

2. **返回通知（After returning advice）**：
   - 在目标方法成功执行之后执行。
   - 可以访问方法的返回值，但不能修改它。
   - 用途：修改返回给调用者的数据、记录日志、统计方法执行时间等。

3. **异常通知（After throwing advice）**：
   - 如果方法执行过程中抛出异常，则执行该通知。
   - 可以访问抛出的异常对象，但不能修改它。
   - 用途：异常处理、记录日志、发送错误报告等。

4. **最终通知（After (finally) advice）**：
   - 无论目标方法是否成功完成，该通知都会在方法执行后执行。
   - 不能访问方法的返回值或抛出的异常。
   - 用途：释放资源、清理、日志记录等。

5. **环绕通知（Around advice）**：
   - 是最强大的一种通知类型。
   - 环绕通知可以在方法调用前后执行自定义逻辑，也可以决定是否继续执行目标方法。
   - 可以修改原始的参数值，改变执行的返回值，或抛出一个异常来终止方法的调用。
   - 用途：全面控制方法的执行，适用于日志记录、事务管理、性能监测等。

示例：使用注解定义通知

```java
@Aspect
@Component
public class MyAspect {

    @Before("execution(* com.example.service.MyService.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        // 前置通知逻辑
    }

    @AfterReturning(value = "execution(* com.example.service.MyService.*(..))", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        // 后置通知逻辑
    }

    @AfterThrowing(value = "execution(* com.example.service.MyService.*(..))", throwing = "exception")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        // 异常通知逻辑
    }

    @After("execution(* com.example.service.MyService.*(..))")
    public void afterFinallyAdvice(JoinPoint joinPoint) {
        // 最终通知逻辑
    }

    @Around("execution(* com.example.service.MyService.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 环绕通知逻辑
        return proceedingJoinPoint.proceed(); // 继续执行目标方法
    }
}
```

在Spring AOP中，`execution`是最常用的==切点表达式==（pointcut expression），用于匹配方法执行的连接点。它能够根据方法的签名指定哪些方法应当被拦截。通过使用`execution`表达式，可以精确控制通知（advice）的织入点，实现在特定方法的执行前、执行后、抛出异常后等不同时间点织入不同的行为。

`execution`表达式的==一般语法==为：

```xml
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)throws-pattern?)
```

各部分含义如下：

- **modifiers-pattern**：（可选）方法的修饰符，例如`public`。
- **ret-type-pattern**：方法的返回类型，可以是具体类型或`*`表示任意返回类型。
- **declaring-type-pattern**：（可选）方法所在类的全路径名，也可以使用`*`表示任意类。
- **name-pattern**：方法名，可以使用`*`匹配任意方法。
- **param-pattern**：方法参数列表，可以使用特定类型，`*`表示任意类型的单个参数，`..`表示任意类型和数量的参数。
- **throws-pattern**：（可选）方法抛出的异常类型。



### （3）AOP注解方式实现

**1）导入aop依赖**

```xml
  <dependencies>
      <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>6.1.4</version>
        </dependency>
        <dependency>
            <groupId>aopalliance</groupId>
            <artifactId>aopalliance</artifactId>
            <version>1.0</version>
        </dependency>
</project>
```

**2）配置applicationContext.xml**

准备对象（省略接口）

```java
@Repository
public class DeptDaoImpl implements DeptDao {
    @Override
    public void addDept() {
        System.out.println("addDept....");
    }
}
```

```java
@Repository
public class ServiceImpl implements Service {
    @Autowired
    EmpDao empDao;
    @Autowired//自动装配
    DeptDao deptDao;
    @Override
    public void addEmp() {
        empDao.addEmp();
    }
    @Override
    public void addDept() {
        deptDao.addDept();
    }
}
```

配置xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">
        <context:component-scan base-package="com.lxy"></context:component-scan>
        <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
```

**3）编写Aspect**

AspectDao

```java
package com.lxy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DaoAspect {
    //设置公共切点
    @Pointcut("execution(* com.lxy.dao.*.add*(..))")
    public void addPointCut(){}

    @Before("addPointCut()")
    public void methodBefore(JoinPoint joinPoint){
        System.out.println("Before invoke");
    }

    @After("addPointCut()")
    public void methodAfter(JoinPoint joinPoint){
        System.out.println("After invoke");
    }
    
    @AfterReturning(value="addPointCut()",returning = "res")
    public void methodAfterReturning(JoinPoint joinPoint,Object res){
        System.out.println("AfterReturning invoke");
    }

    @AfterThrowing( value = "addPointCut()",throwing = "ex")
    public void methodAfterThrowing(Exception ex){
        System.out.println("AfterThrowing invoked");
    }
    @Around("addPointCut()")
    public Object methodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("aroundA invoked");
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("aroundB invoked");
        return proceed;
    }
}
```

==**AOP完全注解实现示例**==

```java
@Configuration
@ComponentScan(basePackages = "com.lxy")//包扫描
@EnableAspectJAutoProxy(proxyTargetClass = true)//自动代理
public class SpringConfig {
}
```



### （4）AOP.xml配置方式实现（了解）

```xml
<!--创建对象--> 
<bean id="userDao" class="com.com.msb.UserDaoImpl"></bean> 
<bean id="daoAspect" class="com.com.aspect.DaoAspect"></bean>
 <!--配置aop增强-->
    <aop:config>
        <!--切入点-->
        <aop:pointcut id="pointCutAdd" expression="execution(* com.msb.dao.UserDao.add*(..))"/>
        <!--配置切面-->
        <aop:aspect ref="daoAspect">
            <!--增强作用在具体的方法上-->
            <aop:before method="methodBefore" pointcut-ref="pointCutAdd"/>
            <aop:after method="methodAfter" pointcut-ref="pointCutAdd"/>
            <aop:around method="methodAround" pointcut-ref="pointCutAdd"/>
            <aop:after-returning method="methodAfterReturning"  pointcut-ref="pointCutAdd" returning="res"/>
            <aop:after-throwing method="methodAfterThrowing"  pointcut-ref="pointCutAdd" throwing="ex"/>
        </aop:aspect>
    </aop:config>
```

# 六、JDBC Template

`JdbcTemplate`是Spring框架中提供的一个用于==简化数据库操作的工具类==，它属于Spring的JDBC模块。通过使用`JdbcTemplate`，可以避免编写冗长的JDBC代码和手动处理数据库资源的创建和关闭，从而让数据库操作变得更加简洁和安全。

## 1.简单使用

### （1）导入依赖

```xml
<!--德鲁伊连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.10</version>
</dependency>
<!--mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.22</version>
</dependency>
<!--springJDBC包-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.5</version>
</dependency>
```

### （2）配置applicationContext.xml文件

创建jdbc.properties

```xml
jdbc_username=root
jdbc_password=root
jdbc_driver=com.mysql.cj.jdbc.Driver
jdbc_url=jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.lxy"></context:component-scan>
    <context:property-placeholder location="jdbc.properties"></context:property-placeholder>
    <!--配置druid连接池（数据源）-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc_username}"></property>
        <property name="password" value="${jdbc_password}"></property>
        <property name="url" value="${jdbc_url}"></property>
        <property name="driverClassName" value="${jdbc_driver}"></property>
    </bean>
    <!--配置jdbcTemplate 将druid注入-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
</beans>
```

### （3）开发业务程序

实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp implements Serializable {
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

Dao（省去接口）

```java
package com.lxy.dao.impl;

import com.lxy.dao.EmpDao;
import com.lxy.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpDaoImpl implements EmpDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public int findEmpCount() {
        /*查询员工个数
         * queryForObject 两个参数
         * 1 SQL语句
         * 2 返回值类型
         *
         * */
        Integer empCount = jdbcTemplate.queryForObject("select count(1) from emp", Integer.class);
        return empCount;
    }
    @Override
    public Emp findByEmpno(int empno) {
        /*
         * 查询单个员工对象
         * queryForObject三个参数
         * 1 SQL语句
         * 2 RowMapper接口的实现类对象,用于执行返回的结果用哪个类来进行封装 ,实现类为BeanPropertyRowMapper
         * 3 SQL语句中需要的参数 (可变参数)
         * */
        BeanPropertyRowMapper<Emp> rowMapper =new BeanPropertyRowMapper<>(Emp.class);
        Emp emp = jdbcTemplate.queryForObject("select * from emp where empno =?", rowMapper, empno);
        return emp;
    }
    @Override
    public List<Emp> findByDeptno(int deptno) {
        /*
         * 查询单个员工对象
         * query三个参数
         * 1 SQL语句
         * 2 RowMapper接口的实现类对象,用于执行返回的结果用哪个类来进行封装 ,实现类为BeanPropertyRowMapper
         * 3 SQL语句中需要的参数 (可变参数)
         * */
        BeanPropertyRowMapper<Emp> rowMapper =new BeanPropertyRowMapper<>(Emp.class);
        List<Emp> emps = jdbcTemplate.query("select * from emp where deptno =?", rowMapper, deptno);
        return emps;
    }
    @Override
    public int addEmp(Emp emp) {
        /*增删改
         * 统统用update方法 两个参数
         * 1 SQL语句
         * 2 SQL语句需要的参数 (可变参数)
         *
         * */
        String sql ="insert into emp values(DEFAULT ,?,?,?,?,?,?,?)";
        Object[] args ={emp.getEname(),emp.getJob(),emp.getMgr(),emp.getHiredate(),emp.getSal(),emp.getComm(),emp.getDeptno()};
        return jdbcTemplate.update(sql,args);
    }
    @Override
    public int updateEmp(Emp emp) {
        String sql ="update emp set ename =? , job =?, mgr=? , hiredate =?, sal=?, comm=?, deptno =? where empno =?";
        Object[] args ={emp.getEname(),emp.getJob(),emp.getMgr(),emp.getHiredate(),emp.getSal(),emp.getComm(),emp.getDeptno(),emp.getEmpno()};
        return jdbcTemplate.update(sql,args);
    }
    @Override
    public int deleteEmp(int empno) {
        String sql ="delete  from emp where empno =?";
        return jdbcTemplate.update(sql, empno);
    }
}
```

Service（省去接口）

```java
package com.lxy.service.impl;

import com.lxy.dao.EmpDao;
import com.lxy.pojo.Emp;
import com.lxy.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpDao empDao;
    @Override
    public int findEmpCount() {
        return empDao.findEmpCount();
    }
    @Override
    public Emp findByEmpno(int empno) {
        return empDao.findByEmpno( empno);
    }
    @Override
    public List<Emp> findByDeptno(int deptno) {
        return empDao.findByDeptno( deptno);
    }
    @Override
    public int addEmp(Emp emp) {
        return empDao.addEmp(emp);
    }
    @Override
    public int updateEmp(Emp emp) {
        return empDao.updateEmp(emp);
    }
    @Override
    public int deleteEmp(int empno) {
        return empDao.deleteEmp(empno);
    }
}
```

## 2.批处理

`JdbcTemplate` 是 Spring 框架中提供的一个 JDBC 抽象库，它简化了 JDBC 的使用，使数据库操作更加简洁。对于需要执行批量操作的场景，比如插入、更新或删除大量数据，`JdbcTemplate` 提供了批处理功能，可以有效提高性能。

批量更新是 `JdbcTemplate` 批处理中最常用的操作之一，可以用来执行大量的插入、更新或删除操作。`JdbcTemplate` 提供了几种不同的 `batchUpdate` 方法来支持批处理。这里是一个使用 `batchUpdate` 方法进行批量插入的例子：

```java
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BatchExample {
    private JdbcTemplate jdbcTemplate;

    public BatchExample(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void batchInsert(List<Person> people) {
        final String sql = "INSERT INTO people (name, age) VALUES (?, ?)";

        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Person person = people.get(i);
                ps.setString(1, person.getName());
                ps.setInt(2, person.getAge());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
    }

    static class Person {
        private String name;
        private int age;

        // Constructors, getters and setters
    }
}
```

在这个例子中，`batchInsert` 方法接受一个 `Person` 对象的列表，并使用 `batchUpdate` 方法批量插入这些对象。`BatchPreparedStatementSetter` 接口用于设置批处理中每个更新的参数。

# 七、事务的控制

在数据库管理和应用开发中，事务是一系列操作，它们作为一个单一的工作单元执行。事务要么全部成功，要么全部失败，这是通过数据库管理系统（DBMS）的事务管理和控制实现的。

## 1.ACID属性

1. **原子性（Atomicity）**：事务中的所有操作都被视为一个单一的单元，它们==要么全部成功执行，要么全部失败==。如果事务中的任何操作失败，整个事务将回滚到开始状态，就像什么都没发生一样。

2. **一致性（Consistency）**：事务的执行==将数据库从一个一致性状态转换到另一个一致性状态==。事务执行期间不会违反数据库的任何完整性约束。

3. **隔离性（Isolation）**：==并发执行的事务彼此隔离==，事务的执行不会被其他事务干扰。这意味着事务看不到系统中的中间状态，只能看到事务开始之前和事务提交之后的状态。

4. **持久性（Durability）**：一旦事务提交，它==对数据库的改变就是永久性的==，即使系统崩溃也不会丢失数据。

## 2.事务并发问题

在数据库事务中，存在几种典型的并发问题，分别是脏读（Dirty Reads）、不可重复读（Non-Repeatable Reads）和幻读（Phantom Reads）。这些问题描述了在并发环境下可能发生的数据读取异常情况。数据库管理系统（DBMS）通过实现不同级别的事务隔离来防止这些问题。

### （1）脏读（Dirty Reads）

==脏读发生在一个事务读取了另一个未提交事务修改的数据==。如果那个未提交的事务最终被回滚，那么第一个事务读取的数据就是“脏”的，因为它读取了从未被确认的数据。

**示例**：事务A修改了一条记录，但还没有提交。事务B读取了这条记录的修改后的值。如果A回滚，B读到的数据实际上是不存在的。

### （2）不可重复读（Non-Repeatable Reads）

不可重复读描述的是在==同一个事务内，多次读取同一数据集合时，由于另一个并发事务的更新操作，导致两次读取的数据不一致的情况==。

**示例**：事务A读取了一条记录，事务B更新了这条记录并提交，当事务A再次读取这条记录时，会发现数据已经变了。

### （3）幻读（Phantom Reads）

幻读与不可重复读类似，但它是指当一个事务重新执行一个范围查询时，返回一组符合查询条件的“幻影”数据。这是因为==另一个并发事务插入或删除了满足查询条件的数据==。

**示例**：事务A根据条件查询得到一组记录，这时事务B插入了一条满足A查询条件的新记录并提交。当事务A再次执行相同的查询时，会发现多了一条之前未见过的记录。

## 3.事务管理

在应用开发中，事务管理是确保数据一致性和完整性的关键。在Spring框架中，提供了声明式事务管理和编程式事务管理两种方式来处理事务：

1. ==**声明式事务管理**==：通过Spring的`@Transactional`注解实现，这是推荐的方法，因为它将事务管理代码从业务逻辑中分离出来，使得代码更简洁、更易于维护。

2. **编程式事务管理**：使用`TransactionTemplate`或直接使用`PlatformTransactionManager`。虽然提供了更多的控制，但编程式事务管理使得代码与事务管理逻辑耦合，通常不推荐使用。

示例：使用@Transactional注解

配置applicationContext.xml文件

```xml
<!--配置一个事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--将数据源注入事务管理器-->
        <property name="dataSource"  ref="dataSource"></property>
    </bean>
    <!--开启事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"/>

```

将需要事务管理的服务添加注解

```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {
    @Transactional
    public void performSomeDatabaseOperations() {
        // 这里的数据库操作将在一个事务中执行
        // 如果发生异常，事务将回滚
    }
}
```

示例：零xml配置事务管理

```java
@Configuration
@ComponentScan("com.lxy")
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
public class Config {
    @Value("${jdbc_driver}")
    private String driver;
    @Value("${jdbc_url}")
    private String url;
    @Value("${jdbc_username}")
    private String username;
    @Value("${jdbc_password}")
    private String password;
    /*创建数据库连接池*/
    @Bean
    public DruidDataSource getDruidDataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    /*创建JdbcTemplate对象*/
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
    /*创建事务管理器*/
    @Bean
    public PlatformTransactionManager getPlatformTransactionManager(DataSource dataSource){
        DataSourceTransactionManager transactionManager =new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
```

## 4.事务传播

事务的传播行为==定义了在方法被调用时事务上下文如何被传播==。Spring Framework 提供了多种事务传播行为，允许开发者精细地控制方法执行时事务的创建、复用或挂起等行为。这些传播行为在 `@Transactional` 注解中通过 `propagation` 属性设置。

1. ==**REQUIRED（默认）**==：如果当前存在事务，就加入该事务；如果当前没有事务，则创建一个新的事务。

2. **SUPPORTS**：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务方式执行。

3. **MANDATORY**：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。

4. **==REQUIRES_NEW==**：总是创建一个新的事务，如果当前存在事务，则将当前事务挂起。

5. **NOT_SUPPORTED**：总是以非事务方式执行，如果当前存在事务，则将当前事务挂起。

6. **NEVER**：总是以非事务方式执行，如果当前存在事务，则抛出异常。

7. **NESTED**：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则表现同`REQUIRED`。嵌套事务是一个子事务，它独立于封闭事务提交或回滚。如果封闭事务失败，则会回滚嵌套事务的操作。但是，嵌套事务内的失败只会回滚嵌套事务，不会影响封闭事务。

