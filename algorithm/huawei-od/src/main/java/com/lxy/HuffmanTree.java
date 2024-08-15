package com.lxy;


import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 16:18
 * @Description：
 */
public class HuffmanTree {
    public static class Node{
        public int weight;
        public Node left;
        public Node right;
        Node(Node left, Node right){
            this.left = left;
            this.right = right;
            weight = left.weight + right.weight;
        }
        Node(int weight) {
            this.weight = weight;
            this.left = null;
            this.right = null;
        }
    }
    public static class Tree{
        public static Node buildHuffmanTree(int[]weights){
            PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.weight==o2.weight?height(o1)-height(o2):o1.weight-o2.weight);

            // 创建叶节点并加入优先队列
            for (int weight : weights) {
                queue.add(new Node(weight));
            }

            while(queue.size()>1){
                Node left = queue.poll();
                Node right = queue.poll();
                Node parent = new Node(left,right);
                queue.offer(parent);
            }
            return queue.poll();
        }

        public static int height(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(height(node.left), height(node.right));
        }
        public static void inorderTraversal(Node root) {
            if (root == null) return;
            inorderTraversal(root.left);
            System.out.print(root.weight + " ");
            inorderTraversal(root.right);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        int[] weights = new int[count];
        for (int i = 0; i < count; i++) {
            weights[i] = sc.nextInt();
        }
        Node root = Tree.buildHuffmanTree(weights);
        Tree.inorderTraversal(root);
    }
}
