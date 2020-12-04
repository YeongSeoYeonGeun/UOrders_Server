package com.example.uorders.dto.cart;

import com.example.uorders.api.constants.Text;
import com.example.uorders.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter  @AllArgsConstructor
public class Cart_cartMenuDto {
    private Long cartMenuIndex;
    private Long menuIndex;
    private String menuName;
    private MenuTemperature menuTemperature;
    private MenuSize menuSize;
    private String menuTakeType;
    private int menuCount;
    private int menuOrderPrice;
    private String menuPriceText;

    public static Cart_cartMenuDto of(CartMenu cartMenu, String languageCode) {
        Menu menu = cartMenu.getMenu();
        String menuPriceText = Text.menuPrice(cartMenu.getCount(), cartMenu.getOrderPrice(), languageCode);
        String menuName = menu.getName(languageCode);

        return new Cart_cartMenuDto(cartMenu.getId(), menu.getId(), menuName, cartMenu.getMenuTemperature(),
                cartMenu.getMenuSize(), cartMenu.getMenuTakeType(), cartMenu.getCount(), cartMenu.getOrderPrice(), menuPriceText);
    }
}
