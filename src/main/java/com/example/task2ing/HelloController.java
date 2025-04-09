package com.example.task2ing;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import java.io.File;

    public class HelloController {

        @FXML
        private ImageView screen;

        @FXML
        private Pane indicatorPane;

        @FXML
        private Button startButton, stopButton, chooseDirectoryButton, previousButton, nextButton, firstButton, lastButton;

        private static final int SLIDESHOW_DELAY = 1000; // 1 секунда

        private ConcreteAggregate slides;
        private Iterator iter;
        private Timeline timeline;
        private boolean isPlaying = false;

        private Builder indicatorBuilder;
        private int currentSlideIndex = 0;
        private int totalSlides;

        public void initialize() {
            setButtonGraphics();
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            indicatorBuilder = new ConcreteBuilder();
        }

        private void setButtonGraphics() {
            setButtonGraphic(firstButton, "⏪");
            setButtonGraphic(previousButton, "⏪");
            setButtonGraphic(startButton, "▶");
            setButtonGraphic(stopButton, "⏹");
            setButtonGraphic(nextButton, "⏩");
            setButtonGraphic(lastButton, "⏩");
        }

        private void setButtonGraphic(Button button, String text) {
            button.setText(text); // Устанавливаем текстовый символ
            button.setStyle("-fx-font-size: 20; -fx-background-color: transparent;"); // Настраиваем стиль кнопки
        }

        @FXML
        public void startSlideshow() {if (slides != null && !isPlaying) {
            isPlaying = true;
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(SLIDESHOW_DELAY), e -> nextImage()));
            timeline.play();
        }
        }

        @FXML
        public void stopSlideshow() {
            if (isPlaying) {
                isPlaying = false;
                timeline.stop();
            }
        }

        @FXML
        public void chooseDirectory() {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Выберите папку с изображениями");

            // Открываем диалог выбора папки
            File selectedDirectory = directoryChooser.showDialog(screen.getScene().getWindow());

            if (selectedDirectory != null && selectedDirectory.isDirectory()) {
                slides = new ConcreteAggregate(selectedDirectory.getAbsolutePath());
                iter = slides.getIterator();
                totalSlides = slides.size();

                // Загружаем первое изображение, если оно есть
                if (iter.hasNext()) {
                    currentSlideIndex = 0;
                    updateSlide((Image) iter.next());
                    // Создаем индикатор
                    indicatorBuilder.setIndicator(totalSlides, currentSlideIndex + 1);
                    indicatorBuilder.linePaint(currentSlideIndex + 1);
                    Indicator indicator = indicatorBuilder.build();
                    indicator.show(indicatorPane);
                } else {
                    System.out.println("В выбранной папке нет изображений.");
                }
            }
        }
        private void updateIndicator() {
            if (slides != null) {
                // Очищаем старый индикатор
                indicatorPane.getChildren().clear();

                // Создаем новый индикатор
                indicatorBuilder.reset(); // Сбрасываем состояние билдера
                indicatorBuilder.setIndicator(slides.size(), currentSlideIndex + 1); // Устанавливаем индикатор для текущего слайда
                indicatorBuilder.linePaint(currentSlideIndex + 1); // Обновляем индикатор с новым значением

                Indicator indicator = indicatorBuilder.build(); // Строим индикатор
                indicator.show(indicatorPane); // Отображаем индикатор на панели
            }
        }


        @FXML
        public void nextImage() {
            if (iter != null && iter.hasNext()) {
                if (currentSlideIndex < totalSlides - 1) {
                    currentSlideIndex++;
                    updateSlide((Image) iter.next());
                    //indicatorBuilder.linePaint(currentSlideIndex + 1);
                    updateIndicator(); // Обновляем индикатор
                }
            }
        }

        @FXML
        public void previousImage() {
            if (iter != null) {
                if (currentSlideIndex > 0) {
                    currentSlideIndex = Math.max(0, currentSlideIndex - 1);
                    updateSlide((Image) iter.preview());
                    //indicatorBuilder.linePaint(currentSlideIndex + 1);
                    updateIndicator(); // Обновляем индикатор
                }
            }
        }

        @FXML
        public void goToFirstImage() {
            if (slides != null) {
                currentSlideIndex = 0;
            /*updateSlide(slides.getFirstImage());
            indicatorBuilder.linePaint(currentSlideIndex + 1);*/
                iter = slides.getIterator();
                updateSlide(slides.getFirstImage());
                updateIndicator();
            }
        }

        @FXML
        public void goToLastImage() {
            if (slides != null) {
                currentSlideIndex = slides.size() - 1;
            /*updateSlide(slides.getLastImage());
            indicatorBuilder.linePaint(currentSlideIndex + 1);*/
                iter = slides.getIterator();

                for (int i = 0; i < currentSlideIndex; i++) {
                    iter.next();
                }
                updateSlide(slides.getLastImage());
                updateIndicator();
            }
        }

        private void updateSlide(Image image) {
            screen.setImage(image);
        }

    }


