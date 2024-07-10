package com.example.jvmcase.optimize;


//方法内联
public class Demo1 {

    //方法内联优化前
    private int add4(int x1, int x2, int x3, int x4) {

        return add2(x1, x2) + add2(x3, x4);
    }

    private int add3(int x1, int x2, int x3, int x4) {

        return add2(x1, x2) + add2(x3, x4);
    }

    private int add6(int x1, int x2, int x3, int x4) {

        return add2(x1, x2) + add2(x3, x4);
    }

    private int add7(int x1, int x2, int x3, int x4) {

        return add2(x1, x2) + add2(x3, x4);
    }

    private int add2(int x1, int x2) {
        return x1 + x2;
    }

//    //方法内联优化后
//    private int add4(int x1, int x2, int x3, int x4) {
//        return   x1 + x2 + x3 + x4;
//    }
}
