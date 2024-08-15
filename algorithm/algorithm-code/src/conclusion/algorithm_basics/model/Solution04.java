package conclusion.algorithm_basics.model;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 0:14
 * @Description：
 */
public class Solution04 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        sc.nextLine();
        String[] strings = sc.nextLine().split("-");
        String s=stringSplit(strings,k);
        System.out.println(s);
    }

    private static String stringSplit(String[] strings,int k) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < strings.length; i++) {
            builder.append(strings[i]);
        }
        LinkedList<String> list = handleSubstrings(builder.toString(), k);
        if(list.isEmpty()) return strings[0];
        String collect = String.join("-", list);
        String ans = strings[0].concat("-").concat(collect);
        return ans;
    }

    private static  LinkedList<String> handleSubstrings(String string, int k) {
        char[] charArray = string.toCharArray();
        LinkedList<String> ans = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            builder.append(charArray[i]);
            if((i+1)%k==0){
                String s = caseConversion(builder.toString());
                ans.add(s);
                builder=new StringBuilder();
            }
        }
        if(!builder.toString().isEmpty()){
            String s = caseConversion(builder.toString());
            ans.add(s);
        }
        return ans;
    }


    public static String caseConversion(String str){
        int lowerCaseCount = 0;
        int upperCaseCount = 0;
        for (char c : str.toCharArray()) {
            if (Character.isLowerCase(c)) {
                lowerCaseCount++;
            } else if (Character.isUpperCase(c)) {
                upperCaseCount++;
            }
        }
        return lowerCaseCount > upperCaseCount ? str.toLowerCase() :
                upperCaseCount > lowerCaseCount ? str.toUpperCase() : str;
    }
}
