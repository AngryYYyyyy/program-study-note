# 金三银四-面试突击-Spring专题(上)

> lecture：波哥



IOC  DI  AOP 事务

# 一、谈谈你对Spring的理解

这个问题可以从多个角度来分析

https://www.processon.com/view/link/66128eec9212073d7f864f2d?cid=661233723f45151c41c284c3

## 1. Spring的发展历史

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/d4d2a8915be24615bcf7dadd11481c2a.png)

## 2. Spring的体系结构

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/dc2638c504aa4a14885b39544c44ed1f.png)


## 3. Spring相关组件

- 1. Spring和SpringMVC的关系
  2. Spring和SpringBoot的关系
  3. Spring和SpringCloud的关系
  4. Spring和SpringSecurity的关系
  5. .....


# 二、谈谈你对IoC的理解

&emsp;&emsp;Spring的IoC（Inversion of Control，控制反转）是一种设计模式，它的核心思想是将对象的创建、组装和管理过程交给框架来完成，而不是由应用程序直接控制。这种模式通过将应用程序的控制权交给框架来提高应用程序的可扩展性、灵活性和可维护性。

## 1. IoC的应用

1. Bean的定义：基于xml的方式，基于配置类的方式，@Component注解
2. Bean的发现：@Resources @Autowired 注解

## 2. IoC的原理

Bean 的管理： bean的定义  bean加载  bean 存储。bean的实例化

Bean的定义==》 BeanDefinition  ==》 BeanFactory【存储了所有的BeanDefinition】==》BeanDefinitionRegistry ==》 Bean实例有两种类型 单例，原型  单例==》容器初始化的时候==》完成对应的实例。单例Bean保存在一级缓存中。  原型Bean  在我们获取Bean的时候getBean()会完成对象的实例化。

对应的Bean的创建也是穿插在Spring容器的初始化过程中的


https://www.processon.com/view/link/6612404756d26b10e7b5fc84?cid=624802a95653bb072bd6da1e


![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/632f155a579d4e2aa5bcfe0964572520.png)



# 三、谈谈你对Bean的生命周期的理解

https://blog.csdn.net/qq_38526573/article/details/88143169

Servlet的生命周期

Fileter的生命周期

SpringBean对象：

对象的创建

对象的初始化

对象的使用

对象的销毁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/ec47b02465db4029b0615ea2fce2dacd.png)

具体流程的说明：

1. 如果实现了BeanFactoryPostProcessor接口，那么在容器启动的时候，该接口中的postProcessBeanFactory方法可以修改Bean中元数据中的信息。该方法是在实例化对象之前执行
2. 如果实现了InstantiationAwareBeanPostProcessor接口，那么在实例化Bean对象之前会调用**postProcessBeforeInstantiation**方法，该方法如果返回的不为null则会直接调用postProcessAfterInitialization方法，而跳过了Bean实例化后及初始化前的相关方法，如果返回null则正常流程，**postProcessAfterInstantiation**在实例化成功后执行，这个时候对象已经被实例化，但是该实例的属性还未被设置，都是null。因为它的返回值是决定要不要调用postProcessPropertyValues方法的其中一个因素（因为还有一个因素是mbd.getDependencyCheck()）；如果该方法返回false,并且不需要check，那么postProcessPropertyValues就会被忽略不执行；如果返回true, postProcessPropertyValues就会被执行,**postProcessPropertyValues**用来修改属性，在初始化方法之前执行。
3. 如果实现了Aware相关的结果，那么相关的set方法会在初始化之前执行。
4. 如果实现了BeanPostProcessor接口，那么该接口的方法会在实例化后的初始化方法前后执行。
5. 如果实现了InitializingBean接口则在初始化的时候执行afterPropertiesSet
6. 如果指定了init-method属性则在初始化的时候会执行指定的方法。
7. 如果指定了@PostConstruct则在初始化的时候会执行标注的方法。
8. 到此对象创建完成
9. 当对象需要销毁的时候。
10. 如果实现了DisposableBean接口会执行destroy方法
11. 如果指定了destroy-method属性则会执行指定的方法
12. 如果指定了@PreDestroy注解则会执行标注的方法


# 四、谈谈你对AOP的理解

## 1. AOP的概念

&emsp;&emsp;AOP（Aspect Oriented Programming）,即面向切面编程，可以说是OOP（Object Oriented Programming，面向对象编程）的补充和完善。面向切面是面向对象中的一种方式而已。在代码执行过程中，动态嵌入其他代码，叫做面向切面编程。

## 2. AOP的使用

https://blog.csdn.net/qq_38526573/article/details/86441916

## 3. AOP的原理

https://cloud.fynote.com/share/d/NqIAAHPPN

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/550da5063aa74c1eab4b41916f64c53a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/297bfd36d46943ccb3f896d72e7e54f9.png)



# 五、谈谈你对Spring事务管理的理解

单体的事务解决方案

分布式事务的解决方案

jdbc事务的概念

## 1. 事务的传播属性和隔离级别

https://blog.csdn.net/qq_38526573/article/details/87898161

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/f66aea624e824322b692a3e1cef01eef.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/676d8686cd6f42bcbf986a1193bae0c6.png)

## 2.事务原理分析

https://cloud.fynote.com/share/d/1qIUHPSAI


# 六、谈谈你对Spring循环依赖的理解

https://cloud.fynote.com/share/d/GieUHTv6

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1462/1712465875039/4df2247c869040c5a880750381bbb90e.png)

1。非Spring的场景下的循环依赖问题：提前暴露 Map 一级缓存

2。Spring场景下的循环依赖问题：AOP代理对象。 提前暴露 三级缓存   一级【Bean定义后生成的单例对象 IOC 实例化生成的所有的单例对象】 二级【半成品对象】 三级【Lambda表达式】

3。各种Bean的情况对循环依赖的不同支持情况
