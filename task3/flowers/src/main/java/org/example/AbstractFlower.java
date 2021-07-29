package org.example;

import java.util.Objects;

public abstract class AbstractFlower implements Flower {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flower)) return false;
        Flower flower = (Flower) o;
        return Double.compare(flower.getPrice(), getPrice()) == 0 && getName().equals(flower.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice());
    }
}
