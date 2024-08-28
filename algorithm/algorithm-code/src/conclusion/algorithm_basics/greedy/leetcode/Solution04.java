package conclusion.algorithm_basics.greedy.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 12:02
 * @Description：
 */
public class Solution04 {
    public List<Integer> partitionLabels(String s) {
        int[]last=new int[26];
        List<Integer> list=new ArrayList<>();
        /*记录字符最后一个下标*/
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i)-'a']=i;
        }
        int start=0,end=0;
        for (int i = 0; i < s.length(); i++) {
            end=Math.max(end,last[s.charAt(i)-'a']);
            if(i==end){
                list.add(end-start+1);
                start=end+1;
            }
        }
        return list;
    }
}
