package com.example.uorders.api;

import com.example.uorders.Service.CartMenuService;
import com.example.uorders.Service.CartService;
import com.example.uorders.Service.MenuService;
import com.example.uorders.Service.UserService;
import com.example.uorders.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        User user = userService.findOne(id).orElse(null);
        if(user == null){
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        Cart cart = userService.findCart(id);

        if(cart == null){
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        Set<CartMenu> findCartMenus = cart.getCartMenus();

        //엔티티 -> DTO 변환
        List<CartMenuDto> collect = findCartMenus.stream()
                .map(cm -> new CartMenuDto(cm.getId(), cm.getMenu().getId(), cm.getMenu().getName(), cm.getMenuTemperature(), cm.getMenuSize(), cm.getMenuTakeType(), cm.getCount(), cm.getOrderPrice()))
                .collect(Collectors.toList());

        // 장바구니 비어있음
        String cafeName;
        Long cafeIndex;
        if(collect.size() == 0) { cafeName = ""; cafeIndex = 0L;}
        else {
            Cafe cafe = menuService.findOne(collect.get(0).menuIndex).orElse(null).getCafe();
            cafeName = cafe.getName();
            cafeIndex = cafe.getId();
        }

        int totalPrice = cart.getTotalPrice();
        Result result = new Result(cafeIndex, cafeName, collect, totalPrice);

        MessageWithData message = new MessageWithData();
        message.setStatus(StatusCode.OK);
        message.setMessage(ResponseMessage.READ_CARTMENU);
        message.setData(result);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }


    /**
     *  장바구니 메뉴 추가
     */
    @PostMapping("/users/cart")
    public ResponseEntity<Message> addCartMenu(@RequestHeader("userIndex") Long id, @RequestBody CreateCartMenuRequest request) {

        User user = userService.findOne(id).orElse(null);
        if(user == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        Cart cart = user.getCart();
        Menu menu = menuService.findOne(request.getMenuIndex()).orElse(null);
        if(menu == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_MENU);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

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

                    System.out.println("aaaaaaa");
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

        Message message = new Message();
        message.setStatus(StatusCode.OK);
        message.setMessage(ResponseMessage.CREATE_CARTMENU);

        return new ResponseEntity<>(message, null, HttpStatus.OK);
    }

    @Data
    static class CreateCartMenuRequest {
        private Long menuIndex;
        private String menuName;
        private int menuCount;
        private MenuTemperature menuTemperature;
        private MenuSize menuSize;
        private MenuTakeType menuTakeType;
        private int menuTotalPrice;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private Long cafeIndex;
        private String cafeName;
        private T cartInfo;
        private int totalPrice;
    }

    @Data
    @AllArgsConstructor
    static
    class CartMenuDto {
        private Long cartMenuIndex;
        private Long menuIndex;
        private String menuName;
        private MenuTemperature menuTemperature;
        private MenuSize menuSize;
        private MenuTakeType menuTakeType;
        private int menuCount;
        private int menuPrice;
    }

    /**
     *  장바구니 메뉴 삭제
     */
    @DeleteMapping("users/cart")
    public ResponseEntity<Message> deleteCartMenu(@RequestHeader("userIndex") Long  userId, @RequestHeader("cartMenuIndex") Long cartMenuId) {

        User user = userService.findOne(userId).orElse(null);
        if(user == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        Cart cart = userService.findCart(userId);
        CartMenu cartMenu = cartMenuService.findOne(cartMenuId).orElse(null);
        if(cartMenu == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER_OR_MENU);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        cartMenuService.deleteOne(cartMenu);

        Message message = new Message();
        message.setStatus(StatusCode.OK);
        message.setMessage(ResponseMessage.DELETE_CARTMENU);

        return new ResponseEntity<>(message, null, HttpStatus.OK);
    }
}
