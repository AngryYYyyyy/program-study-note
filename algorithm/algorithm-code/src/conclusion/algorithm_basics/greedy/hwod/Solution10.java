package conclusion.algorithm_basics.greedy.hwod;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 9:29
 * @Description：
 */
public class Solution10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] parks = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int park=findPark(parks);
        System.out.println(park);
    }

    private static int findPark(int[] parks) {
        int n=parks.length;
        int maxDistance=0;
        int i=0;
        while(i<n){
            if(parks[i]==0){
                int start=i;
                while(i<n&&parks[i]==0){
                    i++;
                }
                int end=i;//[)
                int len=end-start;
                if(start==0||end==n){
                    maxDistance=Math.max(maxDistance,len);
                }else{
                    int distance=(int)Math.ceil((double)len/2);
                    maxDistance=Math.max(maxDistance,distance);
                }
            }else{
                i++;
            }
        }
        return maxDistance;
    }
}
