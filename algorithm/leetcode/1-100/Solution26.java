import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/26 10:52
 * @Description：
 */
public class Solution26 {
    public int removeDuplicates(int[] nums) {
        int ni=0;
        for(int i=0;i<nums.length;i++){
            if(nums[ni]!=nums[i]){
                nums[++ni]=nums[i];
            }
        }
        return ni+1;
    }
}
