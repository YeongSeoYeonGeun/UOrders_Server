package com.example.uorders.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "CART")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    //== 빌더 ==//
    @Builder
    public Cart(Set<CartMenu> cartMenuSet, User user, Cafe cafe) {
        this.cartMenuSet = cartMenuSet;
        this.user = user;
        user.setCart(this); //== 연관관계 ==//

        this.cafe = cafe;
        if (cafe != null){
            cafe.getCartSet().add(this); //== 연관관계 ==//
        }


    }
}
