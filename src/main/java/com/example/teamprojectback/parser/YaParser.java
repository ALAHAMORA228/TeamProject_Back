package com.example.teamprojectback.parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class YaParser {

    Document document;
    public void getProductNames(){
        try {
            document = Jsoup.connect("https://market.yandex.ru/?wprid=1712483749099407-624781689861120" +
                    "3595-balancer-l7leveler-kubr-yp-sas-8-BAL&utm_source_service=web&cl" +
                    "id=703&src_pof=703&icookie=0J%2FPN6vWlEztGJBlUv4XToJUu7wrkVGM7kM" +
                    "4fH7C4AJ0uX2D1AcEEWtl7waCpXyfH0CJC35T0pMKVZntOVxMe55dMIA%3D&baobab_event_id=lupclypxuz").get();
            String[] neededTag=document.select("h3").text().split(",");

            for (String s : neededTag) {
                if(!s.contains("Dual"))
                    System.out.println(s);
            }

            System.out.println("\n");
            System.out.println("\n");
            System.out.println("\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
