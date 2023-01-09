package com.devthunder.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    private Clip clip;

    public static final Sound hurtEffect = new Sound("/hurt.wav");

    private Sound(String name) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(name));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch(Throwable e) {}
    }

    public void play() {
        try {
            new Thread() {
                public void run() {
                    System.out.println("Played");
                    clip.start();
                }
            }.start();
        }catch(Throwable e) {}
    }

    public void loop(int times) {
        try {
            new Thread() {
                public void run() {
                    clip.loop(times);
                }
            }.start();
        }catch(Throwable e) {}
    }
}
