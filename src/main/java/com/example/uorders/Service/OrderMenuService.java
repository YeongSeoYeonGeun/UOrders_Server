package com.example.uorders.Service;

import com.example.uorders.domain.Cart;
import com.example.uorders.domain.CartMenu;
import com.example.uorders.domain.Order;
import com.example.uorders.domain.OrderMenu;
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

    @Transactional
    public void saveOrderMenu(OrderMenu orderMenu) { orderMenuRepository.save(orderMenu); }

    @Transactional
    public Set<OrderMenu> createOrderMenus(Cart cart) {

        Set<OrderMenu> orderMenus = new HashSet<>();
        Set<CartMenu> cartMenus = cart.getCartMenus();

        for(CartMenu cartMenu: cartMenus) {
            OrderMenu orderMenu = OrderMenu.createOrderMenu(cartMenu.getMenu(), cartMenu.getOrderPrice(), cartMenu.getCount());
            orderMenus.add(orderMenu);
            saveOrderMenu(orderMenu);
        }

        return orderMenus;
    }
}
