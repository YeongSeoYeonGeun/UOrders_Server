package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.MenuService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.dto.menu.MenuDto;
import com.example.uorders.dto.menu.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final CafeService cafeService;
    private final MenuService menuService;


    @GetMapping("/menu")
    public ResponseEntity<Message> readMenu(@RequestParam("cafeIndex") Long cafeId, @RequestParam("menuIndex") Long menuId) {
        Cafe cafe = cafeService.findById(cafeId);
        Menu menu = menuService.findById(menuId);
        if(menu == null) {
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_MENU);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        MenuDto menuDto = MenuDto.of(menu);
        MenuResponse response = new MenuResponse(menuDto);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_MENU, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
