package com.example.uorders.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class myPageDto {

    private myPage_cafeDto cafeInfo;
    private List<myPage_orderDto> todayOrderInfo;

    public static myPageDto of(myPage_cafeDto cafeDto, List<myPage_orderDto> orderDtoList){
        return new myPageDto(cafeDto, orderDtoList);
    }
}
