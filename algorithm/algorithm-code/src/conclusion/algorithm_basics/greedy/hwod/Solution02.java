package conclusion.algorithm_basics.greedy.hwod;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 22:46
 * @Description：
 */
/*在一款虚拟游戏中生活，你必须进行投资以增强在虚拟游戏中的资产以免被淘汰出局。现有一家 Bank，它提供有若干理财产品 m，风险及投资回报不同，你有 N（元）进行投资，能接受的总风险值为 X。

你要在可接受范围内选择最优的投资方式获得最大回报。

说明：

1、在虚拟游戏中，每项投资风险值相加为总风险值；

2、在虚拟游戏中，最多只能投资 2 个理财产品；

3、在虚拟游戏中，最小单位为整数，不能拆分为小数； 投资额*回报率=投资回报*/
public class Solution02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();  // 产品数量
        int N = sc.nextInt();  // 总投资额
        int X = sc.nextInt();  // 可接受的总风险值

        int[] returns = new int[m];
        int[] risks = new int[m];
        int[] maxInvests = new int[m];

        for (int i = 0; i < m; i++) {
            returns[i] = sc.nextInt();
        }
        for (int i = 0; i < m; i++) {
            risks[i] = sc.nextInt();
        }
        for (int i = 0; i < m; i++) {
            maxInvests[i] = sc.nextInt();
        }

        int[] bestInvestment = new int[m];
        int maxTotalReturn = 0;

        // 单产品最优选择
        for (int i = 0; i < m; i++) {
            if (risks[i] <= X) {
                int invest = Math.min(maxInvests[i], N);
                int totalReturn = invest * returns[i];
                if (totalReturn > maxTotalReturn) {
                    Arrays.fill(bestInvestment, 0);
                    bestInvestment[i] = invest;
                    maxTotalReturn = totalReturn;
                }
            }
        }

        // 双产品组合选择
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                if (risks[i] + risks[j] <= X) {
                    for (int investI = 0; investI <= Math.min(maxInvests[i], N); investI++) {
                        int investJ = Math.min(maxInvests[j], N - investI);
                        int totalReturn = investI * returns[i] + investJ * returns[j];
                        if (totalReturn > maxTotalReturn) {
                            Arrays.fill(bestInvestment, 0);
                            bestInvestment[i] = investI;
                            bestInvestment[j] = investJ;
                            maxTotalReturn = totalReturn;
                        }
                    }
                }
            }
        }

        // 输出最优投资组合
        for (int invest : bestInvestment) {
            System.out.print(invest + " ");
        }
        System.out.println();
    }
}
