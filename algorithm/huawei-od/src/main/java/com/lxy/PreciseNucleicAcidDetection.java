package com.lxy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/7 20:20
 * @Description：
 */
public class PreciseNucleicAcidDetection {
    public static class UnionFind{
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
                help[index++]=x;
                x = parent[x];
            }
            for(index--; index>=0; index--) {
                parent[help[index]]=x;
            }
            return x;
        }
        public void union(int x, int y) {
            int rootX=find(x);
            int rootY=find(y);
            if(rootX!=rootY){
                int sizeX = size[rootX];
                int sizeY = size[rootY];
                if(sizeX>sizeY){
                    parent[rootY]=rootX;
                    size[rootX]+=sizeY;
                }else {
                    parent[rootX]=rootY;
                    size[rootY]+=sizeX;
                }
            }
        }
        public boolean isSameSet(int x, int y) {
            return find(x)==find(y);
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        sc.nextLine();
        String[] s = sc.nextLine().split(",");
        LinkedList<Integer>  infectedPerson= new LinkedList<>();
        for (String e:s){
            infectedPerson.add(Integer.parseInt(e));
        }
        int[][] patients = new int[count][count];
        for (int i = 0; i < count; i++) {
            String[] s1 = sc.nextLine().split(",");
            for (int j = 0; j < count; j++) {
                patients[i][j]=Integer.parseInt(s1[j]);
            }
        }
        int num=numberOfTesting(infectedPerson,patients);
        System.out.println(num);
    }

    private static int numberOfTesting(LinkedList<Integer> infectedPerson, int[][] patients) {
        int count=patients.length;
        UnionFind unionFind = new UnionFind(count);
        for (int i = 0; i < count; i++) {
            for (int j = i+1; j < count; j++) {
                if(patients[i][j]==1){
                    unionFind.union(i,j);
                }
            }
        }
        int result=0;
        HashSet<Integer> set = new HashSet<>();
        for (Integer e:infectedPerson) {
            int i = unionFind.find(e);
            if(!set.contains(i)){
                set.add(i);
                result+=unionFind.size[i];
            }
        }
        return result-infectedPerson.size();
    }
}
