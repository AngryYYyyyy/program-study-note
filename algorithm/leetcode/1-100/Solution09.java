/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/25 19:02
 * @Description：
 */
public class Solution09 {
    public boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        int l=0,r=str.length()-1;
        while(l<r){
            if(str.charAt(l)!=str.charAt(r)){
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
    /*不使用整数转字符串*/
    /*直接反转一半，比较*/
    public boolean isPalindrome1(int x) {
        if(x<0||(x!=0&&x%10==0)){
            return false;
        }
        int revertedNumber=0;
        while(x>revertedNumber){
            revertedNumber=revertedNumber*10+x%10;
            x=x/10;
        }
        return x==revertedNumber||x==revertedNumber/10;
    }
}
