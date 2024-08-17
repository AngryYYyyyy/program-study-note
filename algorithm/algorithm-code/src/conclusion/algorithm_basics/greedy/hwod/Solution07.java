package conclusion.algorithm_basics.greedy.hwod;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 8:37
 * @Description：
 */
public class Solution07 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] charArray = sc.nextLine().toCharArray();
        int countX=0;
        int countY=0;
        int count=0;
        for (char c : charArray) {
            if(c=='X'){
                countX++;
            }else {
                countY++;
            }
            if(countX==countY){
                count++;
                countX=0;
                countY=0;
            }
        }
        System.out.println(count);
    }
}
