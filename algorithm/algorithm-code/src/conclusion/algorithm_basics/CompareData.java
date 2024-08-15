package conclusion.algorithm_basics;

import java.util.Random;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 22:28
 * @Description：
 */
public class CompareData {
    public static int[] generateRandomArray(int maxLength, int maxValue) {
        Random random = new Random();
        // 随机生成数组的长度，从1到maxLength
        int length = random.nextInt(maxLength) + 1;
        int[] array = new int[length];

        // 填充数组，元素值范围为0到maxValue（包括0、maxValue）
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(maxValue+1);
        }
        return array;
    }
}
