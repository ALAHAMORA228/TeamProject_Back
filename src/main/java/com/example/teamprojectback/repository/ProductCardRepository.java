package com.example.teamprojectback.repository;

import com.example.teamprojectback.dto.ProductCardDTO;
import com.example.teamprojectback.entities.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCardRepository extends JpaRepository<ProductCard,Long> {
    ProductCard create(ProductCardDTO dto);

    ProductCard update(ProductCard productCard);
}
