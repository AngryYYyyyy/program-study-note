/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/26 11:16
 * @Description：
 */
public class Solution28 {
    public int strStr(String haystack, String needle) {
        for(int l=0;l<haystack.length();l++){
            int r=l;
            int i=0;
            while(haystack.charAt(r)==needle.charAt(i)){
                r++;
                i++;
                if(i==needle.length()){
                    return l;
                }
            }
            l++;
        }
        return -1;
    }
    public int strStr1(String haystack, String needle) {
        int[]next=getArrayNext(needle);
        int i=0,j=0;
        while(i<haystack.length()&&j<needle.length()){
            if(haystack.charAt(i)==needle.charAt(j)){
                /*匹配*/
                i++;
                j++;
            } else if (next[j]==-1) {
                /*j==0,还不匹配*/
                i++;
            }else{
                /*跳转到匹配的最大前缀后缀数组的下一个*/
                /*next[]，既代表长度，也代表前缀结束的下一位*/
                j=next[j];
            }
        }
        return j==needle.length()?i-j:-1;
    }

    private int[] getArrayNext(String needle) {
        if (needle.length() == 1) {
            return new int[] { -1 };
        }
        int[] next=new int[needle.length()];
        /*固定值*/
        next[0]=-1;
        next[1]=0;
        int i=2;
        /*与i-1比对的下标*/
        int ni=0;
        while(i<needle.length()){
            if(needle.charAt(ni)==needle.charAt(i-1)){
                /*匹配*/
                /*++ni，简化后续匹配*/
                next[i++]=++ni;
            } else if (ni>0) {
                ni=next[ni];
            }else{
                /*前面没有匹配的*/
                next[i++]=0;
            }
        }
        return next;
    }
}
