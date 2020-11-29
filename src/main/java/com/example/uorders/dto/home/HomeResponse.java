package com.example.uorders.dto.home;

import com.example.uorders.dto.cafe.CafeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class HomeResponse {
    private String userName;
    private List<CafeDto> cafeInfo;
}
