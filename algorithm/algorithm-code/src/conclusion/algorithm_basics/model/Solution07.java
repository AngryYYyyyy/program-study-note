package conclusion.algorithm_basics.model;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 9:56
 * @Description：
 */
public class Solution07 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] heights = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int peakCount=countPeaks(heights);
        System.out.println(peakCount);
    }

    private static int countPeaks(int[] heights) {
        if(heights.length==1 && heights[0]==0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < heights.length; i++) {
            // 判断是否是山峰
            // 对于数组的边界，只需要比较一侧的邻居。
            // 并且如果只有一个height，且不为0，则同时满足i==0&&i == heights.length - 1，count也会++
            if ((i == 0 || heights[i] > heights[i - 1]) && (i == heights.length - 1 || heights[i] > heights[i + 1])) {
                count++;
            }
        }
        return count;
    }
}
