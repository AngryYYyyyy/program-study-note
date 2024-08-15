package conclusion.algorithm_basics.model;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 9:37
 * @Description：
 */
public class Solution05 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int executeTasks = sc.nextInt();
        int N = sc.nextInt();
        int[] tasksPerSecond = new int[N];
        for (int i = 0; i < N; i++) {
            tasksPerSecond[i] = sc.nextInt();
        }
        int time=minTime(executeTasks,tasksPerSecond);
        System.out.println(time);
    }
    private static int minTime(int executeTasks, int[] tasksPerSecond) {
        int totalTime = 0;
        int restTasks = 0;
        for (int tasks : tasksPerSecond) {
            restTasks += tasks;
            if(restTasks > executeTasks){
                restTasks-=executeTasks;
            }else {
                restTasks=0;
            }
            totalTime++;
        }
        totalTime+=(restTasks+executeTasks-1)/executeTasks;//n/m向上取整：（n+m-1）/m
        return totalTime;
    }
}
