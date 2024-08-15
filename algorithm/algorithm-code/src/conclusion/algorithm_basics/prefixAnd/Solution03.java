package conclusion.algorithm_basics.prefixAnd;

import java.util.Scanner;


/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 18:34
 * @Description：
 */
public class Solution03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int maxLen=getMaxLen(s);
        System.out.println(maxLen);
    }

    private static int getMaxLen(String s) {
        int maxLen=0;
        char[] charArray = s.toCharArray();
        int n = charArray.length;
        char[] chars = new char[n * 2];
        for (int i = 0; i < n*2; i++) {
            chars[i]=charArray[i%n];
        }
        for(int start=0;start<n;start++){
            for(int end=start+1;end<start+n;end++){
                if(isFeasible(start,end,chars)){
                    maxLen=Math.max(maxLen,end-start+1);
                }
            }
        }
        return maxLen;
    }
    /*"l"、"o"、"x"*/
    private static boolean isFeasible(int start, int end,char[] chars) {
        int count1=0;
        int count2=0;
        int count3=0;
        for(int i=start;i<=end;i++){
            char c=chars[i];
            if(c=='l'){
                count1++;
            } else if(c=='o'){
                count2++;
            } else if(c=='x'){
                count3++;
            }
        }
        return (count1%2)==0&&(count2%2)==0&&(count3%2)==0;
    }
}
