# 2024金三银四面试突击课程

# 1.说一下 JVM 的主要组成部分及其作用？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1660195887078/70c416b3a15c4629886e45cfab0e87f6.png)

JVM包含两个子系统和两个组件，两个子系统为Class loader(类装载)、Execution engine(执行引擎)；两个组件为Runtime data area(运行时数据区)、Native Interface(本地接口)。

Class loader(类装载)：根据给定的全限定名类名(如：java.lang.Object)来装载class文件到Runtime data area中的method area。
Execution engine（执行引擎）：执行classes中的指令。
Native Interface(本地接口)：与native libraries交互，是其它编程语言交互的接口。
Runtime data area(运行时数据区域)：这就是我们常说的JVM的内存。
作用 ：首先通过编译器把 Java 代码转换成字节码，类加载器（ClassLoader）再把字节码加载到内存中，将其放在运行时数据区（Runtime data area）的方法区内，而字节码文件只是 JVM 的一套指令集规范，并不能直接交给底层操作系统去执行，因此需要特定的命令解析器执行引擎（Execution Engine），将字节码翻译成底层系统指令，再交由 CPU 去执行，而这个过程中需要调用其他语言的本地库接口（Native Interface）来实现整个程序的功能。

下面是Java程序运行机制详细说明

Java程序运行机制步骤

首先利用IDE集成开发工具编写Java源代码，源文件的后缀为.java；
再利用编译器(javac命令)将源代码编译成字节码文件，字节码文件的后缀名为.class；
运行字节码的工作是由解释器(java命令)来完成的。

从上图可以看，java文件通过编译器变成了.class文件，接下来类加载器又将这些.class文件加载到JVM中。
其实可以一句话来解释：类的加载指的是将类的.class文件中的二进制数据读入到内存中，将其放在运行时数据区的方法区内，然后在堆区创建一个 java.lang.Class对象，用来封装类在方法区内的数据结构。

# 2.说一下堆栈的区别？

**物理地址**

堆的物理地址分配对对象是不连续的。因此性能慢些。在GC的时候也要考虑到不连续的分配，所以有各种算法。比如，标记-消除，复制，标记-压缩，分代（即新生代使用复制算法，老年代使用标记——压缩）

栈使用的是数据结构中的栈，先进后出的原则，物理地址分配是连续的。所以性能快。

![16601958870783010274ffy](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1/4146672d2ea54edc9641413b6ee18e16.png)

**内存分别**

堆因为是不连续的，所以分配的内存是在运行期确认的，因此大小不固定。一般堆大小远远大于栈。

栈是连续的，所以分配的内存大小要在编译期就确认，大小是固定的。

**存放的内容**

堆存放的是对象的实例和数组。因此该区更关注的是数据的存储

栈存放：局部变量，操作数栈，返回结果。该区更关注的是程序方法的执行。

PS：

静态变量放在方法区
静态的对象还是放在堆。
**程序的可见度**

堆对于整个应用程序都是共享、可见的。

栈只对于线程是可见的。所以也是线程私有。他的生命周期和线程相同。

# 3.介绍一下强引用、软引用、弱引用、虚引用的区别？

> 1.**强引用**
>
> JVM内存管理器从根引用集合（Root Set）出发遍寻堆中所有到达对象的路径。当到达某对象的任意路径都不含有引用对象时，对这个对象的引用就被称为强引用
>
> 2.软引用
>
> 软引用是用来描述一些还有用但是非必须的对象。对于软引用关联的对象，在系统将于发生内存溢出异常之前，将会把这些对象列进回收范围中进行二次回收。
>
> （当你去处理占用内存较大的对象  并且生命周期比较长的，不是频繁使用的）
>
> 问题：软引用可能会降低应用的运行效率与性能。比如：软引用指向的对象如果初始化很耗时，或者这个对象在进行使用的时候被第三方施加了我们未知的操作。
>
> 用处： 软引用在实际中有重要的应用，例如浏览器的后退按钮。按后退时，这个后退时显示的网页内容是重新进行请求还是从缓存中取出呢？这就要看具体的实现策略了。
>
> （1）如果一个网页在浏览结束时就进行内容的回收，则按后退查看前面浏览过的页面时，需要重新构建
>
> （2）如果将浏览过的网页存储到内存中会造成内存的大量浪费，甚至会造成内存溢出
>
> 3.弱引用
>
> 弱引用（Weak Reference）对象与软引用对象的最大不同就在于：GC在进行回收时，需要通过算法检查是否回收软引用对象，而对于Weak引用对象， GC总是进行回收。因此Weak引用对象会更容易、更快被GC回收
>
> 4.虚引用
>
> 也叫幽灵引用和幻影引用，为一个对象设置虚引用关联的唯一目的就是能在这个对象被回收时收到一**个系统通知。也就是说,如果一个对象被设置上了一个虚引用,实际上跟没有设置引用没有**任何的区别
>
> 一般不用，辅助咱们的Finaliza函数的使用

# 4.JVM类加载机制的三种特性

* 全盘负责，当一个类加载器负责加载某个Class时，该Class所依赖的和引用的其他Class也将由该类加载器负责载入，除非显示使用另外一个类加载器来载入

> 例如，系统类加载器AppClassLoader加载入口类（含有main方法的类）时，会把main方法所依赖的类及引用的类也载入，依此类推。“全盘负责”机制也可称为当前类加载器负责机制。显然，入口类所依赖的类及引用的类的当前类加载器就是入口类的类加载器。
>
> 以上步骤只是调用了ClassLoader.loadClass(name)方法，并没有真正定义类。真正加载class字节码文件生成Class对象由“双亲委派”机制完成。

* 父类委托，“双亲委派”是指子类加载器如果没有加载过该目标类，就先委托父类加载器加载该目标类，只有在父类加载器找不到字节码文件的情况下才从自己的类路径中查找并装载目标类。

> 父类委托别名就叫双亲委派机制。
>
> “双亲委派”机制加载Class的具体过程是：
>
> 1. ClassLoader先判断该Class是否已加载，如果已加载，则返回Class对象；如果没有则委托给父类加载器。
> 2. 父类加载器判断是否加载过该Class，如果已加载，则返回Class对象；如果没有则委托给祖父类加载器。
> 3. 依此类推，直到始祖类加载器（引用类加载器）。
> 4. 始祖类加载器判断是否加载过该Class，如果已加载，则返回Class对象；如果没有则尝试从其对应的类路径下寻找class字节码文件并载入。如果载入成功，则返回Class对象；如果载入失败，则委托给始祖类加载器的子类加载器。
> 5. 始祖类加载器的子类加载器尝试从其对应的类路径下寻找class字节码文件并载入。如果载入成功，则返回Class对象；如果载入失败，则委托给始祖类加载器的孙类加载器。
> 6. 依此类推，直到源ClassLoader。
> 7. 源ClassLoader尝试从其对应的类路径下寻找class字节码文件并载入。如果载入成功，则返回Class对象；如果载入失败，源ClassLoader不会再委托其子类加载器，而是抛出异常。

> “双亲委派”机制只是Java推荐的机制，并不是强制的机制。
>
> 我们可以继承java.lang.ClassLoader类，实现自己的类加载器。如果想保持双亲委派模型，就应
>
> 该重写findClass(name)方法；如果想破坏双亲委派模型，可以重写loadClass(name)方法。
>
> ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/1463/1646137467048/000d19eeb6c64783875c14592668f223.png)

* 缓存机制，缓存机制将会保证所有加载过的Class都将在内存中缓存，当程序中需要使用某个Class时，类加载器先从内存的缓存区寻找该Class，只有缓存区不存在，系统才会读取该类对应的二进制数据，并将其转换成Class对象，存入缓存区。这就是为什么修改了Class后，必须重启JVM，程序的修改才会生效.对于一个类加载器实例来说，相同全名的类只加载一次，即 loadClass方法不会被重复调用。

> 而这里我们JDK8使用的是直接内存，所以我们会用到直接内存进行缓存。这也就是我们的类变量为什么只会被初始化一次的由来。

```java
   protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            // First，在虚拟机内存中查找是否已经加载过此类...类缓存的主要问题所在！！！
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
						//先让上一层加载器进行加载
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
					//调用此类加载器所实现的findClass方法进行加载
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
				//resolveClass方法是当字节码加载到内存后进行链接操作，对文件格式和字节码验证，并为 static 字段分配空间并初始化，符号引用转为直接引用，访问控制，方法覆盖等
                resolveClass(c);
            }
            return c;
        }
    }
```

# 5.常量池分类：

#### 1.静态常量池

静态常量池是相对于运行时常量池来说的，属于描述class文件结构的一部分

由**字面量**和**符号引用**组成，在类被加载后会将静态常量池加载到内存中也就是运行时常量池

**字面量** ：文本，字符串以及Final修饰的内容

**符号引用** ：类，接口，方法，字段等相关的描述信息。

直接引用：我的符号引用已经具体的落地到了内存，有了自己的地址

#### 2.运行时常量池

当静态常量池被加载到内存后就会变成运行时常量池。

> 也就是真正的把文件的内容落地到JVM内存了

#### 3.字符串常量池

**设计理念：**字符串作为最常用的数据类型，为减小内存的开销，专门为其开辟了一块内存区域（字符串常量池）用以存放。

JDK1.6及之前版本，字符串常量池是位于永久代（相当于现在的方法区）。

JDK1.7之后，字符串常量池位于Heap堆中

**面试常问点：（笔试居多）**

下列三种操作最多产生哪些对象

**1.直接赋值**

`String a ="aaaa";`

解析：

最多创建一个字符串对象。

首先“aaaa”会被认为字面量，先在字符串常量池中查找（.equals()）,如果没有找到，在堆中创建“aaaa”字符串对象，并且将“aaaa”的引用维护到字符串常量池中（实际是一个hashTable结构，存放key-value结构数据），再返回该引用；如果在字符串常量池中已经存在“aaaa”的引用，直接返回该引用。

**2.new String()**

`String  a  =new  String("aaaa");`

解析：

最多会创建两个对象。

首先“aaaa”会被认为字面量，先在字符串常量池中查找（.equals()）,如果没有找到，在堆中创建“aaaa”字符串对象，然后再在堆中创建一个“aaaa”对象，返回后面“aaaa”的引用；
**3.intern()**

```java
String s1 = new String("yzt");
String s2 = s1.intern();
System.out.println(s1 == s2); //false
```

解析：

String中的intern方法是一个 native 的方法，当调用 intern方法时，如果常量池已经包含一个等于此String对象的字符串（用equals(object)方法确定），则返回池中的字符串。否则，将intern返回的引用指向当前字符串 s1(jdk1.6版本需要将s1 复制到字符串常量池里)

常量池在内存中的布局：

![](file:///C:\Users\root\AppData\Local\Temp\ksohtml\wps3C44.tmp.jpg)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1648646330029/05f6a433465e4d77b67c5817e3ab0381.png)

# 6.访问对象有哪几种方式

句柄池访问：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1649922126094/d57189bb02aa4488809eb602b1562793.png)

直接指针访问对象图解:

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1649922126094/e7f4267aee394abf9d7d99c52c1ee150.png)

**区别:**

**句柄池:**

使用句柄访问对象，会在堆中开辟一块内存作为句柄池，句柄中储存了对象实例数据(属性值结构体) 的内存地址，访问类型数据的内存地址(类信息，方法类型信息)，对象实例数据一般也在heap中开 辟，类型数据一般储存在方法区中。

**优点** :reference存储的是稳定的句柄地址，在对象被移动(垃圾收集时移动对象是非常普遍的行为) 时只会改变句柄中的实例数据指针，而reference本身不需要改变。

**缺点** :增加了一次指针定位的时间开销。

**直接访问:**

直接指针访问方式指reference中直接储存对象在heap中的内存地址，但对应的类型数据访问地址需要 在实例中存储。

**优点** :节省了一次指针定位的开销。

**缺点** :在对象被移动时(如进行GC后的内存重新排列)，reference本身需要被修改

# 7.对象的生命周期可以描述一下吗

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1650279430028/01c7a2f4dd3a4593bca61e1c89fc5d4b.png)

**创建阶段**

（1）为对象分配存储空间

（2）开始构造对象

（3）从超类到子类对static成员进行初始化

（4）超类成员变量按顺序初始化，递归调用超类的构造方法

（5）子类成员变量按顺序初始化，子类构造方法调用，并且一旦对象被创建，并被分派给某些变量赋值，这个对象的状态就切换到了应用阶段

**应用阶段**

（1）系统至少维护着对象的一个强引用（Strong Reference）

（2）所有对该对象的引用全部是强引用（除非我们显式地使用了：软引用（Soft Reference）、弱引用（Weak Reference）或虚引用（Phantom Reference））

finalize方法代码Demo：

```java
public class Finalize {

    private static Finalize save_hook = null;//类变量

    public void isAlive() {
        System.out.println("我还活着");
    }

    @Override
    public void finalize() {
        System.out.println("finalize方法被执行");
        Finalize.save_hook = this;
    }

    public static void main(String[] args) throws InterruptedException {



        save_hook = new Finalize();//对象
        //对象第一次拯救自己
        save_hook = null;
        System.gc();
        //暂停0.5秒等待他
        Thread.sleep(500);
        if (save_hook != null) {
            save_hook.isAlive();
        } else {
            System.out.println("好了，现在我死了");
        }

        //对象第二次拯救自己
        save_hook = null;
        System.gc();
        //暂停0.5秒等待他
        Thread.sleep(500);
        if (save_hook != null) {
            save_hook.isAlive();
        } else {
            System.out.println("我终于死亡了");
        }
    }
}
```

**不可见阶段**

不可见阶段的对象在虚拟机的对象根引用集合中再也找不到直接或者间接的强引用，最常见的就是线程或者函数中的临时变量。程序不在持有对象的强引用。  （但是某些类的静态变量或者JNI是有可能持有的 ）

**不可达阶段**

指对象不再被任何强引用持有，GC发现该对象已经不可达。

# 8.你能聊一聊对象模型的对齐填充设计原因吗？

对齐填充的意义是 **提高CPU访问数据的效率** ，主要针对会存在**该实例对象数据跨内存地址区域存储**的情况。

例如：在没有对齐填充的情况下，内存地址存放情况如下:

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1649922126094/bc2b480aee764ef4b4157e1cfdc7dcea.png)

因为处理器只能0x00-0x07，0x08-0x0F这样读取数据，所以当我们想获取这个long型的数据时，处理 器必须要读两次内存，第一次(0x00-0x07)，第二次(0x08-0x0F)，然后将两次的结果才能获得真正的数值。

那么在有对齐填充的情况下，内存地址存放情况是这样的:

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1649922126094/2942966ff63844ecbeacb8433e57958e.png)

现在处理器只需要直接一次读取(0x08-0x0F)的内存地址就可以获得我们想要的数据了。

# 9.对象的垃圾回收流程

一般情况下，新创建的对象都会被分配到Eden区，一些特殊的大的对象会直接分配到Old区。

```
我是一个普通的Java对象,我出生在Eden区,在Eden区我还看到和我长的很像的小兄弟,我们在Eden区中玩了挺长时间。有一天Eden区中的人实在是太多了,我就被迫去了Survivor区的“From”区,自从去了Survivor区,我就开始漂了,有时候在Survivor的“From”区,有时候在Survivor的“To”区,居无定所。直到我18岁的时候,爸爸说我成人了,该去社会上闯闯了。于是我就去了年老代那边,年老代里,人很多,并且年龄都挺大的。
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1649922126094/54d6dce4c3634dc0b44a136f68479771.png)

# 10.为什么需要两个Survivor区？

最大的好处就是解决了碎片化。也就是说为什么一个Survivor区不行?第一部分中,我们知道了必须设置Survivor区。假设现在只有一个Survivor区,我们来模拟一下流程:
刚刚新建的对象在Eden中,一旦Eden满了,触发一次Minor GC,Eden中的存活对象就会被移动到Survivor区。这样继续循环下去,下一次Eden满了的时候,问题来了,此时进行Minor GC,Eden和Survivor各有一些存活对象,如果此时把Eden区的存活对象硬放到Survivor区,很明显这两部分对象所占有的内存是不连续的,也就导致了内存碎片化。
永远有一个Survivor space是空的,另一个非空的Survivor space无碎片。

# 11.堆内存中都是线程共享的区域吗？

JVM默认为每个线程在Eden上开辟一个buffer区域，用来加速对象的分配，称之为TLAB，全称:Thread Local Allocation Buffer。
对象优先会在TLAB上分配，但是TLAB空间通常会比较小，如果对象比较大，那么还是在共享区域分配。

# 12.方法区与元数据区以及持久代到底是什么关系

规范与实现        元数据区 元空间    MetaSpace       直接内存      持久代    永久代    permSpace    JVM的内存

# 13.栈帧结构是什么样子的？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1710224315093/ceaa3917938b4f48bb0e583c8169fb66.png)

# 14.栈帧的动态链接怎么去聊

动态链接是为了支持方法的动态调用过程 。

动态链接将这些符号方法引用转换为具体的方法引用       解析 （静态链接）    动态链接

符号引用转变为直接引用      为了支持java的多态

void    a(){

b();

}

void    b(){

c();

}

void    c(){

}
