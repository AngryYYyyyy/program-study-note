# 学习目标

> 理解五种IO模型的基本概念, 重点是IO多路转接
>
> 掌握select编程模型, 能够实现select版本的TCP服务器
>
> 掌握poll编程模型, 能够实现poll版本的TCP服务器
>
> 掌握epoll编程模型, 能够实现epoll版本的TCP服务器
>
>  理解epoll的LT模式和ET模式
>
> 理解select和epoll的优缺点对比

# 一、IO模型

## 1.1 理解IO

> I/O指的是程序与外部世界（如文件系统、网络、外部设备等）之间的数据交互。
>
> I/O 操作涉及两个基本过程：等待时间和数据拷贝

## 1.2 同步/异步

### 1.2.1 同步I/O

> 在同步I/O模型中，应用程序直接参与I/O操作，并且当一个I/O请求正在处理时，应用程序会被阻塞，直到该请求完成。

### 1.2.2 异步I/O

> 在异步I/O模型中，应用程序发起I/O请求后立即返回，不需要等待I/O操作完成。当I/O操作完成时，系统（通常是内核）会以某种方式通知应用程序

## 1.3 五种IO模型

> 在计算机网络中，当我们谈论I/O模型时，通常是指在进行输入/输出操作时程序如何管理执行流程。
>
> 在UNIX环境下，特别是与套接字I/O相关的环境，存在五种基本的I/O模型。这些模型描述了数据如何从应用程序传输到其所在设备（例如网络接口卡），或从设备传输到应用程序。

### 1.3.1 **阻塞式I/O**

> - 调用I/O操作时，应用程序需要等待I/O完成后才能进行其他操作。
> - 在I/O操作完成前，应用程序是被阻塞的。

#### （1）示例

> `cin`（在C++中用于标准输入）是阻塞式的I/O函数。当你在程序中使用`cin`等待用户输入时，程序的执行会暂停，直到用户提供了输入并按下Enter键。

```c++
#include <iostream>
#include <string>
int main()
{
    std::string buffer;
    while(true)
    {
        std::cout<<"请输入：";
        std::cin>>buffer;// 这里会阻塞，等待用户输入
        std::cout<<"输入结果："<<buffer<<std::endl;
    }
}
```

### 1.3.2 **非阻塞式I/O**

> - 应用程序执行I/O调用，无需等待其完成。
> - 如果I/O未准备好，调用会立即返回一个错误。
> - 应用程序需要轮询，检查I/O是否准备好。

#### （1）示例

> 该代码的目的是展示如何在等待用户输入时同时执行其他任务。为了实现这一目的，代码将标准输入设置为非阻塞模式，并在一个无限循环中同时进行输入和其他任务。

```c++
#include <iostream>
#include <string>
#include <functional>
#include <vector>
#include <unistd.h>
#include "util.hpp"
void show1()
{
    std::cout<<"111111111"<<std::endl;
}
void show2()
{
    std::cout<<"222222222"<<std::endl;
}
void show3()
{
    std::cout<<"333333333"<<std::endl;
}
using fun_t =std::function<void()>;
int main()
{
    util::setNonBlock(0);   //将标准输入设为非阻塞,具体实现在下文fcntl函数
    std::vector<fun_t> funs;
    funs.push_back(show1);
    funs.push_back(show2);
    funs.push_back(show3);

    char buffer[256]; 
    while(true)
    {
        std::cout << "请输入：";
        ssize_t bytesRead = read(0, buffer, sizeof(buffer) - 1);

        if (bytesRead > 0)
        {
            buffer[bytesRead] = '\0';  // 添加字符串结束标记
            std::cout << "输出结果：" << buffer << std::endl;
        }
        //做其他事情
        std::cout<<std::endl;
        for(auto e:funs)
        {
            e();
        }
        sleep(1);
    }
}
```

#### （2）fcntl函数

> `fcntl()` 函数是一个多功能的文件描述符控制函数，它在 Unix 和类 Unix 系统中广泛使用。该函数提供了多种文件描述符的操作，如获取/设置文件描述符的属性、文件锁等

```c++
#include <fcntl.h>
int fcntl(int fd, int cmd, ... /* arg */ );
```

示例：将一个文件设置为非阻塞模式

```c++
#include <fcntl.h>
bool setNonBlock(int fd) 
{
    int flags;
    flags = fcntl(fd, F_GETFL, 0);
    if (flags == -1) return flase;
    if (fcntl(fd, F_SETFL, flags |= O_NONBLOCK) == -1) return flase;
    return true;
}
```



### 1.3.3 **I/O多路转接**

> - 应用程序可以监视多个文件描述符（例如，多个套接字）。
> - 一旦某个描述符准备好（例如，连接建立、数据可读/可写/异常等），相应的通知就会发送给应用程序。
> - 这种方法允许一个单一的进程管理多个I/O操作。

### 1.3.4 **信号驱动I/O（SIGIO）**

> - 应用程序告诉内核：当描述符上的I/O活动开始时，发送一个信号。
> - 应用程序继续执行，当I/O准备好时，它会收到一个信号。
> - 这之后，应用程序可以开始真正的I/O操作。

### 1.3.5 **异步I/O（POSIX的aio_系列函数）**

> - 应用程序启动一个I/O操作，无需等待其完成。
> - 当I/O操作完成时，应用程序会收到通知。
> - 这与信号驱动I/O不同，因为真正的I/O操作是异步进行的，而不是从开始的信号。



# 二、多路转接

## 2.1 select

> `select()` 是一个用于多路复用输入/输出的系统调用，它==允许程序监视多个文件描述符==（例如，sockets、pipes 或真实的文件）来查看它们是否准备好进行读、写或是否有异常条件发生。它的主要优势是能够等待多个 I/O 操作中的任何一个而不是仅仅一个，从而使应用程序能够同时处理多个连接。

### 2.1.1 select（）函数

```c++
#include <sys/select.h>
int select(int nfds, fd_set *readfds, fd_set *writefds,
           fd_set *exceptfds, struct timeval *timeout);
```

#### （1）fd_set类型

> `fd_set` 是一个数据类型，用于在 `select()` 函数调用中表示==文件描述符集合==。
>
> 这不是一个简单的数组，而是一个特定大小的==位数组==。
>
> 由于文件描述符是非负整数，所以可以将其视为位数组的索引，其中位的设置表示文件描述符在集合中，未设置表示文件描述符不在集合中。

#### （2）参数

> - **nfds**：最大的文件描述符编号+1。
> - **readfds**：需要检查==是否可以读取的文件描述符的集合==。
> - **writefds**：需要检查==是否可以写入的文件描述符的集合==。
> - **exceptfds**：需要检查是否有异常条件的文件描述符的集合。
> - **timeout**：等待的最大时间；如果设置为 `NULL`，`select()` 将无限期等待。

#### （3）返回值

> **正值**：表示在指定的时间限制内有文件描述符变得活跃。返回的正值==表示活跃文件描述符的数量==。
>
> **0**：表示在指定的时间限制内没有文件描述符变得活跃。
>
> **-1**：表示发生错误。
>
> - `EBADF`：集合中的一个或多个文件描述符无效。
> - `EINTR`：在 `select()` 能够完成之前，一个信号被交付到了调用进程。
> - `EINVAL`：`nfds` 的值无效，或 `timeout` 的值是负数。
> - `ENOMEM`：不能为内部数据结构分配内存。

#### （4）相关宏操作

> - `FD_ZERO(fd_set *set);` 清除集合。
> - `FD_SET(int fd, fd_set *set);` 添加一个文件描述符到集合。
> - `FD_CLR(int fd, fd_set *set);` 从集合中删除一个文件描述符。
> - `FD_ISSET(int fd, fd_set *set);` 检查集合中的文件描述符是否可读/写/异常。

### 2.1.2 理解连接

> **连接建立**：当两个通信实体（例如，两台计算机上的进程）试图建立一个TCP连接时，它们执行一个称为三次握手的过程。在这个过程中，发送和接收连接请求的消息涉及到I/O操作。
>
> **数据交换**：一旦连接建立，两个实体就可以通过该连接进行数据交换。每次数据的发送和接收都涉及到I/O。
>
> **连接终止**：与建立连接的过程相似，连接的终止（如TCP的四次挥手）也涉及到I/O操作，因为它涉及到发送和接收终止连接的消息。
>
> 总之，从底层来看，网络中的“连接”是由数据在物理介质（如电缆、无线频道等）上的移动实现的，这些数据的发送和接收是通过I/O操作完成的。因此，可以说建立连接的本质是I/O。

### 2.1.3 基于select的TCP服务器

#### （1）sock.hpp

这段代码展示了一个简单的封装TCP socket操作的C++类 `sock`。它提供了创建、绑定、监听和接受连接的静态方法。下面是对每个部分的简要说明和一些建议：

创建socket (`creatSock`)

- 创建一个IPv4的TCP socket。
- 设置socket选项以允许地址和端口重用，这==对于服务器快速重启和绑定到其它服务使用过的端口很有帮助。==
- 如果创建socket失败，程序会退出。

注意：

- `SO_REUSEPORT|SO_REUSEPORT` 应该是 `SO_REUSEADDR|SO_REUSEPORT`。`SO_REUSEADDR` 允许其他socket绑定到这个端口，除非有活动的监听socket绑定到该端口。`SO_REUSEPORT` 允许多个socket绑定到相同的端口，条件是它们必须有相同的socket类型和相同的地址族。

绑定socket (`bindSock`)

- 绑定socket到一个本地地址和端口上。端口号通过参数传递给方法，地址设置为 `INADDR_ANY`，这表示socket会监听所有网络接口。
- 如果绑定失败，程序会退出。

监听 (`listenSock`)

- 开始监听来自客户端的连接请求。`backlog` 参数指定了队列中最多可以有多少个待处理的连接。
- 如果监听失败，程序会退出。

接受连接 (`acceptSock`)

- 接受一个来自客户端的连接请求，并返回一个新的socket文件描述符，用于与客户端通信。
- 接收客户端的地址信息，并可选地返回客户端的IP地址和端口号给调用者。
- 如果接受连接失败，程序会退出。

注意：

- 在 `acceptSock` 方法中，有关于 `inet_ntoa(peer.sin_addr)` 的使用。请注意，`inet_ntoa` 返回的字符串是静态分配的，这意味着每次调用都会覆盖上次的结果。如果你需要保留IP地址，应该立即复制这个字符串。
- 使用 `exit` 函数直接退出程序可能不是最佳实践，尤其是在库或类中。更好的做法可能是返回错误代码，或者抛出一个异常，让调用者决定如何处理错误。

```c++
#include <sys/types.h>          
#include <sys/socket.h>
#include <string>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <cstring>
class sock
{
public:
    //已完成连接队列的最大长度
    static const int backlog=20;
    //创建
    static int creatSock()
    {
        int sockfd=socket(AF_INET,SOCK_STREAM,0);
        if(sockfd<0)
        {
            exit(1);
        }
        int option=1;
        //允许端口重用
        setsockopt(sockfd,SOL_SOCKET,SO_REUSEPORT|SO_REUSEPORT,&option,sizeof(option));
        return sockfd;
    }
    //绑定
    static void bindSock(int sockfd,uint16_t port)
    {
        struct sockaddr_in local;
        memset(&local,0,sizeof local);
        local.sin_family=AF_INET;
        local.sin_port=htons(port);
        local.sin_addr.s_addr=INADDR_ANY;
        if(bind(sockfd,(const sockaddr*)&local,sizeof local)<0)
        {
            exit(2);
        }
        return;
    }
    //监听
    static void listenSock(int sockfd)
    {
        if(listen(sockfd,backlog)==-1)
        {
            exit(3);
        }
        return;
    }
    //连接
    static int acceptSock(int sockfd,std::string *ip,uint16_t *port)
    {
        struct sockaddr_in peer;
        socklen_t len=sizeof peer;
        int serverSock=accept(sockfd,(sockaddr *)&peer,&len);
        if(serverSock==-1)
        {
            exit(4);
        }
        if(ip) *ip=inet_ntoa(peer.sin_addr);//函数的调用者可能决定不传递其中的某个指针,表示他们对这个特定的信息不感兴趣。
        if(port) *port=ntohs(peer.sin_port);//判断确保传入的指针是有效的，从而防止可能的空指针解引用错误。
        return serverSock;
    }
};
```



#### （2）select.hpp

这段代码实现了一个基于 `select` 系统调用的多路IO复用类 `Select`，主要用于同时监视多个文件描述符（FDs）的读、写和异常事件。通过封装 `select` 函数和相关操作，这个类提供了一种有效的方法来处理多个客户端连接和IO事件。

类设计

- `Select` 类通过维护一个文件描述符的集合 `_fds`，允许用户添加或删除要监视的FD。
- 构造函数接受一个监听socket（`listensock`），将其添加到 `_fds` 集合中，这通常是服务器监听客户端连接请求的socket。
- `parameterResetSelect` 方法设置 `select` 函数的读、写和异常FD集合，并执行 `select` 调用。这个方法返回 `select` 函数的返回值，即就绪的FD数量，或者在错误的情况下返回-1。
- `handlerEvent` 方法根据 `select` 的结果处理就绪的事件，可以处理连接请求、读取数据和异常事件。它接受三个函数作为参数（`send`、`recv`、`except`），分别对应写、读和异常处理的回调函数。

```c++
#include <iostream>
#include <string>
#include <vector>
#include <sys/select.h>
#include <functional>
#define DEFAULT -1
using fun_t=std::function<int(int)>;
class Select
{
public:
    Select(int listensock):_fds(sizeof(fd_set)*8,DEFAULT)
    {
        _fds[0]=listensock;
    }
    //参数重置的多路转接
    int parameterResetSelect()
    {
        //只针对读事件处理
        //清理_readfds
        FD_ZERO(&_readfds);
        int maxFd=DEFAULT;
        for(auto e:_fds)
        {
            if(e==DEFAULT) continue;
            //将感兴趣的读事件文件描述符填充进_readfds
            FD_SET(e,&_readfds);
            //调整最大fd
            if(e>maxFd) maxFd=e;
        }
        //timeval等待策略，可以设置阻塞和非阻塞，也可以设置限制时间
        //设置两秒的超时时间
        struct timeval timeout={2, 0};
        //超时集合全部置0，有就绪事件则只将其置1，其余置0，因此每一次select都需要重置集合参数
        return select(maxFd+1,&_readfds,&_writefds,&_exceptfds,&timeout);
    }
    //处理事件
    void handlerEvent(fun_t send,fun_t recv,fun_t except)
    {
        //遍历_fds，判断事件是否就绪
        for(auto& e:_fds)
        {
            if(e==DEFAULT) continue;
            if(e==_fds[0])
            {
                // 连接事件
                if (FD_ISSET(e, &_readfds))
                {
                    // 连接事件就绪
                    uint16_t port;
                    std::string ip;
                    int serverSock = sock::acceptSock(e, &ip, &port);
                    if (serverSock == -1)
                        break;
                    std::cout << "获取新连接成功: " << ip << ":" << port << " | sock: " << serverSock << std::endl;
                    for(auto &x:_fds)
                    {
                        if(x==DEFAULT) 
                        {
                            x=serverSock;
                            break;
                        }
                    }
                }
            }
            else
            {
                //普通IO事件
                if(FD_ISSET(e, &_readfds))
                {
                    // 普通IO读事件就绪
                    //判断回调函数是否存在，即用户使用接口时可能不关心读事件，即传入nullptr
                    if(recv) 
                    {
                        //当返回值小于0时，说明读取错误或读端关闭，即不再需要关注这个文件描述符
                        if(recv(e)<0)
                        {
                            //先关闭文件描述符
                            close(e);
                            e=DEFAULT;
                        }
                    }

                }
                //以下先不做处理
                if(FD_ISSET(e, &_writefds))
                {
                    // 普通IO写事件就绪
                    if(send) send(e);
                }
                if(FD_ISSET(e, &_exceptfds))
                {
                    // 普通IO异常事件就绪
                    if(except) except(e);
                }
            }
        }
    }
private:
    //保存需要关注的fd文件描述符
    std::vector<int> _fds;
    fd_set _readfds;
    fd_set _writefds;
    fd_set _exceptfds;
};
```



#### （3）server.hpp

```c++
#pragma once
#include <string>
#include <iostream>
#include <unistd.h>
#include "sock.hpp"
#include "select.hpp"
using fun_t=std::function<int(int)>;
class server
{
public:
    server(uint16_t port,fun_t func) : _port(port),_recv(func),_send(nullptr), _except(nullptr)
    {
        _listenSock = sock::creatSock();
        std::cout<<"sock cread sucess:"<<_listenSock<<std::endl;
        sock::bindSock(_listenSock, _port);
        std::cout<<"sock bind sucess"<<std::endl;
        sock::listenSock(_listenSock);
        std::cout<<"sock listen sucess"<<std::endl;
        _select = new Select(_listenSock);
        std::cout<<"select init sucess"<<std::endl;
    }
    ~server()
    {
        close(_listenSock);
    }

public:
    void loop()
    {
        while (true)
        {
            int n = _select->parameterResetSelect();
            switch (n)
            {
            case 0:
                std::cout << "time out ... : " << (unsigned long)time(nullptr) << std::endl;
                break;
            case -1:
                std::cerr << errno << " : " << strerror(errno) << std::endl;
                break;
            default:
                _select->handlerEvent(_send,_recv,_except);
            }
        }
    }
private:
    int _listenSock;
    uint16_t _port;
    std::string _ip;
    Select *_select;
    fun_t _send;
    fun_t _recv;
    fun_t _except;
};
```



#### （4）main.cc

```c++
#include "server.hpp"
int func(int sock)
{
        char buffer[1024];
        ssize_t s = recv(sock, buffer, sizeof(buffer), 0); // 不会阻塞
        if (s > 0)
        {
            buffer[s] = 0;
            std::cout << "client[" << sock << "]# " << buffer << std::endl;
        }
        else if (s == 0)
        {
            std::cout << "client[" << sock << "] quit, server close " << sock << std::endl;
        }
        else
        {
            std::cout << "client[" << sock << "] error, server close " << sock << std::endl;
        }
        return s;
}

void usage(std::string process)
{
    std::cerr << "\nUsage: " << process << " port\n"
              << std::endl;
}
int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        usage(argv[0]);
        exit(-1);
    }
    uint16_t port = atoi(argv[1]);
    server ser(port, func);
    ser.loop();
    return 0;
}
```



### 2.1.4 优缺点

> `select` 是一个经典的I/O多路复用技术，它允许程序员在单一线程内同时监视多个文件描述符（例如sockets）的活动。虽然它是一个有力的工具，但它也有其局限性。

#### （1）优点

> 1. **简单性**：对于简单的应用程序，`select` 提供了一个直观的API，使得监视文件描述符变得相对简单。
>
> 2. **不仅仅是sockets**：虽然 `select` 经常用于sockets，但它也可以用于任何文件描述符，包括普通文件、管道和更多内容（注意：这在不同的平台和操作系统上可能有所不同）。

#### （2）缺点

> 1. **扩展性问题**：`select`使用三个独立的文件描述符集合（读、写和异常）来监视活动。这些集合的大小是固定的（通常由`FD_SETSIZE`定义，经常设置为1024），这意味着`select`无法直接监视超过`FD_SETSIZE`的描述符。这在大型应用程序或高性能服务器上可能是一个问题。
> 2. **效率问题**：每次调用`select`时，你都必须重新初始化文件描述符集合，这可能在有大量文件描述符时非常低效。此外，当`select`返回时，你必须遍历所有文件描述符以确定哪些是活动的。
>

## 2.2 poll

### 2.2.1 poll（）函数

```c++
#include <poll.h>
int poll(struct pollfd *fds, nfds_t nfds, int timeout);
```

#### （1）pollfd类型

```c++
struct pollfd {
    int   fd;         // 文件描述符
    short events;     // 请求的事件
    short revents;    // 返回的事件
};
```

> 其中 `events` 和 `revents` 的可能值包括：
>
> - `POLLIN`: 数据如普通数据、优先级数据、文件尾标记或进程之间的通信事件可以被读取。
> - `POLLOUT`: 普通数据可以写入。
> - `POLLERR`: 指定的文件描述符发生错误。
> - `POLLHUP`: 发生挂断。
> - `POLLNVAL`: 文件描述符不是一个打开的文件。

#### （2）参数

> - `fds`: 一个指向 `pollfd` 结构体数组的指针，其中每个结构体都表示一个要监控的文件描述符及其对应的事件。
> - `nfds`: 要监控的文件描述符数量（即 `fds` 数组的大小）。
> - `timeout`: 指定 `poll()` 应该等待的最长时间（以毫秒为单位）。如果 `timeout` 是 `-1`，`poll()` 会无限期等待。

#### （3）返回值

> - `> 0`: 准备好的文件描述符的数量。
> - `0`: 指定的超时时间已过，但没有任何文件描述符准备好。
> - `-1`: 出错，并将 `errno` 设置为相应的错误码

### 2.2.2 基于poll的TCP服务器

#### （1）poll.hpp

这段代码实现了一个基于 `poll` 系统调用的多路IO复用类 `Poll`。与 `select` 类似，`poll` 提供了一种方法来监视和等待多个文件描述符上的事件，但它解决了 `select` 的一些限制（例如文件描述符数量的限制）并提供了更好的可扩展性。这个类封装了 `poll` 的基本用法，使得它可以用于高效地管理多个网络连接和IO事件。下面是对这段代码的详细分析：

类设计

- `Poll` 类通过维护一个 `pollfd` 结构的数组 `_fds` 来跟踪要监视的文件描述符及其事件。`pollfd` 结构包含三个字段：`fd` 表示文件描述符，`events` 表示感兴趣的事件，`revents` 表示实际发生的事件。
- 构造函数接受一个监听socket（`listensock`），将其设置为第一个要监视的文件描述符，并注册 `POLLIN` 事件，表示对读取操作的关注。

参数重置的多路转接 (`parameterResetSelect`)

- `parameterResetSelect` 方法封装了 `poll` 函数的调用，设置了超时时间（这里为1000毫秒，即1秒），并返回 `poll` 的返回值，表示就绪的文件描述符数量。

处理事件 (`handlerEvent`)

- `handlerEvent` 方法遍历 `_fds` 数组，检查每个文件描述符上是否有就绪的事件。
- 如果检测到监听socket上有 `POLLIN` 事件，表示有新的连接请求，使用 `sock::acceptSock` 方法接受新连接，并将新socket添加到 `_fds` 数组中，继续监视其上的 `POLLIN` 事件。
- 对于非监听socket，如果有 `POLLIN` 事件发生，调用 `recv` 回调函数处理读事件。如果读操作返回小于或等于0，表示客户端关闭连接或读取错误，随后关闭文件描述符，并将其从 `_fds` 数组中移除。

```c++
#pragma once
#include <iostream>
#include <string>
#include <vector>
#include <functional>
#include "sock.hpp"
#include <poll.h>
#include <unistd.h>
#define DEFAULT -1
#define NUM 1024
using fun_t=std::function<int(int)>;
class Poll
{
public:
    Poll(int listensock):_fds(new pollfd[NUM])
    {
        for(int i=0;i<NUM;i++)
        {
            _fds[i].fd=DEFAULT;
            _fds[i].events=0;
            _fds[i].revents=0;
        }
        _fds[0].fd=listensock;
        _fds[0].events = POLLIN;
    }
    //参数重置的多路转接
    int parameterResetSelect()
    {
        int timeout=1000;
        return poll(_fds,NUM,timeout);
    }
    //处理事件
    void handlerEvent(fun_t send,fun_t recv,fun_t except)
    {
        for(int i=0;i<NUM;i++)
        {
            if(_fds[i].fd==DEFAULT) continue;
            if(_fds[i].fd==_fds[0].fd)
            {
                // 连接事件
                if (_fds[i].revents&POLLIN)
                {
                    // 连接事件就绪
                    uint16_t port;
                    std::string ip;
                    int serverSock = sock::acceptSock(_fds[i].fd, &ip, &port);
                    if (serverSock == -1)
                        break;
                    std::cout << "获取新连接成功: " << ip << ":" << port << " | sock: " << serverSock << std::endl;       
                    for (int j = 0;j < NUM; j++)
                    {
                        if (_fds[j].fd == DEFAULT)
                        {
                            _fds[j].fd = serverSock; 
                            _fds[j].events = POLLIN;
                            _fds[j].revents = 0;
                            break;
                        }
                    }
                }
            }
            else
            {
                //普通IO事件
                if(_fds[i].revents&POLLIN)
                {
                    // 普通IO读事件就绪
                    if(recv) 
                    {
                        int n=recv(_fds[i].fd)<0;
                        if(n<0|n==0)
                        {
                            close(_fds[i].fd);
                            _fds[i].fd=DEFAULT;
                            _fds[i].events=0;
                            _fds[i].revents = 0;
                        }
                    }

                }
            }
        }
    }
private:
    pollfd *_fds;
};
```



### 2.2.3 优缺点

#### （1）**优点**

> 1. **无最大文件描述符限制**：与`select`不同，`poll`不受文件描述符数量的限制（`select`通常受制于FD_SETSIZE的大小）。
> 2. **不会修改数据结构**：`poll`使用一个`pollfd`结构数组来管理文件描述符，在返回时不会修改`pollfd`数组，这与`select`修改其fd_set参数不同。
>

#### （2）**缺点**

> 对于大量的文件描述符，`poll`可能不是最佳选择，因为它需要遍历整个文件描述符数组来查找哪个描述符准备好了。当文件描述符的数量增加时，这种方法的效率降低。

## 2.3 epoll

`epoll`是Linux特有的I/O多路复用机制，为了解决`select`和`poll`在处理大量文件描述符时的性能瓶颈而设计。`epoll`在某些场景中提供了更高的性能和更好的扩展性，特别是在需要持续监控大量文件描述符的应用中。

### 2.3.1 接口

#### （1）epoll_create（）

```c++
int epoll_create(int size);
```

> ##### 参数:
>
> - `size`: 此参数早期用于告诉内核监听的文件描述符数目。但是从Linux 2.6.8开始，`epoll_create()` 内部并不直接使用此参数，只要这个参数大于0，函数就能正常运行。尽管如此，为了保证兼容性，此参数通常还是设置为预期的监听文件描述符的最大值。
>
> ##### 返回值:
>
> - 成功：==返回一个非负的文件描述符==（代表创建的`epoll`实例）。
> - 失败：返回-1，并设置相应的errno。

#### （2）epoll_ctl（）

```c++
int epoll_ctl(int epfd, int op, int fd, struct epoll_event *event);
```

> ##### 参数
>
> - `epfd`: 通过 `epoll_create()` 或 `epoll_create1()` 创建的 epoll 实例的文件描述符。
> - `op`: ==指定操作类型==。
>   - `EPOLL_CTL_ADD`: 向 epoll 实例中添加一个新的文件描述符。
>   - `EPOLL_CTL_MOD`: 修改已经在 epoll 实例中的文件描述符的监听事件。
>   - `EPOLL_CTL_DEL`: 从 epoll 实例中删除一个文件描述符。
> - `fd`: 要操作的文件描述符。
> - `event`: 指向 `epoll_event` ==结构体的指针==，它指定了要监听的事件以及与文件描述符关联的数据。
>   - `EPOLLIN`: 对应文件描述符可读 (包括对端 SOCKET 正常关闭)。
>   - `EPOLLOUT`: 对应文件描述符可写。
>   - `EPOLLPRI`: 对应文件描述符有紧急的数据可读(这通常指的是 TCP Socket 的 OOB 数据)。
>   - `EPOLLERR`: 对应文件描述符发生错误。
>   - `EPOLLHUP`: 对应文件描述符被挂断。
>   - `EPOLLET`: 将 `EPOLL` 设置为边缘触发(Edge Triggered)模式，这是与默认的水平触发(Level Triggered)模式相对的。
>   - `EPOLLONESHOT`: 为文件描述符设置一次性事件，当该事件被响应后，文件描述符会从 `epoll` 的内核事件表中删除。
>
> ```c++
> struct epoll_event {
>     uint32_t     events;      /* Epoll events */
>     epoll_data_t data;        /* User data variable */
> };
> 
> typedef union epoll_data {
>     void    *ptr;
>     int      fd;
>     uint32_t u32;
>     uint64_t u64;
> } epoll_data_t;
> ```
>
> ##### 返回值
>
> - 成功: 返回 0。
> - 失败: 返回 -1 并设置相应的 `errno`。

#### （3）epoll_wait（）

```c++
int epoll_wait(int epfd, struct epoll_event *events, int maxevents, int timeout);
```

> ##### **参数**
>
> - `epfd`：由 `epoll_create()` 或 `epoll_create1()` 创建的 epoll 文件描述符。
> - `events`：一个预先分配的 `epoll_event` 结构体数组，==用于返回发生了事件的文件描述符相关的事件信息==。当 `epoll_wait()` 返回后，可以遍历这个数组以处理发生的事件。
> - `maxevents`：`events` 数组的大小，即==最大可以返回的事件数==。
> - `timeout`：等待事件的最大时间（以毫秒为单位）。如果设置为 -1，`epoll_wait()` 会一直等待，直到某个事件发生；如果设置为 0，`epoll_wait()` 会立即返回，即使没有事件发生。
>
> ##### **返回值**
>
> - 成功：返回准备好的文件描述符数量（可能为零）。
> - 失败：返回 -1，并设置 errno 为相应的错误。

### 2.3.2 原理

> `epoll`==使用一个事件表来跟踪哪些文件描述符上的哪些事件是活跃的==。与`select`和`poll`不同，`epoll`不需要在每次调用时检查所有被监控的文件描述符；只有活跃的文件描述符才会被报告，这使得它在文件描述符数量增加时仍能保持高性能。

#### （1）回调机制

#### （2）红黑树

> - `epoll` 使用红黑树来管理添加到其内部的文件描述符（通过 `epoll_ctl` 添加）。
> - 红黑树是一个自平衡的二叉搜索树，它的插入、删除和查找操作的时间复杂度都是 O(log n)。这使得当文件描述符的数量很大时，epoll 还能保持较高的效率。
> - 当新的文件描述符被添加到 epoll 实例时，它会被插入到红黑树中；当文件描述符从 epoll 实例中被删除时，它会从红黑树中移除。

#### （3）就绪队列

> - 当某些文件描述符上发生了我们关注的事件，它们会被加入到一个就绪链表中。
> - 当用户程序调用 `epoll_wait` 时，内核就检查这个就绪链表。如果链表非空，`epoll_wait` 会返回这些已准备好的文件描述符。
> - 这意味着与 select 或 poll 不同，`epoll` 不需要遍历所有文件描述符来查找已准备好的描述符，它只需要检查就绪链表，这使得它在处理大量文件描述符时更为高效。

### 2.3.3 基于poll实现TCP服务器

#### （1）epoll.hpp

这段代码定义了一个使用 `epoll` 进行IO多路复用的 `Epoll` 类。`epoll` 是Linux特有的，相较于 `select` 和 `poll`，它提供了更好的性能，特别是在处理大量并发连接时。`epoll` 通过使用一个更有效的数据结构（通常是红黑树）来跟踪每个文件描述符的状态，以及一个就绪列表来存储那些有事件发生的文件描述符，从而减少了需要检查的文件描述符的数量，提高了效率。

类的主要功能和流程

1. **构造函数**：在构造函数中，通过 `epoll_create` 创建了一个 `epoll` 实例，并将监听socket添加到 `epoll` 的监视列表中，设置其关注 `EPOLLIN` 事件（即有数据可读或新的连接请求）。
2. **`multiplexing` 方法**：这个方法调用 `epoll_wait` 来等待事件的发生，它返回就绪事件的数量，并将就绪的事件填充到 `_events` 数组中。该方法接受一个超时时间，这里设置为1000毫秒。
3. **`handlerEvent` 方法**：处理就绪的事件。如果是监听socket就绪，表示有新的连接请求，它会接受这个连接并将新的socket添加到 `epoll` 监视列表中。如果是其他socket就绪，表示有数据可读，它会调用 `recv` 函数处理读事件。如果 `recv` 返回小于或等于0，表示对端关闭连接或出错，此时会关闭socket并从 `epoll` 监视列表中移除。

```c++
#pragma once
#include <iostream>
#include <string>
#include <vector>
#include <functional>
#include <sys/epoll.h>
#include "sock.hpp"
#include <unistd.h>
#define DEFAULT -1
#define NUM 1024
using fun_t=std::function<int(int)>;
class Epoll
{
public:
    //设置预期的监听文件描述符的最大值
    static const int fdNum=128;
    //就绪队列长度
    static const int readyQueue=128;
    //创建epoll模型，并将listensock填充入红黑树
    Epoll(int listensock):_events(new epoll_event[readyQueue]),_listensock(listensock)
    {
        _epollfd=epoll_create(fdNum);
        epoll_event ev;
        ev.data.fd=listensock;
        //设置关注读事件
        ev.events=EPOLLIN;
        epoll_ctl(_epollfd,EPOLL_CTL_ADD,listensock,&ev);
    }
    //多路转接等待，并返回n个就绪事件数量
    int multiplexing()
    {
        int timeout=1000;
        return epoll_wait(_epollfd,_events,readyQueue,timeout);
    }
    //处理事件
    void handlerEvent(fun_t send,fun_t recv,fun_t except,int n)
    {
        for(int i=0;i<n;i++)
        {
            int sock=_events[i].data.fd;
            uint32_t revent = _events[i].events;
            if(sock==_listensock)
            {
                // 连接事件
                if (revent&EPOLLIN)
                {
                    // 连接事件就绪
                    uint16_t port;
                    std::string ip;
                    int serverSock = sock::acceptSock(sock, &ip, &port);
                    if (serverSock == -1)
                        break;
                    std::cout << "获取新连接成功: " << ip << ":" << port << " | sock: " << serverSock << std::endl;
                    //将serverSock填充进红黑树
                    epoll_event ev;
                    ev.data.fd = serverSock;
                    ev.events = EPOLLIN;
                    epoll_ctl(_epollfd, EPOLL_CTL_ADD, serverSock, &ev);
                }
            }
            else
            {
                //普通IO事件
                if(revent&EPOLLIN)
                {
                    // 普通IO读事件就绪
                    if(recv) 
                    {
                        int n=recv(sock)<0;
                        if(n<0|n==0)
                        {
                            close(sock);
                            //将sock在红黑树中删除
                            epoll_ctl(_epollfd, EPOLL_CTL_DEL, sock, nullptr);
                        }
                    }

                }
            }
        }
    }
private:
    int _epollfd;
    int _listensock;
    epoll_event* _events;
};
```



### 2.3.4 epoll工作方式

#### （1）水平触发（LT）

> 默认的触发方式是水平触发。当你在描述符上设置了某个事件（例如`EPOLLIN`，表示有数据可读），并且这个事件的条件满足时，`epoll_wait`会返回这个事件。即使你没有处理这个事件（例如，你没有读取数据），只要条件仍然满足，`epoll_wait`在下次调用时仍然会返回这个事件。
>
> 例如，如果一个socket有未读的数据，并且你设置了`EPOLLIN`事件，但是你没有读取数据，那么每次调用`epoll_wait`都会返回这个socket的`EPOLLIN`事件。

#### （2）边缘触发（ET）

> 边缘触发是非默认方式，需要明确指定（例如，使用`EPOLLET`标志）。在边缘触发模式下，只有在事件的状态从"不满足"变为"满足"时，`epoll_wait`才会返回这个事件。因此，一旦你收到了一个事件通知，并且处理了它，你不会再收到这个事件的通知，除非事件的状态再次改变。
>
> 使用上面的例子，如果一个socket上有未读的数据，并且你设置了`EPOLLIN`和`EPOLLET`事件，但是你没有读取数据，那么下次调用`epoll_wait`不会返回这个socket的`EPOLLIN`事件，除非再次有新数据到达。

#### （3）比较

> - **水平触发 (`LT`)**: 更容易理解和使用，但可能导致效率问题，因为你可能多次收到相同的通知。
> - **边缘触发 (`ET`)**: 更难正确使用，但在某些场景中可能更高效。使用时要特别小心，因为如果你不完全处理事件（例如，不完全读取所有可用的数据），你可能会错过一些通知。

## 2.4 Reactor

> Reactor设计模式是一种事件处理的模式，用于处理服务请求的分发。它通过同步IO（Synchronous I/O）将请求转发给相应的处理程序。在网络编程中，Reactor模式特别适合于高并发、多客户端的连接请求处理，常见于服务器的设计中。这种模式能够高效地管理和分发客户端请求，是实现高性能网络服务的关键技术之一。

### 2.4.1 优缺点

> 优点：
>
> - 可扩展性高：它能有效地处理大量的并发连接。
> - 资源使用低：由于没有为每个连接使用线程，所以资源使用很少。
>
> 缺点：
>
> - 编程复杂度高：与线程模型相比，使用 Reactor 模式需要更多的编程努力。
> - 对于计算密集型任务不是很适合，因为所有的工作都在一个线程中完成。

### 2.4.2 reactor设计模式的TCP服务器

#### （1）util.hpp

```c++
#pragma once
#include <fcntl.h>
class util
{
public:
    static bool setNonBlock(int fd)
    {
        int flags=fcntl(fd,F_GETFL);
        if(flags==-1) return false;
        if(fcntl(fd,F_SETFL,flags | O_NONBLOCK)==-1)return false;
        return true;
    }
};
```



#### （2）socl.hpp

```c++
#pragma once
#include <sys/types.h>          
#include <sys/socket.h>
#include <string>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <cstring>
#include <unistd.h>
#include "log.hpp"


class sock
{
public:
    int _listenSock;
    uint16_t _port;
public:
    sock(uint16_t port):_listenSock(-1),_port(port)
    {}
    ~sock()
    {
        close(_listenSock);
    }
public:
    void init()
    {
        _listenSock=creatSock();
        bindSock(_listenSock,_port);
        listenSock(_listenSock);
    }
    int acceptSock(int sockfd,std::string *ip,uint16_t *port)
    {
        struct sockaddr_in peer;
        socklen_t len=sizeof peer;
        int serverSock=accept(sockfd,(sockaddr *)&peer,&len);
        if(serverSock==-1)
        {
            logMessage(Logger::FATAL,"acceptSock |%d:%s",errno,strerror(errno));
            exit(errno);
        }
        if(ip) *ip=inet_ntoa(peer.sin_addr);//函数的调用者可能决定不传递其中的某个指针,表示他们对这个特定的信息不感兴趣。
        if(port) *port=ntohs(peer.sin_port);//判断确保传入的指针是有效的，从而防止可能的空指针解引用错误。
        return serverSock;
    }
private:
    int creatSock()
    {
        int sockfd=socket(AF_INET,SOCK_STREAM,0);
        if(sockfd<0)
        {
            logMessage(Logger::FATAL,"creatSock |%d:%s",errno,strerror(errno));
            exit(errno);
        }
        int option=1;
        setsockopt(sockfd,SOL_SOCKET,SO_REUSEPORT|SO_REUSEPORT,&option,sizeof(option));//允许套接字和端口重用
        return sockfd;
    }
    void bindSock(int sockfd,uint16_t port)
    {
        struct sockaddr_in local;
        memset(&local,0,sizeof local);
        local.sin_family=AF_INET;
        local.sin_port=htons(port);
        local.sin_addr.s_addr=INADDR_ANY;
        if(bind(sockfd,(const sockaddr*)&local,sizeof local)<0)
        {
            logMessage(Logger::FATAL,"bindSock |%d:%s",errno,strerror(errno));
            exit(errno);
        }
        return;
    }
    void listenSock(int sockfd)
    {
        int backlog=20;
        if(listen(sockfd,backlog)==-1)
        {
            logMessage(Logger::FATAL,"listenSock |%d:%s",errno,strerror(errno));
            exit(errno);
        }
        return;
    }
};
```



#### （3）epoll.hpp

```c++
#pragma once
#include <string>
#include <functional>
#include <sys/epoll.h>
#include <cstring>
#include "log.hpp"
class epoll
{
public:
    int _epollfd;
    epoll_event* _events;
    static const int readyQueue=128;
public:
    epoll(){}
    ~epoll()
    {
        close(_epollfd);
        delete[] _events;
    }
public:
    void init()
    {
        _epollfd=createEpoll();
        _events = new epoll_event[readyQueue];
    }
    int multiplexing()
    {
        int timeout=1000;
        int n = epoll_wait(_epollfd, _events, readyQueue, timeout);
        if (n == -1)
        {
            logMessage(Logger::FATAL, "multiplexing | %d : %s", errno, strerror(errno));
            exit(errno);
        }
        logMessage(Logger::INFO, "[%ld]就绪事件数量: %d ", (unsigned long)time(nullptr),n);
        return n;
    }
public:
    void addEvent(int sock, uint32_t event)
    {
        struct epoll_event ev;
        ev.events = event;
        ev.data.fd = sock;
        int n = epoll_ctl(_epollfd, EPOLL_CTL_ADD, sock, &ev);
        if(n==-1)
        {
            logMessage(Logger::FATAL, "addEvent : %d : %s", errno, strerror(errno));
            exit(errno);
        }
    }
    void modEvent(int sock, uint32_t event)
    {
        struct epoll_event ev;
        ev.events = event;
        ev.data.fd = sock;
        int n = epoll_ctl(_epollfd, EPOLL_CTL_MOD, sock, &ev);
        if(n==-1)
        {
            logMessage(Logger::FATAL, "delEvent : %d : %s", errno, strerror(errno));
            exit(errno);
        }
    }
    void delEvent(int sock)
    {
        int n = epoll_ctl(_epollfd, EPOLL_CTL_DEL, sock, nullptr);
        if(n==-1)
        {
            logMessage(Logger::FATAL, "delEvent : %d : %s", errno, strerror(errno));
            exit(errno);
        }
    }
private:
    int createEpoll()
    {
        const int fdNum=128;
        int epfd = epoll_create(fdNum);
        if (epfd < 0)
        {
            logMessage(Logger::FATAL, "createEpoll : %d : %s", errno, strerror(errno));
            exit(errno);
        }
        return epfd;
    }
};

```



#### （4）connection.hpp

```c++
#pragma once
#include <string>
#include <functional>
class connection;
using func_t =std::function<void(connection*)>;

class connection
{
public:
    func_t _receive;
    func_t _send;
    func_t _except;
    int _sock;
    std::string _inbuffer;
    std::string _outbuffer;
public:
    connection(int sock):_sock(sock)
    {}
    ~connection(){}
public:
    void setHnader(func_t receive,func_t send,func_t except)
    {
        if(receive) _receive=receive;
        if(send) _send=send;
        if(except) _except=except;
    }
};
```



#### （5）server.hpp

```c++
#pragma once
#include <string>
#include <unordered_map>
#include <vector>
#include <functional>
#include "sock.hpp"
#include "log.hpp"
#include "epoll.hpp"
#include "connection.hpp"
#include "util.hpp"
#include "protocol.hpp"

using func_t =std::function<void(connection*)>;
using service_t =std::function<void(connection*,std::string&)>;


class server
{
private:
    sock* _sock;
    epoll* _epoll;
    std::unordered_map<int,connection*> _connections;
    service_t _service;
public:
    server(uint16_t port,service_t service):_service(service)
    {
        _sock=new sock(port);
        _epoll=new epoll;
    }
    ~server()
    {
        delete _sock;
        delete _epoll;
    }
public:
    void init()
    {
        _sock->init();
        _epoll->init();
        addConnection(_sock->_listenSock, [this](connection* con){ this->receiveConnect(con); },nullptr,nullptr,EPOLLIN | EPOLLET);
    }
    void run()
    {
        while(true)
        {
            int n=_epoll->multiplexing();
            for(int i=0;i<n;i++)
            {
                uint32_t revent=_epoll->_events[i].events;
                int sock=_epoll->_events[i].data.fd;
                if(revent & EPOLLHUP) revent |= (EPOLLIN|EPOLLOUT);
                if(revent & EPOLLERR) revent |= (EPOLLIN|EPOLLOUT);
                if(revent&EPOLLIN)
                {
                    if (isExists(sock) && _connections[sock]->_receive)            
                    {
                        _connections[sock]->_receive(_connections[sock]);
                    }
                }
                if(revent&EPOLLOUT)
                {
                    if (isExists(sock) && _connections[sock]->_send)
                    _connections[sock]->_send(_connections[sock]);
                }
            }
        }
    }
private:
    void receiveConnect(connection* con)
    {
        uint16_t port;
        std::string ip;
        int serverSock = _sock->acceptSock(con->_sock, &ip, &port);
        if (serverSock == -1)
        {
            logMessage(Logger::FATAL, "receiveConnect | %d: %s", errno,strerror(errno));
            exit(errno);
        }
        //std::cout<<"获取"<<ip<<" : "<<port<<"新连接成功sock"<<serverSock<<std::endl;
        logMessage(Logger::INFO, "获取[%s:%d]新连接成功:%d",ip.c_str(),port,serverSock);
        addConnection(serverSock,
            [this](connection* con){ this->receiveData(con); },
            [this](connection* con){ this->sendData(con); },
            [this](connection* con){ this->excepter(con); },
            EPOLLET|EPOLLIN);

    }

    void receiveData(connection* con)
    {
        while (true)
        {
            char buffer[1024];
            ssize_t s = recv(con->_sock, buffer, sizeof(buffer) - 1, 0);
            if (s > 0)
            {
                buffer[s] = 0;
                con->_inbuffer+= buffer;
            }
            else if (s == 0)
            {
                logMessage(Logger::INFO,"client close:%d",con->_sock);
                con->_except(con);
                break;
            }
            else
            {
                if (errno == EINTR)
                    continue;
                //表示当前没有数据可读,即完全读完
                else if (errno == EAGAIN || errno == EWOULDBLOCK)
                    break;
                else
                {
                    logMessage(Logger::DEBUG, "recv receiveData| %d:%s", errno, strerror(errno));
                    con->_except(con);
                    break;
                }
            }
        }
        std::vector<std::string> result;
        packageSplit(con->_inbuffer, &result);
        for (auto &message : result)
        {
            _service(con, message);
        }
    }
    void sendData(connection* con)
    {
        while(true)
        {
            ssize_t n = send(con->_sock, con->_outbuffer.c_str(), con->_outbuffer.size(), 0);
            if(n > 0)
            {
                // 注意不一定全部发完
                // 去除已经成功发送的数据
                con->_outbuffer.erase(0, n);
            }
            else
            {
                if(errno == EINTR) continue;
                else if(errno == EAGAIN || errno == EWOULDBLOCK) 
                    break; //发完了，不一定outbuffer清空
                else
                {
                    logMessage(Logger::DEBUG, "sendData error| %d:%s", errno, strerror(errno));
                    con->_except(con);
                    break;
                }
            }
        }
        if(con->_outbuffer.empty()) enableReadWrite(con->_sock, true, false);
         else enableReadWrite(con->_sock, true, true);
    }



    void excepter(connection * con)
    {
        // 0.
        if(!isExists(con->_sock)) return ;
        
        // 所有的服务器异常，都会被归类到这里
        // 坑：一定要先从epoll中移除，然后再关闭fd
        // 1.
        _epoll->delEvent(con->_sock);
        logMessage(Logger::INFO, "remove epoll event!","");

        close( con->_sock);
        logMessage(Logger::INFO, "close fd: %d",  con->_sock,"");

        delete  _connections[ con->_sock];
        logMessage(Logger::INFO, "delete  conection object done","");

        _connections.erase( con->_sock);
        logMessage(Logger::INFO, "erase  conection from connections","");
    }

    void addConnection(int sock,func_t receive,func_t send,func_t except ,uint32_t event)
    {
        util::setNonBlock(sock);
        _epoll->addEvent(sock,event);
        connection* con=new connection(sock);
        con->setHnader(receive,send,except);
        _connections[sock]=con;
        logMessage(Logger::INFO, "添加新链接到connections成功: %d", sock);
    }

    void enableReadWrite(int sock, bool readable, bool writeable)
    {
        uint32_t event = 0;
        event |= (readable ? EPOLLIN : 0);
        event |= (writeable ? EPOLLOUT : 0);
        _epoll->modEvent(sock,event);
    }
    bool isExists(int sock)
    {
        auto iter = _connections.find(sock);
        if (iter == _connections.end())
            return false;
        else
            return true;
    }
};
```



#### （6）protocol.hpp

```c++
#pragma once
#include <string>
#include <cstring>
#include <iostream>
#include <vector>
#define CRLF "\r\n"
#define CRLF_LEN strlen(CRLF)
#define SPACE " "
#define SPACE_LEN strlen(SPACE)
#define OPS "+-*/%"
#define SEP 'X'
#define SEP_LEN sizeof(SEP)
std::string encode(const std::string &package,uint32_t len)
{
    std::string encodePackage;
    encodePackage=std::to_string(len)+CRLF+package+CRLF;
    return encodePackage;
}


std::string decode(std::string &package,uint32_t *len)
{
    //num\r\n data \r\n.....->data
    size_t pos=package.find(CRLF);
    if(pos==std::string::npos){return "";}
    uint32_t packageLen=atoi(package.substr(0,pos).c_str());
    std::string decodePackage;
    decodePackage=package.substr(pos+CRLF_LEN,packageLen);
    *len=packageLen;
    uint32_t removeLen = package.substr(0,pos).size() + packageLen + 2 * CRLF_LEN;
    package.erase(0,removeLen);
    return decodePackage;
}



class request
{
public:
    request(){}
    ~request(){}
public:
    void serialize(std::string *package)
    {
        //"_x op _y"
        *package=std::to_string(_x)+SPACE+_op+SPACE+std::to_string(_y);
    }
    bool deserialize(std::string &package)
    {
        //"_x op _y"-> x op y
        size_t space1=package.find(SPACE);
        if(space1==std::string::npos){return false;}
        size_t space2=package.rfind(SPACE);
        if(space2==std::string::npos){return false;}
        _x=atoi(package.substr(0,space1).c_str());
        _y=atoi(package.substr(space2+SPACE_LEN).c_str());
        _op=*package.substr(space1+SPACE_LEN,space2-space1+1).c_str();
        return true;
    }

public:
    int _x;
    int _y;
    char _op;
};

class response
{
public:
    response():_exitCode(0), _result(0){}
    ~response(){}
public:
    void serialize(std::string *package)
    {
        //"_exitCode _result"
        *package=std::to_string(_exitCode)+SPACE+std::to_string(_result);
    }
    bool deserialize(const std::string &package)
    {
        size_t pos=package.find(SPACE);
        if(pos==std::string::npos){return false;}
        _exitCode=atoi(package.substr(0,pos).c_str());
        _result=atoi(package.substr(pos+SPACE_LEN).c_str());
        return true;
    }

public:
    int _exitCode;
    int _result;
};


bool makeReuquest(const std::string &str, request *req)
{
    char strtmp[1024];
    snprintf(strtmp, sizeof strtmp, "%s", str.c_str());
    char *left = strtok(strtmp, OPS);
    if (!left) return false;
    char *right = strtok(nullptr, OPS);
    if (!right) return false;
    req->_op = str[strlen(left)];
    req->_x = atoi(left);
    req->_y = atoi(right);
    return true;
}

void packageSplit(std::string &inbuffer, std::vector<std::string> *result)
{
    // while (true)
    // {
    //     uint32_t len = 0;
    //     std::string buffer = decode(inbuffer, &len);
    //     if (len == 0)
    //         break;
    //     result->push_back(buffer);
    //     inbuffer.erase(0, len);
    // }
    while (true)
    {
        std::size_t pos = inbuffer.find(SEP);
        if (pos == std::string::npos)
            break;
        result->push_back(inbuffer.substr(0, pos));
        inbuffer.erase(0, pos + SEP_LEN);
    }
}
```



#### （7）log.hpp

```c++
#pragma once

#include <iostream>
#include <fstream>
#include <string>
#include <ctime>
#include <sstream>

class Logger {
public:
    enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        FATAL
    };

    Logger(const std::string &filename = "") : _filename(filename) {
        if (!_filename.empty()) {
            _logfile.open(_filename, std::ios::out | std::ios::app);
            if (!_logfile) {
                std::cerr << "Failed to open log file: " << _filename << std::endl;
                exit(1);
            }
        }
    }

    ~Logger() {
        if (_logfile.is_open()) {
            _logfile.close();
        }
    }

    void log(Level level, const std::string &message) {
        std::ostringstream oss;
        oss << getCurrentTime() << " [" << levelToString(level) << "] " << message << std::endl;

        if (_logfile.is_open()) {
            _logfile << oss.str();
        }
        
        // For errors, warnings, and fatal logs, print to stderr.
        if (level >= WARNING) {
            std::cerr << oss.str();
        } else {
            std::cout << oss.str();
        }
    }

private:
    std::string _filename;
    std::ofstream _logfile;

    std::string getCurrentTime() {
        std::time_t now = std::time(nullptr);
        char buf[100];
        std::strftime(buf, sizeof(buf), "%Y-%m-%d %H:%M:%S", std::localtime(&now));
        return buf;
    }

    const char* levelToString(Level level) {
        switch (level) {
            case DEBUG: return "DEBUG";
            case INFO: return "INFO";
            case WARNING: return "WARNING";
            case ERROR: return "ERROR";
            case FATAL: return "FATAL";
            default: return "UNKNOWN";
        }
    }
};

#define logMessage(level, fmt, ...) do { \
    char _buf_[1024]; \
    snprintf(_buf_, sizeof(_buf_), fmt, __VA_ARGS__); \
    Logger().log(level, _buf_); \
} while(0)

```



#### （8）main.cc

```c++
#include <string>
#include <iostream>
#include "server.hpp"
#include "connection.hpp"
void usage(std::string process)
{
    std::cout<<process<<"  port"<<std::endl;
}

response calculator(const request &req)
{
    response resp;
    switch (req._op)
    {
    case '+':
        resp._result = req._x + req._y;
        break;
    case '-':
        resp._result = req._x - req._y;
        break;
    case '*':
        resp._result = req._x * req._y;
        break;
    case '/':
        { // _x / _y
            if (req._y == 0) resp._exitCode = -1; // -1. 除0
            else resp._result = req._x / req._y;
        }
    break;
    case '%':
        { // _x / _y
            if (req._y == 0) resp._exitCode = -2; // -2. 模0
            else resp._result = req._x % req._y;
        }
    break;
    default:
        resp._exitCode = -3; // -3: 非法操作符
        break;
    }

    return resp;
} 

void service(connection*con,std::string &message)
{
    request req;
    req.deserialize(message);
    response resp=calculator(req);


    std::cout << req._x << " " << req._op << " " << req._y << std::endl;
    std::cout << resp._exitCode << " " << resp._result << std::endl;


    std::string sendstr;
    resp.serialize(&sendstr);

    // 处理完毕的结果，发送回给client
    con->_outbuffer += sendstr;
    con->_send(con);
    // if(con->_outbuffer.empty()) con->_R->EnableReadWrite(con->_sock, true, false);
    // else con->_R->EnableReadWrite(con->_sock, true, true);

    std::cout << "这里就是上次的业务逻辑啦 --- end" << std::endl;
}
int main(int argc, char *argv[])
{
    if(argc!=2)
    {
        usage(argv[0]);
        exit(-1);
    }
    uint16_t port=atoi(argv[1]);
    server ser(port,service);
    ser.init();
    ser.run();
    return 0;
}
```

