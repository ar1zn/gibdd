package com.gibdd.gidbb.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class NumberGeneratorPerfomanceTest {

    private NumberGenerator generator;
    private static final int NUMBER_OF_TESTS = 100000;

    @BeforeEach
    void setUp() {
        generator = new NumberGeneratorDefault();
    }

    @Test
    void testGetNextPerformance() {
        long startTime = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            generator.getNext();
        }
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // Convert to milliseconds
        System.out.println("getNext() execution time: " + duration + " ms");

        // Here you can set an acceptable threshold for your performance test
        assertTrue(duration < 5000, "Performance of getNext() method is not acceptable");
    }

    @Test
    void testGenerateRandomPerformance() {
        long startTime = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            generator.generateRandom();
        }
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // Convert to milliseconds
        System.out.println("generateRandom() execution time: " + duration + " ms");

        // Here you can set an acceptable threshold for your performance test
        assertTrue(duration < 5000, "Performance of generateRandom() method is not acceptable");
    }
}
