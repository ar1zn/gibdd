package com.gibdd.gidbb.service;

import com.gibdd.gidbb.generator.NumberGenerator;
import com.gibdd.gidbb.generator.NumberGeneratorDefault;
import com.gibdd.gidbb.repository.NumberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gibdd.gidbb.model.Number;

@Service
public class NumberService {
    private NumberGenerator numberGenerator;
    private NumberRepository numberRepository;

    @Autowired
    public NumberService(NumberRepository numberRepository) {
        this.numberGenerator = new NumberGeneratorDefault();
        this.numberRepository = numberRepository;
    }

    public String generateRandomNumber() {
        return numberGenerator.generateRandom();
    }

    public String generateNextNumber() {
        return numberGenerator.getNext();
    }

    @Transactional(rollbackOn = Exception.class)
    public Number saveNumber() {
        Number number = new Number(numberGenerator.getLastNum());
        return numberRepository.save(number);
    }
}
