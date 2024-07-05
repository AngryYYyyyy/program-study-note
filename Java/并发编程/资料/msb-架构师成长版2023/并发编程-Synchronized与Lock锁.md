# 一、ReentrantLock

`ReentrantLock` 是 Java 中一个非常强大的工具，用于实现同步。它提供了比传统的 `synchronized` 更多的功能和灵活性。`ReentrantLock` 有两种模式：公平锁和非公平锁。非公平锁允许抢占式的锁获取，这意味着新请求的锁可以在其他线程等待锁的队列之前获得锁，这通常会提高性能，但可能会导致饥饿。

## 1.锁特性

`AbstractQueuedSynchronizer`（AQS）是 Java 并发包的核心，它提供了一个框架==通过使用先进先出（FIFO）等待队列来构建阻塞锁和相关的同步器（如信号量、事件等）==。AQS ==抽象了同步状态的管理，线程的排队和阻塞，以及唤醒线程的机制==。这些功能是通过一系列关键方法实现的，这些方法允许开发者定义同步器的具体行为。

以下是AQS的三个核心组成部分的详细说明：

1. **State：**在ReentrantLock中，`state`是一个关键的变量，用于表示==锁的持有状态==。当`state`的值为0时，表明锁当前没有被任何线程持有；当`state`的值大于0时，则表示锁已被一个或多个线程持有。ReentrantLock使用CAS（Compare-And-Swap）操作，尝试将`state`从0设置为1，以此来尝试获取锁。如果设置成功，则表示获取锁资源成功；如果失败，则说明锁已被其他线程占有。

2. **==同步队列==（双向链表）：**如果一个线程尝试获取ReentrantLock但锁已被其他线程占用，则该线程会被加入到同步队列中，此队列是一个FIFO（先进先出）的双向链表。在这里，线程会等待直到它们能够再次尝试获取锁。当锁被释放时，队列中的下一个线程（通常是头部线程）将有机会获取锁。

3. **==条件队列==（单向链表）：**当线程已经持有ReentrantLock，并调用了条件变量的`await()`方法后，它会释放锁并进入条件队列等待。这个条件队列是一个单向链表，用于存放调用了`await()`方法的线程。当条件变量的`signal()`或`signalAll()`方法被调用时，线程会从条件队列移回同步队列，等待重新获取锁。

以下是 AQS 提供的一些关键方法，分为独占模式和共享模式的方法：

独占模式意味着每次只有一个线程可以持有锁。

1. **acquire(int arg)**
   - 获取独占锁。如果获取失败，则当前线程进入同步队列等待，直到获取成功或线程被中断。
2. **release(int arg)**
   - 释放独占锁。如果释放后允许其他线程获取锁，它将唤醒同步队列中的后继线程。
3. **tryAcquire(int arg)**
   - 尝试获取独占锁。这是一个需要用户实现的方法，用于定义锁获取的逻辑。
4. **tryRelease(int arg)**
   - 尝试释放独占锁。这是另一个需要用户实现的方法，用于定义锁释放的逻辑。

共享模式允许多个线程同时获取锁。

1. **acquireShared(int arg)**
   - 获取共享锁。根据实现可能允许多个线程同时获取锁。
2. **releaseShared(int arg)**
   - 释放共享锁。释放操作可能会唤醒等待中的线程。
3. **tryAcquireShared(int arg)**
   - 尝试获取共享锁。用户定义的方法，返回值指示获取成功、失败或需要排队等待。
4. **tryReleaseShared(int arg)**
   - 尝试释放共享锁。用户定义的方法，返回值通常表示是否成功释放锁，并可能触发后续的线程唤醒。

下面这些方法用于检查和操作内部同步队列。

1. **hasQueuedThreads()**
   - 查询是否有线程正在等待获取锁。
2. **hasContended()**
   - 查询是否有线程竞争过这个锁。
3. **getFirstQueuedThread()**
   - 返回同步队列中的第一个（等待时间最长的）线程。
4. **getQueueLength()**
   - 返回等待获取锁的线程数量。

下面这些方法与 `Condition` 对象配合使用，用于条件等待。

1. newCondition()
   - 返回与此锁关联的新的 `Condition` 实例。

AQS通过这样的内部结构提供了一种高效的方式来管理同步状态，使得构建锁和其他同步器变得简单而高效。使用AQS开发的同步工具可以显著简化多线程编程，同时提供强大的并发性能。

## 2.锁底层

### （1）加锁流程

> 非公平锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1700739126043/dd3552d3e0e74673a07a9874d94605f8.png)

以下是对`ReentrantLock`非公平锁加锁流程的详细描述：

#### 1. 尝试直接获取锁
- 当一个线程尝试获取锁时，它首先尝试通过`compareAndSetState(0, 1)`原子操作直接更新锁的状态从0到1。
- 如果CAS操作成功，则锁状态更新为1，表明当前线程成功获取了锁。

#### 2. 检查锁状态和重入
- 如果第一步的CAS操作失败，此时线程会执行`acquire`方法，在其中调用`tryAcquire`方法来详细判断锁的状态。
- 在`tryAcquire`方法中，会首先检查锁的状态：
  - 如果状态为0，则尝试再次执行CAS操作以获取锁。
  - 如果锁的状态不为0，会进一步检查当前线程是否为锁的持有者（重入情况）。如果是，更新状态（增加重入计数）。

#### 3. 加入等待队列
- 如果锁被其他线程持有且当前线程非锁持有者，当前线程需要等待。
- 线程通过`addWaiter(Node.EXCLUSIVE)`方法将自己封装成一个节点（Node对象），并尝试将其添加到同步队列的尾部。这个操作是通过CAS完成的，如果失败则重复尝试。
- 加入队列后，进入一个循环，在循环中，线程执行`acquireQueued`方法尝试获取锁：
  - 在`acquireQueued`方法中，线程首先获取其前驱节点。
  - 如果前驱节点是头节点（head），则表示当前节点位于队列的第二位置，此时线程会再次尝试通过`tryAcquire`获取锁。
  - 如果前驱不是头节点或者再次尝试获取锁失败，则确保前驱节点的状态能够在释放时唤醒当前节点，并调用`LockSupport.park(this)`将自己挂起（阻塞）。
  - 当前节点被唤醒（通常是因为前驱节点释放了锁并唤醒了它）后，循环继续，直到获取到锁或线程被中断。

### （2）释放锁流程

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1700739126043/b655133cc467438ab59b4a5636ea987c.png)下面是释放锁的流程：

#### 1. 尝试释放锁
- 当线程完成其临界区的代码后，它会调用 `unlock()` 方法来释放锁。
- `unlock()` 方法内部首先调用 `tryRelease(int releases)` 方法，这里的 `releases` 通常是1，表示尝试减少一个持锁计数。

#### 2. 更新锁的状态
- 在 `tryRelease` 方法中，首先进行检查以确认当前锁是否由调用线程持有。
  - 如果锁不是由当前线程持有，会抛出 `IllegalMonitorStateException` 异常，因为一个线程不能释放另一个线程持有的锁。
  - 如果锁由当前线程持有，则减少锁的持有计数。如果计数为0，表示锁完全释放，设置锁的状态为0，表示没有线程持有该锁。这个操作不需要CAS，因为当前线程已经确定是锁的持有者。

#### 3. 唤醒后续节点
- 如果 `tryRelease` 返回 `true`（表示锁被完全释放），`unlock()` 方法将继续执行 `release(1)` 中的后续逻辑。
- 在 `release` 方法中，会首先检查同步队列：
  - 获取同步队列的头节点（head）。如果头节点的后继节点（head.next）存在，并且该节点处于等待状态（节点状态不为0），则直接唤醒该节点。
  - 如果头节点的后继节点不存在或不处于正确的状态，将遍历同步队列以找到适合唤醒的最近的节点（通常是距离头节点最近的处于等待状态的节点）。
- 使用 `LockSupport.unpark(successor)` 来唤醒找到的后续等待节点。被唤醒的线程将得到机会尝试获取锁。

### // 2.3 await流程

### // 2.4 signal流程

# 二、synchronized

## 1.synchronized特性

只聊synchronized的重量级锁的内容。

在synchronized的重量级锁中，也有类似于AQS的内容。

直接查看openjdk中的ObjectMonitor.hpp中提供的一些核心内容

https://hg.openjdk.org/jdk8u/jdk8u/hotspot/file/69087d08d473/src/share/vm/runtime/objectMonitor.hpp

```hpp
ObjectMonitor() {
    _header       = NULL;
    _count        = 0;
    _waiters      = 0,      // WaitSet里等待的线程个数。今儿不涉及
    _recursions   = 0;      // 跟AQS的state一样。
    _object       = NULL;
    _owner        = NULL;   // 跟AQS的exclusiveOwnerThread一样
    _WaitSet      = NULL;   // 类似于AQS里的单向链表（双向链表） 今儿不涉及
    _WaitSetLock  = 0 ;
    _Responsible  = NULL ;
    _succ         = NULL ;
    _cxq          = NULL ;  // 类似于AQS里的同步队列（单向链表）。拿锁失败先扔cxq
    FreeNext      = NULL ;
    _EntryList    = NULL ;  // 类似于AQS里的同步队列（双向链表）。释放锁，可能会将cxq排队的节点扔到EntryList
    _SpinFreq     = 0 ;
    _SpinClock    = 0 ;
    OwnerIsThread = 0 ;
    _previous_owner_tid = 0;
}
```

## 2.synchronized底层

### （1）加锁流程

同步代码块转换为指令后，可以看到，加锁的指令是monitorenter指令。对应到C++里面的函数，就是他

https://hg.openjdk.org/jdk8u/jdk8u/hotspot/file/69087d08d473/src/share/vm/runtime/objectMonitor.cpp

```cpp
void ATTR ObjectMonitor::enter(TRAPS) {…………}
```

#### 4.1.1 分析enter函数

```cpp
// monitorenter指令入口的函数
void ATTR ObjectMonitor::enter(TRAPS) {、
  // Self就是当前线程
  Thread * const Self = THREAD ;
  void * cur ;

  // 这里是基于CAS，将ObjectMontor中的_owner从NULL修改为Self，返回的原值
  cur = Atomic::cmpxchg_ptr (Self, &_owner, NULL) ;

  // 如果返回NULL，证明从NULL修改为当前线程成功了！
  if (cur == NULL) {
     // 代表拿锁成功。
     assert (_recursions == 0   , "invariant") ;
     assert (_owner      == Self, "invariant") ;
     // 告辞！
     return ;
  }

  // 如果返回的Self，CAS失败了。持有锁的线程就是当前线程
  if (cur == Self) {
     // 对_recursions + 1，代表锁重入！
     _recursions ++ ;
     // 告辞！
     return ;
  }

  // 当前第一次来，代表是从轻量级锁升级过来的，这里也是直接设置好，锁升级操作！
  if (Self->is_lock_owned ((address)cur)) {
    assert (_recursions == 0, "internal state error");
    _recursions = 1 ;
    _owner = Self ;
    OwnerIsThread = 1 ;
    return ;
  }

  assert (Self->_Stalled == 0, "invariant") ;
  Self->_Stalled = intptr_t(this) ;


  // TrySpin,自旋锁（循环执行CAS），尝试获取锁资源！
  if (Knob_SpinEarly && TrySpin (Self) > 0) {
     assert (_owner == Self      , "invariant") ;
     assert (_recursions == 0    , "invariant") ;
     assert (((oop)(object()))->mark() == markOopDesc::encode(this), "invariant") ;
     Self->_Stalled = 0 ;
     // 说明自旋锁拿锁成功，告辞！
     return ;
  }

  assert (_owner != Self          , "invariant") ;
  assert (_succ  != Self          , "invariant") ;
  assert (Self->is_Java_thread()  , "invariant") ;
  JavaThread * jt = (JavaThread *) Self ;
  assert (!SafepointSynchronize::is_at_safepoint(), "invariant") ;
  assert (jt->thread_state() != _thread_blocked   , "invariant") ;
  assert (this->object() != NULL  , "invariant") ;
  assert (_count >= 0, "invariant") ;

  Atomic::inc_ptr(&_count);

  JFR_ONLY(JfrConditionalFlushWithStacktrace<EventJavaMonitorEnter> flush(jt);)
  EventJavaMonitorEnter event;
  if (event.should_commit()) {
    event.set_monitorClass(((oop)this->object())->klass());
    event.set_address((uintptr_t)(this->object_addr()));
  }

  {
    JavaThreadBlockedOnMonitorEnterState jtbmes(jt, this);

    Self->set_current_pending_monitor(this);

    DTRACE_MONITOR_PROBE(contended__enter, this, object(), jt);
    if (JvmtiExport::should_post_monitor_contended_enter()) {
      JvmtiExport::post_monitor_contended_enter(jt, this);
    }

    OSThreadContendState osts(Self->osthread());
    ThreadBlockInVM tbivm(jt);

    for (;;) {
      jt->set_suspend_equivalent();
      // 如果前面的几次操作没拿到锁，执行EnterI函数。
      // 再次尝试或者排队操作！
      EnterI (THREAD);

      if (!ExitSuspendEquivalent(jt)) break ;

          _recursions = 0 ;
      _succ = NULL ;
      exit (false, Self) ;

      jt->java_suspend_self();
    }
    Self->set_current_pending_monitor(NULL);
  }

  Atomic::dec_ptr(&_count);
  assert (_count >= 0, "invariant") ;
  Self->_Stalled = 0 ;

  assert (_recursions == 0     , "invariant") ;
  assert (_owner == Self       , "invariant") ;
  assert (_succ  != Self       , "invariant") ;
  assert (((oop)(object()))->mark() == markOopDesc::encode(this), "invariant") ;

  DTRACE_MONITOR_PROBE(contended__entered, this, object(), jt);
  if (JvmtiExport::should_post_monitor_contended_entered()) {
    JvmtiExport::post_monitor_contended_entered(jt, this);
  }

  if (event.should_commit()) {
    event.set_previousOwner((uintptr_t)_previous_owner_tid);
    event.commit();
  }

  if (ObjectMonitor::_sync_ContendedLockAttempts != NULL) {
     ObjectMonitor::_sync_ContendedLockAttempts->inc() ;
  }
}
```

#### 4.1.2 分析EnterI函数

```cpp
// 前面enter操作拿锁失败，走这
void ATTR ObjectMonitor::EnterI (TRAPS) {
    // Self是当前抢锁线程
    Thread * Self = THREAD ;
    assert (Self->is_Java_thread(), "invariant") ;
    assert (((JavaThread *) Self)->thread_state() == _thread_blocked   , "invariant") ;

    // 执行一次CAS尝试拿锁
    if (TryLock (Self) > 0) {
        // 拿锁成功
        assert (_succ != Self              , "invariant") ;
        assert (_owner == Self             , "invariant") ;
        assert (_Responsible != Self       , "invariant") ;
        // 告辞！
        return ;
    }

    DeferredInitialize () ;

    // 再次基于自旋的形式拿锁
    if (TrySpin (Self) > 0) {
        // 拿锁成功
        assert (_owner == Self        , "invariant") ;
        assert (_succ != Self         , "invariant") ;
        assert (_Responsible != Self  , "invariant") ;
        // 告辞！
        return ;
    }

    assert (_succ  != Self            , "invariant") ;
    assert (_owner != Self            , "invariant") ;
    assert (_Responsible != Self      , "invariant") ;

    // 拿锁失败，将线程Self封装为ObjectWaiter对象，也就是node
    ObjectWaiter node(Self) ;
    Self->_ParkEvent->reset() ;
    node._prev   = (ObjectWaiter *) 0xBAD ;
    // 将node状态设置为cxq，代表一会要扔到_cxq单向链表里！
    node.TState  = ObjectWaiter::TS_CXQ ;

    ObjectWaiter * nxt ;
    for (;;) {
        node._next = nxt = _cxq ;
        // 基于CAS的方式，将封装好的Node，扔到cxq的后面
        if (Atomic::cmpxchg_ptr (&node, &_cxq, nxt) == nxt) break ;

        // 没扔进去，再挣扎一下，尝试拿个锁
        if (TryLock (Self) > 0) {
            // 拿锁成功
            assert (_succ != Self         , "invariant") ;
            assert (_owner == Self        , "invariant") ;
            assert (_Responsible != Self  , "invariant") ;
            // 告辞！
            return ;
        }
    }

    if ((SyncFlags & 16) == 0 && nxt == NULL && _EntryList == NULL) {
        Atomic::cmpxchg_ptr (Self, &_Responsible, NULL) ;
    }


    TEVENT (Inflated enter - Contention) ;
    int nWakeups = 0 ;
    int RecheckInterval = 1 ;

    for (;;) {
        // 再挣扎一下。
        if (TryLock (Self) > 0) break ;
        assert (_owner != Self, "invariant") ;

        if ((SyncFlags & 2) && _Responsible == NULL) {
           Atomic::cmpxchg_ptr (Self, &_Responsible, NULL) ;
        }

        // 如果前面挣扎失败，这里就会涉及到线程的挂起！
        if (_Responsible == Self || (SyncFlags & 1)) {
            TEVENT (Inflated enter - park TIMED) ;
            Self->_ParkEvent->park ((jlong) RecheckInterval) ;
            RecheckInterval *= 8 ;
            if (RecheckInterval > 1000) RecheckInterval = 1000 ;
        } else {
            TEVENT (Inflated enter - park UNTIMED) ;
            Self->_ParkEvent->park() ;
        }

        // 到这就是被唤醒了，抢锁！
        if (TryLock(Self) > 0) break ;

        TEVENT (Inflated enter - Futile wakeup) ;
        if (ObjectMonitor::_sync_FutileWakeups != NULL) {
           ObjectMonitor::_sync_FutileWakeups->inc() ;
        }
        ++ nWakeups ;

        if ((Knob_SpinAfterFutile & 1) && TrySpin (Self) > 0) break ;

        if ((Knob_ResetEvent & 1) && Self->_ParkEvent->fired()) {
           Self->_ParkEvent->reset() ;
           OrderAccess::fence() ;
        }
        if (_succ == Self) _succ = NULL ;

        OrderAccess::fence() ;
    }

    assert (_owner == Self      , "invariant") ;
    assert (object() != NULL    , "invariant") ;

    UnlinkAfterAcquire (Self, &node) ;
    if (_succ == Self) _succ = NULL ;

    assert (_succ != Self, "invariant") ;
    if (_Responsible == Self) {
        _Responsible = NULL ;
        OrderAccess::fence(); // Dekker pivot-point
    }
    if (SyncFlags & 8) {
       OrderAccess::fence() ;
    }
    return ;
}
```

#### 4.1.3 分析tryLock&trySpin函数

tryLock的逻辑

```cpp
// tryLock尝试拿锁的逻辑
int ObjectMonitor::TryLock (Thread * Self) {
   for (;;) {
      void * own = _owner ;
      // 有线程持有锁，直接告辞，没抢到锁。
      if (own != NULL) return 0 ;
      // 如果own是NULL，直接CAS尝试一波
      if (Atomic::cmpxchg_ptr (Self, &_owner, NULL) == NULL) {
         // 拿锁成功
         assert (_recursions == 0, "invariant") ;
         assert (_owner == Self, "invariant") ;
         // 告辞！
         return 1 ;
      }
      // TODO记得优化！！ 返回-1，拿锁失败！
      if (true) return -1 ;
   }
}
```

trySpin的逻辑

```cpp
int ObjectMonitor::TrySpin_VaryDuration (Thread * Self) {
    // 拿到自旋的次数
    int ctr = Knob_FixedSpin ;
    if (ctr != 0) {
        // 基于tryLock开始自旋尝试拿锁，成功返回1
        while (--ctr >= 0) {
            if (TryLock (Self) > 0) return 1 ;
            SpinPause () ;
        }
        // 循环结束没拿到，返回0
        return 0 ;
    }
    // 省略一堆代码
}
```

### （2）释放锁流程

再次查看一个函数，前面看到过指令，加锁是monitorenter，释放锁是monitorexit。

同理，这里要查看的函数是exit

```cpp
void ATTR ObjectMonitor::exit(bool not_suspended, TRAPS) {}
```

```cpp
// 释放锁的流程
void ATTR ObjectMonitor::exit(bool not_suspended, TRAPS) {
   // 拿线程
   Thread * Self = THREAD ;
   // 持有线程的不是当前这个线程。没持有锁，想释放锁？？？
   if (THREAD != _owner) {
     // 说明是锁升级过来的，让当前线程持有这个锁。
     if (THREAD->is_lock_owned((address) _owner)) {
       assert (_recursions == 0, "invariant") ;
       _owner = THREAD ;
       _recursions = 0 ;
       OwnerIsThread = 1 ;
     } else {
       // 持有锁的线程不是当前线程，甩你一脸异常
       TEVENT (Exit - Throw IMSX) ;
       assert(false, "Non-balanced monitor enter/exit!");
       if (false) {
          THROW(vmSymbols::java_lang_IllegalMonitorStateException());
       }
       return;
     }
   }
   
   // 一次释放不干净，先--一波。
   if (_recursions != 0) {
     _recursions--;   
     TEVENT (Inflated exit - recursive) ;
     return ;
   }

   if ((SyncFlags & 4) == 0) {
      _Responsible = NULL ;
   }

#if INCLUDE_JFR
   if (not_suspended && EventJavaMonitorEnter::is_enabled()) {
    _previous_owner_tid = JFR_THREAD_ID(Self);
   }
#endif

   for (;;) {
      assert (THREAD == _owner, "invariant") ;

      // 走这个策略。
      if (Knob_ExitPolicy == 0) {
         // 看样子就是释放锁！！
         OrderAccess::release_store_ptr (&_owner, NULL) ;   // drop the lock
         OrderAccess::storeload() ;                         // See if we need to wake a successor
         if ((intptr_t(_EntryList)|intptr_t(_cxq)) == 0 || _succ != NULL) {
            TEVENT (Inflated exit - simple egress) ;
            return ;
         }
         TEVENT (Inflated exit - complex egress) ;

         // 重新获取锁资源！
         // 为了可以操作_cxq和_EntryList
         if (Atomic::cmpxchg_ptr (THREAD, &_owner, NULL) != NULL) {
            // 如果重新获取失败了，当前其他线程拿到了！直接告辞！
            return ;
         }
         TEVENT (Exit - Reacquired) ;
      } else {
         if ((intptr_t(_EntryList)|intptr_t(_cxq)) == 0 || _succ != NULL) {
            OrderAccess::release_store_ptr (&_owner, NULL) ;   // drop the lock
            OrderAccess::storeload() ;
            // Ratify the previously observed values.
            if (_cxq == NULL || _succ != NULL) {
                TEVENT (Inflated exit - simple egress) ;
                return ;
            }
            if (Atomic::cmpxchg_ptr (THREAD, &_owner, NULL) != NULL) {
               TEVENT (Inflated exit - reacquired succeeded) ;
               return ;
            }
            TEVENT (Inflated exit - reacquired failed) ;
         } else {
            TEVENT (Inflated exit - complex egress) ;
         }
      }

      guarantee (_owner == THREAD, "invariant") ;

      // 声明ObjectWaiter变量
      ObjectWaiter * w = NULL ;
      int QMode = Knob_QMode ;

      // QMode == 2，并且cxq里面有排队的，直接唤醒cxq头部的节点
      if (QMode == 2 && _cxq != NULL) {
          // 从cxq头部拿到等待的线程，直接唤醒的干活
          w = _cxq ;
          assert (w != NULL, "invariant") ;
          assert (w->TState == ObjectWaiter::TS_CXQ, "Invariant") ;
          // 具体的唤醒
          ExitEpilog (Self, w) ;
          return ;
      }
      // QMode == 3，并且cxq不为null。  将cxq里的节点扔到EntryList尾部
      if (QMode == 3 && _cxq != NULL) {
          // 拿到cxq链表
          w = _cxq ;
          // 清空cxq链表里的东西
          for (;;) {
             assert (w != NULL, "Invariant") ;
             ObjectWaiter * u = (ObjectWaiter *) Atomic::cmpxchg_ptr (NULL, &_cxq, w) ;
             if (u == w) break ;
             w = u ;
          }
          assert (w != NULL              , "invariant") ;
          ObjectWaiter * q = NULL ;
          ObjectWaiter * p ;
          // 将ObjectWaiter设置为ENTER，要进入EntryList中
          for (p = w ; p != NULL ; p = p->_next) {
              guarantee (p->TState == ObjectWaiter::TS_CXQ, "Invariant") ;
              p->TState = ObjectWaiter::TS_ENTER ;
              p->_prev = q ;
              q = p ;
          }
          // 将cxq里的内容扔到EntryList的尾部
          ObjectWaiter * Tail ;
          for (Tail = _EntryList ; Tail != NULL && Tail->_next != NULL ; Tail = Tail->_next) ;
          if (Tail == NULL) {
              _EntryList = w ;
          } else {
              Tail->_next = w ;
              w->_prev = Tail ;
          }
      }
      // QMode == 4，并且cxq不为null。  将cxq里的节点扔到EntryList头部
      if (QMode == 4 && _cxq != NULL) {
          w = _cxq ;
          for (;;) {
             assert (w != NULL, "Invariant") ;
             ObjectWaiter * u = (ObjectWaiter *) Atomic::cmpxchg_ptr (NULL, &_cxq, w) ;
             if (u == w) break ;
             w = u ;
          }
          assert (w != NULL              , "invariant") ;

          ObjectWaiter * q = NULL ;
          ObjectWaiter * p ;
          for (p = w ; p != NULL ; p = p->_next) {
              guarantee (p->TState == ObjectWaiter::TS_CXQ, "Invariant") ;
              p->TState = ObjectWaiter::TS_ENTER ;
              p->_prev = q ;
              q = p ;
          }

          // Prepend the RATs to the EntryList
          if (_EntryList != NULL) {
              q->_next = _EntryList ;
              _EntryList->_prev = q ;
          }
          _EntryList = w ;
      }

      // 拿到EntryList
      w = _EntryList  ;
      // EntryList不为null
      if (w != NULL) {
          assert (w->TState == ObjectWaiter::TS_ENTER, "invariant") ;
          // 唤醒EntryList中的Node
          ExitEpilog (Self, w) ;
          return ;
      }

      // 如果EntryList没节点，看下cxq。
      w = _cxq ;
      // 如果cxq也为null，跳出这次循环，利用循环前面的操作结束当前唤醒操作
      if (w == NULL) continue ;

      // 如果cxq不为null，清空cxq。
      for (;;) {
          assert (w != NULL, "Invariant") ;
          ObjectWaiter * u = (ObjectWaiter *) Atomic::cmpxchg_ptr (NULL, &_cxq, w) ;
          if (u == w) break ;
          w = u ;
      }
      // 准备将cxq里的节点都扔到EntryList
      TEVENT (Inflated exit - drain cxq into EntryList) ;

      assert (w != NULL              , "invariant") ;
      assert (_EntryList  == NULL    , "invariant") ;


      // 如果QMode == 1，将cxq里的Node反转，扔到EntryList
      if (QMode == 1) {
         // QMode == 1 : drain cxq to EntryList, reversing order
         // We also reverse the order of the list.
         ObjectWaiter * s = NULL ;
         ObjectWaiter * t = w ;
         ObjectWaiter * u = NULL ;
         while (t != NULL) {
             guarantee (t->TState == ObjectWaiter::TS_CXQ, "invariant") ;
             t->TState = ObjectWaiter::TS_ENTER ;
             u = t->_next ;
             t->_prev = u ;
             t->_next = s ;
             s = t;
             t = u ;
         }
         _EntryList  = s ;
         assert (s != NULL, "invariant") ;
      } else {
         // QMode == 0 or QMode == 2，不等于1，直接将cxq帅到EntryList
         _EntryList = w ;
         ObjectWaiter * q = NULL ;
         ObjectWaiter * p ;
         for (p = w ; p != NULL ; p = p->_next) {
             guarantee (p->TState == ObjectWaiter::TS_CXQ, "Invariant") ;
             p->TState = ObjectWaiter::TS_ENTER ;
             p->_prev = q ;
             q = p ;
         }
      }

      if (_succ != NULL) continue;

      // 将cxq的节点扔到EntryList后，如果EntryList不为null
      w = _EntryList  ;
      if (w != NULL) {
          guarantee (w->TState == ObjectWaiter::TS_ENTER, "invariant") ;
          // 唤醒EntryList中的节点
          ExitEpilog (Self, w) ;
          return ;
      }
   }
}
```
