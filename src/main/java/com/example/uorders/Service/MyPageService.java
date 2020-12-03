package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Order;
import com.example.uorders.domain.OrderMenu;
import com.example.uorders.dto.myPage.myPageDto;
import com.example.uorders.dto.myPage.myPage_cafeDto;
import com.example.uorders.dto.myPage.myPage_orderDto;
import com.example.uorders.dto.myPage.myPage_order_menuDto;
import com.example.uorders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final OrderRepository orderRepository;
    private final CafeService cafeService;

    public myPageDto myPage(Long cafeId) {
        Cafe cafe = cafeService.findById(cafeId);
        LocalDateTime now = LocalDateTime.now();

        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        Set<Order> todayOrderSet = orderRepository.findTodayOrder(cafeId, year, month, day);

        List<myPage_orderDto> orderDtoList = new ArrayList<>();
        for (Order todayOrder : todayOrderSet){

            LocalDateTime orderDateTime = todayOrder.getOrderTime();
            String orderTime = "";
            int hour = orderDateTime.getHour();
            int minute = orderDateTime.getMinute();

            if(hour >= 12) {
                if(hour != 12) hour = hour - 12;
                orderTime = "오후 " + hour + "시 " + minute + "분";
            } else {
                if(hour == 0) hour = hour + 12;
                orderTime = "오전 " + hour + "시 " + minute + "분";
            }

            List<myPage_order_menuDto> orderMenuDtoList = new ArrayList<>();
            for(OrderMenu orderMenu: todayOrder.getOrderMenuSet()) {
                orderMenuDtoList.add(myPage_order_menuDto.of(orderMenu));
            }
            orderDtoList.add(myPage_orderDto.of(todayOrder, orderTime, orderMenuDtoList));
        }
        return new myPageDto(myPage_cafeDto.of(cafe), orderDtoList);
    }
}
