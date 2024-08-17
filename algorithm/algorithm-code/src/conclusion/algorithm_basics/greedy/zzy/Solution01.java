package conclusion.algorithm_basics.greedy.zzy;

import java.util.*;

import static conclusion.algorithm_basics.CompareData.copyStringArray;
import static conclusion.algorithm_basics.CompareData.generateRandomStringArray;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/17 22:48
 * @Description： 字典序最小字符串合并
 */
public class Solution01 {
    /**
     * 使用字符串比较和排序方法实现最小字典序合并（贪心解法）
     * @param strings 待合并的字符串数组
     * @return 合并后的最小字典序字符串
     */
    private static String minDictionaryOrder1(String[] strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        Arrays.sort(strings, (o1, o2) -> o1.concat(o2).compareTo(o2.concat(o1)));
        StringBuilder builder = new StringBuilder();
        for(String s : strings) {
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * 使用优先队列实现字符串的最小字典序合并（暴力解法）
     * @param strings 待合并的字符串数组
     * @return 合并后的最小字典序字符串
     */
    public static String minDictionaryOrder2(String[] strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        return process(Arrays.asList(strings));
    }

    /**
     * 递归处理字符串列表，每次从列表中选取一个字符串进行合并
     * @param strings 待处理的字符串列表
     * @return 返回当前合并状态下的优先选择
     */
    private static String process(List<String> strings) {
        if (strings.isEmpty()) return "";
        String str="";
        for (String s : strings) {
            List<String> list = new ArrayList<>(strings);
            list.remove(s);
            String next = process(list);
            if(str.isEmpty()){
                str = s+next;
            }else{
                str=str.compareTo(s+next)>0?s+next:str;
            }
        }
        return str;
    }


    /**
     * 测试字符串合并方法的正确性
     */
    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!minDictionaryOrder1(arr1).equals(minDictionaryOrder2(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println(minDictionaryOrder2(arr1));
                System.out.println(minDictionaryOrder1(arr2));
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束!");
    }
}
