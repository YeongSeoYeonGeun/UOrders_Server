package com.example.uorders.dto.order;


import com.example.uorders.api.constants.Text;
import com.example.uorders.domain.Menu;
import com.example.uorders.dto.cafe.OwnerCafeDetail_menuDto;
import com.example.uorders.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor

public class OwnerOrderDetail {

    private String cafeName;
    private String cafeLocaion;
    private List<OwnerOrderDetail_orderInfo> orderInfo;

}
