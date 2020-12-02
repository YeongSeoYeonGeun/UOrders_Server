package com.example.uorders.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "CART_MENU")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartMenu {

    @Id @GeneratedValue
    @Column(name = "cart_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int count;
    private int orderPrice;

    @Enumerated(EnumType.STRING)
    private MenuTemperature menuTemperature; // HOT, ICED

    @Enumerated(EnumType.STRING)
    private MenuSize menuSize; // SMALL, REGULAR, LARGE

    private String menuTakeType; // HERE, TO GO

    //==연관관계 메서드==//
    public void setCart(Cart cart) {
        this.cart = cart;
        cart.getCartMenuSet().add(this);
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        menu.getCartMenuSet().add(this);
    }


    //== 빌더 ==//
    @Builder
    public CartMenu(Cart cart, Menu menu, int count, int orderPrice, MenuTemperature menuTemperature, MenuSize menuSize, String menuTakeType) {
        this.cart = cart;
        cart.getCartMenuSet().add(this); //== 연관관계 ==//

        this.menu = menu;
        menu.getCartMenuSet().add(this); //== 연관관계 ==//

        this.count = count;
        this.orderPrice = orderPrice;
        this.menuTemperature = menuTemperature;
        this.menuSize = menuSize;
        this.menuTakeType = menuTakeType;
    }
}
