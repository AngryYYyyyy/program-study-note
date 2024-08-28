package conclusion.dp.zzy;

import java.util.HashSet;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 10:56
 * @Description：
 */
public class Solution03 {
    public  void permutation(String string){
        HashSet<String> sets = new HashSet<>();
        if(!string.isEmpty()) {
            func(string,"",sets);
        }
        for(String s : sets) {
            System.out.println(s);
        }
    }

    private  void func(String string, String combine, HashSet<String> sets) {
        if(string.isEmpty()) {
            sets.add(combine);
            return;
        }
        for(int i = 0; i < string.length(); i++) {
            func(string.substring(0,i)+string.substring(i+1),combine+string.charAt(i),sets);
            //回溯
        }
    }


    public  void permutation1(String string){
        HashSet<String> sets = new HashSet<>();
        if(!string.isEmpty()) {
            func1(0,string.toCharArray(),sets);
        }
        for(String s : sets) {
            System.out.println(s);
        }
    }

    private  void func1(int index,char[]chars,  HashSet<String> sets) {
        if(index == chars.length) {
            sets.add(String.valueOf(chars));
            return;
        }
        boolean[] visited=new boolean[26];
        for(int i = index; i < chars.length; i++) {
            if(!visited[chars[i]-'a']) {
                visited[chars[i]-'a']=true;
                swap(chars,i,index);
                func1(index+1,chars,sets);
                swap(chars,i,index);
            }
        }
    }

    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
        solution.permutation("abca");
        System.out.println("-----------------------");
        solution.permutation1("abca");
    }
}
