package conclusion.dp.zzy;

import java.util.Stack;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 19:22
 * @Description：
 */
public class Solution04 {
    public void reserve(Stack<Integer> stack) {
        if (stack.isEmpty()) return;
        int last = func(stack);
        reserve(stack);
        stack.push(last);
    }
    /*获取栈底元素*/
    private int func(Stack<Integer> stack) {
        if (stack.size()==1) return stack.pop();
        int top = stack.pop();
        int last=func(stack);
        stack.push(top);
        return last;
    }

    public static void main(String[] args) {
        Solution04 solution = new Solution04();
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        solution.reserve(stack);
        System.out.println(stack);
    }

}
