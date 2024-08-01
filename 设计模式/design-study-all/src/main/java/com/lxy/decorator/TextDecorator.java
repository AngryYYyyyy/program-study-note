package com.lxy.decorator;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 14:27
 * @Description：
 */
abstract class TextDecorator implements Text {
    protected Text innerText;

    public TextDecorator(Text text) {
        this.innerText = text;
    }
}
