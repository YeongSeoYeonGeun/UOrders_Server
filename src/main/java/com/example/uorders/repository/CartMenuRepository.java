package com.example.uorders.repository;

import com.example.uorders.domain.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {
}
