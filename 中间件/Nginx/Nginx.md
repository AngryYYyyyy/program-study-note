# 一、Nginx概述

## 1. Web应用服务器

Web应用服务器（WAS）托管和运行Web应用程序，为应用程序提供运行环境及服务，如安全性、事务管理和负载平衡。它们处理动态内容和业务逻辑，而Web服务器则处理静态内容。常见的Web应用服务器包括：

- **Apache Tomcat**：Java Servlet容器，用于托管Java Servlets和JSPs。
- **JBoss/WildFly**：完全支持Java EE规范的开源Java EE应用服务器。
- **Jetty**：轻量级的Web服务器和Servlet容器，常用于嵌入式系统。
- **GlassFish**和**WebLogic**：Oracle支持的企业级Java EE应用服务器。
- **WebSphere**：IBM提供的企业级解决方案。

主要功能：

- **请求处理**：生成HTTP响应。
- **安全性**：包括认证、授权和加密。
- **事务管理**：确保数据一致性。
- **资源管理**：如数据库和线程池管理。
- **负载均衡**：优化资源使用，提高可用性。

## 2. Nginx

Nginx是一种高性能Web服务器，也可以作为反向代理服务器和负载均衡器。它以处理高并发、高性能和低内存消耗闻名。主要功能和特点包括：

- **反向代理和负载均衡**：将请求分发到后端服务器，提高系统可用性。
- **动静分离**：提高处理静态和动态内容的效率。
- **高并发处理**：采用异步非阻塞架构，有效管理并发连接。
- **安全性**：支持SSL/TLS加密、访问控制和DDoS防护。
- **模块化扩展性**：支持灵活的模块加载和卸载。

常见使用场景：

- **作为缓存**：处理静态资源请求。
- **API网关**：管理API请求，执行认证和限流。
- **负载均衡器**：在大型系统中分配流量。

## 3. 安装与部署

### （1）编译安装

**编译安装**允许自定义安装过程，选择特定的编译选项和模块。步骤如下：

1. **下载源代码**：
   从Nginx官方网站或代码仓库下载源代码：
   ```sh
   wget http://nginx.org/download/nginx-1.21.3.tar.gz
   tar -zxvf nginx-1.21.3.tar.gz
   cd nginx-1.21.3
   ```

2. **安装依赖**：
   在CentOS上安装编译所需的依赖包：
   ```sh
   sudo yum install gcc make
   sudo yum install pcre pcre-devel zlib zlib-devel openssl openssl-devel
   ```

3. **配置编译选项**：
   运行配置脚本，选择需要的编译选项和模块：
   ```sh
   ./configure --prefix=/usr/local/nginx --with-http_ssl_module
   ```

4. **编译和安装**：
   编译源代码并安装：
   ```sh
   make
   sudo make install
   ```

### （2）自动化安装

**自动化安装**利用配置管理工具和自动化脚本简化部署过程，适用于大规模部署。

示例（使用yum安装Nginx）：

1. **配置Nginx官方yum源**：
   在 `/etc/yum.repos.d/nginx.repo` 中添加如下内容：
   ```ini
   [nginx-stable]
   name=nginx stable repo
   baseurl=http://nginx.org/packages/centos/$releasever/$basearch/
   gpgcheck=1
   enabled=1
   gpgkey=https://nginx.org/keys/nginx_signing.key
   module_hotfixes=true
   ```

2. **安装Nginx**：
   使用yum安装Nginx：
   ```sh
   sudo yum install nginx -y
   ```

### （3）二进制安装

**二进制安装**使用预编译的二进制包，适合大多数用户，是最简单快速的方法。

1. **使用包管理器安装**：
   在CentOS上使用yum安装：
   ```sh
   sudo yum install nginx
   ```

2. **下载官方二进制包**：
   从Nginx官方网站下载并安装预编译的二进制包：
   ```sh
   wget http://nginx.org/download/nginx-1.21.3.tar.gz
   tar -zxvf nginx-1.21.3.tar.gz
   cd nginx-1.21.3
   sudo cp -r * /usr/local/nginx/
   ```

3. **启动和配置**：
   启动Nginx服务器并进行基本配置：
   ```sh
   sudo /usr/local/nginx/sbin/nginx
   ```

你的笔记已经很详细地说明了Nginx的关键文件结构和配置，这里我将进行些许整理以提高易读性和专业性。

## 4. 文件结构

### （1）Nginx 文件结构

### （2）文件说明

#### ① `/etc/nginx/`

这是Nginx的主配置目录，包含了多个重要的配置文件和目录：

- **`nginx.conf`**：主配置文件，控制了Nginx的大部分操作和设置。
  - 设置用户和工作进程
  - 定义日志记录方式和路径
  - 配置连接处理方式
  - 包含其他配置文件，以扩展和细化配置
- **`mime.types`**：定义了不同文件类型与其MIME类型的映射关系。
- **`conf.d/`**：目录用于存放单独的服务器配置文件，便于管理和自定义特定场景。

#### ② `/usr/sbin/nginx`

此路径包含Nginx的二进制执行文件，主要用于启动、停止、重启等操作。

#### ③ `/var/log/nginx/`

用于存储日志文件，有助于追踪运行时的行为和错误：

- **`access.log`**：记录所有请求的详细访问信息。
- **`error.log`**：记录运行时的错误和警告信息。

#### ④ `/var/www/html/`

默认的Web内容目录，通常用于存放静态网页文件。

- **`index.html`**：默认的主页文件。

#### ⑤ `/var/run/`

存放运行时生成的PID文件，标识Nginx进程：

- **`nginx.pid`**：包含Nginx主进程的进程ID。

### （3）详解配置文件

#### ① `nginx.conf`

这是Nginx的核心配置文件，包含了对全局设置、HTTP服务器、以及可能的邮件代理设置的定义。`nginx.conf`通常包括以下几个部分：

- **全局块**：设置全局级别的配置，如用户、工作进程数量等。
- **events块**：配置与工作进程相关的事件处理模型参数。
- **http块**：包含了所有与HTTP相关的配置，如服务器列表、路由、MIME类型定义、日志格式等。
  - **server块**：定义一个虚拟服务器。每个`server`块可以包含监听的端口和IP、服务器名和多个`location`块。
  - **location块**：基于请求的URI来处理请求，可以有多种匹配方式，决定请求如何响应。

#### ② `conf.d/`子配置文件

`conf.d/`目录通常用于存放具体的虚拟主机配置或特定应用的配置文件。这些配置文件会被主配置文件包含（include），从而实现配置的模块化和清晰管理。每个配置文件通常包含一个或多个`server`块，定义特定的服务或应用如何运行。

下面是一个Nginx的服务器配置示例，展示了如何设置监听端口、定义服务器名、配置根目录以及错误页面：

```nginx
server {
    listen       80;  # 监听80端口
    server_name  localhost;  # 服务器名称为localhost

    location / {
        root   /usr/share/nginx/html;  # 网站根目录
        index  index.html index.htm;  # 默认索引文件
    }

    error_page   500 502 503 504  /50x.html;  # 定义错误页面
    location = /50x.html {
        root   /usr/share/nginx/html;  # 错误页面的根目录
    }
}
```

这个配置文件清晰地展示了Nginx服务器的基本设置，包括端口、文档根目录以及错误处理。你可以根据实际需要调整或扩展这些设置。

## 5.常用命令

启动Nginx服务器。
```sh
sudo systemctl start nginx
```

停止Nginx服务器。
```sh
sudo systemctl stop nginx
```

重启Nginx服务器，适用于重大配置更改后。
```sh
sudo systemctl restart nginx
```

在不停止服务的情况下重新加载Nginx配置文件，适用于配置文件的修改。
```sh
sudo systemctl reload nginx
```

检查Nginx服务器的当前状态。
```sh
sudo systemctl status nginx
```

发送信号停止Nginx主进程。
```sh
sudo nginx -s stop
```

优雅地关闭Nginx服务。
```sh
sudo nginx -s quit
```

向Nginx主进程发送信号重新加载配置文件。
```sh
sudo nginx -s reload
```

## 6. 简单使用

### （1）配置Nginx

Nginx的主配置文件位于 `/etc/nginx/nginx.conf`。下面是一个简单的Nginx服务器配置示例，用于设置一个基本的Web服务器：

```nginx
server {
    listen 80;  # 监听80端口
    server_name localhost;  # 服务器名称

    # 主目录和索引文件设置
    location / {
        root /var/www/html;  # 网站根目录
        index index.html index.htm;  # 默认页面
    }

    # 定义404错误页面
    error_page 404 /404.html;
    location = /404.html {
        internal;
    }

    # 定义500系列错误页面
    error_page 500 502 503 504 /50x.html;
    location = /50x.html {
        root /var/www/html;
    }
}
```

#### 创建网站根目录和示例文件

设置服务器的根目录并创建一个简单的HTML文件：

```sh
sudo mkdir -p /var/www/html
echo "Hello, Nginx!" | sudo tee /var/www/html/index.html
```

### （2）启用站点并重启Nginx

应用配置更改并重启Nginx：

```sh
sudo systemctl restart nginx
```

### （3）访问网站

使用浏览器访问服务器地址（如`localhost`）。如果配置无误，应显示“Hello, Nginx!”消息。

### （4）日志文件

Nginx的访问和错误日志记录了重要的服务器运行信息：

- **access.log**：记录所有访问请求的详细信息。
- **error.log**：记录服务器运行中的错误信息。

## 7. Nginx原理

### （1）网站访问的方式

用户可以通过以下方式访问网站：

1. **IP地址**：直接通过IP地址访问，例如 `http://192.168.150.130`。
2. **IP地址 + 端口**：通过指定端口访问，例如 `http://192.168.150.130:8080`。
3. **域名**：通过域名访问，需要域名解析将其转换为IP地址，例如 `http://www.example.com`。
4. **域名 + 端口**：通过域名和指定端口访问，例如 `http://www.example.com:8080`。

### （2）访问流程

HTTP请求到达Nginx时，以下是请求处理流程：

1. **接收请求**：通过配置的监听端口接收HTTP请求。
2. **解析请求**：提取请求中的域名、端口、URI等信息。
3. **匹配服务器块**：根据请求的域名与配置中的`server_name`匹配，确定使用哪个服务器块。
   - 使用最具体匹配的`server_name`。
   - 若无匹配，则使用默认服务器块（`default_server`）。
4. **匹配位置块**：在服务器块中匹配请求的URI与`location`指令。
5. **处理请求**：根据`location`指令配置处理请求，可能包括：
   - 提供静态文件
   - 反向代理
   - FastCGI处理
   - 重定向
   - 访问控制
6. **调用内核**：执行操作，如读取文件或与后端通信。
7. **获取响应资源**：从文件系统或后端服务器获取数据。
8. **封装响应**：将数据封装成HTTP响应报文。
9. **返回响应**：发送响应报文给用户。

### （3）示例

这些示例展示了Nginx如何根据不同的请求来源处理和响应：

1. **访问 `http://192.168.150.130`**：
   - 默认服务器块处理，返回 `/var/www/default` 目录内容。

2. **访问 `http://www.site1.com`**：
   - 匹配 `server_name www.site1.com`，返回 `/var/www/site1` 目录内容。

3. **访问 `http://www.site2.com`**：
   - 匹配 `server_name www.site2.com`，返回 `/var/www/site2` 目录内容。

# 二、Nginx的进阶应用

## 1. ngx_http_access_module
**功能描述**：此模块通过客户端IP地址控制访问权限。
**案例**：
```nginx
server {
    listen 80;
    server_name example.com;
    location / {
        deny all;
        allow 192.168.1.1;
        allow 192.168.1.2;
    }
}
```

## 2. ngx_http_autoindex_module
**功能描述**：自动列出目录中的文件和子目录。
**案例**：

```nginx
server {
    listen 80;
    server_name example.com;
    location / {
        root /var/www/html;
        autoindex on;
    }
}
```

## 3. ngx_http_ssl_module
**功能描述**：提供SSL/TLS支持，加密HTTP连接。
**案例**：
```nginx
server {
    listen 443 ssl;
    server_name example.com;
    ssl_certificate /etc/nginx/ssl/example.com.crt;
    ssl_certificate_key /etc/nginx/ssl/example.com.key;
}
```

## 4. ngx_http_proxy_module
**功能描述**：使Nginx可以作为反向代理服务器。
**案例**：
```nginx
server {
    listen 80;
    server_name example.com;
    location / {
        proxy_pass http://backend_server;
    }
}
```

## 5. ngx_http_rewrite_module
**功能描述**：提供强大的URL重写引擎。
**案例**：
```nginx
server {
    listen 80;
    server_name example.com;
    rewrite ^(.*)$ http://www.example.com$1 permanent;
}
```

## 6. ngx_http_gzip_module
**功能描述**：对HTTP响应数据进行压缩。
**案例**：
```nginx
http {
    gzip on;
    gzip_types text/plain text/css application/json;
}
```

## 7. ngx_http_secure_link_module
**功能描述**：生成安全链接，保护静态资源。
**案例**：
```nginx
location /secure/ {
    secure_link $arg_st,$arg_e;
    secure_link_md5 "secret$secure_link_expires$uri";
    if ($secure_link = "") { return 403; }
}
```

## 8. ngx_http_lua_module
**功能描述**：在Nginx配置中嵌入Lua脚本。
**案例**：
```nginx
location /lua {
    content_by_lua_block { ngx.say("Hello, Lua!") }
}
```

## 9. ngx_http_geo_module
**功能描述**：根据客户端IP的地理位置信息进行请求处理。
**案例**：
```nginx
http {
    geo $geo {
        default unknown;
        127.0.0.1 localhost;
    }
    server {
        listen 80;
        server_name example.com;
        if ($geo = "local") {
            return 200 "Welcome, local user!";
        }
    }
}
```

## 10. ngx_http_limit_req_module
**功能描述**：限制客户端请求的速率。
**案例**：
```nginx
http {
    limit_req_zone $binary_remote_addr zone=one:10m rate=1r/s;
    server {
        listen 80;
        server_name example.com;
        location / {
            limit_req zone=one burst=5;
        }
    }
}
```

# 三、Nginx企业级应用

## 1. HTTPS安全认证

### （1）使用OpenSSL生成自签名证书

#### 创建证书存放目录

```bash
sudo mkdir -p /etc/nginx/ssl
```

#### 生成私钥

```bash
openssl genpkey -algorithm RSA -out /etc/nginx/ssl/private.key
```

#### 生成证书签名请求(CSR)

在生成CSR的过程中，你将需要填写一些身份验证信息，例如国家代码和组织名称。

```bash
openssl req -new -key /etc/nginx/ssl/private.key -out /etc/nginx/ssl/csr.pem
```

#### 使用私钥和CSR生成自签名证书

```bash
openssl x509 -req -days 365 -in /etc/nginx/ssl/csr.pem -signkey /etc/nginx/ssl/private.key -out /etc/nginx/ssl/certificate.crt
```

### （2）配置Nginx使用自签名证书

编辑Nginx配置文件，启用HTTPS并使用生成的证书：

```nginx
server {
    listen 80;
    server_name example.com www.example.com;
    return 301 https://$host$request_uri;  # 重定向HTTP到HTTPS
}

server {
    listen 443 ssl;
    server_name example.com www.example.com;

    ssl_certificate /etc/nginx/ssl/certificate.crt;
    ssl_certificate_key /etc/nginx/ssl/private.key;

    ssl_session_cache shared:SSL:10m;
    ssl_session_timeout 10m;

    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_prefer_server_ciphers on;
    ssl_ciphers "ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-CHACHA20-POLY1305:ECDHE-RSA-CHACHA20-POLY1305:DHE-RSA-AES128-GCM-SHA256:DHE-RSA-AES256-GCM-SHA384";

    location / {
        root /var/www/html;
        index index.html;
    }

    error_page 404 /404.html;
    location = /404.html {
        internal;
    }

    error_page 500 502 503 504 /50x.html;
    location = /50x.html {
        root /var/www/html;
    }

    add_header Strict-Transport-Security "max-age=31536000; includeSubDomains" always;
    ssl_stapling on;
    ssl_stapling_verify on;
    resolver 8.8.8.8 8.8.4.4 valid=300s;
    resolver_timeout 5s;
}
```

### （3）测试配置并重新加载Nginx

在应用更改前，检查Nginx配置文件是否有误：

```bash
sudo nginx -t
```

如果检测结果显示配置正确，可以重新加载Nginx来应用更改：

```bash
sudo systemctl reload nginx
```

这个整理后的过程更加简洁和逻辑清晰，确保了所有命令的正确执行和配置的准确性。如果还有任何问题或需要进一步的帮助，请随时告诉我！

## 2. Web防火墙

### （1）环境准备

#### 安装OpenResty

OpenResty 是一个基于 Nginx 与 LuaJIT 的全功能 Web 应用服务器。它集成了许多成熟的 Nginx 模块，非常适合用来构建动态网站和应用。

1. 添加OpenResty仓库：
   ```bash
   sudo yum install yum-utils
   sudo yum-config-manager --add-repo https://openresty.org/package/centos/openresty.repo
   ```

2. 安装OpenResty：
   ```bash
   sudo yum install openresty
   ```

#### 安装Redis

Redis 是一个开源的高性能键值数据库。它常用作数据缓存、消息队列等。

1. 安装Redis：
   ```bash
   sudo yum install redis
   ```

2. 启动Redis服务：
   ```bash
   sudo systemctl start redis
   ```

3. 设置Redis服务开机自启：
   ```bash
   sudo systemctl enable redis
   ```

### （2）配置Nginx与Lua脚本

创建一个Lua脚本文件`/etc/nginx/lua/web-firewall.lua`来处理所有逻辑：

```lua
lua复制代码local limit_traffic = require "resty.limit.req"
local redis = require "resty.redis"

local lim, err = limit_traffic.new("my_limit_req_store", 2, 10)
if not lim then
    ngx.log(ngx.ERR, "failed to instantiate a resty.limit.req object: ", err)
    return ngx.exit(500)
end

-- 判断IP是否在黑名单中
local function is_blacklisted(ip)
    local red = redis:new()
    red:set_timeout(1000)
    local ok, err = red:connect("127.0.0.1", 6379)
    if not ok then
        ngx.log(ngx.ERR, "failed to connect to Redis: ", err)
        return false
    end

    local is_blocked = red:sismember("blacklist", ip)
    if is_blocked == 1 then
        return true
    else
        return false
    end
end

local ip = ngx.var.remote_addr
if is_blacklisted(ip) then
    return ngx.exit(ngx.HTTP_FORBIDDEN)
end

-- 执行限流
local key = ip
local delay, err = lim:incoming(key, true)
if not delay then
    if err == "rejected" then
        return ngx.exit(503)
    end
    ngx.log(ngx.ERR, "failed to limit req: ", err)
    return ngx.exit(500)
end

if delay >= 0.001 then
    ngx.sleep(delay)
end
```

更新你的Nginx配置以使用这个Lua脚本：

```nginx
nginx复制代码http {
    lua_shared_dict my_limit_req_store 10m;
    server {
        listen 80;
        server_name example.com;

        location / {
            access_by_lua_file /etc/nginx/lua/web-firewall.lua;
        }
    }
}
```

### （3）测试配置并重启Nginx

在重启Nginx前，确保配置文件没有错误：

```bash
sudo /usr/local/openresty/nginx/sbin/nginx -t
```

如果配置正确，重启OpenResty服务以应用更改：

```bash
sudo systemctl restart openresty
```

## 3. 代理

### （1）正向代理
正向代理位于客户端和服务器之间，为客户端代理访问互联网上的服务器。客户端通过正向代理发送请求，代理服务器代表客户端向真实服务器发送请求并将结果返回给客户端。正向代理可以用于缓存、控制或记录Internet使用、访问受地理限制的资源等。

### （2）反向代理
反向代理位于客户端和服务器之间，但它代表服务器接受来自客户端的请求。客户端向反向代理发送请求，无需知道背后的服务器。这可以提高服务器的安全性、负载均衡和全局服务器负载分配。反向代理还可以用作SSL终端代理和HTTP缓存。

### （3）负载均衡

#### 负载均衡种类

四层负载均衡（传输层），基于IP地址和端口号，通常使用TCP或UDP协议。该方法较为基础，能够根据IP协议头信息快速分发流量。

七层负载均衡（应用层），基于HTTP头信息，如URL或主机头。它可以进行更智能的流量分发，比如分发到处理特定类型请求的服务器。

`ngx_stream_upstream_module` 使Nginx能够处理TCP/UDP流量，支持基于TCP和UDP的四层负载均衡。此模块提供了对上游服务器的健康检查、故障转移等高级功能。

#### 负载调度算法

- **轮询**（Round Robin）：按顺序将请求分配到不同的服务器上。
- **加权轮询**（Weighted Round Robin）：基于权重的轮询，允许某些服务器根据配置的权重接收更多请求。
- **ip_hash**：基于客户端IP地址的哈希结果将请求分配给特定服务器，常用于会话保持。
- **least_conn**：优先将请求分配给连接数最少的服务器，适用于处理时间不均的请求。
- **url_hash**：根据请求的URL进行哈希，将来自同一URL的请求分配到同一服务器，保持本地缓存最大化利用。









