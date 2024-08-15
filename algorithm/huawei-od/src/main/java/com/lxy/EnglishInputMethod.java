package com.lxy;

import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/2 21:49
 * @Description：
 */
public class EnglishInputMethod {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine().split("[^a-zA-Z]");
        String s = scanner.nextLine();
        PriorityQueue<String> queue = new PriorityQueue<>();
        for (String word : words) {
            if(word.startsWith(s))
            {
                queue.add(word);
            }
        }
        if(queue.isEmpty()){
            System.out.println(s);
        }
        while(!queue.isEmpty()){
            System.out.print(queue.poll()+" ");
        }
    }
}
