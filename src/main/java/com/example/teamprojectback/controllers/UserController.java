package com.example.teamprojectback.controllers;

import com.example.teamprojectback.dto.ProductCardDTO;
import com.example.teamprojectback.dto.UserDTO;
import com.example.teamprojectback.entities.ProductCard;
import com.example.teamprojectback.entities.User;
import com.example.teamprojectback.repository.ProductCardRepository;
import com.example.teamprojectback.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO dto){
        return new ResponseEntity<>(userRepository.create(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> readAllO(){
        return new ResponseEntity<List<User>>(userRepository.findAll(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user){
        return new ResponseEntity<>(userRepository.update(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        userRepository.deleteById(id);
        return HttpStatus.OK;
    }
}
