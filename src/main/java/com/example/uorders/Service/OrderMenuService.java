package com.example.uorders.Service;

import com.example.uorders.domain.Cart;
import com.example.uorders.domain.CartMenu;
import com.example.uorders.domain.Order;
import com.example.uorders.domain.OrderMenu;
import com.example.uorders.exception.OrderMenuNotFoundException;
import com.example.uorders.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderMenuService {

    private final OrderMenuRepository orderMenuRepository;

    public OrderMenu findById(Long orderMenuId) {
        return orderMenuRepository.findById(orderMenuId).orElseThrow(()->new OrderMenuNotFoundException(orderMenuId));
    }

    @Transactional
    public void saveOrderMenu(OrderMenu orderMenu) { orderMenuRepository.save(orderMenu); }

    @Transactional
    public Set<OrderMenu> createOrderMenus(Cart cart, Order order) {

        Set<OrderMenu> orderMenus = new HashSet<>();
        Set<CartMenu> cartMenus = cart.getCartMenuSet();

        for(CartMenu cartMenu: cartMenus) {
            OrderMenu orderMenu = OrderMenu.builder()
                    .menu(cartMenu.getMenu())
                    .menuSize(cartMenu.getMenuSize())
                    .menuTakeType(cartMenu.getMenuTakeType())
                    .menuTemperature(cartMenu.getMenuTemperature())
                    .order(order)
                    .orderPrice(cartMenu.getOrderPrice())
                    .count(cartMenu.getCount())
                    .build();

            orderMenus.add(orderMenu);
            saveOrderMenu(orderMenu);
        }

        return orderMenus;
    }
}
