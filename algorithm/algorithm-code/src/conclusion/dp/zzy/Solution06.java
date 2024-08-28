package conclusion.dp.zzy;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/22 20:51
 * @Description：
 */
public class Solution06 {
    public int cardGame(int[]cards){
        if(cards.length == 0) return 0;
        int first=func1(cards,0,cards.length-1);
        int second=func2(cards,0,cards.length-1);
        return Math.max(first,second);
    }
    /*先手*/
    public int func1(int[]cards,int left,int right){
        if(left == right) return cards[left];
        int choiceLeft=cards[left] + func2(cards,left+1,right);
        int choiceRight=cards[right] + func2(cards,left,right-1);
        return Math.max(choiceLeft,choiceRight);
    }
    /*后手*/
    public int func2(int[]cards,int left,int right){
        if(left == right) return 0;
        int choiceLeft=func1(cards,left+1,right);
        int choiceRight=func1(cards,left,right-1);
        return Math.min(choiceLeft,choiceRight);
    }
}
