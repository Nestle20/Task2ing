package com.example.task2ing;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController {
    @FXML
    private TextField timerField;
    @FXML
    private TextField repeatField;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button resetButton;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField textField;
    @FXML
    private Button textStartButton;
    @FXML
    private Button textStopButton;
    @FXML
    private TextField bellField;
    @FXML
    private Button bellStartButton;
    @FXML
    private Button bellStopButton;
    @FXML
    private Button clockStartButton;
    @FXML
    private Button clockStopButton;
    @FXML
    private ImageView clockGif; // ImageView для отображения GIF

    private TimeServer timeServer;
    private ComponentText componentText;
    private ComponentMusic componentMusic;
    private AnimationPlayer animationPlayer;

    @FXML
    public void initialize() {
        timeServer = new TimeServer();
        componentText = new ComponentText(textField);

        // Проверка на пустые строки и нечисловые значения
        int bellDelay = 5; // Значение по умолчанию
        if (!bellField.getText().isEmpty()) {
            try {
                bellDelay = Integer.parseInt(bellField.getText());
            } catch (NumberFormatException e) {
                statusLabel.setText("Ошибка: неверное значение для задержки звонка");
            }
        }
        componentMusic = new ComponentMusic(bellDelay);

        // Загрузка GIF-анимации
        Image clockImage = new Image(getClass().getResourceAsStream("clook.gif"));
        clockGif.setImage(clockImage);

        // Инициализация AnimationPlayer с ImageView
        animationPlayer = new AnimationPlayer(timeServer, clockGif);

        timeServer.attach(componentText);
        timeServer.attach(componentMusic);
        timeServer.attach(animationPlayer);
    }

    @FXML
    public void start() {
        try {
            timeServer.start();
            statusLabel.setText("Таймер активен");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Ошибка при запуске таймера: " + e.getMessage());
        }
    }

    @FXML
    public void stop() {
        timeServer.stop();
        statusLabel.setText("Таймер остановлен");
    }

    @FXML
    public void reset() {
        timeServer.reset();
        statusLabel.setText("Таймер сброшен");
    }

    @FXML
    public void startText() {
        componentText.start();
    }

    @FXML
    public void stopText() {
        componentText.stop();
    }

    @FXML
    public void startBell() {
        try {
            int bellDelay = Integer.parseInt(bellField.getText());
            componentMusic.start(bellDelay);
        } catch (NumberFormatException e) {
            statusLabel.setText("Ошибка: неверное значение для задержки звонка");
        }
    }

    @FXML
    public void stopBell() {
        componentMusic.stop();
    }

    @FXML
    public void startClock() {
        animationPlayer.startAnimationEvery(20); // По умолчанию 20 секунд
    }

    @FXML
    public void stopClock() {
        animationPlayer.stopAnimation();
    }
}