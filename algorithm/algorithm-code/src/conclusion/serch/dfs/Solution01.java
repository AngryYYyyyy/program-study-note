package conclusion.serch.dfs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 22:21
 * @Description：
 */
public class Solution01 {
    private static class UnionFind{
        private int[] parent;
        private int[] size;
        private int[] help;
        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        public int find(int x) {
            int index=0;
            while (x != parent[x]) {
                help[index++] = x;
                x = parent[x];
            }
            for(index--; index>=0; index--) {
                parent[help[index]]=x;
            }
            return x;
        }
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if(rootP!=rootQ){
                int sizeP = size[rootP];
                int sizeQ = size[rootQ];
                if(sizeP>sizeQ){
                    parent[rootQ]=rootP;
                    size[rootP]+=sizeQ;
                }else{
                    parent[rootP]=rootQ;
                    size[rootQ]+=sizeP;
                }
            }
        }
        public boolean isSame(int p, int q) {
            return find(p) == find(q);
        }
        public int size(int x){
            return size[find(x)];
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        int[] infectedPersons = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int[][] contactMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            contactMatrix[i]=Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        }
        int num=calculateTestedPeople(infectedPersons,contactMatrix);
        System.out.println(num);
    }

    private static int calculateTestedPeople(int[] infectedPersons, int[][] contactMatrix) {
        int testedPeople = 0;
        int n=contactMatrix.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if(contactMatrix[i][j]==1){
                    uf.union(i, j);
                }
            }
        }
        HashSet<Integer> set = new HashSet<>();
        for(int infectedPerson : infectedPersons){
            int people = uf.find(infectedPerson);
            if(!set.contains(people)){
                set.add(people);
                testedPeople+=uf.size(people);
            }
        }
        return testedPeople-infectedPersons.length;
    }

}
