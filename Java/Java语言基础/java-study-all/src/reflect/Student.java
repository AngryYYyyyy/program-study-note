package reflect;

public class Student {
    public String name;
    public Integer age;
    public Student() {}
    public Student(String name, Integer age) {this.name = name; this.age = age;}
    public void study(String book){
        System.out.println(age+" "+name+
                " studying :"+book);
    }
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
