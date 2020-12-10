package com.example.uorders.dto.order;

import com.example.uorders.domain.Order;
import com.example.uorders.domain.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderAlarmDto {
    private String userId;
    private int totalPrice;
    private List<OrderAlarm_menuDto> menuInfo;

    public static OrderAlarmDto of (Order order){

        List<OrderAlarm_menuDto> menuDtoList = new ArrayList<>();
        for(OrderMenu orderMenu: order.getOrderMenuSet()){
            menuDtoList.add(OrderAlarm_menuDto.of(orderMenu));
        }

        return new OrderAlarmDto(order.getUser().getName(), order.getTotalPrice(), menuDtoList);
    }
}
