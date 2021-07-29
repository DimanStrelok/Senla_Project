package org.example;

import org.example.flowers.Flower;

import java.util.HashMap;
import java.util.Map;

public class Bouquet {
    private final Map<Flower, Integer> flowers = new HashMap<>();

    public Bouquet addFlowers(Flower flower, int count) {
        if (flowers.containsKey(flower)) {
            int new_count = flowers.get(flower) + count;
            flowers.put(flower, new_count);
        } else {
            flowers.put(flower, count);
        }
        return this;
    }

    public double computeBouquetPrice() {
        return flowers.entrySet().stream().mapToDouble(entry -> {
            double flower_price = entry.getKey().price();
            int flowers_count = entry.getValue();
            return flower_price * flowers_count;
        }).reduce(Double::sum).orElse(0.0);
    }

    public static Bouquet build() {
        return new Bouquet();
    }
}
