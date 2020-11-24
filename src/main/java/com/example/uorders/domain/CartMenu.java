package com.example.uorders.domain;

import com.example.uorders.Service.MenuService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(CartMenuId.class)
@Table(name = "CART_MENU")
public class CartMenu {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int count;
    private int orderPrice;

    @Enumerated(EnumType.STRING)
    private MenuTemperature menuTemperature; // HOT, ICED

    @Enumerated(EnumType.STRING)
    private MenuSize menuSize; // SMALL, REGULAR, LARGE

    @Enumerated(EnumType.STRING)
    private MenuTakeType menuTakeType; // HERE, TO_GO

    //==연관관계 메서드==//
    public void setCart(Cart cart) {
        this.cart = cart;
        cart.getCartMenus().add(this);
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        menu.getCartMenus().add(this);
    }

    //==생성 메서드//
    public static CartMenu createCartMenu(Menu menu, int orderPrice, int count, MenuTemperature menuTemperature, MenuSize menuSize, MenuTakeType menuTakeType, Cart cart) {
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
