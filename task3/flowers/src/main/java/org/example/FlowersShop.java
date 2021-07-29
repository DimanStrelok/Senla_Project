package org.example;

import java.util.*;

public class FlowersShop {
    private final Set<Flower> flowers;

    public FlowersShop(Set<Flower> flowers) {
        this.flowers = flowers;
    }

    public Flower findFlowerByName(String name) {
        return flowers.stream().filter(flower -> flower.name().equals(name)).findFirst().orElseThrow(() -> new FlowerNotFoundException(name));
    }
}
