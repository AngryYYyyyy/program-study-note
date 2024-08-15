package com.lxy;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/9 10:04
 * @Description：
 */
public class StringSplitting {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        sc.nextLine();
        String[] strings = sc.nextLine().split("-");
        String s=stringSplit(strings,k);
        System.out.println(s);
    }

    private static String stringSplit(String[] strings,int k) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < strings.length; i++) {
            builder.append(strings[i]);
        }
        LinkedList<String> list = handleSubstrings(builder.toString(), k);
        builder.delete(0, builder.length());
        builder.append(strings[0]);
        for(String s : list) {
            builder.append("-").append(s);
        }
        return builder.toString();
    }

    private static  LinkedList<String> handleSubstrings(String string, int k) {
        char[] charArray = string.toCharArray();
        LinkedList<String> list = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            builder.append(charArray[i]);
            if((i+1)%k==0){
                String s = caseConversion(builder.toString());
                list.add(s);
                builder.delete(0,builder.length());
            }
        }
        if(!builder.isEmpty()){
            String s = caseConversion(builder.toString());
            list.add(s);
        }
        return list;
    }


    public static String caseConversion(String str){
        char[] arr = str.toCharArray();
        int count1=0;
        int count2=0;
        for(char c:arr){
            if(c>='a'&&c<='z'){
                count1++;
            }
            if(c>='A'&&c<='Z'){
                count2++;
            }
        }
        if(count1>count2){
            return str.toLowerCase();
        } else if(count1<count2){
            return str.toUpperCase();
        }
        return str;
    }
}
