package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.User;
import com.example.uorders.dto.cafe.CafeDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/cafe")
public class CafeController {

    private final CafeService cafeService;
    private final UserService userService;

    /** 매장 상세 조회 */
    @GetMapping("/{cafeIndex}")
    public ResponseEntity<Message> readCafeDetail(@RequestHeader("userIndex") Long userId, @PathVariable("cafeIndex") Long cafeId) {
        User user = userService.findById(userId);
        Cafe cafe = cafeService.findById(cafeId);

        CafeDetailDto response = cafeService.readCafeDetail(user, cafe);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
