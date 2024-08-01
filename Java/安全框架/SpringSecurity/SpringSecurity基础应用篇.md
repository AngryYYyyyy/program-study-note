# 一、什么是Spring Security

Spring Security 是一个功能强大且高度可定制的身份验证和访问控制框架，它是为 Java 应用程序设计的。它主要用于对 Web 应用进行安全保护，但也可以用于保护企业应用程序。Spring Security 提供全面的安全服务，包括身份验证、授权、防止跨站点请求伪造等。

## 1.**核心功能**

   - **身份验证**：Spring Security 支持多种身份验证方式，如表单登录、HTTP 基本认证、LDAP、OpenID、OAuth2 等。它通过一个认证管理器 `AuthenticationManager` 来统一管理这些认证过程。

   - **授权**：授权（authorization）可以基于角色（Role-based）或细粒度的权限（Permission-based）进行访问控制。Spring Security 提供了多种方法来限制对特定资源的访问，如使用注解、XML 配置或 Java 配置。

   - **防止跨站请求伪造（CSRF）**：Spring Security 提供内置的防护机制来防止 CSRF 攻击。开发者可以通过简单的配置开启或关闭 CSRF 防护。

## 2.**Spring Security 的架构**

   - **SecurityContextHolder 和 SecurityContext**：Spring Security 使用 `SecurityContextHolder` 来存储当前的安全上下文（Security Context），这个上下文包含了当前用户的详细信息。

   - **Authentication 和 GrantedAuthority**：用户的认证信息存储在 `Authentication` 对象中，这包括了用户的凭证和相关的权限（`GrantedAuthority`）。

   - **Filter Chain**：在 Web 应用中，Spring Security 使用一系列的过滤器（Filter）来应用安全策略。这些过滤器负责处理身份验证、授权等任务。

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

# 三、Spring Security高级特性

## 1.核心组件

### （1）Filter Chain

SpringSecurity的原理其实就是一个过滤器链，内部包含了提供各种功能的过滤器。这里我们可以看看入门案例中的过滤器。

图中只展示了核心过滤器，其它的非核心过滤器并没有在图中展示。

* **UsernamePasswordAuthenticationFilter**: 负责处理我们在登陆页面填写了用户名密码后的登陆请求。入门案例的认证工作主要有它负责。
* **ExceptionTranslationFilter**：处理过滤器链中抛出的任何AccessDeniedException和AuthenticationException 。
* **FilterSecurityInterceptor**：负责权限校验的过滤器。（新版本称**AuthorizationFilter**）

![image-20240722223604448](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407222236384.png)

### （2）HttpSecurity

在使用Spring Security框架时，`HttpSecurity` 是一个非常核心的类，用于配置Web安全性。通过`HttpSecurity`，你可以详细地控制HTTP请求如何被安全地处理。它提供了一种流式编程的方式来配置路径、权限、认证机制等安全性控制。下面我们来详细了解`HttpSecurity`的主要用法和配置选项。

`HttpSecurity` 提供的功能主要可以分为以下几个方面：

1. **请求授权配置**：
   通过`.authorizeRequests()`方法，你可以定义哪些URL路径应该被保护、哪些不应该。你可以为不同的URL路径指定不同的安全要求，如角色、权限等。

2. **表单登录配置**：
   使用`.formLogin()`方法，你可以定制登录过程。这包括自定义登录页面、登录失败页面、默认成功登录后的页面等。

3. **注销配置**：
   `.logout()`方法允许配置注销行为，如注销后重定向到某个页面、清除cookies等。

4. **HTTP基本认证**：
   `.httpBasic()`用于配置HTTP基本认证，这是一种简单但不太安全的认证方式，适用于某些API认证场景。

5. **跨站请求伪造（CSRF）保护**：
   `.csrf()`用于开启或关闭CSRF保护。默认情况下，Spring Security会启用CSRF保护。

6. **跨域资源共享（CORS）**：
   `.cors()`方法可以用来配置CORS，允许跨域请求。

7. **会话管理**：
   `.sessionManagement()`用于配置会话管理策略，如会话固定保护、会话超时处理等。

8. **异常处理**：
   `.exceptionHandling()`允许配置安全异常的处理方式，如访问拒绝时的重定向等。

下面是一个使用`HttpSecurity`配置Spring Security的典型例子：

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        // 开启CORS和CSRF支持
        .cors().and().csrf().disable()
        
        // 配置请求授权
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()  // 公开访问的路径
            .antMatchers("/admin/**").hasRole("ADMIN")  // 需要ADMIN角色的路径
            .anyRequest().authenticated()  // 其他所有路径都需要身份认证
        
        // 配置表单登录
        .formLogin()
            .loginPage("/login")  // 自定义登录页面URL
            .permitAll()  // 允许所有用户访问登录页面
            .defaultSuccessUrl("/home")  // 登录成功后的默认跳转页面
        
        // 配置注销
        .logout()
            .logoutSuccessUrl("/login?logout")  // 注销成功后跳转的URL
            .permitAll();
}
```

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

#### **什么是JWT**

**JWT (JSON Web Token)** 是一种基于 JSON 的开放标准（RFC 7519），用于创建访问令牌，允许系统在各方之间安全地传递信息。JWT 是目前非常流行的跨域认证解决方案，通过数字签名的方式，以JSON对象为载体，在用户和服务器之间传递安全可靠的信息。

#### **JWT 的结构**

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

#### **JWT 认证流程**

1. 用户认证请求：用户提交用户名和密码并使用HTTPS进行传输，确保敏感信息在互联网传输过程中不被嗅探或窃取。

2. 服务端生成并发送JWT：服务端接收到用户名和密码后，首先进行验证，验证成功后，选择必要的用户信息（如用户ID），并将这些信息放入JWT的Payload中，发送JWT给客户端。
3. 客户端发送请求：在发起请求时，客户端需要在HTTP请求的Header中包含JWT。通常放在`Authorization`字段，格式为`Bearer <token>`。这种方式有助于解决跨站请求伪造（CSRF）的问题，因为JWT只通过HTTP头部传输，不会像某些Cookie那样自动发送。并且开发者可以直接访问这些存储来获取或设置 Token，无需复杂的后端逻辑来处理发送和接收 Cookie。

5. 服务端验证JWT：客户端接收到请求后，首先检查HTTP头部的`Authorization`字段是否包含JWT。

   - 检查签名的正确性，确保JWT未被篡改。

   - 验证Token的有效性，如是否已过期。

   - 确认Token的接收方是否正确。

6. 授权与响应：一旦JWT验证成功，服务端将使用JWT中的信息执行请求的业务逻辑，并返回相应的结果。如果用户需要登出，应删除存储的JWT，确保退出登录状态。

#### JJWT（Java JWT）实战

> 官方文档：[jwtk/jjwt: Java JWT: JSON Web Token for Java and Android (github.com)](https://github.com/jwtk/jjwt)

##### 导入依赖

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```

注意：从 Java 9 开始，`javax.xml.bind` 模块不再包含在 JDK 的标准库中，如果使用高版本的JDK，需要额外添加依赖

```xml
<!-- 添加 JAXB API -->
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>
<!-- 添加 JAXB 核心实现 -->
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>2.3.2</version>
</dependency>
```

##### JWTUtils

```java
/**
 * JWT工具类，用于生成和解析JSON Web Tokens（JWT）。
 * 本类提供了根据指定用户名和过期时间生成JWT令牌的方法，
 * 以及解析令牌以提取信息的方法。
 */
public class JWTUtils {

    // 用于签名JWT令牌的密钥。请将"your_secret_key"替换为一个强大的、私有的密钥。
    private static final String SECRET_KEY = "your_secret_key";

    /**
     * 生成JWT令牌。
     * @param username 用户名，通常是用户的唯一标识。
     * @param expirationMillis 令牌过期时间，以毫秒为单位。
     * @return 生成的JWT令牌字符串。
     */
    public static String createToken(String username, long expirationMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        return Jwts.builder()
                .setSubject(username) // 设置主题，即令牌的所有者。
                .setIssuedAt(now)     // 设置令牌的签发时间。
                .setExpiration(new Date(nowMillis + expirationMillis)) // 设置令牌的过期时间。
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 使用HS256算法和密钥进行签名。

                .compact();
    }

    /**
     * 解析JWT令牌。
     * @param token 要解析的JWT令牌字符串。
     * @return Claims对象，包含令牌中的声明信息。
     * @throws io.jsonwebtoken.JwtException 如果令牌无效或解析过程中出现问题。
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 设置用于解析JWT的签名密钥。
                .parseClaimsJws(token)    // 解析传入的令牌。
                .getBody();
    }
}
```

##### 测试

```java
@SpringBootTest
class JWTUtilsTest {

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void jwt() {
        String token = JWTUtils.createToken("Angryyyyy",1000000);
        System.out.println(token);
        Claims parse = JWTUtils.parseToken(token);
        System.out.println(parse);
    }
}
```

### （4）部分源码分析

Spring Security 的身份验证体系中包含了一系列核心组件，它们共同工作以提供强大的安全保障。这些组件包括各种过滤器、管理器、提供者以及用户详细信息服务。下面是对您提到的每个组件的详细介绍：

![image-20240730213145537](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407302141660.png)

![image-20240730214335976](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407302143276.png)

#### **AbstractAuthenticationProcessingFilter**

这是一个抽象基类，用于处理基于表单的登录请求。它封装了身份验证流程的通用逻辑，包括尝试身份验证、成功或失败后的处理。实现此类的具体子类会根据不同类型的认证方式进行具体的认证逻辑实现。

`UsernamePasswordAuthenticationFilter`是 `AbstractAuthenticationProcessingFilter` 的一个具体实现，用于处理基于用户名和密码的身份验证请求。此过滤器获取 HTTP 请求中的用户名和密码，创建一个 `UsernamePasswordAuthenticationToken`，并将其传递给配置的 `AuthenticationManager` 来进行认证。

#### **AuthenticationManager**

它是一个接口，定义了认证的方法。`AuthenticationManager` 最常见的实现是 `ProviderManager`，负责协调多个 `AuthenticationProvider`。`ProviderManager` 会依次尝试每一个配置的提供者，直到其中一个能够处理提供的认证令牌或者全部尝试失败。

#### **AbstractUserDetailsAuthenticationProvider**

这是 `AuthenticationProvider` 的一个抽象实现，专门用于处理基于用户详细信息的服务的认证请求。它通过 `UserDetailsService` 来获取用户的详细信息，并进行相应的认证逻辑。

`DaoAuthenticationProvider`是 `AbstractUserDetailsAuthenticationProvider` 的一个具体实现，它通过 `UserDetailsService` 获取用户详细信息，并使用密码比较器验证用户提供的密码是否正确。

#### **UserDetailsService**

这是一个接口，用于加载用户特定的数据。它只有一个方法 `loadUserByUsername(String username)`，该方法根据用户名查找并返回用户的详细信息。实现这个接口的类需要提供用户存储机制。

`InMemoryUserDetailsManager`是 `UserDetailsService` 的一个实现，它使用内存来存储用户信息。这通常用于测试或小规模应用中，因为它不需要外部存储，而是将所有用户详细信息直接存储在内存中。

### （5）自定义认证

在使用 Spring Security 构建安全的应用程序时，自定义用户认证是一个常见需求。通过实现 `UserDetailsService` 和 `UserDetails` 接口，开发者可以灵活地定义如何从数据源中检索用户信息，并将这些信息转换成 Spring Security 能理解的格式。

在实际应用中，可以通过 `BCryptPasswordEncoder` 来处理用户密码的加密，确保系统的安全性。

#### 实现 UserDetailsService 接口

下面的代码示例展示了如何自定义 `UserDetailsService`。通过重写 `loadUserByUsername` 方法，我们可以根据用户名从数据库中查询用户信息，并将其封装为 Spring Security 所需的 `UserDetails` 对象。

```java
/**
 * 自定义 UserDetailsService 实现类，用于从数据库加载用户信息。
 * 
 * @author spikeCong
 * @date 2023/4/14
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserMapper userMapper; // 使用 UserMapper 来查询数据库中的用户信息。
 
    /**
     * 根据用户名加载用户的详细信息。
     *
     * @param username 用户名
     * @return UserDetails 用户详细信息
     * @throws UsernameNotFoundException 如果无法找到用户则抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, username);
        SysUser user = userMapper.selectOne(wrapper);
 
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
 
        return new LoginUser(user);
    }
}

```

#### 实现 UserDetails 接口

为了使自定义的用户信息符合 Spring Security 的要求，需要创建一个 `LoginUser` 类，它实现了 `UserDetails` 接口。这个类将封装用户信息，并提供 Spring Security 需要的用户状态方法。

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
 
    private SysUser sysUser; // 内部封装的 SysUser 对象，包含用户的详细信息。
 
    /**
     * 返回用户所具有的权限集合，用于访问控制。
     *
     * @return 用户的权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 返回 null 或具体的权限信息，根据实际需要决定。
    }
 
    /**
     * 返回用户密码。
     *
     * @return 用户的密码
     */
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }
 
    /**
     * 返回用户名。
     *
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }
 
    /**
     * 账户是否未过期。
     *
     * @return true 表示未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    /**
     * 账户是否未锁定。
     *
     * @return true 表示未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    /**
     * 凭证（密码）是否未过期。
     *
     * @return true 表示密码未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return

```

#### 密码加密

在现代应用中，密码安全是不可忽视的一环。Spring Security 提供了 `BCryptPasswordEncoder`，一个基于 bcrypt 算法的密码加密工具，它帮助开发者安全地存储用户密码。

* `encode(CharSequence rawPassword)`：对原始密码进行加密处理，并返回加密后的密码字符串。
* `matches(CharSequence rawPassword, String encodedPassword)`：对比原始密码和加密后的密码是否匹配。

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置密码编码器
     * 使用 BCryptPasswordEncoder 来加密密码
     * @return 返回 BCryptPasswordEncoder 实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

```

#### 自定义登录接口

自定义登录流程通常包括创建一个接口用于接收用户登录请求，并处理这些请求。关键步骤包括使用 `AuthenticationManager` 进行用户认证，并在认证成功后生成 JWT 作为认证后的令牌。

```java
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 处理用户登录请求
     * @param user 用户登录信息
     * @return 返回登录结果
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody SysUser user) {
        return loginService.login(user);
    }
}

```

#### 登录服务实现

登录服务 `LoginServiceImpl` 负责具体的用户认证逻辑，包括调用 `AuthenticationManager`，生成 JWT，并将用户信息存储至 Redis。

```java
public interface LoginService {
    ResponseResult login(SysUser sysUser);
}

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(SysUser sysUser) {
        // 1. 使用 AuthenticationManager 进行用户认证
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword()));

        // 2. 验证认证结果
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("登录失败");
        }

        // 3. 认证通过，生成 JWT 并返回
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getSysUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);

        // 4. 用户信息存储至 Redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        // 5. 返回响应结果
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult(200, "登录成功", map);
    }
}
```

`UsernamePasswordAuthenticationToken` 是 Spring Security 中用于表示用户认证信息的一个类。它通常在用户登录过程中使用，以存储和传递用户的认证信息。该类有几个构造方法，其中一个常用的构造方法接收三个参数：

1. **Principal**: 这个参数通常表示用户的身份。在大多数情况下，这可以是用户名、用户ID或者用户的整个对象。
2. **Credentials**: 这个参数用于表示用户的凭据，通常是密码。在验证用户身份后，为了安全起见，通常会将凭据从内存中清除或设置为 `null`。
3. **Authorities**: 这个参数是一个 `Collection`，包含了用户所拥有的权限。这些权限通常用于决定用户可以访问应用中的哪些功能。这些权限（或角色）是 `GrantedAuthority` 对象的集合。

#### 登录接口允许访问

在 `SecurityConfig` 类中配置 Spring Security 的安全措施，允许对登录接口的匿名访问，并设置其他接口的安全要求。

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入 AuthenticationManager 以供外部使用
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置 HTTP 安全，定义请求的授权规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/user/login").anonymous()
            .anyRequest().authenticated();
    }
}
```

#### 认证过滤器

在用户成功登录并获取JWT之后，每次请求都需要验证JWT的有效性。认证过滤器的任务是从请求中提取JWT，验证它，并从中提取用户信息，然后将其存储在`SecurityContextHolder`中，以供后续请求使用。

#### 自定义Jwt认证过滤器

这个过滤器将继承`OncePerRequestFilter`类，确保在每次请求中只调用一次过滤器。它的主要职责是解析JWT，从中提取用户ID，并使用这个ID从Redis中获取用户详细信息。

```java
/**
 * 自定义JWT认证过滤器，用于验证请求中的JWT并从Redis加载用户详情
 * @author spikeCong
 * @date 2023/4/25
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    /**
     * 实现JWT认证逻辑
     * @param request 请求对象
     * @param response 响应对象
     * @param filterChain 过滤器链
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 从请求头获取JWT
        String token = request.getHeader("token");

        // 如果token为空，继续过滤链
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析JWT获取用户ID
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("非法token");
        }

        // 从Redis获取用户信息
        String redisKey = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }

        // 设置SecurityContext
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}

```

#### 配置Jwt认证过滤器

过滤器需要在`SecurityConfig`中注册，以确保它被包括在Spring Security的过滤器链中。这通常是通过`addFilterBefore`方法完成的，它确保自定义过滤器在`UsernamePasswordAuthenticationFilter`之前执行。

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/user/login").anonymous()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
```

#### 退出登录接口

在使用JWT和Redis进行用户会话管理的应用中，定义一个退出登录接口主要涉及删除Redis中存储的用户会话数据。这样做确保即使JWT仍在有效期内，也不会被后续请求所使用，因为相关的会话已经被标记为无效。

当使用无状态的认证机制（如基于JWT的认证）时，每次请求都需要重新认证。这意味着`SecurityContextHolder`会在每次请求开始时被设置，并在请求结束时清理。因此，即使在当前会话中清除了`SecurityContextHolder`，这对于无状态认证机制来说并不是必需的，因为它每次都不会携带之前的认证信息。

**LoginController - 控制器**

定义一个GET请求的退出登录接口，当调用此接口时，触发登录服务的`logout`方法。

```java
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 退出登录接口，删除用户会话
     * @return 返回注销操作结果
     */
    @GetMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }
}
```

**LoginService - 服务接口**

在登录服务接口中定义`logout`方法，这样可以在服务实现类中具体实现注销逻辑。

```java
public interface LoginService {
    ResponseResult login(SysUser sysUser);
    ResponseResult logout();
}
```

**LoginServiceImpl - 服务实现类**

服务实现类中包括从`SecurityContextHolder`获取当前用户的认证信息，并使用此信息来从Redis中删除用户的会话数据。

```java
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult logout() {
        // 获取当前用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("用户未登录或已过期");
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();

        // 删除Redis中的用户信息
        redisCache.deleteObject("login:" + userId);
        SecurityContextHolder.clearContext();  // 可选：清除SecurityContextHolder中的认证信息
        return new ResponseResult(200, "注销成功");
    }
}
```

## 3.授权

### （1）什么是authorization

授权是信息安全中的一个核心概念，其目的是确保只有得到授权的用户或系统才能访问资源或执行操作。授权过程通常在认证（Authentication）之后进行，认证是用来验证用户的身份，例如通过用户名和密码。一旦用户的身份得到认证，系统就会进行授权，根据用户的权限决定他们可以访问哪些资源或执行哪些操作。

授权可以通过多种方式实现，其中包括：

- **基于角色的访问控制（RBAC）**：如你所述，通过角色来管理权限，适用于权限和角色较为固定的环境。
- **基于属性的访问控制（ABAC）**：允许基于用户属性（如部门、地理位置等）来动态控制访问权限，更加灵活。
- **基于策略的访问控制（PBAC）**：通过详细的策略规则来管理用户对资源的访问，适用于需要复杂规则的环境。

### （2）RBAC

RBAC 是一种非常流行的授权模型，它简化了权限管理。在这个模型中，权限不是直接分配给用户，而是分配给角色，用户通过成为角色的一部分来继承这些权限。这种模式的优势在于：

- **简化管理**：管理者只需关心角色的权限设置，而不是每个个体用户的权限。
- **提高安全性**：通过严格定义角色和相关权限，可以减少不必要的权限赋予。
- **易于扩展**：随着组织的发展，可以轻松添加新的角色或修改现有角色的权限，而不需要重新配置每个用户。

RBAC 特别适用于用户基数大且角色定义明确的组织，如企业、政府机构等。在设计系统时，通常需要仔细定义角色和权限，以确保既满足业务需求，又不妨碍系统的安全性和管理效率。

通过这些概念和机制的正确实施，组织可以有效地控制对敏感信息和关键资源的访问，从而保护数据不受未授权的使用或泄露。

### （3）自定义授权

在Spring Security框架中，自定义授权是通过设置访问特定资源所需的权限来实现的。这一过程涉及使用注解来声明方法级的安全要求，以及通过角色基于访问控制（RBAC）模型来管理用户权限。

#### 设置资源访问所需要的权限

在`SecurityConfig`类中添加`@EnableGlobalMethodSecurity(prePostEnabled = true)`注解，以启用全局方法级的安全性设置。这允许使用`@PreAuthorize`和`@PostAuthorize`注解来在方法执行前后执行安全性检查。

```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全性控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 配置方法
}
```

在控制器中，可以使用`@PreAuthorize`注解来定义哪些权限的用户可以访问特定的方法。例如，使用`@PreAuthorize("hasAuthority('test')")`注解确保只有拥有'test'权限的用户才能访问该方法。

```java
@RestController
public class SomeController {

    @PreAuthorize("hasAuthority('test')")
    @GetMapping("/secured")
    public ResponseEntity<String> getSecuredResource() {
        return ResponseEntity.ok("This is a secured resource");
    }
}

```



#### 根据RBAC权限模型创建表

基于RBAC模型，需要在数据库中存储用户、角色和权限数据，并能够检索一个用户的所有权限。

![image-20240731162800152](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202407311628644.png)

#### 从数据库获取权限信息

**数据库查询接口**

```java
 public interface MenuMapper extends BaseMapper<Menu> {
     
     List<String> selectPermsByUserId(Long id);
 }
```

**MyBatis映射文件**

```xml
 <?xml version="1.0" encoding="UTF-8" ?>
 <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
 
 <mapper namespace="com.mashibing.springsecurity_example.mapper.MenuMapper">
 
     <select id="selectPermsByUserId" resultType="java.lang.String">
         SELECT 
             DISTINCT sm.perms
         FROM sys_user_role sur 
             LEFT JOIN sys_role sr ON sur.role_id = sr.role_id
             LEFT JOIN sys_role_menu srm ON sr.role_id = srm.role_id
             LEFT JOIN sys_menu sm ON srm.menu_id = sm.menu_id
         WHERE 
             user_id = #{userid}
             AND sr.status = 0
             AND sm.status = 0
     </select>
 
 </mapper>
```
#### 封装权限信息

在`UserDetailsServiceImpl`中，从数据库查询用户权限，并将这些权限信息封装到`LoginUser`对象中。`LoginUser`对象应该实现`UserDetails`接口，提供权限信息的集合供Spring Security使用。

```JAVA
 List<String> permissions = menuMapper.selectPermsByUserId(user.getUserId());
        return new LoginUser(user, permissions);
```

 如果SpringSecurity想要获取用户权限信息,其实最终要调用 getAuthorities()方法,所以要在这个方法中将查询到的权限信息进行转换,转换另一个List集合,其中保存的数据类型是 GrantedAuthority 类型。这是一个接口,我们用它的这个实现`SimpleGrantedAuthority`。

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
 
    private SysUser sysUser; // 内部封装的 SysUser 对象，包含用户的详细信息。
 	
    private List<String> permissions;//存储权限信息集合
    
    //authorities集合不需要序列化,只需要序列化permissions集合即可
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;
    /**
     * 返回用户所具有的权限集合，用于访问控制。
     *
     * @return 用户的权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities != null){
             return authorities;
         }
        authorities = permissions.stream()
                 .map(SimpleGrantedAuthority::new)
                 .collect(Collectors.toList());
         return authorities;
    }
}
```

在`JwtAuthenticationTokenFilter`过滤器中，设置当前用户的权限信息到`SecurityContextHolder`，以便后续的请求能够进行权限验证。

```java
UsernamePasswordAuthenticationToken authenticationToken =
     new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
```

### （4）@PreAuthorize

`@PreAuthorize` 是 Spring Security 提供的一种注解，用于方法安全，它允许在方法调用之前基于表达式的条件来进行访问控制。这种权限控制的方法特别适合细粒度的安全需求，例如对特定方法的访问进行限制。下面详细介绍 `@PreAuthorize` 的用法，特别是和 `hasAuthority`, `hasAnyAuthority`, `hasRole`, `hasAnyRole` 这些常用方法的结合。

`@PreAuthorize` 注解可以放在方法上，用来决定一个方法是否应该被执行。它可以使用表达式语言来描述安全要求，常见的表达式包括：

- **`hasAuthority(String authority)`**：
  检查当前用户是否有指定的权限（authority）。权限通常是用户进行操作所必需的，不同于角色，它可以是更细粒度的控制。

- **`hasAnyAuthority(String... authorities)`**：
  检查当前用户是否具有列出的任何权限。如果用户拥有参数中任意一个指定的权限，表达式结果为真。

- **`hasRole(String role)`**：
  检查当前用户是否有指定的角色。在使用时，Spring Security 会默认在角色名前添加前缀 “ROLE_”。因此如果你指定 `hasRole('ADMIN')`，实际上它会检查 `ROLE_ADMIN`。

- **`hasAnyRole(String... roles)`**：
  类似于 `hasAnyAuthority`，检查当前用户是否拥有列出的任何角色中的一个。

以下是一个使用 `@PreAuthorize` 的示例，演示了如何在实际代码中使用这些表达式：

```java
import org.springframework.security.access.prepost.PreAuthorize;

public class UserService {

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        // 代码逻辑，只有 ADMIN 角色的用户才能执行此方法
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void editUser(Long id) {
        // 代码逻辑，ADMIN 或 EDITOR 角色的用户都可以执行此方法
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGES')")
    public void writePost() {
        // 代码逻辑，要求用户具有 WRITE_PRIVILEGES 权限
    }

    @PreAuthorize("hasAnyAuthority('READ_PRIVILEGES', 'WRITE_PRIVILEGES')")
    public void readPost() {
        // 代码逻辑，用户具有 READ_PRIVILEGES 或 WRITE_PRIVILEGES 中的任意一个权限时可以执行
    }
}
```

- 使用 `@PreAuthorize` 时需要确保启用了方法安全性配置。在 Spring 配置中可以通过 `@EnableGlobalMethodSecurity(prePostEnabled = true)` 来启用。
- `@PreAuthorize` 可以与 `@PostAuthorize`, `@PreFilter`, `@PostFilter` 等注解一起使用，提供更复杂的安全策略。

## 4.异常处理

### （1）ExceptionTranslationFilter

`ExceptionTranslationFilter` 是 Spring Security 框架中的一个关键过滤器，用于处理请求过程中抛出的异常，并将其转化为合适的响应。它的主要作用是保护应用程序中受保护资源的访问，并根据用户的身份进行适当的响应。

**当 Spring Security 抛出异常时，**`ExceptionTranslationFilter` 将会捕获该异常并根据异常类型去判断是认证失败还是授权失败出现的异常。然后根据 Spring Security 的配置进行处理。

* **如果是认证过程中出现的异常会被封装成** `AuthenticationException` , 然后调用**AuthenticationEntryPoint**对象的方法去进行异常处理。
* **如果是授权过程中出现的异常会被封装成** `AccessDeniedException` , 然后调用**AccessDeniedHandler**对象的方法去进行异常处理。

### （2）AuthenticationEntryPoint

`AuthenticationEntryPoint` 是 Spring Security 中用于处理未经身份验证的用户访问受保护资源时的异常的接口。

**通过实现 **`AuthenticationEntryPoint` 接口，我们可以自定义未经身份验证的用户访问需要认证的资源时应该返回的响应。

```java
 /**
  * 自定义认证过程异常处理
  * @author spikeCong
  * @date 2023/4/26
  **/
 @Component
 public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
     
     @Override
     public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
 
         ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
         String json = JSON.toJSONString(result);
         WebUtils.renderString(response,json);
     }
 }
 
```

### （3）AccessDeniedHandler

在 Spring Security 中，当用户请求某个受保护的资源，但是由于权限不足或其他原因被拒绝访问时，Spring Security 会调用`AccessDeniedHandler` 来处理这种情况。

通过自定义实现 `AccessDeniedHandler` 接口，并覆盖 `handle` 方法，我们可以自定义处理用户被拒绝访问时应该返回的响应。

```java
 /**
  * 自定义处理授权过程中的异常
  * @author spikeCong
  * @date 2023/4/26
  **/
 @Component
 public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
 
     @Override
     public void handle(HttpServletRequest request, HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException, ServletException {
         ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(),"权限不足,禁止访问");
         String json = JSON.toJSONString(result);
         WebUtils.renderString(response,json);
     }
 }
```

### （4）SecurityConfig

**先注入对应的处理器**

```java
 @Autowired
 private AuthenticationEntryPoint authenticationEntryPoint;
 
 @Autowired
 private AccessDeniedHandler accessDeniedHandler;
```

**然后使用HttpSecurity对象的方法去进行配置**

```java
 //配置异常处理器
 http.exceptionHandling()
     //配置认证失败处理器
     .authenticationEntryPoint(authenticationEntryPoint)
     //配置授权失败处理器
     .accessDeniedHandler(accessDeniedHandler);
```

## 5.跨域资源共享

### （1）概述 

跨域资源共享（Cross-Origin Resource Sharing, CORS）是一种安全机制，它允许或限制网页上的资源（如JavaScript）能否被不同域（Origin）的其他网页访问。例如，如果一个网页试图从另一个不同源的服务器请求资源，那么这种请求就属于跨域请求。

默认情况下，浏览器的同源策略（Same-Origin Policy）会阻止网页读取或修改不同源网页的内容，这是为了防止恶意网站读取另一个网站上的敏感数据。CORS 通过HTTP头部中的一系列规则（如`Access-Control-Allow-Origin`），允许网站管理员定义哪些外部域可以访问该网站上的资源。

CORS的实现方式主要是通过HTTP头部来实现的，浏览器会在请求中添加一些自定义的HTTP头部，告诉服务器请求的来源、目标地址等信息。服务器在接收到请求后，会根据请求头中的信息来判断是否允许跨域请求，并在响应头中添加一些自定义的HTTP头部，告诉浏览器是否允许请求、允许哪些HTTP方法、允许哪些HTTP头部等信息。

在响应头中添加以下字段，可以解决跨域问题:

* `access-control-allow-origin` : 该字段是必须的。它的值要么是请求时 `Origin`字段的值，要么是一个 `*`，表示接受任意域名的请求。
* `access-control-allow-credentials` : 该字段可选。它的值是一个布尔值，表示是否允许发送Cookie。默认情况下，Cookie不包括在CORS请求之中。设为 `true`，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。这个值也只能设为 `true`，如果服务器不要浏览器发送Cookie，删除该字段即可
* `Access-Control-Allow-Methods` : 该字段必需，它的值是逗号分隔的一个字符串，表明服务器支持的所有跨域请求的方法。注意，返回的是所有支持的方法，而不单是浏览器请求的那个方法。这是为了避免多次"预检"请求。

其实最重要的就是 `access-control-allow-origin` 字段，添加一个 * ，允许所有的域都能访问

### （2）跨站请求伪造 (CSRF)

### （3）整合SpringBoot

在SpringBoot项目中只需要编写一个配置类使其实现WebMvcConfigurer接口并重写其addCorsMappings方法即可。

```java
 @Configuration
 public class CorsConfig implements WebMvcConfigurer {
 
     @Override
     public void addCorsMappings(CorsRegistry registry) {
         // 设置允许跨域的路径
         registry.addMapping("/**")
                 // 设置允许跨域请求的域名
                 .allowedOriginPatterns("*")
                 // 是否允许cookie
                 .allowCredentials(true)
                 // 设置允许的请求方式
                 .allowedMethods("GET", "POST", "DELETE", "PUT")
                 // 设置允许的header属性
                 .allowedHeaders("*")
                 // 跨域允许时间
                 .maxAge(3600);
     }
 }
```

也可以通过使用 `@CrossOrigin` 注解来解决跨域问题

```java
 @RestController
 public class MyController {
     
     @CrossOrigin(origins = "http://localhost:8080")
     @GetMapping("/my-endpoint")
     public String myEndpoint() {
         // ...
     }
 }
```

`@CrossOrigin` 注解的 `origins` 参数指定了允许访问该接口的域名。在上面的例子中，只有来自 `http://localhost:8080` 域名的请求才能访问 `myEndpoint` 接口。

### （4）整合SpringSecurity

由于我们的资源都会受到SpringSecurity的保护，所以想要跨域访问还要让SpringSecurity运行跨域访问。

```java
 //该方法用于配置 HTTP 请求的安全处理
 @Override
 protected void configure(HttpSecurity http) throws Exception {
     http
         //关闭csrf
         .csrf().disable()
         //不会创建会话，每个请求都将被视为独立的请求。
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         //定义请求授权规则
         .authorizeRequests()
         // 对于登录接口 允许匿名访问
         .antMatchers("/user/login").anonymous()
         // 除上面外的所有请求全部需要鉴权认证
         .anyRequest().authenticated();
 
     //将自定义认证过滤器,添加到过滤器链中
     http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
 
     //配置异常处理器
     http.exceptionHandling()
         //配置认证失败处理器
         .authenticationEntryPoint(authenticationEntryPoint)
         //配置授权失败处理器
         .accessDeniedHandler(accessDeniedHandler);
 
     //允许跨域
     http.cors();
 }
```

## 6.认证处理器

### （1）配置登录页面

**引入模板依赖**

通过引入Thymeleaf模板引擎依赖，可以在Spring Boot项目中使用Thymeleaf作为页面渲染工具。这允许我们使用HTML模板来构建动态的Web页面。

```xml
 <!--thymeleaf-->
 <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
 </dependency>
```

**在 templates 中定义登录界面 `login.html`**

创建一个简单的登录表单，该表单通过Thymeleaf的`th:action`属性指定表单数据提交到`/login`端点。

```html
 <!DOCTYPE html>
 <html lang="en" xmlns:th="http://www.thymeleaf.org">
 <head>
     <meta charset="UTF-8">
     <title>登录页面</title>
 </head>
     <body>
         <h1>用户登录</h1>
         <form method="post" th:action="@{/login}">
             用户名:<input name="username" type="text"/><br>
             密码:<input name="password" type="password"/><br>
             <input type="submit" value="登录"/>
         </form>
     </body>
 </html>
```

**配置 Spring Security 配置类**

配置Spring Security来处理登录认证，允许访问登录页面和主页（index），并为表单登录提供了自定义页面和处理路径。

```java
 @Configuration
 public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
     @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests()    //开始配置授权，即允许哪些请求访问系统
                 .mvcMatchers("/login.html").permitAll()   //指定哪些请求路径允许访问
                 .mvcMatchers("/index").permitAll()      //指定哪些请求路径允许访问
                 .anyRequest().authenticated()  //除上述以外,指定其他所有请求都需要经过身份验证
                 .and()
                 .formLogin()    //配置表单登录
                 .loginPage("/login.html")      //登录页面
                 .loginProcessingUrl("/login")  //提交路径
                 .usernameParameter("username") //表单中用户名
                 .passwordParameter("password") //表单中密码
                 .successForwardUrl("/index")  //指定登录成功后要跳转的路径为 /index
                 //.defaultSuccessUrl("/index")   //redirect 重定向  注意:如果之前请求路径,会有优先跳转之前请求路径
                 .failureUrl("/login.html") //指定登录失败后要跳转的路径为 /login.htm
                 .and()
                 .csrf().disable();//关闭 CSRF
     }
 }
```

**说明**

* **permitAll() 代表放行该资源,该资源为公共资源 无需认证和授权可以直接访问**
* **anyRequest().authenticated() 代表所有请求,必须认证之后才能访问**
* **formLogin() 代表开启表单认证 **
* **successForwardUrl 、defaultSuccessUrl 这两个方法都可以实现成功之后跳转**
  * **successForwardUrl  默认使用 **`forward`跳转，不会跳转到之前请求路径`
  * **defaultSuccessUrl   默认使用 **`redirect` 跳转 ，`defaultSuccessUrl(url, true)` 的第二个参数 `true` 或 `false` 用来指定是否应该忽略之前保存的请求并总是重定向到 `url`。如果设置为 `true`，不管之前是否有保存的请求，用户登录成功后都将被重定向到指定的 `url`。如果设置为 `false`（默认值），则会如上所述优先考虑之前的请求路径。

注意:

- 在配置安全规则时，确保公开可访问的路径（如登录页面）在任何需要认证的路径之前放行。
- 使用的是Spring Security 5.7及更高版本，需要使用`SecurityFilterChain`和`WebSecurityCustomizer `代替`WebSecurityConfigurerAdapter`。

**创建Controller**

`Controller`定义了登录页面和一个测试用的响应方法。确保所有路径与业务需求相匹配。

```java
 @Controller
 public class LoginController {
     @RequestMapping("/ok")
     public String ok(){
         return "ok";
     }
 
     @RequestMapping("/login.html")
     public String login(){
         return "login";
     }
 }
```

### （2）自定义认证成功处理器

在前后端分离的应用中，通常不需要在认证成功后进行页面跳转，而是需要返回JSON数据。自定义 `AuthenticationSuccessHandler` 允许开发者在用户成功登录后执行自定义逻辑，如返回一个包含状态和消息的JSON响应。

**实现 `AuthenticationSuccessHandler`**

 `AuthenticationSuccessHandlerImpl` 类通过输出JSON格式的响应来通知前端登录成功，这种方式适合RESTful或前后端分离的架构。

```java
 @Component
 public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
 
     @Override
     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException, ServletException {
 
         Map<String, Object> result = new HashMap<String, Object>();
         result.put("msg", "登录成功");
         result.put("status", 200);
         response.setContentType("application/json;charset=UTF-8");
         String s = new ObjectMapper().writeValueAsString(result);
         response.getWriter().println(s);
     }
 }
```

**配置 `AuthenticationSuccessHandler`**

在Spring Security配置中，通过注入自定义的 `AuthenticationSuccessHandler` 并在表单登录配置中使用它，实现了自定义的登录成功逻辑。

```java
 @Configuration
 public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
 
     @Autowired
     private AuthenticationSuccessHandler successHandler;
 
     @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests()    
       .and()
                 .formLogin()    //配置表单登录
                 .successHandler(successHandler)
                 .failureUrl("/login.html") //指定登录失败后要跳转的路径为 /login.htm
                 .and()
                 .csrf().disable();//这里先关闭 CSRF
     }
 }
```

### （3）自定义认证失败处理器

同样地，自定义 `AuthenticationFailureHandler` 允许在用户登录失败时执行自定义操作，例如返回一个错误消息和状态码的JSON响应。

**实现 `AuthenticationFailureHandler`**

`AuthenticationFailureHandlerImpl` 类提供了登录失败时的自定义处理逻辑，通过返回包含错误信息的JSON响应通知前端。

```java
 @Component
 public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
     @Override
     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException exception) throws IOException, ServletException {
 
         Map<String, Object> result = new HashMap<String, Object>();
         result.put("msg", "登录失败: "+exception.getMessage());
         result.put("status", 500);
         response.setContentType("application/json;charset=UTF-8");
         String s = new ObjectMapper().writeValueAsString(result);
         response.getWriter().println(s);
     }
 }
```

**配置 `AuthenticationFailureHandler`**

展示了如何在Spring Security配置中设置自定义的登录失败处理器，这同样适用于需要向前端直接反馈登录结果的场景。

```java
 @Configuration
 public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
 
     @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests()
               //...
                 .and()
                 .formLogin()
                //..
                 .failureHandler(new MyAuthenticationFailureHandler())
                 .and()
                 .csrf().disable();//这里先关闭 CSRF
     }
 }
```

### （4）自定义注销登录处理器

Spring Security 中也提供了默认的注销登录配置，在开发时也可以按照自己需求对注销进行个性化定制。

```java
 @Configuration
 public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
 @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests()
                 //...
                 .and()
                 .formLogin()
                 //...
                 .and()
                 .logout()
                 .logoutUrl("/logout")
                 .invalidateHttpSession(true)
                 .clearAuthentication(true)
                 .logoutSuccessUrl("/login.html")
                 .and()
                 .csrf().disable();//这里先关闭 CSRF
     }
 }
```

* **通过 logout() 方法开启注销配置**
* **logoutUrl 指定退出登录请求地址，默认是 GET 请求，路径为 **`/logout`
* **invalidateHttpSession 退出时是否是 session 失效，默认值为 true**
* **clearAuthentication 退出时是否清除认证信息，默认值为 true**
* **logoutSuccessUrl 退出登录时跳转地址**

注销登录在前后端分离的应用中通常也不需要页面重定向，而是需要返回一条注销成功的JSON消息。

**自定义 `LogoutSuccessHandler`**

通过实现 `LogoutSuccessHandler`，您提供了在用户注销登录成功后返回JSON消息的逻辑。

```java
 @Component
 public class LogoutSuccessHandlerImpl  implements LogoutSuccessHandler {
 
     @Override
     public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication) throws IOException, ServletException {
         
         Map<String, Object> result = new HashMap<String, Object>();
         result.put("msg", "注销成功");
         result.put("status", 200);
         response.setContentType("application/json;charset=UTF-8");
         String s = new ObjectMapper().writeValueAsString(result);
         response.getWriter().println(s);
     }
 }
```

**配置注销登录**

展示了如何配置Spring Security的注销功能，包括清除认证信息和HTTP会话，并使用自定义的 `LogoutSuccessHandler` 来处理注销成功事件。

```java
 @Configuration
 public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
 
     @Autowired
     private LogoutSuccessHandler logoutSuccessHandler;
 
     @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests()    //开始配置授权，即允许哪些请求访问系统
 
                 .and()
                 .formLogin()    //配置表单登录
         //...
                 .and()
                 .logout()
 //                .logoutUrl("/logout")
                 .invalidateHttpSession(true)
                 .clearAuthentication(true)
 //                .logoutSuccessUrl("/login.html")
                 .logoutSuccessHandler(logoutSuccessHandler)
                 .and()
                 .csrf().disable();//这里先关闭 CSRF
     }
 }
```

## 7.图形验证码

在用户登录时，一般通过表单的方式进行登录都会要求用户输入验证码，`Spring Security`默认没有实现图形验证码的功能，所以需要我们自己实现。

#### （1）**自定义图形验证码的过滤器 `ImageCodeValidateFilter`**

在 `UsernamePasswordAuthenticationFilter` 过滤器之前校验图形验证码的正确性。

- 检查是否为POST方式的登录表单提交请求。
- 对于符合条件的请求，验证用户输入的验证码与服务器保存的验证码是否匹配。
- 验证失败抛出自定义的 `ValidateCodeException`（继承自 `AuthenticationException`）。

#### （2）**验证码的生成和验证机制**

图形验证码包含两部分：图片和文字验证码。

- **Session机制（传统方式）**：
  - 后端生成验证码并存储在Session中。
  - 前端提交的验证码与Session中保存的进行比较。

- **Token机制（前后端分离）**：
  - 使用全局缓存（如Redis）存储验证码，以保证验证码的唯一性和时效性。
  - 生成验证码同时创建一个唯一的验证码ID（codeId），将验证码和codeId保存到缓存中。
  - 前端在请求验证码时获取codeId，并在提交表单时一同发送给后端进行验证。

#### （3）**后端响应验证码**

**Base64编码**：

- 生成的图形验证码图片使用Base64编码后传送给前端，避免直接传输图片文件。
- Base64编码方便在HTTP环境中传输，且易于在前端直接显示。

#### （4）案例

**导入easy-captcha库**：这个库用于生成图形验证码，非常方便集成和使用。

```xml
         <dependency>
             <groupId>com.github.whvcse</groupId>
             <artifactId>easy-captcha</artifactId>
             <version>1.6.2</version>
         </dependency>
```

**`CaptchaController`**：负责生成验证码和将验证码信息（包括验证码图片和唯一标识）存储到Redis缓存中。验证码的唯一标识（UUID）和对应的验证码值存储在Redis中，以便验证时使用。

通过将图形验证码的图片以Base64编码的方式发送到前端，同时提供一个唯一标识（UUID），前端在提交登录表单时需回传此UUID，以供后端校验。

```java
 @RestController
 public class CaptchaController {
 
 
     @Autowired
     private RedisCache redisCache;
 
     /**
      * 生成验证码
      * @param response
      * @return: com.mashibing.springsecurity_example.common.ResponseResult
      */
     @GetMapping("/captchaImage")
     public ResponseResult getCode(HttpServletResponse response){
         SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
 
         //生成验证码,及验证码唯一标识
         String uuid = UUID.randomUUID().toString().replaceAll("-", "");
         String key = Constants.CAPTCHA_CODE_KEY + uuid;
         String code = specCaptcha.text().toLowerCase();
 
         //保存到redis
         redisCache.setCacheObject(key,code,1000, TimeUnit.SECONDS);
 
         //创建map
         HashMap<String,Object> map = new HashMap<>();
         map.put("uuid",uuid);
         map.put("img",specCaptcha.toBase64());
 
         return new ResponseResult(200,"验证码获取成功",map);
     }
 }
```

**`LoginBody`**：定义了登录时需要的数据结构，包括用户名、密码、验证码及其唯一标识。

```java
 /**
  * 用户登录对象
  * @author spikeCong
  * @date 2023/4/30
  **/
 public class LoginBody {
 
     /**
      * 用户名
      */
     private String userName;
 
     /**
      * 用户密码
      */
     private String password;
 
     /**
      * 验证码
      */
     private String code;
 
     /**
      * 唯一标识
      */
     private String uuid = "";
 
     public String getUserName() {
         return userName;
     }
 
     public void setUserName(String userName) {
         this.userName = userName;
     }
 
     public String getPassword() {
         return password;
     }
 
     public void setPassword(String password) {
         this.password = password;
     }
 
     public String getCode() {
         return code;
     }
 
     public void setCode(String code) {
         this.code = code;
     }
 
     public String getUuid() {
         return uuid;
     }
 
     public void setUuid(String uuid) {
         this.uuid = uuid;
     }
 }
```

`LoginController`处理登录请求，调用`LoginService`进行实际的登录逻辑。

```java
 /**
      * 登录方法
      *
      * @param loginBody 登录信息
      * @return 结果
      */
 @PostMapping("/user/login")
 public ResponseResult login(@RequestBody LoginBody loginBody)
 {
     // 生成令牌
     String token = loginService.login(loginBody.getUserName(), loginBody.getPassword(), loginBody.getCode(),
                                       loginBody.getUuid());
 
     Map<String,Object> map = new HashMap<>();
     map.put("token",token);
     return new ResponseResult(200,"登录成功",map);
 }
```

`LoginService`实现具体的登录逻辑，包括验证码的校验、用户认证和JWT令牌的生成。

```java
 public interface LoginService {
     String login(String username, String password, String code, String uuid);
 }
```

在`LoginService`中，登录前首先检查验证码是否正确，包括验证码的存在性和匹配性。

如果验证码不正确，抛出自定义的`CaptchaNotMatchException`。

```java
 @Service
 public class LoginServiceImpl implements LoginService {
 
     @Autowired
     private AuthenticationManager authenticationManager;
 
     @Autowired
     private RedisCache redisCache;
 
     /**
      * 带验证码登录
      * @param username
      * @param password
      * @param code
      * @param uuid
      * @return: java.lang.String
      */
     @Override
     public String login(String username, String password, String code, String uuid) {
 
         //从redis中获取验证码
         String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
         String captcha = redisCache.getCacheObject(verifyKey);
         redisCache.deleteObject(captcha);
 
         if (captcha == null || !code.equalsIgnoreCase(captcha)){
             throw new CaptchaNotMatchException("验证码错误!");
         }
 
         // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
         Authentication authentication = authenticationManager
                 .authenticate(new UsernamePasswordAuthenticationToken(username, password));
 
 
         //如果认证通过,使用userId生成一个JWT,并将其保存到 ResponseResult对象中返回
         //获取经过身份验证的用户的主体信息
         LoginUser loginUser = (LoginUser) authentication.getPrincipal();
 
         //获取到userID 生成JWT
         String userId = loginUser.getSysUser().getUserId().toString();
         String jwt = JwtUtil.createJWT(userId);
 
         //将用户信息存储在Redis中，在下一次请求时能够识别出用户,userid作为key
         redisCache.setCacheObject("login:"+userId,loginUser);
 
         //封装ResponseResult,并返回
         return jwt;
     }
 }
```

`CaptchaNotMatchException`继承自`AuthenticationException`，用于处理验证码验证失败的情况。

```java
 public class CaptchaNotMatchException extends AuthenticationException {
 
     public CaptchaNotMatchException(String msg) {
         super(msg);
     }
 
     public CaptchaNotMatchException(String msg, Throwable cause) {
         super(msg, cause);
     }
 }
```

在Spring Security的配置类中，确保登录接口和验证码获取接口可以匿名访问。

```java
 // 对于登录接口 允许匿名访问
 .mvcMatchers("/user/login","/captchaImage").anonymous()
```

`mvcMatchers` vs `antMatchers`

使用`mvcMatchers`提供更加严密的URL匹配，增加安全性和灵活性。







