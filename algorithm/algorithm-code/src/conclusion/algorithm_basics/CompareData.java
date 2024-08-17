package conclusion.algorithm_basics;

import java.util.Random;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 22:28
 * @Description：
 */
public class CompareData {
    public static int generateRandom(int maxValue) {
        Random random = new Random();
        return random.nextInt(maxValue+1);
    }
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

    /**
     * 生成随机字符串
     * @param strLen 生成字符串的最大长度
     * @return 随机生成的字符串
     */
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    /**
     * 生成随机字符串数组
     * @param arrLen 数组的最大长度
     * @param strLen 每个字符串的最大长度
     * @return 随机生成的字符串数组
     */
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }
    /**
     * 复制字符串数组
     * @param arr 原始字符串数组
     * @return 新的字符串数组副本
     */
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }
}
