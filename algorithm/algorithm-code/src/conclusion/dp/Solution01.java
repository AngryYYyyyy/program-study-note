package conclusion.dp;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/15 22:41
 * @Description：
 */

/*题目描述
“吃货”和“馋嘴”两人到披萨店点了一份铁盘（圆形）披萨，并嘱咐店员将披萨按放射状切成大小相同的偶数扇形小块。但是粗心服务员将技萨切成了每块大小都完全不同奇数块，且肉眼能分辨出大小。

由于两人都想吃到最多的披萨，他们商量了一个他们认为公平的分法：从“吃货”开始，轮流取披萨。

除了第一块披萨可以任意选取以外，其他都必须从缺口开始选。他俩选披萨的思路不同。

“馋嘴”每次都会选最大块的披萨，而且“吃货"知道“馋嘴”的想法。

已知披萨小块的数量以及每块的大小，求“吃货”能分得的最大的披萨大小的总和。*/
public class Solution01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] pizzas=new int[n];
        for (int i = 0; i < n; i++) {
            pizzas[i]=sc.nextInt();
        }
        sc.close();
        int chihuo=sumPizzas(pizzas);
        System.out.println(chihuo);
    }
    private static int sumPizzas(int[] pizzas) {
        int sum=0;
        for(int i=0;i<pizzas.length;i++){
            sum=Math.max(sum,chihuoChoice(i,pizzas,0));
        }
        return sum;
    }

    private static int chihuoChoice(int i, int[] pizzas,int count) {
        if(count>=pizzas.length){return pizzas[i];}
        int n = pizzas.length;
        int index=chanzuiChoice(i,pizzas);
        int pre=(index-1+n)%n;
        int next=(index+1)%n;
        int sum1=chihuoChoice(pre,pizzas,count+2)+pizzas[i];
        int sum2=chihuoChoice(next,pizzas,count+2)+pizzas[i];
        return Math.max(sum1,sum2);
    }
    private static int chanzuiChoice(int i,int[] pizzas) {
        int n = pizzas.length;
        int pre=(i-1+n)%n;
        int next=(i+1)%n;
        return pizzas[pre]>pizzas[next]?pre:next;
    }
}
