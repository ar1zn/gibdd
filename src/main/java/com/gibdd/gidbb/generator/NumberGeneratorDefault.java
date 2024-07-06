package com.gibdd.gidbb.generator;

import java.util.*;
import java.util.stream.Collectors;

public class NumberGeneratorDefault implements NumberGenerator {
    public static String regex = "[АЕТОРНУКХСВМ]\\d{3}[АЕТОРНУКХСВМ]{2}116 RUS";
    private static final char[] LETTERS = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М'};
    private static final String REGION = "116 RUS";
    private final Set<String> generatedNums = new HashSet<>();
    private final Set<String> savedNums = new HashSet<>();
    private String lastGeneratedNum;
    private static final int MAX_GENERATION_ATTEMPTS = 17424000;
    private static final Random random = new Random();


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
        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            newNum = generateRandomCharacter(LETTERS) +
                    random.nextInt(10) +
                    random.nextInt(10) +
                    random.nextInt(10) +
                    generateRandomCharacter(LETTERS) +
                    generateRandomCharacter(LETTERS) +
                    REGION;
            if (!generatedNums.contains(newNum)) {
                generatedNums.add(newNum);
                lastGeneratedNum = newNum;
                return newNum;
            }
        }
        throw new RuntimeException("Не удалось сгенерировать уникальный номер после " + MAX_GENERATION_ATTEMPTS + " попыток");
    }

    private String generateRandomCharacter(char[] characters) {
        return String.valueOf(characters[random.nextInt(characters.length)]);
    }

    private String incrementNumber(String number) {
        char[] chars = number.toCharArray();
        int index = chars.length - 1;
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
                char nextLetter = nextLetter(currentChar);
                chars[index] = nextLetter;
                if (generatedNums.contains(new String(chars).concat(REGION))) {
                    num++;
                    if (num > 999) {
                        break;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(number.charAt(0));
                    sb.append(num);
                    sb.append(number.substring(4, number.length()));
                    return sb.toString();
                }
                break;
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
