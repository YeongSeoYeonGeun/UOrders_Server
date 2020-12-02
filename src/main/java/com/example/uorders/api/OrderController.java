package com.example.uorders.api;

import com.example.uorders.Service.*;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.*;
import com.example.uorders.dto.order.AcceptOrderRequest;
import com.example.uorders.dto.order.CreateOrderRequest;
import com.example.uorders.dto.order.OrderDto;
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
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMenuService orderMenuService;
    private final UserService userService;
    private final CafeService cafeService;
    private final CartService cartService;
    private final OwnerService ownerService;

    /**
     *  주문 추가
     */
    @PostMapping
    public ResponseEntity<Message> createOrderApi(@RequestBody CreateOrderRequest createOrderRequest) {
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
    @GetMapping
    public ResponseEntity<Message> readOrderApi(@RequestHeader("userIndex") Long id) {
        User user = userService.findById(id);

        Set<Order> orders = userService.findOrderSet(id);

        List<OrderDto> orderListDtoList = new ArrayList<>();

        for(Order order: orders) {
            OrderDto orderListDto = OrderDto.of(order);
            orderListDtoList.add(orderListDto);
        }

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_ORDER_LIST, orderListDtoList);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     *  주문 접수
     */
    @PutMapping("{orderIndex}")
    public ResponseEntity<Message> acceptOrder(@RequestHeader("ownerIndex") Long ownerId, @RequestHeader("cafeIndex") Long cafeId, @PathVariable("orderIndex") Long orderId, @RequestBody AcceptOrderRequest request) {
        Owner owner = ownerService.findById(ownerId);
        Cafe cafe = cafeService.findById(cafeId);
        Order order = orderService.findById(orderId);

        orderService.acceptOrder(order, request);

        Message message = new Message(StatusCode.OK, ResponseMessage.ACCEPT_ORDER);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
