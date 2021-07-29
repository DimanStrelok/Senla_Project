package org.example;

import org.example.flowers.Flower;
import org.example.flowers.SpecificFlower;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Flower> flowers = new HashSet<>();
        flowers.add(new SpecificFlower("Роза", 100.0));
        flowers.add(new SpecificFlower("Хризантема", 200.0));
        flowers.add(new SpecificFlower("Тюльпан", 300.0));
        FlowersShop flowersShop = new FlowersShop(flowers);
        try {
            Bouquet bouquet = Bouquet.build()
                    .addFlowers(flowersShop.findFlowerByName("Роза"), 2)
                    .addFlowers(flowersShop.findFlowerByName("Хризантема"), 2)
                    .addFlowers(flowersShop.findFlowerByName("Тюльпан"), 2);
            double bouquetPrice = bouquet.computeBouquetPrice();
            System.out.println(bouquetPrice);
        } catch (FlowerNotFoundException e) {
            e.printStackTrace();
        }
    }
}
