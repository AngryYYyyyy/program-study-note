# 一、What is a Container？

## 1.容器的定义

容器在计算机科学中通常指的是==一种封装和部署应用程序及其依赖的轻量级、可移植的、自包含的环境==。容器与虚拟机不同，它们不需要包括一个完整的操作系统，而是在现有的操作系统上运行，并利用该系统的内核。这样，容器可以迅速启动，占用的资源更少。

具体来说，容器通过提供一个隔离的环境，使得应用程序在任何支持容器的系统上都能以相同的方式运行。这解决了开发中常见的“在我的机器上可以运行”的问题，有助于开发、测试和生产环境之间的一致性。常见的容器技术包括Docker和Kubernetes等。

![image-20240609225956861](D:\note\容器管理\Docker\assets\image-20240609225956861.png)

## 2.容器与虚拟机对比

| 对比属性               | 容器（Container） | 虚拟机（VM）       |
| ---------------------- | ----------------- | ------------------ |
| 隔离性                 | 基于进程隔离      | 提供资源的完全隔离 |
| 启动时间               | 毫秒级或秒级      | 秒级或分钟级       |
| 内核                   | 共用宿主机内核    | 使用独立内核       |
| 占用资源               | MB级              | GB级               |
| 系统支持容量（同级别） | 支持上千个容器    | 几十台虚拟机       |

## 3.容器解决了什么问题

- 快速交付和部署应用 (镜像与容器)
- 资源的高效利用和隔离 (在物理机上实现高密度部署)
- 便捷迁移和扩缩容(一次构建，多处运行)

## 4.使用容器步骤

- 安装容器管理工具 
  - Docker   (Docker公司)
  - Containerd  (2017年docker捐给CNCF云原生计算基金会)
  - Pouch  (阿里云)

- 搜索/下载容器镜像(Image)
- 使用容器镜像生成容器
- 终端用户访问
- 迁移部署

# 二、容器技术的核心---两个Linux特性

## 1.NameSpace

命名空间提供了一种隔离技术，它可以==将系统资源在逻辑上隔离为独立的组，使得每个容器只能看到自己的一套虚拟化资源==，包括进程树、网络接口、用户 ID 和文件系统等。这样，容器内的进程就像是在自己独立的系统中运行一样，无法看到或影响其他容器的进程。

## 2.CGroup

控制组是一种 Linux 内核功能，用于==限制、记录和隔离进程组使用的物理资源==（如 CPU、内存、磁盘 I/O 和网络）。通过控制组，系统管理员可以精确控制每个容器可以使用多少资源，防止任何一个容器占用过多资源而影响系统整体性能。控制组的主要功能包括：

- **资源限制**：为容器设置资源使用上限，如内存、CPU 时间片等。
- **优先级分配**：在资源有限的情况下，控制不同容器的资源获取优先级。
- **资源监控**：监控容器使用的资源量，帮助理解资源使用情况和进行调优。
- **资源隔离**：保证容器之间的资源使用不会互相影响。

![image-20240609232306568](D:\note\容器管理\Docker\assets\image-20240609232306568.png)

在容器技术中，命名空间和控制组通常结合使用，以实现高效且安全的资源隔离和管理。==Docker 容器在创建时会为其分配一组特定的命名空间和控制组规则，从而确保容器内的应用只能访问指定的资源，并且其资源使用不会超过管理员设定的限制==。

# 三、容器管理工具 Docker生态架构及部署

## 1.Docker生态架构

![image-20240609233515247](D:\note\容器管理\Docker\assets\image-20240609233515247.png)

### (1) Docker Host
Docker Host 是安装了 Docker 守护进程（daemon）的物理或虚拟机。这台主机上可以基于容器镜像运行容器，为容器提供必要的运行环境。

### (2) Docker Daemon
Docker Daemon 是 Docker 架构中的核心组件，负责管理 Docker Host 上的容器、容器镜像、网络等资源。==这些容器实际上是由底层的容器运行时，如 Containerd.io，来提供运行支持的==。

### (3) Registry
Registry 是容器镜像仓库，用于存储和分享容器镜像。这些镜像是基于应用运行模板创建的，用户可以从 Registry 下载镜像来运行包含在其中的应用程序。典型的 Registry 示例包括 Docker Hub 和企业级的私有镜像仓库如 Harbor。

### (4) Docker Client
Docker Client 是 Docker Daemon 的客户端工具，允许用户与 Daemon 进行通信并执行各种操作。

### (5) Image
Image（镜像）是包含了应用运行所需环境及其依赖的静态文件。这些镜像文件是不可变的，并可用于在任何 Docker Host 上启动容器。镜像是构建容器的模板，确保了应用的一致性和可移植性。

### (6) Container
Container 是由镜像启动的实例，为应用程序提供实际运行的环境。容器包括镜像中的所有文件以及用户在容器运行过程中所添加的文件，属于镜像生成的可读写层，为应用程序的动态运行空间。

### (7) Docker Dashboard
Docker Dashboard 是一个图形界面工具，目前仅限于在 MacOS 和 Windows 操作系统上使用。它提供了一个用户友好的界面，允许用户管理和监控 Docker 容器、应用程序和镜像，使得用户无需依赖命令行界面（CLI）即可执行核心操作。

## 2.Docker部署

### （1）yam部署

获取yam源文件

```bash
在docker host上使用 wget下载到/etc/yum.repos.d目录中即可。
# wget -O /etc/yum.repos.d/docker-ce.repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

安装docker-ce

```bash
直接安装docker-ce，此为docker daemon，所有依赖将被yum自动安装，含docker client等。
# yum -y install docker-ce
```

配置Docker Daemon启动文件

> 由于Docker使用过程中会对Centos操作系统中的Iptables防火墙中的FORWARD链默认规划产生影响及需要让Docker Daemon接受用户自定义的daemon.json文件，需要要按使用者要求的方式修改。

~~~powershell
# vim /usr/lib/systemd/system/docker.service
~~~

<img src="D:\note\容器管理\Docker\assets\image-20240610001004383.png" alt="image-20240610001004383" style="zoom:50%;" />

启动Docker服务并查看已安装版本

~~~powershell
重启加载daemon文件
# systemctl daemon-reload

启动docker daemon
# systemctl start docker

设置开机自启动
# systemctl enable docker

使用docker version客户端命令查看已安装docker软件版本
# docker version
~~~

### （2）二进制文件部署



