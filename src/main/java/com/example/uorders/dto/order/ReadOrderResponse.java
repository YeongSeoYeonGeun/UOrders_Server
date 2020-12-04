package com.example.uorders.dto.order;

import com.example.uorders.api.constants.Text;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReadOrderResponse {
    private String readOrderText;
    private String yearText;
    private String monthText;
    private String dayText;
    private String morningText;
    private String afternoonText;
    private String totalPriceText;
    private String wonText;
    private String orderDateText;
    private String andText;
    private String numberText;

    private List<OrderDto> orderInfo;

    public static ReadOrderResponse of(String languageCode, List<OrderDto> orderDtoList) {
        String readOrderText = Text.readOrder(languageCode);
        String yearText = Text.year(languageCode);
        String monthText = Text.month(languageCode);
        String dayText = Text.day(languageCode);
        String morningText = Text.morning(languageCode);
        String afternoonText = Text.afternoon(languageCode);
        String totalPriceText = Text.totalPrice(languageCode);
        String wonText = Text.won(languageCode);
        String orderDateText = Text.orderDate(languageCode);
        String andText = Text.and(languageCode);
        String numberText = Text.number(languageCode);


        return new ReadOrderResponse(readOrderText, yearText, monthText, dayText, morningText, afternoonText, totalPriceText, wonText, orderDateText, andText, numberText, orderDtoList);
    }
}
