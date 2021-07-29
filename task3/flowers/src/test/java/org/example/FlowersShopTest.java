package org.example;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FlowersShopTest {
    @Test
    void shouldFindFlowerByName() {
        Set<Flower> flowers = new HashSet<>();
        flowers.add(new SpecificFlower("Роза", 100.0));
        FlowersShop flowersShop = new FlowersShop(flowers);
        assertDoesNotThrow(() -> flowersShop.findFlowerByName("Роза"));
        Flower flower = flowersShop.findFlowerByName("Роза");
        assertEquals(flower.getName(), "Роза");
        assertEquals(flower.getPrice(), 100.0);
    }

    @Test
    void shouldFlowerNotFound() {
        Set<Flower> flowers = new HashSet<>();
        flowers.add(new SpecificFlower("Роза", 100.0));
        FlowersShop flowersShop = new FlowersShop(flowers);
        assertThrows(FlowerNotFoundException.class, () -> flowersShop.findFlowerByName("Тюльпан"));
    }

    @Test
    void shouldCheckBouquetPrice() {
        Set<Flower> flowers = new HashSet<>();
        flowers.add(new SpecificFlower("Роза", 100.0));
        FlowersShop flowersShop = new FlowersShop(flowers);
        double bouquetPrice = Bouquet.build()
                .addFlowers(flowersShop.findFlowerByName("Роза"), 2)
                .computeBouquetPrice();
        assertEquals(bouquetPrice, 200.0);
    }
}
