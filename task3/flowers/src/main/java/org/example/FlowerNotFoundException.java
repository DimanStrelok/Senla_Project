package org.example;

public class FlowerNotFoundException extends RuntimeException {
    public FlowerNotFoundException(String name) {
        super("Цветок " + name + " отсутствует в магазине.");
    }
}
