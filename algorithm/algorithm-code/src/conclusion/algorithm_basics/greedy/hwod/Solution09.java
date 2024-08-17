package conclusion.algorithm_basics.greedy.hwod;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 9:27
 * @Description：
 */
public class Solution09 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();;  // 木材长度
        List<Integer> cuts = maxProfitCuts(X);
        cuts.sort(Integer::compareTo);
        for (int cut : cuts) {
            System.out.print(cut + " ");
        }
    }

    public static List<Integer> maxProfitCuts(int X) {
        List<Integer> result = new ArrayList<>();
        while (X > 0) {
            if (X == 4) { //4=2*2并且>3*1，无需切割
                result.add(4);
                break;
            }
            if (X >= 3) {
                result.add(3);
                X -= 3;
            } else {
                result.add(X);
                break;
            }
        }
        return result;
    }
}
