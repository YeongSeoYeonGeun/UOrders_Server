package com.example.uorders.Service;

import com.example.uorders.domain.*;
import com.example.uorders.exception.OrderNotFoundException;
import com.example.uorders.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * 주문
     */
    @Transactional
    public Order createOrder(User user, Cafe cafe, Cart cart, LocalDateTime dateTime,Set<OrderMenu> orderMenus){

        Order order = Order.createOrder(user, cafe, cart, dateTime, orderMenus);
        saveOrder(order);
        return order;
    }


    /**
     *  주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.cancel();
    }

    /**
     *  주문 승인
     */
    @Transactional
    public void acceptOrder(Long orderId, LocalDateTime estimatedTime) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.accept(estimatedTime);
    }

    /**
     *  주문 거절
     */
    @Transactional
    public void rejectOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.reject();
    }
}
