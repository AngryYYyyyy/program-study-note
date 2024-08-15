package com.lxy;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/2 20:48
 * @Description：
 */
public class ParkingLotVehicleStatistics {
    public static void main(String[] args) {
        int count=0;
        String s = new Scanner(System.in).nextLine();
        String[] positions = s.replaceAll(",", "").replaceAll("0+", "0").split("0");
        for (String position : positions) {
            int length = position.length();
                if(length/3!=0){
                    count+=length/3;
                    length%=3;
                }
                if (length/2!=0){
                    count+=length/2;
                    length%=2;
                }
                count+=length;
        }
        System.out.println(count);
    }
}
