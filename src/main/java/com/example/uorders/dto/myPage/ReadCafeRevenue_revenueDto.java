package com.example.uorders.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadCafeRevenue_revenueDto {
    private int year;
    private int month;
    private Long revenue;

    public static ReadCafeRevenue_revenueDto of(int year, int month, Long revenue){
        return new ReadCafeRevenue_revenueDto(year, month, revenue);
    }
}
