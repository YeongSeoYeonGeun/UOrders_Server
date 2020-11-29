package com.example.uorders.dto.favorite;

import com.example.uorders.domain.Cafe;
import com.example.uorders.dto.user.UserDto;
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

        public static FavoriteDto.FavoriteCafeDto of(Cafe cafe) {
            return new FavoriteDto.FavoriteCafeDto(cafe.getId(), cafe.getName(), cafe.getLocation(), cafe.getImage());
        }
    }
}
