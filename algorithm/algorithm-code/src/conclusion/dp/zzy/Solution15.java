package conclusion.dp.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 16:52
 * @Description：
 */
public class Solution15 {
    public static int combineCoins(int[]coins,int aim){
        /*建议不要增加无谓的判断*/
        return func(0,aim,coins);
    }
    /*[0,i]已经组合完，只需要判断[i....]的货币*/
    /*当前i货币要多少,以及之后*/
    public static int func(int i,int rest,int[] coins){
        if(i==coins.length)return rest==0?1:0;
        int ways=0;
        for(int count=0;rest-count*coins[i]>=0;count++){
            ways+=func(i+1,rest-count*coins[i],coins);
        }
        return ways;
    }

    public static int combineCoins1(int[]coins,int aim){
        int n=coins.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        for(int i=n-1;i>=0;i--){
            for(int rest=0;rest<=aim;rest++){
                int ways=0;
                for(int count=0;rest-count*coins[i]>=0;count++){
                    ways+=dp[i+1][rest-count*coins[i]];
                }
                dp[i][rest]=ways;
            }
        }

        return dp[0][aim];
    }
    public static int combineCoins2(int[]coins,int aim){
        int n=coins.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        for(int i=n-1;i>=0;i--){
            for(int rest=0;rest<=aim;rest++){
                dp[i][rest]=dp[i+1][rest];
                if(rest-coins[i]>=0){
                    dp[i][rest]+=dp[i][rest-coins[i]];
                }
            }
        }
        return dp[0][aim];
    }


    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
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
        int maxValue = 30;
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
