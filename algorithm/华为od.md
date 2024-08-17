# 【贪心】2024D-停车场车辆统计

#### 题目描述

特定大小的停车场，数组cars表示，其中1表示有车，0表示没车。 

车辆大小不一，小车占一个车位(长度1)，货车占两个车位(长度2)，卡车占三个车位(长度3)，统计停车场最少可以停多少辆车，返回具体的数目。

#### 输入

整型字符串数组cars，其中1表示有车，0表示没车，数组长度小于1000

#### 输出

整型数字字符串，表示最少停车数目。

#### 样例输入 复制

```plain
1,0,1
```

#### 样例输出 复制

```plain
2
```

#### coding

```Java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] positions = sc.nextLine().replaceAll(",", "").replaceAll("0+", "0").split("0");
        int cars=getMinCars(positions);
        System.out.println(cars);
    }

    private static int getMinCars(String[] positions) {
        int count=0;
        for (String position : positions) {
            if(position.isEmpty()) continue;
            int length = position.length();
            if(length>3){
                count+=length/3;
                length=length%3;
            }
            if(length>0){
                count++;
            }
        }
        return count;
    }
}
}
```

# 【哈希集合】2024D-英文输入法

#### 题目描述

主管期望你来实现英文输入法单词联想功能，需求如下： 

1. 依据用户输入的单词前缀，从已输入的英文语句中联想出用户想输入的单词。 
2. 按字典序输出联想到的单词序列，如果联想不到，请输出用户输入的单词前缀。 

注意：

1. 英文单词联想时区分大小写 
2. 缩略形式如"don’t" 判定为两个单词 "don"和 “t” 
3. 输出的单词序列不能有重复单词，且只能是英文单词，不能有标点符号

#### 输入

输入两行。 

首行输入一段由英文单词word和标点构成的语句str，接下来一行为一个英文单词前缀pre。 

0 < word.length() <= 20

0 < str.length() <= 10000，0 < pre.length() <= 20

#### 输出

输出符合要求的单词序列或单词前缀。存在多个时，单词之间以单个空格分割

#### 样例输入 复制

```plain
I love you
lo
He
```

#### 样例输出 复制

```plain
He
```

#### coding

```java
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine().split("[^a-zA-Z]");
        String s = scanner.nextLine();
        PriorityQueue<String> queue = new PriorityQueue<>();
        for (String word : words) {
            if(word.startsWith(s))
            {
                queue.add(word);
            }
        }
        if(queue.isEmpty()){
            System.out.println(s);
        }
        while(!queue.isEmpty()){
            System.out.print(queue.poll()+" ");
        }
    }
}
```

# 【模拟】2024D-API 集群负载统计

#### 题目描述

某个产品的 RESTful API 集合部署在服务器集群的多个节点上，近期对客户端访问日志进行了采集，需要统计各个 API 的访问频次，根据热点信息在服务器节点之间做负载均衡，现在需要实现热点信息统计查询功能。 

RESTful API 的由多个层级构成，层级之间使用/连接，如/A/B/C/D这个地址，A 属于第一级，B 属于第二级，C 属于第三级，D 属于第四级。 

现在负载均衡模块需要知道给定层级上某个名字出现的频次，未出现过用 0 次表示，实现这个功能。

#### 输入

第一行为 N，表示访问历史日志的条数，0<N<=100。
接下来 N 行，每一行为一个 RESTful API 的 URL 地址，约束地址中仅包含英文字母和连接符/，最大层级为 10，每层级字符串最大长度为 10。
最后一行为层级 L 和要查询的关键字。

#### 输出

输出给定层级上，关键字出现的频次，使用完全匹配方式（大小写敏感）。

#### 样例输入 复制

```plain
5
/huawei/computing/no/one
/huawei/computing
/huawei
/huawei/cloud/no/one
/huawei/wireless/no/one
2 computing
```

#### 样例输出 复制

```plain
2
```

#### 提示

在第二层级上，computing 出现了 2 次，因此输出 2.

#### coding

```java
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        scanner.nextLine();// 吸收nextInt后的换行符
        HashMap<Integer, LinkedList<String>> map = new HashMap<>();
        while(row>0){
            row--;
            String url = scanner.nextLine();
            String[] strings = url.split("/");
            int level=1;
            for (String string : strings) {
                if (string.isEmpty()) {
                    continue;  // 跳过由于开头/导致的空字符串
                }
                //getOrDefault：有则获取，没有则赋默认值
                LinkedList<String> list = map.getOrDefault(level, new LinkedList<>());
                list.add(string);
                map.put(level, list);
                level++;
            }
        }
        int level=scanner.nextInt();
        String word=scanner.next();
        LinkedList<String> list = map.get(level);
        int ans=0;
        for (String e : list) {
            if(word.equals(e)){
                ans++;
            }
        }
        System.out.println(ans);
    }
}
```

# 【双指针】2024D-提取字符串中最长数学表达式

#### 题目描述

提取字符串中的最长合法简单数学表达式，字符串长度最长的，并计算表达式的值。

如果没有，则返回0。 简单数学表达式只能包含以下内容：0-9数字，符号 +-* 

说明： 

1. 所有数字，计算结果都不超过long 
2. 如果有多个长度一样的，请返回第一个表达式的结果 
3. 数学表达式，必须是最长的，合法的 
4. 操作符不能连续出现，如 +--+1 是不合法的

#### 输入

字符串

#### 输出

表达式值

#### 样例输入 复制

```plain
1-2abcd
```

#### 样例输出 复制

```plain
-1
```

#### coding（88.900）

```java
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s=sc.nextLine();
        String maxLenExp = getMaxLenExp(s);
        if (maxLenExp.isEmpty()) {
            System.out.println(0);
        } else {
            System.out.println(calcExpStr(maxLenExp));
        }
    }

    public static String getMaxLenExp(String s) {

        Matcher matcher = Pattern.compile("([+-]?(\\d+[+*-])*\\d+)").matcher(s);

        String maxLenExp = "";

        while (matcher.find()) {
            String exp = matcher.group();
            if (exp.length() > maxLenExp.length()) {
                maxLenExp = exp;
            }
        }

        return maxLenExp;
    }

    public static long calcExpStr(String exp) {
        // 这里在表达式结尾追加"+0"是为了避免后面收尾操作
        exp += "+0";

        // 记录表达式中各块的操作数
        Stack<Integer> stack = new Stack<>();
        // 各块操作数的"值"部分的缓存容器
        StringBuilder numStr = new StringBuilder();

        // 各块操作数的"系数"部分，开头的操作数系数默认为1
        Integer num_coef = 1;

        // 如果合法的表达式可以+或-开头
        char start = exp.charAt(0);

        if (start == '+' || start == '-') {
            // 将exp开头的符号去掉
            exp = exp.substring(1);
        }

        if (start == '-') {
            // 如果表达式开头是负号，则开头操作数的系数为-1
            num_coef = -1;
        }

        // 处理剩余表达式
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c >= '0' && c <= '9') {
                numStr.append(c);
                continue;
            }

            // 如果扫描到的字符c是运算符，那么该运算符打断了前面操作数的扫描，前面操作数 = 系数 * 值
            Integer num = num_coef * Integer.parseInt(numStr.toString());
            stack.add(num);

            // 清空缓存容器，用于下一个操作数的”值“记录
            numStr = new StringBuilder();

            num_coef = switch (c) {
                case '+' ->
                    // 如果运算符是加法，则后一个操作数的系数为1
                        1;
                case '-' ->
                    // 如果运算符是减法，则后一个操作数的系数为-1
                        -1;
                case '*' ->
                    // 如果运算符是乘法，则后一个操作数的系数为栈顶值，比如2*3，其中2可以当作3的系数
                        stack.pop();
                default -> num_coef;
            };
        }

        // 表达式分块后，每一块独立计算，所有块的和就是表达式的结果
        Integer result = 0;
        for (Integer i : stack) {
            result += i;
        }
        return result;
    }
}

```



# 【排序】2024D-开源项目热榜

#### 题目描述

某个开源社区希望将最近热度比较高的开源项目出一个榜单，推荐给社区里面的开发者。 对于每个开源项目，开发者可以进行关注(watch)、收藏(star)、fork、提 issue、提交合并请求(MR)等。 数据库里面统计了每个开源项目关注、收藏、fork、issue、MR 的数量，开源项目的热度根据这 5 个维度的加权求和进行排序。 

H = W_{watch} * \#watch +W_{star} * \#star+W_{fork} * \#fork+W_{issure} * \#issure+W_{mr} * \#mr

\- H：表示热度值； 

\- W_{watch}，W_{star} ，W_{fork}，W_{issure} ，W_{mr}：分别表示 5 个统计维度的权重； 

\- \#watch ，\#star，\#fork，\#issure，\#mr$$：分别表示 5 个统计维度的统计值。 

榜单按照热度值降序排序，对于热度值相等的，按照项目名字转换为全小写字母后的字典序排序。

#### 输入

第一行输入为 N，表示开源项目的个数，0 < N < 100。 

第二行输入为权重值列表，一共 5 个整型值，分别对应关注、收藏、fork、issue、MR 的权重，权重取值 0 < M ≤ 50。 

第三行开始接下来的 N 行为开源项目的统计维度，每一行的格式为： 

name nr_watch nr_star nr_fork nr_issue nr_mr 

其中 name 为开源项目的名字，由英文字母组成，长度 ≤50，其余 5 个整型值分别为该开源项目关注、收藏、fork、issue、MR 的数量，数量取值 0 < nr ≤ 1000。

#### 输出

按照热度降序，输出开源项目的名字，对于热度值相等的，按照项目名字转换为全小写字母后的字典序排序

#### 样例输入 复制

```plain
5
5 6 6 1 2
camila 13 88 46 26 169
grace 64 38 87 23 103
lucas 91 79 98 154 79
leo 29 27 36 43 178
ava 29 27 36 43 178
```

#### 样例输出 复制

```plain
lucas
grace
camila
ava
leo
```

#### 提示

排序热度值计算： camila: 13*5 + 88*6 + 46*6 + 26*1 + 169*2 = 1233 grace: 64*5 + 38*6 + 87*6 + 23*1 + 103*2 = 1299 lucas: 91*5 + 79*6 + 98*6 + 154*1 + 79*2 = 1829 leo: 29*5 + 27*6 + 36*6 + 43*1 + 178*2 = 922 ava: 29*5 + 27*6 + 36*6 + 43*1 + 178*2 = 922 根据热度值降序，对于 leo 和 ava，热度值相等，按照字典序，ava 排在 leo 前面，得到结果。

#### coding

```java

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Main {
    public static class Project{
        public String name;
        public Integer nr_watch ;
        public Integer nr_star ;
        public Integer nr_fork ;
        public Integer nr_issue;
        public Integer nr_mr;
        private Integer hot;
        public static Integer[] weights;

        public Project(String project) {
            String[] strings = project.split(" ");
            this.name = strings[0];
            this.nr_watch = Integer.parseInt(strings[1]);
            this.nr_star = Integer.parseInt(strings[2]);
            this.nr_fork = Integer.parseInt(strings[3]);
            this.nr_issue = Integer.parseInt(strings[4]);
            this.nr_mr = Integer.parseInt(strings[5]);
            this.hot = nr_watch*weights[0]+nr_star*weights[1]+nr_fork*weights[2]+nr_issue*weights[3]+nr_mr*weights[4];
        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Integer count=Integer.parseInt(scanner.nextLine());

        String[] strings = scanner.nextLine().split(" ");
        Integer[] weights = new Integer[5];
        for(int i=0;i<5;i++){
            weights[i]=Integer.parseInt(strings[i]);
        }
        Project.weights=weights;


        PriorityQueue<Project> queue = new PriorityQueue<>(((o1, o2) -> Objects.equals(o1.hot, o2.hot) ?o1.name.compareTo(o2.name):o2.hot-o1.hot));
        while(count-->0){
            String project = scanner.nextLine();
            queue.add(new Project(project));
        }
        while(!queue.isEmpty()){
            System.out.println(queue.poll().name);
        }

    }
}

```

# 【贪心】2024D-虚拟游戏理财

#### 题目描述

在一款虚拟游戏中生活，你必须进行投资以增强在虚拟游戏中的资产以免被淘汰出局。现有一家 Bank，它提供有若干理财产品 m，风险及投资回报不同，你有 N（元）进行投资，能接受的总风险值为 X。 

你要在可接受范围内选择最优的投资方式获得最大回报。 

说明： 

1、在虚拟游戏中，每项投资风险值相加为总风险值； 

2、在虚拟游戏中，最多只能投资 2 个理财产品； 

3、在虚拟游戏中，最小单位为整数，不能拆分为小数； 投资额*回报率=投资回报

#### 输入

第一行：产品数(取值范围[1, 20])，总投资额(整数，取值范围[1, 10000])，可接受的总风险(整数，取值范围[1, 200]) 

第二行：产品投资回报率序列，输入为整数，取值范围[1,60] 

第三行：产品风险值序列，输入为整数，取值范围[1,100] 

第四行：最大投资额度序列，输入为整数，取值范围[1,10000]

#### 输出

每个产品的投资额序列

#### 样例输入 复制

```plain
5 100 10
10 20 30 40 50
3 4 5 6 10
20 30 20 40 30
```

#### 样例输出 复制

```plain
0 30 0 40 0
```

#### 提示

投资第二项 `30` 个单位，第四项 `40` 个单位，总的投资风险为两项相加为 `4+6=10`

1、在虚拟游戏中，每项投资风险值相加为总风险值； 

2、在虚拟游戏中，最多只能投资 2 个理财产品； 

3、在虚拟游戏中，最小单位为整数，不能拆分为小数； 

投资额*回报率=投资回报

#### coding

```java
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();  // 产品数量
        int N = sc.nextInt();  // 总投资额
        int X = sc.nextInt();  // 可接受的总风险值

        int[] returns = new int[m];
        int[] risks = new int[m];
        int[] maxInvests = new int[m];

        for (int i = 0; i < m; i++) {
            returns[i] = sc.nextInt();
        }
        for (int i = 0; i < m; i++) {
            risks[i] = sc.nextInt();
        }
        for (int i = 0; i < m; i++) {
            maxInvests[i] = sc.nextInt();
        }

        int[] bestInvestment = new int[m];
        int maxTotalReturn = 0;

        // 单产品最优选择
        for (int i = 0; i < m; i++) {
            if (risks[i] <= X) {
                int invest = Math.min(maxInvests[i], N);
                int totalReturn = invest * returns[i];
                if (totalReturn > maxTotalReturn) {
                    Arrays.fill(bestInvestment, 0);
                    bestInvestment[i] = invest;
                    maxTotalReturn = totalReturn;
                }
            }
        }

        // 双产品组合选择
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                if (risks[i] + risks[j] <= X) {
                    for (int investI = 0; investI <= Math.min(maxInvests[i], N); investI++) {
                        int investJ = Math.min(maxInvests[j], N - investI);
                        int totalReturn = investI * returns[i] + investJ * returns[j];
                        if (totalReturn > maxTotalReturn) {
                            Arrays.fill(bestInvestment, 0);
                            bestInvestment[i] = investI;
                            bestInvestment[j] = investJ;
                            maxTotalReturn = totalReturn;
                        }
                    }
                }
            }
        }

        // 输出最优投资组合
        for (int invest : bestInvestment) {
            System.out.print(invest + " ");
        }
        System.out.println();
    }
}
```

# 【哈希表】2024D-掌握单词个数

#### 题目描述

有一个字符串数组 words 和一个字符串 chars 。 

假如可以用 chars 中的字母拼写出 words 中的某个“单词”（字符串），那么我们就认为你掌握了这个单词。 

words 的字符仅由 a-z 英文小写字母组成，例如 “abc” chars 由 a-z 英文小写字母和 “?” 组成。其中英文问号 “?” 表示万能字符，能够在拼写时当做任意一个英文字母。

例如：“?” 可以当做 “a” 等字母。 

注意：每次拼写时，chars 中的每个字母和万能字符都只能使用一次。 

输出词汇表 words 中你掌握的所有单词的个数。没有掌握任何单词，则输出 0。

#### 输入

第 1 行输入数组 words 的个数，记为 N。 

从第 2 行开始到第 N+1 行一次输入数组 words 的每个字符串元素。 

第 N+2 行输入字符串 chars。

#### 输出

输出一个整数，表示词汇表 words 中你掌握的单词个数。

#### 样例输入 复制

```plain
4
cat
bt
hat
tree
at?ch
```

#### 样例输出 复制

```plain
3
```

#### 提示

at?ch可以拼写出单词cat、hat和bt，因此掌握的单词是3个。

#### coding

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine().trim()); // 读取单词数量
        String[] words = new String[N];

        for (int i = 0; i < N; i++) {
            words[i] = scanner.nextLine().trim(); // 读取每个单词
        }

        String chars = scanner.nextLine().trim(); // 读取字符集
        System.out.println(countWordsThatCanBeFormedByChars(words, chars));
        scanner.close();
    }

    private static int countWordsThatCanBeFormedByChars(String[] words, String chars) {
        Map<Character, Integer> charMap = new HashMap<>();
        int wildcardCount = 0;

        // 计数 chars 中的每个字符
        for (char ch : chars.toCharArray()) {
            if (ch == '?') {
                wildcardCount++;
            } else {
                charMap.put(ch, charMap.getOrDefault(ch, 0) + 1);
            }
        }

        int count = 0;
        // 验证每个单词是否可以由 chars 中的字符拼写
        for (String word : words) {
            if (canFormWord(word, new HashMap<>(charMap), wildcardCount)) {
                count++;
            }
        }

        return count;
    }

    private static boolean canFormWord(String word, Map<Character, Integer> charMap, int wildcardCount) {
        for (char ch : word.toCharArray()) {
            if (!charMap.containsKey(ch) || charMap.get(ch) == 0) {
                if (wildcardCount > 0) {
                    wildcardCount--; // 使用一个万能字符？
                } else {
                    return false;
                }
            } else {
                charMap.put(ch, charMap.get(ch) - 1); // 使用一个字符
            }
        }
        return true;
    }
}

```

# 【排序】2024D-身高排序

#### 题目描述

小明今年升学到了小学一年级，来到新班级后，发现其他小朋友身高参差不齐，然后就想基于各小朋友和自己的身高差，对他们进行排序，请帮他实现排序。

#### 输入

第一行为正整数H和N，其中0 < H < 200 为小明的身高，0 < N < 50 为新班级其他小朋友个数。 

第二行为 N 个正整数H1 ~ Hn分别是其他小朋友的身高，取值范围0 < Hi < 200，且N个正整数各不相同。

#### 输出

输出排序结果，各正整数以空格分割，和小明身高差绝对值最小的小朋友排在前面，和小明身高差绝对值最大的小朋友排在后面，如果两个小朋友和小明身高差一样，则个子较小的小朋友排在前面。

#### 样例输入 复制

```plain
100 10
95 96 97 98 99 101 102 103 104 105
```

#### 样例输出 复制

```plain
99 101 98 102 97 103 96 104 95 105
```

#### coding

```java
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int self = sc.nextInt();
        int count = sc.nextInt();
        Height[] heights = new Height[count];
        for (int i = 0; i < count; i++) {
            heights[i] = new Height();
            int height = sc.nextInt();
            heights[i].height = height;
            heights[i].heightDifference=Math.abs(height-self);
        }
        int[]result=heightSort(self,heights);
        for (int e:result){
            System.out.print(e+" ");
        }
    }
    public static class Height{
        public int height;
        public int heightDifference;

    }
    private static int[] heightSort(int self, Height[] heights) {
        Arrays.sort(heights,((o1, o2) -> o1.heightDifference== o2.heightDifference? o1.height - o2.height: o1.heightDifference-o2.heightDifference));
        int[] result = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            result[i] = heights[i].height;
        }
        return result;
    }
}
```

# 【DFS/BFS】2024D-精准核酸检测

#### 题目描述

为了达到新冠疫情精准防控的需要，为了避免全员核酸检测带来的浪费，需要精准圈定可能被感染的人群。现在根据传染病流调以及大数据分析，得到了每个人之间在时间、空间上是否存在轨迹的交叉。

现在给定一组确诊人员编号 (X1, X2., X3, ..., n)，在所有人当中，找出哪些人需要进行核酸检测，输出需要进行核酸检测的人数。(注意:确诊病例自身不需要再做核酸检测) 

需要进行核酸检测的人，是病毒传播链条上的所有人员，即有可能通过确诊病例所能传播到的所有人。 

例如：A是确诊病例，A和B有接触、B和C有接触、C和D有接触、D和E有接触，那么B\C\D\E都是需要进行核酸检测的人。

#### 输入

第一行为总人数`N`

第二行为确诊病例人员编号(确诊病例人员数量`<N`)，用逗号分割

第三行开始，为一个`N*N`的矩阵，表示每个人员之间是否有接触，`0`表示没有接触，`1`表示有接触。

#### 输出

整数：需要做核酸检测的人数

#### 样例输入 复制

```plain
5
1,2
1,1,0,1,0
1,1,0,0,0
0,0,1,0,1
1,0,0,1,0
0,0,1,0,1
```

#### 样例输出 复制

```plain
3
```

#### 提示

编号为1、2号的人员，为确诊病例。 

1号和0号有接触，0号和3号有接触。 

2号和4号有接触。 

所以，需要做核酸检测的人是0号、3号、4号，总计3人需要进行核酸检测

#### coding

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


public class Main {
    private static class UnionFind{
        private int[] parent;
        private int[] size;
        private int[] help;
        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        public int find(int x) {
            int index=0;
            while (x != parent[x]) {
                help[index++] = x;
                x = parent[x];
            }
            for(index--; index>=0; index--) {
                parent[help[index]]=x;
            }
            return x;
        }
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if(rootP!=rootQ){
                int sizeP = size[rootP];
                int sizeQ = size[rootQ];
                if(sizeP>sizeQ){
                    parent[rootQ]=rootP;
                    size[rootP]+=sizeQ;
                }else{
                    parent[rootP]=rootQ;
                    size[rootQ]+=sizeP;
                }
            }
        }
        public boolean isSame(int p, int q) {
            return find(p) == find(q);
        }
        public int size(int x){
            return size[find(x)];
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        int[] infectedPersons = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int[][] contactMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            contactMatrix[i]=Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        }
        int num=calculateTestedPeople(infectedPersons,contactMatrix);
        System.out.println(num);
    }

    private static int calculateTestedPeople(int[] infectedPersons, int[][] contactMatrix) {
        int testedPeople = 0;
        int n=contactMatrix.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if(contactMatrix[i][j]==1){
                    uf.union(i, j);
                }
            }
        }
        HashSet<Integer> set = new HashSet<>();
        for(int infectedPerson : infectedPersons){
            int people = uf.find(infectedPerson);
            if(!set.contains(people)){
                set.add(people);
                testedPeople+=uf.size(people);
            }
        }
        return testedPeople-infectedPersons.length;
    }
}

```

# 【排序】2024D-身高体重排序

#### 题目描述

某学校举行运动会,学生们按编号(1、2、3.....n) 进行标识

现需要按照身高由低到高排列

对身高相同的人，按体重由轻到重排列

对于身高体重都相同的人，维持原有的编号顺序关系。

请输出排列后的学生编号

#### 输入

某学校举行运动会,学生们按编号(1、2、3.....n) 进行标识, 现需要按照身高由低到高排列，对身高相同的人，按体重由轻到重排列，对于身高体重都相同的人，维持原有的编号顺序关系。 请输出排列后的学生编号

#### 输出

排列结果，每个数值都是原始序列中的学生编号，编号从 1 开始

#### 样例输入 复制

```plain
4
100 100 120 130
40 30 60 50
```

#### 样例输出 复制

```plain
2134
```

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static class Student{
        public int id;
        public int height;
        public int weight;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        Student[] students = new Student[count];
        for (int i = 0; i < count; i++) {
            students[i]= new Student();
            students[i].id=i+1;
            students[i].height=scanner.nextInt();
        }
        for (int i = 0; i < count; i++) {
            students[i].weight=scanner.nextInt();
        }
        Arrays.sort(students,((o1, o2) -> o1.height==o2.height?(o1.weight==o2.weight?o1.id-o2.id:o1.weight-o2.weight):o1.height-o2.height));
        for (int i = 0; i < count; i++) {
            System.out.print(students[i].id);
        }
    }
}
```

# 【贪心】2024D-贪心歌手

#### 题目描述

歌手准备从 A 城去 B 城参加演出 

1. 按照合同，他必须在 T 天内赶到。 
2. 歌手途径 N 座城市。 
3. 歌手不能往回走。 
4. 每两座城市之间需要的天数都可以提前获知。 
5. 歌手在每座城市都可以在路边卖唱赚钱。经过调研，歌手提前获知了每座城市卖唱的收入预期。如果在一座城市第一天卖唱可以赚 M，后续每天的收入会减少 D (第二天赚的钱是 M-D，第三天是 M-2D…)。如果收入减到 0 就不会再少了。 
6. 歌手到达后的第二天才能开始卖唱。如果今天卖过唱，第二天才能出发。 问贪心的歌手最多可以赚多少钱?

#### 输入

第一行两个数字 T 和 N，中间用空格隔开，T 代表总天数； 

N 代表路上经过 N 座城市; 

0 < T < 1000，0 < N < 100 

第二行 N+1 个数字，中间用空格隔开，代表每两座城市之间耗费的时间，其总和<=T。 

接下来 N 行，每行两个数字 M 和 D，中间用空格隔开。代表每个城市的收入预期。 

0 < M < 1000，0 < D < 100

#### 输出

一个数字。代表歌手最多可以赚多少钱。以回车结束

#### 样例输入 复制

```plain
10 3
1 1 2 3
120 20
90 10
100 20
```

#### 样例输出 复制

```plain
320
```

#### coding

```java
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        int N = scanner.nextInt();
        int[] days = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            days[i] = scanner.nextInt();
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0])); // Max heap
        for (int i = 0; i < N; i++) {
            int M = scanner.nextInt();
            int D = scanner.nextInt();
            heap.offer(new int[]{M, D});
        }
        int X = T - sum(days);
        int ans = 0;
        for (int i = 0; i < X; i++) {
            if (heap.isEmpty()) {
                break;
            }
            int[] city = heap.poll();
            int M = city[0];
            int D = city[1];
            ans += M;
            M -= D;
            if (M > 0) {
                heap.offer(new int[]{M, D});
            }
        }
        System.out.println(ans);
    }

    private static int sum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }
}

```

# 【系统设计】2024D-简易内存池

#### 题目描述

请实现一个简易内存池,根据请求命令完成内存分配和释放。 

内存池支持两种操作命令，REQUEST和RELEASE，其格式为: 



REQUEST=请求的内存大小 

表示请求分配指定大小内存，如果分配成功，返回分配到的内存首地址；如果内存不足，或指定的大小为0，则输出error 



RELEASE=释放的内存首地址 

表示释放掉之前分配的内存，释放成功无需输出，如果释放不存在的首地址则输出error。

#### 输入

首行为整数N，表示操作命令的个数，取值范围 0<N<=100

接下来的`N`行，每行将给出一个操作命令，操作命令和参数之间用`"="`分割。

#### 输出

见题面输出要求

#### 样例输入 复制

```plain
5
REQUEST=10
REQUEST=20
RELEASE=20
RELEASE=10
REQUEST=10
```

#### 样例输出 复制

```plain
0
10
error
10
```

#### 提示

注意: 

1. 内存池总大小为100字节。 
2. 内存池地址分配必须是连续内存，并优先从低地址分配。 
3. 内存释放后可被再次分配，已释放的内存在空闲时不能被二次释放。 
4. 不会释放已申请的内存块的中间地址。 
5. 释放操作只是针对首地址所对应的单个内存块进行操作，不会影响其它内存块。

#### coding

```java
import java.util.HashMap;
import java.util.Scanner;


public class Main {
    public static class MemoryPool {
        private final int[] memory; // 模拟内存的数组
        private final HashMap<Integer, Integer> allocations; // 存储内存分配，key为起始地址，value为块大小
        private static final int MEMORY_SIZE = 100; // 内存池大小为100字节

        public MemoryPool() {
            this.memory = new int[MEMORY_SIZE];
            this.allocations = new HashMap<>();
        }

        public String request(int size) {
            if (size <= 0 || size > MEMORY_SIZE) {
                return "error";
            }

            int start = findSpace(size);
            if (start == -1) {
                return "error";
            }

            // 标记内存为已分配
            for (int i = start; i < start + size; i++) {
                memory[i] = 1;
            }
            allocations.put(start, size);
            return Integer.toString(start);
        }

        public String release(int startAddress) {
            Integer size = allocations.get(startAddress);
            if (size == null) {
                return "error";
            }

            // 清除内存分配
            for (int i = startAddress; i < startAddress + size; i++) {
                memory[i] = 0;
            }
            allocations.remove(startAddress);
            return "";
        }

        private int findSpace(int size) {
            int currentFree = 0;

            for (int i = 0; i < MEMORY_SIZE; i++) {
                if (memory[i] == 0) {
                    if (++currentFree == size) {
                        return i - size + 1;
                    }
                } else {
                    currentFree = 0;
                }
            }

            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine();

        MemoryPool pool = new MemoryPool();
        for (int i = 0; i < N; i++) {
            String command = scanner.nextLine();
            String[] parts = command.split("=");
            if (parts[0].equals("REQUEST")) {
                int size = Integer.parseInt(parts[1]);
                System.out.println(pool.request(size));
            } else if (parts[0].equals("RELEASE")) {
                int address = Integer.parseInt(parts[1]);
                String result = pool.release(address);
                if (!result.isEmpty()) {
                    System.out.println(result);
                }
            }
        }
        scanner.close();
    }
}
```

# 【不定滑窗】2024D-最大可购买的宝石数量

#### 题目描述

橱窗里有一排宝石，不同的宝石对应不同的价格，宝石的价格标记为`gems[i],0<=i<n`，`n = gems.length`。宝石可同时出售`0`个或多个，如果同时出售多个，则要求出售的宝石编号连续；

例如客户最大购买宝石个数为`m`，购买的宝石编号必须为`gems[i],gems[i+1]...gems[i+m-1](0<=i<n,m<=n)`。假设你当前拥有总面值为`value`的钱，请问最多能购买到多少个宝石。如无法购买宝石，则返回`0`。



#### 输入

第一行输入n，参数类型为 int，取值范围：[0,10^6]，表示橱窗中宝石的总数量。 

之后n行分别表示从第0个到第n-1个宝石的价格，即gems[0]到gems[n-1]的价格，类型为int，取值范围：(0,1000]。 

之后一行输入v，类型为int，取值范围：[0,10^9]表示你拥有的钱。

#### 输出

输出int类型的返回值，表示最大可购买的宝石数量。

#### 样例输入 复制

```plain
7
8
4
6
3
1
6
7
10
```

#### 样例输出 复制

```plain
3
```

#### coding

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count=sc.nextInt();
        int[] gems = new int[count];
        for (int i = 0; i < count; i++) {
            gems[i] = sc.nextInt();
        }
        int money=sc.nextInt();
        int num=numberOfGems(gems,money);
        System.out.println(num);
    }

    private static int numberOfGems(int[] gems, int money) {
        int cost=0;
        int num=0;
        int maxNum=0;
        int L=0;
        int R=0;
        while (R < gems.length) {
            if(cost+gems[R]<=money){
                cost+=gems[R];
                num++;
                R++;
            }else {
                maxNum=Math.max(maxNum,num);
                cost-=gems[L];
                num--;
                L++;
            }

        }
        return maxNum;
    }
}
```

# 【回溯】2024D-表演赛游戏分组

#### 题目描述

部门准备举办一场王者荣耀表演赛，有 10 名游戏爱好者参与，分为两队，每队 5 人。 

每位参与者都有一个评分，代表着他的游戏水平。 

为了表演赛尽可能精彩，我们需要把 10 名参赛者分为实力尽量相近的两队。

一队的实力可以表示为这一队 5 名队员的评分总和。 

现在给你 10 名参与者的游戏水平评分，请你根据上述要求分队最后输出这两组的实力差绝对值。 

例: 10 名参赛者的评分分别为 5 1 8 3 4 6 7 10 9 2，分组为 (1 3 5 8 10) (2 4 6 7 9)，两组实力差最小，差值为 1。

有多种分法，但实力差的绝对值最小为 1。

#### 输入

10 个整数，表示 10 名参与者的游戏水平评分。范围在[1,10000]之间

#### 输出

1 个整数，表示分组后两组实力差绝对值的最小值。

#### 样例输入 复制

```plain
1 2 3 4 5 6 7 8 9 10
```

#### 样例输出 复制

```plain
1
```

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static int totalScore=0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] scores = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int abs=assignTeams(scores);
        System.out.println(abs);
    }
    private static int assignTeams(int[] scores) {
        for(int score:scores){
            totalScore+=score;
        }
        return backtracking(scores,0,0,0,0);
    }

    private static int backtracking(int[] scores, int i, int teamACount, int teamBCount, int teamASum) {
        if(i==scores.length) {
            return Math.abs(2*teamASum-totalScore);
        }
        int abs1=Integer.MAX_VALUE;
        int abs2=Integer.MAX_VALUE;
        if(teamACount<5) {abs1=backtracking(scores,i+1,teamACount+1,teamBCount,teamASum+scores[i]);}
        if(teamBCount<5) {abs2=backtracking(scores,i+1,teamACount,teamBCount+1,teamASum);}
        return Math.min(abs1,abs2);
    }
}
```

# 【贪心】2024D-任务处理

#### 题目描述

在某个项目中有多个任务(用 tasks 数组表示)需要您进行处理，其中 tasks[i] = [si, ei]，你可以在 si<= day<=ei中的任意一天处理该任务。请返回你可以处理的最大任务数。 

注：一天可以完成一个任务的处理。

#### 输入

第一行为任务数量n，1 <= n <= 100000。

后面n行表示各个任务的开始时间和终止时间，用si和ei表示，1 <= si <= ei <= 100000

#### 输出

输出为一个整数，表示可以处理的最大任务数。

#### 样例输入 复制

```plain
3
1 1
1 2
1 3
```

#### 样例输出 复制

```plain
3
```

#### coding

```java
import java.util.*;
public class Main {
    public static class Task{
        int start;
        int end;
        boolean flag;
        public Task(int start, int end) {
            this.start = start;
            this.end = end;
            flag = false;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Task[] tasks = new Task[N];
        for (int i = 0; i < N; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            tasks[i] = new Task(start, end);
        }
        int num=taskProcess(tasks);
        System.out.println(num);
    }

    private static int taskProcess(Task[] tasks) {
        int num=0;
        int timeStrap=0;
        Arrays.sort(tasks, (Task t1, Task t2) -> t1.end - t2.end);
        while (timeStrap<=tasks[tasks.length-1].end) {
            for(Task task:tasks) {
                if(task.start <= timeStrap&&task.end >= timeStrap&&!task.flag){
                    num++;
                    task.flag=true;
                    break;
                }
            }
            timeStrap++;
        }
        return num;
    }
}
```

优化：

```java
import java.util.*;

public class Main {
    public static class Task {
        int start;
        int end;
        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            tasks.add(new Task(sc.nextInt(), sc.nextInt()));
        }
        int num = taskProcess(tasks);
        System.out.println(num);
    }

    private static int taskProcess(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(t -> t.start));
        PriorityQueue<Task> availableTasks = new PriorityQueue<>((o1, o2) -> o1.end== o2.end?o1.start-o2.start:o1.end-o2.end);

        int currentTime = 0, completedTasks = 0;
        int i=0;
        while (i < tasks.size()) {
            currentTime=tasks.get(i).start;
            while (i < tasks.size() && tasks.get(i).start <= currentTime) {
                availableTasks.add(tasks.get(i));
                i++;
            }

            while (!availableTasks.isEmpty()) {
                Task task = availableTasks.poll();
                if (task.end>=currentTime) {
                    completedTasks++;
                    currentTime++;
                    while (i < tasks.size() && tasks.get(i).start <= currentTime) {
                        availableTasks.add(tasks.get(i));
                        i++;
                    }
                }
            }
        }
        return completedTasks;
    }
}
```



# 【哈希表】2024D-查找众数及中位数

#### 题目描述

1. 众数是指一组数据中出现次数最多的那个数，众数可以是多个 
2. 中位数是指把一组数据从小到大排列，最中间的那个数，如果这组数据的个数是奇数，那最中间那个就是中位数，如果这组数据的个数为偶数，那就是中间的两个数之和除以2，所以得的结果就是中位数 
3. 查找整型数组中元素的众数并组成一个新的数组，求新数组的中位数

#### 输入

输入一个一维整型数组，数组大小取值范围0 < N < 1000，数组中每个元素取值范围 0 < E < 1000

#### 输出

输出众数组成的新数组的中位数

#### 样例输入 复制

```plain
2 1 5 4 3 3 9 2 7 4 6 2 15 4 2 4
```

#### 样例输出 复制

```plain
3
```

#### coding

```java
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] strings = sc.nextLine().split(" ");
        int[] array = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
        ArrayList<Integer> modes=getTheMode(array);
        Integer[] newArray = modes.toArray(new Integer[0]);
        int median=getTheMedian(newArray);
        System.out.println(median);
    }

    private static int getTheMedian(Integer[] newArray) {
        Arrays.sort(newArray);
        if(newArray.length%2==0){
            return (newArray[newArray.length/2]+newArray[newArray.length/2-1])/2;
        }else{
            return newArray[newArray.length/2];
        }
    }

    private static ArrayList<Integer> getTheMode(int[] array) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int e:array){
            map.put(e,map.getOrDefault(e,0)+1);
        }
        ArrayList<Integer> modes = new ArrayList<>();
        int maxNum=0;
        for(Map.Entry<Integer,Integer> e:map.entrySet()){
            if(e.getValue()>maxNum){
                maxNum=e.getValue();
            }
        }
        for(Map.Entry<Integer,Integer> e:map.entrySet()){
            if(e.getValue()==maxNum){
                modes.add(e.getKey());
            }
        }
        return modes;
    }
}

```

# 【贪心】2024D-小朋友来自多少小区

#### 题目描述

幼儿园组织活动，老师布置了一个任务: 每个小朋友去了解与自己同一个小区的小朋友还有几个。我们将这些数量汇总到数组garden中，请根据这些小朋友给出的信息，计算班级里至少有几个小朋友？

#### 输入

garden = [2, 2, 3] 

说明: garden数组长度最大为999。

每个小区的小朋友数量最多1000人，也就是garden的范围为[0,999]

#### 输出

一个数字

#### 样例输入 复制

```plain
2 2 3
```

#### 样例输出 复制

```plain
7
```

#### 提示

第一个小朋友反馈有两个小朋友和自己同一小区，即此小区有3个小朋友 

第二个小朋友反馈有两个小朋友和自己同一小区，即此小区有3个小朋友。 

这两个小朋友，可能是同一小区的，且此小区的小朋友只有3个人。 

第三个小朋友反馈还有3个小朋友与自己同一小区，则这些小朋友只能是另外一个小区的。

这个小区有4个小朋友。 

班级里至少有3+4 = 7个小朋友。

#### coding

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //用于记录每个整数（小区标识）的出现次数
        Map<Integer, Integer> map = new HashMap<>();
        while (scanner.hasNextInt()) {
            int t = scanner.nextInt();
            map.put(t, map.getOrDefault(t, 0) + 1);
        }
        int number = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer k = entry.getKey();
            Integer v = entry.getValue();
            //int community=v/(k+1);向上取整
            int community=(v+k)/(k+1);
            number+=community*(k+1);
        }
        System.out.println(number);
    }
}
```

# 【二分查找】2024D-部门人力分配

#### 题目描述

部门在进行需求开发时需要进行人力安排。当前部门需要完成 N 个需求，需求用 requirements[i] 表示，requirements[i] 表示第 i 个需求的工作量大小，单位：人月。这部分需求需要在 M 个月内完成开发，进行人力安排后每个月的人力是固定的。 

目前要求每个月最多有 2 个需求开发，并且每个月需要完成的需求不能超过部门人力。请帮部门评估在满足需求开发进度的情况下，每个月需要的最小人力是多少

#### 输入

输入第一行为 M ，第二行为 requirements 。 

M 表示需要开发时间要求，requirements 表示每个需求工作量大小 N 为 requirements 长度，

1 ≤ N / 2 ≤ M ≤ N ≤ 10000，1 ≤ requirements[i]≤ 10^9

#### 输出

对于每一组测试数据，输出部门需要人力需求，行末无多余的空格。

#### 样例输入 复制

```plain
3
3 5 3 4
```

#### 样例输出 复制

```plain
6
```

#### 提示

输入数据两行，第一行输入数据 3 表示开发时间要求，第二行输入数据表示需求工作量大小，输出数据一行，表示部门人力需求。 

当选择人力为6时，2个需求量为3的工作可以在1个月里完成，其他2个工作各需要1个月完成。可以在3个月内完成所有需求。 

当选择人力为5时，4个工作各需要1个月完成，一共需要4个月才能完成所有需求。 

因此6是部门最小的人力需求。

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        sc.nextLine();
        int[] requirements = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int minManpower=getMinManpower(requirements,M);
        System.out.println(minManpower);
    }

    private static int getMinManpower(int[] requirements,int M) {
        int low= Arrays.stream(requirements).max().getAsInt();
        int high = Arrays.stream(requirements).sum();
        Arrays.sort(requirements);
        while(low<high){
            int mid = low+((high-low)>>1);
            if(isFeasible(mid,M,requirements)){
                high = mid;
            }else {
                low = mid+1;
            }
        }
        return low;
    }

    private static boolean isFeasible(int manpower, int M, int[] requirements) {
        int month=0;
        int l=0;
        int r=requirements.length-1;
        while(l<=r){
            if(requirements[l]+requirements[r]<=manpower){
                l++;
            }
            r--;
            month++;
        }
        return month<=M;
    }
}
```

# 【贪心】2024D-座位调整

#### 题目描述

​        疫情期间课堂的座位进行了特殊的调整，不能出现两个同学紧挨着，必须隔至少一个空位。 

​        给你一个整数数组 desk 表示当前座位的占座情况，由若干 0 和 1 组成，其中 0 表示没有占位，1 表示占位。

​        在不改变原有座位秩序情况下，还能安排坐几个人？

#### 输入

​		第一行是个子数组表示作为占座情况，由若干 `0` 和 `1` 组成，其中 `0` 表示没有占位，`1` 表示占位 



#### 输出

​		输出数值表示还能坐几个人 



#### 样例输入 复制

```plain
1,0,0,0,1
```

#### 样例输出 复制

```plain
1
```

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] desk = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int count = 0;
        int n = desk.length;
        int i = 0;
        // 特殊情况：整个数组都是0
        boolean allZero = true;
        for (int seat : desk) {
            if (seat != 0) {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            System.out.println((n + 1) / 2);
            return;
        }

        // 一般情况处理
        while (i < n) {
            if (desk[i] == 0) {
                int start = i;
                while (i < n && desk[i] == 0) {
                    i++;
                }
                int end = i;

                // 计算这段空位中可以放置的人数
                if (start == 0 || end == n) {
                    // 如果这段空位到达了数组的开头或结尾
                    count += (end - start) / 2;
                } else {
                    // 两端都是1的情况，两边都被占据
                    count += (end - start - 1) / 2;
                }
            } else {
                i++;
            }
        }
        System.out.println(count);
    }
}
```

# 【DFS】2024D-生成哈夫曼树

#### 题目描述

给定长度为n的无序的数字数组，每个数字代表二叉树的叶子节点的权值，数字数组的值均大于等于1。 

请完成一个函数，根据输入的数字数组,生成哈夫曼树，并将哈夫曼树按照中序遍历输出。 

为了保证输出的二又树中序遍历结果统一，增加以下限制：二叉树节点中，左节点权值小于等于右节点权值，根节点权值为左右节点权值之和。当左右节点权值相同时，左子树高度高度小于等于右子树 

注意：所有用例保证有效，并能生成哈夫曼树。 

提醒：哈夫曼树又称最优二叉树，是一种带权路径长度最短的二又树。所谓树的带权路径长度，就是树中所有的叶结点的权值乘上其到根结点的路径长度（若根结点为0层，叶结点到根结点的路径长度为叶结点的层数）。

![img](data:image/png;base64,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Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+/aacEQEEEEAAAQQQQAABRQSo+4osBMNAAAEEEEAAAQQQQMB+Aeq+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)

该树的最短带权路径长度为`100*0+40*1+30*2+15*3+5*4+10*4 = 205`

#### 输入

第一行输入为数组长度，记为N，1<=N<=1000 

第二行输入无序数值数组，以空格分割，数值均大于等于1，小于100000

#### 输出

输出一个哈夫曼树的中序遍历的数组，数值间以空格分割

#### 样例输入 复制

```plain
5
5 15 40 30 10
```

#### 样例输出 复制

```plain
40 100 30 60 15 30 5 15 10
```

#### coding

```java
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringJoiner;

public class Main {
    public static class Node{
        public int weight;
        public Node left;
        public Node right;
        public int height;
        public Node(int weight,int height,Node left,Node right) {
            this.weight = weight;
            this.height = height;
            this.left = left;
            this.right = right;
        }
    }
    public static class HuffmanTree{
        public static Node generate(int[] leafNodeWeights){
            PriorityQueue<Node> pq=new PriorityQueue<>((o1, o2) -> o1.weight==o2.weight?o1.height-o2.height:o1.weight-o2.weight);
            for(int weight:leafNodeWeights){
                pq.offer(new Node(weight,1,null,null));
            }
            while(pq.size()>=2){
                Node left=pq.poll();
                Node right=pq.poll();
                Node newNode=new Node(left.weight+right.weight,Math.max(left.height,right.height)+1,left,right);
                pq.offer(newNode);
            }
            return pq.poll();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] leafNodeWeights = new int[n];
        for (int i = 0; i < n; i++) {
            leafNodeWeights[i] = sc.nextInt();
        }
        Node root = HuffmanTree.generate(leafNodeWeights);
        inorderTraversal(root);
    }
    public static void inorderTraversal(Node root){
        if(root==null) return;
        inorderTraversal(root.left);
        System.out.print(root.weight + " ");
        inorderTraversal(root.right);
    }
}

```

# 【贪心】2024D-分割均衡字符串

#### 题目描述

均衡串定义: 字符串只包含两种字符，且两种字符的个数相同。 

给定一个均衡字符串，请给出可分割成新的均衡子串的最大个数。 

约定字符串中只包含大写的X和Y两种字符。

#### 输入

均衡串: XXYYXY 

字符串的长度[2,10000]。

给定的字符串均为均衡串。

#### 输出

输出一个数字，表述可分割成新的均衡子串的最大个数。 

如上述例子可分割为两个子串，XXYY和XY，输出答案为2。

#### 样例输入 复制

```plain
XXYYXY
```

#### 样例输出 复制

```plain
2
```

#### 提示

分割后的子串，是原字符串的连续子串。

```java
import java.util.HashMap;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] charArray = sc.nextLine().toCharArray();
        int countX=0;
        int countY=0;
        int count=0;
        for (char c : charArray) {
            if(c=='X'){
                countX++;
            }else {
                countY++;
            }
            if(countX==countY){
                count++;
                countX=0;
                countY=0;
            }
        }
        System.out.println(count);
    }
}
```

# 【DP】2024D-分披萨

#### 题目描述

“吃货”和“馋嘴”两人到披萨店点了一份铁盘（圆形）披萨，并嘱咐店员将披萨按放射状切成大小相同的偶数扇形小块。但是粗心服务员将技萨切成了每块大小都完全不同奇数块，且肉眼能分辨出大小。 

由于两人都想吃到最多的披萨，他们商量了一个他们认为公平的分法：从“吃货”开始，轮流取披萨。

除了第一块披萨可以任意选取以外，其他都必须从缺口开始选。他俩选披萨的思路不同。 

“馋嘴”每次都会选最大块的披萨，而且“吃货"知道“馋嘴”的想法。 

已知披萨小块的数量以及每块的大小，求“吃货”能分得的最大的披萨大小的总和。

#### 输入

第1行为一个正整数奇数N，表示披萨小块数量。3 <= N <= 500。 

接下来的第2行到第N+1行(共N行)，每行为一个正整数，表示第ì块披萨的大小。1 <= i <= N。 

披萨小块从某一块开始，按照一个方向依次顺序编号为1~N。每块披萨的大小范围为[1,21474836471]。

#### 输出

“吃货”能分得的最大的披萨大小的总和。

#### 样例输入 复制

```plain
5
8
2
10
5
7
```

#### 样例输出 复制

```plain
19
```

#### coding

```java
import java.util.Scanner;

public class Main {
    public static int[][] dpA;
    public static int[][] dpB;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();  // 披萨块数，奇数个
        int[] slices = new int[N];
        dpA=new int[N][N];
        dpB=new int[N][N];
        for (int i = 0; i < N; i++) {
            slices[i] = scanner.nextInt();
        }

        int maxPizza = 0;
        // 从每个位置开始尝试，模拟环状结构
        for (int i = 0; i < N; i++) {
            maxPizza = Math.max(maxPizza, choose(slices,  (i-1+N)%N,(i+1)%N,false)+slices[i]);
        }
        System.out.println(maxPizza);
        scanner.close();
    }

    //上一个选择了index，这轮选什么
    /*8 2 10 5 7*/
    private static int choose(int[] slices,int prev,int next,boolean isChiHuoTurn) {
        int n=slices.length;
        if(prev == next) {return slices[prev];}
        if(isChiHuoTurn) {
            if(dpA[prev][next]!=0){return dpA[prev][next];}
            dpA[prev][next]=Math.max(choose(slices,(prev-1+n)%n,next,false)+slices[prev],choose(slices,prev,(next+1)%n,false)+slices[next]);
            return dpA[prev][next];
        }else {
            if(dpB[prev][next]!=0){return dpB[prev][next];}
            if(slices[prev]<slices[next]) {
                dpB[prev][next]=choose(slices,prev,(next+1)%n,true);
                return dpB[prev][next];
            }else {
                dpB[prev][next]=choose(slices,(prev-1+n)%n,next,true);
                return dpB[prev][next];
            }
        }
    }
}
```

# 【模拟】2024D-整数对最小和

#### 题目描述

给定两个整数数组array1、array2，数组元素按升序排列。假设从array1、array2中分别取出一个元素可构成一对元素，现在需要取出k对元素，并对取出的所有元素求和计算和的最小值。 

注意：两对元素如果对应于array1、array2中的两个下标均相同，则视为同一对元素。

#### 输入

输入两行数组array1、array2，每行首个数字为数组大小size(0 < size <= 100) 

0 < array1[i] <= 1000 

0 < array2[i] <= 1000 

接下来一行为正整数k 0 < k <= array1.size()*array2.size()

#### 输出

满足要求的最小和

#### 样例输入 复制

```plain
3 1 1 2
3 1 2 3
2
```

#### 样例输出 复制

```plain
4
```

#### coding

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n=sc.nextInt();
        int[] array1 = new int[n];
        for (int i = 0; i < n; i++) {
            array1[i] = sc.nextInt();
        }
        int m = sc.nextInt();
        int[] array2 = new int[m];
        for (int i = 0; i < m; i++) {
            array2[i] = sc.nextInt();
        }
        int k = sc.nextInt();
        int min = kSmallestPairs(array1, array2, k);
        System.out.println(min);
    }

    private static int kSmallestPairs(int[] array1, int[] array2, int k) {
        if (array1.length == 0 || array2.length == 0) return 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> a-b);
        int sum=0;
        for (int value1 : array1) {
            for (int value2 : array2) {
                heap.add(value1 + value2);
            }
        }
        while (k-->0&&!heap.isEmpty()) {
            sum+= heap.poll();
        }
        return sum;
    }
}
```

# 【贪心】2024D-变换最小字符串

#### 题目描述

给定一个字符串s，最多只能进行一次变换，返回变换后能得到的最小字符串（按照字典序进行比较） 

变换规则： 交换字符串中任意两个不同位置的字符。

#### 输入

一串小写字母组成的字符串s

#### 输出

按照要求进行变换得到的最小字符串

#### 样例输入 复制

```plain
edcba
```

#### 样例输出 复制

```plain
adcbe
```

#### 提示

s是都是小写字符组成 

1 <= s.length <= 1000

#### coding

暴力枚举

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] charArray = sc.nextLine().toCharArray();
        String minString = String.valueOf(charArray);
        for (int i = 0; i < charArray.length; i++) {
            for (int j = i+1; j < charArray.length; j++) {
                swap(charArray,i,j);
                String newString = String.valueOf(charArray);
                if(newString.compareTo(minString)<0){
                    minString = newString;
                }
                swap(charArray,i,j);
            }
        }
        System.out.println(minString);
    }
    public static void swap(char[]charArray,int i, int j) {
        char tmp=charArray[i];
        charArray[i]=charArray[j];
        charArray[j]=tmp;
    }
}
```

贪心策略：记录i位置之后的最小字符，从头遍历，判断i位置是否为最小字符，如果不是则交换

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] charArray = sc.nextLine().toCharArray();
        String minString=smallestStringAfterSwap(charArray);
        System.out.println(minString);
    }
    public static class MinChar{
        public char character;
        public int index;
        public MinChar(char character, int index) {
            this.character = character;
            this.index = index;
        }
        public MinChar(MinChar minChar) {
            this.character = minChar.character;
            this.index = minChar.index;
        }
    }
    public static String smallestStringAfterSwap(char[] charArray) {
        int n = charArray.length;
        MinChar minChar = new MinChar(charArray[n-1],n-1);
        MinChar[] minChars = new MinChar[n];
        for(int i=n-1;i>=0;i--){
            char cur=charArray[i];
            char min=minChar.character;
            if(cur<min){
                minChar.index=i;
                minChar.character=cur;
            }
            minChars[i]=new MinChar(minChar);
        }
        for(int i=0;i<n;i++){
            char cur=charArray[i];
            minChar=minChars[i];
            if(cur>minChar.character){
                swap(charArray,i, minChar.index);
                break;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (char c : charArray) {
            builder.append(c);
        }
        return builder.toString();
    }
    public static void swap(char[]charArray,int i, int j) {
        char tmp=charArray[i];
        charArray[i]=charArray[j];
        charArray[j]=tmp;
    }
}
```



# 【单调栈】2024D-回转寿司

#### 题目描述

寿司店周年庆，正在举办优惠活动回馈新老客户寿司转盘上总共有n盘寿司，prices[i] 是第 i 盘寿司的价格，如果客户选择了第 i盘寿司，寿司店免费赠送客户距离第 i 盘寿司最近的下一盘寿司 j，前提是prices[j] < prices[i]，如果没有满足条件的 j，则不赠送寿司。 

每个价格的寿司都可无限供应。

#### 输入

输入的每一个数字代表每盘寿司的价格，每盘寿司的价格之间使用空格分隔 

寿司的盘数 n范围为: 1 <= n <= 500

#### 输出

输出享受优惠后的一组数据，每个值表示客户选择第 i 盘寿司时实际得到的寿司的总价格。使用空格进行分隔。

#### 样例输入 复制

```plain
3 15 6 14
```

#### 样例输出 复制

```plain
3 21 9 17
```

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] prices = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n=prices.length;
        int[] realPrices = new int[n];
        for (int i = 0; i < n; i++) {
            realPrices[i] = prices[i];
            for (int j = (i+1)%n; j!=i; j=(j+1)%n) {
                if(prices[i]>prices[j]){
                    realPrices[i] += prices[j];
                    break;
                }
            }
        }
        for(int e:realPrices){
            System.out.print(e+" ");
        }
    }
}
```

# 【前缀和】2024D-查找接口成功率最优时间段

#### 题目描述

服务之间交换的接口成功率作为服务调用关键质量特性，某个时间段内的接口失败率使用一个数组表示，数组中每个元素都是单位时间内失败率数值，数组中的数值为 0~100 的整数，给定一个数值(minAverageLost)表示某个时间段内平均失败率容忍值，即平均失败率小于等于 minAverageLost，找出数组中最长时间段，如果未找到则直接返回 NULL。

#### 输入

输入有两行内容，第一行为minAverageLost，第二行为数组，数组元素通过空格" "分隔，minAverageLost 及数组中元素取值范围为 0~100 的整数，数组元素的个数不会超过 100 个。

#### 输出

找出平均值小于等于 minAverageLost 的最长时间段，输出数组下标对，格式{beginIndex}-{endIndx}(下标从 0 开始)，如果同时存在多个最长时间段，则输出多个下标对且下标对之间使用空格" "拼接，多个下标对按下标从小到大排序。

#### 样例输入 复制

```plain
2
0 0 100 2 2 99 0 2
```

#### 样例输出 复制

```plain
0-1 3-4 6-7
```

#### 提示

A、输入解释：minAverageLost = 2，数组[0, 0, 100, 2, 2, 99, 0, 2] 

B、通过计算小于等于 2 的最长时间段为：数组下标为 0-1 即[0, 0]，数组下标为 3-4 即[2, 2]，数组下标为 6-7 即[0, 2]，这三个部分都满足平均值小于等 2 的要求，因此输出 0-1 3-4 6-7

#### coding

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int minAverageLost = sc.nextInt();
        sc.nextLine();
        int[] lostArray = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        LinkedList<String>timePeriod=getMaxTimePeriod(lostArray,minAverageLost);
        if(timePeriod.isEmpty()){
            System.out.println("NULL");
        }else{
            timePeriod.forEach(time->System.out.print(time+" "));
        }
    }

    private static LinkedList<String> getMaxTimePeriod(int[] lostArray, int minAverageLost) {
        int n=lostArray.length;
        //针对处理区间和,prefixAnd[i+1]表示数组array前i个的前缀和,防止越界
        int[] prefixAnd=new int[n+1];
        prefixAnd[0]=0;
        for(int i=1;i<=n;i++){
            prefixAnd[i]=prefixAnd[i-1]+lostArray[i-1];
        }
        LinkedList<String> timePeriod=new LinkedList<>();
        int maxLength=0;
        for(int start=0;start<n;start++){
            for(int end=start;end<n;end++){
                //sum_(i,j)=prefixAnd[j]-prefixAnd[i-1]
                //但是由于做了处理，首元素越界
                //sum_(i,j)=prefixAnd[j+1]-prefixAnd[i]
                int sum=prefixAnd[end+1]-prefixAnd[start];
                if(sum<=minAverageLost*(end-start+1)){
                    int length=end-start+1;
                    if(length>maxLength){
                        maxLength=length;
                        timePeriod.clear();
                        timePeriod.add(start+"-"+end);
                    }else if(length==maxLength){
                        timePeriod.add(start+"-"+end);
                    }
                }
            }
        }
        return timePeriod;
    }
}
```

# 【二分查找】2024D-项目排期

#### 题目描述

项目组共有N个开发人员，项目经理接到了M个独立的需求，每个需求的工作量不同，且每个需求只能由一个开发人员独立完成，不能多人合作。

假定各个需求之间无任何先后依赖关系，请设计算法帮助项目经理进行工作安排，使整个项目能用最少的时间交付。

#### 输入

第一行输入为M个需求的工作量，单位为天，用逗号隔开。 例如：X1 X2 X3 … Xm 。

表示共有M个需求，每个需求的工作量分别为X1天，X2天…Xm天。 

其中0 < M < 30；0 < Xm < 200 

第二行输入为项目组人员数量N

#### 输出

最快完成所有工作的天数

#### 样例输入 复制

```plain
6 2 7 7 9 3 2 1 3 11 4
2
```

#### 样例输出 复制

```plain
28
```

#### 提示

共有两位员工，其中一位分配需求 6 2 7 7 3 2 1 共需要28天完成，另一位分配需求 9 3 11 4 共需要27天完成，故完成所有工作至少需要28天。

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    
   public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] requirements = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int N = sc.nextInt();
        int minTime=getMinTime(requirements,N);
        System.out.println(minTime);
    }

    private static int getMinTime(int[] requirements, int n) {
        int low= Arrays.stream(requirements).max().getAsInt();
        int high = Arrays.stream(requirements).sum();
        Arrays.sort(requirements);
        while(low<high){
            int mid=low+((high-low)>>1);
            if(isFeasible(requirements,n,mid)){
                high=mid;
            }else{
                low=mid+1;
            }
        }
        return low;
    }

    private static boolean isFeasible(int[] requirements, int n, int day) {
        int[] developers = new int[n];
        for (int i = requirements.length-1; i >=0; i--) {
            int requirement = requirements[i];
            int index = getMinDeveloper(developers);
            developers[index] += requirement;
            if (developers[index] > day) {
                return false;
            }
        }
        return true;
    }

    private static int getMinDeveloper(int[] developers) {
        int min=Integer.MAX_VALUE;
        int index=0;
        for (int i = 0; i < developers.length; i++) {
            if(developers[i]<min){
                min=developers[i];
                index=i;
            }
        }
        return index;
    }
}

```

# 【模拟】2024D-回收银饰

#### 题目描述

有 N 块二手市场收集的银饰，每块银饰的重量都是正整数，收集到的银饰会被熔化用于打造新的饰品。 

每一回合，从中选出三块 最重的 银饰，然后一起熔掉。假设银饰的重量分别为 x 、y 和 z，且 x <= y <= z。

那么熔掉的可能结果如下： 

如果x == y == z，那么三块银饰都会被完全熔掉； 

如果x == y且y != z，会剩余重量为z - y的银块无法被熔掉； 

如果x != y且y == z，会剩余重量为y - x的银块无法被熔掉； 

如果x != y且y != z，会剩余重量为z - y与y - x差值的银块无法被熔掉。 

如果剩余两块，返回较大的重量（若两块重量相同，返回任意一块皆可）；如果只剩下一块，返回该块的重量；如果没有剩下，就返回 0。

#### 输入

输入数据为两行 

第一行为银饰数组长度 n，1 ≤ n ≤ 40， 

第二行为 n 块银饰的重量，重量的取值范围为[1，2000]，重量之间使用空格隔开

#### 输出

如果剩余两块，返回较大的重量（若两块重量相同，返回任意一块皆可）；如果只剩下一块，返回该块的重量；如果没有剩下，就返回 0。

#### 样例输入 复制

```plain
3
3 7 10
```

#### 样例输出 复制

```plain
1
```

#### 来源/分类

```java
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int weight=dissolve(arr);
        System.out.println(weight);
    }

    private static int dissolve(Integer[] weights) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for(Integer weight:weights){
            heap.offer(weight);
        }
        while(heap.size()>=3){
            Integer z= heap.poll();
            Integer y= heap.poll();
            Integer x= heap.poll();
            if(Objects.equals(x, y) && !Objects.equals(z, y)){
                heap.add(z-y);
            }else if(!Objects.equals(x, y) && Objects.equals(z, y)){
                heap.add(y-x);
            }else if(!Objects.equals(x, y) && !Objects.equals(z, y)){
                int difference=Math.abs((z - y)-(y - x));
                //差值为0时，不需要装入
                if(difference!=0){
                    heap.add(difference);
                }
            }
        }
        if(heap.isEmpty()){
            return 0;
        }else if(heap.size()==1){
            return heap.poll();
        } else  {
            int a=heap.poll();
            int b=heap.poll();
            return Math.max(a, b);
        }
    }
}
```

# 【模拟】2024D-字符串分割（二）

#### 题目描述

给定一个非空字符串S，其被N个'-'分隔成N+1的子串，给定正整数K，要求除第一个子串外，其余的子串每K个字符组成新的子串，并用'-'分隔。

对于新组成的每一个子串，如果它含有的小写字母比大写字母多，则将这个子串的所有大写字母转换为小写字母; 反之，如果它含有的大写字母比小写字母多，则将这个子串的所有小写字母转换为大写字母，大小写字母的数量相等时，不做转换。

#### 输入

输入为两行，第一行为参数K，第二行为字符串S.

#### 输出

输出转换后的字符串。

#### 样例输入 复制

```plain
3
12abc-abCABc-4aB@
```

#### 样例输出 复制

```plain
12abc-abc-ABC-4aB-@
```

#### 提示

子串为12abc、abCABc、4aB@，第一个子串保留,后面的子串每3个字符一组为abC、ABc、4aB、@。 

abC中小写字母较多，转换为abc 

ABc中大写字母较多，转换为ABC 

4aB中大小写字母都为1个，不做转换 

@中没有字母 

连起来即12abc-abc-ABC-4aB-@

#### coding

```java
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        sc.nextLine();
        String[] strings = sc.nextLine().split("-");
        String s=stringSplit(strings,k);
        System.out.println(s);
    }

    private static String stringSplit(String[] strings,int k) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < strings.length; i++) {
            builder.append(strings[i]);
        }
        LinkedList<String> list = handleSubstrings(builder.toString(), k);
        if(list.isEmpty()) return strings[0];
        String collect = String.join("-", list);//对于list可以以指定符号链接为String
        String ans = strings[0].concat("-").concat(collect);
        return ans;
    }

    private static  LinkedList<String> handleSubstrings(String string, int k) {
        char[] charArray = string.toCharArray();
        LinkedList<String> ans = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            builder.append(charArray[i]);
            if((i+1)%k==0){
                String s = caseConversion(builder.toString());
                ans.add(s);
                builder=new StringBuilder();
            }
        }
        if(!builder.toString().isEmpty()){
            String s = caseConversion(builder.toString());
            ans.add(s);
        }
        return ans;
    }


    public static String caseConversion(String str){
        int lowerCaseCount = 0;
        int upperCaseCount = 0;
        for (char c : str.toCharArray()) {
            if (Character.isLowerCase(c)) {//Character类，判断字符大小写
                lowerCaseCount++;
            } else if (Character.isUpperCase(c)) {
                upperCaseCount++;
            }
        }
        return lowerCaseCount > upperCaseCount ? str.toLowerCase() :
                upperCaseCount > lowerCaseCount ? str.toUpperCase() : str;
    }
}
```

```
3
4
1 1 1 1
```

# 【模拟】2024D-GPU调度

#### 题目描述

为了充分发挥 GPU 算力，需要尽可能多的将任务交给 GPU 执行，现在有一个任务数组，数组元素表示在这1s内新增的任务个数，且每秒都有新增任务，假设 GPU 最多一次执行n个任务，一次执行耗时1s，在保证 GPU 不空闲的情况下，最少需要多长时间执行完成。

#### 输入

第一个参数为 GPU 最多执行的任务个数 

取值范围1 ~ 10000 

第二个参数为任务数组的长度 

取值范围1 ~ 10000 

第三个参数为任务数组 

数字范围1 ~ 10000

#### 输出

执行完所有任务需要多少秒

#### 样例输入 复制

```plain
3
5
1 2 3 4 5
```

#### 样例输出 复制

```plain
6
```

#### 提示

一次最多执行3个任务 

最少耗时6s

```java
import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int executeTasks = sc.nextInt();
        int N = sc.nextInt();
        int[] tasksPerSecond = new int[N];
        for (int i = 0; i < N; i++) {
            tasksPerSecond[i] = sc.nextInt();
        }
        int time=minTime(executeTasks,tasksPerSecond);
        System.out.println(time);
    }
    private static int minTime(int executeTasks, int[] tasksPerSecond) {
        int totalTime = 0;
        int restTasks = 0;
        for (int tasks : tasksPerSecond) {
            restTasks += tasks;
            if(restTasks > executeTasks){
                restTasks-=executeTasks;
            }else {
                restTasks=0;
            }
            totalTime++;
        }
        totalTime+=(restTasks+executeTasks-1)/executeTasks;//n/m向上取整：（n+m-1）/m
        return totalTime;
    }
}
```

# 【排序】2024D-智能成绩表

#### 题目描述

小明来到某学校当老师，需要将学生按考试总分或单科分数进行排名，你能帮帮他吗？

#### 输入

第1行输入两个整数，学生人数n和科目数量m。0 < n < 100，0 < m < 10 

第2行输入m个科目名称，彼此之间用空格隔开，科目名称只由英文字母构成，单个长度不超过10个字符。

科目的出现顺字和后续输入的学生成绩一一对应。不会出现重复的科目名称。 

第3行开始的行，每行包含一个学生的姓名和该生m个科目的成绩空格隔开)，学生不会重名。 学生姓名只由英文字母构成，长度不超过10个字符。

成绩是0~100的整数，依次对应第2行中输入的科目。 

第n+2行，输入用作排名的科目名称。 若科目不存在，则按总分进行排序。

#### 输出

输出一行，按成绩排序后的学生名字，空格隔开。成绩相同的按照学生姓名字典顺序排序。

#### 样例输入 复制

```plain
3 2
yuwen shuxue
fangfang 95 90
xiaohua 88 95
minmin 100 82
shuxue
```

#### 样例输出 复制

```plain
xiaohua fangfang minmin
```

#### coding

```java
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static int index;
    public static class Student{
        public String name;
        public int[] score;

        public Student(String name, int[] score) {
            this.name = name;
            this.score = score;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        String[] subjects = new String[m];
        for (int i = 0; i < m; i++) {
            subjects[i] = sc.next();
        }
        Student[] students = new Student[n];
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int[] score = new int[m];
            for (int j = 0; j < m; j++) {
                score[j] = sc.nextInt();
            }
            students[i] = new Student(name,score);
        }
        String rankBy="";
        if(sc.hasNext()){
            rankBy=sc.next();
        }

        Student[] rank = gradeRanking(students, rankBy, subjects);
        for (int i = 0; i < n; i++) {
            System.out.print(students[i].name+" ");
        }
    }

    private static Student[]  gradeRanking(Student[] students, String rankBy,String[] subjects) {
        if(rankBy.isEmpty()){
            Arrays.sort(students,new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    int sum1 = Arrays.stream(o1.score).sum();
                    int sum2 = Arrays.stream(o2.score).sum();
                    return sum2==sum1?o1.name.compareTo(o2.name):sum2-sum1;
                }
            });
        }else {
            index=0;
            while (index < subjects.length) {
                if(rankBy.equals(subjects[index])){
                    break;
                }
                index++;
            }
            Arrays.sort(students,new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    int score1=o1.score[index];
                    int score2=o2.score[index];
                    return score2==score1?o1.name.compareTo(o2.name):score2-score1;
                }
            });
        }
        return students;
    }
}
```

# 【模拟】2024D-整数分解

#### 题目描述

一个整数可以由连续的自然数之和来表示。 

给定一个整数，计算该整数有几种连续自然数之和的表达式， 并打印出每一种表达式。

#### 输入

一个目标整数 t，1 <= t <= 1000

#### 输出

1. 该整数的所有表达式和表达式的个数，如果有多种表达式，自然数个数最少的表达式优先输出 
2. 每个表达式中按自然数递增输出 
3. 具体的格式参见样例 
4. 在每个测试数据结束时，输出一行 Result:X 
5. 其中 X 是最终的表达式个数

#### 样例输入 复制

```plain
9
```

#### 样例输出 复制

```plain
9=9
9=4+5
9=2+3+4
Result:3
```

#### coding

```java
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        LinkedList<String> expression=decompose(n);
        for(String exp:expression){
            System.out.println(exp);
        }
        System.out.println("Result:"+expression.size());
    }

    private static LinkedList<String> decompose(int n) {
        LinkedList<String> list = new LinkedList<>();
        for(int i=n;i>=1;i--){
            int cur=i;
            int sum=0;
            StringBuilder builder = new StringBuilder().append(n).append("=");
            while(cur<=n){
                sum+=cur;
                builder.append(cur).append("+");
                if(sum==n){
                    builder.deleteCharAt(builder.length()-1);
                    list.add(builder.toString());
                    break;
                }
                if(sum>n){
                    break;
                }
                cur++;
            }
        }
        return list;
    }
}
```

# 【模拟】2024D-攀登者1

#### 题目描述

攀登者喜欢寻找各种地图，并且尝试攀登到最高的山峰。

地图表示为一维数组，数组的索引代表水平位置，数组的高度代表相对海拔高度。其中数组元素 `0` 代表地面。

例如`[0,1,2,4,3,1,0,0,1,2,3,1,2,1,0]`， 代表如下图所示的地图。地图中有两个山脉位置分别为 `1,2,3,4,5`和`8,9,10,11,12,13`，最高峰高度分别为 `4,3`。最高峰位置分别为`3,10`。

一个山脉可能有多座山峰（高度大于相邻位置的高度，或在地图边界且高度大于相邻的高度）。

```Shell
                 4

              +---+
              |   |
              |   | 3                       3
              |   |
              |   +---+                   +---+
              |       |                   |   |
            2 |       |                 2 |   |     2
              |       |                   |   |
          +---+       |               +---+   |   +---+
          |           |               |       |   |   |
        1 |           | 1           1 |       | 1 |   | 1
          |           |               |       |   |   |
      +---+           +---+       +---+       +---+   +---+
      |                   |       |                       |
    0 |                   | 0   0 |                       | 0
      |                   |       |                       |
  +---+                   +-------+                       +---+

    0   1   2   3   4   5   6   7   8   9  10  11  12  13  14 
```

登山时会消耗登山者的体力(整数)，上山时，消耗相邻高度差两倍的体力，下坡时消耗相邻高度差一倍的体力，平地不消耗体力，登山者体力消耗到零时会有生命危险。

例如，上图所示的山峰：从索引 `0`，走到索引 `1`，高度差为 `1`，需要消耗 `2*1=2` 的体力；从索引 `2` 高度 `2`走到高度 `4` 索引 `3` 需要消耗 `2*2=4` 的体力；从索引 `3` 走到索引 `4` 则消耗 `1*1=1` 的体力。

登山者想知道一张地图中有多少座山峰？

#### 输入

第一行输入一个长度为N的数组，表示地图。

#### 输出

输出一个数字，地图中的山峰数量

#### 样例输入 复制

```plain
0,1,4,3,1,0,0,1,2,3,1,2,1,0
```

#### 样例输出 复制

```plain
3
```

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] heights = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int peakCount=countPeaks(heights);
        System.out.println(peakCount);
    }

    private static int countPeaks(int[] heights) {
        if(heights.length==1 && heights[0]==0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < heights.length; i++) {
            // 判断是否是山峰
            // 对于数组的边界，只需要比较一侧的邻居。
            // 并且如果只有一个height，且不为0，则同时满足i==0&&i == heights.length - 1，count也会++
            if ((i == 0 || heights[i] > heights[i - 1]) && (i == heights.length - 1 || heights[i] > heights[i + 1])) {
                count++;
            }
        }
        return count;
    }
}
```

# 【模拟】2024D-攀登者2（90%）

#### 题目描述

攀登者喜欢寻找各种地图，并且尝试攀登到最高的山峰。

地图表示为一维数组，数组的索引代表水平位置，数组的高度代表相对海拔高度。其中数组元素 `0` 代表地面。

例如`[0,1,2,4,3,1,0,0,1,2,3,1,2,1,0]`， 代表如下图所示的地图。地图中有两个山脉位置分别为 `1,2,3,4,5`和`8,9,10,11,12,13`，最高峰高度分别为 `4,3`。最高峰位置分别为`3,10`。

一个山脉可能有多座山峰（高度大于相邻位置的高度，或在地图边界且高度大于相邻的高度）。

```Shell
                 4

              +---+
              |   |
              |   | 3                       3
              |   |
              |   +---+                   +---+
              |       |                   |   |
            2 |       |                 2 |   |     2
              |       |                   |   |
          +---+       |               +---+   |   +---+
          |           |               |       |   |   |
        1 |           | 1           1 |       | 1 |   | 1
          |           |               |       |   |   |
      +---+           +---+       +---+       +---+   +---+
      |                   |       |                       |
    0 |                   | 0   0 |                       | 0
      |                   |       |                       |
  +---+                   +-------+                       +---+

    0   1   2   3   4   5   6   7   8   9  10  11  12  13  14 
```

登山时会消耗登山者的体力(整数)，上山时，消耗相邻高度差两倍的体力，下坡时消耗相邻高度差一倍的体力，平地不消耗体力，登山者体力消耗到零时会有生命危险。

例如，上图所示的山峰：从索引 `0`，走到索引 `1`，高度差为 `1`，需要消耗 `2*1=2` 的体力；从索引 `2` 高度 `2`走到高度 `4` 索引 `3` 需要消耗 `2*2=4` 的体力；从索引 `3` 走到索引 `4` 则消耗 `1*1=1` 的体力。

攀登者想要评估一张地图内有多少座山峰可以进行攀登，且可以安全返回到地面，且无生命危险。

例如上图中的教组，有`3`个不同的山峰，登上位置在`3`的山可以从位置`0`或者位置`6`开始，从位置`0`登到山顶需要消耗体力`1*2+1*2+2*2=8`，从山顶返回到地面`0`需要消耗体力 `2*1+1*1+1*1=4`的体力，按照登山路线`0->3->0`需要消耗体力 `12`。攀登者至少需要`12`以上的体力(大于`12`)才能安全返回。

#### 输入

第一行输入 一个长度为N的数组，表示地图。 第二行输入最大体力。

#### 输出

输出一个数字，地图中可以攀登到达的山峰数量

#### 样例输入 复制

```plain
0,1,4,3,1,0,0,1,2,3,1,2,1,0
11
```

#### 样例输出 复制

```plain
2
```

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] heights = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int maxStamina = sc.nextInt();
        int climbablePeaks =countClimbablePeaks(heights, maxStamina);
        System.out.println(climbablePeaks);
    }
    public static int countClimbablePeaks(int[] heights, int maxStamina) {
        int count  = 0;
        // 寻找所有的山峰
        for (int i = 0; i < heights.length; i++) {
            if (isPeak(heights, i)) {
                int staminaNeeded = calculateStamina(heights, i);
                if(staminaNeeded==-1) continue;
                if (staminaNeeded <= maxStamina) {
                    count++;
                }
            }
        }
        return count ;
    }

    private static int calculateStamina(int[] heights, int peakIndex) {
        int staminaCostLeft = 0;
        int staminaCostRight = 0;
        boolean canClimbFromLeft = true;
        boolean canClimbFromRight = true;

        // 找到左边最接近的0,并计算从左边上下山的耐力
        int left = peakIndex;
        while (left > 0 ) {
            int heightDiff = Math.abs(heights[left] - heights[left - 1]);
            staminaCostLeft += 3 * heightDiff; // 上山和下山的耐力消耗合并计算
            if(heights[left - 1] == 0){
                break;
            }
            left--;
        }
        // 检查左侧是否有0作为起点
        if (left == 0 && heights[left] != 0) {
            canClimbFromLeft = false; // 如果左侧边界不是0，不能从左侧开始
        }

        // 找到右边最接近的0,并计算从右边的耐力
        int right = peakIndex;
        while (right < heights.length - 1 ) {
            int heightDiff = Math.abs(heights[right] - heights[right + 1]);
            staminaCostRight += 3 * heightDiff; // 上山和下山的耐力消耗合并计算
            if(heights[right + 1] == 0){
                break;
            }
            right++;
        }
        // 检查右侧是否有0作为起点
        if (right == heights.length - 1 && heights[right] != 0) {
            canClimbFromRight = false; // 如果右侧边界不是0，不能从右侧开始
        }

        // 判断最小耐力消耗的路线
        if (canClimbFromLeft && canClimbFromRight) {
            return Math.min(staminaCostLeft, staminaCostRight);
        } else if (canClimbFromLeft) {
            return staminaCostLeft;
        } else if (canClimbFromRight) {
            return staminaCostRight;
        }
        return -1; // 如果两边都不是0，表示这个山峰不可攀登
    }


    private static boolean isPeak(int[] heights, int i) {
        if(heights.length==1&&heights[0]==0) return false;
        return (i==0||heights[i] > heights[i - 1])&&(i==heights.length-1||heights[i] > heights[i+1]);
    }
}
```

# 【模拟】2024D-小明能到达的最大坐标值

#### 题目描述

小明在玩一个游戏，游戏规则如下：在游戏开始前，小明站在坐标轴原点处（坐标值为 0）给定一组指令和一个幸运数，每个指令都是一个整数，小明按照指定的要求前进或者后退指定的步数。 

前进代表朝坐标轴的正方向走，后退代表朝坐标轴的负方向走，幸运数为一个整数，如果某个指令正好和幸运数相等，则小明行进步数加 1 。 

例如： 幸运数为 3 ，指令数组为[2, 3, 0, -5] 

指令为 2 ，表示前进 2 步 

指令为 3 ，正好好和幸运数相等，前进 3+1=4 步 

指令为 0 ，表示原地不动，既不前进，也不后退 

指令为 -5 ，表示后退 5 步。 

请你计算小明在整个游戏过程中，小明所处的最大坐标值。

#### 输入

第一行输入 1 个数字，代表指令的总个数 n(1≤n≤100） 

第二行输入 1 个数字，代表幸运数 m(−100≤m≤100) 

第三行输入 n 个指令，每个指令值的取值范围为： − 100 ≤ 指令值 ≤ 100

#### 输出

输出在整个游戏过程中，小明所处的最大坐标值。异常情况下输出：12345

#### 样例输入 复制

```plain
5
-5
-5 1 6 0 -7
```

#### 样例输出 复制

```plain
1
```

#### coding

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int luckyNumber = sc.nextInt();
        int[] steps = new int[n];
        for (int i = 0; i < n; i++) {
            steps[i] = sc.nextInt();
        }
        int result=getMaxCoordinate(luckyNumber,steps);
        System.out.println(result);
    }

    private static int getMaxCoordinate(int luckyNumber, int[] steps) {
        int max=0;
        int position = 0;
        for (int i = 0; i < steps.length; i++) {
            int step = steps[i];
            if(step==0) continue;
            if (luckyNumber == step) {
                //判断整数符号
                step = Integer.signum(luckyNumber) == -1 ? step-1 : step+1;
            }
            position += step;
            max=Math.max(max,position);
        }
        return max;
    }
}

```

# 【模拟】2024D-素数之积

#### 题目描述

RSA加密算法在网络安全世界中无处不在，它利用了极大些数因数分解的闲难度，数据越大，安全系数越高，给定一个32位整数，请对其进行因数分解，找出是哪两个素数的乘积。

#### 输入

1个正整数num 

0 < num <= 2147483647

#### 输出

如果成功找到，以单个空格分割，从小到大输出两个素数。分解失败，请输出-1 -1

#### 样例输入 复制

```plain
15
```

#### 样例输出 复制

```plain
3 5
```

#### 提示

因数分解后，找到两个素数3和5，使得3*5=15，按从小到大排列后，输出3 5

#### coding

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[]result=product(n);
        System.out.print(result[0]+" "+result[1]);
    }

    private static int[] product(int n) {
        int[] result=new int[2];
        result[0]=-1;
        result[1]=-1;
        //不存在两个因子大于平方根的情况
        for (int i = 2; i <= (int)Math.sqrt(n); i++) {
            if(n%i==0){
                int temp=n/i;
                if(isPrime(i)&&isPrime(temp)){
                    result[0]=i;
                    result[1]=temp;
                    break;
                }
            }
        }
        return result;
    }

    private static boolean isPrime(int n) {
        boolean flag=true;
        for(int i=2;i<= (int)Math.sqrt(n);i++) {
            if(n%i==0) {
                flag=false;
                break;
            }
        }
        return flag;
    }
}
```

# 【模拟】2024D-高效货运

#### 题目描述

老李是货运公司承运人，老李的货车额定载货重量为wt。 

现有两种货物，货物A单件重量为wa，单件运费利润为pa。货物B单件重量为wb，单件运费利润为pb。 

老李每次发车时载货总重量刚好为货车额定载货重量wt，车上必须同时有货物A和货物B，货物A、B 不可切割。 

老李单车次满载运输可获得的最高利润是多少？

#### 输入

输入一行五个正整数。
第一个数字为货物A的单件重量wa，0<wa<10000
第二个数字为货物B的单件重量wb，0<wb<10000
第三个数字为货车的额定载重wt，0<wt<100000
第四个数字为货物A的单件运费利润pa，0<pa<1000
第五个数字为货物B的单件运费利润pb，0<pb<1000

#### 输出

单次满载运输的最高利润

#### 样例输入 复制

```plain
10 8 36 15 7
```

#### 样例输出 复制

```plain
44
```

#### coding

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int wa = sc.nextInt();
        int wb = sc.nextInt();
        int wt = sc.nextInt();
        int pa = sc.nextInt();
        int pb = sc.nextInt();
        int profit=calculateMaxProfit(wa,wb,wt,pa,pb);
        profit=profit==Integer.MAX_VALUE?0:profit;
        System.out.println(profit);
    }
    public static int calculateMaxProfit(int wa, int wb, int wt, int pa, int pb) {
        int maxProfit=Integer.MIN_VALUE;
        for (int a  = 1; a  <= wt/wa; a++) {
            int remainingWeight = wt - a * wa;
            if (remainingWeight % wb == 0) {
                int b = remainingWeight / wb;
                if (b > 0) { // 确保货物B至少有一个
                    int currentProfit = a * pa + b * pb;
                    maxProfit = Math.max(maxProfit, currentProfit);
                }
            }
        }
        return maxProfit;
    }
}
```

# 【模拟】2024D-结队编程

#### 题目描述

某部门计划通过结队编程来进行项目开发，已知该部门有 N 名员工，每个员工有独一无二的职级，每三个员工形成一个小组进行结队编程。 

结队分组规则如下： 从部门中选出序号分别为i、j、k 的 3 名员工，他们的职级分别为 level[i], level[j], level[k] 

结队小组需满足 level[i] < level[j] < level[k] 或者 level[i] > level[j] > level[k] ，其中 0 ⩽ i < j < k < n 请你按上述条件计算可能组合的小组数量。

同一员工可以参加多个小组。

#### 输入

第一行输入：员工总数 n 

第二行输入：按序号依次排列的员工的职级 level，中间用空格隔开 

限制： 

1 ⩽ n ⩽ 6000 

1 ⩽ level[i] ⩽ 10^5 

#### 输出

可能组合的小组数量

#### 样例输入 复制

```plain
4
1 2 3 4
```

#### 样例输出 复制

```plain
4
```

#### 提示

可能结队成的组合 (1,2,3)、(1,2,4)、(1,3,4)、(2,3,4)。

#### coding

```java
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] levels=new int[n];
        for (int i = 0; i < n; i++) {
            levels[i]=sc.nextInt();
        }
        int result=assemble(levels);
        System.out.println(result);
    }
    public static int assemble(int[] levels) {
        int n = levels.length;
        if(n<3){
            return 0;
        }
        int num=0;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                for(int k=j+1;k<n;k++){
                    if(levels[i]>levels[j] && levels[j]>levels[k]||levels[i]<levels[j]&&levels[j]<levels[k]){
                        num++;
                    }
                }
            }
        }
        return num;
    }
}
```

优化：固定一个元素并考虑其左右两侧的元素来构造解

```java
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] levels=new int[n];
        for (int i = 0; i < n; i++) {
            levels[i]=sc.nextInt();
        }
        long count=countTriplets(n,levels);
        System.out.println(count);
    }
    private static long countTriplets(int n, int[] level) {
        long result = 0;
        for (int j = 1; j < n - 1; j++) {
            // Count elements less and greater than level[j] on the left side
            int lessLeft = 0, greaterLeft = 0;
            for (int i = 0; i < j; i++) {
                if (level[i] < level[j]) {
                    lessLeft++;
                }
                if (level[i] > level[j]) {
                    greaterLeft++;
                }
            }

            // Count elements less and greater than level[j] on the right side
            int lessRight = 0, greaterRight = 0;
            for (int k = j + 1; k < n; k++) {
                if (level[k] < level[j]) {
                    lessRight++;
                }
                if (level[k] > level[j]) {
                    greaterRight++;
                }
            }

            // Compute combinations for both increasing and decreasing sequences
            result += (long) lessLeft * greaterRight + (long) greaterLeft * lessRight;
        }

        return result;
    }
}

```

该方法的核心思想是基于**双重计数**来确定围绕一个固定中心元素的有效递增和递减三元组的数量。具体地说，方法侧重于：

1. **固定中间元素**：对于每个可能的中间点 `j`（该点位于数组中间位置，允许其有左右两侧的元素），检查其左侧和右侧元素以形成合法的三元组。
2. **双向计数**：
   - **左侧计数**：对于每个固定的中间元素 `level[j]`，计算在其左侧有多少元素小于它（`lessLeft`）和多少元素大于它（`greaterLeft`）。
   - **右侧计数**：同样地，计算在其右侧有多少元素小于它（`lessRight`）和多少元素大于它（`greaterRight`）。
3. **组合计算**：
   - 对于递增序列（`level[i] < level[j] < level[k]`），利用左侧小于 `j` 的元素数量 (`lessLeft`) 和右侧大于 `j` 的元素数量 (`greaterRight`) 来计算可能的三元组数量。这是因为每个从左侧选择的小于 `level[j]` 的 `level[i]` 都可以与右侧的每个大于 `level[j]` 的 `level[k]` 形成一个递增序列。
   - 对于递减序列（`level[i] > level[j] > level[k]`），使用左侧大于 `j` 的元素数量 (`greaterLeft`) 和右侧小于 `j` 的元素数量 (`lessRight`) 来计算可能的三元组数量。每个从左侧选择的大于 `level[j]` 的 `level[i]` 都可以与右侧的每个小于 `level[j]` 的 `level[k]` 形成一个递减序列。
4. **效率考虑**：通过这种固定中心并双向计数的策略，算法能在 O(n2)O(n^2)O(n2) 时间内完成。每个中心元素只需遍历一次左侧和右侧元素，这比直接的三重循环遍历（时间复杂度为 O(n3)O(n^3)O(n3)）要有效得多。

总结来说，这个方法通过系统地组合左侧和右侧的计数数据，以固定中间元素的方式，优雅地统计了所有符合条件的递增和递减三元组的数量。这种方法充分利用了问题中的单调性约束（递增或递减），从而提高了计算的效率。

# 【模拟】2024D-螺旋数字矩阵

#### 题目描述

疫情期间，小明隔离在家，百无聊赖，在纸上写数字玩。 

他发明了一种写法：给出数字个数n和行数m (0 < n < 999，0 < m < 999)，从左上角的1开始，按照顺时针螺旋向内写方式，依次写出2, 3, ..., n，最终形成一个m行矩阵。 

小明对这个矩阵有些要求： 

1. 每行数字的个数一样多 
2. 列的数量尽可能少 
3. 填充数字时优先填充外部 
4. 数字不够时，使用单个*号占位

#### 输入

两个整数，空格隔开，依次表示n、m

#### 输出

符合要求的唯一短阵

#### 样例输入 复制

```plain
9 4
```

#### 样例输出 复制

```plain
1 2 3
* * 4
9 * 5
8 7 6
```

#### coding

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();  // 总数字个数
        int rows = scanner.nextInt();  // 行数
        scanner.close();

        // 计算列数，每行数字个数尽可能少，同时能容纳所有数字
        int cols = (int) Math.ceil(n / (double) rows);

        // 初始化矩阵
        String[][] matrix = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = "*";
            }
        }

        // 螺旋填充数字
        int num = 1;
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;
        while (num <= n) {
            for (int i = left; i <= right && num <= n; i++) {
                matrix[top][i] = String.valueOf(num++);
            }
            top++;
            for (int i = top; i <= bottom && num <= n; i++) {
                matrix[i][right] = String.valueOf(num++);
            }
            right--;
            for (int i = right; i >= left && num <= n; i--) {
                matrix[bottom][i] = String.valueOf(num++);
            }
            bottom--;
            for (int i = bottom; i >= top && num <= n; i--) {
                matrix[i][left] = String.valueOf(num++);
            }
            left++;
        }

        // 输出矩阵
        for (String[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                if (j > 0) System.out.print(" ");
                System.out.print(row[j]);
            }
            System.out.println();
        }
    }
}
```

# 【模拟】2024D-灰度图恢复

#### 题目描述

黑白图像常采用灰度图的方式存储，即图像的每像素填充一个灰阶值，256阶灰度图是个灰阶值取值范围为0-255的灰阶矩阵，0表示全黑，255表示全白，范围内的其他值表示不同的灰度，比如下面的图像及其对应的灰阶矩阵: 

![img](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408121911279.jpeg)

但在计算机中实际存储时，会使用压缩算法，其中一种压缩格式和描述如下：10 10 255 34 0 1 255 8 0 3 255 6 0 5 255 4 0 7 255 2 0 9 255 21 

1. 有数值以空格分隔 
2. 前两个数分别表示矩阵的行数和列数 
3. 从第三个数开始，每两个数一组，每组第一个数是灰阶值，第二个数表示该灰阶值以左到右，从上到下（可理解为将二维数组按行存储在一维矩阵中)的连续像素个数。

比如题目所述例子，255 34表示有连续34个像素的灰阶值是255。

如此，图像软件在打开此格式灰度图的时候，就可以根据此算法从压缩数据恢复出原始灰度图矩阵。 

请从输入的压缩数恢复灰度图原始矩阵，并返回指定像素的灰阶值。

#### 输入

输入包括两行，第一行是灰度图压缩数据，第二行表示一个像素位置的行号和列号，如 0 0 表示左上角像素

#### 输出

一个数字，表示输入数据表示的灰阶矩阵的指定像素的灰阶值

#### 样例输入 复制

```plain
10 10 255 34 0 1 255 8 0 3 255 6 0 5 255 4 0 7 255 2 0 9 255 21
3 4
```

#### 样例输出 复制

```plain
0
```

#### 提示

1. 系统保证输入的压缩数据是合法有效的，不会出现数据越界、数值不合法等无法恢复的场景 
2. 系统保证输入的像素坐标是合法的，不会出现不在矩阵中的像素 
3. 矩阵的行和列数范围为: (0, 100] 
4. 灰阶值取值范围为 [0,255]

#### coding

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static class Grayscale{
        public int value;
        public int count;
        public Grayscale(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int col = sc.nextInt();
        //skip跳过
        int[] array = Arrays.stream(sc.nextLine().split(" ")).skip(1).mapToInt(Integer::parseInt).toArray();
        int m=sc.nextInt();
        int n=sc.nextInt();
        LinkedList<Grayscale> list = new LinkedList<>();
        for (int i = 0; i < array.length; i+=2) {
            list.add(new Grayscale(array[i], array[i+1]));
        }
        int[][] matrix = new int[row][col];
        recovery(list,matrix);
        System.out.println(matrix[m][n]);
    }

    private static void recovery(LinkedList<Grayscale> list, int[][] matrix) {
        int row=matrix.length;
        int col=matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Grayscale g = list.getFirst();
                matrix[i][j] = g.value;
                g.count--;
                if (g.count == 0) {
                    list.removeFirst();
                }
            }
        }
    }
}
```

# 【模拟】2024D-来自异国的客人

#### 题目描述

有位客人来自异国，在该国使用m进制计数。该客人有个幸运数字n(n < m)，每次购物时，其总是喜欢计算本次支付的花费（折算为异国的价格后）中存在多少幸运数字。问: 当其购买一个在我国价值k的产品时，其中包含多少幸运数字?

#### 输入

第一行输入为k n m 

k 表示 该客人购买的物品价值 (以十进制计算的价格) 

n 表示 该客人的幸运数字 

m 表示 该客人所在国度的采用的进制

#### 输出

输出幸运数字的个数，行末无空格.

#### 样例输入 复制

```plain
10 2 4
```

#### 样例输出 复制

```plain
2
```

#### coding

进制转换方法：

转换一个数从十进制到任意进制 m 的基本方法是使用除基取余法，具体步骤如下：

1. **除基取余**：
   - 将十进制数 k 除以进制数 m，记录下余数。
   - 将得到的商继续除以进制数 m，再次记录余数。
   - 重复这个过程，直到商为0。
2. **倒序输出余数**：
   - 上面步骤记录的余数，从最后一个开始到第一个，依次构成了 kkk 在 mmm 进制下的表示。

```java

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt(); // 购买的物品价值（十进制）
        int n = scanner.nextInt(); // 幸运数字
        int m = scanner.nextInt(); // 使用的进制

        // 调用方法计算幸运数字的数量
        int count = countLuckyNumbers(k, n, m);
        System.out.print(count);

        scanner.close();
    }

    private static int countLuckyNumbers(int value, int luckyNumber, int base) {
        int count = 0;
        while (value > 0) {
            int digit = value % base;
            if (digit == luckyNumber) {
                count++;
            }
            value /= base;
        }
        return count;
    }
}
```

# 【排序】2024D-数组去重和排序

#### 题目描述

给定一个乱序的数组，删除所有的重复元素，使得每个元素只出现一次，并且按照出现的次数从高到低进行排序，相同出现次数按照第一次出现顺序进行先后排序。

#### 输入

一个数组

#### 输出

去重排序后的数组

#### 样例输入 复制

```plain
1,3,3,3,2,4,4,4,5
```

#### 样例输出 复制

```plain
3,4,1,2,5
```

#### 提示

数组大小不超过100，数组元素值大小不超过100

#### coding

```java
import java.util.*;

public class Main {
    public static class IntegerCount{
        public int value;
        public int count;
        public IntegerCount(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] array = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        LinkedHashMap<Integer,Integer> map = new LinkedHashMap<>();
        LinkedList<IntegerCount> list = new LinkedList<>();
        for (int i = 0; i < array.length; i++) {
            int cur=array[i];
            Integer count = map.getOrDefault(cur, 0);
            map.put(cur, count + 1);
        }
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            list.add(new IntegerCount(key,value));
        }
        list.sort((o1, o2) -> o2.count - o1.count);
        while(list.size() >1){
            IntegerCount integerCount = list.removeFirst();
            System.out.print(integerCount.value+",");
        }
        System.out.print(list.removeFirst().value);
    }
}
```

# 【模拟】2024D-学生重新排队（90%）

#### 题目描述

n个学生排成一排，学生编号分别是1到n，n为3的整倍数。 

老师随机抽签决定将所有学生分成个3人的小组，n=3*m

为了便于同组学生交流，老师决定将小组成员安排到一起，也就是同组成员彼此相连，同组任意两个成员之间无其它组的成员。 

因此老师决定调整队伍，老师每次可以调整任何一名学生到队伍的任意位置，计为调整了一次，请计算最少调整多少次可以达到目标。 

注意：对于小组之间没有顺序要求，同组学生之间没有顺序要求

#### 输入

两行字符串，空格分隔表示不同的学生编号。 

第一行是学生目前排队情况第二行是随机抽签分组情况，从左开始每3个元素为一组n为学生的数量，n的范围为[3，900]，n一定为3的整数倍。第一行和第二行的元素个数一定相同。

#### 输出

老师调整学生达到同组彼此相连的最小次数

#### 样例输入 复制

```plain
7 9 8 5 6 4 2 1 3
7 8 9 4 2 1 3 5 6
```

#### 样例输出 复制

```plain
1
```

#### 提示

学生目前排队情况：7 9 8 5 6 4 2 1 3 

学生分组情况：[7 8 9]、[4 2 1]、[3 5 6] 

将3调整到4之前，队列调整为7 9 8 5 6 3 4 2 1 

那么三个小组成员均彼此相连[7 9 8]、[5 6 3]、[4 2 1] 

输出：1

#### coding

```java
import java.util.*;

public class Main {
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
                //流式处理，寻找下一个组号相同的组
                Group next = groups.stream()
                        .filter(g -> g.groupId == group.groupId)
                        .findFirst().get();
                Group[] neighbors = findNeighbors(groups, next);
                //如果组的大小为2，且为当前位置的前后邻居不为同一组，移动一步即当前group移动到next
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

```



# 【贪心】2024D-伐木工

#### 题目描述

一根X米长的树木，伐木工切割成不同长度的木材后进行交易，交易价格为每根木头长度的乘积。 

规定切割后的每根木头长度都为正整数，也可以不切割，直接拿整根树木进行交易。 

请问伐木工如何尽量少的切割，才能使收益最大化？

#### 输入

木材的长度 (X<=50)

#### 输出

输出最优收益时的各个树木长度，以空格分割，按升序排列

#### 样例输入 复制

```plain
10
```

#### 样例输出 复制

```plain
3 3 4
```

#### coding

为了最大化乘积，我们可以尝试以下策略：

- 尽可能将木材切成长度为 3 的段，因为 3 是乘积增长最快的长度之一（除非剩余长度小于 4）。
- 如果剩余长度为 4，不再切成 1 和 3，而是切成 2 和 2（因为 2×2=4大于 1×3=3）。

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();;  // 木材长度
        List<Integer> cuts = maxProfitCuts(X);
        cuts.sort(Integer::compareTo); 
        for (int cut : cuts) {
            System.out.print(cut + " ");
        }
    }

    public static List<Integer> maxProfitCuts(int X) {
        List<Integer> result = new ArrayList<>();
        while (X > 0) {
            if (X == 4) { //4=2*2并且>3*1，无需切割
                result.add(4);
                break;
            }
            if (X >= 3) {
                result.add(3);
                X -= 3;
            } else {
                result.add(X);
                break;
            }
        }
        return result;
    }
}
```

# 【贪心】2024D-停车找车位

#### 题目描述

停车场有一横排车位，0 代表没有停车，1 代表有车。至少停了一辆车在车位上，也至少有一个空位没有停车。 为了防剐蹭，需为停车人找到一个车位，使得距停车人的车最近的车辆的距离是最大的，返回此时的最大距离。

#### 输入

​		1、一个用半角逗号分割的停车标识字符串，停车标识为 `0` 或 `1`，`0` 为空位，`1` 为已停车。 

​		2、停车位最多 `100` 个。 



#### 输出

​		输出一个整数记录最大距离。 



#### 样例输入 复制

```plain
1,0,0,0,0,1,0,0,1,0,1
```

#### 样例输出 复制

```plain
2
```

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] parks = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int park=findPark(parks);
        System.out.println(park);
    }

   private static int findPark(int[] parks) {
        int n=parks.length;
        int maxDistance=0;
        int i=0;
        while(i<n){
            if(parks[i]==0){
                int start=i;
                while(i<n&&parks[i]==0){
                    i++;
                }
                int end=i;//[)
                int len=end-start;
                if(start==0||end==n){
                    maxDistance=Math.max(maxDistance,len);
                }else{
                    int distance=(int)Math.ceil((double)len/2);
                    maxDistance=Math.max(maxDistance,distance);
                }
            }else{
                i++;
            }
        }
        return maxDistance;
    }
}

```

# 【贪心】2024D-社交距离

#### 题目描述

疫情期间，需要大家保证一定的社交距离，公司组织开交流会议，座位有一排共N个座位，编号分别为[0, N-1]，要求员工一个接着一个进入会议室，并且可以在任何时候离开会议室。 

满足：每当一个员工进入时，需要坐到最大社交距离的座位（例如：位置A与左右有员工落座的位置距离分别为2和2，位置B与左右有员工落座的位置距离分别为2和3，影响因素都为2个位置，则认为座位A和B与左右位置的社交距离是一样的)；如果有多个这样的座位，则坐到索引最小的那个座位。

#### 输入

会议室座位总数seatNum，(1 ≤ seatNums ≤ 500) 

员工的进出顺序seatOrLeave数组，元素值为1：表示进场；元素值为负数，表示出场（特殊：位置0的员工不会离开），例如-4表示坐在位置4的员工离开（保证有员工坐在该座位上）

#### 输出

最后进来员工，他会坐在第几个位置，如果位置已满，则输出-1

#### 样例输入 复制

```plain
10
[1, 1, 1, 1, -4, 1]
```

#### 样例输出 复制

```plain
5
```

#### coding

```java
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static class Seat{
        public int seatId;
        public int distance;
        public Seat(int seatId, int distance) {
            this.seatId = seatId;
            this.distance = distance;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String input= sc.nextLine();
        int[] seatOrLeave = Arrays.stream(input.substring(1, input.length() - 1).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        int seatId=findSeat(n,seatOrLeave);
        System.out.println(seatId);
    }

    private static int findSeat(int n, int[] seatOrLeave) {
        long in = Arrays.stream(seatOrLeave).filter(e -> e == 1).count();
        long out = seatOrLeave.length-in;
        if(in-out>n&&seatOrLeave[seatOrLeave.length-2]==1){
            return -1;
        }
        PriorityQueue<Seat> queue = new PriorityQueue<>((o1, o2) -> o1.distance==o2.distance?o1.seatId-o2.seatId:o2.distance - o1.distance);
        boolean[] occupied=new boolean[n];
        queue.add(new Seat(0,0));
        Seat seat=null;
        for(int action:seatOrLeave){
            if(action==1){
                if(!queue.isEmpty()){
                    seat = queue.poll();
                    occupied[seat.seatId]=true;
                    queue=updateDistance(occupied);
                }
            }else{
                if(occupied[-action]){
                    occupied[-action]=false;
                    queue=updateDistance(occupied);
                }
            }
        }
        return seat==null?-1:seat.seatId;
    }

    private static PriorityQueue<Seat> updateDistance(boolean[] occupied) {
        PriorityQueue<Seat> queue = new PriorityQueue<>((o1, o2) -> o1.distance == o2.distance ? o1.seatId - o2.seatId : o2.distance - o1.distance);
        for (int i = 0; i < occupied.length; i++) {
            if(!occupied[i]){
                int distance=getDistance(i,occupied);
                queue.add(new Seat(i,distance));
            }
        }
        return queue;
    }

    private static int getDistance(int i, boolean[] occupied) {
        int left=i;
        int leftDistance=0;
        int right=i;
        int rightDistance=0;
        while(left>=0&&!occupied[left]){
            leftDistance++;
            left--;
        }
        while(right<occupied.length&&!occupied[right]){
            rightDistance++;
            right++;
        }
        if(i==occupied.length-1)return leftDistance;
        return Math.min(leftDistance,rightDistance);
    }
}

```

# 【贪心】2024D-会议室占用时间

#### 题目描述

现有若干个会议，所有会议共享一个会议室，用数组表示各个会议的开始时间和结束时间，格式为： 

[[会议1开始时间, 会议1结束时间], [会议2开始时间, 会议2结束时间]] 

请计算会议室占用时间段。

- 会议室个数范围：`[1, 100]`
- 会议室时间段：`[1, 24]`

#### 输入

第一行输入一个整数n，表示会议数量 

之后输入n行，每行两个整数，以空格分隔，分别表示会议开始时间，会议结束时间

#### 输出

输出多行，每个两个整数，以空格分隔，分别表示会议室占用时间段开始和结束

#### 样例输入 复制

```plain
4
1 4
2 5
7 9
14 18
```

#### 样例输出 复制

```plain
1 5
7 9
14 18
```

#### 提示

输入：[[1,4],[2,5],[7,9],[14,18]] 

输出：[[1,5],[7,9],[14,18]] 

说明：时间段[1,4]和[2,5]重叠，合并为[1,5]

#### coding

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static class Meeting{
        public int start;
        public int end;
        public Meeting(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            String[] input = sc.nextLine().split(" ");
            meetings[i] = new Meeting(Integer.parseInt(input[0]), Integer.parseInt(input[1])); // 创建Meeting对象
        }
        LinkedList<String>timeSlots=occupancyTimeSlots(meetings);
        for(String timeSlot:timeSlots){
            System.out.println(timeSlot);
        }
    }

    private static LinkedList<String> occupancyTimeSlots(Meeting[] meetings) {
        Arrays.sort(meetings,(o1,o2)->o1.start-o2.start);
        LinkedList<String> timeSlots = new LinkedList<>();
        int i=0;
        while(i<meetings.length){
            int curStart = meetings[i].start;
            int curEnd = meetings[i].end;
            while(i<meetings.length && meetings[i].start<=curEnd){
                curEnd = Math.max(curEnd, meetings[i].end);
                i++;
            }
            timeSlots.add(curStart+" "+curEnd);
        }
        return timeSlots;
    }
}

```

# 【二分查找】2024D-孙悟空吃蟠桃

#### 题目描述

孙悟空喜欢吃蟠桃，一天他趁守卫蟠桃园的天兵天将离开了而偷偷的来到王母娘娘的蟠桃园偷吃蟠桃。 

已知蟠桃园有 N 棵蟠桃树，第 i棵蟠桃树上有 N[i]（大于 0）个蟠桃，天兵天将将在 H（不小于蟠桃树棵数）小时后回来。 

孙悟空可以决定他吃蟠桃的速度 K （单位：个/小时），每个小时他会选择一颗蟠桃树，从中吃掉 K 个蟠桃，如果这棵树上的蟠桃数小于 K ，他将吃掉这棵树上所有蟠桃，然后这一小时内不再吃其余蟠桃树上的蟠桃。 

孙悟空喜欢慢慢吃，但仍想在天兵天将回来前将所有蟠桃吃完。 

求孙悟空可以在 H 小时内吃掉所有蟠桃的最小速度 K（K 为整数）。

#### 输入

第一行输入为 N 个数字，N 表示桃树的数量，这 N 个数字表示每颗桃树上蟠桃的数量 

第二行输入为一个数字，表示守卫离开的时间 H。 

其中数字通过空格分割，N、H 为正整数，每颗树上都有蟠桃

 0 < N < 10000，0< H < 10000。

#### 输出

吃掉所有蟠桃的最小速度 K（K 为整数），无解或者输入异常时输出 0 。

#### 样例输入 复制

```plain
3 11 6 7 8
5
```

#### 样例输出 复制

```plain
11
```

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] peachTree = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int H = Integer.parseInt(sc.nextLine());
        int k=getMinK(peachTree,H);
        System.out.println(k);
    }

    private static int getMinK(int[] peachTree, int h) {
        int low=1;
        int high= Arrays.stream(peachTree).max().getAsInt();
        while(low<high){
            int mid=low+((high-low)>>1);
            if(isFeasible(peachTree,h,mid)){
                high=mid;
            }else{
                low=mid+1;
            }
        }
        return low;
    }

    private static boolean isFeasible(int[] peachTree, int h, int k) {
        int hours=0;
        for(int peach:peachTree){
            hours+=(int)Math.ceil(peach/(double)k);
        }
        return hours<=h;
    }
}
```

# 【二分查找】2024D-小明找位置

#### 题目描述

小朋友出操，按学号从小到大排成一列；小明来迟了，请你给小明出个主意，让他尽快找到他应该排的位置。 

算法复杂度要求不高于 nlog(n)；学号为整数类型，队列规模 <= 10000；

#### 输入

\- 第一行：输入已排成队列的小朋友的学号（正整数），以","隔开；例如：93,95,97,100,102,123,155 

\- 第二行：小明学号，如 110；

#### 输出

输出一个数字，代表队列位置（从 1 开始）。

#### 样例输入 复制

```plain
93,95,97,100,102,123,155
110
```

#### 样例输出 复制

```plain
6
```

#### coding

```java
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] heights = Arrays.stream(sc.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int XiaoMing=sc.nextInt();
        int index=binarySearch(heights,XiaoMing);
        System.out.println(index+1);
    }
    private static int binarySearch(int[] arr, int target) {
        int index = Arrays.binarySearch(arr, target);
        return -index - 1;
    }
}

```

# 【前缀和】2024D-分割数组的最大差值

#### 题目描述

给定一个由若干整数组成的数组nums ，可以在数组内的任意位置进行分割，将该数组分割成两个非空子数组（即左数组和右数组），分别对子数组求和得到两个值，计算这两个值的差值，请输出所有分割方案中，差值最大的值。

#### 输入

第一行输入数组中元素个数n，1 < n <= 100000 

第二行输入数字序列，以空格进行分隔，数字取值为4字节整数

#### 输出

输出差值的最大取值

#### 样例输入 复制

```plain
6
1 -2 3 4 -9 7
```

#### 样例输出 复制

```plain
10
```

#### 提示

将数组nums 划分为两个非空数组的可行方案有： 

左数组 = [1] 且 右数组 = [-2, 3, 4, -9, 7]，和的差值 = |1 - 3|=2 

左数组 = [1,-2] 且 右数组 = [3,4,-9,7]，和的差值 =| -1-5 |=6 

左数组 =[1,-2,3,1] 且 右数组 =[4, -9, 7]，和的差值 =|2 - 2|=0 

左数组 =[1,-2,3,4] 且 右数组=[-9, 7]，和的差值 =|6 -(-2)| = 8 

左数组 =[1,-2,3,4,-9] 且 右数组 = [7]，和的差值 =|-3-7| = 10 

最大的差值为10

#### coding

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr=new int[n];
        for (int i = 0; i < n; i++) {
            arr[i]=sc.nextInt();
        }
        int maxDiff=getMaxDifference(arr);
        System.out.println(maxDiff);
    }

    private static int getMaxDifference(int[] arr) {
        int maxDiff=0;
        int n = arr.length;
        int[] prefixAnd = new int[n + 1];
        prefixAnd[0]=0;
        for (int i = 1; i < n+1; i++) {
            prefixAnd[i] = prefixAnd[i-1]+arr[i-1];
        }
        for (int i = 1; i < n; i++) {
            //[start,end]
            int start1=0,end1=i-1,start2=i,end2=n-1;
            int value1=prefixAnd[end1+1]-prefixAnd[start1];
            int value2=prefixAnd[end2+1]-prefixAnd[start2];
            int diff=Math.abs(value1-value2);
            maxDiff=Math.max(maxDiff,diff);
        }
        return maxDiff;
    }
}
```

# 【前缀和】2024D-环中最长字串2（需优化）

#### 题目描述

给你一个字符串s，字符串s首尾相连成一个环形 ，请你在环中找出"l"、"o"、"x" 字符都恰好出现了偶数次最长子字符串的长度。

#### 输入

输入是一串小写的字母组成的字符串。

#### 输出

输出是一个整数。

#### 样例输入 复制

```plain
alolobo
```

#### 样例输出 复制

```plain
6
```

#### coding

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int maxLen=getMaxLen(s);
        System.out.println(maxLen);
    }

    private static int getMaxLen(String s) {
        int maxLen=0;
        char[] charArray = s.toCharArray();
        int n = charArray.length;
        char[] chars = new char[n * 2];
        for (int i = 0; i < n*2; i++) {
            chars[i]=charArray[i%n];
        }
        for(int start=0;start<n;start++){
            for(int end=start+1;end<start+n;end++){
                if(isFeasible(start,end,chars)){
                    maxLen=Math.max(maxLen,end-start+1);
                }
            }
        }
        return maxLen;
    }
    /*"l"、"o"、"x"*/
    private static boolean isFeasible(int start, int end,char[] chars) {
        int count1=0;
        int count2=0;
        int count3=0;
        for(int i=start;i<=end;i++){
            char c=chars[i];
            if(c=='l'){
                count1++;
            } else if(c=='o'){
                count2++;
            } else if(c=='x'){
                count3++;
            }
        }
        return (count1%2)==0&&(count2%2)==0&&(count3%2)==0;
    }
}

```

# 【DFS/BFS】2024D-可以组成网络的服务器

#### 题目描述

在一个机房中，服务器的位置标识在 n*m 的整数矩阵网格中，1表示单元格上有服务器，0 表示没有。如果两台服务器位于同一行或者同一列中紧邻的位置，则认为它们之间可以组成一个局域网。请你统计机房中最大的局域网包含的服务器个数。

#### 输入

第一行输入两个正整数，n和m，0 < n,m <= 100 

之后为n*m的二维数组，代表服务器信息

#### 输出

最大局域网包含的服务器个数。

#### 样例输入 复制

```plain
2 2
1 0
1 1
```

#### 样例输出 复制

```plain
3
```

#### 提示

[0][0]、[1][0]、[1][1]三台服务器相互连接，可以组成局域网

#### coding

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] matrixNetwork=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                matrixNetwork[i][j]=sc.nextInt();
            }
        }
        sc.close();
        int maxLan=countMaxLan(matrixNetwork,n,m);
        System.out.println(maxLan);
    }

    private static int countMaxLan(int[][] matrixNetwork,int n,int m) {
        int maxCountLan=0;
        boolean[][] visited=new boolean[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(matrixNetwork[i][j]==1&&!visited[i][j]){
                    int count=dfs(matrixNetwork,n,m,i,j,visited);
                    maxCountLan=Math.max(maxCountLan,count);
                }
            }
        }
        return maxCountLan;
    }

    private static int dfs(int[][] matrixNetwork, int n,int m,int i, int j, boolean[][] visited) {
        if(i<0||i>=n||j<0||j>=m||visited[i][j]||matrixNetwork[i][j]==0){
            return 0;
        }
        int count=1;
        visited[i][j]=true;
        count+=dfs(matrixNetwork,n,m,i+1,j,visited);
        count+=dfs(matrixNetwork,n,m,i-1,j,visited);
        count+=dfs(matrixNetwork,n,m,i,j+1,visited);
        count+=dfs(matrixNetwork,n,m,i,j-1,visited);
        return count;
    }
}
```

# 【DFS/BFS】2024D-图像物体的边界

#### 题目描述

给定一个二维数组`M`行`N`列，二维数组里的数字代表图片的像素，为了简化问题，仅包含像素`1`和`5`两种像素，每种像素代表一个物体，`2`个物体相邻的格子为边界，求像素`1`代表的物体的边界个数。像素`1`代表的物体的边界指与像素`5`相邻的像素`1`的格子，边界相邻的属于同一个边界，相邻需要考虑8个方向(上，下，左，右，左上，左下，右上，右下)

其他约束:

地图规格约束为:

```
0<M<100
0<N<100
```

1 如下图，与像素`5`的格子相邻的像素`1`的格子`(0,0)、(0,1)、(0,2)、(1,0)、(1,2)、(2,0)、(2,1)、(2,2)、(4,4)、(4,5)、(5,4)`为边界，另`(0,0)、(0,1)、(0,2)、(1,0)、(1,2)、(2,0)、(2,1)、(2,2)`相邻，为`1`个边界，`(4,4)、(4,5)、(5,4)`相邻，为`1`个边界，所以下图边界个数为`2`。

![img](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408151157841.jpeg)



2 如下图，与像素`5`的格子相邻的像素`1`的格子`(0,0)、(0,1)、(0,2)、(1,0)、(1,2)、(2,0)、(2,1)、(2,2)、(3,3)、(3,4)、(3,5)、(4,3)、(4,5)、(5,3)、(5,4)、(5,5)`为边界，另这些边界相邻，所以下图边界个数为`1`。注：`(2,2)、(3,3)`相邻

![img](https://cdn.jsdelivr.net/gh/AngryYYyyyy/picture/note/202408151157836.jpeg)



#### 输入

第一行，行数M，列数N 

第二行开始，是M行N列的像素的二维数组，仅包含像素1和5

#### 输出

像素1代表的物体的边界个数。如果没有边界输出0（比如只存在像素1，或者只存在像素5）

#### 样例输入 复制

```plain
6 6
1 1 1 1 1 1
1 5 1 1 1 1
1 1 1 1 1 1
1 1 1 1 1 1
1 1 1 1 1 1
1 1 1 1 1 5
```

#### 样例输出 复制

```plain
2
```

#### coding

```java
import java.util.Scanner;

public class Main {
    private static final int[]dx={-1,-1,-1,0,0,1,1,1};
    private static final int[]dy={-1,0,1,1,-1,-1,0,1};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                grid[i][j]=sc.nextInt();
            }
        }
        sc.close();
        int boundaryCount=countBoundary(grid,n,m);
        System.out.println(boundaryCount);
    }

    private static int countBoundary(int[][] grid, int n, int m) {
        boolean[][] visited=new boolean[n][m];
        int count=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(!visited[i][j]&&grid[i][j]==1&&isBoundary(grid,i,j)){
                    dfs(grid,i,j,visited);
                    count++;
                }
            }
        }
        return count;
    }

    private static void dfs(int[][] grid, int i, int j, boolean[][] visited) {
        int n = grid.length;
        int m = grid[0].length;
        if(i<0||i>=n||j<0||j>=m||visited[i][j]||grid[i][j]!=1)  return;
        visited[i][j]=true;
        for(int k=0;k<8;k++){
            int x=i+dx[k];
            int y=j+dy[k];
            if(isValid(x,y,n,m)&&!visited[x][y]&&grid[x][y]==1&&isBoundary(grid,x,y)){
                dfs(grid,x,y,visited);
            }
        }
    }

    private static boolean isBoundary(int[][] grid, int i, int j) {
        int n=grid.length;
        int m=grid[0].length;
        for(int k=0;k<8;k++){
            int x=i+dx[k];
            int y=j+dy[k];
            if(isValid(x,y,n,m)&&grid[x][y]==5){
                return true;
            }
        }
        return false;
    }

    private static boolean isValid(int x, int y,int n,int m) {
        return x>=0&&x<n&&y>=0&&y<m;
    }
}

```

# 【DFS/BFS】2024D-地图寻宝（BFS）

#### 题目描述

小华按照地图去寻宝，地图上被划分成 m 行和 n 列的方格，横纵坐标范围分别是 [0, n-1] 和 [0, m-1]。 

在横坐标和纵坐标的数位之和不大于 k 的方格中存在黄金（每个方格中仅存在一克黄金），但横坐标和纵坐标数位之和大于 k 的方格存在危险不可进入。

小华从入口 (0,0) 进入，任何时候只能向左，右，上，下四个方向移动一格。 请问小华最多能获得多少克黄金？

#### 输入

坐标取值范围如下： 0 ≤ m ≤ 50，0 ≤ n ≤ 50

k 的取值范围如下： 0 ≤ k ≤ 100 

输入中包含 3 个字数，分别是 m, n, k

#### 输出

输出小华最多能获得多少克黄金

#### 样例输入 复制

```plain
40 40 18
```

#### 样例输出 复制

```plain
1484
```

#### coding

```java
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 行数
        int m = sc.nextInt(); // 列数
        int k = sc.nextInt(); // 数位之和的最大允许值
        sc.close();
        int maxTreasure = treasureHunt(n, m, k);
        System.out.println(maxTreasure);
    }

    private static int treasureHunt(int n, int m, int k) {
        if (!isFeasible(0, 0, k)) return 0; // 检查起点是否安全

        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int i = current[0];
            int j = current[1];
            count++; // 访问该方格，增加黄金计数

            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : directions) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                if (isValid(ni, nj, n, m) && !visited[ni][nj] && isFeasible(ni, nj, k)) {
                    visited[ni][nj] = true;
                    queue.offer(new int[]{ni, nj});
                }
            }
        }
        return count;
    }

    private static boolean isValid(int i, int j, int n, int m) {
        return i >= 0 && i < n && j >= 0 && j < m;
    }

    private static boolean isFeasible(int i, int j, int k) {
        return getDigitSum(i) + getDigitSum(j) <= k;
    }

    private static int getDigitSum(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}

```

# 【DFS/BFS】2024D-二叉树的广度优先遍历

#### 题目描述

有一棵二叉树，每个节点由一个大写字母标识（最多26个节点），现有两组字母，分别表示后序遍历 (左孩子->右孩子->父节点) 和中序遍历 (左孩子->父节点->右孩子) 的结果，请输出层次遍历的结果。

#### 输入

输入为两个字符串，分别是二叉树的后序遍历和中序遍历结果

#### 输出

输出二叉树的层次遍历结果

#### 样例输入 复制

```plain
CBEFDA CBAEDF
```

#### 样例输出 复制

```plain
ABDCEF
```

#### 提示

二叉树为
  A
  / \
 B  D
 /  / \
C  E  F

#### coding

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static class Node{
        public char value;
        public Node left;
        public Node right;
        public Node(char value) {
            this.value = value;
        }
        public Node(char value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] strings = sc.nextLine().split(" ");
        String postorder=strings[0];
        String inorder=strings[1];
        Node root=buildTree(postorder,inorder);
        levelOrderTraversal(root);
    }

    private static void levelOrderTraversal(Node root) {
        if(root==null) return;
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            Node temp=q.poll();
            System.out.print(temp.value);
            if(temp.left!=null) q.add(temp.left);
            if(temp.right!=null) q.add(temp.right);
        }
    }

    private static Node buildTree(String postorder, String inorder) {
        if (postorder.isEmpty() || inorder.isEmpty()) {return null;}
        int poL = postorder.length();
        int ioL = inorder.length();
        char c = postorder.charAt(poL - 1);
        Node root = new Node(c);
        int idx = inorder.indexOf(c);
        root.left = buildTree(postorder.substring(0,idx), inorder.substring(0,idx));
        root.right = buildTree(postorder.substring(idx,poL-1),inorder.substring(idx+1));
        return root;
    }
}

```

# 【DFS/BFS】2024D-聚餐地点

#### 题目描述

小华和小为是很好的朋友，他们约定周末一起吃饭，通过手机交流，他们在地图上选择了很多聚餐地点 

（由于自然地形等原因，部分聚餐地点不可达），求小华和小为都能达到的聚餐地点有多少个。

#### 输入

第一行输入 m 和 n，m 表示地图长度，n 表示地图宽度 

第二行开始具体输入地图信息，地图信息包括 

0 为通畅的道路 

1 为障碍物（且仅 1 为障碍物） 

2 为小华或小为，地图中必定有且仅有两个（非障碍物） 

3 为被选中的聚餐地点（非障碍物）

#### 输出

可以两方都到达的聚餐地点的数量，行末无空格

#### 样例输入 复制

```plain
4 4
2 1 0 3
0 1 2 1
0 3 0 0
0 0 0 0
```

#### 样例输出 复制

```plain
2
```

#### coding

```java
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        int dinnerCount = countDinner(grid, n, m);
        System.out.println(dinnerCount);
    }

    private static int countDinner(int[][] grid, int n, int m) {
        LinkedList<int[]> queue = new LinkedList<>();
        int[][] visited = new int[n][m];  // 0:未访问, 1:由小华访问, 2:由小为访问, 3:两者都访问过
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        // 初始化队列和访问数组
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    queue.add(new int[]{i, j});
                    if (visited[i][j] == 0) {
                        visited[i][j] = queue.size();  // 1 或 2 标记
                    } else {
                        visited[i][j] = 3;  // 两者都可以到达
                    }
                }
            }
        }
        
        // BFS遍历
        while (!queue.isEmpty()) {
            int[] position = queue.poll();
            int x = position[0];
            int y = position[1];
            for (int[] direction : directions) {
                int nx = x + direction[0];
                int ny = y + direction[1];
                if (isValid(nx, ny, n, m) && grid[nx][ny] != 1 && visited[nx][ny] != 3) {
                    if (visited[nx][ny] == 0 || visited[nx][ny] != visited[x][y]) {
                        visited[nx][ny] |= visited[x][y];  // 设置访问标记
                        queue.offer(new int[]{nx, ny});
                    }
                }
            }
        }
        
        // 统计可以两方都到达的聚餐地点数量
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 3 && visited[i][j] == 3) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isValid(int x, int y, int n, int m) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }
}
```

# 【回溯】2024D-找到它

#### 题目描述

找到它是个小游戏，你需要在一个矩阵中找到给定的单词 

假设给定单词HELLOWORLD，在矩阵中只要能找HELLOWORLD就算通过 

注意区分英文字母大小写，并且你只能上下左右行走，不能走回头路

#### 输入

输入第一行包含两个整数N M (0 < N, M < 21) 

分别表示N行M列的矩阵 

第二行是长度不超过100的单词W 

在整个矩阵中给定单词W只会出现一次 

从第3行到第N+2是只包含大小写英文字母的长度为M的字符串矩阵

#### 输出

如果能在矩阵中连成给定的单词，则输出给定单词首字母在矩阵中的位置为第几行第几列 

否则输出 NO

#### 样例输入 复制

```plain
5 5
HELLOWORLD
CPUCY
EKLQH
CHELL
LROWO
DGRBC
```

#### 样例输出 复制

```plain
3 2
```

#### coding

```java
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private static final int[][] directions={{0,1},{1,0},{0,-1},{-1,0}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();
        String target = sc.nextLine();
        char[][] grid = new char[n][m];

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
            }
        }
        int[]position=findIt(grid,n,m,target);
        if(position[0]==-1&&position[1]==-1){
            System.out.println("NO");
        }else{
            System.out.println((position[0]+1)+" "+(position[1]+1));
        }

    }
    private static int[] findIt(char[][] grid, int n, int m,String target) {
        int[]position=new int[]{-1,-1};
        boolean[][] visited=new boolean[n][m];
        LinkedList<int[]> departures=new LinkedList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(target.startsWith(String.valueOf(grid[i][j]))){
                    departures.add(new int[]{i,j});
                }
            }
        }
        while(!departures.isEmpty()){
            int[] departure =departures.removeFirst();
            int x=departure[0];
            int y=departure[1];
            visited[x][y]=true;
            if(isFind(x,y,1,target,visited,grid)){
                position[0]=x;
                position[1]=y;
                break;
            }
            visited[x][y]=false;
        }
        return position;
    }
    /*表示字符串第i个元素该如何选择去向*/
    /*i之前的路径已匹配*/
    private static boolean isFind(int x, int y, int i, String target, boolean[][] visited, char[][] grid) {
        if(i==target.length()){return true;}
        visited[x][y]=true;
        int n=grid.length;
        int m=grid[0].length;
        /*回溯*/
        for(int[] direction:directions){
            int nx=x+direction[0];
            int ny=y+direction[1];
            if(isValid(nx,ny,n,m)&&!visited[nx][ny]&&grid[nx][ny]==target.charAt(i)){
                if(isFind(nx,ny,i+1,target,visited,grid)){
                    return true;
                }
                /*回溯*/
                visited[nx][ny]=false;
            }
        }
        return false;
    }

    private static boolean isValid(int x, int y, int n, int m) {
        return x>=0&&x<n&&y>=0&&y<m;
    }

}
```

# 【回溯】2024D-考古学家

#### 题目描述

有一个考古学家发现一个石碑，但是很可惜 发现时其已经断成多段，原地发现 N 个断口整齐的石碑碎片，为了破解石碑内容 

考古学家希望有程序能帮忙计算复原后的石碑文字，你能帮忙吗 



备注 如果存在石碑碎片内容完全相同，则由于碎片间的顺序不影响复原后的碑文内容。仅相同碎片间的位置变化不影响组合

#### 输入

第一行输入 N，N 表示石碑碎片的个数 

第二行依次输入石碑碎片上的文字内容 S 共有 N 组

#### 输出

输出石碑文字的组合(按照升序排列)，行尾无多余空格

#### 样例输入 复制

```plain
3
a b ab
```

#### 样例输出 复制

```plain
aabb
abab
abba
baab
baba
```

#### coding

```java
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] fragments=new String[n];
        for (int i = 0; i < n; i++) {
            fragments[i]=sc.next();
        }
        sc.close();
        LinkedList<String>texts=recover(fragments,n);
        Collections.sort(texts);
        texts.stream().distinct().forEach(System.out::println);
    }

    private static LinkedList<String> recover(String[] fragments, int n) {
        LinkedList<String> _fragments = new LinkedList<>(Arrays.stream(fragments).collect(Collectors.toList()));
        LinkedList<String> texts = new LinkedList<>();
        contact(_fragments,"",texts);
        return texts;
    }

    private static void contact(LinkedList<String> fragments, String current, LinkedList<String> texts) {
        if(fragments.isEmpty()){
            texts.add(current);
        }

        for (String fragment : fragments) {
            LinkedList<String> list = new LinkedList<>(fragments);
            list.remove(fragment);
            contact(list,current+fragment,texts);
        }
    }
}

```

# 【回溯】2024D-田忌赛马

**内存限制：128 MB****时间限制：2.000 S**

**评测方式：文本比较****命题人：外部导入**

**提交：531****解决：143**

[提交](https://oj.algomooc.com/submitpage.php?id=3850)[提交记录](https://oj.algomooc.com/status.php?problem_id=3850)[统计](https://oj.algomooc.com/problemstatus.php?id=3850)[露一手!](https://oj.algomooc.com/problem.php?id=3850#)

#### 题目描述

给定两个只包含数字的数组 a, b，调整数组 a 里面数字的顺序，使得尽可能多的 a[i] > b[i]。数组 a 和 b 中的数字各不相同。 

输出所有可以达到最优结果的 a 数组数量

#### 输入

输入的第一行是数组 a 中的数字， 输入的第二行是数组 b 中的数字， 其中只包含数字，每两个数字之间相隔一个空格，a，b 数组大小不超过 10

#### 输出

输出所有可以达到最优结果的 a 数组数量

#### 样例输入 复制

```plain
11 12 20
10 13 7
```

#### 样例输出 复制

```plain
2
```

#### 提示

有两个 a 数组的排列可以达到最优结果，[12, 20, 11]和[11, 20, 12]，故输出 2

#### coding

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
   static int maxMatches = 0;
    static int optimalCount = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] aStr = scanner.nextLine().split(" ");
        String[] bStr = scanner.nextLine().split(" ");
        Integer[] a = new Integer[aStr.length];
        Integer[] b = new Integer[bStr.length];

        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(aStr[i]);
            b[i] = Integer.parseInt(bStr[i]);
        }

        Arrays.sort(b); // 排序b数组以便比较
        permuteAndEvaluate(a, b, 0);
        
        // 输出0而不是1，当没有任何有效的匹配时
        if (maxMatches == 0) {
            System.out.println(0);
        } else {
            System.out.println(optimalCount);
        }
    }

    private static void permuteAndEvaluate(Integer[] a, Integer[] b, int start) {
        if (start == a.length) {
            int currentMatches = 0;
            for (int i = 0; i < a.length; i++) {
                if (a[i] > b[i]) {
                    currentMatches++;
                }
            }

            if (currentMatches > maxMatches) {
                maxMatches = currentMatches;
                optimalCount = 1;
            } else if (currentMatches == maxMatches && maxMatches > 0) {
                optimalCount++;
            }
            return;
        }

        for (int i = start; i < a.length; i++) {
            swap(a, start, i);
            permuteAndEvaluate(a, b, start + 1);
            swap(a, start, i);
        }
    }

    private static void swap(Integer[] array, int i, int j) {
        Integer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
```

