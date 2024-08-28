package conclusion.algorithm_basics.greedy.leetcode;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 10:28
 * @Description：
 */
public class Solution02 {
    public boolean canJump(int[] nums) {
        int step=1;
        for (int i = nums.length-2; i >=0 ; i--) {
            if(nums[i]>=step){
                step=0;
            }
            step++;
        }
        return step==1;
    }
    public boolean canJump1(int[] nums) {
        int maxPosition=0;
        for (int i = 0; i <nums.length ; i++) {
            if(i<=maxPosition){
                maxPosition = Math.max(maxPosition, nums[i]+i);
                if(maxPosition>=nums.length-1){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.canJump(new int[]{3,2,1,0,4}));
    }
}
