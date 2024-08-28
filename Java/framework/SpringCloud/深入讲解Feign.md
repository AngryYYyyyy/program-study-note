# 五、OpenFeign使用

## 1、 使用Feign实现远程HTTP调用

### 1.1、常见HTTP客户端

- **HttpClient**

  HttpClient 是 Apache Jakarta Common 下的子项目，用来提供高效的、最新的、功能丰富的支持 Http 协 议的客户端编程工具包，并且它支持 HTTP 协议最新版本和建议。HttpClient 相比传统 JDK 自带的 URLConnection，提升了易用性和灵活性，使客户端发送 HTTP 请求变得容易，提高了开发的效率。
- **Okhttp**

  一个处理网络请求的开源项目，是安卓端最火的轻量级框架，由 Square 公司贡献，用于替代 HttpUrlConnection 和 Apache HttpClient。OkHttp 拥有简洁的 API、高效的性能，并支持多种协议 （HTTP/2 和 SPDY）。
- **HttpURLConnection**

  HttpURLConnection 是 Java 的标准类，它继承自 URLConnection，可用于向指定网站发送 GET 请求、 POST 请求。HttpURLConnection 使用比较复杂，不像 HttpClient 那样容易使用。
- **RestTemplate**

  RestTemplate 是 Spring 提供的用于访问 Rest 服务的客户端，RestTemplate 提供了多种便捷访问远程 HTTP 服务的方法，能够大大提高客户端的编写效率。

### 1.2、什么是Fegin

Feign是Netflix开源的声明式HTTP客户端

### 1.3、优点

Feign可以做到使用 HTTP 请求远程服务时就像调用本地方法一样的体验，开发者完全感知不到这是远程方 ，更感知不到这是个 HTTP 请求。

【Fegin和OpenFeign的区别：Spring Cloud openfeign对Feign进行了 增强，使其支持Spring MVC注解，另外还整合了Ribbon和Eureka，从而使得Feign的使用更加方便 】

### 1.4、重构以前的代码：

三板斧：

1. 引入依赖

   ```pom
    <dependency>
    	<groupId>org.springframework.cloud</groupId>
    	<artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
   ```
2. 写启动注解

   ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/2b6a0c7d6a644d3383445287d24233c2.png)
3. 增加配置

   ```
   暂时没有配置
   ```
4. 进行改造

增加一个feign，他也是基于ribbon进行操作的，所以以前我们学的ribbon在这里也适用

```java
package com.msb.order.feignclient;
@FeignClient(name = "msb-stock")
public interface StockFeignClient {

    @GetMapping("/stock/reduce/{productId}")
    String reduce(@PathVariable Integer productId);
}

```

```java
package com.msb.order.controller;
@Slf4j
@RestController
public class OrderController {
    @Autowired
    private StockFeignClient stockFeignClient;

    @GetMapping("/order/create")
    public String createOrder(Integer productId,Integer userId){
        String reduce = stockFeignClient.reduce(productId);
        log.info("减库存成功：{}",reduce);
        return "下单成功";
    }
}
```

我们在这里看一下我们启动了两个微服务，他们都发起了调用，也就feign后台实现了负载均衡。我们可以看一下他的继承关系，我们发现fegin里面有依赖了ribbon，所以他除了增强了我们springmvc注解，同时也整合我们的ribbon

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/c6aeaf281ae5445c9f973ef7f6e66284.png)

## 2、Feign的组成

| 接口               | 作用                                                         | 默认值                                                       |
| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Feign.Builder      | Feign的入口                                                  | Feign.Builder                                                |
| Client             | Feign底层用什么去请求                                        | 和Ribbon配合时：LoadBalancerFeignClient&#x3c;br />不和Ribbon配合时：Feign.Client.Default |
| Contract           | 契约，注解支持                                               | SpringMVC Contract                                           |
| Encoder            | 解码器，用于将独享转换成HTTP请求消息体                       | SpringEncoder                                                |
| Decoder            | 编码器，将相应消息体转成对象                                 | ResponseEntityDecoder                                        |
| Logger             | 日志管理器                                                   | Slf4jLogger                                                  |
| RequestInterceptor | 用于为每个请求添加通用逻辑（拦截器，例子：比如想给每个请求都带上heared） | 无                                                           |

Feign.Client.Default 利用的是我们默认的HttpURLConnection，他是没有连接池的。也没有资源管理这个概念，性能不是很好，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/ecf6801bc7934b6bb9c3e8e1b6cc8ba1.png)

## 3、细粒度配置自定义

### 3.1、代码方式-指定日志级别

feign默认是不打印任何日志的，但是我们希望打印一些日志信息。比如调用的时间。

Feign设置方式就不同的，他有四个级别如下：

| 级别          | 打印日志内容                                  |
| ------------- | --------------------------------------------- |
| NONE（默认值) | 不记录任何日志                                |
| BASIC         | 仅记录请求方法、URL、响应状态代码以及执行时间 |
| HEADERS       | 记录BASIC级别的基础上，记录请求和响应的header |
| FULL          | 记录请求和响应的header、body和元数据          |

#### 1.1 指定springboot日志

Feign日志是基于Spring boot的日志所以先设置SpringBoot日志：

```properties
logging:
  level:
    com.msb: debug
```

#### 1.2 创建配置类

BASIC使用于生产环境 FULL适用于开发环境

创建配置类UserFeignConfiguration ，注意这里并没有增加@Configuration

```java
package com.msb.order.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
//这里不能增加@Configuration，如果添加了就会变为全局配置文件。
public class StockFeignConfiguration {
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
```

更改请求feign的配置文件。

#### 1.3 将配置类引入

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/c39b3a69133946489fb9a0cdf1eecbae.png)

### 3.2、属性方式-指定日志级别

feign.client.config.&#x3c;feignName>.loggerLevel:full

```properties
logging:
  level:
    com.msb: debug
feign:
  client:
    config:
      msb-stock:
        loggerLevel: full
```

## 4、全局配置自定义

### 1、代码配置

- 方式一:让父子上下文ComponentScan重叠（强烈不建议使用）

  ```java
  @Configuration
  public class StockFeignConfiguration {
      /**
       * 日志级别
       * 通过源码可以看到日志等级有 4 种，分别是：
       * NONE：不输出日志。
       * BASIC：只输出请求方法的 URL 和响应的状态码以及接口执行的时间。
       * HEADERS：将 BASIC 信息和请求头信息输出。
       * FULL：输出完整的请求信息。
       */
      @Bean
      public Logger.Level level(){
          return Logger.Level.FULL;
      }
  }
  ```
- 方式二【唯一正确的途径】:
  EnableFeignClients(defaultConfiguration=xxx.class)

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/52b0da9fc29b47ecaaaf7d3255187810.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/fcc457261143470e856cd1dda7043ab7.png)

### 2、属性配置

```yaml
logging:
  level:
    com.msb: debug
feign:
  client:
    config:
      default:
        loggerLevel: full
```

## 5、 支持的配置项

### 5.1、契约配置

Spring Cloud 在 Feign 的基础上做了扩展，可以让 Feign 支持 Spring MVC 的注解来调用。原生的 Feign 是不支持 Spring MVC 注解的，如果你想在 Spring Cloud 中使用原生的注解方式来定义客户端也是 可以的，通过配置契约来改变这个配置，Spring Cloud 中默认的是 SpringMvcContract。

#### 1.1代码方式

```java
    /**
     * 修改契约配置，这里仅仅支持Feign原生注解
     * 这里是一个扩展点，如果我们想支持其他的注解，可以更改Contract的实现类。
     * @return
     */
    @Bean
    public Contract feignContract(){
        return new Contract.Default();
    }
```

注意：这里修改了契约配置之后，我们就只能用Fegin的原生注解

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/97e5093b1f8647e5a63a61fe6596a44d.png)

#### 1.2 属性方式

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/f467eb9e5c31421b854cfb0a27ae9d35.png)

### 5.2、编解码

Feign 中提供了自定义的编码解码器设置，同时也提供了多种编码器的实现，比如 Gson、Jaxb、Jackson。 我们可以用不同的编码解码器来处理数据的传输。。

扩展点：Encoder & Decoder

默认我们使用：SpringEncoder和SpringDecoder

```java
package feign.codec;
public interface Encoder {
  void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException;
}
```

```java
package feign.codec;
public interface Decoder {
  Object decode(Response response, Type type) throws IOException, DecodeException, FeignException;
}
```

#### 2.1 代码方式

```java
@Bean
public Decoder decoder(){
    return new CustomDecoder();
}
@Bean
public Encoder encoder(){
    return new CustomEncoder();
}
```

#### 2.2 属性方式

```yaml
feign:
  client:
    config:
      #想要调用的微服务的名称
      msb-user:
        encoder: com.xxx.CustomDecoder
        decoder: com.xxx..CustomEncoder
```

### 5.3、拦截器

通常我们调用的接口都是有权限控制的，很多时候可能认证的值是通过参数去传递的，还有就是通过请求头 去传递认证信息，比如 Basic 认证方式。

#### 3.1 扩展点：

```java
package feign;

public interface RequestInterceptor {
  void apply(RequestTemplate template);
}
```

#### 3.2 使用场景

1. 统一添加 header 信息；
2. 对 body 中的信息做修改或替换；

#### 3.3 自定义逻辑

```java
package com.msb.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignAuthRequestInterceptor implements RequestInterceptor {

    private String tokenId;

    public FeignAuthRequestInterceptor(String tokenId) {
        this.tokenId = tokenId;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization",tokenId);
    }
}
```

```java
package com.msb.order.configuration;

import com.msb.order.interceptor.FeignAuthRequestInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${feign.tokenId}")
    private String tokenId;

    /**
     * 自定义拦截器
     * @return
     */

    @Bean
    public FeignAuthRequestInterceptor feignAuthRequestInterceptor(){
        return new FeignAuthRequestInterceptor(tokenId);
    }
}
```

```yaml
feign:
  tokenId: d874528b-a9d9-46df-ad90-b92f87ccc557
```

在msb-stock项目中增加springmvc中的拦截器

拦截器代码：

```java
package com.msb.stock.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        // 逻辑认证
        String authorization = request.getHeader("Authorization");
        log.info("获取的认证信息 Authorization：{}",authorization);
        if(StringUtils.hasText(authorization)){
            return true;
        }
        return false;
    }
}
```

增加类配置:

```java
package com.msb.user.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor());
    }
   
```

### 5.4、Client 设置

Feign 中默认使用 JDK 原生的 URLConnection 发送 HTTP 请求，我们可以集成别的组件来替换掉 URLConnection，比如 Apache HttpClient，OkHttp。

#### 4.1 扩展点

Feign发起调用真正执行逻辑：**feign.Client#execute （扩展点）**

```java
public interface Client {
  Response execute(Request request, Options options) throws IOException;
 }
```

#### 4.2 配置Apache HttpClient

1. 引入依赖

   ```pom
   <dependency>
       <groupId>io.github.openfeign</groupId>
       <artifactId>feign-httpclient</artifactId>
   </dependency>
   ```
2. 修改yml配置

   开启feign ,这里可以不用配置，可以参考源码分析

   ```yaml
   feign:
     httpclient:
       #使用apache httpclient做请求，而不是jdk的HttpUrlConnection
       enabled: true
       # feign最大链接数 默认200
       max-connections: 200
       #feign 单个路径的最大连接数  默认 50
       max-connections-per-route: 50
   ```
3. 源码分析 FeignAutoConfiguration

   ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/6a853f1760f244cfa2a1bdac4478ad0a.png)

此时默认增加一个ApacheHttpCient实现类

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/b222b81a4c71455687bc0f877b7a50ca.png)

#### 4.3 设置OkHttp

1. 引入依赖

   ```pom
   <dependency>
       <groupId>io.github.openfeign</groupId>
       <artifactId>feign-okhttp</artifactId>
   </dependency>
   ```
2. 增加配置

   ```yaml
   feign:
     okhttp:
       enabled: true
       #线程池可以使用httpclient的配置   
     httpclient:
       max-connections: 200
       max-connections-per-route: 50
   ```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/b5f8ae0d0854434ab016e02305dbc9ce.png)

3、源码分析 FeignAutoConfiguration

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/761aebce94cd47978b363d77b3973720.png)

### 5.5、超时配置

通过 Options 可以配置连接超时时间和读取超时时间，Options 的第一个参数是连接的超时时间（ms）， 默认值是 10s；第二个是请求处理的超时时间（ms），默认值是 60s。

Request.Options

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/83960fc994ec4720a1e3bcf983ec0709.png)

#### 5.1 代码配置

```java
@Bean
public Request.Options options(){
    return new Request.Options(2000,50000);
}
```

msb-stock改造

```java
@GetMapping("query")
public User queryInfo(User user){
    try {
        Thread.sleep(10*1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return user;
}
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/0e02e5e964ea4f09bb46bfb1c2c6eded.png)

## 6、推荐配置方式

- 尽量使用属性配置，属性方式实现不了的情况下再考虑用代码配置
- 在同一个微服务内尽量保持单一性，比如统一使用属性配置，不要两种方式混用，增加定位代码的复杂性