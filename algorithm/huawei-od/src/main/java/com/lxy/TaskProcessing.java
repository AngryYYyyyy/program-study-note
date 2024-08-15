package com.lxy;

import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/8 10:24
 * @Description：
 */
public class TaskProcessing {
    public static class Task{
        int start;
        int end;
        boolean flag;
        public Task(int start, int end) {
            this.start = start;
            this.end = end;
            flag = false;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Task[] tasks = new Task[N];
        for (int i = 0; i < N; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            tasks[i] = new Task(start, end);
        }
        int num=taskProcess(tasks);
        System.out.println(num);
    }

    private static int taskProcess(Task[] tasks) {
        int num=0;
        int timeStrap=0;
        Arrays.sort(tasks, (Task t1, Task t2) -> t1.end - t2.end);
        while (timeStrap<=tasks[tasks.length-1].end) {
            for(Task task:tasks) {
                if(task.start <= timeStrap&&task.end >= timeStrap&&!task.flag){
                    num++;
                    task.flag=true;
                    break;
                }
            }
            timeStrap++;
        }
        return num;
    }
}
