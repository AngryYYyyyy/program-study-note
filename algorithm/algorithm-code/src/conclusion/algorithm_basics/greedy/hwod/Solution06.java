package conclusion.algorithm_basics.greedy.hwod;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 23:27
 * @Description：
 */
public class Solution06 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] desk = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int count = 0;
        int n = desk.length;
        int i = 0;
        // 特殊情况：整个数组都是0
        boolean allZero = true;
        for (int seat : desk) {
            if (seat != 0) {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            System.out.println((n + 1) / 2);
            return;
        }

        // 一般情况处理
        while (i < n) {
            if (desk[i] == 0) {
                int start = i;
                while (i < n && desk[i] == 0) {
                    i++;
                }
                int end = i;
                // 计算这段空位中可以放置的人数
                if (start == 0 || end == n) {
                    // 如果这段空位到达了数组的开头或结尾
                    count += (end - start) / 2;
                } else {
                    // 两端都是1的情况，两边都被占据
                    count += (end - start - 1) / 2;
                }
            } else {
                i++;
            }
        }
        System.out.println(count);
    }
}
