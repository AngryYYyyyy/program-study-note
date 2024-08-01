# 一、Spring

## 1.概述

Spring 是一个开源框架，由 Rod Johnson 于2003年首次发布，旨在解决企业应用开发的复杂性。Spring 使用“控制反转”（IoC）的方法来实现低耦合，通过“依赖注入”（DI）的方式来管理组件之间的依赖关系。它的核心特点包括：

1. **轻量级**：Spring 是一个轻量级的控制框架，相对于传统的重型企业应用框架，它不仅使用简单，而且基本的版本对系统资源的消耗相对较小。
2. **控制反转**：通过控制反转，Spring 把对象的创建和对象之间的调用过程交给框架控制，从而减少了组件间的耦合性。
3. **面向切面编程**（AOP）：Spring 支持面向切面编程，它允许开发者从业务逻辑中分离出一些服务功能，如事务管理、日志记录等。
4. **声明式事务管理**：Spring 支持程序的声明式事务管理，这使得事务管理更加简单，并且可以维持代码的清洁和简洁。
5. **灵活的整合能力**：Spring 能够与许多领先的企业应用技术无缝整合，包括 J2EE、Quartz、JDBC、Hibernate 等。

## 2.版本更替

自从2003年首次发布以来，经历了多次重要的版本迭代，每次迭代都在功能上做出了显著的扩展和改进。

1. **Spring 1.x**（2004年首发）：这是Spring框架的最初版本，提供了基础的依赖注入功能，支持XML配置文件。

2. **Spring 2.0**（2006年发布）：引入了基于注解的配置，简化了XML配置的复杂性。同时增加了对AspectJ的支持，增强了AOP（面向切面编程）功能。

3. **Spring 2.5**（2007年发布）：进一步加强了注解支持，可以完全不使用XML配置进行开发。

4. **Spring 3.0**（2009年发布）：
   - 支持Java 5+，使得框架利用了泛型和其他新特性。
   - 引入了Spring Expression Language（SpEL），一个强大的表达式语言，用于运行时查询和操作对象图。
   - 改进了Web模块，增加了REST风格的Web服务支持。

5. **Spring 3.1**（2011年发布）：
   - 引入了环境抽象，允许更容易地配置应用在不同环境下的行为。
   - 提供了声明式缓存支持。

6. **Spring 4.0**（2013年发布）：
   - 全面支持Java 8，利用了Java 8的Lambda表达式和其他特性。
   - 支持WebSocket通信。
   - 引入了更多的注解驱动的开发。

7. **Spring 5.0**（2017年发布）：
   - 增加了对响应式编程的支持，引入了Spring WebFlux模块。
   - 增强了对Kotlin的支持。
   - 升级了核心容器使之运行在Java 9+模块化系统上。

8. **Spring 5.1**（2018年发布）：增加了JDK 11的支持和许多性能改进。

9. **Spring 5.2**（2019年发布）：
   - 改进了核心容器的功能。
   - 引入了对RSocket的支持，这是一个新的网络协议，用于在微服务架构中建立持久连接。

10. **Spring 5.3**（2020年发布）：
    - 专注于改善核心容器的功能。
    - 增加了对GraalVM的支持，这是一个高性能JVM，支持多种编程语言。

11. **Spring 6.0**（2022年发布）：
    - 预计将完全支持Java 17的特性。
    - 将重点放在云原生构建和运行Spring应用上，支持更多现代化部署和开发模式。

随着时间的推移，Spring 框架已经从一个依赖注入容器发展成为全面的企业级应用开发框架，涵盖了从前端到后端的各种功能，如数据访问、事务管理、Web应用开发、安全控制等，非常适合构建复杂的企业级应用。

## 3.核心概念

Spring框架的核心在于提供了一个系统的方式来管理对象的创建和它们之间的依赖关系。这主要通过控制反转（IoC）、依赖注入（DI）和面向切面编程（AOP）实现，这些都是提高代码质量和项目维护性的关键技术。

### （1）**控制反转（IoC, Inversion of Control）**

IoC是一种设计原则，用于将对象的创建和管理交给外部容器，从而减轻了应用程序代码的负担。在Spring中，这通常是通过“容器”实现的，这个容器负责实例化、配置和组装对象。

### （2）**依赖注入（DI, Dependency Injection）**

DI是IoC的一种实现方式，它允许对象通过构造器参数、工厂方法的参数或在构造后通过属性直接设置来定义它们的依赖。

-  **构造器注入**：通过构造器传递依赖。
- **Setter注入**：通过Setter方法或其他属性设置方法传递依赖。

通过DI，对象的依赖关系变得更加明确，同时也更易于测试，因为可以在对象不知道Spring API的情况下进行替换。

### （3）**面向切面编程（AOP, Aspect-Oriented Programming）**

AOP是一种编程范式，它允许开发者将全局关注点（如事务管理、安全、日志等）从业务逻辑中分离出来，通过定义“切面”实现。

- **切点（Pointcut）**：定义了何处需要插入横切关注点的“点”。
- **通知（Advice）**：在切点处实际执行的操作。
- **连接点（Join point）**：在程序执行过程中插入切面的可能点（如方法调用）。

这种分离使得管理关注点变得更加集中和统一，也有助于维护和复用代码。

# 二、IOC

## 1.手写IOC

### （1）前置分析

在这里，我们来详细分析IoC（控制反转）容器的职责，它不仅负责创建和管理类实例，而且还向使用者提供这些实例。所谓的Bean，实际上就是类的对象，它们由IoC容器，也称为Bean工厂，创建并管理。

![image-20240801092117868](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408010921794.png)

首先，Bean工厂需要对外提供获取Bean实例的方法，因此必须定义一个`getBean()`方法。该方法接受Bean的名称或类型作为参数，返回相应的Bean实例，使用Object类型来适应不同返回类型的需要。

![](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408010918398.png)

接下来，我们需要定义如何告知Bean工厂所需创建的对象类型及其具体创建方式。这就引入了Bean的定义信息（BeanDefinition），它为Bean工厂提供了必要的元数据，如对象的构造方式，是否通过构造方法、静态方法或成员方法来实例化等。

创建对象可以通过三种方式

- 构造方法：Bean的类对象
- 静态方法：BeanFactory的类对象、BeanFactory方法
- 成员方法：BeanFactory类名、BeanFactory方法

这样一来我们就清楚了BeanDefinition应该要具有的基本功能了。

![image.png](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408010924169.png)

为了进一步丰富Bean工厂的功能，我们可以设置Bean实例为单例，并定义特定的初始化和销毁方法。

![image.png](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408010925316.png)

&emsp;&emsp;同时创建BeanDefinition的一个通用实现类：GenericBeanDefinition。

![image.png](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408010925731.png)

Bean的定义清楚后，我们要考虑的就是如何实现`BeanDefinition`和`BeanFactory`的关联了。实现一个`BeanDefinitionRegistry`来管理Bean定义的注册，确保每个Bean都有唯一的名称与其定义信息相对应。

![image-20240801092746637](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408010927648.png)

### （2）实现

#### BeanFactory

```java
/**
 * BeanFactory 接口定义了一个IoC容器的基本功能。
 * 该接口负责管理bean的获取，支持按名称、按类型等方式检索bean。
 */
public interface BeanFactory {

    /**
     * 根据bean的名称获取bean实例。
     * 
     * @param name bean的名称。
     * @return 返回bean的实例对象。
     * @throws Exception 如果bean无法创建或找不到。
     */
    Object getBean(String name) throws Exception;

    /**
     * 根据bean的类型获取bean实例。
     * 
     * @param type bean的Class类型。
     * @param <T>  bean的类型参数。
     * @return 返回指定类型的bean实例。
     * @throws Exception 如果bean无法创建或找不到。
     */
    <T> T getBean(Class<T> type) throws Exception;

    /**
     * 获取容器中所有指定类型的bean，返回一个Map，其中包含bean的名称和对应的bean实例。
     * 
     * @param type bean的Class类型。
     * @param <T>  bean的类型参数。
     * @return 返回一个Map，键是bean的名称，值是对应的bean实例。
     * @throws Exception 如果操作失败。
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws Exception;

    /**
     * 获取指定bean名称的类型。
     * 
     * @param name bean的名称。
     * @return 返回bean的Class类型。
     * @throws Exception 如果找不到指定的bean。
     */
    Class<?> getType(String name) throws Exception;
}

```

#### BeanDefinition

```java
/**
 * 该接口定义了Spring框架中的bean定义的标准结构。
 * 它包含了bean的类类型、作用域、生命周期方法以及工厂方法的信息。
 */
public interface BeanDefinition {

    // 定义单例作用域常量
    String SCOPE_SINGLETION = "singleton";
    // 定义原型作用域常量
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 获取bean的类类型。
     *
     * @return Class，表示bean的具体类类型。
     */
    Class<?> getBeanClass();

    /**
     * 获取bean的作用域。
     *
     * @return String，可能是“singleton”或“prototype”等。
     */
    String getScope();

    /**
     * 判断bean是否被定义为单例。
     *
     * @return boolean，如果bean是单例则返回true，否则返回false。
     */
    boolean isSingleton();

    /**
     * 判断bean是否被定义为原型（每次请求都创建新的实例）。
     *
     * @return boolean，如果bean是原型则返回true，否则返回false。
     */
    boolean isPrototype();

    /**
     * 获取工厂bean的名称，如果这个bean是由工厂bean产生的。
     *
     * @return String，工厂bean的名称。
     */
    String getFactoryBeanName();

    /**
     * 获取用于创建bean的工厂方法的名称。
     *
     * @return String，工厂方法的名称。
     */
    String getFactoryMethodName();

    /**
     * 获取bean的初始化方法名。
     *
     * @return String，初始化方法的名称。
     */
    String getInitMethodName();

    /**
     * 获取bean的销毁方法名。
     *
     * @return String，销毁方法的名称。
     */
    String getDestroyMethodName();

    /**
     * 判断该bean是否被标记为主要的bean。
     *
     * @return boolean，如果是主要的bean则返回true。
     */
    boolean isPrimary();

    /**
     * 校验bean定义的合法性。验证包括类的定义和工厂方法的指定。
     *
     * @return boolean，如果bean定义合法则返回true，不合法则返回false。
     */
    default boolean validate() {
        // 没定义class,工厂bean或工厂方法没指定，则不合法。
        if (this.getBeanClass() == null) {
            if (StringUtils.isBlank(getFactoryBeanName()) || StringUtils.isBlank(getFactoryMethodName())) {
                return false;
            }
        }

        // 定义了类，又定义工厂bean，不合法
        if (this.getBeanClass() != null && StringUtils.isNotBlank(getFactoryBeanName())) {
            return false;
        }

        return true;
    }
}

```

#### BeanDefinitionRegistry

```java
/**
 * BeanDefinitionRegistry 接口定义了注册和管理Bean定义的方法。
 * 该接口允许向注册表中添加新的bean定义，检索bean定义，以及检查bean定义是否存在。
 */
public interface BeanDefinitionRegistry {
    
    /**
     * 将一个新的bean定义注册到注册表中。
     * 如果注册表中已存在同名的bean定义，则可能会抛出异常。
     * 
     * @param beanName bean的名称，作为唯一标识。
     * @param beanDefinition 要注册的bean定义对象。
     * @throws BeanDefinitionRegistException 如果注册过程中出现错误，例如名称冲突。
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionRegistException;

    /**
     * 根据bean名称获取一个bean定义。
     * 如果名称对应的bean定义不存在，则返回null。
     * 
     * @param beanName 要检索的bean的名称。
     * @return BeanDefinition 如果找到对应的bean定义，则返回该定义；否则返回null。
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 检查注册表中是否已存在指定名称的bean定义。
     * 
     * @param beanName 要检查的bean的名称。
     * @return boolean 返回true，如果bean定义已存在；否则返回false。
     */
    boolean containsBeanDefinition(String beanName);
}

```

#### GenericBeanDefinition

```java
/**
 * GenericBeanDefinition 类实现了 BeanDefinition 接口，提供了 Bean 的详细定义。
 * 这个类包含了 Bean 实例化和管理所需的所有信息，如类类型、作用域、工厂方法等。
 */
public class GenericBeanDefinition implements BeanDefinition {

    private Class<?> beanClass;  // Bean或BeanFactory的类对象
    private String scope = SCOPE_SINGLETION;  // Bean的作用域，默认为单例
    private String factoryBeanName;  // 工厂Bean的名称，用于工厂方法实例化
    private String factoryMethodName;  // 工厂方法的名称，用于实例化Bean
    private String initMethodName;  // 初始化方法的名称，实例化后调用
    private String destroyMethodName;  // 销毁方法的名称，Bean销毁前调用
    private boolean primary;  // 标记该Bean是否为首选Bean

    /**
     * 设置Bean的类类型。
     * @param beanClass Bean的类对象。
     */
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 设置Bean的作用域。
     * @param scope 作用域的字符串表示，如 "singleton" 或 "prototype"。
     */
    public void setScope(String scope) {
        if (StringUtils.isNotBlank(scope)) {
            this.scope = scope;
        }
    }

    /**
     * 设置工厂Bean的名称，该名称用于从容器中获取工厂Bean，进而创建目标Bean。
     * @param factoryBeanName 工厂Bean的名称。
     */
    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    /**
     * 设置用于创建Bean的工厂方法名称。
     * @param factoryMethodName 工厂方法的名称。
     */
    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    /**
     * 设置Bean的初始化方法名称，该方法在Bean实例化后调用。
     * @param initMethodName 初始化方法的名称。
     */
    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    /**
     * 设置Bean的销毁方法名称，该方法在Bean销毁前调用。
     * @param destroyMethodName 销毁方法的名称。
     */
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public boolean isSingleton() {
        return SCOPE_SINGLETION.equals(this.scope);
    }

    @Override
    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(this.scope);
    }

    @Override
    public String getFactoryBeanName() {
        return this.factoryBeanName;
    }

    @Override
    public String getFactoryMethodName() {
        return this.factoryMethodName;
    }

    @Override
    public String getInitMethodName() {
        return this.initMethodName;
    }

    @Override
    public String getDestroyMethodName() {
        return this.destroyMethodName;
    }

    /**
     * 设置该Bean是否为首选Bean。
     * @param primary 首选标志，true表示这是首选Bean。
     */
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    @Override
    public boolean isPrimary() {
        return this.primary;
    }

    @Override
    public String toString() {
        return "GenericBeanDefinition [beanClass=" + beanClass + ", scope=" + scope + ", factoryBeanName="
                + factoryBeanName + ", factoryMethodName=" + factoryMethodName + ", initMethodName=" + initMethodName
                + ", destroyMethodName=" + destroyMethodName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((beanClass == null) ? 0 : beanClass.hashCode());
        result = prime * result + ((destroyMethodName == null) ? 0 : destroyMethodName.hashCode());
        result = prime * result + ((factoryBeanName == null) ? 0 : factoryBeanName.hashCode());
        result = prime * result + ((factoryMethodName == null) ? 0 : factoryMethodName.hashCode());
        result = prime * result + ((initMethodName == null) ? 0 : initMethodName.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GenericBeanDefinition other = (GenericBeanDefinition) obj;
        if (beanClass == null) {
            if (other.beanClass != null)
                return false;
        } else if (!beanClass.equals(other.beanClass))
            return false;
        if (destroyMethodName == null) {
            if (other.destroyMethodName != null)
                return false;
        } else if (!destroyMethodName.equals(other.destroyMethodName))
            return false;
        if (factoryBeanName == null) {
            if (other.factoryBeanName != null)
                return false;
        } else if (!factoryBeanName.equals(other.factoryBeanName))
            return false;
        if (factoryMethodName == null) {
            if (other.factoryMethodName != null)
                return false;
        } else if (!factoryMethodName.equals(other.factoryMethodName))
            return false;
        if (initMethodName == null) {
            if (other.initMethodName != null)
                return false;
        } else if (!initMethodName.equals(other.initMethodName))
            return false;
        if (scope == null) {
            if (other.scope != null)
                return false;
        } else if (!scope.equals(other.scope))
            return false;
        return true;
    }
}

```

#### DefaultBeanFactory

```java
/**
 * DefaultBeanFactory 类实现了 BeanFactory 和 BeanDefinitionRegistry 接口，
 * 提供了一个默认的bean工厂实现，用于注册、存储和创建bean。
 * 同时，该类支持 Closeable 接口，允许在关闭时清理资源。
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DefaultBeanFactory.class);

    // 存储Bean定义的并发安全的Map
    protected Map<String, BeanDefinition> beanDefintionMap = new ConcurrentHashMap<>(256);

    // 存储单例Bean实例的并发安全的Map
    private Map<String, Object> singletonBeanMap = new ConcurrentHashMap<>(256);

    // 存储Bean类型到Bean名称的映射，用于类型查找
    private Map<Class<?>, Set<String>> typeMap = new ConcurrentHashMap<>(256);

    /**
     * 注册一个Bean定义。
     *
     * @param beanName Bean的名称。
     * @param beanDefinition Bean的定义。
     * @throws BeanDefinitionRegistException 如果Bean定义不合法或已存在相同名称的定义。
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
        throws BeanDefinitionRegistException {
        Objects.requireNonNull(beanName, "注册bean需要给入beanName");
        Objects.requireNonNull(beanDefinition, "注册bean需要给入beanDefinition");

        if (!beanDefinition.validate()) {
            throw new BeanDefinitionRegistException("名字为[" + beanName + "] 的bean定义不合法：" + beanDefinition);
        }

        if (this.containsBeanDefinition(beanName)) {
            throw new BeanDefinitionRegistException(
                "名字为[" + beanName + "] 的bean定义已存在:" + this.getBeanDefinition(beanName));
        }

        this.beanDefintionMap.put(beanName, beanDefinition);
    }

    /**
     * 注册所有Bean定义到类型映射。
     *
     * @throws Exception 如果在映射过程中出现错误。
     */
    public void registerTypeMap() throws Exception {
        for (String name : this.beanDefintionMap.keySet()) {
            Class<?> type = this.getType(name);
            this.registerTypeMap(name, type);
            this.registerSuperClassTypeMap(name, type);
            this.registerInterfaceTypeMap(name, type);
        }
    }

    /**
     * 注册接口类型映射。
     * 该方法递归地为给定Bean的接口及其所有父接口建立映射，确保可以通过任何一个接口类型检索到Bean。
     *
     * @param name Bean的名称。
     * @param type Bean的实际类类型。
     */
    private void registerInterfaceTypeMap(String name, Class<?> type) {
        // 获取当前类实现的所有接口
        Class<?>[] interfaces = type.getInterfaces();
        // 如果存在接口，则进行遍历
        if (interfaces.length > 0) {
            for (Class<?> interf : interfaces) {
                // 为当前接口注册类型映射
                this.registerTypeMap(name, interf);
                // 递归注册该接口的父接口
                this.registerInterfaceTypeMap(name, interf);
            }
        }
    }

    /**
     * 注册超类类型映射。
     * 该方法递归地为给定Bean的超类及其接口建立映射，包括所有父类层次，直到Object类为止（不包括Object类）。
     *
     * @param name Bean的名称。
     * @param type Bean的实际类类型。
     */
    private void registerSuperClassTypeMap(String name, Class<?> type) {
        // 获取当前类的直接父类
        Class<?> superClass = type.getSuperclass();
        // 确保父类存在且不是Object类
        if (superClass != null && !superClass.equals(Object.class)) {
            // 为父类注册类型映射
            this.registerTypeMap(name, superClass);
            // 递归为父类的父类进行注册
            this.registerSuperClassTypeMap(name, superClass);
            // 为父类实现的接口进行注册
            this.registerInterfaceTypeMap(name, superClass);
        }
    }

    /**
     * 注册类型映射。
     * 该方法将Bean的名称与其类型关联，支持通过类型查找所有相关的Bean名称。
     *
     * @param name Bean的名称。
     * @param type Bean的类型或其父类型（接口、父类等）。
     */
    private void registerTypeMap(String name, Class<?> type) {
        // 从类型映射中获取当前类型的Bean名称集合
        Set<String> names2type = this.typeMap.get(type);
        // 如果该类型尚未映射任何Bean名称，则初始化名称集合
        if (names2type == null) {
            names2type = new HashSet<>();
            // 将新的名称集合与类型关联
            this.typeMap.put(type, names2type);
        }
        // 将当前Bean的名称添加到类型的名称集合中
        names2type.add(name);
    }


    /**
     * 获取指定名称的Bean的类型。
     *
     * @param name Bean的名称。
     * @return 返回Bean的Class类型。
     * @throws Exception 如果无法获取类型。
     */
    @Override
    public Class<?> getType(String name) throws Exception {
        BeanDefinition bd = this.getBeanDefinition(name);
        Class<?> type = bd.getBeanClass();
        if (type != null) {
            if (StringUtils.isBlank(bd.getFactoryMethodName())) {
                // 构造方法来构造对象的，type就是BeanClass
            } else {
                // 静态工厂方法，此时type是BeanFactoryClass，反射获得Method,再获取Method的返回值类型
                type = type.getDeclaredMethod(bd.getFactoryMethodName(),null).getReturnType();
            }
        } else {
            //成员工厂方法
            type = this.getType(bd.getFactoryBeanName());
            type = type.getDeclaredMethod(bd.getFactoryMethodName(),null).getReturnType();
        }

        return type;
    }

    /**
     * 获取指定名称的Bean定义。
     *
     * @param beanName Bean的名称。
     * @return 返回对应的Bean定义。
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefintionMap.get(beanName);
    }

    /**
     * 检查是否包含指定名称的Bean定义。
     *
     * @param beanName Bean的名称。
     * @return 如果包含返回true，否则返回false。
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefintionMap.containsKey(beanName);
    }

    /**
     * 根据Bean名称获取Bean实例。
     *
     * @param name Bean的名称。
     * @return 返回Bean的实例。
     * @throws Exception 如果创建Bean实例失败。
     */
    @Override
    public Object getBean(String name) throws Exception {
        return this.doGetBean(name);
    }

    /**
     * 根据Bean类型获取Bean实例，支持获取Primary Bean或处理多个同类型Bean的逻辑。
     *
     * @param type Bean的类型。
     * @param <T>  Bean的类型参数。
     * @return 返回Bean的实例。
     * @throws Exception 如果处理失败或找不到符合条件的Bean。
     */
    @Override
    public <T> T getBean(Class<T> type) throws Exception {
        Set<String> names = this.typeMap.get(type);
        if(names != null) {
            if(names.size() == 1){
                return (T)this.getBean(names.iterator().next());
            }
            else {
                //找Primary
                BeanDefinition bd = null;
                String primaryName = null;
                StringBuilder nameStrings = new StringBuilder();
                for(String name : names){
                    bd = this.getBeanDefinition(name);
                    if(bd != null && bd.isPrimary()) {
                        if(primaryName != null){
                            String mess = type + " 类型的Bean存储多个Primary[" + primaryName + "," + name + "]";
                            log.error(mess);
                            throw new Exception(mess);
                        }
                        else {
                            primaryName = name;
                        }
                    }
                    nameStrings.append(" " + name);
                }

                if(primaryName != null){
                    return (T)this.getBean(primaryName);
                }
                else {
                    String mess = type + " 类型的Bean存在多个[" + nameStrings + "] 但无法确定Primary";
                    log.error(mess);
                    throw new Exception(mess);
                }
            }
        }
        return null;
    }

    /**
     * 获取指定类型的所有Bean实例，返回一个包含Bean名称和实例的Map。
     *
     * @param type Bean的类型。
     * @param <T>  Bean的类型参数。
     * @return 返回一个Map，其中包含Bean名称和对应的Bean实例。
     * @throws Exception 如果处理失败。
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws Exception {
        Set<String> names = this.typeMap.get(type);
        if(names != null) {
            Map<String, T> map = new HashMap<>();
            for(String name : names){
                map.put(name,(T) this.getBean(name));
            }
            return map;
        }
        return null;
    }

    /**
     * 根据Bean的名称获取Bean实例，包括处理单例逻辑。
     * 如果Bean是单例，则首先从单例缓存中查找，如果不存在则创建。
     * 对于非单例Bean，每次请求都会创建一个新实例。
     *
     * @param beanName Bean的名称。
     * @return 返回请求的Bean实例。
     * @throws Exception 如果创建Bean实例失败。
     */
    protected Object doGetBean(String beanName) throws Exception {
        Objects.requireNonNull(beanName, "beanName不能为空");

        Object instance = singletonBeanMap.get(beanName);

        if (instance != null) {
            return instance;
        }

        BeanDefinition bd = this.getBeanDefinition(beanName);
        Objects.requireNonNull(bd, "beanDefinition不能为空");

        if (bd.isSingleton()) { 
            synchronized (this.singletonBeanMap) { 
                instance = this.singletonBeanMap.get(beanName);
                if (instance == null) {
                    instance = doCreateInstance(bd);
                    this.singletonBeanMap.put(beanName, instance);
                }
            }
        } else {
            instance = doCreateInstance(bd);
        }

        return instance;
    }

    /**
     * 创建Bean实例的具体方法，根据Bean定义中指定的创建方式执行。
     * 支持通过构造函数、静态工厂方法和工厂Bean方法创建Bean。
     *
     * @param bd Bean定义。
     * @return 创建的Bean实例。
     * @throws Exception 如果无法创建Bean。
     */
    private Object doCreateInstance(BeanDefinition bd) throws Exception {
        Class<?> type = bd.getBeanClass();
        Object instance;

        if (type != null) {
            if (StringUtils.isBlank(bd.getFactoryMethodName())) {
                instance = this.createInstanceByConstructor(bd);
            } else {
                instance = this.createInstanceByStaticFactoryMethod(bd);
            }
        } else {
            instance = this.createInstanceByFactoryBean(bd);
        }

        this.doInit(bd, instance);
        return instance;
    }

    /**
     * 通过调用无参构造函数创建Bean实例。
     *
     * @param bd Bean定义。
     * @return Bean实例。
     * @throws InstantiationException 如果无法实例化类。
     * @throws IllegalAccessException 如果构造函数无法访问。
     */
    private Object createInstanceByConstructor(BeanDefinition bd)
        throws InstantiationException, IllegalAccessException {
        try {
            return bd.getBeanClass().newInstance();
        } catch (SecurityException e) {
            log.error("创建bean的实例异常,beanDefinition：" + bd, e);
            throw e;
        }
    }

    /**
     * 通过静态工厂方法创建Bean实例。
     *
     * @param bd Bean定义。
     * @return 通过静态方法创建的Bean实例。
     * @throws Exception 如果静态方法调用失败。
     */
    private Object createInstanceByStaticFactoryMethod(BeanDefinition bd) throws Exception {
        Class<?> type = bd.getBeanClass();
        Method method = type.getMethod(bd.getFactoryMethodName(), null);
        return method.invoke(type, null);
    }

    /**
     * 通过工厂Bean的方法创建Bean实例。
     *
     * @param bd Bean定义。
     * @return 通过工厂Bean方法创建的Bean实例。
     * @throws Exception 如果方法调用失败。
     */
    private Object createInstanceByFactoryBean(BeanDefinition bd) throws Exception {
        Object factoryBean = this.doGetBean(bd.getFactoryBeanName());
        Method method = factoryBean.getClass().getMethod(bd.getFactoryMethodName(), null);
        return method.invoke(factoryBean, null);
    }

    /**
     * 执行Bean的初始化方法。
     *
     * @param bd Bean定义。
     * @param instance 刚创建的Bean实例。
     * @throws Exception 如果调用初始化方法失败。
     */
    private void doInit(BeanDefinition bd, Object instance) throws Exception {
        if (StringUtils.isNotBlank(bd.getInitMethodName())) {
            Method method = instance.getClass().getMethod(bd.getInitMethodName(), null);
            method.invoke(instance, null);
        }
    }

    /**
     * 关闭工厂时的清理逻辑，包括调用单例Bean的销毁方法。
     * 此方法确保所有单例Bean按照定义进行适当的资源释放。
     *
     * @throws IOException 如果销毁过程中出现IO异常。
     */
    @Override
    public void close() throws IOException {
        for (Entry<String, BeanDefinition> entry : this.beanDefintionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition bd = entry.getValue();

            if (bd.isSingleton() && StringUtils.isNotBlank(bd.getDestroyMethodName())) {
                Object instance = this.singletonBeanMap.get(beanName);
                try {
                    Method method = instance.getClass().getMethod(bd.getDestroyMethodName(), null);
                    method.invoke(instance, null);
                } catch (Exception e) {
                    log.error("执行bean[" + beanName + "] " + bd + " 的销毁方法异常！", e);
                }
            }
        }
    }
}

```

### （3）IOC增强

别名、类型

### （4）核心组件关系图

![image-20240801102105748](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408011021805.png)

# 三、DI

# 四、AOP