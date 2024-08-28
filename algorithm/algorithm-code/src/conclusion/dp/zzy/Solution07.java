package conclusion.dp.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 22:27
 * @Description：
 */
public class Solution07 {
    public static int knapsack(int[] weights, int[] values, int bag) {
        if (weights.length == 0) return 0;
        return func(0, weights, values, bag);
    }

    private static int func(int i, int[] weights, int[] values, int restBag) {
        if (i == weights.length) {
            return 0;
        }
        int p1 = 0;
        if (restBag - weights[i] >= 0) {
            p1 = func(i + 1, weights, values, restBag - weights[i]) + values[i];
        }
        int p2 = func(i + 1, weights, values, restBag);
        return Math.max(p1, p2);
    }

    public static int dp(int[] weights, int[] values, int bag) {
        if (weights.length == 0) return 0;
        int n = weights.length;
        int[][] dp = new int[n + 1][bag + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int restBag = 0; restBag <= bag; restBag++) {

                int p1 = 0;
                if (restBag - weights[i] >= 0) {
                    p1 = dp[i + 1][restBag - weights[i]] + values[i];
                }
                int p2 = dp[i + 1][restBag];
                dp[i][restBag] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }
    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(knapsack(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }
}
