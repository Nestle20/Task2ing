package com.example.task2ing;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Stack;

public class HelloController {
    @FXML
    private Canvas canvas;
    @FXML
    private ListView<String> shapeListView;
    @FXML
    private TextField sizeInput;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button clearButton;
    @FXML
    private Button undoButton;
    @FXML
    private CheckBox garlandCheckBox; // Чекбокс для режима "Гирлянда"

    private ShapeFactory shapeFactory = new ShapeFactory();
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Stack<Shape> undoStack = new Stack<>();
    private ArrayList<Shape> garlandShapes = new ArrayList<>(); // Фигуры, нарисованные после активации гирлянды

    private boolean isDrawing = false;
    private Shape currentShape = null;//ЗДЕСЬ БУДЕТ ПРИСВАИВАНИЕ!!!!
    private boolean isGarlandMode = false;
    private Timeline garlandTimeline;

    // Инициализация ListView
    public void initialize() {
        shapeListView.setItems(FXCollections.observableArrayList(
                "Линия", "Квадрат", "Треугольник", "Круг", "Угол", "Шестиугольник"
        ));

        // Инициализация анимации для гирлянды
        garlandTimeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> animateGarland())
        );
        garlandTimeline.setCycleCount(Timeline.INDEFINITE);

        // Подключаем чекбокс к режиму "Гирлянда"
        garlandCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            isGarlandMode = isNowSelected;
            if (isGarlandMode) {
                garlandTimeline.play();
            } else {
                garlandTimeline.stop();
                redrawCanvas(); // Восстанавливаем фигуры в нормальном состоянии
            }
        });
    }

    // Метод для очистки холста
    public void onClear() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        shapes.clear();
        undoStack.clear();
        garlandShapes.clear(); // Очищаем фигуры, нарисованные в режиме гирлянды
    }

    // Анимация гирлянды
    private void animateGarland() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Перерисовываем фигуры, нарисованные до активации гирлянды
        for (Shape shape : shapes) {
            if (!garlandShapes.contains(shape)) {
                shape.draw(gc);
            }
        }

        // Анимируем фигуры, нарисованные в режиме гирлянды
        for (Shape shape : garlandShapes) {
            double alpha = Math.random(); // Случайная прозрачность
            gc.setGlobalAlpha(alpha);
            shape.draw(gc);
        }
        gc.setGlobalAlpha(1.0); // Возвращаем прозрачность к норме
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
            case "Шестиугольник":
                return shapeFactory.createShape("Hexagon", color, size);
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
                // Устанавливаем позицию фигуры
                if (shapeName.equals("Круг")) {
                    // Для круга центр фигуры - место нажатия
                    currentShape.setCenterPosition(event.getX(), event.getY());
                } else {
                    // Для остальных фигур верхний левый угол - место нажатия
                    currentShape.setPosition(event.getX(), event.getY());
                }

                if (!isGarlandMode) {
                    currentShape.draw(gc);
                }

                // Добавляем фигуру в список и стек для отмены
                shapes.add(currentShape);
                undoStack.push(currentShape);

                // Если режим гирлянды активен, добавляем фигуру в список гирлянды
                if (isGarlandMode) {
                    garlandShapes.add(currentShape);
                }

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
            garlandShapes.remove(lastShape); // Удаляем фигуру из списка гирлянды
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

    public void onGarlandMode(ActionEvent actionEvent) {
    }
}