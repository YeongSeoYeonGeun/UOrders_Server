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
    private List<OwnerCafeDetail_menuDto> menuInfo;

    public static OwnerCafeDetail of(Cafe cafe) {
        List<OwnerCafeDetail_menuDto> menuDtoList = new ArrayList<>();
        for(Menu menu: cafe.getMenuSet()) {
            OwnerCafeDetail_menuDto menuDto = OwnerCafeDetail_menuDto.of(menu);
            menuDtoList.add(menuDto);
        }

        return new OwnerCafeDetail(cafe.getName(),cafe.getLocation(), menuDtoList);
    }
}