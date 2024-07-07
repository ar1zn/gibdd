package com.gibdd.gidbb.generator;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NumberGeneratorDefault implements NumberGenerator {
    public static String regex = "[АЕТОРНУКХСВМ]\\d{3}[АЕТОРНУКХСВМ]{2}116 RUS";
    private static final char[] LETTERS = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М'};
    private static final String REGION = "116 RUS";
    private final Set<String> savedNums = new HashSet<>();
    private String lastGeneratedNum;
    private int ixLastGeneratedNum;
    public static final int MAX_GENERATION_ATTEMPTS = 1728000;
    private static final Random random = new Random();
    private static ArrayList<String> allNumbers;

    @PostConstruct
    public void init() {
        allNumbers = (ArrayList<String>) generateAllPossibleNumbers();
        ixLastGeneratedNum = allNumbers.size() - 1;
    }

    @Override
    public String getNext() {
        if (allNumbers.size() == 0) {
            lastGeneratedNum = "М999ММ116 RUS";
        } else {
            lastGeneratedNum = allNumbers.get(ixLastGeneratedNum);
            allNumbers.remove(ixLastGeneratedNum--);
        }
        return lastGeneratedNum;
    }

    @Override
    public String generateRandom() {
        if (allNumbers.size() == 0) {
            throw new RuntimeException("Номеров больше нет");
        }
        String randElem;
        int startIndex = allNumbers.size() * 19 / 20;
        int randIx = startIndex + random.nextInt(allNumbers.size() - startIndex);

        randElem = allNumbers.get(randIx);
        lastGeneratedNum = randElem;
        ixLastGeneratedNum = randIx;
        allNumbers.remove(randIx);

        return randElem;
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

    public List<String> generateAllPossibleNumbers() {
        List<String> allNumbers = new ArrayList<>();
        for (char firstLetter : LETTERS) {
            for (int num = 0; num <= 999; num++) {
                String numStr = String.format("%03d", num);
                for (char secondLetter : LETTERS) {
                    for (char thirdLetter : LETTERS) {
                        String number = String.format("%c%s%c%c", firstLetter, numStr, secondLetter, thirdLetter) + REGION;
                        allNumbers.add(number);
                    }
                }
            }
        }
        return allNumbers;
    }
}
