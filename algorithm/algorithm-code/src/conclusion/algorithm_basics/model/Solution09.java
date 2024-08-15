package conclusion.algorithm_basics.model;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 10:56
 * @Description：
 */
public class Solution09 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int luckyNumber = sc.nextInt();
        int[] steps = new int[n];
        for (int i = 0; i < n; i++) {
            steps[i] = sc.nextInt();
        }
        int result=getMaxCoordinate(luckyNumber,steps);
        System.out.println(result);
    }

    private static int getMaxCoordinate(int luckyNumber, int[] steps) {
        int max=0;
        int position = 0;
        for (int i = 0; i < steps.length; i++) {
            int step = steps[i];
            if(step==0) continue;
            if (luckyNumber == step) {
                step = Integer.signum(luckyNumber) == -1 ? step-1 : step+1;
            }
            position += step;
            max=Math.max(max,position);
        }
        return max;
    }
}
