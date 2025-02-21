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
        double halfSize = size / 2;
        double[] xPoints = {
                x - halfSize, // Левый угол
                x + halfSize, // Правый угол
                x            // Вершина (центр)
        };
        double[] yPoints = {
                y + halfSize, // Левый и правый углы
                y + halfSize, // Левый и правый углы
                y - halfSize  // Вершина
        };
        gc.fillPolygon(xPoints, yPoints, 3);
    }
}