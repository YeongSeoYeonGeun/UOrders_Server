package com.example.uorders.repository;

import com.example.uorders.domain.Cart;
import com.example.uorders.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
