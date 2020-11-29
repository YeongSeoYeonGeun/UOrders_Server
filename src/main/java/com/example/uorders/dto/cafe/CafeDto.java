package com.example.uorders.dto.cafe;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.dto.menu.MenuDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class CafeDto {
    private Long cafeIndex;
    private String cafeName;
    private String cafeLocation;
    private String cafeImage;


    public static CafeDto of(Cafe cafe) {
        return new CafeDto(cafe.getId(), cafe.getName(), cafe.getLocation(), cafe.getImage());
    }
}
