package com.example.uorders.dto.order;

import com.example.uorders.api.OrderApiController;
import com.example.uorders.domain.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDto {
    private Long orderIndex;
    private String cafeName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDate;
    private List<Order_orderMenuDto> menuInfo;
    private int totalPrice;

    public static OrderDto of(Order order) {
        List<Order_orderMenuDto> orderMenuDtoList = new ArrayList<>();

        for (OrderMenu orderMenu : order.getOrderMenus()) {
            Order_orderMenuDto orderMenuDto = Order_orderMenuDto.of(orderMenu);
            orderMenuDtoList.add(orderMenuDto);
        }

        return new OrderDto(order.getId(), order.getCafe().getName(), order.getDateTime(), orderMenuDtoList, order.getTotalPrice());
    }
}