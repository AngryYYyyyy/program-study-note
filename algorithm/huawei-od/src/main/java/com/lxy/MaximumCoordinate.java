package com.lxy;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/10 16:14
 * @Description：
 */
public class MaximumCoordinate {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int luckyNumber = sc.nextInt();
        int[] steps = new int[n];
        for (int i = 0; i < n; i++) {
            steps[i] = sc.nextInt();
        }
        int max=getMaximumCoordinate(luckyNumber,steps);
        System.out.println(max);
    }

    private static int getMaximumCoordinate(int luckyNumber, int[] steps) {
        int max=0;
        int position = 0;
        for (int i = 0; i < steps.length; i++) {
            int step = steps[i];
            if (luckyNumber == step) {
                if(luckyNumber<0){
                    step--;
                }else if(luckyNumber>0){
                    step++;
                }
            }
            position += step;
            max=Math.max(max,position);
        }
        return max;
    }
}
