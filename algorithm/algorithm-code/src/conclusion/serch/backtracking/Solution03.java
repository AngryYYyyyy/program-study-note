package conclusion.serch.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 19:47
 * @Description：
 */
public class Solution03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] fragments=new String[n];
        for (int i = 0; i < n; i++) {
            fragments[i]=sc.next();
        }
        sc.close();
        LinkedList<String>texts=recover(fragments,n);
        Collections.sort(texts);
        texts.stream().distinct().forEach(System.out::println);
    }

    private static LinkedList<String> recover(String[] fragments, int n) {
        LinkedList<String> _fragments = new LinkedList<>(Arrays.stream(fragments).collect(Collectors.toList()));
        LinkedList<String> texts = new LinkedList<>();
        contact(_fragments,"",texts);
        return texts;
    }

    private static void contact(LinkedList<String> fragments, String current, LinkedList<String> texts) {
        if(fragments.isEmpty()){
            texts.add(current);
        }

        for (String fragment : fragments) {
            LinkedList<String> list = new LinkedList<>(fragments);
            list.remove(fragment);
            contact(list,current+fragment,texts);
        }
    }
}
