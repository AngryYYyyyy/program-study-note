package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 21:18
 * @Description：
 */
public class ConveyorBeltSushi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] prices = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n=prices.length;
        int[] realPrices = new int[n];
        for (int i = 0; i < n; i++) {
            realPrices[i] = prices[i];
            for (int j = (i+1)%n; j!=i; j=(j+1)%n) {
                if(prices[i]>prices[j]){
                    realPrices[i] += prices[j];
                    break;
                }
            }
        }
        for(int e:realPrices){
            System.out.print(e+" ");
        }
    }
}
