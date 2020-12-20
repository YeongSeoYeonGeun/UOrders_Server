package com.example.uorders.dto.menu;

import com.example.uorders.api.constants.Text;
import com.example.uorders.domain.Menu;
import com.example.uorders.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuDto {

    private String menuDetailText;
    private String addCartText;
    private String wonText;
    private String sizeSelectText;
    private String menuName;
    private int menuPrice;
    private String menuPriceText;
    private String menuImage;
    private boolean selectTemperature;
    private boolean selectSize;

    public static MenuDto of(Menu menu, String languageCode){

        String menuDetailText = Text.menuDetail(languageCode);
        String addCartText = Text.addToCart(languageCode);
        String wonText = Text.won(languageCode);
        String selectSizeText = Text.selectSize(languageCode);
        String menuName = menu.getName(languageCode);
        String menuPriceText = Text.menuPrice(menu.getPrice(), languageCode);
        return new MenuDto(menuDetailText, addCartText, wonText, selectSizeText, menuName, menu.getPrice(), menuPriceText,  menu.getImage(), menu.getTemperatureSelect(), menu.getSizeSelect());
    }
}
