package com.example.uorders.repository;

import com.example.uorders.domain.OrderMenu;
import com.example.uorders.domain.OrderMenuId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
