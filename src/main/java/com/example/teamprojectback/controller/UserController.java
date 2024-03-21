package com.example.teamprojectback.controller;


import com.example.teamprojectback.model.Product;
import com.example.teamprojectback.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api/v1/main")
public class UserController {
    @GetMapping("/userWishList")
    public ArrayList<Product> getUserWishList(User user){
        return user.getWishList();
    }

    @GetMapping("/userPersonalArea")
    public User getUser(User user){
        return user;
    }

}
