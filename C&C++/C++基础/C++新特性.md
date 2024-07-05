# 一、智能指针

智能指针是C++中一种==模拟指针行为同时提供自动资源管理的对象==，用于解决原始指针（raw pointers）中==常见的资源泄露和指针悬挂问题==。通过封装原始指针并在适当的时候自动释放所占用的资源，智能指针使得资源管理更加安全和简单。C++标准库提供了几种智能指针，主要包括`std::unique_ptr`、`std::shared_ptr`和`std::weak_ptr`。

### （1）std::unique_ptr

`std::unique_ptr`提供==对一个对象的独占所有权==。一次只能有一个`std::unique_ptr`指向一个给定资源，这确保了资源的生命周期被明确地管理。当`std::unique_ptr`被销毁时，它所指向的对象也会被自动删除。这种智能指针特别适用于表示独占资源的场景。

```cpp
#include <memory>

std::unique_ptr<int> p1(new int(42));
std::unique_ptr<int> p2 = std::move(p1); // 现在p2拥有资源，p1为空
```

### （2）std::shared_ptr

`std::shared_ptr`允许==多个指针实例共享同一个对象的所有权==。它通过==引用计数机制==来跟踪有多少个`std::shared_ptr`实例指向同一个资源，当最后一个这样的实例被销毁时，资源也会被自动释放。这使得`std::shared_ptr`非常适合于需要共享资源的场景。

```cpp
#include <memory>

std::shared_ptr<int> p1 = std::make_shared<int>(42);
std::shared_ptr<int> p2 = p1; // p1和p2共享同一个整数
```

### （3）std::weak_ptr

`std::weak_ptr`是一种==非拥有性智能指针==，它指向由`std::shared_ptr`管理的对象，但不会增加对象的引用计数。这意味着`std::weak_ptr`的存在不会阻止其指向的对象被销毁。`std::weak_ptr`通常用于解决`std::shared_ptr`之间的循环引用问题。

```cpp
#include <memory>

std::shared_ptr<int> p1 = std::make_shared<int>(42);
std::weak_ptr<int> wp = p1; // wp指向p1管理的对象，但不拥有它

std::shared_ptr<int> p2 = wp.lock(); // 从weak_ptr创建一个shared_ptr
if (p2) {
    // 安全地使用p2
}
```

### 总结

智能指针在C++中是资源管理的重要工具，通过自动管理生命周期来减少内存泄露和指针悬挂的风险。选择合适类型的智能指针可以简化代码，提高程序的稳定性和安全性。

# 二、move

在C++中，`std::move`是一个标准库函数，用于将对象的状态或所有权从一个实例转移到另一个实例，实现所谓的"移动语义"。它是C++11及之后版本中引入的一个重要特性，旨在提高程序的性能，尤其是在涉及到大量数据复制操作的场景中。

### （1）基本概念

- **移动语义（Move Semantics）**：允许资源（如动态分配的内存、文件句柄等）的所有权从一个对象转移到另一个对象，而不是复制资源。这通常通过修改源对象，使其进入一个有效但未定义的状态来实现。
- **右值引用（Rvalue Reference）**：通过类型后加`&&`符号表示，如`int&&`。它是对临时对象的引用，表明该对象即将被销毁，其资源可以被安全地"窃取"而不影响程序的正确性。

### （2）std::move的作用

`std::move`实际上并不移动任何内容，而是将其参数转换为右值引用，这样就可以触发移动构造函数或移动赋值操作符，而非复制构造函数或复制赋值操作符。这使得资源的所有权能够被转移，从而避免了不必要的复制，提高了效率。

### 使用示例

考虑以下示例，其中展示了使用`std::move`来触发移动构造函数和移动赋值操作符：

```cpp
#include <iostream>
#include <vector>
#include <utility> // For std::move

class HugeData {
public:
    HugeData() = default;
    // 移动构造函数
    HugeData(HugeData&& other) noexcept : data_(std::move(other.data_)) {
        std::cout << "Moved\n";
    }
    // 移动赋值操作符
    HugeData& operator=(HugeData&& other) noexcept {
        if (this != &other) {
            data_ = std::move(other.data_);
            std::cout << "Moved\n";
        }
        return *this;
    }
private:
    std::vector<int> data_;
};

int main() {
    HugeData a;
    HugeData b = std::move(a); // 触发移动构造函数
    HugeData c;
    c = std::move(b); // 触发移动赋值操作符
}
```

### 注意事项

- 使用`std::move`后，源对象处于未定义状态，因此除了销毁或赋予新值外，不应再使用。
- 移动操作应保证异常安全，特别是移动构造函数和移动赋值操作符通常应标记为`noexcept`。
- 不是所有的对象都比较适合移动，比如内置类型（int、double等）使用移动语义几乎没有性能上的益处。

总的来说，`std::move`和移动语义一起提供了一种高效管理资源的方式，特别适用于处理大型对象和容器，能够显著提升C++应用程序的性能。



















### YI常

throw+任意类型

异常必须try（异常栈展开），直接从throw（临时对象）跳到近的catch（类型匹配）捕获（同一位置没有相同的捕获），否则终止程序，继续运行程序

```c++
catch(...)//捕获没有匹配的任意类型异常
{
    cout<<"未知异常"<<endl;
}
```

throw派生类，用基类（exception）catch

异常安全问题：new后在delete前抛异常，造成内存泄漏

```c++
catch(...)
{
    delete[];
    throw;//捕获什么就抛什么
}
```



### 智能指针

gc垃圾回收器

RAII（Resource Acquisition Is Initialization）思想+像指针一样+拷贝

拷贝问题：浅拷贝，但需要释放资源

方法1：C++98  auto_ptr  置空操作管理权转移

C++11  	

unique_ptr 防拷贝    拷贝和赋值只声明，不实现+   放入私有 或 =delete

shared_ptr  引用计数，最后一个析构的对象释放资源     拷贝和赋值计数增加，析构减少

weak_ptr  解决循环引用

定制删除器



### 强制类型转换

static_cast、reinterpret_cast、const_cast、dynamic_cast

RTII(Run-time Type identifification)

C++通过以下方式来支持RTTI：

1. typeid运算符
2. dynamic_cast运算符
3. decltype





### io流

scanf/printf….面向过程且只能针对内置类型

io流 面向对象且针对自定义类型

