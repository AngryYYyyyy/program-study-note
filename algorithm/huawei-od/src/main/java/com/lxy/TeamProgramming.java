package com.lxy;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 10:18
 * @Description：
 */
public class TeamProgramming {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] levels=new int[n];
        for (int i = 0; i < n; i++) {
            levels[i]=sc.nextInt();
        }
        int result=assemble(levels);
        System.out.println(result);
    }
    public static int assemble(int[] levels) {
        int n = levels.length;
        if(n<3){
            return 0;
        }
        int num=0;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                for(int k=j+1;k<n;k++){
                    if(levels[i]>levels[j] && levels[j]>levels[k]||levels[i]<levels[j]&&levels[j]<levels[k]){
                        num++;
                    }
                }
            }
        }
        return num;
    }
}
