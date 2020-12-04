package com.example.uorders.dto.cart;

import com.example.uorders.api.constants.Text;
import com.example.uorders.domain.Cart;
import com.example.uorders.domain.CartMenu;
import com.example.uorders.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {

    private String cartText;
    private String initializeCartText;
    private Long cartIndex;
    private Long cafeIndex;
    private String cafeName;
    private List<Cart_cartMenuDto> cartMenuInfo;
    private int totalPrice;
    private String payText;

    public static CartDto of(Cart cart, Long cafeIndex, String cafeName, String languageCode) {
        String cartText = Text.cart(languageCode);
        String initializeCartText = Text.initializeCart(languageCode);
        String payText = Text.payMenu(cart.getTotalPrice(), languageCode);

        List<Cart_cartMenuDto> cartMenuDtoList = new ArrayList<>();
        for (CartMenu cartMenu : cart.getCartMenuSet()) {
            Cart_cartMenuDto cartMenuDto = Cart_cartMenuDto.of(cartMenu, languageCode);
            cartMenuDtoList.add(cartMenuDto);
        }

        return new CartDto(cartText, initializeCartText, cart.getId(), cafeIndex, cafeName, cartMenuDtoList, cart.getTotalPrice(), payText);
    }
}
