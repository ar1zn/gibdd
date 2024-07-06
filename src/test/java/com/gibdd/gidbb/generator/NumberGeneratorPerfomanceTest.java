package com.gibdd.gidbb.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class NumberGeneratorPerfomanceTest {

    private NumberGenerator generator;
    private static final int NUMBER_OF_TESTS = 40000000;

    @BeforeEach
    void setUp() {
        generator = new NumberGeneratorDefault();
    }

    @DisplayName("Скорость getNext")
    @Test
    void testGetNextPerformance() {
        long startTime = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            generator.getNext();
        }
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("getNext() execution time: " + duration + " ms");

        assertTrue(duration < 5000);
    }

    @DisplayName("Скорость generateRandom")
    @Test
    void testGenerateRandomPerformance() {
        long startTime = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            try {
                generator.generateRandom();
            } catch (Exception e) {
                System.out.println("Все возможные номера уже созданы");
                e.printStackTrace();
                break;
            }
        }
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("generateRandom() execution time: " + duration + " ms");

        assertTrue(duration < 5000);
    }
}
