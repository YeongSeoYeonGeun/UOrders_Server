package com.example.uorders.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDERS")
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

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    Set<OrderMenu> orderMenus = new HashSet<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime; // 주문 시간
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime acceptTime; // 주문 접수 시간
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime estimateTime; // 완료 예상 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PLACED, ACCEPTED, COMPLETE

    private int totalPrice;


    //== 연관관계 메서드==//
    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenu.setOrder(this);
        orderMenus.add(orderMenu);
    }


    //== 생성 메서드 ==//
    // 완료 시간은 주문 접수 후에
    public static Order createOrder(User user, Cafe cafe, Cart cart, LocalDateTime dateTime, Set<OrderMenu> orderMenus) {
        Order order = new Order();
        order.setUser(user);
        order.setCafe(cafe);
        order.setOrderTime(dateTime);
        order.setStatus(OrderStatus.PLACED);
        order.setTotalPrice(cart.getTotalPrice());

        for(OrderMenu orderMenu : orderMenus) {
            order.addOrderMenu(orderMenu);
        }
        return order;
    }
}
