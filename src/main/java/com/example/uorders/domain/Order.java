package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PLACED, IN_PROGRESS, COMPLETE

    // 로직으로 빼는게 맞는지
    private int totalPrice;

    // 타입 맞는지
    private LocalDateTime estimated_time;

    private List<Cart> orderItems = new ArrayList<>();
}
