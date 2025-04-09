package com.example.task2ing;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class ComponentMusic implements Observer {
    private int delay;
    private MediaPlayer mediaPlayer;
    private int lastPlayedTime = 0;
    private boolean isActive = false;

    public ComponentMusic(int delay) {
        this.delay = delay;
        try {
            File file = new File("src/main/resources/com/example/l7/kino.mp3");
            if (!file.exists()) {
                System.err.println("Ошибка: файл kino.mp3 не найден!");
                return;
            }
            Media sound = new Media(file.toURI().toString());
            this.mediaPlayer = new MediaPlayer(sound);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка загрузки медиафайла: " + e.getMessage());
        }
    }

    @Override
    public void update(Subject st) {
        if (isActive && mediaPlayer != null) {
            TimeServer timeServer = (TimeServer) st;
            if (timeServer.getTimeState() - lastPlayedTime >= delay) {
                mediaPlayer.play();
                lastPlayedTime = timeServer.getTimeState();
            }
        }
    }

    public void start(int delay) {
        this.delay = delay;
        isActive = true;
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        isActive = false;
    }
}