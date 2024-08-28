import com.msb.threaddemo.GrabMethod3;

/**
 * synchronized 括号的时候
 */
public class TestSynchronized3 {
    public static void main(String[] args) {
        for (int i = 0;i<3;i++){
            GrabMethod3 g = new GrabMethod3();
            g.start();
        }
    }
}







