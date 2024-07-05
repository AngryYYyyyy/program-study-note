# 学习目标

> 复习C语言IO相关操作
>
> 认识文件相关系统调用接口
>
> 认识文件描述符,理解重定向
>
> 对比fd和FILE，理解系统调用和库函数的关系
>
> 理解文件系统中inode的概念
>
> 认识软硬连接，对比区别
>
> 认识动态静态库，学会结合gcc选项，制作动静态库

# 文件回顾

### 1、文件概念

> ==文件是计算机中存储数据的一种抽象概念==。在计算机系统中，文件是由一系列的字节（byte）组成的有序集合。文件可以包含文本、图像、音频、视频等各种形式的数据。
>
> 在 Linux 系统中，==一切皆为文件==。这是 UNIX 和 Linux 中的重要设计理念。这种设计理念的含义是，系统中的每一项硬件设备如打印机、鼠标、键盘、磁盘驱动器、网络接口等，以及所有数据都被系统视为文件。
>
> ==文件由内容和属性两部分组成，这两者都是数据==，并不完全分开，例如修改内容的同时也改变了文件大小属性。打开文件（内存文件）意味着文件的内容或属性加载到内存，以便进行访问操作，当文件未被打开时（磁盘文件），它的内容和属性一般存在于磁盘上。

### 2、c语言文件操作

#### 1）fopen

> 此函数用于打开文件。

```c
#incldue <stdio.h> 
FILE *fopen(const char *path, const char *mode)
```

- **path**

> 文件路径名，可以是相对路径或绝对路径的字符串，默认在当前路径下。
>
> 当前路径是进程所在的路径。

- **mode**

> r：只读
>
> w：Truncate  file  to zero length or create text file for  writing.（Empty the file）
>
> w+/r+/a+:读写
>
> a（append）：打开以追加在文件末尾写入，如果文件不存在，则创建该文件。可以对应后面即将学习的追加重定向
>
> 没有rw，mode标志位只有一个

- **FILE**

> FILE是一个结构体类型，封装了文件操作所需的些重要信息，比如文件描述符等。用于实现C库函数如fopen, fclose, fread, fwrite等操作。
>
> 成功完成 fopen（） 后，返回一个 FILE 指针。 否则，返回NULL ，并且 errno 设置为指示错误。

#### 2）fclose

> 此函数用于关闭所打开的文件

```c
int fclose(FILE *fp);
```

#### 3）fread

> 此函数 从流指向的流中读取数据的 nmemb 个元素，每个size字节长，将它们存储在 ptr 给出的位置。

```c
size_t fread(void *ptr, size_t size, size_t nmemb, FILE *stream);
```

> `ptr`：指向要写入的数据块的指针。
>
> `size`：每个数据项的大小（以字节为单位）。
>
> `nmemb`：要写入的数据的数量。
>
> `stream`：要写入数据的文件指针。
>
> 返回值为成功读取的数据的数量，如果发生错误则返回0。

#### 4）fwrite

> 此函数将数据的 nmemb 个元素，每个size字节长，写入流指向的流，从 ptr 给出的位置获取它们

```c
size_t fwrite(const void *ptr, size_t size, size_t nmemb,FILE *stream);
```

#### 5）fseek

> 用于设置文件指针的位置

```c
int fseek(FILE *stream, long offset, int whence);
```

> offset：偏移量，表示要移动的字节数。
>
> whence：参考位置，可以是以下值之一：
>
> - SEEK_SET：从文件开头开始计算偏移量。
> - SEEK_CUR：从当前位置开始计算偏移量。
> - SEEK_END：从文件末尾开始计算偏移量。
>
> fseek函数的返回值为0表示操作成功，非零值表示出现了错误。

**示例**

```c
#include <stdio.h>
#include <string.h>
int main()
{
  FILE *fp=fopen("bit","w+");//以读写形式打开，没有bit文件则创建
  if(fp==NULL)
  {
    perror("fopen");//打开失败
    return 1;
  }
  const char *buffer="linux so easy\n";
  size_t ret1=fwrite(buffer,sizeof(char),strlen(buffer),fp);//向bit输入buffer内容
  if(ret1!=strlen(buffer))
  {
    perror("fwrite");//写入失败
    return 1;
  }
  fseek(fp,0,SEEK_SET);//写入后若想直接读取，需要将文件指针设为开头，或者关闭重新打开文件
  char read_buffer[128];
  size_t ret2=fread(read_buffer,sizeof(char),sizeof(read_buffer),fp);//从bit读取内容
  if(ret2>0)
  {
    fprintf(stdout,"%s",read_buffer);//将读取的内容打印到显示器
  }
  fclose(fp);
  return 0;
}

```

> 标准库的输入/输出函数（如`fread()`和`fwrite()`）通常会在需要的时候刷新缓冲区。

#### 6）==fgets、fputs==

> fgets函数用于从文件中读取一行数据，并将其存储到指定的字符串缓冲区中。
>
> fputs函数用于向文件中写入一行。

```c
char *fgets(char *s, int size, FILE *stream);//失败返回NULL
int fputs(const char *s, FILE *stream);
```

#### 7）fprintf、fscanf

```c
int fprintf(FILE *stream, const char *format, ...);
int fscanf(FILE *stream, const char *format, ...);
```

**示例：**

模拟实现cat

```c
#include <stdio.h>
int main(int argc,char *argv[]) //命令行参数
{
    if(argc!=2)//命令使用错误
    {
        printf("Usage: %s :filename\n",argv[0]);//使用提示
        return 1;
    }
    FILE *pf=fopen(argv[1],"r");//只读打开
    if(pf==NULL)
    {
        perror("fopen");//错误信息
        return 1;
    }
    char buffer[64];//缓冲区
    while(fgets(buffer,sizeof(buffer),fp)!=NULL)//在fp流中读取一行数据
    {
        fprintf(stdout,"%s",buffer);//打印一行数据
    }
    return 0;
}
```

### 3、理解系统调用的封装

> 文件的操作实则是由操作系统管理的，那么必然少不了操作系统提供的相关系统调用
>
> 其实上述函数的调用，底层均是对系统调用的封装
>
> 封装可以使原生系统接口的使用成本降低，并且使其可以具有跨平台性，c语言中对于封装的解决方案是穷举和条件编译，其他语言一般会通过多态来实现。
>
> 学习系统调用，可以更容易理解其他语言对系统调用封装的函数

# 操作系统的文件管理

### 1、文件的系统调用

#### 1）open

```c
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
int open(const char *pathname, int flags, mode_t mode);
```

> - `pathname`：文件路径名，可以是相对路径或绝对路径的字符串，默认在当前路径下。
>
> - `flags`：文件打开标志位，用于指定打开文件的方式和权限，可以通过按位或操作来组合不同的选项，==一个比特位代表了一个状态==，注意不能重叠。常见的标志包括`O_RDONLY`（只读）、`O_WRONLY`（只写）、`O_RDWR`（读写）、`O_CREAT`（创建文件）、`O_TRUNC`（truncate）等。这些选项可以通过按位或操作（`|`）进行组合，实现传递多个标记位，然后通过与判断可以执行不同的命令。
>
>   在后续学习中，标记位的传递要学会使用**位图**的数据结构
>
> - `mode`：文件权限，==**用于指定新创建文件的访问权限**==。通常以八进制表示，例如`0644`表示文件所有者有读写权限，其他用户只有读权限。受到umask约束
>
> - 返回值：成功打开文件时返回==**文件描述符fd**==（非负整数），失败时返回-1。

#### 2）close

```c
int close(int fd);
```

#### 3）write

```c
ssize_t write(int fd, const void *buf, size_t count);
```

> `ssize_t`是一个有符号整数类型，在C语言中表示有符号的大小或者长度。它通常用于表示==函数返回的字节数或者字符数==。

**示例：**

```c
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <unistd.h>
int main() 
{
    int fd=open("file.txt",O_WRONLY|O_CREAT,0666);//多标志位传递，若没有该文件则创建文件
    if(fd==-1)//open失败
    {
      perror("open");
      return 1;
    }
    int cnt=0;
    char *buffer="hello bit!!\n";//后面带有'\0'
    while(cnt<5)//简单的讲buffer内容写入file.txt
    {
      write(fd,buffer,strlen(buffer));//注意不需要考虑c语言层面默认加的'\0'
      cnt++;
    }
    return 0;
}
```

> 此时flags文件打开标志位并没有`O_TRUNC`，因此如果改变buffer，file.txt内容将会被覆盖，由此可以得出，c语言的fopen函数的“w”方式，底层由 `O_CREAT|O_WRONLY|O_TRUNC`来封装,同样”a“的底层是``O_CREAT|O_WRONLY|O_APPEND`。

#### 4）read

```c
ssize_t read(int fd, void *buf, size_t count);
```

#### 5）lseek

`lseek`函数用于改变打开的文件描述符的读写位置。它允许你在文件内移动读写指针到指定的位置，使得后续的读写操作可以从这个新位置开始。通过这种方式，`lseek`提供了一种机制来读写文件中任意位置的数据，而不仅仅是按顺序从头到尾进行。它在处理大文件、实现文件随机访问等场合非常有用。

```c
off_t lseek(int fd, off_t offset, int whence);
```

> - fd：文件描述符，它是一个打开的文件或设备的抽象表示。
> - offset：偏移量，表示从参照位置移动的字节数。
> - whence：参照位置，它有三种可能的值：SEEK_SET, SEEK_CUR, SEEK_END。
>
> 返回值：成功时返回新的文件偏移量，失败时返回-1并设置errno。

**示例：**

```c
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
int main()
{
    int fd=open("file.txt",O_RDONLY,0666);//只读，不要加O_TRUNC,否则打开的时候会被清空，也就读不到内容
    if(fd==-1)
    {
        perror("open");
        return 1;
    }
    char buffer[128];
    //大多数C标准库中处理字符串的函数（如printf、strcpy、strlen等）都依赖于这个空字符来判断字符串的结束位置。
    ssize_t ret=read(fd,buffer,sizeof(buffer)-1);
    if(ret>0)
    {
        buffer[ret]='\0';
        printf("%s",buffer);
    }
    close(fd);
    return 0;
}
```

> 从上面系统调用的使用方法和声明，我们可以得出结论，C库的函数调用的底层是系统调用，是系统调用函数的封装，那么fopen里必然封装了open，即fopen含有文件描述符的数据，由结构体FILE来封装，这也解释了C库文件操作函数通过FILE找寻文件，而系统调用通过文件描述符fd找寻文件。

### 2、文件描述符

> 文件描述符（File Descriptor）是一个用于==**抽象表示和识别**文件、管道、网络套接字等I/O流对象的**非负整数**==。
>

#### 1）理解一切皆文件

> 在类Unix风格的系统中，一切皆文件（everything is a file）的设计哲学使得多种类型的I/O设备都可以通过统一的文件描述符接口来进行操作。例如，文件、目录、设备（如键盘、鼠标、显示器、硬盘等）、网络连接、管道等都可以通过文件描述符进行识别和操作。
>
> 当我们说"一切皆文件"时，并不是说所有的东西都存储在文件系统中，而是说，==所有的这些资源都可以通过文件系统（VFS，虚拟文件系统）的统一的接口来进行访问和管理（通过多一层软件层的封装，封装具体资源的使用方式的差异）==。这就意味着，开发者可以使用相同的系统调用（如open, read, write, close等）来操作所有类型的资源，而无需为不同类型的资源学习和使用一套不同的接口。
>
> - **优点：**
>
> 1. 一致性：由于所有的资源都可以用同样的方式来访问，因此开发者只需要学习一套接口，就可以操作各种资源。
> 2. 简洁性：由于只需要一套接口，所以系统的设计和实现都更加简洁。
> 3. 易于扩展：当需要添加新的资源类型时，只需要实现相应的文件操作接口，就可以很容易地将新的资源类型加入到系统中。
>
> - **缺点：**
>
> 1. 过度抽象：有些资源的特性可能无法通过文件接口完全表示出来，因此需要使用特殊的系统调用来访问这些特性。
> 2. 性能问题：由于所有的资源都通过文件接口来访问，因此可能会引入额外的开销，影响性能。

#### 2）分配规则

> 当一个进程打开或者创建一个文件时，操作系统会给这个流分配一个文件描述符，通常情况下，系统会选择当前进程的==文件描述表未被使用且数值最小的整数作为新的文件描述符==。
>
> **默认分配：**
>
> - 在Linux中，每个进程默认会有三个预定义的文件描述符：==0、1、2，分别代表标准输入（键盘）、标准输出（显示器）和标准错误（显示器）。==
>
> 在C语言层面上也有代表标准输入、标准输出和标准错误的指向，分别是FILE *stdin，FILE *stdout，FILE *stderr，既然文件操作只能通过文件描述符来访问，那么FILE作为一个结构，内部必然封装了文件描述符。

```c
int main()
{
  char buffer[128];
    ssize_t ret=read(0,buffer,sizeof(buffer)-1);//从标准输入读取
    if(ret>0)
    {
      buffer[ret]='\0';
      printf("%s",buffer);
    }
    const char* buffer1="hello world!!!\n";
    write(1,buffer1,strlen(buffer1));//写入标准输出
    write(2,buffer1,strlen(buffer1));//写入标准错误
    printf("%d\n",stdin->_fileno);
    printf("%d\n",stdout->_fileno);
    printf("%d\n",stderr->_fileno);
    return 0;
}
```

> 上述证明了Linux操作系统下默认的文件标识符，可以在不打开的情况下支持读写操作，而且FILE结构体内确实存在_fileno文件标识符。

### 3、文件表项（System-wide File Table Entry）

> 在Linux操作系统中，当一个文件被打开时，会在内核中为该文件创建一个文件表项，文件表项组成文件表。文件表是一种数据结构，它==用于存储和管理已打开文件的信息==，归属于系统，而不是某个进程。
>
> 文件表中的每一项（文件表项）包含了许多关于该打开文件的信息，比如：
>
> 1. 文件状态标志：这些标志记录了一些关于文件如何被访问和使用的信息，例如，文件是否可读，可写，是不是一个目录等。
> 2. 文件偏移量（文件位置指针）：这是一个记录了读/写操作发生在文件的哪个位置的值。比如，如果你读了前100个字节，文件偏移量就会被设置为100，下一次读操作就会从第101个字节开始。
> 3. 文件的i节点指针：这是一个指向文件i节点的指针。i节点是在磁盘上存储，包含了文件的元数据，比如文件的权限，所有者，文件大小，文件的数据块在磁盘上的位置等。
>
> 当进程打开一个文件时，系统会在文件表中为该文件创建一个新的文件表项，并返回一个文件描述符。文件描述符其实就是文件表中的一个索引，它指向了该文件对应的文件表项。

### 4、进程访问文件的过程

#### 1）进程与文件内核结构的关系

> Linux的操作系统中，每个进程都有一个文件描述符表，==文件描述符表通常是一个数组或者类似数组的数据结构，存储的是文件表项==。这个表是操作系统内核为每个进程维护的，用来映射该进程打开的所有文件和其他I/O流。而==文件描述符则是这个数组结构的下标==。
>
> 进程通过文件描述符与文件的内核结构（文件表）建立了连接。
>
> 值得注意的是，每个进程都有自己的文件描述符表，文件描述符在各个进程之间是隔离的。例如，进程A的文件描述符3和进程B的文件描述符3可能对应的是不同的文件或者I/O流。

![image-20230518200300818](D:\笔记\C++\linux系统编程网络编程\image-20230518200300818.png)

> file*可以类似一个智能指针，通过引用计数的方案来管理

#### 2）访问文件的过程

> 当一个进程打开或者创建一个文件流，操作系统会为这个流在文件描述表中分配一个文件描述符，并在文件表中创建文件表项，建立文件描述符与相应的文件（文件表项）的映射关系。
>
> 当进程要对一个文件进行读写操作时，它只需要给操作系统提供相应的文件描述符，然后根据文件描述符在文件描述符表中查找到对应的文件，即通过数组的索引指向对应文件指针或者引用，然后对这个文件进行操作。
>
> 另外，虽然文件描述符是全局唯一的，但是文件描述符表是每个进程独立的，也就是说，不同的进程可以有相同的文件描述符，但是这些文件描述符在不同的进程中可能对应到不同的文件。



### 5、理解重定向

> 当我们在 shell 中使用重定向操作符（如 `>` 或 `>>`）时，==实际上是在修改这些文件描述符的指向==。比如，当我们执行 `command > file` 时，shell 会创建或打开 `file`，然后将文件描述符 1（标准输出）指向该文件，这样 `command` 的所有标准输出就会写入 `file`。
>
> 重定向是一种改变程序输入和输出流向的机制。常见的有：
>
> - **`command > file`**：将`command`的标准输出（STDOUT，文件描述符为1）重定向到`file`，如果`file`已存在，则覆盖它。
> - **`command 2> file`**：将`command`的标准错误输出（STDERR，文件描述符为2）重定向到`file`，如果`file`已存在，则覆盖它。
> - **`command > file 2>&1`**：首先，`command > file`将标准输出重定向到`file`，然后`2>&1`将标准错误输出重定向到当前标准输出的位置，即也是`file`。结果是`command`的所有输出（标准输出和标准错误输出）都会被写入到`file`。
> - ==**`command &> file`**==：这是`command > file 2>&1`的简写形式，作用相同，也是将所有输出重定向到`file`。
>
> 重定向不仅限于文件，也可以将输出重定向到其他进程。例如，管道操作符 `|` 可以将一个进程的标准输出连接到另一个进程的标准输入。
>
> 例如，`ls | grep "txt"`命令会列出当前目录下的所有文件和目录，然后`grep "txt"`命令从这个列表中筛选出包含"txt"的行。在这个过程中，`ls`的输出（原本会显示到终端的）被直接传送到`grep`命令，而不是显示到屏幕上。

#### 1）==dup2函数==

- **声明**

```c
#include <unistd.h>
int dup2(int oldfd, int newfd);
```

> makes newfd be the copy of oldfd, closing newfd first if necessary, but note the following:
>
> - If oldfd is not a valid file descriptor, then the call fails, and newfd is not closed.
>
>
> *  If  oldfd is a valid file descriptor, and newfd has the same value as oldfd, then dup2() does noth‐
>       ing, and returns newfd.

- **使用**

> 输入重定向：dup2(fd,0);
>
> 输出重定向：dup2(fd,1);
>
> 追加重定向：dup2(fd,1);   //open的flags需要有`O_APPEND`

- **示例**

```c
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
int main() 
{
    int fd = open("test.txt", O_RDONLY, 0666);
    if(fd==-1)
    {
        perror("open");
        return 1;
    }
    dup2(fd, 0); // 输入重定向
    char buffer[128];
    while(fgets(buffer,sizeof(buffer),stdin)!=NULL)
    {
        printf("%s",buffer);
    }
    close(fd);
    return 0;
}
```

> 在这个例子中，我们打开了一个名为 "test.txt" 的文件，并获取了它的文件描述符 `fd`。然后我们使用 `dup2(fd, 0)` 把标准输入（文件描述符为 0）重定向到了这个文件。所以，buffer从 "test.txt" 文件中输入，而不是从屏幕输入。

#### 2）缓冲区

> 在 Linux 操作系统中，缓冲区是一块内存区域，通常用于暂存输入输出（I/O）操作的数据。
>
> 在c语言中，每个打开的文件都有其自己的缓冲区，这是因为每个文件的读写操作互相独立，需要独立进行管理。例如，你可能在读取一个大文件的同时，还需要写入另一个文件。这两个操作不能相互干扰，所以需要有各自的缓冲区来暂存数据。
>
> 但是需要注意的是，这里说的==“每个文件都有独立的缓冲区”==，主要是用户级别的缓冲区，而不是系统级别的，在c语言中cache是由C库函数管理的，封装在FILE结构体中。

- **作用**

> 缓冲区的主要目的是为了提高系统的性能。
>
> 由于硬件操作（例如硬盘读写或网络数据传输）比内存访问要慢得多，因此I/O操作时操作系统通常会尽量将数据储存在内存中的缓冲区，然后在需要时以批量的方式进行硬件操作。同时，缓冲区还可以让程序能够异步地进行I/O操作，也就是说，程序可以在等待I/O操作完成的同时，做一些其他的工作。

- **刷新策略**

> - **常规刷新**
>
> **无缓冲**：数据直接从调用进程被送到目标，不经过缓冲。标准错误输出（`stderr`）通常是无缓冲的，这确保了错误信息能够尽快显示给用户。
>
> **行缓冲**：当缓冲区满或遇到换行符时，缓冲区的内容会被刷新（即写入目标文件或设备）==。标准输出（`stdout`）在连接到终端（如命令行界面）时通常是行缓冲的，这使得用户可以逐行看到输出。==
>
> **全缓冲**：只有在缓冲区满时，缓冲区的内容才会被刷新。==当标准输出被重定向到文件或通过管道连接到另一个程序时，它通常会变为全缓冲模式。==
>
> - **特殊刷新**
>
> 1. 进程退出（close 0，1，2）
> 2. 用户强制刷新（ffush(fd)）

- **刷新本质**

> ==刷新缓冲区的目的是确保数据的一致性和持久性==。这是非常重要的，特别是对于文件系统，因为文件系统通常需要保证即使在系统崩溃或断电的情况下，数据也不会丢失。
>
> 如果关闭一个文件描述符（通过 `close` 系统调用）前不刷新缓冲区，那么缓冲区中的数据将会丢失。

```c
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
int main()
{
    const char *str1="hello printf\n";
    const char *str2="hello fprintf\n";
    const char *str3="hello fputs\n";
    const char *str4="hello write\n";
    //c库函数
    printf(str1);
    fprintf(stdout,str2);
    fputs(str3,stdout);
    //系统调用
    write(1,str4,strlen(str4));
    fork();
}
```

> 如果此时进行重定向输入，./test >file.txt，结果是什么呢？
>
> 首先由于重定向刷新策略由行缓冲变为了全缓冲，然后子进程继承了父进程的缓冲区数据，那么当进程结束，会刷新父子的缓冲区，不论谁先发生，均会发生写时拷贝，因此会多次写入。

#### 3）模拟实现c库的封装

```c
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#define NUM 1024
typedef struct _MyFILE{//模拟建立FILE结构体
    int _fd;//文件描述符
    char _buffer[NUM];//缓冲区
    int _end;//缓冲区末尾
}MyFILE;
MyFILE *my_fopen(const char *filename, const char *method)//模拟fopen
{
    int flag = 0;//标记状态位
    if(strcmp(method, "w") == 0)
    {
        flag = O_WRONLY | O_CREAT | O_TRUNC;//传递多个状态
    }
    else if(strcmp(method, "a") == 0)
    {
        flag = O_WRONLY | O_CREAT | O_APPEND;
    }
    int fd = open(filename, flags, 0666);//系统调用open
    if(fd < 0)
    {
        return NULL;
    }
    MyFILE *fp = (MyFILE *)malloc(sizeof(MyFILE));//为FILE分配资源
    if(fp == NULL) return fp;
    fp->_fd = fd;//给FILE传入文件描述符
    fp->_end = 0;
    return fp;
}
void my_fflush(MyFILE *fp)//模拟fflush
{
    write(fp->_fd, fp->_buffer, fp->_end);//将缓冲区的内容写入内核
    fp->_end = 0;
    syncfs(fp->_fd);//syncfs() 是一个 Linux 系统调用，其作用是将一个文件系统（由打开的文件描述符指定）的缓冲区数据强制同步（即写入）到底层的存储设备，但不会等待 I/O 完成。
}
void my_fwrite(MyFILE *fp, const char *start, int len)//模拟fwrite
{
    strncpy(fp->_buffer+fp->_end, start, len); //将数据写入到缓冲区了
    fp->_end += len;
    if(fp->_end > 0 && fp->_buffer[fp->_end-1] == '\n')//行刷新策略
    {
        write(fp->_fileno, fp->_buffer, fp->_end);
        fp->_end = 0;
        syncfs(fp->_fileno);
    }
}
void my_fclose(MyFILE *fp)
{
    my_fflush(fp);//关闭前要刷新文件缓冲区
    close(fp->_fileno);
    free(fp);//释放FILE资源
}
int main()
{
    MyFILE *fp = my_fopen("log.txt", "w");
    if(fp == NULL)
    {
        printf("my_fopen error\n");
        return 1;
    }
    const char *s = "hello world\n";
    my_fwrite(fp, s, strlen(s));
    my_fclose(fp);
}
```

#### 3）在命令行模拟实现重定向

```c
#define DROP_SPACE(s) do { while(isspace(*s)) s++; }while(0)
#define NONE_REDIR  -1
#define INPUT_REDIR  0
#define OUTPUT_REDIR 1
#define APPEND_REDIR 2
int g_redir_flag = NONE_REDIR;
char *g_redir_filename = NULL;
void ReDir(char *commands)
{
    assert(commands);
    while(commands!='\0');
    {
        if(*commands == '>')
        {
            if(*(commands+1) == '>') //追加
            {
                *start = '\0';
                start += 2;
                g_redir_flag = APPEND_REDIR;
                DROP_SPACE(commands);
                g_redir_filename = commands;
                break;
            }
            else{
                *commands = '\0';//输出重定向
                commands++;
                DROP_SPACE(commands);
                g_redir_flag = OUTPUT_REDIR;
                g_redir_filename = commands;
                break;
            }
        }
        else if(*commands == '<')
        {
            *commands = '\0';// 输入重定向
            commands++;
            DROP_SPACE(commands);
            g_redir_flag = INPUT_REDIR;
            g_redir_filename = commands;
            break;
        }
        else 
        {
            commands++;
        }
    }
}
int main()
{
    while(1)
    {
        g_redir_flag = NONE_REDIR;
        g_redir_filename = NULL;
    	//....
        ReDir(command_line);
        //....
        pid_t id = fork();
        if(id == 0)
        {
            int fd = -1;
            switch(g_redir_flag)
            {
                case NONE_REDIR:
                    break;
                case INPUT_REDIR:
                    fd = open(g_redir_filename, O_RDONLY);
                    dup2(fd, 0);
                    break;
                case OUTPUT_REDIR:
                    fd = open(g_redir_filename, O_WRONLY | O_CREAT | O_TRUNC);
                    dup2(fd, 1);
                    break;
                case APPEND_REDIR:
                    fd = open(g_redir_filename, O_WRONLY | O_CREAT | O_APPEND);
                    dup2(fd, 1);
                    break;
                default:
                    printf("Bug?\n");
                    break;
            }
			//...
        }
    }
}
```



# 文件系统

> 在 Linux 中，文件是一个包含信息的容器。这个容器中包含的信息可以是文本，也可以是二进制数据。
>
> 文件系统是计算机系统中用于存储、组织、操作和管理数据的方法或数据结构。它负责决定数据如何在磁盘或其他存储设备上存储，以及如何找到存储的数据。
>
> 文件系统负责在存储介质上创建、写入、读取和删除文件。它处理文件存储的所有细节，包括磁盘空间的分配、数据访问控制、数据块的追踪、数据的修改以及文件碎片的管理等。

### 1、磁盘

#### 1）磁盘的物理结构

> 磁盘通常由一组盘片（磁头）构成，每个盘片由磁性材料覆盖，磁性抽象成01数据，数据就储存在这些磁性材料中。
>
> 每个盘片被划分为同心圆轨道，每个轨道又被划分为扇区，扇区文件存储分配的最小单位（一般为512byte）。
>
> 在一组盘片中，同一位置的一组磁道形成一个柱面。
>
> CHS（Cylinder-Head-Sector）寻址是一种磁盘存储器寻址方式，用于确定磁盘上的数据存储位置。在CHS寻址中，磁盘被划分为多个柱面（Cylinder）、磁头（Head）和扇区（Sector），通过这三个参数可以唯一确定磁盘上的一个数据。

#### 2）磁盘的存储结构

> 从存储的角度看，磁盘可以划分为多个分区，每个分区可以看作是一个逻辑磁盘。在文件系统的管理下，每个分区可以进一步划分为一系列连续的块。==块是文件系统访问磁盘的基本单位（4KB）==,磁盘上的每一个文件都是以一个或者多个块的形式存储的。
>
> 扇区（512Btye），文件系统访问磁盘（4KB）：可以提高IO的效率、以及实现软硬件的解耦合

![image-20230519124729711](D:\笔记\C++\linux系统编程网络编程\image-20230519124729711.png)

> Block Group：文件系统会根据分区的大小划分为数个Block Group。而每个Block Group都有着相同的结构组成。
>
> 超级块（Super Block）：存放文件系统本身的属性信息。记录的信息主要有：系统中bolck 和 inode的总量，未使用的block和inode的数量，一个block和inode的大小，最近一次挂载的时间，最近一次写入数据的时间，最近一次检验磁盘的时间等其他文件系统的相关信息。Super Block的信息被破坏，可以说整个文件系统结构就被破坏了，因此在每个快组都存放一个超级块，进行备份。
>
> GDT（Group Descriptor Table）：块组描述符，描述块组属性信息
>
> 块位图（Block Bitmap）：每个bit表示一个block是否空闲可用
>
> inode位图（inode Bitmap）：每个bit表示一个inode是否空闲可用。
>
> i节点表（inode table）:以128字节为单位，存放文件属性如文件大小
>
> 数据区（Data block）：以块为单位，存放文件内容。

#### 3）	LBA寻址

> LBA（Logical Block Addressing）寻址是一种磁盘存储器的寻址方式，用于定位磁盘上的数据块。在LBA寻址中，磁盘被抽象为一个连续的逻辑地址空间，每个逻辑地址对应一个固定大小的数据块。
>
> LBA寻址的工作原理如下：
>
> 1. 每个磁盘被划分为多个逻辑块，每个逻辑块的大小是固定的，通常为4KB。
> 2. 每个逻辑块都被赋予一个唯一的逻辑块地址（LBA），从0开始递增。逻辑块地址表示数据块在逻辑地址空间中的位置。
> 3. 当操作系统或应用程序需要读取或写入磁盘上的数据时，会指定一个逻辑块地址作为目标地址。
> 4. 磁盘控制器根据逻辑块地址找到对应的物理磁道、磁头和扇区，然后进行读取或写入操作。
>
> LBA寻址的优点是简单且易于管理，屏蔽了具体的磁道、磁头和扇区号，简化了磁盘管理和数据访问的操作。使用LBA寻址，操作系统和应用程序可以将磁盘看作是一个连续的存储空间，不需要考虑物理寻道和扇区的细节，实现了软硬件的解耦，符合软硬件设计弱相关的设计理念。

### 2、inode

> 在Linux文件系统中，inode（index node，索引节点）是一个数据结构，它存储了有关文件的大部分信息，除了文件名和文件内容。每个文件都有一个独立的inode，每个inode都有一个独立的编号，该编号可以理解为一个全局变量。
>
> 文件系统将文件属性（inode）和文件内容（block）分开存储。
>
> 分离属性和内容存储使得不同文件可以共享数据块，可以节省存储空间，并便于管理和维护，也可以更好地控制对文件访问的权限。

#### 1）inode内容

> 一个inode包含了许多有关文件的信息，比如：
>
> - 文件类型（比如，是普通文件、目录、字符设备、块设备等）
> - 文件的权限
> - 文件的所有者和组
> - 文件的大小
> - 文件的创建、修改和访问时间
> - 文件内容的位置（这通常是一个指向磁盘上数据块的指针列表）

#### 2）inode和文件内容的关联

> ==inode中存储了一个指针数组，每个指针指向磁盘上的一个块，这些块就存储了文件的实际内容==。因此，inode和文件内容的关联是通过这些指针实现的。要读取文件的内容，系统首先查找文件的inode，然后通过inode中的指针找到存储文件内容的磁盘块。
>
> inode中的指针数组不仅可以指向存储数据的磁盘块，==还可以指向存储其他块指针的磁盘块==，这种设计允许文件系统处理比直接指针数组可以引用的磁盘块更多的磁盘块，从而支持更大的文件。

#### 3）创建文件和删除文件的OS管理

> 注意，文件名是文件属性，但inode内容并没有文件名。
>
> 在Unix和类Unix系统中，==每个目录都是一个特殊类型的文件，它本身也有一个与之对应的inode==。目录的内容（数据部分）实际上是一系列的记录，==每条记录包含一个文件名以及与该文件名对应的inode号。这就是所谓的“目录项”（directory entry），它建立了文件名到inode的映射关系==。因此，不同的目录可以有相同的文件名，因为每个目录有自己的一组目录项，这些文件名指向的inode号可以不同，从而指向文件系统中的不同文件。

- **创建文件：**

> 1. **寻找空闲inode**：文件系统会检查inode bitmap来找到一个未被使用的inode。每个位对应一个inode，如果位是0，则表示相应的inode是空闲的。
>2. **初始化inode**：选中的空闲inode将根据新文件的属性进行初始化。这包括设置文件类型、权限、所有者、大小（最初通常是0），以及其他元数据。
> 3. **分配数据块**：当文件需要存储数据时，文件系统会分配一个或多个数据块来存储文件内容，并更新inode中的指针数组，这些指针直接指向数据块或间接通过其他块指针指向数据块。
>4. **更新目录项**：新文件的文件名和对应的inode编号会被添加到父目录的目录项中，从而建立文件名到inode的映射。

- **目录删除文件：**

> 1. **寻找文件inode**：通过目录项找到要删除的文件名对应的inode编号。
> 2. **释放数据块**：根据inode中记录的信息，释放该文件所占用的所有数据块，并在数据块bitmap中相应位置标记为未使用（置0）。
> 3. **释放inode**：将该文件的inode标记为未使用（在inode bitmap中置0），并清除inode内容。
> 4. **更新目录项**：从父目录的目录项中移除该文件的条目。
>
> - **数据未立即被覆盖**：当文件被删除时，它的数据块和inode并不会立即被物理删除或覆盖。只是在文件系统的管理结构中标记为可用，这就意味着在数据被新数据覆盖之前，有可能通过某些工具恢复删除的文件。
> - **inode和数据块重用**：删除文件后，它占用的inode和数据块可以被其他新文件使用。这种设计使得文件系统能高效地管理存储空间，但也意味着一旦删除的文件被部分或全部覆盖，恢复将变得非常困难或不可能。

### 3、软硬连接：

#### 1）连接相关指令

- **ln指令**

> 用法：ln <option\>  \<target>   <link_name>
>
> 选项：-s 软连接
>
> 功能：为文件创建软硬连接

- **unlink指令**

> 用法：unlink link_name使
>
> 功能：取消连接，也可以删查文件

#### 2）硬连接

> 硬连接是与目标文件指向一个inode，是一个连接到 inode 的文件别名。
>
> 硬连接数是存储在inode里的计数器，表明有多少个文件名指向inode。
>
> 创建普通文件默认硬连接数默认是1，目录是2（./..）。这也解释了.为什么是当前目录，..是目录的目录
>
> 删除一个硬连接并不会影响其他连接，只有当所有连接都被删除时，文件才会被真正删除。

#### 3）软连接

> 软连接是一个独立的文件，有自己的独立的inode。
>
> 它保存了连接的目标文件的路径。例如快捷方式
>
> 删除软连接并不会影响连接的目标文件或目录。



# 动静态库

### 1、静态库

> - ==静态库是编译时连接到可执行文件的库文件==。
> - 静态库的文件扩展名通常为 .a
> - 静态库的优点是使用简单，不依赖于外部环境，可在不同平台上进行移植。
> - 静态库的缺点是占用磁盘空间较大，无法在运行时更新库文件。
>

#### 1）建立静态库

> - `ar` 命令：用于创建静态库。
> - `rcs` 参数：指定创建归档文件并生成索引。

```c
gcc -o fun1.o -c fun1.c
gcc -o fun2.o -c fun2.c
//file1.o,file2.o 可以直接被链接
ar -rc libmylib.a fun1.o fun2.o  //打包静态库   静态库名字规范lib（）.a
```

#### 2）发布静态库

> 库文件+头文件

```c
mkdir -p lib-static/lib//建立多级目录
mkdir -p lib-static/include
cp *.a lib-static/lib
cp *.h lib-static/include
```

#### 3）使用静态库

> 头文件的搜索路径
>
> - 当前路径查找（默认）
> - 系统头文件查找 （默认）
> - 指定搜索路径
>
> 头文件一般在 /user/include/
>
> 库一般在 /lib64/

```c
gcc test.c -o test -I./lib-static/include/ -L./lib-static/lib -lmylib  //指定搜索路径
```

> - `-I.`：指定头文件所在的路径为当前路径。
> - `-L.`：指定库文件所在的路径为当前路径。
> - `-l`：==指定库的名字，按库规范名字去掉lib和后缀==

### 2、动态库

> - ==动态库是在运行时加载的库文件，与可执行文件分开存储，可以被多个程序共享使用。==
> - 动态库的文件扩展名通常为 .so
> - 动态库的优点是节省磁盘空间，多个程序可以共享同一个库，更新库时无需重新编译可执行文件。
> - 动态库的缺点是在运行时需要依赖正确版本的库文件，可能会受到库文件版本不兼容等问题的影响。
>
> 推荐库：ncurses、boost

#### 1）建立动态库

> - `-shared`：生成动态库文件。
> - `-o <output_file>`：指定输出文件的名称。
> - `-fPIC`：生成位置无关的代码。

**示例：**

```c
gcc -o fun1.o -c -fPIC fun1.c
gcc -o fun2.o -c -fPIC fun2.c
gcc -shared -o lib-dyl.so file1.o file2.o
```

#### 2）发布动态库

```makefile
all:libmylib.a libmylib.so

libmylib.so:Add.o Print.o
	gcc -shared -o libmylib.so Add.o Print.o
Add.o:Add.c
	gcc -o Add.o -c -fPIC Add.c
Print.o:Print.c
	gcc -o	Print.o -c -fPIC Print.c


libmylib.a:Add_s.o Print_s.o
	ar -rc libmylib.a Add_s.o Print_s.o
Add_s.o:Add.c
	gcc -o Add_s.o -c Add.c
Print_s.o:Print.c
	gcc -o	Print_s.o -c Print.c

.PHONY:lib
lib:
	mkdir -p lib-static/lib
	mkdir -p lib-static/include
	cp *a lib-static/lib
	cp *h lib-static/include
	mkdir -p lib-dynamic/lib
	mkdir -p lib-dynamic/include
	cp *so lib-dynamic/lib
	cp *h lib-dynamic/include
.PHONY:clean
clean:
	rm -fr *.o lib* *.so *.a

```



#### 3）使用动态库

> 在运行时，依旧需要指定库的路径

- **导入环境变量**

```c
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/lxy/...  //重启shell会消失
```

- **系统配置文件**

```c
ls /etc/ld.so.conf.d/
touch /etc/ld.so.conf.d/my.conf //将库的路径保存在myconf
ldconfig /etc/ld.so.conf.d/my.conf//刷新配置
```

- **软连接**

```c
ln -s /home/lxy/.../libmylib.so   /lib64/libmylib.so    //建立在lib64中建立软连接，指向库的路径
```

#### 4）共享区

> 在操作系统中，共享区（Shared Memory）是指在多个进程之间共享内存空间的一种机制。它允许多个进程将同一块物理内存映射到它们各自的地址空间中，从而实现数据的共享和通信。
>
> 在执行动态库时，只需要在内存中开辟一块空间，通过页表映射到共享区，而静态库直接写入代码区。
>
> 因此，程序运行时，需要加载动态库，就必须知道动态库的位置。



