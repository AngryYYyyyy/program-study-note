# 课程开篇

## 为什么懂得了一个个技术点，却依然用不好 RocketMQ？

我知道，很多同学都是带着一个个具体的问题来学这门课的，比如说，RocketMQ数据怎么做持久化？消息重复方案应该怎么做？这些问题当然很重要，但是，如果你只是急于解决这些细微的问题，你的RocketMQ使用能力就很难得到质的提升。

只关注零散的技术点，没有建立起一套完整的知识框架，缺乏系统观，但是，系统观其实是至关重要的。从某种程度上说，在解决问题时，拥有了系统观，就意味着你能有依据、有章法地定位和解决问题。

如何高效地形成系统观呢？我们做事情一般都希望“多快好省”，说白了，就是希望花很少的时间掌握更丰富的知识和经验，解决更多的问题。听起来好像很难，但实际上，只要你能抓住主线，在自己的脑海中绘制一幅 RocketMQ的 全景知识图，这完全是可以实现的。而这也是我在设计这门课时所遵循的思路。

本课程基于RocketMQ5版本来进行讲解，下图是RocketMQ5的“两大维度、三大主线”

“两大维度”就是指实战应用维度和底层与源码维度。

“三大主线”也就是指高性能、高可用和高可扩展（可以简称为“三高”）。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/d573a3c336c542db95b95fd6f2c3deab.png)

## 如何多快好省的学习RocketMQ

RocketMQ 作为庞大的消息中间件，可以说遍地都是知识，一抓一大把，我们怎么能快速地知道该学哪些呢？

**你完全可以按照这三大主线**

高性能：包括存储模型、数据结构、多线程运用；

高可用：包括重试机制、流控机制、HA机制；

高可扩展：包括消息过滤、批量消息、proxy机制；

**在应用维度上**，我建议大家按照两种方式学习: “应用场景驱动”和“典型案例驱动”，一个是“面”的梳理，一个是“点”的掌握。

异步和解耦是 RocketMQ的两大广泛的应用场景。在这些场景中，本身就具有一条显式的技术链。比如说，提到消息中间件的场景，你肯定会想到消息丢失、消息重复、消息顺序等一连串的问题。

# 基础入门

## **消息中间件(MQ)的定义**

一般认为，消息中间件属于分布式系统中一个子系统，关注于数据的发送和接收，利用高效可靠的异步消息传递机制对分布式系统中的其余各个子系统进行集成。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/3af8d04386094d5f96013720762aae1f.png)

### **应用异步与解耦**

系统的耦合性越高，容错性就越低。以电商应用为例，用户创建订单后，如果耦合调用库存系统、物流系统、支付系统，任何一个子系统出了故障或者因为升级等原因暂时不可用，都会造成下单操作异常，影响用户使用体验

使用消息中间件，系统的耦合性就会提高了。比如物流系统发生故障，需要几分钟才能来修复，在这段时间内，物流系统要处理的数据被缓存到消息队列中，用户的下单操作正常完成。当物流系统恢复后，继续处理存放在消息队列中的订单消息即可，终端系统感知不到物流系统发生过几分钟故障。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/515f06e1af544da28f995dc74f14f27f.png)

### **流量削峰**

应用系统如果遇到系统请求流量的瞬间猛增，有可能会将系统压垮。有了消息队列可以将大量请求缓存起来，分散到很长一段时间处理，这样可以大大提到系统的稳定性和用户体验。

**互联网公司的大促场景（双十一、店庆活动、秒杀活动）都会使用到** **MQ** **。**

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml17212\wps2.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/3464914df4f84cf6a175bfb3ce23a84f.png)

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml17212\wps3.jpg)

## RockeMQ的安装

### RocketMQ的windows下的安装

#### 官方下载地址

[https://rocketmq.apache.org/zh/download/]()

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/120961307015418080c839752881c84a.png)

控制台下载

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/6524c731667e43cb9c34ebf9977d5bcf.png)

#### 环境要求

JDK1.8(64位)

#### 配置环境变量

变量名：ROCKETMQ_HOME

变量值：MQ解压路径\MQ文件夹名

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/a594af429cd64d549863344d6ac9c6f0.png)

### 启动

RocketMQ的物理架构中，都是需要先启动NameServer再启动Broker的。所以启动顺序一定不要搞反了。

#### 启动NAMESERVER

Cmd命令框执行进入至‘MQ文件夹\bin’下，然后执行‘start mqnamesrv.cmd’，启动NAMESERVER。成功后会弹出提示框，此框勿关闭。

![](file:///C:\Users\Administrator\AppData\Local\Temp\msohtmlclip1\01\clip_image008.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/aba943b4be324474b811df034cef63ae.png)

弹出boot success后，说明启动成功，那么NameServer的监听端口是本机是9876端口。

这种启动方式后，日志的一般放在用户目录下的 logs目录下。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/4be26137ebca4bc099a3c897b917aa24.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/c162b4ce538a45d3bf8f5aa45d78a38a.png)

#### 启动BROKER

Cmd命令框执行进入至‘MQ文件夹\bin’下，然后执行

```
 start mqbroker.cmd -n localhost:9876 
```

启动BROKER。成功后会弹出提示框，此框勿关闭。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/8adb02c3f43b47db95c2375069e9e3f3.png)

日志的一般放在用户目录下的 logs目录下。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/ba0ff006dae346d4b9826bc21aa12b06.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/3950455488024692ac3659c8b0c06865.png)

### 控制台启动

下载地址：[https://github.com/apache/rocketmq-dashboard](https://github.com/apache/rocketmq-dashboard)

**控制台端口及服务地址配置：**

下载完成之后，进入‘\rocketmq-console\src\main\resources’文件夹，打开‘application.properties’进行配置。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/420d5c00d2d947f3913090445507d488.png)

因为本身控制台也是单独的Java应用，默认的是8080，为了防止与Tomcat冲突，我改成了8089

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1682342232048/2f749e4cdab34d13938008e5b8282f20.png)

然后进入dashboard根目录执行‘mvn clean package -Dmaven.test.skip=true’，编译生成。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/0da25241600640848cbd9f6a131d6261.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/a070c76ecb064533aea9c7cf25eab654.png)

执行‘java -jar rocketmq-dashboard-1.0.0.jar’，启动dashboard。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/83ed790632fb4fe9b9b5c1b4d827b720.png)

### 启动脚本整合

每次启动RocketMQ的命令还是挺麻烦的，所以这里做了一个脚本整合。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/71921b2e8c6e41fdba29ebcbc8ec5a95.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/39bdd125c6e04a86acd64a6a041b4356.png)

因为都是采用默认配置，数据文件的地址默认都是在C盘（C盘最好确保有足够的空间，数据文件默认启动就是1G。）

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/49a920ee9f32456d9edcf25b45a202d8.png)

## RockeMQ的基本概念

### 消息的发送与消费模型

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/3846461a51dd4b4eb8e69c3f9904f74c.png)

### **主题(Topic)**

标识RocketMQ中一类消息的逻辑名字，消息的逻辑管理单位。无论消息生产还是消费，都需要指定Topic。主题主要用于区分消息的种类：一个生产者可以发送消息给一个或者多个Topic，消息的消费者也可以订阅一个或者多个Topic消息。

### **消息队列**  **(**  **Message Queue** **)**

简称Queue或Q。消息物理管理单位。一个Topic将有若干个Q。

无论生产者还是消费者，实际的生产和消费都是针对Q级别。例如Producer发送消息的时候，会预先选择（默认轮询）好该Topic下面的某一条Q发送；Consumer消费的时候也会负载均衡地分配若干个Q，只拉取对应Q的消息。

### **生产者(Producer)**

**生产者** ：也称为消息发布者，负责发送消息至RocketMQ。

### **消费者(Consumer)**

**消费者** ：也称为消息订阅者，负责从RocketMQ接收并消费消息。

### **消费者分组(ConsumerGroup)**

标识一类Consumer的集合名称，这类Consumer通常消费一类消息（也称为Consumer Group），且消费逻辑一致。同一个Consumer Group下的各个实例将共同消费topic的消息，起到负载均衡的作用。

### 订阅关系（Subscription）

RocketMQ中的消费者订阅关系（Subscription）是指消费者与主题（Topic）之间的订阅关系。

消费者可以通过指定订阅规则来订阅某个主题的消息。

## RocketMQ生产消费流程

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/1dc10bb4affd41338e68d4007a2b3a1c.png)

## 普通消息的发送与消费

### **三种消息发送方式**

#### **同步发送消息**

同步发送是指消息发送方发出数据后，同步等待，直到收到接收方发回响应之后才发下一个请求。这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml13012\wps1.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/eb79152ffc864f28834f14bc975cc937.png)

#### **异步发送消息**

异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。消息发送方在发送了一条消息后，不等接收方发回响应，接着进行第二条消息发送。发送方通过回调接口的方式接收服务器响应，并对响应结果进行处理。

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml13012\wps2.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/d41c240f1bfa489a9b399d627b42d1e7.png)

#### **单向发送消息**

这种方式主要用在不特别关心发送结果的场景，例如日志发送。单向（Oneway）发送特点为发送方只负责发送消息，不等待服务器回应且没有回调函数触发，即只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml13012\wps3.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/ec2538a7d75b400ab8a4a6edac90866b.png)

### **两种消息消费方式**

#### **集群消费（负载均衡模式）**

消费者采用集群消费方式消费消息，一个分组(Group)下的多个消费者共同消费队列消息，每个消费者处理的消息不同。一个Consumer Group中的各个Consumer实例分摊去消费消息，即一条消息只会投递到一个Consumer Group下面的一个实例。例如某个Topic有3个队列，其中一个Consumer Group 有 3 个实例，那么每个实例只消费其中的1个队列。集群消费模式是消费者默认的消费方式。

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml13012\wps4.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/cf9c879d3897424bbf8b332c9f80b071.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/09c423c27ad24c3b9202c96095eccfb6.png)

这里稍微深入一点，就是集群消费提交的偏移量，持久化是存在broker上的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/65360310780c4552ba539ff8d7355c20.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/364c0633706e464fb38ba304d53d976a.png)

#### **广播消费**

广播消费模式中消息将对一个Consumer Group下的各个Consumer实例都投递一遍。即使这些 Consumer属于同一个Consumer Group，消息也会被Consumer Group 中的每个Consumer都消费一次。实际上，是一个消费组下的每个消费者实例都获取到了topic下面的每个Message Queue去拉取消费。所以消息会投递到每个消费者实例。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/8a2775e53e514aa898db202f0750effa.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/2fc90b2010d34fc69201a9a9936dfad7.png)

这里稍微深入一点，就是广播消费提交的偏移量，持久化是存在客户端（消费者）的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/754e2dd6f20e4152a809d5632a0cae28.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/a8e6765dfbd34aebb8a39d532654054c.png)

### 两种消费监听方式

#### 消息并发监听

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/2b85fdf6449f43f4ac9deac87f88893b.png)

MessageListenerConcurrently：同时开启多个线程并发消费消息。所以这里有可能胡

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/4f138c0400c74e779391baa7c8579760.png)

对于消费的结果，有两种方式：1、成功，2、重试。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/fd3ec50a4ddc43eebe726d9e4ace8ab9.png)

消费端如果发生消息失败，没有提交成功（或者直接抛出异常、或者返回null值），消息默认情况下会进入重试队列中。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/01e6eb2343b74b19a8a6a7fe3c6baf90.png)

**注意重试队列的名字其实是跟消费群组有关，不是主题，因为一个主题可以有多个群组消费，所以要注意**

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml23108\wps2.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/3887f09d358246eeb9103d210abd55d4.png)

重试的间隔默认如下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/84cc1a8a8f0d4d1a94528cc9ad6fb57d.png)

这里重试机制的大体逻辑是这样，消息一旦重试，比如消息A，那么这个消息就会进入%RETRY%....这个队列，然后看是第几次重试，达到了重试间隔，这个消息才会进行消费者重新投递。所以这种情况很容易导致消息的无序。

#### 消息顺序监听

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/a1e964282a6a4f2a99c71469feea667c.png)

MessageListenerOrderly：在同一时刻只允许一个线程消费一个队列的消息，并且保证在消费这个队列消息的顺序性。

玩顺序消息时。consume消费消息失败时，不能返回reconsume——later，这样会导致乱序，应该返回suspend_current_queue_a_moment,意思是先等一会（默认1s），一会儿再处理这批消息，而不是放到重试队列里。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/4f536bf82de14f8c81af4df5c23de710.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/0fd91d77063b4843862d47a9cf2f541f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/364c8139663b481294c92448813b043e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/5ad80f9c77764ebbabd7de53acde10cb.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/71828a3f4c024984bf56787e29dccfe5.png)

该消费线程在消费消息时，会使用锁来保证消息的顺序性。当某个消息消费失败时，该消息所属的队列会进入暂停状态，直到该消息处理成功后才会继续消费下一条消息。

##### 严格使用顺序消息注意事项

但是这里有一个误区，不要认为这么做就可以确保顺序消费，因为这个顺序保障只是确保队列级的。消息在不同的队列中依然是无序的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/cf3e1b9708ab4d48869d9f8b45102174.png)

所以要做到顺序消费，就必须要创建只能有一个队列的主题。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/c98f8e8702a844a891f04d98458664d1.png)

同时就算一个主题只有一个队列，你也要使用顺序消费监听，如果使用并发消费监听一样会有问题。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/4457e7388d2943769061f992210f38c4.png)

##### 分区顺序消息

## 批量消息的发送与消费

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/9a22448063be4fc6a5079c145223b5c7.png)

如果发送的消息超过了4M，就需要进行拆分。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/2b0b35d838534fef89de1e82d21b7d88.png)

## **过滤消息**

### **T**ag过滤

在大多数情况下，TAG是一个简单而有用的设计，其可以来选择您想要的消息。

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml23108\wps3.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/cd9e3d7b0eb84ac9b3bb44cb9086ce15.png)

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml23108\wps4.jpg)消费者将接收包含TAGA或TAGB或TAGC的消息。但是限制是一个消息只能有一个标签，这对于复杂的场景可能不起作用。在这种情况下，可以使用SQL表达式筛选消息。SQL特性可以通过发送消息时的属性来进行计算。

### **Sql过滤**

需要修改Broker.conf配置文件。加入enablePropertyFilter=true 然后重启Broker服务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/cde4bc1f9ebf4070a3e012c63cc74d92.png)

如果抛出错误：说明Sql92功能没有开启

#### ***SQL基本语法***

RocketMQ定义了一些基本语法来支持这个特性。你也可以很容易地扩展它。

只有使用push模式的消费者才能用使用SQL92标准的sql语句，常用的语句如下：

**数值比较：** 比如：>，>=，<，<=，BETWEEN，=；

**字符比较：** 比如：=，<>，IN；

IS NULL 或者IS NOT NULL；

**逻辑符号：** AND，OR，NOT；

**常量支持类型为：**

数值，比如：123，3.1415；

字符，比如：'abc'，必须用单引号包裹起来；

**NULL** ，特殊的常量

布尔值，TRUE 或FALSE

#### ***消息生产者（***  ***加入消息属性*** ***）***

发送消息时，你能通过putUserProperty来设置消息的属性

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml23108\wps6.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/7823c68f414a47448d901724b86cfeb5.png)

#### ***消息消费者*** ***（使用SQL筛选）***

用MessageSelector.bySql来使用sql筛选消息

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml23108\wps7.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1683530057014/468be740138b44b98a5e79b7d76a35fd.png)

## **延时消息**

### **概念介绍**

**延时消息：** Producer 将消息发送到消息队列RocketMQ 服务端，但并不期望这条消息立马投递，而是延迟一定时间后才投递到Consumer 进行消费，该消息即延时消息。

### **适用场景**

消息生产和消费有时间窗口要求：比如在电商交易中超时未支付关闭订单的场景，在订单创建时会发送一条延时消息。这条消息将会在 30 分钟以后投递给消费者，消费者收到此消息后需要判断对应的订单是否已完成支付。如支付未完成，则关闭订单。如已完成支付则忽略。

### **使用方式**

Apache RocketMQ目前只支持固定精度的定时消息，因为如果要支持任意的时间精度，在Broker 层面，必须要做消息排序，如果再涉及到持久化，那么消息排序要不可避免的产生巨大性能开销。（阿里云RocketMQ提供了任意时刻的定时消息功能，Apache的RocketMQ并没有,阿里并没有开源）

发送延时消息时需要设定一个延时时间长度，消息将从当前发送时间点开始延迟固定时间之后才开始投递。

延迟消息是根据延迟队列的level来的，延迟队列默认是

msg.setDelayTimeLevel(3)代表延迟10秒

"1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"

是这18个等级（秒（s）、分（m）、小时（h）），level为1，表示延迟1秒后消费，level为5表示延迟1分钟后消费，level为18表示延迟2个小时消费。生产消息跟普通的生产消息类似，只需要在消息上设置延迟队列的level即可。消费消息跟普通的消费消息一致。

同时RocketMQ5还支持任意时间的演示


### **代码演示**

**org.apache.rocketmq.example.** **scheduled****包中**

#### ***生产者***

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml23108\wps10.jpg)

#### ***消费者***

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml23108\wps11.jpg)

![](file:///C:\Users\lijin\AppData\Local\Temp\ksohtml23108\wps12.jpg)
