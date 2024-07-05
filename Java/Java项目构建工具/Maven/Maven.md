# 一、简介

"Maven" 是一个流行的自动化构建工具，主要用于Java项目。它==基于项目对象模型（POM）概念==，Maven 可以管理项目的构建、报告和文档从一个中心片段信息(.xml配置信息文件)。

Maven 的主要优点和特性包括：

1. **项目模型**：通过其POM（项目对象模型）文件，Maven 描述了项目的构建、依赖和其它信息。
   
2. **依赖管理**：==Maven 自动处理项目依赖项==。它从中央仓库下载所需的库，并将其包含在项目构建中。
   
3. **标准化和约定**：Maven 鼓励采用“约定优于配置”的原则，这意味着如果遵循标准结构，那么需要的配置会很少。
   
4. **生命周期管理**：Maven 有一个定义明确的构建生命周期，包括编译、测试和打包等阶段。
   
5. **插件系统**：通过插件，Maven 可扩展以处理各种类型的项目和语言。
   
6. **项目构建和测试自动化**：它可以轻松集成到持续集成/持续部署的工作流中，如与Jenkins或Travis CI的集成。

7. **文档生成**：Maven 可以生成项目文档，包括Javadoc。

Maven 的使用有助于提高项目的构建效率，确保了构建的一致性，并促进了项目的标准化和质量控制。

# 二、创建Maven项目

## 1.配置Maven

![image-20231206151307108](D:\note\Java\SSM\image-20231206151307108.png)

## 2.配置setting.xml

### （1）本地仓库

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">
  <localRepository>E:\\data\\Maven_repository</localRepository>
</settings>    
```

### （2）镜像仓库

```xml
<mirrors>
    <mirror> 
                <!-- 指定镜像ID（可自己改名） -->
                <id>nexus-aliyun</id> 
                <!-- 匹配中央仓库（阿里云的仓库名称，不可以自己起名，必须这么写）-->
                <mirrorOf>central</mirrorOf>
                <!-- 指定镜像名称（可自己改名）  -->   
                <name>Nexus aliyun</name> 
                <!-- 指定镜像路径（镜像地址） -->
                <url>http://maven.aliyun.com/nexus/content/groups/public</url> 
        </mirror>
  </mirrors>
```

### （3）JDK

```xml
  <profiles>
	<!-- settings.xml中的id不能随便起的 -->
                <!-- 告诉maven我们用jdk1.8 -->
                <id>jdk-1.8</id>
                <!-- 开启JDK的使用 -->
                <activation>
                                <activeByDefault>true</activeByDefault>
                                <jdk>1.8</jdk>
                </activation>
                <properties>
                        <!-- 配置编译器信息 -->
                        <maven.compiler.source>1.8</maven.compiler.source>
                        <maven.compiler.target>1.8</maven.compiler.target>
                        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
                </properties>
  </profiles>
```



## 3.创建Maven项目

![image-20231206151648833](D:\note\Java\SSM\image-20231206151648833.png)

tip：GroupId、ArtifactId为坐标

## 4.项目结构

![image-20231206152015345](D:\note\Java\SSM\image-20231206152015345.png)

标准的Maven项目结构，如果以框架模型创建的话，需要补充。

![image-20231206151817333](D:\note\Java\SSM\image-20231206151817333.png)

web框架模型创建的Maven项目，补充java、test等文件。

![image-20231206152722343](D:\note\Java\SSM\image-20231206152722343.png)

# 三、项目结构

在使用Maven进行项目管理时，了解不同类型的项目结构（如POM工程、JAR工程和WAR工程）是非常重要的。这些工程类型决定了Maven如何构建和打包你的项目。下面是这三种工程类型的简要介绍：

### 1. POM（Project Object Model）工程
- POM工程主要是作为一个==项目聚合的方式==。它通常不包含源代码，而是定义了一组模块（子项目），使得这些模块可以被一起构建。
- 它的主要作用是通过一个单一的POM文件来管理多个模块或项目，便于项目的整体依赖管理和构建。
- POM工程的`packaging`类型在`pom.xml`中被设置为`pom`。

示例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.lxy</groupId>
    <artifactId>beacon-cloud</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.12.RELEASE</version>
        <relativePath />
    </parent>
    <properties>
        <spring.cloud-version>Hoxton.SR12</spring.cloud-version>
        <spring.cloud.alibaba-version>2.2.6.RELEASE</spring.cloud.alibaba-version>
    </properties>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud-version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring.cloud.alibaba-version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
```



### 2. JAR（Java Archive）工程
- JAR工程是最常见的Java项目类型之一，用于==生成Java库或应用程序==。
- 这种类型的项目包含Java类和其他资源（如图片、文本等），并将它们打包成`.jar`文件，这样可以轻松地被Java环境执行或作为库被其他项目引用。
- JAR工程的`packaging`类型在`pom.xml`中被设置为`jar`。

### 3. WAR（Web Application Archive）工程
- WAR工程用于==开发Java Web应用程序==。它包含Servlet、JSP、JavaScript、CSS、图像等Web资源。
- 与JAR相比，WAR包含了专门用于Web应用的结构和文件，如`WEB-INF`目录下的`web.xml`部署描述文件。
- 打包时，这些资源被打包成一个`.war`文件，可以被部署到任何Java EE兼容的Web服务器上（如Tomcat、Jetty等）。
- WAR工程的`packaging`类型在`pom.xml`中被设置为`war`。

