package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 18:21
 * @Description：
 */
public class StopAndFindAParkingSpace {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] parks = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int park=findPark(parks);
        System.out.println(park);
    }

    private static int findPark(int[] parks) {
        int i=0;
        int ans=0;
        while(i<parks.length){
            int len=0;
            while(i<parks.length && parks[i]==0){
                i++;
                len++;
            }
            if(i==len||i==parks.length){
                ans=Math.max(ans,len);
            }else{
                int distance=(int)Math.ceil(len/(double)2);
                ans=Math.max(ans,distance) ;
            }
            i++;
        }
        return ans;
    }
}
