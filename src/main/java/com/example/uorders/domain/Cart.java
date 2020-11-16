package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartMenu> cartMenus = new ArrayList<>();

    @OneToOne(mappedBy = "cart", fetch = FetchType.LAZY)
    private User user;

    @OneToOne(mappedBy = "cart", fetch = FetchType.LAZY)
    private Order order;

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.setCart(this);
    }

    public void setOrder(Order order) {
        this.order = order;
        order.setCart(this);
    }

    //== 비즈니스 로직 ==//
    /**
     * 장바구니 메뉴 삭제
     */
    //public void deleteCartMenu(Long id) {}

    //== 조회 로직 ==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (CartMenu cartMenu : cartMenus) {
            totalPrice += cartMenu.getTotalPrice();
        }
        return totalPrice;
    }
}
