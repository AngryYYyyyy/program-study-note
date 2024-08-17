package conclusion.serch.bfs;

import java.util.LinkedList;
import java.util.Scanner;

public class Solution03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][]grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        int dinnerCount=countDinner(grid,n,m);
        System.out.println(dinnerCount);
    }

    private static int countDinner(int[][] grid, int n, int m) {
        LinkedList<int[]> queue = new LinkedList<>();
        int[][] visited = new int[n][m];  // 0:未访问, 1:由小华访问, 2:由小为访问, 3:两者都访问过
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        // 初始化队列和访问数组
        int start=1;//假设先碰到的位置是小华
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    queue.add(new int[]{i, j});
                    visited[i][j] = start++;  // 先1 后 2 标记
                }
            }
        }

        // BFS遍历
        while (!queue.isEmpty()) {
            int[] position = queue.poll();
            int x = position[0];
            int y = position[1];
            for (int[] direction : directions) {
                int nx = x + direction[0];
                int ny = y + direction[1];
                if (isValid(nx, ny, n, m) && grid[nx][ny] != 1 && visited[nx][ny] != 3) {
                    //未标记或非同源标记
                    if (visited[nx][ny] == 0 || visited[nx][ny] != visited[x][y]) {
                        visited[nx][ny] |= visited[x][y];  // 设置访问标记
                        queue.offer(new int[]{nx, ny});
                    }
                }
            }
        }

        // 统计可以两方都到达的聚餐地点数量
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 3 && visited[i][j] == 3) {
                    count++;
                }
            }
        }
        return count;
    }



    private static boolean isValid(int x, int y, int n, int m) {
        return x>=0&&x<n&&y>=0&&y<m;
    }

}
