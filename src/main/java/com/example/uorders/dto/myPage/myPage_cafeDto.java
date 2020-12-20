package com.example.uorders.dto.myPage;

import com.example.uorders.domain.Cafe;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class myPage_cafeDto {
    private String cafeName;
    private String cafeLocation;
    private String cafeImage;

    public static myPage_cafeDto of(Cafe cafe) {
        return new myPage_cafeDto(cafe.getName(), cafe.getLocation(), cafe.getImage());
    }
}
