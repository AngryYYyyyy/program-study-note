package conclusion.algorithm_basics.binarySearch;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 15:51
 * @Description：
 */
public class Solution04 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] heights = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int XiaoMing=sc.nextInt();
        int index=binarySearch(heights,XiaoMing);
        System.out.println(index+1);
    }
    private static int binarySearch(int[] arr, int target) {
        int index = Arrays.binarySearch(arr, target);
        return -index - 1;
    }
}
