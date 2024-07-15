# 一、Nacos定义

Nacos 是一种==服务注册和配置管理平台==，特别适合用于构建云原生应用。它支持微服务架构中的服务发现和服务健康监测，提供了轻量级的配置管理和服务管理功能，适用于发现、配置和管理微服务。

## 1.安装与下载

### （1）源码

> nacos1.4.1

maven编译，注意jdk版本为1.8，在idea中启动，设置参数` -Dnacos.standalone=true`

```pom
mvn clean install -DskipTests -Drat.skip=true -f pom.xml
```

### （2）服务端

```shell
startup.cmd -m standalone #单机启动
```

## 2.领域模型

### （1）数据模型

Nacos 的数据模型主要涉及如何存储和管理服务注册和配置数据。

- **Namespace**：命名空间，可以用来支持不同的开发环境（如开发、测试、生产环境）之间的隔离。
- **Group**：配置分组，用来将不同的配置分组管理，实现配置隔离和细粒度控制。

- **Service/Data ID**：标识一个微服务的集合/标识一个配置信息的ID。

<img src="D:\note\微服务与分布式\springcloud\nacos\assets\image-20240612130704913.png" alt="image-20240612130704913" style="zoom:67%;" />

### （2）服务领域模型

- **Service**：服务的抽象，代表了一个可以被调用的功能单元，比如一个微服务或者一个 API。
- **Cluster**：集群，由多个服务实例组成，通常在同一个区域内。
- **Instance**：服务实例，代表服务的一个具体实现，通常是一个运行中的服务应用实例。

<img src="D:\note\微服务与分布式\springcloud\nacos\assets\image-20240612130933739.png" alt="image-20240612130933739" style="zoom:67%;" />

### （3）配置领域模型

在配置管理领域，Nacos 主要关注于提供动态的、集中式的、版本化的服务配置管理。

![image-20240612131026648](D:\note\微服务与分布式\springcloud\nacos\assets\image-20240612131026648.png)

## 3.简单使用

> Nacos Version 1.4.1

### （1）版本选择

> https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
>

| Spring Cloud Alibaba Version | Spring Cloud Version       | Spring Boot Version |
| ---------------------------- | -------------------------- | ------------------- |
| 2.1.4.RELEASE                | Spring Cloud Greenwich.SR6 | 2.1.13.RELEASE      |

| Spring Cloud Alibaba Version | Sentinel Version | Nacos Version | Seata Version |
| ---------------------------- | ---------------- | ------------- | ------------: |
| 2.1.4.RELEASE                | 1.8.0            | 1.4.1         |         1.3.0 |

### （2）注册中心

导入依赖，pom.xml文件

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <version>版本号</version>
</dependency>
```

相关配置

bootstrap.yml

```yml
server:
  port: 10001 #服务端口
spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #nacos-server地址
        service: service-name #服务名
```

启动类

```java
@SpringBootApplication
@EnableDiscoveryClient //服务发现客户端
public class NacosAll {
    public static void main(String[] args) {
        SpringApplication.run(NacosAll.class,args);
    }
}
```

业务

> 详细参考gitub

![image-20240612204634386](D:\note\微服务与分布式\springcloud\nacos\assets\image-20240612204634386.png)

### （3）配置中心

导入依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

相关配置

bootstarp.yml

```yml
spring:
  cloud:
    nacos:
      discovery:
        service: service01
      config:
        server-addr: localhost:8848
        prefix: service01 #前缀
        file-extension: yml #文件后缀
  profiles:
    active: dev #配置环境
  #service01-dev.yml
```

nacos配置中心service01-dev.yml

```yml
server:
    port: 10004
name: zhangsan
```

业务

```java
@RestController
public class ConfigController {
    @Value("${name}")
    private String name;
    @GetMapping("/name")
    public String getName(){
        return name;
    }
}
```

# 二、Nacos注册中心

## 1.主要流程

![image-20240614085310399](D:\note\微服务与分布式\springcloud\nacos\assets\image-20240614085310399.png)

### （1）服务提供者

- **服务注册**：服务提供者在启动时，需要向 Nacos Server 注册自身。注册过程包括提交服务的关键信息，如服务名称、IP地址、端口号以及健康状态等。这一步骤是确保 Nacos 服务列表及时更新和管理所有活动服务的关键。
- **心跳检测**：注册后，服务提供者将定期向 Nacos Server 发送心跳信号。这个机制的目的是让 Nacos Server 知道该服务仍然处于活跃状态。通过心跳检测，Nacos 能够维护一个精确的服务活动列表，当服务停止发送心跳时，Nacos 会将其标记为不可用并更新服务列表。

### （2）**服务消费者**

- **服务发现**：服务消费者需要调用其他服务时，会查询 Nacos Server 以获取所需服务的可用实例列表。这些信息通常会被服务消费者缓存，以减少网络开销和加速服务调用过程。
- **服务调用**：服务消费者根据从 Nacos 获取的服务实例信息，调用相应的服务提供者。这一过程可能会涉及到负载均衡策略，如随机选择、轮询或基于实例权重的调度，以优化资源的使用和提高服务的响应速度。

### （3）**服务状态同步**

- **数据同步**：在 Nacos 集群环境中，所有节点之间需要进行持续的数据同步。这确保了每个节点都持有最新且一致的服务信息和配置数据。这种同步机制保证了服务消费者无论连接到哪个 Nacos 节点，都能获得一致且准确的服务信息。

## 2.Distro协议

## 3.源码剖析

# 三、配置中心

## 1.配置加载优先级

在 Spring Cloud 环境中，配置加载和优先级遵循以下顺序：

1. **系统参数和命令行参数**

2. **`bootstrap.yml` 或 `bootstrap.properties`**

3. **从配置中心加载的配置**

   ```properties
   #作用：顺序
   #${application.name}-${profile}.${file- extension}   
   #${application.name}.${file-extension}   
   #${application.name}   
   #extensionConfigs  扩展配置文件
   #sharedConfigs  多个微服务公共配置 redis
   ```

4. **应用的主配置文件 `application.yml` 或 `application.properties`**

5. **内部默认配置**

## 2.长轮询机制

### （1）动态监听模式

#### Push 模式（推送）

在 push 模式中，服务器主动向客户端发送（推送）数据。当服务器端的数据发生变化时，服务器会立即将更新的数据发送给所有订阅了该数据的客户端。这种模式非常适合对实时性要求极高的应用场景。

**优点**：及时性

**缺点**：资源消耗

#### Pull 模式（拉取）

在 pull 模式中，客户端定期向服务器发送请求以获取最新的数据。这种模式依赖客户端主动请求数据，适用于对数据实时性要求不是特别高的场景。

**优点**：减轻服务器负担

**缺点**：数据延迟、过多的请求

### （2）Nacos长轮询pull

![image-20240614100524829](D:\note\微服务与分布式\springcloud\nacos\assets\image-20240614100524829.png)

**客户端发起长连接请求**

- 客户端发送请求至服务端，携带当前配置的版本信息，通知服务端客户端的当前状态，并请求最新的配置信息。

**服务端接收并处理请求**

- **初次检查**：判断自上次请求以来配置是否有更新。
- **挂起请求**：
  - 如果无更新，请求进入等待队列，挂起状态维持至近超时前。
  - 设置定时任务（通常延迟29.5秒），用于最终的超时处理前的再次检查。

**持续监控配置变更**

- **配置更新触发**：任何配置更新立即导致挂起的请求被处理，定时任务取消。
- **响应发送**：服务端发送包含最新配置的响应给客户端。

**处理超时**

- **定时任务执行**：到达29.5秒后，任务执行，服务端进行最后一次配置更新检查。
- **超时响应**：如果没有发现更新，发送超时响应或不包含更新的标准响应。

客户端处理响应并重新请求

- **处理响应**：客户端接收并处理来自服务端的数据，无论是更新的配置还是超时响应。
- **重新请求**：处理完成后，客户端立即发起新的长轮询请求，以继续监控未来的配置变化。









