package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/9 15:46
 * @Description：
 */
public class Climber1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] heights = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int num=mountainPeak(heights);
        System.out.println(num);
    }

    private static int mountainPeak(int[] heights) {
        int num=0;
        if(heights.length==0){return num;}
        if(heights.length==1){
            return heights[0]>0?1:0;
        }
        if(heights.length==2){
            return heights[0]==heights[1]?0:1;
        }

        if(heights[0]>heights[1]){
            num++;
        }
        if(heights[heights.length-1]>heights[heights.length-2]){
            num++;
        }

        for(int i=1;i<heights.length-1;i++){
            if(heights[i-1]<heights[i]&&heights[i+1]<heights[i]){
                num++;
            }
        }
        return num;
    }
}
