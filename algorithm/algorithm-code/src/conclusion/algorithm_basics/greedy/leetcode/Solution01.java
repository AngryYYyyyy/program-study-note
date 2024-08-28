package conclusion.algorithm_basics.greedy.leetcode;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 10:19
 * @Description：
 */
public class Solution01 {
    public int maxProfit(int[] prices) {
        int minPrice=Integer.MAX_VALUE;
        int maxProfit=0;
        for (int i = 0; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        return maxProfit;
    }
}
