package com.example.teamprojectback.repository;

import com.example.teamprojectback.dto.ProductCardDTO;
import com.example.teamprojectback.dto.UserDTO;
import com.example.teamprojectback.entities.ProductCard;
import com.example.teamprojectback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User create(UserDTO dto);

    User update(User user);
}
