package conclusion.algorithm_basics.prefixAnd;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 18:10
 * @Description：
 */
public class Test {
    public static void main(String[] args) {
        int[] arr={1,2,3,4,5};
        int n = arr.length;
        int[]prefixSums = new int[2 * n];
        prefixSums[0] = arr[0];

        for (int i = 1; i < 2 * n; i++) {
            prefixSums[i] = prefixSums[i - 1] + arr[i % n];
        }
        for (int i = 0; i < 2 * n; i++) {
            System.out.print(prefixSums[i]+" ");
        }
    }
}
