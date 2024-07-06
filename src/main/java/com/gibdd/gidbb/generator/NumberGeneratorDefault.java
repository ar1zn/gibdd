package com.gibdd.gidbb.generator;

import java.util.*;

public class NumberGeneratorDefault implements NumberGenerator {
    public static String regex = "[АЕТОРНУКХСВМ]\\d{3}[АЕТОРНУКХСВМ]{2}116 RUS";
    private static final char[] letters = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М'};
    private final String region = "116 RUS";
    private Set<String> generatedNums = new LinkedHashSet<>();
    private Set<String> savedNums = new LinkedHashSet<>();

    @Override
    public String getNext() {
        if (generatedNums.size() == 0) {
            generatedNums.add(new NumberGeneratorDefault().generateRandom());
        }
        List<String> list = new ArrayList<>(generatedNums);
        String prevNum = list.get(list.size() - 1);
        String nextNumber = incrementNumber(prevNum.substring(0, 6)) +
                prevNum.substring(6);

        generatedNums.add(nextNumber);
        return nextNumber;
    }

    @Override
    public String generateRandom() {
        String newNum;
        do {
            newNum = generateRandomCharacter(letters) +
                    (new Random().nextInt(10)) +
                    (new Random().nextInt(10)) +
                    (new Random().nextInt(10)) +
                    generateRandomCharacter(letters) +
                    generateRandomCharacter(letters) +
                    region;
        } while (generatedNums.contains(newNum));
        generatedNums.add(newNum);
        return newNum;
    }

    private String generateRandomCharacter(char[] characters) {
        Random random = new Random();
        return String.valueOf(characters[random.nextInt(characters.length)]);
    }

    private String incrementNumber(String number) {
        char[] chars = number.toCharArray();
        int index = chars.length - 1;
        int num = Integer.parseInt(number.substring(1, 4));

        while (index >= 0) {
            char currentChar = chars[index];

            if (Character.isDigit(currentChar)) {
                if (currentChar == '9') {
                    chars[index] = '0';
                    index--;
                } else {
                    chars[index]++;
                    break;
                }
            } else if (Character.isLetter(currentChar)) {
                char nextLetter = nextLetter(currentChar);
                chars[index] = nextLetter;
                if (generatedNums.contains(new String(chars).concat(region))) {
                    num++;
                    StringBuilder sb = new StringBuilder();
                    sb.append(number.charAt(0));
                    sb.append(num);
                    sb.append(number.substring(4, number.length()));
                    return sb.toString();
                }
                break;
            }
        }

        return String.valueOf(chars);
    }

    private char nextLetter(char c) {
        for (int i = 0; i < letters.length - 1; i++) {
            if (letters[i] == c) {
                return letters[i + 1];
            }
        }

        return letters[0];
    }

    public String getLastNum() {
        if (generatedNums.size() == 0) {
            throw new RuntimeException("Такой номер уже есть");
        }
        List<String> list = new ArrayList<>(generatedNums);
        String lastNum = list.get(list.size() - 1);
        if (savedNums.contains(lastNum)) {
            throw new RuntimeException("Такой номер уже есть");
        }
        if (!lastNum.matches(regex)) {
            throw new IllegalArgumentException("Неверный формат данных");
        }
        savedNums.add(lastNum);
        return new String(lastNum);
    }
}
