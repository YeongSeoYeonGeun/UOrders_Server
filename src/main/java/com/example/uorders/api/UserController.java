package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.FavoriteService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.*;
import com.example.uorders.dto.favorite.FavoriteDto;
import com.example.uorders.dto.user.CreateUserResponse;
import com.example.uorders.dto.user.LoginRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final CafeService cafeService;
    private final FavoriteService favoriteService;

    /**
     *  로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequest request) {
        CreateUserResponse response = userService.login(request);
        Message message = new Message(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
