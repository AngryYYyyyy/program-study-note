package conclusion.dp.zzy;

import java.util.HashMap;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 23:25
 * @Description：
 */
public class Solution09 {
    public static int papercutting(String target,String[]stickers){
        if(target.isEmpty()||stickers.length==0) return -1;
        int count = func(stickers, target);
        return count== Integer.MAX_VALUE?-1:count;
    }
    /*重复选择stickers*/
    private static int func(String[]stickers,String target){
        int minCount=Integer.MAX_VALUE;
        if(target.isEmpty()) return 0;
        for(String sticker : stickers){
            String rest=minus(target,sticker);
            if(!rest.equals(target)){
                int count=func(stickers,rest)+1;
                minCount=Math.min(minCount,count);
            }
        }
        return minCount;
    }


    private static String minus(String target, String sticker) {
        int[]count=new int[26];
        for(int i=0;i<target.length();i++){
            count[target.charAt(i)-'a']++;
        }
        for(int i=0;i<sticker.length();i++){
            count[sticker.charAt(i)-'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<26;i++){
            if(count[i]>0){
                for (int j=0;j<count[i];j++){
                    builder.append((char)(i+'a'));
                }
            }
        }
        return builder.toString();
    }

    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/



    public static int papercutting1(String target,String[]stickers){
        if(target.isEmpty()||stickers.length==0) return -1;
        int[][] counts = new int[stickers.length][26];
        for(int i=0;i<stickers.length;i++){
            for(int j=0;j<stickers[i].length();j++){
                counts[i][stickers[i].charAt(j)-'a']++;
            }
        }
        int count = func1(target, counts);
        return count== Integer.MAX_VALUE?-1:count;
    }
    /*根据当前剩余target选择贴纸*/
    private static int func1(String target, int[][] stickers){
        if(target.isEmpty())return 0;
        int minCount=Integer.MAX_VALUE;
        int[] tcount = new int[26];
        for(int i=0;i<target.length();i++){
            tcount[target.charAt(i)-'a']++;
        }
        for(int i=0;i<stickers.length;i++){
            int[] sticker=stickers[i];
            /*当前贴纸是否有target的第一个字符，重要剪枝！！*/
            if(sticker[target.charAt(0)-'a']>0){
                StringBuilder builder = new StringBuilder();
                for(int j=0;j<26;j++){
                    if(tcount[j]>0){
                        int num=tcount[j]-sticker[j];
                        for(int k=0;k<num;k++){
                            builder.append((char)(j+'a'));
                        }
                    }
                }
                int count=1+func1(builder.toString(),stickers);
                minCount=Math.min(minCount,count);
            }
        }
        return minCount;
    }


    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/



    public static int papercutting2(String target,String[]stickers){
        if(target.isEmpty()||stickers.length==0) return -1;
        int[][] counts = new int[stickers.length][26];
        for(int i=0;i<stickers.length;i++){
            for(int j=0;j<stickers[i].length();j++){
                counts[i][stickers[i].charAt(j)-'a']++;
            }
        }
        /*存储针对不同状态(剩余目标)的值(贴纸数量)*/
        HashMap<String, Integer> dp = new HashMap<>();
        int count = func2(target, counts,dp);
        return count== Integer.MAX_VALUE?-1:count;
    }
    private static int func2(String target, int[][] stickers, HashMap<String, Integer> dp){
        if(dp.containsKey(target)){return dp.get(target);}
        if(target.isEmpty())return 0;
        int minCount=Integer.MAX_VALUE;
        int[] tcount = new int[26];
        for(int i=0;i<target.length();i++){
            tcount[target.charAt(i)-'a']++;
        }
        for(int i=0;i<stickers.length;i++){
            int[] sticker=stickers[i];
            /*当前贴纸是否有target的第一个字符，重要剪枝！！*/
            if(sticker[target.charAt(0)-'a']>0){
                StringBuilder builder = new StringBuilder();
                for(int j=0;j<26;j++){
                    if(tcount[j]>0){
                        int num=tcount[j]-sticker[j];
                        for(int k=0;k<num;k++){
                            builder.append((char)(j+'a'));
                        }
                    }
                }
                String restTarget = builder.toString();
                dp.put(restTarget,func1(restTarget,stickers));
                int count=1+dp.get(restTarget);
                minCount=Math.min(minCount,count);
                dp.put(target,minCount);
            }
        }
        return minCount;
    }


    public static void main(String[] args) {
        String[] stickers={"ba", "c", "abcd"};
        System.out.println(papercutting("babac", stickers));
        System.out.println(papercutting1("babac", stickers));
        System.out.println(papercutting2("babac", stickers));
    }
}


















