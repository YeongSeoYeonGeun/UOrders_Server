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
    @ExceptionHandler({
            UserNotFoundException.class,
            CartMenuNotFoundException.class,
            CartNotFoundException.class,
            MenuNotFoundException.class,
            OrderNotFoundException.class,
            OrderMenuNotFoundException.class,
            OwnerNotFoundException.class,
    })

    public ResponseEntity<Message> BadRequestException(final ChangeSetPersister.NotFoundException exception){
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_VALUE);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
