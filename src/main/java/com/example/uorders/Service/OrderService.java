package com.example.uorders.Service;

import com.example.uorders.domain.*;
import com.example.uorders.dto.order.AcceptOrderRequest;
import com.example.uorders.exception.OrderNotFoundException;
import com.example.uorders.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    /**
     * 주문
     */
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

    /**
     *  주문 접수
     */
    @Transactional
    public void acceptOrder(Order order, AcceptOrderRequest request) {

        if (order.getStatus() != PLACED) {
            throw new IllegalStateException("이미 승인되었거나 완료된 주문입니다.");
        }
        order.setEstimateTime(LocalDateTime.now().plusMinutes(request.getEstimateTime()));
        order.setStatus(OrderStatus.ACCEPTED);

        saveOrder(order);
    }
}
