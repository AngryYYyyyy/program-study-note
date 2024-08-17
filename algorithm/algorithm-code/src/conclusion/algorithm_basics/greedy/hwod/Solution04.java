package conclusion.algorithm_basics.greedy.hwod;

import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 23:06
 * @Description：
 */
public class Solution04 {
    public static class Task {
        int start;
        int end;
        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            tasks.add(new Task(sc.nextInt(), sc.nextInt()));
        }
        int num = taskProcess(tasks);
        System.out.println(num);
    }

    private static int taskProcess(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(t -> t.start));
        PriorityQueue<Task> availableTasks = new PriorityQueue<>((o1, o2) -> o1.end== o2.end?o1.start-o2.start:o1.end-o2.end);

        int currentTime = 0, completedTasks = 0;
        int i=0;
        while (i < tasks.size()) {
            currentTime=tasks.get(i).start;
            while (i < tasks.size() && tasks.get(i).start <= currentTime) {
                availableTasks.add(tasks.get(i));
                i++;
            }

            while (!availableTasks.isEmpty()) {
                Task task = availableTasks.poll();
                if (task.end>=currentTime) {
                    completedTasks++;
                    currentTime++;
                    while (i < tasks.size() && tasks.get(i).start <= currentTime) {
                        availableTasks.add(tasks.get(i));
                        i++;
                    }
                }
            }
        }
        return completedTasks;
    }
}
