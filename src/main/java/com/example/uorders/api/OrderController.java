package com.example.uorders.api;

import com.example.uorders.Service.*;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.api.constants.WeChat;
import com.example.uorders.domain.*;
import com.example.uorders.dto.order.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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

    /** 주문 추가 */
    @PostMapping
    public ResponseEntity<Message> createOrderApi(@RequestBody CreateOrderRequest createOrderRequest) {
        Long userId = createOrderRequest.getUserIndex();
        Long cafeId = createOrderRequest.getCafeIndex();
        LocalDateTime orderDateTime = createOrderRequest.getOrderDateTime();

        ObjectMapper objectMapper = new ObjectMapper();
        User user = userService.findById(userId);
        Cafe cafe = cafeService.findById(cafeId);
        Owner owner = cafe.getOwner();

        // 주문 추가
        Cart cart = userService.findCart(userId);

        Order order = orderService.createOrder(user, cafe, orderDateTime, cart.getTotalPrice());
        Set<OrderMenu> orderMenus = orderMenuService.createOrderMenus(cart, order);

        orderService.saveOrder(order);

        // 장바구니 비우기
        cartService.initializeCart(cart);

        // 점주용 앱에 알림 푸시
        FirebaseCloudMessageService firebaseCloudMessageService = new FirebaseCloudMessageService(objectMapper);
        try {
            firebaseCloudMessageService.sendMessageTO(owner.getDeviceToken(),"UOrders","주문이 도착했습니다!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CreateOrderResponse response = CreateOrderResponse.of(order, user.getLanguageCode());
        Message message = new Message(StatusCode.OK, ResponseMessage.CREATE_ORDER, response);
        return new ResponseEntity<>(message, HttpStatus.OK);


    }

    /** 주문 내역 조회 */
    @GetMapping
    public ResponseEntity<Message> readOrderHistory(@RequestHeader("userIndex") Long userId) {
        User user = userService.findById(userId);
        List<OrderDto> orderDtoList = orderService.readOrderHistory(userId, user.getLanguageCode());
        ReadOrderResponse response = ReadOrderResponse.of(user.getLanguageCode(), orderDtoList);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_ORDER_LIST, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /** 주문 접수 */
    @PutMapping("/{orderIndex}")
    public ResponseEntity<Message> acceptOrder(@RequestHeader("ownerIndex") Long ownerId, @RequestHeader("cafeIndex") Long cafeId, @PathVariable("orderIndex") Long orderId, @RequestBody AcceptOrderRequest request) {
        Owner owner = ownerService.findById(ownerId);
        Cafe cafe = cafeService.findById(cafeId);
        Order order = orderService.findById(orderId);

        orderService.acceptOrder(order, request);

        Message message = new Message(StatusCode.OK, ResponseMessage.ACCEPT_ORDER);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /** 결제 */
    @GetMapping("/pay")
    public ResponseEntity<Message> payApi(@RequestHeader("userIndex") Long userId) {

        User user = userService.findById(userId);
        PayResponse response = orderService.pay(user);

        if(response == null) {
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.PAY_FAIL);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        Message message = new Message(StatusCode.OK, ResponseMessage.PAY_SUCCESS, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /** 점주용 주문 관리 조회 */
    @GetMapping("/main")
    public ResponseEntity<Message> readOrderOwner(@RequestHeader("ownerIndex") Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        Cafe cafe = cafeService.findById(owner.getCafe().getId());

        OwnerOrderDetail result = OwnerOrderDetail.of(cafe);
        Message message = new Message(StatusCode.OK, ResponseMessage.READ_ORDER_LIST, result);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //주문 완료 처리//
    @PutMapping("/complete")
    public ResponseEntity<Message> updateOrderOwner(@RequestHeader("orderIndex") Long orderId ){
        Order order = orderService.findById(orderId);

        orderService.UpdateOrderOwner(order);

        Message message = new Message(StatusCode.OK, ResponseMessage.UPDATE_OWNER_ORDER);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
