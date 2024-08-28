package conclusion.dp.hwod;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 22:41
 * @Description：
 */

/*题目描述
“吃货”和“馋嘴”两人到披萨店点了一份铁盘（圆形）披萨，并嘱咐店员将披萨按放射状切成大小相同的偶数扇形小块。但是粗心服务员将技萨切成了每块大小都完全不同奇数块，且肉眼能分辨出大小。

由于两人都想吃到最多的披萨，他们商量了一个他们认为公平的分法：从“吃货”开始，轮流取披萨。

除了第一块披萨可以任意选取以外，其他都必须从缺口开始选。他俩选披萨的思路不同。

“馋嘴”每次都会选最大块的披萨，而且“吃货"知道“馋嘴”的想法。

已知披萨小块的数量以及每块的大小，求“吃货”能分得的最大的披萨大小的总和。*/
public class Solution01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] pizzas=new int[n];
        for (int i = 0; i < n; i++) {
            pizzas[i]=sc.nextInt();
        }
        sc.close();
        int chihuo=sumPizzas1(pizzas);
        System.out.println(chihuo);
    }
    private static int sumPizzas(int[] pizzas) {
        int maxSum=0;
        int n=pizzas.length;
        for (int i=0;i<n;i++) {
            int sum=pizzas[i]+func1(pizzas,(i-1+n)%n,(i+1)%n);
            maxSum=Math.max(maxSum,sum);
        }
        return maxSum;
    }

    //吃货这一回合后手选择
    private static int func1(int[] pizzas, int pre,int next) {
        if(pre==next){
            return 0;
        }
        int sum=0;
        int n=pizzas.length;
        /*馋嘴选大的*/
        if(pizzas[pre]>pizzas[next]){
            /*馋嘴选pre*/
            /*吃货只能在先手时选next和pre-1*/
            sum=func2(pizzas,(pre-1+n)%n,next);
        }else{
            /*馋嘴选next*/
            /*吃货只能在先手时选next+1和pre*/
            sum=func2(pizzas,pre,(next+1)%n);
        }
        return sum;
    }
    //吃货这一回合先手选择
    private static int func2(int[] pizzas, int pre,int next) {
        if(pre==next){
            return pizzas[pre];
        }
        int n=pizzas.length;
        /*吃货先手选pre和next的较大的返回*/
        /*pre*/
        int preVal=pizzas[pre]+func1(pizzas,(pre-1+n)%n,next);
        /*next*/
        int nextVal=pizzas[next]+func1(pizzas,pre,(next+1)%n);
        return Math.max(preVal,nextVal);
    }
    /*------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------*/
    private static int sumPizzas1(int[] pizzas) {
        int maxSum=0;
        int n=pizzas.length;
        /*后手*/
        int[][] dp1=new int[n][n];
        /*先手*/
        int[][] dp2=new int[n][n];
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                dp1[i][j]=-1;
                dp2[i][j]=-1;
            }
        }
        for (int i=0;i<n;i++) {
            int sum=pizzas[i]+func3(pizzas,(i-1+n)%n,(i+1)%n,dp1,dp2);
            maxSum=Math.max(maxSum,sum);
        }
        return maxSum;
    }

    //吃货这一回合后手选择
    private static int func3(int[] pizzas, int pre,int next,int[][]dp1,int[][]dp2) {
        if(dp1[pre][next]!=-1){
            return dp1[pre][next];
        }
        if(pre==next){
            dp1[pre][next]=0;
            return 0;
        }
        int n=pizzas.length;
        int sum=0;
        /*馋嘴选大的*/
        if(pizzas[pre]>pizzas[next]){
            /*馋嘴选pre*/
            /*吃货只能在先手时选next和pre-1*/
            dp1[pre][next]=dp2[(pre-1+n)%n][next]=func4(pizzas,(pre-1+n)%n,next,dp1,dp2);
        }else{
            /*馋嘴选next*/
            /*吃货只能在先手时选next+1和pre*/
            dp1[pre][next]=dp2[pre][(next+1)%n]=func4(pizzas,pre,(next+1)%n,dp1,dp2);
        }
        return dp1[pre][next];
    }
    //吃货这一回合先手选择(真正选择)
    private static int func4(int[] pizzas, int pre,int next,int[][]dp1,int[][]dp2) {
        if(dp2[pre][next]!=-1){
            return dp2[pre][next];
        }
        if(pre==next){
            dp2[pre][next]=pizzas[pre];
            return pizzas[pre];
        }
        int n=pizzas.length;
        /*吃货先手选pre和next的较大的返回*/
        /*pre*/
        dp1[(pre-1+n)%n][next]=func3(pizzas,(pre-1+n)%n,next,dp1,dp2);
        int preVal=pizzas[pre]+dp1[(pre-1+n)%n][next];
        /*next*/
        dp1[pre][(next+1)%n]=func3(pizzas,pre,(next+1)%n,dp1,dp2);
        int nextVal=pizzas[next]+dp1[pre][(next+1)%n];
        dp2[pre][next]=Math.max(preVal,nextVal);
        return dp2[pre][next];
    }
}
