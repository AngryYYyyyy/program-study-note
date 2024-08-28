package conclusion.dp.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 14:04
 * @Description：
 */
public class Solution11 {
    public static int LPS(String str) {
        if(str.isEmpty()) return 0;
        return func(str.toCharArray(),0,str.length()-1);
    }
    /*[0...left-1]和[right+1....]回文已经匹配*/
    /*现匹配[left,right]回文*/
    public static int func(char[] str, int left, int right) {
        if(left>right) return 0;
        if(left == right) return 1;
        if(str[left] == str[right]) return func(str,left+1,right-1)+2;
        int p1=func(str,left+1,right);
        int p2=func(str,left,right-1);
        return Math.max(p1,p2);
    }
    public static int LPS1(String str) {
        if(str.isEmpty()) return 0;
        int n = str.length();
        int[][] dp = new int[n][n];
        for(int left=n-1;left>=0;left--){
            dp[left][left]=1;
            for(int right=left+1;right<n;right++){
                if(str.charAt(left) == str.charAt(right)){
                    dp[left][right]=dp[left+1][right-1]+2;
                }else{
                    dp[left][right]=Math.max(dp[left+1][right],dp[left][right-1]);
                }
            }
        }
        return dp[0][n-1];
    }





    public static void main(String[] args) {
        System.out.println(LPS("aababaa"));
        System.out.println(LPS1("aababaa"));
    }
}
