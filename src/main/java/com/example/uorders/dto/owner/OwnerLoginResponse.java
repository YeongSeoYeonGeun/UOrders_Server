package com.example.uorders.dto.owner;

import com.example.uorders.domain.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnerLoginResponse {
    private Long ownerIndex;
    private Long cafeIndex;

    public static OwnerLoginResponse of(Owner owner) {
        return new OwnerLoginResponse(owner.getId(), owner.getCafe().getId());
    }
}
