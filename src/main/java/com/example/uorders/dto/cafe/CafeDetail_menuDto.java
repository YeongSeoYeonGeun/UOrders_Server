package com.example.uorders.dto.cafe;

import com.example.uorders.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CafeDetail_menuDto {
    private Long menuIndex;
    private String menuName;
    private int menuPrice;
    private String menuImage;

    public static CafeDetail_menuDto of(Menu menu) {
        return new CafeDetail_menuDto(menu.getId(), menu.getName(), menu.getPrice(), menu.getImage());
    }
}
