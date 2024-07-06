package com.lxy.basic;

public enum Day {
    // 枚举常量
    SUNDAY(0, "星期天"),
    MONDAY(1, "星期一"),
    TUESDAY(2, "星期二"),
    WEDNESDAY(3, "星期三"),
    THURSDAY(4, "星期四"),
    FRIDAY(5, "星期五"),
    SATURDAY(6, "星期六");

    // 字段
    private final int id;
    private final String week;

    // 构造函数
    Day(int id, String week) {
        this.id = id;
        this.week = week;
    }

    // 获取id的方法
    public int getId() {
        return id;
    }

    // 获取week的方法
    public String getWeek() {
        return week;
    }

    public static void main(String[] args) {
        Day friday = Day.FRIDAY;
        Day sunday = Day.SUNDAY;

        System.out.println("Friday: " + friday.getId() + ", " + friday.getWeek());
        System.out.println("Sunday: " + sunday.getId() + ", " + sunday.getWeek());
    }
}
