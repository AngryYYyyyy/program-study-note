package conclusion.algorithm_basics.enums;

import java.util.*;


/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 22:58
 * @Description：
 */
public class Main {
    public static void main(String[] args) {
        int[]arr={0,1,2,3,4,5,6,7,8,9};
        int index=binarySearch(arr,9);
        System.out.println(index);
    }

    private static int binarySearch(int[] arr,int target) {
        int left=0,right=arr.length-1;
        while(left<=right) {
            int mid=left+((right-left)>>1);
            if(arr[mid]<target) {
                left=mid+1;
            }else if(arr[mid]>target) {
                right=mid-1;
            }else {
                return mid;
            }
        }
        return -1;
    }
}
