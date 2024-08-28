package conclusion.data_structure.union_find;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 13:58
 * @Description：
 */
public class Solution03 {
    public static class newUnionFind extends UnionFind {
        public  final int[][]direction=new int[][]{{0,1},{1,0},{0,-1},{-1,0}};

        public newUnionFind(int cols, int rows) {
            super();
            super.cols = cols;
            super.rows = rows;
            parent = new int[cols * rows];
            size = new int[cols * rows];
            helper = new int[cols * rows];
            sets=0;
        }

        public int connect(int i,int j) {
            int x=index(i,j);
            if(size[x]==0){
                parent[x]=x;
                size[x]=1;
                sets++;
                for (int[]d:direction) {
                    int ni=i+d[0];
                    int nj=j+d[1];
                    if(ni>=0&&nj>=0&&ni<rows&&nj<cols){
                        union(x,index(ni,nj));
                    }
                }
            }
            return sets;
        }
    }
    public  List<Integer> islands(int row, int col, int[][]position){
        newUnionFind unionFind = new newUnionFind(row, col);
        List<Integer> ans = new ArrayList<>();
        for(int[]e:position){
            ans.add(unionFind.connect(e[0],e[1]));
        }
        return ans;
    }
}
