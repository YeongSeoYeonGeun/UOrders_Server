package com.example.uorders.dto.menu;

import com.example.uorders.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuDto {

    private String menuName;
    private int menuPrice;
    private String menuImage;
    private boolean selectTemperature;
    private boolean selectSize;

    public static MenuDto of(Menu menu){
        return new MenuDto(menu.getName(), menu.getPrice(), menu.getImage(), menu.getTemperatureSelect(), menu.getSizeSelect());
    }
}
