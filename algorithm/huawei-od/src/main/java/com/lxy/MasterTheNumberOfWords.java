package com.lxy;

import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/7 16:21
 * @Description：
 */
public class MasterTheNumberOfWords {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine().trim()); // 读取单词数量
        String[] words = new String[N];

        for (int i = 0; i < N; i++) {
            words[i] = scanner.nextLine().trim(); // 读取每个单词
        }

        String chars = scanner.nextLine().trim(); // 读取字符集
        System.out.println(countWordsThatCanBeFormedByChars(words, chars));
        scanner.close();
    }

    private static int countWordsThatCanBeFormedByChars(String[] words, String chars) {
        Map<Character, Integer> charMap = new HashMap<>();
        int wildcardCount = 0;

        // 计数 chars 中的每个字符
        for (char ch : chars.toCharArray()) {
            if (ch == '?') {
                wildcardCount++;
            } else {
                charMap.put(ch, charMap.getOrDefault(ch, 0) + 1);
            }
        }

        int count = 0;
        // 验证每个单词是否可以由 chars 中的字符拼写
        for (String word : words) {
            if (canFormWord(word, new HashMap<>(charMap), wildcardCount)) {
                count++;
            }
        }

        return count;
    }

    private static boolean canFormWord(String word, Map<Character, Integer> charMap, int wildcardCount) {
        for (char ch : word.toCharArray()) {
            if (!charMap.containsKey(ch) || charMap.get(ch) == 0) {
                if (wildcardCount > 0) {
                    wildcardCount--; // 使用一个万能字符？
                } else {
                    return false;
                }
            } else {
                charMap.put(ch, charMap.get(ch) - 1); // 使用一个字符
            }
        }
        return true;
    }
}
