package com.lxy.decorator;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 14:29
 * @Description：
 */
class EncryptDecorator extends TextDecorator {
    public EncryptDecorator(Text text) {
        super(text);
    }

    @Override
    public String getContent() {
        return new StringBuilder(innerText.getContent()).reverse().toString();
    }
}
