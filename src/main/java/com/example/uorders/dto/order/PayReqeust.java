package com.example.uorders.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
public class PayReqeust {
    private String openId;
    private int amount;

    @Builder
    public PayReqeust(String openId, int amount) {
        this.openId = openId;
        this.amount = amount;
    }
}
