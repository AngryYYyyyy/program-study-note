package conclusion.serch.backtracking;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 16:23
 * @Description：
 */
public class Solution01 {
    private static int totalScore=0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] scores = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int abs=assignTeams(scores);
        System.out.println(abs);
    }
    private static int assignTeams(int[] scores) {
        for(int score:scores){
            totalScore+=score;
        }
        return backtracking(scores,0,0,0,0);
    }

    private static int backtracking(int[] scores, int i, int teamACount, int teamBCount, int teamASum) {
        if(i==scores.length) {
            return Math.abs(2*teamASum-totalScore);
        }
        int abs1=Integer.MAX_VALUE;
        int abs2=Integer.MAX_VALUE;
        if(teamACount<5) {abs1=backtracking(scores,i+1,teamACount+1,teamBCount,teamASum+scores[i]);}
        if(teamBCount<5) {abs2=backtracking(scores,i+1,teamACount,teamBCount+1,teamASum);}
        return Math.min(abs1,abs2);
    }
}
