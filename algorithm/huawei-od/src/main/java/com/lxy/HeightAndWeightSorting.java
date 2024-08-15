package com.lxy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/7 20:57
 * @Description：
 */
public class HeightAndWeightSorting {
    public static class Student{
        public int id;
        public int height;
        public int weight;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        Student[] students = new Student[count];
        for (int i = 0; i < count; i++) {
            students[i]= new Student();
            students[i].id=i+1;
            students[i].height=scanner.nextInt();
        }
        for (int i = 0; i < count; i++) {
            students[i].weight=scanner.nextInt();
        }
        Arrays.sort(students,((o1, o2) -> o1.height==o2.height?(o1.weight==o2.weight?o1.id-o2.id:o1.weight-o2.weight):o1.height-o2.height));
        for (int i = 0; i < count; i++) {
            System.out.print(students[i].id);
        }
    }
}
