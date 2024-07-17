package com.lxy.proxy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 16:32
 * @Description：
 */
class RealImageNotImpl  {
    private String filePath;
    RealImageNotImpl() {}
    public RealImageNotImpl(String filePath) {
        this.filePath = filePath;
    }

    public void display() {
        System.out.println("Displaying " + filePath);
    }
}
