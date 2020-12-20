package com.example.uorders.dto.myPage;

import com.example.uorders.domain.MenuSize;
import com.example.uorders.domain.MenuTakeType;
import com.example.uorders.domain.MenuTemperature;
import com.example.uorders.domain.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class myPage_order_menuDto {
    private String orderMenuName;
    private Integer count;
    private MenuTemperature temperature;
    private MenuSize size;
    private String takeType;

    public static myPage_order_menuDto of(OrderMenu orderMenu) {
        return new myPage_order_menuDto(orderMenu.getMenu().getName(), orderMenu.getCount(), orderMenu.getMenuTemperature(), orderMenu.getMenuSize(), orderMenu.getMenuTakeType());
    }
}
