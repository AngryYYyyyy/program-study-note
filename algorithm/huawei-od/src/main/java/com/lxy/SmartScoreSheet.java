package com.lxy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/9 11:44
 * @Description：
 */
public class SmartScoreSheet {
    public static int index;
    public static class Student{
        public String name;
        public int[] score;

        public Student(String name, int[] score) {
            this.name = name;
            this.score = score;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        String[] subjects = new String[m];
        for (int i = 0; i < m; i++) {
            subjects[i] = sc.next();
        }
        Student[] students = new Student[n];
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int[] score = new int[m];
            for (int j = 0; j < m; j++) {
                score[j] = sc.nextInt();
            }
            students[i] = new Student(name,score);
        }
        String rankBy="";
        if(sc.hasNext()){
            rankBy=sc.next();
        }


        Student[] rank = gradeRanking(students, rankBy, subjects);
        for (int i = 0; i < n; i++) {
            System.out.print(students[i].name+" ");
        }
    }

    private static Student[]  gradeRanking(Student[] students, String rankBy,String[] subjects) {
        if(rankBy.isEmpty()){
            Arrays.sort(students,new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    int sum1 = Arrays.stream(o1.score).sum();
                    int sum2 = Arrays.stream(o2.score).sum();
                    return sum2==sum1?o1.name.compareTo(o2.name):sum2-sum1;
                }
            });
        }else {
            index=0;
            while (index < subjects.length) {
                if(rankBy.equals(subjects[index])){
                    break;
                }
                index++;
            }
            Arrays.sort(students,new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    int score1=o1.score[index];
                    int score2=o2.score[index];
                    return score2==score1?o1.name.compareTo(o2.name):score2-score1;
                }
            });
        }
        return students;
    }
}
