package com.example.teamprojectback.controllers;

import com.example.teamprojectback.dto.ProductCardDTO;
import com.example.teamprojectback.entities.ProductCard;
import com.example.teamprojectback.repository.ProductCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductCardController {

    private final ProductCardRepository productCardRepository;


    @PostMapping
    public ResponseEntity<ProductCard> create(@RequestBody ProductCardDTO dto){
        return new ResponseEntity<>(productCardRepository.create(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductCard>> readAllO(){
        return new ResponseEntity<List<ProductCard>>(productCardRepository.findAll(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductCard> update(@RequestBody ProductCard productCard){
        return new ResponseEntity<>(productCardRepository.update(productCard), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        productCardRepository.deleteById(id);
        return HttpStatus.OK;
    }
}
