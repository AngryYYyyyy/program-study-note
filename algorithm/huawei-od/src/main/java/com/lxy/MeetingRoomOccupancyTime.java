package com.lxy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 21:23
 * @Description：
 */
public class MeetingRoomOccupancyTime {
    public static class Meeting{
        public int start;
        public int end;
        public Meeting(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            String[] input = sc.nextLine().split(" ");
            meetings[i] = new Meeting(Integer.parseInt(input[0]), Integer.parseInt(input[1])); // 创建Meeting对象
        }
        LinkedList<String>timeSlots=occupancyTimeSlots(meetings);
        for(String timeSlot:timeSlots){
            System.out.println(timeSlot);
        }
    }

    private static LinkedList<String> occupancyTimeSlots(Meeting[] meetings) {
        Arrays.sort(meetings,(o1,o2)->o1.start-o2.start);
        LinkedList<String> timeSlots = new LinkedList<>();
        int i=0;
        while(i<meetings.length){
            int curStart = meetings[i].start;
            int curEnd = meetings[i].end;
            while(i<meetings.length && meetings[i].start<=curEnd){
                curEnd = Math.max(curEnd, meetings[i].end);
                i++;
            }
            timeSlots.add(curStart+" "+curEnd);
        }
        return timeSlots;
    }
}
