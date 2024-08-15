package conclusion.algorithm_basics.binarySearch;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 15:33
 * @Description：
 */
public class Solution03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] peachTree = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int H = Integer.parseInt(sc.nextLine());
        int k=getMinK(peachTree,H);
        System.out.println(k);
    }

    private static int getMinK(int[] peachTree, int h) {
        int low=1;
        int high= Arrays.stream(peachTree).max().getAsInt();
        while(low<high){
            int mid=low+((high-low)>>1);
            if(isFeasible(peachTree,h,mid)){
                high=mid;
            }else{
                low=mid+1;
            }
        }
        return low;
    }

    private static boolean isFeasible(int[] peachTree, int h, int k) {
        int hours=0;
        for(int peach:peachTree){
            hours+=(int)Math.ceil(peach/(double)k);
        }
        return hours<=h;
    }
}
