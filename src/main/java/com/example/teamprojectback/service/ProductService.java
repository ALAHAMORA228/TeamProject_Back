package com.example.teamprojectback.service;

import com.example.teamprojectback.dto.ProductCardDTO;
import com.example.teamprojectback.entities.ProductCard;
import com.example.teamprojectback.repository.ProductCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductCardRepository productCardRepository;

    public ProductCard create(ProductCardDTO dto){
        return productCardRepository.save(ProductCard.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .marketplace(dto.getMarketplace())
                .feedback(dto.getFeedback())
                .link(dto.getLink())
                .raiting(dto.getRaiting())
                .imageUrl(dto.getImageUrl())
                .build());
    }

    public List<ProductCard> readAll(){
        return productCardRepository.findAll();
    }
    public ProductCard update(ProductCard productCard){
        return productCardRepository.save(productCard);
    }

    public void delete(Long id){
        productCardRepository.deleteById(id);
    }
}
