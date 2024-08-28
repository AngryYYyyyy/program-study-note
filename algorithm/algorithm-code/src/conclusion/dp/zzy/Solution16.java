package conclusion.dp.zzy;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 17:28
 * @Description：
 */
public class Solution16 {
    public static class Info{
        public int[] coins;
        public int[] counts;
        public Info(int[]c){
            HashMap<Integer, Integer> map = new HashMap<>();
            for(int e:c){
                map.put(e, map.getOrDefault(e, 0) + 1);
            }
            coins = new int[map.size()];
            counts = new int[map.size()];
            int i=0;
            for(Map.Entry<Integer, Integer> entry:map.entrySet()){
                int coin=entry.getKey();
                int count=entry.getValue();
                coins[i]=coin;
                counts[i++]=count;
            }
        }
    }
    public static int combineCoins(int[]coins,int aim){
        Info info = new Info(coins);
        return func(0,aim, info.coins,info.counts);
    }
    /*[0,i]已经组合完，只需要判断[i....]的货币*/
    /*当前i货币要多少,以及之后*/
    public static int func(int i,int rest,int[] coins,int[] counts){
        if(i==coins.length)return rest==0?1:0;
        int ways=0;
        for(int count=0;rest-count*coins[i]>=0&&count<=counts[i];count++){
            ways+=func(i+1,rest-count*coins[i],coins,counts);
        }
        return ways;
    }

    public static int combineCoins1(int[]c,int aim){
        Info info = new Info(c);
        int[] coins= info.coins;
        int[] counts=info.counts;
        int n=coins.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0]=1;
        for(int i=n-1;i>=0;i--){
            for(int rest=0;rest<=aim;rest++){
                int ways=0;
                for(int count=0;rest-count*coins[i]>=0&&count<=counts[i];count++){
                    ways+=dp[i+1][rest-count*coins[i]];
                }
                dp[i][rest]=ways;
            }
        }
        return dp[0][aim];
    }
    public static int combineCoins2(int[]c,int aim){
        Info info = new Info(c);
        int[] coins= info.coins;
        int[] counts=info.counts;
        int n=coins.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0]=1;
        for(int i=n-1;i>=0;i--){
            for(int rest=0;rest<=aim;rest++){
                dp[i][rest]=dp[i+1][rest];
                if(rest-coins[i]>=0) {
                    dp[i][rest]+=dp[i][rest-coins[i]];
                    if(rest-(counts[i]+1)*coins[i]>=0){
                        dp[i][rest]-=dp[i+1][rest-(counts[i]+1)*coins[i]];
                    }
                }
            }
        }
        return dp[0][aim];
    }



    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = combineCoins(arr, aim);
            int ans2 = combineCoins1(arr, aim);
            int ans3 = combineCoins2(arr, aim);
            if (ans1 != ans2 ||ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
