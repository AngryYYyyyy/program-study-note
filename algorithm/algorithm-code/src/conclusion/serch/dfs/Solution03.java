package conclusion.serch.dfs;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 11:16
 * @Description：
 */
public class Solution03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] matrixNetwork=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                matrixNetwork[i][j]=sc.nextInt();
            }
        }
        sc.close();
        int maxLan=countMaxLan(matrixNetwork,n,m);
        System.out.println(maxLan);
    }

    private static int countMaxLan(int[][] matrixNetwork,int n,int m) {
        int maxCountLan=0;
        boolean[][] visited=new boolean[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(matrixNetwork[i][j]==1&&!visited[i][j]){
                    int count=dfs(matrixNetwork,n,m,i,j,visited);
                    maxCountLan=Math.max(maxCountLan,count);
                }
            }
        }
        return maxCountLan;
    }

    private static int dfs(int[][] matrixNetwork, int n,int m,int i, int j, boolean[][] visited) {
        if(i<0||i>=n||j<0||j>=m||visited[i][j]||matrixNetwork[i][j]==0){
            return 0;
        }
        int count=1;
        visited[i][j]=true;
        count+=dfs(matrixNetwork,n,m,i+1,j,visited);
        count+=dfs(matrixNetwork,n,m,i-1,j,visited);
        count+=dfs(matrixNetwork,n,m,i,j+1,visited);
        count+=dfs(matrixNetwork,n,m,i,j-1,visited);
        return count;
    }
}
