package conclusion.serch.backtracking;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 20:15
 * @Description：
 */
public class Solution04 {
    static int maxMatches = 0;
    static int optimalCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] aStr = scanner.nextLine().split(" ");
        String[] bStr = scanner.nextLine().split(" ");
        Integer[] a = new Integer[aStr.length];
        Integer[] b = new Integer[bStr.length];

        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(aStr[i]);
            b[i] = Integer.parseInt(bStr[i]);
        }

        Arrays.sort(b); // 排序b数组以便比较
        permuteAndEvaluate(a, b, 0);

        // 输出0而不是1，当没有任何有效的匹配时
        if (maxMatches == 0) {
            System.out.println(0);
        } else {
            System.out.println(optimalCount);
        }
    }

    private static void permuteAndEvaluate(Integer[] a, Integer[] b, int start) {
        if (start == a.length) {
            int currentMatches = 0;
            for (int i = 0; i < a.length; i++) {
                if (a[i] > b[i]) {
                    currentMatches++;
                }
            }

            if (currentMatches > maxMatches) {
                maxMatches = currentMatches;
                optimalCount = 1;
            } else if (currentMatches == maxMatches && maxMatches > 0) {
                optimalCount++;
            }
            return;
        }

        for (int i = start; i < a.length; i++) {
            swap(a, start, i);
            permuteAndEvaluate(a, b, start + 1);
            swap(a, start, i);
        }
    }

    private static void swap(Integer[] array, int i, int j) {
        Integer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
