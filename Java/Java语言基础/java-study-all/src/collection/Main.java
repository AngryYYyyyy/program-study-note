package collection;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*转换为包装类Integer，必须是引用数据类型*/
        Generic.method(3);
        Generic.method(new Integer[]{1, 2, 3, 4});
        Generic.method(new ArrayList<>(Arrays.asList("a", "b", "c")));
        ArrayList<Integer> list = new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();
        HashSet hashSet = new HashSet();
        TreeSet treeSet = new TreeSet();
        HashMap hashMap = new HashMap();
    }
}
