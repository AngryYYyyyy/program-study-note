package conclusion.data_structure.sliding_window;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 21:37
 * @Description：
 */
public class Solution04 {
    public static int coins(int[] coins, int aim) {
        return func(0,coins,aim);
    }
    /*[0,i-1]货币已选择*/
    /*选择[i....]的货币组合*/
    public static int func(int i,int[] coins,int rest) {
        if(i==coins.length) {
            /*0成功 & -1失败*/
            return rest==0?0:-1;
        }
        int minCount = Integer.MAX_VALUE;
        for(int count=0;rest-count*coins[i]>=0;count++) {
            int next=func(i+1,coins,rest-count*coins[i]);
            if(next==-1){
                continue;
            }
            minCount = Math.min(minCount,next+count);
        }
        return minCount;
    }
}
