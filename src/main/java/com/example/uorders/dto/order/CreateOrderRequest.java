package com.example.uorders.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateOrderRequest {
    Long userIndex;
    Long cafeIndex;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING)
    LocalDateTime orderDateTime;
}
