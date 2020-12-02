package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartMenu> cartMenuSet = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.setCart(this);
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getCartSet().add(this);
    }

    //== 조회 로직 ==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (CartMenu cartMenu : cartMenuSet) {
            totalPrice += cartMenu.getOrderPrice();
        }
        return totalPrice;
    }
}
