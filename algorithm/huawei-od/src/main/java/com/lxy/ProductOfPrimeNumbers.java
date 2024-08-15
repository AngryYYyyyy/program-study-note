package com.lxy;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 9:13
 * @Description：
 */
public class ProductOfPrimeNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[]result=product(n);
        System.out.print(result[0]+" "+result[1]);
    }

    private static int[] product(int n) {
        int[] result=new int[2];
        result[0]=-1;
        result[1]=-1;
        for (int i = 2; i < n; i++) {
            if(n%i==0){
                int temp=n/i;
                if(isPrime(i)&&isPrime(temp)){
                    result[0]=i;
                    result[1]=temp;
                    break;
                }
            }
        }
        return result;
    }

    private static boolean isPrime(int n) {
        boolean flag=true;
        for(int i=2;i<n;i++) {
            if(n%i==0) {
                flag=false;
            }
        }
        return flag;
    }
}
