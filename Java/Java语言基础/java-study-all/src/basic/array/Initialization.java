package basic.array;

import java.util.Arrays;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/17 16:56
 * @Description：
 */
public class Initialization {
    public static void main(String[] args) {
        /*数组初始化*/
        /*默认初始化，赋予默认值*/
        int[]arr1=new int[10];
        for (int e : arr1) {
            System.out.print(e+" ");
        }
        System.out.println();
        /*静态初始化*/
        int[]arr2=new int[]{1,2,3,4};
        for (int e : arr2) {
            System.out.print(e+" ");
        }
        int[]arr3={1,2,3,4,5,6};
        for (int e : arr3) {
            System.out.print(e+" ");
        }
    }
}
