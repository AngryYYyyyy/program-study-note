package conclusion.algorithm_basics.model;


import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/13 14:34
 * @Description：
 */
public class Solution16 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //初始化排队顺序
        int[] current = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] group = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = current.length;

        //序号 -> 组号的对应关系
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(group[i],i/3+1);
        }
        //输出最多需要排多少次
        System.out.println(minMoves(current, map));
    }
    public static class Group{
        public int groupId;
        public int count;
        public Group(int groupId, int count) {
            this.groupId = groupId;
            this.count = count;
        }
    }

    /**
     * 按组调整需要的最小调整次数
     *
     * @param current
     * @param groupMap
     * @return int
     */
    public static int minMoves(int[] current, HashMap<Integer,Integer> groupMap) {
        int moves = 0;
        LinkedList<Group> groups = new LinkedList<>();
        for (int studentId : current) {
            int groupId = groupMap.get(studentId);
            if (groups.isEmpty() || groups.getLast().groupId != groupId) {
                groups.offer(new Group(groupMap.get(studentId), 1));
            } else {
                groups.getLast().count++;
            }
        }
        while(!groups.isEmpty()) {
            Group group = groups.removeFirst();
            if(group.count==1){
                Group next = groups.stream()
                        .filter(g -> g.groupId == group.groupId)
                        .findFirst().get();
                Group[] neighbors = findNeighbors(groups, next);
                //找到另一个一个组号相同的组，如果组的大小为2，且为当前位置的前后邻居不为同一组，移动一步即当前group移动到next
                if(next.count==2&&(neighbors[1]==null||neighbors[0].groupId!=neighbors[1].groupId)){
                    moves++;
                }else{
                    moves+=2;
                }
            }else if(group.count==2){
                moves++;
            }
            groups=removeByGroupId(groups,group.groupId);
        }
        return moves;
    }

    /**
     * 把queue中组号为groupId的删去后合并剩下的组
     *
     * @param groups
     * @param groupId
     * @return java.util.LinkedList<OD347.Main.Block>
     */
    public static LinkedList<Group> removeByGroupId(LinkedList<Group> groups, int groupId) {
        LinkedList<Group> back_queue = new LinkedList<>();
        while (!groups.isEmpty()) {
            Group group = groups.removeFirst();
            if (group.groupId == groupId) {
                continue;
            }
            if (back_queue.isEmpty() || back_queue.getLast().groupId != group.groupId) {
                back_queue.addLast(new Group(group.groupId, group.count));
            } else {
                back_queue.getLast().count += group.count;
            }
        }

        return back_queue;
    }
    // 获取给定group的前一个和后一个对象
    public static Group[] findNeighbors(LinkedList<Group> groups, Group group) {
        Group[] ans=new Group[2];
        ListIterator<Group> iterator = groups.listIterator();
        Group previous = null;
        Group next = null;

        while (iterator.hasNext()) {
            Group current = iterator.next();
            if (current == group) {
                // 已找到当前group，获取下一个group
                next = iterator.hasNext() ? iterator.next() : null;
                break;
            }
            previous = current;
        }
        ans[0] = previous;
        ans[1] = next;
        return ans;
    }


}
