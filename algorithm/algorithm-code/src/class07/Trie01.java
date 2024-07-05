package class07;

public class Trie01 {
    public static class Node{
        public int pass;
        public int end;
        public Node[] next;
        Node(){
            pass=0;//经过这个节点单词数量
            end=0;//在这个节点结束单词的数量
            next=new Node[26];//记录下一个节点的路径
        }
    }
    private Node root;
    public Trie01(){
        root=new Node();
    }

    public void insert(String word){
        if(word==null){
            return;
        }
        char[] chars = word.toCharArray();
        Node cur=root;
        cur.pass++;
        for(char e:chars){
            int path=e-'a';
            if(cur.next[path]==null){
                cur.next[path]=new Node();
            }
            cur=cur.next[path];
            cur.pass++;
        }
        cur.end++;
    }
    public void erase(String word) {
        if(countWordsEqualTo(word)==0){
            return;
        }
        char[] chars = word.toCharArray();
        Node cur=root;
        for(char e:chars){
            int path=e-'a';
            if(--cur.next[path].pass==0){
                cur.next[path]=null;
                return;
            }
            cur=cur.next[path];
        }
        cur.end--;
    }
    public int countWordsEqualTo(String word) {
        if(word==null){
            return 0;
        }
        char[] chars = word.toCharArray();
        Node cur=root;
        for(char e:chars){
            int path=e-'a';
            if(cur.next[path]==null){
                return 0;
            }else{
                cur=cur.next[path];
            }
        }
        return cur.end;
    }
    public int countWordsStartingWith(String pre) {
        if(pre==null){
            return 0;
        }
        char[] chars = pre.toCharArray();
        Node cur=root;
        for(char e:chars){
            int path=e-'a';
            if(cur.next[path]==null){
                return 0;
            }else{
                cur=cur.next[path];
            }
        }
        return cur.pass;
    }
}
