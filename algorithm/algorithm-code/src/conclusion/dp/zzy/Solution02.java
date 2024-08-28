package conclusion.dp.zzy;

import java.util.HashSet;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 10:49
 * @Description：
 */
public class Solution02 {
    public  void subsequences(String string){
        HashSet<String> sets = new HashSet<>();
        if(!string.isEmpty()) {
            func(0,string,"",sets);
        }
        for(String s : sets) {
            System.out.println(s);
        }
    }

    private  void func(int i, String string, String subsequence, HashSet<String> sets) {
        if(i == string.length()) {
            sets.add(subsequence);
            return;
        }
        char c = string.charAt(i);
        func(i+1,string,subsequence+c,sets);
        func(i+1,string,subsequence,sets);
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        solution.subsequences("abc");
    }
}
