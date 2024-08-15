package com.lxy;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

public class RecycleSilverJewelry {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int weight=dissolve(arr);
        System.out.println(weight);
    }

    private static int dissolve(Integer[] weights) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for(Integer weight:weights){
            heap.offer(weight);
        }
        while(heap.size()>=3){
            Integer z= heap.poll();
            Integer y= heap.poll();
            Integer x= heap.poll();
            if(Objects.equals(x, y) && Objects.equals(z, y)){

            }else if(Objects.equals(x, y) && !Objects.equals(z, y)){
                heap.add(z-y);
            }else if(!Objects.equals(x, y) && Objects.equals(z, y)){
                heap.add(y-x);
            }else if(!Objects.equals(x, y) && !Objects.equals(z, y)){
                int difference=Math.abs((z - y)-(y - x));
                if(difference!=0){
                    heap.add(difference);
                }
            }
        }
        if(heap.isEmpty()){
            return 0;
        }else if(heap.size()==1){
            return heap.poll();
        } else  {
            int a=heap.poll();
            int b=heap.poll();
            return Math.max(a, b);
        }
    }
}
