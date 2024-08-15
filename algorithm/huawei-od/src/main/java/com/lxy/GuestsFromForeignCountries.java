package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 19:13
 * @Description：
 */
public class GuestsFromForeignCountries {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int value = sc.nextInt();
        String lucky = sc.next();
        int base = sc.nextInt();
        String basedValue = Integer.toString(value, base);
        int index=0;
        int result=0;
        while((index=basedValue.indexOf(lucky,index))!=-1){
            result++;
            index+=lucky.length();
        }

        System.out.println(result);
    }

}
