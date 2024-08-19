# 一、简介

Spring MVC是Spring框架的一部分，用于构建Web应用程序。MVC代表模型-视图-控制器，这是一种设计模式，用于分离应用程序的不同方面，使管理和维护变得更容易。在Spring MVC中：

- **模型（Model）**代表应用程序的数据模型，通常是业务模型和数据访问层的组合，负责处理应用程序的数据逻辑。
- **视图（View）**负责渲染模型数据，通常是通过JSP或其他模板引擎生成HTML输出。
- **控制器（Controller）**作为模型和视图之间的中介，处理用户请求，基于请求数据执行业务逻辑，并选择一个视图进行响应。

Spring MVC框架通过一系列注解和约定，简化了Web应用程序的开发。主要特点包括：

- **易于集成**：Spring MVC可以轻松地与Spring框架的其他部分（如Spring Security, Spring Data）集成，提供了一站式的解决方案。
- **灵活的URL映射**：控制器可以通过注解轻松地映射URL到具体的处理方法。
- **强大的数据绑定**：自动将请求参数绑定到业务对象，简化了数据处理。
- **丰富的视图支持**：支持多种视图技术，包括但不限于JSP、Thymeleaf、Freemarker等。
- **异常处理**：提供了一种简单的方式来处理应用程序中的异常。

总的来说，Spring MVC提供了一个强大、灵活的框架，使得开发复杂的Web应用程序变得更简单、更结构化。

# 二、简单使用

## 1.搭建项目结构

> 版本：JDK 8、Tomcat 9.0.93

![image-20240819092211345](./assets/image-20240819092211345.png)

**/WEB-INF/ 目录** 此目录对外界是不可访问的，是保护应用配置和视图模板等文件的理想位置：

- **web.xml**：放在 `/WEB-INF/` 目录的根部。它是 Servlet 规范的一部分，用于配置 Servlet、Filter 和 Listener 等。
- **applicationContext.xml** 和其他 Spring 相关的配置文件（如 `dispatcher-servlet.xml`, `security-config.xml`, `data-access-config.xml` 等）也通常放在 `/WEB-INF/` 目录下，有时候它们会被放在一个专门的子目录中，如 `/WEB-INF/spring/`，以保持更好的组织性。

- **views**：这个目录用于存放视图文件，如 JSP 文件。将视图文件放在 WEB-INF 下可以防止直接访问，确保视图只能通过控制器渲染。



## 2.导入依赖

使用Maven作为项目管理工具。

注意版本兼容性。

```xml
<!-- Spring Core & Beans -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.18</version>
</dependency>
<!-- Spring MVC -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.18</version>
</dependency>
```

## 2.配置文件

### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                             http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

  <!-- Root Context: defines shared resources visible to all other web components -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/spring/applicationContext.xml</param-value>
  </context-param>

  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>

  <!-- Servlets -->
  <servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>/WEB-INF/spring/dispatcher-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
```

### dispatcher-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/mvc
                           http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- Activates various annotations to be detected in bean classes -->
    <context:component-scan base-package="com.lxy.project.controller" />

    <!-- Configures the annotation-driven Spring MVC Controller programming model.
         Note that, with Spring 3.0, this tag works in Servlet MVC only! -->
    <mvc:annotation-driven />

    <!-- Define Spring MVC view resolver -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/" />
        <property name="suffix" value=".jsp" />
    </bean>
</beans>

```

## 3.业务开发

### HomeController

```java
@Controller
public class HomeController {
    @RequestMapping("/home")
    public ModelAndView home() {
        System.out.println("HomeController");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("msg", "Welcome to Spring MVC");
        return modelAndView;
    }
}
```

### home.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
<h1>Spring MVC Home Page</h1>
<!-- 使用 EL 表达式从 ModelAndView 中获取数据 -->
<p>${msg}</p>
</body>
</html>

```

![image-20240819092803608](./assets/image-20240819092803608.png)

# 三、工作原理

## 1.相关组件介绍

> 处理器映射器、处理器适配器、视图解析器称为 SpringMVC 的三大组件

![image-20240315093319263](.\assets\image-20240315093319263.png)



### （2）HandlerMapping
- **作用**：负责==根据请求的URL或其他信息通过多种映射器查找相应的Controller==。
- **实例**：有多种实现，比如`RequestMappingHandlerMapping`用于处理带有`@RequestMapping`注解的方法。

### （3）HandlerAdapter
- **作用**：DispatcherServlet通过它来调用Controller的处理方法。HandlerAdapter负责将请求映射到相应的控制器方法。
- **特点**：它使得Controller的方法签名可以灵活多变，不必遵循单一模式。

### （4）ModelAndView
- **作用**：封装了模型数据和视图信息。控制器处理完用户请求后，会创建并返回这个对象。
- **组成**：Model部分包含了视图渲染所需的数据，View部分表示渲染的视图（可以是视图名，也可以是具体的View对象）。

### （5）ViewResolver
- **作用**：根据Controller返回的视图名称，解析出实际要渲染的View对象。
- **实例**：有多种实现，如`InternalResourceViewResolver`可以将视图名称解析为JSP页面的路径。

Spring MVC 的这些组件是构建 Web 应用程序的核心。下面详细介绍每个组件的功能和作用：

### （1）**DispatcherServlet**

DispatcherServlet 是 Spring MVC 的前端控制器，它负责接收所有进入应用的 HTTP 请求并将它们委派给相应的处理器。它是 MVC 模式中的控制器部分，作为中心调度器，协调其他各个组件的工作。主要职责包括：

1. 请求接收：作为 Servlet 容器（如 Tomcat）中的一个 Servlet，它首先接收所有请求。
2. 请求解析：解析请求，提取 URI 并确定请求的目的地。
3. 处理器映射：调用 HandlerMapping 确定哪个 Controller 应该处理请求。
4. 请求委托：将请求委托给相应的 Controller。
5. 响应返回：处理完请求后，收集响应从 Controller 返回给客户端。

### （2）**HandlerMapping**

HandlerMapping 负责根据请求的 URL 来查找处理该请求的 Controller。在 Spring MVC 配置中，可以定义多个 HandlerMapping，Spring 会按照顺序检查每个 HandlerMapping 直到找到一个匹配的处理器。常见的实现类有：

1. **RequestMappingHandlerMapping**：支持 @RequestMapping 注解的处理器，可以根据 URL、HTTP 方法等多个维度匹配处理器。
2. **SimpleUrlHandlerMapping**：直接通过 URL 映射到特定的处理器。

### （3）**HandlerAdapter**

HandlerAdapter 负责调用由 HandlerMapping 映射得到的具体处理器（Controller）。主要功能包括：

1. 调用适配：将 DispatcherServlet 接收到的请求调用适配到具体的 Controller 方法。
2. 参数绑定：负责将请求参数绑定到处理器方法的入参中。
3. 执行处理：执行处理器方法并处理返回结果。

### （4）**Controller**

Controller 负责具体的业务逻辑处理。在 Spring MVC 中，一个 Controller 可以是一个简单的 POJO，通过 @Controller 注解标记。Controller 类通常包含一个或多个方法，这些方法使用 @RequestMapping 或其他相关注解来映射请求路径和方法。Controller 方法可以直接返回 ModelAndView 对象，或者使用 @ResponseBody 注解返回对象，由消息转换器转换为响应体。

### （5）**ModelAndView**

ModelAndView 是一个容器，它包含模型数据和视图信息，Controller 处理完业务逻辑后，通常会返回 ModelAndView 对象。它主要有两部分组成：

1. Model：包含了视图渲染所需的数据。
2. View：指定渲染哪个视图，或直接指定视图的名称。

### （6）**ViewResolver**

ViewResolver 负责解析 Controller 返回的视图名到具体的 View 实现，负责将模型数据渲染到视图中。常见的 ViewResolver 有：

1. **InternalResourceViewResolver**：解析 JSP 文件路径，可以配置前缀和后缀。
2. **ThymeleafViewResolver**：用于解析 Thymeleaf 模板视图。

通过这些组件的协调工作，Spring MVC 能够有效地处理和响应用户请求，同时保持清晰的程序结构和灵活的配置。

## 2.执行流程

### （1）**请求接收与解析**

当用户发起请求时，所有请求首先被发送到 DispatcherServlet。作为前端控制器，DispatcherServlet 的任务是接收请求并确定请求的类型（如 GET、POST 等），并根据请求的 URL 或其他信息，解析请求以决定后续的操作。

### （2）**处理器映射与调用**

DispatcherServlet 使用 HandlerMapping 来确定哪个 Controller 应该处理该请求。HandlerMapping 根据配置的路由信息查找并返回一个 HandlerExecutionChain 对象，这个对象不仅包含了对应的 Controller，还可能包括多个 HandlerInterceptor（拦截器），用于在 Controller 处理请求前后执行额外的处理。

### （3）**Controller 的执行**

通过 HandlerAdapter，DispatcherServlet 调用 Controller 的方法。HandlerAdapter 负责适配 DispatcherServlet 和 Controller 之间的接口差异，确保 Controller 的执行。这一步骤中，Controller 可能会执行业务逻辑，访问数据库，或调用其他服务，最终生成响应数据。

### （4）**数据模型与视图选择**

Controller 处理完请求后，通常会产生一些数据，这些数据需要返回给用户。Controller 将这些数据放入 Model 中，并选择一个视图（View）进行渲染。这个过程通常通过返回一个 ModelAndView 对象完成，其中 Model 是数据模型，View 是视图的名称或实际的视图对象。

### （5）**视图解析与渲染**

DispatcherServlet 接收到 ModelAndView 后，使用 ViewResolver 来解析 ModelAndView 中的 View 部分。ViewResolver 根据配置将逻辑视图名称解析成具体的 View 实现，如解析成 JSP 文件的路径或其他视图技术的实现。

### （6）**视图渲染**

解析得到的 View 会用 Model 中的数据进行渲染。在这个阶段，视图技术（如 JSP、Thymeleaf 等）会根据 Model 提供的数据生成 HTML 内容。这一步是在服务器端完成的，生成的 HTML 将包含所有动态生成的内容。

### （7）**响应返回**

渲染完成后，生成的 HTML 内容被包装在 HTTP 响应中返回给客户端浏览器。这标志着整个请求处理流程的结束。

通过这个流程，Spring MVC 处理来自用户的请求，并生成响应的页面或其他类型的响应，如 JSON 数据，实现了 Web 应用程序的基本交互功能。这个流程的设计使得开发者可以清晰地分离控制逻辑、业务模型和视图展示，从而提高应用程序的可维护性和扩展性。

## 3.配置相关组件

dispatcher-servlet.xml

```xml
<!--配置处理器映射器-->
    <!-- <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"></bean>-->
    <!--配置处理器适配器-->
    <!-- <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"></bean>-->
    <!--一个注解替换上面的两个配置-->
    <!--<mvc:annotation-driven>会自动注册RequestMappingHandlerMapping与RequestMappingHandlerAdapter两个Bean-->
    <mvc:annotation-driven/>
    <!--配置视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"  ></property>
        <property name="suffix" value=".jsp"  ></property>
    </bean>
```

## 4.静态资源放行

在Spring MVC中，如果不进行特定的静态资源配置，那么==通过Spring的DispatcherServlet处理的请求默认不会直接映射到静态资源==。这是因为DispatcherServlet主要处理来自应用程序的控制器逻辑和数据，而不是直接服务静态资源。不配置静态资源的放行，意味着所有通过DispatcherServlet接收的请求都会尝试被映射到一个控制器上，对于实际指向静态资源的请求，Spring MVC会尝试找到一个匹配的控制器方法处理该请求，如果找不到，就会返回404错误。

在Web应用中，处理静态资源（如图片、CSS和JavaScript文件）是一个常见需求。Spring MVC提供了灵活的方式来处理静态资源，确保这些资源可以被直接请求而不经过Spring的DispatcherServlet，从而提高效率。以下是在Spring MVC中配置静态资源放行的几种常用方法：

### （1）使用`<mvc:resources>`标签（web.xml）



如果您使用XML配置，可以通过`<mvc:resources>`标签映射静态资源路径。这个标签告诉Spring哪些路径下的资源应该直接对外提供服务，而不是被当作控制器请求处理。

```xml
<mvc:resources mapping="/resources/**" location="/public-resources/"/>
```

这里，`mapping`属性定义了资源的请求路径，`location`属性指定了这些资源在应用内的实际位置。您可以根据实际情况修改这些属性的值。

### （2）==使用Java配置==（推荐）

对于基于Java的配置，您可以通过实现`WebMvcConfigurer`接口并重写`addResourceHandlers`方法来配置静态资源。

```java
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")//定义了静态资源的可访问 URL 路径模式
                .addResourceLocations("/public-resources/");//静态资源的存放位置
    }
}
```

这段代码的作用与上面XML配置的例子相同，定义了静态资源的映射路径和位置。

后续学习在Spring Boot中，静态资源一般放在`/static`、`/public`、`/resources`或`/META-INF/resources`目录下。Spring Boot自动配置默认就会将这些目录下的静态资源映射到应用的根路径下，而不需要设置静态资源的放行。

# 四、RESTful

> 强调资源的表现层和无状态的通信

REST（Representational State Transfer）是一种软件架构风格，它定义了一套约束条件和原则，用于设计网络应用程序的接口。REST利用HTTP协议的标准方法（如GET、POST、PUT、DELETE等）来交换数据，使得Web服务更加轻量、高效和易于使用。在RESTful架构中，数据和功能被视为资源，每个资源都有一个唯一的URI（统一资源标识符），并且可以通过这个URI进行访问和操作。

## 1.@RequestMapping、@PathVariable注解

### （1）@RequestMapping注解

`@RequestMapping`注解用于将HTTP请求映射到特定的处理器方法上。它可以声明在类或方法上，定义处理请求的类型（如GET、POST等）、请求的URL路径、请求的参数、请求头等多种维度的映射信息。

#### 基本用法

- **指定请求路径**：通过`value`或`path`属性指定请求的URL路径。
- **指定HTTP方法**：通过`method`属性指定请求的HTTP方法类型，如`RequestMethod.GET`、`RequestMethod.POST`等。
- **指定请求参数**：通过`params`属性指定必须包含（或不包含）的请求参数。
- **指定请求头**：通过`headers`属性指定必须包含（或不包含）的请求头。

```java
@RequestMapping(value = "/users", method = RequestMethod.GET)
public List<User> getUsers() {
    // 处理GET请求，返回用户列表
}
```

### （2）@PathVariable注解

`@PathVariable`注解用于==将URL路径中的变量绑定到控制器处理方法的参数上==。这使得方法能够动态地处理请求URL中的变化部分。

#### 基本用法

- **绑定URL模板变量**：将方法参数与URL中的模板变量绑定。
- **自定义变量名**：如果方法参数名和URL模板变量名不一致，可以通过`@PathVariable("name")`指定URL模板变量的名字。

```java
@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
public User getUser(@PathVariable Long userId) {
    // 根据userId获取并返回用户
}

@RequestMapping(value = "/users/{userId}/orders/{orderId}", method = RequestMethod.GET)
public Order getOrder(@PathVariable("userId") Long user, @PathVariable("orderId") Long order) {
    // 根据userId和orderId获取并返回订单
}
```

## 2.在Spring中使用REST

`@RequestMapping`和`@PathVariable`经常一起使用，以便灵活地处理RESTful风格的URL。它们使得创建根据资源和资源ID进行操作的API变得非常简单。

这种设计强调了==数据（资源）与其表现形式的分离，允许在不改变资源标识的情况下支持多种表现形式==，增强了Web服务的灵活性和可扩展性。

web.xml配置筛选器

```xml
<!--配置hiddenHttpMethodFilter ,将POST请求转换为PUT或者DELETE请求-->
<filter>
  <filter-name>hiddenHttpMethodFilter</filter-name>
  <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>hiddenHttpMethodFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

MyController控制器

```java
@RequestMapping(value = "/myController")
@RestController
public class MyController {
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.PUT)
    public String testPut(@PathVariable(value = "id") Integer id){
        System.out.println("testPut, id:"+id);
        return "success";
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.DELETE)
    public String testDelete(@PathVariable(value = "id") Integer id){
        System.out.println("testDelete, id:"+id);
        return "success";
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.POST)
    public String testPOST(@PathVariable(value = "id") Integer id){
        System.out.println("testPOST, id:"+id);
        return "success";
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.GET)
    public String testGET(@PathVariable(value = "id") Integer id){
        System.out.println("testGET, id:"+id);
        return "success";
    }
}
```

show.html页面

```html
<form action="myController/testRest/10" method="POST">
    <input  type="hidden" name="_method" value="PUT">
    <input type="submit" value="testPUT">
</form>
<br/>
<form action="myController/testRest/10" method="POST">
    <input  type="hidden" name="_method" value="DELETE">
    <input type="submit" value="testDELETE">
</form>
<br/>
<form action="myController/testRest/10" method="POST">
    <input type="submit" value="testPOST">
</form>
<br/>
<form action="myController/testRest/10" method="GET">
    <input type="submit" value="testGET">
</form>
```

# 五、请求处理

## 1.接收引用类型参数

注意

- ​    提交的参数名必须和POJO的属性名保持一致，若不一致使用@RequestParam
- ​    底层通过反射以及set方法给参数列表的属性赋值

```java
public class Person implements Serializable {
    private String pname;
    private String page;
    private String gender;
    private String[] hobby;
    private String birthdate;
}
@RestController
public class ReceiveDataController {
    @RequestMapping("/getDataByPojo")
    public String getDataByPojo(Person p){
        System.out.println(p);
        return "success";
    }
}
```

form表单

```html
<form action="getDataByPojo">
    <p>姓名<input type="text" name="pname"></p>
    <p>年龄<input type="text" name="page"></p>
    <p>性别:
        <input type="radio" name="gender" value="1" >男
        <input type="radio" name="gender" value="0" >女
    </p>
    <p>爱好
        <input type="checkbox" name="hobby" value="1"> 篮球
        <input type="checkbox" name="hobby" value="2"> 足球
        <input type="checkbox" name="hobby" value="3"> 羽毛球
    </p>生日
    <p>
        <input type="text" name="birthdate">
    </p>
    <input type="submit">
</form>
```

## 2.日期类型转换

```java
public class Person implements Serializable {
    private String pname;
    private String page;
    private String gender;
    private String[] hobby;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
}
```

## 3.接收list、map集合

form表单

```html
<form action="getDataByPojo" method="post">
    <p>姓名<input type="text" name="pname"></p>
    <p>年龄<input type="text" name="page"></p>
    <p>性别:
        <input type="radio" name="gender" value="1" >男
        <input type="radio" name="gender" value="0" >女
    </p>
    <p>爱好
        <input type="checkbox" name="hobby" value="1"> 篮球
        <input type="checkbox" name="hobby" value="2"> 足球
        <input type="checkbox" name="hobby" value="3"> 羽毛球
    </p>生日
    <p>
        <input type="text" name="birthdate">
    </p>
    宠物List:
    <p>
        宠物1: 名字:<input type="text" name="pets[0].petName" >类型:<input type="text" name="pets[0].petType">
    </p>
    <p>
        宠物2: 名字:<input type="text" name="pets[1].petName" >类型:<input type="text" name="pets[1].petType">
    </p>
    宠物Map:
    <p>
        宠物1: 名字:<input type="text" name="petMap['a'].petName" >类型:<input type="text" name="petMap['a'].petType">
    </p>
    <p>
        宠物2: 名字:<input type="text" name="petMap['b'].petName" >类型:<input type="text" name="petMap['b'].petType">
    </p>
    <input type="submit">
</form>
```

Peroson

```java
public class Person implements Serializable {
    private String pname;
    private String page;
    private String gender;
    private String[] hobby;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private List<Pet> pets;
    private Map<String,Pet> petMap;
}
```

## 4.请求乱码问题

### （1）GET

Tomcat中server.xml文件加上URIEncoding="utf-8"

```xml
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443"
           maxParameterCount="1000"
		   URIEncoding="utf-8"
           />
```

### （2）POST

在web.xml中添加过滤器

```xml
<!--Spring 中提供的字符编码过滤器-->
<filter>
  <filter-name>encFilter</filter-name>
  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
  <init-param>
    <param-name>encoding</param-name>
    <param-value>utf-8</param-value>
  </init-param>
</filter>
<filter-mapping>
  <filter-name>encFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

若还存在编码问题，在编译器设置中添加-Dfile.encoding=utf-8

![image-20240315112610500](.\assets\image-20240315112610500.png)

# 六、响应处理

在Spring MVC中，响应处理是指控制器（Controller）处理完请求后，如何向客户端返回数据或视图的过程。Spring MVC提供了灵活的方式来处理响应，包括返回视图（View）、返回数据（如JSON或XML）、重定向等。

## 1.forward

在Spring MVC的控制器方法中，你可以通过返回包含`forward:`前缀的字符串来实现请求的转发。这告诉Spring MVC框架==将请求转发到另一个路径而不是返回一个视图名称==。

当使用`forward:`前缀进行请求转发时，==返回的路径不会经过视图解析器处理，而是直接作为请求路径==。这与返回视图名称（用于渲染页面的情况）不同，视图名称会根据配置的视图解析器解析成最终的视图资源路径。

```java
@GetMapping("/original")
public String originalRequest(HttpServletRequest request) {
    // 添加一些逻辑处理
    return "forward:/forwarded";
}

@GetMapping("/forwarded")
public String forwardedRequest() {
    // 处理被转发的请求
    return "forwardedView";
}

```

## 2.redirect

```java
@GetMapping("/redirect")
public String handleRedirect() {
    return "redirect:/greeting";
}

```

## 3.返回视图和数据

在Spring MVC中，返回视图通常是通过返回`ModelAndView`对象或者直接返回视图名称（一个字符串）来实现的。这种方式适用于传统的Web应用，需要服务器渲染页面后返回给客户端。

`ModelAndView`的作用域是==请求级别==的。这意味着它仅在当前的HTTP请求内有效。每次请求都会创建一个新的`ModelAndView`实例，其中的模型数据和视图信息仅对该请求和相应的响应有效。一旦响应被发送给客户端，`ModelAndView`实例的生命周期就结束了。

### （1）返回数据mode

> 以键值对的形式存储

```java
@RequestMapping("setData2")
    public String setData2(Model model){
        List<User> users = userService.findAllUser();
        // 向域中放入数据
        model.addAttribute("message", "reqMessage");
        model.addAttribute("users", users);
        // 跳转至showDataPage
 		// return "forward:/showDataPage.jsp";
        return "redirect:/showDataPage.jsp";
    }
```

### （2）返回视图和数据ModelAndView

```java
@RequestMapping("setData3")
    public ModelAndView setData3(){
        ModelAndView mv=new ModelAndView();
        Map<String, Object> model = mv.getModel();
        // 向request域中放入数据
        List<User> users = userService.findAllUser();
        model.put("message", "reqMessage");
        model.put("users", users);
        // 设置视图
        //mv.setViewName("forward:/showDataPage.jsp");
        mv.setViewName("redirect:/showDataPage.jsp");
        return mv;
    }
```



## 4.返回正文

在Spring MVC中，`@ResponseBody`注解用于将控制器方法的返回值作为HTTP响应正文发送给客户端，而不是解析为视图名称。当你需要构建RESTful Web服务时，这个注解非常有用，特别是在返回JSON或XML等数据格式时。为了使`@ResponseBody`能自动将返回对象转换为JSON格式，通常需要配合`Jackson`库（或其他JSON处理库），==Spring Boot默认就包含了Jackson==。

```java
@Controller
public class UserController {
    @GetMapping("/user")
    @ResponseBody
    public User getUser() {
        return new User(1L, "JohnDoe", "john.doe@example.com");
    }
}
```

**使用`@RestController`简化注解**

为了进一步简化开发，Spring 4.0引入了`@RestController`注解，它是`@Controller`和`@ResponseBody`的结合。当你在控制器类上使用`@RestController`注解时，这个类中的所有方法都默认使用`@ResponseBody`进行注解，因此不需要在每个方法上单独添加`@ResponseBody`。

# 七、SSM整合

示例：开发一个用户登录应用

## 1.搭建环境

### （1）数据库

![image-20240315142949203](.\assets\image-20240315142949203.png)

### （2）项目结构

![image-20240315142847516](.\assets\image-20240315142847516.png)

### （3）导入依赖

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.lxy</groupId>
  <artifactId>springmvc_test_ssm</artifactId>
  <packaging>war</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>springmvc_test_ssm Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <!--spring核心容器包-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.3.5</version>
    </dependency>
    <!--spring切面包-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>5.3.5</version>
    </dependency>
    <!--aop联盟包-->
    <dependency>
      <groupId>aopalliance</groupId>
      <artifactId>aopalliance</artifactId>
      <version>1.0</version>
    </dependency>
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
    <!--spring事务控制包-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>5.3.5</version>
    </dependency>
    <!--spring orm 映射依赖-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-orm</artifactId>
      <version>5.3.5</version>
    </dependency>
    <!--Apache Commons日志包-->
    <dependency>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
      <version>1.2</version>
    </dependency>
    <!--log4j2 日志-->
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-slf4j-impl</artifactId>
      <version>2.14.0</version>
      <scope>test</scope>
    </dependency>
    <!--lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.12</version>
      <scope>provided</scope>
    </dependency>
    <!--spring test测试支持包-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.3.5</version>
      <scope>test</scope>
    </dependency>
    <!--junit5单元测试-->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <version>5.7.0</version>
      <scope>test</scope>
    </dependency>
    <!--springMVC支持包-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>5.3.5</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.3.5</version>
    </dependency>
    <!--jsp 和Servlet  可选-->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>javax.servlet.jsp-api</artifactId>
      <version>2.3.3</version>
      <scope>provided</scope>
    </dependency>
    <!--json依赖-->
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.12.1</version>
    </dependency>
    <!--mybatis 核心jar包-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.3</version>
    </dependency>
    <!--spring mybatis整合包-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>2.0.3</version>
    </dependency>
  </dependencies>
  <build>
    <finalName>springmvc_test_ssm</finalName>
  </build>
</project>
```

## 2.处理配置文件

### （1）jbdc.properties

```properties
jdbc_driver=com.mysql.cj.jdbc.Driver
jdbc_url=jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
jdbc_username=root
jdbc_password=root
```

### （2）log4j2.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="DEBUG">
    <Appenders>
        <Console name="Console" target="SYSTEM_ERR">
            <PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n" />
        </Console>
    </Appenders>
    <Loggers>
        <Root level="DEBUG">
            <AppenderRef ref="Console" />
        </Root>
    </Loggers>
</Configuration>
```

### （3）web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--spring核心配置文件位置-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
    <!--spring Listener-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!--编码过滤器-->
    <filter>
        <filter-name>encFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--DispatcherServlet-->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

### （4）applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xmlns:util="http://www.springframework.org/schema/util"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util
       http://www.springframework.org/schema/util/spring-util.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd
">
    <!--加载外部属性文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!--扫描service层-->
    <context:component-scan base-package="com.lxy.service"/>
    <!--配置德鲁伊数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="${jdbc_username}"/>
        <property name="password" value="${jdbc_password}"/>
        <property name="url" value="${jdbc_url}"/>
        <property name="driverClassName" value="${jdbc_driver}"/>
    </bean>
    <!--配置sqlSessionFactory-->
    <bean id="factory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource"  ref="dataSource"/>
        <property name="typeAliasesPackage" value="com.lxy.pojo"/>
    </bean>
    <!--配置MapperScanner 扫描mapper.xml 和接口-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--配置SQLSessionFactory-->
        <property name="sqlSessionFactoryBeanName" value="factory"/>
        <!--扫描mapper-->
        <property name="basePackage" value="com.lxy.mapper"/>
    </bean>
    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--开启事务注解-->
    <tx:annotation-driven  transaction-manager="transactionManager"/>
</beans>
```

### （5）dispatcher-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xmlns:util="http://www.springframework.org/schema/util"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util
       http://www.springframework.org/schema/util/spring-util.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd
">
    <!--扫描controller-->
    <context:component-scan base-package="com.lxy.controller"/>
    <!--这里配置三大组件-->
    <mvc:annotation-driven />
    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    </bean>
    <!--配置静态资源放行-->
    <!--<mvc:resources mapping="/js/**" location="/js/"></mvc:resources>-->
    <!--<mvc:resources mapping="/css/**" location="/css/"></mvc:resources>-->
    <!--<mvc:resources mapping="/img/**" location="/img/"></mvc:resources>-->
    <!--<mvc:resources mapping="/static/**" location="/static/"></mvc:resources>-->
</beans>
```

## 3.编写业务

### （1）pojo

User

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User implements Serializable {
    private int uid;
    private String uname;
    private String password;
}
```

### （2）前端页面资源

index.jsp登录页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="login" method="post">
    <input type="text" name="uname">
    <input type="password" name="password">
    <input type="submit" value="登录">
</form>
</body>
</html>
```

success登录成功页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
登录成功
</body>
</html>
```

fail.jsp登录失败页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
登录失败
</body>
</html>
```

### （3）Controraller

```java
@Controller
public class UserController {
    @Autowired
    UserService service;
    @RequestMapping("/login")
    public String login(String uname,String password){
        User user=service.findUser(uname,password);
        if(user!=null){
            return "/success.jsp";
        }
        else{
            return "fail.jsp";
        }
    }
}
```

### （4）Service

UserService

```java
public interface UserService {
    User findUser(String uname, String password);
}
```

UserServiceImpl

```java
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User findUser(String uname, String password) {
        return userMapper.findUser(uname,password);
    }
}
```

### （5）Mapper

UserMapper

```java
@Mapper
public interface UserMapper {
    User findUser(String uname, String password);
}
```

UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lxy.mapper.UserMapper">
    <!--User findUser(String uname, String password);-->
    <select id="findUser" resultType="user">
        select *from user where uname=#{param1} and password=#{param2}
    </select>
</mapper>
```

# 八、文件资源控制

## 1.文件的上传

### （1）依赖和配置

```xml
<!--文件上传依赖-->
    <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.4</version>
    </dependency>
    <dependency>
      <groupId>commons-io</groupId>
      <artifactId>commons-io</artifactId>
      <version>2.8.0</version>
    </dependency>
```

```xml
<!--文件上传解析组件id必须为multipartResolverspringmvc默认使用该id找该组件-->
    <bean id="multipartResolver"                                                                  class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>
```



### （2）前端页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <style>
    .progress {
      width: 200px;
      height: 10px;
      border: 1px solid #ccc;
      border-radius: 10px;
      margin: 10px 0px;
      overflow: hidden;
    }
    /* 初始状态设置进度条宽度为0px */
    .progress > div {
      width: 0px;
      height: 100%;
      background-color: yellowgreen;
      transition: all .3s ease;
    }
  </style>
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript">
    $(function(){
      $("#uploadFile").click(function(){
        // 获取要上传的文件
        var photoFile =$("#photo")[0].files[0]
        if(photoFile==undefined){
          alert("您还未选中文件")
          return;
        }
        // 将文件装入FormData对象
        var formData =new FormData();
        formData.append("headPhoto",photoFile)
        // ajax向后台发送文件
        $.ajax({
          type:"post",
          data:formData,
          url:"fileUpload.do",
          processData:false,
          contentType:false,
          success:function(result){
            // 接收后台响应的信息
            alert(result.message)
            // 图片回显
            $("#headImg").attr("src","upload/"+result.newFileName);
          },
          xhr: function() {
            var xhr = new XMLHttpRequest();
            //使用XMLHttpRequest.upload监听上传过程，注册progress事件，打印回调函数中的event事件
            xhr.upload.addEventListener('progress', function (e) {
              //loaded代表上传了多少
              //total代表总数为多少
              var progressRate = (e.loaded / e.total) * 100 + '%';
              //通过设置进度条的宽度达到效果
              $('.progress > div').css('width', progressRate);
            })
            return xhr;
          }
        })
      })
    })
  </script>
</head>
<body>
<form action="addPlayer" method="get">
  <p>账号<input type="text" name="name"></p>
  <p>密码<input type="text" name="password"></p>
  <p>昵称<input type="text" name="nickname"></p>
  <p>头像:
    <br/>
    <input id="photo" type="file">
    <%--图片回显--%>
    <br/>
    <img id="headImg" style="width: 200px;height: 200px" alt="你还未上传图片">
    <br/>
    <%--进度条--%>
  <div class="progress">
    <div></div>
  </div>
  <a id="uploadFile" href="javascript:void(0)">立即上传</a>
  </p>
  <p><input type="submit" value="注册"></p>
</form>
</body>
</html>
```

### （2）Controller

```java
@Controller
public class FileUploadController {
    @ResponseBody
    @RequestMapping("fileUpload.do")
    public Map<String,String> fileUpload(MultipartFile headPhoto, HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<>();
        // 控制文件大小
        if(headPhoto.getSize()>1024*1024*5){
            map.put("message", "文件大小不能超过5M");
            return map;
        }
        // 指定文件存储目录为我们项目部署环境下的upload目录
        String realPath = req.getServletContext().getRealPath("/upload");
        File dir = new File(realPath);
        // 如果不存在则创建目录
        if(!dir.exists()){
            dir.mkdirs();
        }
        // 获取文件名
        String originalFilename = headPhoto.getOriginalFilename();
        // 避免文件名冲突,使用UUID替换文件名
        String uuid = UUID.randomUUID().toString();
        // 获取拓展名
        String extendsName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //  控制文件类型
        if(!extendsName.equals(".png")){
            map.put("message", "文件类型必须是.png");
            return map;
        }
        // 新的文件名
        String newFileName=uuid.concat(extendsName);
        // 文件存储位置
        File file =new File(dir,newFileName);
        //  文件保存
        headPhoto.transferTo(file);
        // 上传成功之后,把文件的名字和文件的类型返回给浏览器
        map.put("message", "上传成功");
        map.put("newFileName", newFileName);
        map.put("filetype", headPhoto.getContentType());
        return map;
    }
}
```

这段代码是一个Spring MVC控制器，用于处理文件上传的逻辑。这个`FileUploadController`包含一个名为`fileUpload`的方法，这个方法接收一个类型为`MultipartFile`的参数`headPhoto`，这代表着上传的文件。方法使用了`@ResponseBody`注解，意味着返回的`Map<String, String>`将会被自动转换成JSON格式作为HTTP响应正文返回给客户端。

1. **检查文件大小**：首先检查上传的文件大小是否超过5MB，如果超过，则返回一个包含错误信息的Map。

2. **确定文件存储位置**：接下来，代码确定了文件应该被存储在服务器上的位置。它使用`HttpServletRequest`对象获取服务器上名为`/upload`的目录的实际路径。如果这个目录不存在，代码会创建这个目录。

3. **生成新的文件名**：为了避免文件名冲突，代码使用UUID生成一个新的文件名，并保留原始文件的扩展名（在这个例子中，代码限制了只能上传`.png`格式的文件）。

4. **保存文件**：使用`MultipartFile`的`transferTo`方法，文件被保存到之前确定的位置。

5. **返回响应**：最后，方法返回一个包含消息、新文件名和文件类型的Map。这个Map会被Spring MVC转换成JSON格式，作为HTTP响应返回给客户端。

## 2.分服务器上传

文件分服务器上传通常涉及到将文件从用户的浏览器上传到一个中心服务器，然后再从这个中心服务器上传到最终的存储服务器或多个存储节点。这种方法在处理大规模分布式系统中的文件存储时非常有用，特别是在需要提高可靠性、可用性或者分散地理位置风险时。

### （1）单独解压一个Tomcat作为文件服务器

### （2）导入依赖

```xml
<dependency>
  <groupId>com.sun.jersey</groupId>
  <artifactId>jersey-client</artifactId>
  <version>1.19</version>
</dependency>
```

### （3）前端页面

保存完整player信息进入数据库

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .progress {
            width: 200px;
            height: 10px;
            border: 1px solid #ccc;
            border-radius: 10px;
            margin: 10px 0px;
            overflow: hidden;
        }
        /* 初始状态设置进度条宽度为0px */
        .progress > div {
            width: 0px;
            height: 100%;
            background-color: yellowgreen;
            transition: all .3s ease;
        }
    </style>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#uploadFile").click(function(){
                // 获取要上传的文件
                var photoFile =$("#photo")[0].files[0]
                if(photoFile==undefined){
                    alert("您还未选中文件")
                    return;
                }
                // 将文件装入FormData对象
                var formData =new FormData();
                formData.append("headPhoto",photoFile)
                // ajax向后台发送文件
                $.ajax({
                    type:"post",
                    data:formData,
                    url:"fileUpload.do",
                    processData:false,
                    contentType:false,
                    success:function(result){
                        // 接收后台响应的信息
                        alert(result.message)
                        // 图片回显
                        $("#headImg").attr("src","http://192.168.8.109:8090/upload/"+result.newFileName);
                        // 将文件类型和文件名放入form表单
                        $("#photoInput").val(result.newFileName)
                        $("#filetypeInput").val(result.filetype)
                    },
                    xhr: function() {
                        var xhr = new XMLHttpRequest();
                        //使用XMLHttpRequest.upload监听上传过程，注册progress事件，打印回调函数中的event事件
                        xhr.upload.addEventListener('progress', function (e) {
                            //loaded代表上传了多少
                            //total代表总数为多少
                            var progressRate = (e.loaded / e.total) * 100 + '%';
                            //通过设置进度条的宽度达到效果
                            $('.progress > div').css('width', progressRate);
                        })
                        return xhr;
                    }
                })
            })
        })
    </script>
</head>
<body>
    <form action="addPlayer" method="get">
        <p>账号<input type="text" name="name"></p>
        <p>密码<input type="text" name="password"></p>
        <p>昵称<input type="text" name="nickname"></p>
        <p>头像:
            <br/>
            <input id="photo" type="file">
            <%--图片回显--%>
            <br/>
            <img id="headImg" style="width: 200px;height: 200px" alt="你还未上传图片">
            <br/>
           <%--进度条--%>
            <div class="progress">
                <div></div>
            </div>
            <a id="uploadFile" href="javascript:void(0)">立即上传</a>
            <%--使用隐藏的输入框存储文件名称和文件类型--%>
            <input id="photoInput"  type="hidden" name="photo" >
            <input id="filetypeInput"  type="hidden" name="filetype">
        </p>
        <p><input type="submit" value="注册"></p>
    </form>
</body>
</html>
```

showPlaer.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        #playerTable{
            width: 50%;
            border: 3px solid cadetblue;
            margin: 0px auto;
            text-align: center;
        }
        #playerTable th,td{
            border: 1px solid gray;
        }
        #playerTable img{
            width: 100px;
            height: 100px;
        }
    </style>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script>
        $(function(){
            $.ajax({
                type:"get",
                url:"getAllPlayer",
                success:function(players){
                    $.each(players,function(i,e){
                        $("#playerTable").append('<tr>\n' +
                            '        <td>'+e.id+'</td>\n' +
                            '        <td>'+e.name+'</td>\n' +
                            '        <td>'+e.password+'</td>\n' +
                            '        <td>'+e.nickname+'</td>\n' +
                            '        <td>\n' +
                            '            <img src="http://192.168.8.109:8090/upload/'+e.photo+'" alt="" src>\n' +
                            '        </td>\n' +
                            '        <td>\n' +
                            '            <a href="">下载</a>\n' +
                            '        </td>\n' +
                            '    </tr>')
                    })
                }
            })
        })
    </script>
</head>
<body>
<table id="playerTable" cellspacing="0xp" cellpadding="0px">
    <tr>
        <th>编号</th>
        <th>用户名</th>
        <th>密码</th>
        <th>昵称</th>
        <th>头像</th>
        <th>操作</th>
    </tr>
</table>
</body>
</html>
```



### （3）Controller

异步上传文件

```java
@Controller
public class FileUploadController {
    // 文件存储位置
    private final static String FILESERVER="http://192.168.8.109:8090/upload/";
    @ResponseBody
    @RequestMapping("fileUpload.do")
    public Map<String,String> fileUpload(MultipartFile headPhoto, HttpServletRequest req) throws IOException {
        Map<String,String> map=new HashMap<>();
        // 获取文件名
        String originalFilename = headPhoto.getOriginalFilename();
        // 避免文件名冲突,使用UUID替换文件名
        String uuid = UUID.randomUUID().toString();
        // 获取拓展名
        String extendsName = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新的文件名
        String newFileName=uuid.concat(extendsName);
        // 创建 sun公司提供的jersey包中的client对象
        Client client=Client.create();
        WebResource resource = client.resource(FILESERVER + newFileName);
        //  文件保存到另一个服务器上去了
        resource.put(String.class, headPhoto.getBytes());
        // 上传成功之后,把文件的名字和文件的类型返回给浏览器
        map.put("message", "上传成功");
        map.put("newFileName",newFileName);
        map.put("filetype", headPhoto.getContentType());
        return map;
    }
}
```

PlayerController

```java
@Controller
public class PlayerController  {
    @Autowired
    private PlayerService playerService;
    @RequestMapping("addPlayer")
    public String addPlayer(Player player){
        // 调用服务层方法,将数据保存进入数据库
        playerService.addPlayer(player);
        // 页面跳转至player信息展示页
        return "redirect:/showPlayer.jsp";
    }
    @RequestMapping("getAllPlayer")
    @ResponseBody
    public List<Player> getAllPlayer(){
        return playerService.getAllPlayer();
    }
}
```

### （4）model

Service

```java
@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerMapper playerMapper;
    @Override
    public boolean addPlayer(Player player) {
        return playerMapper.addPlayer(player)>0;
    }
}

```

Mapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.msb.lxy.PlayerMapper">
    <insert id="addPlayer">
        insert into player values(DEFAULT ,#{name},#{password},#{nickname},#{photo},#{filetype})
    </insert>
</mapper>
```

## 3.文件的下载

### （1）前端页面

```jsp

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        #playerTable{
            width: 50%;
            border: 3px solid cadetblue;
            margin: 0px auto;
            text-align: center;
        }
        #playerTable th,td{
            border: 1px solid gray;
        }
        #playerTable img{
            width: 100px;
            height: 100px;
        }
    </style>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script>
        $(function(){
            $.ajax({
                type:"get",
                url:"getAllPlayer",
                success:function(players){
                    $.each(players,function(i,e){
                        $("#playerTable").append('<tr>\n' +
                            '        <td>'+e.id+'</td>\n' +
                            '        <td>'+e.name+'</td>\n' +
                            '        <td>'+e.password+'</td>\n' +
                            '        <td>'+e.nickname+'</td>\n' +
                            '        <td>\n' +
                            '            <img src="http://192.168.8.109:8090/upload/'+e.photo+'" alt="" src>\n' +
                            '        </td>\n' +
                            '        <td>\n' +
                            '            <a href="fileDownload.do?photo='+e.photo+'&filetype='+e.filetype+'">下载</a>\n' +
                            '        </td>\n' +
                            '    </tr>')
                    })
                }
            })
        })
    </script>
</head>
<body>
<table id="playerTable" cellspacing="0xp" cellpadding="0px">
    <tr>
        <th>编号</th>
        <th>用户名</th>
        <th>密码</th>
        <th>昵称</th>
        <th>头像</th>
        <th>操作</th>
    </tr>
</table>
</body>
</html>
```

### （2）Controller

```java
@RequestMapping("fileDownload.do")
    public void fileDownLoad(String photo, String filetype, HttpServletResponse response) throws IOException {
        // 设置响应头
        // 告诉浏览器要将数据保存到磁盘上,不在浏览器上直接解析
        response.setHeader("Content-Disposition", "attachment;filename="+photo);
        // 告诉浏览下载的文件类型
        response.setContentType(filetype);
        // 获取一个文件的输入流
        InputStream inputStream = new URL(FILESERVER + photo).openStream();
        // 获取一个指向浏览器的输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 向浏览器响应文件即可
        IOUtils.copy(inputStream, outputStream);
    }
```

# 九、拦截器

Spring MVC的拦截器（Interceptor）用于在请求的处理过程中的特定点进行拦截，然后执行一些预处理和后处理操作。这对于实现跨切面的逻辑非常有用，例如日志记录、权限检查、事务处理等。拦截器可以应用于特定的URL路径模式，使得它们非常灵活和强大。

![image-20240315163025762](.\assets\image-20240315163025762.png)

## 1.拦截器的工作流程

1. **预处理（preHandle）**：==在控制器方法（Handler）执行之前调用==。可以进行编码、安全控制等处理。如果返回`true`，则继续向下执行；如果返回`false`，则终止请求，不再调用后续的拦截器和控制器方法。

2. **后处理（postHandle）**：==在控制器方法执行之后，但在视图被渲染之前调用==。可以对模型数据进行处理或对视图进行处理。

3. **完成处理（afterCompletion）**：在整个请求结束之后，即==视图渲染完毕时调用==。可以用来进行资源清理等操作。

示例：

springmvc.xml中配置拦截器

```xml
<!--注册拦截器-->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/login.action"/>
            <bean id="myInterceptor" class="com.msb.interceptor.MyInterceptor"></bean>
        </mvc:interceptor>
    </mvc:interceptors>
```



```java
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*在请求到达我们定义的handler之前工作的*/
        System.out.println("MyInterceptor preHandle");
        /*返回的是true,代表放行,可以继续到达handler*/
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor postHandle");
        /*handler 处理单元返回ModelAndView 时候进行 拦截*/
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*
        页面渲染完毕,但是还没有给浏览器响应数据的时候
         */
        System.out.println("MyInterceptor afterCompletion");
    }
}
```

# 十、异常处理

在Spring MVC中，异常处理可以通过几种方式实现，以确保应用程序能够优雅地处理运行时错误，并向用户提供有用的反馈。以下是Spring MVC中处理异常的一些常用方法：

## 1. @ExceptionHandler

`@ExceptionHandler`注解用于在控制器层处理特定异常。它可以应用于单个控制器内的方法，用于捕获该控制器抛出的异常，并进行处理。

```java
@Controller
public class MyController {

    @ExceptionHandler(Exception.class)//也可以具体一些比如value ={ArithmeticException.class,NullPointerException.class}
    public ModelAndView handleError(Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMessage());
        mav.setViewName("errorPage");
        return mav;
    }
}
```

## 2. @ControllerAdvice

`@ControllerAdvice`注解允许你在==整个Spring MVC应用程序中全局处理异常==。它可以与`@ExceptionHandler`结合使用，以处理多个控制器中的异常。

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

## 3. HandlerExceptionResolver

Spring MVC提供了`HandlerExceptionResolver`接口，允许你==自定义异常处理逻辑==。通过实现这个接口，你可以自定义如何解析异常，并返回相应的模型和视图。

```java
public class MyExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", ex.getMessage());
        mav.setViewName("errorPage");
        return mav;
    }
}
```

## 5. SimpleMappingExceptionResolver

`SimpleMappingExceptionResolver`提供了一种通过属性文件配置异常处理规则的方法。你可以映射特定的异常到不同的错误页面。

```xml
<!--自定义异常解析器-->
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
    <property name="exceptionMappings">
        <props>
            <prop key="java.lang.Exception">errorPage</prop>
        </props>
    </property>
</bean>
```

通过这些方法，Spring MVC允许开发者灵活地处理应用中的异常，确保即使发生错误，用户也能获得清晰和友好的反馈信息。

# 十一、其他注解

在Spring MVC中，有几个关键的注解用于构建RESTful Web服务，处理HTTP请求和响应，格式化数据，以及处理跨域请求。下面是对你提到的四个注解的简要说明：

## 1. @GetMapping

`@GetMapping`是一个组合注解，是`@RequestMapping(method = RequestMethod.GET)`的缩写。它用于将HTTP GET请求映射到特定的处理方法上。

```java
@Controller
public class MyController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
```

## 2. @PostMapping

`@PostMapping`是`@RequestMapping(method = RequestMethod.POST)`的缩写。它用于将HTTP POST请求映射到特定的处理方法上，常用于处理表单提交。

```java
@Controller
public class MyController {

    @PostMapping("/submitForm")
    public String submitForm(@ModelAttribute User user, Model model) {
        // 处理提交的表单数据
        return "resultPage";
    }
}
```

## 3. @JsonFormat

`@JsonFormat`是Jackson库提供的注解，用于控制日期时间格式的序列化和反序列化。这个注解可以用在字段级别，指定日期时间字段在JSON中的格式。

```java
public class User {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birthDate;
    
    // getters and setters
}
```

## 4. @CrossOrigin

`@CrossOrigin`注解用于==处理跨域资源共享（CORS）问题==。它可以应用于整个控制器或单个处理方法上，允许来自不同源的Web客户端请求资源。

```java
@RestController
@RequestMapping("/api")
public class MyRestController {

    @CrossOrigin(origins = "http://example.com")
    @GetMapping("/data")
    public ResponseEntity<Data> getData() {
        // 返回一些数据
    }
}
```

或者，在全局配置中处理跨域请求：

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://example.com");
    }
}
```

这些注解在构建基于Spring MVC的Web应用时非常有用，它们简化了请求处理、数据格式化和跨域请求的处理。
