package v3;

import javassist.*;
import javassist.bytecode.AccessFlag;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class JavassistTest {
    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        Loader loader = new Loader(pool);
        CtClass ct = pool.makeClass("zyer");
        CtField field = new CtField(CtClass.intType,"age",ct);
        field.setModifiers(AccessFlag.PUBLIC);
        ct.addField(field);
        CtConstructor constructor = CtNewConstructor.make("public GeneratedClass(int age){this.age=age;}",ct);
        ct.addConstructor(constructor);
        CtMethod method = CtNewMethod.make("public void hello(int age){System.out.println(age);}",ct);
        ct.addMethod(method);
        String cmd = "public static void main(String[] args) {System.out.println(\"my name is zyer\");}";
        CtMethod method1 = CtNewMethod.make(cmd,ct);
        ct.addMethod(method1);
        ct.writeFile("/Users/zyer/Downloads/untitled/out/production/untitled/");
        Class name = loader.loadClass("zyer");
        Constructor constructor1 = name.getDeclaredConstructor(int.class);
        Object obj = constructor1.newInstance(1);
        Method method2 = name.getDeclaredMethod("hello",int.class);
        method2.invoke(obj,123);
        Method method3 = name.getDeclaredMethod("main",String[].class);
        method3.invoke(null,(Object) new String[]{});
    }
}
