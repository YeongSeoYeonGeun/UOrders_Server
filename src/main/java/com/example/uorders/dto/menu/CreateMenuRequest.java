package com.example.uorders.dto.menu;

import lombok.Data;

@Data
public class CreateMenuRequest {
    private Long menuIndex;
    private String menuName;
    private String menuTemperature;
    private String menuSize;
    private int menuPrice;
    private boolean soldOut;
    private String menuImage;


}
