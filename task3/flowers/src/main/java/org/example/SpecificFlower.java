package org.example;

public class SpecificFlower extends AbstractFlower {
    private final String name;
    private final double price;

    public SpecificFlower(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public double price() {
        return price;
    }
}
