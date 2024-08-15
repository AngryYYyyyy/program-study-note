package com.lxy;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 8:44
 * @Description：
 */
public class ExhibitionGameGrouping {
    static int minDifference=Integer.MAX_VALUE;
    static int totalSum=0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer[] scores = new Integer[10];

        for (int i = 0; i < 10; i++) {
            scores[i] = sc.nextInt();
            totalSum+=scores[i];
        }
        group(scores);
        System.out.println(minDifference);
    }

    private static void group(Integer[] scores) {
        process(0,scores,0,0,0);
    }

    private static void process(int i, Integer[] scores, Integer currentTeamASum,Integer currentTeamACount,Integer currentTeamBCount) {
        if(i==scores.length){
            if(currentTeamACount==5&&currentTeamBCount==5){
                int difference = Math.abs(2*currentTeamASum-totalSum);
                minDifference=Math.min(minDifference,difference);
                return;
            }
        }
        if(currentTeamACount<5) process(i+1,scores,currentTeamASum+scores[i],currentTeamACount+1,currentTeamBCount);
        if(currentTeamBCount<5) process(i+1,scores,currentTeamASum,currentTeamACount,currentTeamBCount+1);
    }

}
