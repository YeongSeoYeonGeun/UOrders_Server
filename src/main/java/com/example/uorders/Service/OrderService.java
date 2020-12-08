package com.example.uorders.Service;

import com.example.uorders.api.OrderController;
import com.example.uorders.api.constants.WeChat;
import com.example.uorders.domain.*;
import com.example.uorders.dto.order.AcceptOrderRequest;
import com.example.uorders.dto.order.OrderDto;
import com.example.uorders.dto.order.PayReqeust;
import com.example.uorders.dto.order.PayResponse;
import com.example.uorders.exception.OrderNotFoundException;
import com.example.uorders.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.uorders.domain.OrderStatus.COMPLETED;
import static com.example.uorders.domain.OrderStatus.PLACED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CafeRepository cafeRepository;
    private final CartService cartService;
    private final OrderMenuService orderMenuService;


    @Transactional
    public void saveOrder(Order order){ orderRepository.save(order); }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException(orderId));
    }

    public Set<Order> findOrderByUser(User user){
        return orderRepository.findByUser(user);
    }

    /** 주문 */
    @Transactional
    public Order createOrder(User user, Cafe cafe, LocalDateTime dateTime, int totalPrice){

        Order order = Order.builder()
                .user(user)
                .cafe(cafe)
                .orderMenuSet(new HashSet<>())
                .orderTime(dateTime)
                .orderStatus(PLACED)
                .estimateTime(dateTime.plusMinutes(20))
                .acceptTime(null)
                .totalPrice(totalPrice)
                .build();

        saveOrder(order);
        return order;
    }

    /** 주문 접수 */
    @Transactional
    public void acceptOrder(Order order, AcceptOrderRequest request) {

        if (order.getStatus() != PLACED) {
            throw new IllegalStateException("이미 승인되었거나 완료된 주문입니다.");
        }
        order.setEstimateTime(LocalDateTime.now().plusMinutes(request.getEstimateTime()));
        order.setStatus(OrderStatus.ACCEPTED);

        saveOrder(order);
    }

    /** 주문 내역 조회 */
    public List<OrderDto> readOrderHistory(Long userId, String languageCode) {

        List<Order> orders = orderRepository.findOrderByUserDESC(userId);
        List<OrderDto> orderListDtoList = new ArrayList<>();
        for(Order order: orders) {
            OrderDto orderListDto = OrderDto.of(order, languageCode);
            orderListDtoList.add(orderListDto);
        }

        return orderListDtoList;
    }

    /** 결제 */
    public PayResponse pay(User user) {

        String appid = WeChat.appid;
        String secret = WeChat.secret;
        int amount = user.getCart().getTotalPrice();
        String js_code = user.getCode();
        String grantType = "authorization_code";
        String openid = "";

        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectionRequestTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String url = "https://api.weixin.qq.com/sns/jscode2session";

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("appid", appid)
                    .queryParam("secret", secret)
                    .queryParam("js_code", js_code)
                    .queryParam("grant_type", grantType);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            HttpEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class);

            String responseBody = response.getBody();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse( responseBody );
            JSONObject jsonObj = (JSONObject) obj;
            openid = (String) jsonObj.get("openid");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectionRequestTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            PayReqeust payRequestBody = PayReqeust.builder().openId(openid).amount(amount).build();

            String url = "https://open.ifprod.cc/api/v1/shoots/pay";

            ResponseEntity<String> resultMap = restTemplate.postForEntity(url, payRequestBody, String.class);

            String responseBody = resultMap.getBody();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse( responseBody );
            JSONObject jsonObj = (JSONObject) obj;
            String timestamp = (String) jsonObj.get("timeStamp");
            String nonceStr = (String) jsonObj.get("nonceStr");
            String package_ = (String) jsonObj.get("package");
            String signType = (String) jsonObj.get("signType");
            String tId = (String) jsonObj.get("tId");
            String paySign = (String) jsonObj.get("paySign");

            return new PayResponse(timestamp, nonceStr, package_, signType, tId, paySign);

        } catch (RestClientException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public void UpdateOrderOwner(Order order){
        order.setStatus(COMPLETED);
        saveOrder(order);
    }
}
