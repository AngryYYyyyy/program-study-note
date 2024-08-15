package conclusion.algorithm_basics.model;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 0:07
 * @Description：
 */
public class Solution03 {
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
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for(Integer weight:weights){
            heap.offer(weight);
        }
        while(heap.size()>=3){
            Integer z= heap.poll();
            Integer y= heap.poll();
            Integer x= heap.poll();
            if(Objects.equals(x, y) && !Objects.equals(z, y)){
                heap.add(z-y);
            }else if(!Objects.equals(x, y) && Objects.equals(z, y)){
                heap.add(y-x);
            }else if(!Objects.equals(x, y) && !Objects.equals(z, y)){
                int difference=Math.abs((z - y)-(y - x));
                //差值为0时，不需要装入
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
