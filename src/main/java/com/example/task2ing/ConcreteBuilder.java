package com.example.task2ing;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ConcreteBuilder implements Builder {

    private Indicator indicator = new Indicator();
    private int totalSlides;

    @Override
    public void reset() {
        indicator.clear();
    }

    // Устанавливаем количество слайдов
    @Override
    public void setIndicator(int totalSlides, int currentSlide) {
        this.totalSlides = totalSlides;

        // Создаем полосу индикатора
        Line line = new Line(200, 10, 600, 10);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);

        // Добавляем текстовые метки для начального и конечного значения
        Text startText = new Text(200, -10,"1");
        Text endText = new Text(590, -10, String.valueOf(totalSlides));

        // Собираем элементы на панель
        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(line, startText, endText);
        indicator.add(pane);

        // Добавляем указатель
        linePaint(currentSlide);
    }

    // Обновляем указатель и номер слайда
    @Override
    public void linePaint(int currentSlide) {
        AnchorPane pane = new AnchorPane();

        if (currentSlide > totalSlides) {
            return;//прерываем, если слайд больше максимального
        }

        // Вычисляем положение указателя на линии
        double fixedWidth = 400;
        double position = fixedWidth * (currentSlide - 1) / (totalSlides - 1);

        position = Math.min(position, fixedWidth);//ограничиваем указатель

        // Создаем указатель
        Rectangle marker = new Rectangle(5, 15, Color.DARKGRAY);
        marker.setX(position + 200); // Центрируем указатель относительно линии
        marker.setY(0);

        // Добавляем текст с номером текущего слайда
        Text slideText = new Text(position + 200, 45, "Слайд " + currentSlide);
        pane.getChildren().addAll(marker, slideText);
        indicator.add(pane);
    }

    @Override
    public Indicator build() {
        return indicator;
    }
}
