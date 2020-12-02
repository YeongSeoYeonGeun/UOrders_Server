package com.example.uorders.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    Set<OrderMenu> orderMenuSet = new HashSet<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime; // 주문 시간
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime acceptTime; // 주문 접수 시간
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime estimateTime; // 완료 예상 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PLACED, ACCEPTED, COMPLETE

    private int totalPrice;

    @Builder
    public Order(User user, Cafe cafe, Set<OrderMenu> orderMenuSet, LocalDateTime orderTime, LocalDateTime acceptTime, LocalDateTime estimateTime, OrderStatus orderStatus, int totalPrice) {
        this.user = user;
        user.getOrderSet().add(this); //== 연관관계 ==//

        this.cafe = cafe;
        cafe.getOrderSet().add(this); //== 연관관계 ==//

        this.orderMenuSet = orderMenuSet;
        this.orderTime = orderTime;
        this.acceptTime = acceptTime;
        this.estimateTime = estimateTime;
        this.status = orderStatus;
        this.totalPrice = totalPrice;
    }
}
