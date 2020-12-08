package com.example.uorders.dto.order;

import com.example.uorders.domain.Menu;
import com.example.uorders.domain.MenuSize;
import com.example.uorders.domain.MenuTemperature;
import com.example.uorders.domain.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OrderAlarm_menuDto {
    private Long menuIndex;
    private String menuName;
    private MenuTemperature menuTemperature;
    private MenuSize menuSize;
    private String menuTakeType;

    public static OrderAlarm_menuDto of (OrderMenu orderMenu){
        Menu menu = orderMenu.getMenu();

        return new OrderAlarm_menuDto(menu.getId(), menu.getName(), orderMenu.getMenuTemperature(), orderMenu.getMenuSize(), orderMenu.getMenuTakeType());
    }
}
