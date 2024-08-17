package conclusion.serch.bfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 14:35
 * @Description：
 */
public class Solution02 {
    public static class Node{
        public char value;
        public Node left;
        public Node right;
        public Node(char value) {
            this.value = value;
        }
        public Node(char value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] strings = sc.nextLine().split(" ");
        String postorder=strings[0];
        String inorder=strings[1];
        Node root=buildTree(postorder,inorder);
        levelOrderTraversal(root);
    }

    private static void levelOrderTraversal(Node root) {
        if(root==null) return;
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            Node temp=q.poll();
            System.out.print(temp.value);
            if(temp.left!=null) q.add(temp.left);
            if(temp.right!=null) q.add(temp.right);
        }
    }

    private static Node buildTree(String postorder, String inorder) {
        if (postorder.isEmpty() || inorder.isEmpty()) {return null;}
        int poL = postorder.length();
        int ioL = inorder.length();
        char c = postorder.charAt(poL - 1);
        Node root = new Node(c);
        int idx = inorder.indexOf(c);
        root.left = buildTree(postorder.substring(0,idx), inorder.substring(0,idx));
        root.right = buildTree(postorder.substring(idx,poL-1),inorder.substring(idx+1));
        return root;
    }
}
