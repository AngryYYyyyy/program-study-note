package conclusion.serch.bfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 行数
        int m = sc.nextInt(); // 列数
        int k = sc.nextInt(); // 数位之和的最大允许值
        sc.close();
        int maxTreasure = treasureHunt(n, m, k);
        System.out.println(maxTreasure);
    }

    private static int treasureHunt(int n, int m, int k) {
        if (!isFeasible(0, 0, k)) return 0; // 检查起点是否安全

        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int i = current[0];
            int j = current[1];
            count++; // 访问该方格，增加黄金计数

            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : directions) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                if (isValid(ni, nj, n, m) && !visited[ni][nj] && isFeasible(ni, nj, k)) {
                    visited[ni][nj] = true;
                    queue.offer(new int[]{ni, nj});
                }
            }
        }
        return count;
    }

    private static boolean isValid(int i, int j, int n, int m) {
        return i >= 0 && i < n && j >= 0 && j < m;
    }

    private static boolean isFeasible(int i, int j, int k) {
        return getDigitSum(i) + getDigitSum(j) <= k;
    }

    private static int getDigitSum(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
