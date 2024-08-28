import com.msb.threaddemo.GrabMethod2;

/**
 * synchronized 括号的时候
 */
public class TestSynchronized2 {
    public static void main(String[] args) {
        for (int i = 0;i<3;i++){
            GrabMethod2 g = new GrabMethod2();
            g.start();
        }
    }
}







