package com.example.task2ing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Shape {
    protected Color color;
    /**
     * Абстрактный класс, представляющий геометрическую фигуру.
     * Этот класс служит базовым для всех конкретных реализаций фигур.
     */
    protected double size;
    protected double x, y;

    public Shape(Color color, double size) {
        this.color = color;
        this.size = size;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param gc
     */
    public abstract void draw(GraphicsContext gc);
}