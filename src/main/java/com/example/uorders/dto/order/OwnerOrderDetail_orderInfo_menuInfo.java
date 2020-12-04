package com.example.uorders.dto.order;

import com.example.uorders.domain.Menu;
import com.example.uorders.domain.OrderMenu;
import com.example.uorders.dto.cafe.OwnerCafeDetail_menuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OwnerOrderDetail_orderInfo_menuInfo {
    private Long menuIndex;
    private String menuName;
    private Boolean menuTemperature;
    private Boolean menuSize;

    public static OwnerOrderDetail_orderInfo_menuInfo of(Menu menu) {
        return new OwnerOrderDetail_orderInfo_menuInfo(menu.getId(), menu.getName(), menu.getTemperatureSelect(), menu.getSizeSelect());
    }
}
