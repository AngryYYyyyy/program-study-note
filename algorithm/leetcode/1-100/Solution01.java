import java.util.Arrays;
import java.util.HashMap;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/25 18:42
 * @Description：
 */
public class Solution01 {
    /*简单的暴力枚举方法*/
    public int[] twoSum(int[] nums, int target) {
        int n=nums.length;
        int[] res=new int[2];
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(nums[i]+nums[j]==target){
                    res[0]=i;
                    res[1]=j;
                }
            }
        }
        return res;
    }

    /*优化*/
    /*在枚举过程中，发现对于一个确定的数num[i]，其结果已确定为target-num[i]*/
    /*无需进行暴力枚举，只需要将其状态记录即可*/
    public int[] twoSum1(int[] nums, int target) {
        int n=nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            if(map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return new int[0];
    }
}
