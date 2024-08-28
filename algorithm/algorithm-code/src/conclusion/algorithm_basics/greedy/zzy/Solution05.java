package conclusion.algorithm_basics.greedy.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/21 0:06
 * @Description：
 */
public class Solution05 {
    public static int minLight(String road){
        char[] tmp= road.toCharArray();
        int ans=0;
        int i=0;
        while (i < tmp.length) {
            if(tmp[i]=='X'){
                i++;
            }
            else{
                ans++;
                if(i+1==tmp.length){
                    break;
                }
                if(tmp[i+1]=='.'){
                    i+=3;
                }else{
                    i+=2;
                }
            }
        }
        return ans;
    }
}
