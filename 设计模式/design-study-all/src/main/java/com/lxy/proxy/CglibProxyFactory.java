package com.lxy.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 17:17
 * @Description：
 */
public class CglibProxyFactory implements MethodInterceptor{
    private Object target;
    public CglibProxyFactory(Object target) {
        this.target = target;
    }
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Display placeholder while loading the image");
        return methodProxy.invokeSuper(o, objects);
    }
}
