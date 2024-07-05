# 一、初识Kafka

Apache Kafka是一个分布式流处理平台，由LinkedIn开发并于2011年作为开源项目贡献给了Apache Software Foundation。Kafka旨在提供高吞吐量、可扩展、可靠且持久的发布订阅消息系统，支持处理实时和历史数据。Kafka广泛应用于日志收集、实时流分析、事件源、微服务之间的通信以及提供实时的数据管道和集成层。

## 1.核心概念

- **Producer**：生产者，向Kafka的一个或多个topic发布消息的客户端或应用程序。
- **Consumer**：消费者，订阅topics并处理其发布的消息的客户端或应用程序。
- **Broker**：Kafka集群中的服务器，负责消息的存储和转发。
- **Topic**：消息的分类，生产者发布消息到指定的topic，而消费者从topic读取消息。
- **Partition**：为了实现消息的并行处理，每个topic可以分成多个partition，每个partition是一个有序的消息队列。
- **Offset**：每条消息在partition中的唯一标识，用于标记消费者在partition中的位置。

## 2.消息队列

消息队列（Message Queue, MQ）作为一种在分布式系统中用于==缓冲、系统间解耦和削峰填谷==的重要中间件，扮演着至关重要的角色。

### （1）消息队列模式

至多一次：消息生产者将数据写入消息系统，然后由消费者负责去拉去消息服务器中的消息，一 旦消息被确认消费之后 ，由消息服务器主动删除队列中的数据，这种消费方式一般只允许被一个 消费者消费，并且消息队列中的数据不允许被重复消费。 

没有限制:同上诉消费形式不同，生产者发不完数据以后，该消息可以被多个消费者同时消费，并 且同一个消费者可以多次消费消息服务器中的同一个记录。主要是因为消息服务器一般可以长时间存储海量消息。

### （2）Kafka作为消息队列的实际应用

在一个快速发展的电子商务平台中，每天都会产生大量的订单。这些订单需要被处理，包括验证、支付处理、库存管理、发货等步骤。随着业务量的增加，这些步骤的处理变得越来越复杂，对系统的性能和可扩展性提出了更高的要求。

为了应对这种挑战，电子商务平台采用了基于Kafka的消息队列来优化订单处理流程。具体实现方式如下：

1. **订单提交**：当用户在网站上下单后，订单服务会生成一个订单，并将订单详情作为消息发布到Kafka的一个名为`orders`的Topic中。
2. **服务解耦**：订单处理流程中的各个环节，如订单验证、支付处理、库存检查、发货等，都作为独立的服务运行，这些服务分别订阅`orders` Topic，根据订单消息进行相应的处理。
3. **异步处理**：例如，支付服务从`orders` Topic中读取订单消息，进行支付授权。支付成功后，它将支付确认消息发送到另一个名为`payments`的Topic中，供库存服务进一步处理。
4. **流量控制和削峰填谷**：在高峰时段，订单消息的数量可能会急剧增加。使用Kafka作为消息队列，可以有效地缓冲这些消息。各服务根据自身的处理能力，从Topic中拉取并处理消息，避免了系统过载，并且实现了高效的流量控制。
5. **可靠性保证**：Kafka保证了消息的持久化存储和高可用性。即使某个服务暂时无法处理消息，也不会导致订单信息的丢失，系统可以在服务恢复后继续处理未处理的消息。

## 3.Kafka Streaming 流处理

Kafka Streams是Apache Kafka的一个客户端库，用于构建健壮、可扩展和高性能的实时流处理应用。它允许您通过简单的应用程序来处理和分析存储在Kafka主题中的数据流。Kafka Streams设计理念是简单易用，它直接在Kafka中实现了流处理的能力，无需额外的流处理框架。

## 4.Kafka架构

在Apache Kafka中，==消息以记录（Record）的形式被组织和管理==，==每个记录归属于一个特定的主题（Topic）==。每个Topic在Kafka集群中的==物理存储==上，==对应于一组分区（Partition）的日志文件==，这些日志文件==负责持久化存储Topic中的记录，以保证数据的耐久性和可靠性==。

为了实现高可用性和数据的负载均衡，Kafka采用了分布式的架构设计。在这一架构中，==Topic的每个日志分区都由集群中的一个Broker担任领导者（Leader），而其他Broker则作为该分区的追随者（Follower）==。==领导者Broker负责处理该分区的所有读写操作==，而==追随者Broker则负责同步领导者Broker上的数据==。这种设计既保证了数据的一致性，又提高了系统的吞吐量。

在集群运行过程中，==如果负责某个分区的领导者Broker发生故障宕机，集群会自动从该分区的追随者Broker中选举出新的领导者，以无缝继续该分区的数据读写服务==。这种机制确保了Kafka系统的高可用性和故障自恢复能力。

Kafka集群的健康监控、领导者选举以及Topic的部分元数据管理，依赖于Zookeeper。Zookeeper作为一个分布式协调服务，它管理着Broker之间的状态信息，并参与领导者选举过程，确保了集群状态的一致性和系统的稳定运行。

通过这种设计，Kafka提供了一个高性能、可扩展且可靠的消息系统，能够支持从简单的消息传递到复杂的实时流处理应用。

### （1）日志分区

每个分区被组织成一个有序的、不可变的消息序列，称为日志。日志中的每条消息（Record）都被分配了一个唯一的序列号，即偏移量（Offset）。Kafka集群负责持久化所有发布到Topic中的消息，消息的保留期限可以通过配置项`log.retention.hours`（默认值为168小时）来设定。

Kafka周期性地检查日志文件，移除过期的数据。由于Kafka使用硬盘进行日志存储，因此可以无压力地长时间缓存大量日志数据。

Kafka通过分区实现了Topic的==扩展性==和==负载均衡==：

- **扩展性**：分区允许Topic的数据量超过单个服务器的容量限制。尽管每个分区需要适配其所在服务器的容量，但一个Topic可以包含多个分区，因此理论上可以处理无限量的数据。
- **负载均衡**：在Kafka集群中，每个服务器可能充当某些分区的领导者（Leader），同时作为其他分区的追随者（Follower）。这种设计使得集群中的工作负载得到了有效的分配和平衡。

### （2）发布与订阅

**数据发布**：生产者（Producers）将消息发布到指定的Topic。Kafka负责决定将消息分配到Topic的哪个分区（Partition）中。这个==分配过程可以通过简单的轮询方式（round-robin）来均衡负载==，或者==通过特定的分区策略==（例如，基于消息键（Key）的语义分区）来实现。

**数据订阅：**消费者（Consumers）在订阅Topic数据时，==会针对其消费的分区维护一个偏移量，表示当前消费的位置==。消费者在处理完一批次的消息后，会将这次消费的偏移量提交给Kafka集群。这意味着消费者可以根据需要从Topic分区的任意位置开始读取数据。由于每个消费者管理着自己的偏移量，不同的消费者之间相互独立，互不影响。

### （3）消费者组

在Apache Kafka中，消费者通过使用消费者组（Consumer Group）名称来==标识==自己，而发布到特定主题（Topic）的每条消息将被==分配给订阅该主题的某个消费者组中的一个消费者实例处理==。这种设计使得Kafka提供了灵活的消息消费机制，既支持负载均衡也支持消息广播：

- 当所有消费者实例属于==**同一个消费者组**==时，主题中的消息将在该组内的消费者实例之间进行==分摊==，确保每条消息只被组内的一个消费者处理。这种模式适用于需要负载均衡和消息分摊的场景，比如高性能的消息处理应用。

- 当每个消费者实例属于==**不同的消费者组**==时，每条消息会被==广播到所有消费者组==，由各个组内的消费者实例独立处理。这种模式适用于需要将消息复制到多个独立处理流的场景，例如日志聚合或实时数据分析。

Kafka中的消费者组概念实际上是发布-订阅模型的一种扩展，其中订阅者不再是单个进程，而是一个==消费者实例的集群==，即消费者组。这种设计不仅实现了消息消费的可伸缩性和容错性，也简化了消费者管理。

每个消费者组内部，Kafka基于主题的分区（Partition）来实现消息的均衡分发。随着消费者组内实例的增减，Kafka会==自动重新分配分区的所有权，保证消息的连续处理并最大化消费的并行性==。这种动态的分区所有权转移机制，既增强了系统的容错能力，也提升了处理能力。

需要注意的是，==Kafka保证的是分区内消息的有序性，而不是跨分区的全局有序性==。对于大多数大数据应用和场景而言，分区内的有序性或基于Key的分区策略已足够满足需求。然而，如果应用需要全局的消息有序性，可以通过创建只有一个分区的主题来实现，尽管这样做意味着每个消费者组中只能有一个消费者实例在运行，这可能会限制消息处理的并行度和吞吐量。

通过这种灵活而强大的消费者模型，Kafka能够支持从简单的数据传输到复杂的实时数据流处理的各种应用场景，满足现代数据驱动应用的需求。

## 4.高性能之道

### （1）顺序写和mmp

Kafka通过顺序写入和内存映射文件（Memory Mapped Files, MMF）两种关键技术==优化磁盘I/O==，从而实现高吞吐率，即便运行在普通的服务器上也能支持每秒上百万条消息的写入，超越了许多其他消息中间件。这些特性使得Kafka成为处理日志和海量数据场景的理想选择。

**顺序写入**

硬盘（尤其是传统的机械硬盘）的读写性能受限于其机械结构。硬盘读写数据时，磁头需要移动到数据存储的位置（寻址），这个过程包含了机械动作，是整个读写过程中最耗时的部分。相比之下，顺序I/O（数据连续存储在磁盘上）可以显著减少磁头的移动次数，从而减少寻址时间，提高读写速度。

Kafka充分利用了顺序写入的优势，==将消息连续追加到日志文件的末尾，避免了磁盘寻址的开销==。这种方式不仅提高了写入速度，而且还降低了CPU的使用率，因为它减少了与随机I/O相关的大量计算。

**内存映射文件（Memory Mapped Files, MMF）**

为了进一步提高I/O效率，Kafka利用了现代操作系统提供的内存映射文件技术。==内存映射文件允许开发者将磁盘上的文件内容映射到进程的地址空间中==，应用程序可以像访问普通内存一样访问文件数据，操作系统负责将内存的更改同步回磁盘。

内存映射文件的使用，使得Kafka可以借助操作系统的页面缓存（Page Cache）来缓存频繁访问的数据，减少对磁盘的直接读写。当Kafka写入数据时，实际上是写入到内存中的映射区域，由操作系统异步将数据刷新到磁盘，这大大降低了磁盘I/O操作的次数，提高了写入效率。

通过结合顺序写入和内存映射文件技术，Kafka实现了高效的数据存储方案。顺序写入确保了写操作的高效性，而内存映射文件则通过利用操作系统的缓存机制来提升读写性能。这些设计使Kafka能够在保证数据持久化的同时，实现极高的消息吞吐率，满足大规模数据处理的需求。

### （2）DMA

DMA（Direct Memory Access）是一种允许某些硬件子系统在不涉及CPU的情况下，直接访问系统内存的技术。通过DMA，==数据可以在外设和内存之间高效地传输，而不会占用CPU过多的资源==，从而提高整体系统性能。DMA是实现高速数据传输操作的关键技术之一，广泛应用于硬盘驱动器、固态驱动器、网络卡以及其他能够支持高速数据传输的硬件中。

1. **初始化传输**：当外设需要读写内存时，它会向DMA控制器发送一个传输请求。
2. **配置DMA控制器**：DMA控制器接收到请求后，根据外设提供的信息（如数据源、目的地内存地址、传输大小等），配置相应的传输参数。
3. **执行传输**：DMA控制器直接管理数据在外设和内存之间的传输过程，而CPU可以继续执行其他任务。
4. **传输完成**：一旦数据传输完成，DMA控制器会向CPU发出中断信号，通知它传输任务已完成。

### （3）Zero Copy

Kafka在处理客户端的读取请求时，采用了Zero Copy技术以优化数据传输过程，显著提高了数据处理的效率。Zero Copy技术通过==减少数据在用户空间和内核空间之间的拷贝次数==，降低了CPU的负担，提高了传输速度。

**传统I/O操作**

在传统的I/O操作模型中，数据从磁盘到达用户空间需要经过多次拷贝：

1. **应用发起读取请求**：用户进程调用如`read`的系统调用请求将数据读取到用户内存缓冲区，进程随之阻塞。
2. **操作系统向磁盘发送请求**：系统将I/O请求下发至磁盘。
3. **磁盘读取数据**：磁盘将数据读入其内部缓冲区，完成后通知操作系统。
4. **数据拷贝到内核缓冲区**：操作系统将数据从磁盘缓冲区拷贝到内核缓冲区。
5. **数据拷贝到用户缓冲区**：操作系统将数据从内核缓冲区拷贝到用户缓冲区，完成数据传输。

这一过程中，数据被多次拷贝，消耗了大量CPU资源和时间。

采用Zero Copy技术后，数据传输过程得到优化，减少了拷贝步骤：

1. **从磁盘到内核缓冲区**：数据首先被读取到内核缓冲区，这一步骤与传统模型相同。
2. **直接从内核缓冲区到协议引擎**：然后，数据从内核缓冲区被直接传递到网络协议引擎，无需拷贝到用户缓冲区，也无需再次拷贝到内核的socket缓冲区。

通过这种方式，Zero Copy技术有效减少了CPU的拷贝操作，减少了系统调用次数，降低了上下文切换的开销，从而提升了数据处理的速度和系统的整体性能。对于Kafka这样需要处理大量数据传输的系统来说，Zero Copy技术的应用是提高吞吐率、降低延迟的关键优化手段。

Zero Copy技术通过优化数据传输路径，减轻了CPU的负担，提高了传输效率，是Kafka高性能特性的重要支撑。它使得Kafka能够更加高效地处理大规模数据，满足日志处理和消息传递等海量数据场景的需求。

# 二、安装和部署

## 1.安装 JDK 1.8 并配置环境变量

首先，确保 Java Development Kit 1.8（JDK 1.8）被安装并正确配置。通过修改 `.bashrc` 文件，添加 JDK 相关的环境变量来完成这一步骤。这包括设置 `JAVA_HOME` 变量指向 JDK 的安装目录，以及更新 `PATH` 变量以包含 JDK 的 `bin` 目录。这样，Java 程序和工具就可以在命令行中被直接访问。

```properties
#/bashrc
JAVA_HOME=/usr/java/latest
PATH=$PATH:$JAVA_HOME/bin
CLASSPATH=.
export JAVA_HOME
export PATH
```

注意更新资源，加载环境变量

```bash
source .bashrc
```

## 2.配置主机名与 IP 映射

在 `/etc/sysconfig/network` 文件中定义系统的主机名，然后在 `/etc/hosts` 文件中添加主机名与 IP 地址的映射。这确保了系统可以通过主机名解析到正确的 IP 地址，对于在集群环境中的通信尤为重要。

```
192.168.2.130 CentOS_A
192.168.2.131 CentOS_B
192.168.2.132 CentOS_C
```

## 3.关闭防火墙与防火墙开机自启动

为了测试的方便，可能需要暂时关闭系统的防火墙。在 CentOS 7 上，这通常意味着停止并禁用 `firewalld` 服务。执行相关命令后，通过 `systemctl` 命令检查 `firewalld` 服务的状态，确保其已正确停止和禁用。

**禁用 `firewalld` 服务**

```bash
sudo systemctl disable firewalld
```

## 4.同步时钟

使用 `ntpdate` 命令与 NTP 服务器同步系统时间，保证集群中各节点的时间一致性。首先安装 `ntp` 包，然后选择一个可靠的时间服务器`ntpdate cn.pool.ntp.org | ntp[1-7].aliyun.com`进行时间同步，最后使用 `clock -w` 命令将同步的时间写入硬件时钟。

## 5.安装与配置 Zookeeper

Kafka 依赖于 Zookeeper 来进行集群管理和协调。从 Zookeeper 的配置模板 (`zoo_sample.cfg`) 创建一个新的配置文件 (`zoo.cfg`)，并根据需要进行相应的修改，如设置数据目录`dataDir=/root/zkdata`和服务器节点信息（集群），例如`server.1=CentOS_A:2888:3888`。

通过 `scp` 命令将配置好的 Zookeeper 目录复制到集群中的其他服务器上，例如`scp -r /usr/zookeeper-3.4.6/ CentOS_B:/usr/`，并在每台服务器上设置唯一的节点 ID，例如`echo 1 > /root/zkdata/myid`。

最后，使用 `zkServer.sh` 脚本启动 Zookeeper。

**`zkServer.sh`脚本相关使用**

启动 Zookeeper 服务，并使用一个特定的配置文件

```bash
./zkServer.sh start /path/zoo.cfg
```

停止/重启 Zookeeper 服务器

```bash
./zkServer.sh stop
./zkServer.sh restart
```

查看 Zookeeper 服务状态

```bash
./zkServer.sh status
```

## 6.安装与配置 Kafka

在配置 Kafka 之前，需要修改 `config/server.properties` 文件以设置 Kafka 集群的参数，包括 broker 的唯一标识符、监听地址、日志文件目录以及连接的 Zookeeper 服务器地址。完成配置后，使用 `kafka-server-start.sh` 脚本以守护进程模式启动 Kafka 服务。

broker 的唯一标识符

![image-20240411161656006](D:\笔记\MCA\消息中间件\Kafka\image\image-20240411161656006.png)

监听地址

![image-20240411161400683](D:\笔记\MCA\消息中间件\Kafka\image/image-20240411161400683.png)

日志文件目录，注意克隆情况下要保证该目录文件为空

![image-20240411153715530](D:\笔记\MCA\消息中间件\Kafka\image/image-20240411153715530.png)

Zookeeper 服务器地址

![image-20240411161533121](D:\笔记\MCA\消息中间件\Kafka\image/image-20240411161533121.png)

Kafka 配套提供了一系列的脚本工具，这些工具使得管理 Kafka 集群、创建和管理主题、生产和消费消息等操作变得简单。以下是一些常用的 Kafka 脚本及其用法：

Kafka 配套提供了一系列的脚本工具，这些工具使得管理 Kafka 集群、创建和管理主题、生产和消费消息等操作变得简单。以下是一些常用的 Kafka 脚本及其用法：

**kafka-topics.sh**

用于创建、删除、列出、修改主题或获取主题的信息。

- **创建主题**：
  
  ```bash
  ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my-topic --partitions 3 --replication-factor 1
  ```
  这个命令创建了一个名为 `my-topic` 的主题，指定了 3 个分区和 1 的副本因子，副本因子与服务器（broker）相关。
  
- **列出所有主题**：
  ```bash
  ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
  ```

- **查看主题详情**：
  ```bash
  ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic my-topic
  ```

**kafka-console-producer.sh**

提供一个简单的消息生产者，可以通过命令行发送消息到指定的主题。

- **向主题发送消息**：
  
  ```bash
  ./bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic my-topic
  ```
  运行后，你可以在命令行输入消息，每输入一行并回车，该行消息就会发送到 `my-topic`。

**kafka-console-consumer.sh**

提供一个简单的消息消费者，可以从指定的主题读取消息。

- **从主题读取消息**：
  ```bash
  ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my-topic --from-beginning
  ```
  这个命令会从 `my-topic` 开始读取消息，并显示出来。`--from-beginning` 参数告诉消费者从主题的开始处读取消息，如果省略这个参数，它将只消费新产生的消息。

**kafka-consumer-groups.sh**

用于管理消费者组信息。

- **列出所有消费者组**：
  ```bash
  ./bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
  ```

- **查看消费者组详情**：
  ```bash
  ./bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-group
  ```
  显示指定消费者组 `my-group` 的详细信息，包括每个分区的偏移量等。

**kafka-server-start.sh / kafka-server-stop.sh**

用于启动和停止 Kafka 服务器。

- **启动 Kafka 服务器**：
  
  ```bash
  ./bin/kafka-server-start.sh config/server.properties
  ```
  使用 `server.properties` 配置文件启动 Kafka 服务。
  
- **停止 Kafka 服务器**：
  
  ```bash
  ./bin/kafka-server-stop.sh
  ```
  停止 Kafka 服务。

这些脚本工具是 Kafka 操作的基础，通过它们可以轻松管理 Kafka 集群和数据流。在实际使用中，你可能需要根据具体需求调整脚本参数，比如改变端口号或者指定不同的配置文件。

# 三、Kafka基础API

## 1.Topic基本操作 DML管理

### （1）依赖以及log4j配置

```xml

<dependencies>
    <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>2.2.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-streams</artifactId>
        <version>2.2.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/log4j/log4j -->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>1.7.25</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.9</version>
    </dependency>
</dependencies>
```

log4j.properties

```properties
log4j.rootLogger = info,console
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern =  %p %d{yyyy-MM-dd HH:mm:ss} %c - %m%n
```

### （2）配置连接参数

首先，需要在系统的 `hosts` 文件中设置服务器的 IP 地址与主机名的映射。这个文件位于 `C:\Windows\System32\drivers\etc` 路径下。例如：

```
192.168.2.130 CentOS_A
192.168.2.131 CentOS_B
192.168.2.132 CentOS_C
```

通过这样的配置，系统将能够通过指定的主机名直接解析到相应的 IP 地址，以便进行网络配置和测试。

```java
//配置连接参数
Properties props = new Properties();
props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
            "CentOS_A:9092,CentOS_B:9092,CentOS_C:9092");
KafkaAdminClient adminClient= (KafkaAdminClient) KafkaAdminClient.create(props);
```

### （3）KafkaAdminClient

`KafkaAdminClient` 是 Apache Kafka 的一个 Java 客户端 API，用于管理和检查Kafka集群的状态和配置。它包括了许多管理功能，比如创建和删除主题、修改主题配置、管理消费者组、查看集群信息等。这个 API 从 Kafka 0.11 版本开始引入，使得程序化管理 Kafka 集群变得更加容易。

**KafkaAdminClient 的关键功能包括：**

- **创建和删除主题**：可以编程方式创建新的主题或删除现有的主题。
- **修改和查看主题配置**：允许修改现有主题的配置（如分区数、副本因子、保留策略等）以及查看任何主题的配置。
- **查看集群信息**：可以获取关于 Kafka 集群的详细信息，如当前的 broker 节点、主题列表、每个主题的分区详情等。
- **管理消费者组**：可以列出所有消费者组、描述消费者组的详细信息、删除消费者组的偏移量等。
- **ACLs 管理**：支持对 Kafka 集群进行访问控制列表（ACLs）的管理，可以添加或删除权限规则。

示例：

```java
KafkaAdminClient adminClient= (KafkaAdminClient) KafkaAdminClient.create(props);
//查询topics
 KafkaFuture<Set<String>> nameFutures = adminClient.listTopics().names();
 for (String name : nameFutures.get()) {
     System.out.println(name);
 }
 //创建Topics
List<NewTopic> newTopics = Arrays.asList(new NewTopic("topic02", 3, (short) 3));
adminClient.createTopics(newTopics);
 //删除Topic
adminClient.deleteTopics(Arrays.asList("topic02"));
 //查看Topic详情
 DescribeTopicsResult describeTopics =
         adminClient.describeTopics(Arrays.asList("topic01"));
 Map<String, TopicDescription> tdm = describeTopics.all().get();
 for (Map.Entry<String, TopicDescription> entry : tdm.entrySet()) {
     System.out.println(entry.getKey()+"\t"+entry.getValue());
 }
adminClient.close();
```

## 2.生产者和消费者

### （1）生产者

`KafkaProducer` 是一个能够将数据发送到 Kafka 集群的客户端。生产者负责创建消息，然后将它们发布到 Kafka 主题。生产者可以高度配置化，比如设置消息的持久性（通过副本）、可靠性（通过确认）、序列化方式等。

```java
Properties props = new Properties();
props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOS_A:9092,CentOS_B:9092,CentOS_C:9092");
props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
KafkaProducer<String, String> producer = new KafkaProducer<>(props);
```

`ProducerRecord` 是生产者发送消息时使用的一个容器，包含要发送到 Kafka 的消息数据。在创建 `ProducerRecord` 时，可以指定主题、可选的分区、键（key）、值（value）和时间戳等信息。键和值的序列化方式应与 `KafkaProducer` 的配置相匹配。

```java
ProducerRecord<String, String> record = new ProducerRecord<>("topic1", "key1", "value1");
producer.send(record);
```

**示例：**

```java
public class KafkaProducerDemo {
    public static void main(String[] args) throws InterruptedException {
        //1.创建链接参数
        Properties props=new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOS_A:9092,CentOS_B:9092,CentOS_C:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        //2.创建生产者
        KafkaProducer<String,String> producer=new KafkaProducer<>(props);

        //3.封装消息队列
        for(Integer i=0;i< 10;i++){
            Thread.sleep(100);
            ProducerRecord<String, String> record = new ProducerRecord<>("topic01", "key" + i, "value" + i);
            producer.send(record);
        }

        producer.close();
    }
}
```

### （2）消费者

`KafkaConsumer` 是一个消息消费客户端，用于从 Kafka 主题订阅和拉取数据。消费者可以订阅一个或多个主题并处理来自这些主题的消息流。消费者同样支持多种配置，可以设置如何管理偏移量（offsets）、如何在消费群组中平衡分区等。

```java
Properties props=new Properties();
props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOS_A:9092,CentOS_B:9092,CentOS_C:9092");
props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
props.put(ConsumerConfig.GROUP_ID_CONFIG,"group01");

KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
consumer.subscribe(Arrays.asList("topic1", "topic2"));
```

`ConsumerRecord` 表示从 Kafka 主题中拉取到的单条消息记录。它包含消息所在的主题、分区、偏移量、键、值和时间戳等信息。消费者在拉取消息后，通常会遍历 `ConsumerRecord` 对象进行处理。

```java
ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
for (ConsumerRecord<String, String> record : records) {
    System.out.println("Topic: " + record.topic() + " Key: " + record.key() + " Value: " + record.value());
}
```

在 Kafka 的消费者 API 中，`poll` 方法用于从服务器拉取数据。`poll(Duration timeout)` 方法的参数 `timeout` 指定了消费者等待数据的最大时间。

示例：

```java
public class KafkaConsumerDemo {
    public static void main(String[] args) {
        //1.创建Kafka链接参数
        Properties props=new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOS_A:9092,CentOS_B:9092,CentOS_C:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"group01");

        //2.创建Topic消费者
        KafkaConsumer<String,String> consumer= new KafkaConsumer<>(props);
        //3.订阅topic开头的消息队列
        consumer.subscribe(Pattern.compile("^topic.*$"));

        while (true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            for(ConsumerRecord<String,String>record:consumerRecords){
                String key = record.key();
                String value = record.value();
                long offset = record.offset();
                int partition = record.partition();
                System.out.println("key:"+key+",value:"+value+",partition:"+partition+",offset:"+offset);
            }
        }
    }
}
```

### （3）消费者组测试

编辑编译器，将允许多个实例添加，这样就可以启动多个程序。

![image-20240411200029922](D:\笔记\MCA\消息中间件\Kafka\image/image-20240411200029922.png)

创建多个消费者实例。

![image-20240411200117962](D:\笔记\MCA\消息中间件\Kafka\image/image-20240411200117962-1712837218304-3.png)

当多个消费者实例属于同一个消费者组时，消息会在这些消费者之间均衡分配。Kafka 保证同一个分区的消息只被消费者组中的一个消费者处理，这样就可以通过增加消费者实例的数量来提升处理能力，实现水平扩展。

当消费者以不同的组身份运行时，每个消费者组都能接收到主题中的完整消息队列。这意味着，不同消费者组之间不会共享或竞争消息；相反，每个组都会独立接收到主题的所有消息的副本。这种设计支持了多订阅者场景，其中每个订阅者或订阅者组需要独立处理相同的消息流。

## 4.自定义分区

在 Apache Kafka 中，自定义分区器（Partitioner）允许您控制消息被发送到哪个分区。默认情况下，如果消息的 key 为 null，Kafka 将轮询所有分区来均匀地分配消息；如果指定了 key，Kafka 使用 key 的哈希值来选择分区。通过自定义分区逻辑，您可以根据特定的业务需求来优化消息的分布。

要实现自定义分区器，需要实现 Kafka 提供的 `Partitioner` 接口，并重写其中的 `partition` 方法。

并且需要在生产者设置相关参数。

```java
props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,UserDefinePartitioner.class.getName());
```

**示例：**

```java
public class UserDefinePartitioner  implements Partitioner {
    private AtomicInteger atomicInteger=new AtomicInteger(0);
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int numPartitions = cluster.partitionsForTopic(topic).size();
        if(keyBytes==null || keyBytes.length==0){
            return atomicInteger.addAndGet(1) & Integer.MAX_VALUE% numPartitions;
        } else {
            return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
        }
    }

    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        System.out.println("configure");
    }
}
```

**partition 方法参数解释**

- `String topic`：消息将要发送到的主题名称。
- `Object key`：消息的键。根据这个键来决定消息分配到哪个分区，如果键为 `null`，则可能会采用轮询或其他策略。
- `byte[] keyBytes`：消息键的序列化后的字节。这是实际用于计算分区的键的数据。
- `Object value`：消息的值。虽然这个值在计算分区时通常不会被用到，但是它在这里提供了额外的上下文。
- `byte[] valueBytes`：消息值的序列化后的字节。
- `Cluster cluster`：当前集群的元数据信息，可以用来获取主题的分区信息等。

**具体实现**

- `atomicInteger` 是一个 `AtomicInteger` 实例，用于实现轮询策略，确保消息在没有指定键的情况下均匀地分布到各个分区。

- `partition` 方法的实现包含两个主要的逻辑分支：
  - 如果消息的键（`keyBytes`）是 `null` 或者为空，则使用 `atomicInteger` 来实现简单的轮询机制。`atomicInteger.addAndGet(1) & Integer.MAX_VALUE` 确保了返回的值是一个正整数，然后对分区数（`numPartitions`）取模，得到一个分区号。这个逻辑确保了在键为空时，消息会被均匀地分配到不同的分区。
  - 如果消息有键，则使用 `Utils.murmur2(keyBytes)` 方法计算键的 MurmurHash 值，并通过 `Utils.toPositive` 确保是正数，然后同样对分区数取模。这样可以保证相同键的消息总是被分配到同一个分区，这对于保持消息顺序很重要。

- `close` 方法在分区器关闭时被调用，这里简单地输出了一个信息。这个方法可以用来清理资源，但在这个例子中没有实际的资源需要清理。

- `configure` 方法用于配置分区器。当生产者初始化分区器时，会调用此方法并传入配置。这里也只是简单地输出了一个信息。

如果我们把自定义分区简单处理，仅仅是`return 0`，即发布的消息只会在0分区，通过测试，我们发现当消费者所在的消费者组内的消费者数量等于主题的分区数时，消费者和分区之间存在一一对应关系，即每个消费者只消费一个分区的消息。

以下是开启三个分区以及三个同组消费者使用自定义分区的结果。

![image-20240411202158644](D:\笔记\MCA\消息中间件\Kafka\image/image-20240411202158644.png)

这种情况下，消息的负载均衡得到了最优的实现，每个消费者处理一个分区的数据，从而实现了高效的数据处理和消息消费。

- 如果消费者的数量**超过**分区的数量，那么将会有一些消费者不会被分配到任何分区，即这部分消费者将闲置，不会消费任何消息。
- 如果消费者的数量**少于**分区的数量，那么一些消费者将被分配多个分区，负责消费这些分区的消息。

## 5.自定义序列化

### （1）SerializationUtils

在 Apache Commons Lang 库中，`SerializationUtils` 提供了一套静态方法，用于 Java 对象的序列化和反序列化。使用这个工具类之前，需要确保您的对象实现了 `java.io.Serializable` 接口。

**依赖**

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version> <!-- 使用最新的版本 -->
</dependency>
```

**关键方法**

- `serialize(Serializable obj)`：将给定的可序列化对象序列化为字节数组。
- `deserialize(byte[] objectData)`：将给定的字节数组反序列化为对象。

### （2）序列化

```java
public class ObjectSerializer implements Serializer<Object> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        System.out.println("configure");
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return SerializationUtils.serialize((Serializable) data);
    }

    @Override
    public void close() {
        System.out.println("close");
    }
}
```

### （3）反序列化

```java
public class ObjectDeserializer implements Deserializer<Object> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        System.out.println("configure");
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        return SerializationUtils.deserialize(data);
    }

    @Override
    public void close() {
        System.out.println("close");
    }
}
```

注意：需要在消费者、生产者绑定自定义序列化以及反序列化，如下消费者属性绑定。

```java
props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,ObjectDeserializer.class.getName());
```



## 6.自定义拦截器

```java
public class UserDefineProducerInterceptor implements ProducerInterceptor {
    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        ProducerRecord wrapRecord = new ProducerRecord(record.topic(), record.key(), record.value());
        wrapRecord.headers().add("user","mashibing".getBytes());
        return wrapRecord;

    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        System.out.println("metadata:"+metadata+",exception:"+exception);
    }

    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        System.out.println("configure");
    }
}
```

注意：根据 Kafka 客户端的 API 设计，`ProducerRecord` 的实例是不可变的。这意味着，一旦创建，您无法更改其主题、键、值或任何头信息。因此，当您想要修改 `ProducerRecord`（例如，添加头信息）时，必须创建一个新的 `ProducerRecord` 实例，并在新实例上进行修改。

# 四、Kafka高级特性

## 1.Kafka 消费者的 Offset 自动控制机制

在 Kafka 中，消费者如何处理尚未订阅的主题的 offset —— 或者更准确地说，当系统尚未存储该消费者对特定分区的消费记录时，存在一个默认行为。Kafka 为消费者提供了一个默认的首次消费策略，通过 `auto.offset.reset` 配置项来控制：

`auto.offset.reset=latest`是默认设置，意味着消费者将从最新的偏移量开始消费，即消费者加入时，它将忽略任何已存在的消息，只消费新生成的消息。

- `earliest`：自动将偏移量重置到最早的偏移量，这使得消费者能够消费到在其启动之前已经发送到主题的所有消息。

- `latest`：自动将偏移量重置到最新的偏移量，与默认设置相同。

- `none`：如果找不到消费者组的先前偏移量，系统将抛出异常到消费者。这种情况下，消费者不会开始读取任何消息，直到偏移量被显式设置。

Kafka 消费者默认会定期自动提交消费的偏移量，这确保了所有消息至少被消费一次。这种行为可以通过以下参数进行配置：

- `enable.auto.commit=true`：这是默认设置，开启了偏移量的自动提交。

- `auto.commit.interval.ms=5000`：这是自动提交偏移量的时间间隔，默认为 5000 毫秒（5秒）。

如果希望手动控制偏移量的提交，以便更精确地管理消费状态，可以通过将 `enable.auto.commit` 设置为 `false` 来关闭自动提交。在这种模式下，开发者需要负责在合适的时机手动提交偏移量。需要注意的是，手动提交的偏移量应该总是比本次消费的偏移量大一，因为 Kafka 期望接收到的是下一条消息的偏移量。这样，消费者在下次 `poll` 调用时，会从这个位置开始读取数据。

在 Kafka 中，`commitAsync()` 方法用于异步提交消费者的偏移量。这种提交方式不会阻塞调用线程，允许消费者继续其消息处理流程，从而提高了消费者的吞吐量。

示例：

```java
public class KafkaConsumerDemo_02 {
    public static void main(String[] args) {
        //1.创建Kafka链接参数
        Properties props=new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOSA:9092,CentOSB:9092,CentOSC:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"group01");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);

        //2.创建Topic消费者
        KafkaConsumer<String,String> consumer=new KafkaConsumer<String, String>(props);
        //3.订阅topic开头的消息队列
        consumer.subscribe(Pattern.compile("^topic.*$"));

        while (true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            Iterator<ConsumerRecord<String, String>> recordIterator = consumerRecords.iterator();
            while (recordIterator.hasNext()){
                ConsumerRecord<String, String> record = recordIterator.next();
                String key = record.key();
                String value = record.value();
                long offset = record.offset();
                int partition = record.partition();
                Map<TopicPartition, OffsetAndMetadata> offsets=new HashMap<TopicPartition, OffsetAndMetadata>();

                offsets.put(new TopicPartition(record.topic(),partition),new OffsetAndMetadata(offset));
                consumer.commitAsync(offsets, new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        System.out.println("完成："+offset+"提交！");
                    }
                });
                System.out.println("key:"+key+",value:"+value+",partition:"+partition+",offset:"+offset);

            }
        }
    }
}
```

## 2.幂等性

在 HTTP/1.1 协议中，幂等性是一个关键概念，意味着无论对某个资源发起一次还是多次请求，只要请求是相同的，那么对于资源本身的影响应该是一致的，即多次执行和一次执行对资源的影响相同（这里不考虑网络超时等异常情况）。换句话说，对于具有幂等性质的方法来说，N 次大于 0 的相同请求对资源的副作用与单一请求相同。

自 Kafka 0.11.0.0 版本起，引入了对幂等性的支持，这是从生产者的角度出发的一项特性。幂等性保证了生产者发送的消息既不会丢失，也不会被重复处理。实现幂等性的关键在于服务端能够识别出重复的请求，并过滤掉这些请求。要做到这一点，主要依赖于两个方面：

1. **唯一标识**：为了区分请求是否重复，请求中必须包含唯一标识。例如，在支付请求中，订单号可以作为这样的唯一标识。

2. **记录处理过的请求标识**：仅有唯一标识还不够，还需要有机制记录下已经处理过的请求。这样，当收到新的请求时，可以通过比较新请求中的标识和已处理请求的记录来判断。如果记录中存在相同的标识，则认为这是一个已成功处理的重复请求，因此拒绝处理。

Kafka 通过在初始化阶段为每个生产者生成一个唯一的 ID，称为 Producer ID 或 PID，来支持幂等性，也被称为 "exactly once" 传输。为了防止消息被多次处理，消息必须严格只被持久化到 Kafka Topic 中一次。

当发送消息时，PID 和序列号会绑定在消息上一同发送给 Broker。由于序列号是从零开始且单调递增的，Broker 只会接受那些其序列号比该 PID/TopicPartition 对中最后提交的消息序列号正好大 1 的消息。如果不满足这个条件，Broker 将判定这是生产者的重复发送。

为了启用幂等性，可以在生产者配置中设置 `enable.idempotence=true`（默认为 `false`）。同时，使用幂等性时，必须将 `retries` 设置为非零值（建议启用重试），并且将 `acks` 设置为 `all`，以确保消息的可靠传输。这些配置共同作用，确保了 Kafka 生产者能够在不丢失消息的同时，避免消息重复。

- **retries**：这是 Kafka 生产者的一个配置选项，用于指定在发送消息失败时重试的次数。在网络问题或瞬时服务不可用的情况下，重试可以增加消息成功送达的机会。重试机制配合幂等性可以确保消息不会因为暂时的问题而丢失。
- **acks**：这是另一个 Kafka 生产者配置，用于控制生产者在认为消息已经成功写入 Kafka 之前需要收到的确认数。它有几个选项：
  - `acks=0`：生产者不会等待任何来自服务器的确认。这种模式具有最低的延迟，但也有最高的消息丢失风险。
  - `acks=1`（默认）：只要集群的首领节点收到了消息，生产者就会收到一个成功的响应。这种模式平衡了延迟和可靠性。
  - `acks=all`（或`acks=-1`）：只有当所有同步副本都收到消息时，生产者才会收到一个成功的响应。这提供了最高的数据可靠性保证。

示例：

`ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG`将生产者等待时间设为1ms便于测试，如果没有启动幂等性，那么生产者会多次发送消息，知道达到`retries`上限。

```java
public class KafkaProducerDemo_02 {
    public static void main(String[] args) {
        //1.创建链接参数
        Properties props=new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOS_A:9092,CentOS_B:9092,CentOS_C:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,UserDefineProducerInterceptor.class.getName());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,1);
        props.put(ProducerConfig.ACKS_CONFIG,"-1");
        props.put(ProducerConfig.RETRIES_CONFIG,3);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);

        //2.创建生产者
        KafkaProducer<String,String> producer=new KafkaProducer<String, String>(props);

        //3.封账消息队列
        for(Integer i=0;i< 10;i++){
            ProducerRecord<String, String> record = new ProducerRecord<>("topic01", "key" + i, "value" + i);
            producer.send(record);
        }
        producer.close();
    }
}
```

## 3.事务控制

在 Kafka 0.11.0.0 版本中，除了引入幂等性保证之外，还增加了对事务的支持。这两个特性共同提高了消息处理的可靠性和完整性。

### （1）幂等性与事务

- **幂等性**：Kafka 的幂等性能够保证单条记录在被发送到其分区时的原子性，即无论发送多少次，都能保证消息不会被重复处理。这对于单个消息或单个分区的操作非常有效。

- **事务**：为了在多条记录和跨多个分区的情况下保持完整性，Kafka 引入了事务操作。事务可以保证一组消息要么全部被成功处理，要么全部不处理，从而实现跨分区的一致性。

### （2）Kafka 事务的类型

Kafka 支持两种类型的事务：

- **生产者事务Only**：这种事务仅涉及生产者发送消息的操作，确保消息以原子方式被处理。

- **消费者&生产者事务**：这种事务涵盖了消费者消费消息和生产者发送消息的整个过程，确保数据的一致性和完整性。

### （3）事务隔离级别

默认情况下，消费者可能会读取到未提交的数据（`read_uncommitted` 级别）。因此，在启用事务后，需要将消费者的事务隔离级别设置为 `read_committed`，以确保只消费已成功提交的事务中的数据。

- `isolation.level`：这个配置有两个选项 `read_committed` 和 `read_uncommitted`。在开启事务控制时，应将消费者的事务隔离级别设置为 `read_committed`。

### （4）开启生产者事务

要启用生产者事务，只需在生产者配置中指定 `transactional.id` 属性。一旦开启了事务，幂等性也会自动被启用，因为事务的正确执行依赖于幂等性的保证。重要的是，`transactional.id` 必须是唯一的，因为在任何给定时刻，只能有一个生产者实例与一个 `transactional.id` 关联。如果尝试使用相同的 `transactional.id` 创建多个生产者实例，那么除了最初的实例之外，其他的都将被关闭。

示例：

```java
public class KafkaProducerConsumer {
    public static void main(String[] args) {

        //1.生产者&消费者
        KafkaProducer<String,String> producer=buildKafkaProducer();
        KafkaConsumer<String, String> consumer = buildKafkaConsumer("group01");

        consumer.subscribe(Arrays.asList("topic01"));
        producer.initTransactions();//初始化事务

        try{
            while(true){
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
                Iterator<ConsumerRecord<String, String>> consumerRecordIterator = consumerRecords.iterator();
                //开启事务控制
                producer.beginTransaction();
                Map<TopicPartition, OffsetAndMetadata> offsets=new HashMap<TopicPartition, OffsetAndMetadata>();
                while (consumerRecordIterator.hasNext()){
                    ConsumerRecord<String, String> record = consumerRecordIterator.next();
                    //创建Record
                    ProducerRecord<String,String> producerRecord=new ProducerRecord<String,String>("topic02",record.key(),record.value());
                    producer.send(producerRecord);
                    //记录元数据
                    offsets.put(new TopicPartition(record.topic(),record.partition()),new OffsetAndMetadata(record.offset()+1));
                }
                //提交事务
                producer.sendOffsetsToTransaction(offsets,"group01");
                producer.commitTransaction();
            }
        }catch (Exception e){
            producer.abortTransaction();//终止事务
        }finally {
            producer.close();
        }
    }
    public static KafkaProducer<String,String> buildKafkaProducer(){
        Properties props=new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOSA:9092,CentOSB:9092,CentOSC:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"transaction-id");
        return new KafkaProducer<String, String>(props);
    }
    public static KafkaConsumer<String,String> buildKafkaConsumer(String group){
        Properties props=new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOSA:9092,CentOSB:9092,CentOSC:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG,group);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_committed");

        return new KafkaConsumer<String, String>(props);
    }
}
```

# 五、Kafka架构进阶

## 1.数据同步机制

在 Kafka 中，每个主题被划分成多个分区，而每个分区的数据又是按照被称为“Segments”的存储文件块来组织的。分区中的日志，实质上是存储在磁盘上的一系列日志消息。Kafka 能够确保在单个分区内的消息是按照它们到达的顺序来存储的，从而保证了消息的有序性。在每个分区中，有一个 Leader 负责处理读写操作，而 Follower 则负责同步数据以确保高可用性和容错性。

在 Kafka 0.11 版本之前，Kafka 采用 highwatermark（高水位线）机制来保证数据的一致性和同步。但是，基于 highwatermark 的数据同步策略可能导致数据不一致或乱序的问题。

### （1）Kafka数据同步的相关概念：

**LEO（Log End Offset）**：表示分区中最后一条消息的下一个位置。每个分区的副本都维护有自己的 LEO，这标识着该副本的当前长度。

**HW（High Watermark）**：称为高水位线，是所有副本中已成功备份的数据的最小偏移量。所有小于或等于高水位线的数据都被认为是可靠的，当所有副本都成功备份后，Leader 会更新这个水位线。

**ISR（In-sync Replicas）**：即同步副本集，是指当前与 Leader 数据保持同步的副本集合。如果副本在 `replica.lag.time.max.ms` 设定的时间内没有发起 fetch 请求，或者即便发起了 fetch 请求但未能在此时间内追上 Leader 的数据，则会被从 ISR 列表中移除。从 Kafka 0.9.0 版本开始，系统移除了 `replica.lag.max.messages` 的设置，以避免因为基于消息数量的限制导致副本频繁地加入或退出 ISR。

### （2）HW的缺陷

#### **数据丢失**

假设一个 Kafka 分区有一个 Leader 和两个 Follower。每个副本（包括 Leader 和 Follower）都有自己的本地日志以及维护的LEO和HW。

**故障前的状态**

1. **消息写入**：Leader 接收到一批消息并将它们写入自己的日志，然而，在消息被写入磁盘之前，Leader 将这些消息的偏移量通知给 Follower，并更新自己的 LEO。
2. **HW 更新**：在正常情况下，当所有活跃的 Follower 都已成功复制这些消息，并各自更新了它们的 LEO 之后，Leader 会根据最慢的 Follower 更新 HW。这确保了 HW 之前的消息已经被所有副本复制，因此对消费者可见。

**故障发生时**

1. **Leader 宕机**：如果 Leader 在将消息写入磁盘之前宕机（由于配置错误或特定的失败场景），这批消息尽管已经被 Follower 复制（并可能被消费者读取，如果消费者读取了处于 HW 和 LEO 之间的消息），但这些消息并未被持久化。
2. **Leader 切换**：新的 Leader 被选举出来后，它的 HW 初始设定为它自己的 HW，这可能不包括之前那批未持久化的消息，因为新 Leader 的 HW 是基于它自己的日志状态来确定的。

**HW 的变化和问题**

- 在故障发生前，Follower 根据从旧 Leader 接收到的消息更新它们的 LEO，但如果这些消息没有在旧 Leader 上持久化，新 Leader 在接管后可能不包含这些消息。
- 如果新 Leader 在旧 Leader 宕机前没有完全复制所有消息，那么它的 HW 可能不会反映那些临时存在于旧 Leader 和某些 Follower 中的消息。
- 这导致了数据丢失：尽管某些消息可能已经被部分消费者看到（尤其是读取了 HW 之后但在 LEO 之前的消息），但这些消息在新 Leader 选举后不再可用。

#### **数据不一致**：

假设我们有一个 Kafka 分区，其中包括一个 Leader 副本和两个 Follower 副本（Follower1 和 Follower2）。这里是一个具体的场景分析，展示了高水位线（HW）的工作方式和潜在的数据不一致问题：

**故障前的状态**

1. **正常操作**：在正常操作期间，Leader 接收来自生产者的消息并将其写入自己的日志，同时也向 Follower 分发这些消息以进行同步。每当 Follower 成功复制了消息，它们就会向 Leader 发送确认。一旦所有活跃的 Follower 都确认复制了特定消息，Leader 就会更新 HW，表明这些消息已经安全地同步到所有副本。
2. **网络分区**：由于网络分区或其他原因，假设 Follower2 与集群的其他部分失去了联系，错过了一系列消息的更新。
3. **Follower2 重连**：经过一段时间后，网络分区解除，Follower2 重新加入集群并尝试追赶丢失的消息。此时，Follower2 的 LEO 落后于 Leader 和 Follower1。

**故障和新 Leader 的选举**

1. **Leader 宕机**：在 Follower2 还未完全追赶上最新状态时，如果当前的 Leader 突然宕机，集群会进行 Leader 选举。
2. **Follower2 成为新 Leader**：由于某些原因（例如，选举算法或者 Follower1 也遇到了问题），失联且信息落后的 Follower2 被选为新的 Leader。

**数据不一致的产生**

1. **新 Leader 的 HW**：作为新 Leader，Follower2 的 HW 是基于它自己的日志状态来设置的。因为 Follower2错过了一些消息更新，它的 HW 可能比旧 Leader 的要低。
2. **消息不可达**：这意味着在旧 Leader 下已被认为是“安全”的消息（即在旧 Leader 的 HW 之下的消息），在新 Leader 的 HW 下可能变得不可达，因为新 Leader 的 HW 反映了更早的状态。
3. **尝试同步**：虽然新 Leader 会尝试从其他副本（此处为 Follower1）同步缺失的消息以更新自己的状态，但在同步完成前，任何落后于新 Leader HW 的消息都对消费者不可见，从而导致数据不一致的问题。

### （3）Leader Epoch 机制

在 Kafka 0.11 版本之前，副本间的数据同步主要依赖于高水位线（HW）机制。然而，这种方式在一定条件下可能导致数据不一致或数据丢失。为了解决这些问题，从 Kafka 0.11 版本起，引入了“Leader Epoch”机制，它显著改进了数据同步和截断的过程。

**引入 Leader Epoch 的背景**

- **Leader Epoch 概念**：每个 Leader 被赋予一个唯一的“Leader Epoch”编号，这是一个由控制器（Controller）管理的 32 位数值，记录在 Zookeeper 的分区状态信息中。每次 Leader 更换时，这个编号会通过 LeaderAndIsrRequest 消息传递给新的 Leader，用以标记 Producer 发送的每条消息，从而提高数据同步的精确性。

- **消息格式的改进**：为适应 Leader Epoch 机制，Kafka 更新了其消息格式，让每个消息批次都包含一个 4 字节的 Leader Epoch 号。在每个日志目录中，新增了一个“Leader Epoch Sequence”文件，用于记录 Leader Epoch 的序列和每个 Epoch 对应的消息起始偏移量（Start Offset）。这些信息同时被缓存在副本的内存中，便于快速访问。

- **起始偏移量（Start Offset）**：与每个 Leader Epoch 关联的是该 Epoch 开始时的日志偏移量。这个偏移量标记了新 Leader 开始写入日志的起点。这是关键信息，因为它允许 Kafka 在需要时进行精确的日志截断，例如，如果一个旧的 Leader 在新 Leader 开始写入数据之后仍然错误地继续接收和处理数据。

**副本的角色转换**

- **从 Follower 到 Leader**：当 Follower 被提升为 Leader 时，会在“Leader Epoch Sequence”文件中记录新的 Leader Epoch 号，该副本的日志结束偏移量（LEO），被作为起始偏移量（Start Offset），写入文件内容。这一信息随后被刷新到磁盘，确保所有新生成的消息批次都携带最新的 Leader Epoch 标记。

- **从 Leader 到 Follower**：若副本需从“Leader Epoch Sequence”加载数据，它会把数据载入内存，并向当前分区的 Leader 发送一个包含最新 Epoch ID 和起始偏移量的请求。Leader 收到请求后，会返回相应的最后偏移量，这可能是该 Epoch ID 的起始偏移量或该 Epoch ID 的日志末尾偏移量。

**同步过程**

​	**情形1：Follower 的 Offset 比 Leader 的小**

在这种情况下，Follower 的日志条目较少，可能是由于暂时的故障或延迟导致未能及时从 Leader 同步日志。Kafka 的处理流程如下：

1. **发现差异**：通过定期的心跳和同步请求，Follower 和 Leader 之间会持续对比彼此的最新偏移量（Offset）和 Leader Epoch。
2. **请求缺失日志**：当 Follower 发现自己的最新偏移量小于 Leader 时，会发起一个 Fetch 请求，请求从从其自身的最新偏移量开始请求数据，到当前最大偏移量的所有后续日志条目。
3. **数据同步**：Leader 接收到 Fetch 请求后，从指定的偏移量开始返回日志条目给 Follower。这些条目包括所有未同步的消息，以及可能的任何在此期间提交的新消息。
4. **日志更新**：Follower 接收到数据后，会将这些日志条目追加到本地日志文件中，更新自己的偏移量，从而与 Leader 保持一致。

**情形2：Follower 的 Leader Epoch 的信息 startOffset 比 Leader 返回的 LastOffset 要大**

这种情形涉及到数据截断和 Leader 变更，具体处理过程如下：

1. **Epoch 不匹配发现**：Follower 在同步数据时，如果发现其记录的某个 Leader Epoch 的起始偏移量（startOffset）大于从当前 Leader 获得的相应 Epoch 的最后偏移量（LastOffset），这表明在新的 Leader 确立后，之前的数据可能已被截断。
2. **调整 Epoch 文件**：为了修正这种不一致，Follower 需要更新自己的 Leader Epoch 文件，将当前的偏移量调整为 Leader 的 LastOffset。这通常意味着需要截断自己日志中超出该偏移量的部分。
3. **日志截断**：Follower 根据从 Leader 收到的 LastOffset 进行日志截断，移除所有超出这个偏移量的日志条目，以保持数据的一致性。
4. **刷新到磁盘**：更新的 Leader Epoch 和起始偏移量信息被记录并刷新到磁盘上的 Leader Epoch 文件中。
5. **本地日志更新**：随后，Follower 继续从 Leader Fetch 新的日志条目，并将其追加到本地日志文件中，确保从正确的偏移量开始同步。

## 2.Kafka-Eagle集成



## 3.Kafa-SpringBoot集成

