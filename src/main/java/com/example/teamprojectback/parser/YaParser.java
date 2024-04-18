package com.example.teamprojectback.parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YaParser {

    private static Document document;

    static {
        try {
            document = Jsoup.connect("https://market.yandex.ru/?wprid=1712483749099407-624781689861120" +
                    "3595-balancer-l7leveler-kubr-yp-sas-8-BAL&utm_source_service=web&cl" +
                    "id=703&src_pof=703&icookie=0J%2FPN6vWlEztGJBlUv4XToJUu7wrkVGM7kM" +
                    "4fH7C4AJ0uX2D1AcEEWtl7waCpXyfH0CJC35T0pMKVZntOVxMe55dMIA%3D&baobab_event_id=lupclypxuz").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getProductNames(){
        List<String> names = List.of(document.select("h3").text().split(" "));
        return names;
    }
    public List<String> getProductPrices(){
        String[] forSearch=new String[]{"0","1","2","3","4","5","6","7","8","9"};
        List<String> output=new ArrayList<>();
        List<String> neededTag = List.of(document.select("._3gYEe").text().split(" "));

        for(String s:forSearch){
            for(String el:neededTag){
                System.out.println(el);
//                if(el.contains(s)){
//                    System.out.println(el+"₽");
                    output.add(el);
//                }
            }
        }
        return output;
    }
    public List<String> getProductsRef() {
        Elements neededRef = document.select("a");
        List<String> hrefOutput = new ArrayList<>();
        for (Element el : neededRef) {
            if (el.toString().contains("product--") && !hrefOutput.contains(el.toString())) {
                //System.out.println(el.attr("href").toString());
                hrefOutput.add(el.attr("href"));
            }
        }
        return hrefOutput;
    }

    public List<String> getProductsImg(){
        Elements imgs=document.select("img");
        List<String> output=new ArrayList<>();
        for(Element img : imgs){
            output.add(img.attr("src").toString());
            //System.out.println(img.attr("src"));
        }
        return output;
    }
}