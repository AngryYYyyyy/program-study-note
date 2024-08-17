package basic.array;

import com.sun.xml.internal.ws.api.model.CheckedException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/17 17:04
 * @Description：
 */
public class ArraysUtils {
    public static void main(String[] args) {
        int[]arr={3,1,7,4,1,6,9,5};
        /*排序*/
        Arrays.sort(arr);
        /*转换字符串格式[,,,]*/
        System.out.println(Arrays.toString(arr));
        /*二分查找*/
        int i = Arrays.binarySearch(arr, 3);
        System.out.println(i);
        /*转换列表*/
        /*泛型在 Java 中不支持基本数据类型，它们只能处理对象*/
        //List<int[]> list = Arrays.asList(arr);
        Arrays.stream(arr).boxed().collect(Collectors.toList()).forEach(e->System.out.print(e+" "));
    }
}
