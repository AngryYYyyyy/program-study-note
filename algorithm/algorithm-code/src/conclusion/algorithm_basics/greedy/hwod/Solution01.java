package conclusion.algorithm_basics.greedy.hwod;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 22:12
 * @Description：
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
