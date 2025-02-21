package com.example.task2ing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Shape {
    public Triangle(Color color, double size) {
        super(color, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        double[] xPoints = {x, x + size, x + size / 2};
        double[] yPoints = {y, y, y + size};
        gc.fillPolygon(xPoints, yPoints, 3);
    }
}