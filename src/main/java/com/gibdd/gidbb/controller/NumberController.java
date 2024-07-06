package com.gibdd.gidbb.controller;

import com.gibdd.gidbb.model.Number;
import com.gibdd.gidbb.service.NumberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/number")
public class NumberController {
    private NumberService numberService;

    public NumberController(NumberService numberService){
        this.numberService = numberService;
    }

    @GetMapping("/next")
    public String getNext(){
     return numberService.getNextNumber();
    }

    @GetMapping("/random")
    public String getRandom(){
        return numberService.generateRandomNumber();
    }

    @GetMapping("/save")
    public String saveNum(){
        return numberService.saveNumber().toString();
    }
}
