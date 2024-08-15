package com.lxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 17:50
 * @Description：
 */
public class GreedyLumberjack {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();;  // 木材长度
        List<Integer> cuts = maxProfitCuts(X);
        cuts.sort(Integer::compareTo);
        for (int cut : cuts) {
            System.out.print(cut + " ");
        }
    }

    public static List<Integer> maxProfitCuts(int X) {
        List<Integer> result = new ArrayList<>();
        while (X > 0) {
            if (X == 4) { //4=2*2并且>3*1，无需切割
                result.add(4);
                break;
            }
            if (X >= 3) {
                result.add(3);
                X -= 3;
            } else {
                result.add(X);
                break;
            }
        }
        return result;
    }
}
