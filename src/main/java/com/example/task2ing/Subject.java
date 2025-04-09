package com.example.task2ing;

public interface Subject {
    void notifyAllObservers();
    void attach(Observer obs);
    void detach(Observer obs);
}