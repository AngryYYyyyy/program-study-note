package conclusion.serch.backtracking;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 17:02
 * @Description：
 */
public class Solution02 {
    private static final int[][] directions={{0,1},{1,0},{0,-1},{-1,0}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();
        String target = sc.nextLine();
        char[][] grid = new char[n][m];

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
            }
        }
        int[]position=findIt(grid,n,m,target);
        if(position[0]==-1&&position[1]==-1){
            System.out.println("NO");
        }else{
            System.out.println((position[0]+1)+" "+(position[1]+1));
        }

    }
    private static int[] findIt(char[][] grid, int n, int m,String target) {
        int[]position=new int[]{-1,-1};
        boolean[][] visited=new boolean[n][m];
        LinkedList<int[]> departures=new LinkedList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(target.startsWith(String.valueOf(grid[i][j]))){
                    departures.add(new int[]{i,j});
                }
            }
        }
        while(!departures.isEmpty()){
            int[] departure =departures.removeFirst();
            int x=departure[0];
            int y=departure[1];
            visited[x][y]=true;
            if(isFind(x,y,1,target,visited,grid)){
                position[0]=x;
                position[1]=y;
                break;
            }
            visited[x][y]=false;
        }
        return position;
    }
    /*表示字符串第i个元素该如何选择去向*/
    /*i之前的路径已匹配*/
    private static boolean isFind(int x, int y, int i, String target, boolean[][] visited, char[][] grid) {
        if(i==target.length()){return true;}
        visited[x][y]=true;
        int n=grid.length;
        int m=grid[0].length;
        /*回溯*/
        for(int[] direction:directions){
            int nx=x+direction[0];
            int ny=y+direction[1];
            if(isValid(nx,ny,n,m)&&!visited[nx][ny]&&grid[nx][ny]==target.charAt(i)){
                if(isFind(nx,ny,i+1,target,visited,grid)){
                    return true;
                }
                /*回溯*/
                visited[nx][ny]=false;
            }
        }
        return false;
    }

    private static boolean isValid(int x, int y, int n, int m) {
        return x>=0&&x<n&&y>=0&&y<m;
    }

}
