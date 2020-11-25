package com.example.uorders.api;

import com.example.uorders.Service.*;
import com.example.uorders.domain.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final OrderMenuService orderMenuService;
    private final UserService userService;
    private final CafeService cafeService;
    private final CartService cartService;

    /**
     *  주문 추가
     */
    @PostMapping("/orders")
    public ResponseEntity<Message> createOrderApi(@RequestBody createOrderRequest createOrderRequest) {
        Long userId = createOrderRequest.userIndex;
        Long cafeId = createOrderRequest.cafeIndex;
        LocalDateTime orderDateTime = createOrderRequest.orderDateTime;

        User user = userService.findOne(userId).orElse(null);
        if(user == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        Cafe cafe = cafeService.findOne(cafeId).orElse(null);
        if(cafe==null){
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_CAFE);
            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        // 주문 추가
        Cart cart = userService.findCart(userId);

        Set<OrderMenu> orderMenus = orderMenuService.createOrderMenus(cart);
        Order order = orderService.createOrder(user, cafe, cart, orderDateTime, orderMenus);

        for(OrderMenu orderMenu : orderMenus) {
            orderMenu.setOrder(order);
        }

        orderService.saveOrder(order);

        // 장바구니 비우기
        cartService.initializeCart(cart);

        Message message = new Message();
        message.setStatus(StatusCode.OK);
        message.setMessage(ResponseMessage.CREATE_ORDER);
        return new ResponseEntity<>(message, null, HttpStatus.OK);
    }

    @Data
    static class createOrderRequest {
        Long userIndex;
        Long cafeIndex;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime orderDateTime;
    }

    /**
     *  주문 내역 조회
     */
    @GetMapping("/orders")
    public ResponseEntity<Message> readOrderApi(@RequestHeader("userIndex") Long id) {
        User user = userService.findOne(id).orElse(null);
        if(user == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        Set<Order> orders = userService.findOrders(id);

        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o.getId(), o.getCafe().getName(), o.getDateTime(),
                        o.getOrderMenus().stream()
                                .map(om -> new MenuDto(om.getMenu().getName(), om.getCount(), om.getOrderPrice()))
                                .collect(Collectors.toList())
                        ,o.getTotalPrice()))
                .collect(Collectors.toList());

        MessageWithData message = new MessageWithData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusCode.OK);
        message.setMessage(ResponseMessage.READ_ORDER_LIST);
        message.setData(new Result(collect));

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T orderInfo;
    }

    @Data
    @AllArgsConstructor
    static class MenuDto {
        private String menuName;
        private int orderCount;
        private int orderPrice;
    }

    @Data
    @AllArgsConstructor
    static class OrderDto {
        private Long orderIndex;
        private String cafeName;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime orderDate;
        private List<MenuDto> menuInfo;
        private int totalPrice;
    }
}
