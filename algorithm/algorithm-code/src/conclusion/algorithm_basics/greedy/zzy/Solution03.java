package conclusion.algorithm_basics.greedy.zzy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

import static conclusion.CompareData.generateRandomArray;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/18 0:35
 * @Description：解决最小合并成本问题的两种方法
 */
public class Solution03 {
    /**
     * 使用优先队列（贪心算法）求解最小合并成本
     * @param arr 输入的元素数组
     * @return 返回合并所有元素的最小成本
     */
    private static int minCost1(int[] arr) {
        int cost = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int e : arr) {
            pq.add(e);
        }
        while (pq.size() != 1) {
            int cur = pq.poll() + pq.poll();
            pq.add(cur);
            cost += cur;
        }
        return cost;
    }

    /**
     * 使用递归和回溯求解最小合并成本
     * @param golds 输入的元素数组
     * @return 返回合并所有元素的最小成本
     */
    private static int minCost2(int[] golds) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int gold : golds) {
            list.add(gold);
        }
        return process(list);
    }

    /**
     * 递归处理合并过程
     * @param golds 当前待合并的元素列表
     * @return 从当前状态开始，达到最终状态的最小成本
     */
    private static int process(LinkedList<Integer> golds) {
        if (golds.size() == 1) return 0;  // 如果只剩一个元素，则无需合并，成本为0

        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < golds.size(); i++) {
            for (int j = i + 1; j < golds.size(); j++) {
                int mergedGold = golds.get(i) + golds.get(j);

                LinkedList<Integer> newList = new LinkedList<>(golds);
                newList.remove(j);
                newList.remove(i);
                newList.add(mergedGold);

                int cost = process(newList) + golds.get(i) + golds.get(j);
                minCost = Math.min(minCost, cost);
            }
        }
        return minCost;
    }

    /**
     * 主函数，用于测试两种方法是否给出相同的结果
     */
    public static void main(String[] args) {
        int maxLen = 6;
        int maxValue = 50;
        int timeTimes = 10000;
        System.out.println("开始测试！");
        for (int i = 0; i < timeTimes; i++) {
            int[] arr1 = generateRandomArray(maxLen, maxValue);
            int[] arr2 = Arrays.copyOfRange(arr1, 0, arr1.length);
            if (minCost1(arr1) != minCost2(arr2)) {
                System.out.println("出错了！");
                System.out.println(minCost1(arr1) + ":" + minCost2(arr2));
            }
        }
        System.out.println("测试结束！");
    }
}
