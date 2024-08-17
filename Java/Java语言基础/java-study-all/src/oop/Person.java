package oop;

public class Person {
    int age;
    String name;

    public Person() {
    }
    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public void sleep() {
        System.out.println("age:"+age+"\n"+"name:"+name +"\n"+"person sleep");
    }
    public void eat() {
        System.out.println("age:"+age+"\n"+"name:"+name +"\n"+"person eat");
    }
}
