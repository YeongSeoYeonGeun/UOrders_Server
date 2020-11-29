package com.example.uorders.api;

import com.example.uorders.Service.*;
import com.example.uorders.api.constants.Message;
import com.example.uorders.domain.*;
import com.example.uorders.dto.order.OrderDto;
import com.example.uorders.dto.order.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<Message> createOrderApi(@RequestBody OrderRequest.CreateOrderRequest createOrderRequest) {
        Long userId = createOrderRequest.getUserIndex();
        Long cafeId = createOrderRequest.getCafeIndex();
        LocalDateTime orderDateTime = createOrderRequest.getOrderDateTime();

        User user = userService.findById(userId);
        Cafe cafe = cafeService.findById(cafeId);

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

        Message message = new Message(StatusCode.OK, ResponseMessage.CREATE_ORDER);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     *  주문 내역 조회
     */
    @GetMapping("/orders")
    public ResponseEntity<Message> readOrderApi(@RequestHeader("userIndex") Long id) {
        User user = userService.findById(id);

        Set<Order> orders = userService.findOrders(id);

        List<OrderDto> orderListDtoList = new ArrayList<>();

        for(Order order: orders) {
            OrderDto orderListDto = OrderDto.of(order);
            orderListDtoList.add(orderListDto);
        }

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_ORDER_LIST, orderListDtoList);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
