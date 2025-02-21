package com.example.task2ing;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.*;

public class HelloController {
    @FXML
    private Canvas canvas;
    @FXML
    private ListView<String> shapeListView;
    @FXML
    private TextField sizeInput;
    @FXML
    private ColorPicker colorPicker;

    private ShapeFactory shapeFactory = new ShapeFactory();
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Stack<Shape> undoStack = new Stack<>();
    private PriorityQueue<String> shapeQueue = new PriorityQueue<>();
    private Map<String, Integer> shapeCountMap = new HashMap<>();

    private boolean isDrawing = false;
    private Shape currentShape = null;

    // Инициализация ListView
    public void initialize() {
        shapeListView.setItems(FXCollections.observableArrayList(
                "Линия", "Квадрат", "Треугольник", "Круг", "Угол", "Пятиугольник"
        ));
    }

    // Метод для очистки холста
    public void onClear() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        shapes.clear();
        undoStack.clear();
        shapeQueue.clear();
        shapeCountMap.clear();
    }

    // Создаёт фигуру на основе выбранного названия
    private Shape createShapeByName(String shapeName, Color color, double size) {
        switch (shapeName) {
            case "Линия":
                return shapeFactory.createShape("Line", color, size);
            case "Квадрат":
                return shapeFactory.createShape("Square", color, size);
            case "Треугольник":
                return shapeFactory.createShape("Triangle", color, size);
            case "Круг":
                return shapeFactory.createShape("Circle", color, size);
            case "Угол":
                return shapeFactory.createShape("Angle", color, size);
            case "Пятиугольник":
                return shapeFactory.createShape("Pentagon", color, size);
            default:
                return null;
        }
    }

    // Показывает уведомление об ошибке
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Обработчик для нажатия мыши
    @FXML
    private void onMousePressed(MouseEvent event) {
        isDrawing = true;
        onMouseDragged(event);
    }

    // Обработчик для отпускания мыши
    @FXML
    private void onMouseReleased(MouseEvent event) {
        isDrawing = false;
        currentShape = null;
    }

    // Обработчик для движения мыши при зажатой клавише
    @FXML
    private void onMouseDragged(MouseEvent event) {
        if (isDrawing) {
            String shapeName = shapeListView.getSelectionModel().getSelectedItem(); // Получаем выбранное название фигуры
            Color color = colorPicker.getValue(); // Получаем цвет
            double size = Double.parseDouble(sizeInput.getText()); // Получаем размер фигуры
            GraphicsContext gc = canvas.getGraphicsContext2D();

            if (currentShape == null) {
                currentShape = createShapeByName(shapeName, color, size);
            }

            if (currentShape != null) {
                // Устанавливаем позицию фигуры на место курсора
                currentShape.setPosition(event.getX(), event.getY());
                currentShape.draw(gc);
                // Добавляем фигуру в список и стек для отмены
                shapes.add(currentShape);
                undoStack.push(currentShape);

                // Обновляем статистику
                shapeQueue.add(shapeName);
                shapeCountMap.put(shapeName, shapeCountMap.getOrDefault(shapeName, 0) + 1);

                // Создаем новую фигуру для следующего рисования
                currentShape = createShapeByName(shapeName, color, size);
            } else {
                showAlert("Ошибка", "Неверное название фигуры.");
            }
        }
    }

    // Метод для отмены последнего действия
    public void onUndo() {
        if (!undoStack.isEmpty()) {
            Shape lastShape = undoStack.pop();
            shapes.remove(lastShape);
            redrawCanvas();
        }
    }

    // Перерисовываем холст с учетом удаленных фигур
    private void redrawCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }
}