/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/26 9:15
 * @Description：
 */
public class Solution14 {
    public String longestCommonPrefix(String[] strs) {
        /*将第一个字符串当作公共前缀，依次匹配*/
        /*记录匹配的最短索引，即最大的公共前缀*/
        String prefix = strs[0];
        int min=prefix.length();
        for(String s : strs){
            int index=0;
            while(index<s.length()&&index<prefix.length()){
                if(s.charAt(index)!=prefix.charAt(index)){
                    break;
                }
                index++;
            }
            min=Math.min(min,index);
            if(min==0){
                return "";
            }
        }
        return prefix.substring(0,min);
    }
}
