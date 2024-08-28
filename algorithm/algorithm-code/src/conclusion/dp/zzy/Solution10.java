package conclusion.dp.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 11:56
 * @Description：
 */
public class Solution10 {
    public static int LCS(String a, String b) {
        return func(a.toCharArray(),b.toCharArray(),0,0);
    }

    /*
            arr1[0,i-1],arr2[0,j-1]公共序列已匹配，现在匹配arr1[i,arr1.len-1],arr2[j,arr2.len-1]
    */
    private static int func(char[] arr1, char[] arr2, int i, int j) {
        if(i==arr1.length||j==arr2.length){
            return 0;
        }
        /*相等*/
        if(arr1[i]==arr2[j]){
            return func(arr1,arr2,i+1,j+1)+1;
        }
        /*不相等*/
        /*探索[i...]和[j+1...]*/
        int p1=func(arr1,arr2,i,j+1);
        /*探索[i+1...][j...]*/
        int p2=func(arr1,arr2,i+1,j);
        return Math.max(p1,p2);
    }



    public static int LCS1(String a, String b) {
        int n = a.length();
        char[] arr1=a.toCharArray();
        int m = b.length();
        char[] arr2=b.toCharArray();
        int[][] dp = new int[n + 1][m + 1];
        for(int i=n-1;i>=0;i--){
            for(int j=m-1;j>=0;j--){
                if(arr1[i]==arr2[j]){
                    dp[i][j]=dp[i+1][j+1]+1;
                }else{
                    dp[i][j]=Math.max(dp[i+1][j],dp[i][j+1]);
                }
            }
        }
        return dp[0][0];
    }



    public static void main(String[] args) {
        System.out.println(LCS("1ef23ghi4j56k", "a12b3c456d"));
    }
}
