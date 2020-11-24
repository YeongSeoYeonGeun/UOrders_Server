package com.example.uorders.repository;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Order;
import com.example.uorders.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findByUser(User user);
}
