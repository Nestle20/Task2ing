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

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(GraphicsContext gc);
}