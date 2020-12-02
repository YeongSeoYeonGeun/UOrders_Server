package com.example.uorders.api;

import com.example.uorders.Service.*;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.api.constants.WeChat;
import com.example.uorders.domain.*;
import com.example.uorders.dto.order.AcceptOrderRequest;
import com.example.uorders.dto.order.CreateOrderRequest;
import com.example.uorders.dto.order.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        Order order = orderService.createOrder(user, cafe, orderDateTime, cart.getTotalPrice());
        Set<OrderMenu> orderMenus = orderMenuService.createOrderMenus(cart, order);

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

    /**
     *  결제
     */
    @GetMapping("/pay")
    public ResponseEntity<Message> payApi(@RequestHeader("userIndex") Long userId) {
        User user = userService.findById(userId);

        String appid = WeChat.appid;
        String secret = WeChat.secret;
        Integer amount = user.getCart().getTotalPrice();
        String js_code = user.getCode();

        HashMap<String, Object> result = new HashMap<String, Object>();

        String jsonInString = "";

        String openid = "";
        String session_key;
        Integer unionid;
        Integer errcode;
        String errmsg;

        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectionRequestTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "https://api.weixin.qq.com/sns/jscode2session";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"appId="+ appid +"&secret="+
                    secret + "&js_code="+ js_code +"&grant_type=authorization_code").build();

            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue());
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody());

            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writeValueAsString(resultMap.getBody());

            openid = (String)resultMap.getBody().get("openId");
            System.out.println("opneiddddddddddddddddddddddddddddddddddddddddddd: " + openid);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body", e.getStatusText());
            System.out.println("dfdfdfd");
            System.out.println(e.toString());
        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body", "exception 오류");
            System.out.println(e.toString());
        }

        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectionRequestTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("openId", openid);
            map.add("amount", amount);
            HttpEntity<Object> entity = new HttpEntity<>(map);

            String url = "https://open.ifprod.cc/api/v1/shoots/pay";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"appid="+ appid +"&secret="+
                    secret +"&js_code="+ js_code +"&grant_type=authorization_code").build();

            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue());
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody());

            Message message = new Message(StatusCode.OK, ResponseMessage.PAY_SUCCESS, result);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (RestClientException e) {
            e.printStackTrace();
        }

        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.PAY_FAIL);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

    }
}
