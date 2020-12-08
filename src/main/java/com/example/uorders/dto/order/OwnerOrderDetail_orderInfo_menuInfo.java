package com.example.uorders.dto.order;

import com.example.uorders.domain.*;
import com.example.uorders.dto.cafe.OwnerCafeDetail_menuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OwnerOrderDetail_orderInfo_menuInfo {
    private Long menuIndex;
    private String menuName;
    private MenuTemperature menuTemperature;
    private MenuSize menuSize;
    private String menuTakeType;

    public static OwnerOrderDetail_orderInfo_menuInfo of(OrderMenu ordermenu) {
        Menu menu = ordermenu.getMenu();
        return new OwnerOrderDetail_orderInfo_menuInfo(menu.getId(), menu.getName(), ordermenu.getMenuTemperature(), ordermenu.getMenuSize(), ordermenu.getMenuTakeType());
    }
}
