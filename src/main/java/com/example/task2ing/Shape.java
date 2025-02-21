package com.example.task2ing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected Color color;
    protected double size;
    protected double x, y;

    public Shape(Color color, double size) {
        this.color = color;
        this.size = size;
    }

    // Устанавливает верхний левый угол фигуры
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Устанавливает центр фигуры
    public void setCenterPosition(double centerX, double centerY) {
        this.x = centerX - size / 2;
        this.y = centerY - size / 2;
    }

    public abstract void draw(GraphicsContext gc);
}