package com.lxy.decorator;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 14:28
 * @Description：
 */
class HtmlDecorator extends TextDecorator {
    public HtmlDecorator(Text text) {
        super(text);
    }

    @Override
    public String getContent() {
        return "<p>" + innerText.getContent() + "</p>";
    }
}
