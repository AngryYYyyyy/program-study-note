package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/7 19:11
 * @Description：
 */
public class HeightSorting {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int self = sc.nextInt();
        int count = sc.nextInt();
        Height[] heights = new Height[count];
        for (int i = 0; i < count; i++) {
            heights[i] = new Height();
            int height = sc.nextInt();
            heights[i].height = height;
            heights[i].heightDifference=Math.abs(height-self);
        }
        int[]result=heightSort(self,heights);
        for (int e:result){
            System.out.print(e+" ");
        }
    }
    public static class Height{
        public int height;
        public int heightDifference;

    }
    private static int[] heightSort(int self, Height[] heights) {
        Arrays.sort(heights,((o1, o2) -> o1.heightDifference== o2.heightDifference? o1.height - o2.height: o1.heightDifference-o2.heightDifference));
        int[] result = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            result[i] = heights[i].height;
        }
        return result;
    }
}
