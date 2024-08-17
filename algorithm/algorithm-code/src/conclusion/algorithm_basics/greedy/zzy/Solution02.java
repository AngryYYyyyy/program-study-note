package conclusion.algorithm_basics.greedy.zzy;

import java.util.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/17 23:45
 * @Description：会议安排问题的求解，使用贪心算法和递归暴力方法
 */
public class Solution02 {
    /**
     * 定义会议类，包含会议的开始和结束时间
     */
    public static class Meeting {
        public int start;
        public int end;

        /**
         * 会议的构造方法
         * @param start 会议的开始时间
         * @param end 会议的结束时间
         */
        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 使用贪心算法求解最多能参加的会议数
     * @param meetings 所有会议数组
     * @return 最大可参加的会议数量
     */
    private static int maxMeeting1(Meeting[] meetings) {
        /* 以会议结束时间升序排序 */
        Arrays.sort(meetings, (o1, o2) -> o1.end - o2.end);
        int timeLine = 0;
        int count = 0;
        for(Meeting meeting : meetings) {
            /* 检查当前会议是否能参加 */
            if(meeting.start >= timeLine) {
                timeLine = meeting.end;
                count++;
            }
        }
        return count;
    }

    /**
     * 使用递归暴力方法求解最多能参加的会议数
     * @param meetings 所有会议数组
     * @return 最大可参加的会议数量
     */
    private static int maxMeeting2(Meeting[] meetings) {
        LinkedList<Meeting> list = new LinkedList<>();
        for (Meeting meeting : meetings) {
            list.offer(meeting);
        }
        return process(list, 0);
    }

    /**
     * 递归处理，寻找最优会议安排
     * @param meetings 当前待处理的会议列表
     * @param timeLine 当前时间线，即最后一个被选中会议的结束时间
     * @return 最大会议数
     */
    private static int process(LinkedList<Meeting> meetings, int timeLine) {
        if(meetings.isEmpty()) return 0;
        int count = 0;
        for(Meeting meeting : meetings) {
            if(meeting.start >= timeLine) {
                LinkedList<Meeting> list = new LinkedList<>(meetings);
                list.remove(meeting);
                count = Math.max(count, process(list, meeting.end) + 1);
            }
        }
        return count;
    }

    /**
     * 生成随机会议数组用于测试
     * @param programSize 生成会议的数量
     * @param timeMax 会议时间的最大范围
     * @return 随机生成的会议数组
     */
    public static Meeting[] generatePrograms(int programSize, int timeMax) {
        Meeting[] ans = new Meeting[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Meeting(r1, r1 + 1);
            } else {
                ans[i] = new Meeting(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    /**
     * 主函数，用于执行测试
     */
    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        System.out.println("开始测试!");
        for (int i = 0; i < timeTimes; i++) {
            Meeting[] meetings = generatePrograms(programSize, timeMax);
            if (maxMeeting2(meetings) != maxMeeting1(meetings)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束!");
    }
}
