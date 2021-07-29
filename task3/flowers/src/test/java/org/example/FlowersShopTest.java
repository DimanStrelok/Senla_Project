package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FlowersShopTest {
    FlowersShop flowersShop;

    @BeforeEach
    void init() {
        Set<Flower> flowers = new HashSet<>();
        flowers.add(new SpecificFlower("Роза", 100.0));
        flowersShop = new FlowersShop(flowers);
    }

    @Test
    void shouldFindFlowerByName() {
        assertDoesNotThrow(() -> flowersShop.findFlowerByName("Роза"));
        Flower flower = flowersShop.findFlowerByName("Роза");
        assertEquals(flower.getName(), "Роза");
        assertEquals(flower.getPrice(), 100.0);
    }

    @Test
    void shouldFlowerNotFound() {
        assertThrows(FlowerNotFoundException.class, () -> flowersShop.findFlowerByName("Тюльпан"));
    }

    @Test
    void shouldCheckBouquetPrice() {
        double bouquetPrice = Bouquet.build()
                .addFlowers(flowersShop.findFlowerByName("Роза"), 2)
                .computeBouquetPrice();
        assertEquals(bouquetPrice, 200.0);
    }
}
