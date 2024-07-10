//package com.example.jvmcase.loader;
//
//
//import java.io.*;
//
//
//public class MyClassLoader extends ClassLoader {
//
//    private String root;
//
//    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        byte[] classData = loadClassData(name);
//        if (classData == null) {
//            throw new ClassNotFoundException();
//        } else {
//            //此方法负责将二进制的字节码转换为Class对象
//            return defineClass(name, classData, 0, classData.length);
//        }
//    }
//
//    private byte[] loadClassData(String className) {
//        String fileName = root +
//                File.separatorChar + className.replace('.', File.separatorChar) + ".class";
//        try {
//            InputStream ins = new FileInputStream(fileName);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            int bufferSize = 1024;
//
//            byte[] buffer = new byte[bufferSize];
//
//            int length = 0;
//            while ((length = ins.read(buffer)) != -1) {
//                baos.write(buffer, 0, length);
//            }
//            return baos.toByteArray();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//
//    public String getRoot() {
//        return root;
//
//    }
//
//
//    public void setRoot(String root) {
//
//        this.root = root;
//    }
//
//
//    public static void main(String[] args) {
//        MyClassLoader classLoader = new MyClassLoader();
////        classLoader.setRoot("D:\\codes\\jvm-case\\src\\main\\java");
//        classLoader.setRoot("D:\\");
//
//
//        Class<?> testClass = null;
//        try {
////            testClass = classLoader.loadClass("com.example.jvmcase.basic.Test");
//            testClass = classLoader.loadClass("Test");
//            System.out.println(testClass);
//            Object object = testClass.newInstance();
//
//            System.out.println(object.getClass().getClassLoader());
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//
//        }
//    }
//}
