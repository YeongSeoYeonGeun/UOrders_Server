package com.example.uorders.dto.order;

import com.example.uorders.domain.*;
import com.example.uorders.util.Translator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long orderIndex;
    private String cafeName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDate;
    private List<Order_orderMenuDto> menuInfo;
    private int totalPrice;

    public static OrderDto of(Order order, String languageCode) {
        String cafeName = order.getCafe().getName(languageCode);

        List<Order_orderMenuDto> orderMenuDtoList = new ArrayList<>();

        for (OrderMenu orderMenu : order.getOrderMenuSet()) {
            Order_orderMenuDto orderMenuDto = Order_orderMenuDto.of(orderMenu, languageCode);
            orderMenuDtoList.add(orderMenuDto);
        }

        return new OrderDto(order.getId(), cafeName, order.getOrderTime(), orderMenuDtoList, order.getTotalPrice());
    }
}
