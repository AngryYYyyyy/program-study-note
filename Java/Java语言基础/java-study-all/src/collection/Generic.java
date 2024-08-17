package collection;

import java.util.ArrayList;
import java.util.Arrays;

public class Generic {
    public static <T> void method(T l){
        System.out.println(l);
    }
    public static <T> void method(T[] l){
        Arrays.stream(l).forEach(e->System.out.print(e+" "));
        System.out.println();
    }
    public static  <T>void method(ArrayList<T> l){
        System.out.println(l);
    }
}
