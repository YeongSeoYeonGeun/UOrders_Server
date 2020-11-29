package com.example.uorders.dto.cart;

import com.example.uorders.domain.*;
import com.example.uorders.dto.cartMenu.CartMenuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter  @AllArgsConstructor
public class Cart_cartMenuDto {
    private Long cartMenuIndex;
    private Long menuIndex;
    private String menuName;
    private MenuTemperature menuTemperature;
    private MenuSize menuSize;
    private MenuTakeType menuTakeType;
    private int menuCount;
    private int menuOrderPrice;

    public static Cart_cartMenuDto of(CartMenu cartMenu) {
        Menu menu = cartMenu.getMenu();
        return new Cart_cartMenuDto(cartMenu.getId(), menu.getId(), menu.getName(), cartMenu.getMenuTemperature(),
                cartMenu.getMenuSize(), cartMenu.getMenuTakeType(), cartMenu.getCount(), cartMenu.getOrderPrice());
    }
}
