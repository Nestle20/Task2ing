package com.example.task2ing;

/*import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Indicator {
    private final Label label;

    public Indicator(Label label) {
        this.label = label;
    }

    // Метод для отображения индикатора на указанной панели
    public void show(Pane pane) {
        pane.getChildren().clear(); // Очищаем панель от предыдущего содержимого
        pane.getChildren().add(label); // Добавляем индикатор (Label)
    }
}*/

import javafx.scene.layout.Pane;

public class Indicator {
    private Pane panel = new Pane();

    public void add(Pane pane) {
        panel.getChildren().add(pane);
    }

    public void show(Pane pane) {
        pane.getChildren().add(panel);
    }

    public void clear() {
        panel.getChildren().clear();
    }

}

