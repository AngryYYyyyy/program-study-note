package conclusion.dp.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 10:20
 * @Description：
 */
public class Solution01 {
    public void hanoi(int n){
        if(n>0){
            func(n,"left","right","mid");
        }
    }

    private void func(int n, String from, String to, String other) {
        if(n==1){
            System.out.println("Move 1 from "+from+" to "+to);
            return;
        }
        func(n-1,from,other,to);
        System.out.println("Move "+n+" from "+from+" to "+to);
        func(n-1,other,to,from);
    }
    public static void main(String[] args) {
        Solution01 solution = new Solution01();
        solution.hanoi(3);
    }
}
