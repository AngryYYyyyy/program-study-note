package conclusion.dp.zzy;

import static conclusion.CompareData.generateRandomMatrix;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 15:14
 * @Description：
 */
public class Solution13 {
    public static int traveller(int[][]grid){
        if(grid.length==0)return 0;
        return func(0,0,grid);
    }
    /*从(i,j)出发,到达右下角(n-1,m-1)位置所消耗的最短距离*/
    private static int func(int i, int j, int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        if (i==n-1 && j==m-1) return grid[i][j];

        int path1=Integer.MAX_VALUE;
        if(i+1<n){
            path1=func(i+1,j,grid);
        }
        int path2=Integer.MAX_VALUE;
        if(j+1<m){
            path2=func(i,j+1,grid);
        }

        return Math.min(path1,path2)+grid[i][j];
    }

    public static int traveller1(int[][]grid){
        if(grid.length==0)return 0;
        int n=grid.length;
        int m=grid[0].length;
        int[][] dp = new int[n][m];
        dp[n-1][m-1]=grid[n-1][m-1];
        /*先初始化，防止主循环过多的判断*/
        /*填充最后一列*/
        for(int i=n-2;i>=0;i--){
            dp[i][m-1]=dp[i+1][m-1]+grid[i][m-1];
        }
        /*填充最后一行*/
        for(int j=m-2;j>=0;j--){
            dp[n-1][j]=dp[n-1][j+1]+grid[n-1][j];
        }

        for(int i=n-2;i>=0;i--){
            for(int j=m-2;j>=0;j--){
                dp[i][j]=Math.min(dp[i+1][j],dp[i][j+1])+grid[i][j];
            }
        }
        return dp[0][0];
    }





    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(traveller(m));
        System.out.println(traveller1(m));
        System.out.println(minPathSum2(m));

    }
    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[] dp = new int[col];
        dp[0] = m[0][0];
        for (int j = 1; j < col; j++) {
            dp[j] = dp[j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            dp[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + m[i][j];
            }
        }
        return dp[col - 1];
    }
}
