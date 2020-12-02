package com.example.uorders.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "CART_MENU")
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

    //==생성 메서드//
    public static CartMenu createCartMenu(Menu menu, int orderPrice, int count, MenuTemperature menuTemperature, MenuSize menuSize, String menuTakeType, Cart cart) {
        CartMenu cartMenu = new CartMenu();
        cartMenu.setMenu(menu);
        cartMenu.setOrderPrice(orderPrice);
        cartMenu.setCount(count);
        cartMenu.setMenuTemperature(menuTemperature);
        cartMenu.setMenuSize(menuSize);
        cartMenu.setMenuTakeType(menuTakeType);

        cartMenu.setCart(cart);
        return cartMenu;
    }

}
