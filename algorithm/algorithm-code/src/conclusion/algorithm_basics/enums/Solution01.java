package conclusion.algorithm_basics.enums;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 21:54
 * @Description：
 */
public class Solution01 {
    public static void main(String[] args) {
        int[] arr = {-3,-2,-1,0,1,2,3,4};
        int result=process(arr);
        System.out.println(result);
    }

    private static int process(int[]array) {
        int answer=0;
        int max = Arrays.stream(array).map(Math::abs).max().getAsInt();
        boolean[] bucket=new boolean[2*max+1];
        for (int i = 0; i < array.length; i++) {
            if(bucket[max-array[i]]) answer++;
            bucket[max+array[i]]=true;
        }
        return answer;
    }
}
