package com.gibdd.gidbb.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.gibdd.gidbb.generator.NumberGeneratorDefault.regex;
import static org.junit.jupiter.api.Assertions.*;

public class NumberGeneratorDefaultTest {

    private NumberGeneratorDefault numberGenerator;

    @BeforeEach
    public void setUp() {
        numberGenerator = new NumberGeneratorDefault();
        numberGenerator.init();
    }

    @DisplayName("Проверка одинаковы ли рандомные числа")
    @Test
    public void testGenerateRandom() {
        String random1 = numberGenerator.generateRandom();
        String random2 = numberGenerator.generateRandom();

        assertNotNull(random1);
        assertNotNull(random2);
        assertNotEquals(random1, random2);

        assertTrue(random1.matches(regex));
        assertTrue(random2.matches(regex));
    }

    @DisplayName("Проверка одинаковы ли последовательные числа")
    @Test
    public void testGetNext() {
        String initial = "C399BA 116 RUS";
        String next1 = numberGenerator.getNext();
        String next2 = numberGenerator.getNext();

        assertNotNull(next1);
        assertNotNull(next2);
        assertNotEquals(initial, next1);

        assertTrue(next1.matches(regex));
        assertTrue(next2.matches(regex));
    }
}
