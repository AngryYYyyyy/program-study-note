package com.lxy.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    void test() throws ClassNotFoundException {
        System.out.println(Student.class);
        Student zhangsan = new Student("zhangsan", 18);
        System.out.println(zhangsan.getClass());
        Class<?> aClass = Class.forName("com.lxy.reflect.Student");
        System.out.println(aClass);
    }
    @Test
    void test2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<Student> studentClass = Student.class;
        Constructor<Student> constructor = studentClass.getConstructor(String.class, Integer.class);
        Student student = constructor.newInstance("zhangsan",18);
        System.out.println(student);
        Constructor<Student> constructor1 = studentClass.getConstructor();
        Student student1 = constructor1.newInstance();
        Field name = studentClass.getField("name");
        Field age = studentClass.getField("age");
        name.set(student1, "lisi");
        age.set(student1, 18);
        System.out.println(student1);

        Method method = studentClass.getMethod("study", String.class);
        method.invoke(student1, "math");
    }
}