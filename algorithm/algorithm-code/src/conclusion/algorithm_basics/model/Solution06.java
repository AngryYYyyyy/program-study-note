package conclusion.algorithm_basics.model;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 9:42
 * @Description：
 */
public class Solution06 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        LinkedList<String> expression=decompose(n);
        for(String exp:expression){
            System.out.println(exp);
        }
        System.out.println("Result:"+expression.size());
    }

    private static LinkedList<String> decompose(int n) {
        LinkedList<String> list = new LinkedList<>();
        for(int i=n;i>=1;i--){
            int cur=i;
            int sum=0;
            StringBuilder builder = new StringBuilder().append(n).append("=");
            while(cur<=n){
                sum+=cur;
                builder.append(cur).append("+");
                if(sum==n){
                    builder.deleteCharAt(builder.length()-1);
                    list.add(builder.toString());
                    break;
                }
                if(sum>n){
                    break;
                }
                cur++;
            }
        }
        return list;
    }
}
