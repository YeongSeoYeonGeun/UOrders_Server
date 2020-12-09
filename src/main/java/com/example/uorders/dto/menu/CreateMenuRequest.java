package com.example.uorders.dto.menu;

import com.example.uorders.domain.MenuStatus;
import lombok.Data;

@Data
public class CreateMenuRequest {

    private Long cafeIndex;
    private String menuName;
    private boolean menuTemperature;
    private boolean menuSize;
    private int menuPrice;
    private MenuStatus soldOut;
    private String menuImage;


}
