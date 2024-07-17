package com.lxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 16:48
 * @Description：
 */
public class ProxyFactory {
    private Object target;
    public ProxyFactory(Object target) {
        this.target = target;
    }
    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("Display placeholder while loading the image");
                return method.invoke(target, args);
            }
        });
    }
}
