import com.msb.threaddemo.GrabMethod;

/**
 * 得出结论：并不是所有的在方法上加synchronized技能保证同步执行
 */
public class TestSynchronized1 {
    /**
     * 模拟10个抢单的请求
     * @param args
     */
    public static void main(String[] args) {
        for (int i=0;i<3;i++){
            GrabMethod g = new GrabMethod();
            g.start();
        }
    }
}



