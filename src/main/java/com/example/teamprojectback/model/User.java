package com.example.teamprojectback.model;

import lombok.*;

import java.util.List;


@Data
@Builder
public class User {
    private String login;
    private String password;
    private String email;
    private List<Product> wishList;
}
