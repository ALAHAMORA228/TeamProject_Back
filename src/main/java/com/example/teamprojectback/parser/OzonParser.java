package com.example.teamprojectback.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class OzonParser {
    Document document;
    public void getProductNames(){
        try {
            document = Jsoup.connect("https://www.ozon.ru").get();
            String[] neededTag=document.select("span").text().split(",");

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
