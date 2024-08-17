package conclusion.serch.dfs;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 11:36
 * @Description：
 */
public class Solution04 {
    private static final int[]dx={-1,-1,-1,0,0,1,1,1};
    private static final int[]dy={-1,0,1,1,-1,-1,0,1};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                grid[i][j]=sc.nextInt();
            }
        }
        sc.close();
        int boundaryCount=countBoundary(grid,n,m);
        System.out.println(boundaryCount);
    }

    private static int countBoundary(int[][] grid, int n, int m) {
        boolean[][] visited=new boolean[n][m];
        int count=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(!visited[i][j]&&grid[i][j]==1&&isBoundary(grid,i,j)){
                    dfs(grid,i,j,visited);
                    count++;
                }
            }
        }
        return count;
    }

    private static void dfs(int[][] grid, int i, int j, boolean[][] visited) {
        int n = grid.length;
        int m = grid[0].length;
        if(i<0||i>=n||j<0||j>=m||visited[i][j]||grid[i][j]!=1)  return;
        visited[i][j]=true;
        for(int k=0;k<8;k++){
            int x=i+dx[k];
            int y=j+dy[k];
            if(isValid(x,y,n,m)&&!visited[x][y]&&grid[x][y]==1&&isBoundary(grid,x,y)){
                dfs(grid,x,y,visited);
            }
        }
    }

    private static boolean isBoundary(int[][] grid, int i, int j) {
        int n=grid.length;
        int m=grid[0].length;
        for(int k=0;k<8;k++){
            int x=i+dx[k];
            int y=j+dy[k];
            if(isValid(x,y,n,m)&&grid[x][y]==5){
                return true;
            }
        }
        return false;
    }

    private static boolean isValid(int x, int y,int n,int m) {
        return x>=0&&x<n&&y>=0&&y<m;
    }
}
