package class07;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/16 13:58
 * @Description：
 */
public class Main {
    public static void main(String[] args) {

    }
    static int max=0;
    private static  void dfs(int i,int[]gems,int restValue,int count){
        if((i==gems.length&&restValue>=0)||restValue==0){
            if(count>max){
                max=count;
            }
            return;
        }
        for(int j=0;restValue-gems[i]*j>=0;j++){
            dfs(i+1,gems,restValue-gems[i]*j,count+j);
        }
    }
}
