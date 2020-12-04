package com.example.uorders.dto.order;

import com.example.uorders.domain.Menu;
import com.example.uorders.domain.OrderMenu;
import com.example.uorders.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order_orderMenuDto {
    private Long menuIndex;
    private String menuName;
    private int orderCount;
    private int orderPrice;

    public static Order_orderMenuDto of(OrderMenu orderMenu, String languageCode) {
        Menu menu = orderMenu.getMenu();
        String menuName = menu.getName(languageCode);

        return new Order_orderMenuDto(menu.getId(), menuName, orderMenu.getCount(), orderMenu.getOrderPrice());
    }
}
