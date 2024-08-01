package com.lxy.decorator;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 14:27
 * @Description：
 */
class PlainText implements Text {
    private String content;

    public PlainText(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}