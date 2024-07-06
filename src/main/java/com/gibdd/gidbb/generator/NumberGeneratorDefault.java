package com.gibdd.gidbb.generator;

import java.util.*;

public class NumberGeneratorDefault implements NumberGenerator {
    public static String regex = "[АЕТОРНУКХСВМ]\\d{3}[АЕТОРНУКХСВМ]{2}116 RUS";
    private static final char[] LETTERS = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М'};
    private static final String REGION = "116 RUS";
    private final Set<String> generatedNums = new HashSet<>();
    private final Set<String> savedNums = new HashSet<>();
    private String lastGeneratedNum;

    @Override
    public String getNext() {
        if (generatedNums.isEmpty()) {
            lastGeneratedNum = generateRandom();
        } else {
            lastGeneratedNum = incrementNumber(lastGeneratedNum.substring(0, 6)) + REGION;
        }
        generatedNums.add(lastGeneratedNum);
        return lastGeneratedNum;
    }

    @Override
    public String generateRandom() {
        String newNum;
        do {
            newNum = generateRandomCharacter(LETTERS) +
                    new Random().nextInt(10) +
                    new Random().nextInt(10) +
                    new Random().nextInt(10) +
                    generateRandomCharacter(LETTERS) +
                    generateRandomCharacter(LETTERS) +
                    REGION;
        } while (generatedNums.contains(newNum));
        generatedNums.add(newNum);
        lastGeneratedNum = newNum;
        return newNum;
    }

    private String generateRandomCharacter(char[] characters) {
        Random random = new Random();
        return String.valueOf(characters[random.nextInt(characters.length)]);
    }

    private String incrementNumber(String number) {
        char[] chars = number.toCharArray();
        int num = Integer.parseInt(number.substring(1, 4));

        for (int i = chars.length - 1; i >= 0; i--) {
            char currentChar = chars[i];
            if (Character.isDigit(currentChar)) {
                if (currentChar == '9') {
                    chars[i] = '0';
                } else {
                    chars[i]++;
                    break;
                }
            } else if (Character.isLetter(currentChar)) {
                chars[i] = nextLetter(currentChar);
                if (i == 0 || !Character.isDigit(chars[i - 1])) {
                    break;
                }
            }
        }

        return new String(chars);
    }

    private char nextLetter(char c) {
        for (int i = 0; i < LETTERS.length - 1; i++) {
            if (LETTERS[i] == c) {
                return LETTERS[i + 1];
            }
        }
        return LETTERS[0];
    }

    public String getLastNum() {
        if (lastGeneratedNum == null) {
            throw new RuntimeException("Нечего сохранять");
        }
        if (savedNums.contains(lastGeneratedNum)) {
            throw new RuntimeException("Такой номер уже есть");
        }
        if (!lastGeneratedNum.matches(regex)) {
            throw new IllegalArgumentException("Неверный формат данных");
        }
        savedNums.add(lastGeneratedNum);
        return lastGeneratedNum;
    }
}
