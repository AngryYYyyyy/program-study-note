import java.util.LinkedList;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/26 18:28
 * @Description：
 */
public class Solution {
    private int game(int[]skills,int k){
        LinkedList<Integer> list = new LinkedList<>();
        for(int i=0;i<skills.length;i++){
            list.add(skills[i]);
        }
        int count=0;
        while(true){
            Integer first = list.removeFirst();
            Integer second = list.removeFirst();
            if(first>second){
                count++;
                if(count==k){
                    for(int i=0;i<skills.length;i++){
                        if(skills[i]==first){
                            return i;
                        }
                    }
                }
                list.addLast(second);
                list.addFirst(first);
            }else{
                count=1;
                list.addLast(first);
                list.addFirst(second);
            }
        }
    }
    /*skills = [4,2,6,3,9], k = 2*/
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.game(new int[]{4,2,6,3,9},2));
    }
}
