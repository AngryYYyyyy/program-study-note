# Netty源码之请求处理流程

## Netty源码之启动服务

我们了解到Netty本身是基于JDK的NIO进行优化和改进一个框架，其实Netty本身还是基于JDK的NIO实现，所以我们再次把JDK的NIO的流程拉出来看一看。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/bcbbb4f6aec94cc2b74cd1b4cd6652d2.png)

从上图可以看到，要启动NIO,那么就需要创建Selector，同样Netty要启动，同样也需要创建Selector。

那么我们可以采用调试跟踪的方式来分析源码。

**主线:**

**主线程**

* 创建Selector
* 创建ServerSocketChannel
* 初始化ServerSocketChannel
* 给ServerSocketChannel 从 boss group 中选择一个 NioEventLoop

**BossThread**

* 将ServerSocketChannel 注册到选择的 NioEventLoop的Selector
* 绑定地址启动
* 注册接受连接事件（OP_ACCEPT）到Selector 上

### 主线程

#### 创建Selector

调试案例中的启动Netty服务端的代码

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/718f53d4bf2747f3832a5b35bdbd6a73.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/2312851faf4b467ba11a80053b256ecb.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/1a9f95544d8e4e8b9e2890cae790966b.png)

最后可以得出：NioEventLoopGroup的构造方法中调用JDK的SelectorProvider中provider()创建 selector

#### 创建ServerSocketChannel

那么我们需要在这里加上断点：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/f6616ff3f9d7432f90858e322c3f479d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/44fe11e08f044428bde14e2c89a2031c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c08f11484e1646c0a6e779f6cb5239d9.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/80ac007983ed4db181bfe08a9dc948a5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/f4aec0bdfede4344986239b3ce82588b.png)

#### 初始化ServerSocketChannel

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/cc7b68427b1b4b9c92957ddff8dee479.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/65763fc8154e47cda67e39aad0e93375.png)

#### 给ServerSocketChannel 从boss group中选择一个NioEventLoop

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/0ab7003470d34271aa1c5d06b8eb1fde.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/a80a2a6d575c435bb05f35cfd68e3f0d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/cacca3c548c641d0aa1cd596c294331f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/3e711b3d95704db8a4eac695f0e3a44b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/f644541f7bba47f1a7c6d08329824390.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/2926c31e611247ddbe57588ab731584f.png)

从上图可以看到，这里都是main方法线程。那么main方法线程其实已经完成了从boss group中选择一个NioEventLoop，不要还要做的事情就是衔接NioEventLoop的子线程。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/a9940c25e8074e43b21922458e4cc41b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/bacd3c7642ee454b842030d34ac4966b.png)

### BossThread

#### 将ServerSocketChannel 注册到选择的 NioEventLoop的Selector

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c23636915a4d44e49da840d4e11d2467.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/82cbb685256246c0aabcc4807ab06f78.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/16b12844fded4235b4b36a7bcf95ac8a.png)

#### 绑定地址启动

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c5fa4ff705de45c5bc00796c09047525.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/d445e112bdf0445c861808e02c12ae76.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/bf8dfc4ed40140d7a671ae839dd8d80c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/3b47231f8c2a471385860a794dc8932d.png)

所以这里就进行了绑定地址启动。（这里因为涉及到责任链模式pipeline，我们先快速过，后面再来讲）

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/d0ffae6bdb374ad6a86f022c18be5742.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/10017a5283ab42058486d74983d066b2.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/68a76e86676d4eceae868dec13af78ac.png)

#### 注册接受连接事件（OP_ACCEPT）到Selector上

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/5d5c1fe79dcd4078b3e233c6b9e20686.png)

在断点处，使用alt+鼠标左键点击pipeline,

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/7aed6cf3eca6400490a09f8980dca1f6.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/aeac01b67143496c90c56c944db60f18.png)

这里发现处理都是DefaultChannelPipeline这个类

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/05de5785d8174ccab496b3497736dc0d.png)

加个断点继续调试

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/d067ac47f32e43999429f573973731a5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/6b56862822844441ba87550d4321d811.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/18b93502b84742378fd72289460962fc.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/1ec1ff99e50e4cbfa0ae614674420122.png)

## Netty之责任链模式

适用场景:

* 对于一个请求来说,如果有个对象都有机会处理它,而且不明确到底是哪个对象会处理请求时,我们可以考虑使用责任链模式实现它,让请求从链的头部往后移动,直到链上的一个节点成功处理了它为止

优点:

* 发送者不需要知道自己发送的这个请求到底会被哪个对象处理掉,实现了发送者和接受者的解耦
* 简化了发送者对象的设计
* 可以动态的添加节点和删除节点

缺点:

* 所有的请求都从链的头部开始遍历,对性能有损耗
* 极差的情况,不保证请求一定会被处理

Netty的pipeline设计，就采用了责任链设计模式，底层采用双向链表的数据结构，将链上的各个处理器串联起来

客户端每一个请求的到来，netty都认为pipeline中的所有的处理器都有机会处理它。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/efce9588fdd84c9f93c183374eaba56d.png)

```
package com.msb.netty.pipeline;

import java.text.DecimalFormat;

/**
 * Pipeline实现类
 */
public class Pipeline {
    //handler上下文，维护链表和负责链表的执行
    class HandlerChainContext {
        HandlerChainContext next;// 持有下一个节点
        AbstractHandler handler;
        public HandlerChainContext(AbstractHandler handler) {
            this.handler = handler;
        }
        // 将节点持有下去
        void handler(Object arg0) {
            this.handler.doHandler(this, arg0);
        }
        // 继续执行下一个
        void runNext(Object arg0) {
            if (this.next != null) {
                this.next.handler(arg0);
            }
        }
    }
    // 持有上下文(可以获得需要的数据,属性)
    public HandlerChainContext context = new HandlerChainContext(new AbstractHandler() {
        @Override
        void doHandler(HandlerChainContext context, Object arg0) {
            System.out.println("折扣前"+arg0);
            context.runNext(arg0);
        }
    });

    // 添加责任链
    public void addLast(AbstractHandler handler) {
        HandlerChainContext next = context;
        while (next.next != null) {
            next = next.next;
        }
        next.next = new HandlerChainContext(handler);
    }

    // 开始调用
    public void requestProcess(Object arg0) {
        context.handler(arg0);
    }
    //处理器抽象类
    static abstract class AbstractHandler {
        abstract void doHandler(HandlerChainContext context, Object arg0);
    }
    //具体的处理器实现类（购物折扣1）
    static class Handler1 extends AbstractHandler {
        @Override
        void doHandler(HandlerChainContext handlerChainContext, Object arg0) {
            System.out.println("--首次购买打9折！");
            arg0 = new DecimalFormat("0.00").format(Double.valueOf(arg0.toString())*0.9);
            System.out.println("折扣后金额："+arg0);
            // 继续执行下一个
            handlerChainContext.runNext(arg0);
        }
    }
    //具体的处理器实现类（购物折扣2）
    static class Handler2 extends AbstractHandler {
        @Override
        void doHandler(HandlerChainContext handlerChainContext, Object arg0) {
            System.out.println("--满200减20！");
            if(Double.valueOf(arg0.toString()) >= 200){
                arg0 = Double.valueOf(arg0.toString())-20;
                // 继续执行下一个
                System.out.println("折扣后金额："+arg0);
                handlerChainContext.runNext(arg0);

            }else{
                System.out.println("不满足条件，折扣结束："+arg0);
            }
        }
    }
    //具体的处理器实现类（购物折扣3）
    static class Handler3 extends AbstractHandler {
        @Override
        void doHandler(HandlerChainContext handlerChainContext, Object arg0) {
            System.out.println("--第二件减10元！");
            arg0 = Double.valueOf(arg0.toString())-20;
            System.out.println("折扣后金额："+arg0);
            // 继续执行下一个
            handlerChainContext.runNext(arg0);
        }
    }

    public static void main(String[] args) {
        Pipeline p = new Pipeline();
        p.addLast(new Handler1());
        p.addLast(new Handler2());
        p.addLast(new Handler3());

        p.requestProcess("500");
    }

}
```

### Netty的责任链设计

根据我们在前面对Netty的使用，我们知道，我们不需要自己创建pipeline，因为使用ServerBootstrap或者Bootstrap启动服务端或者客户端时，Netty会为每个Channel连接创建一个独立的pipeline。对于使用者而言，只需要将自定义的拦截器加入到 pipeline 中即可。

ChannelPipeline支持运行态动态的添加或者删除 ChannelHandler，在某些场景下这个特性非常实用。例如当业务高峰期需要对系统做拥塞保护时，就可以根据当前的系统时间进行判断，如果处于业务高峰期，则动态地将系统拥塞保护ChannelHandler添加到当前的ChannelPipeline中，当高峰期过去之后，就可以动态删除拥塞保护 ChannelHandler了。

ChannelPipeline是线程安全的,这意味着N个业务线程可以并发地操作ChannelPipeline而不存在多线程并发问题。但是，ChannelHandler却不是线程安全的，这意味着尽管ChannelPipeline是线程安全的，但是用户仍然需要自己保证ChannelHandler的线程安全。

#### netty的责任处理器接口

责任处理器接口, pipeline中的所有的handler的顶级抽象接口,它规定了所有的handler统一要有添加,移除,异常捕获的行为。netty对责任处理接口,做了更细粒度的划分, 处理器被分成了两种, 一种是入站处理器 ChannelInboundHandler,另一种是出站处理器 ChannelOutboundHandler,这两个接口都继承自ChannelHandler

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/4677857a406946038f692199af24b535.png)

ChannelInboundHandler的生命周期方法。这些方法将会在数据被接收时或者与其对应的Channel 状态发生改变时被调用。正如我们前面所提到的，这些方法和Channel 的生命周期密切相关。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/52dc8de27807466db6494645e11715cb.png)

ChannelOutboundHandler出站操作和数据将由ChannelOutboundHandler 处理。它的方法将被Channel、Channel-Pipeline 以及ChannelHandlerContext 调用。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/2f323795f07b4040b65248cbe062887a.png)

#### **添加删除责任处理器的接口**

netty中所有的处理器最终都在添加在pipeline上,所以,添加删除责任处理器的接口的行为 netty在channelPipeline中的进行了规定

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c2621b5caee146288796d263410d0f94.png)

#### **上下文**

pipeline中的handler被封装进了上下文中,如下, 通过上下文,可以轻松拿到当前节点所属的channel, 以及它的线程执行器

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/a945f9590e08459a99425206822759e3.png)

alloc 返回和这个实例相关联的Channel 所配置的ByteBufAllocator
bind 绑定到给定的SocketAddress，并返回ChannelFuture
channel 返回绑定到这个实例的Channel
close 关闭Channel，并返回ChannelFuture
connect 连接给定的SocketAddress，并返回ChannelFuture
deregister 从之前分配的EventExecutor 注销，并返回ChannelFuture
disconnect 从远程节点断开，并返回ChannelFuture
executor 返回调度事件的EventExecutor
fireChannelActive 触发对下一个ChannelInboundHandler 上的channelActive()方法（已连接）的调用
fireChannelInactive 触发对下一个ChannelInboundHandler 上的channelInactive()方法（已关闭）的调用
fireChannelRead 触发对下一个ChannelInboundHandler 上的channelRead()方法（已接收的消息）的调用
fireChannelReadComplete 触发对下一个ChannelInboundHandler 上的channelReadComplete()方法的调用
fireChannelRegistered 触发对下一个ChannelInboundHandler 上的fireChannelRegistered()方法的调用
fireChannelUnregistered 触发对下一个ChannelInboundHandler 上的fireChannelUnregistered()方法的调用
fireChannelWritabilityChanged 触发对下一个ChannelInboundHandler 上的fireChannelWritabilityChanged()方法的调用
fireExceptionCaught 触发对下一个ChannelInboundHandler 上的fireExceptionCaught(Throwable)方法的调用
fireUserEventTriggered 触发对下一个ChannelInboundHandler 上的fireUserEventTriggered(Object evt)方法的调用
handler 返回绑定到这个实例的ChannelHandler
isRemoved 如果所关联的ChannelHandler 已经被从ChannelPipeline中移除则返回true
name 返回这个实例的唯一名称
pipeline 返回这个实例所关联的ChannelPipeline
read 将数据从Channel读取到第一个入站缓冲区；如果读取成功则触发一个channelRead事件，并（在最后一个消息被读取完成后）通知ChannelInboundHandler 的channelReadComplete(ctx)方法
write 通过这个实例写入消息并经过ChannelPipeline
writeAndFlush 通过这个实例写入并冲刷消息并经过ChannelPipeline

#### 责任终止机制

* 在pipeline中的任意一个节点,只要我们不手动的往下传播下去,这个事件就会终止传播在当前节点
* 对于入站数据,默认会传递到尾节点,进行回收,如果我们不进行下一步传播,事件就会终止在当前节点,别忘记回收msg
* 对于出站数据,用header节点的使用unsafe对象,把数据写会客户端也意味着事件的终止

#### **事件的传播**

底层事件的传播使用的就是针对链表的操作

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c08c8d51bbac470eb2243dd531cb3a62.png)

### Netty的责任链源码解读

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/ca454159824a49c89ef20ff7f3f9b016.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/5df8e66cba6f447e9671b7167e948954.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/4deddef2a249498d9138248914e7daa2.png)

## Netty源码之构建连接

从前面我们知道一个技术点，就是绑定IP也好，处理连接也好都是NioEventLoop来做，于是我们来看对应的核心run方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/b7668d7e25244be8a360270516affc0a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/bb1614e1725a48c0bbc1f5fc66e0fdb8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/1c481ccbfd64487f86e78af5e1896c1b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/ad4150fa4f34433abae75bcc15bc0e54.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/a6652efc0213498ca18dcb5783e92f2a.png)

断点在这里调试一下，同时我们看下ops的标志代表着什么？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/2de957d3828746fd96c6f5a42c5c5d84.png)

在这里我们需要debug一下看下连接事件的发生

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c2ebd3e5847d4b20ae482c5988658662.png)

然后启动一个客户端连接

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/3e80671b7f794645bfc31619d0dfbd02.png)

再回到服务端的断点，可以看到ops=16，这个是accpct

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/e5370a151f8642469fef9bba42909c1b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/03737eabb3cc49cdb3af84849210e7dc.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/b803843ac78a42e197b5f40ae64e842a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/1def9afe072d4ef592e9d7f34b69aa6a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/cbc2ef16c9a54372bef8519c63ad4622.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/d64146e10452451197493e272b6e6f91.png)跟完之后整体流程图就出来了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/99cb7bd819bf4820b6dfb9294bb02ad4.png)


## Netty源码之接收数据

接收数据，也就是读数据（对于服务端来说），也是在NioEventLoop中操作的，不过具体的实现却不同。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/11c137c42184401a9044ce5c89735ed4.png)

具体再回到构建连接之后代码

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/2d2539337e1b420886e783a6f01327bb.png)

这里很明显使用到了责任链模式

然后我们回到NioEventLoop中在以下位置打上断点，再次调试运行server，同时发起client调用

第一次进入断点,发现这里是NioServerSocketChannel说明走的是创建连接

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/35a361c3ca934afba7ecc60d91f128e3.png)

第二次进入断点，发现这里是NioSocketChannel，这里走的应该是读事件了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/3ad67d44657d43afbf8b7432763c8524.png)

另外也可以观察到两次进入的方法除了对象不同，线程也不同，因为这里采用了主从的方式。

然后我们接着进入来看读数据如何处理的

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/5becfd4438e24bc3a8535b462d31fa4a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/7ded69739e074552a85700c995685758.png)

### 读数据技巧

#### 每次该读多大

自适应数据大小的分配器（AdaptiveRecvByteBufAllocator）

第一次靠猜，默认是1024

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/72f8bea3cbc840ff990caf986ef5575a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/e8485e6af63b49b5b2147132ac587200.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c9e1d9d2f8584e528c6a9abd06762e83.png)

然后会根据情况进行buffer大小的调整，默认 都是这个AdaptiveRecvByteBufAllocator的record方法来调整。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/05d1c3ac713a47a0a5d460c0d4f00d74.png)

不过这里有两种情况，第一种就是读的数据超过1024，那么就要扩容，如果是小于1024，那么就要缩容

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/332bac33aba34800a5c5f2dfaf9e65fc.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/ddea3532be4d47c5b53c855754ab06f1.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/b4ec3858182645cf93698aa6dcdabfc3.png)

这段业务逻辑比较复杂，不过看这个类的话可以得出结论

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/a8926505a322433b97072f026e957e0a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/9974714acc984ecdac5405713bb41c2b.png)

这里要注意的一个点就是。

AdaptiveRecvByteBufAllocator 对 bytebuf 的猜测：放大果断，缩小谨慎（需要连续 2 次判断）

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/6f22eea10b354e3d94e9421d6a5b783d.png)

#### 连续尝试读取16次

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/0f028e4dadfc4e14a53f3568ce835b03.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/306043d89c3347669115b3829916a5f8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/be2e747cd156461f8007593d7e7cfb9e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/94594e1ab5ab4f879afe300c36782b19.png)

"什么是16次最优，不是其它的2的幂次方倍?"这个本身不是问题，因为写32的话，别人也会问为什么不是16，这里的思想是“雨露均沾”，就是说给别人读的机会，所以要控制中的次数（不要无限循环）

如果是超过16次数据还没有读完也没关系，还会触发读事件的，所以还是能处理。是说把这次机会（否则一直读下去，别的连接就没有什么机会读到了）让出去了。

## Netty源码之业务处理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/cdf189495bdd4afab15a9bbc5ac4a0d5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/300f3717b19f4f6a9f6c54362434795f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/4f9494007fa143c490c5ebd2fd4ff7d1.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c1c807b5cc304485b5fddbd63784992d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/3fc5b076d6534a9b9eecb12088396115.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/63362b3bbee445f1aca918ccfab0fafd.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/5b05d091e4f54a1dbb91292bb275c921.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/7ec5527832794c8684d9fe36ae3bd6d3.png)跳转几次后，就可以进入自己写的业务的handler

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/004ea44fc7504ab4b2c8bb3d31432ea7.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/ad75ce924ccb427bbf56d9068653f94b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/f4ba035039b54743bfba6b318bcbdd5c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/ae850c20d5d14e79a0c4da4b3fee74e0.png)

总结一下就是：

**处理业务本质：**数据在 pipeline 中所有的 handler 的 channelRead() 执行过程
Handler 要实现 io.netty.channel.ChannelInboundHandler#channelRead (ChannelHandlerContext ctx ,
Object msg)，且不能加注解 @Skip 才能被执行到

## Netty源码之写数据

### write ：写到一个 buffer

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/0932bd44351d462dbeb7bc536aa56b70.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/f6114829bed347ba9dc3702c8d707b50.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/3c7a090d681e4dd7833767ace58d99e8.png)

调试一下，走到执行这里

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/20f900582f9f4bd88a01e4a99788eeaf.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/05edbcd93c984df0a2bc6fce30633f38.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/d312715c104a483a854d9252abe0c6de.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/90287f57c32f4ae5a91fb4ecab38fe0a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/1649b5a62a69426c8beb652a248ac77b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/8686f39746744cb9a0238b52eca8c760.png)

这个地方为什么要做这个处理？

Netty 待写数据太多，超过一定的水位线（ writeBufferWaterMark.high) () ），会将可写的标志位改成
false ，让应用端自己做决定要不要发送数据了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/193a1ab4e57244c1846a41e15284ae44.png)

### flush : 把 buffer 里的数据发送出去

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/381b9e33fba14d3e91ff6d894f763929.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/4cc226d5c8c6400896dee0e1bfb06327.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/c85aafbaf56f4192a5c5c0ea02f3bb80.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/dbc8a972effd4db59ad6ed1a6cd4871e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/b70a71610276405084b6939f10af546a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/f3823d089a8e408a8fd1361f36b34aeb.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/814d71f706c9460a95d5c7d726c6ac0e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/bb4b35ba77424747bde3083bae5c0b29.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1658733477089/71afd3c58d164035918aa3c1f195bff4.png)

这里可以分析出两个亮点：

1、只要有数据要写，且能写，则一直尝试，直到 16 次（ writeSpinCount ），写 16 次还没有写完，就直接 schedule 一个 task 来继续写，而不是用注册写事件来触发，更简洁有力。
2、批量写数据时，如果尝试写的都写进去了，接下来会尝试写更多（ maxBytesPerGatheringWrite ）
