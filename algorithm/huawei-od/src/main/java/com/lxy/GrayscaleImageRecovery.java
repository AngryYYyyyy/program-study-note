package com.lxy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 18:48
 * @Description：
 */
public class GrayscaleImageRecovery {
    public static class Grayscale{
        public int value;
        public int count;
        public Grayscale(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int col = sc.nextInt();
        String[] strings = sc.nextLine().split(" ");
        int[] array = Arrays.stream(Arrays.copyOfRange(strings, 1, strings.length)).mapToInt(Integer::parseInt).toArray();;
        int m=sc.nextInt();
        int n=sc.nextInt();
        LinkedList<Grayscale> list = new LinkedList<>();
        for (int i = 0; i < array.length; i+=2) {
            list.add(new Grayscale(array[i], array[i+1]));
        }
        int[][] matrix = new int[row][col];
        recovery(list,matrix);
        System.out.println(matrix[m][n]);
    }

    private static void recovery(LinkedList<Grayscale> list, int[][] matrix) {
        int row=matrix.length;
        int col=matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Grayscale g = list.getFirst();
                matrix[i][j] = g.value;
                g.count--;
                if (g.count == 0) {
                    list.removeFirst();
                }
            }
        }
    }
}
