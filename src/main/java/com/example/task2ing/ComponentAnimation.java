package com.example.task2ing;

import javafx.scene.image.ImageView;

public class ComponentAnimation implements Observer {
    private ImageView imageView;
    private int period = 20; // По умолчанию 20 секунд
    private int lastAnimatedTime = 0;
    private boolean isActive = false;

    public ComponentAnimation(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void update(Subject st) {
        if (isActive) {
            TimeServer timeServer = (TimeServer) st;
            if (timeServer.getTimeState() - lastAnimatedTime >= period) {
                animate();
                lastAnimatedTime = timeServer.getTimeState();
            }
        }
    }

    private void animate() {
        imageView.setVisible(true);
    }

    public void start(int period) {
        this.period = period;
        isActive = true;
    }

    public void stop() {
        imageView.setVisible(false);
        isActive = false;
    }
}