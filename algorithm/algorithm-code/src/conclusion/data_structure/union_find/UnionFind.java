package conclusion.data_structure.union_find;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 12:48
 * @Description：
 */
public class UnionFind {
    public int[] parent; // 存储每个节点的父节点
    public int[] size; // 存储每个根节点所在集合的大小
    public int[] helper; // 辅助数组，用于路径压缩
    public int sets;//集合总数
    public int cols;//二维数组列
    public int rows;
    public UnionFind() {
    }

    /**
     * 构造函数初始化并查集。
     * @param n 元素总数
     */
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        helper = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // 初始时，每个节点的父节点是它自己
            size[i] = 1; // 初始时，每个集合的大小为1
        }
        sets = n;
    }
    /**
     * 构造函数初始化并查集。
     * @param gird 二维数组网
     */
    public UnionFind(int[][] gird) {
        rows = gird.length;
        cols = gird[0].length;
        int n=rows*cols;
        parent = new int[n];
        size = new int[n];
        helper = new int[n];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (gird[i][j] == 1) {
                    int x=index(i,j);
                    parent[x] = x; // 初始时，每个节点的父节点是它自己
                    size[x] = 1; // 初始时，每个集合的大小为1
                    sets++;
                }
            }
        }
    }

    public int index(int i, int j) {
        return i*cols + j;
    }

    /**
     * 查找节点 x 的根节点，并应用路径压缩优化。
     * @param x 要查找的节点
     * @return 节点 x 的根节点
     */
    public int find(int x) {
        int index = 0;
        while (x != parent[x]) {
            helper[index++] = x;
            x = parent[x];
        }
        for (index--; index >= 0; index--) {
            parent[helper[index]] = x; // 路径压缩，直接将路径上的节点指向根节点
        }
        return x;
    }

    /**
     * 合并节点 x 和节点 y 所在的集合。
     * @param x 第一个节点
     * @param y 第二个节点
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            sets--;
            if (size[rootX] > size[rootY]) {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            } else {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            }
        }
    }

    /**
     * 判断节点 x 和节点 y 是否属于同一个集合。
     * @param x 第一个节点
     * @param y 第二个节点
     * @return 如果 x 和 y 属于同一个集合返回 true，否则返回 false
     */
    public boolean isSameSet(int x, int y) {
        return find(x) == find(y);
    }
    public int getSets() {
        return sets;
    }
}
