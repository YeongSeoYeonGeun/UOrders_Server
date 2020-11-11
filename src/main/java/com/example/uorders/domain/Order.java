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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PLACED, IN_PROGRESS, COMPLETE

    // 로직으로 빼는게 맞는지
    private int totalPrice;

    // 타입 맞는지
    private LocalDateTime estimated_time;

    private Cart cart;


}
