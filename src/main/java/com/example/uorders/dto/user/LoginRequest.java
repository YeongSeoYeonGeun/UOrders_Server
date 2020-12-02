package com.example.uorders.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class LoginRequest {
    private Long userIndex;
    private String js_code;
}
