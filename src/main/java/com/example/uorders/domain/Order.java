package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PLACED, IN_PROGRESS, COMPLETE

    private int totalPrice;

    // 타입 맞는지
    private LocalDateTime estimatedTime;

    //== 생성 메서드 ==//
    // 완료 시간은 주문 접수 후에
    public static Order createOrder(User user, Cafe cafe, Cart cart) {
        Order order = new Order();
        order.setUser(user);
        order.setCafe(cafe);
        order.setCart(cart);
        order.setDateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setTotalPrice(cart.getTotalPrice());
        return order;
    }

    //== 비즈니스 로직 ==//
    /**
     * 주문 거절
     */
    public void reject() {
        if(this.getStatus() != OrderStatus.PLACED) {
            throw new IllegalStateException("이미 승인되었거나 완료된 주문입니다.");
        }

        this.setStatus(OrderStatus.REJECTED);
    }

    /**
     *  주문 취소
     */
    public void cancel() {
        if(this.getStatus() != OrderStatus.PLACED) {
            throw new IllegalStateException("이미 승인되었거나 완료된 주문입니다.");
        }

        this.setStatus(OrderStatus.CANCELED);
    }

    /**
     *  주문 승인
     */
    public void  accept(LocalDateTime estimatedTime) {
        if(this.getStatus() != OrderStatus.PLACED) {
            throw new IllegalStateException("이미 승인되었거나 완료된 주문입니다.");
        }

        this.setEstimatedTime(estimatedTime);
        this.setStatus(OrderStatus.ACCEPTED);
    }
}
