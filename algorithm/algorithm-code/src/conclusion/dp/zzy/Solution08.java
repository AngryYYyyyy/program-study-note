package conclusion.dp.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 22:53
 * @Description：
 */
public class Solution08 {
    public static int decode(String s) {
        if (s.isEmpty()) return 0;
        return func(0, s);
    }
    /*[0,i-1]已经解析*/
    /*[i....]如何解析*/
    private static int func(int i, String s) {
        if (i == s.length()) return 1;
        /*错误情况，0无法解析*/
        if(s.charAt(i) == '0') return 0;
        int p1 = func(i + 1, s);
        int p2 = 0;
        if (i + 1 < s.length()) {
            int num = (s.charAt(i) - '0') * 10 + s.charAt(i + 1) - '0';
            if (num <= 26) {
                p2 = func(i + 2, s);
            }
        }
        return p1 + p2;
    }

    public static int dp(String s) {
        if (s.isEmpty()) return 0;
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n-1; i >=0 ; i--) {
            if(s.charAt(i) == '0') continue;
            int p1 = dp[i + 1];
            int p2 = 0;
            if (i + 1 < n) {
                int num = (s.charAt(i) - '0') * 10 + s.charAt(i + 1) - '0';
                if (num <= 26) {
                    p2 = dp[i + 2];
                }
            }
            dp[i] = p1 + p2;
        }
        return dp[0];
    }

    // 为了测试
    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = randomString(len);
            int ans0 = decode(s);
            int ans1 = dp(s);
            if (ans0 != ans1 ) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
