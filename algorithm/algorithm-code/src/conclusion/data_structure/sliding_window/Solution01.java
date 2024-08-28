package conclusion.data_structure.sliding_window;

import java.util.LinkedList;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 18:40
 * @Description：
 */
public class Solution01 {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0||k==0) return null;
        /*存储下标，维护窗口内的最大值*/
        LinkedList<Integer> maxQueue = new LinkedList<>();
        int n = nums.length;
        /*存储结果*/
        int[] res = new int[n - k + 1];
        int index=0;
        for (int R = 0; R < n ; R++) {
            /*向右扩容*/
            /*将队尾小于等于新元素的下标删去*/
            while (!maxQueue.isEmpty() && nums[maxQueue.peekLast()] <= nums[R]) {
                maxQueue.pollLast();
            }
            /*增加新元素下标*/
            maxQueue.addLast(R);
            /*如果此时队头（维护的最大值）为即将扩容的L，因为扩容需要L++，删掉*/
            if(maxQueue.peekFirst()==R-k){
                maxQueue.pollFirst();
            }
            /*形成了窗口*/
            if(R>=k-1){
                res[index++]= nums[maxQueue.peekFirst()];
            }
        }
        return res;
    }
    public static int[] minSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0||k==0) return null;
        int n = nums.length;
        int[] res = new int[n - k + 1];
        int index=0;
        LinkedList<Integer> minQueue = new LinkedList<>();
        for (int R = 0; R < n; R++) {
            while (!minQueue.isEmpty() && nums[minQueue.peekLast()] >= nums[R]) {
                minQueue.pollLast();
            }
            minQueue.addLast(R);
            if(minQueue.peekFirst()==R-k){
                minQueue.pollFirst();
            }

            if(R>=k-1){
                res[index++]= nums[minQueue.peekFirst()];
            }
        }
        return res;
    }
}
