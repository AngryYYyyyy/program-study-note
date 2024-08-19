# 一、Servlet简介

Servlet 是一种运行在服务器端的 Java 程序，主要==用于处理客户端请求并生成动态 Web 页面==。它是 Java EE 规范的一部分，通常与 JSP一起使用，以提供动态 Web 内容。在MVC模式中,Servlet作为Controller层(控制层)主要技术，通过使用 Servlet，开发者可以控制 Web 应用的行为，处理复杂的业务逻辑，并与数据库等后端服务进行交互。

## 1.特点

- **生命周期管理**：Servlet 由容器（如 Tomcat）管理，包括其生命周期的各个阶段：加载、初始化、处理请求、终止。
- **请求处理**：Servlet 可以接收 HTTP 请求，处理业务逻辑，并生成响应。
- **多线程支持**：Servlet 容器可以为每个请求创建一个新的线程，使得 Servlet 能够同时处理多个请求。
- **配置灵活**：Servlet 可以通过注解或在 web.xml 文件中进行配置。

## 2.简单使用

以下为主要步骤：

1. 继承HttpServlet类，重写service方法
2. 在web.xml中配置映射路径

示例：编写一个简单的servlet程序

```java
public class Test01 extends HttpServlet {//继承HttpServlet
    //重写service
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>hello world<h1>");//向浏览器打印信息
    }
}
```

配置web.xml文件，将Test01映射到一个url路径

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--配置映射路径-->
    <servlet>
        <servlet-name>test01</servlet-name><!--别名-->
        <servlet-class>com.lxy.servlet01.Test01</servlet-class><!--路径-->
    </servlet>
    <servlet-mapping>
        <servlet-name>test01</servlet-name>
        <url-pattern>/test01.do</url-pattern><!--映射url-->
    </servlet-mapping>
</web-app>
```

请求资源



![image-20231201113018225](.\assets\image-20231201113018225.png)

## 3.登录页开发案例

以下为主要步骤：

1. 编写后台处理登录逻辑。
2. 制作前端登录页面。
3. 配置信息。

示例：

业务逻辑：从请求中获取信息账户，简单逻辑判断，打印结果

```java
public class Login extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account=req.getParameter("account");//根据name属性获取value
        String password = req.getParameter("password");
        resp.setCharacterEncoding("UTF-8");//设置响应编码格式
        resp.setContentType("text/html;charset=UTF-8");//让浏览器知道，内容类型以及编码格式
        if("405005800".equals(account)&&"123456".equals(password)){
            resp.getWriter().write("<h1>登录成功</h1>");//打印信息
        }else{
            resp.getWriter().write("<h1>登陆失败</h1>");
        }
    }
}
```

使用form表单，制作简单的前端页面，能够将数据传入后端

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form method="get" action="login.do"><!--采用get方式，发送到login.do目标-->
        <table>
            <tr>
                <td>account</td>
                <td><input type="text" name="account"></td>
            </tr>
            <tr>
                <td>password</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="提交"></td>
            </tr>
        </table>
    </form>
</body>
</html>
```

web.xml配置，需要将欢迎页设置为login.html

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>login</servlet-name>
        <servlet-class>com.lxy.servlet01.Login</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>login</servlet-name>
        <url-pattern>/login.do</url-pattern>
    </servlet-mapping>
    <!--设置欢迎页，即默认初始页-->
    <welcome-file-list>
        <welcome-file>login.html</welcome-file>
    </welcome-file-list>
</web-app>
```

测试

![image-20231201121303759](.\assets\image-20231201121303759.png)

![image-20231201121219982](.\assets\image-20231201121219982.png)

# 二、请求与响应

## 1.HttpServletRequest

在Java Servlet API中，`HttpServletRequest` 对象用于获取有关HTTP请求的信息。以下是它提供的一些主要方法：

### （1）请求行信息获取方法
- `getMethod()`: 返回请求的HTTP方法（如GET、POST）。
- `getRequestURI()`: 返回请求的URI。
- `getProtocol()`: 返回请求使用的协议（例如HTTP/1.1）。
- `getQueryString()`: 返回请求的查询字符串。

### （2）请求头信息获取方法
- `getHeader(String name)`: 返回具有给定名称的请求头的值。
- `getHeaderNames()`: 返回所有请求头名称的枚举。

### （3）请求体数据获取方法
- `getParameter(String name)`: 返回请求参数的值。
- `getParameterValues(String name)`: 返回同一参数名称的所有值（例如，多选框）。
- `getParameterNames()`: 返回所有请求参数的名称。
- `getParameterMap()`: 返回请求参数的一个 `Map` 集合，其中键为参数名称，值为参数值（一个或多个）。
- `getReader()`: 获取用于读取请求体的字符流。

### （4）设置请求字符编码
- `setCharacterEncoding(String env)`: 设置请求体使用的字符编码。

测试

```java
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求编码格式
        req.setCharacterEncoding("UTF-8");
        //请求行
        System.out.println(req.getMethod());
        System.out.println(req.getRequestURL());
        System.out.println(req.getRequestURI());
        System.out.println(req.getProtocol());
        System.out.println(req.getQueryString());
       //请求头
       System.out.println(req.getHeader("Connection"));
        Enumeration<String> headerNames = req.getHeaderNames();//迭代器
        while(headerNames.hasMoreElements()){
            String headerName=headerNames.nextElement();
            System.out.println(headerName+":"+req.getHeader(headerName));
        }
        //数据
        System.out.println(req.getParameter("user"));
        System.out.println(req.getParameter("sentence"));
        String[] hobbies = req.getParameterValues("hobby");
        for(String e:hobbies){
            System.out.println(e);
        }
        Enumeration<String> parameterNames = req.getParameterNames();
        while(parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println(s + ":" + req.getParameter(s));
        }
        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for(Map.Entry<String, String[]> e:entries){
            System.out.println(e.getKey()+":"+ Arrays.toString(e.getValue()));
        }
    }
}
```

## 2.HttpServletResponse

`HttpServletResponse` 是 Java Servlet API 中用于构造 HTTP 响应的类。以下是它的一些主要功能和方法：

### （1）**设置响应状态码**

- `setStatus(int sc)`: 设置响应的状态码。例如，`200` 表示成功，`404` 表示未找到。

### （2）**设置响应头**

- `setHeader(String name, String value)`: 设置一个响应头的值。
- `addHeader(String name, String value)`: 添加一个响应头，而不覆盖现有的头部。

### （3）**设置响应内容类型和编码**

- `setContentType(String type)`: 设置响应的内容类型，如 `text/html`。
- `setCharacterEncoding(String charset)`: 设置响应的字符编码，如 `UTF-8`。

### （4）**写入响应内容**

- `getWriter()`: 获取 `PrintWriter` 对象以发送字符文本到客户端。
- `getOutputStream()`: 获取 `ServletOutputStream` 用于发送二进制数据到客户端。

测试

```java
public class Test02 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应内容类型和编码
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        //设置响应码
        resp.setStatus(200);
        //设置响应头
        resp.setHeader("Date","2023-10-1");
        resp.addHeader("name", "zhangsan");
        resp.getWriter().println("<h1>你好</h1>");
    }
}
```

## 3.中文乱码

### （1）控制台乱码

修改Tomcat目录下conf的logging.properties文件。将编码格式设置为与控制台相同的。

![image-20231201194025594](.\assets\image-20231201194025594.png)

### （2）请求乱码

#### 1）GET方法

Tomcat9.0.83版本会自动转换编码格式，与idea相同。

也可以手动设置，在server.xml文件中，添加URIEncoding=“utf-8”

![image-20231201194424549](.\assets\image-20231201194424549.png)

#### 2）POST方法

Tomcat由于历史原因，默认使用 ISO-8859-1 编码解析请求，当浏览器以utf-8的编码格式传递请求时，被Tomcat错误解码。

需要先将解码后的错误数据再次以ISO-8859-1的编码格式进行编码，得到以 utf-8格式的字节流信息，再用utf-8的编码格式进行解码。

```java
String user = new String(req.getParameter("user").getBytes("iso-8859-1"), "utf-8");
```

当然也可以设置请求编码格式。

```java
req.setCharacterEncoding("utf-8");
```

以上两种方法都可以解决请求中含有中文乱码的情况。

### （3）响应乱码

分别需要设置Tomcat封装数据的编码格式和浏览器解析数据的编码格式。

```java
//以UTF-8编码处理数据
resp.setContentType("UTF-8");
//设置响应头,以便浏览器知道以何种编码解析数据
resp.setContentType("text/html;charset=UTF-8");
```

# 三、Servlet详解

## 1.继承和实现结构

![image-20231203094141604](.\assets\image-20231203094141604.png)

![image-20231203094710869](.\assets\image-20231203094710869.png)

## 2.各个结构的核心方法

### （1） `Servlet` 接口

`Servlet` 接口定义了 Servlet 生命周期的几个核心方法：

- `init(ServletConfig config)`: 用于初始化 Servlet。
- `service(ServletRequest req, ServletResponse res)`: 核心方法，用于处理客户端请求。
- `destroy()`: 当 Servlet 结束生命周期时调用，用于释放资源。
- `getServletConfig()`: 返回此 Servlet 的配置对象。ServletConfig是容器向servlet传递参数的载体。
- `getServletInfo()`: 返回关于 Servlet 的信息，如作者、版本等。

### （2） `GenericServlet` 类

`GenericServlet` 是一个抽象类，它实现了 `Servlet` 接口，并提供了 `Servlet` 接口方法的基本实现，除了 `service` 方法。`GenericServlet` 主要为非 HTTP 请求提供服务，其设计的目的是为了和应用层协议解耦。

我们也可以通过继承GenericServlet并实现Service方法实现请求的处理，但是需要将ServletReuqest 和 ServletResponse 转为 HttpServletRequest 和 HttpServletResponse。

- `service(ServletRequest req, ServletResponse res)`: 抽象方法，必须由子类实现，用于处理请求。

### （3）`HttpServlet` 类

`HttpServlet` 类继承自 `GenericServlet` 类，专门用于处理 HTTP 请求。它为 HTTP 协议的每种请求方法提供了相应的方法。

- `doGet(HttpServletRequest req, HttpServletResponse res)`: 处理 GET 请求。
- `doPost(HttpServletRequest req, HttpServletResponse res)`: 处理 POST 请求。
- `doPut(HttpServletRequest req, HttpServletResponse res)`: 处理 PUT 请求。
- `doDelete(HttpServletRequest req, HttpServletResponse res)`: 处理 DELETE 请求。
- `doHead(HttpServletRequest req, HttpServletResponse res)`: 处理 HEAD 请求。
- `doOptions(HttpServletRequest req, HttpServletResponse res)`: 处理 OPTIONS 请求。
- `doTrace(HttpServletRequest req, HttpServletResponse res)`: 处理 TRACE 请求。

在我们自定义的Servlet中，如果想区分请求方式，不同的请求方式使用不同的代码处理,那么我么重写 doGet 、doPost等即可，
如果我们没有必要区分请求方式的差异，那么我们直接重写service方法即可。

### （4）`ServletConfig` 与`ServletContext`

#### 1）`ServletCotext`

![image-20231203105710986](.\assets\image-20231203105710986.png)

`ServletContext` 代表了整个 web 应用的运行环境。一个 web 应用只有一个 `ServletContext` 实例，它是由 Servlet 容器创建的。用于在同一个 web 应用的不同部分间共享数据。

常用方法：

- 获取ServletContext：this.getServletContext()。

- 获取`web.xml`信息：getInitParameter(String name)、getInitParameterNames()。

```xml
<context-param>
    <param-name>key</param-name>
    <param-value>value</param-value>
</context-param>
```

- 全局容器：getAttribute(String name)、setAttribute(String name, Object object)。

示例：

```java
@WebServlet(urlPatterns = "/03test02.do")
public class Test02 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取
        ServletContext servletContext = req.getServletContext();
        //获取配置信息
        System.out.println(servletContext.getInitParameter("key"));
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String s = initParameterNames.nextElement();
            System.out.println(servletContext.getInitParameter(s));
        }

        //定义全局容器
        servletContext.setAttribute("key3", "value3");
        servletContext.setAttribute("key4", "value4");
        Enumeration<String> attributeNames = servletContext.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String s = attributeNames.nextElement();
            System.out.println(servletContext.getAttribute(s));
        }
    }
}
```

```java
@WebServlet(urlPatterns = "/03test03.do")
public class Test03 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取
        ServletContext servletContext = req.getServletContext();
        //获取配置信息
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String s = initParameterNames.nextElement();
            System.out.println(servletContext.getInitParameter(s));
        }

        //全局容器
        Enumeration<String> attributeNames = servletContext.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String s = attributeNames.nextElement();
            System.out.println(servletContext.getAttribute(s));
        }
    }
}
```

#### 2）`ServletConfig` 

`ServletConfig` 为单个 Servlet 提供配置信息。每个 Servlet 都有它自己的 `ServletConfig` 实例，由 Servlet 容器提供。

常用方法：

- 获取ServletContext：this.getServletConfig()。

- 获取web.xml配置信息：getInitParameter(String name)、getInitParameterNames()。

```xml
<servlet>
        <servlet-name>02test02</servlet-name>
        <servlet-class>com.lxy.servlet02.Test02</servlet-class>
        <init-param>
            <param-name>key</param-name>
            <param-value>value</param-value>
        </init-param>
    </servlet>
```



## 3.生命周期

Servlet 的生命周期是由 Servlet 容器（如 Apache Tomcat）控制的，它定义了 Servlet 从创建到销毁的整个过程。

### （1）加载和实例化
当 Servlet 容器启动或首次访问 Servlet 时，它会加载 Servlet 类，并且 Servlet 容器创建 Servlet 实例。这一步通常发生在首次请求到达之前，但具体时机可能因配置和容器实现而异。

注意：如果需要Servlet在服务启动时就实例化并初始化，在 `web.xml` 中的 `<servlet>` 元素内，可以为Servlet 指定一个 `load-on-startup` 元素，用来指示 Servlet 加载的顺序。

```xml
<servlet>
    <servlet-name>MyServlet</servlet-name>
    <servlet-class>com.example.MyServlet</servlet-class>
    <load-on-startup>1</load-on-startup><!-- 避免冲突且应>6-->
</servlet>
```



### （2）初始化
在 Servlet 实例化之后，容器调用 `init` 方法进行初始化。这个方法接收一个 `ServletConfig` 对象，其中包含了 Servlet 的初始化参数和对 Servlet 上下文的引用。`init` 方法通常用于执行如打开数据库连接、读取配置文件等只需执行一次的初始化操作。

### （3） 请求处理 
每次 Servlet 接收到请求时，容器调用 `service` 方法。这个方法分析请求类型（如 GET 或 POST）并将其转发到相应的处理方法（如 `doGet` 或 `doPost`）。开发者通常会根据需要重写这些方法来实现自己的业务逻辑。

### （4）销毁
 当 Servlet 容器关闭或应用程序被卸载时，容器会调用每个 Servlet 的 `destroy` 方法。这是 Servlet 生命周期的最后一个方法调用。 `destroy` 方法通常用于执行清理工作，如释放数据库连接、关闭文件句柄等。

整个生命周期中，`init` 和 `destroy` 方法各被调用一次，而 `service` 方法可能被多次调用，每次处理一个请求。这种设计允许 Servlet 在多个请求之间共享资源，如数据库连接，同时确保这些资源在 Servlet 生命周期结束时得以适当释放。

由于 Servlet 容器通常会为每个请求创建一个新的线程来执行 `service` 方法，这意味着同一个 Servlet 实例可能会同时被多个线程访问。如果 Servlet 中有共享数据（如成员变量），则可能会出现线程安全问题。

## 4.Servlet请求处理的详细过程

![image-20231203104853269](.\assets\image-20231203104853269.png)

- #### 浏览器发起 GET 请求
  用户通过浏览器向服务器发送一个基于 GET 方法的 HTTP 请求。

- #### 请求到达 Servlet 容器
  请求首先到达 Servlet 容器（如 Tomcat），容器解析 HTTP 请求头和其他信息。

- #### 解析请求和 `web.xml` 配置
  Servlet 容器解析请求的 URI，并根据 `web.xml` 中的配置确定请求对应的 Servlet。

  `web.xml` 中定义了 Servlet 类和 URL 模式之间的映射。

- #### Servlet 实例并初始化
  容器检查是否已经存在该 Servlet 的实例。如果不存在，它会创建一个新的实例。Servlet 实例通常只被创建一次，并在后续请求中重用。

  对于新创建的 Servlet 实例，容器会调用其 `init` 方法进行初始化。`HttpServlet` 类中的 `init` 方法通常是空的，但可以被子类覆盖以执行初始化任务。

- #### 调用 `service` 方法
  容器为每个请求在新线程中调用 Servlet 的 `service` 方法。`HttpServlet` 类重写了 `service` 方法，以便根据 HTTP 请求类型（GET、POST 等）调用相应的处理方法。

  `service` 方法通过检查 HTTP 请求的类型来决定调用 `doGet` 或 `doPost` 方法。对于 GET 请求，`service` 方法会调用 `doGet` 方法。

  #### 响应返回给客户端

- 方法执行完毕后，其生成的响应被发送回客户端。

## 5.url-pattern

### （1）匹配规则

#### 1） 精确匹配
精确匹配意味着 URL 必须完全匹配才能由对应的 Servlet 处理。
- 例如：`<url-pattern>/exactMatch</url-pattern>`
- 只有精确匹配 `/exactMatch` 的请求会被对应的 Servlet 处理。

#### 2）目录匹配
使用斜杠（`/`）后跟一个星号（`*`）表示目录匹配。
- 例如：`<url-pattern>/directory/*</url-pattern>`
- 这将匹配所有以 `/directory/` 开头的 URL。例如，`/directory/test` 和 `/directory/subdirectory/item` 都将匹配。

#### 3）扩展名匹配
以星号（`*`）开始，后跟一个点（`.`）和扩展名，表示扩展名匹配。
- 例如：`<url-pattern>*.ext</url-pattern>`
- 这将匹配所有以 `.ext` 结尾的 URL。例如，`/path/file.ext` 将匹配，但 `/path/file.jsp` 或 `/path.ext/file` 不会匹配。

#### 4）默认匹配（或 "捕获所有" 匹配）
只有一个斜杠（`/`）表示默认匹配，也称为 "捕获所有" 匹配。但不能匹配jsp文件，还需要加*。
- 例如：`<url-pattern>/</url-pattern>`
- 这将匹配所有不符合其他映射规则的 URL。它通常用于处理静态资源，如图片、CSS 文件和 JavaScript 文件。

### （2）映射方式

在 `web.xml` 文件中，可以配置多个 URL 模式（`url-pattern`）映射到同一个 Servlet，但是相同的 URL 不能被映射到两个不同的 Servlet。这是因为 Servlet 映射必须是唯一的，以确保当一个特定的 URL 被请求时，Servlet 容器能够明确地知道应该调用哪个 Servlet 来处理该请求。

# 四、注解模式开发Servlet

基于注解（Annotation）的 Servlet 开发是从 Servlet 3.0 开始引入的一种新方式，它允许开发者通过使用特定的注解来配置 Servlet，而无需修改 `web.xml` 文件。这种方法提高了开发效率，并使代码更易于阅读和维护。

## 1.常用注解

- **@WebServlet**：用于声明一个类为 Servlet。
- **@WebInitParam**：与 `@WebServlet` 结合使用，用于指定初始化参数。
- **@WebListener**用于定义监听器（Listener）。监听器用于监听应用程序的生命周期事件，如 ServletContext 的创建和销毁。
- **@WebFilter**用于声明过滤器（Filter）。可以定义 `urlPatterns`、`servletNames` 等属性，指定过滤器应用的 URL 模式或 Servlet。

## 2.@WebServlet常用属性

### （1）name
- **类型**: `String`
- **描述**: 指定 Servlet 的名称。这个名称在应用中应该是唯一的。

### （2）urlPatterns 或 value
- **类型**: `String[]`
- **描述**: 定义 Servlet 的 URL 匹配模式。可以是一个字符串数组，用于指定一个或多个 URL 模式。`urlPatterns` 和 `value` 是等价的，可以交替使用。

### （3）loadOnStartup
- **类型**: `int`
- **描述**: 指定 Servlet 的加载顺序。如果大于或等于 0，表示 Servlet 容器在启动时应该加载并初始化这个 Servlet。数字越小，优先级越高。

### （4）initParams
- **类型**: `WebInitParam[]`
- **描述**: 用于定义 Servlet 的初始化参数。每个 `WebInitParam` 注解包括一个参数名称 (`name`) 和一个参数值 (`value`)。

### 示例

下面是一个使用了多个 `@WebServlet` 属性的示例：

```java
@WebServlet(
    name = "ExampleServlet", 
    urlPatterns = {"/example", "/exampleServlet"}, //注意前面要加/
    loadOnStartup = 1,
    initParams = {
        @WebInitParam(name = "param1", value = "value1"),
        @WebInitParam(name = "param2", value = "value2")
    },
)
public class ExampleServlet extends HttpServlet {
    // Servlet implementation
}
```

# 五、请求与转发和响应重定向

## 1.请求与转发

请求转发是在服务器内部进行的，用于将请求从一个 Servlet 转发到另一个 Servlet 或其他资源。 通常用于在服务器内部的 Servlet 或 JSP 之间传递控制，适用于需要共享请求数据的情况。

### （1）特点

- **客户端透明**：对于客户端（如浏览器）来说，它看起来像是一个单一的请求，因为 URL 不会发生变化。
- **共享同一个请求和响应对象**：原始请求和响应对象被传递给目标资源，这意味着可以共享数据。
- **性能**：由于转发是在服务器内部进行的，它通常比重定向更快。
- **只能转发到同一个应用中的资源**：无法将请求转发到不同的域或应用，同时请求转发可以转发至WEB-INF。

### （2）forward

![image-20231203115341341](.\assets\image-20231203115341341.png)

#### 1）处理流程

- 清空Response存放响应正文数据的缓冲区。
- 如果目标资源为Servlet或JSP，就调用它们的service()方法，把该方法产生的响应结果发送到客户端；如果目标资源文件系统中的静态HTML文档，就读取文档中的数据把它发送到客户端。

#### 2）处理特点

- 源Servlet生成的响应结果不会被发送到客户端，只有目标资源生成的响应结果才会被发送到客户端。
- 如果源Servlet在进行请求转发之前，已经提交了响应，那么forward()方法抛出IllegalStateException。为了避免该异常，不应该在源Servlet中提交响应结果。

示例：

```java
@WebServlet(urlPatterns = "/04test01.do")
public class Test01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取转发器
        //不可以请求外部资源，"https://www.baidu.com"属于项目外部资源
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("https://www.baidu.com");
        //请求内部动态资源,并且可以共享请求中的数据
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/04test02.do");

        //请求内部静态资源,路径问题后续学习
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.html");
        //转发前后的响应行为，均不会发送到客户端
        //resp.getWriter().write("this is web");
        //forward方式，转发后直接向客户端做出响应，不会回到src servlet
        requestDispatcher.forward(req,resp);

        //不会发送到客户端
        //resp.getWriter().write("this is web");
    }
}
```

```java
@WebServlet(urlPatterns = "/04test02.do")
public class Test02 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        resp.getWriter().write("<h1>this is forword web</h1>");
        resp.getWriter().write("<h1>desc servlet revice name:"+name+" age:"+age+"</h1>");
    }
}
```

### （3）include

![image-20231203115639829](.\assets\image-20231203115639829.png)

#### 1）处理流程

- 如果目标资源为Servlet或JSP，就调用他们的相应的service()方法，把该方法产生的响应正文添加到源Servlet的响应结果中；如果目标组件为HTML文档，就直接把文档的内容添加到源Servlet的响应结果中。
- 返回到源Servlet的服务方法中，继续执行后续代码块。

#### 2）处理特点

- 源Servlet与被包含的目标资源的输出数据都会被添加到响应结果中。
- 在目标资源中对响应状态码或者响应头所做的修改都会被忽略。

示例：

```java
@WebServlet(urlPatterns = "/04test03.do")
public class Test03 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取转发器
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/04test02.do");
        //include方式请求转发,做出响应后会返回到该src Servlet
        requestDispatcher.include(req,resp);
        //src servlet做出响应，会与des拼接响应内容，一同返回到客户端
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        resp.getWriter().write("<h1>src servlet revice name:"+name+" age:"+age+"</h1>");

    }
}
```

## 2.响应重定向

响应重定向是通过在响应中设置一个特殊的状态码和头信息来实现的，告诉客户端（如浏览器）去请求一个新的 URL。适用于将用户导航到外部资源或在客户端完成状态变更后导航到新页面（如表单提交后重定向到确认页面），以防止重复提交。

![image-20231203122947986](.\assets\image-20231203122947986.png)

### （1）特点

- **客户端行为**：重定向会导致客户端（浏览器）发起一个新的 HTTP 请求到指定的 URL，因此 URL 会发生变化。
- **不共享请求和响应对象**：由于是新的请求，原始的请求和响应对象不再可用，不可共享请求的资源。
- **可以重定向到任何 URL**：可以将用户重定向到同一个应用的不同部分，或者完全不同的网站，不存在只能访问内部资源的限定。
- **更高的网络延迟**：因为需要额外的客户端请求。

### （2）处理流程

- 用户在浏览器端输入特定URL，请求访问服务器端的某个Servlet。
- 服务器端的Servlet返回一个状态码为302的响应结果，该响应结果的含义为：让浏览器端再请求访问另一个Web资源，在响应结果中提供了另一个Web资源的URL。另一个Web资源有可能在同一个Web服务器上，也有可能不再同一个Web服务器上。

- 当浏览器端接收到这种响应结果后，再立即自动请求访问另一个Web资源。

- 浏览器端接收到另一个Web资源的响应结果。

示例：

```java
@WebServlet(urlPatterns = "/05test01.do")
public class Test01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //可以重定向任意资源，但不能共享资源，并且修改url
        //resp.sendRedirect("https://www.baidu.com");
        //访问内部资源时要注意路径问题，路径问题后续学习总结，目前提示+/说明是相对路径，不加是绝对路径
        resp.sendRedirect("/ServletDemo01_war_exploded/login.html");
        //resp.sendRedirect("http://localhost:8080/ServletDemo01_war_exploded/login.html");
    }
}
```

## 3.路径问题

### （1）前端路径

这些是在浏览器中使用的路径，例如在 HTML 文件中链接资源（如 CSS、JavaScript 文件）或在 JavaScript 中发送 AJAX 请求时使用的路径。

- **相对路径**：相对于当前页面的 URL，url前没有/开头。
- **绝对路径**：从服务器的根目录开始的完整路径，以/开头，绝对路径后需要写当前项目部署名。

示例：

项目结构如下：

![image-20231203141113211](.\assets\image-20231203141113211.png)

测试点击代码test.html

```html

    <!--..代表返回上一目录，从当前路径（a1）出发，到目标路径-->
    <a href="../../b/b1/test02.html">test02</a>
    <a href="../../b/b1/test03.html">test03</a>
</body>
</html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--可以设置基准路径，默认为当前路径-->
    <!--默认路径: <base href="http://127.0.0.1:8080/ServletDemo01_war_exploded/a/a1/">-->
    <!--修改基准路径,注意最后要加/结尾-->
    <base href="http://localhost:8080/ServletDemo01_war_exploded/">
</head>
<body>
    <h1>this is test</h1>
    <!-- test01.html与test.html位于同一文件夹a1中-->
    <a href="test01.html">test01</a>
    <!--..代表返回上一目录，从当前路径（a1）出发，到目标路径-->
    <a href="../../b/b1/test02.html">test02</a>
    <a href="../../b/b1/test03.html">test03</a>
    <!--修改基准路径后,即相对基准路径-->
    <a href="a/a1/test01.html">test01</a>
    <a href="b/b1/test02.html">test02</a>
    <a href="b/b1/test03.html">test03</a>
</body>
</html>
```

### （2）请求转发路径

- **路径的上下文**：在使用 `RequestDispatcher` 进行请求转发时，路径通常是相对于 Web 应用的上下文根目录。
- **相对基准路径：**相对于当前Servlet本身的位置,urlPattern决定了基准路径。
- **绝对基准路径：**以项目为基准路径(不允许跨服务,所以绝对路径只能是本服务内的资源)，绝对路径后不需要写当前项目部署名。

示例

```java
@WebServlet(urlPatterns = "/a/a1/06test01.do")
public class Test01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //相对路径，基于urlPatterns
        //urlPatterns = "/06test01.do"时,相对路径的基准为Web根目录
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("a/a1/test.html");
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("a/a1/test01.html");
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("b/b1/test02.html");
        //urlPatterns = "/a/a1/06test01.do"时，相对路径的基准为/a/a1/
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("test.html");
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("test01.html");
        //绝对路径,以项目为根目录
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/a/a1/test.html");
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("/a/a1/test01.html");
        requestDispatcher.forward(req,resp);
    }
}
```

### （3） 重定向路径

- **绝对路径**：如果访问外部资源，需要完整的 URL 。例如，`http://www.example.com/path/page.html`。

如果访问内部资源，绝对路径以当前项目所在目录为跟路径，绝对路径后需要写当前项目部署名。

- **相对路径**：相对于当前请求的 URL 或服务器根目录，跟urlPatterns有关。

```java
@WebServlet(urlPatterns = "/a/a1/06test02.do")
public class Test02 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //相对路径，基于urlPatterns
        //urlPatterns = "/06test02.do"时,相对路径的基准为Web根目录
       //resp.sendRedirect("a/a1/test.html");
       //resp.sendRedirect("a/a1/test01.html");
        //urlPatterns = "/a/a1/06test02.do"时，相对路径的基准为/a/a1/
        //resp.sendRedirect("test.html");
        //resp.sendRedirect("test01.html");
        //绝对路径,以项目为根目录
        //resp.sendRedirect("/ServletDemo01_war_exploded/a/a1/test.html");
        resp.sendRedirect("/ServletDemo01_war_exploded/a/a1/test01.html");
    }
}
```

# 六、会话管理

由于HTTP协议是一个无状态的协议，所以服务端并不会记录当前客户端浏览器的访问状态，但是在有些时候需要服务端能够记录客户端浏览器的访问状态的，如获取当前客户端浏览器的访问服务端的次数时就需要会话状态的维持。

在Web开发中，`Cookie` 和 `HttpSession` 是用于在服务器和客户端之间保持状态的两种不同的机制。

## 1.Cookie

Cookie是一种保存少量信息至浏览器的一种机制，第一次请求时，服务器可以响应给浏览器一些Cookie信息，第二次请求，浏览器会携带之前的Cookie发送给服务器，通过这种机制可以实现在浏览器端保留一些用户信息，为服务端获取用户状态获得依据。

### （1）特点

- **客户端存储**：Cookie是小型的文本数据，存储在客户端的浏览器中。
- **有限的存储容量**：每个Cookie的大小一般限制在4KB左右。
- **持久性**：可以设置Cookie的过期时间，使其在长时间内有效，即使浏览器关闭后仍然存在。
- **安全性问题**：由于存储在客户端，可能被篡改或用于跟踪用户行为。可以通过设置为仅在HTTPS连接下发送和访问Cookie来提高安全性。
- **跨域限制**：Cookie通常只能被创建它的同源（同一域名）的服务器访问。

### （2）常用方法

示例1：向浏览器响应Cookie

```java
@WebServlet("/test01")
public class Test01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建Cookie
        Cookie cookie = new Cookie("key", "value");
        //设置持久化时间
        cookie.setMaxAge(60);
        //添加至响应
        resp.addCookie(cookie);
    }
}
```

示例2：读取Cookie

```java
@WebServlet("/test02.do")
public class Test02 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName()+":"+cookie.getValue());
            }
        }
    }
}
```

示例3：记录登录状态（次数）

```java
@WebServlet("/test03.do")
public class Test03 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean flag=true;
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        Cookie[] cookies = req.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if("boot".equals(cookie.getName())){
                    int num=Integer.parseInt(cookie.getValue())+1;
                    Cookie boot = new Cookie("boot", String.valueOf(num));
                    resp.addCookie(boot);
                    flag=false;
                    resp.getWriter().write("您已登录"+num+"次");
                }
            }
        }
        if(flag){
            Cookie boot = new Cookie("boot", "1");
            resp.addCookie(boot);
            resp.getWriter().write("这是第一次登录");
        }
    }
}
```

## 2.HttpSession

HttpSession是在服务器端创建的，用于跟踪用户的状态，通常只在一次用户会话中有效，当用户关闭浏览器或者会话超时后，Session会被销毁。

### （1）特点

- **服务器端存储**：HttpSession是在服务器端创建的，用于存储与每个用户相关的信息。
- **生命周期**：Session在用户打开浏览器、与服务器交互到关闭浏览器或会话超时时保持有效。
- **安全性较高**：由于存储在服务器端，对于敏感数据更加安全。
- **资源消耗**：每个用户的Session都会占用服务器资源，因此在高并发的应用中需要谨慎使用。
- **无大小限制**：与Cookie的大小限制不同，Session可以存储较大的数据。
- **依赖于Cookie**：Session通常依赖于Cookie中的唯一标识（如JSESSIONID）来识别用户。

### （2）常用方法

示例1：存入数据

```java
@WebServlet("/02test01.do")
public class Test01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取HttpSession对象，如果没有则创建
        HttpSession session = req.getSession();
        session.setAttribute("name", "zhangsan");
        session.setAttribute("age", 10);
        session.setAttribute("sex", "male");
    }
}
```

示例2：读取数据



## 3.`Cookie` 和 `HttpSession` 的使用过程
