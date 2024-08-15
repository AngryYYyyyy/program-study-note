package com.lxy;


import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 8:17
 * @Description：
 */
public class MaximumNumberOfGemsThatCanBePurchased {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count=sc.nextInt();
        int[] gems = new int[count];
        for (int i = 0; i < count; i++) {
            gems[i] = sc.nextInt();
        }
        int money=sc.nextInt();
        int num=numberOfGems(gems,money);
        System.out.println(num);
    }

    private static int numberOfGems(int[] gems, int money) {
        int cost=0;
        int num=0;
        int maxNum=0;
        int L=0;
        int R=0;
        while (R < gems.length) {
            if(cost+gems[R]<=money){
                cost+=gems[R];
                num++;
                R++;
            }else {
                maxNum=Math.max(maxNum,num);
                cost-=gems[L];
                num--;
                L++;
            }

        }
        return maxNum;
    }
}
