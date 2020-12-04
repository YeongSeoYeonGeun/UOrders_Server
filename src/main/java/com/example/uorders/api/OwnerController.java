package com.example.uorders.api;

import com.example.uorders.Service.OwnerService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.dto.owner.OwnerLoginRequest;
import com.example.uorders.dto.owner.OwnerLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/owner")
public class OwnerController {

    private final OwnerService ownerService;

    /** 로그인 */
    public ResponseEntity<Message> loginForOwner(@RequestBody OwnerLoginRequest request) {
        OwnerLoginResponse response = ownerService.login(request);
        Message message = new Message(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
