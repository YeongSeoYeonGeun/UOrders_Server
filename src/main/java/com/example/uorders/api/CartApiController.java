package com.example.uorders.api;

import com.example.uorders.Service.CartMenuService;
import com.example.uorders.Service.CartService;
import com.example.uorders.Service.MenuService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.*;
import com.example.uorders.dto.cart.CartDto;
import com.example.uorders.dto.cartMenu.CartMenuRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;
    private final UserService userService;
    private final MenuService menuService;
    private final CartMenuService cartMenuService;

    /**
     * 장바구니 메뉴 조회
     */
    @GetMapping("/users/cart")
    public ResponseEntity<Message> getCartMenu(@RequestHeader("userIndex") Long id) {

        User user = userService.findById(id);
        Cart cart = userService.findCart(id);

        Set<CartMenu> findCartMenus = cart.getCartMenus();

        // 장바구니 비어있음
        String cafeName = "";
        Long cafeIndex = 0L;
        if(findCartMenus.size() == 0) { cafeName = ""; cafeIndex = 0L;}
        else {
            //Cafe cafe = menuService.findById(findCartMenus.get.get(0).menuIndex).getCafe();
            //cafeName = cafe.getName();
            //cafeIndex = cafe.getId();
        }

        CartDto response = CartDto.of(cart, cafeIndex, cafeName);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CARTMENU, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    /**
     *  장바구니 메뉴 추가
     */
    @PostMapping("/users/cart")
    public ResponseEntity<Message> addCartMenu(@RequestHeader("userIndex") Long id, @RequestBody CartMenuRequest.CreateCartMenuRequest request) {

        User user = userService.findById(id);

        Cart cart = user.getCart();
        Menu menu = menuService.findById(request.getMenuIndex());

        Set<CartMenu> findCartMenus = cartService.findCartMenus(cart, menu);
        boolean duplicateFlag = false;

        if(findCartMenus.size() == 0) // 장바구니에 해당 메뉴가 없다면
        {
            // 장바구니_메뉴 생성
            CartMenu.createCartMenu(menu, menu.getPrice()*request.getMenuCount(), request.getMenuCount(), request.getMenuTemperature(), request.getMenuSize(), request.getMenuTakeType(), cart);
        }
        else { // 장바구니에 해당 메뉴가 있다면

            for(CartMenu cartMenu: findCartMenus) { // 해당 장바구니 메뉴 중



                // 사이즈, 온도, 포장 여부까지 동일한 메뉴가 있다면
                if(cartMenu.getMenuSize() == request.getMenuSize() && cartMenu.getMenuTemperature() == request.getMenuTemperature() && cartMenu.getMenuTakeType() == request.getMenuTakeType())
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
                CartMenu.createCartMenu(menu, menu.getPrice()*request.getMenuCount(), request.getMenuCount(), request.getMenuTemperature(), request.getMenuSize(), request.getMenuTakeType(), cart);
            }

        }

       cartService.saveCart(cart);

        Message message = new Message(StatusCode.OK, ResponseMessage.CREATE_CARTMENU);
        return new ResponseEntity<>(message,  HttpStatus.OK);
    }

    /**
     *  장바구니 메뉴 삭제
     */
    @DeleteMapping("users/cart")
    public ResponseEntity<Message> deleteCartMenu(@RequestHeader("userIndex") Long  userId, @RequestHeader("cartMenuIndex") Long cartMenuId) {

        User user = userService.findById(userId);

        Cart cart = userService.findCart(userId);
        CartMenu cartMenu = cartMenuService.findById(cartMenuId);

        cartMenuService.deleteOne(cartMenu);

        Message message = new Message(StatusCode.OK, ResponseMessage.DELETE_CARTMENU);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
