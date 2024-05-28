package com.example.teamprojectback;

import com.example.teamprojectback.parser.OzonParser;
import com.example.teamprojectback.parser.YaParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class TeamProjectBackApplication {
    static YaParser yaMarket=new YaParser();
    static OzonParser ozon=new OzonParser();
    static WildBerriesParser wb=new WildBerriesParser();
    public static void main(String[] args) {

        SpringApplication.run(TeamProjectBackApplication.class, args);

    }

}
