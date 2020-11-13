package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Cart;
import com.example.uorders.domain.Order;
import com.example.uorders.domain.User;
import com.example.uorders.repository.CafeRepository;
import com.example.uorders.repository.CartRepository;
import com.example.uorders.repository.OrderRepository;
import com.example.uorders.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CafeRepository cafeRepository;


    /**
     * 주문
     */
    @Transactional
    public Long order(Long userId, Long cafeId , Long cartId){
        // 엔티티 조회
        User user = userRepository.findOne(userId);
        Cafe cafe = cafeRepository.findOne(cafeId);
        Cart cart = cartRepository.findOne(cartId);

        // 주문 생성
        Order order = Order.createOrder(user, cafe, cart);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     *  주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /**
     *  주문 승인
     */
    @Transactional
    public void acceptOrder(Long orderId, LocalDateTime estimatedTime) {
        Order order = orderRepository.findOne(orderId);
        order.accept(estimatedTime);
    }

    /**
     *  주문 거절
     */
    @Transactional
    public void rejectOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.reject();
    }
}
