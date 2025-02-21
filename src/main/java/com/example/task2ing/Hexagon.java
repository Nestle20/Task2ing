package com.example.task2ing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Hexagon extends Shape {
    public Hexagon(Color color, double size) {
        super(color, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        double halfSize = size / 2;
        double[] xPoints = {
                x - halfSize,
                x - halfSize / 2,
                x + halfSize / 2,
                x + halfSize,
                x + halfSize / 2,
                x - halfSize / 2
        };
        double[] yPoints = {
                y,
                y - halfSize,
                y - halfSize,
                y,
                y + halfSize,
                y + halfSize
        };
        gc.fillPolygon(xPoints, yPoints, 6);
    }
}