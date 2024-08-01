package com.lxy.decorator;

import org.junit.Test;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 14:29
 * @Description：
 */
public class TextTest {
    @Test
    public void test() {
        Text text = new PlainText("zhangsan");
        String content = text.getContent();
        System.out.println(content);

        Text text1 = new HtmlDecorator(text);
        System.out.println(text1.getContent());

        System.out.println(new EncryptDecorator(text).getContent());

        System.out.println(new HtmlDecorator(
                new EncryptDecorator(text)
        ).getContent());
    }

}