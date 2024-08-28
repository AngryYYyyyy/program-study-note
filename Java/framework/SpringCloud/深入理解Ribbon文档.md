# 三、Ribbon使用以及原理

## 1、负载均衡的两种方式

- 服务器端负载均衡

  传统的方式前端发送请求会到我们的的nginx上去，nginx作为反向代理，然后路由给后端的服务器，由于负载均衡算法是nginx提供的，而nginx是部署到服务器端的，所以这种方式又被称为服务器端负载均衡。

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/dc81b37551a24364b179126ea2227cee.png)
- 客户端侧负载均衡

现在有三个实例，内容中心可以通过discoveryClient 获取到用户中心的实例信息，如果我们再订单中心写一个负载均衡  的规则计算请求那个实例，交给restTemplate进行请求，这样也可以实现负载均衡，这个算法里面，负载均衡是有订单中心提供的，而订单中心相对于用户中心是一个客户端，所以这种方式又称为客户端负负载均衡。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/e378273029ef4931967dcf528a8ade10.png)

## 2、手写一个客户端侧负载均衡器

◆随机选择实例

```java
@Autowired
private DiscoveryClient discoveryClient;
@GetMapping("/order/create")
public String createOrder(Integer productId,Integer userId){
    List<ServiceInstance> instances = discoveryClient.getInstances("msb-stock");
    List<String> targetUrls = instances.stream()
        // 数据变换
        .map(instance -> instance.getUri().toString() + "/stock/reduce")
        .collect(Collectors.toList());
    int i = ThreadLocalRandom.current().nextInt(targetUrls.size());
    String targetUrl = targetUrls.get(i);
    log.info("请求求目标地址：{}",targetUrl);
    String result = restTemplate.getForObject(targetUrl +"/"+ productId, String.class);
    log.info("进行减库存:{}",result);
    return "下单成功";
}
```

## 3、使用Ribbon实现负载均衡

Ribbon是什么?
Netflix开源的客户端侧负载均衡器

更加直观说就是ribbon就是简化我们这段代码的小组件，不过他比我们的代码要强大一些，他给他们提供了丰富的负载均衡算法。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/0101314c4f4648afaad2af46bb44e213.jpg)

引入ribbon :三步骤： 加依赖，启动注解，写配置

不需要加，nacosdiscovery，已经给添加了依赖，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/08e8e41cae584691bd8f1b22eec9fee0.png)

写注解，需要写到RestTemplate上面。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/fddeaec92a1143899ed62b27e5d9163f.png)

第三步：写配置

没有配置。

改造我们的请求：

url:改为 下面 当请求发送的发送的时候ribbon会将msb-stock进行转化为我们nacos里面中的地址。并且进行负载均衡算法，进行请求，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/b2ef91dca69241f59cc8cbbc96d12180.png)

## 4、Ribbon的重要接口 以及内置负载均衡规则

### 4.1、Ribbon重要接口

| 接口                    | 作用                       | 默认值                                                       |
| ----------------------- | -------------------------- | ------------------------------------------------------------ |
| IClientConfig           | 读取配置                   | DefaultclientConfigImpl                                      |
| IRule                   | 负载均衡规则，选择实例     | ZoneAvoidanceRule                                            |
| IPing                   | 筛选掉ping不通的实例       | 默认采用DummyPing实现，该检查策略是一个特殊的实现，&#x3c;br />实际上它并不会检查实例是否可用，而是始终返回true，默认认为所&#x3c;br />有服务实例都是可用的. |
| ServerList&#x3c;Server> | 交给Ribbon的实例列表       | **Ribbon**: ConfigurationBasedServerList&#x3c;br /> **Spring Cloud Alibaba**: NacosServerList |
| ServerListFilter        | 过滤掉不符合条件的实例     | ZonePreferenceServerListFilter                               |
| ILoadBalancer           | Ribbon的入口               | ZoneAwareLoadBalancer                                        |
| ServerListUpdater       | 更新交给Ribbon的List的策略 | PollingServerListUpdater                                     |

### 4.2、Ribbon负载均衡规则

| 规则名称                 | 特点                                                         |
| ------------------------ | ------------------------------------------------------------ |
| RandomRule               | 随机选择一个Server                                           |
|                          |                                                              |
| RetryRule                | 对选定的负责均衡策略机上充值机制，在一个配置时间段内当选择Server不成功，则一直尝试使用subRule的方式选择一个可用的Server |
| RoundRobinRule           | 轮询选择，轮询index，选择index对应位置Server                 |
| WeightedResponseTimeRule | 根据相应时间加权，相应时间越长，权重越小，被选中的可能性越低 |
| ZoneAvoidanceRule        | （默认是这个）该策略能够在多区域环境下选出最佳区域的实例进行访问。在没有Zone的环境下，类似于轮询（RoundRobinRule） |

## 5、Ribbon的配置

### 5.1 类配置方式

```java
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule(){
        //随机选择
        return new RandomRule();
    }
}
```

```java
/**
* 指定配置
**/
@Configuration
@RibbonClient(name = "msb-stock",configuration = RibbonConfiguration.class)
public class UserRibbonConfiguration {
}
```

### 5.2 属性配置

将前面的配置注释掉：

如下进行配置：

```java
msb-stock:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
```

### 5.3 优先级高低

java配置的要高， 我们可以类配置的路由规则为随机（RandomRule），然后属性配置为轮训（RoundRobinRule）;

测试是随机则java配置高于属性配置

### 5.4 全局配置

@RibbonClients(defaultConfiguration=xxx.class)

就是将RibbonClient改为RibbonClients  将configuration改为defaultConfiguration

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/d036a2e9c84d4371a17c2e01aeded70d.png)

## 6、饥饿加载

ribbon默认是懒加载的，只有第一层调用的时候才会生成userCenter的ribbonClient，这就会导致首次调用的会很慢的问题。

```yaml
ribbon:
  eager-load:
    enabled: true
      clients: msb-stock
```

# 四、Loadbalance的使用

## 1、介绍

Spring Cloud LoadBalancer是Spring Cloud官方自己提供的客户端负载均衡器,抽象和实现，用来替代Ribbon（已经停更），

## 2、Ribbon和Loadbalance 对比

| **组件**                  | **组件提供的负载策略**                                       | **支持负载的客户端**           |
| ------------------------- | ------------------------------------------------------------ | ------------------------------ |
| Ribbon                    | 随机 RandomRule&#x3c;br />轮询 RoundRobinRule    &#x3c;br />重试 RetryRule&#x3c;br />最低并发 BestAvailableRule&#x3c;br />可用过滤 AvailabilityFilteringRule&#x3c;br />响应时间加权重 ResponseTimeWeightedRule&#x3c;br />区域权重 ZoneAvoidanceRule | Feign或openfeign、RestTemplate |
| Spring Cloud Loadbalancer | RandomLoadBalancer 随机（高版本有，此版本没有RoundRobinLoadBalancer 轮询（默认） | Ribbon 所支持的、WebClient     |

LoadBalancer 的优势主要是，支持**响应式编程**的方式**异步访问**客户端，依赖 Spring Web Flux 实现客户端负载均衡调用。

## 3、整合LoadBlance

> 注意如果是Hoxton之前的版本，默认负载均衡器为Ribbon，需要移除Ribbon引用和增加配置spring.cloud.loadbalancer.ribbon.enabled: false。

**移除ribbon依赖，增加loadBalance依赖**

```pom
<!--nacos-服务注册发现-->
<dependency>
	<groupId>com.alibaba.cloud</groupId>
	<artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
	<exclusions>
		<!--将ribbon排除-->
		<exclusion>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
		</exclusion>
	</exclusions>
</dependency>


<!--添加loadbalanncer依赖, 添加spring-cloud的依赖-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

## 4、自定定义负载均衡器

```java
package com.msb.order.loadbalance;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.reactive.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.reactive.Request;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Random;

public class CustomRandomLoadBalancerClient implements ReactorServiceInstanceLoadBalancer {
 
    // 服务列表
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
 
    public CustomRandomLoadBalancerClient(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }
 
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable();
        return supplier.get().next().map(this::getInstanceResponse);
    }
 
    /**
     * 使用随机数获取服务
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(
            List<ServiceInstance> instances) {
        System.out.println("进来了");
        if (instances.isEmpty()) {
            return new EmptyResponse();
        }
 
        System.out.println("进行随机选取服务");
        // 随机算法
        int size = instances.size();
        Random random = new Random();
        ServiceInstance instance = instances.get(random.nextInt(size));
 
        return new DefaultResponse(instance);
    }
}
```

```java
@EnableDiscoveryClient
@SpringBootApplication
// 设置全局负载均衡器
@LoadBalancerClients(defaultConfiguration = {CustomRandomLoadBalancerClient.class})
// 指定具体服务用某个负载均衡
//@LoadBalancerClient(name = "msb-stock",configuration = CustomRandomLoadBalancerClient.class)
//@LoadBalancerClients(
//        value = {
//                @LoadBalancerClient(value = "msb-stock",configuration = CustomRandomLoadBalancerClient.class)
//        },defaultConfiguration = LoadBalancerClientConfiguration.class
//)
public class OrderApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
```