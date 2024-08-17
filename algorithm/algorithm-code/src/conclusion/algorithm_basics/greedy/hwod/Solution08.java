package conclusion.algorithm_basics.greedy.hwod;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/14 8:40
 * @Description：
 */
public class Solution08 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] charArray = sc.nextLine().toCharArray();
        String minString=smallestStringAfterSwap(charArray);
        System.out.println(minString);
    }
    public static class MinChar{
        public char character;
        public int index;
        public MinChar(char character, int index) {
            this.character = character;
            this.index = index;
        }
        public MinChar(MinChar minChar) {
            this.character = minChar.character;
            this.index = minChar.index;
        }
    }
    public static String smallestStringAfterSwap(char[] charArray) {

        int n = charArray.length;
        MinChar minChar = new MinChar(charArray[n-1],n-1);
        MinChar[] minChars = new MinChar[n];
        for(int i=n-1;i>=0;i--){
            char cur=charArray[i];
            char min=minChar.character;
            if(cur<min){
                minChar.index=i;
                minChar.character=cur;
            }
            minChars[i]=new MinChar(minChar);
        }
        for(int i=0;i<n;i++){
            char cur=charArray[i];
            minChar=minChars[i];
            if(cur>minChar.character){
                swap(charArray,i, minChar.index);
                break;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (char c : charArray) {
            builder.append(c);
        }
        return builder.toString();
    }
    public static void swap(char[]charArray,int i, int j) {
        char tmp=charArray[i];
        charArray[i]=charArray[j];
        charArray[j]=tmp;
    }
}
