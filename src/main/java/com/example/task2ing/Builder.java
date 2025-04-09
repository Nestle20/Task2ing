package com.example.task2ing;

public interface Builder {
    void reset(); // Сбрасывает текущее состояние
    void setIndicator(int totalSlides, int currentSlide); // Устанавливает параметры индикатора
    void linePaint(int currentSlide);
    Indicator build(); // Создает объект индикатора
}
