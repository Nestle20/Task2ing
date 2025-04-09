package com.example.task2ing;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConcreteAggregate implements Aggregate {
    private final List<File> imageFiles;

    public ConcreteAggregate(String directoryPath) {
        this.imageFiles = new ArrayList<>();
        loadImages(new File(directoryPath));
        if (imageFiles.isEmpty()) {
            throw new RuntimeException("В каталоге не найдено изображений.");
        }
    }

    private void loadImages(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    loadImages(file);
                } else if (file.getName().toLowerCase().matches(".*\\.(png|jpg)")) {
                    imageFiles.add(file);
                }
            }
        }
    }

    public Image getFirstImage() {
        return new Image(imageFiles.get(0).toURI().toString());
    }

    public Image getLastImage() {
        return new Image(imageFiles.get(imageFiles.size() - 1).toURI().toString());
    }

    public int size() {
        return imageFiles.size();
    }

    @Override
    public Iterator getIterator() {
        return new ImageIterator();
    }

    private class ImageIterator implements Iterator {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return !imageFiles.isEmpty();
        }

        @Override
        public Object next() {
            return new Image(imageFiles.get(index++ % imageFiles.size()).toURI().toString());
        }

        @Override
        public Object preview() {
            index = (index - 1 + imageFiles.size()) % imageFiles.size();
            return new Image(imageFiles.get(index).toURI().toString());
        }
    }
}

