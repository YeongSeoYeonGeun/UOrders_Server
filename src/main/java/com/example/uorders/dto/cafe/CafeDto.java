package com.example.uorders.dto.cafe;

import com.example.uorders.domain.Cafe;
import com.example.uorders.util.Translator;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class CafeDto {
    private Long cafeIndex;
    private String cafeName;
    private String cafeLocation;
    private String cafeImage;


    public static CafeDto of(Cafe cafe) {
        String translateCafeName = Translator.translate(cafe.getName(), "chinese");
        String translateCafeLocation = Translator.translate(cafe.getLocation(), "chinese");
        return new CafeDto(cafe.getId(), translateCafeName, translateCafeLocation, cafe.getImage());
    }
}
