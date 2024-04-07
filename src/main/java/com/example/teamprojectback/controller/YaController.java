package com.example.teamprojectback.controller;


import com.example.teamprojectback.parser.YaParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("https://market.yandex.ru/?wprid=1712483749099407-624781689861120" +
        "3595-balancer-l7leveler-kubr-yp-sas-8-BAL&utm_source_service=web&cl" +
        "id=703&src_pof=703&icookie=0J%2FPN6vWlEztGJBlUv4XToJUu7wrkVGM7kM" +
        "4fH7C4AJ0uX2D1AcEEWtl7waCpXyfH0CJC35T0pMKVZntOVxMe55dMIA%3D&baobab_event_id=lupclypxuz")
public class YaController {
    YaParser yaMarket;

    @GetMapping
    public void getAllProductNames(){
        yaMarket.getProductNames();
    }

}
