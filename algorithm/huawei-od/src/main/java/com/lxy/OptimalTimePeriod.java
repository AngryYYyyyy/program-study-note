package com.lxy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 21:33
 * @Description：
 */
public class OptimalTimePeriod {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int minAverageLost = sc.nextInt();
        sc.nextLine();
        int[] failureRates = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        process(minAverageLost,failureRates);
    }
    private static void process(int minAverageLost, int[] arr) {
        int n=arr.length;
        int[] prefixSum=new int[n+1];
        /*前缀和：prefixSum[i]表示i及i之前的和*/
        /*[i,j]的区间和等于prefixSum[j+1]-prefixSum[i]*/
        for (int i = 0; i <n; i++) {
            prefixSum[i+1]=prefixSum[i]+arr[i];
        }
        LinkedList<String> list = new LinkedList<>();
        int maxLength=0;
        for (int l = 0; l < n; l++) {
            for (int r = l; r < n; r++) {
                int averageLost = prefixSum[r+1]-prefixSum[l];
                int length=r-l+1;
                if(averageLost<=minAverageLost*length) {
                    if(length>maxLength) {
                        maxLength=length;
                        list.clear();
                        list.add(l+"-"+r);
                    }else if(length==maxLength) {
                        list.add(l+"-"+r);
                    }
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("NULL");
        } else {
            list.forEach(e -> System.out.print(e + " "));
            System.out.println(); // 添加换行符以确保输出格式正确
        }

    }
}
