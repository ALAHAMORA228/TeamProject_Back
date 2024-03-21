package com.example.teamprojectback.model;

import lombok.*;

import java.util.ArrayList;


@Data
@Builder
public class User {
    private String login;
    private String password;
    private String email;
    private ArrayList<Product> wishList;
}
