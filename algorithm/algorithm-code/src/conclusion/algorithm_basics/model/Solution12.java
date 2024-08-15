package conclusion.algorithm_basics.model;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 12:04
 * @Description：
 */
public class Solution12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] levels=new int[n];
        for (int i = 0; i < n; i++) {
            levels[i]=sc.nextInt();
        }
        long count=countTriplets(n,levels);
        System.out.println(count);
    }
    private static long countTriplets(int n, int[] level) {
        long result = 0;
        for (int j = 1; j < n - 1; j++) {
            // Count elements less and greater than level[j] on the left side
            int lessLeft = 0, greaterLeft = 0;
            for (int i = 0; i < j; i++) {
                if (level[i] < level[j]) {
                    lessLeft++;
                }
                if (level[i] > level[j]) {
                    greaterLeft++;
                }
            }

            // Count elements less and greater than level[j] on the right side
            int lessRight = 0, greaterRight = 0;
            for (int k = j + 1; k < n; k++) {
                if (level[k] < level[j]) {
                    lessRight++;
                }
                if (level[k] > level[j]) {
                    greaterRight++;
                }
            }

            // Compute combinations for both increasing and decreasing sequences
            result += (long) lessLeft * greaterRight + (long) greaterLeft * lessRight;
        }

        return result;
    }
}
