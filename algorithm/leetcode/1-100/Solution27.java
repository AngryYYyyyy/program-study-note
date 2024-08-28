/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/26 11:13
 * @Description：
 */
public class Solution27 {
    public int removeElement(int[] nums, int val) {
        int ni=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=val){
                nums[ni++]=nums[i];
            }
        }
        return ni;
    }
}
