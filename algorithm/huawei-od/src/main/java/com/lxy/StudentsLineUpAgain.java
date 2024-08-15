package com.lxy;

import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 11:19
 * @Description：
 */
public class StudentsLineUpAgain {
    public static class Block{
        public int groupId;
        public int count;
        public Block(int groupId, int count){
            this.groupId = groupId;
            this.count = count;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] currentQueue = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] group = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int result=again(currentQueue,group);
        System.out.println(result);
    }

    private static int again(int[] currentQueue, int[] group) {
        int step=0;
        LinkedList<Block> blocks = new LinkedList<>();
        HashMap<Integer,Integer> mapToGroupId = new HashMap<>();
        for(int i=0;i<group.length;i++){
            mapToGroupId.put(group[i],i/3);
        }

        for (int cur : currentQueue) {
            int groupId = mapToGroupId.get(cur);
            if (blocks.isEmpty() || blocks.getLast().groupId != groupId) {
                blocks.add(new Block(groupId, 1));
            } else {
                blocks.getLast().count++;
            }
        }

        blocks.removeIf(e->e.count==3);
        while(!blocks.isEmpty()) {
            Block block =blocks.removeFirst();
            if(block.count==1){
                boolean isTwo=blocks.stream().anyMatch(e-> (e.groupId == block.groupId) && (e.count == 2));
                step=isTwo?step+1:step+2;
                blocks = combine(blocks, block.groupId);
            }else if(block.count==2){
                step+=1;
                blocks = combine(blocks, block.groupId);
            }
        }
        return step;
    }

    private static LinkedList<Block> combine(LinkedList<Block> blocks, int groupId) {
        LinkedList<Block> list = new LinkedList<>();
        while(!blocks.isEmpty()){
            Block block = blocks.removeFirst();
            if(block.groupId==groupId) {
                continue;
            }else if(list.isEmpty()||list.getLast().groupId!=groupId){
                list.addLast(new Block(block.groupId,block.count));
            }else {
                list.getLast().count+=block.count;
            }
        }
        list.removeIf(e->e.count==3);
        return list;
    }
}
