package class07;

import javax.sound.sampled.Line;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
/*
* 思路：最大线段重合问题初步观察需要判断不同line之间start与end的关系，先将lines以start为基准升序排序，这样确保后续的line的start都在之前line的右侧，
* 这样只需比较end的关系即可，同时需要建立小根堆（插入时自动排序），以记录能够堆结果产生影响的最小end。当前line如果其start大于堆顶end，那么说明该end
* 已经不会对结果产生影响，因为后续的start均会大于该值，因此移除，直到找到start小于或等于堆顶的end，或者堆为空，此时当前line会穿过以end结尾的线段，即
* 重合，并且也会与堆内其他end结尾的线段重合，那么则将其end加入到小根堆中，此时堆内元素的数量就是当前lines重合的数量，记录结果比较最大值，lines遍历结束，
* 后，返回最大的记录。
* 时间复杂度：O(N*logN)
*
*
*
* */
public class Question01 {
    public static int solution(int[][]lines) {
        //根据每条线段的起始位置start对所有线段进行排序。
        Arrays.sort(lines,(line1,line2) -> (line1[0]-line2[0]));
        //使用一个小根堆来维护当前活跃的线段的结束位置end。小根堆允许我们快速地访问当前所有重叠线段中最早结束的线段。
        PriorityQueue<Integer> heap=new PriorityQueue<>();
        //记录结果
        int res=0;
        heap.add(lines[0][1]);
        //遍历线段
        for (int[] line : lines) {
            // 移除heap内所有end早于当前线段start的线段
            if(!heap.isEmpty()&&line[0]>=heap.peek()){
               heap.poll();
            }
            //当前线段end加入heap
            heap.add(line[1]);
            //更新历史最大重合线段数量
            res=Math.max(res,heap.size());
        }
        return res;
    }

    //-----------------------------------------------------------------------------------------------------

    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p += 1) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    public static int maxCover2(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        Arrays.sort(lines, new StartComparator());
        // 小根堆，每一条线段的结尾数值，使用默认的
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            // lines[i] -> cur 在黑盒中，把<=cur.start 东西都弹出
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i].end);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static class EndComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.end - o2.end;
        }

    }

    // 和maxCover2过程是一样的
    // 只是代码更短
    // 不使用类定义的写法
    public static int maxCover3(int[][] m) {
        // m是二维数组，可以认为m内部是一个一个的一维数组
        // 每一个一维数组就是一个对象，也就是线段
        // 如下的code，就是根据每一个线段的开始位置排序
        // 比如, m = { {5,7}, {1,4}, {2,6} } 跑完如下的code之后变成：{ {1,4}, {2,6}, {5,7} }
        Arrays.sort(m, (a, b) -> (a[0] - b[0]));
        // 准备好小根堆，和课堂的说法一样
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int[] line : m) {
            while (!heap.isEmpty() && heap.peek() <= line[0]) {
                heap.poll();
            }
            heap.add(line[1]);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    // for test
    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static class StartComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }

    }

    public static void main(String[] args) {

        Line l1 = new Line(4, 9);
        Line l2 = new Line(1, 4);
        Line l3 = new Line(7, 15);
        Line l4 = new Line(2, 4);
        Line l5 = new Line(4, 6);
        Line l6 = new Line(3, 7);

        // 底层堆结构，heap
        PriorityQueue<Line> heap = new PriorityQueue<>(new StartComparator());
        heap.add(l1);
        heap.add(l2);
        heap.add(l3);
        heap.add(l4);
        heap.add(l5);
        heap.add(l6);

        while (!heap.isEmpty()) {
            Line cur = heap.poll();
            System.out.println(cur.start + "," + cur.end);
        }

        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            int ans3 = maxCover3(lines);
            int ans4 =solution(lines);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }

}
