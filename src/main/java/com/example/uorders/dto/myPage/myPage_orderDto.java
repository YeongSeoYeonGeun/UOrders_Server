package com.example.uorders.dto.myPage;

import com.example.uorders.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class myPage_orderDto {
    private String orderer;
    private String orderTime;
    private List<myPage_order_menuDto> orderMenuInfo;
    private Integer orderTotalPrice;

    public static myPage_orderDto of(Order order, String orderTime, List<myPage_order_menuDto> orderMenuDtoList) {
        return new myPage_orderDto(order.getUser().getName(), orderTime, orderMenuDtoList, order.getTotalPrice());
    }
}
