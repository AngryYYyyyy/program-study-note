package conclusion.algorithm_basics.model;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 10:09
 * @Description：
 */
public class Solution08 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] heights = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int maxStamina = sc.nextInt();
        int climbablePeaks =countClimbablePeaks(heights, maxStamina);
        System.out.println(climbablePeaks);
    }
    public static int countClimbablePeaks(int[] heights, int maxStamina) {
        int count  = 0;
        // 寻找所有的山峰
        for (int i = 0; i < heights.length; i++) {
            if (isPeak(heights, i)) {
                int staminaNeeded = calculateStamina(heights, i);
                if(staminaNeeded==-1) continue;
                if (staminaNeeded <= maxStamina) {
                    count++;
                }
            }
        }
        return count ;
    }

    private static int calculateStamina(int[] heights, int peakIndex) {
        int staminaCostLeft = 0;
        int staminaCostRight = 0;
        boolean canClimbFromLeft = true;
        boolean canClimbFromRight = true;

        // 找到左边最接近的0,并计算从左边上下山的耐力
        int left = peakIndex;
        while (left > 0 ) {
            int heightDiff = Math.abs(heights[left] - heights[left - 1]);
            staminaCostLeft += 3 * heightDiff; // 上山和下山的耐力消耗合并计算
            if(heights[left - 1] == 0){
                break;
            }
            left--;
        }
        // 检查左侧是否有0作为起点
        if (left == 0 && heights[left] != 0) {
            canClimbFromLeft = false; // 如果左侧边界不是0，不能从左侧开始
        }

        // 找到右边最接近的0,并计算从右边的耐力
        int right = peakIndex;
        while (right < heights.length - 1 ) {
            int heightDiff = Math.abs(heights[right] - heights[right + 1]);
            staminaCostRight += 3 * heightDiff; // 上山和下山的耐力消耗合并计算
            if(heights[right + 1] == 0){
                break;
            }
            right++;
        }
        // 检查右侧是否有0作为起点
        if (right == heights.length - 1 && heights[right] != 0) {
            canClimbFromRight = false; // 如果右侧边界不是0，不能从右侧开始
        }

        // 判断最小耐力消耗的路线
        if (canClimbFromLeft && canClimbFromRight) {
            return Math.min(staminaCostLeft, staminaCostRight);
        } else if (canClimbFromLeft) {
            return staminaCostLeft;
        } else if (canClimbFromRight) {
            return staminaCostRight;
        }
        return -1; // 如果两边都不是0，表示这个山峰不可攀登
    }


    private static boolean isPeak(int[] heights, int i) {
        if(heights.length==1&&heights[0]==0) return false;
        return (i==0||heights[i] > heights[i - 1])&&(i==heights.length-1||heights[i] > heights[i+1]);
    }
}
