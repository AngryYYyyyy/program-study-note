package conclusion.algorithm_basics.greedy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 23:15
 * @Description：
 */
public class Solution05 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //用于记录每个整数（小区标识）的出现次数
        Map<Integer, Integer> map = new HashMap<>();
        while (scanner.hasNextInt()) {
            int t = scanner.nextInt();
            map.put(t, map.getOrDefault(t, 0) + 1);
        }
        int number = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer k = entry.getKey();
            Integer v = entry.getValue();
            //int community=v/(k+1);向上取整
            int community=(v+k)/(k+1);
            number+=community*(k+1);
        }
        System.out.println(number);
    }
}
