package com.example.uorders.repository;

import com.example.uorders.domain.Order;
import com.example.uorders.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findByUser(User user);

    @Query(value = "select * from orders where year(order_time) = :year and month(order_time) = :month and day(order_time) = :day and cafe_id = :cafeId ", nativeQuery = true)
    Set<Order> findTodayOrder(@Param("cafeId") Long cafeId, @Param("year") int year, @Param("month") int month, @Param("day") int day);
}
