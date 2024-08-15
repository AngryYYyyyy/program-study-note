package com.lxy;


import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/7 21:09
 * @Description：
 */
public class GreedySinger {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        int N = scanner.nextInt();
        int[] days = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            days[i] = scanner.nextInt();
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0])); // Max heap
        for (int i = 0; i < N; i++) {
            int M = scanner.nextInt();
            int D = scanner.nextInt();
            heap.offer(new int[]{M, D});
        }
        int X = T - sum(days);
        int ans = 0;
        for (int i = 0; i < X; i++) {
            if (heap.isEmpty()) {
                break;
            }
            int[] city = heap.poll();
            int M = city[0];
            int D = city[1];
            ans += M;
            M -= D;
            if (M > 0) {
                heap.offer(new int[]{M, D});
            }
        }
        System.out.println(ans);
    }

    private static int sum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }
}
