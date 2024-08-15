package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 20:49
 * @Description：
 */
public class TransformMinimalString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] charArray = sc.nextLine().toCharArray();
        String minString = String.valueOf(charArray);
        for (int i = 0; i < charArray.length; i++) {
            for (int j = i+1; j < charArray.length; j++) {
                swap(charArray,i,j);
                String newString = String.valueOf(charArray);
                if(newString.compareTo(minString)<0){
                    minString = newString;
                }
                swap(charArray,i,j);
            }
        }
        System.out.println(minString);
    }
    public static void swap(char[]charArray,int i, int j) {
        char tmp=charArray[i];
        charArray[i]=charArray[j];
        charArray[j]=tmp;
    }
}
