package conclusion.dp.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/23 14:45
 * @Description：
 */
public class Solution12 {
    public static int horseJump(int a,int b,int k){
        if(k==0) return 0;
        return func(k,0,0,a,b);
    }
    /*（2,1）（2，-1）（1，-2）（-1，-2）（-2，-1）（-2，1）（-1，2）（1，2）*/
    private static final int[][]directions=new int[][]{{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2},{1,2}};
    /*当前位置x，y剩余restStep步，该如何选择*/
    private static int func(int restStep,int x,int y,int a,int b){
        if(restStep==0) return x==a&&y==b?1:0;
        int ways=0;
        for(int[]direction:directions){
            int nx=x+direction[0];
            int ny=y+direction[1];
            if(isValid(nx,ny)){
                ways+=func(restStep-1,nx,ny,a,b);
            }
        }
        return ways;
    }
    private static boolean isValid(int x,int y){
        return x>=0&&y>=0&&x<9&&y<10;
    }

    public static int horseJump1(int a,int b,int k){
        if(k==0) return 0;
        int[][][] dp = new int[k + 1][9][10];
        dp[0][a][b]=1;
        for(int restStep=1;restStep<=k;restStep++){
            for(int x=0;x<9;x++){
                for(int y=0;y<10;y++){
                    int ways=0;
                    for(int[]direction:directions){
                        int nx=x+direction[0];
                        int ny=y+direction[1];
                        if(isValid(nx,ny)){
                            ways+=dp[restStep-1][nx][ny];;
                        }
                    }
                    dp[restStep][x][y]=ways;
                }
            }
        }
        return dp[k][0][0];
    }



    public static void main(String[] args) {
        System.out.println(horseJump(3, 1, 2));
        System.out.println(horseJump1(3, 1, 2));
    }
}
