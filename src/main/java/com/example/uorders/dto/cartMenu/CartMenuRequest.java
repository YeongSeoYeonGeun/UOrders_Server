package com.example.uorders.dto.cartMenu;

import com.example.uorders.domain.MenuSize;
import com.example.uorders.domain.MenuTakeType;
import com.example.uorders.domain.MenuTemperature;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CartMenuRequest {

    @Getter
    @AllArgsConstructor
    public static class CreateCartMenuRequest {
        private Long cafeIndex;
        private Long menuIndex;
        private String menuName;
        private int menuCount;
        private MenuTemperature menuTemperature;
        private MenuSize menuSize;
        private String menuTakeType;
        private int menuTotalPrice;
    }
}
