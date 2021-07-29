package org.example;

import java.util.HashMap;
import java.util.Map;

public class Bouquet {
    private final Map<Flower, Integer> flowers = new HashMap<>();

    public static Bouquet build() {
        return new Bouquet();
    }

    public Bouquet addFlowers(Flower flower, int count) {
        if (flowers.containsKey(flower)) {
            int newCount = flowers.get(flower) + count;
            flowers.put(flower, newCount);
        } else {
            flowers.put(flower, count);
        }
        return this;
    }

    public double computeBouquetPrice() {
        return flowers.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .reduce(Double::sum)
                .orElse(0.0);
    }
}
