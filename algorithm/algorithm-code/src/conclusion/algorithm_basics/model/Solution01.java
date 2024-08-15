package conclusion.algorithm_basics.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 23:45
 * @Description：
 */
public class Solution01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        scanner.nextLine();
        HashMap<Integer, LinkedList<String>> map = new HashMap<>();
        while(row>0){
            row--;
            String url = scanner.nextLine();
            String[] strings = url.split("/");
            int level=1;
            for (String string : strings) {
                if (string.isEmpty()) {
                    continue;  // 跳过由于开头/导致的空字符串
                }
                LinkedList<String> list = map.getOrDefault(level, new LinkedList<>());
                list.add(string);
                map.put(level, list);
                level++;
            }
        }
        int level=scanner.nextInt();
        String word=scanner.next();
        LinkedList<String> list = map.get(level);
        int ans=0;
        for (String e : list) {
            if(word.equals(e)){
                ans++;
            }
        }
        System.out.println(ans);
    }
}
