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
    @FXML
    private Button clearButton;
    @FXML
    private Button undoButton;
    @FXML
    private CheckBox garlandCheckBox;
    @FXML
    private Button selectButton;

    private ShapeFactory shapeFactory = new ShapeFactory();
    private Group group = new Group(Color.TRANSPARENT, 0);
    private Stack<Shape> undoStack = new Stack<>();
    private List<Shape> garlandShapes = new ArrayList<>();
    private Queue<Shape> shapeQueue = new LinkedList<>();

    private boolean isDrawing = false;
    private Shape currentShape = null;
    private boolean isGarlandMode = false;
    private Timeline garlandTimeline;

    private SelectionArea selectionArea = new SelectionArea();
    private boolean isSelecting = false;

    public void initialize() {
        shapeListView.setItems(FXCollections.observableArrayList(
                "Линия", "Квадрат", "Треугольник", "Круг", "Угол", "Шестиугольник"
        ));

        garlandTimeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> animateGarland())
        );
        garlandTimeline.setCycleCount(Timeline.INDEFINITE);

        garlandCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            isGarlandMode = isNowSelected;
            if (isGarlandMode) {
                garlandTimeline.play();
            } else {
                garlandTimeline.stop();
                redrawCanvas();
            }
        });
    }

    @FXML
    private void onClear() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        group.getShapes().clear();
        undoStack.clear();
        garlandShapes.clear();
        shapeQueue.clear();
    }

    private void animateGarland() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Shape shape : group.getShapes()) {
            if (!garlandShapes.contains(shape)) {
                shape.draw(gc);
            }
        }

        for (Shape shape : garlandShapes) {
            double alpha = Math.random();
            gc.setGlobalAlpha(alpha);
            shape.draw(gc);
        }
        gc.setGlobalAlpha(1.0);
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        if (isSelecting) {
            selectionArea.setStart(event.getX(), event.getY());
        } else {
            isDrawing = true;
            onMouseDragged(event);
        }
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        if (isSelecting) {
            selectionArea.setEnd(event.getX(), event.getY());
            changeColorInSelection(colorPicker.getValue());
            isSelecting = false;
        } else {
            isDrawing = false;
            currentShape = null;
        }
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        if (isDrawing) {
            String shapeName = shapeListView.getSelectionModel().getSelectedItem();
            Color color = colorPicker.getValue();
            double size = Double.parseDouble(sizeInput.getText());
            GraphicsContext gc = canvas.getGraphicsContext2D();

            if (currentShape == null) {
                currentShape = createShapeByName(shapeName, color, size);
            }

            if (currentShape != null) {
                if (shapeName.equals("Круг")) {
                    currentShape.setCenterPosition(event.getX(), event.getY());
                } else {
                    currentShape.setPosition(event.getX(), event.getY());
                }

                if (!isGarlandMode) {
                    currentShape.draw(gc);
                }

                group.addShape(currentShape);
                undoStack.push(currentShape);
                shapeQueue.add(currentShape);

                if (isGarlandMode) {
                    garlandShapes.add(currentShape);
                }

                currentShape = createShapeByName(shapeName, color, size);
            }
        }
    }

    @FXML
    private void onUndo() {
        if (!undoStack.isEmpty()) {
            Shape lastShape = undoStack.pop();
            group.removeShape(lastShape);
            garlandShapes.remove(lastShape);
            redrawCanvas();
        }
    }

    @FXML
    private void onSelectMode(ActionEvent event) {
        isSelecting = true;
    }

    @FXML
    private void onGarlandMode(ActionEvent event) {
        isGarlandMode = garlandCheckBox.isSelected();
        if (isGarlandMode) {
            garlandTimeline.play();
        } else {
            garlandTimeline.stop();
            redrawCanvas();
        }
    }

    private void changeColorInSelection(Color newColor) {
        for (Shape shape : group.getShapes()) {
            if (selectionArea.contains(shape.getX(), shape.getY())) {
                shape.setColor(newColor);
            }
        }
        redrawCanvas();
    }

    private void redrawCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        group.draw(gc);
    }

    private Shape createShapeByName(String shapeName, Color color, double size) {
        switch (shapeName) {
            case "Линия":
                return new Line(color, size);
            case "Квадрат":
                return new Square(color, size);
            case "Треугольник":
                return new Triangle(color, size);
            case "Круг":
                return new Circle(color, size);
            case "Угол":
                return new Angle(color, size);
            case "Шестиугольник":
                return new Hexagon(color, size);
            default:
                return null;
        }
    }
}