package com.lxy;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/3 17:22
 * @Description：
 */
public class OpenSourceProjectHotList {
    public static class Project{
        public String name;
        public Integer nr_watch ;
        public Integer nr_star ;
        public Integer nr_fork ;
        public Integer nr_issue;
        public Integer nr_mr;
        private Integer hot;
        public static Integer[] weights;

        public Project(String project) {
            String[] strings = project.split(" ");
            this.name = strings[0];
            this.nr_watch = Integer.parseInt(strings[1]);
            this.nr_star = Integer.parseInt(strings[2]);
            this.nr_fork = Integer.parseInt(strings[3]);
            this.nr_issue = Integer.parseInt(strings[4]);
            this.nr_mr = Integer.parseInt(strings[5]);
            this.hot = nr_watch*weights[0]+nr_star*weights[1]+nr_fork*weights[2]+nr_issue*weights[3]+nr_mr*weights[4];
        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Integer count=Integer.parseInt(scanner.nextLine());

        String[] strings = scanner.nextLine().split(" ");
        Integer[] weights = new Integer[5];
        for(int i=0;i<5;i++){
            weights[i]=Integer.parseInt(strings[i]);
        }
        Project.weights=weights;


        PriorityQueue<Project> queue = new PriorityQueue<>(((o1, o2) -> Objects.equals(o1.hot, o2.hot) ?o1.name.compareTo(o2.name):o2.hot-o1.hot));
        while(count-->0){
            String project = scanner.nextLine();
            queue.add(new Project(project));
        }
        while(!queue.isEmpty()){
            System.out.println(queue.poll().name);
        }

    }
}
