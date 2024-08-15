package com.lxy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/2 22:53
 * @Description：
 */
public class APIClusterLoadStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        scanner.nextLine();  // 吸收nextInt后的换行符
        HashMap<Integer, LinkedList<String>> map = new HashMap<>();
        while(row>0){
            row--;
            String url = scanner.nextLine().trim();
            String[] strings = url.split("/");
            int level=1;
            for (String string : strings) {
                if (string.isEmpty()) {
                    continue;  // 跳过由于开头/导致的空字符串
                }
                LinkedList<String> list;
                if(!map.containsKey(level)){
                    list = new LinkedList<>();
                }else {
                    list = map.get(level);
                }
                list.add(string);
                map.put(level, list);
                level++;
            }
        }
        int level=scanner.nextInt();
        String word=scanner.next();
        LinkedList<String> list = map.get(level);
        int count=0;
        for (String e : list) {
            if(word.equals(e)){
                count++;
            }
        }
        System.out.println(count);
    }
}
