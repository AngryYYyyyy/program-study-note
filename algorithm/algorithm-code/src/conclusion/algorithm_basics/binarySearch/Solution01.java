package conclusion.algorithm_basics.binarySearch;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 14:23
 * @Description：
 */
public class Solution01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        sc.nextLine();
        int[] requirements = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int minManpower=getMinManpower(requirements,M);
        System.out.println(minManpower);
    }

    private static int getMinManpower(int[] requirements,int M) {
        int low= Arrays.stream(requirements).max().getAsInt();
        int high = Arrays.stream(requirements).sum();
        Arrays.sort(requirements);
        while(low<high){
            int mid = low+((high-low)>>1);
            if(isFeasible(mid,M,requirements)){
                high = mid;
            }else {
                low = mid+1;
            }
        }
        return low;
    }

    private static boolean isFeasible(int manpower, int M, int[] requirements) {
        int month=0;
        int l=0;
        int r=requirements.length-1;
        while(l<=r){
            if(requirements[l]+requirements[r]<=manpower){
                l++;
            }
            r--;
            month++;
        }
        return month<=M;
    }
}
