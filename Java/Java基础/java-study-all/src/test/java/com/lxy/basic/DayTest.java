package com.lxy.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {
    @Test
    void test() {
        Day[] values = Day.values();
        for (Day value : values) {
            System.out.println(value);
        }
    }
}