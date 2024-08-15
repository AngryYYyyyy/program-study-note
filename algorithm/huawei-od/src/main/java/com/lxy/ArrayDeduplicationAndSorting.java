package com.lxy;

import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 21:04
 * @Description：
 */
public class ArrayDeduplicationAndSorting {
    public static class IntegerCount{
        public int value;
        public int count;
        public IntegerCount(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] array = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        LinkedHashMap<Integer,Integer> map = new LinkedHashMap<>();
        LinkedList<IntegerCount> list = new LinkedList<>();
        for (int i = 0; i < array.length; i++) {
            int cur=array[i];
            Integer count = map.getOrDefault(cur, 0);
            map.put(cur, count + 1);
        }
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            list.add(new IntegerCount(key,value));
        }
        list.sort((o1, o2) -> o2.count - o1.count);
        while(list.size() >1){
            IntegerCount integerCount = list.removeFirst();
            System.out.print(integerCount.value+",");
        }
        System.out.print(list.removeFirst().value);
    }
}
