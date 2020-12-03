package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.FavoriteService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.User;
import com.example.uorders.dto.user.CreateUserResponse;
import com.example.uorders.dto.user.LoginRequest;
import com.example.uorders.dto.user.UpdateUserLanguageCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final CafeService cafeService;
    private final FavoriteService favoriteService;

    /** 로그인 */
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequest request) {
        CreateUserResponse response = userService.login(request);
        Message message = new Message(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /** 언어 코드 변경 */
    @PutMapping("/language")
    public ResponseEntity<Message> updateLanguage(@RequestHeader("userIndex") Long userId, @RequestBody UpdateUserLanguageCodeRequest request) {
        User user = userService.findById(userId);
        userService.updateLanguageCode(user, request);

        Message message = new Message(StatusCode.OK, ResponseMessage.UPDATE_USER_LANGUAGE);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
