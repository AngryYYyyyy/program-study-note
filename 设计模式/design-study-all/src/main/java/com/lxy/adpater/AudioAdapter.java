package com.lxy.adpater;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 17:27
 * @Description：
 */
public class AudioAdapter extends Mp3Player implements WavPlayer{
    @Override
    public void playWav(String fileName) {
        System.out.println("Converting wav to mp3...");
        playMp3(fileName);
    }
}
