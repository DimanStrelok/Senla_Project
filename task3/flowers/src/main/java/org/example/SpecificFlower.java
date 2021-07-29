package org.example;

public class SpecificFlower extends AbstractFlower {
    private final String name;
    private final double price;

    public SpecificFlower(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
