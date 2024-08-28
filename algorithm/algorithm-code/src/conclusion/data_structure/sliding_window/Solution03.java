package conclusion.data_structure.sliding_window;

import java.util.LinkedList;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 20:19
 * @Description：
 */
public class Solution03 {
    public static int gasStation(int[] gas, int[] distance) {
        int n = gas.length;
        if (n == 0) return 0;
        int[] netRestPer = new int[n * 2];
        for (int i = 0; i < n; i++) {
            netRestPer[i] = gas[i] - distance[i];
            netRestPer[i + n] = gas[i] - distance[i];
        }

        for (int i = 0; i < n; i++) {
            int rest = netRestPer[i];
            if (rest < 0) {
                continue;
            }
            int j = i + 1;
            for (; j < i + n; j++) {
                rest += netRestPer[j];
                if (rest < 0) {
                    break;
                }
            }
            if (i + n == j) {
                return i;
            }
        }
        return -1;
    }

    public static int gasStation1(int[] gas, int[] distance) {
        int n = gas.length;
        if (n == 0) return 0;
        int[] netRestPer = new int[n * 2];
        for (int i = 0; i < n; i++) {
            netRestPer[i] = gas[i] - distance[i];
            netRestPer[i + n] = gas[i] - distance[i];
        }
        /*前缀和*/
        /*判断位置i能否环形抵达，只需判断[i,i+n-1]该回环的前缀和数组-PreAnd[i-1]是否都大于0*/
        int[] preAnd = new int[n * 2+1];
        preAnd[0] = netRestPer[0];
        for (int i = 1; i < n * 2; i++) {
            preAnd[i] = netRestPer[i] + preAnd[i - 1];
        }
        LinkedList<Integer> minQueue = new LinkedList<>();
        /*L=R-n+1*/
        /*此时i为L*/
        int pre=0;
        for (int R = 0; R < 2 * n; R++) {
            while (!minQueue.isEmpty() && preAnd[minQueue.peekLast()] >= preAnd[R]) {
                minQueue.pollLast();
            }
                minQueue.addLast(R);
            if (minQueue.peekFirst() == R - n) {
                minQueue.pollFirst();
            }
            /*形成窗口，并且最小值不小于0*/
            if (R>=n-1) {
                if(preAnd[minQueue.peekFirst()]-pre >= 0){
                    return R - n + 1;
                }else {
                    pre=preAnd[R - n+1];
                }
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int[] gas = new int[]{1, 2, 3, 4, 5};
        int[] distance = new int[]{3, 4, 5, 1, 2};
        System.out.println(gasStation(gas, distance));
        System.out.println(gasStation1(gas, distance));
    }
}
