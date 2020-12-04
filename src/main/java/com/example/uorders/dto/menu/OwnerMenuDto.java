package com.example.uorders.dto.menu;

import com.example.uorders.api.constants.Text;
import com.example.uorders.domain.Menu;
import com.example.uorders.domain.MenuStatus;
import com.example.uorders.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OwnerMenuDto {
    private Long menuIndex;
    private String menuName;
    private boolean menuTemperature;
    private boolean menuSize;

}