package conclusion.algorithm_basics.greedy.hwod;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 22:12
 * @Description：
 */
/*特定大小的停车场，数组cars表示，其中1表示有车，0表示没车。
车辆大小不一，小车占一个车位(长度1)，货车占两个车位(长度2)，
卡车占三个车位(长度3)，统计停车场最少可以停多少辆车，返回具体的数目。
*/
public class Solution01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] positions = sc.nextLine().replaceAll(",", "").replaceAll("0+", "0").split("0");
        int cars=getMinCars(positions);
        System.out.println(cars);
    }

    private static int getMinCars(String[] positions) {
        int count=0;
        for (String position : positions) {
            if(position.isEmpty()) continue;
            int length = position.length();
            if(length>3){
                count+=length/3;
                length=length%3;
            }
            if(length>0){
                count++;
            }
        }
        return count;
    }
}
