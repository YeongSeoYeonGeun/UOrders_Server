package com.example.uorders.repository;

import com.example.uorders.domain.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

    Set<CartMenu> findByMenuId(Long id);
}
