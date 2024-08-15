package com.lxy;



import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/2 23:26
 * @Description：
 */
public class ExtractExpression {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s=sc.nextLine();
        String maxLenExp = getMaxLenExp(s);
        if (maxLenExp.isEmpty()) {
            System.out.println(0);
        } else {
            System.out.println(calcExpStr(maxLenExp));
        }
    }

    public static String getMaxLenExp(String s) {

        Matcher matcher = Pattern.compile("([+-]?(\\d+[+*-])*\\d+)").matcher(s);

        String maxLenExp = "";

        while (matcher.find()) {
            String exp = matcher.group();
            if (exp.length() > maxLenExp.length()) {
                maxLenExp = exp;
            }
        }

        return maxLenExp;
    }

    public static long calcExpStr(String exp) {
        // 这里在表达式结尾追加"+0"是为了避免后面收尾操作
        exp += "+0";

        // 记录表达式中各块的操作数
        Stack<Integer> stack = new Stack<>();
        // 各块操作数的"值"部分的缓存容器
        StringBuilder numStr = new StringBuilder();

        // 各块操作数的"系数"部分，开头的操作数系数默认为1
        Integer num_coef = 1;

        // 如果合法的表达式可以+或-开头
        char start = exp.charAt(0);

        if (start == '+' || start == '-') {
            // 将exp开头的符号去掉
            exp = exp.substring(1);
        }

        if (start == '-') {
            // 如果表达式开头是负号，则开头操作数的系数为-1
            num_coef = -1;
        }

        // 处理剩余表达式
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c >= '0' && c <= '9') {
                numStr.append(c);
                continue;
            }

            // 如果扫描到的字符c是运算符，那么该运算符打断了前面操作数的扫描，前面操作数 = 系数 * 值
            Integer num = num_coef * Integer.parseInt(numStr.toString());
            stack.add(num);

            // 清空缓存容器，用于下一个操作数的”值“记录
            numStr = new StringBuilder();

            num_coef = switch (c) {
                case '+' ->
                    // 如果运算符是加法，则后一个操作数的系数为1
                        1;
                case '-' ->
                    // 如果运算符是减法，则后一个操作数的系数为-1
                        -1;
                case '*' ->
                    // 如果运算符是乘法，则后一个操作数的系数为栈顶值，比如2*3，其中2可以当作3的系数
                        stack.pop();
                default -> num_coef;
            };
        }

        // 表达式分块后，每一块独立计算，所有块的和就是表达式的结果
        Integer result = 0;
        for (Integer i : stack) {
            result += i;
        }
        return result;
    }
}
