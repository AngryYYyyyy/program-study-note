package conclusion.algorithm_basics.prefixAnd;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 17:41
 * @Description：
 */
public class Solution02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr=new int[n];
        for (int i = 0; i < n; i++) {
            arr[i]=sc.nextInt();
        }
        int maxDiff=getMaxDifference(arr);
        System.out.println(maxDiff);
    }

    private static int getMaxDifference(int[] arr) {
        int maxDiff=0;
        int n = arr.length;
        int[] prefixAnd = new int[n + 1];
        prefixAnd[0]=0;
        for (int i = 1; i < n+1; i++) {
            prefixAnd[i] = prefixAnd[i-1]+arr[i-1];
        }
        for (int i = 1; i < n; i++) {
            //[start,end]
            int start1=0,end1=i-1,start2=i,end2=n-1;
            int value1=prefixAnd[end1+1]-prefixAnd[start1];
            int value2=prefixAnd[end2+1]-prefixAnd[start2];
            int diff=Math.abs(value1-value2);
            maxDiff=Math.max(maxDiff,diff);
        }
        return maxDiff;
    }
}
