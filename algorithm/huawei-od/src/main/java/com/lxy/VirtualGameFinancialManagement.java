package com.lxy;


import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/7 14:26
 * @Description：
 */
public class VirtualGameFinancialManagement {
    private static int[] returns;
    private static int[] risks;
    private static int[] maxInvestments;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int N = scanner.nextInt();
        int X = scanner.nextInt();
        returns = new int[m];
        risks = new int[m];
        maxInvestments = new int[m];
        for (int i = 0; i < m; i++) {
            returns[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            risks[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            maxInvestments[i] = scanner.nextInt();
        }

        int[] optimalInvestments = findOptimalInvestments(m, N, X);
        for (int investment : optimalInvestments) {
            System.out.print(investment + " ");
        }
    }

    public static int[] findOptimalInvestments(int m, int N, int X) {
        int[] bestInvestment = new int[m];
        int maxProfit = 0;

        // 单个产品投资
        for (int i = 0; i < m; i++) {
            if (risks[i] <= X && maxInvestments[i] <= N) {
                int profit = maxInvestments[i] * returns[i];
                if (profit > maxProfit) {
                    maxProfit = profit;
                    bestInvestment = new int[m];  // 重置为全0
                    bestInvestment[i] = maxInvestments[i];
                }
            }
        }

        // 双产品组合投资
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                int combinedRisk = risks[i] + risks[j];
                if (combinedRisk <= X) {
                    for (int investI = 0; investI <= Math.min(maxInvestments[i], N); investI++) {
                        int investJ = Math.min(maxInvestments[j], N - investI);
                        int profit = investI * returns[i] + investJ * returns[j];
                        if (profit > maxProfit) {
                            maxProfit = profit;
                            bestInvestment = new int[m];
                            bestInvestment[i] = investI;
                            bestInvestment[j] = investJ;
                        }
                    }
                }
            }
        }

        return bestInvestment;
    }
}
