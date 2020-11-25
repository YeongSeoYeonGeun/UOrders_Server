package com.example.uorders.repository;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.CartMenu;
import com.example.uorders.domain.CartMenuId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

    Set<CartMenu> findByMenuId(Long id);
}
