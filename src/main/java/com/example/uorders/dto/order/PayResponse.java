package com.example.uorders.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayResponse {
    private String timeStamp;
    private String nonceStr;
    private String package_;
    private String signType;
    private String tId;
    private String paySign;
}
