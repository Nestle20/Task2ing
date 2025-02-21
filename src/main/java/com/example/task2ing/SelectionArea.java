package com.example.task2ing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SelectionArea {
    private double startX, startY, endX, endY;

    public void setStart(double x, double y) {
        startX = x;
        startY = y;
    }

    public void setEnd(double x, double y) {
        endX = x;
        endY = y;
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        gc.strokeRect(
                Math.min(startX, endX),
                Math.min(startY, endY),
                Math.abs(endX - startX),
                Math.abs(endY - startY)
        );
    }

    public boolean contains(double x, double y) {
        return x >= Math.min(startX, endX) &&
                x <= Math.max(startX, endX) &&
                y >= Math.min(startY, endY) &&
                y <= Math.max(startY, endY);
    }
}