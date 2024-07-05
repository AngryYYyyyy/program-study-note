# 容器运行时 Docker

# 一、容器运行时（Container Runtime） 是什么

容器的运行时（runtime）就是运行和管理容器进程、镜像的工具。

# 二、 容器运行时分类

Docker属于容器技术早期的发展项目，也是目前最广泛的容器引擎技术。当然，随着容器生态圈的日益繁荣，业界慢慢也出现了其他各种运行时工具，如containerd、rkt、Kata Container、CRI-O等。这些工具提供的功能不尽相同，有些只有容器运行的功能，有些除运行容器外还提供了容器镜像的管理功能。根据容器运行时提供功能，可以将容器运行时分为**低层运行时**和**高层运行时**。



## 2.1 低层运行时

低层运行时主要负责与宿主机操作系统打交道，根据指定的容器镜像在宿主机上运行容器的进程，并对容器的整个生命周期进行管理。而这个低层运行时，正是负责执行我们前面讲解过的设置容器 Namespace、Cgroups等基础操作的组件。常见的低层运行时种类有：

-  runc：传统的运行时，基于Linux Namespace和Cgroups技术实现，代表实现Docker
-  runv：基于虚拟机管理程序的运行时，通过虚拟化 guest kernel，将容器和主机隔离开来，使得其边界更加清晰，代表实现是Kata Container和Firecracker
- runsc：runc + safety ，通过拦截应用程序的所有系统调用，提供安全隔离的轻量级容器运行时沙箱，代表实现是谷歌的gVisor



## 2.2 高层运行时

高层运行时主要负责镜像的管理、转化等工作，为容器的运行做前提准备。主流的高层运行时主要containerd和CRI-O。

高层运行时与低层运行时各司其职，容器运行时一般先由高层运行时将容器镜像下载下来，并解压转换为容器运行需要的操作系统文件，再由低层运行时启动和管理容器。



## 2.3 低层运行时与高层运行时之间的关系



![暂无图片](容器运行时 Docker.assets/modb_20221109_27ad88b4-6003-11ed-b45b-fa163eb4f6be.png)



容器运行时(Container Runtime)更侧重于运行容器，为容器设置命名空间和控制组（cgroup），也被称为底层容器运行时。高层的容器运行时或容器引擎专注于格式、解包、管理和镜像共享。它们还为开发者提供 API。



# 三、Docker组成

我们发现在安装docker时，不仅会安装docker engine与docker cli工具，而且还会安装containerd，这是为什么呢？

Docker最初是一个单体引擎，主要负责容器镜像的制作、上传、拉取及容器的运行及管理。随着容器技术的繁荣发展，为了促进容器技术相关的规范生成和Docker自身项目的发展，Docker将单体引擎拆分为三部分，分别为runC、containerd和dockerd

其中：

- runC主要负责容器的运行和生命周期的管理（即前述的低层运行时）
- containerd主要负责容器镜像的下载和解压等镜像管理功能（即前述的高层运行时）
- dockerd主要负责提供镜像制作、上传等功能同时提供容器存储和网络的映射功能，同时也是Docker服务器端的守护进程，用来响应Docker客户端（命令行CLI工具）发来的各种容器、镜像管理的任务。

Docker公司将runC捐献给了OCI，将containerd捐献给了CNCF，剩下的dockerd作为Docker运行时由Docker公司自己维护。





![img](容器运行时 Docker.assets/modb_20221109_27d72a48-6003-11ed-b45b-fa163eb4f6be.png)



> *开放容器计划(Open Container Initiative)*（OCI）是一个 Linux 基金会的项目。其目的是设计某些开放标准或围绕如何与容器运行时和容器镜像格式工作的结构。它是由 Docker、rkt、CoreOS 和其他行业领导者于 2015 年 6 月建立的。
>
> 两个规范：
>
> 1、**镜像规范**
>
> 该规范的目标是创建可互操作的工具，用于构建、传输和准备运行的容器镜像。
>
> 该规范的高层组件包括：
>
> - 镜像清单— 一个描述构成容器镜像的元素的文件
> - 镜像索引— 镜像清单的注释索引
> - 镜像布局— 一个镜像内容的文件系统布局
> - 文件系统布局 — 一个描述容器文件系统的变更集
> - 镜像配置 — 确定镜像层顺序和配置的文件，以便转换成 运行时捆包
> - 转换— 解释应该如何进行转换的文件
> - 描述符 — 一个描述被引用内容的类型、元数据和内容地址的参考资料
>
> **2、运行时规范**
>
> 该规范用于定义容器的配置、执行环境和生命周期。`config.json` 文件为所有支持的平台提供了容器配置，并详细定义了用于创建容器的字段。在详细定义执行环境时也描述了为容器的生命周期定义的通用操作，以确保在容器内运行的应用在不同的运行时环境之间有一个一致的环境。
>
> Linux 容器规范使用了各种内核特性，包括 *命名空间(namespace)*、 *控制组(cgroup)*、 *权能(capability)*、LSM 和文件系统 *隔离(jail)*等来实现该规范。



>云原生计算基金会（CNCF）成立于 2015 年，旨在传播和推广云原生基金会的开放标准和项目。CNCF 在市场上享有全球认可，并在定义和引领云计算的未来方面发挥着至关重要的作用。



# 四、容器运行机制

当我们使用docker run运行一个命令在容器中时，在容器运行时层面会发生什么？

1、如果本地没有镜像，则从镜像 登记仓库(registry)拉取镜像

2、镜像被提取到一个写时复制（COW）的文件系统上，所有的容器层相互堆叠以形成一个合并的文件系统

3、为容器准备一个挂载点

4、从容器镜像中设置元数据，包括诸如覆盖 CMD、来自用户输入的 ENTRYPOINT、设置 SECCOMP 规则等设置，以确保容器按预期运行

5、提醒内核为该容器分配某种隔离，如进程、网络和文件系统（ 命名空间(namespace)）

6、提醒内核为该容器分配一些资源限制，如 CPU 或内存限制（ 控制组(cgroup)）

7、传递一个 系统调用(syscall)给内核用于启动容器

8、设置 SELinux/AppArmor

以上，就是容器运行时负责的所有的工作。当我们提及容器运行时，想到的可能是 runc、lxc、containerd、rkt、cri-o 等等。这些都是容器引擎和容器运行时，每一种都是为不同的情况建立的。



# 五、Docker安装

> 操作系统为：CentOS7u9操作系统。
>
> 关于Docker版本
>
> - Docker-ce Docker社区版，主要用于个人开发者测试使用，免费版本
> - Docker-ee Docker企业版，主要用于为企业开发及应用部署使用，收费版本，免费试用一个月，2020年因国际政治原因曾一度限制中国企业使用。



## 5.1 使用YUM源安装

### 5.1.1 Docker安装YUM源准备

>使用阿里云开源软件镜像站。



~~~powershell
# wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo -O /etc/yum.repos.d/docker-ce.repo
~~~



### 5.1.2 Docker安装



~~~powershell
# yum -y install docker-ce
~~~



### 5.1.3 启动Docker服务



~~~powershell
# systemctl enable --now docker
~~~



## 5.2 使用二进制安装



![image-20230713122224560](容器运行时 Docker.assets/image-20230713122224560.png)



![image-20230713122334908](容器运行时 Docker.assets/image-20230713122334908.png)



![image-20230713122420030](容器运行时 Docker.assets/image-20230713122420030.png)



![image-20230713122742606](容器运行时 Docker.assets/image-20230713122742606.png)





![image-20230713123615448](容器运行时 Docker.assets/image-20230713123615448.png)



![image-20230713123712828](容器运行时 Docker.assets/image-20230713123712828.png)





~~~powershell
[root@docker-host-1 ~]# wget https://download.docker.com/linux/static/stable/x86_64/docker-24.0.4.tgz
~~~



~~~powershell
[root@docker-host-1 ~]# ls
docker-24.0.4.tgz

[root@docker-host-1 ~]# tar xf docker-24.0.4.tgz

[root@docker-host-1 ~]# ls
docker

[root@docker-host-1 ~]# ls docker
containerd  containerd-shim-runc-v2  ctr  docker  dockerd  docker-init  docker-proxy  runc
~~~



~~~powershell
[root@docker-host-1 ~]# cp docker/* /usr/bin/
~~~



~~~powershell
[root@docker-host-1 ~]# dockerd &
~~~



~~~powershell
[root@docker-host-1 ~]# docker version
Client:
 Version:           24.0.4
 API version:       1.43
 Go version:        go1.20.5
 Git commit:        3713ee1
 Built:             Fri Jul  7 14:49:50 2023
 OS/Arch:           linux/amd64
 Context:           default

Server: Docker Engine - Community
 Engine:
  Version:          24.0.4
  API version:      1.43 (minimum version 1.12)
  Go version:       go1.20.5
  Git commit:       4ffc614
  Built:            Fri Jul  7 14:51:12 2023
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          v1.7.1
  GitCommit:        1677a17964311325ed1c31e2c0a3589ce6d5c30d
 runc:
  Version:          1.1.7
  GitCommit:        v1.1.7-0-g860f061
 docker-init:
  Version:          0.19.0
  GitCommit:        de40ad0
~~~



~~~powershell
[root@docker-host-1 ~]# docker run hello-world
~~~



~~~powershell
输出内容：
Unable to find image 'hello-world:latest' locally
latest: Pulling from library/hello-world
719385e32844: Pull complete
Digest: sha256:a13ec89cdf897b3e551bd9f89d499db6ff3a7f44c5b9eb8bca40da20eb4ea1fa
Status: Downloaded newer image for hello-world:latest
time="2023-07-13T12:41:36.682815223+08:00" level=info msg="loading plugin \"io.containerd.internal.v1.shutdown\"..." runtime=io.containerd.runc.v2 type=io.containerd.internal.v1
time="2023-07-13T12:41:36.682877747+08:00" level=info msg="loading plugin \"io.containerd.ttrpc.v1.pause\"..." runtime=io.containerd.runc.v2 type=io.containerd.ttrpc.v1
time="2023-07-13T12:41:36.682906923+08:00" level=info msg="loading plugin \"io.containerd.event.v1.publisher\"..." runtime=io.containerd.runc.v2 type=io.containerd.event.v1
time="2023-07-13T12:41:36.682927870+08:00" level=info msg="loading plugin \"io.containerd.ttrpc.v1.task\"..." runtime=io.containerd.runc.v2 type=io.containerd.ttrpc.v1

Hello from Docker!
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps:
 1. The Docker client contacted the Docker daemon.
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64)
 3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.
 4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.

To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
 https://hub.docker.com/

For more examples and ideas, visit:
 https://docs.docker.com/get-started/

INFO[2023-07-13T12:41:36.859153133+08:00] shim disconnected                             id=b58de675fe2e875cc873975dddd9bdb51d5d0e988339ee805cb2289e09fcad2a namespace=moby
WARN[2023-07-13T12:41:36.859186370+08:00] cleaning up after shim disconnected           id=b58de675fe2e875cc873975dddd9bdb51d5d0e988339ee805cb2289e09fcad2a namespace=moby
INFO[2023-07-13T12:41:36.859191432+08:00] cleaning up dead shim                         namespace=moby
INFO[2023-07-13T12:41:36.859315711+08:00] ignoring event                                container=b58de675fe2e875cc873975dddd9bdb51d5d0e988339ee805cb2289e09fcad2a module=libcontainerd namespace=moby topic=/tasks/delete type="*events.TaskDelete"
~~~





## 5.3 在windows主机上安装 docker desktop

### 5.3.1 docker desktop安装



![image-20230713124400503](容器运行时 Docker.assets/image-20230713124400503.png)





![image-20230713130704705](容器运行时 Docker.assets/image-20230713130704705.png)



![image-20230713130741772](容器运行时 Docker.assets/image-20230713130741772.png)



> 双击后即可安装，安装完成后需要重启操作系统。



![image-20230713130845356](容器运行时 Docker.assets/image-20230713130845356.png)





![image-20230713130919705](容器运行时 Docker.assets/image-20230713130919705.png)



![image-20230713131123448](容器运行时 Docker.assets/image-20230713131123448.png)



![image-20230713131155536](容器运行时 Docker.assets/image-20230713131155536.png)



![image-20230713131227936](容器运行时 Docker.assets/image-20230713131227936.png)



### 5.3.2 使用docker desktop运行容器



![image-20230713141344768](容器运行时 Docker.assets/image-20230713141344768.png)





![image-20230713141544350](容器运行时 Docker.assets/image-20230713141544350.png)



![image-20230713141627857](容器运行时 Docker.assets/image-20230713141627857.png)



![image-20230713141756293](容器运行时 Docker.assets/image-20230713141756293.png)



![image-20230713141843897](容器运行时 Docker.assets/image-20230713141843897.png)





![image-20230713142223965](容器运行时 Docker.assets/image-20230713142223965.png)





![image-20230713142356288](容器运行时 Docker.assets/image-20230713142356288.png)





![image-20230713142310433](容器运行时 Docker.assets/image-20230713142310433.png)



![image-20230713142442666](容器运行时 Docker.assets/image-20230713142442666.png)



![image-20230713153747460](容器运行时 Docker.assets/image-20230713153747460.png)









# 六、Docker 使用生态介绍

![image-20230713143832748](容器运行时 Docker.assets/image-20230713143832748.png)



### 6.1 Docker Host

用于安装Docker daemon的主机，即为Docker Host，并且该主机中可基于容器镜像运行容器。



### 6.2 Docker daemon

用于管理Docker Host中运行的容器、容器镜像、容器网络等，管理由Containerd.io提供的容器。



### 6.3 Registry

容器镜像仓库，用于存储已生成容器运行模板的仓库，用户使用时，可直接从容器镜像仓库中下载容器镜像，即容器运行模板，就可以运行容器镜像中包含的应用了。例如：DockerHub,也可以使用Harbor实现企业私有的容器镜像仓库。



### 6.4 Docker client

Docker Daemon客户端工具，用于同Docker Daemon进行通信，执行用户指令，可部署在Docker Host上，也可以部署在其它主机，能够连接到Docker Daemon即可操作。



### 6.5 Image

把应用运行环境及计算资源打包方式生成可再用于启动容器的不可变的基础设施的模板文件，主要用于基于其启动一个容器。



### 6.6 Container

由容器镜像生成，用于应用程序运行的环境，包含容器镜像中所有文件及用户后添加的文件，属于基于容器镜像生成的可读写层，这也是应用程序活跃的空间。



### 6.7 Docker Desktop

> 仅限于MAC、Windows、部分Linux操作系统上安装使用。

Docker Desktop 提供了一个简单的界面，使您能够直接从您的机器管理您的容器、应用程序和映像，而无需使用 CLI 来执行核心操作。





# 七、 Docker命令介绍

## 7.1 容器运行时Docker 命令示意图



![image-20230713144101159](容器运行时 Docker.assets/image-20230713144101159.png)



```
export http_proxy=http://192.168.2.16:33210
export ftp_proxy=http://192.168.2.16:33210
```



## 7.2 使用容器运行Nginx应用

### 7.2.1 使用docker run命令运行Nginx应用

#### 7.2.1.1 观察下载容器镜像过程

> 查找本地容器镜像文件

~~~powershell
执行命令过程一：下载容器镜像
# docker run -d nginx:latest
Unable to find image 'nginx:latest' locally
latest: Pulling from library/nginx
a2abf6c4d29d: Downloading  1.966MB/31.36MB 下载中
a9edb18cadd1: Downloading  1.572MB/25.35MB
589b7251471a: Download complete 下载完成
186b1aaa4aa6: Download complete
b4df32aa5a72: Waiting 等待下载
a0bcbecc962e: Waiting
~~~



~~~powershell
执行命令过程二：下载容器镜像
[root@localhost ~]# docker run -d nginx:latest
Unable to find image 'nginx:latest' locally
latest: Pulling from library/nginx
a2abf6c4d29d: Downloading  22.87MB/31.36MB
a9edb18cadd1: Downloading  22.78MB/25.35MB
589b7251471a: Waiting
186b1aaa4aa6: Waiting
b4df32aa5a72: Waiting
~~~



~~~powershell
执行命令过程三：下载容器镜像
[root@localhost ~]# docker run -d nginx:latest
Unable to find image 'nginx:latest' locally
latest: Pulling from library/nginx
a2abf6c4d29d: Pull complete 下载完成
a9edb18cadd1: Pull complete
589b7251471a: Pull complete
186b1aaa4aa6: Pull complete
b4df32aa5a72: Waiting 等待下载
~~~



#### 7.2.1.2 观察容器运行情况

~~~powershell
# docker run -d nginx:latest
9834c8c18a7c7c89ab0ea4119d11bafe9c18313c8006bc02ce57ff54d9a1cc0c
~~~



~~~powershell
命令解释
docker run 启动一个容器
-d 把容器在后台执行
nginx 应用容器镜像的名称，通常表示该镜像为某一个软件
latest 表示上述容器镜像的版本，表示最新版本，用户可自定义其标识，例如v1或v2等
~~~



~~~powershell
# docker ps
CONTAINER ID   IMAGE        COMMAND                  CREATED          STATUS        PORTS     NAMES
9834c8c18a7c   nginx:latest "/docker-entrypoint.…"   24 seconds ago   Up 23 seconds 80/tcp condescending_pare
~~~



~~~powershell
命令解释
docker ps 类似于Linux系统的ps命令，查看正在运行的容器，如果想查看没有运行的容器，需要在此命令后使用--all
~~~



**输出内容解释**

| CONTAINERID  | IMAGE        | COMMAND                | CREATED        | STATUS        | PORTS  | NAMES              |
| ------------ | ------------ | ---------------------- | -------------- | ------------- | ------ | ------------------ |
| 9834c8c18a7c | nginx:latest | "/docker-entrypoint.…" | 24 seconds ago | Up 23 seconds | 80/tcp | condescending_pare |



### 7.2.2 访问容器中运行的Nginx服务

#### 7.2.2.1 确认容器IP地址

> 实际工作中不需要此步操作。

~~~powershell
 # docker inspect 9834
 
 "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "172.17.0.2", 容器IP地址
            "IPPrefixLen": 16,
            "IPv6Gateway": "",
            "MacAddress": "02:42:ac:11:00:02",
            "Networks": {
                "bridge": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": null,
                    "NetworkID": "d3de2fdbc30ee36a55c1431ef3ae4578392e552009f00b2019b4720735fe5a60",
                    "EndpointID": "d91f47c9f756ff22dc599a207164f2e9366bd0c530882ce0f08ae2278fb3d50c",
                    "Gateway": "172.17.0.1",
                    "IPAddress": "172.17.0.2",   容器IP地址
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "MacAddress": "02:42:ac:11:00:02",
                    "DriverOpts": null
                }
            }
        }
    }
]
~~~



~~~powershell
命令解释
docker inspect 为查看容器结构信息命令
9834 为前面生成的容器ID号前4位，使用这个ID号时，由于其较长，使用时能最短识别即可。
~~~



#### 7.2.2.2 容器网络说明



![image-20220121172037253](容器运行时 Docker.assets/image-20220121172037253.png)



~~~powershell
# ip a s
......
docker0网桥，用于为容器提供桥接，转发到主机之外的网络
5: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default
    link/ether 02:42:d5:c3:d4:cc brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:d5ff:fec3:d4cc/64 scope link
       valid_lft forever preferred_lft forever
       
       
与容器中的虚拟网络设备在同一个命名空间中，用于把容器中的网络连接到主机
9: veth393dece@if8: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue master docker0 state UP group default
    link/ether 02:e3:11:58:54:0f brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet6 fe80::e3:11ff:fe58:540f/64 scope link
       valid_lft forever preferred_lft forever
~~~



#### 7.2.2.3 使用curl命令访问

~~~powershell
# curl http://172.17.0.2

返回结果，表示访问成功！
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
html { color-scheme: light dark; }
body { width: 35em; margin: 0 auto;
font-family: Tahoma, Verdana, Arial, sans-serif; }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>
~~~



## 7.3 Docker命令

### 7.3.1 Docker命令获取帮助方法



~~~powershell
# docker -h
Flag shorthand -h has been deprecated, please use --help

Usage:  docker [OPTIONS] COMMAND  用法

A self-sufficient runtime for containers 功能介绍

Options: 选项
      --config string      Location of client config files (default "/root/.docker")
  -c, --context string     Name of the context to use to connect to the daemon (overrides
                           DOCKER_HOST env var and default context set with "docker context use")
  -D, --debug              Enable debug mode
  -H, --host list          Daemon socket(s) to connect to
  -l, --log-level string   Set the logging level ("debug"|"info"|"warn"|"error"|"fatal")
                           (default "info")
      --tls                Use TLS; implied by --tlsverify
      --tlscacert string   Trust certs signed only by this CA (default "/root/.docker/ca.pem")
      --tlscert string     Path to TLS certificate file (default "/root/.docker/cert.pem")
      --tlskey string      Path to TLS key file (default "/root/.docker/key.pem")
      --tlsverify          Use TLS and verify the remote
  -v, --version            Print version information and quit

Management Commands: 管理类命令
  app*        Docker App (Docker Inc., v0.9.1-beta3)
  builder     Manage builds
  buildx*     Docker Buildx (Docker Inc., v0.7.1-docker)
  config      Manage Docker configs
  container   Manage containers
  context     Manage contexts
  image       Manage images
  manifest    Manage Docker image manifests and manifest lists
  network     Manage networks
  node        Manage Swarm nodes
  plugin      Manage plugins
  scan*       Docker Scan (Docker Inc., v0.12.0)
  secret      Manage Docker secrets
  service     Manage services
  stack       Manage Docker stacks
  swarm       Manage Swarm
  system      Manage Docker
  trust       Manage trust on Docker images
  volume      Manage volumes

Commands: 未分组命令
  attach      Attach local standard input, output, and error streams to a running container
  build       Build an image from a Dockerfile
  commit      Create a new image from a container's changes
  cp          Copy files/folders between a container and the local filesystem
  create      Create a new container
  diff        Inspect changes to files or directories on a container's filesystem
  events      Get real time events from the server
  exec        Run a command in a running container
  export      Export a container's filesystem as a tar archive
  history     Show the history of an image
  images      List images
  import      Import the contents from a tarball to create a filesystem image
  info        Display system-wide information
  inspect     Return low-level information on Docker objects
  kill        Kill one or more running containers
  load        Load an image from a tar archive or STDIN
  login       Log in to a Docker registry
  logout      Log out from a Docker registry
  logs        Fetch the logs of a container
  pause       Pause all processes within one or more containers
  port        List port mappings or a specific mapping for the container
  ps          List containers
  pull        Pull an image or a repository from a registry
  push        Push an image or a repository to a registry
  rename      Rename a container
  restart     Restart one or more containers
  rm          Remove one or more containers
  rmi         Remove one or more images
  run         Run a command in a new container
  save        Save one or more images to a tar archive (streamed to STDOUT by default)
  search      Search the Docker Hub for images
  start       Start one or more stopped containers
  stats       Display a live stream of container(s) resource usage statistics
  stop        Stop one or more running containers
  tag         Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE
  top         Display the running processes of a container
  unpause     Unpause all processes within one or more containers
  update      Update configuration of one or more containers
  version     Show the Docker version information
  wait        Block until one or more containers stop, then print their exit codes
~~~



### 7.3.2 Docker官网提供的命令说明

网址链接：https://docs.docker.com/reference/



![image-20220121173519802](容器运行时 Docker.assets/image-20220121173519802.png)



![image-20220121173613294](容器运行时 Docker.assets/image-20220121173613294.png)



![image-20220121173705508](容器运行时 Docker.assets/image-20220121173705508.png)



### 7.3.3 docker命令应用

#### 7.3.3.1 docker run



~~~powershell
# docker run -i -t --name c1 centos:latest bash
[root@948f234e22a1 /]#
~~~



~~~powershell
命令解释
docker run 运行一个命令在容器中，命令是主体，没有命令容器就会消亡
-i 交互式
-t 提供终端
--name c1 把将运行的容器命名为c1
centos:latest 使用centos最新版本容器镜像
bash 在容器中执行的命令
~~~



~~~powershell
查看主机名
[root@948f234e22a1 /]#
~~~



~~~powershell
查看网络信息
[root@948f234e22a1 /]# ip a s
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
12: eth0@if13: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default
    link/ether 02:42:ac:11:00:03 brd ff:ff:ff:ff:ff:ff link-netnsid 0
    inet 172.17.0.3/16 brd 172.17.255.255 scope global eth0
       valid_lft forever preferred_lft forever
~~~



~~~powershell
查看进程
[root@948f234e22a1 /]# ps aux
USER        PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root          1  0.0  0.1  12036  2172 pts/0    Ss   09:58   0:00 bash
root         16  0.0  0.0  44652  1784 pts/0    R+   10:02   0:00 ps aux
~~~



~~~powershell
查看用户
[root@948f234e22a1 /]# cat /etc/passwd
root:x:0:0:root:/root:/bin/bash
bin:x:1:1:bin:/bin:/sbin/nologin
daemon:x:2:2:daemon:/sbin:/sbin/nologin
adm:x:3:4:adm:/var/adm:/sbin/nologin
lp:x:4:7:lp:/var/spool/lpd:/sbin/nologin
sync:x:5:0:sync:/sbin:/bin/sync
shutdown:x:6:0:shutdown:/sbin:/sbin/shutdown
halt:x:7:0:halt:/sbin:/sbin/halt
mail:x:8:12:mail:/var/spool/mail:/sbin/nologin
operator:x:11:0:operator:/root:/sbin/nologin
games:x:12:100:games:/usr/games:/sbin/nologin
ftp:x:14:50:FTP User:/var/ftp:/sbin/nologin
nobody:x:65534:65534:Kernel Overflow User:/:/sbin/nologin
dbus:x:81:81:System message bus:/:/sbin/nologin
systemd-coredump:x:999:997:systemd Core Dumper:/:/sbin/nologin
systemd-resolve:x:193:193:systemd Resolver:/:/sbin/nologin
~~~



~~~powershell
查看目录
[root@948f234e22a1 /]# pwd
/
[root@948f234e22a1 /]# ls
bin  etc   lib    lost+found  mnt  proc  run   srv  tmp  var
dev  home  lib64  media       opt  root  sbin  sys  usr
~~~



~~~powershell
退出命令执行，观察容器运行情况
[root@948f234e22a1 /]# exit
exit
[root@localhost ~]#
~~~



#### 7.3.3.2 docker ps



~~~powershell
# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
~~~



~~~powershell
命令解释
docker ps 查看正在运行的容器，本案例由于没有命令在容器中运行，因此容器被停止了，所以本次查看没有结果。
~~~



~~~powershell
# docker ps --all
CONTAINER ID   IMAGE           COMMAND     CREATED             STATUS                         PORTS     NAMES
948f234e22a1   centos:latest   "bash"    10 minutes ago      Exited (0) 2 minutes ago                    c1
~~~



| CONTAINERID  | IMAGE         | COMMAND | CREATED        | STATUS                   | PORTS | NAMES |
| ------------ | ------------- | ------- | -------------- | ------------------------ | ----- | ----- |
| 948f234e22a1 | centos:latest | "bash"  | 10 minutes ago | Exited (0) 2 minutes ago |       | c1    |



~~~powershell
命令解释
docker ps --all 可以查看正在运行的和停止运行的容器
~~~



#### 7.3.3.3 docker inspect



~~~powershell
# docker run -it --name c2 centos:latest bash
[root@9f2eea16da4c /]# 
~~~



~~~powershell
操作说明
在上述提示符处按住ctrl键，再按p键与q键，可以退出交互式的容器，容器会处于运行状态。
~~~



~~~powershell
# docker ps
CONTAINER ID   IMAGE           COMMAND   CREATED          STATUS          PORTS     NAMES
9f2eea16da4c   centos:latest   "bash"    37 seconds ago   Up 35 seconds             c2
~~~



~~~powershell
命令解释
可以看到容器处于运行状态
~~~



~~~powershell
# docker inspect c2

"Networks": {
                "bridge": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": null,
                    "NetworkID": "d3de2fdbc30ee36a55c1431ef3ae4578392e552009f00b2019b4720735fe5a60",
                    "EndpointID": "d1a2b7609f2f73a6cac67229a4395eef293f695c0ac4fd6c9c9e6913c9c85c1c",
                    "Gateway": "172.17.0.1",
                    "IPAddress": "172.17.0.2",
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "MacAddress": "02:42:ac:11:00:02",
                    "DriverOpts": null
                }
            }
        }
    }
]

~~~



~~~powershell
命令解释
docker inpect 查看容器详细信息
~~~





#### 7.3.3.4 docker  exec 



~~~powershell
# docker exec -it c2 ls /root
anaconda-ks.cfg  anaconda-post.log  original-ks.cfg
~~~



~~~powershell
命令解释
docker exec 在容器外实现与容器交互执行某命令
-it 交互式
c2 正在运行的容器名称
ls /root 在正在运行的容器中运行相关的命令
~~~



~~~powershell
下面命令与上面命令执行效果一致
# docker exec c2 ls /root
anaconda-ks.cfg
anaconda-post.log
original-ks.cfg
~~~



#### 7.3.3.5 docker attach



~~~powershell
查看正在运行的容器
# docker ps
CONTAINER ID   IMAGE           COMMAND   CREATED          STATUS          PORTS     NAMES
9f2eea16da4c   centos:latest   "bash"    13 minutes ago   Up 13 minutes             c2
~~~



~~~powershell
[root@localhost ~]# docker attach c2
[root@9f2eea16da4c /]#
~~~



~~~powershell
命令解释
docker attach 类似于ssh命令，可以进入到容器中
c2 正在运行的容器名称
~~~



~~~powershell
说明
docker attach 退出容器时，如不需要容器再运行，可直接使用exit退出；如需要容器继续运行，可使用ctrl+p+q
~~~



#### 7.3.3.6 docker stop



~~~powershell
# docker ps
CONTAINER ID   IMAGE           COMMAND   CREATED          STATUS          PORTS     NAMES
9f2eea16da4c   centos:latest   "bash"    22 minutes ago   Up 22 minutes             c2
~~~



~~~powershell
# docker stop 9f2eea
9f2eea
~~~



~~~powershell
# docker ps --all
CONTAINER ID   IMAGE           COMMAND                  CREATED          STATUS                       PORTS     NAMES
9f2eea16da4c   centos:latest   "bash"                   22 minutes ago   Exited (137) 4 seconds ago             c2
~~~



~~~powershell
批量停止容器方法
# docker stop $(docker ps -a -q)
~~~





#### 7.3.3.7 docker start



~~~powershell
# docker ps --all
CONTAINER ID   IMAGE           COMMAND     CREATED          STATUS                       PORTS     NAMES
9f2eea16da4c   centos:latest   "bash"      22 minutes ago   Exited (137) 4 seconds ago              c2
~~~



~~~powershell
# docker start 9f2eea
9f2eea
~~~



~~~powershell
# docker ps
CONTAINER ID   IMAGE           COMMAND   CREATED          STATUS          PORTS     NAMES
9f2eea16da4c   centos:latest   "bash"    24 minutes ago   Up 16 seconds             c2
~~~





#### 7.3.3.8  docker top

>在Docker Host查看容器中运行的进程信息

~~~powershell
# docker top c2
UID    PID     PPID      C      STIME        TTY              TIME                CMD
root  69040   69020      0      18:37       pts/0           00:00:00              bash
~~~



| UID  | PID   | PPID  | C    | STIME | TTY   | TIME     | CMD  |
| ---- | ----- | ----- | ---- | ----- | ----- | -------- | ---- |
| root | 69040 | 69020 | 0    | 18:37 | pts/0 | 00:00:00 | bash |



~~~powershell
命令解释
docker top 查看container内进程信息，指在docker host上查看，与docker exec -it c2 ps -ef不同。
~~~



~~~powershell
输出说明
UID 容器中运行的命令用户ID
PID 容器中运行的命令PID
PPID 容器中运行的命令父PID，由于PPID是一个容器，此可指为容器在Docker Host中进程ID
C     占用CPU百分比
STIME 启动时间
TTY   运行所在的终端
TIME  运行时间
CMD   执行的命令
~~~



#### 7.3.3.9 docker rm

> 如果容器已停止，使用此命令可以直接删除；如果容器处于运行状态，则需要提前关闭容器后，再删除容器。下面演示容器正在运行关闭后删除的方法。



##### 7.3.3.9.1 指定删除容器



~~~powershell
# docker ps
CONTAINER ID   IMAGE           COMMAND   CREATED      STATUS         PORTS     NAMES
9f2eea16da4c   centos:latest   "bash"    2 days ago   Up 3 seconds             c2
~~~



~~~powershell
# docker stop c2
或
# docker stop 9f2eea16da4c
~~~



~~~powershell
# docker rm c2
或
# docker rm 9f2eea16da4c
~~~



##### 7.3.3.9.2 批量删除容器



~~~powershell
# docker ps --all
CONTAINER ID   IMAGE           COMMAND          CREATED      STATUS                  PORTS    NAMES
948f234e22a1   centos:latest   "bash"           2 days ago   Exited (0) 2 days ago            c1
01cb3e01273c   centos:latest   "bash"           2 days ago   Exited (0) 2 days ago            systemimage1
46d950fdfb33   nginx:latest    "/docker-ent..." 2 days ago   Exited (0) 2 days ago            upbeat_goldberg
~~~



~~~powershell
# docker ps --all | awk '{if (NR>=2){print $1}}' | xargs docker rm
~~~



~~~powershell
# docker rm $(docker ps -a -q)
~~~



#### 7.3.3.10 查看容器占用主机资源情况



~~~powershell
[root@docker-host-1 ~]# docker ps
CONTAINER ID   IMAGE          COMMAND                   CREATED         STATUS         PORTS     NAMES
5d0ce02a12c9   nginx:latest   "/docker-entrypoint.…"   2 minutes ago   Up 2 minutes   80/tcp    loving_blackwell


[root@docker-host-1 ~]# docker stats --no-stream 5d0ce02a12c9
CONTAINER ID   NAME               CPU %     MEM USAGE / LIMIT     MEM %     NET I/O       BLOCK I/O     PIDS
5d0ce02a12c9   loving_blackwell   0.00%     6.695MiB / 3.818GiB   0.17%     2.78kB / 0B   0B / 53.2kB   5
~~~



#### 7.3.3.11 docker prune



~~~powershell
删除未使用的容器镜像
# docker image prune
~~~



~~~powershell
删除所有未使用的容器镜像
# docker image prune -a
~~~



~~~powershell
删除所有停止运行的容器
# docker container prune
~~~



~~~powershell
删除所有未被挂载的卷
# docker volume prune
~~~



~~~powershell
删除所有网络
# docker network prune
~~~



~~~powershell
删除docker所有资源
# docker system prune
~~~





# 八、 Docker容器镜像



## 8.1 Docker容器镜像操作

### 8.1.1 查看本地容器镜像

#### 8.1.1.1 使用docker images命令查看

~~~powershell
# docker images
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
bash         latest    5557e073f11c   2 weeks ago    13MB
nginx        latest    605c77e624dd   3 weeks ago    141MB
centos       latest    5d0da3dc9764   4 months ago   231MB
~~~



#### 8.1.1.2 使用docker image命令查看

~~~powershell
# docker image list
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
bash         latest    5557e073f11c   2 weeks ago    13MB
nginx        latest    605c77e624dd   3 weeks ago    141MB
centos       latest    5d0da3dc9764   4 months ago   231MB
~~~



#### 8.1.1.3  查看docker容器镜像本地存储位置

> 考虑到docker容器镜像会占用本地存储空间，建议搭建其它存储系统挂载到本地以便解决占用大量本地存储的问题。



~~~powershell
# ls /var/lib/docker
buildkit  containers  image  network  overlay2  plugins  runtimes  swarm  tmp  trust  volumes
~~~





### 8.1.2 搜索Docker Hub容器镜像

#### 8.1.2.1  命令行搜索



~~~powershell
# docker search centos
~~~



~~~powershell
输出
NAME                              DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
centos                            The official build of CentOS.                   6987      [OK]
ansible/centos7-ansible           Ansible on Centos7                              135                  [OK]
consol/centos-xfce-vnc            Centos container with "headless" VNC session…   135                  [OK]
jdeathe/centos-ssh                OpenSSH / Supervisor / EPEL/IUS/SCL Repos - …   121                  [OK]
~~~





#### 8.1.2.2  Docker Hub Web界面搜索



![image-20220124162022990](容器运行时 Docker.assets/image-20220124162022990.png)





![image-20220124162116338](容器运行时 Docker.assets/image-20220124162116338.png)



![image-20220124162200273](容器运行时 Docker.assets/image-20220124162200273.png)



![image-20220124162312918](容器运行时 Docker.assets/image-20220124162312918.png)





### 8.1.3 Docker 容器镜像下载

~~~powershell
# docker pull centos
~~~



### 8.1.4 Docker容器镜像删除方法



~~~powershell
# docker images
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
bash         latest    5557e073f11c   2 weeks ago    13MB
nginx        latest    605c77e624dd   3 weeks ago    141MB
centos       latest    5d0da3dc9764   4 months ago   231MB
~~~



~~~powershell
# docker rmi centos
Untagged: centos:latest
Untagged: centos@sha256:a27fd8080b517143cbbbab9dfb7c8571c40d67d534bbdee55bd6c473f432b177
Deleted: sha256:5d0da3dc976460b72c77d94c8a1ad043720b0416bfc16c52c45d4847e53fadb6
Deleted: sha256:74ddd0ec08fa43d09f32636ba91a0a3053b02cb4627c35051aff89f853606b59
~~~

或

~~~powershell
# docker images
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
centos       latest    5d0da3dc9764   4 months ago   231MB
~~~



~~~powershell
# docker rmi 5d0da3dc9764
~~~



## 8.2 Docker容器镜像介绍

### 8.2.1 Docker Image

- Docker 镜像是只读的容器模板，是Docker容器基础
- 为Docker容器提供了静态文件系统运行环境（rootfs）
- 是容器的静止状态
- 容器是镜像的运行状态



### 8.2.2 联合文件系统

#### 8.2.2.1 联合文件系统定义

- 联合文件系统(union filesystem)
- 联合文件系统是实现联合挂载技术的文件系统
- 联合挂载技术可以实现在一个挂载点同时挂载多个文件系统，将挂载点的原目录与被挂载内容进行整合，使得最终可见的文件系统包含整合之后的各层文件和目录



#### 8.2.2.2 图解



![image-20220125080435098](容器运行时 Docker.assets/image-20220125080435098.png)





### 8.2.3 Docker Overlay2

容器文件系统有多种存储驱动实现方式：aufs，devicemapper，overlay，overlay2 等，本次以overlay2为例进行说明。

#### 8.2.3.1 概念

- registry/repository： registry 是 repository 的集合，repository 是镜像的集合。
- image：image 是存储镜像相关的元数据，包括镜像的架构，镜像默认配置信息，镜像的容器配置信息等等。它是“逻辑”上的概念，并无物理上的镜像文件与之对应。
- layer：layer(镜像层) 组成了镜像，单个 layer 可以被多个镜像共享。



![image-20220125082226414](容器运行时 Docker.assets/image-20220125082226414.png)





#### 8.2.3.2 查看Docker Host存储驱动方式



~~~powershell
# docker info | grep overlay
 Storage Driver: overlay2
~~~



#### 8.2.3.3 了解images分层

~~~powershell
# docker pull nginx
Using default tag: latest
latest: Pulling from library/nginx
a2abf6c4d29d: Pull complete
a9edb18cadd1: Pull complete
589b7251471a: Pull complete
186b1aaa4aa6: Pull complete
b4df32aa5a72: Pull complete
a0bcbecc962e: Pull complete
Digest: sha256:0d17b565c37bcbd895e9d92315a05c1c3c9a29f762b011a10c54a66cd53c9b31
Status: Downloaded newer image for nginx:latest
docker.io/library/nginx:latest
~~~



可以看到上述下载的镜像分为6层，如何找到这6层存储在Docker Host哪个位置呢？

首先查看nginx镜像

~~~powershell
# docker images
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
nginx        latest    605c77e624dd   3 weeks ago    141MB
~~~



通过其Image ID 605c77e624dd 就可以找到存储位置



~~~powershell
# ls /var/lib/docker/image/overlay2/
distribution  imagedb  layerdb  repositories.json
~~~



这个目录是查找的入口，非常重要。它存储了镜像管理的元数据。

- repositories.json 记录了 repo 与镜像 ID 的映射关系
- imagedb 记录了镜像架构，操作系统，构建镜像的容器 ID 和配置以及 rootfs 等信息
- layerdb 记录了每层镜像层的元数据。



通过短 ID 查找 repositories.json 文件，找到镜像 nginx 的长 ID，通过长 ID 在 imagedb 中找到该镜像的元数据：



~~~powershell
# cat /var/lib/docker/image/overlay2/repositories.json | grep 605c77e624dd
{"Repositories":"nginx":{"nginx:latest":"sha256:605c77e624ddb75e6110f997c58876baa13f8754486b461117934b24a9dc3a85","nginx@sha256:0d17b565c37bcbd895e9d92315a05c1c3c9a29f762b011a10c54a66cd53c9b31":"sha256:605c77e624ddb75e6110f997c58876baa13f8754486b461117934b24a9dc3a85"}}}}
~~~



~~~powershell
# cat /var/lib/docker/image/overlay2/imagedb/content/sha256/605c77e624ddb75e6110f997c58876baa13f8754486b461117934b24a9dc3a85
......
"os":"linux","rootfs":{"type":"layers","diff_ids":["sha256:2edcec3590a4ec7f40cf0743c15d78fb39d8326bc029073b41ef9727da6c851f","sha256:e379e8aedd4d72bb4c529a4ca07a4e4d230b5a1d3f7a61bc80179e8f02421ad8","sha256:b8d6e692a25e11b0d32c5c3dd544b71b1085ddc1fddad08e68cbd7fda7f70221","sha256:f1db227348d0a5e0b99b15a096d930d1a69db7474a1847acbc31f05e4ef8df8c","sha256:32ce5f6a5106cc637d09a98289782edf47c32cb082dc475dd47cbf19a4f866da","sha256:d874fd2bc83bb3322b566df739681fbd2248c58d3369cb25908d68e7ed6040a6"]}}
~~~



这里仅保留我们想要的元数据 rootfs。在 rootfs 中看到 layers 有6层，这6层即对应镜像的6层镜像层。并且，自上而下分别映射到容器的底层到顶层。



## 8.3 Docker容器镜像操作命令

### 8.3.1  docker commit

上节提到容器内写文件会反映在 overlay 的可读写层，那么读写层的文件内容可以做成镜像吗？

可以。docker 通过 commit 和 build 操作实现镜像的构建。commit 将容器提交为一个镜像，build 在一个镜像的基础上构建镜像。

使用 commit 将上节的容器提交为一个镜像：



~~~powershell
[root@355e99982248 /]#   ctrl+p+q
~~~



~~~powershell
# docker ps
CONTAINER ID   IMAGE           COMMAND                  CREATED          STATUS          PORTS     NAMES
355e99982248   centos:latest   "bash"                   21 minutes ago   Up 21 minutes             fervent_perlman
~~~



~~~powershell
# docker commit 355e99982248
sha256:8965dcf23201ed42d4904e2f10854d301ad93b34bea73f384440692e006943de
~~~





~~~powershell
# docker images
REPOSITORY   TAG       IMAGE ID       CREATED              SIZE
<none>       <none>    8965dcf23201   About a minute ago   231MB
~~~





image 短 ID 8965dcf23201 即为容器提交的镜像，查看镜像的 imagedb 元数据：



~~~powershell
# cat  /var/lib/docker/image/overlay2/imagedb/content/sha256/8965dcf23201ed42d4904e2f10854d301ad93b34bea73f384440692e006943de
......
"os":"linux","rootfs":{"type":"layers","diff_ids":["sha256:74ddd0ec08fa43d09f32636ba91a0a3053b02cb4627c35051aff89f853606b59","sha256:551c3089b186b4027e949910981ff1ba54114610f2aab9359d28694c18b0203b"]}}
~~~



可以看到镜像层自上而下的前1个镜像层 diff_id 和 centos 镜像层 diff_id 是一样的，说明每层镜像层可以被多个镜像共享。而多出来的一层镜像层内容即是上节我们写入文件的内容：



~~~powershell
# echo -n "sha256:74ddd0ec08fa43d09f32636ba91a0a3053b02cb4627c35051aff89f853606b59 sha256:551c3089b186b4027e949910981ff1ba54114610f2aab9359d28694c18b0203b" | sha256sum -
92f7208b1cc0b5cc8fe214a4b0178aa4962b58af8ec535ee7211f335b1e0ed3b  -
~~~



~~~powershell
# cd /var/lib/docker/image/overlay2/layerdb/sha256/92f7208b1cc0b5cc8fe214a4b0178aa4962b58af8ec535ee7211f335b1e0ed3b
[root@192 92f7208b1cc0b5cc8fe214a4b0178aa4962b58af8ec535ee7211f335b1e0ed3b]# ls
cache-id  diff  parent  size  tar-split.json.gz


[root@192 92f7208b1cc0b5cc8fe214a4b0178aa4962b58af8ec535ee7211f335b1e0ed3b]# cat cache-id
250dc0b4f2c5f27952241a55cd4c286bfaaf8af4b77c9d0a38976df4c147cb95


[root@192 92f7208b1cc0b5cc8fe214a4b0178aa4962b58af8ec535ee7211f335b1e0ed3b]# ls /var/lib/docker/overlay2/250dc0b4f2c5f27952241a55cd4c286bfaaf8af4b77c9d0a38976df4c147cb95
diff  link  lower  work


[root@192 92f7208b1cc0b5cc8fe214a4b0178aa4962b58af8ec535ee7211f335b1e0ed3b]# ls /var/lib/docker/overlay2/250dc0b4f2c5f27952241a55cd4c286bfaaf8af4b77c9d0a38976df4c147cb95/diff
msb.txt

~~~



### 8.3.2 docker save

> 导出容器镜像，方便分享。



~~~powershell
# docker save -o centos.tar centos:latest  
~~~



~~~powershell
# ls

centos.tar  
~~~



### 8.3.3 docker load

> 把他人分享的容器镜像导入到本地，这通常是容器镜像分发方式之一。



~~~powershell
# docker load -i centos.tar
~~~



### 8.3.4 docker export

> 把正在运行的容器导出



~~~powershell
# docker ps
CONTAINER ID   IMAGE           COMMAND                  CREATED       STATUS       PORTS     NAMES
355e99982248   centos:latest   "bash"                   7 hours ago   Up 7 hours             fervent_perlman
~~~



~~~powershell
# docker export -o centos7.tar 355e99982248
~~~



~~~powershell
# ls
centos7.tar
~~~



### 8.3.5 docker import

> 导入使用docker export导入的容器做为本地容器镜像。



~~~powershell
# ls
centos7.tar 
~~~



~~~powershell
# docker import centos7.tar centos7:v1
~~~



~~~powershell
# docker images
REPOSITORY   TAG       IMAGE ID       CREATED              SIZE
centos7      v1        3639f9a13231   17 seconds ago       231MB
~~~



通过docker save与docker load及docker export与docker import分享容器镜像都是非常麻烦的，有没有更方便的方式分享容器镜像呢？





# 九、Docker 容器镜像加速器及本地容器镜像仓库

## 9.1 容器镜像加速器

> 由于国内访问国外的容器镜像仓库速度比较慢，因此国内企业创建了容器镜像加速器，以方便国内用户使用容器镜像。



### 9.1.1 获取阿里云容器镜像加速地址

![image-20220125221631548](容器运行时 Docker.assets/image-20220125221631548.png)



![image-20220125221748100](容器运行时 Docker.assets/image-20220125221748100.png)









### 9.1.2 配置docker daemon使用加速器



~~~powershell
添加daemon.json配置文件
# vim /etc/docker/daemon.json
# cat /etc/docker/daemon.json
{
        "registry-mirrors": ["https://s27w6kze.mirror.aliyuncs.com"]
}
~~~



~~~powershell
重启docker
# systemctl daemon-reload
# systemctl restart docker
~~~



~~~powershell
尝试下载容器镜像
# docker pull centos
~~~





## 9.2 容器镜像仓库

### 9.2.1 dockerhub

#### 9.2.1.1 注册



> 准备邮箱及用户ID



![image-20220125224745376](容器运行时 Docker.assets/image-20220125224745376.png)



![image-20220125224838850](容器运行时 Docker.assets/image-20220125224838850.png)



![image-20220125225243088](容器运行时 Docker.assets/image-20220125225243088.png)





![image-20220125225448956](容器运行时 Docker.assets/image-20220125225448956.png)









#### 9.2.1.2 登录



![image-20220125225753719](容器运行时 Docker.assets/image-20220125225753719.png)



![image-20220125225914283](容器运行时 Docker.assets/image-20220125225914283.png)





#### 9.2.1.3 创建容器镜像仓库



![image-20220125230046456](容器运行时 Docker.assets/image-20220125230046456.png)





![image-20220125230216488](容器运行时 Docker.assets/image-20220125230216488.png)



![image-20220125230307699](容器运行时 Docker.assets/image-20220125230307699.png)





#### 9.2.1.4 在本地登录Docker Hub



~~~powershell
默认可以不添加docker hub容器镜像仓库地址
# docker login 
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username: dockersmartmsb
Password:
WARNING! Your password will be stored unencrypted in /root/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded 成功
~~~





~~~powershell
登出
# docker logout
Removing login credentials for https://index.docker.io/v1/
~~~



#### 9.2.1.5 上传容器镜像

> 在登录Docker Hub主机上传容器镜像,向全球用户共享容器镜像。



~~~powershell
为容器镜像重新打标记

原始容器镜像
# docker images
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
centos       latest    5d0da3dc9764   4 months ago   231MB

重新为容器镜像打标记
# docker tag centos:latest dockersmartmsb/centos:v1

重新打标记后容器镜像
# docker images
REPOSITORY              TAG       IMAGE ID       CREATED        SIZE
dockersmartmsb/centos   v1        5d0da3dc9764   4 months ago   231MB
centos                  latest    5d0da3dc9764   4 months ago   231MB
~~~



~~~powershell
上传容器镜像至docker hub
# docker push dockersmartmsb/centos:v1
The push refers to repository [docker.io/dockersmartmsb/centos]
74ddd0ec08fa: Mounted from library/centos
v1: digest: sha256:a1801b843b1bfaf77c501e7a6d3f709401a1e0c83863037fa3aab063a7fdb9dc size: 529
~~~



![image-20220125231912826](容器运行时 Docker.assets/image-20220125231912826.png)



#### 9.2.1.6 下载容器镜像



~~~powershell
在其它主机上下载

下载
# docker pull dockersmartmsb/centos:v1
v1: Pulling from dockersmartmsb/centos
a1d0c7532777: Pull complete
Digest: sha256:a1801b843b1bfaf77c501e7a6d3f709401a1e0c83863037fa3aab063a7fdb9dc
Status: Downloaded newer image for dockersmartmsb/centos:v1
docker.io/dockersmartmsb/centos:v1


查看下载后容器镜像
# docker images
REPOSITORY              TAG       IMAGE ID       CREATED        SIZE
dockersmartmsb/centos   v1        5d0da3dc9764   4 months ago   231MB
~~~





### 9.2.2 harbor

#### 9.2.2.1 获取 docker compose二进制文件

~~~powershell
下载docker-compose二进制文件
# wget https://github.com/docker/compose/releases/download/1.25.0/docker-compose-Linux-x86_64
或
# wget https://github.com/docker/compose/releases/download/v2.20.0/docker-compose-linux-x86_64
~~~



~~~powershell
查看已下载二进制文件
# ls
docker-compose-Linux-x86_64
~~~



~~~powershell
移动二进制文件到/usr/bin目录，并更名为docker-compose
# mv docker-compose-Linux-x86_64 /usr/bin/docker-compose
~~~



~~~powershell
为二进制文件添加可执行权限
# chmod +x /usr/bin/docker-compose
~~~



~~~powershell
安装完成后，查看docker-compse版本
# docker-compose version
docker-compose version 1.25.0, build 0a186604
docker-py version: 4.1.0
CPython version: 3.7.4
OpenSSL version: OpenSSL 1.1.0l  10 Sep 2019
~~~





#### 9.2.2.2 获取harbor安装文件



![image-20220125232445910](容器运行时 Docker.assets/image-20220125232445910.png)

![image-20220125232519365](容器运行时 Docker.assets/image-20220125232519365.png)





![image-20220125233602760](容器运行时 Docker.assets/image-20220125233602760.png)







![image-20220125233652604](容器运行时 Docker.assets/image-20220125233652604.png)





![image-20220125233739356](容器运行时 Docker.assets/image-20220125233739356.png)





~~~powershell
下载harbor离线安装包
# wget https://github.com/goharbor/harbor/releases/download/v2.4.1/harbor-offline-installer-v2.4.1.tgz
~~~



~~~powershell
查看已下载的离线安装包
# ls
harbor-offline-installer-v2.4.1.tgz
~~~





#### 9.2.2.3 获取TLS文件



~~~powershell
查看准备好的证书
# ls
kubemsb.com_nginx.zip
~~~



~~~powershell
解压证书压缩包文件
# unzip kubemsb.com_nginx.zip
Archive:  kubemsb.com_nginx.zip
Aliyun Certificate Download
  inflating: 6864844_kubemsb.com.pem
  inflating: 6864844_kubemsb.com.key
~~~



~~~powershell
查看解压出的文件
# ls
6864844_kubemsb.com.key
6864844_kubemsb.com.pem
~~~



#### 9.2.2.4 修改配置文件



~~~powershell
解压harbor离线安装包
# tar xf harbor-offline-installer-v2.4.1.tgz
~~~



~~~powershell
查看解压出来的目录
# ls
harbor 
~~~





~~~powershell
移动证书到harbor目录
# # mv 6864844_kubemsb.com.* harbor

查看harbor目录
# ls harbor
6864844_kubemsb.com.key  6864844_kubemsb.com.pem  common.sh  harbor.v2.4.1.tar.gz  harbor.yml.tmpl  install.sh  LICENSE  prepare
~~~



~~~powershell
创建配置文件
# cd harbor/
# mv harbor.yml.tmpl harbor.yml
~~~



~~~powershell
修改配置文件内容

# vim harbor.yml

# Configuration file of Harbor

# The IP address or hostname to access admin UI and registry service.
# DO NOT use localhost or 127.0.0.1, because Harbor needs to be accessed by external clients.
hostname: www.kubemsb.com 修改为域名，而且一定是证书签发的域名

# http related config
http:
  # port for http, default is 80. If https enabled, this port will redirect to https port
  port: 80

# https related config
https:
  # https port for harbor, default is 443
  port: 443
  # The path of cert and key files for nginx
  certificate: /root/harbor/6864844_kubemsb.com.pem 证书
  private_key: /root/harbor/6864844_kubemsb.com.key 密钥

# # Uncomment following will enable tls communication between all harbor components
# internal_tls:
#   # set enabled to true means internal tls is enabled
#   enabled: true
#   # put your cert and key files on dir
#   dir: /etc/harbor/tls/internal

# Uncomment external_url if you want to enable external proxy
# And when it enabled the hostname will no longer used
# external_url: https://reg.mydomain.com:8433

# The initial password of Harbor admin
# It only works in first time to install harbor
# Remember Change the admin password from UI after launching Harbor.
harbor_admin_password: 12345 访问密码
......
~~~





#### 9.2.2.5 执行预备脚本



~~~powershell
# ./prepare
~~~



~~~powershell
输出
prepare base dir is set to /root/harbor
Clearing the configuration file: /config/portal/nginx.conf
Clearing the configuration file: /config/log/logrotate.conf
Clearing the configuration file: /config/log/rsyslog_docker.conf
Generated configuration file: /config/portal/nginx.conf
Generated configuration file: /config/log/logrotate.conf
Generated configuration file: /config/log/rsyslog_docker.conf
Generated configuration file: /config/nginx/nginx.conf
Generated configuration file: /config/core/env
Generated configuration file: /config/core/app.conf
Generated configuration file: /config/registry/config.yml
Generated configuration file: /config/registryctl/env
Generated configuration file: /config/registryctl/config.yml
Generated configuration file: /config/db/env
Generated configuration file: /config/jobservice/env
Generated configuration file: /config/jobservice/config.yml
Generated and saved secret to file: /data/secret/keys/secretkey
Successfully called func: create_root_cert
Generated configuration file: /compose_location/docker-compose.yml
Clean up the input dir
~~~





#### 9.2.2.6 执行安装脚本



~~~powershell
# ./install.sh
~~~



~~~powershell
输出
[Step 0]: checking if docker is installed ...

Note: docker version: 20.10.12

[Step 1]: checking docker-compose is installed ...

Note: docker-compose version: 1.25.0

[Step 2]: loading Harbor images ...

[Step 3]: preparing environment ...

[Step 4]: preparing harbor configs ...
prepare base dir is set to /root/harbor

[Step 5]: starting Harbor ...
Creating network "harbor_harbor" with the default driver
Creating harbor-log ... done
Creating harbor-db     ... done
Creating registry      ... done
Creating registryctl   ... done
Creating redis         ... done
Creating harbor-portal ... done
Creating harbor-core   ... done
Creating harbor-jobservice ... done
Creating nginx             ... done
✔ ----Harbor has been installed and started successfully.----
~~~





#### 9.2.2.7 验证运行情况



~~~powershell
# docker ps
CONTAINER ID   IMAGE                                COMMAND                  CREATED              STATUS                        PORTS                                                                            NAMES
71c0db683e4a   goharbor/nginx-photon:v2.4.1         "nginx -g 'daemon of…"   About a minute ago   Up About a minute (healthy)   0.0.0.0:80->8080/tcp, :::80->8080/tcp, 0.0.0.0:443->8443/tcp, :::443->8443/tcp   nginx
4e3b53a86f01   goharbor/harbor-jobservice:v2.4.1    "/harbor/entrypoint.…"   About a minute ago   Up About a minute (healthy)                                                                                    harbor-jobservice
df76e1eabbf7   goharbor/harbor-core:v2.4.1          "/harbor/entrypoint.…"   About a minute ago   Up About a minute (healthy)                                                                                    harbor-core
eeb4d224dfc4   goharbor/harbor-portal:v2.4.1        "nginx -g 'daemon of…"   About a minute ago   Up About a minute (healthy)                                                                                    harbor-portal
70e162c38b59   goharbor/redis-photon:v2.4.1         "redis-server /etc/r…"   About a minute ago   Up About a minute (healthy)                                                                                    redis
8bcc0e9b06ec   goharbor/harbor-registryctl:v2.4.1   "/home/harbor/start.…"   About a minute ago   Up About a minute (healthy)                                                                                    registryctl
d88196398df7   goharbor/registry-photon:v2.4.1      "/home/harbor/entryp…"   About a minute ago   Up About a minute (healthy)                                                                                    registry
ed5ba2ba9c82   goharbor/harbor-db:v2.4.1            "/docker-entrypoint.…"   About a minute ago   Up About a minute (healthy)                                                                                    harbor-db
dcb4b57c7542   goharbor/harbor-log:v2.4.1           "/bin/sh -c /usr/loc…"   About a minute ago   Up About a minute (healthy)   127.0.0.1:1514->10514/tcp                                                        harbor-log

~~~







#### 9.2.2.8 访问harbor UI界面



##### 9.2.2.8.1 在物理机通过浏览器访问



![image-20220126000804490](容器运行时 Docker.assets/image-20220126000804490.png)



![image-20220126000825616](容器运行时 Docker.assets/image-20220126000825616.png)





![image-20220126000840905](容器运行时 Docker.assets/image-20220126000840905.png)



##### 9.2.2.8.2 在Docker Host主机通过域名访问



~~~powershell
添加域名解析
# vim /etc/hosts
# cat /etc/hosts
127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4
::1         localhost localhost.localdomain localhost6 localhost6.localdomain6
192.168.10.155 www.kubemsb.com
~~~



![image-20220126001253192](容器运行时 Docker.assets/image-20220126001253192.png)



![image-20220126001447862](容器运行时 Docker.assets/image-20220126001447862.png)



![image-20220126001510880](容器运行时 Docker.assets/image-20220126001510880.png)





## 9.3 docker镜像上传至Harbor及从harbor下载

### 9.3.1  修改docker daemon使用harbor



~~~powershell
添加/etc/docker/daemon.json文件，默认不存在，需要手动添加
# vim /etc/docker/daemon.json
# cat /etc/docker/daemon.json
{
        "insecure-registries": ["www.kubemsb.com"]
}
~~~



~~~powershell
重启加载daemon配置
# systemctl daemon-reload
~~~



~~~powershell
重启docker
# systemctl restart docker
~~~





### 9.3.2 docker tag



~~~powershell
查看已有容器镜像文件
# docker images
REPOSITORY                      TAG       IMAGE ID       CREATED        SIZE
centos                          latest    5d0da3dc9764   4 months ago   231MB
~~~



~~~powershell
为已存在镜像重新添加tag
# docker tag centos:latest www.kubemsb.com/library/centos:v1
~~~



~~~powershell
再次查看本地容器镜像
# docker images
REPOSITORY                       TAG       IMAGE ID       CREATED        SIZE
centos                           latest    5d0da3dc9764   4 months ago   231MB
www.kubemsb.com/library/centos   v1        5d0da3dc9764   4 months ago   231MB
~~~





### 9.3.3 docker push



~~~powershell
# docker login www.kubemsb.com
Username: admin  用户名 admin
Password:        密码   12345
WARNING! Your password will be stored unencrypted in /root/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded 登陆成功
~~~



~~~powershell
推送本地容器镜像到harbor仓库
# docker push www.kubemsb.com/library/centos:v1
~~~



![image-20220126002747864](容器运行时 Docker.assets/image-20220126002747864.png)





### 9.3.4 docker pull

> 在其它主机上下载或使用harbor容器镜像仓库中的容器镜像



~~~powershell
在本地添加域名解析
# vim /etc/hosts
# cat /etc/hosts
127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4
::1         localhost localhost.localdomain localhost6 localhost6.localdomain6
192.168.10.155 www.kubemsb.com
~~~





~~~powershell
在本地添加/etc/docker/daemon.json文件，其中为本地主机访问的容器镜像仓库
# vim /etc/docker/daemon.json
# cat /etc/docker/daemon.json
{
        "insecure-registries": ["www.kubemsb.com"]
}
~~~



~~~powershell
# systemctl daemon-reload
# systemctl restart docker
~~~



~~~powershell
下载容器镜像
# docker pull www.kubemsb.com/library/centos:v1
v1: Pulling from library/centos
Digest: sha256:a1801b843b1bfaf77c501e7a6d3f709401a1e0c83863037fa3aab063a7fdb9dc
Status: Downloaded newer image for www.kubemsb.com/library/centos:v1
www.kubemsb.com/library/centos:v1
~~~



~~~powershell
查看已下载的容器镜像
# docker images
REPOSITORY                       TAG       IMAGE ID       CREATED        SIZE
www.kubemsb.com/library/centos   v1        5d0da3dc9764   4 months ago   231MB
~~~





# 十、Docker 运行企业应用



## 10.1 使用Docker容器化部署企业级应用必要性

- 有利于快速实现企业级应用部署
- 有利于快速实现企业级应用恢复



## 10.2 使用Docker容器化部署企业级应用参考资料

![image-20220211145757283](容器运行时 Docker.assets/image-20220211145757283.png?lastModify=1689308130)



## 10.3 使用Docker容器实现Nginx部署

### 10.3.1 获取参考资料

![image-20220211145839441](容器运行时 Docker.assets/image-20220211145839441.png?lastModify=1689308130)





![image-20220211145905117](容器运行时 Docker.assets/image-20220211145905117.png?lastModify=1689308130)





![image-20220211145956450](容器运行时 Docker.assets/image-20220211145956450.png?lastModify=1689308130)



### 10.3.2 运行Nginx应用容器

> 不在docker host暴露端口



```powershell
 # docker run -d --name nginx-server -v /opt/nginx-server:/usr/share/nginx/html:ro nginx
 664cd1bbda4ad2a71cbd09f0c6baa9b34db80db2d69496670a960be07b9521cb
```



```powershell
 # docker ps
 CONTAINER ID   IMAGE       COMMAND                  CREATED          STATUS          PORTS                                                  NAMES
 664cd1bbda4a   nginx       "/docker-entrypoint.…"   4 seconds ago    Up 3 seconds    80/tcp                                                 nginx-server
```



```powershell
 # docker inspect 664 | grep IPAddress
             "SecondaryIPAddresses": null,
             "IPAddress": "172.17.0.3",
                     "IPAddress": "172.17.0.3",
```



```powershell
 # curl http://172.17.0.3
 <html>
 <head><title>403 Forbidden</title></head>
 <body>
 <center><h1>403 Forbidden</h1></center>
 <hr><center>nginx/1.21.6</center>
 </body>
 </html>
```





```powershell
 # ls /opt
 nginx-server
 # echo "nginx is working" > /opt/nginx-server/index.html
```



```powershell
 # curl http://172.17.0.3
 nginx is working
```



### 10.3.3 运行Nginx应用容器

> 在docker host暴露80端口



```powershell
 # docker run -d -p 80:80 --name nginx-server-port -v /opt/nginx-server-port:/usr/share/nginx/html:ro nginx
```



```powershell
 # docker ps
 CONTAINER ID   IMAGE       COMMAND                  CREATED             STATUS             PORTS                                                  NAMES
 74dddf51983d   nginx       "/docker-entrypoint.…"   3 seconds ago       Up 2 seconds       0.0.0.0:80->80/tcp, :::80->80/tcp                      nginx-server-port
```



```powershell
 # ls /opt
 nginx-server  nginx-server-port
```



```powershell
 # echo "nginx is running" > /opt/nginx-server-port/index.html
```



**在宿主机上访问**



![image-20220211151131609](容器运行时 Docker.assets/image-20220211151131609.png?lastModify=1689308130)





```powershell
 # docker top nginx-server-port
 UID                 PID                 PPID                C                   STIME               TTY                 TIME                CMD
 root                22195               22163               0                   15:08               ?                   00:00:00            nginx: master process nginx -g daemon off;
 101                 22387               22195               0                   15:08               ?                   00:00:00            nginx: worker process
 
```







### 10.3.4 运行Nginx应用容器

> 挂载配置文件,需要创建一个nginx容器，把配置文件复制出来修改后使用。



```powershell
 # docker cp nginxwebcontainername:/etc/nginx/nginx.conf /opt/nginxcon/
 修改后即可使用
```





```powershell
 # ls /opt/nginxcon/nginx.conf
 /opt/nginxcon/nginx.conf
```



```powershell
 # docker run -d \
 -p 82:80 --name nginx-server-conf \
 -v /opt/nginx-server-conf:/usr/share/nginx/html:ro \
 -v /opt/nginxcon/nginx.conf:/etc/nginx/nginx.conf:ro \
 nginx
 76251ec44e5049445399303944fc96eb8161ccb49e27b673b99cb2492009523c
```



```powershell
 # docker top nginx-server-conf
 UID                 PID                 PPID                C                   STIME               TTY                 TIME                CMD
 root                25005               24972               0                   15:38               ?                   00:00:00            nginx: master process nginx -g daemon off;
 101                 25178               25005               0                   15:38               ?                   00:00:00            nginx: worker process
 101                 25179               25005               0                   15:38               ?                   00:00:00            nginx: worker process
```





## 10.4 使用Docker容器实现Tomcat部署

### 10.4.1 获取参考资料

![image-20220211154602595](容器运行时 Docker.assets/image-20220211154602595.png?lastModify=1689308130)





![image-20220211154639682](容器运行时 Docker.assets/image-20220211154639682.png?lastModify=1689308130)





![image-20220211154747062](容器运行时 Docker.assets/image-20220211154747062.png?lastModify=1689308130)





### 10.4.2 运行tomcat应用容器

#### 10.4.2.1 不暴露端口运行



```powershell
 # docker run -d --rm tomcat:9.0
```



```powershell
 # docker ps
 CONTAINER ID   IMAGE        COMMAND                  CREATED             STATUS             PORTS                                                  NAMES
 c20a0e781246   tomcat:9.0   "catalina.sh run"        27 seconds ago      Up 25 seconds      8080/tcp                                               heuristic_cori
```



#### 10.4.2.2  暴露端口运行

```powershell
 # docker run -d -p 8080:8080 --rm tomcat:9.0
 2fcf5762314373c824928490b871138a01a94abedd7e6814ad5f361d09fbe1de
```



```powershell
# docker ps
CONTAINER ID   IMAGE        COMMAND                  CREATED             STATUS             PORTS                                                  NAMES
2fcf57623143   tomcat:9.0   "catalina.sh run"        3 seconds ago       Up 1 second        0.0.0.0:8080->8080/tcp, :::8080->8080/tcp              eloquent_chatelet
```



**在宿主机访问**



![image-20220211160925125](容器运行时 Docker.assets/image-20220211160925125.png?lastModify=1689308130)



```powershell
# docker exec 2fc ls /usr/local/tomcat/webapps
里面为空，所以可以添加网站文件。
```



#### 10.4.2.3 暴露端口及添加网站文件



```powershell
# docker run -d -p 8081:8080 -v /opt/tomcat-server:/usr/local/tomcat/webapps/ROOT tomcat:9.0
f456e705d48fc603b7243a435f0edd6284558c194e105d87befff2dccddc0b63
```



```powershell
# docker ps
CONTAINER ID   IMAGE        COMMAND             CREATED         STATUS         PORTS                                       NAMES
f456e705d48f   tomcat:9.0   "catalina.sh run"   3 seconds ago   Up 2 seconds   0.0.0.0:8081->8080/tcp, :::8081->8080/tcp   cool_germain
```



```powershell
# echo "tomcat running" > /opt/tomcat-server/index.html
```



**在宿主机访问**

![image-20220211162127222](容器运行时 Docker.assets/image-20220211162127222.png?lastModify=1689308130)

## 10.5 使用Docker容器实现MySQL部署

### 10.5.1 单节点MySQL部署

![image-20220211162728055](容器运行时 Docker.assets/image-20220211162728055.png?lastModify=1689308130)



![image-20220211162817731](容器运行时 Docker.assets/image-20220211162817731.png?lastModify=1689308130)





![image-20220211162911952](容器运行时 Docker.assets/image-20220211162911952.png?lastModify=1689308130)



```powershell
# docker run -p 3306:3306 \
 --name mysql \
 -v /opt/mysql/log:/var/log/mysql \
 -v /opt/mysql/data:/var/lib/mysql \
 -v /opt/mysql/conf:/etc/mysql \
 -v /opt/mysql/conf.d:/etc/mysql/conf.d \
 -e MYSQL_ROOT_PASSWORD=root \
 -d \
 mysql:5.7
```



```powershell
# docker ps
CONTAINER ID   IMAGE       COMMAND                  CREATED          STATUS          PORTS                                                  NAMES
6d16ca21cf31   mysql:5.7   "docker-entrypoint.s…"   32 seconds ago   Up 30 seconds   0.0.0.0:3306->3306/tcp, :::3306->3306/tcp, 33060/tcp   mysql
```



```powershell
通过容器中客户端访问
# docker exec -it mysql mysql -uroot -proot
mysql: [Warning] Using a password on the command line interface can be insecure.
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 4
Server version: 5.7.37 MySQL Community Server (GPL)

Copyright (c) 2000, 2022, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql>
```



```powershell
在docker host上访问
# yum -y install mariadb

# mysql -h 192.168.255.157 -uroot -proot -P 3306
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MySQL connection id is 7
Server version: 5.7.37 MySQL Community Server (GPL)

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MySQL [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
4 rows in set (0.00 sec)
```







### 10.5.2  MySQL主从复制集群部署

#### 10.5.2.1 MySQL主节点部署

```powershell
# docker run -p 3306:3306 \
 --name mysql-master \
 -v /opt/mysql-master/log:/var/log/mysql \
 -v /opt/mysql-master/data:/var/lib/mysql \
 -v /opt/mysql-master/conf:/etc/mysql \
 -v /opt/mysql-master/mysql.conf.d:/etc/mysql/mysql.conf.d \
 -v /opt/mysql-master/conf.d:/etc/mysql/conf.d \
 -e MYSQL_ROOT_PASSWORD=root \
 -d mysql:5.7
```



```powershell
# docker ps
CONTAINER ID   IMAGE       COMMAND                  CREATED          STATUS          PORTS                                                  NAMES
2dbbed8e35c7   mysql:5.7   "docker-entrypoint.s…"   58 seconds ago   Up 57 seconds   0.0.0.0:3306->3306/tcp, :::3306->3306/tcp, 33060/tcp   mysql-master
```



#### 10.5.2.2  MySQL主节点配置



```powershell
# vim /opt/mysql-master/conf/my.cnf
# cat /opt/mysql-master/conf/my.cnf
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve

server_id=1
log-bin=mysql-bin
read-only=0
binlog-do-db=kubemsb_test

replicate-ignore-db=mysql
replicate-ignore-db=sys
replicate-ignore-db=information_schema
replicate-ignore-db=performance_schema
```



#### 10.5.2.3  MySQL从节点部署

```powershell
# docker run -p 3307:3306 \
 --name mysql-slave \
 -v /opt/mysql-slave/log:/var/log/mysql \
 -v /opt/mysql-slave/data:/var/lib/mysql \
 -v /opt/mysql-slave/conf:/etc/mysql \
 -v /opt/mysql-slave/mysql.conf.d:/etc/mysql/mysql.conf.d \
 -v /opt/mysql-slave/conf.d:/etc/mysql/conf.d \
 -e MYSQL_ROOT_PASSWORD=root \
 -d \
 --link mysql-master:mysql-master \
 mysql:5.7
```



```powershell
# docker ps
CONTAINER ID   IMAGE       COMMAND                  CREATED         STATUS         PORTS                                                  NAMES
caf7bf3fc68f   mysql:5.7   "docker-entrypoint.s…"   8 seconds ago   Up 6 seconds   33060/tcp, 0.0.0.0:3307->3306/tcp, :::3307->3306/tcp   mysql-slave
```



#### 10.5.2.4  MySQL从节点配置

```powershell
# vim /opt/mysql-slave/conf/my.cnf
# cat /opt/mysql-slave/conf/my.cnf
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve

server_id=2
log-bin=mysql-bin
read-only=1
binlog-do-db=kubemsb_test

replicate-ignore-db=mysql
replicate-ignore-db=sys
replicate-ignore-db=information_schema
replicate-ignore-db=performance_schema
```



#### 10.5.2.5  master节点配置

```powershell
# mysql -h 192.168.255.157 -uroot -proot -P 3306
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MySQL connection id is 2
Server version: 5.7.37 MySQL Community Server (GPL)

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MySQL [(none)]>
```



```powershell
授权
MySQL [(none)]> grant replication slave on *.* to 'backup'@'%' identified by '123456';
```



```powershell
重启容器，使用配置生效
# docker restart mysql-master
```



```powershell
查看状态
MySQL [(none)]> show master status\G
*************************** 1. row ***************************
             File: mysql-bin.000001
         Position: 154
     Binlog_Do_DB: kubemsb_test
 Binlog_Ignore_DB:
Executed_Gtid_Set:
1 row in set (0.00 sec)
```





#### 10.5.2.6 slave节点配置

```powershell
# docker restart mysql-slave
```



```powershell
# mysql -h 192.168.255.157 -uroot -proot -P 3307
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MySQL connection id is 2
Server version: 5.7.37 MySQL Community Server (GPL)

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MySQL [(none)]>
```



```powershell
MySQL [(none)]> change master to master_host='mysql-master', master_user='backup', master_password='123456', master_log_file='mysql-bin.000001', master_log_pos=154, master_port=3306;
```



```powershell
MySQL [(none)]> start slave;
```



```powershell
MySQL [(none)]> show slave status\G
*************************** 1. row ***************************
               Slave_IO_State: Waiting for master to send event
                  Master_Host: mysql-master
                  Master_User: backup
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql-bin.000001
          Read_Master_Log_Pos: 154
               Relay_Log_File: e0872f94c377-relay-bin.000002
                Relay_Log_Pos: 320
        Relay_Master_Log_File: mysql-bin.000001
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
              Replicate_Do_DB:
          Replicate_Ignore_DB: mysql,sys,information_schema,performance_schema
           Replicate_Do_Table:
       Replicate_Ignore_Table:
      Replicate_Wild_Do_Table:
  Replicate_Wild_Ignore_Table:
                   Last_Errno: 0
                   Last_Error:
                 Skip_Counter: 0
          Exec_Master_Log_Pos: 154
              Relay_Log_Space: 534
              Until_Condition: None
               Until_Log_File:
                Until_Log_Pos: 0
           Master_SSL_Allowed: No
           Master_SSL_CA_File:
           Master_SSL_CA_Path:
              Master_SSL_Cert:
            Master_SSL_Cipher:
               Master_SSL_Key:
        Seconds_Behind_Master: 0
Master_SSL_Verify_Server_Cert: No
                Last_IO_Errno: 0
                Last_IO_Error:
               Last_SQL_Errno: 0
               Last_SQL_Error:
  Replicate_Ignore_Server_Ids:
             Master_Server_Id: 1
                  Master_UUID: 0130b415-8b21-11ec-8982-0242ac110002
             Master_Info_File: /var/lib/mysql/master.info
                    SQL_Delay: 0
          SQL_Remaining_Delay: NULL
      Slave_SQL_Running_State: Slave has read all relay log; waiting for more updates
           Master_Retry_Count: 86400
                  Master_Bind:
      Last_IO_Error_Timestamp:
     Last_SQL_Error_Timestamp:
               Master_SSL_Crl:
           Master_SSL_Crlpath:
           Retrieved_Gtid_Set:
            Executed_Gtid_Set:
                Auto_Position: 0
         Replicate_Rewrite_DB:
                 Channel_Name:
           Master_TLS_Version:
1 row in set (0.00 sec)
```



#### 10.5.2.7  验证MySQL集群可用性



```powershell
在MySQL Master节点添加kubemsb_test数据库
# mysql -h 192.168.255.157 -uroot -proot -P3306

MySQL [(none)]> create database kubemsb_test;
Query OK, 1 row affected (0.00 sec)

MySQL [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| kubemsb_test       |     |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
6 rows in set (0.00 sec)
```



```powershell
在MySQL Slave节点查看同步情况
# mysql -h 192.168.255.157 -uroot -proot -P3307

MySQL [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| kubemsb_test       |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
5 rows in set (0.00 sec)
```





## 10.6 使用Docker容器实现ElasticSearch+Kibana部署

### 10.6.1 获取参考资料

#### 10.6.1.1 ES部署参考资料

![image-20220212211742909](容器运行时 Docker.assets/image-20220212211742909.png?lastModify=1689308130)





![image-20220212211840530](容器运行时 Docker.assets/image-20220212211840530.png?lastModify=1689308130)



![image-20220212211857732](容器运行时 Docker.assets/image-20220212211857732.png?lastModify=1689308130)





![image-20220212211950202](容器运行时 Docker.assets/image-20220212211950202.png?lastModify=1689308130)





#### 10.6.1.2 Kibana部署参考资料

![image-20220212212223735](容器运行时 Docker.assets/image-20220212212223735.png?lastModify=1689308130)



![image-20220212212245791](容器运行时 Docker.assets/image-20220212212245791.png?lastModify=1689308130)

![image-20220212212305429](容器运行时 Docker.assets/image-20220212212305429.png?lastModify=1689308130)





![image-20220212212341841](容器运行时 Docker.assets/image-20220212212341841.png?lastModify=1689308130)





### 10.6.2 ES部署



```powershell
# docker pull elasticsearch:7.17.0
```



```powershell
# mkdir -p /opt/es/config
# mkdir -p /opt/es/data
```



```powershell
# echo "http.host: 0.0.0.0" >> /opt/es/config/elasticsearch.yml
```



```powershell
# chmod -R 777 /opt/es/
```



```powershell
# docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms64m -Xmx512m" \
-v /opt/es/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /opt/es/data:/usr/share/elasticsearch/data \
-v /opt/es/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.17.0
```



```powershell
# docker ps
CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS          PORTS                                                                                  NAMES
e1c306e6e5a3   elasticsearch:7.17.0   "/bin/tini -- /usr/l…"   22 seconds ago   Up 20 seconds   0.0.0.0:9200->9200/tcp, :::9200->9200/tcp, 0.0.0.0:9300->9300/tcp, :::9300->9300/tcp   elasticsearch
```



![image-20220212224446838](容器运行时 Docker.assets/image-20220212224446838.png?lastModify=1689308130)



### 10.6.3 Kibana部署



```powershell
# docker pull kibana:7.17.0
```



```powershell
# docker run --name kibana -e ELASTICSEARCH_HOSTS=http://192.168.255.157:9200 -p 5601:5601 \
-d kibana:7.17.0
```



```powershell
# docker ps
CONTAINER ID   IMAGE                  COMMAND                  CREATED         STATUS         PORTS                                                                                  NAMES
fb60e73f9cd5   kibana:7.17.0          "/bin/tini -- /usr/l…"   2 minutes ago   Up 2 minutes   0.0.0.0:5601->5601/tcp, :::5601->5601/tcp                                              kibana
```



![image-20220212224524598](容器运行时 Docker.assets/image-20220212224524598.png?lastModify=1689308130)









## 10.7 使用Docker容器实现Redis部署

### 10.7.1 获取参考资料

![image-20220212225251173](容器运行时 Docker.assets/image-20220212225251173.png?lastModify=1689308130)



![image-20220212225313006](容器运行时 Docker.assets/image-20220212225313006.png?lastModify=1689308130)



![image-20220212225336437](容器运行时 Docker.assets/image-20220212225336437.png?lastModify=1689308130)







![image-20220212225412367](容器运行时 Docker.assets/image-20220212225412367.png?lastModify=1689308130)





### 10.7.2 运行Redis容器

```powershell
# mkdir -p /opt/redis/conf
```



```powershell
# touch /opt/redis/conf/redis.conf
```



```powershell
# docker run -p 6379:6379 --name redis-server -v /opt/redis/data:/data \
-v /opt/redis/conf:/etc/redis \
-d redis redis-server /etc/redis/redis.conf
```



```powershell
# docker ps
CONTAINER ID   IMAGE                  COMMAND                  CREATED          STATUS          PORTS                                                                                  NAMES
9bd2b39cd92a   redis                  "docker-entrypoint.s…"   44 seconds ago   Up 42 seconds   0.0.0.0:6379->6379/tcp, :::6379->6379/tcp                                              redis
```



### 10.7.3 验证



```powershell
# wget -O /etc/yum.repos.d/epel.repo http://mirrors.aliyun.com/repo/epel-7.repo
```



```powershell
# yum -y install redis
```



```powershell
# redis-cli -h 192.168.255.157 -p 6379

192.168.255.157:6379> set test1 a
OK
192.168.255.157:6379> get test1
"a"
```



### 10.7.4 Redis集群

安装redis-cluster；3主3从方式，从为了同步备份，主进行slot数据分片

```powershell
编辑运行多个redis容器脚本文件
# vim redis-cluster.sh
# cat redis-cluster.sh
for port in $(seq 8001 8006); \
do \
mkdir -p /mydata/redis/node-${port}/conf
touch /mydata/redis/node-${port}/conf/redis.conf
cat << EOF >/mydata/redis/node-${port}/conf/redis.conf
port ${port}
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
cluster-announce-ip 192.168.10.180
cluster-announce-port ${port}
cluster-announce-bus-port 1${port}
appendonly yes
EOF
docker run -p ${port}:${port} -p 1${port}:1${port} --name redis-${port} \
-v /mydata/redis/node-${port}/data:/data \
-v /mydata/redis/node-${port}/conf/redis.conf:/etc/redis/redis.conf \
-d redis:5.0.7 redis-server /etc/redis/redis.conf; \
done
```





```powershell
执行脚本
# sh redis-cluster.sh
```



```powershell
查看已运行容器
# docker ps
CONTAINER ID   IMAGE         COMMAND                  CREATED              STATUS              PORTS                                                                                                NAMES
8d53864a98ce   redis:5.0.7   "docker-entrypoint.s…"   About a minute ago   Up About a minute   0.0.0.0:8006->8006/tcp, :::8006->8006/tcp, 6379/tcp, 0.0.0.0:18006->18006/tcp, :::18006->18006/tcp   redis-8006
e2b5da0f0605   redis:5.0.7   "docker-entrypoint.s…"   2 minutes ago        Up About a minute   0.0.0.0:8005->8005/tcp, :::8005->8005/tcp, 6379/tcp, 0.0.0.0:18005->18005/tcp, :::18005->18005/tcp   redis-8005
70e8e8f15aea   redis:5.0.7   "docker-entrypoint.s…"   2 minutes ago        Up 2 minutes        0.0.0.0:8004->8004/tcp, :::8004->8004/tcp, 6379/tcp, 0.0.0.0:18004->18004/tcp, :::18004->18004/tcp   redis-8004
dff8e4bf02b4   redis:5.0.7   "docker-entrypoint.s…"   2 minutes ago        Up 2 minutes        0.0.0.0:8003->8003/tcp, :::8003->8003/tcp, 6379/tcp, 0.0.0.0:18003->18003/tcp, :::18003->18003/tcp   redis-8003
c34dc4c423ef   redis:5.0.7   "docker-entrypoint.s…"   2 minutes ago        Up 2 minutes        0.0.0.0:8002->8002/tcp, :::8002->8002/tcp, 6379/tcp, 0.0.0.0:18002->18002/tcp, :::18002->18002/tcp   redis-8002
b8cb5feffb43   redis:5.0.7   "docker-entrypoint.s…"   2 minutes ago        Up 2 minutes        0.0.0.0:8001->8001/tcp, :::8001->8001/tcp, 6379/tcp, 0.0.0.0:18001->18001/tcp, :::18001->18001/tcp   redis-8001
```



```powershell
登录redis容器
# docker exec -it redis-8001 bash
root@b8cb5feffb43:/data#
```





```powershell
创建redis-cluster
root@b8cb5feffb43:/data# redis-cli --cluster create 192.168.10.180:8001 192.168.10.180:8002 192.168.10.180:8003 192.168.10.180:8004 192.168.10.180:8005 192.168.10.180:8006 --cluster-replicas 1
```



```powershell
输出：
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 192.168.255.157:8005 to 192.168.255.157:8001
Adding replica 192.168.255.157:8006 to 192.168.255.157:8002
Adding replica 192.168.255.157:8004 to 192.168.255.157:8003
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: abd07f1a2679fe77558bad3ff4b7ab70ec41efa5 192.168.255.157:8001
   slots:[0-5460] (5461 slots) master
M: 40e69202bb3eab13a8157c33da6240bb31f2fd6f 192.168.255.157:8002
   slots:[5461-10922] (5462 slots) master
M: 9a927abf3c2982ba9ffdb29176fc8ffa77a2cf03 192.168.255.157:8003
   slots:[10923-16383] (5461 slots) master
S: 81d0a4056328830a555fcd75cf523d4c9d52205c 192.168.255.157:8004
   replicates 9a927abf3c2982ba9ffdb29176fc8ffa77a2cf03
S: 8121a28519e5b52e4817913aa3969d9431bb68af 192.168.255.157:8005
   replicates abd07f1a2679fe77558bad3ff4b7ab70ec41efa5
S: 3a8dd5343c0b8f5580bc44f6b3bb5b4371d4dde5 192.168.255.157:8006
   replicates 40e69202bb3eab13a8157c33da6240bb31f2fd6f
Can I set the above configuration? (type 'yes' to accept): yes 输入yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
.....
>>> Performing Cluster Check (using node 192.168.255.157:8001)
M: abd07f1a2679fe77558bad3ff4b7ab70ec41efa5 192.168.255.157:8001
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: 81d0a4056328830a555fcd75cf523d4c9d52205c 192.168.255.157:8004
   slots: (0 slots) slave
   replicates 9a927abf3c2982ba9ffdb29176fc8ffa77a2cf03
M: 40e69202bb3eab13a8157c33da6240bb31f2fd6f 192.168.255.157:8002
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: 8121a28519e5b52e4817913aa3969d9431bb68af 192.168.255.157:8005
   slots: (0 slots) slave
   replicates abd07f1a2679fe77558bad3ff4b7ab70ec41efa5
M: 9a927abf3c2982ba9ffdb29176fc8ffa77a2cf03 192.168.255.157:8003
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 3a8dd5343c0b8f5580bc44f6b3bb5b4371d4dde5 192.168.255.157:8006
   slots: (0 slots) slave
   replicates 40e69202bb3eab13a8157c33da6240bb31f2fd6f
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```





## 10.8 使用Docker容器实现RabbitMQ部署

### 10.8.1 获取参考资料

![image-20220213123228483](容器运行时 Docker.assets/image-20220213123228483.png?lastModify=1689308130)





![image-20220213123307806](容器运行时 Docker.assets/image-20220213123307806.png?lastModify=1689308130)





![image-20220213123355531](容器运行时 Docker.assets/image-20220213123355531.png?lastModify=1689308130)





![image-20220213123503083](容器运行时 Docker.assets/image-20220213123503083.png?lastModify=1689308130)



### 10.8.2 部署RabbitMQ

> 部署带管理控制台的RabbitMQ



```powershell
# docker run -d --name rabbitmq -p 5671:5671 -p 5672:5672 -p 4369:4369 -p 25672:25672 -p 15671:15671 -p 15672:15672 -v /opt/rabbitmq:/var/lib/rabbitmq rabbitmq:management
```



```powershell
# docker ps
CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS         PORTS                                                                                                                                                                                                                                             NAMES
97d28093faa4   rabbitmq:management   "docker-entrypoint.s…"   11 seconds ago   Up 6 seconds   0.0.0.0:4369->4369/tcp, :::4369->4369/tcp, 0.0.0.0:5671-5672->5671-5672/tcp, :::5671-5672->5671-5672/tcp, 0.0.0.0:15671-15672->15671-15672/tcp, :::15671-15672->15671-15672/tcp, 0.0.0.0:25672->25672/tcp, :::25672->25672/tcp, 15691-15692/tcp   rabbitmq
```





```powershell
端口说明：
4369, 25672 (Erlang发现&集群端口)
5672, 5671 (AMQP端口)
15672 (web管理后台端口)
61613, 61614 (STOMP协议端口)
1883, 8883 (MQTT协议端口)
15671(管理监听端口)
```



![image-20220213124157710](容器运行时 Docker.assets/image-20220213124157710.png?lastModify=1689308130)





![image-20220213124232819](容器运行时 Docker.assets/image-20220213124232819.png?lastModify=1689308130)



![image-20220213124302137](容器运行时 Docker.assets/image-20220213124302137.png?lastModify=1689308130)





