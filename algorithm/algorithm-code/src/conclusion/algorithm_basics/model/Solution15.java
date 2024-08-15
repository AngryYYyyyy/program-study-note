package conclusion.algorithm_basics.model;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 14:22
 * @Description：
 */
public class Solution15 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt(); // 购买的物品价值（十进制）
        int n = scanner.nextInt(); // 幸运数字
        int m = scanner.nextInt(); // 使用的进制

        // 调用方法计算幸运数字的数量
        int count = countLuckyNumbers(k, n, m);
        System.out.print(count);

        scanner.close();
    }

    private static int countLuckyNumbers(int value, int luckyNumber, int base) {
        int count = 0;
        while (value > 0) {
            int digit = value % base;
            if (digit == luckyNumber) {
                count++;
            }
            value /= base;
        }
        return count;
    }
}
