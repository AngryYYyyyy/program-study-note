package conclusion.algorithm_basics.prefixAnd;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 16:39
 * @Description：
 */
public class Solution01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int minAverageLost = sc.nextInt();
        sc.nextLine();
        int[] lostArray = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        LinkedList<String>timePeriod=getMaxTimePeriod(lostArray,minAverageLost);
        if(timePeriod.isEmpty()){
            System.out.println("NULL");
        }else{
            timePeriod.forEach(time->System.out.print(time+" "));
        }
    }

    private static LinkedList<String> getMaxTimePeriod(int[] lostArray, int minAverageLost) {
        int n=lostArray.length;
        //针对处理区间和,prefixAnd[i+1]表示数组array前i个的前缀和,防止越界
        int[] prefixAnd=new int[n+1];
        prefixAnd[0]=0;
        for(int i=1;i<=n;i++){
            prefixAnd[i]=prefixAnd[i-1]+lostArray[i-1];
        }
        LinkedList<String> timePeriod=new LinkedList<>();
        int maxLength=0;
        for(int start=0;start<n;start++){
            for(int end=start;end<n;end++){
                //sum_(i,j)=prefixAnd[j]-prefixAnd[i-1]
                //但是由于做了处理，首元素越界
                //sum_(i,j)=prefixAnd[j+1]-prefixAnd[i]
                int sum=prefixAnd[end+1]-prefixAnd[start];
                if(sum<=minAverageLost*(end-start+1)){
                    int length=end-start+1;
                    if(length>maxLength){
                        maxLength=length;
                        timePeriod.clear();
                        timePeriod.add(start+"-"+end);
                    }else if(length==maxLength){
                        timePeriod.add(start+"-"+end);
                    }
                }
            }
        }
        return timePeriod;
    }
}
