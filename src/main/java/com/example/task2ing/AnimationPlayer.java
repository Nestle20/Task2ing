package com.example.task2ing;

import javafx.scene.image.ImageView;

public class AnimationPlayer implements Observer {
    private final Subject subject;
    private ImageView imageView;
    private int start;
    private int interval;

    public AnimationPlayer(Subject subject, ImageView imageView) {
        this.subject = subject;
        this.imageView = imageView;
        this.interval = 20; // Устанавливаем значение по умолчанию
    }

    public void startAnimationEvery(int interval) {
        this.interval = interval;
    }

    @Override
    public void update(Subject st) {
        TimeServer timeServer = (TimeServer) st;
        int time = timeServer.getTimeState();
        if (time % interval == 0) {
            animateGif();
        }
    }

    private void animateGif() {
        imageView.setVisible(true);
    }

    public void stopAnimation() {
        imageView.setVisible(false);
    }
}