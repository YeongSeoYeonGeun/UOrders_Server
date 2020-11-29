package com.example.uorders.dto.order;

import com.example.uorders.domain.Menu;
import com.example.uorders.domain.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order_orderMenuDto {
    private Long menuIndex;
    private String menuName;
    private int orderCount;
    private int orderPrice;

    public static Order_orderMenuDto of(OrderMenu orderMenu) {
        Menu menu = orderMenu.getMenu();
        return new Order_orderMenuDto(menu.getId(), menu.getName(), orderMenu.getCount(), orderMenu.getOrderPrice());
    }
}
