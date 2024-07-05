# Netty入门

## Netty是什么？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/7f9ffff0a6ab4f6b842a5e62c521d335.png)

Netty 由 Trustin Lee(韩国，Line 公司)2004 年开发

本质：网络应用程序框架

实现：异步、事件驱动

特性：高性能、可维护、快速开发

用途：开发服务器和客户端

Netty的性能很高，按照Facebook公司开发小组的测试表明，Netty最高能达到接近百万的吞吐。

## Netty发展历程

* 2004年6月Netty2发布(声称Java社区中第一个基于事件驱动的应用网络框架)
* 2008年10月Netty3发布
* 2013年7月Netty4 发布
* 2013年12月发布5.0.0.Alpha1
* 2015年11月废弃5.0.0

**现状与趋势**

30000+项目在使用（统计方法:依赖项中声明io.netty:netty-all）

* 数据库:Cassandra
* 大数据处理:Spark、Hadoop
* Message Queue: RocketMQ
* 检索: Elasticsearch
* 框架：gRPC、Apache Dubbo
* 分布式协调器：ZooKeeper
* 工具类: async-http-client

[Issues · netty/netty · GitHub](https://github.com/netty/netty/issues)

同时也可以看到Netty中大部分的问题都已经解决了。

## 第一个Netty程序

maven中引入一个比较稳定的4.1.25.Final的版本

```
 <dependency>
            <groupId>io.netty</groupId>
            <artifactId>netty-all</artifactId>
            <version>4.1.28.Final</version>
        </dependency>
```

代码如下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/e1d4856addd34d7a9724277529ec84cc.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/ad3fe3cb649e4c43baf551df65fc3d5b.png)

### 重要的类、方法解析

#### EventLoop

EventLoop暂时可以看成一个线程、EventLoopGroup自然就可以看成线程组。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/d56542aab8c342828ce7a608e80b2bcf.png)

网络编程里，“服务器”和“客户端”实际上表示了不同的网络行为；换句话说，是监听传入的连接还是建立到一个或者多个进程的连接。因此，有两种类型的引导：一种用于客户端（简单地称为Bootstrap），而另一种（ServerBootstrap）用于服务器。无论你的应用程序使用哪种协议或者处理哪种类型的数据，唯一决定它使用哪种引导类的是它是作为一个客户端还是作为一个服务器。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/0323cc4b34be422fbfc17ca75a353fe8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/7ef724c5fc3349b385c0a2225f84516c.png)

ServerBootstrap将绑定到一个端口，因为服务器必须要监听连接，而Bootstrap 则是由想要连接到远程节点的客户端应用程序所使用的。

引导一个客户端只需要一个EventLoopGroup，但是一个ServerBootstrap 则需要两个，因为服务器需要两组不同的Channel。第一组将只包含一个ServerChannel，代表服务器自身的已绑定到某个本地端口的正在监听的套接字。而第二组将包含所有已创建的用来处理传入客户端连接（对于每个服务器已经接受的连接都有一个）的Channel。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/99b4ed97f3e84474b9bf97a76f842c89.png)

Channel 是Java NIO 的一个基本构造。

它代表一个到实体（如一个硬件设备、一个文件、一个网络套接字或者一个能够执行一个或者多个不同的I/O操作的程序组件）的开放连接，如读操作和写操作

目前，可以把Channel 看作是传入（入站）或者传出（出站）数据的载体。因此，它可以被打开或者被关闭，连接或者断开连接。

#### 事件和ChannelHandler、ChannelPipeline

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/09846ec38b084d19b78f1bd8a1b73bd0.png)

Netty 使用不同的事件来通知我们状态的改变或者是操作的状态。这使得我们能够基于已经发生的事件来触发适当的动作。

**可能由入站数据或者相关的状态更改而触发的事件包括：**

连接已被激活或者连接失活；数据读取；用户事件；错误事件。

**出站事件是未来将会触发的某个动作的操作结果，这些动作包括：**

打开或者关闭到远程节点的连接；将数据写到或者冲刷到套接字。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/8c95be4444794e96978d9689547aa957.png)

每个事件都可以被分发给ChannelHandler 类中的某个用户实现的方法。

Netty 提供了大量预定义的可以开箱即用的ChannelHandler 实现，包括用于各种协议（如HTTP 和SSL/TLS）的ChannelHandler。

#### ChannelFuture

Netty 中所有的I/O 操作都是异步的。

JDK 预置了interface java.util.concurrent.Future，Future 提供了一种在操作完成时通知应用程序的方式。这个对象可以看作是一个异步操作的结果的占位符；它将在未来的某个时刻完成，并提供对其结果的访问。但是其所提供的实现，只允许手动检查对应的操作是否已经完成，或者一直阻塞直到它完成。这是非常繁琐的，所以Netty提供了它自己的实现——ChannelFuture，用于在执行异步操作的时候使用。

**每个Netty 的出站I/O操作都将返回一个 ChannelFuture 。**

## **Netty实现HTTP**

### Netty内置的编解码器和ChannelHandler

Netty 为许多通用协议提供了编解码器和处理器，几乎可以开箱即用，这减少了你在那些相当繁琐的事务上本来会花费的时间与精力。

### HTTP 系列

HTTP 是基于请求/响应模式的：客户端向服务器发送一个HTTP 请求，然后服务器将会返回一个HTTP 响应。Netty 提供了多种编码器和解码器以简化对这个协议的使用。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/817353769a55480998b3b9bf45fd94f0.png)

一个HTTP 请求/响应可能由多个数据部分组成，FullHttpRequest 和FullHttpResponse 消息是特殊的子类型，分别代表了完整的请求和响应。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/e2454d0191294d20aa459c72bb93a264.png)

## **Netty实现UDP单播和广播**

### UDP协议

**UDP**

* 面向无连接的通讯协议。
* 通讯时不需要接收方确认，属于不可靠的传输。

* 因为不需要建立连接，所以传输速度快，但是容易丢失数据。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/b15980edc91b4a87a7eac42170ce5a80.png)

**报文组成**

源端口：源端口号，在需要对方回信时选用，不需要时可用全0。

目的端口：目的端口号，这在终点交付报文时必须要使用到。

长度：UDP用户数据包的长度，其最小值是8（仅有首部）。

校验和：检测UDP用户数据报在传输中是否有错,有错就丢弃。


UDP是面向无连接的通讯协议，UDP报头由4个域组成，其中每个域各占用2个字节，其中包括目的端口号和源端口号信息，数据报的长度域是指包括报头和数据部分在内的总字节数，校验值域来保证数据的安全。由于通讯不需要连接，所以可以实现广播发送。

UDP通讯时不需要接收方确认，属于不可靠的传输，可能会出现丢包现象，实际应用中要求程序员编程验证。

UDP与TCP位于同一层，但它不管数据包的顺序、错误或重发。因此，UDP不被应用于那些使用虚电路的面向连接的服务，UDP主要用于那些面向查询---应答的服务：

例如NFS。相对于FTP或Telnet，这些服务需要交换的信息量较小。使用UDP的服务包括NTP（网络时间协议）和DNS（DNS也使用TCP），包总量较少的通信（DNS、SNMP等）；2.视频、音频等多媒体通信（即时通信）；3.限定于 LAN 等特定网络中的应用通信；4.广播通信（广播、多播）。

常用的QQ，就是一个以UDP为主，TCP为辅的通讯协议。

TCP 和 UDP 的优缺点无法简单地、绝对地去做比较：TCP 用于在传输层有必要实现可靠传输的情况；而在一方面，UDP 主要用于那些对高速传输和实时性有较高要求的通信或广播通信。TCP 和 UDP 应该根据应用的目的按需使用。

### 实现UDP单播和广播

单播的传输模式：定义为发送消息给一个由唯一的地址所标识的单一的网络目的地。面向连接的协议和无连接协议都支持这种模式。

广播：传输到网络（或者子网）上的所有主机。

### Netty 的UDP相关类

Netty 的DatagramPacket 是一个简单的消息容器，DatagramChannel 实现用它来和远程节点通信。它包含了接收者（和可选的发送者）的地址以及消息的有效负载本身。

NioDatagramChannel

扩展了Netty 的Channel 抽象以支持UDP 的多播组管理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/3f398bb99b7945a2b5e9af9535bc4ef9.png)

DatagramPacket是final类不能被继承，只能被使用。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/421c047914bb43b09d072b3a411a3cca.png)

**通过** **content()** **来获取消息内容**

**通过** **sender();** **来获取发送者的消息**

**通过** **recipient();** **来获取接收者的消息。**

### UDT（应用层协议）

基于UDP的数据传输协议（UDP-based Data Transfer Protocol，简称UDT）是一种互联网数据传输协议。UDT的主要目的是支持 **高速广域网上的海量数据传输** ，最典型的例子就是建立在光纤广域网上的网格计算，一些研究所在这样的网络上运行他们的分布式的数据密集程式，例如，远程访问仪器、分布式数据挖掘和高分辨率的多媒体流。

而互联网上的标准数据传输协议TCP在高带宽长距离网络上性能很差。顾名思义，UDT建于UDP之上，并引入新的拥塞控制和数据可靠性控制机制。UDT是面向连接的双向的应用层协议。

UDT的特性主要包括在以下几个方面：

基于UDP的应用层协议：有基本网络知识的朋友都知道TCP和UDP的区别和使用场景，但是有没有一种协议能同时兼顾TCP协议的安全可靠和UDP协议的高效，那么UDT就是一种。

面向连接的协议：面向连接意味着两个使用协议的应用在彼此交换数据之前必须先建立一个连接，当然UDT是逻辑上存在的连接通道。这种连接的维护是基于握手、Keep-alive（保活）以及关闭连接。

可靠的协议：依靠包序号机制、接收者的ACK响应和丢包报告、ACK序号机制、重传机制(基于丢包报告和超时处理)来实现数据传输的可靠性。

双工的协议：每个UDT实例包含发送端和接收端的信息。

新的拥塞算法，并且具有可扩展的拥塞控制框架：新的拥塞控制算法不同于基于窗口的TCP拥塞控制算法(慢启动和拥塞避免)，是混合的基于窗口的、基于速率的拥塞控制算法。可扩展的拥塞控制框架开源的代码和拥塞控制的C++类架构，可支持开发者派生专用的拥塞控制算法。

带宽估计：UDT使用对包(PP -- Packet pair)的机制来估计带宽值。即每16个包为一组,最后一个是对包,即发送方不用等到下一个发送周期内再发送。接收方接收到对包后对其到达时间进行记录,可结合上次记录的值计算出链路的带宽(计算的方法称为中值过滤法), 并在下次ACK中进行反馈。

## **Netty实现WebSocket通信**

### **什么是** **WebSocket** **？**

WebSocket ——一种在2011 年被互联网工程任务组（IETF）标准化的协议。

WebSocket解决了一个长期存在的问题：既然底层的协议（HTTP）是一个请求/响应模式的交互序列，那么如何实时地发布信息呢？AJAX提供了一定程度上的改善，但是数据流仍然是由客户端所发送的请求驱动的。还有其他的一些或多或少的取巧方式(Comet)

WebSocket规范以及它的实现代表了对一种更加有效的解决方案的尝试。简单地说，WebSocket提供了“在一个单个的TCP连接上提供双向的通信……结合WebSocket API……它为网页和远程服务器之间的双向通信提供了一种替代HTTP轮询的方案。”

，但是最终它们仍然属于扩展性受限的变通之法。也就是说，WebSocket 在客户端和服务器之间提供了真正的双向数据交换。WebSocket 连接允许客户端和服务器之间进行全双工通信，以便任一方都可以通过建立的连接将数据推送到另一端。WebSocket 只需要建立一次连接，就可以一直保持连接状态。这相比于轮询方式的不停建立连接显然效率要大大提高。

Web浏览器和服务器都必须实现 WebSockets 协议来建立和维护连接。

**特点**

l  HTML5中的协议，实现与客户端与服务器双向，基于消息的文本或二进制数据通信

l  适合于对数据的实时性要求比较强的场景，如通信、直播、共享桌面，特别适合于客户与服务频繁交互的情况下，如实时共享、多人协作等平台。

l  采用新的协议，后端需要单独实现

l  客户端并不是所有浏览器都支持

### Netty对WebSocket 的支持

由IETF 发布的WebSocket RFC，定义了6 种帧，Netty 为它们每种都提供了一个POJO 实现。同时Netty也为我们提供很多的handler专门用来处理数据压缩，ws的通信握手等等。

### WebSocket通信握手

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/940bee02d2ab460cbe8c345943e3a2af.png)

**Websocket****借用了****HTTP的协议来完成一部分握手（应用层的握手，不是TCP的握手）**

**客户端的请求：**

Connection 必须设置 Upgrade，表示客户端希望连接升级。

Upgrade 字段必须设置 Websocket，表示希望升级到 Websocket 协议。

Sec-WebSocket-Key 是随机的字符串，服务器端会用这些数据来构造出一个 SHA-1 的信息摘要。把“Sec-WebSocket-Key”加上一个特殊字符串“258EAFA5-E914-47DA-95CA-C5AB0DC85B11”，然后计算 SHA-1 摘要，之后进行 BASE-64 编码，将结果做为“Sec-WebSocket-Accept”头的值，返回给客户端。如此操作，可以尽量避免普通 HTTP 请求被误认为 Websocket 协议。

Sec-WebSocket-Version 表示支持的 Websocket 版本。RFC6455 要求使用的版本是 13，之前草案的版本均应当弃用。

**服务器端：**

Upgrade: websocket

Connection: Upgrade

依然是固定的，告诉客户端即将升级的是 Websocket 协议，而不是mozillasocket，lurnarsocket或者shitsocket。

然后， Sec-WebSocket-Accept 这个则是经过服务器确认，并且加密过后的 Sec-WebSocket-Key 。

后面的， Sec-WebSocket-Protocol 则是表示最终使用的协议。

至此，HTTP已经完成它所有工作了，接下来就是完全按照Websocket协议进行

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1657526666011/d1d8cf5df2274403b1d589c04b908af2.png)
