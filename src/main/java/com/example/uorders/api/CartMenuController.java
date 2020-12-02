package com.example.uorders.api;

import com.example.uorders.Service.*;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.*;
import com.example.uorders.dto.cartMenu.CartMenuRequest;
import com.example.uorders.exception.CafeNotFoundException;
import com.example.uorders.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/cartMenu")

public class CartMenuController {

    private final UserService userService;
    private final CartMenuService cartMenuService;
    private final CafeService cafeService;
    private final MenuService menuService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    /**
     *  장바구니 메뉴 삭제
     */
    @DeleteMapping
    public ResponseEntity<Message> deleteCartMenu(@RequestHeader("userIndex") Long  userId, @RequestHeader("cartMenuIndex") Long cartMenuId) {

        User user = userService.findById(userId);

        Cart cart = userService.findCart(userId);
        CartMenu cartMenu = cartMenuService.findById(cartMenuId);

        cartMenuService.deleteOne(cartMenu);

        Message message = new Message(StatusCode.OK, ResponseMessage.DELETE_CARTMENU);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    /**
     *  장바구니 메뉴 추가
     */
    @PostMapping
    public ResponseEntity<Message> addCartMenu(@RequestHeader("userIndex") Long userId, @RequestBody CartMenuRequest.CreateCartMenuRequest request) {

        User user = userService.findById(userId);
        Cafe cafe = cafeService.findById(request.getCafeIndex());

        Cart cart = cartRepository.findByUser(user);


        if(cart.getCartMenuSet().size() != 0 && cart.getCafe() != cafe) { // 장바구니가 비어있지않으면서 장바구니에 담겨있는 메뉴와 다른 카페의 메뉴를 담은 경우
            
            // 장바구니 초기화
            cartService.initializeCart(cart);
        }

        Menu menu = menuService.findById(request.getMenuIndex());

        Set<CartMenu> findCartMenus = cartService.findCartMenus(cart, menu);
        boolean duplicateFlag = false;

        System.out.println(findCartMenus.size());
        if(findCartMenus.size() == 0) // 장바구니에 해당 메뉴가 없다면
        {
            // 장바구니_메뉴 생성
            CartMenu.builder()
                    .menu(menu)
                    .orderPrice(menu.getPrice()*request.getMenuCount())
                    .count(request.getMenuCount())
                    .menuTemperature(request.getMenuTemperature())
                    .menuSize(request.getMenuSize())
                    .menuTakeType(request.getMenuTakeType())
                    .cart(cart)
                    .build();
        }
        else { // 장바구니에 해당 메뉴가 있다면

            for(CartMenu cartMenu: findCartMenus) { // 해당 장바구니 메뉴 중

                // 사이즈, 온도, 포장 여부까지 동일한 메뉴가 있다면
                if(cartMenu.getMenuSize() == request.getMenuSize() && cartMenu.getMenuTemperature() == request.getMenuTemperature() && cartMenu.getMenuTakeType().equals(request.getMenuTakeType()))
                {
                    // 메뉴 count, TotalPrice만 증가
                    cartMenu.setCount(cartMenu.getCount() + request.getMenuCount());
                    cartMenu.setOrderPrice(cartMenu.getOrderPrice() + request.getMenuTotalPrice());

                    duplicateFlag = true;
                    break;
                }
            }

            if(!duplicateFlag) // 장바구니에 메뉴가 있지만 사이즈, 온도, 포장 여부가 다르다면
            {
                // 장바구니_메뉴 생성
                CartMenu.builder()
                        .menu(menu)
                        .orderPrice(menu.getPrice()*request.getMenuCount())
                        .count(request.getMenuCount())
                        .menuTemperature(request.getMenuTemperature())
                        .menuSize(request.getMenuSize())
                        .menuTakeType(request.getMenuTakeType())
                        .cart(cart)
                        .build();
            }

        }

        cart.setCafe(cafe);
        cartService.saveCart(cart);

        Message message = new Message(StatusCode.OK, ResponseMessage.CREATE_CARTMENU);
        return new ResponseEntity<>(message,  HttpStatus.OK);
    }
}
