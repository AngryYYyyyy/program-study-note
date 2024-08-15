package com.lxy;

import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 11:15
 * @Description：
 */
public class FindModeAndMedian {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] strings = sc.nextLine().split(" ");
        int[] array = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
        ArrayList<Integer> modes=getTheMode(array);
        Integer[] newArray = modes.toArray(new Integer[0]);
        int median=getTheMedian(newArray);
        System.out.println(median);
    }

    private static int getTheMedian(Integer[] newArray) {
        Arrays.sort(newArray);
        if(newArray.length%2==0){
            return (newArray[newArray.length/2]+newArray[newArray.length/2-1])/2;
        }else{
            return newArray[newArray.length/2];
        }
    }

    private static ArrayList<Integer> getTheMode(int[] array) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int e:array){
            map.put(e,map.getOrDefault(e,0)+1);
        }
        ArrayList<Integer> modes = new ArrayList<>();
        int maxNum=0;
        for(Map.Entry<Integer,Integer> e:map.entrySet()){
            if(e.getValue()>maxNum){
                maxNum=e.getValue();
            }
        }
        for(Map.Entry<Integer,Integer> e:map.entrySet()){
            if(e.getValue()==maxNum){
                modes.add(e.getKey());
            }
        }
        return modes;
    }
}
