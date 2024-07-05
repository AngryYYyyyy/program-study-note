# 一、初识SpringCloud

## 1、SpringCloud简介

Spring Cloud 是一套全面的==微服务架构构建工具集==，它利用 Spring Boot 的开发便捷性简化了分布式系统基础设施的开发，如配置管理、服务发现、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等。Spring Cloud 旨在为开发者提供快速构建分布式系统中一些常见模式的工具，例如配置管理、服务发现、断路器、智能路由、微代理和事件总线。

## 2、SpringCloud项目

| 项目           | 项目名称                                         |
| -------------- | ------------------------------------------------ |
| 服务注册于发现 | Alibaba Nacos、Netflix Eureka、Apache Zookper    |
| 分布式配置中心 | Alibaba Nacos、Spring Cloud Config               |
| 网关           | Spring Cloud Gateway、Netflix Zull               |
| 限流熔断器     | Alibaba Sentinel、Netflix Hystrix、 Resilience4j |
| 服务调用       | RestTemplate、Open Feign、Dubbo Spring Cloud     |
| 负载均衡       | Spring Cloud LoadBalancer、Netflix Ribbon        |
| 消息总线       | Spring Cloud Bus                                 |
| ...            | ....                                             |

## 3、SpringCloud版本选择

https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E

| Spring Cloud Alibaba Version | Spring Cloud Version       | Spring Boot Version |
| ---------------------------- | -------------------------- | ------------------- |
| 2.1.4.RELEASE                | Spring Cloud Greenwich.SR6 | 2.1.13.RELEASE      |

| Spring Cloud Alibaba Version | Sentinel Version | Nacos Version | Seata Version |
| ---------------------------- | ---------------- | ------------- | ------------: |
| 2.1.4.RELEASE                | 1.8.0            | 1.4.1         |         1.3.0 |

# 二、Nacos作为注册中心

Nacos 是一种==动态服务发现（服务注册）和配置管理平台==，特别适合用于构建云原生应用。它支持微服务架构中的服务发现和服务健康监测，提供了轻量级的配置管理和服务管理功能，适用于发现、配置和管理微服务。

## 1、安装与下载

### （1）源码单机启动

解压进入目录中进行maven编译

```pom
mvn clean install -DskipTests -Drat.skip=true -f pom.xml
```

- 将jdk版本都设置为jdk8
- 设置参数

```xml
 -Dnacos.standalone=true
```

### （2）单机启动服务

```shell
startup.cmd -m standalone
```

将MODE模式改为standalone，这样下次直接双击startup.cmd就可以了

## 2.服务领域模型

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/f5f315ce86494c35bc38bab9d0b52da1.png)

**支持大型互联网公司的需求**：Nacos 的设计允许大型互联网公司进行多集群跨机房部署，以提高服务的可用性和扩展性。

**小公司的使用场景**：对于规模较小的公司，虽然可能不需要复杂的集群和命名空间隔离，但仍可以利用 Nacos 提供的基本服务注册和配置管理功能来简化服务管理和运维。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/0bbad64c99b04261bf9dc1933d863a10.jpg)

Nacos作为一个动态的服务发现和配置服务平台，通过精心设计的服务领域模型，支持微服务架构中的服务发现、配置管理和服务管理，以下是Nacos各个组件的详细解释及其应用：

1. **Namespace（命名空间）**
   - **作用**：用于实现环境隔离。主要用于区分开发、测试和生产环境，确保不同环境之间的配置不会互相影响。
   - **默认值**：`public`
   - **应用场景**：确保测试环境中的实验性功能不会影响到生产环境，从而提高系统的稳定性。

2. **Group（分组）**
   - **作用**：将服务按照业务或逻辑功能组织在一起，便于管理和分类。
   - **默认值**：`DEFAULT_GROUP`
   - **应用场景**：例如，可以将所有交易相关的服务（如订单服务、支付服务）划分到同一个分组，方便进行集中管理和监控。

3. **Service（服务）**
   - **作用**：标识一个微服务的集合，是服务注册与发现的核心。
   - **应用场景**：Service层面上的配置和服务发现主要用于管理同一业务下的不同微服务。

4. **Cluster（集群）**
   - **作用**：对服务进行进一步的逻辑划分，通常用于区分服务的不同部署区域或集群。
   - **默认值**：`DEFAULT`
   - **应用场景**：在地理位置分散的环境中，如北方用户主要访问北京集群，南方用户主要访问上海集群，从而减少延迟和提高服务响应速度。

5. **Instance（实例）**
   - **作用**：服务的具体运行实例，每个实例代表部署在特定服务器上的单个服务应用。
   - **应用场景**：实现服务的高可用，如一个实例失败时，其他实例能够接管工作，保证服务的连续性。在整个集群失效的情况下，其他集群可以继续提供服务。

通过以上详细的分层介绍，Nacos 为大型互联网公司提供了强大的支持，特别是在处理多集群和跨机房部署的场景中，有效地提高了服务的可用性和扩展性。对于小型企业而言，虽然可能不需要如此复杂的设置，但Nacos的灵活性也使其能够简化服务管理和运维，从而满足不同规模企业的需求。

## 3.Nacos的使用

以msb-stock与msb-order为例

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/291eb48cdd134d17894d9d3c4b87334d.jpg)

### （1）导入依赖

```xml
  <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  </dependency>
```

### （2）相关配置

**启动注释**

```java
@EnableDiscoveryClient
@SpringBootApplication
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class);
    }
}
```

```java
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
```

**application.yml**

```yaml
spring:
  cloud:
    nacos:
      discovery:
        service: msb-stock
        server-addr: localhost:8848
```

```yml
spring:
  cloud:
    nacos:
      discovery:
        service: msb-order
        server-addr: localhost:8848
```

### （3）业务

示例：实现一个订单系统调用库存系统的减少库存服务

```java
  @GetMapping("/order/create")
    public String createOrder(Integer productId,Integer userId){
        //RestTemplate 被用于调用库存服务（msb-stock）的 reduce 接口，以减少指定 productId 的库存数量。
        String result = restTemplate.getForObject("http://msb-stock/stock/reduce//" + productId, String.class);
        return "下单成功";
    }
```

先简单介绍一下RestTemplate

`RestTemplate` 是 Spring 框架提供的一个用于同步客户端 HTTP 请求的模板工具类，主要用于服务间的调用。它封装了客户端的 HTTP 交互，简化了远程服务器的资源访问和消费。

**使用 `RestTemplate` 的步骤**

1. **创建实例**：通常 `RestTemplate` 的实例是在 Spring 的配置类中被创建并通过依赖注入的方式在应用中使用。

   ```java
   @Bean
   public RestTemplate restTemplate() {
       return new RestTemplate();
   }
   ```

2. **发起请求**：使用 `RestTemplate` 的方法来发起请求。例如 `getForObject`, `postForObject` 等。

   ```java
   String result = restTemplate.getForObject("http://服务提供者的URL/资源路径", String.class);
   ```

不过，在使用 `RestTemplate` 进行服务间调用时，如果直接使用服务名称（如 `msb-stock`），而不是具体的 IP 地址或端口号，`RestTemplate` 默认是无法解析和路由到正确的服务实例的。这是因为 `RestTemplate` 默认不支持服务发现和负载均衡。

**服务发现**：在微服务架构中，服务实例通常会动态注册到服务注册中心（如Eureka、Consul）。`RestTemplate` 需要与这些服务注册中心集成，以便能够解析服务名（如 `msb-stock`）到具体的服务实例列表。

**负载均衡**：从服务列表中选择一个合适的服务实例进行调用是通过负载均衡器来实现的。在 Spring Cloud 中，可以通过集成 Ribbon 来自动为 `RestTemplate` 提供负载均衡功能。Ribbon 是一个客户端负载均衡工具，它可以拦截 `RestTemplate` 的请求，并将逻辑服务名（如 `msb-stock`）转换为实际的服务地址（如 `localhost:11001`）。要使 `RestTemplate` 支持 Ribbon，需要在 `RestTemplate` 的实例化过程中添加 `LoadBalancerInterceptor`。这个拦截器负责将服务名替换为具体的服务地址，并实现负载均衡。

下面是启用服务发现、创建支持负载均衡的 `RestTemplate`

```java
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new LoadBalancerInterceptor(loadBalancerClient)));
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
```

- **`@EnableDiscoveryClient`**: 这个注解使得您的应用成为一个服务发现的客户端

- **`LoadBalancerClient`**: 这是一个接口，定义了获取服务实例的方法，它是负载均衡器的抽象。Spring Cloud 提供了多种实现（如 Ribbon）。
- **`new LoadBalancerInterceptor(loadBalancerClient)`**: 这是一个拦截器，它在 `RestTemplate` 发出请求之前拦截请求，使用 `LoadBalancerClient` 来选择一个服务实例，并重写请求的 URI，从而将请求发送到合适的服务实例。

```java
public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
    URI originalUri = request.getURI();
    String serviceName = originalUri.getHost();
    Assert.state(serviceName != null, "Request URI does not contain a valid hostname: " + originalUri);
    //将服务名转为URL，即msb-stock--->URL
    return (ClientHttpResponse)this.loadBalancer.execute(serviceName, this.requestFactory.createRequest(request, body, execution));
}
```

- **`setInterceptors`**: 这个方法将负载均衡拦截器添加到 `RestTemplate` 的拦截器链中。这使得每次使用这个 `RestTemplate` 发出请求时，请求都会经过负载均衡拦截器进行处理。

也可以使用注解`@LoadBalanced`

```java
@LoadBalanced
@Bean
public RestTemplate restTemplate(){
    return new RestTemplate();
}
```

## 4.Nacos注册中心的原理

Nacos 注册中心的工作原理基于“服务注册与发现”的模式，它涉及服务提供者和服务消费者的交互过程。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1678263581076/bc47487f858e4d8ab7bbc94a7cbea2ce.png)

### （1）服务提供者注册流程

- **启动注册**：服务提供者启动时，会向Nacos Server注册自己。这通常包括服务的基本信息，如服务名、IP地址、端口号、健康状态等。
  
- **心跳检测**：一旦注册，服务提供者会定期向Nacos Server发送心跳信号。这是为了让Nacos知道服务仍然活跃和可用。心跳机制帮助Nacos维护一个最新和准确的服务列表，当服务不再发送心跳时，Nacos会将其视为下线，并更新服务列表。

### （2）服务消费者发现流程

- **服务查询与缓存**：服务消费者在启动或执行过程中，需要调用其他服务时，会向Nacos Server查询所需服务的可用实例列表。服务消费者通常会缓存这些信息，并根据本地缓存或实时从Nacos获取的数据来调用特定的服务实例。
  
- **服务调用**：服务消费者根据获取的服务实例信息，执行对服务提供者的调用。调用可能基于某种负载均衡策略，如随机、轮询或基于权重等。

### （3）服务同步

- **服务状态同步**：在Nacos集群环境中，所有Nacos节点需要同步数据，确保每个节点的数据都是最新和一致的。这确保了服务消费者无论连接到哪个Nacos节点，都能获取到一致和准确的服务信息和配置数据。

## 5.Nacos服务注册源码解析

### （1）入口

`spring.factories` 文件是 Spring 框架中用于自动配置的一个核心组件，特别是在 Spring Boot 项目中扮演了重要角色。它主要用于配置和启用自动化的服务发现机制，允许框架在应用启动时自动注册和加载各种组件、配置类和其他服务。

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration,\
  com.alibaba.cloud.nacos.ribbon.RibbonNacosAutoConfiguration,\
  com.alibaba.cloud.nacos.endpoint.NacosDiscoveryEndpointAutoConfiguration,\
  com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration,\
  com.alibaba.cloud.nacos.discovery.NacosDiscoveryClientConfiguration,\
  com.alibaba.cloud.nacos.discovery.reactive.NacosReactiveDiscoveryClientConfiguration,\
  com.alibaba.cloud.nacos.discovery.configclient.NacosConfigServerAutoConfiguration,\
  com.alibaba.cloud.nacos.NacosServiceAutoConfiguration
org.springframework.cloud.bootstrap.BootstrapConfiguration=\
  com.alibaba.cloud.nacos.discovery.configclient.NacosDiscoveryClientConfigServiceBootstrapConfiguration
org.springframework.context.ApplicationListener=\
  com.alibaba.cloud.nacos.discovery.logging.NacosLoggingListener
```

## 6.集群搭建

### （1）配置数据库

在Nacos集群模式下，多个Nacos服务器实例需要共享状态信息，包括服务注册信息、健康状态、配置更新等。所有的Nacos实例都会读取和更新同一个数据库，以保证集群中的所有实例都有一致的数据视图。这样可以确保服务发现和配置管理的准确性和一致性，即服务消费者总是能发现最新的、健康的服务提供者，而服务的配置也保持最新。

修改每个服务数据库链接

```properties
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&serverTimezone=UTC
db.user=root
db.password=root
```

### （2）修改集群配置文件

将cluster.conf.example 改为 cluster.conf，并添加对应服务集群Ip:port,

### （3）集群启动

```shell
startup.cmd -m cluster
```

### （4）安装Nginx

修改nginx.conf

```java
worker_processes  1;
events {
    worker_connections  1024;
}
stream {
      upstream nacos {
        server 192.168.1.11:8848;
        server 192.168.1.11:8868;
        server 192.168.1.11:8888;
      }
     server {
        listen  81;
        proxy_pass nacos;
     }
}
```

启动nginx

```shell
start nginx.exe
```

重新加载

```shell
nginx.exe -s reload
```

### （5）项目链接

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/625ca1aabd514674a0addb730f84bf62.png)

## 7.Distro协议

