package com.example.uorders.dto.order;

import com.example.uorders.domain.MenuSize;
import com.example.uorders.domain.MenuTakeType;
import com.example.uorders.domain.MenuTemperature;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class OrderRequest {

    @Getter
    @AllArgsConstructor
    public static class CreateOrderRequest {
        Long userIndex;
        Long cafeIndex;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime orderDateTime;
    }
}
