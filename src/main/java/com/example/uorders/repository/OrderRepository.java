package com.example.uorders.repository;

import com.example.uorders.domain.Order;
import com.example.uorders.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findByUser(User user);

    @Query(value = "select * from orders where year(order_time) = :year and month(order_time) = :month and day(order_time) = :day and cafe_id = :cafeId ", nativeQuery = true)
    Set<Order> findTodayOrder(@Param("cafeId") Long cafeId, @Param("year") int year, @Param("month") int month, @Param("day") int day);

    @Query(value = "select * from orders where user_id = :userId ORDER BY order_time DESC", nativeQuery = true)
    List<Order> findOrderByUserDESC(@Param("userId") Long userId);

    @Query(value = "select * from(select * from orders where cafe_id = :cafeId order by order_time ASC) o LIMIT 1", nativeQuery = true)
    Order findFirstOrder(@Param("cafeId") Long cafeId);

    @Query(value = "select * from(select * from orders where cafe_id = :cafeId order by order_time DESC) o LIMIT 1", nativeQuery = true)
    Order findLastOrder(@Param("cafeId") Long cafeId);

    @Query(value = "select sum(total_price) from orders where year(order_time) = :year and month(order_time) = :month and cafe_id = :cafeId", nativeQuery = true)
    Long sumRevenue(int year, int month, Long cafeId);
}
