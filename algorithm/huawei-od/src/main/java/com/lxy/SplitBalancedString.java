package com.lxy;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 16:41
 * @Description：
 */
public class SplitBalancedString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] charArray = sc.nextLine().toCharArray();
        int countX=0;
        int countY=0;
        int count=0;
        for (char c : charArray) {
            if(c=='X'){
                countX++;
            }else {
                countY++;
            }
            if(countX==countY){
                count++;
                countX=0;
                countY=0;
            }
        }
        System.out.println(count);
    }
}
