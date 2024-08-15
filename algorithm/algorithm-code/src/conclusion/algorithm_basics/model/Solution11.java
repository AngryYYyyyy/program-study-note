package conclusion.algorithm_basics.model;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 11:49
 * @Description：
 */
public class Solution11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int wa = sc.nextInt();
        int wb = sc.nextInt();
        int wt = sc.nextInt();
        int pa = sc.nextInt();
        int pb = sc.nextInt();
        int profit=calculateMaxProfit(wa,wb,wt,pa,pb);
        profit=profit==Integer.MAX_VALUE?0:profit;
        System.out.println(profit);
    }
    public static int calculateMaxProfit(int wa, int wb, int wt, int pa, int pb) {
        int maxProfit=Integer.MIN_VALUE;
        for (int a  = 1; a  <= wt/wa; a++) {
            int remainingWeight = wt - a * wa;
            if (remainingWeight % wb == 0) {
                int b = remainingWeight / wb;
                if (b > 0) { // 确保货物B至少有一个
                    int currentProfit = a * pa + b * pb;
                    maxProfit = Math.max(maxProfit, currentProfit);
                }
            }
        }
        return maxProfit;
    }
}
