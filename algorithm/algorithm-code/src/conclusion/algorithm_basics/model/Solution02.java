package conclusion.algorithm_basics.model;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 23:59
 * @Description：
 */
public class Solution02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n=sc.nextInt();
        int[] array1 = new int[n];
        for (int i = 0; i < n; i++) {
            array1[i] = sc.nextInt();
        }
        int m = sc.nextInt();
        int[] array2 = new int[m];
        for (int i = 0; i < m; i++) {
            array2[i] = sc.nextInt();
        }
        int k = sc.nextInt();
        int min = kSmallestPairs(array1, array2, k);
        System.out.println(min);
    }

    private static int kSmallestPairs(int[] array1, int[] array2, int k) {
        if (array1.length == 0 || array2.length == 0) return 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int sum=0;
        for (int value1 : array1) {
            for (int value2 : array2) {
                heap.add(value1 + value2);
            }
        }
        while (k-->0&&!heap.isEmpty()) {
            sum+= heap.poll();
        }
        return sum;
    }
}
