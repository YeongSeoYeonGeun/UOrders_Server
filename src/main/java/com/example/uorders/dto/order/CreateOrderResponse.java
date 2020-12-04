package com.example.uorders.dto.order;

import com.example.uorders.api.constants.Text;
import com.example.uorders.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderResponse {
    private Long orderIndex;
    private String orderCompleteText;
    private String acceptText;
    private String returnHomeText;

    public static CreateOrderResponse of(Order order, String languageCode) {
        String orderCompleteText = Text.completeOrder(languageCode);
        String acceptText = Text.acceptOrder(languageCode);
        String returnHomeText = Text.returnHome(languageCode);

        return new CreateOrderResponse(order.getId(), orderCompleteText, acceptText, returnHomeText);
    }
}

