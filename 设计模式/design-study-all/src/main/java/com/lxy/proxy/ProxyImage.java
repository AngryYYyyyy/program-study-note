package com.lxy.proxy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 16:33
 * @Description：
 */
class ProxyImage implements Image {
    private RealImage realImage;

    public ProxyImage(RealImage realImage) {
        this.realImage = realImage;
    }

    @Override
    public void display() {
        System.out.println("Display placeholder while loading the image");
        realImage.display();
    }
}
