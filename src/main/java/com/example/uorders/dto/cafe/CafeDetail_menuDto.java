package com.example.uorders.dto.cafe;

import com.example.uorders.api.constants.Text;
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
    private String menuPriceText;
    private String menuImage;

    public static CafeDetail_menuDto of(Menu menu, String languageCode) {
        String menuPriceText = menu.getPrice() + Text.menuPrice(menu.getPrice(), languageCode);
        return new CafeDetail_menuDto(menu.getId(), menu.getName(), menu.getPrice(), menuPriceText, menu.getImage());
    }
}
