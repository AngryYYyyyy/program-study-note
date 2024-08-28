package conclusion.data_structure.sliding_window;

import java.util.LinkedList;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 19:11
 * @Description：
 */
public class Solution02 {
    public static int absNum(int[]arr,int num){
        if(arr==null||arr.length==0)return 0;
        int n = arr.length;
        int res=0;
        LinkedList<Integer> minQueue = new LinkedList<>();
        LinkedList<Integer> maxQueue = new LinkedList<>();
        int R=0;
        for(int L=0;L<n;L++){
            while(R<n){
                while(!minQueue.isEmpty() && arr[minQueue.peekLast()]>=arr[R]){
                    minQueue.pollLast();
                }
                while (!maxQueue.isEmpty() && arr[maxQueue.peekLast()]<=arr[R]){
                    maxQueue.pollLast();
                }
                minQueue.addLast(R);
                maxQueue.addLast(R);
                int abs=arr[maxQueue.peekFirst()]-arr[minQueue.peekFirst()];
                if(abs<=num){
                    R++;
                }else{
                    break;
                }
            }
            res+=R-L;
            if(L==minQueue.peekFirst()){
                minQueue.pollFirst();
            }
            if(L==maxQueue.peekFirst()){
                maxQueue.pollFirst();
            }
        }
        return res;
    }
}
