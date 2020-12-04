package com.example.uorders.dto.favorite;

import com.example.uorders.domain.Cafe;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FavoriteDto {

    @Getter
    @AllArgsConstructor
    public static class FavoriteCafeDto {
        private Long cafeIndex;
        private String cafeName;
        private String cafeLocation;
        private String cafeImage;

        public static FavoriteDto.FavoriteCafeDto of(Cafe cafe, String languageCode) {
            String cafeName = cafe.getName(languageCode);
            String cafeLocation = cafe.getLocation(languageCode);

            return new FavoriteDto.FavoriteCafeDto(cafe.getId(), cafeName, cafeLocation, cafe.getImage());
        }
    }
}
