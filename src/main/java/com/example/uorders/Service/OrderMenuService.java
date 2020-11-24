package com.example.uorders.Service;

import com.example.uorders.domain.OrderMenu;
import com.example.uorders.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderMenuService {

    private final OrderMenuRepository orderMenuRepository;

    @Transactional
    public void saveOrderMenu(OrderMenu orderMenu) { orderMenuRepository.save(orderMenu); }
}
