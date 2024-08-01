package com.lxy.adpater;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 17:29
 * @Description：
 */
public class AudioAdapterTest {
    @Test
    public void test() {
        WavPlayer wavPlayer = new AudioAdapter();
        wavPlayer.playWav("sample.wav");
    }
}