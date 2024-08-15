package conclusion.serch.dfs;


import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 22:49
 * @Description：
 */
public class Solution02 {
    public static class Node{
        public int weight;
        public Node left;
        public Node right;
        public int height;
        public Node(int weight,int height,Node left,Node right) {
            this.weight = weight;
            this.height = height;
            this.left = left;
            this.right = right;
        }
    }
    public static class HuffmanTree{
        public static Node generate(int[] leafNodeWeights){
            PriorityQueue<Node> pq=new PriorityQueue<>((o1, o2) -> o1.weight==o2.weight?o1.height-o2.height:o1.weight-o2.weight);
            for(int weight:leafNodeWeights){
                pq.offer(new Node(weight,1,null,null));
            }
            while(pq.size()>=2){
                Node left=pq.poll();
                Node right=pq.poll();
                Node newNode=new Node(left.weight+right.weight,Math.max(left.height,right.height)+1,left,right);
                pq.offer(newNode);
            }
            return pq.poll();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] leafNodeWeights = new int[n];
        for (int i = 0; i < n; i++) {
            leafNodeWeights[i] = sc.nextInt();
        }
        Node root = HuffmanTree.generate(leafNodeWeights);
        inorderTraversal(root);
    }
    public static void inorderTraversal(Node root){
        if(root==null) return;
        inorderTraversal(root.left);
        System.out.print(root.weight + " ");
        inorderTraversal(root.right);
    }
}
