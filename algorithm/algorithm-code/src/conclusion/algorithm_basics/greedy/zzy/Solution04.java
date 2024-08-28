package conclusion.algorithm_basics.greedy.zzy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/20 23:56
 * @Description：
 */
public class Solution04 {
    public static class Program{
        public int cost;
        public int profit;

        public Program(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }
    public static class MinCostComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost-o2.cost;
        }
    }
    public static class MaxProfitComparator implements Comparator<Program>{

        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit-o1.profit;
        }
    }
    public static int findMaximizedCapital(int k, int w, int[] profits, int[] costs) {
        PriorityQueue<Program> minCost = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> maxProfit = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < profits.length; i++) {
            minCost.add(new Program(costs[i],profits[i]));
        }
        for (int i = 0; i < k; i++) {
            while(!minCost.isEmpty()&&minCost.peek().cost<=w){
                maxProfit.add(minCost.poll());
            }
            if(maxProfit.isEmpty()){
                return w;
            }
            w+=maxProfit.poll().profit;
        }
        return w;
    }
}
