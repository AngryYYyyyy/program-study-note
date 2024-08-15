package com.lxy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 14:53
 * @Description：
 */

import java.util.Arrays;
import java.util.Scanner;

public class DepartmentWorkload {
    // 判断每个月需要的最小人力 power ，能否在 M 个月内完成开发
    public static boolean check(int[] requirements, long power, int M) {
        int l=0;
        int r=requirements.length-1;
        int month=0;
        while(l<=r){
            if(requirements[l]+requirements[r]<=power){
                l++;
            }
            r--;
            month++;
        }
        return month<=M;
    }

    public static void main(String[] args) {
        /*输入*/
        Scanner sc = new Scanner(System.in);
        int M=sc.nextInt();
        sc.nextLine();
        int[] requirements = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(requirements);
        int low=requirements[requirements.length-1];
        int high=Arrays.stream(requirements).sum();
        while (low <= high) {
            int mid=(low+high)/2;
            if (check(requirements, mid, M)) {
                high = mid-1;
            }else {
                low=mid+1;
            }
        }
        System.out.println(low);
    }

}

