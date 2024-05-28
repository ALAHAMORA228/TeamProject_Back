package com.example.teamprojectback.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ProductCardDTO {
    private String name;
    private String imageUrl;
    private int price;
    private String marketplace;
    private float raiting;
    private int feedback;
    private String link;
}
