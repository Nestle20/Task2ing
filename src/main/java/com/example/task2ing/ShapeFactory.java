package com.example.task2ing;

import javafx.scene.paint.Color;

public class ShapeFactory {
    public Shape createShape(String type, Color color, double size) {
        switch (type) {
            case "Line":
                return new Line(color, size);
            case "Square":
                return new Square(color, size);
            case "Circle":
                return new Circle(color, size);
            case "Angle":
                return new Angle(color, size);
            case "Triangle":
                return new Triangle(color, size);
            case "Rectangle":
                return new Rectangle(color, size, size, size); // Здесь можно изменить ширину и высоту
            default:
                return null;
        }
    }
}