package com.example.uorders.api;

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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;
    private final UserService userService;
    private final MenuService menuService;

    /**
     * 장바구니 메뉴 조회
     */
    @GetMapping("/users/cart")
    public ResponseEntity<Message> getCartMenu(@RequestHeader("userIndex") Long id) {

        User user = userService.findOne(id).orElse(null);
        if(user==null){
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        Cart cart = user.getCart();
        List<CartMenu> findCartMenus = cart.getCartMenus();

        //엔티티 -> DTO 변환
        List<CartMenuDto> collect = findCartMenus.stream()
                .map(cm -> new CartMenuDto(cm.getId(), cm.getMenu().getName(), cm.getMenuTemperature(), cm.getMenuSize(), cm.getMenuTakeType(), cm.getCount(), cm.getOrderPrice()))
                .collect(Collectors.toList());

        int totalPrice = cart.getTotalPrice();
        Result result = new Result(collect, totalPrice);

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

        // Total이 order로 바뀌어야??
        // TotalPrice 사용 안하는 중
        CartMenu cartMenu = CartMenu.createCartMenu(menu, menu.getPrice(), request.getMenuCount(), request.getMenuTemperature(), request.getMenuSize(), request.getMenuTakeType());

        cartMenu.setCart(cart);
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
    class Result<T> {
        private T cartInfo;
        private int totalPrice;
    }

    @Data
    @AllArgsConstructor
    class CartMenuDto {
        private Long menuIndex;
        private String menuName;
        private MenuTemperature menuTemperature;
        private MenuSize menuSize;
        private MenuTakeType menuTakeType;
        private int menuCount;
        private int menuPrice;
    }
}
