package com.example.task2ing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Angle extends Shape {
    public Angle(Color color, double size) {
        super(color, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(size);
        gc.strokeLine(x - size / 2, y, x + size / 2, y);
        gc.strokeLine(x + size / 2, y, x + size / 2, y + size / 2);
    }
}