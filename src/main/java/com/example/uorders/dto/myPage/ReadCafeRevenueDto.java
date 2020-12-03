package com.example.uorders.dto.myPage;

import com.example.uorders.domain.Cafe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ReadCafeRevenueDto {
    private String cafeName;
    private String ownerName;
    private String businessNumber;
    private List<ReadCafeRevenue_revenueDto> revenueInfo;

    public static ReadCafeRevenueDto of(Cafe cafe, List<ReadCafeRevenue_revenueDto> revenueDtoList ) {
        return new ReadCafeRevenueDto(cafe.getName(), cafe.getOwner().getName(), cafe.getOwner().getBusinessNumber(), revenueDtoList);
    }
}
