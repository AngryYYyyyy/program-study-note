package conclusion.data_structure.union_find;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 12:57
 * @Description：
 */
public class Solution02 {
    public int numIslands(char[][] grid) {
        int[][]newGrid=transform(grid);
        int num=0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<newGrid[i].length;j++){
                if(newGrid[i][j]==1){
                    dfs(newGrid,i,j);
                    num++;
                }
            }
        }
        return num;
    }
    public static final int[][]direction=new int[][]{{0,1},{0,-1},{1,0},{-1,0}};

    public void dfs(int[][] grid, int i, int j) {
        if(i<0||j<0||i==grid.length || j==grid[i].length||grid[i][j]==0) {
            return;
        }
        grid[i][j]=0;
        for(int[]d:direction){
            dfs(grid,i+d[0],j+d[1]);
        }
    }
    public  int[][]transform(char[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] res = new int[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(grid[i][j]=='1'){
                    res[i][j]=1;
                }
            }
        }
        return res;
    }



    public static int cols=0;
    public int numIslands1(int[][]grid){
        UnionFind unionFind = new UnionFind(grid);
        int rows = grid.length;
        cols = grid[0].length;
        for(int i=1;i<rows;i++){
            if(grid[i-1][0]==1&&grid[i][0]==1){
                unionFind.union(index(i-1,0),index(i,0));
            }
        }
        for(int j=1;j<cols;j++){
            if(grid[0][j-1]==1&&grid[0][j]==1){
                unionFind.union(index(0,j-1),index(0,j));
            }
        }
        for(int i=1;i<rows;i++){
            for(int j=1;j<cols;j++){
                if(grid[i][j]==1&&grid[i-1][j]==1){
                    unionFind.union(index(i,j),index(i-1,j));
                }
                if(grid[i][j]==1&&grid[i][j-1]==1){
                    unionFind.union(index(i,j),index(i,j-1));
                }
            }
        }
        return unionFind.getSets();
    }
    public int index(int i, int j){
        return i*cols+j;
    }
}
