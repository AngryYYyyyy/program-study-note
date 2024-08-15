package com.lxy;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 16:49
 * @Description：
 */
public class SplitThePizza {
    public static int[][] dpA;
    public static int[][] dpB;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();  // 披萨块数，奇数个
        int[] slices = new int[N];
        dpA=new int[N][N];
        dpB=new int[N][N];
        for (int i = 0; i < N; i++) {
            slices[i] = scanner.nextInt();
        }

        int maxPizza = 0;
        // 从每个位置开始尝试，模拟环状结构
        for (int i = 0; i < N; i++) {
            maxPizza = Math.max(maxPizza, choose(slices,  (i-1+N)%N,(i+1)%N,false)+slices[i]);
        }
        System.out.println(maxPizza);
        scanner.close();
    }

    //上一个选择了index，这轮选什么
    /*8 2 10 5 7*/
    private static int choose(int[] slices,int prev,int next,boolean isChiHuoTurn) {
        int n=slices.length;
        if(prev == next) {return slices[prev];}
        if(isChiHuoTurn) {
            if(dpA[prev][next]!=0){return dpA[prev][next];}
            dpA[prev][next]=Math.max(choose(slices,(prev-1+n)%n,next,false)+slices[prev],choose(slices,prev,(next+1)%n,false)+slices[next]);
            return dpA[prev][next];
        }else {
            if(dpB[prev][next]!=0){return dpB[prev][next];}
            if(slices[prev]<slices[next]) {
                dpB[prev][next]=choose(slices,prev,(next+1)%n,true);
                return dpB[prev][next];
            }else {
                dpB[prev][next]=choose(slices,(prev-1+n)%n,next,true);
                return dpB[prev][next];
            }
        }
    }
}
