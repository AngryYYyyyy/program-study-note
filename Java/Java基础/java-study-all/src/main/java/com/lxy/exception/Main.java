package com.lxy.exception;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println( 1/ 0);
            System.out.println("Running");
            //即使加了return，也是finally先执行
            //System.exit(0);//终止当前的虚拟机执行，finally不会执行
        } catch (Exception e) {
            throw e;
        }
        finally {
            System.out.println("project is running!!!");//即使异常又被重新抛出，依旧可以执行
        }
    }
}
