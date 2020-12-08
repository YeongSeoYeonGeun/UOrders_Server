package com.example.uorders.dto.order;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.domain.Order;
import com.example.uorders.domain.OrderMenu;
import com.example.uorders.dto.cafe.OwnerCafeDetail;
import com.example.uorders.dto.cafe.OwnerCafeDetail_menuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class OwnerOrderDetail_orderInfo {
    private Long ticketNumber;
    private String orderID;
    private int orderTime;
    private List<OwnerOrderDetail_orderInfo_menuInfo> menuInfo;

    public static OwnerOrderDetail_orderInfo of(Order order) {
        List<OwnerOrderDetail_orderInfo_menuInfo> OwnerMenuDtoList = new ArrayList<>();
        for (OrderMenu orderMenu : order.getOrderMenuSet()) {
            OwnerOrderDetail_orderInfo_menuInfo OwnerMenuDto = OwnerOrderDetail_orderInfo_menuInfo.of(orderMenu);
            OwnerMenuDtoList.add(OwnerMenuDto);
        }

        LocalDateTime estimateTime = order.getEstimateTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, estimateTime);
        long time = (duration.getSeconds()/60);
        int leftTime = Long.valueOf(time).intValue();

        return new OwnerOrderDetail_orderInfo(order.getId(), order.getUser().getName(), leftTime ,OwnerMenuDtoList);
    }

}