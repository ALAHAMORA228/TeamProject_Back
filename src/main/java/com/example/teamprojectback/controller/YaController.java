package com.example.teamprojectback.controller;


import com.example.teamprojectback.parser.YaParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class YaController {
    YaParser yaMarket;

    @GetMapping
    public void getAllProductNames(){
        yaMarket.getProductNames();

    }

}
