package com.lxy.oop;

public class Student extends Person {
    int stuId;
    //除了上述属性，还有继承自父类的属性
    public Student(){
    }
    public Student(int stuId) {
        this.stuId = stuId;
    }

    public Student(int age, String name, int stuId) {
        super(age, name);//类比this，super表示继承父类的构造器
        //其实构造器的第一行都有super() 来调用父类的空构造器，一般省略不写
        this.stuId = stuId;
    }

    public void study(){
        System.out.println("student study");
    }
    //除了上述方法，还有继承自父类的方法

    //eat方法重写
    public void eat() {
        System.out.println("age:"+age+"\n"+"name:"+name +"\n"+"student eat");
    }

    //对toString的重写，后续会讲解
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
