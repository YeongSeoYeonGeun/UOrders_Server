package com.example.uorders.dto.cafe;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OwnerCafeDetail {

    private String cafeName;
    private String cafeLocation;
    private List<CafeDetail_menuDto> menuInfo;

    public static OwnerCafeDetail of(Cafe cafe) {
        List<CafeDetail_menuDto> menuDtoList = new ArrayList<>();
        for(Menu menu: cafe.getMenuSet()) {
            CafeDetail_menuDto menuDto = CafeDetail_menuDto.of(menu);
            menuDtoList.add(menuDto);
        }

        return new OwnerCafeDetail(cafe.getName(),cafe.getLocation(), menuDtoList);
    }
}