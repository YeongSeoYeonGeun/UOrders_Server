package com.example.uorders.api;

import com.example.uorders.Service.OrderService;
import com.example.uorders.Service.OwnerService;
import com.example.uorders.Service.CafeService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Order;
import com.example.uorders.domain.OrderMenu;
import com.example.uorders.domain.Owner;
import com.example.uorders.dto.cafe.OwnerCafe;
import com.example.uorders.dto.cafe.OwnerCafeDetail;
import com.example.uorders.dto.order.OrderAlarmDto;
import com.example.uorders.dto.owner.OwnerLoginRequest;
import com.example.uorders.dto.owner.OwnerLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

import static com.example.uorders.domain.OrderStatus.PLACED;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/owner")
public class OwnerController {

    private final OwnerService ownerService;
    private final CafeService cafeService;
    private final OrderService orderService;

    /** 로그인 */
    public ResponseEntity<Message> loginForOwner(@RequestBody OwnerLoginRequest request) {
        OwnerLoginResponse response = ownerService.login(request);
        Message message = new Message(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //매장 정보 조회//
    @GetMapping("/{ownerIndex}")
    public ResponseEntity<Message> readCafe(@PathVariable("ownerIndex") Long ownerId ){
        Owner owner = ownerService.findById(ownerId);
        Cafe cafe = cafeService.findById(owner.getCafe().getId());

        OwnerCafe result = OwnerCafe.of(cafe);
        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE, result);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /** 주문 도착알람 */
    @GetMapping("/alarm")
    public ResponseEntity<Message> readAlarm(@RequestHeader("ownerIndex") Long ownerId){
        Owner owner = ownerService.findById(ownerId);
        OrderAlarmDto result = null;
        LocalDateTime tempTime = LocalDateTime.of(2020,12,1,00,00,00);

        for(Order order : owner.getCafe().getOrderSet()){
            if (order.getStatus() == PLACED) {
                if(order.getOrderTime().isAfter(tempTime)){
                    result = OrderAlarmDto.of(order);
                    tempTime = order.getOrderTime();
                }
            }
        }
        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE, result);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
