package com.lxy.proxy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 16:32
 * @Description：
 */
class RealImage implements Image {
    private String filePath;

    public RealImage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void display() {
        System.out.println("Displaying " + filePath);
    }
}
