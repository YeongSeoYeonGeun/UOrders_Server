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
public class CafeDetailDto {

    private String cafeName;
    private String cafeLocation;
    private Boolean isFavorite;
    private List<CafeDetail_menuDto> menuInfo;

    public static CafeDetailDto of(Cafe cafe, Boolean isFavorite) {
        List<CafeDetail_menuDto> menuDtoList = new ArrayList<>();
        for(Menu menu: cafe.getMenuSet()) {
            CafeDetail_menuDto menuDto = CafeDetail_menuDto.of(menu);
            menuDtoList.add(menuDto);
        }

        return new CafeDetailDto(cafe.getName(),cafe.getLocation(), isFavorite, menuDtoList);
    }
}
