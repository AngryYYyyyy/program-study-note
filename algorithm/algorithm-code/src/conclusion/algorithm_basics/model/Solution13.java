package conclusion.algorithm_basics.model;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 12:13
 * @Description：
 */
public class Solution13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();  // 总数字个数
        int rows = scanner.nextInt();  // 行数
        scanner.close();

        // 计算列数，每行数字个数尽可能少，同时能容纳所有数字
        int cols = (int) Math.ceil(n / (double) rows);

        // 初始化矩阵
        String[][] matrix = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = "*";
            }
        }

        // 螺旋填充数字
        int num = 1;
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;
        while (num <= n) {
            for (int i = left; i <= right && num <= n; i++) {
                matrix[top][i] = String.valueOf(num++);
            }
            top++;
            for (int i = top; i <= bottom && num <= n; i++) {
                matrix[i][right] = String.valueOf(num++);
            }
            right--;
            for (int i = right; i >= left && num <= n; i--) {
                matrix[bottom][i] = String.valueOf(num++);
            }
            bottom--;
            for (int i = bottom; i >= top && num <= n; i--) {
                matrix[i][left] = String.valueOf(num++);
            }
            left++;
        }

        // 输出矩阵
        for (String[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                if (j > 0) System.out.print(" ");
                System.out.print(row[j]);
            }
            System.out.println();
        }
    }
}
