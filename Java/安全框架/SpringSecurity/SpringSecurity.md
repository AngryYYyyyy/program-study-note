# 一、什么是Spring Security

Spring Security 是一个强大而灵活的安全管理框架，属于 Java Spring 框架的一部分，专为与 Spring 应用程序无缝集成而设计。它提供了全面的安全服务和工具，旨在保护企业应用程序的安全。

此框架的主要功能包括身份验证、授权、访问控制、密码管理、单点登录等，并具备抵御多种网络攻击的能力，如跨站脚本攻击和 SQL 注入攻击。

Spring Security 的核心功能是认证和授权：

* **认证（Authentication）**：确认用户身份，确保用户是合法的，并允许访问系统资源。
* **授权（Authorization）**：管理用户对系统资源的访问权限，通过将用户分配至特定的角色或权限来控制其可执行的操作和可访问的资源。

Spring Security 通过一系列可配置的过滤器实现这些安全功能。在应用程序的请求处理过程中，这些过滤器负责执行安全检查。其模块化和层次化的架构不仅提供了高度的灵活性，而且确保了它能够满足企业级应用程序对安全性的高要求。

# 二、快速入门

以 Spring Boot 项目为例，使用 Maven 进行依赖管理，本教程将引导你快速设置和测试 Spring Security。

## 导入依赖

首先，你需要在 Maven 的 `pom.xml` 文件中添加 Spring Security 的依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

这个依赖包含了启动 Spring Security 所需的所有基础设施和默认配置。

## 业务

创建一个简单的控制器 `HelloController`，该控制器包含一个 `hello` 接口，返回简单的字符串：

```java
@RestController
public class HelloController {
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}
```

## 测试

默认情况下，Spring Security 会添加登录界面，并要求身份验证。进行如下操作测试：

启动应用，然后访问 `http://localhost:8080/hello`。

浏览器会自动重定向到登录页面 `http://localhost:8080/login`。

![image-20240722222933329](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407222229252.png)

使用用户名 `user` 登录。登录密码会在应用启动时打印在控制台中。



![image-20240722223152092](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407222231922.png)

登录成功后，将重定向回之前试图访问的 `/hello` 页面，显示 "hello"。

![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407222233851.png)

Spring Security 也提供了默认的登出功能：

访问 `http://localhost:8080/logout`。

应用会处理登出逻辑，并将用户重定向到登录页面，同时显示登出成功的提示。

![image-20240722223423328](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407222234013.png)

# 三、Spring Security原理分析

## 1.核心过滤器

SpringSecurity的原理其实就是一个过滤器链，内部包含了提供各种功能的过滤器。这里我们可以看看入门案例中的过滤器。

图中只展示了核心过滤器，其它的非核心过滤器并没有在图中展示。

* **UsernamePasswordAuthenticationFilter**: 负责处理我们在登陆页面填写了用户名密码后的登陆请求。入门案例的认证工作主要有它负责。
* **ExceptionTranslationFilter**：处理过滤器链中抛出的任何AccessDeniedException和AuthenticationException 。
* **FilterSecurityInterceptor**：负责权限校验的过滤器。

![image-20240722223604448](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407222236384.png)

## 2.认证

### （1）什么是Authentication

通俗地讲就是验证当前用户的身份，证明“你是你自己”（比如：你每天上下班打卡，都需要通过指纹打卡，当你的指纹和系统里录入的指纹相匹配时，就打卡成功）

互联网中的认证

* **用户名密码登录**
* **邮箱发送登录链接**
* **手机号接收验证码**
* **只要你能收到邮箱/验证码，就默认你是账号的主人**

### （2）两种认证方式

#### 基于Session

**什么是Cookie**

**Cookie** 是由 web 服务器发送到用户浏览器并保存在本地的一小块数据，它主要用来识别用户，存储会话信息，如用户的登录状态。Cookie 数据在用户与服务器之间来回传递，每次用户尝试访问服务器时，浏览器都会发送相应的 Cookie 信息，以便服务器验证用户的身份或恢复会话状态。

当一个 cookie 被标记为 HTTP-only 后，它将不能被客户端的 JavaScript 直接访问。这意味着即使攻击者能够通过 XSS 注入恶意脚本到网页中，他们也无法读取这些标记为 HTTP-only 的 cookies。

**认证流程**

![image-20240722233226214](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407222332779.png)

在基于 Session 的认证中，认证流程通常包括以下几个步骤：

1. **用户登录：** 用户提交用户名和密码给服务器。
2. **服务器验证：** 当用户首次登录时，服务器验证用户的凭据（如用户名和密码），如果凭据正确，服务器将创建一个会话对象，这个会话对象存储在服务器的内存或其他存储介质中，并且会被赋予一个唯一的会话I。
3. **发送 Cookie：** 服务器向用户的浏览器发送一个 Cookie，通常包含一个唯一的 Session ID，但不包含敏感信息如用户名或密码。
4. **客户端存储和使用 Cookie：** 浏览器将 Cookie 存储起来，并在之后的每次请求中自动发送给服务器。
5. **服务器识别和响应：** 服务器接收请求并通过 Session ID 检索对应的 Session，以确定用户身份和权限，然后根据请求提供相应的服务。

**Session认证的问题**

尽管基于 Session 的认证机制广泛应用于多种 Web 应用程序中，但它也存在一些问题：

1. **分布式：** 当应用需要在多台服务器之间共享 Session 信息时，维护其一致性变得复杂。这可能需要额外的 Session 管理机制，如 Session 共享、持久化或使用中心化的 Session 存储。
2. **安全性：** Session 可能遭受会话劫持攻击，攻击者通过盗取或篡改用户的 Cookie 来获得未经授权的访问权限。此外，如果 Session 数据在服务器上被泄露，也可能导致安全问题。
3. **资源消耗：** 每个用户都会在服务器上创建一个 Session，这可能会导致服务器资源（如内存）的显著消耗，尤其是在大量用户访问的情况下。

#### 基于Token

**什么是Token**

> 访问资源接口（API）时所需要的资源凭证

**Token** 是一种安全凭证，通常是一个编码的字符串，包含了用户的认证信息和其他数据。在 Web 应用中，Token 被用来在客户端和服务器之间安全地传输用户状态，无需保存服务器状态。这使得 Token 认证成为一种无状态的认证方式，广泛应用于 REST API 和移动应用程序中。

它通常包括：

- **uid（用户唯一的身份标识）**：标识请求者的唯一ID。
- **time（时间戳）**：用于检测 Token 的新鲜度或过期状态。
- **sign（签名）**：Token 的前几位经过哈希算法压缩成的一定长度的十六进制字符串，用于验证 Token 的完整性和真实性。

Token特点：

* **服务端无状态化、可扩展性好**：服务端无需存储用户状态，便于横向扩展。
* **支持移动端设备**：Token 机制自然适合非浏览器环境。
* **安全**：通过使用签名和时间戳提高安全性。
* **支持跨程序调用**：Token 可以被不同的客户端程序所共享和使用。

**存储方式**

Token 可以以两种方式进行存储，分别对应有状态和无状态的服务端处理：

1. **存到数据库（如 MySQL、Redis）中**：服务端存储 Token，每次请求时从数据库中取出并验证，使得服务端保持有状态。
2. **不存，服务端无状态（如 JWT）**：服务端不存储 Token，而是依靠 Token 内嵌的信息（如签名）进行验证，每次请求时根据相同的生成方法重新生成或验证 Token。

**认证流程**

![image-20240722233303362](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407222333612.png)

1. 客户端使用用户名和密码请求登录。
2. 服务端收到请求后验证用户名与密码，验证成功后，服务器基于用户的身份信息生成一个 Token。这个 Token 包含了用户的身份标识和其他声明（claims），并且通常会被加密和/或签名，并返回至服务端。
3. 客户端收到 Token 后，将其存储，放在 Cookie  中。
4. 客户端在之后的每次请求中携带 Token 向服务端请求资源。
5. 服务端验证请求中的 Token，若验证成功，则返回所请求的数据。

**与Session区别**

- **无状态 vs. 有状态：** Token 认证是无状态的，服务器不需要存储用户的会话信息，这有助于减轻服务器的负担并简化扩展。而 Session 认证是有状态的，需要在服务器上存储用户的会话数据。
- **安全性：** Token 通常更安全，尤其是在使用 HTTPS 时，因为 Token 本身包含所有必要的用户信息，并且可以通过各种手段（如 JWT）进行安全加密。而 Session 可能面临会话劫持的风险。
- **可扩展性：** Token 方便在不同的系统和服务之间共享认证状态，特别适用于微服务架构和分布式系统。而 Session 则需要额外的机制来同步和管理会话状态，这在多服务器环境中可能比较复杂。
- **跨平台支持：** Token 更适合非浏览器环境，如移动应用和桌面应用，因为它们可以轻松地在 HTTP 头部携带 Token 进行请求。而 Session 依赖于 Cookie，主要适用于 Web 浏览器。

### （3）JWT

**什么是JWT**

**JWT (JSON Web Token)** 是一种基于 JSON 的开放标准（RFC 7519），用于创建访问令牌，允许系统在各方之间安全地传递信息。JWT 是目前非常流行的跨域认证解决方案，通过数字签名的方式，以JSON对象为载体，在用户和服务器之间传递安全可靠的信息。

**JWT 的结构**

JWT 主要包含三个部分，它们之间由点（`.`）分隔：

1. **Header（头部）**
   - 包含 Token 类型（通常是 JWT）和所使用的签名算法（例如 HMAC SHA256 或 RSA）。
   - 示例：`{"alg": "HS256", "typ": "JWT"}`。
2. **Payload（有效载荷）**
   - **Registered Claims（注册声明）**：这些声明不是必须的，但它们是预定义的，推荐使用以提供一致的、互操作的 JWT。它们包括：
     - `iss`（Issuer）：发行者
     - `sub`（Subject）：主题
     - `aud`（Audience）：观众
     - `exp`（Expiration Time）：过期时间
     - `nbf`（Not Before）：生效时间
     - `iat`（Issued At）：发行时间
     - `jti`（JWT ID）：JWT 的唯一身份标识
   - **Public Claims（公共声明）**：可以自由定义的声明，通常用于共享信息。为了避免冲突，它们应当在 IANA JSON Web Token Registry 中注册，或包含一个包含碰撞防止命名空间的名称。
   - **Private Claims（私有声明）**：这些是发送方和接收方之间共享的声明，不需要注册，可以自由使用。这类声明用于携带与业务逻辑相关的信息。
3. **Signature（签名）**
   - 使用 Header 中指定的算法和密钥对 Header 和 Payload 进行签名。这是为了验证消息的发送者是谁，以及确保消息在途中未被篡改。
   - 例如，如果使用的是 HMAC SHA256 算法：`HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)`。

**JWT 认证流程**

1. 用户认证请求：用户提交用户名和密码并使用HTTPS进行传输，确保敏感信息在互联网传输过程中不被嗅探或窃取。

2. 服务端生成并发送JWT：服务端接收到用户名和密码后，首先进行验证，验证成功后，选择必要的用户信息（如用户ID），并将这些信息放入JWT的Payload中，发送JWT给客户端。
3. 客户端发送请求：在发起请求时，客户端需要在HTTP请求的Header中包含JWT。通常放在`Authorization`字段，格式为`Bearer <token>`。这种方式有助于解决跨站请求伪造（CSRF）的问题，因为JWT只通过HTTP头部传输，不会像某些Cookie那样自动发送。并且开发者可以直接访问这些存储来获取或设置 Token，无需复杂的后端逻辑来处理发送和接收 Cookie。

5. 服务端验证JWT：客户端接收到请求后，首先检查HTTP头部的`Authorization`字段是否包含JWT。

   - 检查签名的正确性，确保JWT未被篡改。

   - 验证Token的有效性，如是否已过期。

   - 确认Token的接收方是否正确。

6. 授权与响应：一旦JWT验证成功，服务端将使用JWT中的信息执行请求的业务逻辑，并返回相应的结果。如果用户需要登出，应删除存储的JWT，确保退出登录状态。

### （4）JJWT（Java JWT）实战

> 官方文档：[jwtk/jjwt: Java JWT: JSON Web Token for Java and Android (github.com)](https://github.com/jwtk/jjwt)

#### 导入依赖

#### JJWT API

#### JWTUtils































