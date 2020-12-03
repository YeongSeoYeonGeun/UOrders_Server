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


    public static CafeDto of(Cafe cafe, String languageCode) {
        String cafeName = Translator.translate(cafe.getName(), languageCode);
        String cafeLocation = Translator.translate(cafe.getLocation(), languageCode);
        return new CafeDto(cafe.getId(), cafeName, cafeLocation, cafe.getImage());
    }
}
