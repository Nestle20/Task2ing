package com.example.task2ing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Group extends Shape {
    private List<Shape> shapes = new ArrayList<>();

    // Конструктор для Group
    public Group(Color color, double size) {
        super(color, size);
    }

    // Добавление фигуры в группу
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    // Удаление фигуры из группы
    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    // Получение списка фигур в группе
    public List<Shape> getShapes() {
        return shapes;
    }

    // Отрисовка всех фигур в группе
    @Override
    public void draw(GraphicsContext gc) {
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }
}