package conclusion.dp.zzy;

import java.util.Arrays;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 19:41
 * @Description：
 */
public class Solution05 {
    public int move(int k,int n,int m,int p){
        if(k==0){
            return m==p?1:0;
        }
        return func(k,n,m,p);
    }
    private int func(int k, int n, int m, int p) {
        if(p<0||p==n){
            return 0;
        }
        if(k==0){
            return m==p?1:0;
        }
        return func(k-1,n,m,p+1)+func(k-1,n,m,p-1);
    }
    public int move1(int k,int n,int m,int p){
        if(k==0){
            return m==p?1:0;
        }
        int[][] dp  = new int[k + 1][n];
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        int r=func1(k,n,m,p,dp);
        return r;
    }
    private int func1(int k, int n, int m, int p,int[][]dp) {
        if(dp[k][p]!=-1){
            return dp[k][p];
        }
        if(k==0){
            dp[k][p]=m==p?1:0;
            return dp[k][p];
        }
        if(p==0){
            dp[k][p]=func(k-1,n,m,p+1);
            return dp[k][p];
        }
        if(p==n-1){
            dp[k][p]=func(k-1,n,m,p-1);
            return dp[k][p];
        }
        dp[k][p]=func(k-1,n,m,p+1)+func(k-1,n,m,p-1);
        return dp[k][p];
    }
    private int move2(int k,int n,int m,int p){
        if(k==0){
            return m==p?1:0;
        }
        int[][] dp  = new int[k + 1][n];
        dp[0][m]=1;
        for (int i = 1; i <= k; i++) {
            dp[i][0]=dp[i-1][1];
            dp[i][n-1]=dp[i-1][n-2];
            for (int j = 1; j < n-1; j++) {
                dp[i][j]=dp[i-1][j+1]+dp[i-1][j-1];
            }
        }
        return dp[k][p];
    }

    public static void main(String[] args) {
        Solution05 solution05 = new Solution05();
        System.out.println(solution05.move(7,10,1,6));
        System.out.println(solution05.move1(7,10,1,6));
        System.out.println(solution05.move2(7,10,1,6));
    }

}
