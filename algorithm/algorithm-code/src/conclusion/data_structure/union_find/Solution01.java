package conclusion.data_structure.union_find;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 12:50
 * @Description：
 */
public class Solution01 {
    public int friendCircle(int[][]matrix){
        int n = matrix.length;
        UnionFind unionFind = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (matrix[i][j] == 1) unionFind.union(i, j);
            }
        }
        return unionFind.getSets();
    }
}
