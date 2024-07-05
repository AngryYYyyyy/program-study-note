package com.example.jvmcase.reference;

import com.example.jvmcase.domain.Worker;
import org.hibernate.jdbc.Work;

import java.lang.ref.SoftReference;

/**
 * @ClassName SoftReferenceDemo
 * @Title：
 * @Author: yzt
 * @Date: 2020/12/12 0012 15:26
 * @Description:TODO
 */

public class SoftReferenceDemo {
    public static void main(String[] args) {
        //。。。一堆业务代码

        Worker a = new Worker();
//。。业务代码使用到了我们的Worker实例

        // 使用完了a，将它设置为soft 引用类型，并且释放强引用；
        SoftReference sr = new SoftReference(a);
        a = null;
//这个时候他是有可能执行一次GC的
        System.gc();

        // 下次使用时
        if (sr != null) {
            a = (Worker) sr.get();
            System.out.println(a );
        } else {
            // GC由于内存资源不足，可能系统已回收了a的软引用，
            // 因此需要重新装载。
            a = new Worker();
            sr = new SoftReference(a);
        }
    }


}
