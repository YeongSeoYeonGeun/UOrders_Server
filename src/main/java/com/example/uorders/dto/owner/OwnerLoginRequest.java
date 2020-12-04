package com.example.uorders.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnerLoginRequest {
    private String ownerId;
    private String ownerPw;
    private String deviceToken;
}
