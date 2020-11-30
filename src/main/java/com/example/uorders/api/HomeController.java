package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.User;
import com.example.uorders.dto.cafe.CafeDto;
import com.example.uorders.dto.home.HomeResponse;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/home")
public class HomeController {
    private final UserService userService;
    private final CafeService cafeService;

    /**
     *
     * 홈 화면
     */
    @GetMapping
    public ResponseEntity<Message> cafe(@RequestHeader("userIndex") Long userId) {
        User user = userService.findById(userId);
        String userName = user.getName();
        List<CafeDto> cafeDtoList = cafeService.readCafeList();

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE_LIST, new HomeResponse(userName, cafeDtoList));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
