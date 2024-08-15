package com.lxy;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 19:56
 * @Description：
 */
public class SocialDistancing {
    public static class Seat{
        public int seatId;
        public int distance;
        public Seat(int seatId, int distance) {
            this.seatId = seatId;
            this.distance = distance;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String input= sc.nextLine();
        int[] seatOrLeave = Arrays.stream(input.substring(1, input.length() - 1).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        int seatId=findSeat(n,seatOrLeave);
        System.out.println(seatId);
    }

    private static int findSeat(int n, int[] seatOrLeave) {
        PriorityQueue<Seat> queue = new PriorityQueue<>((o1, o2) -> o1.distance==o2.distance?o1.seatId-o2.seatId:o2.distance - o1.distance);
        boolean[] occupied=new boolean[n];
        queue.add(new Seat(0,0));
        Seat seat=null;
        for(int action:seatOrLeave){
            if(action==1){
                if(queue.isEmpty())return -1;
                seat = queue.poll();
                occupied[seat.seatId]=true;
                queue=updateDistance(occupied);
            }else{
                if(occupied[-action]){
                    occupied[-action]=false;
                    queue=updateDistance(occupied);
                }
            }
        }
        return seat==null?-1:seat.seatId;
    }

    private static PriorityQueue<Seat> updateDistance(boolean[] occupied) {
        PriorityQueue<Seat> queue = new PriorityQueue<>((o1, o2) -> o1.distance == o2.distance ? o1.seatId - o2.seatId : o2.distance - o1.distance);
        for (int i = 0; i < occupied.length; i++) {
            if(!occupied[i]){
                int distance=getDistance(i,occupied);
                queue.add(new Seat(i,distance));
            }
        }
        return queue;
    }

    private static int getDistance(int i, boolean[] occupied) {
        int left=i;
        int leftDistance=0;
        int right=i;
        int rightDistance=0;
        while(left>=0&&!occupied[left]){
            leftDistance++;
            left--;
        }
        while(right<occupied.length&&!occupied[right]){
            rightDistance++;
            right++;
        }
        if(i==occupied.length-1)return leftDistance;
        return Math.min(leftDistance,rightDistance);
    }
}
