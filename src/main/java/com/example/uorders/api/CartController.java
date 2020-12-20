package com.example.uorders.api;

import com.example.uorders.Service.*;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.*;
import com.example.uorders.dto.cart.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final MenuService menuService;
    private final CartMenuService cartMenuService;
    private final CafeService cafeService;

    /** 장바구니 조회 */
    @GetMapping
    public ResponseEntity<Message> readCart(@RequestHeader("userIndex") Long userId) {
        User user = userService.findById(userId);
        Cart cart = userService.findCart(userId);

        CartDto response = cartService.readCart(user, cart);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CART, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /** 장바구니 비우기 */
    @DeleteMapping
    public ResponseEntity<Message> initializeCart(@RequestHeader("userIndex") Long userId, @RequestHeader("cartIndex") Long cartId) {
        User user = userService.findById(userId);
        Cart cart = cartService.findById(cartId);
        cartService.initializeCart(cart);
        Message message = new Message(StatusCode.OK, ResponseMessage.INITIALIZE_CART);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
