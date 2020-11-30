package com.example.uorders.dto.order;

import lombok.Getter;

@Getter
public class PayRequest {
    private String appid;
    private String secret;
    private String js_code;
    private String grant_type;
    private Integer amount;
}
