package conclusion.algorithm_basics.greedy.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 11:07
 * @Description：
 */
public class Solution03 {
    public int jump(int[] nums) {
        int position = nums.length - 1;
        int step = 0;
        while (position > 0) {
            for (int i = 0; i < nums.length - 1; i++) {
                if (i + nums[i] >= position) {
                    position = i;
                    step++;
                    break;
                }
            }
        }
        return step;
    }

    public int jump1(int[] nums) {
        int step = 0;
        int maxPosition = 0;
        int end=0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxPosition = Math.max(maxPosition, nums[i]+i);
            if(i==end){
                end=maxPosition;
                step++;
            }
        }
        return step;
    }
}
