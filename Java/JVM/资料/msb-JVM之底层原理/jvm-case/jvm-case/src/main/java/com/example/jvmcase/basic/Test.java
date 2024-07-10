package com.example.jvmcase.basic;

import com.example.jvmcase.domain.Worker;
import org.openjdk.jol.info.ClassLayout;

/**
 * @ClassName Test
 * @Title：
 * @Author: yzt
 * @Date: 2021/11/30 0030 15:32
 * @Description:TODO
 */

public class Test {

    public static void main(String []args) {
        Worker work = new Worker();
//        System.out.println( work.hashCode());
//        work.setId(1);
//        work.setPassword("viuehgahvhfszhvdszdv;ufsbivubfds");
        System.out.println(ClassLayout.parseInstance(work).toPrintable());
    }



}