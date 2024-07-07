package com.gibdd.gidbb.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class NumberGeneratorPerfomanceTest {

    private NumberGeneratorDefault numberGenerator;
    private static final int NUMBER_OF_TESTS = 4000000;

    @BeforeEach
    void setUp() {
        numberGenerator = new NumberGeneratorDefault();
        numberGenerator.init();
    }

    @DisplayName("Скорость getNext")
    @Test
    void testGetNextPerformance() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            numberGenerator.getNext();
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("getNext() execution time: " + duration + " ms");

        assertTrue(duration < 5000);
    }

    @DisplayName("Скорость generateRandom")
    @Test
    void testGenerateRandomPerformance() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            try {
                numberGenerator.generateRandom();
            } catch (Exception e) {
                System.out.println("Все возможные номера уже созданы");
                e.printStackTrace();
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("generateRandom() execution time: " + duration + " ms");

        assertTrue(duration < 6000);
    }

    @DisplayName("Сравнение листов")
    @Test
    void testGenerateRandomPerformanceForList() {
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            numberGenerator.generateRandom();
        }
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("generateRandom() for AL execution time: " + duration + " ms");
        assertTrue(duration < 5000);

        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            numberGenerator.generateRandom();
        }
        endTime = System.nanoTime();
        duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("generateRandom() for LL execution time: " + duration + " ms");

        assertTrue(duration < 5000);
    }
}
