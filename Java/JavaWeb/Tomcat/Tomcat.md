# 一、JavaEE简介

> JavaEE平台规范了在开发企业级web应用中的技术标准

JavaEE（Java Platform, Enterprise Edition）是一种广泛使用的服务器端平台，用于开发和运行企业级应用程序和网络服务。

## 1.技术栈

> 主要学习的核心技能Servlet、MVC模式

## 2.JavaEE应用服务器

- JavaEE应用服务器是一种服务器软件，它提供了一个环境，==用于部署和运行企业级Java应用程序==。
- 应用服务器提供多种服务，如事务管理、安全性、并发处理、负载均衡和集群等，以支持高性能和高可靠性的企业应用。
- 常见的JavaEE应用服务器包括Oracle的WebLogic Server、IBM的WebSphere Application Server和Red Hat的JBoss EAP。

## 3.Web容器

- Web容器，也称为Servlet容器，==管理着Web组件的生命周期==，如Servlet和JSP（JavaServer Pages）。
- 它负责处理来自Web客户端（如浏览器）的请求，并将这些请求映射到相应的Servlet或JSP页面进行处理。处理完成后，Web容器还负责将响应返回给客户端。
- Web容器也提供了一系列服务，如会话管理、资源池管理和安全性管理。
- Apache Tomcat是最知名的Web容器之一，虽然它不是完整的JavaEE应用服务器，但它是执行Servlet和JSP规范的理想环境。

# 二、Tomcat

## 1.简介

Apache Tomcat是一个开源的Web容器，它实现了Java Servlet、JavaServer Pages、Java Expression Language和Java WebSocket技术的规范。Tomcat由Apache软件基金会管理，并且是Java EE规范的一个主要参考实现。它主要用于托管Java应用程序，并且广泛应用于小到中型系统和互联网应用程序。

主要特点和功能包括：

1. **Servlet和JSP支持**：Tomcat提供了对Servlet和JSP的全面支持，使得它成为开发和部署基于Java的Web应用程序的理想选择。

2. **轻量级和灵活**：与其他完整的Java EE应用服务器（如WebLogic或WebSphere）相比，Tomcat更加轻量级，部署简单，对资源的要求相对较低。这使得Tomcat非常适合中小型应用和快速开发环境。

3. **可扩展性**：Tomcat的架构允许它适应各种应用需求，可以通过添加额外组件来扩展其功能。

4. **社区支持**：作为一个开源项目，Tomcat有一个活跃的开发者和用户社区，为用户提供帮助、指南和改进。

5. **安全性**：Tomcat提供了多种安全特性，包括对SSL/TLS的支持，以及与Java安全管理器的集成，以确保Web应用程序的安全。

6. **管理和配置工具**：Tomcat附带了一系列管理和配置工具，包括一个Web管理界面，用于简化应用程序的部署和服务器的配置。

7. **高性能**：Tomcat优化了处理请求的方式，提供了良好的性能，特别是在处理静态内容和小规模动态请求时。

Tomcat广泛用于为不同类型的Web应用程序提供动力，从小型个人项目到大型企业系统。虽然它不是一个完整的Java EE服务器（它不支持EJB等全套Java EE规范），但在许多情况下，Tomcat的功能足以满足开发者的需求。

## 2.安装与使用

### （1）下载

官网：[Apache Tomcat® - Welcome!](https://tomcat.apache.org/)

![image-20231130120440213](.\assets\image-20231130120440213.png)

注意：Tomcat启动需要Java的运行环境，在下载时，需要查询Tomcat各版本的Java运行环境版本。

下面以9.0.83版本作为使用，需要至少Java8的运行环境

### （2）启动

运行bin目录下的startup.bat文件。

在浏览器url输入localhost:8080查询是否启动成功。

![image-20231130120838886](.\assets\image-20231130120838886.png)

注意：

启动前需要设置相关的环境变量，JAVA_HOME和CATALINA_HOME

### （3）退出

运行bin目录下的shutdown.bat文件，或者直接关闭命令台窗口。

## 3.目录结构

Apache Tomcat 9.0.83（或类似版本）下载后的目录结构是标准的Tomcat布局。

![image-20231130121203808](.\assets\image-20231130121203808.png)

1. **bin** - 包含用于启动和停止Tomcat服务器的脚本。对于Windows系统，这些是.bat文件，而对于Unix/Linux系统，这些是.sh文件。

2. **conf** - 包含Tomcat服务器的配置文件。最重要的是`server.xml`（主要的Tomcat配置文件）、`web.xml`（全局Web应用设置）和`context.xml`（上下文配置）。

3. **lib** - 包含Tomcat运行所需的所有库文件，包括Java Servlet API和Tomcat的核心类。

4. **logs** - 存储Tomcat的日志文件。这些日志提供了关于服务器操作和错误的信息，对于故障排除是非常有用的。

5. **webapps** - 默认的应用程序部署目录。这里是你放置你的WAR文件或Web应用程序目录的地方。

6. **work** - Tomcat用于存放JSP编译后的Servlet以及其他工作文件的地方。通常，用户不需要直接访问这个目录。

7. **temp** - 临时文件的存储地。Tomcat运行时使用的一些临时文件存放在此目录。

8. **LICENSE**、**NOTICE**、**RELEASE-NOTES**、**RUNNING.txt** - 这些文件包括了Tomcat的许可证信息、发行说明和基本的运行指南。

## 4.部署项目

以下是部署Web应用程序到Apache Tomcat 9.0.83的基本步骤：

1. **复制应用程序到Tomcat**：
   - 将WAR文件复制到Tomcat的`webapps`目录中。Tomcat会自动解压WAR文件并部署应用程序。
   - 或者，你可以直接将应用程序目录复制到`webapps`目录中。目录名称将成为应用程序的上下文路径。
2. **启动Tomcat服务器**：
   - 在Tomcat的`bin`目录中运行启动脚本。在Windows上是`startup.bat`，在Unix/Linux上是`startup.sh`。
   - 确认Tomcat已成功启动，通常可以通过访问`http://localhost:8080`来检查。
3. **访问你的应用程序**：
   - 在浏览器中输入URL来访问你的应用程序。URL通常是`http://localhost:8080/yourAppName`，其中`yourAppName`是你的WAR文件名（不包括`.war`扩展名）或你的应用程序目录的名称。

## 5.主要组件

在 Apache Tomcat 中，`Server`、`Service`、`Connector`、`Engine`、`Host` 和 `Context` 是关键组件，它们共同构成了 Tomcat 的核心架构。

![image-20231130145956150](.\assets\image-20231130145956150.png)

1. **Server**：
   - `Server` 是 Tomcat 实例的顶级元素。它代表整个 Tomcat 服务器，是所有其他组件的容器。一个 Tomcat 实例只有一个 `Server` 组件。
   - 它负责管理所有的 `Service` 组件，并且可以包含全局配置设置，如JVM设置和全局资源。

2. **Service**：
   - `Service` 代表一组功能，它是一个或多个 `Connector` 组件和单个 `Engine` 组件的容器。
   - 每个 `Service` 可以处理一组特定的请求（例如，处理不同类型的网络协议）。一个 `Server` 可以包含多个 `Service`。

3. **Connector**：
   - `Connector` 组件负责接收来自客户端的请求，并将其传递给相应的 `Engine` 进行处理。
   - Tomcat 可以配置多个 `Connector`，每个 `Connector` 可以支持不同的协议（如HTTP/1.1、HTTP/2、AJP）。

4. **Engine**：
   - `Engine` 是 Tomcat 中的请求处理中心，处理通过 `Connector` 接收的请求。
   - 每个 `Service` 只有一个 `Engine`，并且它负责管理多个 `Host`（虚拟主机）。

5. **Host**：
   - `Host` 代表一个虚拟主机，即一个网络域名。一个 `Engine` 可以包含多个 `Host`。
   - 每个 `Host` 可以有多个部署的 Web 应用程序，它们由 `Context` 组件表示。

6. **Context**：
   - `Context` 代表一个单独的 Web 应用程序。它是部署在 Tomcat 上的每个应用程序的配置和资源的容器。
   - 每个 `Context` 都有其自己的 Servlet 上下文，可以包含特定的配置（如数据源、安全约束等）。

这些组件按照层次结构组织，形成了 Tomcat 的整体架构。在 `server.xml` 配置文件中，可以看到这些组件的层次和关系，这是 Tomcat 配置和管理的核心。

## 6.Tomcat处理请求步骤

下面以访问localhost:8080/test/index.jsp为例

1. **请求接收**：请求首先被配置为监听端口 8080 并处理 HTTP/1.1 协议的 `Connector` 接收。
2. **请求传递至 Engine**：`Connector` 将请求传递给其所属 `Service` 的 `Engine` 以进行处理。
3. **Engine 和 Host 的请求处理**：
   - `Engine` 接收请求并在其配置的 `Host` 实例中进行匹配。
   - 名为 `localhost` 的 `Host`（或默认 `Host`，如果没有匹配项）被选中来处理请求。
4. **Context 匹配和处理请求**：
   - 选定的 `Host` 根据请求的 URI `/test` 匹配适当的 `Context`。
   - 匹配到的 `Context` 获得 `/index.jsp` 请求。
5. **Servlet 执行和响应构建**：
   - 在 `Context` 内，`HttpServletRequest` 和 `HttpServletResponse` 对象被创建并传递给 `JspServlet`。
   - `JspServlet` 执行请求的业务逻辑并通过 `HttpServletResponse` 构建响应。
6. **响应的返回**：
   - 处理后的响应通过 `Context`、`Host`、`Engine` 回到 `Connector`。
   - `Connector` 最终将响应发送回用户的浏览器。

# 三、HTTP协议

## 1.HTTP协议

> 简单的请求-响应协议

HTTP（超文本传输协议）是用于传输超媒体文档（如HTML）的应用层协议。它是构建Web上所有数据交换的基础，并且是互联网上最广泛使用的协议之一。

### （1）特点

**客户端-服务器模型**：

HTTP遵循客户端-服务器模型，客户端（通常是Web浏览器）发起请求，服务器响应请求。客户端和服务器通过请求和响应消息进行通信。

**无状态协议**：

HTTP是一个无状态协议，这意味着服务器不保持任何有关客户端请求的状态信息。每个请求都是独立的，服务器不会记住之前的交互。这简化了交互，但也限制了应用程序的功能，通常通过Cookies、Session等机制来解决。

**可扩展性**：

HTTP允许传输任何类型的数据，只要双方知道如何处理这些数据。这使得它可以用于传输HTML文档、图片、视频等多种类型的媒体。类型由Content-Type加以标记。

**安全性**：

HTTP的简单性、可靠性和可扩展性使它成为了互联网上最重要的协议之一，支撑着全球绝大多数网站和网络服务。

**无连接**：

HTTP是一个典型的无连接协议。每次HTTP请求后，一旦响应被发送，服务器就会关闭与客户端的连接。这样做有助于节省资源，因为服务器不需要为每个客户端保持连接状态。然而，它也带来了一些限制，例如增加了通信的重复性和冗余，因为每次传输都需要重新提供完整的地址和路由信息。

### （2）版本

HTTP（超文本传输协议）经历了多个版本的发展，每个版本都在性能、安全性和功能上带来了改进。

1. **HTTP/1.0**：
   - 引入了方法（GET、POST、HEAD）、状态码、HTTP头等概念。
   - 每次请求完成后连接即关闭，不支持持久连接。
2. **HTTP/1.1**：
   - 引入了持久连接（默认连接保持开启状态，减少了建立和关闭连接的频率）。
   - 支持流水线处理（pipelining）、分块传输编码、更多的缓存控制选项、Host头（允许多个域名托管在同一IP地址上）。
   - 是目前最广泛使用的版本。
3. **HTTP/2**：
   - 引入了多路复用（同一连接上并行发送多个请求/响应，减少了延迟）。
   - 支持首部压缩（减少了协议开销）和服务器推送。

每个版本的HTTP都旨在解决先前版本的限制，提高Web通信的效率和安全性。随着Web技术的不断发展，HTTP协议也在不断进化以满足新的需求和挑战。

## 2.HTTP请求

HTTP请求是客户端（通常是Web浏览器或Web应用程序）发送到服务器的消息，以请求对资源的访问。

![image-20231130154346523](.\assets\image-20231130154346523.png)

1. **请求行**：
   
   - 请求方法：指示要执行的操作。
   - 请求的URI（统一资源标识符）：指定所请求资源的位置。
   - HTTP版本：比如 HTTP/1.1 或 HTTP/2。
   
2. **请求头**：
   
   包含关于请求的元数据，如客户端希望接收的响应类型（通过`Accept`字段）、客户端的类型（通过`User-Agent`字段）、对资源的缓存管理（`Cache-Control`）、认证信息等。

4. **请求体**（POST）：
   
   包含发送到服务器的数据。并非所有请求都有请求体，例如 `GET` 请求通常没有，而 `POST` 请求通常包含要提交给服务器的数据。请求体的格式和数据类型可以通过请求头中的 `Content-Type` 字段指定。

示例

一个HTTP `GET` 请求的例子，请求 `http://localhost:8080/myProject/test.html` 页面：

![image-20231130153141115](.\assets\image-20231130153141115.png)

HTTP请求的结构和内容取决于请求的类型和目的，它是客户端与服务器之间通信的基础。

## 3.HTTP响应

HTTP响应是服务器对客户端HTTP请求的回应。它由几个部分组成，告知客户端其请求的结果以及返回的相关数据。HTTP响应的结构包括：

1. **状态行**：
   - 包括HTTP版本，如HTTP/1.1。
   - 状态码。
   - 状态文本：状态码的简短描述。
   
2. **响应头**：
   
   提供了关于服务器和响应本身的信息，如`Content-Type`（响应内容的类型）、`Content-Length`（响应内容的长度）、`Server`（服务器信息）、`Set-Cookie`（用于设置Cookies）等。

4. **响应体**：
   
   包含服务器返回的数据。这部分数据的内容和格式取决于请求和服务器的具体实现。对于一个Web页面请求，响应体通常是HTML文档。对于API请求，响应体可能是JSON或XML格式的数据。

### 示例

一个HTTP响应的例子，响应一个对 `http://localhost:8080/myProject/test.html` 的 `GET` 请求：

![image-20231130155622081](.\assets\image-20231130155622081.png)

HTTP响应的结构和内容根据请求的类型和服务器的响应而有所不同，它是Web通信的关键组成部分。

# 四、JavaWeb开发项目开发

## 1.创建idea开发项目

### （1）添加JavaEE框架

选中项目（模块），ctrl+shift+a，选择添加框架支持。

![image-20231201092138421](.\assets\image-20231201092138421.png)

勾选JavaEE Application和Web Application。

![image-20231201092220895](.\assets\image-20231201092220895.png)

### （2）项目结构

![image-20231201092348630](.\assets\image-20231201092348630.png)

![image-20231201092509701](.\assets\image-20231201092509701.png)

## 2.配置Tomcat

### （1）添加Tomcat应用服务器

![image-20231201102937142](.\assets\image-20231201102937142.png)

### （2）添加Tomcat依赖包

![image-20231201103052869](.\assets\image-20231201103052869.png)

### （3）配置编译器

![image-20231201103216947](.\assets\image-20231201103216947.png)

![image-20231201103248108](.\assets\image-20231201103248108.png)

## 3.运行项目

![image-20231201103337615](.\assets\image-20231201103337615.png)























