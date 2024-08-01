package com.lxy.proxy;

import org.junit.Test;




/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 16:34
 * @Description：
 */
public class ProxyImageTest {
    @Test
    public void testProxy() {
        RealImage realImage = new RealImage("C:\\Users\\Administrator\\Desktop\\1.jpg");
        ProxyImage proxyImage = new ProxyImage(realImage);
        proxyImage.display();
    }

    @Test
    public void testProxy2() {
        RealImage realImage = new RealImage("C:\\Users\\Administrator\\Desktop\\1.jpg");
        ProxyImage proxyImage = new ProxyImage(realImage);
        proxyImage.display();
    }

    @Test
    public void testProxy3() {
        ProxyFactory proxyFactory = new ProxyFactory(new RealImage("C:\\Users\\Administrator\\Desktop\\1.jpg"));
        Image proxyImage = (Image) proxyFactory.getProxy();
        proxyImage.display();
    }
    @Test
    public void testProxy4() {
        CglibProxyFactory cglibProxyFactory = new CglibProxyFactory(new RealImageNotImpl("C:\\Users\\Administrator\\Desktop\\1.jpg"));
        RealImageNotImpl proxyImage = (RealImageNotImpl) cglibProxyFactory.getProxy();
        proxyImage.display();
    }
}