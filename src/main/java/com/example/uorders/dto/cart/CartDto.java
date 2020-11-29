package com.example.uorders.dto.cart;

import com.example.uorders.domain.Cart;
import com.example.uorders.domain.CartMenu;
import com.example.uorders.dto.cartMenu.CartMenuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter @AllArgsConstructor
public class CartDto {

    private Long cartIndex;
    private Long cafeIndex;
    private String cafeName;
    private List<Cart_cartMenuDto> cartMenuInfo;
    private int totalPrice;

    public static CartDto of(Cart cart, Long cafeIndex, String cafeName) {

        List<Cart_cartMenuDto> cartMenuDtoList = new ArrayList<>();

        for (CartMenu cartMenu : cart.getCartMenus()) {
            Cart_cartMenuDto cartMenuDto = Cart_cartMenuDto.of(cartMenu);
            cartMenuDtoList.add(cartMenuDto);
        }

        return new CartDto(cart.getId(), cafeIndex, cafeName, cartMenuDtoList, cart.getTotalPrice());
    }
}
