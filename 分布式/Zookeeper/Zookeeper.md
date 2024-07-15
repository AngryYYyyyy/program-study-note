# 一、Zookeeper的介绍和安装

## 1. 为什么要使用Zookeeper

​       我们为了学习Dubbo，而在dubbo中需要一个注册中心，而Zookeeper是我们在使用Dubbo是官方推荐的注册中心，所以我们先来介绍Zookeeper

![image-20210226191457919](img\image-20210226191457919.png)



![image-20210226191530528](img\image-20210226191530528.png)



## 2. Zookeeper简介

![image-20210226192111730](img\image-20210226192111730.png)





### （1）Zookeeper概述

ZooKeeper 是一个由 Apache 软件基金会开发的开源服务器，用于==协调分布式应用程序==。它提供了一种简单的原始构建块，可以用来构建更复杂的服务，如==命名服务、配置管理、同步服务和组服务==等。

| 序号 | 功能描述                                                     |
| ---- | ------------------------------------------------------------ |
| 1    | 为其他分布式程序提供服务，支持==分布式系统的协调和管理==     |
| 2    | 作为一个==自身独立的分布式程序运行==，提供高可用性和可扩展性 |
| 3    | 提供==主从协调服务，支持服务器节点的动态上下线，统一配置管理，实现分布式共享锁和统一的名称服务== |
| 4    | 管理和存储用户程序提交的数据，提供数据节点==监听==服务以便用户程序可以实时响应数据变化 |

### （2）集群

ZooKeeper 作为一个关键的分布式服务协调程序，其自身的集群机制至关重要，以确保高可用性和可靠性。、

**集群机制**

ZooKeeper 集群采用==**半数存活机制**==，即集群中超过半数的节点存活时，整个集群环境才被视为可用。这种设计强调了集群节点数为奇数的重要性，以避免=="脑裂"==情况的发生。

### （3）集群节点的角色

**Leader**

- Leader 服务器是 ZooKeeper 集群的核心，==负责协调和处理所有事务请求==，确保集群事务处理的一致性和顺序性。
- Leader 还负责==集群内部的服务器调度和状态同步==。

**Follower**

- Follower 服务器处理来自客户端的==非事务性请求（如读取数据）==并将所有事务请求转发给 Leader 服务器。
- Follower 参与事务请求Proposal的投票过程以及 Leader 的选举过程。

**Observer**

- Observer 服务器作为集群中的观察者，==监视并同步==集群的最新状态变化，对非事务性请求可以独立处理，而事务性请求则转发给 Leader 服务器。
- Observer ==不参与任何形式的投票==，既不参与事务请求的投票也不参与 Leader 选举的投票，从而降低了对集群整体投票过程的负载。

这些角色共同确保 ZooKeeper 集群可以在分布式环境中提供稳定、可靠的服务协调。每个角色都有其明确的职责，使得 ZooKeeper 能够有效地管理大规模的分布式系统。

## 3. 集群环境搭建

​       参考Kafka笔记

## 4. 选举机制

ZooKeeper 集群中的节点需要选举出一个领导者（Leader），负责处理所有更新集群状态的事务请求，以==确保数据的一致性和顺序性==。当==集群启动==或者==领导者节点因故障下线==时，就需要进行新的领导者选举。

### （1）集群启动过程中的Leader选举

当使用 ZooKeeper 构建分布式服务器集群时，通常建议使用==奇数个节点==以优化选举过程和提高集群的容错性。以三个节点的服务器集群为例，详细说明服务器在初始化时的选举过程。

![image-20210301104123274](img\image-20210301104123274.png)

#### 初始启动和投票过程
1. **启动节点**：当启动第一台装有 ZooKeeper 的节点时，由于它无法独立进行选举，它将处于等待状态。当第二台节点启动时，这两个节点将开始相互通信并启动选举过程。
   
2. **发起投票**：每个服务器投出初始票据，选择自己作为Leader。==投票内容包括服务器ID（SID）和事务ID（ZXID）==。SID是在安装ZooKeeper时在配置文件中指定的`myid`；ZXID是事务ID，表示该节点操作znode的更新程度。因为在服务器初始化时，每个服务器上的ZXID为0，所以Server1投的票是（1,0），Server2的票是（2,0）。每个服务器将其投票发送给集群中的其他服务器。

#### 投票接收和处理
3. **接收投票**：每个服务器接收来自其他服务器的投票。首先，每个服务器会检查收到的投票的有效性，例如确认这是否是当前轮次的投票，以及是否来自处于Looking状态的服务器。

4. **处理投票**：服务器按以下规则处理收到的票据：
   
   - **==比较ZXID==**：具有较高ZXID的服务器优先被选为Leader。
   - **==比较SID==**：如果ZXID相同（如在初始化阶段，每个服务器的ZXID都是0），则比较`myid`。具有较大`myid`的服务器将被选为Leader。
   
   在这个例子中，Server1接收到的投票是（2,0），由于其自身的票是（1,0），它将转而支持Server2为Leader，并更新其票为（2,0）。Server2接收到的来自Server1的票是（1,0），因为这比它自己的票小，Server2维持其票不变。随后，Server1和Server2再次投出新一轮票据，此时都为（2,0）。

#### 统计投票和更新状态
5. **统计投票**：每轮投票后，每个服务器都会统计收到的票据。如果某个服务器获得了超过半数的支持，那么该服务器将被选为Leader。在本例中，Server1和Server2统计发现已经有两台机器接受了（2,0）的投票，从而确定Server2为Leader。

6. **更新服务器状态**：一旦Leader确定后，每个服务器将更新其状态。Leader状态更新为Leading，而Follower则更新为Following。

通过这种机制，ZooKeeper确保在集群启动时快速且一致地选举出Leader，从而维护集群的高可用性和稳定性。

### （2）领导者节点因故障下线时的Leader选举

在 ZooKeeper 运行期间，集群可能会==因Leader服务器本身宕机==需要重新进行Leader选举。以下是Leader宕机后集群如何选举新Leader的详细过程：

#### 选举触发
1. **状态变更**：当Leader服务器（假设为Server2）因故障宕机，剩余的非Observer服务器（如Server1和Server3）将自身状态变更为Looking，并启动新一轮的Leader选举。

#### 投票过程
2. **发起投票**：每个服务器发起一轮投票，票面包含自己的服务器ID（myid）和最新的事务ID（ZXID）。==由于集群已在运行中，各服务器的ZXID可能各不相同==。例如，假设Server1的ZXID为145，Server3的ZXID为122，那么在第一轮投票中，Server1和Server3各自投自己，生成的票分别为（1, 145）和（3, 122），并将这些票发送给集群中所有其他机器。

#### 投票接收和处理
3. **处理和决策投票**：每个服务器接收来自其他服务器的投票。与初始化选举过程相同，每个服务器会根据收到的投票更新自己的投票决策：
   
   - 首先比较ZXID的大小。具有较大ZXID的服务器更倾向于成为新的Leader，因为它的数据更“新”。
   - 如果ZXID相同，则比较服务器的myid，myid较大的服务器将优先被选为Leader。
   
   在本例中，假设Server1和Server3收到对方的票后，由于Server1的ZXID较大，它将获得Server3的支持。因此，在随后的投票中，Server3将更改其投票支持Server1。

#### 统计投票和更新状态
4. **统计投票结果**：每次投票后，每个服务器都统计收到的投票信息。如果某个服务器获得了超过半数的支持，则被选为新的Leader。
5. **更新服务器状态**：一旦选出新的Leader，如Server1，则Server1将其状态更新为Leading，而其他Follower则更新状态为Following，并开始从新Leader同步数据。

通过这种机制，ZooKeeper确保在Leader突然宕机的情况下能够迅速恢复服务，选出新的Leader来维持集群的稳定运行和数据一致性。

# 二、Zookeeper客户端

## 1. 配置环境变量

为了便于操作 ZooKeeper 而不需要每次都进入到 ZooKeeper 的安装目录，我们可以将 ZooKeeper 的安装路径添加到系统的环境变量中。这样可以从任何位置直接访问 ZooKeeper 的命令。

1. **编辑系统环境变量文件**：
   打开 `/etc/profile` 以添加环境变量：

   ```shell
   vim /etc/profile
   ```

2. **添加以下内容到文件中**：

   ```shell
   export ZOOKEEPER_HOME=/opt/zookeeper
   export PATH=$PATH:$ZOOKEEPER_HOME/bin
   ```

3. **激活修改**：
   使环境变量的改动立即生效，执行：

   ```shell
   source /etc/profile
   ```

4. **分发配置文件**：
   使用 `scp` 命令将更新后的 `profile` 文件发送到集群中的其他节点：

   ```shell
   scp /etc/profile ***:/etc/
   ```

通过这些步骤，ZooKeeper 的命令就可以在节点的任意位置执行了。

## 2. 客户端连接

连接 ZooKeeper 服务可以通过在 bin 目录下使用 `zkCli.sh` 脚本实现。

**连接到本地 ZooKeeper 实例：**

```shell
zkCli.sh
```

该命令默认连接到当前节点上运行的 ZooKeeper 实例。

**连接到特定的 ZooKeeper 节点：**

如果需要连接到集群中的其他节点，可以指定服务器地址和端口：

```shell
zkCli.sh -timeout 5000 -server bobo02:2181
```

这里，`-timeout 5000` 设置了会话的超时时间为 5000 毫秒，`-server bobo02:2181` 指定了要连接的服务器地址和端口。

通过上述命令，可以方便地管理和监控 ZooKeeper 服务，无论是在本地节点还是跨服务器进行操作。

## 3. 数据操作

### （1）ZooKeeper的数据结构

![image-20210301113558950](img\image-20210301113558950.png)

ZooKeeper 维护着一个类似于文件系统的层次化的目录结构，其中：

1. **层次化目录结构**：其命名和组织方式符合常规文件系统的规范。
2. **节点称为 znode**：在 ZooKeeper 中，每个节点被称为 znode，并通过唯一的路径进行标识。
3. **数据和子节点**：znode 节点可以存储数据以及拥有子节点。需要注意的是，==临时（ephemeral）类型的节点不能拥有子节点==。
4. **监听器**：客户端应用可以在节点上设置监听器，以便在节点的数据或子节点发生变化时得到通知。

### （2）节点类型

ZooKeeper 中的 znode 有两大类和四种具体形式：

**基本类型**：
- **短暂性（Ephemeral）**：连接断开时，节点自动删除。
- **持久性（Persistent）**：连接断开后，节点依然保留。

**具体形式**：

| 序号 | 节点类型              | 描述                                                        |
| ---- | --------------------- | ----------------------------------------------------------- |
| 1    | PERSISTENT            | 持久节点，连接断开后节点不被删除。                          |
| 2    | PERSISTENT_SEQUENTIAL | 持久有序节点，创建时ZooKeeper会在节点名后附加一个递增序号。 |
| 3    | EPHEMERAL             | 短暂节点，连接断开后节点自动删除。                          |
| 4    | EPHEMERAL_SEQUENTIAL  | 短暂有序节点，连接断开后节点自动删除，且名字带有递增序号。  |

**序号特性**：
- 创建带序号的节点时（无论是持久还是短暂），ZooKeeper会在节点名后自动附加一个从0开始的递增值。这个序号由父节点维护，并且在父节点下是唯一的。
- 在分布式系统中，这些顺序号可以用来为所有事件进行全局排序，从而==允许客户端推断出事件发生的顺序==。

通过上述结构和机制，ZooKeeper 能够有效地支持复杂的分布式协调任务，提供一致性保障及事件顺序性的判断依据。

### （3）常用 ZooKeeper 命令

在使用 ZooKeeper 的 `zkCli.sh` 客户端进行交互时，有几个基本命令非常有用。这些命令允许用户进行数据的创建、检索、修改和删除。以下是这些命令的基础形式、作用和示例：

1. **`ls [path]`**：列出指定路径下的子节点。
      **示例**：

   ```shell
   ls /zookeeper
   ```
   这条命令会列出 `/zookeeper` 路径下的所有子节点。

2. **`ls2 [path]`**：除了列出子节点，还提供关于节点的额外信息，如状态信息和元数据。
      **示例**：

   ```shell
   ls2 /zookeeper
   ```
   这条命令会列出 `/zookeeper` 路径下的子节点及其额外信息。

3. **`create [path] [data]`**：在指定路径创建一个新的 znode，并可选择性地为其提供数据。
      **示例**：

   ```shell
   create /myapp "This is data for myapp node"
   ```
   这条命令在路径 `/myapp` 下创建一个新的 znode，并存储了一些数据。

4. **`get [path]`**：获取指定路径节点的数据及其状态信息。
      **示例**：

   ```shell
   get /myapp
   ```
   这条命令会显示 `/myapp` 节点存储的数据和状态信息。

5. **`delete [path]`**：删除指定路径的节点。注意，要删除的节点不能有子节点。
      **示例**：

   ```shell
   delete /myapp
   ```
   这条命令会删除路径 `/myapp` 下的节点，前提是该节点没有子节点。

6. **`set [path] [data]`**：更新指定路径节点上的数据。
      **示例**：

   ```shell
   set /myapp "Updated data for myapp node"
   ```
   这条命令会更新 `/myapp` 路径节点上存储的数据。

这些命令是 ZooKeeper 日常管理和操作的基础，能够有效地对集群中的数据节点进行操作和维护。



### （4） 事件监听

在 ZooKeeper 中，事件监听是一种重要的机制，允许客户端对节点数据或子节点的变化进行监控。监听器一旦设置，便可以在相应的事件发生时得到通知。

#### **节点数据变化**

监听某个节点的数据内容变化是常见的需求。在 ZooKeeper 3.4 及更高版本中，可以通过在 `get` 命令后添加 `-w` 参数来设置对指定节点的数据变化的监听。

**设置监听**：
```shell
get /app1 -w
```
该命令将对 `/app1` 节点设置数据改变的监听。当 `/app1` 节点的数据被修改时（例如，在其他客户端上执行 `set /app1 "new data"`），设置了监听的客户端将收到通知。

**注意**：在 ZooKeeper 中，==监听器是一次性的==。这意味着一旦触发了一次监听事件后，监听器将不再有效。==如果需要持续监听，必须在每次触发事件的处理函数中重新设置监听==。

#### **节点子列表变化**

除了监听节点的数据变化外，还可以监听节点下子节点的增加或删除。

**设置子节点变化的监听**：
```shell
ls /app1 -w
```
使用 `-w` 参数与 `ls` 命令结合可以监听 `/app1` 节点下子节点的变化。如果 `/app1` 下的子节点被创建或删除，设置了监听的客户端会收到通知。

**触发示例**：
假设当前 `/app1` 下没有子节点，然后在另一个客户端执行：
```shell
create /app1/new_child "some data"
```
这将在 `/app1` 下创建一个名为 `new_child` 的新子节点，并触发在 `/app1` 上设置的子节点变化监听。

#### **ZooKeeper服务状态变化**









通过这种方式，ZooKeeper 使得跨分布式环境中的不同组件之间的数据同步和状态变化处理成为可能，从而支持构建高效且可靠的分布式应用。

# 三、Zookeeper Java API使用

## 1.导入依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.5.9</version>
    </dependency>
    <dependency>
        <groupId>com.github.sgroschupf</groupId>
        <artifactId>zkclient</artifactId>
        <version>0.1</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>

</dependencies>
```

## 2.API的使用

ZooKeeper 提供了一个强大的 Java API，使得应用可以通过编程方式与 ZooKeeper 集群交互，执行任务如读取、写入、监听数据变更等。下面是一些 ZooKeeper Java API 的常用功能和方法介绍：

### （1）**连接 ZooKeeper 服务**

首先，你需要创建一个 `ZooKeeper` 类的实例来连接到 ZooKeeper 服务：

```java
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperConnection {
    private ZooKeeper zoo;
    private static final int SESSION_TIMEOUT = 5000;

    public ZooKeeper connect(String host) throws IOException, InterruptedException {
        zoo = new ZooKeeper(host, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent we) {
                //SyncConnected 状态表示客户端与ZooKeeper服务器已经建立了一个同步的、稳定的连接。
                if (we.getState() == KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to ZooKeeper");
                }
            }
        });
        return zoo;
    }

    public void close() throws InterruptedException {
        zoo.close();
    }
}
```
在这段代码中，`ZooKeeper` 类负责建立连接。连接时需要指定服务器的主机名和端口号、会话超时设置以及一个 `Watcher` 对象。

### （2）**创建节点**

可以使用 `create` 方法在 ZooKeeper 中创建一个新的 znode：

```java
zoo.create("/path", data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
```
这里的 `CreateMode` 可以是 `PERSISTENT`（持久节点）、`PERSISTENT_SEQUENTIAL`（持久顺序节点）、`EPHEMERAL`（临时节点）、或 `EPHEMERAL_SEQUENTIAL`（临时顺序节点）。

ACL 是指在 ZooKeeper 中对 znode 进行访问控制的规则集合。每个 znode 都有一个与之关联的 ACL，用于控制不同用户或用户组对这个 znode 的访问权限。`ZooDefs.Ids.OPEN_ACL_UNSAFE` 是一个预设的 ACL 设置，表示这个 znode 没有任何访问限制，允许任何客户端对其进行读写操作。

### （3）**获取节点数据**

要读取 znode 的数据，可以使用 `getData` 方法：

```java
byte[] data = zoo.getData("/path", true, null);
```
这里，`true` 表示是否要安装一个观察点（watcher）。如果 znode 发生变化，ZooKeeper 服务会通知客户端。

### （4）**设置节点数据**

使用 `setData` 方法可以修改 znode 的数据：

```java
Stat stat = zoo.setData("/path", data.getBytes(), zoo.exists("/path", true).getVersion());
```
在 ZooKeeper 中，修改节点数据时通常需要指定节点的版本号，以实现乐观锁的功能，防止数据在不知情的情况下被覆写。版本号可以通过调用 `exists` 方法获取，这个方法除了检查节点是否存在，还可以用来获取节点的当前状态，包括其版本信息。

### （5）**删除节点**

删除节点可以使用 `delete` 方法：

```java
zoo.delete("/path", zoo.exists("/path", true).getVersion());
```
同样，删除操作中也可以使用 `exists` 方法获取版本号进行版本控制，以确保删除操作的正确性。

### （6） **监听节点变化**

你可以通过设置 `Watcher` 来监听 znode 的变化：

```java
Watcher watcher = new Watcher() {
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDataChanged) {
            System.out.println("/path node data changed");
        }
    }
};

byte[] data = zoo.getData("/path", watcher, null);
```

在 ZooKeeper 中，`EventType` 是一个枚举类型，用来表示观察事件的类型。这些事件类型是 ZooKeeper 客户端与服务器交互中可能触发的不同类型的通知。以下是所有可能的 `EventType` 枚举值及其用途的描述：

1. `None`：这种类型通常用于表示会话相关的更改，而不是特定于某个 znode 的更改。例如，当客户端与服务器成功建立会话时，会触发 `None` 事件，并伴随一个 `KeeperState` 来说明会话的状态（如 `SyncConnected`）。

2. `NodeCreated`：当在被观察的路径上创建 znode 时触发此事件。如果设置了对某个不存在的节点的子节点的监视，然后在该路径上创建了节点，也会触发此事件。

3. `NodeDeleted`：当观察的节点被删除时触发此事件。这对于监视节点的生命周期非常有用，例如在配置管理或服务注册中。

4. `NodeDataChanged`：当节点的数据发生变化时触发。如果你对节点数据变动感兴趣，如配置更新，这个事件是非常重要的。

5. `NodeChildrenChanged`：当观察的节点的子节点列表发生变化时触发。这包括子节点的添加或删除。这对于监视目录或服务发现机制中节点的增加或减少非常关键。

通过上述 API 的使用，可以实现对 ZooKeeper 集群中数据的动态监控和管理，支持构建复杂的分布式应用程序。这些 API 方法都是同步调用的，ZooKeeper 也支持异步方法，适用于不阻塞线程的场景。
