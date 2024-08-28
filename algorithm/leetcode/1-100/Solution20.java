import java.util.Stack;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/26 9:56
 * @Description：
 */
public class Solution20 {
    public boolean isValid(String s) {
        /*先入后出的特性*/
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                /*简化后续判断*/
                stack.push(c=='('?')':(c=='{'?'}':']'));
            } else {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (top!=c) return false;
            }
        }
        return stack.isEmpty();
    }
}
