package com.example.uorders.exception;

import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.api.constants.Message;
import com.example.uorders.domain.Owner;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(UserNotFoundException e) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartMenuNotFoundException.class)
    public ResponseEntity<Message> handleCartMenuNotFoundException(CartMenuNotFoundException e) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_CARTMENU);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<Message> handleMenuNotFoundException(MenuNotFoundException e) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_MENU);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Message> handleOrderNotFoundException(OrderNotFoundException e) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_ORDER);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderMenuNotFoundException.class)
    public ResponseEntity<Message> handleOrderMenuNotFoundException(OrderMenuNotFoundException e) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_ORDERMENU);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Message> handleOwnerNotFoundException(OwnerNotFoundException e) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_OWNER);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CafeNotFoundException.class)
    public ResponseEntity<Message> handleCafeNotFoundException(CafeNotFoundException e) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_CAFE);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<Message> handleLoginFailException(LoginFailException e) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
