package com.example.uorders.dto.cafe;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OwnerCafe {

    private String cafeName;
    private String cafeLocation;

    public static OwnerCafe of(Cafe cafe) {
        String cafeName = cafe.getName();
        String cafeLocation = cafe.getLocation();

        return new OwnerCafe(cafeName, cafeLocation);
    }
}