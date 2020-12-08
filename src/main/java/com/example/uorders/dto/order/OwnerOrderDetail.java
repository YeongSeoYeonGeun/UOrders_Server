package com.example.uorders.dto.order;


import com.example.uorders.api.constants.Text;
import com.example.uorders.domain.*;
import com.example.uorders.dto.cafe.OwnerCafeDetail_menuDto;
import com.example.uorders.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor

public class OwnerOrderDetail {

    private String cafeName;
    private String cafeLocation;
    private List<OwnerOrderDetail_orderInfo> orderInfo;


    public static OwnerOrderDetail of(Cafe cafe) {
        List<OwnerOrderDetail_orderInfo> OwnerOrderDtoList = new ArrayList<>();
        for (Order order : cafe.getOrderSet()) {
            if(order.getStatus() ==  OrderStatus.ACCEPTED || order.getStatus() == OrderStatus.PLACED) {
                OwnerOrderDetail_orderInfo orderDto = OwnerOrderDetail_orderInfo.of(order);
                OwnerOrderDtoList.add(orderDto);
            }
        }

        return new OwnerOrderDetail(cafe.getName(), cafe.getLocation(), OwnerOrderDtoList);
    }
}
