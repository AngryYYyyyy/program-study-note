package conclusion.algorithm_basics.binarySearch;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 14:53
 * @Description：
 */
public class Solution02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] requirements = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int N = sc.nextInt();
        int minTime=getMinTime(requirements,N);
        System.out.println(minTime);
    }

    private static int getMinTime(int[] requirements, int n) {
        int low= Arrays.stream(requirements).max().getAsInt();
        int high = Arrays.stream(requirements).sum();
        Arrays.sort(requirements);
        while(low<high){
            int mid=low+((high-low)>>1);
            if(isFeasible(requirements,n,mid)){
                high=mid;
            }else{
                low=mid+1;
            }
        }
        return low;
    }

    private static boolean isFeasible(int[] requirements, int n, int day) {
        int[] developers = new int[n];
        for (int i = requirements.length-1; i >=0; i--) {
            int requirement = requirements[i];
            int index = getMinDeveloper(developers);
            developers[index] += requirement;
            if (developers[index] > day) {
                return false;
            }
        }
        return true;
    }

    private static int getMinDeveloper(int[] developers) {
        int min=Integer.MAX_VALUE;
        int index=0;
        for (int i = 0; i < developers.length; i++) {
            if(developers[i]<min){
                min=developers[i];
                index=i;
            }
        }
        return index;
    }
}
