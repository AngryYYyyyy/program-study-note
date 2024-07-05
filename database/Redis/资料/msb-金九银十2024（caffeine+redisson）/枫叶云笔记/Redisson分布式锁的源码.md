**Redisson的中文API地址：**

https://github.com/redisson/redisson/wiki/目录

# Redisson分布式锁的源码

分布式锁的核心功能其实就三个：加锁、解锁、设置锁超时

同时在了解分布式锁之前，也需要了解Redis的发布订阅功能。

## Redis的发布订阅功能

![](https://ask.qcloudimg.com/http-save/yehe-8200658/vevjmr1xwq.png)![](https://ask.qcloudimg.com/http-save/yehe-8200658/vevjmr1xwq.png)![](https://ask.qcloudimg.com/http-save/yehe-8200658/vevjmr1xwq.png)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/823685ae72864492ac321151e1e1e421.png)

具体的操作演示如下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/d4023d5f9e384f2781795a34999eb082.png)

开启3个客户端，一个订阅了频道 **channel1** ，另外个订阅了频道 **channel1**，最后一个通过PUBLISH发送消息后，订阅的那个就能收到了，靠这种模式就能实现不同客户端之间的通信。

## Redisson分布式锁源码整体分析

### RLock接口

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/250c474183a6491eadfc7718febff6c2.png)

**RLock**是一个接口，具体的同步器需要实现该接口，当我们调用 `redisson.getLock()`时，程序会初始化一个默认的同步执行器**RedissonLock**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/2cd050ea660c49309b84f5657ff26925.png)

1、**commandExecutor** ：异步的Executor执行器，Redisson中所有的命令都是通过...Executor 执行的 ；

2、**internalLockLeaseTime**：等待获取锁时间，这里读的是配置类中默认定义的，时间为30秒；

3、**pubSub：**封装了发布订阅功能组件

### Redisson锁的执行流程

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/ff26e6f00cf34e129136cae8651b1a39.png)

#### 通过Redis的monitor监控Redis服务器的命令

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/a8b769d7c68d4310a1d1f69f6dc5f292.png)

##### 1、不启动看门狗的加锁

这里只加锁，然后让主线程休眠，我们看下针对Redis做了什么操作

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/66cbfea6c6b54a66b349dd66b64fb4c7.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/9018b621cc5744edb4c616e6668b2972.png)

这里可以和《Redisson锁的执行流程》对应看一看。

这里可以看到Redisson的加锁实际上是一个Lua脚本+针对一个哈希类型的操作（hincrby命令）

##### 2、不启动看门狗的加锁+解锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/a015b526112045e2aa1a77a68bd85c00.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/ad27999ac6944ec9b2b517cc9069a473.png)

这里可以看到：

这里可以看到Redisson的解锁实际上是一个Lua脚本（del操作）+publish发布的操作。

##### 3、不启动看门狗的加锁+解锁（重入锁的情况）

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/d483bde315714906971fae8b99ac79f0.png)

可以看到这里没有删除掉key，这里实现锁的可重入是利用一个计数，加锁一次就加1，解锁一次就减1。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/ab41419350684bc181cdf45fdcd1acb7.png)

##### 4、启动看门狗的加锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/62d4d93799b44d8683b51eda2f1b826e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/5b29ba76830749aa96b05560f654b335.png)

## Redisson锁的执行流程+源码分析

跟踪lock方法进入源码

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/5525e9d3c36741f9a57ce31a204e7ec5.png)

代码还是挺长的，不过流程也就两步：

**要么线程拿到锁返回成功；**

**要么没拿到锁并且等待时间还没过就继续循环拿锁（这里使用监听机制避免来优化了），同时监听锁是否被释放。**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/28c6e0ea8159481da9a88f037c09c743.png)

以上的Lua脚本就比较清晰了：

1、当前key不存在：标识锁未被占用

2、使用hset写入一个hash类型的数据：其中，key为锁的名称、field为“Redisson客户端ID：线程id”，value=1

3、执行pexpire，设置失效时间

4、当前key存在：标识已经获取到了锁

5、hincrby新增field的value，并且重新设置超时时间。

6、最后都不满足：获取锁失败，返回锁的剩余超时时间。

**拿不到的话，就会返回锁的剩余过期时长，这个时长有什么作用呢？？？**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/4b8bb0aac80d4e3bba67322f06c39a35.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/e7b145ca5a0847e296f10c6624ff8566.png)

用Java的Semaphore信号量的tryAcquire方法来阻塞线程。

Semaphore信号量又是由谁控制呢，何时才能release呢。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/d4c957e29f994c4e857d179cc832b607.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/d9d21f36a72843df946a753f75bd89c8.png)

这段代码的作用在于将当前线程的**threadId**添加到一个**AsyncSemaphore**中，并且设置一个redis的监听器，这个监听器是通过redis的发布、订阅功能实现的。

一旦监听器收到redis发来的消息，就从中获取与当前thread相关的，如果是锁被释放的消息，就立马通过操作 **Semaphore** （也就是调用**release**方法）来让刚才阻塞的地方释放。

看门狗线程续锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/94f2cdd32b234254b12f8abfa051f677.png)

io.netty.util.Timeout 使用

这个源码，就是一个定时器，每隔 10s 递归执行一次

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/11bd7af628614f6c8f0489f4f57ca791.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1716967978049/2863ac5215ab40f6a32b492dc9bcddd9.png)
