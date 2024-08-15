package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/9 16:02
 * @Description：
 */
public class Climber2 {
    public static int countPeaks(int[] heights, int maxEnergy) {
        int n = heights.length;
        int safePeaksCount = 0;
        // 寻找所有的山峰
        for (int i = 0; i < n; i++) {
            if (isPeak(heights, i)) {
                int left = i;
                int right = i;

                // 向左找到第一个0
                while (left > 0 && heights[left] != 0) {
                    left--;
                }

                // 向右找到第一个0
                while (right < n - 1 && heights[right] != 0) {
                    right++;
                }

                // 计算体力消耗
                int leftEnergy = Integer.MAX_VALUE;
                int rightEnergy = Integer.MAX_VALUE;
                if (heights[left] == 0) {
                    leftEnergy = calculateEnergyLeft(heights, left, i)+calculateEnergyRight(heights,i,left) ;
                }
                if (heights[right] == 0) {
                    rightEnergy = calculateEnergyLeft(heights, i, right)+calculateEnergyRight(heights,right,i);
                }
                int energy = Math.min(leftEnergy, rightEnergy);
                if (energy <= maxEnergy) {
                    safePeaksCount++;
                }
            }
        }
        return safePeaksCount;
    }

    private static int calculateEnergyLeft(int[] heights, int start, int end) {
        int energy = 0;
        /*j--->j+1*/
        for (int j = start; j < end; j++) {
            if (heights[j + 1] > heights[j]) {
                energy += 2 * (heights[j + 1] - heights[j]);  // 上坡消耗
            } else {
                energy += heights[j] - heights[j + 1];  // 下坡消耗
            }
        }
        return energy;
    }
    private static int calculateEnergyRight(int[] heights, int start, int end) {
        int energy = 0;
        /*j--->j-1*/
        for (int j = start; j > end; j--) {
            if (heights[j - 1] > heights[j]) {
                energy += 2 * (heights[j - 1] - heights[j]);  // 上坡消耗
            } else {
                energy += heights[j] - heights[j - 1];  // 下坡消耗
            }
        }
        return energy;
    }

    private static boolean isPeak(int[] heights, int i) {
        boolean isPeak = false;
        if (heights.length == 0) {
            return false;
        }
        if (heights.length == 1) {
            return heights[0] > 0;
        }
        if (i == 0) {
            return heights[0] > heights[1];
        }
        if (i == heights.length - 1) {
            return heights[heights.length - 1] > heights[heights.length - 2];
        }
        return heights[i] > heights[i - 1] && heights[i] > heights[i + 1];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] heights = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int maxEnergy = sc.nextInt();
        System.out.println(countPeaks(heights, maxEnergy));
    }
}
