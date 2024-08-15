package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 23:22
 * @Description：
 */
public class ProjectSchedule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] tasks = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int num = sc.nextInt();
        int time=minTime(tasks,num);
        System.out.println(time);
    }

    private static int minTime(int[] tasks, int num) {
        Arrays.sort(tasks); // 任务排序
        int low = Arrays.stream(tasks).max().getAsInt(); // 最大单个任务时间
        int high = Arrays.stream(tasks).sum(); // 所有任务的总时间

        while (low < high) {
            int mid = (low + high) / 2;
            if (isFeasible(mid,tasks, num)) {
                high = mid; // 可行，尝试更小的T
            } else {
                low = mid + 1; // 不可行，增加T
            }
        }

        return low;
    }

    private static boolean isFeasible(int maxTime, int[] tasks, int num) {
        int[] developers = new int[num];
        Arrays.sort(tasks);  // 从大到小排序任务以优化分配
        // 尝试将每个任务分配给工作量最小的开发人员
        for (int i = tasks.length - 1; i >= 0; i--) {
            int minIndex = 0;
            int minValue = developers[0];
            for (int j = 1; j < num; j++) {
                if (developers[j] < minValue) {
                    minValue = developers[j];
                    minIndex = j;
                }
            }

            if (developers[minIndex] + tasks[i] > maxTime) {
                return false; // 如果当前最小工作量的开发人员不能接受这个任务
            }

            developers[minIndex] += tasks[i]; // 分配任务
        }

        return true;
    }
}
