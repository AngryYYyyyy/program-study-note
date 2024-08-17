package jdk8;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/17 19:51
 * @Description：
 */
public class Lambda {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }).start();
        new Thread(() -> System.out.println("word")).start();
    }
}
