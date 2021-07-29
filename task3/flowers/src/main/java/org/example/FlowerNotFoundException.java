package org.example;

public class FlowerNotFoundException extends RuntimeException {
    FlowerNotFoundException(String name) {
        super("Цветок " + name + " отсутствует в магазине.");
    }
}
