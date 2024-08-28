/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/26 17:48
 * @Description：
 */
public class Solution53 {
    public int maxSubArray(int[] nums) {
        int currentMax = nums[0];
        int globalMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            globalMax = Math.max(globalMax, currentMax);
        }
        return globalMax;
    }
}
