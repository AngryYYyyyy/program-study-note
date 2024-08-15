package com.lxy;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 9:47
 * @Description：
 */
public class EfficientFreight {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int wa = sc.nextInt();
        int wb = sc.nextInt();
        int wt = sc.nextInt();
        int pa = sc.nextInt();
        int pb = sc.nextInt();
        int profit=maxProfit(wa,wb,wt,pa,pb);
        System.out.println(profit);
    }
    public static int maxProfit(int wa, int wb, int wt, int pa, int pb) {
        int max=0;
        for (int i = 1; i <= wt/wa; i++) {
            for (int j = 1; j <= wt/wb; j++) {
                int weight=wa*i+wb*j;
                if(weight==wt){
                    System.out.println(i+":"+j);
                    int profit=pa*i+pb*j;
                    max=Math.max(max,profit);
                }
            }
        }
        return max;
    }
}
